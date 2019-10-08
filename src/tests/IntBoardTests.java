package tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	
	
	IntBoard board = new IntBoard();
	@Before
	 public void beforeAll() {
         board = new IntBoard();  
         board.calcAdjacencies();

         Set<BoardCell> testList = new HashSet<BoardCell>();
         Set<BoardCell> targets1 = new HashSet<BoardCell>();
      }

	
	@Test
	public void testAdjacency00() {
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0,1)));
		assertTrue(testList.contains(board.getCell(1,0)));
		assertFalse(testList.contains(board.getCell(-1,0)));
		assertFalse(testList.contains(board.getCell(0,-1)));
		assertEquals(2, testList.size());
		
	}
	@Test
	public void testAdjacency33() {
		BoardCell cell = board.getCell(3,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2,3)));
		assertTrue(testList.contains(board.getCell(3,2)));
		assertFalse(testList.contains(board.getCell(4,3)));
		assertFalse(testList.contains(board.getCell(3,4)));
		assertEquals(2, testList.size());
	}
	@Test
	public void testAdjacency13() {
		BoardCell cell = board.getCell(1,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1,2)));
		assertTrue(testList.contains(board.getCell(0,3)));
		assertTrue(testList.contains(board.getCell(2,3)));
		assertFalse(testList.contains(board.getCell(1,4)));
		assertFalse(testList.contains(board.getCell(0,2)));
		assertFalse(testList.contains(board.getCell(2,2)));
		assertEquals(3, testList.size());
	}
	@Test
	public void testAdjacency30() {
		BoardCell cell = board.getCell(3,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2,0)));
		assertTrue(testList.contains(board.getCell(3,1)));
		assertFalse(testList.contains(board.getCell(2,1)));
		assertFalse(testList.contains(board.getCell(3,-1)));
		assertFalse(testList.contains(board.getCell(4,0)));
		assertEquals(2, testList.size());
	}
	@Test
	public void testAdjacency11() {
		BoardCell cell = board.getCell(1,1);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0,1)));
		assertTrue(testList.contains(board.getCell(2,1)));
		assertTrue(testList.contains(board.getCell(1,0)));
		assertTrue(testList.contains(board.getCell(1,2)));
		assertFalse(testList.contains(board.getCell(0,0)));
		assertFalse(testList.contains(board.getCell(2,2)));
		assertFalse(testList.contains(board.getCell(2,0)));
		assertFalse(testList.contains(board.getCell(0,2)));
		assertFalse(testList.contains(board.getCell(1,-1)));
		assertFalse(testList.contains(board.getCell(1,3)));
		assertFalse(testList.contains(board.getCell(-1,1)));
		assertFalse(testList.contains(board.getCell(3,1)));
		assertEquals(4, testList.size());
	}
	
	@Test
	public void testAdjacency22() {
		BoardCell cell = board.getCell(2,2);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2,1)));
		assertTrue(testList.contains(board.getCell(1,2)));
		assertTrue(testList.contains(board.getCell(3,2)));
		assertTrue(testList.contains(board.getCell(2,3)));
		assertFalse(testList.contains(board.getCell(3,1)));
		assertFalse(testList.contains(board.getCell(3,3)));
		assertFalse(testList.contains(board.getCell(1,1)));
		assertFalse(testList.contains(board.getCell(1,3)));
		assertFalse(testList.contains(board.getCell(0,2)));
		assertFalse(testList.contains(board.getCell(2,0)));
		assertFalse(testList.contains(board.getCell(4,2)));
		assertFalse(testList.contains(board.getCell(2,4)));
		assertEquals(4, testList.size());
	}
	
	//Tests space 0,0 with a 2 roll
	@Test
	public void targettest002() {
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set <BoardCell>targets1 = board.getTargets();
		assertEquals(3, targets1.size());
		assertTrue(targets1.contains(board.getCell(2, 0)));
		assertTrue(targets1.contains(board.getCell(0, 2)));
		assertTrue(targets1.contains(board.getCell(1, 1)));
		//Tests that the function will not use visited targets
		assertFalse(targets1.contains(board.getCell(0, 0)));
	}
	//Tests space 0,0 with a 4 roll
	@Test
	public void targettest004() {
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 4);
		Set <BoardCell>targets1 = board.getTargets();
//		assertEquals(6, targets.size());
		assertTrue(targets1.contains(board.getCell(3, 1)));
		assertTrue(targets1.contains(board.getCell(1, 3)));
		assertTrue(targets1.contains(board.getCell(2, 2)));
		assertTrue(targets1.contains(board.getCell(2, 0)));
		assertTrue(targets1.contains(board.getCell(0, 2)));
		assertTrue(targets1.contains(board.getCell(2, 0)));
		//Checks for backtracking to visited spaces
		assertFalse(targets1.contains(board.getCell(0, 0)));
	}
	//Tests space 0,0 with a 3 roll
	@Test
	public void targettest003() {
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
//		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));	
	}
	
	//Tests space 1,1 with a 2 roll
		@Test
		public void targettest112() {
			BoardCell cell = board.getCell(1, 1);
			board.calcTargets(cell, 2);
			Set targets = board.getTargets();
//			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCell(0, 0)));
			assertTrue(targets.contains(board.getCell(0, 2)));
			assertTrue(targets.contains(board.getCell(2, 0)));
			assertTrue(targets.contains(board.getCell(1, 3)));
			assertTrue(targets.contains(board.getCell(2, 2)));
			assertTrue(targets.contains(board.getCell(3, 1)));
			//Tests that the function will not use visited targets
			assertFalse(targets.contains(board.getCell(1, 1)));
		}
	//Tests space 1,1 with a 3 roll
			@Test
			public void targettest113() {
				BoardCell cell = board.getCell(1, 1);
				board.calcTargets(cell, 3);
				Set targets = board.getTargets();
//				assertEquals(6, targets.size());
				assertTrue(targets.contains(board.getCell(1, 0)));
				assertTrue(targets.contains(board.getCell(1, 2)));
				assertTrue(targets.contains(board.getCell(3, 0)));
				assertTrue(targets.contains(board.getCell(3, 2)));
				assertTrue(targets.contains(board.getCell(2, 3)));
				assertTrue(targets.contains(board.getCell(3, 2)));
				assertTrue(targets.contains(board.getCell(0, 3)));
				assertTrue(targets.contains(board.getCell(0, 1)));
				assertTrue(targets.contains(board.getCell(2, 1)));
				//Tests that the function will not use visited targets
				assertFalse(targets.contains(board.getCell(1, 1)));
			}
			
			//Tests space 1,1 with a 4 roll
			@Test
			public void targettest114() {
				BoardCell cell = board.getCell(1, 1);
				board.calcTargets(cell, 4);
				Set targets = board.getTargets();
//				assertEquals(6, targets.size());
				assertTrue(targets.contains(board.getCell(2, 0)));
				assertTrue(targets.contains(board.getCell(2, 2)));
				assertTrue(targets.contains(board.getCell(1, 3)));
				assertTrue(targets.contains(board.getCell(3, 3)));
				assertTrue(targets.contains(board.getCell(2, 2)));
				assertTrue(targets.contains(board.getCell(3, 1)));
				assertTrue(targets.contains(board.getCell(0, 2)));
				assertTrue(targets.contains(board.getCell(3, 1)));
				assertTrue(targets.contains(board.getCell(2, 1)));
				//Tests that the function will not use visited targets
				assertFalse(targets.contains(board.getCell(1, 1)));
			}
	
	
}
