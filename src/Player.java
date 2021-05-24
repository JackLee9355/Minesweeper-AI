public interface Player {

    /**
     * @return A int array with a length of two. The values representing the row and column of the location they would like to guess next respectively.
     */
    int[] nextLocation();

    /**
     * For the AI player.
     */
    void setBoard(Board board);
}
