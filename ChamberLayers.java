import java.awt.*;
import java.awt.event.*;
<<<<<<< HEAD
import java.util.Random;

=======
>>>>>>> master
import javax.swing.*;

public class ChamberLayers extends JLayeredPane {
	private static final long serialVersionUID = -2716144694728695338L;
	private int animation;
	public static final int forward = 3;
	public static final int up = 4;
	public static final int down = 5;
	public static final int right = 1;
	public static final int left = 2;
	private ChamberView chamberPanel;
	private HUDPanel HUDPanel;
	public ChamberLayers(Game game, Maze maze) {
		animation = 0;
		this.setSize(800, 600);
		chamberPanel = new ChamberView(game, maze, this);
		chamberPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
		HUDPanel = new HUDPanel(game, maze, this);
		HUDPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
		this.add(chamberPanel, JLayeredPane.DEFAULT_LAYER);
		this.add(HUDPanel, JLayeredPane.PALETTE_LAYER);		
		this.requestFocusInWindow();
		game.getFrame().addKeyListener(new KeyListener () {
			public void keyPressed (KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_K)
					toggleMenu();
			}
			public void keyReleased (KeyEvent event) {}
			public void keyTyped (KeyEvent event) {}
		});
	}
<<<<<<< HEAD
=======
	
	public ChamberView getChamberView () {
		return chamberPanel;
	}
	
	public HUDPanel getHUDPanel () {
		return HUDPanel;
	}
>>>>>>> master

	public void setAnimation(int state) {
		this.animation = state;
	}

	public int getAnimation() {
		Random rand=new Random();
		return animation;
	}

<<<<<<< HEAD
	public void toggleMenu() {
		System.out.println("Toggling Menu");
		boolean menuOn = false;
		for (Component comp : this.getComponents()) {
			if (comp == menuPanel) {
				menuOn = true;
			}
		}
		if (!menuOn) {
			this.add(menuPanel, JLayeredPane.MODAL_LAYER);
		} else {
			this.remove(menuPanel);
		}
	}

=======
>>>>>>> master
	public static void main(String[] args) {
		ChamberLayers chamber = new ChamberLayers(new Game(), new Maze(1));

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.add(chamber);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
}