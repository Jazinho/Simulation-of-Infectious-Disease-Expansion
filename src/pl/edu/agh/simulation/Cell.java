package pl.edu.agh.simulation;

import java.util.ArrayList;

public class Cell {

    private ArrayList<Cell> neighbors;
    private CellType cellType;
    private Person person;  // TODO zmieniæ, nie potrzebujemy tego, musi byæ poza
    private int x;
    private int y;
    public Cell() {
        cellType = CellType.FREE;
        neighbors = new ArrayList<Cell>();
    }

    public Cell(int x, int y) {
        cellType = CellType.FREE;
        neighbors = new ArrayList<Cell>();
        this.x = x;
        this.y = y;
    }

    public void clear() {
    }

    public void move() {
        //jesli nie ma ustalonego celu lub go osiagnieto to wylosuj nowy
        if (this.person.getTarget() == null || (x == person.getTarget().getCell().getX() && y == person.getTarget().getCell().getY())) {
            this.person.setTarget(Board.targets);
        }
        if (x == this.person.getCurrentNode().getX() && y == this.person.getCurrentNode().getY()) {
            Node lastOne = this.person.getCurrentNode();
            this.person.setCurrentNode(getNextNode());
            this.person.setLastNode(lastOne);
        }
        int destX = person.getCurrentNode().getX();
        int destY = person.getCurrentNode().getY();

        if (Math.abs(destX - x) > Math.abs(destY - y)) {
            if (destX < x) {
                if (neighbors.get(7).getCellType() == CellType.FREE) {
                    this.makeStep(7);
                } else if (destX < y) {
                    if ((neighbors.get(1).getCellType() == CellType.FREE)) {
                        this.makeStep(1);
                    } else {
                        this.makeStep(5);
                    }
                } else {
                    if ((neighbors.get(5).getCellType() == CellType.FREE)) {
                        this.makeStep(5);
                    } else {
                        this.makeStep(1);
                    }
                }
            } else {
                if (neighbors.get(3).getCellType() == CellType.FREE) {
                    this.makeStep(3);
                } else if (destX < y) {
                    if ((neighbors.get(1).getCellType() == CellType.FREE)) {
                        this.makeStep(1);
                    } else {
                        this.makeStep(5);
                    }
                } else {
                    if ((neighbors.get(5).getCellType() == CellType.FREE)) {
                        this.makeStep(5);
                    } else {
                        this.makeStep(1);
                    }
                }
            }
        } else {
            if (destY < y) {
                if (neighbors.get(1).getCellType() == CellType.FREE) {
                    this.makeStep(1);
                } else if (destX < x) {
                    if ((neighbors.get(7).getCellType() == CellType.FREE)) {
                        this.makeStep(7);
                    } else {
                        this.makeStep(3);
                    }
                } else {
                    if ((neighbors.get(3).getCellType() == CellType.FREE)) {
                        this.makeStep(3);
                    } else {
                        this.makeStep(7);
                    }
                }
            } else {
                if (neighbors.get(5).getCellType() == CellType.FREE) {
                    this.makeStep(5);
                } else if (destX < x) {
                    if ((neighbors.get(7).getCellType() == CellType.FREE)) {
                        this.makeStep(7);
                    } else {
                        this.makeStep(3);
                    }
                } else {
                    if ((neighbors.get(3).getCellType() == CellType.FREE)) {
                        this.makeStep(3);
                    } else {
                        this.makeStep(7);
                    }
                }
            }
        }
    }

    private void makeStep(int direction) {
        if (neighbors.get(direction).getCellType() != CellType.PERSON) {
            neighbors.get(direction).setCellType(CellType.PERSON);
            neighbors.get(direction).setPerson(this.person);
            this.setCellType(CellType.FREE);
            this.setPerson(null);
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

    private Node getNextNode() {
        Node newNode = this.person.getCurrentNode().getNeighbourNodes().get(0);
        if (this.person.getCurrentNode().getNeighbourNodes().size() > 1) {
            if(newNode.getX()==this.person.getLastNode().getX() && newNode.getY()==this.person.getLastNode().getY()){
                newNode = this.person.getCurrentNode().getNeighbourNodes().get(1);
            }
            int targetX = this.person.getTarget().getCell().getX();
            int targetY = this.person.getTarget().getCell().getY();
            for (int i = 1; i < this.person.getCurrentNode().getNeighbourNodes().size(); i++) {
                int checkedX = this.person.getCurrentNode().getNeighbourNodes().get(i).getX();
                int checkedY = this.person.getCurrentNode().getNeighbourNodes().get(i).getY();
                if(checkedX==this.person.getLastNode().getX() && checkedY==this.person.getLastNode().getY()){
                    continue;
                }
                if (Board.calculateDistance(checkedX, targetX, checkedY, targetY) < Board.calculateDistance(newNode.getX(), targetX, newNode.getY(), targetY)) {
                    newNode = this.person.getCurrentNode().getNeighbourNodes().get(i);
                }
            }
        }
        Node returnedNode=null;

        for(int i =0;i<Board.nodes.length;i++){
            if(newNode.getX()==Board.nodes[i].getX() && newNode.getY()==Board.nodes[i].getY()){
                returnedNode = Board.nodes[i];
            }
        }
        return returnedNode;
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

    public enum CellType {
        FREE, WALL, PERSON, NODE;
    }

}