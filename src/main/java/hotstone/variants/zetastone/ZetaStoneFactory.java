package hotstone.variants.zetastone;

import hotstone.framework.*;
import hotstone.variants.alphastone.AlphaStoneDeckBuilderStrategy;
import hotstone.variants.alphastone.AlphaStoneHeroStrategy;
import hotstone.variants.alphastone.AlphaStoneManaStrategy;

public class ZetaStoneFactory implements HotstoneFactory {
    @Override
    public ManaProductionStrategy createManaProductionStrategy() {
        return new AlphaStoneManaStrategy();
    }

    @Override
    public WinningStrategy createWinningStrategy() {
        return new ZetaStoneWinnerStrategy();
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
