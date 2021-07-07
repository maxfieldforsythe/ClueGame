package clueGame;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class MyDialog extends JDialog {
	private JTextField myName;
	private JPasswordField password;
	public JComboBox peopleCombo;
	public JComboBox roomCombo;
	public JComboBox weaponCombo;

	public MyDialog() {
		//Initialize size and layout
		setTitle("Detective Notes");
		setSize(800, 400);
		setLayout(new GridLayout(3, 2));
		
		JPanel peoplePanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(3,2));
		JLabel peopleLabel = new JLabel();
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		
		JCheckBox mouseButton = new JCheckBox("Magic Mouse");
		mouseButton.setMnemonic(KeyEvent.VK_C); 
	    mouseButton.setSelected(false);
	    
	    JCheckBox presidentButton = new JCheckBox("The President");
		presidentButton.setMnemonic(KeyEvent.VK_C); 
	    presidentButton.setSelected(false);
	    
	    JCheckBox dogButton = new JCheckBox("Rabid Dog");
		dogButton.setMnemonic(KeyEvent.VK_C); 
	    dogButton.setSelected(false);
	    
	    JCheckBox carolButton = new JCheckBox("Carol");
		carolButton.setMnemonic(KeyEvent.VK_C); 
	    carolButton.setSelected(false);
	    
	    JCheckBox sgtButton = new JCheckBox("Sgt Pepper");
		sgtButton.setMnemonic(KeyEvent.VK_C); 
	    sgtButton.setSelected(false);
	    
	    JCheckBox childButton = new JCheckBox("Small Child");
		childButton.setMnemonic(KeyEvent.VK_C); 
	    childButton.setSelected(false);

	    
		peoplePanel.add(mouseButton);
		peoplePanel.add(presidentButton);
		peoplePanel.add(dogButton);
		peoplePanel.add(carolButton);
		peoplePanel.add(sgtButton);
		peoplePanel.add(childButton);


		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(3,3));
		JLabel roomLabel = new JLabel();
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		
		JCheckBox kitchenButton = new JCheckBox("Kitchen");
		kitchenButton.setMnemonic(KeyEvent.VK_C); 
	    kitchenButton.setSelected(false);
	    
	    JCheckBox tvButton = new JCheckBox("TV Room");
		tvButton.setMnemonic(KeyEvent.VK_C); 
	    tvButton.setSelected(false);
	    
	    JCheckBox hallButton = new JCheckBox("Hall");
		hallButton.setMnemonic(KeyEvent.VK_C); 
	    hallButton.setSelected(false);
	    
	    JCheckBox diningButton = new JCheckBox("Dining Room");
		diningButton.setMnemonic(KeyEvent.VK_C); 
	    diningButton.setSelected(false);
	    
	    JCheckBox readingButton = new JCheckBox("Reading Room");
		readingButton.setMnemonic(KeyEvent.VK_C); 
	    readingButton.setSelected(false);
	    
	    JCheckBox gymButton = new JCheckBox("Gym");
		gymButton.setMnemonic(KeyEvent.VK_C); 
	    gymButton.setSelected(false);
	    
	    JCheckBox commonButton = new JCheckBox("Common Room");
		commonButton.setMnemonic(KeyEvent.VK_C); 
	    commonButton.setSelected(false);
	    
	    JCheckBox studioButton = new JCheckBox("Studio");
		studioButton.setMnemonic(KeyEvent.VK_C); 
	    studioButton.setSelected(false);
	    
	    JCheckBox barButton = new JCheckBox("Bar");
		barButton.setMnemonic(KeyEvent.VK_C); 
	    barButton.setSelected(false);

	    
		roomPanel.add(kitchenButton);
		roomPanel.add(tvButton);
		roomPanel.add(hallButton);
		roomPanel.add(diningButton);
		roomPanel.add(readingButton);
		roomPanel.add(gymButton);
		roomPanel.add(commonButton);
		roomPanel.add(studioButton);
		roomPanel.add(barButton);
		
		JPanel weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(3,2));
		JLabel weaponLabel = new JLabel();
		weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		
		JCheckBox bazookaButton = new JCheckBox("Bazooka");
		bazookaButton.setMnemonic(KeyEvent.VK_C); 
	    bazookaButton.setSelected(false);
	    
	    JCheckBox knifeButton = new JCheckBox("Butter Knife");
		knifeButton.setMnemonic(KeyEvent.VK_C); 
	    knifeButton.setSelected(false);
	    
	    JCheckBox flyButton = new JCheckBox("Fly Swatter");
		flyButton.setMnemonic(KeyEvent.VK_C); 
	    flyButton.setSelected(false);
	    
	    JCheckBox bottleButton = new JCheckBox("Broken Bottle");
		bottleButton.setMnemonic(KeyEvent.VK_C); 
	    bottleButton.setSelected(false);
	    
	    JCheckBox torchButton = new JCheckBox("Blow Torch");
		torchButton.setMnemonic(KeyEvent.VK_C); 
	    torchButton.setSelected(false);
	    
	    JCheckBox spoonButton = new JCheckBox("Rusty Spoon");
		spoonButton.setMnemonic(KeyEvent.VK_C); 
	    spoonButton.setSelected(false);
	    
	    weaponPanel.add(bazookaButton);
	    weaponPanel.add(knifeButton);
	    weaponPanel.add(flyButton);
	    weaponPanel.add(bottleButton);
	    weaponPanel.add(torchButton);
	    weaponPanel.add(spoonButton);
	    
	   
		JPanel peopleComboPanel = new JPanel();
		JLabel peopleComboLabel = new JLabel();
		peopleComboPanel.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));

		String[] peopleArr = {"????","Magic Mouse", "The President", "Rabid Dog", "Carol", "Sgt Pepper", "Small Child"};
		JComboBox peopleCombo = new JComboBox(peopleArr);
		
		peopleComboPanel.add(peopleCombo);
		
		JPanel roomComboPanel = new JPanel();
		JLabel roomComboLabel = new JLabel();
		roomComboPanel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));

		String[] roomArr = {"????","Kitchen", "TV Room", "Hall", "Dining Room", "Reading Room", "Gym", "Common Room", "Studio", "Bar"};
		JComboBox roomCombo = new JComboBox(roomArr);
		
		roomComboPanel.add(roomCombo);
		
		JPanel weaponComboPanel = new JPanel();
		JLabel weaponComboLabel = new JLabel();
		weaponComboPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));

		String[] weaponArr = {"????","Bazooka", "Butter Knife", "Fly Swatter", "Broken bottle", "Blow Torch", "Rusty Spoon"};
		JComboBox weaponCombo = new JComboBox(weaponArr);
		
		weaponComboPanel.add(weaponCombo);
		
		add(peoplePanel);
		add(peopleComboPanel);
		add(roomPanel);
		add(roomComboPanel);
		add(weaponPanel);
		add(weaponComboPanel);
			

	}

	public MyDialog(Board b, Character c) {
		//Initialize size and layout
				setTitle("Make a suggestion");
				setLayout(new FlowLayout());
				setSize(500, 200);
				JPanel peoplePanel = new JPanel();
				
				String[] peopleArr = {"????","Magic Mouse", "The President", "Rabid Dog", "Carol", "Sgt Pepper", "Small Child"};
				peopleCombo = new JComboBox(peopleArr);
				peopleCombo.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
				
				String[] weaponArr = {"????","Bazooka", "Butter Knife", "Fly Swatter", "Broken bottle", "Blow Torch", "Rusty Spoon"};
				weaponCombo = new JComboBox(weaponArr);
				weaponCombo.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
				
				JButton jb = new JButton("Suggest");
				
				peoplePanel.add(peopleCombo);
				peoplePanel.add(weaponCombo);
				peoplePanel.add(jb);
				
				
				jb.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String room = null;
						
						switch(c) {
						case('C'):
							room = "Common Room";
							break;
						case('K'):
							room = "Kitchen";
							break;
							
						case('B'):
							room = "Bar";
							break;
						case('G'):
							room = "Gym";
							break;
						case('R'):
							room = "Reading Room";
							break;
						case('U'):
							room = "Studio";
							break;
						case('H'):
							room = "Hall";
							break;
						case('T'):
							room = "TV Room";
							break;
						case('D'):
							room = "Dining Room";
							break;
						}
						b.suggestName = String.valueOf(peopleCombo.getSelectedItem());
						b.suggestRoom = room;
						b.suggestWeapon = String.valueOf(weaponCombo.getSelectedItem());
						Solution suggestion = new Solution();
						suggestion.person = b.suggestName;
						suggestion.room = room;
						suggestion.weapon = b.suggestWeapon;
						ClueGame.guess.setText(b.suggestName + " " + b.suggestRoom + " " + b.suggestWeapon);
						Card disproven = b.querySuggestions(b.getPlayerList(), suggestion);
						if (disproven == null) {
							b.disprove = "No new clue";
						} else {
						b.disprove = disproven.getCardName();
						b.wasGuessed = false;
						}
						ClueGame.response.setText(b.disprove);
						dispose();
						
					}

					
					});
				
				
				
				add(peoplePanel);
				
	}

}