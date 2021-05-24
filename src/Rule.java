public class Rule {

    private int mines;
    private Cell[] cells;

    public Rule(int mines, Cell[] cells) {
        this.mines = mines;
        this.cells = cells;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    public Cell[] getCells() {
        return cells;
    }

    public void setCells(Cell[] cells) {
        this.cells = cells;
    }

    public boolean equivalentRule(Rule other) {
        if(cells.length != other.getCells().length)
            return false;

        for (Cell cell : cells) {
            boolean found = false;
            for (Cell otherCell : other.getCells()) {
                if(cell.getRow() == otherCell.getRow() && cell.getColumn() == otherCell.getColumn()) {
                    found = true;
                    break;
                }
            }
            if(!found)
                return false;
        }

        return true && mines == other.getMines();
    }

    public boolean isSubRule(Rule other) {
        if (cells.length >= other.getCells().length)
            return false;

        for (Cell cell : cells) {
            boolean found = false;
            for (Cell otherCell : other.getCells()) {
                if(cell.getRow() == otherCell.getRow() && cell.getColumn() == otherCell.getColumn()) {
                    found = true;
                    break;
                }
            }
            if(!found)
                return false;
        }

        return true;
    }

    public Rule subtractRule(Rule other) { // Expects you to make sure other is a sub-rule of this first.
        Cell[] tmpCells = new Cell[cells.length];
        int numFound = 0;
        int newMines = mines - other.getMines();

        for (Cell cell : cells) {
            boolean found = false;
            for (Cell otherCell : other.getCells()) {
                if(cell.getRow() == otherCell.getRow() && cell.getColumn() == otherCell.getColumn()) {
                    found = true;
                    break;
                }
            }
            if(!found)
                tmpCells[numFound++] = cell;
        }

        Cell[] newCells = new Cell[numFound];
        System.arraycopy(tmpCells, 0, newCells, 0, numFound);
        return new Rule(newMines, newCells);
    }
}