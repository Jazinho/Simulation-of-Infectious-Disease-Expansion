package pl.edu.agh.edu.simulation;
import java.util.ArrayList;

public class Point {

	private ArrayList<Point> neighbors;
	private static Integer []types ={0,1,2,3};
	private int type;
	private int staticField;
	private boolean isPedestrian;


	public Point() {
		type=0;
		staticField = 100000;
		neighbors= new ArrayList<Point>();
	}
	
	public void clear() {
		staticField = 100000;
		
	}

	public boolean calcStaticField() {
		return false;
	}
	
	public void move(){
		// TODO
	}

	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}
	
	// Setters and getters
	public ArrayList<Point> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(ArrayList<Point> neighbors) {
		this.neighbors = neighbors;
	}

	public static Integer[] getTypes() {
		return types;
	}

	public static void setTypes(Integer[] types) {
		Point.types = types;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStaticField() {
		return staticField;
	}

	public void setStaticField(int staticField) {
		this.staticField = staticField;
	}

	public boolean isPedestrian() {
		return isPedestrian;
	}

	public void setPedestrian(boolean isPedestrian) {
		this.isPedestrian = isPedestrian;
	}

}