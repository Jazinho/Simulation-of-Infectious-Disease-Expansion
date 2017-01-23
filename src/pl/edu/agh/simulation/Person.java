package pl.edu.agh.simulation;

import pl.edu.agh.simulation.Cell.CellType;

import java.util.ArrayList;
import java.util.Random;

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
    private ArrayList<Node> visitedNodes;
    private CellType tmp;

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
		this.lastNode = this.currentNode;
		this.delayTime = 0;
        this.visitedNodes = new ArrayList<>();
        this.tmp = CellType.FREE;
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
		this.lastNode = this.currentNode;
		this.delayTime = 0;
        this.visitedNodes = new ArrayList<>();
        this.tmp = CellType.FREE;
	}

    public void move() {
        int destX;
        int destY;

        if(this.getTarget().getNearestNode() == this.getLastNode()){
        	destX = this.getTarget().getCell().getX();
            destY = this.getTarget().getCell().getY();
            // jesli osiagnieto cel to znajdz nowy i wroc na trase do tego samego node'a
            if (x == this.getTarget().getCell().getX() && y == this.getTarget().getCell().getY()) {
            	this.setTarget(Board.targets);
            	this.setCurrentNode(lastNode);
                this.visitedNodes.clear();
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
				if (Board.cells[this.getX()][this.getY()].getNeighbors().get(7).getCellType() == CellType.FREE || Board.cells[this.getX()][this.getY()].getNeighbors().get(7).getCellType() == CellType.WALL) {
                    this.makeStep(7);
                } else if (destX < y) {
                    if ((Board.cells[this.getX()][this.getY()].getNeighbors().get(1).getCellType() == CellType.FREE || Board.cells[this.getX()][this.getY()].getNeighbors().get(1).getCellType() == CellType.WALL)) {
                        this.makeStep(1);
                    } else {
                        this.makeStep(5);
                    }
                } else {
                    if ((Board.cells[this.getX()][this.getY()].getNeighbors().get(5).getCellType() == CellType.FREE || Board.cells[this.getX()][this.getY()].getNeighbors().get(5).getCellType() == CellType.WALL)) {
                        this.makeStep(5);
                    } else {
                        this.makeStep(1);
                    }
                }
            } else {
                if (Board.cells[this.getX()][this.getY()].getNeighbors().get(3).getCellType() == CellType.FREE || Board.cells[this.getX()][this.getY()].getNeighbors().get(3).getCellType() == CellType.WALL) {
                    this.makeStep(3);
                } else if (destX < y) {
                    if ((Board.cells[this.getX()][this.getY()].getNeighbors().get(1).getCellType() == CellType.FREE || Board.cells[this.getX()][this.getY()].getNeighbors().get(1).getCellType() == CellType.WALL)) {
                        this.makeStep(1);
                    } else {
                        this.makeStep(5);
                    }
                } else {
                    if ((Board.cells[this.getX()][this.getY()].getNeighbors().get(5).getCellType() == CellType.FREE || Board.cells[this.getX()][this.getY()].getNeighbors().get(5).getCellType() == CellType.WALL)) {
                        this.makeStep(5);
                    } else {
                        this.makeStep(1);
                    }
                }
            }
        } else {
            if (destY < y) {
                if (Board.cells[this.getX()][this.getY()].getNeighbors().get(1).getCellType() == CellType.FREE || Board.cells[this.getX()][this.getY()].getNeighbors().get(1).getCellType() == CellType.WALL) {
                    this.makeStep(1);
                } else if (destX < x) {
                    if ((Board.cells[this.getX()][this.getY()].getNeighbors().get(7).getCellType() == CellType.FREE || Board.cells[this.getX()][this.getY()].getNeighbors().get(7).getCellType() == CellType.WALL)) {
                        this.makeStep(7);
                    } else {
                        this.makeStep(3);
                    }
                } else {
                    if ((Board.cells[this.getX()][this.getY()].getNeighbors().get(3).getCellType() == CellType.FREE || Board.cells[this.getX()][this.getY()].getNeighbors().get(3).getCellType() == CellType.WALL)) {
                        this.makeStep(3);
                    } else {
                        this.makeStep(7);
                    }
                }
            } else {
                if (Board.cells[this.getX()][this.getY()].getNeighbors().get(5).getCellType() == CellType.FREE || Board.cells[this.getX()][this.getY()].getNeighbors().get(5).getCellType() == CellType.WALL) {
                    this.makeStep(5);
                } else if (destX < x) {
                    if ((Board.cells[this.getX()][this.getY()].getNeighbors().get(7).getCellType() == CellType.FREE || Board.cells[this.getX()][this.getY()].getNeighbors().get(7).getCellType() == CellType.WALL)) {
                        this.makeStep(7);
                    } else {
                        this.makeStep(3);
                    }
                } else {
                    if ((Board.cells[this.getX()][this.getY()].getNeighbors().get(3).getCellType() == CellType.FREE || Board.cells[this.getX()][this.getY()].getNeighbors().get(3).getCellType() == CellType.WALL)) {
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
            Board.cells[this.getX()][this.getY()].setCellType(tmp);
            tmp = Board.cells[this.getX()][this.getY()].getNeighbors().get(direction).getCellType();
        	Board.cells[this.getX()][this.getY()].getNeighbors().get(direction).setCellType(CellType.PERSON);
        	int x = Board.cells[this.getX()][this.getY()].getNeighbors().get(direction).getX();
        	int y = Board.cells[this.getX()][this.getY()].getNeighbors().get(direction).getY();
        	this.setX(x);
        	this.setY(y);
        }
    }

    private Node getNextNode() {
        Node newNode = this.getCurrentNode().getNeighbourNodes().get(0);
        if (this.getCurrentNode().getNeighbourNodes().size() > 1) {
            int targetX = this.getTarget().getCell().getX();
            int targetY = this.getTarget().getCell().getY();
            double currentShorter = -1;
            boolean selectedUnvisitedNode = false;

            for (int i = 0; i < this.getCurrentNode().getNeighbourNodes().size(); i++) {
                int checkedX = this.getCurrentNode().getNeighbourNodes().get(i).getX();
                int checkedY = this.getCurrentNode().getNeighbourNodes().get(i).getY();
                if( !wasVisited(checkedX, checkedY) ){
                    if (Board.calculateDistance(checkedX, targetX, checkedY, targetY) < currentShorter || currentShorter == -1) {
                        currentShorter = Board.calculateDistance(checkedX, targetX, checkedY, targetY);
                        newNode = this.getCurrentNode().getNeighbourNodes().get(i);
                        selectedUnvisitedNode = true;
                    }
                }
            }
            visitedNodes.add(newNode);

            if(!selectedUnvisitedNode){
                visitedNodes.clear();
                visitedNodes.add(currentNode);
                visitedNodes.add(lastNode);
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

    private boolean wasVisited(int x, int y){
        for(Node node: visitedNodes){
            if(node.getX() == x && node.getY() == y){
                return true;
            }
        }
        return false;
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
            if (Board.calculateDistance(fromX, x, fromY, y) <= Board.calculateDistance(fromX, nearest.getX(), fromY, nearest.getY())) {
                if(!Board.areWallsBehind(fromX, fromY, x, y)) {
                    nearest = Board.nodes.get(i);
                }
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
