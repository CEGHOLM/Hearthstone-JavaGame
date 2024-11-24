package hotstone.broker.service;

import hotstone.framework.Card;
import hotstone.framework.Game;
import hotstone.framework.Hero;

/** A simple 'name service' that allows the server
 * side to map object identities to servant objects.
 */
public interface NameService {

    /** Put a card in the name service under given ID
     *
     * @param id object ID of the card
     * @param card card the servant object
     */
    void addCard(String id, Card card);

    /** Get a card.
     *
     * @param id object ID of the card to get
     * @return the card object with this ID
     */
    Card getCard(String id);

    /** Put a hero in the name service under given ID
     *
     * @param id object ID of the hero
     * @param hero hero the servant object
     */
    void addHero(String id, Hero hero);

    /** Get a hero.
     *
     * @param id object ID of the hero to get
     * @return the hero object with this ID
     */
    Hero getHero(String id);
}
