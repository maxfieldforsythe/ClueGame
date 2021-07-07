package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ComputerPlayer extends Player{
	
	private Set<BoardCell> seen = new HashSet<>();
	
	private Set<Card> seenCards = new HashSet<>();
	public ComputerPlayer(){
		super();
	}
	public BoardCell pickLocation(Set<BoardCell> targets) {
	
		if (Board.getCellAt(this.getRow(), this.getColumn()).isDoorway()) {
			BoardCell bc = Board.getCellAt(this.getRow(), getColumn());
			HashSet<BoardCell> tempTarget = new HashSet<BoardCell>();
			tempTarget.addAll(targets);
			tempTarget.add(bc);
			int size = tempTarget.size();
			int rand = new Random().nextInt(size);
			int i = 0;
			for(BoardCell cell : tempTarget)
			{
			    if (i == rand)
			        return cell;
			    i++;
			}
			
		} else {
			for (BoardCell bc: Board.getTargets()) {
				if (bc.isDoorway()) {
					if (!seen.contains(bc)) {
					return bc;
					}
				}
			}
			
			int size = targets.size();
			int rand = new Random().nextInt(size);
			int i = 0;
			for(BoardCell cell : targets)
			{
			    if (i == rand)
			        return cell;
			    i++;
			}
		}
		return null;
		
		
	}
	public void makeAccusation(String person, String room, String weapon, Board b) {
		
		if (person.contentEquals(b.getSolution().person) && room.contentEquals(b.getSolution().room) &&  weapon.contentEquals(b.getSolution().weapon )) {
			JOptionPane.showMessageDialog(new JFrame(), (this.getName() + " has correctly guessed the crook! \n" + person + " in the " + room + " with the " + weapon), "Winner",
			        JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		
		} else {
			JOptionPane.showMessageDialog(new JFrame(), this.getName() +"'s accusation is incorrect \n" + person + " in the " + room + " with the " + weapon, "Incorrect",
			        JOptionPane.ERROR_MESSAGE);

		}
		
	}
	public void addSeenCard(Card card) {
		seenCards.add(card);
	}
	public Solution makeSuggestion (Set<Card> cardDeck, Board b) {
		
		Solution suggestion = new Solution();
		Set<Card> peopleCards = new HashSet<>();
		Set<Card> weaponCards = new HashSet<>();
		
		
		char roomInitial = Board.getCellAt(this.getRow(), this.getColumn()).getInitial();
		switch(roomInitial) {
		case('C'):
			suggestion.room = "Common Room";
			break;
		case('K'):
			suggestion.room = "Kitchen";
			break;
			
		case('B'):
			suggestion.room = "Bar";
			break;
		case('G'):
			suggestion.room = "Gym";
			break;
		case('R'):
			suggestion.room = "Reading Room";
			break;
		case('U'):
			suggestion.room = "Studio";
			break;
		case('H'):
			suggestion.room = "Hall";
			break;
		case('T'):
			suggestion.room = "TV Room";
			break;
		case('D'):
			suggestion.room = "Dining Room";
			break;
			
		
		}
		
		Set<Card> tempDeck = cardDeck;
		Card tempPersonCard = new Card();
		Card tempWeaponCard = new Card();
		Card tempRoomCard = new Card();
		tempPersonCard.setCardName(b.getSolution().person);
		tempPersonCard.setCardType(CardType.PERSON);
		tempDeck.add(tempPersonCard);
		tempWeaponCard.setCardName(b.getSolution().weapon);
		tempWeaponCard.setCardType(CardType.WEAPON);
		tempDeck.add(tempWeaponCard);
		tempRoomCard.setCardName(b.getSolution().room);
		tempRoomCard.setCardType(CardType.ROOM);
		tempDeck.add(tempRoomCard);
		
		for (Card c: getMyCards()) {
			seenCards.add(c);
		}
		
		for (Card card : tempDeck) {
			if (!seenCards.contains(card)) {
				
				
				if(card.getType() == CardType.WEAPON) {
					weaponCards.add(card);
				}
				
				else if(card.getType() == CardType.PERSON) {
					peopleCards.add(card);
				}
			}
		}
		
		int size = peopleCards.size();
		int rand = new Random().nextInt(size);
		int i = 0;
		for(Card card : peopleCards )
		{
		    if (i == rand) {
		    	suggestion.person = card.getName();
		    }
		        ;
		    i++;
		}
		size = weaponCards.size();
		rand = new Random().nextInt(size);
		i = 0;
		for(Card card : weaponCards )
		{
		    if (i == rand) {
		    	suggestion.weapon = card.getName();
		    }
		        
		    i++;
		}
		
		 
		this.setSuggestion(suggestion);
		Card disproven = b.querySuggestions(b.getPlayerList(), suggestion);
		
		if (disproven == null) {
			b.disprove = "No new clue";
		} else {
			if (disproven.getCardType() == CardType.ROOM) {
				seen.add(b.getCellAt(this.getRow(), this.getColumn()));
			}
		b.disprove = disproven.getCardName();
		b.wasGuessed = false;
		}
		
		seenCards.add(disproven);
		return suggestion;
	}
	

	
	@Override
	public void makeMove(Board b) {
		
		
		if (b.disprove.contentEquals("No new clue")) {
			makeAccusation(b.suggestName, b.suggestRoom, b.suggestWeapon, b);
			b.wasGuessed = true;
		}
		
		
		Solution solution = new Solution();
		solution = null;
		
		BoardCell bc = new BoardCell();
		
		b.clearTargets();

		b.calcTargets(this.getRow(), this.getColumn(), b.getRoll() );
		
		bc = this.pickLocation(b.getTargets());
		
		this.setRow(bc.getRow());
		
		this.setColumn(bc.getColumn());

		
		b.ready = true;
		
		b.repaint();
		
		if(bc.isDoorway()) {
		solution = makeSuggestion(b.getDeckOfCards(), b);
		b.suggestName = solution.person;
		b.suggestRoom = solution.room;
		b.suggestWeapon = solution.weapon;
		}
		
		
		
		
	}
	

	
}
