/*
 * Caroline Arndt & Maxfield Forsythe
 */
package clueGame;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.awt.Color;
import java.lang.reflect.Field;

import clueGame.BoardCell;

public class Board {
	
	public static final int MAX_BOARD_SIZE = 50;
	private int numRows;
	private int numColumns;
	private BoardCell [][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
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
	
	public Board() {
		
	}
	
	public static Board getInstance() {
		return theInstance;
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
			Card currentCard = new Card(legend.get(initial),CardType.ROOM);
			cardDeck.add(currentCard);
		}
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

	//Gets target set
	public Set<BoardCell> getTargets() {
		Set<BoardCell> temp = new HashSet<>();
		temp = targets;
		//TODO: This needs to be fixed somewhere else 
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
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

	public BoardCell getCellAt(int i, int j) {
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
	
	public Set<Card> getDeckOfCards(){
		return cardDeck;
	}

}
