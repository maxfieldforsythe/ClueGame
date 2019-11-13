package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class gameSetupTests {

	//Copied the test beforeclass method from CTEST
			// We make the Board static because we can load it one time and 
			// then do all the tests. 
			private static Board board;
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
			}
			
	@Test
	public void loadPeople() {
		//Check if correct number of human and computer players have been assigned
		//Functions are used to count the amount of each type in the set
		assertEquals(1, board.numHuman());
		assertEquals(5, board.numComp());
		
		//Checks the 1st, 3rd, and last player for correct attributes based on the txt config
		
		//First player
		assertEquals("Magic Mouse", board.getPlayer(0).getName());
		assertEquals(20, board.getPlayer(0).getRow());
		assertEquals(6, board.getPlayer(0).getColumn());
		assertEquals(board.convertColor("yellow"), board.getPlayer(0).getColor());
		
		//Third player
		assertEquals("The President", board.getPlayer(2).getName());
		assertEquals(15, board.getPlayer(2).getRow());
		assertEquals(21, board.getPlayer(2).getColumn());
		assertEquals(board.convertColor("blue"), board.getPlayer(2).getColor());
		
		//Last player
		assertEquals("Rabid Dog", board.getPlayer(5).getName());
		assertEquals(6, board.getPlayer(5).getRow());
		assertEquals(12, board.getPlayer(5).getColumn());
		assertEquals(board.convertColor("brown"), board.getPlayer(5).getColor());
		
		
	}
	
	@Test
	public void deckOfCards() {
		Set<Card> deck = board.getDeckOfCards();
		int numWeapons = 0, numPeople = 0, numRooms = 0;
		for (Card card : deck) {
			if(card.getType() == CardType.PERSON)
				numPeople++;
			else if(card.getType() == CardType.ROOM)
				numRooms++;
			else if(card.getType() == CardType.WEAPON)
				numWeapons++;
			
		}
		//checks the deck size
		assertEquals(21, deck.size());
		
		//checks for correct number of each type of card
		assertEquals(6, numPeople);
		assertEquals(6, numWeapons);
		assertEquals(9, numRooms);
		
		//makes sure the deck loaded cards correctly
		assert(deck.contains(board.getCard("Bazooka", CardType.WEAPON)));
		assert(deck.contains(board.getCard("Carol", CardType.PERSON)));
		assert(deck.contains(board.getCard("Kitchen", CardType.ROOM)));
		
				
		
	}
	
	@Test
	public void dealCards() {
		
		board.shuffleAndDealCards();
		
		//Gets player list so we can do tests with their cards
		ArrayList<Player> playerList = board.getPlayerList();
		boolean dealtTwice = false;
		int avgCardsPerPlayer = board.getDeckOfCards().size() / board.getPlayerList().size();
		
		Set<Card> testCardsDealt = new HashSet<>();
		for (Player player: playerList) {
			//this test assures each player has roughly the same amount of cards
			assert(player.getCards().size() <= avgCardsPerPlayer +1 &&
					player.getCards().size() >= avgCardsPerPlayer -1);
			//gets each player's set of cards
			for(Card card: player.getCards()) {
				//tests if a card already exists, then adds to test set
				if (testCardsDealt.contains(card))
					dealtTwice = true;
				testCardsDealt.add(card);
				
			}
		}
		//if the test set is equal to the original deck of cards, true
		assert(testCardsDealt.equals(board.getDeckOfCards()));
		//No card should be dealt twice
		assertFalse(dealtTwice);
		
		
		
		
		
	}

}
