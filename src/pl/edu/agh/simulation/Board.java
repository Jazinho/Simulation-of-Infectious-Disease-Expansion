package pl.edu.agh.simulation;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

import pl.edu.agh.simulation.Cell.CellType;
import pl.edu.agh.simulation.Person.Health;

public class Board extends JComponent implements MouseInputListener {
	
	private static final long serialVersionUID = 1L;
	private Cell[][] cells;
	private Target[] targets;
	private int size = 10;
	private int editType = 0;   //  u¿ywane przy obs³udze myszy

	public void setEditType(int editType) {
		this.editType = editType;
	}

	public Board(int width, int height) {
		int initWidth = (width / size) + 1;
		int initHeight = (height / size) + 1;
		addMouseListener(this);
		addMouseMotionListener(this);
		setBackground(Color.WHITE);
		setOpaque(false);
		initialize(initWidth, initHeight); // tworzy tablice punktów o rozmiarze 137 x 67
	}

	public void iteration() {
		for (int x = 1; x < cells.length - 1; ++x)
			for (int y = 1; y < cells[x].length - 1; ++y)
				cells[x][y].move(targets);
		this.repaint();
		
	}

	public void clear() {
		for (int x = 0; x < cells.length; ++x)
			for (int y = 0; y < cells[x].length; ++y) {
				cells[x][y].clear();
			}
		calculateField();
		this.repaint();
	}

	private void initialize(int height, int width) {   // tworzy tablice punktów o rozmiarze 137 x 67
		cells = new Cell[width][height];

		for (int x = 0; x < cells.length; ++x)
			for (int y = 0; y < cells[x].length; ++y)
				cells[x][y] = new Cell(x,y);
		
		targets = new Target[4];
		targets[0] = new Target(12, 10, false, 5);
		targets[1] = new Target(119, 11, false, 10);
		targets[2] = new Target(13, 56, false, 15);
		targets[3] = new Target(114, 55, false, 20);

		// TODO pêtla for losuj¹ca wspó³rzêdne kilku agentów

	}
	
	private void calculateField(){
	}

	protected void paintComponent(Graphics g) {
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		g.setColor(Color.GRAY);
		drawNetting(g, size);
	}

	private void drawNetting(Graphics g, int gridSpace) {
		Insets insets = getInsets();
		int firstX = insets.left;
		int firstY = insets.top;
		int lastX = this.getWidth() - insets.right;
		int lastY = this.getHeight() - insets.bottom;

		int x = firstX;
		while (x < lastX) {
			g.drawLine(x, firstY, x, lastY);
			x += gridSpace;
		}

		int y = firstY;
		while (y < lastY) {
			g.drawLine(firstX, y, lastX, y);
			y += gridSpace;
		}

		for (x = 1; x < cells.length-1; ++x) {
			for (y = 1; y < cells[x].length-1; ++y) {
				if(cells[x][y].getCellType()==CellType.FREE){
					g.setColor(new Color(1.0f, 1.0f, 1.0f ));
				}
				else if (cells[x][y].getCellType()==CellType.WALL){
					g.setColor(new Color(1.0f, 0.0f, 0.0f, 0.7f));
				}
				else if (cells[x][y].getCellType()==CellType.PERSON){
					if (cells[x][y].getPerson().getHealth() == Health.HEALTY)
						g.setColor(new Color(0.0f, 1.0f, 0.0f, 0.7f));
					else if (cells[x][y].getPerson().getHealth() == Health.INFECTED)
						g.setColor(new Color(0.0f, 1.0f, 0.0f, 0.7f));					// TODO zmieniæ kolory
					else if (cells[x][y].getPerson().getHealth() == Health.RESISTANT)
						g.setColor(new Color(0.0f, 1.0f, 0.0f, 0.7f));					// TODO zmieniæ kolory
					else if (cells[x][y].getPerson().getHealth() == Health.SYMPTOMS)
						g.setColor(new Color(0.0f, 1.0f, 0.0f, 0.7f));					// TODO zmieniæ kolory
				}
				g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
			}
		}
		this.repaint();

	}
	
// Obs³uga myszy zostawiona w celu mo¿liwoœci tworzenia nowych agentów w czasie trwania symulacji
	public void mouseClicked(MouseEvent e) {
		int x = e.getX() / size;
		int y = e.getY() / size;
		if ((x < cells.length) && (x > 0) && (y < cells[x].length) && (y > 0)) {
			if(editType==2){
//				points[x][y].setPedestrian(true); // TODO wywo³aæ new Person w cells, ZDROWY
				cells[x][y].setCellType(CellType.PERSON);
			}
			if(editType==3){
//				points[x][y].setPedestrian(true); // TODO wywo³aæ new Person w cells, CHORY
				cells[x][y].setCellType(CellType.PERSON);
			}
			else{
				cells[x][y].setCellType(CellType.values()[editType]);
				System.out.println(x + " " + y);
			}
			this.repaint();
		}
	}

	// chyba wystarczy zostawiæ samo clicked, ale narazie niech siedzi
	public void mouseDragged(MouseEvent e) {
		int x = e.getX() / size;
		int y = e.getY() / size;
		if ((x < cells.length) && (x > 0) && (y < cells[x].length) && (y > 0)) {
			if(editType==2){
//				points[x][y].setPedestrian(true);   // TODO wywo³aæ new Person w cells, ZDROWY
				cells[x][y].setCellType(CellType.PERSON);
			}
			if(editType==3){
//				points[x][y].setPedestrian(true);   // TODO wywo³aæ new Person w cells, CHORY
				cells[x][y].setCellType(CellType.PERSON);
			}
			else{
				cells[x][y].setCellType(CellType.values()[editType]);
			}
			this.repaint();
		}
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

}
