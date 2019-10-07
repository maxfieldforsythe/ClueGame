package experiment;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;

	public IntBoard() {
		super();
		// add variables
		adjMtx = new HashMap<>();
		visited = new HashSet<>();
		targets = new HashSet<>();
		grid = new BoardCell[4][4];
	}
	
	public void calcAdjacencies() {
		
	}
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return visited;
	}
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	public Set<BoardCell> getTargets() {
		return targets;
	}

	public BoardCell getCell(int i, int j) {
		
		return grid[i][j];
	}
	


	
	
}
