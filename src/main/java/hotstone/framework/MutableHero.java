package hotstone.framework;

/** This interface handles all the mutable areas,
 * connected to the heroes.
 */
public interface MutableHero extends Hero {
    /** Set the heroes mana
     *
     * @param i
     */
    void setMana(int i);

    /** Set the status of the power,
     * whether it is usable or not
     * @param b
     */
    void setPowerStatus(boolean b);

    /** Set the heroes health
     *
     * @param i
     */
    void setHealth(int i);

    /** Decrease the heroes health by a certain amount
     *
     * @param i
     */
    void takeDamage(int i);

    /** Use the heroes power
     *
     * @param game
     */
    void usePower(MutableGame game);
}

