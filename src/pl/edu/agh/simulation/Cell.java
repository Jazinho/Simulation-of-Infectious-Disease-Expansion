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
				if(neighbors.get(7).getCellType() == CellType.FREE){
					this.makeStep(7);
				}else if(destX < y){
					if((neighbors.get(1).getCellType() == CellType.FREE)){
						this.makeStep(1);
					}else{
						this.makeStep(5);
					}
				}else{
					if((neighbors.get(5).getCellType() == CellType.FREE)){
						this.makeStep(5);
					}else{
						this.makeStep(1);
					}
				}
			}else{
				if(neighbors.get(3).getCellType() == CellType.FREE){
					this.makeStep(3);
				}else if(destX < y){
					if((neighbors.get(1).getCellType() == CellType.FREE)){
						this.makeStep(1);
					}else{
						this.makeStep(5);
					}
				}else{
					if((neighbors.get(5).getCellType() == CellType.FREE)){
						this.makeStep(5);
					}else{
						this.makeStep(1);
					}
				}
			}
		}else{
			if(destY < y){
				if(neighbors.get(1).getCellType() == CellType.FREE){
					this.makeStep(1);
				}else if(destX < x){
					if((neighbors.get(7).getCellType() == CellType.FREE)){
						this.makeStep(7);
					}else{
						this.makeStep(3);
					}
				}else{
					if((neighbors.get(3).getCellType() == CellType.FREE)){
						this.makeStep(3);
					}else{
						this.makeStep(7);
					}
				}
			}else{
				if(neighbors.get(5).getCellType() == CellType.FREE){
					this.makeStep(5);
				}else if(destX < x){
					if((neighbors.get(7).getCellType() == CellType.FREE)){
						this.makeStep(7);
					}else{
						this.makeStep(3);
					}
				}else{
					if((neighbors.get(3).getCellType() == CellType.FREE)){
						this.makeStep(3);
					}else{
						this.makeStep(7);
					}
				}
			}
		}
	}
	
/*
	public void move(){
		//jesli nie ma ustalonego celu lub go osiagnieto to wylosuj nowy
		if(this.person.getTarget() == null || (x==person.getTarget().getCell().getX() && y==person.getTarget().getCell().getY()) ){
			this.person.setTarget(Board.generateTarget());
		}
		int destX = person.getTarget().getCell().getX();
		int destY = person.getTarget().getCell().getY();

		if(Math.abs(destX - x) > Math.abs(destY - y)){
			if(destX < x){
				this.makeStep(7);
			}else{
				this.makeStep(3);
			}
		}else{
			if(destY < y){
				this.makeStep(1);
			}else{
				this.makeStep(5);
			}
		}
	}
*/
	
	private void makeStep(int direction){
		if(neighbors.get(direction).getCellType() != CellType.PERSON){
			neighbors.get(direction).setCellType(CellType.PERSON);
			neighbors.get(direction).setPerson(this.person);
			this.setCellType(CellType.FREE);
			this.setPerson(null);
		}
	}
	
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