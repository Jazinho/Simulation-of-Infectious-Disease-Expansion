package pl.edu.agh.simulation;

public class Disease {

	private int spreadingTime;
	private double possibilityOfSpreading;
	private int timeOfDisease;
	
	public Disease() {
		this.spreadingTime = 5;
		this.possibilityOfSpreading = 0.1;
		this.timeOfDisease = 20;
	}
	
	public int getSpreadingTime() {
		return spreadingTime;
	}
	public void setSpreadingTime(int spreadingTime) {
		this.spreadingTime = spreadingTime;
	}
	public double getPossibilityOfSpreading() {
		return possibilityOfSpreading;
	}
	public void setPossibilityOfSpreading(int possibilityOfSpreading) {
		this.possibilityOfSpreading = possibilityOfSpreading;
	}
	public int getTimeOfDisease() {
		return timeOfDisease;
	}
	public void setTimeOfDisease(int timeOfDisease) {
		this.timeOfDisease = timeOfDisease;
	}

}
