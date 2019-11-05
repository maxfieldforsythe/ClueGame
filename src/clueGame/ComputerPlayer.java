package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{

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
	public void createSuggestion() {
		
	}

	
}
