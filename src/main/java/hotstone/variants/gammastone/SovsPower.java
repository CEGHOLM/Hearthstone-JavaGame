package hotstone.variants.gammastone;

import hotstone.framework.*;
import hotstone.standard.StandardCard;

import java.util.List;

public class SovsPower implements HeroPowerStrategy {
    @Override
    public void usePower(MutableGame game, MutableHero hero) {
        // Summon "Sovs" card on the owner's field
        Player owner = hero.getOwner();
        List<Card> ownerField = (List<Card>) game.getField(owner);
        ownerField.add(new StandardCard("Sovs", 0,1,1, owner));
    }

    @Override
    public String getEffectDescription() {
        return "Summon Sovs card";
    }
}
