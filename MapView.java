import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MapView extends JPanel {
	private Game game;
	private Maze maze;
	JFrame frame;
	private JPanel MapViewPane;
	JPanel MenuPane;
	private JButton Up;
	private JButton Down;
	JButton ReturnChamber;
	JButton Instructions;
	JButton QuitGame;
	JButton MenuBtn;
	boolean MenuOn;
//	private PaintGUI gui;
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
		player = game.getPlayer();
		this.maze = maze;
		MenuOn = false;
		//frame = new JFrame();// not included in actual code?
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.makeFrameFullSize(frame);
		frame = game.getFrame();
		
		green = new Color(102, 204, 0);
		difficulty = maze.getDifficulty();

		//MapViewPane = new JPanel();
		MapViewPane = this;
		this.setSize(800, 600);
		MapViewPane.setLayout(null);
		ground = new Color(192, 192, 192);
		MapViewPane.setBackground(ground);

		MenuPane = new JPanel();
		MapViewPane.add(MenuPane);
		MenuPane.setBounds(15 * frame.getWidth() / 18, 0, frame.getWidth() / 6, 2 * frame.getHeight() / 4);
		MenuPane.setLayout(null);

		ChamberListener cl = new ChamberListener(game);
		ReturnChamber = new JButton("Chamber View");
		ReturnChamber.addActionListener(cl);
		frame.addKeyListener(cl);
		//frame.setFocusable(true);
		//frame.setFocusTraversalKeysEnabled(false);
		
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
		MenuBtn.setBounds(23 * frame.getWidth() / 25, frame.getHeight() / 35, frame.getWidth() / 17, frame.getWidth() / 25);
		
		LevelListener ll = new LevelListener(this); 
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
		Up.setBounds(8*frame.getWidth()/100, (4*frame.getHeight()/10)+((32*frame.getHeight()/100)-(4*frame.getHeight()/10))/2, frame.getWidth()/36, frame.getHeight()/11);
		Down.setBounds(8*frame.getWidth()/100, (3*frame.getHeight()/5)-frame.getHeight()/17, frame.getWidth()/36, frame.getHeight()/11);
		currentLvl = player.getPosition().getZ();
	}

	class menuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!MenuOn) {
				MenuOn = true;
				repaint();
			} else {
				MenuOn = false;
				repaint();
			}
			MenuPane.setVisible(MenuOn);
			repaint();
		}
	}

	private class ChamberListener implements KeyListener, ActionListener {
		private Game game;
		ChamberListener(Game gameParam){ this.game = gameParam; }
		
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Chamber View")) {
				game.goToChamberView();
				game.chamberLayers.HUDPanel.changeMap(false);
			}
		}
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_TAB) {
				System.out.println("feourydfihbioufh89weoiybhdjk");
				game.goToChamberView();
				game.chamberLayers.HUDPanel.changeMap(false);
			}
		}
		
		public void keyTyped(KeyEvent e) {
			// empty; use keyReleased instead
			repaint();
		}

		public void keyPressed(KeyEvent e) {
			// empty; use keyReleased instead
			System.out.println("Player position: " + player.getPosition().getX() + " " + player.getPosition().getY() + " " + player.getPosition().getZ());
			repaint();
		}
	}

	class OptionsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Instructions")) {
				game.toggleInstructions();
			} else if (e.getActionCommand().equals("Quit Game")) {
				game.goToIntroScreen();
			}
		}
	}

	private class LevelListener implements ActionListener {
		private MapView mapView;
		LevelListener(MapView mapview){ mapView = mapview; }

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == Up) {
				viewUp();
				repaint();
			} else if (e.getSource() == Down) {
				viewDown();
				repaint();
			}
			repaint();
		}
	}

	public void viewUp() {
		if(currentLvl<levelNum-1) {
			currentLvl++;
			repaint();
		}
		repaint();
	}
	

	public void viewDown() {
		if(currentLvl>0) {
			currentLvl--;
			repaint();
		}
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//this.drawHeader(g);
		
		//Menu and key components
		Graphics2D g2 = (Graphics2D) g;
		for(int i = 1; i <= 3; i++) {
			g2.setStroke(new BasicStroke(4));
			g.setColor(Color.BLACK);
			g.drawRoundRect(23 * frame.getWidth() / 25, i*frame.getHeight() / 35, frame.getWidth() / 17, frame.getWidth() / 100, 10, 15);
			g.setColor(Color.gray);
			g.fillRoundRect(23 * frame.getWidth() / 25, i*frame.getHeight() / 35, frame.getWidth() / 17, frame.getWidth() / 100, 10, 15);
			
		}
		g.setColor(Color.black);
		Font KeyFont = new Font ("Times New Roman", 20, 25);
		g.setFont(KeyFont);
		g.drawString("KEY:", 26*frame.getWidth()/30, 8*frame.getHeight()/15);
		Font WordFont = new Font ("Serif Bold", 20, 15);
		g.setFont(WordFont);
		g.drawString("Door Up", 26*frame.getWidth()/30+18, 14*frame.getHeight()/20-30);
		g.drawString("Door Down", 26*frame.getWidth()/30+10, 16*frame.getHeight()/20-15);
		g.drawString("You Are Here", 26*frame.getWidth()/30, 18*frame.getHeight()/20-10);
		g.setColor(green);
		g.fillPolygon(new int[] {26*frame.getWidth()/30+15, (26*frame.getWidth()/30)+80, ((26*frame.getWidth()/30+15)+(26*frame.getWidth()/30)+80)/2}, new int[] {(14*frame.getHeight()/20)-50, (14*frame.getHeight()/20)-50, (14*frame.getHeight()/20)-80}, 3);
		g.setColor(Color.red);
		g.fillPolygon(new int[] {26*frame.getWidth()/30+15, (26*frame.getWidth()/30)+80, ((26*frame.getWidth()/30+15)+(26*frame.getWidth()/30)+80)/2}, new int[] {(16*frame.getHeight()/20)-75, (16*frame.getHeight()/20)-75, (16*frame.getHeight()/20)-45}, 3);
		g.setColor(Color.blue);
		g.fillPolygon(new int[] {26*frame.getWidth()/30+30, (26*frame.getWidth()/30)+60, ((26*frame.getWidth()/30+30)+(26*frame.getWidth()/30)+60)/2}, new int[] {(18*frame.getHeight()/20)-30, (18*frame.getHeight()/20)-30, (18*frame.getHeight()/20)-80}, 3);
		g.setColor(ground);
		g.fillPolygon(new int[] {26*frame.getWidth()/30+30, (26*frame.getWidth()/30)+60, ((26*frame.getWidth()/30+30)+(26*frame.getWidth()/30)+60)/2}, new int[] {(18*frame.getHeight()/20)-30, (18*frame.getHeight()/20)-30, (18*frame.getHeight()/20)-40}, 3);
		
		//arrow components
		int arrowLength = frame.getWidth()/18;
		g.setColor(green);
		g.fillPolygon(new int[] {2*frame.getWidth()/30, (2*frame.getWidth()/30)+arrowLength, ((2*frame.getWidth()/30)+(2*frame.getWidth()/30)+arrowLength)/2}, new int[] {(4*frame.getHeight()/10), (4*frame.getHeight()/10), (32*frame.getHeight()/100)}, 3);
		g.fillRect((arrowLength/4)+(2*frame.getWidth()/30), (4*frame.getHeight()/10), arrowLength/2, frame.getHeight()/17);
		g.setColor(Color.red);
		g.fillPolygon(new int[] {2*frame.getWidth()/30, (2*frame.getWidth()/30)+arrowLength, ((2*frame.getWidth()/30)+(2*frame.getWidth()/30)+arrowLength)/2}, new int[] {(3*frame.getHeight()/5), (3*frame.getHeight()/5), 67*frame.getHeight()/100}, 3);
		g.fillRect((arrowLength/4)+(2*frame.getWidth()/30), (3*frame.getHeight()/5)-frame.getHeight()/17, arrowLength/2, frame.getHeight()/17);
			
	//Map components
		Color currentColor;
		if (player.roomVisited(player.getPosition().getX(), player.getPosition().getY(), currentLvl))
			currentColor = maze.getRoom(player.getPosition().getX(), player.getPosition().getY(), currentLvl).getColor();
		else
			currentColor = Color.LIGHT_GRAY;
		g.setColor(Color.black);
		if(difficulty == 0) {
			levelNum = 4;
			BoxWidth = (60*frame.getWidth()/100)/4;
			BoxHeight = (70*frame.getHeight()/100)/4;
		}else if (difficulty == 1){
			levelNum = 5;
			BoxWidth = (60*frame.getWidth()/100)/5;
			BoxHeight = (70*frame.getHeight()/100)/5;
		}else if(difficulty == 2){
			levelNum = 6;
			BoxWidth = (60*frame.getWidth()/100)/6;
			BoxHeight = (70*frame.getHeight()/100)/6;
		}
		
		//draws map
		for(int i = 0; i <levelNum; i++) {
			for (int j = 0; j<levelNum; j++) {
				if(player.roomVisited(i,j,currentLvl)) {
					Room room = maze.getRoom(i, j, currentLvl);
					int temp=j;
					j=levelNum-j-1;
					g.setColor(room.getColor());
					g.fillRect((i*BoxWidth)+20*frame.getWidth()/100, (j*BoxHeight)+20*frame.getHeight()/100, BoxWidth, BoxHeight);
					g.setColor(Color.black);
					g.drawLine((i*BoxWidth)+20*frame.getWidth()/100, (j*BoxHeight)+20*frame.getHeight()/100, ((i+1)*BoxWidth)+20*frame.getWidth()/100, (j*BoxHeight)+20*frame.getHeight()/100);
					g.drawLine((i*BoxWidth)+20*frame.getWidth()/100+BoxWidth, (j*BoxHeight)+20*frame.getHeight()/100, (i*BoxWidth)+20*frame.getWidth()/100+BoxWidth, ((j+1)*BoxHeight)+20*frame.getHeight()/100);
					g.drawLine((i*BoxWidth)+20*frame.getWidth()/100, (j*BoxHeight)+20*frame.getHeight()/100+BoxHeight, ((i+1)*BoxWidth)+20*frame.getWidth()/100, (j*BoxHeight)+20*frame.getHeight()/100+BoxHeight);
					g.drawLine((i*BoxWidth)+20*frame.getWidth()/100, (j*BoxHeight)+20*frame.getHeight()/100, (i*BoxWidth)+20*frame.getWidth()/100, ((j+1)*BoxHeight)+20*frame.getHeight()/100);
					g.setColor(ground);
					if(room.getDoor(0)) {
						g.drawLine((i*BoxWidth)+20*frame.getWidth()/100+(BoxWidth/3), (j*BoxHeight)+20*frame.getHeight()/100, (i*BoxWidth)+((20*frame.getWidth()/100)+(2*BoxWidth/3)), (j*BoxHeight)+20*frame.getHeight()/100);
						//g.drawLine(, (j*BoxHeight)+20*frame.getHeight()/100, (i*BoxWidth)+20*frame.getWidth()/100+(BoxWidth), (j*BoxHeight)+20*frame.getHeight()/100);	
					}else{
					}
					if(room.getDoor(1)){
						g.drawLine((i*BoxWidth)+20*frame.getWidth()/100+BoxWidth, (j*BoxHeight)+20*frame.getHeight()/100+(BoxHeight/3), (i*BoxWidth)+20*frame.getWidth()/100+BoxWidth, (j*BoxHeight)+20*frame.getHeight()/100+(2*BoxHeight/3));
						//g.drawLine((i*BoxWidth)+20*frame.getWidth()/100+BoxWidth, (j*BoxHeight)+20*frame.getHeight()/100+(2*BoxHeight/3), (i*BoxWidth)+20*frame.getWidth()/100+BoxWidth, (j*BoxHeight)+20*frame.getHeight()/100+BoxHeight);
					}else{
					}
					if(room.getDoor(2)){
						g.drawLine((i*BoxWidth)+20*frame.getWidth()/100+(BoxWidth/3), (j*BoxHeight)+20*frame.getHeight()/100+BoxHeight, (i*BoxWidth)+20*frame.getWidth()/100+(2*BoxWidth/3), (j*BoxHeight)+20*frame.getHeight()/100+BoxHeight);
						//g.drawLine((i*BoxWidth)+((20*frame.getWidth()/100)+(2*BoxWidth/3)), (j*BoxHeight)+20*frame.getHeight()/100+BoxHeight, (i*BoxWidth)+20*frame.getWidth()/100+(BoxWidth), (j*BoxHeight)+20*frame.getHeight()/100+BoxHeight);
					}else{
					}
					if(room.getDoor(3)){
						g.drawLine((i*BoxWidth)+20*frame.getWidth()/100, (j*BoxHeight)+20*frame.getHeight()/100+(BoxHeight/3), (i*BoxWidth)+20*frame.getWidth()/100, (j*BoxHeight)+20*frame.getHeight()/100+(2*BoxHeight/3));
						//g.drawLine((i*BoxWidth)+20*frame.getWidth()/100, (j*BoxHeight)+20*frame.getHeight()/100+(2*BoxHeight/3), (i*BoxWidth)+20*frame.getWidth()/100, (j*BoxHeight)+20*frame.getHeight()/100+BoxHeight);
					}else{
					}
					if(room.getDoor(4)){
						g.setColor(green);
						g.fillPolygon(new int[] {(i*BoxWidth)+(20*frame.getWidth()/100)+BoxWidth/2, (i*BoxWidth)+((20*frame.getWidth()/100)+BoxWidth/2)-(BoxWidth/4), (i*BoxWidth)+((20*frame.getWidth()/100)+BoxWidth/2)+(BoxWidth/4)}, new int[] {(j*BoxHeight)+20*frame.getHeight()/100, (j*BoxHeight)+(20*frame.getHeight()/100)+(BoxWidth/8), (j*BoxHeight)+(20*frame.getHeight()/100)+(BoxWidth/8)}, 3);
					}
					if(room.getDoor(5)){
						g.setColor(Color.red);
						g.fillPolygon(new int[] {(i*BoxWidth)+(20*frame.getWidth()/100)+BoxWidth/2, (i*BoxWidth)+((20*frame.getWidth()/100)+BoxWidth/2)-(BoxWidth/4), (i*BoxWidth)+((20*frame.getWidth()/100)+BoxWidth/2)+(BoxWidth/4)}, new int[] {((j+1)*BoxHeight)+20*frame.getHeight()/100, ((j+1)*BoxHeight)+(20*frame.getHeight()/100)-(BoxWidth/8), ((j+1)*BoxHeight)+(20*frame.getHeight()/100)-(BoxWidth/8)}, 3);
					}
					j=temp;
				}else if(!player.roomVisited(i,j,currentLvl)){
					int temp=j;
					j=levelNum-j-1;
					g.setColor(Color.black);
					g.drawLine((i*BoxWidth)+20*frame.getWidth()/100, (j*BoxHeight)+20*frame.getHeight()/100, ((i+1)*BoxWidth)+20*frame.getWidth()/100, (j*BoxHeight)+20*frame.getHeight()/100);
					g.drawLine((i*BoxWidth)+20*frame.getWidth()/100+BoxWidth, (j*BoxHeight)+20*frame.getHeight()/100, (i*BoxWidth)+20*frame.getWidth()/100+BoxWidth, ((j+1)*BoxHeight)+20*frame.getHeight()/100);
					g.drawLine((i*BoxWidth)+20*frame.getWidth()/100, (j*BoxHeight)+20*frame.getHeight()/100+BoxHeight, ((i+1)*BoxWidth)+20*frame.getWidth()/100, (j*BoxHeight)+20*frame.getHeight()/100+BoxHeight);
					g.drawLine((i*BoxWidth)+20*frame.getWidth()/100, (j*BoxHeight)+20*frame.getHeight()/100, (i*BoxWidth)+20*frame.getWidth()/100, ((j+1)*BoxHeight)+20*frame.getHeight()/100);
					g.setColor(ground);
					g.fillRect((i*BoxWidth)+202*frame.getWidth()/1000, (j*BoxHeight)+204*frame.getHeight()/1000, 95*BoxWidth/100, 95*BoxHeight/100);
					j=temp;
				}
			}
			
		}
		
		//Player current position
		int[] currentPosition = player.getPosition().toArray();
		currentPosition[1]=levelNum-currentPosition[1]-1;
		int currentOrientation = player.getOrientation();
		if(currentOrientation == 0) {
			//north
			g.setColor(Color.blue);
			g.fillPolygon(new int[] {(currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+BoxWidth/2,(currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+3*BoxWidth/8, (currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+5*BoxWidth/8}, new int[] {(currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+BoxHeight/5, (currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+4*BoxHeight/5, (currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+4*BoxHeight/5}, 3);
			g.setColor(currentColor);
			g.fillPolygon(new int[] {(currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+BoxWidth/2,(currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+3*BoxWidth/8, (currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+5*BoxWidth/8}, new int[] {(currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+64*BoxHeight/100, (currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+4*BoxHeight/5, (currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+4*BoxHeight/5}, 3);
		}else if(currentOrientation == 1){
			//east
			g.setColor(Color.blue);
			g.fillPolygon(new int[] {(currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+4*BoxWidth/5,(currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+BoxWidth/5, (currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+BoxWidth/5}, new int[] {(currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+BoxHeight/2, (currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+2*BoxHeight/8, (currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+6*BoxHeight/8}, 3);
			g.setColor(currentColor);
			g.fillPolygon(new int[] {(currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+36*BoxWidth/100,(currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+BoxWidth/5, (currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+BoxWidth/5}, new int[] {(currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+BoxHeight/2, (currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+2*BoxHeight/8, (currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+6*BoxHeight/8}, 3);
		}else if(currentOrientation == 2){
			//south
			g.setColor(Color.blue);
			g.fillPolygon(new int[] {(currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+BoxWidth/2,(currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+3*BoxWidth/8, (currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+5*BoxWidth/8}, new int[] {(currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+4*BoxHeight/5, (currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+BoxHeight/5, (currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+BoxHeight/5}, 3);
			g.setColor(currentColor);
			g.fillPolygon(new int[] {(currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+BoxWidth/2,(currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+3*BoxWidth/8, (currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+5*BoxWidth/8}, new int[] {(currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+36*BoxHeight/100, (currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+BoxHeight/5, (currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+BoxHeight/5}, 3);
		}else if(currentOrientation == 3){
			//west
			g.setColor(Color.blue);
			g.fillPolygon(new int[] {(currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+BoxWidth/5,(currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+4*BoxWidth/5, (currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+4*BoxWidth/5}, new int[] {(currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+BoxHeight/2, (currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+2*BoxHeight/8, (currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+6*BoxHeight/8}, 3);
			g.setColor(currentColor);
			g.fillPolygon(new int[] {(currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+64*BoxWidth/100,(currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+4*BoxWidth/5, (currentPosition[0]*BoxWidth)+(20*frame.getWidth()/100)+4*BoxWidth/5}, new int[] {(currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+BoxHeight/2, (currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+2*BoxHeight/8, (currentPosition[1]*BoxHeight)+(20*frame.getHeight()/100)+6*BoxHeight/8}, 3);
		}
		
		//Grid labels
		String[] Label = {"1","2","3","4","5","6"};
		g.setColor(Color.black);
		for(int i = 0; i<levelNum; i++) {
			Font LabelFont = new Font ("Serif Bold", 20, 35);
			g.setFont(LabelFont); 
			g.drawString(Label[i], (i*BoxWidth)+(20*frame.getWidth()/100)+BoxWidth/2, (20*frame.getHeight()/100)-30);
			g.drawString(Label[i], (20*frame.getWidth()/100)-50, (i*BoxHeight)+(20*frame.getHeight()/100)+BoxHeight/2);
		}
		
		g.setColor(Color.WHITE);
		Font f = new Font("Arial", Font.BOLD, 20);
		g.setFont(f);
		g.drawString("Level: " + player.getPosition().getZ() + "     Row: " + player.getPosition().getX() + "     Column: "
				+ player.getPosition().getY(), (int) (.05 * 600), (int) (.05 * 800));
		g.drawString("Moves Made: " + player.getMoves(), (int) (.05 * 600), (int) (.08 * 800));
		g.drawString("Facing: " + player.getOrientation(), (int) (.05 * 600), (int) (.11 * 800));
	}

//	private void makeFrameFullSize(JFrame aFrame) {
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		//aFrame.setSize(screenSize.width, screenSize.height);
//		aFrame.setSize(800, 600);
//	}
	

//	public static void main(String[] args) {
//		new MapView();
//	}
}