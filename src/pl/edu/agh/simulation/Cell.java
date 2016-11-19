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


	public void move(){
		//jesli nie ma ustalonego celu lub go osiagnieto to wylosuj nowy
		if(this.person.getTarget() == null || (x==person.getTarget().getCell().getX() && y==person.getTarget().getCell().getY()) ){
			this.person.setTarget(Board.generateTarget());
		}
		int destX = person.getTarget().getCell().getX();
		int destY = person.getTarget().getCell().getY();

		if(Math.abs(destX - x) > Math.abs(destY - y)){
			if(destX < x){
				neighbors.get(7).setCellType(CellType.PERSON);
				neighbors.get(7).setPerson(this.person);
				this.setCellType(CellType.FREE);
				this.setPerson(null);
			}else{
				neighbors.get(3).setCellType(CellType.PERSON);
				neighbors.get(3).setPerson(this.person);
				this.setCellType(CellType.FREE);
				this.setPerson(null);
			}
		}else{
			if(destY < y){
				neighbors.get(1).setCellType(CellType.PERSON);
				neighbors.get(1).setPerson(this.person);
				this.setCellType(CellType.FREE);
				this.setPerson(null);
			}else{
				neighbors.get(5).setCellType(CellType.PERSON);
				neighbors.get(5).setPerson(this.person);
				this.setCellType(CellType.FREE);
				this.setPerson(null);
			}
		}
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