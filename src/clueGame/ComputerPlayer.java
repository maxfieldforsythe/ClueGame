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
		
		suggestion.room = 
		Set<Card> availableCards = new ;
	}

	
}
