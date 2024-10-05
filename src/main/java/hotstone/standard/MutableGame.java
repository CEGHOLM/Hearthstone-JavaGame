package hotstone.standard;

import hotstone.framework.Card;
import hotstone.framework.Game;
import hotstone.framework.Player;
import hotstone.framework.Status;

interface MutableGame extends Game {
    /**
     * Perform end of turn for current player, to prepare for the
     * opponent's turn.  PRECONDITION: The client MUST ensure that
     * endTurn() is never called by the player which is NOT in turn.
     */
    void endTurn();

    /** Play a card from the hand to the field.
     *
     * PRECONDITION: 'who' is never null.
     * PRECONDITION: the card is a non-null card retrieved by the
     * 'getCardInHand()' method.
     * PRECONDITION: atIndex in range 0 .. getFieldSize(who)
     *
     * @param who player to play card
     * @param card card to play
     * @param atIndex index at which the card should be
     *                entered on the field. 0 = left of
     *                leftmost minion, 1 = between first
     *                and second minion, etc.
     * @return Status of operation
     */
    public Status playCard(Player who, MutableCard card, int atIndex);

    /** Attack one card with another on the fields.
     *
     * PRECONDITION: 'who' is never null.
     * PRECONDITION: both cards are a non-null card retrieved by the
     * 'getCardInField()' method.
     *
     * @param playerAttacking the player making the attack
     * @param attackingCard the card attacking
     * @param defendingCard the card defending
     * @return a status identifying the outcome of the attack, which is
     *    either OK or some value explaining why the action was invalid.
     */
    Status attackCard(Player playerAttacking,
                      MutableCard attackingCard, MutableCard defendingCard);

    /** Attack a hero with a card in the field.
     *
     * PRECONDITION: 'who' is never null.
     * PRECONDITION: the card is a non-null card retrieved by the
     * 'getCardInField()' method.
     *
     * @param playerAttacking the player making the attack
     * @param attackingCard the card attacking
     * @return a status identifying the outcome of the attack, which is
     *    either OK or some value explaining why the action was invalid.
     */
    Status attackHero(Player playerAttacking, MutableCard attackingCard);

    /** Use a hero's special power/effect.
     *
     * PRECONDITION: 'who' is never null.
     *
     * @param who the player using his/her hero's power
     * @return a status identifying the outcome of the power use, which
     *    is either OK or some value explaining why the action was
     *    invalid.
     */
    Status usePower(Player who);

    /** Increase a card attack stats
     *
     * @param who The player who owns the card
     * @param cardIndex The field index of the card
     * @param amount The increase value
     */
    void increaseCardAttack(Player who, int cardIndex, int amount);
}
