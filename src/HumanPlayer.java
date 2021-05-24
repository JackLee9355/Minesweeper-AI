import java.util.Scanner;

public class HumanPlayer implements Player{

    @Override
    public int[] nextLocation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a row: ");
        int row = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter a column: ");
        int col = Integer.parseInt(scanner.nextLine());
        return new int[]{row, col};
    }

    @Override
    public void setBoard(Board board) { }
}
