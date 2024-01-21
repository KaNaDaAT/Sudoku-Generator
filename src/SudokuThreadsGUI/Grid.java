package SudokuThreadsGUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class represents a Sudoku Grid consisting of a 9x9 matrix containing
 * nine 3x3 sub-grids of {@link Cell}s.
 */
public class Grid {

	public final Cell[][] grid;
	public Cell[][] solution;
	private SudokuSize size;


	private Grid(Cell[][] grid) {
		this.grid = grid;
		this.solution = Grid.intToCells(Cell.toInt(this.grid));
		this.size = SudokuSize.getSize(grid.length);
	}

	public static Grid of(int[][] grid) {
		return new Grid(intToCells(grid));
	}

	public static Cell[][] intToCells(int[][] grid) {
		SudokuSize size = SudokuSize.getSize(grid.length);
		if(size == null)
			return null;

		verifyGrid(grid);

		Cell[][] cells = new Cell[size.getSize()][size.getSize()];
		List<List<Cell>> rows = new ArrayList<>();
		List<List<Cell>> columns = new ArrayList<>();
		List<List<Cell>> boxes = new ArrayList<>();

		for (int i = 0; i < cells.length; i++) {
			rows.add(new ArrayList<Cell>());
			boxes.add(new ArrayList<Cell>());
		}
		for (int i = 0; i < cells[0].length; i++) {
			columns.add(new ArrayList<Cell>());
		}

		Cell lastCell = null;
		for (int row = 0; row < grid.length; row++) {
			for (int column = 0; column < grid[row].length; column++) {
				Cell cell = new Cell(grid[row][column]);
				cells[row][column] = cell;

				rows.get(row).add(cell);
				columns.get(column).add(cell);
				boxes.get((row / size.getRows()) * size.getRows() + column / size.getColumns()).add(cell);

				if (lastCell != null) {
					lastCell.setNextCell(cell);
				}

				lastCell = cell;
			}
		}

		for (int i = 0; i < cells.length; i++) {
			List<Cell> row = rows.get(i);
			for (Cell cell : row) {
				List<Cell> rowNeighbors = new ArrayList<>(row);
				rowNeighbors.remove(cell);

				cell.setRowNeighbors(rowNeighbors);
			}

			List<Cell> column = columns.get(i);
			for (Cell cell : column) {
				List<Cell> columnNeighbors = new ArrayList<>(column);
				columnNeighbors.remove(cell);

				cell.setColumnNeighbors(columnNeighbors);
			}

			List<Cell> box = boxes.get(i);
			for (Cell cell : box) {
				List<Cell> boxNeighbors = new ArrayList<>(box);
				boxNeighbors.remove(cell);

				cell.setBoxNeighbors(boxNeighbors);
			}
		}
		return cells;
	}
	
	public boolean checkSudoku() {
		return checkSudoku(grid);
	}

	public boolean checkSudoku(Cell[][] grid) {
		for(int r = 0; r < solution.length; r++) {
			for(int c = 0; c < solution[r].length; c++) {
				if(grid[r][c].getValue() != 0 && grid[r][c].getValue() != solution[r][c].getValue()) {
					return false;
				}
			}
		}
		return true;
	}

	public static Grid emptyGrid(SudokuSize size) {
		return Grid.of(size.createGrid());
	}

	private static void verifyGrid(int[][] grid) {
		if (grid == null)
			throw new IllegalArgumentException("grid must not be null");
		SudokuSize size;
		size = SudokuSize.getSize(grid.length);
		if(size == null) {
			throw new IllegalArgumentException("Not a valid sudoku");
		}

		for (int[] row : grid) {
			for (int value : row) {
				if ((value < 0 || value > size.getSize())) {
					throw new IllegalArgumentException("grid must contain values from 0-" + size.getSize());
				}
			}
		}
	}
	public Grid copy() {
		int[][] gridI = new int[this.grid.length][grid[0].length];
		for(int r = 0; r < grid.length; r++) {
			for(int c = 0; c < grid[r].length; c++) {
				gridI[r][c] = grid[r][c].getValue();
			}
		}
		Grid grid = Grid.of(gridI);
		return grid;
	}

	public SudokuSize getSize() {
		return this.size;
	}

	public Cell getCell(int row, int column) {
		return grid[row][column];
	}

	public static boolean isValidValueForCell(Cell cell, int value) {
		return isValidInRow(cell, value) && isValidInColumn(cell, value) && isValidInBox(cell, value);
	}

	private static boolean isValidInRow(Cell cell, int value) {
		return !getRowValuesOf(cell).contains(value);
	}

	private static boolean isValidInColumn(Cell cell, int value) {
		return !getColumnValuesOf(cell).contains(value);
	}

