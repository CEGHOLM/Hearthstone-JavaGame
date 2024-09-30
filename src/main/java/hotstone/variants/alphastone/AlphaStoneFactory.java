package hotstone.variants.alphastone;

import hotstone.framework.*;

public class AlphaStoneFactory implements HotstoneFactory {
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
        return new AlphaStoneHeroStrategy();
    }

    @Override
    public DeckBuilderStrategy createDeckBuilderStrategy() {
        return new AlphaStoneDeckBuilderStrategy();
    }
}
