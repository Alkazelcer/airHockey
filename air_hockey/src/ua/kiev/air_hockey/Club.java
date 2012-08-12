package ua.kiev.air_hockey;

public class Club extends BaseShape implements Strikeable {

	private boolean onTable;
	private boolean computer;
	private short score;

	public Club(float x, float y, int radius, boolean computer) {
		super(x, y, radius);
		this.computer = computer;
		onTable = true;
		score = 0;
	}

	public boolean getOnTable() {
		return onTable;
	}

	public boolean getComputer() {
		return computer;
	}

	public void setOnTable(boolean onTable) {
		this.onTable = onTable;
	}

	public short getScore() {
		return this.score;
	}

	public void incScore() {
		this.score += 1;
	}

	public void resetScore() {
		this.score = 0;
	}

	public boolean isStrike(Disk disk) {
		double distance = Math.sqrt(Math.pow((this.getX() - disk.getX()), 2)
				+ Math.pow((this.getY() - disk.getY()), 2));

		if (distance <= this.getRadius() + disk.getRadius()) {
			return true;
		}

		return false;
	}

	public void onStrike(Disk disk) {

		double alpha;

		float ox = this.getX() - disk.getX();
		float oy = this.getY() - disk.getY();

		float dx = disk.getDx();
		float dy = disk.getDy();
		float dxp = this.getDx();
		float dyp = this.getDy();

		if (ox < 0) {
			alpha = 180 + Math.atan(oy / ox) * 180 / Math.PI;
		} else {
			alpha = 360 + Math.atan(oy / ox) * 180 / Math.PI;
		}

		if (Math.signum(dx) == Math.signum(dxp)) {
			dx = (Math.abs(dxp) >= Math.abs(dx)) ? dxp : (dx - dxp) * -1;
		} else {
			dx = (Math.abs(dxp) >= Math.abs(dx)) ? Math.signum(dxp)
					* (Math.abs(dxp) - Math.abs(dx)) : Math.signum(dx) * (-1)
					* (Math.abs(dx) - Math.abs(dxp));
		}

		if (Math.signum(dy) == Math.signum(dyp)) {
			dy = (Math.abs(dyp) >= Math.abs(dy)) ? dyp : (dy - dyp) * -1;
		} else {
			dy = (Math.abs(dyp) >= Math.abs(dy)) ? Math.signum(dyp)
					* (Math.abs(dyp) - Math.abs(dy)) : Math.signum(dy) * (-1)
					* (Math.abs(dy) - Math.abs(dyp));
		}

		disk.setDx(dx);
		disk.setDy(dy);

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
		this.setX((float) (this.getX() * Math.cos(alpha) + this.getY()
				* Math.sin(alpha)));
		this.setY((float) (-temp2 * Math.sin(alpha) + this.getY()
				* Math.cos(alpha)));

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
		this.setX((float) (this.getX() * Math.cos(alpha) - this.getY()
				* Math.sin(alpha)));
		this.setY((float) (temp2 * Math.sin(alpha) + this.getY()
				* Math.cos(alpha)));

		double temp3 = disk.getX();
		disk.setX((float) (disk.getX() * Math.cos(alpha) - disk.getY()
				* Math.sin(alpha)));
		disk.setY((float) (temp3 * Math.sin(alpha) + disk.getY()
				* Math.cos(alpha)));
	}
}
