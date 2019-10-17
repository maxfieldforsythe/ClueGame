/*
 * Caroline Arndt
 * Maxfield Forsythe
 */
package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class OurTests {

	public static final int LEGEND_SIZE = 10;
	public static final int NUM_ROWS = 21;
	public static final int NUM_COLUMNS = 22;
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
	@Test
	public void testLegend() {
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());
		assertEquals("Kitchen", legend.get('K'));
		assertEquals("Walkway", legend.get('W'));
		assertEquals("Dining room", legend.get('D'));
		assertEquals("TV Room", legend.get('T'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(12, 6);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(5, 15);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(11, 16);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(14, 9);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		room = board.getCellAt(14, 14);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(4, 6);
		assertFalse(cell.isDoorway());		

	}
	
	@Test 
	public void numDoors() {
		int totalDoors = 0;
		for (int i = 0; i < NUM_ROWS; i ++) {
			for (int j = 0; j < NUM_COLUMNS; j++) {
				if (board.getCellAt(i, j).isDoorway()) {
					System.out.println( board.getCellAt(i, j).getInitial());
					totalDoors++;
				}
			}
		}
		assertTrue(totalDoors == 9);
	}
	
	@Test
	public void correctLetter() {
		BoardCell room = new BoardCell();
		// test the closet
		room = board.getCellAt(3, 3);
		assertTrue(room.getInitial() == 'C');
		// test the Tv room
		room = board.getCellAt(4, 11);
		assertTrue(room.getInitial() == 'T');
		// test the Bar
		room = board.getCellAt(7, 19);
		assertTrue(room.getInitial() == 'B');
		// test the studio
		room = board.getCellAt(19, 14);
		assertTrue(room.getInitial() == 'S');
	}

}
