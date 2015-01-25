//package game;

public class Tile {
	int Value;
	int locationX, locationY;
	public Tile(int x, int y) {
		Value = 0;
		locationX = x;
		locationY = y;
	}
	
	public Tile(Position P) {
		Value = 0;
		locationX = P.posX;
		locationY = P.posY;
	}
	
	public Tile(int value, int x, int y) {
		Value = value;
		locationX = x;
		locationY = y;
	}
	
	public Tile(int value, Position P) {
		Value = value;
		locationX = P.posX;
		locationY = P.posY;
	}
	
	public boolean equals(Tile A) {
		if (A == null) return false;
		return Value == A.Value;
	}
	
	public void print() {
		System.out.println(Value + " " + locationX + " " + locationY);
	}
}

/*	0: up
	2: down
	1: left
	3: right
*/

class Direction {
	int dirX;
	int dirY;
	int k;
	public Direction(int k) {
		this.k = k;
		switch (k) {
		case 0: 
			dirX = -1; dirY = 0; 
			break;
		case 2: 
			dirX = 1;  dirY = 0; 
			break;
		case 1: 
			dirX = 0;  dirY = -1; 
			break;
		case 3: 
			dirX = 0;  dirY = 1; 
			break;
		
		}
	}
	public Direction reverseDirection() {
		return new Direction(k ^ 1);
	}
}

class Position {
	int posX, posY;
	public Position(int x, int y) {
		posX = x;
		posY = y;
	}
	
	public void moveInDirection(Direction D) {
		posX -= D.dirX;
		posY -= D.dirY;
	}
	
	public Position nextPosition(Direction D) {
		return new Position(posX - D.dirX, posY - D.dirY);
	}
	
	public boolean equals(Position P) {
		if (P == null) return false;
		return (posX == P.posX && posY == P.posY);
	}
}