package hotstone.variants.etastone;

import hotstone.framework.HotstoneFactory;
import hotstone.framework.strategies.DeckBuilderStrategy;
import hotstone.framework.strategies.HeroStrategy;
import hotstone.framework.strategies.ManaProductionStrategy;
import hotstone.framework.strategies.WinningStrategy;
import hotstone.variants.alphastone.AlphaStoneHeroStrategy;
import hotstone.variants.alphastone.AlphaStoneWinnerStrategy;
import hotstone.variants.deltastone.DeltaStoneManaStrategy;

public class EtaStoneFactory implements HotstoneFactory {
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
        return new EtaStoneDeckBuilderStrategy();
    }
}
