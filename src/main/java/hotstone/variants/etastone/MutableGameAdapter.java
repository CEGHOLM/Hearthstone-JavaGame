package hotstone.variants.etastone;

import hotstone.framework.mutability.MutableGame;
import wizardhub.HotStoneGameTarget;

public class MutableGameAdapter implements HotStoneGameTarget {
    private final MutableGame adaptee;

    public MutableGameAdapter(MutableGame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void changeHeroHealth(int player, int amount) {
        adaptee.getHero(player).adjustHealth(amount);
    }

    @Override
    public void changeMinionAttack(int player, int index, int amount) {
        adaptee.getMinion(player, index).adjustAttack(amount);
    }

    @Override
    public int getThePlayerInTurn() {
        return 0;
    }

    @Override
    public int getSizeOfField(int i) {
        return 0;
    }

    @Override
    public void deltaHealthOrRemoveMinion(int i, int i1, int i2) {

    }

    @Override
    public void deltaAttackMinion(int i, int i1, int i2) {

    }

    @Override
    public void setTauntCategoryMinion(int i, int i1, boolean b) {

    }

    @Override
    public void deltaHealthHero(int i, int i1) {

    }

    @Override
    public void drawCard(int i) {

    }

}