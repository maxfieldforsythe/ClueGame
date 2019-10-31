package clueGame;

import java.awt.Color;

public abstract class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	public Player() {
		// TODO Auto-generated constructor stub
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
}
