package playgroundTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.ListIterator;


public class GridController {
	//set this statically at the beginning, this is BAD
	public static int squareSideLength = 0;
	public ArrayList<GridState> shortestPath;
	public char[][] initalCharArray;
	public Square[][] initalSquares;
	public Grid initGrid;
	public int searchType = 2;
	public double blockerChance = .2;
	
	public GridController(int sides) {
		initalCharArray = generateRandomMap(sides);
		initalSquares = squareFromChar(initalCharArray);
		initGrid = new Grid(initalCharArray);
		initGrid.startingPoint = new Point(1,1);
		initGrid.endingPoint = new Point(sides - 1, sides - 1);
		shortestPath = createShortestPath();
	}
	public ArrayList<GridState> createShortestPath() {
		ArrayList<GridState> grids = findShortestPath(initGrid, searchType);
		return grids;
	}
	
	//This will only be used as the initial state. 
	public static char[][] testArray = {
			{'O','O','X','X','O','O','X','X','O','O'},
			{'O','1','O','X','X','O','X','O','O','X'},
			{'X','O','O','O','O','O','X','O','X','O'},
			{'X','X','O','X','X','O','O','O','X','O'},
			{'O','X','O','X','O','O','X','O','O','O'},
			{'O','O','O','O','O','X','O','O','X','X'},
			{'X','X','X','O','X','O','O','O','O','O'},
			{'X','O','O','O','O','O','O','2','X','O'},
			{'O','O','X','X','O','X','O','X','O','O'},
			{'O','X','O','O','O','X','O','O','O','O'},
	};
	
	public static Square[][] createSquareArray(int squares, int width, int squareSide) {
		Square[][] s = new Square[squares][squares];
		Color c;
		for(int i = 0; i < squares; i++) {
			for(int j = 0; j < squares; j++) {
					//c = new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), (float)Math.random());
					//I think there is rounding here that adds up
//					if(Math.random() <= .2) {
//						c = Color.black;
//					} else {
//						c = Color.white;
//					}
					char typeCode = testArray[i][j];
					c = getColor(typeCode);
					s[i][j] = (new Square((int)j*(squareSide), (int)i*(squareSide), squareSide, squareSide, c, typeCode));
				}
			}
		return s;		
	}
	
	//update colors
	public static Square[][] squareFromChar(char[][] newGrid) {
		int squares = newGrid.length;
		int squareSide = (int)(GUI.width/squares);
		Square[][] s = new Square[squares][squares];
		Color c;
		for(int i = 0; i < squares; i++) {
			for(int j = 0; j < squares; j++) {
					//c = new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), (float)Math.random());
					//I think there is rounding here that adds up
//					if(Math.random() <= .2) {
//						c = Color.black;
//					} else {
//						c = Color.white;
//					}
					c = getColor(newGrid[i][j]);
					s[i][j] = (new Square((int)j*(squareSide), (int)i*(squareSide), squareSide, squareSide, c, newGrid[i][j]));
				}
			}
		return s;	
		
	}
	
	//Helper method means 
	public static Color getColor(char c) {
		switch (c) {
		case 'X': 
			return Color.black;
		case '1':
			return Color.blue;
		case '2':
			return Color.red;
		case 'T':
			return Color.yellow;
		default:
			return Color.white;
		}
	}
	
	private ArrayList<GridState> getPositions(GridState t) {
		ArrayList<GridState> arrayList = new ArrayList<GridState>();
		Point lastT = t.getPath().get(t.getPath().size()-1);
		Grid b = t.getBoard();
		int x = (int)lastT.getX();
		int y = (int)lastT.getY();
		if(b.isOpen(x+1,y)) {
			arrayList.add(new GridState(t,x+1,y));	
		}
		if(b.isOpen(x-1,(int)lastT.getY())) {
			arrayList.add(new GridState(t,x-1,y));	
		}
		if(b.isOpen(x,(int)lastT.getY()+1)) {
			arrayList.add(new GridState(t,x,y+1));	
		}
		if(b.isOpen(x,(int)lastT.getY()-1)) {
			arrayList.add(new GridState(t,x,y-1));	
		}
		
		return arrayList;
	}
	/** 
	 * @param Grid to test for Open adjacent spots in the original board
	 * @return an arrayList of new TraceStates made from open positions
	 */
	private ArrayList<GridState> getPositions(Grid board) {
		ArrayList<GridState> arrayList = new ArrayList<GridState>();
		int x = (int)board.getStartingPoint().getX();
		int y = (int)board.getStartingPoint().getY();
		if(board.isOpen(x,y+1)) {
			arrayList.add(new GridState(board,x,y+1));
		}
		if(board.isOpen(x,y-1)) {
			arrayList.add(new GridState(board,x,y-1));
		}
		if(board.isOpen(x-1,y)) {
			arrayList.add(new GridState(board,x-1,y));
		}
		if(board.isOpen(x+1,y)) {
			arrayList.add(new GridState(board,x+1,y));
		}
		return arrayList;
	}
	
