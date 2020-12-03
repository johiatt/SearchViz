package playgroundTest;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Grid {
	/** current contents of the board */
	private char[][] board;
	/** location of row,col for '1' */
	public Point startingPoint;
	/** location of row,col for '2' */
	public Point endingPoint;

	//constants you may find useful
	private final int ROWS; //initialized in constructor
	private final int COLS; //initialized in constructor
	private final char OPEN = 'O'; //capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";

	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 * 		file containing a grid of characters
	 * @throws FileNotFoundException if Scanner cannot read the file
	 * @throws InvalidFileFormatException for any other format or content issue that prevents reading a valid input file
	 */
	public Grid(char[][] inputBoard) {
		board = inputBoard;
//		if(board==null)
//			throw new FileNotFoundException();
		ROWS = board.length;
		COLS = board[0].length;
		
	}
	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public Grid(Grid original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/** utility method for copy constructor
	 * @return copy of board array */
	public char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			//throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}
	
	/** @return starting Point(row,col) */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point(row,col) */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
	//reads in the board from a filename
	public char[][] readBoard(String fileName) throws InvalidFileFormatException 
	{
		int rowSize = 0;
		int colSize = 0;
		try {
			File file = new File(System.getProperty("user.dir")+"/"+fileName); 
			Scanner sc = new Scanner(file);
			Scanner inputRow = new Scanner(sc.nextLine());
			try {
				rowSize = Integer.parseInt(inputRow.next());
				colSize = Integer.parseInt(inputRow.next());
				} catch (NumberFormatException e) {
					throw new InvalidFileFormatException("Wrong row or col input.");
			}
			if(rowSize!=(int)rowSize || colSize!=(int)colSize)
				throw new InvalidFileFormatException("Wrong row or col input.");
			int scanRows = 0;
			int scanCols = 0;
			String rowString;
			Scanner rowScanner;
			if(inputRow.hasNext())
				throw new InvalidFileFormatException("Wrong amount of numbers in row and col line.");
			else
				inputRow.close();
			int startingPoints = 0;
			int endingPoints = 0;
			char[][] array = new char[rowSize][colSize];
			while(sc.hasNextLine()) {
				if(scanRows==rowSize && sc.hasNextLine())
					throw new InvalidFileFormatException("Too many starting rows");
				scanCols = 0;
				rowString = sc.nextLine();
				if(!rowString.isEmpty()) {
					
				rowScanner = new Scanner(rowString);
				scanRows++;
				while(rowScanner.hasNext()) {
					scanCols++;
					array[scanRows-1][scanCols-1] = rowScanner.next().charAt(0);
					if(!ALLOWED_CHARS.contains(""+array[scanRows-1][scanCols-1]+""))
						throw new InvalidFileFormatException("The char " + array[scanRows-1][scanCols-1]+" is not allowed.");
					if(array[scanRows-1][scanCols-1]==START) {
						startingPoints++;
						if(startingPoints>1)
							throw new InvalidFileFormatException("Too many starting points");
						startingPoint = new Point(scanRows-1,scanCols-1);
					}
					if(array[scanRows-1][scanCols-1]==END) {
						endingPoints++;
						if(endingPoints>1)
							throw new InvalidFileFormatException("Too many ending points");
						endingPoint = new Point(scanRows-1,scanCols-1);
					}
					if(scanCols == colSize && rowScanner.hasNext()) {
						throw new InvalidFileFormatException("Too many columns.");
					}
					else if(scanCols == colSize-1 && !rowScanner.hasNext()) {
						throw new InvalidFileFormatException("Not enough columns.");
					}
				}

				}

			}
			if(scanRows != rowSize) {
				throw new InvalidFileFormatException("Wrong number of rows");
			}
			//sc.close();
			if(sc.hasNext())
				throw new InvalidFileFormatException("Wrong row or column amount.");
			else if(endingPoints==0)
				throw new InvalidFileFormatException("There is no ending point.");
			else if(startingPoints==0)
				throw new InvalidFileFormatException("There is no starting point.");
			else
				sc.close();
			return array;
			}  catch (IOException e) {
				//e.printStackTrace();
				return null;
			}
	}
}
