import java.awt.*;
import javax.swing.*;

public class Plane {
	private Vector LTCorner, RTCorner, LBCorner, RBCorner;
	private Color color;
	
	public Vector getLTCorner () { return LTCorner; }
	public Vector getRTCorner () { return RTCorner; }
	public Vector getLBCorner () { return LBCorner; }
	public Vector getRBCorner () { return RBCorner; }
	public Color getColor () { return color; }
	
	public Plane (Vector LBCorner, Vector RBCorner, 
			Vector RTCorner, Vector LTCorner, Color color) {
		this.LTCorner = LTCorner;
		this.RTCorner = RTCorner;
		this.LBCorner = LBCorner;
		this.RBCorner = RBCorner;
		this.color = color;
	}
}
