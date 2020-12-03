package playgroundTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GUI extends JFrame {

	//public static Board board;
	public static int width = 1000;
	public static int squares = 10;
	public static int squareSide = (int) (width/squares);
	static boolean alternate = true;
	//randomize squareArray
	//private Square[][] squareArray = GridController.createSquareArray(squares, width, squareSide);
	//private Square[][] squareArray = GridController.createSquareArray(squares, width, squareSide);
	GridController gridController = new GridController(20);
	//public graphics
	public static Graphics graphics;
	
	

	//squareSide = 5
	//width = squareSide*squares
	
	public GUI() throws FileNotFoundException {

		
		
		//squareArray = GridController.squareFromChar(gridController.generateRandomMap(5));
		this.setTitle("SearchViz");
		//no idea what's causing this offset
		this.setSize(width+5, width+30);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		//do we still need width change here or no
		
		
		Board board = new Board(gridController.initalSquares, width);
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
	    		
	    		for(GridState grid : gridController.shortestPath) {
					board.updateBoard(GridController.squareFromChar(grid.getBoard().getBoard()));
					//repaint works but paint directly doesn't?
//						board.revalidate();
//						board.repaint();
					
					paintAll(getGraphics());
					
					GridController.wait(10);
					//the CURRENT graphics thing is fucked. 
					//board.paintComponent(getGraphics());
					
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