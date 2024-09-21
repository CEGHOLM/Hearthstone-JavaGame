package hotstone.variants.gammastone;

import hotstone.framework.Game;
import hotstone.framework.Player;
import hotstone.framework.WinningStrategy;

public class GammaStoneWinnerStrategy implements WinningStrategy {
    @Override
    public Player getWinner(Game game) {
        int turnNumber = game.getTurnNumber();
        if (turnNumber >= 6) {
            if (game.getFieldSize(Player.FINDUS) == 0 && game.getFieldSize(Player.PEDDERSEN) > 0) {
                return Player.PEDDERSEN;
            } else if (game.getFieldSize(Player.PEDDERSEN) == 0 && game.getFieldSize(Player.FINDUS) > 0) {
                return Player.FINDUS;
            } else if (game.getFieldSize(Player.FINDUS) == 0 && game.getFieldSize(Player.PEDDERSEN) == 0) {
                return Player.PEDDERSEN;
            }
        }
        return null;
    }
}
