package hotstone.adapter;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import wizardhub.HotStoneGameTarget;

import java.util.List;

public class MutableGameAdapter implements HotStoneGameTarget {
    private final MutableGame game;

    public MutableGameAdapter(MutableGame game) {
        this.game = game;
    }

    @Override
    public int getThePlayerInTurn() {
        return convertPlayerToInt(game.getPlayerInTurn());
    }

    // Helper methods for conversion
    private int convertPlayerToInt(Player player) {
        return player == Player.FINDUS ? 0 : 1;
    }

    private Player convertIntToPlayer(int index) {
        return index == 0 ? Player.FINDUS : Player.PEDDERSEN;
    }

    @Override
    public int getSizeOfField(int playerIndex) {
        Player player = convertIntToPlayer(playerIndex);
        return game.getFieldSize(player);
    }

    @Override
    public void deltaHealthOrRemoveMinion(int playerIndex, int minionIndex, int healthIndex) {
        Player player = convertIntToPlayer(playerIndex);
        MutableCard minion = (MutableCard) game.getCardInField(player, minionIndex);

        if (minion != null) {
            minion.takeDamage(healthIndex);
            if (minion.getHealth() <= 0) {
                game.removeMinionFromField(player, minion);
            }
        }
    }

    @Override
    public void deltaAttackMinion(int playerIndex, int minionIndex, int attackIndex) {
        Player player = convertIntToPlayer(playerIndex);

        // Use `minionIndex` to get the card
        List<? extends MutableCard> fieldMinions = (List<? extends MutableCard>) game.getField(player);

        if (fieldMinions != null && minionIndex < fieldMinions.size()) {
            MutableCard minion = fieldMinions.get(minionIndex);
            game.changeMinionAttack(minion, attackIndex);
        }
    }

    @Override
    public void setTauntCategoryMinion(int i, int i1, boolean b) {

    }

    @Override
    public void deltaHealthHero(int playerIndex, int healthIndex) {
        Player player = convertIntToPlayer(playerIndex);
        game.changeHeroHealth(player, healthIndex);
    }

    @Override
    public void drawCard(int playerIndex) {
        Player player = convertIntToPlayer(playerIndex);
        game.drawCard(player);
    }
}