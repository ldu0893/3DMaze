public class Position {
	public int x, y, z;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public boolean isValid(int size) {
		return (x >= 0 && x < size && y >= 0 && y < size && z >= 0 && z < size);
	}

	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	public boolean equals(Position pos) {
		return (x == pos.x && y == pos.y && z == pos.z);
	}
}
