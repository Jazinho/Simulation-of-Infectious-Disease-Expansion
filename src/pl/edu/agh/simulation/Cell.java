package pl.edu.agh.simulation;

import java.util.ArrayList;

public class Cell {

    private ArrayList<Cell> neighbors;
    private CellType cellType;
    private boolean isInterior;
    private int x;
    private int y;
    private int timeOfContamination;
    
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

    public enum CellType {
		FREE, WALL, PERSON, NODE;
    }

    public int getTimeOfContamination() {
        return timeOfContamination;
    }

    public void setTimeOfContamination(int timeOfContamination) {
        this.timeOfContamination = timeOfContamination;
    }
}