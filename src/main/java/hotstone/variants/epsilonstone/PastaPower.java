package hotstone.variants.epsilonstone;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.mutability.MutableHero;
import hotstone.framework.strategies.HeroPowerStrategy;
import hotstone.framework.strategies.RandomStrategy;

import java.util.List;

public class PastaPower implements HeroPowerStrategy {

    private RandomStrategy randomStrategy;

    // Brug RandomStrategy i stedet for Random
    public PastaPower(RandomStrategy randomStrategy) {
        this.randomStrategy = randomStrategy;
    }

    @Override
    public void usePower(MutableGame game, MutableHero hero) {
        List<? extends Card> friendlyMinions = (List<? extends Card>) game.getField(hero.getOwner());

        if (!friendlyMinions.isEmpty()) {
            // Brug randomStrategy til at vælge en minion
            int targetIndex = randomStrategy.nextInt(friendlyMinions.size());
            MutableCard target = (MutableCard) friendlyMinions.get(targetIndex);
            target.increaseAttack(2);  // Øg angrebet med 2
        }
    }

    @Override
    public String getEffectDescription() {
        return "Add 2 attack to minion";
    }
}
