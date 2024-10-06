package hotstone.variants.alphastone;

import hotstone.framework.mutability.MutableGame;
import hotstone.framework.mutability.MutableHero;
import hotstone.framework.strategies.HeroPowerStrategy;

public class BabyPower implements HeroPowerStrategy {
    @Override
    public void usePower(MutableGame game, MutableHero hero) {
    }

    @Override
    public String getEffectDescription() {
        return "Just Cute";
    }
}
