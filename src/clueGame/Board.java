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
				}
				//Sets board cell equal to the data stored in a temp cell.
				//Temp cell is where the csv data is stored 
				board[i][j] = temp;

			}
		}
		
	}
	
	public void calcAdjacencies() {
		BoardCell tempCell = new BoardCell();
		BoardCell addCell = new BoardCell();
		Set tempSet = new HashSet<BoardCell>();
		for (int i = 0; i <this.numRows; i++) {
			for (int j = 0; j < this.numColumns; j++) {
				tempCell = new BoardCell();
				tempSet = new HashSet<BoardCell>();
				tempCell.setRow(i);
				tempCell.setColumn(j);
				if (i-1 >= 0) {
					addCell = new BoardCell();
					addCell.setRow(i-1);
					addCell.setColumn(j);
					tempSet.add(getCellAt(addCell.getRow(),addCell.getColumn()));
				}
				if (j-1 >= 0) {
					addCell = new BoardCell();

					addCell.setRow(i);
					addCell.setColumn(j-1);
					tempSet.add(getCellAt(addCell.getRow(),addCell.getColumn()));
				}
				if (i+1 <= this.numRows) {
					addCell = new BoardCell();

					addCell.setRow(i +1);
					addCell.setColumn(j);
					tempSet.add(getCellAt(addCell.getRow(),addCell.getColumn()));
				}
				if (j+1 <= this.numColumns) {
					addCell = new BoardCell();

					addCell.setRow(i);
					addCell.setColumn(j+1);
					tempSet.add(getCellAt(addCell.getRow(),addCell.getColumn()));
				}
				this.adjMatrix.put(getCellAt(tempCell.getRow(),tempCell.getColumn()),tempSet);
			}
			
		}
	}
	
	
	
	public Set<BoardCell> getAdjList(int i, int j) {
		
		return adjMatrix.get(board[i][j]);
	}

	public void calcTargets(int row, int column, int pathLength) {
		
		BoardCell startCell = new BoardCell();
		startCell = this.getCellAt(row, column);
		
		for (BoardCell cell : adjMatrix.get(startCell)) {
			if (visited.contains(cell)) {
				continue;
			}
			visited.add(getCellAt(startCell.getRow(), startCell.getColumn()));

			if (pathLength > 1 ) {
				calcTargets(cell.getRow(),cell.getColumn(),pathLength - 1);
			}
			else {
				targets.add(cell);
			}
			visited.remove(cell);
		}
		
	}


	public Set<BoardCell> getTargets() {
		return targets;
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
