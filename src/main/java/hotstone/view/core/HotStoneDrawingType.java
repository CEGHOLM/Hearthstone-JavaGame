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

package hotstone.view.core;

/** The Drawing can operate in two different modes (only, so no compositional
 * solution but a parametric one): either HOTSEAT or OPPONENT.
 *
 * HOTSEAT allows two players using the same screen while OPPONENT requires
 * a remote/AI player.
 */
public enum HotStoneDrawingType {OPPONENT_MODE, HOTSEAT_MODE}
