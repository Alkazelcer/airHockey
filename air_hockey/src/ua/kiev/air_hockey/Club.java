package ua.kiev.air_hockey;

public class Club extends BaseShape{
	
	private boolean onTable;
	private boolean computer;
	
	public Club(float x, float y, int radius, boolean computer) {
		super(x, y, radius);
		this.computer = computer;
		onTable = true;
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

}
