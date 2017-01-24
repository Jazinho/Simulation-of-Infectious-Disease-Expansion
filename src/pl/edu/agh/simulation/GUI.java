package pl.edu.agh.simulation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUI extends JPanel implements ActionListener, ChangeListener {
	private static final long serialVersionUID = 1L;
	private Timer timer;
	private Board board;
	private JButton start;
	private JComboBox<Integer> drawType;
	private JComboBox<String> conditions;
	private JSlider pred;
	private JFrame frame;
	private int iterNum = 0;
	private final int maxDelay = 500;
	private final int initDelay = 0;
	private boolean running = false;
	private JLabel statistics;
	private JFrame heatmapFrame;
	private JButton heatmapButton;
	private Heatmap heatmapPanel;

	public GUI(JFrame jf) {
		frame = jf;
		timer = new Timer(initDelay, this);
		timer.stop();
	}

	public void initialize(Container container) {
		container.setLayout(new BorderLayout());
		container.setSize(new Dimension(500, 768));

		JPanel buttonPanel = new JPanel();

		start = new JButton("Start");
		start.setActionCommand("Start");
		start.addActionListener(this);

		heatmapButton = new JButton("Show/close heat map");
		heatmapButton.setActionCommand("Heatmap");
		heatmapButton.addActionListener(this);

		pred = new JSlider();
		pred.setMinimum(0);
		pred.setMaximum(maxDelay);
		pred.addChangeListener(this);
		pred.setValue(maxDelay - timer.getDelay());

		Integer[] comboBoxTypes = { 0, 1, 2, 3 }; // puste pole, sciana, zdrowy,
													// chory
		drawType = new JComboBox<Integer>(comboBoxTypes);
		drawType.addActionListener(this);
		drawType.setActionCommand("drawType");

		String[] diseaseConditions = { "normal", "+20% of resistance", "-20% of resistance", "-25% of contamination",
				"+25% of contamination" };

		conditions = new JComboBox<String>(diseaseConditions);
		conditions.addActionListener(this);
		conditions.setActionCommand("conditions");

		statistics = new JLabel("Zdrowi: " + "Chorzy: " + "Odporni: " + "Procent: ");

		buttonPanel.add(start);
		buttonPanel.add(drawType);
		buttonPanel.add(conditions);
		buttonPanel.add(pred);
		buttonPanel.add(statistics);
		buttonPanel.add(heatmapButton);

		board = new Board(660, 1360);
		container.add(board, BorderLayout.CENTER);
		container.add(buttonPanel, BorderLayout.SOUTH);

		heatmapPanel = new Heatmap();
		heatmapFrame = new JFrame();
		heatmapFrame.setTitle("Heat Map");
		heatmapFrame.setSize(555, 280);
		heatmapFrame.setLocation(730, 380);
		heatmapFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		heatmapFrame.getContentPane().add(heatmapPanel, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {
		statistics.setText("Zdrowi: " + Board.staty[0] + "  Chorzy: " + Board.staty[1] + "  Odporni: " + Board.staty[2]
				+ "  Procent zachorowañ: " + Board.staty[3] + "%");

		if (e.getSource().equals(timer)) {
			iterNum++;
			frame.setTitle("Disease simulation (" + Integer.toString(iterNum) + " iteration)");
			board.iteration();
		} else {
			String command = e.getActionCommand();
			if (command.equals("Start")) {
				if (!running) {
					timer.start();
					start.setText("Pause");
				} else {
					timer.stop();
					start.setText("Start");
				}
				running = !running;

			} else if (command.equals("drawType")) {
				int newType = (Integer) drawType.getSelectedItem();
				board.setEditType(newType);
			} else if (command.equals("conditions")) {
				Disease.updateDisease(conditions.getSelectedIndex());
			} else if (command.equals("Heatmap")) {
				if (heatmapFrame.isVisible()) {
					heatmapFrame.setVisible(false);
				} else {
					heatmapFrame.setVisible(true);
				}
			}

		}
	}

	public void stateChanged(ChangeEvent e) {
		timer.setDelay(maxDelay - pred.getValue());
	}
}
