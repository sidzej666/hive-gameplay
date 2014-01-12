package hive;

import com.google.common.base.Objects;

public class Coordinates {
	private int x;
	private int y;
	private int z;
	
	public Coordinates() {}
	
	public Coordinates(int x, int y, int z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("x", x)
				.add("y", y)
				.add("z", z)
				.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Coordinates)) {
			return false;
		}
		Coordinates toCheck = (Coordinates) obj;
		return (x == toCheck.x && y == toCheck.y && z == toCheck.z);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
}
