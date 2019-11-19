package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
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
					return bc;
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
	public void makeAccusation() {
		
	}
	public void addSeenCard(Card card) {
		seenCards.add(card);
	}
	public Solution makeSuggestion (Set<Card> cardDeck) {
		
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
		
		for (Card card : cardDeck) {
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
		
		 
				
		return suggestion;
	}
	@Override
	public void makeMove() {
		// TODO Auto-generated method stub
		
	}

	
}
