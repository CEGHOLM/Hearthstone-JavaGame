package hotstone.framework;

/**
 * HeroStrategy defines a strategy for assigning a hero to a player.
 * Each game variant can implement its own strategy for hero assignment.
 */
public interface HeroStrategy {
    /**
     * Get the hero assigned to a specific player.
     * @param player The player for whom the hero is assigned.
     * @return The hero assigned to the given player.
     */
    MutableHero getHero(Player player);
}

