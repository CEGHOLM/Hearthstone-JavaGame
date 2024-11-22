/*
 * Copyright (C) 2022 - 2024. Henrik Bærbak Christensen, Aarhus University.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package hotstone.domain2gui;

import hotstone.framework.Game;
import hotstone.framework.Player;
import hotstone.framework.mutability.MutableCard;
import hotstone.standard.StandardHotStoneGame;
import hotstone.variants.alphastone.AlphaStoneFactory;
import hotstone.view.core.HotStoneDrawing;
import hotstone.view.core.HotStoneDrawingType;
import hotstone.view.core.HotStoneFactory;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/** Visual tests of the ability of HotStoneDrawing to respond to
 * observer events notified by the Game instance - i.e. the Domain
 * to the GUI flow of events.
 */
public class ShowUpdate {
  public static void main(String[] args) {
    Game game = new StandardHotStoneGame(new AlphaStoneFactory());

    DrawingEditor editor =
      new MiniDrawApplication( "Click anywhere to progress in an update sequence...",
                               new HotStoneFactory(game, Player.FINDUS,
                                       HotStoneDrawingType.HOTSEAT_MODE) );
    editor.open();
    editor.setTool( new TriggerGameUpdateTool(editor, game) );
  }
}

/** A tool whose only purpose is to trigger a new, visual,
 * test case for each click that the user makes.
 */
class TriggerGameUpdateTool extends NullTool {
  private DrawingEditor editor;
  private Game game;
  private int count;

  public TriggerGameUpdateTool(DrawingEditor editor, Game game) {
    this.editor = editor;
    this.game = game;
    count = 0;
  }

  @Override
  public void mouseUp(MouseEvent e, int x, int y) {
    // Switch on 'which visual test case is the next to execute'
    // REMEMBER that each of the steps below must obey the rules
    // of the given game variant: you cannot play a card if the
    // hero mana is 0, etc.
    switch (count) {
      case 0: {
        editor.showStatus("Playing Findus Card # 2 in hand to field");
        MutableCard c = (MutableCard) game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, c, 0);
        // Note that this visual test correctly shows the minion
        // (as the handed out code already implements this), BUT
        // the Hero's mana is NOT updated, as HotStoneDrawing's
        // onHeroUpdate() method is not implemented.
        break;
      }
      case 1: {
        editor.showStatus("Playing Findus Card # 1 in hand to field");
        MutableCard c = (MutableCard) game.getCardInHand(Player.FINDUS, 1);
        game.playCard(Player.FINDUS, c, 0);
        break;
      }
      case 2: {
        editor.showStatus("Findus is ending turn");
        game.endTurn();
        break;
      }
      case 3: {
        editor.showStatus("Hack - switching UI state so Peddersen's UI becomes active");
        // We know that MiniDraw's Drawing role is configured to be a HotStoneDrawing
        HotStoneDrawing asHotStoneDrawing = (HotStoneDrawing) editor.drawing();
        // Now we force that drawing to end the 'hotseat' state
        asHotStoneDrawing.endHotSeatState();
        // And the UI is incorrect because the FakeObjectGame cannot switch player;
        // so remember to change to a real game variant in the initialization of this
        // visual test program.
        break;
      }
      case 4: {
        editor.showStatus("Playing Peddersen card # 0 in hand to field");
        MutableCard c = (MutableCard) game.getCardInHand(Player.PEDDERSEN, 0);
        game.playCard(Player.PEDDERSEN, c, 0);
        break;
      }
      case 5: {
        editor.showStatus("Peddersen is ending turn");
        game.endTurn();
        break;
      }
      case 6: {
        editor.showStatus("Hack - switching UI state so Findus's UI becomes active");
        HotStoneDrawing asHotStoneDrawing = (HotStoneDrawing) editor.drawing();
        asHotStoneDrawing.endHotSeatState();
        break;
      }
      case 7: {
        editor.showStatus("Findus's card # 0 in field attacks Peddersen's card # 0 in field");
        MutableCard attackingCard = (MutableCard) game.getCardInField(Player.FINDUS, 0);
        MutableCard defendingCard = (MutableCard) game.getCardInField(Player.PEDDERSEN, 0);
        game.attackCard(Player.FINDUS, attackingCard, defendingCard);
        break;
      }
      case 8: {
        editor.showStatus("Findus's card # 0 in field attacks Peddersen's hero");
        MutableCard attackingCard = (MutableCard) game.getCardInField(Player.FINDUS, 0);
        game.attackHero(Player.FINDUS, attackingCard);
        break;
      }
      case 9: {
        editor.showStatus("Findus uses hero power");
        game.usePower(Player.FINDUS);
        break;
      }
      case 10: {
        editor.showStatus("Findus is ending turn");
        game.endTurn();
        break;
      }
      case 11: {
        editor.showStatus("Hack - switching UI state so Peddersens's UI becomes active");
        HotStoneDrawing asHotStoneDrawing = (HotStoneDrawing) editor.drawing();
        asHotStoneDrawing.endHotSeatState();
        break;
      }
      case 12: {
        editor.showStatus("Peddersen is ending turn");
        game.endTurn();
        break;
      }
      case 13: {
        editor.showStatus("Hack - switching UI state so Finuds's UI becomes active");
        HotStoneDrawing asHotStoneDrawing = (HotStoneDrawing) editor.drawing();
        asHotStoneDrawing.endHotSeatState();
        break;
      }
      case 14: {
        editor.showStatus("Findus is ending turn");
        game.endTurn();
        break;
      }
      case 15: {
        editor.showStatus("Hack - switching UI state so Peddersens's UI becomes active");
        HotStoneDrawing asHotStoneDrawing = (HotStoneDrawing) editor.drawing();
        asHotStoneDrawing.endHotSeatState();
        break;
      }
      case 16: {
        editor.showStatus("Peddersen is ending turn");
        game.endTurn();
        break;
      }
      case 17: {
        editor.showStatus("Hack - switching UI state so Finuds's UI becomes active");
        HotStoneDrawing asHotStoneDrawing = (HotStoneDrawing) editor.drawing();
        asHotStoneDrawing.endHotSeatState();
        break;
      }
      case 18: {
        editor.showStatus("Findus is ending turn");
        game.endTurn();
        break;
      }
      case 19: {
        editor.showStatus("Hack - switching UI state so Peddersens's UI becomes active");
        HotStoneDrawing asHotStoneDrawing = (HotStoneDrawing) editor.drawing();
        asHotStoneDrawing.endHotSeatState();
        break;
      }
      case 20: {
        editor.showStatus("Peddersen is ending turn");
        game.endTurn();
        break;
      }
      case 21: {
        editor.showStatus("Hack - switching UI state so Finuds's UI becomes active");
        HotStoneDrawing asHotStoneDrawing = (HotStoneDrawing) editor.drawing();
        asHotStoneDrawing.endHotSeatState();
        break;
      }
      case 22: {
        editor.showStatus("Findus won the game");
        game.getWinner();
      }
      default: {
        editor.showStatus("No more events in the list...");
      }
    }
    // Increment count to prepare to pick a new 'visual test case' in the
    // above list
    count++;
  }
}
