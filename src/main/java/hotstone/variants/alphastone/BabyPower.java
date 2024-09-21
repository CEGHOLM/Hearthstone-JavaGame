package hotstone.variants.alphastone;

import hotstone.framework.Game;
import hotstone.framework.Hero;
import hotstone.framework.HeroPowerStrategy;

public class BabyPower implements HeroPowerStrategy {
    @Override
    public void usePower(Game game, Hero hero) {
    }

    @Override
    public String getEffectDescription() {
        return "Just Cute";
    }
}
