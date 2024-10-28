package hotstone.framework.strategies;

import hotstone.framework.strategies.DeckBuilderStrategy;
import hotstone.framework.strategies.HeroStrategy;
import hotstone.framework.strategies.ManaProductionStrategy;
import hotstone.framework.strategies.WinningStrategy;

/**
 * The factory for creating the objects that configure a game
 * for a specific hotstone variant
 */
public interface HotstoneFactory {
    /** Create an instance of the mana production strategy*/
    ManaProductionStrategy createManaProductionStrategy();

    /** Create an instance of the winning strategy*/
    WinningStrategy createWinningStrategy();

    /** Create an instance of the hero strategy*/
    HeroStrategy createHeroStrategy();

    /** Create an instance of the deck builder strategy*/
    DeckBuilderStrategy createDeckBuilderStrategy();
}
