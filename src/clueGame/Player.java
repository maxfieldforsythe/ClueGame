package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;

public abstract class Player extends JPanel {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards;
	private Solution suggestion = new Solution();
	
	public Player() {
		myCards = new ArrayList<>();
	}
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> myCardsInSuggestion = new ArrayList<>();
		
		//Loop adds cards that match the suggestion to an array list
		for(Card card: myCards) {
			if (card.getName().contentEquals(suggestion.person)) {
				myCardsInSuggestion.add(card);
			}
			else if (card.getName().contentEquals(suggestion.weapon)) {
				myCardsInSuggestion.add(card);
			}
			else if (card.getName().contentEquals(suggestion.room)) {
				myCardsInSuggestion.add(card);
			}
		}
		
		if (myCardsInSuggestion.size() == 0) {
			return null;
		}
		else if(myCardsInSuggestion.size() == 1) {
			return myCardsInSuggestion.get(0);
		}
		else {
			int rand = new Random().nextInt(myCardsInSuggestion.size());
			return myCardsInSuggestion.get(rand);
		}
	};
	public void addCard(Card card) {
		myCards.add(card);
	}
	
	
	public abstract BoardCell pickLocation (Set<BoardCell> targets);
	
public void drawPlayer(Graphics g) {
		
		super.paintComponent(g);
		g.setColor(this.color);
		g.fillOval(this.column * 28 ,this.row *28 , 28 , 28 );
		g.setColor(color.BLACK);
		g.drawOval(this.column * 28 ,this.row *28 , 28 , 28 );
}
	
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
	public Solution getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(Solution suggestion) {
		this.suggestion = suggestion;
	}
	public void setCards(ArrayList<Card> cards) {
		this.myCards =  cards;
	}
	
	
}
