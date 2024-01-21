package SudokuThreadsGUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum SudokuSize {
    TINY(4, 4),
    SMALLER(6, 10),
    SMALL(8, 14),
    NORMAL(9, 17),
    BIG(10, 22),
    BIGGER(12, 42),
    BIGGEST(16, 130),
    HUMONGOUS(25, 140);

    private final int size;
    private final int clues;
    private int rows;
    private int columns;

    SudokuSize(final int newSize, final int minClues) {
        size = newSize;
        clues = minClues;
        columns = divisor(size);
        rows = size / columns;
    }

    public int getSize() {
        return size;
    }

    public int getClues() {
        return clues;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getRandomEraseCount() {
        int square = size*size;
        int erase = square-(clues + new Random().nextInt((int) (8*Math.pow(square, 0.4) - 19)));
        return erase;
    }

    public static SudokuSize getSize(int size) {
        if (size == TINY.getSize()) {
            return SudokuSize.TINY;
        } else if (size == SMALLER.getSize()) {
            return SudokuSize.SMALLER;
        } else if (size == SMALL.getSize()) {
            return SudokuSize.SMALL;
        } else if (size == NORMAL.getSize()) {
            return SudokuSize.NORMAL;
        } else if (size == BIG.getSize()) {
            return SudokuSize.BIG;
        } else if (size == BIGGER.getSize()) {
            return SudokuSize.BIGGER;
        } else if (size == BIGGEST.getSize()) {
            return SudokuSize.BIGGEST;
        } else if (size == HUMONGOUS.getSize()) {
            return SudokuSize.HUMONGOUS;
        }
        return null;
    }

    public int[][] createGrid() {
        int[][] gridI = new int[size][size];
        for(int r = 0; r < size; r++) {
            for(int c = 0; c < size; c++) {
                gridI[r][c] = 0;
            }
        }
        return gridI;
    }

    private int divisor(int number) {
        List<Integer> numbers = new  ArrayList<Integer>();
        for(int i = 1; i < number; i++) {
            float f = (float) number / (float) i;
            int num = number / i;
            if(f == (float) num) {
                numbers.add(i);
            }
        }
        if(numbers.size() == 0)
            return -1;

        return numbers.get((int) (numbers.size() / 2f + 0.5f));
    }
}
