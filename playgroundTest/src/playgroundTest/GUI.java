package playgroundTest;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame {
	
	int width = 1024;
	int height = 1024;
	int squares = 20;
	int squareSide = (int) (width/squares);
	boolean alternate = true;

	
	public GUI() {
		this.setTitle("SearchViz");
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		Board board = new Board();
		this.setContentPane(board);
	}
	
	public class Board extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, width, height);
			for(int i = 0; i < squares; i++) {
				for(int j = 0; j < squares; j++) {
					if(i % 2 == j % 2) {
						g.setColor(Color.GRAY);
					} else {
						g.setColor(Color.BLUE);
					}
					g.fillRect((int)j*(squareSide), (int)i*(squareSide), squareSide, squareSide);
				}
			}
		}
	}
}
