package experiment;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class IntBoard {
	public Map<BoardCell, Set<BoardCell>> adjMtx;
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
		makeGrid();
	}
	public void makeGrid() {
		
		for (int i = 0; i < 4; i++) {
			for(int j = 0; j <4; j++) {
				BoardCell temp = new BoardCell();
				temp.x = i;
				temp.y = j;
				grid[i][j] = temp;

			}
		}
	}
	//Adds adjacency for square left right above and below target to a set then adds that set to  map with the initial square as the key
	public void calcAdjacencies() {
		BoardCell tempCell = new BoardCell();
		BoardCell addCell = new BoardCell();
		HashSet tempSet = new HashSet<BoardCell>();
		for (int i = 0; i <4; i++) {
			for (int j = 0; j < 4; j++) {
				tempCell = new BoardCell();
				tempSet = new HashSet<BoardCell>();
				tempCell.x = i;
				tempCell.y = j;
				if (i-1 >= 0) {
					addCell = new BoardCell();
					addCell.x = i -1;
					addCell.y=j;
					tempSet.add(getCell(addCell.x,addCell.y));
				}
				if (j-1 >= 0) {
					addCell = new BoardCell();

					addCell.x = i;
					addCell.y=j-1;
					tempSet.add(getCell(addCell.x,addCell.y));
				}
				if (i+1 <= 3) {
					addCell = new BoardCell();

					addCell.x = i +1;
					addCell.y=j;
					tempSet.add(getCell(addCell.x,addCell.y));
				}
				if (j+1 <= 3) {
					addCell = new BoardCell();

					addCell.x = i;
					addCell.y=j+1;
					tempSet.add(getCell(addCell.x,addCell.y));
				}
				this.adjMtx.put(getCell(tempCell.x,tempCell.y),tempSet);
			}
			
		}
	}
	//getter for adjacency list
	public Set<BoardCell> getAdjList(BoardCell cell) {
		for (BoardCell key: adjMtx.keySet()) {
			if (key.x == cell.x && key.y == cell.y) {
				return adjMtx.get(key);			}
			
		}
		return null;
	}
	//CAlculated targets recursively 
	public void calcTargets(BoardCell startCell, int pathLength) {
		
		adjMtx.get(startCell);
		for (BoardCell cell : adjMtx.get(startCell)) {
			if (visited.contains(cell)) {
				continue;
			}
			visited.add(getCell(startCell.x, startCell.y));

			if (pathLength > 1 ) {
				calcTargets(cell,pathLength - 1);
			}
			else {
				targets.add(cell);
			}
			visited.remove(cell);
		}
		
		
	}
	//Getter for targets
	public Set<BoardCell> getTargets() {
		
		return targets;
	}
//Getter for cell using grid
	public BoardCell getCell(int i, int j) {
		
		if (i >= 4 || j >= 4 || i <0 || j<0) {
			return null;
		}
		
		return grid[i][j];
	
	}

	
	
}

