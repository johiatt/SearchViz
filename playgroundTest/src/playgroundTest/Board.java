package playgroundTest;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel {
	public static Graphics graphicsContext;
	private static final long serialVersionUID = 1L;
	private Square[][] boardSquareArray;
	int width;
	public Board(Square[][] squareArray, int width) {
		boardSquareArray = squareArray;
		this.width = width;
	}
	
	public void updateBoard(Square[][] squareArray) {
		this.boardSquareArray = squareArray;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//paint super first... this might be correct
		
		super.paintComponent(g);
		graphicsContext = g;
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, width, width);
		for(int i = 0; i < boardSquareArray.length; i++) {
			for(int j = 0; j < boardSquareArray.length; j++) {
				Square s = boardSquareArray[i][j];
//				if(alternate)
//					g.setColor(Color.black);
//				else
//					g.setColor(Color.blue);
				//System.out.println(GridController.getColor(s.typeCode));
				g.setColor(GridController.getColor(s.typeCode));
				g.fillRect(s.x, s.y, s.width, s.height);
			}
		}
		
	}
}