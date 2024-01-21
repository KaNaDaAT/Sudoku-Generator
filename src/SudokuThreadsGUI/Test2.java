package SudokuThreadsGUI;

public class Test2 {

    public static void main(String[] args) {
        System.nanoTime();
        long start = 0;
        long end = 0;
        Grid grid;
        int[][] sudoku1 = {
                {0, 0, 2, 7, 0, 5, 1, 0, 6},
                {0, 0, 0, 4, 0, 0, 9, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 5, 0},
                {4, 0, 6, 0, 1, 7, 0, 0, 0},
                {2, 0, 0, 0, 0, 0, 0, 7, 5},
                {0, 7, 0, 0, 0, 0, 6, 0, 0},
                {0, 0, 0, 1, 0, 0, 3, 0, 7},
                {9, 0, 0, 0, 0, 4, 2, 0, 0},
                {7, 1, 0, 8, 0, 6, 5, 4, 0}
        };
        int[][] sudoku2 = {
                {1, 0, 5, 0, 6, 0},
                {0, 4, 0, 0, 3, 0},
                {3, 0, 0, 0, 0, 5},
                {0, 0, 1, 3, 0, 0},
                {0, 0, 0, 6, 0, 2},
                {0, 2, 0, 0, 1, 0}
        };
        int[][] sudoku3 = {
                {1, 0, 0, 2, 3, 4, 0, 0, 12, 0, 6, 0, 0, 0, 7, 0},
                {0, 0, 8, 0, 0, 0, 7, 0, 0, 3, 0, 0, 9, 10, 6, 11},
                {0, 12, 0, 0, 10, 0, 0, 1, 0, 13, 0, 11, 0, 0, 14, 0},
                {3, 0, 0, 15, 2, 0, 0, 14, 0, 0, 0, 9, 0, 0, 12, 0},
                {13, 0, 0, 0, 8, 0, 0, 10, 0, 12, 2, 0, 1, 15, 0, 0},
                {0, 11, 7, 6, 0, 0, 0, 16, 0, 0, 0, 15, 0, 0, 5, 13},
                {0, 0, 0, 10, 0, 5, 15, 0, 0, 4, 0, 8, 0, 0, 11, 0},
                {16, 0, 0, 5, 9, 12, 0, 0, 1, 0, 0, 0, 0, 0, 8, 0},
                {0, 2, 0, 0, 0, 0, 0, 13, 0, 0, 12, 5, 8, 0, 0, 3},
                {0, 13, 0, 0, 15, 0, 3, 0, 0, 14, 8, 0, 16, 0, 0, 0},
                {5, 8, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 13, 9, 15, 0},
                {0, 0, 12, 4, 0, 6, 16, 0, 13, 0, 0, 7, 0, 0, 0, 5},
                {0, 3, 0, 0, 12, 0, 0, 0, 0, 0, 0, 4, 11, 0, 0, 16},
                {0, 7, 0, 0, 16, 0, 5, 0, 14, 0, 0, 1, 0, 0, 2, 0},
                {11, 1, 15, 9, 0, 0, 13, 0, 0, 2, 0, 0, 0, 14, 0, 0},
                {0, 14, 0, 0, 0, 11, 0, 2, 0, 0, 13, 3, 5, 0, 0, 12}
        };

        grid = Grid.of(sudoku1);
        start = System.nanoTime();
        System.out.println(Solver.isSolvable(grid));
        end = System.nanoTime();
        System.out.println(formatNs(end-start));

        grid = Grid.of(sudoku2);
        start = System.nanoTime();
        System.out.println(Solver.isSolvable(grid));
        end = System.nanoTime();
        System.out.println(formatNs(end-start));
    }

    private static String formatNs(long time) {
        long ms = time / 1000000;
        long mms = time / 1000 - ms * 1000;
        long ns = time - mms * 1000 - ms * 1000000;
        return ms + "ms " + mms + "µs " + ns + "ns";
    }
}
