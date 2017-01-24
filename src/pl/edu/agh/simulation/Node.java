package pl.edu.agh.simulation;

import java.util.ArrayList;

/**
 * Created by Jaśko on 2016-11-25.
 */
public class Node {
	private int X;
	private int Y;
	private ArrayList<Node> neighbourNodes;

	public Node(int x, int y) {
		X = x;
		Y = y;
		neighbourNodes = new ArrayList<>();
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public ArrayList<Node> getNeighbourNodes() {
		return neighbourNodes;
	}

	public void setNeighbourNodes(ArrayList<Node> neighbourNodes) {
		this.neighbourNodes = neighbourNodes;
	}
}
