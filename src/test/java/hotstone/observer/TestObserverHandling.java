package hotstone.observer;

import hotstone.framework.mutability.MutableCard;
import hotstone.spies.SpyGameObserver;
import hotstone.standard.StandardHotStoneGame;
import hotstone.variants.gammastone.GammaStoneFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import hotstone.framework.*;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;


public class TestObserverHandling {

    private StandardHotStoneGame game;
    private SpyGameObserver spyObserver;

    @BeforeEach
    public void setUp() {
        game = new StandardHotStoneGame(new GammaStoneFactory());
        spyObserver = new SpyGameObserver();
        game.addObserver(spyObserver);
    }

    @Test
    public void shouldNotifyObserverWhenCardIsPlayed() {
        // Given a game
        // When a card is played
        // Create a mock card and set it up
        MutableCard card = mock(MutableCard.class);
        when(card.getOwner()).thenReturn(Player.FINDUS);
        when(card.getManaCost()).thenReturn(2);

        // Play the card
        game.playCard(Player.FINDUS, card, 0);

        // Then the observer was correctly notified
        assertThat("onPlayCard", is(spyObserver.getLastCall()));
        assertThat(Player.FINDUS, is(spyObserver.getLastPlayer()));
        assertThat(card, is(spyObserver.getLastCard()));
    }

}
