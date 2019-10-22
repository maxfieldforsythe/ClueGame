/*
 * Caroline Arndt
 * Maxfield Forsythe
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
	private static Board theInstance = new Board();
	
	public Board() {
		
	}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		//Initialize legend as hash map and load csv and legend files
		legend = new HashMap<Character, String>();
		try{this.loadBoardConfig();
			this.loadRoomConfig();
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
		    String[] strArr = line.split(", ", -2);
		    Character ch = strArr[0].charAt(0);
		    //throws exception if it is the wrong type
		    if (!strArr[2].contentEquals("Card") && !strArr[2].contentEquals("Other") )
		    	throw new BadConfigFormatException();
		    this.legend.put(ch, strArr[1]);
		}
		scanner.close();
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
		scanner.close();
		//Sets length of columns and length of rows
		this.numColumns = arrays.get(0).length;
		for (String[] s: arrays) {
			System.out.println();
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
				BoardCell temp = new BoardCell();
				temp.setRow(i);
				temp.setColumn(j);
				char c = arrays.get(i)[j].charAt(0);
				temp.setInitial(c);
				if (arrays.get(i)[j].length() > 1) {
					if (arrays.get(i)[j].charAt(1) != 'N' ) {
						temp.setDoor (true);
					} 
					if (arrays.get(i)[j].charAt(1) == 'R') {
						temp.setDir(DoorDirection.RIGHT);
					}
					if (arrays.get(i)[j].charAt(1) == 'U') {
						temp.setDir(DoorDirection.UP);
					}
					if (arrays.get(i)[j].charAt(1) == 'D') {
						temp.setDir(DoorDirection.DOWN);
					}
					if (arrays.get(i)[j].charAt(1) == 'L') {
						temp.setDir(DoorDirection.LEFT);
					}
				} else {
					if (arrays.get(i)[j].charAt(0) != 'W' ) {
						temp.setRoom(true);
					}
				}
				//Sets board cell equal to the data stored in a temp cell.
				//Temp cell is where the csv data is stored 
				board[i][j] = temp;

			}
		}
		
	}
	
	public void calcAdjacencies() {
		//Initialize boardcell objects to store temporary data
		BoardCell tempCell = new BoardCell();
		BoardCell addCell = new BoardCell();
		Set tempSet = new HashSet<BoardCell>();
		//Calculates adjacent spaces
		for (int i = 0; i <this.numRows; i++) {
			for (int j = 0; j < this.numColumns; j++) {
				tempCell = new BoardCell();
				tempSet = new HashSet<BoardCell>();
				tempCell.setRow(i);
				tempCell.setColumn(j);
				//If cell is a room add empty set to adjMatrix and continue
				if (getCellAt(tempCell.getRow(),tempCell.getColumn()).isRoom()) {
					this.adjMatrix.put(getCellAt(tempCell.getRow(),tempCell.getColumn()),tempSet);

					continue;
				}
				//Checks the if the cell is a doorway and then adds their adjacent space based on direction
				if (getCellAt(tempCell.getRow(),tempCell.getColumn()).isDoorway()) {
					if (getCellAt(tempCell.getRow(),tempCell.getColumn()).getDir() == DoorDirection.RIGHT) {
						addCell.setRow(i);
						addCell.setColumn(j+1);
						tempSet.add(getCellAt(addCell.getRow(),addCell.getColumn()));
						this.adjMatrix.put(getCellAt(tempCell.getRow(),tempCell.getColumn()),tempSet);

						continue;
					}
					if (getCellAt(tempCell.getRow(),tempCell.getColumn()).getDir() == DoorDirection.LEFT) {
						addCell.setRow(i);
						addCell.setColumn(j-1);
						tempSet.add(getCellAt(addCell.getRow(),addCell.getColumn()));
						this.adjMatrix.put(getCellAt(tempCell.getRow(),tempCell.getColumn()),tempSet);

						continue;
					}
					if (getCellAt(tempCell.getRow(),tempCell.getColumn()).getDir() == DoorDirection.UP) {
						addCell.setRow(i-1);
						addCell.setColumn(j);
						tempSet.add(getCellAt(addCell.getRow(),addCell.getColumn()));
						this.adjMatrix.put(getCellAt(tempCell.getRow(),tempCell.getColumn()),tempSet);

						continue;
					}
					if (getCellAt(tempCell.getRow(),tempCell.getColumn()).getDir() == DoorDirection.DOWN) {
						addCell.setRow(i +1);
						addCell.setColumn(j);
						tempSet.add(getCellAt(addCell.getRow(),addCell.getColumn()));
						this.adjMatrix.put(getCellAt(tempCell.getRow(),tempCell.getColumn()),tempSet);

						continue;
					}
				}
				//For each space, while taking into account edge pieces, adds the surrounding spaces to the adjacenecy list
				//If not a door or a room, adds the space to the list
				//If it is a door which matching direction, it will add the door space
				if (i-1 >= 0) {
					addCell = new BoardCell();
					if (!this.getCellAt(i-1,j).isDoorway() && !this.getCellAt(i-1,j).isRoom()) {
						
					
					addCell.setRow(i-1);
					addCell.setColumn(j);
					tempSet.add(getCellAt(addCell.getRow(),addCell.getColumn()));
					} else if (this.getCellAt(i-1,j).getDir() == DoorDirection.DOWN) {
						addCell.setRow(i-1);
						addCell.setColumn(j);
						tempSet.add(getCellAt(addCell.getRow(),addCell.getColumn()));
					}
				}
				if (j-1 >= 0) {
					addCell = new BoardCell();
					if (!this.getCellAt(i,j-1).isDoorway()  && !this.getCellAt(i,j-1).isRoom()) {
					addCell.setRow(i);
					addCell.setColumn(j-1);
					tempSet.add(getCellAt(addCell.getRow(),addCell.getColumn()));
					} else if (this.getCellAt(i,j-1).getDir() == DoorDirection.RIGHT) {
						addCell.setRow(i);
						addCell.setColumn(j-1);
						tempSet.add(getCellAt(addCell.getRow(),addCell.getColumn()));
					}
				}
				if (i+1 < this.numRows) {
					addCell = new BoardCell();
					if (!this.getCellAt(i+1,j).isDoorway()  && !this.getCellAt(i+1,j).isRoom()) {
					addCell.setRow(i +1);
					addCell.setColumn(j);
					tempSet.add(getCellAt(addCell.getRow(),addCell.getColumn()));
					} else if (this.getCellAt(i+1,j).getDir() == DoorDirection.UP) {
						addCell.setRow(i+1);
						addCell.setColumn(j);
						tempSet.add(getCellAt(addCell.getRow(),addCell.getColumn()));
					}
				}
				if (j+1 < this.numColumns) {
					addCell = new BoardCell();
					if (!this.getCellAt(i,j+1).isDoorway()  && !this.getCellAt(i,j+1).isRoom()) {
					addCell.setRow(i);
					addCell.setColumn(j+1);
					tempSet.add(getCellAt(addCell.getRow(),addCell.getColumn()));
					} else if (this.getCellAt(i,j+1).getDir() == DoorDirection.LEFT) {
						addCell.setRow(i);
						addCell.setColumn(j+1);
						tempSet.add(getCellAt(addCell.getRow(),addCell.getColumn()));
					}
				}
				this.adjMatrix.put(getCellAt(tempCell.getRow(),tempCell.getColumn()),tempSet);
			}
			
		}
	}
	
	
	//Returns the adjacency matrix value associated with the given coordinates
	public Set<BoardCell> getAdjList(int i, int j) {
		
		for (BoardCell key: adjMatrix.keySet()) {
			if (key.getRow() == i && key.getColumn() == j) {
				return adjMatrix.get(key);			}
			
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
		Set temp = new HashSet<BoardCell>();
		temp = targets;
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



}
