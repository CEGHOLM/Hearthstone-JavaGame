package hotstone.framework.mutability;

import hotstone.framework.Card;
import hotstone.standard.StandardHotStoneGame;

/** This interface handles all mutable
 * aspects of the card role
 */
public interface MutableCard extends Card {
    /** Tells us whether or not the card is able to attack
     *
     * @return
     */
    boolean canAttack();

    /** Decreases the cards health by a certain amount
     *
     * @param i
     * @return
     */
    int takeDamage(int i);

    /** Attack an enemy card
     *
     */
    void attack();

    /** Increases the cards attack by a certain amount
     *
     * @param i
     */
    void changeAttack(int i);

    void applyEffect(MutableGame game);
}

