package hotstone.variants.semistone;

import hotstone.framework.HotstoneFactory;
import hotstone.framework.strategies.DeckBuilderStrategy;
import hotstone.framework.strategies.HeroStrategy;
import hotstone.framework.strategies.ManaProductionStrategy;
import hotstone.framework.strategies.WinningStrategy;
import hotstone.standard.StandardRandomStrategy;
import hotstone.variants.betastone.BetaStoneManaStrategy;
import hotstone.variants.betastone.BetaStoneWinnerStrategy;
import hotstone.variants.alphastone.AlphaStoneDeckBuilderStrategy;
import hotstone.variants.etastone.EtaStoneDeckBuilderStrategy;

public class SemiStoneFactory implements HotstoneFactory {
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
        return new SemiStoneHeroStrategy(new StandardRandomStrategy());
    }

    @Override
    public DeckBuilderStrategy createDeckBuilderStrategy() {
        return new EtaStoneDeckBuilderStrategy();
    }
}

