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
		// TODO Auto-generated constructor stub
	}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		legend = new HashMap<Character, String>();
		this.loadBoardConfig();
		this.loadRoomConfig();
	}
	
	public void loadRoomConfig() {
		FileReader reader = null;
		try {
			reader = new FileReader(roomConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		Scanner scanner = new Scanner(reader);		
		while(scanner.hasNextLine()){
		    String line = scanner.nextLine();
		    String[] strArr = line.split(", ", -2);
		    Character ch = strArr[0].charAt(0);
		    
		    this.legend.put(ch, strArr[1]);
		}
		scanner.close();
	}
	
	public void loadBoardConfig() {
		ArrayList<String[]> arrays = new ArrayList<String[]>();
		FileReader reader = null;
		try {
			reader = new FileReader(boardConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		Scanner scanner = new Scanner(reader);
		while(scanner.hasNextLine()){
		    String line = scanner.nextLine();
		    String[] strArr = line.split(",", -2);
		    arrays.add(strArr);
		    		}
		scanner.close();
		
		this.numColumns = arrays.get(0).length;
		this.numRows = arrays.size();
		
		board = new BoardCell[numRows][numColumns];
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
		// TODO Auto-generated method stub
		return board[i][j];
	}

	public void setConfigFiles(String csv, String txt) {
		this.boardConfigFile = csv;
		this.roomConfigFile = txt;

	}

}
