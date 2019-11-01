package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

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
				board.setConfigFiles("GameBoard.csv", "Rooms.txt");	
				// set the file names to use my config files
				board.setCardFiles("Players.txt", "Weapons.txt");		
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
		//TODO
	}
	
	@Test
	public void dealCards() {
		
	}

}
