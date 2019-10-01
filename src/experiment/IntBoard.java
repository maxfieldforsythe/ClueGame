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
	
	private void calcAdjacencies() {
		
	}
	private Set<BoardCell> getAdjList() {
		
	}
	private void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	
	
}
