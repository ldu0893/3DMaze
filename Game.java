import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Game {
	private ArrayList<Double> minimumScores = new ArrayList();
	ArrayList<Double> topTen = new ArrayList();
	private int screenWidth, screenHeight;
	private JFrame gameFrame;
	private Player player;
	private Maze maze;
	private IntroScreen introScreen;
	private DifficultyScreen difficultyScreen;
	private ChamberView chamberView;
	private ChamberLayers chamberLayers;
	private MapView mapView;
	private Instructions instructionScreen;
	private Component backFromInstructions = null;;
	private EndScreen endScreen;
	
	public JFrame getFrame () {
		return gameFrame;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		//game.runGame(1);
	}

	public Game() {
		gameFrame = new JFrame();
		gameFrame.setMinimumSize(new Dimension(800,630));
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.setFocusable(true);
		
		introScreen = new IntroScreen(this);
		introScreen.setMinimumSize(new Dimension(800,600));
		
		difficultyScreen = new DifficultyScreen(this);
		difficultyScreen.setMinimumSize(new Dimension(800,600));
		
		instructionScreen = new Instructions(this);
		instructionScreen.setMinimumSize(new Dimension(800,600));
		
		gameFrame.add(introScreen);
		gameFrame.setVisible(true);
	}

	public void goToIntroScreen() {
		gameFrame.getContentPane().removeAll();
		gameFrame.add(introScreen);
		gameFrame.revalidate();
	}
	
	public void goToChamberView() {
		gameFrame.getContentPane().removeAll();
		gameFrame.add(chamberView);
		gameFrame.revalidate();
	}

	public void goToMapView() {
		gameFrame.removeAll();
		gameFrame.add(mapView);
	}

	public void goToDifficultySelect() {
		gameFrame.getContentPane().removeAll();
		gameFrame.add(difficultyScreen);
		gameFrame.revalidate();
	}

	public void runGame(int difficulty) {
		int size = 1;
		if (difficulty == 0) {
			size = 4;
		} else if (difficulty == 1) {
			size = 5;
		} else if (difficulty == 2) {
			size = 6;
		}
		int[] positions = { size - 1, size - 1, size - 1 };
		maze = new Maze(difficulty);
		player = new Player(positions, 2, size, size, size);
		chamberLayers = new ChamberLayers(this, maze);
		//chamberView = new ChamberView(this, maze, null);
		//mapView = new MapView(this, maze);
		gameFrame.getContentPane().removeAll();
		gameFrame.add(chamberLayers);
		//gameFrame.add(chamberView);
		gameFrame.revalidate();
	}

	public void toggleInstructions() {
		if (backFromInstructions != null) {
			gameFrame.getContentPane().removeAll();
			gameFrame.add((JPanel) backFromInstructions);
			gameFrame.revalidate();
			backFromInstructions.repaint();
			backFromInstructions = null;
		} else {
			backFromInstructions = gameFrame.getContentPane().getComponent(0);
			gameFrame.getContentPane().removeAll();
			gameFrame.add(instructionScreen);
			gameFrame.revalidate();
			instructionScreen.repaint();
		}
	}

	public ArrayList<Double> getMinimumScores() {
		return minimumScores;
	}

	public void submitScore(double score) {
		minimumScores.add(score);
		Collections.sort(minimumScores);
		for (int i = 0; i < 10; i++) {
			topTen = (ArrayList<Double>) minimumScores.subList(0, 9);
		}
	}

	public void win(double score) {
		submitScore(score);
		endScreen = new EndScreen(score, topTen, this);
		gameFrame.removeAll();
		gameFrame.add(endScreen);
	}

//	public void drawHeader(Graphics g) {
//		super.drawHeader(g);
//		g.setColor(Color.WHITE);
//		Font f = new Font("Arial", Font.BOLD, 20);
//		g.setFont(f);
//		g.drawString("Level: " + player.getPosition()[2] + "Row: " + player.getPosition()[0] + "Column: "
//				+ player.getPosition()[1], (int) (.05 * screenWidth), (int) (.05 * screenHeight));
//		g.drawString("Moves Made: " + player.getMoves(), (int) (.05 * screenWidth), (int) (.08 * screenHeight));
//		g.drawString("Facing: " + player.getOrientation(), (int) (.05 * screenWidth), (int) (.11 * screenHeight));
//	}

	public Player getPlayer() {
		return player;
	}
}