package hotstone.framework;

public interface WinningStrategy {
    /**
     * Decides if there is a winner in the game
     * @param game, the game that evaluates
     * @return The winner or null if there is no winner yet
     */
    Player getWinner(Game game);
}
