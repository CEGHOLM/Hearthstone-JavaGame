package hotstone.variants.gammastone;

import hotstone.framework.strategies.*;
import hotstone.variants.alphastone.AlphaStoneDeckBuilderStrategy;
import hotstone.variants.alphastone.AlphaStoneManaStrategy;

public class GammaStoneFactory implements HotstoneFactory {
    @Override
    public ManaProductionStrategy createManaProductionStrategy() {
        return new AlphaStoneManaStrategy();
    }

    @Override
    public WinningStrategy createWinningStrategy() {
        return new GammaStoneWinnerStrategy();
    }

    @Override
    public HeroStrategy createHeroStrategy() {
        return new GammaStoneHeroStrategy();
    }

    @Override
    public DeckBuilderStrategy createDeckBuilderStrategy() {
        return new AlphaStoneDeckBuilderStrategy();
    }
}
