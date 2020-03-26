import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MapView extends JPanel {
	private Game game;
	private Maze maze;
	private JPanel MapViewPane;
	JPanel MenuPane;
	private JButton Up;
	private JButton Down;
	JButton ReturnChamber;
	JButton Instructions;
	JButton QuitGame;
	JButton MenuBtn;
	boolean MenuOn;
	private PaintGUI gui;
	private Color ground;
	private Color green;
	private int BoxWidth;
	private int BoxHeight;
	int difficulty;
	int levelNum;
	int currentLvl;
	Player player;

	public MapView(Game game, Maze maze) {
		this.game = game;
		this.maze = maze;
		player = game.getPlayer();
		MenuOn = false;
		
		green = new Color(102, 204, 0);
		difficulty = 2; //need a getter for difficulty

		MapViewPane = new JPanel();
		MapViewPane.setLayout(null);
		ground = new Color(192, 192, 192);
		MapViewPane.setBackground(ground);

		MenuPane = new JPanel();
		MapViewPane.add(MenuPane);
		MenuPane.setBounds(15 * 800 / 18, 0, 800 / 6, 2 * 600 / 4);
		MenuPane.setLayout(null);

		ChamberListener cl = new ChamberListener(game);
		ReturnChamber = new JButton("Chamber View");
		ReturnChamber.addActionListener(cl);
		game.getFrame().addKeyListener(cl);
		game.getFrame().setFocusable(true);
		game.getFrame().setFocusTraversalKeysEnabled(false);
		
		ReturnChamber.setBorder(BorderFactory.createBevelBorder(0, ground, Color.black, Color.white, ground));
		ReturnChamber.setBackground(Color.white);
		ReturnChamber.setOpaque(true);

		OptionsListener ol = new OptionsListener();
		Instructions = new JButton("Instructions");
		Instructions.addActionListener(ol);
		Instructions.setBackground(Color.white);
		Instructions.setOpaque(true);
		Instructions.setBorder(BorderFactory.createBevelBorder(0, ground, Color.black, Color.white, ground));
		QuitGame = new JButton("Quit Game");
		QuitGame.addActionListener(ol);
		QuitGame.setBackground(Color.white);
		QuitGame.setOpaque(true);
		QuitGame.setBorder(BorderFactory.createBevelBorder(0, ground, Color.black, Color.white, ground));
		MenuPane.add(ReturnChamber);
		ReturnChamber.setBounds(MenuPane.getWidth() / 12, 5 * MenuPane.getHeight() / 12, 8 * MenuPane.getWidth() / 10,
				MenuPane.getHeight() / 10);
		MenuPane.add(Instructions);
		Instructions.setBounds(MenuPane.getWidth() / 12, 7 * MenuPane.getHeight() / 12, 8 * MenuPane.getWidth() / 10,
				MenuPane.getHeight() / 10);
		MenuPane.add(QuitGame);
		QuitGame.setBounds(MenuPane.getWidth() / 12, 9 * MenuPane.getHeight() / 12, 8 * MenuPane.getWidth() / 10,
				MenuPane.getHeight() / 10);
		Color darkGray = new Color(160, 160, 160);
		MenuPane.setBackground(darkGray);
		MenuPane.setVisible(false);
		
		menuListener ml = new menuListener();
		MenuBtn = new JButton();
		MapViewPane.add(MenuBtn);
		MenuBtn.setOpaque(false);
		MenuBtn.setContentAreaFilled(false);
		MenuBtn.setBorderPainted(false);
		MenuBtn.addActionListener(ml);
		MenuBtn.setBounds(720, 12, 55, 40);
		
		LevelListener ll = new LevelListener(new MapView(game, maze)); 
		Up = new JButton(); 
		Up.addActionListener(ll); 
		Down = new JButton();
		Down.addActionListener(ll); 
		MapViewPane.add(Up); 
		MapViewPane.add(Down);
		Up.setOpaque(false);
		Up.setContentAreaFilled(false);
		Up.setBorderPainted(false);
		Down.setOpaque(false);
		Down.setContentAreaFilled(false);
		Down.setBorderPainted(false);
		Up.setBounds(50, 215, 32, 70);
		Down.setBounds(50, 335, 32, 70);
		currentLvl = player.getPosition()[2];
		
		gui = new PaintGUI();
		gui.setSize(800, 600);
		gui.setOpaque(false);
		game.getFrame().add(gui);


		game.getFrame().add(MapViewPane);
		game.getFrame().setVisible(true);

	}

	class menuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!MenuOn) {
				MenuOn = true;
				gui.repaint();
			} else {
				MenuOn = false;
				gui.repaint();
			}
			MenuPane.setVisible(MenuOn);
			gui.repaint();
		}
	}

	private class ChamberListener implements KeyListener, ActionListener {
		private Game game;
		ChamberListener(Game gameParam){
			this.game = gameParam; 
		}
		
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Chamber View")) {
				game.goToChamberView();
			}
		}
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_TAB) {
				game.goToChamberView();
			}
		}
		
		public void keyTyped(KeyEvent e) {
			// empty; use keyReleased instead
			gui.repaint();
		}

		public void keyPressed(KeyEvent e) {
			// empty; use keyReleased instead
			gui.repaint();
		}
	}

	class OptionsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Instructions")) {
				game.toggleInstructions();
			} else if (e.getActionCommand().equals("Quit Game")) {
				game.getFrame().add(new IntroScreen(game));
			}
		}
	}

	private class LevelListener implements ActionListener {
		private MapView mapView;
		LevelListener(MapView mapview){
			mapView = mapview; 
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == Up) {
				viewUp();
				gui.repaint();
			} else if (e.getSource() == Down) {
				viewDown();
				gui.repaint();
			}
			gui.repaint();
		}
	}

	public void viewUp() {
		if(currentLvl<levelNum) {
			currentLvl++;
			gui.repaint();
		}
		gui.repaint();
	}
	

	public void viewDown() {
		if(currentLvl>0) {
			currentLvl--;
			gui.repaint();
		}
		gui.repaint();
	}

	private class PaintGUI extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			//game.drawHeader(g);
			//uncomment drawHeader once game creates this method
			
			//Menu and key components
			Graphics2D g2 = (Graphics2D) g;
			for(int i = 1; i <= 3; i++) {
				g2.setStroke(new BasicStroke(4));
				g.setColor(Color.BLACK);
				g.drawRoundRect(720, i*600 / 40, 55, 7, 10, 15);
				g.setColor(Color.gray);
				g.fillRoundRect(720, i*600 / 40, 55, 7, 10, 15);
				
			}
			g.setColor(Color.black);
			Font KeyFont = new Font ("Times New Roman", 20, 30);
			g.setFont(KeyFont);
			g.drawString("KEY:", 688, 335);
			Font WordFont = new Font ("Serif Bold", 20, 20);
			g.setFont(WordFont);
			g.drawString("Door Up", 681, 400);
			g.drawString("Door Down", 670, 460);
			g.drawString("You Are Here", 660, 542);
			g.setColor(green);
			g.fillPolygon(new int[] {690, 752, 721}, new int[] {375, 375, 350}, 3);
			g.setColor(Color.red);
			g.fillPolygon(new int[] {690, 752, 721}, new int[] {415, 415, 440}, 3);
			g.setColor(Color.blue);
			g.fillPolygon(new int[] {704, 738, 721}, new int[] {520, 520, 470}, 3);
			g.setColor(ground);
			g.fillPolygon(new int[] {704, 738, 721}, new int[] {520, 520, 510}, 3);
			
			//arrow components
			g.setColor(green);
			g.fillPolygon(new int[] {30, 103, 66}, new int[] {(4*600/10), (4*600/10), 195}, 3);
			g.fillRect(50, (4*600/10), 32, 50);
			g.setColor(Color.red);
			g.fillPolygon(new int[] {30, 103, 66}, new int[] {385, 385, 420}, 3);
			g.fillRect(50, 335, 32, 50);

			
			//Map component
			
			Color currentColor = maze.getRoom(player.getPosition()[0], player.getPosition()[1],player.getPosition()[1]).getColor();
			g.setColor(Color.black);
			if(difficulty == 0) {
				levelNum = 4;
				BoxWidth = (60*800/100)/4;
				BoxHeight = (70*600/100)/4;
			}else if (difficulty == 1){
				levelNum = 5;
				BoxWidth = (60*800/100)/5;
				BoxHeight = (70*600/100)/5;
			}else if(difficulty == 2){
				levelNum = 6;
				BoxWidth = (60*800/100)/6;
				BoxHeight = (70*600/100)/6;
			}
			
			//draws map
			for(int i = 0; i <levelNum; i++) {
				for (int j = 0; j<levelNum; j++) {
					if (player.roomVisited(i,j,currentLvl)) {
						g.setColor(maze.getRoom(i,j,currentLvl).getColor());
						g.fillRect((i*BoxWidth)+20*800/100, (j*BoxHeight)+20*600/100, BoxWidth, BoxHeight);
						g.setColor(Color.black);
						if(maze.getRoom(i,j,currentLvl).getDoor(0)) {
							g.drawLine((i*BoxWidth)+20*800/100, (j*BoxHeight)+20*600/100, (i*BoxWidth)+20*800/100+(BoxWidth/3), (j*BoxHeight)+20*600/100);
							g.drawLine((i*BoxWidth)+((20*800/100)+(2*BoxWidth/3)), (j*BoxHeight)+20*600/100, (i*BoxWidth)+20*800/100+(BoxWidth), (j*BoxHeight)+20*600/100);	
						}else{
							g.drawLine((i*BoxWidth)+20*800/100, (j*BoxHeight)+20*600/100, ((i+1)*BoxWidth)+20*800/100, (j*BoxHeight)+20*600/100);
						}
						if(maze.getRoom(i,j,currentLvl).getDoor(1)){
							g.drawLine((i*BoxWidth)+20*800/100+BoxWidth, (j*BoxHeight)+20*600/100, (i*BoxWidth)+20*800/100+BoxWidth, (j*BoxHeight)+20*600/100+(BoxHeight/3));
							g.drawLine((i*BoxWidth)+20*800/100+BoxWidth, (j*BoxHeight)+20*600/100+(2*BoxHeight/3), (i*BoxWidth)+20*800/100+BoxWidth, (j*BoxHeight)+20*600/100+BoxHeight);
						}else{
							g.drawLine((i*BoxWidth)+20*800/100+BoxWidth, (j*BoxHeight)+20*600/100, (i*BoxWidth)+20*800/100+BoxWidth, ((j+1)*BoxHeight)+20*600/100);
						}
						if(maze.getRoom(i,j,currentLvl).getDoor(2)){
							g.drawLine((i*BoxWidth)+20*800/100, (j*BoxHeight)+20*600/100+BoxHeight, (i*BoxWidth)+20*800/100+(BoxWidth/3), (j*BoxHeight)+20*600/100+BoxHeight);
							g.drawLine((i*BoxWidth)+((20*800/100)+(2*BoxWidth/3)), (j*BoxHeight)+20*600/100+BoxHeight, (i*BoxWidth)+20*800/100+(BoxWidth), (j*BoxHeight)+20*600/100+BoxHeight);
						}else{
							g.drawLine((i*BoxWidth)+20*800/100, (j*BoxHeight)+20*600/100+BoxHeight, ((i+1)*BoxWidth)+20*800/100, (j*BoxHeight)+20*600/100+BoxHeight);
						}
						if(maze.getRoom(i,j,currentLvl).getDoor(3)){
							g.drawLine((i*BoxWidth)+20*800/100, (j*BoxHeight)+20*600/100, (i*BoxWidth)+20*800/100, (j*BoxHeight)+20*600/100+(BoxHeight/3));
							g.drawLine((i*BoxWidth)+20*800/100, (j*BoxHeight)+20*600/100+(2*BoxHeight/3), (i*BoxWidth)+20*800/100, (j*BoxHeight)+20*600/100+BoxHeight);
						}else{
							g.drawLine((i*BoxWidth)+20*800/100, (j*BoxHeight)+20*600/100, (i*BoxWidth)+20*800/100, ((j+1)*BoxHeight)+20*600/100);
						}
						if(maze.getRoom(i,j,currentLvl).getDoor(4)){
							g.setColor(green);
							g.fillPolygon(new int[] {(i*BoxWidth)+(20*800/100)+BoxWidth/2, (i*BoxWidth)+((20*800/100)+BoxWidth/2)-(BoxWidth/4), (i*BoxWidth)+((20*800/100)+BoxWidth/2)+(BoxWidth/4)}, new int[] {(j*BoxHeight)+20*600/100, (j*BoxHeight)+(20*600/100)+(BoxWidth/8), (j*BoxHeight)+(20*600/100)+(BoxWidth/8)}, 3);
						}
						if(maze.getRoom(i,j,currentLvl).getDoor(5)){
							g.setColor(Color.red);
							g.fillPolygon(new int[] {(i*BoxWidth)+(20*800/100)+BoxWidth/2, (i*BoxWidth)+((20*800/100)+BoxWidth/2)-(BoxWidth/4), (i*BoxWidth)+((20*800/100)+BoxWidth/2)+(BoxWidth/4)}, new int[] {((j+1)*BoxHeight)+20*600/100, ((j+1)*BoxHeight)+(20*600/100)-(BoxWidth/8), ((j+1)*BoxHeight)+(20*600/100)-(BoxWidth/8)}, 3);
						}
					}else if(!player.roomVisited(i,j,currentLvl)){
						g.setColor(ground);
						g.fillRect((i*BoxWidth)+202*800/1000, (j*BoxHeight)+204*600/1000, 95*BoxWidth/100, 95*BoxHeight/100);
					}
				}
				
			}
			
			//player current position
			int[] currentPosition = player.getPosition();
			int currentOrientation = player.getOrientation();

			if(currentOrientation == 0) {
				//north
				g.setColor(Color.blue);
				g.fillPolygon(new int[] {(currentPosition[0]*BoxWidth)+(20*800/100)+BoxWidth/2,(currentPosition[0]*BoxWidth)+(20*800/100)+3*BoxWidth/8, (currentPosition[0]*BoxWidth)+(20*800/100)+5*BoxWidth/8}, new int[] {(currentPosition[1]*BoxHeight)+(20*600/100)+BoxHeight/5, (currentPosition[1]*BoxHeight)+(20*600/100)+4*BoxHeight/5, (currentPosition[1]*BoxHeight)+(20*600/100)+4*BoxHeight/5}, 3);
				g.setColor(currentColor);
				g.fillPolygon(new int[] {(currentPosition[0]*BoxWidth)+(20*800/100)+BoxWidth/2,(currentPosition[0]*BoxWidth)+(20*800/100)+3*BoxWidth/8, (currentPosition[0]*BoxWidth)+(20*800/100)+5*BoxWidth/8}, new int[] {(currentPosition[1]*BoxHeight)+(20*600/100)+64*BoxHeight/100, (currentPosition[1]*BoxHeight)+(20*600/100)+4*BoxHeight/5, (currentPosition[1]*BoxHeight)+(20*600/100)+4*BoxHeight/5}, 3);
			}else if(currentOrientation == 1){
				//east
				g.setColor(Color.blue);
				g.fillPolygon(new int[] {(currentPosition[0]*BoxWidth)+(20*800/100)+4*BoxWidth/5,(currentPosition[0]*BoxWidth)+(20*800/100)+BoxWidth/5, (currentPosition[0]*BoxWidth)+(20*800/100)+BoxWidth/5}, new int[] {(currentPosition[1]*BoxHeight)+(20*600/100)+BoxHeight/2, (currentPosition[1]*BoxHeight)+(20*600/100)+2*BoxHeight/8, (currentPosition[1]*BoxHeight)+(20*600/100)+6*BoxHeight/8}, 3);
				g.setColor(currentColor);
				g.fillPolygon(new int[] {(currentPosition[0]*BoxWidth)+(20*800/100)+36*BoxWidth/100,(currentPosition[0]*BoxWidth)+(20*800/100)+BoxWidth/5, (currentPosition[0]*BoxWidth)+(20*800/100)+BoxWidth/5}, new int[] {(currentPosition[1]*BoxHeight)+(20*600/100)+BoxHeight/2, (currentPosition[1]*BoxHeight)+(20*600/100)+2*BoxHeight/8, (currentPosition[1]*BoxHeight)+(20*600/100)+6*BoxHeight/8}, 3);
			}else if(currentOrientation == 2){
				//south
				g.setColor(Color.blue);
				g.fillPolygon(new int[] {(currentPosition[0]*BoxWidth)+(20*800/100)+BoxWidth/2,(currentPosition[0]*BoxWidth)+(20*800/100)+3*BoxWidth/8, (currentPosition[0]*BoxWidth)+(20*800/100)+5*BoxWidth/8}, new int[] {(currentPosition[1]*BoxHeight)+(20*600/100)+4*BoxHeight/5, (currentPosition[1]*BoxHeight)+(20*600/100)+BoxHeight/5, (currentPosition[1]*BoxHeight)+(20*600/100)+BoxHeight/5}, 3);
				g.setColor(currentColor);
				g.fillPolygon(new int[] {(currentPosition[0]*BoxWidth)+(20*800/100)+BoxWidth/2,(currentPosition[0]*BoxWidth)+(20*800/100)+3*BoxWidth/8, (currentPosition[0]*BoxWidth)+(20*800/100)+5*BoxWidth/8}, new int[] {(currentPosition[1]*BoxHeight)+(20*600/100)+36*BoxHeight/100, (currentPosition[1]*BoxHeight)+(20*600/100)+BoxHeight/5, (currentPosition[1]*BoxHeight)+(20*600/100)+BoxHeight/5}, 3);
			}else if(currentOrientation == 3){
				//west
				g.setColor(Color.blue);
				g.fillPolygon(new int[] {(currentPosition[0]*BoxWidth)+(20*800/100)+BoxWidth/5,(currentPosition[0]*BoxWidth)+(20*800/100)+4*BoxWidth/5, (currentPosition[0]*BoxWidth)+(20*800/100)+4*BoxWidth/5}, new int[] {(currentPosition[1]*BoxHeight)+(20*600/100)+BoxHeight/2, (currentPosition[1]*BoxHeight)+(20*600/100)+2*BoxHeight/8, (currentPosition[1]*BoxHeight)+(20*600/100)+6*BoxHeight/8}, 3);
				g.setColor(currentColor);
				g.fillPolygon(new int[] {(currentPosition[0]*BoxWidth)+(20*800/100)+64*BoxWidth/100,(currentPosition[0]*BoxWidth)+(20*800/100)+4*BoxWidth/5, (currentPosition[0]*BoxWidth)+(20*800/100)+4*BoxWidth/5}, new int[] {(currentPosition[1]*BoxHeight)+(20*600/100)+BoxHeight/2, (currentPosition[1]*BoxHeight)+(20*600/100)+2*BoxHeight/8, (currentPosition[1]*BoxHeight)+(20*600/100)+6*BoxHeight/8}, 3);
			}
			
			//Grid labels
			String[] Label = {"1","2","3","4","5","6"};
			g.setColor(Color.black);
			for(int i = 0; i<levelNum; i++) {
				Font LabelFont = new Font ("Serif Bold", 20, 35);
				g.setFont(LabelFont); 
				g.drawString(Label[i], (i*BoxWidth)+(20*800/100)+BoxWidth/2, (20*600/100)-30);
				g.drawString(Label[i], (20*800/100)-50, (i*BoxHeight)+(20*600/100)+BoxHeight/2);
			}
		}
	}
}
