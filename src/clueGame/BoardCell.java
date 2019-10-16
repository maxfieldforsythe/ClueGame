package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private boolean door;
	private DoorDirection dir;
	
	public BoardCell() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setDoor(boolean i) {
		this.door = i;
	}
	public void setDir(DoorDirection d) {
		this.dir = d;
	}
	public boolean isWalkway() {
		return true;
	}
	
	public boolean isRoom() {
		return true;
	}
	
	public boolean isDoorway() {
		return this.door;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return this.dir;
	}

	public char getInitial() {
		return this.initial;
	}
	public void setInitial(char c) {
		this.initial = c;
	}

	public void setRow(int i) {
		this.row = i;
	}

	public void setColumn(int j) {
		this.column = j;
	}


}
