package pl.edu.agh.simulation;

import java.util.ArrayList;
import java.util.Random;

import pl.edu.agh.simulation.Cell.CellType;

public class Person {

	public enum Health{
        HEALTHY, INFECTED, SYMPTOMS, RESISTANT
	}
	
	private int x;
	private int y;
	private Health health;
    private int timeToInfect;
    private int timeToIncubate;
	private int timeToRecover;
    private int timeOfResistance;
	private Target target;
	private Node currentNode;
	private Node lastNode;
	private int delayTime;
	
	public Person(int x, int y){
		this.x = x;
		this.y = y;
		this.health = Health.HEALTHY;
        this.timeToInfect=0;
        this.timeToIncubate=0;
		this.timeToRecover = 0;
        this.timeOfResistance = 0;
		this.setTarget(Board.targets);
		this.currentNode = setFirstNode(x, y);
		this.lastNode = currentNode;
		this.delayTime = 0;
	}
	
	public Person(Health health, int x, int y){
		this.x = x;
		this.y = y;
		this.health = health;
        this.timeToInfect=0;
        this.timeToIncubate=0;
		this.timeToRecover = 0;
        this.timeOfResistance = 0;
		this.setTarget(Board.targets);
		this.currentNode = setFirstNode(x, y);
		this.lastNode = currentNode;
		this.delayTime = 0;
	}
	
    public void move() {
    	
        //jesli osiagnieto cel, to znajdz nowy
//        if (x == this.getTarget().getCell().getX() && y == this.getTarget().getCell().getY()) {
////        	if(this.delayTime == 0){
////            	this.delayTime = this.getTarget().getDelayTime(); // co do tego mam w¹tpliwoœci, czy on kiedyœ z tej pêtli wyjdzie :)
////                this.setTarget(Board.targets);
////        	}else{
////        		this.delayTime--;
////        		return;
////        	}
//        	this.setTarget(Board.targets);
//        }

        int destX;
        int destY;
        
        if(this.getTarget().getNearestNode() == this.getLastNode()){
        	destX = this.getTarget().getCell().getX();
            destY = this.getTarget().getCell().getY();
            if (x == this.getTarget().getCell().getX() && y == this.getTarget().getCell().getY()) { // jeœli osiagnieto cel to znajdz nowy i wroc na trase do tego samego node'a
            	this.setTarget(Board.targets);
            	this.setCurrentNode(lastNode);
            }
        }else{
            if (x == this.getCurrentNode().getX() && y == this.getCurrentNode().getY()) {
                Node lastOne = this.getCurrentNode();
                this.setCurrentNode(getNextNode());
                this.setLastNode(lastOne);
            }
        	destX = this.getCurrentNode().getX();
            destY = this.getCurrentNode().getY();
        }
        


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

        if(this.health == Health.SYMPTOMS){ //YELLOW
            contaminateNeighbourhood();
            timeToRecover += 1;
            if(timeToRecover == Disease.timeOfDisease) {
                health = Health.RESISTANT;
                timeToRecover = 0;
            }
        }else if(health == Health.INFECTED){ //RED
            contaminateNeighbourhood();
            timeToIncubate += 1;
            if(timeToIncubate == Disease.incubationTime) {
                health = Health.SYMPTOMS;
                timeToIncubate = 0;
            }
        }else if(health == Health.HEALTHY){ //GREEN
            if(Board.cells[getX()][getY()].getTimeOfContamination() > 0){ //jesli w komorce sa obecne bakterie
                timeToInfect+=1;
                if(timeToInfect == Disease.spreadingTime){
                    health = Health.INFECTED;
                    timeToInfect = 0;
                }
            }else{
                if(timeToInfect>0) timeToInfect-=1;
            }
        }else{ //----------------------resistant BLUE
            timeOfResistance += 1;
            if(timeOfResistance == Disease.timeOfResistance) {
                health = Health.HEALTHY;
                timeOfResistance = 0;
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

        for(int i = 0; i < Board.nodes.size();i++){
            if(newNode.getX()==Board.nodes.get(i).getX() && newNode.getY()==Board.nodes.get(i).getY()){
                returnedNode = Board.nodes.get(i);
            }
        }
        return returnedNode;
    }

    private void contaminateNeighbourhood(){
        int curX = this.getX();
        int curY = this.getY();
        Board.cells[curX][curY].setTimeOfContamination(Disease.cellContaminationTime);
        Board.cells[curX][curY-1].setTimeOfContamination(Disease.cellContaminationTime);
        Board.cells[curX][curY+1].setTimeOfContamination(Disease.cellContaminationTime);
        Board.cells[curX-1][curY].setTimeOfContamination(Disease.cellContaminationTime);
        Board.cells[curX-1][curY-1].setTimeOfContamination(Disease.cellContaminationTime);
        Board.cells[curX-1][curY+1].setTimeOfContamination(Disease.cellContaminationTime);
        Board.cells[curX+1][curY].setTimeOfContamination(Disease.cellContaminationTime);
        Board.cells[curX+1][curY-1].setTimeOfContamination(Disease.cellContaminationTime);
        Board.cells[curX+1][curY+1].setTimeOfContamination(Disease.cellContaminationTime);
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
	
	public void setTarget(ArrayList<Target> targets) {
        Random ran = new Random();
        this.target =  targets.get(ran.nextInt(targets.size()));
	}

	public Node getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(Node currentNode) {
		this.currentNode = currentNode;
	}

	public static Node setFirstNode(int fromX, int fromY) {
        Node nearest = Board.nodes.get(0);
        for (int i = 1; i < Board.nodes.size(); i++) {
            int x = Board.nodes.get(i).getX();
            int y = Board.nodes.get(i).getY();
            if (Board.calculateDistance(fromX, x, fromY, y) < Board.calculateDistance(fromX, nearest.getX(), fromY, nearest.getY())) {
                nearest = Board.nodes.get(i);
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

    public int getTimeToInfect() {
        return timeToInfect;
    }

    public void setTimeToInfect(int timeToInfect) {
        this.timeToInfect = timeToInfect;
    }
}
