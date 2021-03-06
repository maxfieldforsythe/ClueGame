/*
 * Caroline Arndt & Maxfield Forsythe
 */
package clueGame;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;

import clueGame.BoardCell;

public class Board extends JPanel {
	
	public static final int MAX_BOARD_SIZE = 50;
	private int numRows;
	private int numColumns;
	private static BoardCell [][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private static Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;
	private static Board theInstance = new Board();
	private ArrayList<Player> playerList;
	private ArrayList<String> weaponList;
	private ArrayList<Character> legendKeys;
	private Set<Card> cardDeck;
	private Solution solution = new Solution();
	private int roll = 0;
	Rectangle r = null;
	Set<Rectangle> targetRect = new HashSet<>();
	Player tempPlayer = new HumanPlayer();
	public boolean ready = true;
	public String suggestName = "";
	public String suggestWeapon = "";
	public String suggestRoom = "";
	public String disprove = "";
	public boolean wasGuessed = false;
	
	
	
	public Board() {
		addMouseListener(new ClickListen());
	}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	 public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 for (int i = 0; i < board.length; i++) {
			 for (int j = 0; j < board[i].length; j++) {
				 board[i][j].drawBox(g);
			 }
		 }
		 
		 for (Player p: playerList) {
			 p.drawPlayer(g);
		 }
		 this.drawRoomName(g);
	 }
	 
	
	public void initialize() {
		//Initialize legend as hash map and load csv and legend files
		legend = new HashMap<Character, String>();
		playerList = new ArrayList<Player>();
		try{this.loadBoardConfig();
			this.loadRoomConfig();
			this.loadPlayers();
			this.loadCards();
		}catch(BadConfigFormatException e){
			System.out.println(e.getMessage());
		}
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		this.calcAdjacencies();
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		this.makeSolution();
		this.shuffleAndDealCards();
		
		
	}
	


