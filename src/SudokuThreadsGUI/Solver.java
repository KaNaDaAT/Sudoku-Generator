package SudokuThreadsGUI;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;


public class Solver {
	private static final int EMPTY = 0;

	private int[] values;
	private int fails;

	public Solver() {
		this.values = generateRandomValues(9);
	}

	public void solve(Grid grid) {
		fails = 0;
		boolean solvable = solve(grid, grid.getFirstEmptyCell());

		if (!solvable) {
			throw new IllegalStateException("The provided grid is not solvable.");
		}
	}

	private boolean solve(Grid grid, Cell cell) {
		if (cell == null) {
			return true;
		}
		if(fails > Math.pow(grid.getSize().getSize(), Math.PI))
			return false;

		int[] values = generateRandomValues(grid.getSize().getSize());
		for (int value : values) {
			if (value == EMPTY)
				continue;
			if (grid.isValidValueForCell(cell, value)) {
				cell.setValue(value);
				if (solve(grid, grid.getNextEmptyCellOf(cell)))
					return true;
				else
					fails++;
					cell.setValue(EMPTY);
			}
		}
		return false;
	}

	private int[] generateRandomValues(int size) {
		int[] values = new int[size+1];
		values[0] = EMPTY;
		for(int i = 1; i < size+1; i++) {
			values[i] = i;
		}

		Random random = new Random();
		for (
				int i = 0, j = random.nextInt(values.length), tmp = values[j];
			 	i < values.length;
			 	i++, j = random.nextInt(values.length), tmp = values[j]
		) {
			if (i == j)
				continue;

			values[j] = values[i];
			values[i] = tmp;
		}

		return values;
	}

	private static int trys = 0;

	public static boolean isSolvable(Grid grid) {
		trys = 0;
		return solve(Cell.toInt(grid.grid), grid.getSize()) == 1;
	}

	private static int solve(int[][] board, SudokuSize size) {
		trys++;
		if(trys >= 100000)
			return -1;
		int count = 0;
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board.length; column++) {
				if (board[row][column] == 0) {
					for (int k = 1; k <= board.length; k++) {
						board[row][column] = k;
						if (isValid(board, row, column, k, size)) {
							int back = solve(board, size);
							if (back == -1)
								return -1;
							else if(back == 1)
								count++;
						}
						board[row][column] = 0;
						if(count > 1) {
							return -1;
						}
					}
					if(count == 1) {
						return 1;
					} else {
						return 0;
					}
				}
			}
		}
		return 1;
	}

	private static boolean isValid(int[][]board, int row, int column, int value, SudokuSize size) {
		board[row][column] = 0;
		boolean valid = (
				rowConstraint(board, row, value)
						&& columnConstraint(board, column, value)
						&& subsectionConstraint(board, row, column, value, size)
		);
		board[row][column] = value;
		return valid;
	}

	private static boolean rowConstraint(int[][] board, int row, int value) {
		for(int i = 0; i < board.length; i++) {
			if(board[row][i] == value)
				return false;
		}
		return true;
	}

	private static boolean columnConstraint(int[][] board, int column, int value) {
		for(int i = 0; i < board.length; i++) {
			if(board[i][column] == value)
				return false;
		}
		return true;
	}

	private static boolean subsectionConstraint(int[][] board, int row, int column, int value, SudokuSize size) {
		boolean[]constraint = new boolean[board.length];
		int subsectionRowStart = (row / size.getRows()) *  size.getRows();
		int subsectionRowEnd = subsectionRowStart + size.getRows();

		int subsectionColumnStart = (column / size.getColumns()) *  size.getColumns();
		int subsectionColumnEnd = subsectionColumnStart + size.getColumns();

		for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
			for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
				if(board[r][c] == value)
					return false;
			}
		}
		return true;
	}
}