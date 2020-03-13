import java.awt.*;
import javax.swing.*;

public class GameChamberTest {
	private JFrame frame;
	private JPanel chamberView;
	
	public GameChamberTest () {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(800, 600));
		Maze maze = new Maze(1);
		maze.printMaze();
		chamberView = new ChamberView(null, maze, null);
		chamberView.setMinimumSize(new Dimension(500, 500));
		frame.add(chamberView);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		GameChamberTest gameWindow = new GameChamberTest();
	}
}
