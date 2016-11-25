package pl.edu.agh.simulation;

public class Person {

	public enum Health{
		HEALTY, INFECTED, SYMPTOMS, RESISTANT
	}
	
	private Health health;
	private int timeToRecover;
	private Target target;
	private Disease disease;
	private Node currentNode;
	private Node lastNode;
	
	public Person(){
		this.health = Health.HEALTY;
		this.timeToRecover = 0;
		this.target = null;
		this.disease = null;
		this.currentNode = null;
		this.lastNode = null;
	}
	
	public Person(Health health){
		this.health = health;
		this.disease = new Disease();
		this.timeToRecover = disease.getTimeOfDisease();
		this.target = null;
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
	
	public void setTarget(Target target) {
		this.target = target;
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

	public Node getLastNode() {
		return lastNode;
	}

	public void setLastNode(Node lastNode) {
		this.lastNode = lastNode;
	}
}
