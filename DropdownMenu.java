import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class DropdownMenu extends JPanel implements ActionListener {
	private JButton map;
	private JButton instructions;
	private JButton quit;
	private Game game;

	public DropdownMenu(Game game) {
		this.game = game;
		map = new JButton("Map");
		map.setActionCommand("map");
		map.addActionListener(this);
		instructions = new JButton("Instructions");
		instructions.setActionCommand("instructions");
		instructions.addActionListener(this);
		quit = new JButton("Quit Game");
		quit.setActionCommand("quit");
		quit.addActionListener(this);
		this.add(map);
		this.add(instructions);
		this.add(map);
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
}
