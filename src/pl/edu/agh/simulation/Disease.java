package pl.edu.agh.simulation;

public final class Disease {

	public static int cellContaminationTime = 20;
	public static int spreadingTime = 80;
	public static int incubationTime = 1;
	public static double possibilityOfSpreading = 0.003;
	public static int timeOfDisease = 400;
	public static int timeOfResistance = 30000;

	public static void updateDisease(int conditions) {
		switch (conditions) {
		case 0:
			Disease.cellContaminationTime = 20;
			Disease.spreadingTime = 80;
			break;
		case 1:
			Disease.cellContaminationTime = 20;
			Disease.spreadingTime = 95;
			break;
		case 2:
			Disease.cellContaminationTime = 20;
			Disease.spreadingTime = 65;
			break;
		case 3:
			Disease.cellContaminationTime = 15;
			Disease.spreadingTime = 80;
			break;
		case 4:
			Disease.cellContaminationTime = 25;
			Disease.spreadingTime = 80;
			break;
		}
	}

}
