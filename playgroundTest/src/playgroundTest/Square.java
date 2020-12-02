package playgroundTest;

import java.awt.Color;
//types
//0 = start
//1 = end
//2 = blocker
//create basic 10x10 maze

public class Square {
	int x;
	int y; 
	int width;
	int height;
	Color color;
	char typeCode;
	
	public Square(int x, int y, int width, int height, Color color, char typeCode) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.typeCode = typeCode;
	}
}