	public void loadRoomConfig() throws BadConfigFormatException{
		//File reader to read layout csv
		legendKeys = new ArrayList<>();
		FileReader reader = null;
		try {
			reader = new FileReader(roomConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		Scanner scanner = new Scanner(reader);	
		//Loop to map the character aka initial of each legend item to its name
		while(scanner.hasNextLine()){
		    String line = scanner.nextLine();
		    String[] roomConfigArray = line.split(", ", -2);
		    Character initialOfRoom = roomConfigArray[0].charAt(0);
		    String roomName = roomConfigArray[0];
		    legendKeys.add(initialOfRoom);
		    
		    //throws exception if it is the wrong type
		    if (!roomConfigArray[2].contentEquals("Card") && !roomConfigArray[2].contentEquals("Other") )
		    	throw new BadConfigFormatException();
		    this.legend.put(initialOfRoom, roomConfigArray[1]);
		}
		//scanner.close();
	}
	
	public void loadBoardConfig() throws BadConfigFormatException{
		//Creates arrayList of arrays to store the string split arrays when reading in lines from csv
		ArrayList<String[]> arrays = new ArrayList<String[]>();
		FileReader reader = null;
		try {
			reader = new FileReader(boardConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		//splits by , and then stores the resulting array in an array list
		Scanner scanner = new Scanner(reader);
		while(scanner.hasNextLine()){
		    String line = scanner.nextLine();
		    String[] strArr = line.split(",", -2);
		    arrays.add(strArr);
		    		}
		//Sets length of columns and length of rows
		this.numColumns = arrays.get(0).length;
		for (String[] s: arrays) {
			if (s.length != numColumns)
				throw new BadConfigFormatException();
		}
		this.numRows = arrays.size();
		//initialize board with row and column numbers retrieved from csv
		board = new BoardCell[numRows][numColumns];
		//Loop through every cell of the board while storing dating from respective csv entries
		//This includes the initial, isDoor, and direction of door
		for (int i = 0; i < this.numRows; i++) {
			for(int j = 0; j < this.numColumns; j++) {
				BoardCell currentCell = new BoardCell();
				currentCell.setRow(i);
				currentCell.setColumn(j);
				char c = arrays.get(i)[j].charAt(0);
				currentCell.setInitial(c);
				if (arrays.get(i)[j].length() > 1) {
					if (arrays.get(i)[j].charAt(1) != 'N' ) {
						
						currentCell.setDoor (true);
					} 
					if (arrays.get(i)[j].charAt(1) == 'R') {
						currentCell.setDir(DoorDirection.RIGHT);
					}
					if (arrays.get(i)[j].charAt(1) == 'U') {
						currentCell.setDir(DoorDirection.UP);
					}
					if (arrays.get(i)[j].charAt(1) == 'D') {
						currentCell.setDir(DoorDirection.DOWN);
					}
					if (arrays.get(i)[j].charAt(1) == 'L') {
						currentCell.setDir(DoorDirection.LEFT);
					}
					
				} else {
					if (arrays.get(i)[j].charAt(0) != 'W' ) {
						currentCell.setRoom(true);
					}
				}
				//Sets board cell equal to the data stored in a temp cell.
				//Temp cell is where the csv data is stored 
				board[i][j] = currentCell;

			}
		}
		
	}
	//Function to load in data from player config file
	public void loadPlayers() throws BadConfigFormatException {
		//Creates arrayList of arrays to store the string split arrays when reading in lines from csv
		ArrayList<String[]> arrays = new ArrayList<String[]>();
		FileReader reader = null;
		try {
			reader = new FileReader(playerConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		//splits by , and then stores the resulting array in an array list
		Scanner scanner = new Scanner(reader);
		while(scanner.hasNextLine()){
		    String line = scanner.nextLine();
		    String[] strArr = line.split(", ", -2);
		    arrays.add(strArr);
		    		}
		
		Player tempPlayer;
		for (String[] str: arrays) {
			
			if  (str.length == 5) {
			tempPlayer = new HumanPlayer();
			} else {
				tempPlayer = new ComputerPlayer();
			}
			
			tempPlayer.setName(str[0]);
			tempPlayer.setColor(convertColor(str[1]));
			tempPlayer.setRow(Integer.parseInt(str[2]));			
			tempPlayer.setColumn(Integer.parseInt(str[3]));
			this.playerList.add(tempPlayer);
		}
	}
	
	public void loadWeapons() {
		FileReader reader = null;
		weaponList = new ArrayList<>();
		try {
			reader = new FileReader(weaponConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		//splits by , and then stores the resulting array in an array list
		Scanner scanner = new Scanner(reader);
		while(scanner.hasNextLine()){
		    String weapon = scanner.nextLine();
		    weaponList.add(weapon);
		    }
	}
	
	public void loadCards() {
		cardDeck = new HashSet<>();
		loadWeapons();
		//add weapons
		for(String weapon : weaponList) {
			Card currentCard = new Card(weapon,CardType.WEAPON);
			cardDeck.add(currentCard);
		}
		//add people
		for (Player person: playerList) {
			Card currentCard = new Card(person.getName(),CardType.PERSON);
			cardDeck.add(currentCard);
		}
		//add rooms
		for (Character initial: legendKeys) {
			if (initial != 'X' && initial != 'W') {
			Card currentCard = new Card(legend.get(initial),CardType.ROOM);
			cardDeck.add(currentCard);
			}
		}
	}
	
	public void shuffleAndDealCards() {
		int playerIterator = 0;
		int numPlayers = playerList.size();
		ArrayList<Card> cardList = new ArrayList<>();
		
		for (Card card: cardDeck) {
			cardList.add(card);
		}
		Collections.shuffle(cardList);
		
		for (Card card: cardList) {
			playerList.get(playerIterator % numPlayers).addCard(card);
			playerIterator++;
		}
	}
	
	public Card getCard(String name, CardType type) {
		
		for (Card card : cardDeck) {
			if (card.getName().contentEquals(name) && card.getType().equals(type)) {
					return card;
					}
			
		}
		return null;
	}
	
	public void calcAdjacencies() {
		//Initialize boardcell objects to store temporary data
		BoardCell currentCell;
		BoardCell adjacentCell;
		Set <BoardCell> tempSet = new HashSet<>();
		//Calculates adjacent spaces
		for (int i = 0; i <this.numRows; i++) {
			for (int j = 0; j < this.numColumns; j++) {
				currentCell = getCellAt(i,j);
				tempSet = new HashSet<BoardCell>();
				//If cell is a room add empty set to adjMatrix and continue. If it is closet. Add nothing
				if (currentCell.isRoom()) {
					
					this.adjMatrix.put(getCellAt(i,j),tempSet);
					continue;
				}
				//Checks the if the cell is a doorway and then adds their adjacent space based on direction
				if (currentCell.isDoorway()) {
					if (currentCell.getDir() == DoorDirection.RIGHT) {
						tempSet.add(getCellAt(i,j+1));
						this.adjMatrix.put(currentCell,tempSet);
						continue;
					}
					if (currentCell.getDir() == DoorDirection.LEFT) {
						tempSet.add(getCellAt(i,j-1));
						this.adjMatrix.put(currentCell,tempSet);
						continue;
					}
					if (currentCell.getDir() == DoorDirection.UP) {
						tempSet.add(getCellAt(i-1,j));
						this.adjMatrix.put(currentCell,tempSet);
						continue;
					}
					if (currentCell.getDir() == DoorDirection.DOWN) {
						tempSet.add(getCellAt(i+1,j));
						this.adjMatrix.put(currentCell,tempSet);
						continue;
					}
				}
				//For each space, while taking into account edge pieces, adds the surrounding spaces to the adjacenecy list
				//If not a door or a room, adds the space to the list
				//If it is a door which matching direction, it will add the door space
				
				//LEFT CELL
				if (i-1 >= 0) {
					adjacentCell = getCellAt(i-1,j);
					if (!adjacentCell.isDoorway() && !adjacentCell.isRoom()) {
					tempSet.add(adjacentCell);
					} 
					else if (adjacentCell.getDir() == DoorDirection.DOWN) {
						tempSet.add(adjacentCell);
					}		
				}
				//TOP CELL
				if (j-1 >= 0) {
					adjacentCell = getCellAt(i,j-1);
					if (!adjacentCell.isDoorway() && !adjacentCell.isRoom()) {
					tempSet.add(adjacentCell);
					} 
					else if (adjacentCell.getDir() == DoorDirection.RIGHT) {
						tempSet.add(adjacentCell);
					}
				}
				//RIGHT CELL
				if (i+1 < this.numRows) {
					adjacentCell = getCellAt(i+1,j);
					if (!adjacentCell.isDoorway() && !adjacentCell.isRoom()) {
					tempSet.add(adjacentCell);
					} 
					else if (adjacentCell.getDir() == DoorDirection.UP) {
						tempSet.add(adjacentCell);
					}
				}
				//BOTTOM CELL
				if (j+1 < this.numColumns) {
					adjacentCell = getCellAt(i,j+1);
					if (!adjacentCell.isDoorway() && !adjacentCell.isRoom()) {
					tempSet.add(adjacentCell);
					} 
					else if (adjacentCell.getDir() == DoorDirection.LEFT) {
						tempSet.add(adjacentCell);
					}
				}
				this.adjMatrix.put(currentCell,tempSet);
			}
		}
	}
	
	//Returns the adjacency matrix value associated with the given coordinates
	public Set<BoardCell> getAdjList(int i, int j) {
		
		for (BoardCell key: adjMatrix.keySet()) {
			if (key.getRow() == i && key.getColumn() == j) {
				return adjMatrix.get(key);			
				}		
		}
		return null;
	}
	//Recursive function to calculate the targets at a given path length
	public void calcTargets(int row, int column, int pathLength) {
	
		
		for (BoardCell cell : adjMatrix.get(this.getCellAt(row, column))) {
			if (visited.contains(cell)) {
				continue;
			}
			if (cell.isDoorway()) {
				targets.add(cell);
			}
			visited.add(getCellAt(row, column));

			if (pathLength > 1 ) {
				calcTargets(cell.getRow(),cell.getColumn(),pathLength - 1);
			}
			else {
				targets.add(cell);
			}
			visited.remove(cell);
		}	
		
		
	}
	
	public void makeSolution() {
		
		Card card1 = null;
		Card card2 = null;
		Card card3 = null;
		
		ArrayList<Card> cardList = new ArrayList<>();
		for (Card c: cardDeck) {
			cardList.add(c);
		}
		Collections.shuffle(cardList);
		
		for (Card card: cardList) {
			if (card.getType() == CardType.PERSON ) {
				if (getSolution().person == null) {
					getSolution().person = card.getName();
					card1 = card;
				}
			} else if (card.getType() == CardType.ROOM) {
				if (getSolution().room == null) {
					getSolution().room = card.getName();
					card2 = card;
				}
			} else if (card.getType() == CardType.WEAPON) {
				if (getSolution().weapon == null) {
					getSolution().weapon = card.getName();
					card3 = card;
				}
			}
		}
		cardDeck.remove(card1);
		cardDeck.remove(card2);
		cardDeck.remove(card3);
		
		
	}
	
	public boolean accusationCompare(String name, String room, String weapon) {
		if (name == this.solution.person && room == this.solution.room && weapon == this.solution.weapon) {
			return true;
			
		} else {
			return false;
		}
	}
	
	public Card querySuggestions(ArrayList<Player> players, Solution suggestion) {
		Card disproved = new Card();

		
		for (Player p: getPlayerList()) {
		
			disproved = p.disproveSuggestion(suggestion);
			if (disproved != null) {
				return disproved;
			} 
			
		}
		return null;
	}
	

	//Gets target set
	public static Set<BoardCell> getTargets() {
		Set<BoardCell> temp = new HashSet<>();
		temp = targets;
		
		return temp;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public int getNumRows() {
		return this.numRows;
	}

	public int getNumColumns() {
		return this.numColumns;
	}

	public static BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}

	public void setConfigFiles(String csv, String txt) {
		//Stores file path to a variable in the board
		this.boardConfigFile = csv;
		this.roomConfigFile = txt;

	}
	
	public void setCardFiles(String players, String weapons) {
		//Stores file path to a variable in the board
		this.playerConfigFile = players;
		this.weaponConfigFile = weapons;

	}
	
	public Color convertColor(String strColor) {
		Color color; 
		try {  
			Field field =Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null); 
			} catch (Exception e) { 
				color = null; 
				}
		return color;
				}
	
	public void drawRoomName(Graphics g) {
		 
		 
		 g.drawString("Kitchen",70 ,280);
		 g.drawString("Dining Room", 18*28, 18*28);
		 g.drawString("TV Room", 11*28, 3*28);
		 g.drawString("Studio", 13*28 + 10, 16*28);
		 g.drawString("Bar", 19*28, 280);
		 g.drawString("Hall", 20*28 - 10, 2*28);
		 g.drawString("Gym", 8*28+15,18*28 );
		 g.drawString("Common Room", 2*28,2*28);
		 g.drawString("Reading Room", 28, 18*28);
		 
	}
	
	public class ClickListen implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			Point tempPoint = new Point();
			
			tempPoint = e.getPoint();
			
			for (Rectangle rec: targetRect) {
				if (rec.contains(tempPoint)) {
					r = rec;
					tempPlayer.makeMove(ClueGame.board1);
					ClueGame.canAccuse = false;
					targetRect = new HashSet<Rectangle>();
					
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	


	//*****THIS SECTION HAS FUNCTIONS THAT ARE FOR TESTING PURPOSES ONLY*****
	
	//returns num of human players in set
	public int numHuman() {
		int i = 0;
		for (Player pl: playerList) {
			if (pl instanceof HumanPlayer) {
				i++;
				}
		}
		return i;
	}
	
	//returns num of computer players in set
	public int numComp() {
		int i = 0;
		for (Player pl: playerList) {
			if (pl instanceof ComputerPlayer) {
				i++;
				}
		}
		return i;
	}
	
	public Player getPlayer(int i) {
		return playerList.get(i);
	}
	public ArrayList<Player> getPlayerList(){
		return playerList;
	}
	
	public Set<Card> getDeckOfCards(){
		return cardDeck;
	}


	public void clearTargets() {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	public int getRoll() {
		return roll;
	}

	public void setRoll(int roll) {
		this.roll = roll;
	}
	public void setPlayers(ArrayList<Player> p) {
		this.playerList = new ArrayList<Player>();
		this.playerList = p;
	}

}