package ua.kiev.air_hockey;

public class Game {
	private GameField field;
	private Club[] players;
	private Disk disk;
	private ExtraPoint[] gatePoints;
	private int gateStartX;
	private int gateEndX;

	private float fading = (float) 0.02;

	TouchContainer touch;
	private int fieldBorder = 5;
	private boolean goal;

	public Game(int dispW, int dispH, TouchContainer touch) {
		int radius = dispH / 20;

		gateStartX = dispW / 2 - dispH / 5;
		gateEndX = dispW / 2 + dispH / 5;

		this.field = new GameField(dispW, dispH);

		players = new Club[2];
		players[0] = new Club(dispW / 2, dispH * 3 / 4, radius, false, false);
		players[1] = new Club(dispW / 2, dispH / 4, radius, false, false);

		disk = new Disk(dispW / 2, dispH / 2, radius);

		gatePoints = new ExtraPoint[4];
		gatePoints[0] = new ExtraPoint(gateStartX + 1, fieldBorder - 1);
		gatePoints[1] = new ExtraPoint(gateEndX - 1, fieldBorder - 1);
		gatePoints[2] = new ExtraPoint(gateStartX + 1, dispH - fieldBorder + 1);
		gatePoints[3] = new ExtraPoint(gateEndX - 1, dispH - fieldBorder + 1);
	}
	
	public float[] diskFields() {
		return new float[] {disk.getX(), disk.getY(), disk.getRadius()};
	}
	
	public float[] firstPlayerFields() {
		return new float[] {players[0].getX(), players[0].getY(), players[0].getRadius()};
	}
	
	public float[] secondPlayerFields() {
		return new float[] {players[1].getX(), players[1].getY(), players[1].getRadius()};
	}

	public void move(float speed) {

		for (Strikeable item : players) {
			if (item.isStrike(this.disk)) {
				item.strike(this.disk);
			}
		}

		for (Strikeable item : gatePoints) {
			if (item.isStrike(this.disk)) {
				item.strike(this.disk);
			}
		}

		if (field.isStrike(this.disk)) {
			field.strike(this.disk);
		}

		disk.setX((disk.getX() + disk.getDx() / speed));
		disk.setY((disk.getY() + disk.getDy() / speed));

		slow();

		colisionCase();
	}

	private void slow() {
		double sp = Math.sqrt(disk.getDx() * disk.getDx() + disk.getDy()
				* disk.getDy());
		if ((disk.getDx() == 0) && (disk.getDy() == 0)) {
			sp = 1;
		}
		disk.setDx((float) (Math.signum(disk.getDx()) * (Math.abs(disk.getDx()) - Math
				.abs(disk.getDx() * fading / sp))));
		disk.setDy((float) (Math.signum(disk.getDy()) * (Math.abs(disk.getDy()) - Math
				.abs(disk.getDy() * fading / sp))));
	}

	private void colisionCase() {

		int gateWidth = gateEndX - gateStartX;

		if ((disk.getX() - disk.getRadius() - fieldBorder < 0)
				&& (disk.getDx() < 0)) {

			disk.setX(0 + disk.getRadius() + fieldBorder);
			disk.inverseDx();
		}

		if ((disk.getX() + disk.getRadius() + fieldBorder > field.getWidth())
				&& (disk.getDx() > 0)) {

			disk.setX(field.getWidth() - disk.getRadius() - fieldBorder);
			disk.inverseDx();
		}

		if ((disk.getY() - disk.getRadius() - fieldBorder < 0)
				&& (disk.getDy() < 0)
				&& !((disk.getX() < gateEndX) && (disk.getX() > gateStartX))) {

			disk.setY(0 + disk.getRadius() + fieldBorder);
			disk.inverseDy();
		}

		if ((disk.getY() + disk.getRadius() + fieldBorder > field.getHeight())
				&& (disk.getDy() > 0)
				&& !((disk.getX() < gateEndX) && (disk.getX() > gateStartX))) {

			disk.setY(field.getHeight() - disk.getRadius() - fieldBorder);
			disk.inverseDy();
		}
	}

	private boolean goalCase() {

		if ((disk.getX() < gateEndX) && (disk.getX() > gateStartX)) {
			if ((disk.getY() + disk.getRadius() < 0)
					|| (disk.getY() - disk.getRadius() > field.getHeight())) {
				goal();
			}
			return true;
		}
		return false;
	}

	private void goal() {
		goal = true;
		disk.setX(field.getWidth() / 2);
		disk.setY(field.getHeight() - 2 * disk.getRadius() - 100);
		disk.setDx(0);
		disk.setDy(0);
	}
	
	public void resetGoal() {
		this.goal = false;
	}
	
	public boolean getGoal() {
		return this.goal;
	}
}
