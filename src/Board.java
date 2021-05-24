public class Board {

    private Cell[][] arr;
    private int rows;
    private int cols;
    private int mines;

    public Board(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;

        arr = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                arr[i][j] = new Cell(this, i, j);
            }
        }

        populateBoard(mines);
    }

    private void populateBoard(int mines) {
        // Expects an empty board.

        if (mines > rows * cols)
            throw new RuntimeException("Less cells in the board than the amount of mines attempting to populate it");

        // Adds mines.
        for (int i = 0; i < mines; i++) {
            Cell ranCell;

            do {
                int ranRow = (int) (Math.random() * rows);
                int ranCol = (int) (Math.random() * cols);
                ranCell = getCell(ranRow, ranCol);
            } while (ranCell.getState() == CellState.MINE);

            ranCell.setState(CellState.MINE);
        }

        // Updates the numbers.
        CellState[] states = CellState.values();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = getCell(i, j);
                if (cell.getState() == CellState.MINE)
                    continue;
                int adjMines = minesAdjacentTo(i, j);
                CellState state = states[adjMines];
                cell.setState(state);
            }
        }
    }

    public Cell[] cellsAdjacentTo(int row, int col) {
        Cell[] cellsFound = new Cell[8];
        int found = 0;

        int[] shifts = {-1, 0, 1}; // all the directions you need to move to check the adjacent cells including the diagonals.
        for (int rowShift : shifts) {
            for (int colShift : shifts) {
                int newRow = row + rowShift;
                int newCol = col + colShift;
                if ((rowShift == 0 && colShift == 0) || !inBoard(newRow, newCol)) // Excludes checking itself or outside the board.
                    continue;
                Cell newCell = getCell(newRow, newCol);
                cellsFound[found++] = newCell;
            }
        }

        // Trims the array to the correct size;
        Cell[] toReturn = new Cell[found];
        System.arraycopy(cellsFound, 0, toReturn, 0, found);
        return toReturn;
    }

    private int minesAdjacentTo(int row, int col) {
        int count = 0;

        for (Cell cell : cellsAdjacentTo(row, col)) {
            CellState state = cell.getState();
            if (state == CellState.MINE)
                count++;
        }

        return count;
    }

    public Cell getCell(int row, int col) {
        if (!inBoard(row, col))
            throw new RuntimeException(row + ", " + col + " is not within the board."); // Switch to returning null maybe?
        return arr[row][col];
    }

    public boolean inBoard(int row, int col) {
        return 0 <= row && row < rows && 0 <= col && col < cols;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getMines() {
        return mines;
    }

    public int revealedSpaces() {
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (getCell(i, j).isRevealed())
                    count++;
            }
        }

        return count;
    }

    public void display() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        String toReturn = "";

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = getCell(i, j);
                if (Main.DEBUG || cell.isRevealed())
                    toReturn += cell.getState().getDisplay();
                else
                    toReturn += "_";
            }
            toReturn += "\n";
        }

        return toReturn;
    }
}
