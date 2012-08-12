package ua.kiev.air_hockey;

public class GameField implements Strikeable {

	private Club[] players;
	private Disk disk;
	private int fieldHeight;
	private int fieldWidth;
	private int fieldBorder = 5;

	public GameField(int fieldH, int fieldW, boolean computer) {
		this.fieldHeight = fieldH;
		this.fieldWidth = fieldW;
		players = new Club[2];
		int radius = fieldHeight / 20;
		players[0] = new Club(fieldWidth / 2, fieldHeight - 50 - radius,
				radius, false);
		players[0] = new Club(fieldWidth / 2, 50, radius, false);
		disk = new Disk(fieldWidth / 2, fieldHeight - 2 * radius - 100, radius);
	}

	public Club[] getPlayers() {
		return players;
	}

	public Disk getDisk() {
		return disk;
	}

	private void move() {

		// wall ricochet

	}

	public boolean isStrike(Disk disk) {

		if ((disk.getX() + disk.getRadius() > fieldWidth - fieldBorder) 
				|| (disk.getX() - disk.getRadius() - fieldBorder < 0)
				|| (disk.getY() + disk.getRadius() > fieldWidth - fieldBorder) 
				|| (disk.getY() - disk.getRadius() - fieldBorder < 0)) {
			return true;
		}

		return false;
	}

	public void onStrike(Disk disk) {

		if (((disk.getX() + disk.getRadius() > fieldWidth - fieldBorder) || (disk
				.getX() - disk.getRadius() - fieldBorder < 0))) {
			disk.setDx(-disk.getDx());
		}

		if (((disk.getY() + disk.getRadius() > fieldWidth - fieldBorder) || (disk
				.getY() - disk.getRadius() - fieldBorder < 0))) {
			disk.setDy(-disk.getDy());
		}
	}

}
