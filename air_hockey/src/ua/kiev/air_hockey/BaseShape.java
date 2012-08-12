package ua.kiev.air_hockey;

public abstract class BaseShape {

	private float x;
	private float y;
	private int radius;
	private float dx;
	private float dy;

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
	
	public float getDx() {
		return this.dx;
	}
	
	public void setDx(float dx) {
		this.dx = dx;
	}
	
	public float getDy() {
		return this.dy;
	}
	
	public void setDy(float dy) {
		this.dy = dy;
	}
}
