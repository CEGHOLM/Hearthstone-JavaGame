package hotstone.observer;

import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.spies.SpyGameObserver;
import hotstone.standard.StandardHotStoneGame;
import hotstone.utility.TestHelper;
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
        assertThat(card, is(spyObserver.getLastAttackingCard()));
        assertThat(0, is(spyObserver.getLastIndex()));
    }

    @Test
    public void shouldNotifyObserverWhenTurnIsChanged() {
        // Given a game
        // When the turn ends
        game.endTurn();
        // Then the observer should be correctly notified
        assertThat("onChangeTurnTo", is(spyObserver.getLastCall()));
        assertThat(Player.PEDDERSEN, is(spyObserver.getLastPlayer()));
    }

    @Test
    public void shouldNotifyObserverWhenAttackingACard() {
        // Given a game
        // When attacking another card
        // Create mocks of the cards
        MutableCard attackingCard = mock(MutableCard.class);
        MutableCard defendingCard = mock(MutableCard.class);

        // Add the cards to each players field
        when(attackingCard.getOwner()).thenReturn(Player.FINDUS);
        when(defendingCard.getOwner()).thenReturn(Player.PEDDERSEN);
        game.addCardToField(Player.FINDUS, attackingCard);
        game.addCardToField(Player.PEDDERSEN, defendingCard);

        // Activate cards
        when(attackingCard.canAttack()).thenReturn(true);
        when(defendingCard.canAttack()).thenReturn(true);

        // Then the observer should be correctly notified
        assertThat("onAttackCard", is(spyObserver.getLastCall()));
        assertThat(Player.FINDUS, is(spyObserver.getLastPlayer()));
        assertThat(attackingCard, is(spyObserver.getLastAttackingCard()));
        assertThat(defendingCard, is(spyObserver.getLastDefendingCard()));
    }

}