	public ArrayList<GridState> findShortestPath(Grid inputGrid, int type) {
		ArrayList<GridState> gridsToReturn = new ArrayList<GridState>();
		//Grid g = new Grid(testArray);
		//		g.startingPoint = new Point(1, 1); 
		//		g.endingPoint = new Point(7, 7);
		Grid board = null;
		Storage<GridState> stateStore = null;
		ArrayList<GridState> bestPaths = new ArrayList<GridState>();
		GridState retrieved = null;
		Point lastT = null;
		//initialize the Storage to use either a stack or queue
		if(type == 1)
			stateStore = new Storage<GridState>(Storage.DataStructure.queue); 
		else if(type == 2)
			stateStore = new Storage<GridState>(Storage.DataStructure.stack);
		else {
			System.out.println("bad input");
			return null;
		}
		
		
		ListIterator<GridState> iterator = getPositions(inputGrid).listIterator();
		while(iterator.hasNext()) {
			stateStore.store(iterator.next());
			iterator.remove();
		}
		while(!stateStore.isEmpty()) {
			
			retrieved = stateStore.retrieve();
			if(retrieved == null)
				System.out.println("how");
			gridsToReturn.add(retrieved);
			
			if(retrieved.isComplete()) {
				return gridsToReturn;
//				if(bestPaths.isEmpty()) {
//					bestPaths.add(retrieved);
//				}
//				else if(retrieved.getPath().size()==bestPaths.get(0).getPath().size()) {
//					bestPaths.add(retrieved);
//				}
//				else if(retrieved.getPath().size()<bestPaths.get(0).getPath().size()) {
//					//System.out.println(retrieved.getPath().size());
//					bestPaths.clear();
//					bestPaths.add(retrieved);
//				}
			}
	
			//WHERE was last trace added? OK. It's added as POINT in the path. So path(lenght-1) should give the most recent one.
			lastT = retrieved.getPath().get(retrieved.getPath().size()-1);
			iterator = getPositions(retrieved).listIterator();
			while(iterator.hasNext()) {
				stateStore.store(iterator.next());
				iterator.remove();
			}
			
		}
		//I could return null here.. otherwise there is no finish. Then I could do a nullcheck. 
		//
		return gridsToReturn;
	}
	public static void wait(int ms)
	{
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}
	
	//do we want squareArray or charArray here?
	public char[][] generateRandomMap(int sides) {
		//do while(findShortestPath(thismap) == null)
		Grid grid;
		char randomMap[][];
		ArrayList<GridState> pathStates;
		do {
			randomMap = new char[sides][sides];
			for(int i = 0; i < sides; i++) {
				for(int j = 0; j < sides; j++) {
					char c;
					if(Math.random() <= blockerChance) {
						c = 'X';
					} else {
						c = 'O';
					}
					randomMap[i][j] = c;
				}
				randomMap[1][1] = '1';
				randomMap[sides - 1][sides - 1] = '2';
			}
			grid = new Grid(randomMap);
			grid.startingPoint = new Point(1, 1);
			grid.endingPoint = new Point(sides-1, sides-1);
			pathStates = findShortestPath(grid, searchType);
		} while(pathStates == null);

		return randomMap;
	}
	
	
//	public static char[][] createTestArray(){
//		char[][] testArray = {
//				{'O','O','X','X','O','O','X','X','O','O'},
//				{'O','1','O','X','X','O','X','O','O','X'},
//				{'X','O','O','O','O','O','X','O','X','O'},
//				{'X','X','O','X','X','O','O','O','X','O'},
//				{'O','X','O','X','O','O','X','O','O','O'},
//				{'O','O','O','O','O','X','O','O','X','X'},
//				{'X','X','X','O','X','O','O','O','O','O'},
//				{'X','O','O','O','O','O','O','2','X','O'},
//				{'O','O','X','X','O','X','O','X','O','O'},
//				{'O','X','O','O','O','X','O','O','O','O'},
//		};
//		return testArray;
//	}
}
