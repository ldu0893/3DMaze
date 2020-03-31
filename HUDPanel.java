import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

public class HUDPanel extends JPanel implements ActionListener {
	private static String iconPath = "Icons/";
	private JButton[] buttons;
	private Game game;
	private Maze maze;
	private Player player;
	private static String[] commands = { "up", "left", "forward", "right", "down" };
	public static final int forward = 1;
	public static final int up = 2;
	public static final int down = 3;
	public static final int right = 4;
	public static final int left = 5;
	private JButton map;
	private boolean menuOn;
	private JButton toggleMenu;
	private MovementListener movementListener;
	private MapListener mapListener;
	//private MenuListener menuListener;
<<<<<<< HEAD
<<<<<<< HEAD
	private ChamberLayers chamberView;
	private int rows;
	private int cols;
	private Component[][] panelHolder;
	private JButton menuMap, instructions, quit;
	public HUDPanel hudPanel = this;

	public HUDPanel(Game game, Maze maze, ChamberLayers chamberView) {
=======
	private ChamberLayers chamberLayers;
	private int rows;
	private int cols;
=======
	private ChamberLayers chamberLayers;
	private int rows;
	private int cols;
>>>>>>> master
	private JPanel menuPanel, dPadPanel;
	private JButton menuMap, instructions, quit;
	public HUDPanel hudPanel = this;

	public HUDPanel(Game game, Maze maze, ChamberLayers chamberLayers) {
<<<<<<< HEAD
>>>>>>> master
=======
>>>>>>> master
		this.game = game;
		player = game.getPlayer();
		this.maze = maze;
		this.chamberLayers = chamberLayers;
		player = game.getPlayer();
		setProperties();
		setupEventListeners();
		setupButtons();
		addButtons();
		this.requestFocusInWindow();
	}

	private void setProperties() {
		this.setFocusTraversalKeysEnabled(false);
		this.setFocusable(true);
<<<<<<< HEAD
<<<<<<< HEAD
		rows = 600 / 50;
		cols = 800 / 50;
		panelHolder = new Component[rows][cols];
		this.setLayout(new GridLayout(rows, cols, 0, 0));
=======
		//rows = 600 / 50;
		//cols = 800 / 50;
		this.setLayout(new GridLayout(4, 5, 0, 0));
>>>>>>> master
=======
		//rows = 600 / 50;
		//cols = 800 / 50;
		this.setLayout(new GridLayout(4, 5, 0, 0));
>>>>>>> master
		this.setSize(800, 600);
		this.setOpaque(false);
	}

	private void setupEventListeners() {
		movementListener = new MovementListener(game.getPlayer());
		mapListener = new MapListener(game);
		//menuListener = new MenuListener();
<<<<<<< HEAD
<<<<<<< HEAD
		this.addKeyListener(movementListener);
=======
		game.getFrame().addKeyListener(movementListener);
>>>>>>> master
=======
		game.getFrame().addKeyListener(movementListener);
>>>>>>> master
		this.addKeyListener(mapListener);
	}

	private void setupButtons() {
		buttons = new JButton[5];
		for (int i = 0; i < buttons.length; i++) {
			ImageIcon icon = new ImageIcon(iconPath + commands[i] + "Arrow.png");
			buttons[i] = new JButton(icon);
			buttons[i].setActionCommand(commands[i]);
			buttons[i].addActionListener(movementListener);
			buttons[i].setContentAreaFilled(false);
			buttons[i].setBorderPainted(false);
		}
		menuOn = false;
<<<<<<< HEAD
<<<<<<< HEAD
		toggleMenu = new JButton("Menu");
		toggleMenu.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				menuOn = !menuOn;
				//TODO When you press menu, the components don't appear, even though this section is run
				if (menuOn) {
					panelHolder[1][cols-1] = menuMap;
					panelHolder[2][cols-1] = instructions;
					panelHolder[3][cols-1] = quit;
					System.out.println("hello");
				} else {
					panelHolder[1][cols-1] = new JLabel("");
					panelHolder[2][cols-1] = new JLabel("");
					panelHolder[3][cols-1] = new JLabel("");
				}
				hudPanel.revalidate();
				hudPanel.repaint();
				chamberView.toggleMenu();
				chamberView.revalidate();
				chamberView.repaint();
=======
=======
>>>>>>> master
		ImageIcon menuIcon = new ImageIcon(iconPath + "Hamburger.png");	
		toggleMenu = new JButton(menuIcon);
		toggleMenu.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				menuOn = !menuOn;
				menuPanel.removeAll();
				GridBagConstraints c = new GridBagConstraints ();
				c.fill = GridBagConstraints.BOTH;
				c.weightx = 1;
				c.weighty = 1;
				c.gridx = 0;
				c.gridy = 0;
				menuPanel.add(map, c);
				c.gridx = 1;
				c.gridy = 0;
				menuPanel.add(toggleMenu, c);
				
				if (menuOn) {
					c.gridx = 0;
					c.gridy = 1;
					c.gridwidth = 2;
					menuPanel.add(menuMap, c);
					c.gridx = 0;
					c.gridy = 2;
					c.gridwidth = 2;
					menuPanel.add(instructions, c);
					c.gridx = 0;
					c.gridy = 3;
					c.gridwidth = 2;
					menuPanel.add(quit, c);
				} else {					
					c.gridx = 0;
					c.gridy = 1;
					c.gridwidth = 2;
					menuPanel.add(new JLabel(), c);
					c.gridx = 0;
					c.gridy = 2;
					c.gridwidth = 2;
					menuPanel.add(new JLabel(), c);
					c.gridx = 0;
					c.gridy = 3;
					c.gridwidth = 2;
					menuPanel.add(new JLabel(), c);
				}
				hudPanel.revalidate();
				hudPanel.repaint();
				chamberLayers.revalidate();
				chamberLayers.repaint();
<<<<<<< HEAD
>>>>>>> master
=======
>>>>>>> master
				game.getFrame().revalidate();
				game.getFrame().repaint();
			}
		});
		toggleMenu.setMargin(new Insets(0, 0, 0, 0));
		toggleMenu.setContentAreaFilled(false);
		toggleMenu.setBorderPainted(false);
		ImageIcon mapIcon = new ImageIcon(iconPath + "Scroll.png");		
		map = new JButton(mapIcon);
		map.addActionListener(mapListener);
		map.setMargin(new Insets(0, 0, 0, 0));
