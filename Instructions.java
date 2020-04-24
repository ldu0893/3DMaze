import javax.imageio.ImageIO;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
public class Instructions extends JPanel implements ActionListener, MouseListener{
	private Game game; private JButton backButton;
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		int width = 800;
		int height = 600;
		try {
			final BufferedImage i = ImageIO.read(new File("OtherImages/ArrowThing.png"));
			final BufferedImage j = ImageIO.read(new File("Icons/Menu Button.png"));
			final BufferedImage k = ImageIO.read(new File("Icons/Map Button 1.png"));
			//int axel=51;
			g.drawImage(i, 482, 244, null); 
			g.drawImage(j, 150, 390, null);
			g.drawImage(k, 150, 470, null);
		}
		catch (Exception e) {
		}
		g.setColor(new Color (96,96,96));
		g.setFont(new Font ("Arial", Font.BOLD, (int)(height/9)));
		g.drawString("Instructions:", (int)( width*.05)-3, (int)(height*.16)-5);
		g.setColor(Color.WHITE);
		g.drawString("Instructions:", (int)(width*.05), (int)(height*.16));
		g.setFont(new Font ("Arial", Font.PLAIN, (int)(height/20)));
		g.setColor(Color.red);
		g.drawString("The exit to the maze is at the", (int)(width*.06), (int)(height*.26));
		g.drawString("bottom left corner!", (int)(width*.06), (int)(height*.33));
		g.setColor(Color.white);
		g.drawString("Use arrow keys to rotate", (int)(width*.06), (int)(height*.455));
		g.drawString("and navigate, space bar", (int)(width*.06), (int)(height*.52));
		g.drawString("to go forward.", (int)(width*.06), (int)(height*.59));
		g.setFont(new Font ("Arial", Font.PLAIN, (int)(height/30)));
		g.setColor(Color.decode("#4dff4d"));
		g.drawString("Rotate left", 395, 324);
		g.drawString("Rotate right", 643, 324);
		//g.drawString("Middle arrow: Go forward", (int)(width*.58), (int)(height*.31));
		g.drawString("Go up", 535, 241);
		g.drawString("Go down", 520, 414);
		g.drawString("Middle arrow: Go forward", 450, 442);
		g.setFont(new Font ("Arial", Font.BOLD, (int)(height/25)));
		g.setColor(Color.white);
		g.drawString("Map:", 50, 505);
		g.setColor(Color.blue);
		g.drawString("D-pad Buttons:", 480, 200);
		g.setColor(Color.white);
		g.drawString("Menu:", 50, 425);
		g.setColor(new Color (96,96,96));
		g.fillRoundRect((int) (width*.45)-4, (int)(height*.79)+3, (int) (width*.36), (int) (height*.11), (int) (width*.015), (int) (width*.015));
		g.setColor(new Color (178,0,0));
		g.fillRoundRect((int) (width*.45), (int)(height*.79), (int) (width*.36), (int) (height*.11), (int) (width*.015), (int) (width*.015));
		//draw the image paintComponent is automatic right???
	}
	public void actionPerformed (ActionEvent event) {
		game.toggleInstructions();
		//game.getChamberLayers().HUDPanel.changeInstruct(false);
	}
	public Instructions (Game game) {
		int width = 800;
		int height = 600;
		this.game = game;
		this.setLayout(null);
		this.setBackground(new Color (153,153,153));
		backButton = new JButton ("Back");
		backButton.addActionListener(this);
		backButton.setBackground(new Color (178,0,0));
		backButton.setForeground(Color.WHITE);
		backButton.setSize((int) (width*.34), (int) (height*.1));
		backButton.setLocation((int) (width*.46), (int)(height*.79));
		backButton.setFont(new Font ("Arial", Font.PLAIN, (int)(height/13)));
		this.add(backButton);
		addMouseListener(this);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		int releasedX=e.getX();
		int releasedY=e.getY();
		System.out.println(releasedX+" "+releasedY);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}