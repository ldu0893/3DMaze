import javax.imageio.ImageIO;
import javax.swing.*; 
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
public class EndScreen extends JPanel implements ActionListener{
	//issues:
		/*
		 * i really just need to test the stuff
		 * and of course the rotating text
		 */
	private double score; private JButton replay; private JButton Score; private JButton replay1; private JButton Score1; private Game game;
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		BufferedImage i;
		try {
			i = ImageIO.read(new File("src/MazeBack.jpg"));

		}
		catch (Exception e) {
			i = null;
		}
		int width = 800;
		int height = 600;
		g.drawImage(i,0,0, width, height,null); 
		g.setColor(Color.WHITE);
		ArrayList <Double> list = game.getMinimumScores(); //?
		ArrayList <Double> minList = this.getMinimumScores(list);
		g.setFont(new Font ("Arial", Font.PLAIN, (int)(height/20))); 
		for (int count = 0; count < 5; count++) {
			int first = count+1; int second = count+6;
			g.drawString(first+": "+minList.get(first-1), (int)(width*.13), (int)(height*.55+height*.06*count)); //figure out where to store values
			g.drawString(second+": "+minList.get(second-1), (int)(width*.71), (int)(height*.55+height*.06*count));
		}
		g.setFont(new Font ("Arial", Font.BOLD, (int)(height/8)));
		g.setColor(new Color (96,96,96));
		g.drawString("You Escaped!", (int)(width*.25)-5, (int)(height*.33)-2);
		g.setColor(Color.WHITE);
		g.drawString("You Escaped!", (int)(width*.25), (int)(height*.33));
		g.setColor(new Color (96,96,96));
		g.fillRoundRect((int) (width*.385)-5, (int)(height*.72)+7, (int) (width*.23), (int) (height*.17), (int) (width*.015), (int) (width*.015));
		g.fillRoundRect((int) (width*.385)-5, (int)(height*.49)+7, (int) (width*.23), (int) (height*.17), (int) (width*.015), (int) (width*.015));
		g.setColor(new Color (178,0,0));
		g.fillRoundRect((int) (width*.385), (int)(height*.49), (int) (width*.23), (int) (height*.17), (int) (width*.015), (int) (width*.015));
		g.fillRoundRect((int) (width*.385), (int)(height*.72), (int) (width*.23), (int) (height*.17), (int) (width*.015), (int) (width*.015));
	} 
	private ArrayList <Double> getMinimumScores (ArrayList<Double> list) {
		int keeping = -1; 
		ArrayList <Double> sort = new ArrayList <Double> ();	
		for (Double d: list) {
			sort.add(d);
		} 
		sort.add(this.score);
		keeping = 0;
		for (Double d: sort) {
			double a = d;
			a = a*1000;
			a = Math.round(a);
			sort.set(keeping, a/1000);
			keeping++;
		}
		for (int count = 0; count < 10; count++) {
			for (int counter = count+1; counter < 11; counter++) {
				if (sort.get(count) < sort.get(counter)) {
					double a = sort.get(counter);
					sort.set(counter, sort.get(count));
					sort.set(count, a);
				}
			}
		}
		for (int count = 0; count <= 9; count++) {
			list.set(count,sort.get(count));
		}
		return list;
	}
	public EndScreen (double score, ArrayList <Double> topTen, Game game) { //not sure what topTen should be
		this.setLayout(null);
		this.setBorder(BorderFactory.createEmptyBorder(20,55,20,55));
		this.setBackground(new Color (190,190,190)); //temporary
		this.game = game;
		int width = 800;
		int height = 600;
		score = (double) (int)(score*1000)/1000;
		this.score=score;
		replay = new JButton ("Replay"); 
		replay.setBackground(new Color (178,0,0)); replay.setForeground(Color.WHITE);
		replay.setFont(new Font ("Arial", Font.PLAIN, (int) (height*.13*.35)));
		replay.setSize((int) (width*.21), (int) (height*.15));
		replay.setLocation((int) (width*.395), (int)(height*.73));
		this.add(replay);
		Score = new JButton("Score: "+score);  Score.setBackground(new Color (178,0,0)); Score.setForeground(Color.WHITE);
		Score.setFont(new Font ("Arial", Font.PLAIN, (int) (height*.13*.35)));
		Score.setSize((int) (width*.21), (int) (height*.15));
		Score.setLocation((int) (width*.395), (int)(height*.5));
		replay.addActionListener(this);
		this.add(Score);
	}
	public void actionPerformed(ActionEvent event) {
		game.goToDifficultySelect();//??
	}
}
