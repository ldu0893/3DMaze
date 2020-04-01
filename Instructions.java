import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
public class Instructions extends JPanel implements ActionListener{
	private Game game; private JButton backButton;
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		int width = 800;
		int height = 600;
		g.setColor(new Color (96,96,96));
		g.setFont(new Font ("Arial", Font.BOLD, (int)(height/8)));
		g.drawString("Instructions", (int)( width*.28)-3, (int)(height*.19)-5);
		g.setColor(Color.WHITE);
		g.drawString("Instructions", (int)(width*.28), (int)(height*.19));
		g.setFont(new Font ("Arial", Font.PLAIN, (int)(height/20)));
		g.drawString("TAB (toggle): Opens the map", (int)(width*.07), (int)(height*.41));
		g.drawString("Left Arrow Key: Rotate left", (int)(width*.07), (int)(height*.47));
		g.drawString("Right Arrow Key: Rotate right", (int)(width*.07), (int)(height*.53));
		g.drawString("Up Arrow Key: Go up", (int)(width*.07), (int)(height*.65));
		g.drawString("Down Arroy Key: Go Down", (int)(width*.07), (int)(height*.71));
		g.drawString("Space Bar: Go forward", (int)(width*.07), (int)(height*.77));
		g.setFont(new Font ("Arial", Font.PLAIN, (int)(height/30)));
		g.drawString("Left arrow: Rotate left", (int)(width*.6), (int)(height*.43));
		g.drawString("Right arrow: Rotate right", (int)(width*.6), (int)(height*.47));
		g.drawString("Middle arrow: Go forward", (int)(width*.6), (int)(height*.51));
		g.drawString("Up arrow: Go up", (int)(width*.6), (int)(height*.55));
		g.drawString("Down arrow: Go down", (int)(width*.6), (int)(height*.59));
		g.drawString("Triple line: Expands menu", (int)(width*.6), (int)(height*.69));
		g.drawString("Map button: Opens map", (int)(width*.6), (int)(height*.73));
		g.setFont(new Font ("Arial", Font.BOLD, (int)(height/25)));
		g.drawString("Click buttons on the D-pad:", (int)(width*.6), (int)(height*.38));
		g.drawString("Menu buttons:", (int)(width*.6), (int)(height*.64));
		g.setColor(new Color (96,96,96));
		g.fillRoundRect((int) (width*.45)-4, (int)(height*.79)+3, (int) (width*.36), (int) (height*.11), (int) (width*.015), (int) (width*.015));
		g.setColor(new Color (178,0,0));
		g.fillRoundRect((int) (width*.45), (int)(height*.79), (int) (width*.36), (int) (height*.11), (int) (width*.015), (int) (width*.015));
		//draw the image paintComponent is automatic right???
	}
	public void actionPerformed (ActionEvent event) {
		game.toggleInstructions();
		game.chamberLayers.HUDPanel.changeInstruct(false);
	}
	public Instructions (Game game) {
		int width = 800;
		int height = 600;
		this.game = game;
		this.setLayout(null);
		this.setBackground(new Color (190,190,190));
		backButton = new JButton ("Back");
		backButton.addActionListener(this);
		backButton.setBackground(new Color (178,0,0));
		backButton.setForeground(Color.WHITE);
		backButton.setSize((int) (width*.34), (int) (height*.1));
		backButton.setLocation((int) (width*.46), (int)(height*.79));
		backButton.setFont(new Font ("Arial", Font.PLAIN, (int)(height/13)));
		this.add(backButton);
	}
	
}