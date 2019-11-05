package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.BoardCell;

public class gameActionTests {

	
	//Copied the test beforeclass method from CTEST
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("GameBoard.csv", "Rooms.txt");	
		// set the file names to use my config files
		board.setCardFiles("Players.txt", "Weapons.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
		

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
}
