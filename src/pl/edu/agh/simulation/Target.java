package pl.edu.agh.simulation;

public class Target {

	// TODO target musi przechowywa� informacj� o nabli�szym sobie w�le
    private Cell cell;
    private boolean isSafe;
    private int delayTime;
    private Node nearestNode;

    public Target() {

    }

    public boolean isSafe() {
        return isSafe;
    }

    public void setSafe(boolean isSafe) {
        this.isSafe = isSafe;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
    
    public Node getNearestNode(){
    	return nearestNode;
    }

    public Target(int x, int y, boolean isSafe, int delayTime) {
        super();
        this.cell = new Cell(x, y);
        this.isSafe = isSafe;
        this.delayTime = delayTime;
        this.nearestNode = Person.setFirstNode(x,y);
    }

}
