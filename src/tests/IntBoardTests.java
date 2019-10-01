package tests;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntBoardTests {

	@Before
	public void beforeAll() {
		board = new IntBoard();
	}
	@Test
	public void testAdjacency() {
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1,0)));
		assertTrue(testList.contains(board.getCell(0,1)));
		assertFalse(testList.contains(board.getCell(-1,0)));
		assertFalse(testList.contains(board.getCell(0,-1)));
		assertEquals(2, testList.size());
		
	}
	public void testAdjacency33() {
		BoardCell cell = board.getCell(3,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2,3)));
		assertTrue(testList.contains(board.getCell(,1)));
		assertFalse(testList.contains(board.getCell(-1,0)));
		assertFalse(testList.contains(board.getCell(0,-1)));
		assertEquals(2, testList.size());
	}

}
