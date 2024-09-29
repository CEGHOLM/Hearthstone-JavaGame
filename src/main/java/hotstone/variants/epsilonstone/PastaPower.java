package hotstone.variants.epsilonstone;

import hotstone.framework.*;

import java.util.List;
import java.util.Random;

public class PastaPower implements HeroPowerStrategy {

    @Override
    public void usePower(Game game, Hero hero) {
        List<Card> friendlyMinions = (List<Card>) game.getField(hero.getOwner());
        if (!friendlyMinions.isEmpty()) {
            // Use the hero's random generator to pick a friendly minion
            Random random = hero.getRandomGenerator();
            int targetIndex = random.nextInt(friendlyMinions.size());
            Card target = friendlyMinions.get(targetIndex);
            target.increaseAttack(2); // Increase attack by 2
        }
    }

    @Override
    public String getEffectDescription() {
        return "Add 2 attack to minion";
    }
}