<<<<<<< HEAD
<<<<<<< HEAD
=======
		map.setContentAreaFilled(false);
		map.setBorderPainted(false);
>>>>>>> master
=======
		map.setContentAreaFilled(false);
		map.setBorderPainted(false);
>>>>>>> master
		
		menuMap = new JButton("Map");
		menuMap.setActionCommand("map");
		menuMap.addActionListener(this);
		menuMap.setMargin(new Insets(0, 0, 0, 0));
		instructions = new JButton("Instructions");
		instructions.setActionCommand("instructions");
		instructions.addActionListener(this);
		instructions.setMargin(new Insets(0, 0, 0, 0));
		quit = new JButton("Quit Game");
		quit.setActionCommand("quit");
		quit.addActionListener(this);
		quit.setMargin(new Insets(0, 0, 0, 0));
<<<<<<< HEAD
<<<<<<< HEAD
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("map")) {
			game.goToMapView();
		} else if (e.getActionCommand().equals("instructions")) {
			game.toggleInstructions();
		} else if (e.getActionCommand().equals("quit")) {
			game.goToIntroScreen();
		}
	}
	
	private void addButtons() {
		//{ "up", "left", "forward", "right", "down" }
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++) {
				if (i == 0 && j == cols-2) {
					panelHolder[i][j] = map;
				} else if (i == 0 && j == cols-1) {
					panelHolder[i][j] = toggleMenu;
				} else if (i == rows-3 && j == 1) {
					panelHolder[i][j] = buttons[0];
				} else if (i == rows-2 && j == 0) {
					panelHolder[i][j] = buttons[1];
				} else if (i == rows-2 && j == 1) {
					panelHolder[i][j] = buttons[2];
				} else if (i == rows-2 && j == 2) {
					panelHolder[i][j] = buttons[3];
				} else if (i == rows-1 && j == 1) {
					panelHolder[i][j] = buttons[4];
				} else {
					panelHolder[i][j] = new JLabel("");
				}
				this.add(panelHolder[i][j]);
			}