	private static boolean isValidInBox(Cell cell, int value) {
		return !getBoxValuesOf(cell).contains(value);
	}

	private static Collection<Integer> getRowValuesOf(Cell cell) {
		List<Integer> rowValues = new ArrayList<>();
		for (Cell neighbor : cell.getRowNeighbors())
			rowValues.add(neighbor.getValue());
		return rowValues;
	}

	private static Collection<Integer> getColumnValuesOf(Cell cell) {
		List<Integer> columnValues = new ArrayList<>();
		for (Cell neighbor : cell.getColumnNeighbors())
			columnValues.add(neighbor.getValue());
		return columnValues;
	}

	private static Collection<Integer> getBoxValuesOf(Cell cell) {
		List<Integer> boxValues = new ArrayList<>();
		for (Cell neighbor : cell.getBoxNeighbors())
			boxValues.add(neighbor.getValue());
		return boxValues;
	}

	public Cell getFirstEmptyCell() {
		Cell firstCell = grid[0][0];
		if (firstCell.isEmpty()) {
			return firstCell;
		}

		return getNextEmptyCellOf(firstCell);
	}

	public Cell getNextEmptyCellOf(Cell cell) {
		Cell nextEmptyCell = null;

		while ((cell = cell.getNextCell()) != null) {
			if (!cell.isEmpty()) {
				continue;
			}

			nextEmptyCell = cell;
			break;
		}

		return nextEmptyCell;
	}

	@Override
	public String toString() {
		return StringConverter.toString(this);
	}

	public String toString(boolean solution) {
		return StringConverter.toString(this, solution);
	}

	private static class StringConverter {
		public static String toString(Grid grid) {
			return toString(grid, false);
		}

		public static String toString(Grid grid, boolean solution) {
			StringBuilder builder = new StringBuilder();
			int rows = grid.getSize().getSize();
			int columns = grid.getSize().getSize();
			SudokuSize size = grid.getSize();
			if(size == null)
				return "Size not defined!";

			printTopBorder(size, builder);
			for (int row = 0; row < size.getSize(); row++) {
				printRowBorder(builder);
				for (int column = 0; column < size.getSize(); column++) {
					printValue(builder, grid, row, column, solution);
					printRightColumnBorder(builder, column + 1, size);
				}
				printRowBorder(builder);
				builder.append("\n");
				printBottomRowBorder(builder, row + 1, size);
			}
			printBottomBorder(size, builder);

			return builder.toString();
		}

		private static void printTopBorder(SudokuSize size, StringBuilder builder) {
			builder.append("╔");
			for(int i = 1; i < size.getSize(); i++) {
				builder.append("═══");
				if(i % size.getColumns() == 0){
					builder.append("╦");
				} else {
					builder.append("╤");
				}
			}
			builder.append("═══╗\n");
		}

		private static void printRowBorder(StringBuilder builder) {
			builder.append("║");
		}

		private static void printValue(StringBuilder builder, Grid grid, int row, int column, boolean solution) {
			int value = 0;
			if(!solution)
				value = grid.getCell(row, column).getValue();
			else
				value = grid.solution[row][column].getValue();
			String output = value != 0 ? String.valueOf(value) : " ";
			builder.append(String.format(" %1$-2s", output));
		}

		private static void printRightColumnBorder(StringBuilder builder, int column, SudokuSize size) {
			if (column == size.getSize()) {
				return;
			}

			if (column % size.getColumns() == 0) {
				builder.append("║");
			} else {
				builder.append("│");
			}
		}

		private static void printBottomRowBorder(StringBuilder builder, int row, SudokuSize size) {
			if (row == size.getSize()) {
				return;
			}
			if(row % size.getRows() == 0)
				builder.append("╠");
			else
				builder.append("╟");
			for(int i = 1; i < size.getSize(); i++) {
				if(row % size.getRows() == 0)
					builder.append("═══");
				else
					builder.append("───");
				if(i % size.getColumns() == 0){
					if(row % size.getRows() == 0)
						builder.append("╬");
					else
						builder.append("╫");
				} else {
					if(row % size.getRows() == 0)
						builder.append("╪");
					else
						builder.append("┼");
				}
			}
			if(row % size.getRows() == 0)
				builder.append("═══╣\n");
			else
				builder.append("───╢\n");
		}

		private static void printBottomBorder(SudokuSize size, StringBuilder builder) {
			builder.append("╚");
			for(int i = 1; i < size.getSize(); i++) {
				builder.append("═══");
				if(i % size.getColumns() == 0){
					builder.append("╩");
				} else {
					builder.append("╧");
				}
			}
			builder.append("═══╝");
		}
	}


}