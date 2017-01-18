package pl.edu.agh.simulation;

import pl.edu.agh.simulation.Cell.CellType;
import pl.edu.agh.simulation.Person.Health;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@SuppressWarnings("serial")
public class Board extends JComponent implements MouseInputListener {

	public static Integer[] staty = {0,0,0};
    public static ArrayList<Target> targets;
    public static ArrayList<Node> nodes;
    private ArrayList<Person> persons;
    public static Cell[][] cells;
    private int size = 5;
    private int editType = 0;
    private int NUMBER_OF_AGENTS = 250;


    public Board(int width, int height) {
        int initWidth = (width / size) + 2;
        int initHeight = (height / size) + 2;
        addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
        setOpaque(false);
        initialize(initWidth, initHeight); // tworzy tablice punktow o rozmiarze 274 x 134
    }

    public static double calculateDistance(int fromX, int toX, int fromY, int toY) {
        return Math.hypot(fromX - toX, fromY - toY);
    }

    public void iteration() {
        decreaseContamination();
        Arrays.fill(staty, 0);
        for (int it = 0; it < persons.size(); ++it){
        	persons.get(it).move();
        	if(persons.get(it).getHealth() == Health.HEALTHY){
        		staty[0]++;
        	} else if(persons.get(it).getHealth() == Health.INFECTED || persons.get(it).getHealth() == Health.SYMPTOMS){
        		staty[1]++;
        	} else {
        		staty[2]++;
        	}
        }

        this.repaint();
    }

    public void clear() {
        for (int x = 0; x < cells.length; ++x)
            for (int y = 0; y < cells[x].length; ++y) {
                cells[x][y].clear();
            }
        this.repaint();
    }

