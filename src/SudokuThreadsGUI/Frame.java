package SudokuThreadsGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Frame extends JFrame {

	private static final long serialVersionUID = 4641820421741983686L;
	private SudokuGames game;
	private Grid grid;

	public Frame(SudokuGames game) {
		this.game = game;

		this.setTitle("Sudoku");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(700, 500));
		this.setLocationRelativeTo(null);

		initView();
		this.add(this.panel);

		pack();
		this.setVisible(true);
	}

	JPanel panel = new JPanel();
	JPanel southPanel = new JPanel();
	SudokuPanel sudokuPanel = new SudokuPanel();
	JButton bCheckSudoku = new JButton();
	JButton bNewSudoku = new JButton();

	private void initView() {

		// Panel
		this.panel.setLayout(new BorderLayout());
		southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 2));
		southPanel.setPreferredSize(new Dimension(100, 50));
		southPanel.add(bCheckSudoku);
		southPanel.add(bNewSudoku);
		this.panel.add(southPanel, BorderLayout.SOUTH);
		this.panel.add(sudokuPanel, BorderLayout.CENTER);
		
		// Button CheckSudoku
		this.bCheckSudoku.setText("Check Sudoku");
		this.bCheckSudoku.setFocusCycleRoot(false);
		this.bCheckSudoku.setFocusable(false);
		this.bCheckSudoku.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(grid != null) {
					if(grid.checkSudoku(Grid.intToCells(sudokuPanel.gridInt))) {
						JOptionPane.showMessageDialog(null, "Fehlerfrei!");
					} else {
						JOptionPane.showMessageDialog(null, "Fehlerhaft!", "Feld", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		// Button NewSudoku
		this.bNewSudoku.setText("Neues Sudoku");
		this.bNewSudoku.setFocusCycleRoot(false);
		this.bNewSudoku.setFocusable(false);
		this.bNewSudoku.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread() {
					public void run() {
						setSudoku(game.get());
						Container c = sudokuPanel;
						BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
						c.paint(im.getGraphics());
						try {
							ImageIO.write(im, "PNG", new File("D:/shot.png"));
						} catch (IOException ioException) {
							ioException.printStackTrace();
						}
					}
				};
				thread.start();
			}
		});

		// Panel with the Sudoku
		this.sudokuPanel.setLayout(new BorderLayout());
		JLabel label = new JLabel("Press Button!");
		label.setFont(new Font("Arial", Font.BOLD, 40));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		this.sudokuPanel.add(label);
	}

	private void Refresh() {
		this.panel.removeAll();

		this.sudokuPanel = new SudokuPanel(grid);
		this.panel.add(southPanel, BorderLayout.SOUTH);
		this.panel.add(sudokuPanel, BorderLayout.CENTER);
		this.sudokuPanel.repaint();
		this.validate();

	}

	public void setSudoku(Grid grid) {
		this.grid = grid;
		this.Refresh();
	}
}