=======
	}
	
=======
	}
	
>>>>>>> master
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("map")) {
			game.goToMapView();
		} else if (e.getActionCommand().equals("instructions")) {
			game.toggleInstructions();
		} else if (e.getActionCommand().equals("quit")) {
			game.goToIntroScreen();
		}
	}
	
	private void addButtons() {
		menuPanel = new JPanel();
		menuPanel.setOpaque(false);
		menuPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints ();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		
		c.gridx = 0;
		c.gridy = 0;
		menuPanel.add(map, c);
		c.gridx = 1;
		c.gridy = 0;
		menuPanel.add(toggleMenu, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		menuPanel.add(new JLabel(), c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		menuPanel.add(new JLabel(), c);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		menuPanel.add(new JLabel(), c);
		
		dPadPanel = new JPanel();
		dPadPanel.setOpaque(false);
		dPadPanel.setLayout(new GridLayout(3, 3, 0, 0));
		dPadPanel.add(new JLabel());
		dPadPanel.add(buttons[0]);
		dPadPanel.add(new JLabel());
		dPadPanel.add(buttons[1]);
		dPadPanel.add(buttons[2]);
		dPadPanel.add(buttons[3]);
		dPadPanel.add(new JLabel());
		dPadPanel.add(buttons[4]);
		dPadPanel.add(new JLabel());
		
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 5; j++)
				if (i == 0 && j == 4)
					this.add(menuPanel);
				else if (i == 3 && j == 0)
					this.add(dPadPanel);
				else
					this.add(new JLabel());
		
<<<<<<< HEAD
>>>>>>> master
=======
>>>>>>> master
		enableComponents(getRoom(maze, player), player.getOrientation());
		for (Component comp : this.getComponents()) {
			comp.setFocusable(false);
		}
	}

<<<<<<< HEAD
<<<<<<< HEAD
	private void disableComponents() {
		for (Component comp : this.getComponents()) {
=======
=======
>>>>>>> master
	public void disableComponents() {
		for (Component comp : menuPanel.getComponents()) {
			comp.setEnabled(false);
		}
		for (Component comp : dPadPanel.getComponents()) {
<<<<<<< HEAD
>>>>>>> master
=======
>>>>>>> master
			comp.setEnabled(false);
		}
	}

<<<<<<< HEAD
<<<<<<< HEAD
	private void enableComponents(Room room, int orientation) {
		for (Component comp : this.getComponents()) {
=======
=======
>>>>>>> master
	public void enableComponents(Room room, int orientation) {
		for (Component comp : menuPanel.getComponents()) {
			comp.setEnabled(true);
		}
		for (Component comp : dPadPanel.getComponents()) {
<<<<<<< HEAD
>>>>>>> master
=======
>>>>>>> master
			comp.setEnabled(true);
		}
		buttons[0].setEnabled(canMove(room, up, orientation));
		buttons[4].setEnabled(canMove(room, down, orientation));
		buttons[2].setEnabled(canMove(room, forward, orientation));
	}
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> master
	
	public void enableComponents () {
		enableComponents(getRoom(maze, player), player.getOrientation());
	}
<<<<<<< HEAD
>>>>>>> master
=======
>>>>>>> master

	public static boolean canMove(Room room, int movementDirection, int playerOrientation) {
		if (movementDirection == left || movementDirection == right) {
			return true;
		} else if (movementDirection == up) {
			return room.getDoor(Room.up);
		} else if (movementDirection == down) {
			return room.getDoor(Room.down);
		} else if (movementDirection == forward) {
			return room.getDoor(playerOrientation);
		}
		return false;
	}

	public static Room getRoom(Maze maze, Player player) {
		int[] pos = player.getPosition();
		return maze.getRoom(pos[0], pos[1], pos[2]);
	}

	private class MovementListener implements ActionListener, KeyListener {
		private Player player;

		public MovementListener(Player p) {
			this.player = p;
		}

		public void animationFinished() {
			chamberLayers.setAnimation(0);
			enableComponents(getRoom(maze, player), player.getOrientation());
		}

		public void actionPerformed(ActionEvent e) {
			if (chamberLayers.getAnimation() == 0) {
				if (e.getActionCommand().equals(commands[2])) {
					move(forward);
				} else if (e.getActionCommand().equals(commands[0])) {
					move(up);
				} else if (e.getActionCommand().equals(commands[4])) {
					move(down);
				} else if (e.getActionCommand().equals(commands[1])) {
					move(left);
				} else if (e.getActionCommand().equals(commands[3])) {
					move(right);
				}
			}
		}

		public void keyReleased(KeyEvent e) {
			System.out.println("hi");
			if (chamberLayers.getAnimation() == 0) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					move(forward);
				} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_KP_UP) {
					move(up);
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_KP_DOWN) {
					move(down);
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_KP_LEFT) {
					move(left);
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_KP_RIGHT) {
					move(right);
				}
			}
		}

		public void move(int direction) {
<<<<<<< HEAD
<<<<<<< HEAD
			chamberView.setAnimation(direction);
=======
			chamberLayers.setAnimation(direction);
>>>>>>> master
=======
			chamberLayers.setAnimation(direction);
>>>>>>> master
			disableComponents();
			double score = (double) maze.shortestPath() / (double) player.getMoves();
			if (!canMove(getRoom(maze, player), direction, player.getOrientation())) {
				animationFinished();
				return;
			}
			if (direction == forward) {
				if (getRoom(maze, player).leadsOutside(player.getOrientation())) {
					game.win(score);
				}
				player.moveForward();
<<<<<<< HEAD
<<<<<<< HEAD
=======
				chamberLayers.getChamberView().moveForward();
>>>>>>> master
=======
				chamberLayers.getChamberView().moveForward();
>>>>>>> master
			} else if (direction == down) {
				if (getRoom(maze, player).leadsOutside(Room.down)) {
					game.win(score);
				}
				player.moveDown();
<<<<<<< HEAD
<<<<<<< HEAD
=======
				chamberLayers.getChamberView().moveDown();
>>>>>>> master
=======
				chamberLayers.getChamberView().moveDown();
>>>>>>> master
			} else if (direction == up) {
				if (getRoom(maze, player).leadsOutside(Room.up)) {
					game.win(score);
				}
				player.moveUp();
<<<<<<< HEAD
<<<<<<< HEAD
			} else if (direction == left) {
				player.turnLeft();
=======
=======
>>>>>>> master
				chamberLayers.getChamberView().moveUp();
			} else if (direction == left) {
				player.turnLeft();
				chamberLayers.getChamberView().turnLeft();
<<<<<<< HEAD
>>>>>>> master
=======
>>>>>>> master
			} else if (direction == right) {
				player.turnRight();
				chamberLayers.getChamberView().turnRight();
			}
		}

		// Beginning of empty methods
		public void keyPressed(KeyEvent e) {
		}

		public void keyTyped(KeyEvent e) {
		}
		// End of empty methods
	}

	private class MapListener implements ActionListener, KeyListener {
		private Game game;

		MapListener(Game game) {
			this.game = game;
		}

		public void actionPerformed(ActionEvent event) {
			game.goToMapView();
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_TAB) {
				game.goToMapView();
			}
		}

		// Begin empty methods
		public void keyPressed(KeyEvent e) {
		}

		public void keyTyped(KeyEvent e) {
		}
		// End empty methods
	}

//	private class MenuListener implements ActionListener {
//		public void actionPerformed(ActionEvent arg0) {
//			menuOn = !menuOn;
//		}
//	}
}