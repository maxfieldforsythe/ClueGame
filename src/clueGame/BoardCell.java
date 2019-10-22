/*
 * Caroline Arndt
 * Maxfield Forsythe
 */
package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private boolean door;
	private DoorDirection dir;
	private boolean room;
	
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
	public DoorDirection getDir() {
		return dir;
	}
 	public boolean isWalkway() {
		return true;
	}
	
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public boolean isRoom() {
		return room;
	}
	
	public void setRoom(boolean room) {
		this.room = room;
	}
	public boolean isDoorway() {
		return this.door;
	}

	public DoorDirection getDoorDirection() {
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
