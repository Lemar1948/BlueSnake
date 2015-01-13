package SnakeMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class SnakeMain extends JPanel implements Runnable, KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	private final int GWIDTH = 400, GHEIGHT = 400;
	private final Dimension GAME_DIM = new Dimension(GWIDTH, GHEIGHT);
	
	private JFrame frame = new JFrame();;
	//private JPanel panel;
	private ArrayList<ArrayList<JLabel>> labels  = new ArrayList<ArrayList<JLabel>>();
	private Stack<ArrayList<Integer>> snakeBody  = new Stack<ArrayList<Integer>>();
	private final int SIZE = 20;
	private final String NAME = "Snake";
	
	public boolean gameover = false;
	public int headX = 1, headY = 1;
	public int foodX, foodY;
	public int dir = 1;
	boolean flag = false;
	
	public String[][] map = new String[SIZE][SIZE];
	
	public SnakeMain() {
		this.setPreferredSize(GAME_DIM);
		this.setMinimumSize(GAME_DIM);
		
		//panel = new JPanel();
		//panel.setLayout(new GridLayout(SIZE,SIZE,0,0));
		
		frame.setContentPane(this);
		frame.getContentPane().setLayout(new GridLayout(SIZE,SIZE,0,0));
		
		
		frame.setSize(GWIDTH, GHEIGHT);
		frame.setTitle(NAME);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		
		
		initializeLabels();
		addLabelsToPanel();
		
		//frame.add(panel);
		
		frame.addKeyListener(this);
	}
	public void initializeLabels() {
		ArrayList<JLabel> ls;
		for(int i = 0; i < SIZE; i++) {
			ls = new ArrayList<JLabel>();
			for(int j = 0; j < SIZE; j++) {
				ls.add(new JLabel(""));
				ls.get(j).setBackground(Color.WHITE);
				ls.get(j).setOpaque(true);
				map[i][j] = "white";
			}
			labels.add(ls);
		}
		paintInBlue();
	}
	public void addLabelsToPanel() {
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				frame.getContentPane().add(labels.get(i).get(j));	
			}
		}
	}
	public void paintInBlue() {
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(headX);
		al.add(headY);
		snakeBody.add(al);
		labels.get(headY).get(headX).setBackground(Color.BLUE);
		labels.get(headY).get(headX).setOpaque(true);
		map[headX][headY] = "blue";
	}
	public void paintInWhite() {
		int tailX = snakeBody.get(0).get(0);
		int tailY = snakeBody.get(0).get(1);
		labels.get(tailY).get(tailX).setBackground(Color.WHITE);
		labels.get(tailY).get(tailX).setOpaque(true);
		map[headX][headY] = "white";
		snakeBody.remove(0);
	}
	
	public void checkCollision() {
		for(int i = 0; i < snakeBody.size()-1; i++) {
			if(snakeBody.get(i).get(0) == headX && snakeBody.get(i).get(1) == headY) {
				System.out.println("BANG!");
				gameover = true;
				break;
			} else {
				gameover = false;
			}
		}
	}
	public void start() {
		new Thread(this).start();
	}
	public void makeMove() {
		switch(dir) {
		case 0:
			if(flag) { // not eating
				if(headY != 0) {
					headY -= 1;	
				} else {
					headY = SIZE-1;
				}
				paintInBlue();
				paintInWhite();
			} else { // eating
				if(headY != 0) {
					headY -= 1;	
				} else {
					headY = SIZE-1;
				}
				paintInBlue();
			}
			break;
		case 1:
			if(flag) { // not eating
				if(headY != SIZE-1) {
					headY += 1;	
				} else {
					headY = 0;
				}
				paintInBlue();
				paintInWhite();
			} else { // eating
				if(headY != SIZE-1) {
					headY += 1;	
				} else {
					headY = 0;
				}
				paintInBlue();
			}
			break;
		case 2:
			if(flag) { // not eating
				if(headX != 0) {
					headX -= 1;	
				} else {
					headX = SIZE-1;
				}
				paintInBlue();
				paintInWhite();
			} else { // eating
				if(headX != 0) {
					headX -= 1;	
				} else {
					headX = SIZE-1;
				}
				paintInBlue();
			}
			break;
		case 3:
			if(flag) { // not eating
				if(headX != SIZE-1) {
					headX += 1;	
				} else {
					headX = 0;
				}
				paintInBlue();
				paintInWhite();
			} else { // eating
				if(headX != SIZE-1) {
					headX += 1;	
				} else {
					headX = 0;
				}
				paintInBlue();
			}
			break;
		}
	}
	//boolean eaten = false
	public void newFood() {
		Random rend = new Random();
		
		
		while(!flag) {
			foodX = rend.nextInt(SIZE);
			foodY = rend.nextInt(SIZE);
			
			if(map[foodX][foodY].equals("blue")) {
				break;
			} else {
				labels.get(foodY).get(foodX).setBackground(Color.RED);
				labels.get(foodY).get(foodX).setOpaque(true);
				flag = true;
				break;
			}
		}
		if(headX == foodX && headY == foodY) {
			flag = false;
			System.out.println("YAMMY!");
		}
	}
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		//up = 0; down = 1; left = 2; right = 3
		if(code == KeyEvent.VK_UP && dir != 1) {
			//keyPressed = true;
			dir = 0;
			//System.out.println("UP " + headX + " | " + headY);
		}
		if(code == KeyEvent.VK_DOWN && dir != 0) {
			//keyPressed = true;
			dir = 1;
			//System.out.println("DOWN " + headX + " | " + headY);
		}
		if(code == KeyEvent.VK_LEFT && dir != 3) {
			//keyPressed = true;
			dir = 2;
			//System.out.println("LEFT " + headX + " | " + headY);
		}
		if(code == KeyEvent.VK_RIGHT && dir != 2) {
			//keyPressed = true;
			dir = 3;
			//System.out.println("RIGHT " + headX + " | " + headY);
		}
	}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void run() {
		while(!gameover) {
			//keyPressed = false;
			makeMove();
			newFood();
			checkCollision();
			try {
				Thread.sleep(200);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		SnakeMain sm = new SnakeMain();
		sm.start();
	}
}
