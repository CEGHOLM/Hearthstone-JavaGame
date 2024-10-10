package hotstone.variants.semistone;

import hotstone.framework.Player;
import hotstone.framework.mutability.MutableHero;
import hotstone.framework.strategies.HeroStrategy;
import hotstone.framework.strategies.RandomStrategy;
import hotstone.standard.GameConstants;
import hotstone.variants.StubRandomStrategy;
import org.junit.jupiter.api.Test;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestSemiStoneHeroStrategy {

    @Test
    public void shouldChooseThaiChefWhenRandomIsZero() {
        // Given a SemiStoneHeroStrategy with a stub
        // When random is 0
        RandomStrategy randomStrategy = new StubRandomStrategy(0);
        HeroStrategy heroStrategy = new SemiStoneHeroStrategy(randomStrategy);

        // Get the hero
        MutableHero hero = heroStrategy.getHero(Player.FINDUS);

        // Then the hero should be Thai chef
        assertThat(hero.getType(), is(GameConstants.THAI_CHEF_HERO_TYPE));
    }

    @Test
    public void shouldChooseDanishChefWhenRandomIsOne() {
        // Given a SemiStoneHeroStrategy with a stub
        // When random is 1
        RandomStrategy randomStrategy = new StubRandomStrategy(1);
        HeroStrategy heroStrategy = new SemiStoneHeroStrategy(randomStrategy);

        // Get the hero
        MutableHero hero = heroStrategy.getHero(Player.FINDUS);

        // Then the hero should be Danish chef
        assertThat(hero.getType(), is(GameConstants.DANISH_CHEF_HERO_TYPE));
    }

    @Test
    public void shouldChooseFrenchChefWhenRandomIsTwo() {
        // Given a SemiStoneHeroStrategy with a stub
        // When random is 2
        RandomStrategy randomStrategy = new StubRandomStrategy(2);
        HeroStrategy heroStrategy = new SemiStoneHeroStrategy(randomStrategy);

        // Get the hero
        MutableHero hero = heroStrategy.getHero(Player.FINDUS);

        // Then the hero should be French chef
        assertThat(hero.getType(), is(GameConstants.FRENCH_CHEF_HERO_TYPE));
    }

    @Test
    public void shouldChooseItalianChefWhenRandomIsThree() {
        // Given a SemiStoneHeroStrategy with a stub
        // When random is 3
        RandomStrategy randomStrategy = new StubRandomStrategy(3);
        HeroStrategy heroStrategy = new SemiStoneHeroStrategy(randomStrategy);

        // Get the hero
        MutableHero hero = heroStrategy.getHero(Player.FINDUS);

        // Then the hero should be Italian chef
        assertThat(hero.getType(), is(GameConstants.ITALIAN_CHEF_HERO_TYPE));
    }
}
