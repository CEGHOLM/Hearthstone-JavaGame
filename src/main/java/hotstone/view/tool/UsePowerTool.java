package hotstone.view.tool;

import hotstone.framework.Game;
import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.framework.Status;
import hotstone.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class UsePowerTool extends NullTool {
    protected final DrawingEditor editor;
    private Game game;
    private Player whoAmIPlaying;
    private Hero hero;

    public UsePowerTool(DrawingEditor editor, Game game, Player whoAmIPlaying) {
        this.editor = editor;
        this.game = game;
        this.whoAmIPlaying = whoAmIPlaying;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {}

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        // Find the hero below
        boolean isClickingOnOwnHero = x > GfxConstants.MY_HERO_POSITION.x && y > GfxConstants.MY_HERO_POSITION.y;

        // If you're clicking on the hero, use hero power
        if (isClickingOnOwnHero) {
            Status status = game.usePower(whoAmIPlaying);
            editor.showStatus("Using hero power. Result= " + status);
        }
    }
}
