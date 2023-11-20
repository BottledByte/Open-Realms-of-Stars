package org.openRealmOfStars.mapTiles;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2019 Tuomo Untinen
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

import org.openRealmOfStars.player.SpaceRace.SpaceRace;

/**
 *
 * All information to draw fleet as a tile
 *
 */

public class FleetTileInfo {

  /**
   * Which race space ship it is.
   */
  private SpaceRace race;

  /**
   * Image index
   */
  private int imageIndex;

  /**
   * Player which has fleet on that position
   */
  private int playerIndex;

  /**
   * fleet index
   */
  private int fleetIndex;

  /**
   * Planet index for orbital.
   */
  private int planetIndex;

  /**
   * Flag for indicating that same tile contains fleets from two
   * different realms.
   */
  private int conflictIndex;

  /**
   * Constructor for Fleet Tile info
   * @param race Space Race information
   * @param index Ship's image index
   * @param playerIndex player index
   * @param fleetIndex player's fleet index
   */
  public FleetTileInfo(final SpaceRace race, final int index,
      final int playerIndex, final int fleetIndex) {
    this.race = race;
    this.imageIndex = index;
    this.playerIndex = playerIndex;
    this.setFleetIndex(fleetIndex);
    this.planetIndex = -1;
    setConflict(-1);
  }

  /**
   * Constructor when fleet tile info is containing orbital.
   * @param race Space race information
   * @param index Ship's image index
   * @param planetIndex Planet index.
   */
  public FleetTileInfo(final SpaceRace race, final int index,
      final int planetIndex) {
    this.race = race;
    this.imageIndex = index;
    this.playerIndex = -1;
    this.setFleetIndex(-1);
    this.planetIndex = planetIndex;
    setConflict(-1);
  }
  /**
   * Get Space race for Fleet
   * @return Space race
   */
  public SpaceRace getRace() {
    return race;
  }

  /**
   * Set space race for fleet tile
   * @param race Space Race
   */
  public void setRace(final SpaceRace race) {
    this.race = race;
  }

  /**
   * Get Fleet Image index
   * @return Image index
   */
  public int getImageIndex() {
    return imageIndex;
  }

  /**
   * Set Fleet image index
   * @param imageIndex Image index for fleet
   */
  public void setImageIndex(final int imageIndex) {
    this.imageIndex = imageIndex;
  }

  /**
   * Get Player index from player list
   * @return Player index
   */
  public int getPlayerIndex() {
    return playerIndex;
  }

  /**
   * Set player index
   * @param playerIndex in player list
   */
  public void setPlayerIndex(final int playerIndex) {
    this.playerIndex = playerIndex;
  }

  /**
   * Get fleet index in player fleets
   * @return Fleet index
   */
  public int getFleetIndex() {
    return fleetIndex;
  }

  /**
   * Set fleet index in player fleets
   * @param fleetIndex Fleet index
   */
  public void setFleetIndex(final int fleetIndex) {
    this.fleetIndex = fleetIndex;
  }

  /**
   * Is there a conflict in tile between different realms.
   * -1 Means that there is no conflict
   * @return the conflictIndex Another realm index
   */
  public int getConflictIndex() {
    return conflictIndex;
  }

  /**
   * Set Conflict index for tile.
   * @param conflict Original realm index in tile.
   */
  public void setConflict(final int conflict) {
    this.conflictIndex = conflict;
  }

  /**
   * Get planet index for getting the orbital information.
   * @return Planet index
   */
  public int getPlanetIndex() {
    return planetIndex;
  }

  /**
   * Set planet index for orbitals.
   * @param planetIndex Planet index having orbital
   */
  public void setPlanetIndex(final int planetIndex) {
    this.planetIndex = planetIndex;
  }

}
