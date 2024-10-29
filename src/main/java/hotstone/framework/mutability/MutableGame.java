package hotstone.framework.mutability;

import hotstone.framework.Game;
import hotstone.framework.Player;
import hotstone.framework.Status;
import hotstone.standard.StandardCard;

public interface MutableGame extends Game {

    /** Change the health of the players hero,
     * when losing or gaining health
     *
     * @param player The player whose hero's health we are changing
     * @param i The amount the health is changed by
     */
    void changeHeroHealth(Player player, int i);

    /** Draw a card from the players deck
     *
     * @param player The player whose deck we're drawing from
     */
    void drawCard(Player player);

    /** Remove minions from the field, when dead or eliminated
     *
     * @param player The players whose field we're removing from.
     * @param card The minion we're removing (when cards are played, they become minions)
     */
    void removeMinionFromField(Player player, MutableCard card);

    void changeMinionAttack(MutableCard card, int i);

    void addCardToField(Player who, MutableCard card);

    int getMinionCount(int i);

    Object getMinion(int player, int index);
}

