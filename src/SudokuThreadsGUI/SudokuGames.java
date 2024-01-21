package SudokuThreadsGUI;

import java.util.ArrayList;

public class SudokuGames {

	ArrayList<Grid> sudokus = new ArrayList<Grid>();

	public void generate(Grid grid) {
		synchronized (sudokus) {
			if (sudokus.size() >= 10) {
				try {
					sudokus.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (sudokus.size() >= 1) {
				sudokus.notifyAll();
			}
			sudokus.add(grid);
		}
	}

	public Grid get() {
		synchronized (sudokus) {
			if (sudokus.size() == 0) {
				try {
					sudokus.wait();
					System.out.println("HI");
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}
			if(sudokus.size() <= 7)
				sudokus.notifyAll();
			Grid grid = sudokus.get(0);
			sudokus.remove(0);
			return grid;
		}
	}
}
