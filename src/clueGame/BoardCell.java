/*
 * Caroline Arndt
 * Maxfield Forsythe
 */
package clueGame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BoardCell extends JPanel{
	private int row;
	private int column;
	private char initial;
	private String roomName;
	private boolean door;
	private DoorDirection direction;
	private boolean room;
	
	public BoardCell() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardCell(int row, int column, char initial, boolean door, DoorDirection direction, boolean room) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.door = door;
		this.direction = direction;
		this.room = room;
	}
	public void setDoor(boolean i) {
		this.door = i;
	}
	public void setDir(DoorDirection d) {
		this.direction = d;
	}
	public DoorDirection getDir() {
		return direction;
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
		return this.direction;
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

	public void drawBox(Graphics g) {
		
		super.paintComponent(g);
		if (this.isRoom()) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(this.column * 28, this.row * 28, 28, 28);
		} else {
			g.setColor(Color.YELLOW);
			g.fillRect(this.column * 28, this.row * 28, 28, 28);
			g.setColor(Color.BLACK);
			g.drawRect(this.column * 28, this.row * 28, 28, 28);
		}
		if (this.isDoorway()) {
			if (this.getDoorDirection() == DoorDirection.UP) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(this.column * 28, this.row * 28, 28, 28);
				g.setColor(Color.BLUE);
				g.fillRect(this.column * 28, this.row * 28, 28, 8);
			} else if (this.getDoorDirection() == DoorDirection.LEFT) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(this.column * 28, this.row * 28, 28, 28);
				g.setColor(Color.BLUE);
				g.fillRect(this.column * 28, this.row * 28, 8, 28);
			} else if (this.getDoorDirection() == DoorDirection.DOWN) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(this.column * 28, this.row * 28, 28, 28);
				g.setColor(Color.BLUE);
				g.fillRect(this.column * 28, this.row * 28 +20, 28, 8);
			} else if (this.getDoorDirection() == DoorDirection.RIGHT) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(this.column * 28, this.row * 28, 28, 28);
				g.setColor(Color.BLUE);
				g.fillRect(this.column * 28 +20, this.row * 28, 8, 28);
			}
		}
		
		
	}

}
