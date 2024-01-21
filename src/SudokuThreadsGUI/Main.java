package SudokuThreadsGUI;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
		SudokuGames sg = new SudokuGames();
		Frame frame = new Frame(sg);

		Thread generate = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					SudokuSize size = SudokuSize.values()[new Random().nextInt(7)];
					int erase = size.getRandomEraseCount();
					Grid grid = new Generator().generate(size, erase);
					sg.generate(grid);
				}
			}
		});
		generate.start();
	}
}