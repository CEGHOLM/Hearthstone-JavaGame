package hotstone.view.tool;

import hotstone.framework.Card;
import hotstone.framework.Game;
import hotstone.framework.Player;
import hotstone.framework.Status;
import hotstone.framework.mutability.MutableCard;
import hotstone.view.GfxConstants;
import hotstone.view.figure.CardFigure;
import hotstone.view.figure.HotStoneFigure;
import hotstone.view.figure.HotStoneFigureType;
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
        draggedActor = (CardFigure) figureAtPosition;
        // Move the card to the visual top
        model.zOrder(draggedActor, ZOrder.TO_TOP);
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
        Figure figureAtPosition = model.findFigure(e.getX(), e.getY());

        // Invoke related facade method, if figure is a card or hero
        boolean isDraggingAnActor = draggedActor != null;
        boolean isHittingHero = y < GfxConstants.OPPONENT_HERO_POSITION.y + 200 && x < GfxConstants.OPPONENT_HERO_POSITION.x + 224;
        boolean isHittingMinion = false;
        Card defendingCard = null;

        if (figureAtPosition instanceof CardFigure) {
            defendingCard = ((CardFigure) figureAtPosition).getAssociatedCard();
        }

        // moveCardBack is true because we want to move the card back after the attack or if it failed
        boolean moveCardBack = true;

        // If you are trying to attack a minion
        if (isDraggingAnActor && isHittingMinion) {
            Card attackingCard = draggedActor.getAssociatedCard();

            // Try to attack the minion
            Status status = game.attackCard(whoAmIPlaying, (MutableCard) attackingCard, (MutableCard) defendingCard);

            editor.showStatus("Attack minion. Result =" + status);

            System.out.println(attackingCard.getOwner());
            System.out.println(defendingCard.getOwner());
            System.out.println("Attacking minion at position (" + x + ", " + y + ") with status: " + status);
        }

        // If you are trying to attack a hero
        if (isDraggingAnActor && isHittingHero) {
            Card attackingCard = draggedActor.getAssociatedCard();

            // Try to attack the hero
            Status status = game.attackHero(whoAmIPlaying, (MutableCard) attackingCard);

            editor.showStatus("Attack hero. Result =" + status);

            System.out.println("Attacking hero at position (" + x + ", " + y + ") with status: " + status);
        }
        if (moveCardBack) {
            // Move the card back in case of failed attack or if it was a success
            draggedActor.moveBy(orgX - x, orgY - y);
        }
        draggedActor = null;
    }
}
