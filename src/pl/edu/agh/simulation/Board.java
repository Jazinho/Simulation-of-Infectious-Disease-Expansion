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
import java.util.Random;

@SuppressWarnings("serial")
public class Board extends JComponent implements MouseInputListener {

    public static Target[] targets;
    public static Node[] nodes;
    private Cell[][] cells;
    private int size = 10;
    private int editType = 0;   //  uzywane przy obsludze myszy

    //TODO wszystko co z mysz� jest do wywalenia

    public Board(int width, int height) {
        int initWidth = (width / size) + 1;
        int initHeight = (height / size) + 1;
        addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
        setOpaque(false);
        initialize(initWidth, initHeight); // tworzy tablice punktow o rozmiarze 137 x 67
    }

    public static double calculateDistance(int fromX, int toX, int fromY, int toY) {
        return Math.sqrt(Math.pow(fromX - toX, 2) + Math.pow(fromY - toY, 2));
    }

    public void iteration() {
        for (int x = 1; x < cells.length - 1; ++x)
            for (int y = 1; y < cells[x].length - 1; ++y)
                if (cells[x][y].getCellType() == CellType.PERSON) {
                    cells[x][y].move();
                }
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

    private void initialize(int height, int width) {   // tworzy tablice punktow o rozmiarze 137 x 67
        cells = new Cell[width][height];
        nodes = new Node[44];
        int nodeCounter = 0;
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
                if (loadedMap.charAt(y * 137 + x) == '1') {
                    cells[x][y].setCellType(CellType.WALL);
                }
                if (loadedMap.charAt(y * 137 + x) == 'N') {
                    cells[x][y].setCellType(CellType.NODE);
                    nodes[nodeCounter] = new Node(x, y);
                    nodeCounter += 1;
                }
            }
        }
        connectNodes();

        for (int x = 0; x < cells.length; ++x) {
            for (int y = 0; y < cells[x].length; ++y) {
                if (x > 0 && x < 136 && y > 0 && y < 66) cells[x][y].setNeighbors(generateNeighbours(x, y));
            }
        }
        targets = new Target[]{
                new Target(20, 60, false, 5),
                new Target(100, 15, false, 10),
                //new Target(13, 56, false, 15),
                //new Target(114, 55, false, 20)
        };
        generateAgents();
    }

    private void calculateField() {
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
                    if (cells[x][y].getPerson().getHealth() == Health.HEALTY)
                        g.setColor(Color.GREEN);
                    else if (cells[x][y].getPerson().getHealth() == Health.INFECTED)
                        g.setColor(Color.RED);
                    else if (cells[x][y].getPerson().getHealth() == Health.RESISTANT)
                        g.setColor(Color.BLUE);
                    else if (cells[x][y].getPerson().getHealth() == Health.SYMPTOMS)
                        g.setColor(Color.YELLOW);
                }
                g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
            }
        }
        this.repaint();
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
                cells[x][y].setPerson(new Person(x, y));
            }
            if (editType == 3) {
                cells[x][y].setPerson(new Person(Health.INFECTED, x, y));
            }

            this.repaint();
        }
    }

    // chyba wystarczy zostawic samo clicked, ale narazie niech siedzi
    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < cells.length) && (x > 0) && (y < cells[x].length) && (y > 0)) {
            cells[x][y].setCellType(mapToCellType(editType));
            if (editType == 2) {
                cells[x][y].setPerson(new Person(x, y));
            }
            if (editType == 3) {
                cells[x][y].setPerson(new Person(Health.INFECTED, x, y));
            }
            this.repaint();
        }
    }

    //s�siedzi indeksowani s� kolejno: 0- lewy gorny, 1- gorny, 2-prawy gorny, 3-prawy, 4-prawy dolny, 5-dolny, 6-lewy dolny, 7-lewy
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
        int x = ran.nextInt(136);
        int y = ran.nextInt(66);
        for (int i = 0; i < 1; i++) {
            //int x = 130;
            //int y = 56;
            while (cells[x][y].getCellType() != CellType.FREE) {
                x = ran.nextInt(136);
                y = ran.nextInt(66);
            }
            cells[x][y].setPerson(new Person(Health.INFECTED,x,y));
            cells[x][y].setCellType(CellType.PERSON);
        }
    }

    private void connectNodes() {
        for (int i = 0; i < nodes.length; i++) {
            boolean up = false, right = false, down = false, left = false;
            int x = nodes[i].getX();
            int y = nodes[i].getY();
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
                    if (y_down > 65 || cells[x][y_down].getCellType() == CellType.WALL) {
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
                    if (x_right > 135 || cells[x_right][y].getCellType() == CellType.WALL) {
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
            nodes[i].setNeighbourNodes(neighbourNodes);
        }

        for (Node n : nodes) {
            cells[n.getX()][n.getY()].setCellType(CellType.FREE);
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
