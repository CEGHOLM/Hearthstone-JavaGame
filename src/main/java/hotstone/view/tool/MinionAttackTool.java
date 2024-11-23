package hotstone.view.tool;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableCard;
import hotstone.view.GfxConstants;
import hotstone.view.figure.CardFigure;
import hotstone.view.figure.HeroFigure;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.framework.ZOrder;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class MinionAttackTool extends NullTool {
    private DrawingEditor editor;
    private Game game;
    private CardFigure draggedActor;
    private int lastX;
    private int lastY;
    private int orgX;
    private int orgY;
    private Player whoAmIPlaying;

    public MinionAttackTool(DrawingEditor editor, Game game, Player whoAmIPlaying) {
        this.editor = editor;
        this.game = game;
        this.whoAmIPlaying = whoAmIPlaying;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        Drawing model = editor.drawing();
        // The HotSeatStateTool should ensure that this tool is only active if there is a card figure below (x,y)
        Figure figureAtPosition = model.findFigure(e.getX(), e.getY());
        if (figureAtPosition != null) {
            System.out.println("[MinionAttackTool] mouseDown on figure: " + figureAtPosition);
        } else {
            System.out.println("[MinionAttackTool] mouseDown on empty space.");
        }
        draggedActor = (CardFigure) figureAtPosition;
        // Move the card to the visual top
        model.zOrder(draggedActor, ZOrder.TO_BOTTOM);
        // And remember where the card was dragged from (orgX, orgY)
        lastX = x; lastY = y;
        orgX = x; orgY = y;
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
        // compute relative movement
        draggedActor.moveBy(x - lastX, y - lastY);
        // update last position
        lastX = x; lastY = y;
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        Drawing model = editor.drawing();
        Figure figureAtPosition = model.findFigure(x, y);

        boolean isDraggingAnActor = draggedActor != null;
        boolean isHittingEnemyHero = false;
        boolean isHittingMinion = false;
        Card defendingCard = null;
        Card attackingCard = draggedActor.getAssociatedCard();

        // moveCardBack is always true because we want to move the card back after the attack or if it failed
        boolean moveCardBack = true;

        // Find out if it is a card you're trying to attack
        if (figureAtPosition instanceof CardFigure) {
            defendingCard = ((CardFigure) figureAtPosition).getAssociatedCard();
            isHittingMinion = true;
        }

        // If you are trying to attack a minion
        if (isDraggingAnActor && isHittingMinion) {
            // Try to attack the minion
            Status status = game.attackCard(whoAmIPlaying, (MutableCard) attackingCard, (MutableCard) defendingCard);

            editor.showStatus("Attack minion. Result =" + status);
        }

        // Find out if it is a hero you're trying to attack
        if (figureAtPosition instanceof  HeroFigure) {
            isHittingEnemyHero = !((HeroFigure) figureAtPosition).getAssociatedHero().getOwner().equals(whoAmIPlaying);
        }

        // If you are trying to attack a hero
        if (isDraggingAnActor && isHittingEnemyHero) {
            // Try to attack the hero
            Status status = game.attackHero(whoAmIPlaying, (MutableCard) attackingCard);

            editor.showStatus("Attack hero. Result =" + status);
        }
        if (moveCardBack) {
            // Move the card back in case of failed attack or if it was a success
            draggedActor.moveBy(orgX - x, orgY - y);
        }
        draggedActor = null;
    }
}
