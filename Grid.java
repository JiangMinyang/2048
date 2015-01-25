//package game;
import java.lang.*;
import java.util.Random;
import java.util.Vector;

public class Grid {
	private Tile grid[][];
	static int size = 4;
	int k;
	public Grid() {
		k = -1;
		grid = new Tile[4][4];
		Tile T = getRandomTile();
		insertTile(T);
		T = getRandomTile();
		insertTile(T);
	}
	
	private Tile getRandomTile() {
		Random rand = new Random();
		Vector<Position> availableTiles = getAvailableTiles();
		int x = rand.nextInt(availableTiles.size());
		Tile T = new Tile(getRandomValue(), availableTiles.get(x));
		return T;
	}
	
	public void insertRandomTile() {
		this.insertTile(getRandomTile());
	}

	public Tile getTile(Position P) {
		return grid[P.posX][P.posY];
	}
	
	public Vector<Position> getAvailableTiles() {
		Vector<Position> P = new Vector<Position>();
		for(int i = 0; i < size; i++)
			for(int j = 0; j < size; j++)
				if (grid[i][j] == null) P.add(new Position(i, j));
		return P;
		
	}
	
	public Position findNextPosition(Position P, Direction D) {
		Position P2 = new Position(P.posX, P.posY);
		P2.moveInDirection(D);
		while (Grid.isInBounds(P2) && grid[P2.posX][P2.posY] == null) {
			P2.moveInDirection(D);
		}
		if (Grid.isInBounds(P2) && grid[P2.posX][P2.posY].Value == grid[P.posX][P.posY].Value) 
			return P2;
		return null;
	}
	
	
	
	public void print() {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++)
				if (grid[i][j] == null) System.out.printf("%5s", 0);
				else System.out.printf("%5s", grid[i][j].Value);
			System.out.println();
		}
//		System.out.println();
	}
	
	public boolean isMoveAvailable() {
		for(int i = 0; i < size; i++)
			for(int j = 0; j < size; j++) {
				if (grid[i][j] == null) return true;
				Position P = new Position(i, j);
				for(int k = 0; k < 4; k++) {
					Direction D = new Direction(k);
					if (isInBounds(P.nextPosition(D)) &&  getTile(P).equals(getTile(P.nextPosition(D))))
						return true;
				}
			}
		return false;
	}
	
	public static int mod(int a, int b) {
		if (a < 0) return (a + b) % b;
		return a % b;
	}
	
	public Position startScanPosition(int k) {
		switch (k) {
		case 0:
			return new Position(0, 0);
		case 2:
			return new Position(3, 3);
		case 1:
			return new Position(0, 0);
		case 3:
			return new Position(3, 3);
		}
		return null;
	}
	
	public boolean isTileNull(Position P) {
		return grid[P.posX][P.posY] == null; 
	}
	
	public int merge(Position P, Position P2) {
		grid[P.posX][P.posY].Value *= 2;
		removeTile(P2);
		return grid[P.posX][P.posY].Value;
	}
	
	public void move(Position P, Position P2) {
		grid[P.posX][P.posY] = grid[P2.posX][P2.posY];
		removeTile(P2);
	}
	
	public static boolean isInBounds(Position P) {
		if (P.posX < 0 || P.posX >= size) return false;
		if (P.posY < 0 || P.posY >= size) return false;
		return true;
	}
	
	private int getRandomValue() {
		if (Math.random() < 0.9) return 2;
		else return 4;
	}
	
	private void insertTile(Tile T) {
		grid[T.locationX][T.locationY] = T;
	}
	
	private void removeTile(Position P) {
		grid[P.posX][P.posY] = null;
	}
		
	public static void main(String[] args) {
		Grid a = new Grid();
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++)
				if (a.grid[i][j] != null) a.grid[i][j].print();
		}

	}

}
