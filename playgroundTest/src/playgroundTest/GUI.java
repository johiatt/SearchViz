package playgroundTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GUI extends JFrame {
	


	
//	public void repaint() {
//		paintAll(getGraphics());
//	}
	

	//public static Board board;
	public static int width = 1000;
	public static int squares = 10;
	public static int squareSide = (int) (width/squares);
	static boolean alternate = true;
	private Square[][] squareArray = GridController.createSquareArray(squares, width, squareSide);
	GridController g = new GridController();
	//public graphics
	public static Graphics graphics;
	
	
	//squareSide = 5
	//width = squareSide*squares
	
	public GUI() {
		
		
		this.setTitle("SearchViz");
		//no idea what's causing this offset
		this.setSize(width+5, width+30);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		//do we still need width change here or no
		
		Board board = new Board(squareArray, width);
		//this.setContentPane(board);
		//graphics = getGraphics();
		//board.paintComponent(getGraphics());
		//button
		JButton b = new JButton("Click Here");  
	    b.setBounds(50,100,95,30);
	    
	    this.add(b);
	    this.add(board);
	    b.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    		//call a method on the squareArray
//	    		alternate = !alternate;
//	    		board.revalidate();
//	    		board.repaint();
	    		
	    		try {
					ArrayList<GridState> grids = g.findShortestPath();
					//print board at least
					//Square[][] array = board.boardSquareArray;
					for(GridState grid : grids) {
						board.updateBoard(GridController.squareFromChar(grid.getBoard().getBoard()));
						//repaint works but paint directly doesn't?
//						board.revalidate();
//						board.repaint();
						paintAll(getGraphics());
						GridController.wait(100);
						//the CURRENT graphics thing is fucked. 
						//board.paintComponent(getGraphics());
						
					}

					//board.paintComponent(getGraphics());
					
					//painting and waiting should be done here.
					//get a list of states back, iterate through them
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }  
	    });

	}
	//pass in a method and paint the squares.
	//This will be modified when the painting process is iterative and we paint each state
	//For now the squareArray should be public
//	public void printSquare(Square[][] array) {
//		for(int i = 0; i < array.length; i++) {
//			for(int j = 0; j < array.length; j++) {
//				System.out.print(array[i][j].typeCode + " ");
//			}
//			System.out.println();
//		}
//	}
}