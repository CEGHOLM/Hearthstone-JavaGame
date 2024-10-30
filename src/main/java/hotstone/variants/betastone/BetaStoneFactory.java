package hotstone.variants.betastone;

import hotstone.framework.strategies.*;
import hotstone.variants.alphastone.AlphaStoneDeckBuilderStrategy;
import hotstone.variants.alphastone.AlphaStoneHeroStrategy;

public class BetaStoneFactory implements HotstoneFactory {
    @Override
    public ManaProductionStrategy createManaProductionStrategy() {
        return new BetaStoneManaStrategy();
    }

    @Override
    public WinningStrategy createWinningStrategy() {
        return new BetaStoneWinnerStrategy();
    }

    @Override
    public HeroStrategy createHeroStrategy() {
        return new AlphaStoneHeroStrategy();
    }

    @Override
    public DeckBuilderStrategy createDeckBuilderStrategy() {
        return new AlphaStoneDeckBuilderStrategy();
    }
}
