package tests;

import java.util.Set;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class OurAdjTargetTests {

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
			// Initialize will load BOTH config files 
			board.initialize();
		}
	
	//Testing spaces that are inside rooms to check for empty sets
	@Test
	public void insideRoomTests() {
		//Tests middle of room
		Set<BoardCell> testList = board.getAdjList(3, 11);
		assertEquals(0, testList.size());
		//Tests a room space that is next to a walkway space
		testList = board.getAdjList(5, 11);
		assertEquals(0, testList.size());

	}
	
	
	//Testing spaces that only have walkways as adjacent locations
		@Test
		public void walkwayAdjacencyTest() {
			//Tests a walkway space that should have 4 surrounding walkway spaces
			Set<BoardCell> testList = board.getAdjList(11, 10);
			assertEquals(4, testList.size());
			//Tests a walkway space that is against a room space with 3 surrounding walkway spaces
			testList = board.getAdjList(11, 7);
			assertEquals(3, testList.size());

		}

}
