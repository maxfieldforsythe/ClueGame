package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import experiment.gui;

public class ClueGame extends JPanel{

	private JTextField die;
	private JTextField guess;
	private JTextField response;
	private JTextField turn;
	
	public ClueGame(){
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,1));
		
		JPanel panel = createButtonPanel();
		add(panel);
		panel = createNamePanel();
		add(panel);
		
		}
	
	private JPanel createNamePanel() {      
		JPanel panel = new JPanel();
		JPanel diePanel = new JPanel();
		JPanel guessPanel = new JPanel();
		JPanel responsePanel = new JPanel();
		JLabel dieLabel = new JLabel("Roll");
		JLabel guessLabel = new JLabel("Guess");
		JLabel responseLabel = new JLabel("Response");
		die = new JTextField(5);
		guess = new JTextField(30);
		response = new JTextField(10);
		die.setEditable(false);
		guess.setEditable(false);
		response.setEditable(false);
		diePanel.add(dieLabel);
		diePanel.add(die);
		diePanel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		panel.add(diePanel);
		
		guessPanel.add(guessLabel);
		guessPanel.add(guess);
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		panel.add(guessPanel);
		
		responsePanel.add(responseLabel);
		responsePanel.add(response);
		responsePanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		panel.add(responsePanel);
		return panel;
		}
	
	
	private JPanel createButtonPanel() {
		JButton next = new JButton("Next Player");
		JButton accuse = new JButton("Make Accusation");
		JPanel panel = new JPanel();
		JLabel turnLabel = new JLabel("Who's Turn?");
		JPanel turnPanel = new JPanel();
		turn = new JTextField(20);
		turn.setEditable(false);
		panel.add(turnLabel);
		panel.add(turn);
		panel.add(next);
		panel.add(accuse);
		return panel;
	}
	
	private static JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileExitItem());
		return menu;
	}
	
	private static JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
			
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	
	public static void main(String[] args) {
		Board board1 = new Board();
		board1 = Board.getInstance();
		// set the file names to use my config files
		board1.setConfigFiles("GameBoard.csv", "Rooms.txt");	
		// set the file names to use my config files
		board1.setCardFiles("Players.txt", "Weapons.txt");		
		// Initialize will load BOTH config files 
		board1.initialize();	
		board1.repaint();
		
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue GUI");
		frame.setSize(800, 800);
		// Create the JPanel and add it to the JFrame
		ClueGame clueGame = new ClueGame();
		
		// if you change the order of the frame.add() statements, causes it to render differently
		frame.add(clueGame, BorderLayout.SOUTH);
		frame.add(board1, BorderLayout.CENTER);
		
	
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		
		// Now let's view it
		frame.setVisible(true);
		}

}
