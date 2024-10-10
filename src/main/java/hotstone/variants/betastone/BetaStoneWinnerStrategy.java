package hotstone.variants.betastone;

import hotstone.framework.Game;
import hotstone.framework.Player;
import hotstone.framework.strategies.WinningStrategy;

public class BetaStoneWinnerStrategy implements WinningStrategy {
    @Override
    public Player getWinner(Game game) {
        if (game.getHero(Player.FINDUS).getHealth() <= 0) {
            return Player.PEDDERSEN;
        } else if (game.getHero(Player.PEDDERSEN).getHealth() <= 0) {
            return Player.FINDUS;
        }
        return null;
    }
}
