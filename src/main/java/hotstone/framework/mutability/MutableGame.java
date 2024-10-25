package hotstone.framework.mutability;

import hotstone.framework.Game;
import hotstone.framework.Player;
import hotstone.framework.Status;
import hotstone.standard.StandardCard;

public interface MutableGame extends Game {
    void changeHeroHealth(Player player, int i);

    void drawCard(Player player);

    void removeMinionFromField(Player player, MutableCard card);
}

