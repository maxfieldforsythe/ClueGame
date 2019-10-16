package clueGame;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
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
		this.loadBoardConfig();
		this.loadRoomConfig();
	}
	
	public void loadRoomConfig() {
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
		    
		    this.legend.put(ch, strArr[1]);
		}
		scanner.close();
	}
	
	public void loadBoardConfig() {
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
					if (arrays.get(i)[j].charAt(1) != 'N') {
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
				}
				//Sets board cell equal to the data stored in a temp cell.
				//Temp cell is where the csv data is stored 
				board[i][j] = temp;

			}
		}
		
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		
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
