package hotstone.variants.alphastone;

import hotstone.framework.Game;
import hotstone.framework.Player;
import hotstone.framework.strategies.WinningStrategy;

public class AlphaStoneWinnerStrategy implements WinningStrategy {

    @Override
    public Player getWinner(Game game) {
        if (game.getTurnNumber() == 8) {
            return Player.FINDUS;
        }
        return null;
    }
}
