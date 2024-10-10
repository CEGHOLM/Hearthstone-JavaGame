package hotstone.variants.zetastone;

import hotstone.framework.*;
import hotstone.framework.strategies.WinningStrategy;
import hotstone.variants.alphastone.AlphaStoneWinnerStrategy;
import hotstone.variants.betastone.BetaStoneWinnerStrategy;
import hotstone.variants.gammastone.GammaStoneWinnerStrategy;

// ZetaStone Winner Strategy uses the State pattern to switch between different winner determination strategies
public class ZetaStoneWinnerStrategy implements WinningStrategy {
    private WinningStrategy currentState;

    public ZetaStoneWinnerStrategy() {
        // Initial state is like AlphaStone
        currentState = new AlphaStoneWinnerStrategy();
    }
    @Override
    public Player getWinner(Game game) {
        int turn = game.getTurnNumber();

        if (turn > 5 && turn <= 11) {
            // After round 3 up to and including round 6, use Gammastone winner strategy
            currentState = new GammaStoneWinnerStrategy();
        } else {
            // After round 6, use Betastone winner strategy
            currentState = new BetaStoneWinnerStrategy();
        }

        // Decide the winner based in the current state
        return currentState.getWinner(game);
    }
}
