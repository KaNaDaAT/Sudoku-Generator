package SudokuThreadsGUI;

public class Test1 {

    public static void main(String[] args) {
        System.nanoTime();
        long start = 0;
        long end = 0;

        Grid grid;

        start = System.nanoTime();
        grid = new Generator().generate(SudokuSize.BIGGEST, 0);
        end = System.nanoTime();
        System.out.println((end-start) / 1000000 + " ms");

        start = System.nanoTime();
        grid = new Generator().generate(SudokuSize.HUMONGOUS, 0);
        end = System.nanoTime();
        System.out.println((end-start) / 1000000 + " ms");


    }
}
