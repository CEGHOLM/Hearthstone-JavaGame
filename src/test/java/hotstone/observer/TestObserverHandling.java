package hotstone.observer;

import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.strategies.WinningStrategy;
import hotstone.spies.SpyGameObserver;
import hotstone.standard.StandardHotStoneGame;
import hotstone.utility.TestHelper;
import hotstone.variants.alphastone.AlphaStoneFactory;
import hotstone.variants.alphastone.AlphaStoneWinnerStrategy;
import hotstone.variants.epsilonstone.EpsilonStoneFactory;
import hotstone.variants.gammastone.GammaStoneFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import hotstone.framework.*;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;


public class TestObserverHandling {

    private MutableGame game;
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
        assertThat(spyObserver.getCallHistory(), hasItem("onPlayCard"));
        assertThat(spyObserver.getLastPlayer(), is(Player.FINDUS));
        assertThat(spyObserver.getLastAttackingCard(), is(card));
        assertThat(spyObserver.getLastIndex(), is(0));
    }

    @Test
    public void shouldNotifyObserverWhenTurnIsChanged() {
        // Given a game
        // When the turn ends
        game.endTurn();
        // Then the observer should be correctly notified
        assertThat(spyObserver.getLastCall(), is("onChangeTurnTo"));
        assertThat(spyObserver.getLastPlayer(), is(Player.PEDDERSEN));
    }

    @Test
    public void shouldNotifyObserverWhenAttackingACard() {
        // Given a game
        // When attacking another card
        // Create mocks of the cards
        MutableCard attackingCard = mock(MutableCard.class);
        MutableCard defendingCard = mock(MutableCard.class);

        // Add the cards to each players field
        when(attackingCard.getHealth()).thenReturn(1);
        when(defendingCard.getHealth()).thenReturn(1);
        when(attackingCard.getOwner()).thenReturn(Player.FINDUS);
        when(defendingCard.getOwner()).thenReturn(Player.PEDDERSEN);
        game.addCardToField(Player.FINDUS, attackingCard);
        game.addCardToField(Player.PEDDERSEN, defendingCard);

        // Activate cards
        when(attackingCard.canAttack()).thenReturn(true);

        // Initiate the attack
        game.attackCard(Player.FINDUS, attackingCard, defendingCard);

        // Then the observer should be correctly notified
        assertThat(spyObserver.getLastCall(), is("onAttackCard"));
        assertThat(spyObserver.getLastPlayer(), is(Player.FINDUS));
        assertThat(spyObserver.getLastAttackingCard(), is(attackingCard));
        assertThat(spyObserver.getLastDefendingCard(), is(defendingCard));
    }

    @Test
    public void shouldNotifyObserverWhenCardHealthStatChanges() {
        // Given a game
        // When a card loses health
        // Create mocks of the cards
        MutableCard attackingCard = mock(MutableCard.class);
        MutableCard defendingCard = mock(MutableCard.class);

        // Add the cards to each players field
        when(attackingCard.getOwner()).thenReturn(Player.FINDUS);
        when(defendingCard.getOwner()).thenReturn(Player.PEDDERSEN);
        game.addCardToField(Player.FINDUS, attackingCard);
        game.addCardToField(Player.PEDDERSEN, defendingCard);

        // Set necessary card stats and activate
        when(attackingCard.getHealth()).thenReturn(5);
        when(attackingCard.canAttack()).thenReturn(true);
        when(defendingCard.getAttack()).thenReturn(3);
        when(defendingCard.getHealth()).thenReturn(1);

        // Initiate the attack
        game.attackCard(Player.FINDUS, attackingCard, defendingCard);

        // Then the observer should be correctly notified
        assertThat(spyObserver.getLastCall(), is("onCardUpdate"));
        assertThat(spyObserver.getLastAttackingCard(), is(attackingCard));
    }

    @Test
    public void shouldNotifyObserverWhenCardAttackStatChanges() {
        // Given a game
        game = new StandardHotStoneGame(new EpsilonStoneFactory());
        game.addObserver(spyObserver);
        // When a card gains attack
        // Create a mock of the card
        MutableCard card = mock(MutableCard.class);

        // Add the card to field
        when(card.getOwner()).thenReturn(Player.PEDDERSEN);
        game.addCardToField(Player.PEDDERSEN, card);

        // Use hero power
        game.endTurn();
        game.usePower(Player.PEDDERSEN);

        // Then the observer should be correctly notified
        assertThat(spyObserver.getCallHistory(), hasItem("onCardUpdate"));
        assertThat(spyObserver.getLastAttackingCard(), is(card));
    }

    @Test
    public void shouldNotifyObserverWhenCardIsRemoved() {
        // Given a game
        // When a card is removed
        // Create mocks of the cards
        MutableCard attackingCard = mock(MutableCard.class);
        MutableCard defendingCard = mock(MutableCard.class);

        // Add the cards to each players field
        when(attackingCard.getOwner()).thenReturn(Player.FINDUS);
        when(defendingCard.getOwner()).thenReturn(Player.PEDDERSEN);
        game.addCardToField(Player.FINDUS, attackingCard);
        game.addCardToField(Player.PEDDERSEN, defendingCard);

        // Set necessary card stats and activate
        when(attackingCard.getHealth()).thenReturn(1);
        when(attackingCard.canAttack()).thenReturn(true);
        when(defendingCard.getHealth()).thenReturn(0);

        // Initiate the attack
        game.attackCard(Player.FINDUS, attackingCard, defendingCard);

        // Then the observer should be correctly notified
        assertThat(spyObserver.getLastCall(), is("onCardRemove"));
        assertThat(spyObserver.getLastPlayer(), is(Player.PEDDERSEN));
        assertThat(spyObserver.getLastAttackingCard(), is(defendingCard));
    }

    @Test
    public void shouldNotifyObserverWhenHeroIsAttacked() {
        // Given a game
        // When a card attack a hero
        // Create a mock of the card
        MutableCard card = mock(MutableCard.class);

        // Add card to field
        when(card.getOwner()).thenReturn(Player.FINDUS);
        game.addCardToField(Player.FINDUS, card);

        // Set necessary card stats and activate
        when(card.getHealth()).thenReturn(1);
        when(card.canAttack()).thenReturn(true);

        // Initiate the attack
        game.attackHero(Player.FINDUS, card);

        // Then the observer should be correctly notified
        assertThat(spyObserver.getCallHistory(), hasItem("onAttackHero"));
        assertThat(spyObserver.getLastAttackingCard(), is(card));
    }

    @Test
    public void shouldNotifyObserverWhenHeroHealthChanges() {
        // Given a game
        // When a hero loses health
        // Create a mock of the card
        MutableCard card = mock(MutableCard.class);

        // Add card to field
        when(card.getOwner()).thenReturn(Player.FINDUS);
        game.addCardToField(Player.FINDUS, card);

        // Set necessary card stats and activate
        when(card.getHealth()).thenReturn(1);
        when(card.getAttack()).thenReturn(1);
        when(card.canAttack()).thenReturn(true);

        // Initiate the attack
        game.attackHero(Player.FINDUS, card);

        // Then the observer should be correctly notified
        assertThat(spyObserver.getCallHistory(), hasItem("onHeroUpdate"));
        assertThat(spyObserver.getLastPlayer(), is(Player.PEDDERSEN));
    }

    @Test
    public void shouldNotifyObserverWhenUsingHeroPower() {
        // Given a game
        // When Findus uses his hero power
        game.usePower(Player.FINDUS);
        // Then the observer should be correctly notified
        assertThat(spyObserver.getCallHistory(), hasItem("onUsePower"));
        assertThat(spyObserver.getLastPlayer(), is(Player.FINDUS));
    }

    @Test
    public void shouldNotifyObserverWhenSomeOneWinsTheGame() {
        // Given a game (gammaStone)
        // When the winner is found
        TestHelper.advanceGameNRounds(game, 3);
        game.getWinner();
        // Then the observer should be correctly notified
        assertThat(spyObserver.getLastCall(), is("onGameWon"));
        assertThat(spyObserver.getLastPlayer(), is(Player.PEDDERSEN));
    }

    @Test
    public void shouldNotNotifyObserverIfWinnerIsNull() {
        // Given a game (gammaStone)
        // When the asked for the winner after two rounds
        TestHelper.advanceGameNRounds(game, 2);
        game.getWinner();
        // Then the observer should not be notified of a winner
        assertThat(spyObserver.getLastCall(), is(not("onGameWon")));
    }

    @Test
    public void shouldNotifyObserverIfCardIsDrawn() {
        // Given a game
        // When a card is drawn
        // Advance game so Findus draws a card
        TestHelper.advanceGameNRounds(game, 1);
        Card card = game.getCardInHand(Player.FINDUS, 0);

        // Then the observer should be correctly notified
        assertThat(spyObserver.getLastCall(), is("onCardDraw"));
        assertThat(spyObserver.getLastPlayer(), is(Player.FINDUS));
        assertThat(spyObserver.getLastAttackingCard(), is(card));
    }

    @Test
    public void shouldNotifyObserverWhenHeroManaIsChangedByPlayingACard() {
        // Given a game
        // When a player uses mana on playing a card
        // Create a mock of card
        MutableCard card = mock(MutableCard.class);
        when(card.getOwner()).thenReturn(Player.FINDUS);
        when(card.getManaCost()).thenReturn(2);

        // Play the card
        game.playCard(Player.FINDUS, card, 0);

        // Then the observer should be correctly notified
        assertThat(spyObserver.getLastCall(), is("onHeroUpdate"));
        assertThat(spyObserver.getLastPlayer(), is(Player.FINDUS));
    }

    @Test
    public void shouldNotifyObserverWhenHeroManaIsChangedByUsingHeroPower() {
        // Given a game
        // When Findus uses his hero power
        game.usePower(Player.FINDUS);
        // Then the observer should be correctly notified
        assertThat(spyObserver.getLastCall(), is("onHeroUpdate"));
        assertThat(spyObserver.getLastPlayer(), is(Player.FINDUS));
    }

    @Test
    public void shouldTriggerTheCorrectObserversWhenPeddersenUsesSovsPower() {
        // Given a game
        // When Peddersen uses his hero power (sovs power)
        game.endTurn();
        game.usePower(Player.PEDDERSEN);
        // Then all the correct observers should be triggered
        assertThat(spyObserver.getCallHistory(), hasItem("onPlayCard"));
        assertThat(spyObserver.getCallHistory(), hasItem("onUsePower"));
        assertThat(spyObserver.getCallHistory(), hasItem("onHeroUpdate"));
        assertThat(spyObserver.getLastPlayer(), is(Player.PEDDERSEN));
        assertThat(spyObserver.getLastAttackingCard().getName(), is("Sovs"));
    }
}
