package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

public abstract class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards;
	
	public Player() {
		myCards = new ArrayList<>();
	}
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	};
	public void addCard(Card card) {
		myCards.add(card);
	}
	
	
	public abstract BoardCell pickLocation (Set<BoardCell> targets);
	
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
	public ArrayList<Card> getCards(){
		return myCards;
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
