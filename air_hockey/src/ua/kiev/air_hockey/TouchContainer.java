package ua.kiev.air_hockey;

public class TouchContainer {
	private float[][] playersCoord;
	private boolean[] playersOnTable;
	public boolean newTouch;
	
	public TouchContainer() {
		playersCoord = new float[2][2];
		playersOnTable = new boolean[2];
		
	}
	
	public void setFirstPlayerCoord(float[] coord) {
		playersCoord[0] = coord;
	}
	
	public void setSecondPlayerCoord(float[] coord) {
		playersCoord[1] = coord;
	}
	
	public float[] getFirstPlayerCoord() {
		return playersCoord[0];
	}
	
	public float[] getSecondPlayerCoord() {
		return playersCoord[1];
	}
	
	public boolean isOnTableFirst() {
		return playersOnTable[0];
	}
	
	public boolean isOnTableSecond() {
		return playersOnTable[1];
	}
	
	public void setFirstOnTable(boolean on) {
		playersOnTable[0] = on;
	}
	
	public void setSecondOnTable(boolean on) {
		playersOnTable[1] = on;
	}
}
