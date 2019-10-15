package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private Character initial;
	
	public BoardCell() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public boolean isWalkway() {
		return true;
	}
	
	public boolean isRoom() {
		return true;
	}
	
	public boolean isDoorway() {
		return true;
	}
	
}
