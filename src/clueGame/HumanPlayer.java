package clueGame;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class HumanPlayer extends Player{

	
	
	
	
	public HumanPlayer() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public BoardCell pickLocation(Set<BoardCell> targets) {
		// TODO Auto-generated method stub
		return null;
	}
	
public void makeAccusation(String person, String room, String weapon, Board b) {
		
		
		if (person.contentEquals(b.getSolution().person) && room.contentEquals(b.getSolution().room) &&  weapon.contentEquals(b.getSolution().weapon )) {
			JOptionPane.showMessageDialog(new JFrame(), "You have correctly guessed the crook!", "Winner",
			        JOptionPane.ERROR_MESSAGE);
			System.exit((0));
		
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Your accusation is incorrect", "Incorrect",
			        JOptionPane.ERROR_MESSAGE);

		}
		
	}

	@Override
	public void makeMove(Board b) {
			
		Solution solution = new Solution();
		solution = null;
		
		BoardCell bc = new BoardCell();
		
		b.clearTargets();
		
		this.setRow(b.r.y/28);
		
		this.setColumn(b.r.x/28);
		
		b.ready = true;

		b.repaint();
		
		
		if (b.getCellAt(this.getRow(), this.getColumn()).isDoorway()) {
		MyDialog md = new MyDialog(b, b.getCellAt(this.getRow(), this.getColumn()).getInitial());
		md.setVisible(true);		
		
		}
		
		
		
		
	}
	
	

}
