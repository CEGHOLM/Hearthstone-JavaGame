package hotstone.view.tool;

import hotstone.framework.Game;
import hotstone.view.core.HotStoneDrawing;
import hotstone.view.figure.HotStoneFigure;
import hotstone.view.figure.HotStoneFigureType;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class OpponentButtonTool extends NullTool {
    protected final HotStoneDrawing hotStoneDrawing;
    private Game game;

    public OpponentButtonTool(DrawingEditor editor, Game game) {
        hotStoneDrawing = (HotStoneDrawing) editor.drawing();
        this.game = game;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {}

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        // Find the button below
        Figure figureAtPosition = hotStoneDrawing.findFigure(e.getX(), e.getY());
        if (figureAtPosition instanceof HotStoneFigure) {
            HotStoneFigure hsf = (HotStoneFigure) figureAtPosition;
            // Now, either 'end turn' is hit or 'swap player'
            if (hsf.getType() == HotStoneFigureType.OPPONENT_ACTION_BUTTON) {
                // Update the UI
                hotStoneDrawing.requestUpdate();
            }
        }
    }
}
