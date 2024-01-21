package SudokuThreadsGUI;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A Generator to generate random Sudoku {@link Grid} instances.
 */
public class Generator {

	private Solver solver;

	private SudokuSize size = SudokuSize.TINY;

	public Generator() {
		this.solver = new Solver();
	}

	public Grid generate(SudokuSize size, int numberOfEmptyCells) {
		this.size = size;
		Grid grid = generate();

		Grid gridBack = eraseCells(grid, numberOfEmptyCells);
		if(gridBack != null)
			grid = gridBack;
		System.out.println(grid);

		return grid;
	}

	private Grid eraseCells(Grid grid, int numberOfEmptyCells) {
		Random random = new Random();
		int rows = grid.getSize().getSize();
		int columns = grid.getSize().getSize();

		Grid gridClone = grid.copy(); // Create clone

		int maxtrys = 1;
		int removed = 0;
		for(int trys = 0; trys < maxtrys; trys++) {
			removed = 0;
			grid = gridClone.copy(); // Reset from clone

			// Add all cells to an list
			List<Point> cells = new ArrayList<Point>();
			for(int r = 0; r < rows; r++) {
				for(int c = 0; c < columns; c++) {
					cells.add(new Point(c, r));
				}
			}

			// remove n cells
			for (int i = 0; i < numberOfEmptyCells; i++) {
				if(cells.size() == 0) // if all cells tried cancel
					break;

				// Get a random cell which is still available
				int index = random.nextInt(cells.size());
				Point point = cells.get(index);
				cells.remove(index); // Remove cell which got tried already

				// Remove that cell in grid and show if its still solvable
				Cell cell = grid.getCell(point.x, point.y);
				if (!cell.isEmpty()) {
					int oldValue = cell.getValue();
					cell.setValue(0);
					if(!Solver.isSolvable(grid)) {
						cell.setValue(oldValue); // Reset that cell if not solvable
						i--;
					} else {
						removed++; // One more cell got removed
					}
				} else {
					i--; // Cell empty. Just try another (Should never get called)
				}
			}

			if(numberOfEmptyCells == removed || trys + 1 == maxtrys)
				break;
		}
		System.out.println("Wanted Clues: " + (rows * columns - numberOfEmptyCells));
		System.out.println("Gotten Clues: " + (rows * columns - removed));
		return grid;
	}

	private Grid generate() {
		Grid grid = Grid.emptyGrid(this.size);
		while(true) {
			try {
				solver.solve(grid);
				break;
			} catch(Exception e) {
				grid = Grid.emptyGrid(this.size);
			}
		}
		grid.solution = grid.copy().grid;
		return grid;
	}

}