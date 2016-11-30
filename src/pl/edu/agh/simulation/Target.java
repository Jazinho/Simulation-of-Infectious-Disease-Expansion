package pl.edu.agh.simulation;

public class Target {

    private Cell cell;
    private boolean isSafe;
    private int delayTime;

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

    public Target(int x, int y, boolean isSafe, int delayTime) {
        super();
        this.cell = new Cell(x, y);
        this.isSafe = isSafe;
        this.delayTime = delayTime;
    }

}
