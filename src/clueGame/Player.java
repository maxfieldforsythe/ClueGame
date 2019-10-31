package clueGame;

import java.awt.Color;

public abstract class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	
	public Player() {
		
	}
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	};
	
	//*****TEST FUNCTIONS*****
	public String getName() {
		return this.playerName;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setName(String i) {
		this.playerName = i;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setColor(Color convertColor) {
		this.color = convertColor;
		
	}
	
	
}
