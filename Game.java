//package game;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.javafx.collections.MappingChange.Map;


/*
 * 0: UP
 * 1: LEFT
 * 2: DOWN
 * 3: RIGHT
 */

public class Game extends JFrame implements KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Grid map;
	static HashMap<String, Color> color = new HashMap<String, Color>();
	int score;
	JLabel text[][];
	Font font = new Font("微软雅黑", Font.BOLD, 28);
	Font font2 = new Font("微软雅黑", Font.BOLD, 25);
	public void update() {
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				if (map.getTile(new Position(i, j)) == null) {
					text[i][j].setText("");
					setColor(i, j, 0);
				}
				else {
					text[i][j].setText(String.valueOf(map.getTile(new Position(i, j)).Value));
					setColor(i, j, map.getTile(new Position(i, j)).Value);
				}
	}
	public Game() {
		map = new Grid();
		map.print();
		score = 0;
		initColor();
		this.setLayout(new GridLayout(4, 4, 2, 2));
//		for(int i = 0; i < 4; i++)
//			for(int j = 0; j < 4; j++)
//				if (map.getTile(new Position(i, j)) != null)
//				this.add(new JButton(" " + map.getTile(new Position(i, j)).Value));
//				else this.add(new JButton(" 0 "));
		this.setSize(400, 400);
		this.setTitle("2048");
		this.setBackground(new Color(30, 30, 30));
		text = new JLabel[4][4];
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++) {
				text[i][j] = new JLabel("",JLabel.CENTER);
				text[i][j].setFont(font);
				text[i][j].setForeground(Color.black);
		//		text[i][j].setBounds(j * 120 + 10, i * 120 + 10, 100, 100);
				text[i][j].setOpaque(true);
				setColor(i, j, 0);
//				text[i][j].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(160, 90, 59)));
				text[i][j].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(255, 255, 255)));
				this.add(text[i][j]);
			}
		update();
		this.setLocationRelativeTo(null);
//		this.setResizable(false);
		this.setResizable(false);
		this.addKeyListener(this);
		this.setFocusable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
/*
	addKeyListener(new KeyAdapter() {
		public void KeyPressed(KeyEvent e) {
			int code = e.getKeyCode();
			while (code != KeyEvent.VK_LEFT && code != KeyEvent.VK_RIGHT 
					&& code != KeyEvent.VK_UP && code != KeyEvent.VK_DOWN) {
				code = e.getKeyCode();
			}
			switch (code) {
			case KeyEvent.VK_LEFT:
				map.k = 1;
				break;
			case KeyEvent.VK_RIGHT:
				map.k = 3;
				break;
			case KeyEvent.VK_UP:
				map.k = 0;
				break;
			case KeyEvent.VK_DOWN:
				map.k = 2;
				break;
			}
		}
	});
*/	
	public void KeyPressed(KeyEvent e) {

	}
	
	
	public boolean moveAndMerge(int k) {
		boolean flag = false;
		Position P = map.startScanPosition(k);
		Position startP = map.startScanPosition(k);
		Direction D = new Direction(k);
		while (Grid.isInBounds(P)) {
			Position tempP = new Position(P.posX, P.posY);
			while (Grid.isInBounds(P) && map.isTileNull(P)) P.moveInDirection(D);
			if (tempP.equals(P)) {
				if (map.findNextPosition(P, D) != null) {
					score += map.merge(P, map.findNextPosition(P, D));
					flag = true;
				}
				P.moveInDirection(D);
				if (Grid.isInBounds(P)) continue;
			}
			if (!Grid.isInBounds(P)) {
				Direction tempD = D.reverseDirection();
				P = new Position(Grid.mod(P.posX - tempD.dirX, 4), Grid.mod(P.posY - tempD.dirY, 4));
				if (startP.posX == P.posX && startP.posY == P.posY) break;
				continue;
			}
			if (map.findNextPosition(P, D) != null) {
				score += map.merge(P, map.findNextPosition(P, D));
				flag = true;
			}
			map.move(tempP, P);
			P = tempP.nextPosition(D);
			flag = true;
		}
		return flag;
	}
	
	public static void main(String[] args) {
		new Game();
	}


	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}


	public void keyPressed(KeyEvent e) {
		this.setFocusable(true);
		int code = e.getKeyCode();
		if (code != KeyEvent.VK_LEFT && code != KeyEvent.VK_RIGHT 
				&& code != KeyEvent.VK_UP && code != KeyEvent.VK_DOWN) 
			return;
		switch (code) {
		case KeyEvent.VK_LEFT:
			map.k = 1;
//			System.out.println("LEFT");
			break;
		case KeyEvent.VK_RIGHT:
			map.k = 3;
//			System.out.println("RIGHT");
			break;
		case KeyEvent.VK_UP:
			map.k = 0;
//			System.out.println("UP");
			break;
		case KeyEvent.VK_DOWN:
			map.k = 2;
//			System.out.println("DOWN");
			break;
		default:
			map.k = -1;
			break;
		}
		
		
		if (map.k != -1 && moveAndMerge(map.k)) {
			map.insertRandomTile();
	//		this.removeAll();
	//		this.addKeyListener(this);
	//		for(int i = 0; i < 4; i++)
	//			for(int j = 0; j < 4; j++)
	//				if (map.getTile(new Position(i, j)) != null)
	//				this.add(new JButton(" " + map.getTile(new Position(i, j)).Value));
	//				else this.add(new JButton(" 0 "));
			map.print();
			System.out.println(score);
			update();
			map.k = -1;
		}
		if (map.isMoveAvailable() == false) {
			this.removeKeyListener(this);
			JFrame Lose = new JFrame();
			Lose.setLocationRelativeTo(null);
			Lose.setSize(100, 100);
			Lose.setResizable(false);
//			Lose.setLayout(new GridLayout(1, 2, 2, 2));
			JLabel textScore = new JLabel(String.valueOf(score), JLabel.CENTER);
			textScore.setFont(font2);
			Lose.add(textScore);
			
			/*
			JButton button = new JButton("Restart");
			button.setFont(font2);
			Lose.add(button);
			*/
			Lose.setVisible(true);
		}
		
	}
	
	public void setColor(int i, int j, int str){
		Color temp = color.get(String.valueOf(str));
		if (temp != null) 
			text[i][j].setBackground(color.get(String.valueOf(str)));
		else
			text[i][j].setBackground(color.get("other"));
	}
	public void initColor() {
		color.put("2", new Color(216, 191, 216));
		color.put("4", new Color(147, 112, 219));
		color.put("8", new Color(100, 149, 237));
		color.put("16", new Color(135, 206, 235));
		color.put("32", new Color(127, 255, 212));
		color.put("64", new Color(143, 188, 143));
		color.put("128", new Color(189, 183, 107));
		color.put("256", new Color(222, 184, 135));
		color.put("512", new Color(188, 143, 143));
		color.put("1024", new Color(205, 92, 92));
		color.put("2048", new Color(255, 0, 0));
		color.put("4096", new Color(178, 34, 34));
		color.put("8192", new Color(128, 0, 0));
		color.put("other", new Color(130, 130, 130));
	}
}
