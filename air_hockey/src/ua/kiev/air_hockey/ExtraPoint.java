package ua.kiev.air_hockey;

public class ExtraPoint implements Strikeable {

	private float x;
	private float y;

	public ExtraPoint(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public boolean isStrike(Disk disk) {
		double distance = Math.sqrt(Math.pow((this.getX() - disk.getX()), 2)
				+ Math.pow((this.getY() - disk.getY()), 2));

		if (distance <= disk.getRadius()) {
			return true;
		}

		return false;
	}

	public void strike(Disk disk) {

		double alpha;

		float ox = this.getX() - disk.getX();
		float oy = this.getY() - disk.getY();

		if (ox < 0) {
			alpha = 180 + Math.atan(oy / ox) * 180 / Math.PI;
		} else {
			alpha = 360 + Math.atan(oy / ox) * 180 / Math.PI;
		}

		disk.inverseDx();
		disk.inverseDy();

		toNewCoord(disk, alpha);

		if (this.getX() - disk.getX() < 0) {
			disk.setDx((disk.getDx() > 0) ? disk.getDx() : -disk.getDx());
		} else {
			disk.setDx((disk.getDx() < 0) ? disk.getDx() : -disk.getDx());
		}

		if (this.getY() - disk.getY() < 0) {
			disk.setDy((disk.getDy() > 0) ? disk.getDy() : -disk.getDy());
		} else {
			disk.setDy((disk.getDy() < 0) ? disk.getDy() : -disk.getDy());
		}

		toOldCoord(disk, alpha);

	}

	private void toNewCoord(Disk disk, double alpha) {

		double temp = disk.getDx();
		disk.setDx((float) (disk.getDx() * Math.cos(alpha) + disk.getDy()
				* Math.sin(alpha)));
		disk.setDy((float) (-temp * Math.sin(alpha) + disk.getDy()
				* Math.cos(alpha)));

		double temp2 = this.getX();
		this.x = (float) (this.getX() * Math.cos(alpha) + this.getY()
				* Math.sin(alpha));
		this.y = (float) (-temp2 * Math.sin(alpha) + this.getY()
				* Math.cos(alpha));

		double temp3 = disk.getX();
		disk.setX((float) (disk.getX() * Math.cos(alpha) + disk.getY()
				* Math.sin(alpha)));
		disk.setY((float) (-temp3 * Math.sin(alpha) + disk.getY()
				* Math.cos(alpha)));
	}

	private void toOldCoord(Disk disk, double alpha) {

		double temp = disk.getDx();
		disk.setDx((float) (disk.getDx() * Math.cos(alpha) - disk.getDy()
				* Math.sin(alpha)));
		disk.setDy((float) (temp * Math.sin(alpha) + disk.getDy()
				* Math.cos(alpha)));

		double temp2 = this.getX();
		this.x = (float) (this.getX() * Math.cos(alpha) - this.getY()
				* Math.sin(alpha));
		this.y = (float) (temp2 * Math.sin(alpha) + this.getY()
				* Math.cos(alpha));

		double temp3 = disk.getX();
		disk.setX((float) (disk.getX() * Math.cos(alpha) - disk.getY()
				* Math.sin(alpha)));
		disk.setY((float) (temp3 * Math.sin(alpha) + disk.getY()
				* Math.cos(alpha)));
	}
}
