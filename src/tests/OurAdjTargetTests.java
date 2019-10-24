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
	//Spaces are green on the spread sheet
	@Test
	public void insideRoomTests() {
		//Tests middle of room
		Set<BoardCell> testList = board.getAdjList(3, 11);
		assertEquals(0, testList.size());
		//Tests a room space that is next to a walkway space
		testList = board.getAdjList(16, 10);
		assertEquals(0, testList.size());

	}
	
	
	//Testing spaces that only have walkways as adjacent locations
	//Spaces are purple on spreadsheet
		@Test
		public void walkwayAdjacencyTests() {
			//Tests a walkway space that should have 4 surrounding walkway spaces
			Set<BoardCell> testList = board.getAdjList(11, 10);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCellAt(11, 9)));
			assertTrue(testList.contains(board.getCellAt(11, 11)));
			assertTrue(testList.contains(board.getCellAt(10, 10)));
			assertTrue(testList.contains(board.getCellAt(12, 10)));
			//Tests a walkway space that is against a room space with 3 surrounding walkway spaces
			testList = board.getAdjList(11, 7);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCellAt(11, 8)));
			assertTrue(testList.contains(board.getCellAt(10, 7)));
			assertTrue(testList.contains(board.getCellAt(12, 7)));
		
		}
		
	//Testing locations on the sides of the board
	//Spaces are yellow on the spreadsheet
		@Test
		public void boardEdgeTests() {
			//Testing a walkway space that is on the top edge of the board and is next to a door that points in its direction (
			Set<BoardCell> testList = board.getAdjList(0, 17);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCellAt(0, 16)));
			assertTrue(testList.contains(board.getCellAt(0, 18)));
			assertTrue(testList.contains(board.getCellAt(1, 17)));
			//Testing a walkway space on the bottom edge of the board that is near a room space
			testList = board.getAdjList(20, 5);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCellAt(19, 5)));
			assertTrue(testList.contains(board.getCellAt(20, 6)));
			//Testing walkway a space on the left edge of the board that is surrounded by 3 other walkway spaces
			testList = board.getAdjList(5, 0);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCellAt(4, 0)));
			assertTrue(testList.contains(board.getCellAt(6, 0)));
			assertTrue(testList.contains(board.getCellAt(5, 1)));
			//Testing a room space in the bottom right corner
			testList = board.getAdjList(20, 21);
			assertEquals(0, testList.size());

		}
		
	//Testing walkway spaces that are beside a room space which is not a door
	//Cyan on the spreadsheet
			@Test
			public void besidesRoomTests() {
				//Testing walkway space that is next to the kitchen with no door
				Set<BoardCell> testList = board.getAdjList(6, 1);
				assertEquals(3, testList.size());
				assertTrue(testList.contains(board.getCellAt(5, 1)));
				assertTrue(testList.contains(board.getCellAt(6, 0)));
				assertTrue(testList.contains(board.getCellAt(6, 2)));
				//Testing walkway space that is next to two kitchen spaces with no door
				testList = board.getAdjList(6, 20);
				assertEquals(2, testList.size());
				assertTrue(testList.contains(board.getCellAt(5, 20)));
				assertTrue(testList.contains(board.getCellAt(6, 19)));
				//Testing walkway space that is between two room spaces and an edge
				testList = board.getAdjList(20, 15);
				assertTrue(testList.contains(board.getCellAt(19, 15)));
				assertEquals(1, testList.size());

			}
				
	//Testing spaces adjacent to doorways with correct direction
	//Spaces are pink on the spreadsheet
			@Test
			public void adjacentToDoorwayTest() {
				//Tests with a space to the left of a leftward door
				Set<BoardCell> testList = board.getAdjList(11, 15);
				assertTrue(testList.contains(board.getCellAt(11, 16)));
				//Tests with a space to the right of a rightward door
				testList = board.getAdjList(12, 7);
				assertTrue(testList.contains(board.getCellAt(12, 6)));
				//Tests with a space below a downward door
				testList = board.getAdjList(6, 15);
				assertTrue(testList.contains(board.getCellAt(5, 15)));
				//Tests with a space above an upward door
				testList = board.getAdjList(15, 19);
				assertTrue(testList.contains(board.getCellAt(16, 19)));
			
			}

	//Testing doorways for their one adjacent space
	//Spaces are white on the spreadsheet
			@Test
			public void doorwayAdjacentSpots() {
				//Tests a left doorway for the spot to its left
				Set<BoardCell> testList = board.getAdjList(0, 18);
				assertTrue(testList.contains(board.getCellAt(0, 17)));
				//Tests a right door for the spot on its right
				testList = board.getAdjList(17, 4);
				assertTrue(testList.contains(board.getCellAt(17, 5)));
				//Tests a down door for the space below
				testList = board.getAdjList(5, 15);
				assertTrue(testList.contains(board.getCellAt(6, 15)));
				//Tests an up door with the space above
				testList = board.getAdjList(14, 9);
				assertTrue(testList.contains(board.getCellAt(13, 9)));
			
			}
			
	// Targets that allow a user to enter a room
	//Orange on the spreadsheet
			@Test
			public void testRoomEntry()
			{
				// two steps from a room two steps away
				board.calcTargets(13, 10, 2);
				Set<BoardCell> targets= board.getTargets();
				assertTrue(targets.contains(board.getCellAt(14, 9)));
				
				//Test entering a door when steps are greater than distance to room
				board.calcTargets(8, 7, 8);
				targets= board.getTargets();
				assertTrue(targets.contains(board.getCellAt(12, 6)));
				
				
				
			}
	
	// Targets calculated when leaving a room
	//Dark red on spreadsheet
			@Test
			public void testRoomExit()
			{
				// One step from room
				board.calcTargets(13, 14, 1);
				Set<BoardCell> targets= board.getTargets();
				assertEquals(1, targets.size());
				assertTrue(targets.contains(board.getCellAt(12, 14)));

				// Take two steps
				board.calcTargets(13, 14, 2);
				targets= board.getTargets();
				assertEquals(3, targets.size());
				assertTrue(targets.contains(board.getCellAt(11, 14)));
				assertTrue(targets.contains(board.getCellAt(12, 13)));
				assertTrue(targets.contains(board.getCellAt(12, 15)));
				
				
			}
	// Tests target length of 1
	//
			@Test
			public void testTargetOneStep()
			{
				//Test one step and check for the right space
				board.calcTargets(0, 7, 1);
				Set<BoardCell> targets= board.getTargets();
				assertEquals(1, targets.size());
				assertTrue(targets.contains(board.getCellAt(1, 7)));

				// Test one step near a door with the incorrect direction
				board.calcTargets(13, 6, 1);
				targets= board.getTargets();
				assertEquals(3, targets.size());
				assertTrue(targets.contains(board.getCellAt(14, 6)));
				assertTrue(targets.contains(board.getCellAt(13, 5)));
				assertTrue(targets.contains(board.getCellAt(13, 7)));
				
				
			}
			
	//Tests target length of 2
	//
			@Test
			public void testTargetsTwoSteps() {
				//Length of 2
				board.calcTargets(8, 16, 2);
				Set<BoardCell> targets= board.getTargets();
				assertEquals(4, targets.size());
				assertTrue(targets.contains(board.getCellAt(6, 16)));
				assertTrue(targets.contains(board.getCellAt(10, 16)));
				assertTrue(targets.contains(board.getCellAt(7, 15)));
				assertTrue(targets.contains(board.getCellAt(9, 15)));
				//Length of 2
				board.calcTargets(15, 15, 2);
				targets= board.getTargets();
				assertEquals(4, targets.size());
				assertTrue(targets.contains(board.getCellAt(13, 15)));
				assertTrue(targets.contains(board.getCellAt(17, 15)));
				assertTrue(targets.contains(board.getCellAt(14, 16)));
				assertTrue(targets.contains(board.getCellAt(15, 17)));
				
				
						
			}	
			
	//Tests target length of 3
	//
			@Test
			public void testTargetsThreeSteps() {
				//Using walkway space that will go near a door with the wrong direction
				board.calcTargets(8, 16, 2);
				Set<BoardCell> targets= board.getTargets();
				assertEquals(4, targets.size());
				assertTrue(targets.contains(board.getCellAt(6, 16)));
				assertTrue(targets.contains(board.getCellAt(10, 16)));
				assertTrue(targets.contains(board.getCellAt(7, 15)));
				assertTrue(targets.contains(board.getCellAt(9, 15)));
				
						
			}	
			
	//Tests target length of 4
	//
			@Test
			public void testTargetsFourSteps() {
				//Using random walkway space
				board.calcTargets(5, 18, 3);
				Set<BoardCell> targets= board.getTargets();
				assertEquals(10, targets.size());
				assertTrue(targets.contains(board.getCellAt(5, 21)));
				assertTrue(targets.contains(board.getCellAt(6, 20)));
				assertTrue(targets.contains(board.getCellAt(4, 20)));
				assertTrue(targets.contains(board.getCellAt(5, 19)));
				assertTrue(targets.contains(board.getCellAt(3, 17)));
				assertTrue(targets.contains(board.getCellAt(6, 16)));
				assertTrue(targets.contains(board.getCellAt(4, 16)));
				assertTrue(targets.contains(board.getCellAt(5, 17)));
				assertTrue(targets.contains(board.getCellAt(4, 18)));
				assertTrue(targets.contains(board.getCellAt(6, 18)));
				
						
			}
			
	

	
		
	
}
