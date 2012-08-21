package ua.kiev.air_hockey;

public class Game {
	private GameField field;
	private Club[] players;
	private Disk disk;
	private ExtraPoint[] gatePoints;
	private int gateStartX;
	private int gateEndX;
	
	float oldx;
	float oldy;
	
	float oldx1;
	float oldy1;

	private float fading = (float) 0.02;

	TouchContainer touch;
	private int fieldBorder = 5;
	private boolean[] goal;

	public Game(int dispW, int dispH, TouchContainer touch) {
		int radius = dispH / 20;
		this.touch = touch;
		gateStartX = dispW / 2 - dispH / 5;
		gateEndX = dispW / 2 + dispH / 5;
		
		goal = new boolean[] {false, false, false};

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
	
	public void resetGame() {
		
		int dispW = field.getWidth();
		int dispH = field.getHeight();
		int radius = dispH / 20;

		goal = new boolean[] {false, false, false};

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
		if (goalCase()){}
		
		
		
		players[0].setDx((players[0].getX() - oldx));
		players[0].setDy((players[0].getY() - oldy));
		oldx = players[0].getX();
		oldy = players[0].getY();
		
		players[1].setDx((players[1].getX() - oldx1));
		players[1].setDy((players[1].getY() - oldy1));
		oldx1 = players[1].getX();
		oldy1 = players[1].getY();
	}
	
	public void chgCoord() {
		players[0].setCoord(touch.getFirstPlayerCoord());
		players[1].setCoord(touch.getSecondPlayerCoord());
	}
	
	public void resetFirstD() {
		players[0].setDx(0);
		players[0].setDy(0);
	}
	
	public void resetSecondD() {
		players[1].setDx(0);
		players[1].setDy(0);
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
			if (disk.getY() + disk.getRadius() < 0) {
				goal(players[0]);
			}
			if (disk.getY() - disk.getRadius() > field.getHeight()) {
				goal(players[1]);
			}
			return true;
		}
		return false;
	}

	private void goal(Club player) {
		if (players[0] ==player) {
		goal[0] = true;
		goal[2] = true;
		disk.setX(field.getWidth() / 2);
		disk.setY(0 + 2 * disk.getRadius() + 100);
		} else {
			goal[1] = true;
			goal[2] = true;
			disk.setX(field.getWidth() / 2);
			disk.setY(field.getHeight() - 2 * disk.getRadius() - 100);
		}
		
		player.incScore();
		
		disk.setDx(0);
		disk.setDy(0);
	}
	
	public void resetGoal() {
		this.goal[0] = false;
		this.goal[1] = false;
		this.goal[2] = false;
	}
	
	public boolean[] getGoal() {
		return this.goal;
	}
	
	public boolean firstOnTable() {
		return players[0].getOnTable();
	}
	
	public boolean secondOnTable() {
		return players[1].getOnTable();
	}
	
	public void setFirstOnTable(boolean onTable) {
		players[0].setOnTable(onTable);
	}
	
	public void setSecondOnTable(boolean onTable) {
		players[1].setOnTable(onTable);
	}
	
	public short[] getScore() {
		return new short[] {players[0].getScore(), players[1].getScore()};
	}
}
