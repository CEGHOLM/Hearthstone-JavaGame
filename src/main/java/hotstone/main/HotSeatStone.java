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

package hotstone.main;

import hotstone.framework.Game;
import hotstone.framework.Player;
import hotstone.standard.StandardHotStoneGame;
import hotstone.variants.semistone.SemiStoneFactory;
import hotstone.view.core.HotStoneDrawingType;
import hotstone.view.core.HotStoneFactory;
import hotstone.view.tool.HotSeatStateTool;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

/** A single jvm application which uses a 'hotseat' to allow both players to
 * alternate play.
 */
public class HotSeatStone {
  public static void main(String[] args) {

    // Check if args[0] is provided
    if (args.length == 0) {
      System.out.println("Please provide a game variant as an argument.");
      System.out.println("Usage: java HotSeatStone <variant>");
      System.exit(1);
    }

    String variant = args[0];
    System.out.println("=== Starting HotSeat on game variant: " + variant + " ===");

    Game game;

    // Switch on the variant to create the appropriate game instance
    switch (variant.toLowerCase()) {
      case "semistone":
        game = new StandardHotStoneGame(new SemiStoneFactory());
        break;
      default:
        System.out.println("Unknown variant: " + variant);
        System.out.println("Available variants: AlphaStone, BetaStone, GammaStone, DeltaStone, ThetaStone, EpsilonStone, SemiStone");
        System.exit(1);
        return;
    }

    DrawingEditor editor =
            new MiniDrawApplication( "HotSeat: Variant " + variant,
                    new HotStoneFactory(game, Player.FINDUS,
                            HotStoneDrawingType.HOTSEAT_MODE) );
    editor.open();
    editor.setTool(new HotSeatStateTool(editor, game));
  }
}
