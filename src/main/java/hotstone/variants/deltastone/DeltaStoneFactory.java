package hotstone.variants.deltastone;

import hotstone.framework.*;
import hotstone.variants.alphastone.AlphaStoneHeroStrategy;
import hotstone.variants.alphastone.AlphaStoneWinnerStrategy;

public class DeltaStoneFactory implements HotstoneFactory {
    @Override
    public ManaProductionStrategy createManaProductionStrategy() {
        return new DeltaStoneManaStrategy();
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
        return new DeltaStoneDeckBuilderStrategy();
    }
}
