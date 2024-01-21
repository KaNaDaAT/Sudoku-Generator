package SudokuThreadsGUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SudokuPanel extends JPanel {

	private Grid grid;

	public int[][] gridInt;

	public SudokuPanel() {

	}

	public SudokuPanel(Grid grid) {
		this.grid = grid;
		gridInt = new int[grid.grid.length][grid.grid[0].length];
		for (int r = 0; r < gridInt.length; r++) {
			for (int c = 0; c < gridInt.length; c++) {
				gridInt[r][c] = grid.grid[r][c].getValue();
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (grid == null)
			return;

		this.removeAll();
		this.setLayout(null);
		this.setSize(
				this.getWidth() / grid.getSize().getSize() * grid.getSize().getSize(),
				(this.getHeight()) / grid.getSize().getSize() * grid.getSize().getSize() + 2
		);

		int smallerSize = this.getWidth() < this.getHeight() - 2 ? this.getWidth() : this.getHeight() - 2;
		int biggerSize = this.getWidth() > this.getHeight() - 2 ? this.getWidth() : this.getHeight() - 2;
		int paddingLeft = (this.getWidth() - smallerSize) / 2;
		int paddingTop = (this.getHeight() - 2 - smallerSize) / 2;

		int size = grid.getSize().getSize();

		int rows = grid.getSize().getRows();
		int columns = grid.getSize().getColumns();

		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (r % rows == 0 && c % columns == 0) {
					g.setColor(Color.BLACK);
					int row = r / rows;
					int column = c / columns;
					g.drawRect(
							paddingLeft + column * (smallerSize / rows),
							paddingTop + row * (smallerSize / columns),
							smallerSize / rows,
							smallerSize / columns
					);
				}
				g.setColor(Color.GRAY);
				g.drawRect(
						paddingLeft + r * (smallerSize / size) + 1,
						paddingTop + c * (smallerSize / size) + 1,
						smallerSize / size,
						smallerSize / size
				);

				JTextField label = new JTextField();
				label.setName(r + ":" + c);
				label.setBounds(
						paddingLeft + c * (smallerSize / size) + 2,
						paddingTop + r * (smallerSize / size) + 2,
						smallerSize / size - 2,
						smallerSize / size - 2
				);

				label.getDocument().addDocumentListener(new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {
						change(e);
					}

					public void removeUpdate(DocumentEvent e) {
						change(e);
					}

					public void insertUpdate(DocumentEvent e) {
						change(e);
					}

					public void change(DocumentEvent e) {
						String name = label.getName();
						int r = Integer.parseInt(name.substring(0, name.indexOf(":")));
						int c = Integer.parseInt(name.substring(name.indexOf(":") + 1));
						if(e.getLength() == 1) {
							try {
								int value = Integer.parseInt(label.getText());
								if(value <= grid.getSize().getSize()) {
									gridInt[r][c] = value;
								}
							} catch(Exception exc) {
								gridInt[r][c] = 0;
							}
						} else if(e.getLength() == 0) {
							gridInt[r][c] = 0;
						}
					}
				});

				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setFont(new Font("Arial", Font.BOLD, (int) (label.getWidth() / 2.5f)));
				//label.setText(grid.getCell(r, c).getValue() + "");
				label.setText(gridInt[r][c] + "");
				label.setFocusCycleRoot(false);
				label.setBorder(null);
				if (gridInt[r][c] != 0) {
					if(grid.getCell(r, c).getValue() != 0)
						label.setEnabled(false);
				} else {
					label.setText("");
				}
				this.add(label);
			}
		}
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
		gridInt = new int[grid.grid.length][grid.grid[0].length];
		for (int r = 0; r < gridInt.length; r++) {
			for (int c = 0; c < gridInt.length; c++) {
				gridInt[r][c] = grid.grid[r][c].getValue();
			}
		}
		this.repaint();
	}
}
