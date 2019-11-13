package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;

public class gameActionTests {

	
	//Copied the test beforeclass method from CTEST
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	private static Card weapon1;
	private static Card weapon2;
	private static Card weapon3;
	private static Card person1;
	private static Card person2;
	private static Card person3;
	private static Card room1;
	private static Card room2;
	private static Card room3;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ourConfigFiles/GameBoard.csv", "ourConfigFiles/Rooms.txt");	
		// set the file names to use my config files
		board.setCardFiles("ourConfigFiles/Players.txt", "ourConfigFiles/Weapons.txt");		
		// Initialize will load BOTH config files 
		board.initialize();		
		
		weapon1 = new Card("Bazooka", CardType.WEAPON);
		weapon2 = new Card("Butter Knife", CardType.WEAPON);
		weapon3 = new Card("Blow Torch", CardType.WEAPON);
		person1 = new Card("Magic Mouse", CardType.PERSON);
		person2 = new Card("Carol", CardType.PERSON);
		person3 = new Card("Rabid Dog", CardType.PERSON);
		room1 = new Card("TV Room", CardType.ROOM);
		room2 = new Card("Gym", CardType.ROOM);
		room3 = new Card("Kitchen", CardType.ROOM);

	}
	
	@Test
	public void randomSelectTests() {
		//Tests the random target selection when no doors are around
		
		Player compPlayer = new ComputerPlayer();
		
		board.clearTargets();

		board.calcTargets(6, 6, 2);
		
		boolean loc_4_6 = false;
		boolean loc_8_6 = false;
		boolean loc_6_8 = false;
		boolean loc_5_7 = false;
		boolean loc_5_5 = false;
		boolean loc_7_7 = false;
		
		for (int i=0; i<100; i++) {
			BoardCell selected = compPlayer.pickLocation(board.getTargets());
			if (selected == board.getCellAt(4, 6)) {
				loc_4_6 = true;
			} else if (selected == board.getCellAt(8, 6)) {
				loc_8_6 = true;
			} else if (selected == board.getCellAt(6, 8)) {
				loc_6_8 = true;
			} else if (selected == board.getCellAt(5, 7)) {
				loc_5_7 = true;
			} else if (selected == board.getCellAt(5, 5)) {
				loc_5_5 = true;
			} else if (selected == board.getCellAt(7, 7)) {
				loc_7_7 = true;
			}
		
		}
		// Ensure each target was selected at least once
		assertTrue(loc_4_6);
		assertTrue(loc_8_6);
		assertTrue(loc_6_8);
		assertTrue(loc_5_7);
		assertTrue(loc_5_5);
		assertTrue(loc_7_7);
		
	}
	
	//Tests that if the room is a possible target, the computer player will select it as long as it was not the last visited
	@Test 
	public void selectRoom() {
		Player compPlayer = new ComputerPlayer();
		boolean passed = true;
		//Tests that the door is chosen in 3 different scenarios every time for 100 trials
		for (int i = 0; i < 100; i++) {
		//Tests that the door is chosen for a location 2 spaces away with a 2 roll
		board.clearTargets();
		board.calcTargets(17, 6, 2);
		if (compPlayer.pickLocation(board.getTargets()) != board.getCellAt(17, 4)) {
			passed = false;
		}
		//Tests that the door is chosen for a location 2 spaces away and a 3 roll
		board.clearTargets();
		board.calcTargets(17, 6, 3);
		if (compPlayer.pickLocation(board.getTargets()) != board.getCellAt(17, 4)) {
			passed = false;
		}
		//Tests for a roll of 6 when 6 spaces from a door. 3 possible doorways so not testing for any specifics
		board.clearTargets();
		board.calcTargets(13, 6, 6);
		if (!compPlayer.pickLocation(board.getTargets()).isDoorway()) {
			passed = false;
		}
		}
		assertEquals(passed, true);
		
	}
	
	//Tests the random selection if the player is starting in a room. This includes the room itself
	@Test 
	public void selectInRoom() {
		Player compPlayer = new ComputerPlayer();
		compPlayer.setColumn(9);
		compPlayer.setRow(14);
		board.clearTargets();

		board.calcTargets(14, 9, 2);
	
		boolean loc_12_9 = false;
		boolean loc_13_8 = false;
		boolean loc_13_10 = false;
		boolean loc_14_9 = false;
		
		for (int i=0; i<100; i++) {
			BoardCell selected = compPlayer.pickLocation(board.getTargets());
			if (selected == board.getCellAt(12, 9)) {
				loc_12_9 = true;
			} else if (selected == board.getCellAt(13, 8)) {
				loc_13_8 = true;
			} else if (selected == board.getCellAt(13, 10)) {
				loc_13_10 = true;
			} else if (selected == board.getCellAt(14, 9)) {
				loc_14_9 = true;
			}
		}
		
		assertTrue(loc_12_9);
		assertTrue(loc_13_8);
		assertTrue(loc_13_10);
		assertTrue(loc_14_9);
		

	}
	
	//Tests the function to compare accusation to the solution
	@Test
	public void accusationTest() {
		//Initialize solution with known value
		board.getSolution().person = "Magic Mouse";
		board.getSolution().room = "Kitchen";
		board.getSolution().weapon = "Rusty Spoon";
		
		//Test with all values we know are correct
		assertTrue(board.accusationCompare("Magic Mouse", "Kitchen", "Rusty Spoon"));
		
		//Test with incorrect name
		assertFalse(board.accusationCompare("Carol", "Kitchen", "Rusty Spoon"));
		
		//Test with incorrect room
		assertFalse(board.accusationCompare("Magic Mouse", "Library", "Rusty Spoon"));
		
		//Test with incorrect weapon
		assertFalse(board.accusationCompare("Magic Mouse", "Kitchen", "Blow Torch"));
		
	}
	
	@Test
	public void createSuggestion() {
		ComputerPlayer player = new ComputerPlayer();
		
		
		player.setRow(12);
		player.setColumn(6);
		
		
		
		player.addSeenCard(board.getCard("Bazooka", CardType.WEAPON));
		player.addSeenCard(board.getCard("Butter Knife", CardType.WEAPON));
		player.addSeenCard(board.getCard("Fly Swatter", CardType.WEAPON));
		player.addSeenCard(board.getCard("Broken Bottle", CardType.WEAPON));
		player.addSeenCard(board.getCard("Blow Torch", CardType.WEAPON));
		
		player.addSeenCard(board.getCard("Magic Mouse", CardType.PERSON));
		player.addSeenCard(board.getCard("Sgt Pepper", CardType.PERSON));
		player.addSeenCard(board.getCard("The President", CardType.PERSON));
		player.addSeenCard(board.getCard("Carol", CardType.PERSON));
		player.addSeenCard(board.getCard("Small Child", CardType.PERSON));
		
		Solution suggestion = player.makeSuggestion(board.getDeckOfCards());
		//testing for correct room getting location
		assertEquals("Kitchen", suggestion.room);
		//testing for correct person when only one remains
		assertEquals("Rabid Dog",suggestion.person);
		//testing for correct weapon when only one remains
		assertEquals("Rusty Spoon", suggestion.weapon);
		
	}
	
	@Test
	public void testCreateSuggestionRand() {
		ComputerPlayer player = new ComputerPlayer();
		
		player.setRow(16);
		player.setColumn(19);
		
		Solution suggestion = player.makeSuggestion(board.getDeckOfCards());
		
		boolean isBazooka = false;
		boolean isButterKnife = false;
		boolean isFlySwatter = false;
		boolean isBrokenBottle = false;
		boolean isBlowTorch = false;
		boolean isRustySpoon = false;
		
		boolean isMagicMouse = false;
		boolean isSgtPepper= false;
		boolean isThePresident = false;
		boolean isCarol = false;
		boolean isSmallChild = false;
		boolean isRabidDog = false;
		
		for (int i = 0; i < 100; i++) {
			suggestion = player.makeSuggestion(board.getDeckOfCards());
			switch(suggestion.weapon){
			case("Bazooka"):
				isBazooka = true;
				break;
			case("Butter Knife"):
				isButterKnife = true;
				break;
			case("Fly Swatter"):
				isFlySwatter = true;
				break;
			case("Broken Bottle"):
				isBrokenBottle = true;
				break;
			case("Blow Torch"):
				isBlowTorch = true;
				break;
			case("Rusty Spoon"):
				isRustySpoon = true;
				break;
			}
			switch(suggestion.person){
			case("Magic Mouse"):
				isMagicMouse = true;
				break;
			case("Sgt Pepper"):
				isSgtPepper = true;
				break;
			case("The President"):
				isThePresident = true;
				break;
			case("Carol"):
				isCarol = true;
				break;
			case("Small Child"):
				isSmallChild = true;
				break;
			case("Rabid Dog"):
				isRabidDog = true;
				break;
			}
		}
			
			assertTrue(isBazooka);
			assertTrue(isButterKnife);
			assertTrue(isFlySwatter);
			assertTrue(isBrokenBottle);
			assertTrue(isBlowTorch);
			assertTrue(isRustySpoon);
			
			assertTrue(isMagicMouse);
			assertTrue(isSgtPepper);
			assertTrue(isThePresident);
			assertTrue(isCarol);
			assertTrue(isSmallChild);
			assertTrue(isRabidDog);
			
		
	}
	
	//Tests for handling suggestions
	@Test
	public void handleSuggestion() {
		//Create a test solution
		Solution solution = new Solution("Small Child", "Fly Swatter", "Dining Room");

		//Setting up 3 players with 3 cards to put in an array similar to the boards player list
		Player mouse = new ComputerPlayer();
		mouse.setName("Magic Mouse");
		Player pres = new HumanPlayer();
		pres.setName("The President");
		Player carol = new ComputerPlayer();
		carol.setName("Carol");
		Player dog = new ComputerPlayer();
		dog.setName("Rabid Dog");
		
		mouse.addCard(weapon1);
		mouse.addCard(room1);
		mouse.addCard(person1);
		pres.addCard(weapon2);
		pres.addCard(room2);
		pres.addCard(person2);
		carol.addCard(weapon3);
		carol.addCard(room3);
		carol.addCard(person3);
		
		dog.setSuggestion(solution);
		
		ArrayList<Player> playerList = new ArrayList<>();
		playerList.add(dog);
		playerList.add(mouse);
		playerList.add(pres);
		playerList.add(carol);
		
		//Tests for each player the disprove function to check for null when none have a solution
		
		assertEquals(board.querySuggestions(playerList, dog.getSuggestion()),null);
		
		
		//Add matching cards for the accuser and  then test for null
		Card match = new Card("Small Child", CardType.PERSON);
		dog.addCard(match);
		assertEquals(board.querySuggestions(playerList, dog.getSuggestion()),null);
		
		//Tests that the humam player will return a card when they are the only one with a match
		Card humanMatch = new Card("Fly Swatter", CardType.WEAPON);
		pres.addCard(humanMatch);
		assertEquals(board.querySuggestions(playerList, dog.getSuggestion()), humanMatch);
		
		//Test when human is the accuser and is the only one that can disprove
		pres.setSuggestion(solution);
		dog.setCards(new ArrayList<Card>());
		dog.setSuggestion(new Solution());
		assertEquals(board.querySuggestions(playerList, pres.getSuggestion()), null);
		
		//Tests when next two players have a match to see if the next one in line is the one to return
		//Human player is next to have their turn in the array so it should always return the humanMatch card that the human has in their deck
		pres.setSuggestion(new Solution());
		dog.setSuggestion(solution);
		Card humanMatch2 = new Card("Dining Room", CardType.ROOM);
		carol.addCard(humanMatch2);
		
		
		assertEquals(board.querySuggestions(playerList, dog.getSuggestion()), humanMatch);
		
		
		//Tests the same as before except the computer player before the human player has the match instead. 
		//Checks to see if the comp is chosen first
		
		carol.setCards(new ArrayList<Card>());
		mouse.addCard(humanMatch2);
	
		assertEquals(board.querySuggestions(playerList, dog.getSuggestion()), humanMatch2);
			
		
		

		
}
	@Test
	public void disproveSuggestion() {
		
		//creates new player
		ComputerPlayer player = new ComputerPlayer();
		//creates suggestion
		Solution suggestion = new Solution();
		suggestion.person = "Carol";
		suggestion.room = "Kitchen";
		suggestion.weapon = "Butter Knife"; 
		//adds card that is in suggestion so that player can disprove
		Card card = player.disproveSuggestion(suggestion);
		//null when player has no matching cards
		assertEquals(card, null);
		
		player.addCard(board.getCard("Carol", CardType.PERSON));
		
		card = player.disproveSuggestion(suggestion);
		//this method should return Carol. Test with one matching card
		assertEquals(card, board.getCard("Carol", CardType.PERSON));
		
		player.addCard(board.getCard("Kitchen", CardType.ROOM));
		player.addCard(board.getCard("Butter Knife", CardType.WEAPON));
		
		boolean suggestCarol = false;
		boolean suggestKitchen = false;
		boolean suggestButterKnife = false;
		//Test random selection if more than one card
		for (int i = 0; i < 20; i ++) {
			card = player.disproveSuggestion(suggestion);
			if (card.getName().contentEquals("Carol")) {
				suggestCarol = true;
			}
			else if(card.getName().contentEquals("Butter Knife")) {
				suggestButterKnife = true;
			}
			else if (card.getName().contentEquals("Kitchen")) {
				suggestKitchen = true;
			}
			
		}
		
		assertTrue(suggestButterKnife);
		assertTrue(suggestCarol);
		assertTrue(suggestKitchen);
	

		
	}
	
}
