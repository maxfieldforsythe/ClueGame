package clueGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import experiment.gui;

public class ClueGame extends JPanel{

	private JTextField die;
	static JTextField guess;
	static JTextField response;
	private JTextField turn;
	static MyDialog dialog;
	static Player tempPlayer = null;
	static Board board1 = new Board();
	static int counter = 0;
	public static boolean canAccuse;

	
	public ClueGame(Board b){
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,1));
		
		JPanel panel = createButtonPanel(b);
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
	
	
	private JPanel createButtonPanel(Board b) {
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
		
		accuse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
				if (tempPlayer instanceof HumanPlayer) {
					
					if (canAccuse == true) {
					JDialog jd = new JDialog();
					jd.setTitle("Make a suggestion");
					jd.setLayout(new FlowLayout());
					jd.setSize(700, 200);
					JPanel peoplePanel = new JPanel();
					
					String[] peopleArr = {"????","Magic Mouse", "The President", "Rabid Dog", "Carol", "Sgt Pepper", "Small Child"};
					JComboBox peopleCombo = new JComboBox(peopleArr);
					peopleCombo.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));

					
					String[] roomArr = {"????","Kitchen", "TV Room", "Hall", "Dining Room", "Reading Room", "Gym", "Common Room", "Studio", "Bar"};
					JComboBox roomCombo = new JComboBox(roomArr);
					roomCombo.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
					
					String[] weaponArr = {"????","Bazooka", "Butter Knife", "Fly Swatter", "Broken bottle", "Blow Torch", "Rusty Spoon"};
					JComboBox weaponCombo = new JComboBox(weaponArr);
					weaponCombo.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
					
					JButton jb = new JButton("Accuse");
					
					peoplePanel.add(peopleCombo);
					peoplePanel.add(roomCombo);
					peoplePanel.add(weaponCombo);
					peoplePanel.add(jb);
					
					jb.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							jd.dispose();
						tempPlayer.makeAccusation(String.valueOf(peopleCombo.getSelectedItem()),String.valueOf(roomCombo.getSelectedItem()), String.valueOf(weaponCombo.getSelectedItem()), b);
							canAccuse = false;
						}
						
						
						});
					
					
					
					jd.add(peoplePanel);
					
					jd.setVisible(true);
					}
					
			} else { 
				JOptionPane.showMessageDialog(new JFrame(), "It is not your turn :(", "Invalid Attempt",
				        JOptionPane.ERROR_MESSAGE);
			}


			
			}
			}
		);
		
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			ClueGame.nextPlayer(turn, die);
				
			}

			
			});
		return panel;
	}
	
	public static void nextPlayer(JTextField jt, JTextField jt2) {
		
			if (board1.ready == false) {
				JOptionPane.showMessageDialog(new JFrame(), "Please select a space to move to before continuing to next player", "Not Ready",
				        JOptionPane.ERROR_MESSAGE);
			} else {
			
			board1.ready = false;
		
			board1.setRoll(new Random().nextInt(6)+1);
		
			tempPlayer = board1.getPlayer(counter % board1.getPlayerList().size() );
		
			
			if (tempPlayer instanceof HumanPlayer) {
				canAccuse = true;
				board1.tempPlayer = board1.getPlayer(counter % board1.getPlayerList().size() );
				board1.clearTargets();
				board1.calcTargets(tempPlayer.getRow(), tempPlayer.getColumn(), board1.getRoll());
				Set<BoardCell> tempSet = new HashSet<>();
				tempSet = board1.getTargets();
				for (BoardCell bc: tempSet) {
					bc.drawBoxCyan(board1.getGraphics());
					Rectangle rect = new Rectangle(bc.getColumn() * 28, bc.getRow() * 28, 28, 28);
					board1.targetRect.add(rect);
				}
			}
			
			jt.setText(tempPlayer.getName());
			
			jt2.setText(String.valueOf(board1.getRoll()));
			
			if (tempPlayer instanceof ComputerPlayer) {
			tempPlayer.makeMove(board1);
			}
			
			counter += 1;
			
			guess.setText(board1.suggestName + " " + board1.suggestRoom + " " + board1.suggestWeapon);
			response.setText(board1.disprove);
			}
		
	}

	private static JPanel createCardPanel(Board b) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		panel.setSize(75, 500);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Cards"));
		
		JPanel peoplePanel = new JPanel();
		JPanel roomPanel = new JPanel();
		JPanel weaponPanel = new JPanel();
		
		JTextArea people = new JTextArea(9,9);
		JTextArea rooms = new JTextArea(9,9);
		JTextArea weapon = new JTextArea(9,9);
		
		for (Card c: b.getPlayer(0).getMyCards()) {
			if (c.getType() == CardType.PERSON) {
				people.append(c.getName() + "\n\n");
			} else if (c.getType() == CardType.ROOM) {
				rooms.append(c.getName() + "\n\n");
			} else if (c.getType() == CardType.WEAPON) {
				weapon.append(c.getName() + "\n\n");
			}
		}
		
		people.setEditable(false);
		rooms.setEditable(false);
		weapon.setEditable(false);
		
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		
		peoplePanel.add(people);
		roomPanel.add(rooms);
		weaponPanel.add(weapon);
		
		panel.add(peoplePanel);
		panel.add(roomPanel);
		panel.add(weaponPanel);
		
		return panel;
	}
	
	//Creates the file menu
	private static JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileExitItem());
		menu.add(createDialogItem());
		return menu;
	}
	//Creates the menu item for exit
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
	
	private static JMenuItem createDialogItem() {
	JMenuItem item = new JMenuItem("Notes");	
	
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent  e) {
			if (dialog == null) {
			dialog = new MyDialog();
			}
			dialog.setVisible(true);
		}
	}
	item.addActionListener(new ButtonListener());
	return item;
	
	}
	
	
	
	public static void main(String[] args) {
		board1 = Board.getInstance();
		// set the file names to use my config files
		board1.setConfigFiles("ourConfigFiles/GameBoard.csv", "ourConfigFiles/Rooms.txt");	
		// set the file names to use my config files
		board1.setCardFiles("ourConfigFiles/Players.txt", "ourconfigfiles/Weapons.txt");		
		// Initialize will load BOTH config files 
		board1.initialize();	
		board1.repaint();
		
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue GUI");
		frame.setSize(800, 800);
		// Create the JPanel and add it to the JFrame
		ClueGame clueGame = new ClueGame(board1);
		
		// if you change the order of the frame.add() statements, causes it to render differently
		frame.add(clueGame, BorderLayout.SOUTH);
		frame.add(board1, BorderLayout.CENTER);
		frame.add(createCardPanel(board1), BorderLayout.EAST);
		
		//Add menu bar to the UI and give it the menu item as an argument
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		
		JOptionPane splash = new JOptionPane();
		JOptionPane.showMessageDialog(frame, "You are The President, press Next to begin the game", "Welcome to Clue!", JOptionPane.INFORMATION_MESSAGE);
		// Now let's view it
		frame.setVisible(true);
		
	}

}
