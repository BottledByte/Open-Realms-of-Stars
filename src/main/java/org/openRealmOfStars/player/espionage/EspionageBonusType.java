package org.openRealmOfStars.player.espionage;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2023 Tuomo Untinen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/
 */

/**
* Espionage Bonus Type
*
*/

public enum EspionageBonusType {
  /**
   * Espionage from spy fleet
   */
  SPY_FLEET,
  /**
   * Espionage from trade
   */
  TRADE,
  /**
   * "Espionage" on own realm, bonus for this
   * should be always 10.
   */
  OWN_REALM,
  /**
   * Ruler reveals espionage information.
   */
  CHATTERBOX,
  /**
   * Ruler gains espionage information
   */
  NEGOTIATOR;
}
