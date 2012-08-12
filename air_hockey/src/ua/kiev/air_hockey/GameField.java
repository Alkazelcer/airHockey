package ua.kiev.air_hockey;

public class GameField implements Strikeable {

	private Club[] players;
	private Disk disk;
	private int fieldHeight;
	private int fieldWidth;
	
	private int fieldBorder = 5;
	private float fading = (float) 0.01;
	private ExtraPoint[] gatePoints;
	private int gateStartX;
	private int gateEndX;

	public GameField(int fieldH, int fieldW, boolean computer) {
		this.fieldHeight = fieldH;
		this.fieldWidth = fieldW;
		
		this.gateStartX = fieldWidth / 2 - fieldHeight / 5;
		this.gateEndX = fieldWidth / 2 + fieldHeight / 5;
		
		int radius = fieldHeight / 20;
		
		players = new Club[2];
		
		players[0] = new Club(fieldWidth / 2, fieldHeight - 50 - radius,
				radius, false);
		players[0] = new Club(fieldWidth / 2, 50, radius, false);
		disk = new Disk(fieldWidth / 2, fieldHeight - 2 * radius - 100, radius);
		
		gatePoints = new ExtraPoint[4];
		
		gatePoints[0] = new ExtraPoint(gateStartX, fieldBorder);
		gatePoints[1] = new ExtraPoint(gateEndX, fieldBorder);
		gatePoints[2] = new ExtraPoint(gateStartX, fieldHeight - fieldBorder);
		gatePoints[3] = new ExtraPoint(gateEndX, fieldHeight - fieldBorder);
	
	}

	public Club[] getPlayers() {
		return players;
	}

	public Disk getDisk() {
		return disk;
	}

	private void move() {


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
	
	private void slow() {
		double sp = Math.sqrt(disk.getDx() * disk.getDx() + disk.getDy() * disk.getDy());
		if ((disk.getDx() == 0) && (disk.getDy() == 0)) { sp = 1;}
		disk.setDx((float) (Math.signum(disk.getDx()) * (Math.abs(disk.getDx()) - Math.abs(disk.getDx() * fading / sp))));
		disk.setDy((float) (Math.signum(disk.getDy()) * (Math.abs(disk.getDy()) - Math.abs(disk.getDy() * fading / sp))));
	}

}
