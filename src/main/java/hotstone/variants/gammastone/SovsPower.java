package hotstone.variants.gammastone;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableGame;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardCard;
import hotstone.variants.NullEffect;

import java.util.List;

public class SovsPower implements Effect {

    @Override
    public void applyEffect(MutableGame game, Player player) {
        // Summon "Sovs" card on the owner's field
        game.playCard(player, new StandardCard(GameConstants.SOVS_CARD, 0,1,1, player, new NullEffect()), 0);
    }

    @Override
    public String getEffectDescription() {
        return "Summon Sovs card";
    }
}
