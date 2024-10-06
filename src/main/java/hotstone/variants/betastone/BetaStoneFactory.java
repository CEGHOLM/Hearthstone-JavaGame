package hotstone.variants.betastone;

import hotstone.framework.*;
import hotstone.framework.strategies.DeckBuilderStrategy;
import hotstone.framework.strategies.HeroStrategy;
import hotstone.framework.strategies.ManaProductionStrategy;
import hotstone.framework.strategies.WinningStrategy;
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
