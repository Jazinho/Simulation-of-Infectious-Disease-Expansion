package pl.edu.agh.simulation;

import javax.swing.*;
import java.awt.*;

public class Heatmap extends JPanel {
	private static final long serialVersionUID = 1L;
	private int size = 2;

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        int x,y,sum=0, grain=10;

        for (x = 1; x < Board.cells.length - grain; x=x+grain) {
            for (y = 1; y < Board.cells[x].length - grain; y=y+grain) {
                for(int i=0;i<grain;i++){
                    for(int j=0;j<grain;j++){
                        sum += Board.cells[x+i][y+j].getTimeOfContamination();
                    }
                }
                if (sum > 0.5 * Math.pow(grain,2) * Disease.cellContaminationTime) {
                    g.setColor(new Color(255,0,0));
                } else if(sum > 0.3 * Math.pow(grain,2) * Disease.cellContaminationTime){
                    g.setColor(new Color(255,80,0));
                } else if(sum > 0.2 * Math.pow(grain,2) * Disease.cellContaminationTime){
                    g.setColor(new Color(255,140,0));
                } else if(sum > 0.1 * Math.pow(grain,2) * Disease.cellContaminationTime){
                    g.setColor(new Color(255,190,0));
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect((x * size) + 1, (y * size) + 1, grain*size, grain*size);
                sum = 0;
            }
        } 
        this.repaint();
    }
}
