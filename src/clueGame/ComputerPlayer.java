package clueGame;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{

	public ComputerPlayer(){
		super();
	}
	public BoardCell pickLocation(Set<BoardCell> targets) {
		
		int size = targets.size();
		int rand = new Random().nextInt(size);
		int i = 0;
		for(BoardCell cell : targets)
		{
		    if (i == rand)
		        return cell;
		    i++;
		}
		return null;
		
	}
	public void makeAccusation() {
		
	}
	public void createSuggestion() {
		
	}

	
}
