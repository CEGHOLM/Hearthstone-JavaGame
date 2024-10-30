package hotstone.variants.epsilonstone;

import hotstone.framework.strategies.*;
import hotstone.variants.alphastone.AlphaStoneDeckBuilderStrategy;
import hotstone.variants.alphastone.AlphaStoneManaStrategy;
import hotstone.variants.alphastone.AlphaStoneWinnerStrategy;

public class EpsilonStoneFactory implements HotstoneFactory {
    @Override
    public ManaProductionStrategy createManaProductionStrategy() {
        return new AlphaStoneManaStrategy();
    }

    @Override
    public WinningStrategy createWinningStrategy() {
        return new AlphaStoneWinnerStrategy();
    }

    @Override
    public HeroStrategy createHeroStrategy() {
        return new EpsilonStoneHeroStrategy();
    }

    @Override
    public DeckBuilderStrategy createDeckBuilderStrategy() {
        return new AlphaStoneDeckBuilderStrategy();
    }
}
