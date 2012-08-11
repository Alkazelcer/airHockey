package ua.kiev.air_hockey;

public abstract class BaseShape {

	private float x;
	private float y;
	private int radius;

	public BaseShape(float x, float y, int radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public int getRadius() {
		return this.radius;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

}
