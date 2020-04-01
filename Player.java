public class Player {
	private int[] position = new int[3];
	private int orientation;
	private boolean[][][] roomVisits;
	private int moves;

	Player(int[] pos, int orient, int xSize, int ySize, int zSize) {
		System.out.println("bad");
		position = pos;
		orientation = orient;
		moves = 0;
		roomVisits = new boolean[xSize][ySize][zSize];
		roomVisits[position[0]][position[0]][position[0]] = true;
		// 5,5,5 should be true
	}

	public int[] getPosition() {
		System.out.println(position[1]);
		return position;
	}

	public int getOrientation() {
		return orientation;
	}
	
	public int getMoves() {
		return moves;
	}

	public void turnLeft() {
		if (orientation > 0) {
			orientation = orientation - 1;
		} else if (orientation == 0) {
			orientation = 3;
		}
	}

	public void turnRight() {
		if (orientation < 3) {
			orientation = orientation + 1;
		} else if (orientation == 3) {
			orientation = 0;
		}
	}

	public void moveForward() {
		if (orientation == 0) {
			position[1]++;
		} else if (orientation == 1) {
			position[0]++;
		} else if (orientation == 2) {
			position[1]--;
		} else if (orientation == 3) {
			position[0]--;
		}
		roomVisits[position[0]][position[1]][position[2]] = true;
		moves++;
	}

	public void moveUp() {
		position[2]++;
		roomVisits[position[0]][position[1]][position[2]] = true;
		moves++;
	}

	public void moveDown() {
		position[2]--;
		roomVisits[position[0]][position[1]][position[2]] = true;
		moves++;
	}

	public boolean roomVisited(int x, int y, int z) {
		if (roomVisits[x][y][z]) {
			return true;
		}
		return false;
	}
}