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

}
