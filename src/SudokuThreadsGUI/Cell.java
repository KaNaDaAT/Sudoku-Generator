package SudokuThreadsGUI;

import java.util.Collection;

public class Cell {
    private int value;
    private Collection<Cell> rowNeighbors;
    private Collection<Cell> columnNeighbors;
    private Collection<Cell> boxNeighbors;
    private Cell nextCell;

    public Cell(int value) {
        this.value = value;
    }

    public static int[][] toInt(Cell[][] grid) {
        int[][] gridInt = new int[grid.length][grid[0].length];
        for(int r = 0; r < grid.length; r++) {
            for(int c = 0; c < grid[r].length; c++) {
                gridInt[r][c] = grid[r][c].value;
            }
        }
        return gridInt;
    }

    public int getValue() {
        return value;
    }

    public boolean isEmpty() {
        return value == 0;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Collection<Cell> getRowNeighbors() {
        return rowNeighbors;
    }

    public void setRowNeighbors(Collection<Cell> rowNeighbors) {
        this.rowNeighbors = rowNeighbors;
    }

    public Collection<Cell> getColumnNeighbors() {
        return columnNeighbors;
    }

    public void setColumnNeighbors(Collection<Cell> columnNeighbors) {
        this.columnNeighbors = columnNeighbors;
    }

    public Collection<Cell> getBoxNeighbors() {
        return boxNeighbors;
    }

    public void setBoxNeighbors(Collection<Cell> boxNeighbors) {
        this.boxNeighbors = boxNeighbors;
    }

    public Cell getNextCell() {
        return nextCell;
    }

    public void setNextCell(Cell nextCell) {
        this.nextCell = nextCell;
    }
}
