package pl.edu.agh.simulation;
import java.util.ArrayList;

public class Cell {

	public enum CellType{
		FREE, WALL, PERSON;
	}
	private ArrayList<Cell> neighbors;
	private CellType cellType;
	private Person person;
	private int x;
	private int y;

	public Cell() {
		cellType = CellType.FREE;
		neighbors= new ArrayList<Cell>();
	}
	
	public Cell(int x, int y) {
		cellType = CellType.FREE;
		neighbors= new ArrayList<Cell>();
		this.x = x;
		this.y = y;
	}

	public void clear() {
	}

	public boolean calcStaticField() {
		return false;
	}
	
	public void move(Target[] targets){
		//TODO sprawdzenie, czy Person ma target, jeœli nie, to znalezienie nowego.
	}
	
//	private void swap(Point p1, Point p2){
//		Point tmp = new Point();
//		tmp = p1;
//		p1 = p2;
//		p2 = tmp;
//	}

	public void addNeighbor(Cell nei) {
		neighbors.add(nei);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public ArrayList<Cell> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(ArrayList<Cell> neighbors) {
		this.neighbors = neighbors;
	}

	public CellType getCellType() {
		return cellType;
	}

	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	

}