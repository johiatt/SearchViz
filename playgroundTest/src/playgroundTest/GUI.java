package playgroundTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame {
	
	public Board board;
	int width = 1024;
	int squares = 5;
	int squareSide = (int) (width/squares);
	boolean alternate = true;
	
	public GUI() {
		this.setTitle("SearchViz");
		this.setSize(width, width);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		board = new Board(GridController.createSquareArray(squares, width));
		this.setContentPane(board);
		//button
		JButton b=new JButton("Click Here");  
	    b.setBounds(50,100,95,30);  
	    this.add(b);   
	    b.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){  
	    		alternate = !alternate;
	    		board.revalidate();
	    		board.repaint();
	        }  
	    });   
	}
	
	public class Board extends JPanel {
		Square[][] squareArray;
		public Board(Square[][] createSquareArray) {
			this.squareArray = createSquareArray;
		}
		public void paintComponent(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, width, width);
			for(int i = 0; i < squareArray.length; i++) {
				for(int j = 0; j < squareArray.length; j++) {
					Square s = squareArray[i][j];
					if(s.color == Color.BLUE) {
						System.out.println("blue color at x: " + i + " y: " + j);
					}
					g.setColor(s.color);
					g.fillRect(s.x, s.y, s.width, s.height);
				}
			}
		}
	}
}
