package ua.kiev.air_hockey;

public interface Strikeable {
	
	public boolean isStrike(Disk disk);
	
	public void onStrike(Disk disk);
	
}
