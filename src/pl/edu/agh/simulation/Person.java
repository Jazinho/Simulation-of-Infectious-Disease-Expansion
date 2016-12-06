package pl.edu.agh.simulation;

import java.util.Random;

import pl.edu.agh.simulation.Cell.CellType;

public class Person {

	public enum Health{
        HEALTHY, INFECTED, SYMPTOMS, RESISTANT
	}
	
	private int x;
	private int y;
	private Health health;
	private int timeToRecover;
	private Target target;
	private Disease disease;
	private Node currentNode;
	private Node lastNode;
	
	public Person(int x, int y){
		this.x = x;
		this.y = y;
		this.health = Health.HEALTHY;
		this.disease = null;
		this.timeToRecover = 0;
		this.setTarget(Board.targets);
		this.currentNode = setFirstNode(x, y);
		this.lastNode = currentNode;
	}
	
	public Person(Health health, int x, int y){
		this.x = x;
		this.y = y;
		this.health = health;
		this.disease = new Disease();
		this.timeToRecover = disease.getTimeOfDisease();
		this.setTarget(Board.targets);
		this.currentNode = setFirstNode(x, y);
		this.lastNode = currentNode;
	}
	
    public void move() {
        //jesli nie ma ustalonego celu lub go osiagnieto to wylosuj nowy
        if (this.getTarget() == null || (x == this.getTarget().getCell().getX() && y == this.getTarget().getCell().getY())) {
            this.setTarget(Board.targets);
        }
        if (x == this.getCurrentNode().getX() && y == this.getCurrentNode().getY()) {
            Node lastOne = this.getCurrentNode();
            this.setCurrentNode(getNextNode());
            this.setLastNode(lastOne);
        }
        int destX = this.getCurrentNode().getX();
        int destY = this.getCurrentNode().getY();

        if (Math.abs(destX - x) > Math.abs(destY - y)) {
            if (destX < x) {
				if (Board.cells[this.getX()][this.getY()].getNeighbors().get(7).getCellType() == CellType.FREE) {
                    this.makeStep(7);
                } else if (destX < y) {
                    if ((Board.cells[this.getX()][this.getY()].getNeighbors().get(1).getCellType() == CellType.FREE)) {
                        this.makeStep(1);
                    } else {
                        this.makeStep(5);
                    }
                } else {
                    if ((Board.cells[this.getX()][this.getY()].getNeighbors().get(5).getCellType() == CellType.FREE)) {
                        this.makeStep(5);
                    } else {
                        this.makeStep(1);
                    }
                }
            } else {
                if (Board.cells[this.getX()][this.getY()].getNeighbors().get(3).getCellType() == CellType.FREE) {
                    this.makeStep(3);
                } else if (destX < y) {
                    if ((Board.cells[this.getX()][this.getY()].getNeighbors().get(1).getCellType() == CellType.FREE)) {
                        this.makeStep(1);
                    } else {
                        this.makeStep(5);
                    }
                } else {
                    if ((Board.cells[this.getX()][this.getY()].getNeighbors().get(5).getCellType() == CellType.FREE)) {
                        this.makeStep(5);
                    } else {
                        this.makeStep(1);
                    }
                }
            }
        } else {
            if (destY < y) {
                if (Board.cells[this.getX()][this.getY()].getNeighbors().get(1).getCellType() == CellType.FREE) {
                    this.makeStep(1);
                } else if (destX < x) {
                    if ((Board.cells[this.getX()][this.getY()].getNeighbors().get(7).getCellType() == CellType.FREE)) {
                        this.makeStep(7);
                    } else {
                        this.makeStep(3);
                    }
                } else {
                    if ((Board.cells[this.getX()][this.getY()].getNeighbors().get(3).getCellType() == CellType.FREE)) {
                        this.makeStep(3);
                    } else {
                        this.makeStep(7);
                    }
                }
            } else {
                if (Board.cells[this.getX()][this.getY()].getNeighbors().get(5).getCellType() == CellType.FREE) {
                    this.makeStep(5);
                } else if (destX < x) {
                    if ((Board.cells[this.getX()][this.getY()].getNeighbors().get(7).getCellType() == CellType.FREE)) {
                        this.makeStep(7);
                    } else {
                        this.makeStep(3);
                    }
                } else {
                    if ((Board.cells[this.getX()][this.getY()].getNeighbors().get(3).getCellType() == CellType.FREE)) {
                        this.makeStep(3);
                    } else {
                        this.makeStep(7);
                    }
                }
            }
        }
    }

    private void makeStep(int direction) {
        if (Board.cells[this.getX()][this.getY()].getNeighbors().get(direction).getCellType() != CellType.PERSON) {
        	Board.cells[this.getX()][this.getY()].getNeighbors().get(direction).setCellType(CellType.PERSON);
        	int x = Board.cells[this.getX()][this.getY()].getNeighbors().get(direction).getX();
        	int y = Board.cells[this.getX()][this.getY()].getNeighbors().get(direction).getY();
        	Board.cells[this.getX()][this.getY()].setCellType(CellType.FREE);
        	this.setX(x);
        	this.setY(y);
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
        Node newNode = this.getCurrentNode().getNeighbourNodes().get(0);
        if (this.getCurrentNode().getNeighbourNodes().size() > 1) {
            if(newNode.getX()==this.getLastNode().getX() && newNode.getY()==this.getLastNode().getY()){
                newNode = this.getCurrentNode().getNeighbourNodes().get(1);
            }
            int targetX = this.getTarget().getCell().getX();
            int targetY = this.getTarget().getCell().getY();
            for (int i = 1; i < this.getCurrentNode().getNeighbourNodes().size(); i++) {
                int checkedX = this.getCurrentNode().getNeighbourNodes().get(i).getX();
                int checkedY = this.getCurrentNode().getNeighbourNodes().get(i).getY();
                if(checkedX==this.getLastNode().getX() && checkedY==this.getLastNode().getY()){
                    continue;
                }
                if (Board.calculateDistance(checkedX, targetX, checkedY, targetY) < Board.calculateDistance(newNode.getX(), targetX, newNode.getY(), targetY)) {
                    newNode = this.getCurrentNode().getNeighbourNodes().get(i);
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

	public Health getHealth() {
		return health;
	}
	
	public void setHealth(Health health) {
		this.health = health;
	}
	
	public int getTimeToRecover() {
		return timeToRecover;
	}
	
	public void setTimeToRecover(int timeToRecover) {
		this.timeToRecover = timeToRecover;
	}
	
	public Target getTarget() {
		return target;
	}
	
	public void setTarget(Target[] targets) {
        Random ran = new Random();
        this.target =  targets[ran.nextInt(targets.length)];
	}

	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	public Node getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(Node currentNode) {
		this.currentNode = currentNode;
	}

	public Node setFirstNode(int fromX, int fromY) {
        Node nearest = Board.nodes[0];
        for (int i = 1; i < Board.nodes.length; i++) {
            int x = Board.nodes[i].getX();
            int y = Board.nodes[i].getY();
            if (Board.calculateDistance(fromX, x, fromY, y) < Board.calculateDistance(fromX, nearest.getX(), fromY, nearest.getY())) {
                nearest = Board.nodes[i];
            }
        }
		return nearest;
	}

	public Node getLastNode() {
		return lastNode;
	}

	public void setLastNode(Node lastNode) {
		this.lastNode = lastNode;
	}
}
