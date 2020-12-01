package playgroundTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame {
	
	public Board board;
	int width = 1024;
	int height = 1024;
	int squares = 5;
	int squareSide = (int) (width/squares);
	boolean alternate = true;
	
	public GUI() {
		this.setTitle("SearchViz");
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		board = new Board();
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
		public void paintComponent(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, width, height);
			for(int i = 0; i < squares; i++) {
				for(int j = 0; j < squares; j++) {
					if(alternate) {
						if((i % 2 == j % 2) ) {
							g.setColor(Color.GRAY);
						} else {
							g.setColor(Color.BLUE);
						}
					} else {
						if((i % 2 == j % 2) ) {
							g.setColor(Color.BLUE);
						} else {
							g.setColor(Color.GRAY);
						}
					}
					g.fillRect((int)j*(squareSide), (int)i*(squareSide), squareSide, squareSide);
				}
			}
		}
	}
}
