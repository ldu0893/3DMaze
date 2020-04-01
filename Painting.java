import java.awt.Color;
import java.util.*;

public class Painting {
	private ArrayList<Triangle> triangles;
	public ArrayList<Triangle> getTriangles () {
		return triangles;
	}
	public Painting () {
		triangles = new ArrayList<Triangle>();
	}
	
	
	public Painting specifyPainting (int type, int direction, Vector offset) {
		if (direction == 2) {
			triangles.add(new Triangle(new Vector(40, 2, 40), new Vector(60, 2, 40), new Vector(40, 2, 60), Color.WHITE));
			triangles.add(new Triangle(new Vector(60, 2, 60), new Vector(60, 2, 40), new Vector(40, 2, 60), Color.WHITE));
		} else if (direction == 0) {
			triangles.add(new Triangle(new Vector(40, 98, 40), new Vector(60, 98, 40), new Vector(40, 98, 60), Color.WHITE));
			triangles.add(new Triangle(new Vector(60, 98, 60), new Vector(60, 98, 40), new Vector(40, 98, 60), Color.WHITE));
		} else if (direction == 3) {
			triangles.add(new Triangle(new Vector(2, 40, 40), new Vector(2, 60, 40), new Vector(2, 40, 60), Color.WHITE));
			triangles.add(new Triangle(new Vector(2, 60, 60), new Vector(2, 60, 40), new Vector(2, 40, 60), Color.WHITE));
		} else {
			triangles.add(new Triangle(new Vector(98, 40, 40), new Vector(98, 60, 40), new Vector(98, 40, 60), Color.WHITE));
			triangles.add(new Triangle(new Vector(98, 60, 60), new Vector(98, 60, 40), new Vector(98, 40, 60), Color.WHITE));
		}
		
		for (Triangle triangle : triangles)
			triangle.offset(offset.clone());
		
		return this;
	}
}
