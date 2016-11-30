package pl.edu.agh.simulation;

import java.util.Random;

public class Person {

	public enum Health{
		HEALTY, INFECTED, SYMPTOMS, RESISTANT
	}
	
//	private int x;
//	private int y;
	private Health health;
	private int timeToRecover;
	private Target target;
	private Disease disease;
	private Node currentNode;
	private Node lastNode;
	
	public Person(int x, int y){
//		this.x = x;
//		this.y = y;
		this.health = Health.HEALTY;
		this.disease = null;
		this.timeToRecover = 0;
		this.setTarget(Board.targets);
		this.currentNode = setFirstNode(x, y);
		this.lastNode = currentNode;
	}
	
	public Person(Health health, int x, int y){
//		this.x = x;
//		this.y = y;
		this.health = health;
		this.disease = new Disease();
		this.timeToRecover = disease.getTimeOfDisease();
		this.setTarget(Board.targets);
		this.currentNode = setFirstNode(x, y);
		this.lastNode = currentNode;
	}
	
//	public int getX() {
//		return x;
//	}
//
//	public void setX(int x) {
//		this.x = x;
//	}
//
//	public int getY() {
//		return y;
//	}
//
//	public void setY(int y) {
//		this.y = y;
//	}

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
