public class Cell {

    private Board board;
    private int row;
    private int column;
    private CellState state;
    private boolean isRevealed;

    public Cell(Board board, int row, int column) {
        this.board = board;
        this.row = row;
        this.column = column;
        this.isRevealed = false;
        this.state = CellState.ZERO;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
