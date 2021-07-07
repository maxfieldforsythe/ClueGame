package experiment;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class gui extends JPanel{
	private JTextField die;
	private JTextField guess;
	private JTextField response;
	private JTextField turn;
	
	public gui(){
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,0));
		
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
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue GUI");
		frame.setSize(800, 150);
		// Create the JPanel and add it to the JFrame
		gui gui = new gui();
		frame.add(gui);
		// Now let's view it
		frame.setVisible(true);
		}
	}
	
	





