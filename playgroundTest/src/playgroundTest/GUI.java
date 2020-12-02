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

public class GUI extends JFrame {
	

	public static Board board;
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
		
		board = new Board(squareArray);
		this.setContentPane(board);
		graphics = getGraphics();
		System.out.println(this.width);
		//button
		JButton b=new JButton("Click Here");  
	    b.setBounds(50,100,95,30);  
	    this.add(b);   
	    b.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    		//call a method on the squareArray
//	    		alternate = !alternate;
//	    		board.revalidate();
//	    		board.repaint();
	    		
	    		try {
					g.findShortestPath();
					//print board at least
					//Square[][] array = board.boardSquareArray;
					board = new Board(board.boardSquareArray);
					//board.repaint();
					board.paintComponent(getGraphics());
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
	public static class Board extends JPanel {
		public Square[][] boardSquareArray;
		public Board(Square[][] squareArray) {
			boardSquareArray = squareArray;
		}
		public void paintComponent(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, width, width);
			for(int i = 0; i < boardSquareArray.length; i++) {
				for(int j = 0; j < boardSquareArray.length; j++) {
					Square s = boardSquareArray[i][j];
//					if(alternate)
//						g.setColor(Color.black);
//					else
//						g.setColor(Color.blue);
					System.out.println(GridController.getColor(s.typeCode));
					g.setColor(GridController.getColor(s.typeCode));
					g.fillRect(s.x, s.y, s.width, s.height);
				}
			}
		}
	}
	public void printSquare(Square[][] array) {
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array.length; j++) {
				System.out.print(array[i][j].typeCode + " ");
			}
			System.out.println();
		}
	}

}
