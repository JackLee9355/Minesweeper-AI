public class Game {

    private Player player;
    private Board board;

    public Game(Player player, int rows, int cols, int mines) {
        this.player = player;
        this.board = new Board(rows, cols, mines);
        this.player.setBoard(this.board);
    }

    public boolean start() {

        board.display();

        while(board.getRows() * board.getCols() - board.getMines() > board.revealedSpaces()) {
            int[] loc = player.nextLocation();
            int row = loc[0];
            int col = loc[1];
            if(!board.inBoard(row, col) || board.getCell(row, col).isRevealed()) {
                System.out.println("This cell can't be revealed");
                continue;
            }

            Cell cell = board.getCell(row, col);
            cell.setRevealed(true);
            board.display();
            if(cell.getState() == CellState.MINE) {
                System.out.println("BOOM! You lose!");
                return false;
            } else {
                System.out.println("Nice guess. The cell was revealed.");
            }
        }

        System.out.println("All the non-mine squares are revealed! You Win!");

        return true;
    }

}
