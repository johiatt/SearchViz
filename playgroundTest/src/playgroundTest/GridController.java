package playgroundTest;

import java.awt.Color;
import java.util.ArrayList;

public class GridController {
	//grid defined by screen size and number of squares
	public static Square[][] createSquareArray(int squares, int width) {
		Square[][] s = new Square[squares][squares];
		int squareSide = (int) (width/squares);
		Color c;
		for(int i = 0; i < squares; i++) {
			for(int j = 0; j < squares; j++) {
					if((i % 2 == j % 2) ) {
						c = Color.BLUE;
					} else {
						c = Color.GRAY;
					}
					s[i][j] = (new Square((int)j*(squareSide), (int)i*(squareSide), squareSide, squareSide, c));
				}
			}
		return s;		
	}
}