    private void initialize(int height, int width) {   // tworzy tablice punktow o rozmiarze 274 x 134
        cells = new Cell[width][height];
        nodes = new ArrayList<Node>();
        persons = new ArrayList<Person>();
        targets = new ArrayList<Target>();
        BufferedReader br = null;
        String loadedMap = null;

        try {
            br = new BufferedReader(new FileReader("mapa.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            loadedMap = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int x = 0; x < cells.length; ++x) {
            for (int y = 0; y < cells[x].length; ++y) {
                cells[x][y] = new Cell(x, y);
                if (loadedMap.charAt(y * 274 + x) == '1') {
                    cells[x][y].setCellType(CellType.WALL);
                }
                if (loadedMap.charAt(y * 274 + x) == 'N') {
                    cells[x][y].setCellType(CellType.NODE);
                    nodes.add(new Node(x, y));
                }
            }
        }
        connectNodes();
        
        for (int x = 0; x < cells.length; ++x) {
            for (int y = 0; y < cells[x].length; ++y) {
                if (loadedMap.charAt(y * 274 + x) == 'T') {
                	targets.add(new Target(x, y, false, 100));
                }
                if (x > 0 && x < 273 && y > 0 && y < 133) cells[x][y].setNeighbors(generateNeighbours(x, y));

            }
        }

        generateAgents();
    }

    private void decreaseContamination(){
        for(int i=0;i<cells.length;i++){
            for(int j=0;j<cells[i].length;j++){
                if(cells[i][j].getTimeOfContamination() > 0){
                    int x = cells[i][j].getTimeOfContamination();
                    cells[i][j].setTimeOfContamination(x-1);
                }
            }
        }
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

        for (x = 1; x < cells.length - 1; ++x) {
            for (y = 1; y < cells[x].length - 1; ++y) {
                if (cells[x][y].getCellType() == CellType.FREE) {
                    g.setColor(Color.WHITE);
                } else if (cells[x][y].getCellType() == CellType.WALL) {
                    g.setColor(Color.BLACK);
                } else if (cells[x][y].getCellType() == CellType.PERSON) {
                    if (getPerson(x, y).getHealth() == Health.HEALTHY)
                        g.setColor(Color.GREEN);
                    else if (getPerson(x, y).getHealth() == Health.INFECTED)
                        g.setColor(Color.YELLOW);
                    else if (getPerson(x, y).getHealth() == Health.RESISTANT)
                        g.setColor(Color.BLUE);
                    else if (getPerson(x, y).getHealth() == Health.SYMPTOMS)
                        g.setColor(Color.RED);
                }
                g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
            }
        }

        this.repaint();
    }
    
    public Person getPerson(int x, int y){
    	for(int it = 0; it < persons.size(); it++){
    		if(persons.get(it).getX() == x && persons.get(it).getY() == y){
    			return persons.get(it);
    		}
    	}
    	return null;
    }

    public void setEditType(int editType) {
        this.editType = editType;
    }

    // Obsluga myszy zostawiona w celu mozliwosci tworzenia nowych agentow w czasie trwania symulacji
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < cells.length) && (x > 0) && (y < cells[x].length) && (y > 0)) {
            cells[x][y].setCellType(mapToCellType(editType));
            if (editType == 2) {
                persons.add(new Person(x,y));
            }
            if (editType == 3) {
                persons.add(new Person(Health.INFECTED, x, y));
            }

            this.repaint();
        }
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < cells.length) && (x > 0) && (y < cells[x].length) && (y > 0)) {
            cells[x][y].setCellType(mapToCellType(editType));
            if (editType == 2) {
            	persons.add(new Person(x,y));
            }
            if (editType == 3) {
            	persons.add(new Person(Health.INFECTED, x, y));
            }
            this.repaint();
        }
    }

    //sasiedzi indeksowani sa kolejno: 0- lewy gorny, 1- gorny, 2-prawy gorny, 3-prawy, 4-prawy dolny, 5-dolny, 6-lewy dolny, 7-lewy
    // poki co wykorzystywani w Cell:move() sa tylko czterej glowni sasiedzi, takze poni�sze dodanie wszystkich osmiu jest troche na wyrost
    private ArrayList<Cell> generateNeighbours(int x, int y) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        neighbours.add(cells[x - 1][y - 1]);
        neighbours.add(cells[x][y - 1]);
        neighbours.add(cells[x + 1][y - 1]);
        neighbours.add(cells[x + 1][y]);
        neighbours.add(cells[x + 1][y + 1]);
        neighbours.add(cells[x][y + 1]);
        neighbours.add(cells[x - 1][y + 1]);
        neighbours.add(cells[x - 1][y]);
        return neighbours;
    }

    private void generateAgents() {
        Random ran = new Random();
        int x = ran.nextInt(270) +2;
        int y = ran.nextInt(130) +2;
        for (int i = 0; i < NUMBER_OF_AGENTS; i++) {
            while (cells[x][y].getCellType() != CellType.FREE) {
                x = ran.nextInt(272)+1;
                y = ran.nextInt(132)+1;
            }
            persons.add(new Person(Health.HEALTHY, x, y));
            cells[x][y].setCellType(CellType.PERSON);
        }
    }

    private void connectNodes() {
        for (int i = 0; i < nodes.size(); i++) {
            boolean up = false, right = false, down = false, left = false;
            int x = nodes.get(i).getX();
            int y = nodes.get(i).getY();
            int y_up = y - 1;
            int y_down = y + 1;
            int x_right = x + 1;
            int x_left = x - 1;
            ArrayList<Node> neighbourNodes = new ArrayList<>();

            while (up == false || right == false || down == false || left == false) {
                if (up == false) {
                    if (y_up < 1 || cells[x][y_up].getCellType() == CellType.WALL) {
                        up = true;
                    } else {
                        if (cells[x][y_up].getCellType() == CellType.NODE) {
                            neighbourNodes.add(new Node(x, y_up));
                            up = true;
                        } else {
                            y_up -= 1;
                        }
                    }
                }
                if (down == false) {
                    if (y_down > 132 || cells[x][y_down].getCellType() == CellType.WALL) {
                        down = true;
                    } else {
                        if (cells[x][y_down].getCellType() == CellType.NODE) {
                            neighbourNodes.add(new Node(x, y_down));
                            down = true;
                        } else {
                            y_down += 1;
                        }
                    }
                }
                if (right == false) {
                    if (x_right > 273 || cells[x_right][y].getCellType() == CellType.WALL) {
                        right = true;
                    } else {
                        if (cells[x_right][y].getCellType() == CellType.NODE) {
                            neighbourNodes.add(new Node(x_right, y));
                            right = true;
                        } else {
                            x_right += 1;
                        }
                    }
                }
                if (left == false) {
                    if (x_left < 1 || cells[x_left][y].getCellType() == CellType.WALL) {
                        left = true;
                    } else {
                        if (cells[x_left][y].getCellType() == CellType.NODE) {
                            neighbourNodes.add(new Node(x_left, y));
                            left = true;
                        } else {
                            x_left -= 1;
                        }
                    }
                }
            }
            nodes.get(i).setNeighbourNodes(neighbourNodes);
        }

        for (Node n : nodes) {
            cells[n.getX()][n.getY()].setCellType(CellType.FREE);
        }
    }

    public static boolean areWallsBehind(int fromX, int fromY, int x, int y) {
        if (fromX > x) {
            int tmp = x;
            x = fromX;
            fromX = tmp;
        }
        if (fromY > y) {
            int tmp = y;
            y = fromY;
            fromY = tmp;
        }
        for (int i = fromX; i <= x; i++) {
            for (int j = fromY; j <= y; j++) {
                if (Board.cells[i][j].getCellType() == CellType.WALL) return true;
            }
        }
        return false;
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

    private CellType mapToCellType(int param) {
        switch (param) {
            case 1:
                return CellType.WALL;
            case 2:
                return CellType.PERSON;
            default:
                return CellType.FREE;
        }
    }
    
}
