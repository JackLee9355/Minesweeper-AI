public class Main {

    public static final boolean DEBUG = false;

    public static void main(String[] args) {
        Game game = new Game(new AIPlayer(), 5, 10, 15);
        game.start();
    }


}
