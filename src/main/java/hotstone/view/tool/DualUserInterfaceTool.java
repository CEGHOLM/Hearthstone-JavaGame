package hotstone.view.tool;

import frds.broker.IPCException;
import hotstone.framework.Game;
import hotstone.framework.Player;
import hotstone.view.core.HotStoneDrawing;
import hotstone.view.figure.HotStoneFigure;
import hotstone.view.figure.HotStoneFigureType;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.framework.Tool;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class DualUserInterfaceTool extends NullTool {
    protected final Drawing model;
    protected final DrawingEditor editor;
    private Game game;
    private Player whoToPlay;
    private final NullTool theNullTool;
    private Tool state;

    public DualUserInterfaceTool(DrawingEditor editor, Game game, Player whoToPlay) {
        this.editor = editor;
        this.game = game;
        model = editor.drawing();
        state = theNullTool = new NullTool();
        this.whoToPlay = whoToPlay;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        // Find the figure below mouse (x,y)
        Figure figureAtPosition = model.findFigure(e.getX(), e.getY());
        // Iff that figure is associated with our HotStone
        // (All MiniDraw figures that handle HotStone graphics are
        // implementing this role interface).
        if (figureAtPosition instanceof HotStoneFigure) {
            HotStoneFigure hsf = (HotStoneFigure) figureAtPosition;
            if (hsf.getType() == HotStoneFigureType.CARD_FIGURE) {
                state = new PlayCardTool(editor, game, game.getPlayerInTurn());
            } else if (hsf.getType() == HotStoneFigureType.TURN_BUTTON ||
                    hsf.getType() == HotStoneFigureType.SWAP_BUTTON) {
                state = new EndTurnTool(editor, game);
            } else if (hsf.getType() == HotStoneFigureType.MINION_FIGURE) {
                state = new MinionAttackTool(editor, game, game.getPlayerInTurn());
            } else if (hsf.getType() == HotStoneFigureType.HERO_FIGURE) {
                state = new UsePowerTool(editor, game, game.getPlayerInTurn());
            } else if (hsf.getType() == HotStoneFigureType.WIN_BUTTON) {
                // Clicking the 'won button' should do nothing!
                state = theNullTool; // User have to close the window to restart.
            }
        }
        state.mouseDown(e, x, y);
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        try {
            state.mouseUp(e, x, y);
            if (state != theNullTool) {
                System.out.println("----> Bruteforce Redraw");
                model.requestUpdate();
            }
        } catch (IPCException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
        state.mouseDrag(e, x, y);
    }

    @Override
    public void mouseMove(MouseEvent e, int x, int y) {
        state.mouseMove(e, x, y);
    }

}
