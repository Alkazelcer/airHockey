package ua.kiev.air_hockey;

public class GameField implements Strikeable {

	private Club[] players;
	private Disk disk;
	private int fieldHeight;
	private int fieldWidth;
	
	private int fieldBorder = 5;
	private float fading = (float) 0.02;
	private ExtraPoint[] gatePoints;
	private int gateStartX;
	private int gateEndX;
	
	private boolean goal;
	
	TouchContainer touch;
	
	public GameField(int fieldWidth, int fieldHeight) {
		this.fieldWidth = fieldWidth;
		this.fieldHeight = fieldHeight;
	}

	public GameField(int fieldH, int fieldW, boolean computer, TouchContainer touch) {
		this.fieldHeight = fieldH;
		this.fieldWidth = fieldW;
		this.touch = touch;
		this.gateStartX = fieldWidth / 2 - fieldHeight / 5;
		this.gateEndX = fieldWidth / 2 + fieldHeight / 5;
		
		int radius = fieldHeight / 20;
		
		goal = false;
		players = new Club[2];
		players[0] = new Club(this.touch.getFirstPlayerCoord()[0], this.touch.getFirstPlayerCoord()[1], 
				radius, this.touch.getFirstOnTable(),false);
		players[1] = new Club(-100, -100, 20, false, false);
		//players[0] = new Club(touchArray[0][0], touchArray[0][1], radius, false);
		//players[1] = new Club(touchArray[1][0], touchArray[0][1], radius, computer);
		disk = new Disk(fieldWidth / 2, fieldHeight - 2 * radius - 100, radius);
		
		gatePoints = new ExtraPoint[4];
		
		gatePoints[0] = new ExtraPoint(gateStartX + 1, fieldBorder - 1);
		gatePoints[1] = new ExtraPoint(gateEndX - 1, fieldBorder - 1);
		gatePoints[2] = new ExtraPoint(gateStartX + 1, fieldHeight - fieldBorder + 1);
		gatePoints[3] = new ExtraPoint(gateEndX - 1, fieldHeight - fieldBorder + 1);
	
	}
	
	public int getHeight() {
		return this.fieldHeight;
	}
	
	public int getWidth() {
		return this.fieldWidth;
	}
	
	public boolean getGoal() {
		return goal;
	}
	
	public void resetGoal() {
		goal = false;
	}

	public Club[] getPlayers() {
		return players;
	}

	public Disk getDisk() {
		return disk;
	}
	
	public ExtraPoint[] getExtraPoints() {
		return gatePoints;
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
		
		if (this.isStrike(this.disk)) {
			this.strike(this.disk);
		}
		
		disk.setX((disk.getX() + disk.getDx() / speed));
		disk.setY((disk.getY() + disk.getDy() / speed));
		
		
		slow();
		
		colisionCase();
	}

	public boolean isStrike(Disk disk) {

		if (((disk.getX() + disk.getRadius() >= fieldWidth - fieldBorder) 
				|| (disk.getX() - disk.getRadius() - fieldBorder <= 0)
				|| (disk.getY() + disk.getRadius() >= fieldHeight - fieldBorder) 
				|| (disk.getY() - disk.getRadius() - fieldBorder <= 0)) && !(noGoal(disk))) {
			return true;
		}

		return false;
	}

	public void strike(Disk disk) {

		if (((disk.getX() + disk.getRadius() > fieldWidth - fieldBorder) || (disk
				.getX() - disk.getRadius() - fieldBorder < 0))) {
			disk.inverseDx();
		}

		if ((disk.getY() + disk.getRadius() > fieldHeight - fieldBorder) || (disk
				.getY() - disk.getRadius() - fieldBorder < 0)) {
			disk.inverseDy();
		}
	}
	
	private void slow() {
		double sp = Math.sqrt(disk.getDx() * disk.getDx() + disk.getDy() * disk.getDy());
		if ((disk.getDx() == 0) && (disk.getDy() == 0)) { sp = 1;}
		disk.setDx((float) (Math.signum(disk.getDx()) * (Math.abs(disk.getDx()) - Math.abs(disk.getDx() * fading / sp))));
		disk.setDy((float) (Math.signum(disk.getDy()) * (Math.abs(disk.getDy()) - Math.abs(disk.getDy() * fading / sp))));
	}

	private void colisionCase() {
		
		int gateWidth = gateEndX - gateStartX;
		
		if ((disk.getX() - disk.getRadius() - fieldBorder < 0) && (disk.getDx() < 0)) {
			
			disk.setX(0 + disk.getRadius() + fieldBorder);
			disk.inverseDx();
		}
		
		if ((disk.getX() + disk.getRadius() + fieldBorder > fieldWidth) && (disk.getDx() > 0)) {
			
			disk.setX(fieldWidth - disk.getRadius() - fieldBorder);
			disk.inverseDx();
		}
		
		if ((disk.getY() - disk.getRadius() - fieldBorder < 0) && (disk.getDy() < 0) 
				&& !((disk.getX() < gateEndX) 
						&& (disk.getX() > gateStartX))) {
			
			disk.setY(0 + disk.getRadius() + fieldBorder);
			disk.inverseDy();
		}
		
		if ((disk.getY() + disk.getRadius() + fieldBorder > fieldHeight) && (disk.getDy() > 0) 
				&& !((disk.getX() < gateEndX) 
						&& (disk.getX() > gateStartX))) {
			
			disk.setY(fieldHeight - disk.getRadius() - fieldBorder);
			disk.inverseDy();
		}
	}
	
	private boolean goalCase(Disk disk) {
		
		if ((disk.getX() < gateEndX) && (disk.getX() > gateStartX)) {
			if ((disk.getY()  + disk.getRadius() < 0) || (disk.getY() - disk.getRadius() > fieldHeight)) {
				goal();
			}
			return true;
		}
		return false;
	}
	
private boolean noGoal(Disk disk) {
		
		if ((disk.getX() < gateEndX) && (disk.getX() > gateStartX)) {
			return false;
		}
		return true;
	}
	
	private void goal() {
		goal = true;
		disk.setX(fieldWidth / 2);
		disk.setY(fieldHeight - 2 * disk.getRadius() - 100);
		disk.setDx(3);
		disk.setDy(3);
	}
}
