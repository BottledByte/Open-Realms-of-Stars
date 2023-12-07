package org.openRealmOfStars.game;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2023 Tuomo Untinen
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;

import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.utilities.repository.GameRepository;

/**
 *
 * Saved game information
 *
 */
public class SavedGame implements Comparable<SavedGame> {

  /**
   * Player's race
   */
  private SpaceRace playerRace;

  /**
   * Which star year
   */
  private int starYear;

  /**
   * Number of realms, not counting pirates/monsters
   */
  private int realms;
  /**
   * Galaxy size as string
   */
  private String galaxySize;

  /**
   * Filename of saved game file
   */
  private String filename;

  /**
   * First player's empire name
   */
  private String empireName;

  /**
   * File's creation time
   */
  private String creationTime;
  /**
   * File's creation time in milli seconds.
   */
  private long creationTimeMillis;

  /**
   * Load game from certain file name and get all information from saved
   * game
   * @param folderName Folder name where to load saved games
   * @param filename File name
   * @throws IOException if reading fails
   */
  public SavedGame(final String folderName, final String filename)
      throws IOException {
    File file = new File(folderName + "/" + filename);
    BasicFileAttributes attr = Files.readAttributes(file.toPath(),
        BasicFileAttributes.class);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    creationTimeMillis = attr.creationTime().toMillis();
    creationTime = dateFormat.format(creationTimeMillis);
    StarMap starMap = new GameRepository().loadGame(folderName, filename);
    if (starMap == null) {
      throw new IOException("Error while reading the saved game: "
          + folderName + "/" + filename);
    }
    this.filename = filename;
    starYear = starMap.getTurn() + starMap.getStartStarYear();
    realms = starMap.getPlayerList().getCurrentMaxRealms();
    galaxySize = starMap.getMaxX() + " X " + starMap.getMaxY();
    playerRace = starMap.getPlayerList().getPlayerInfoByIndex(0).getRace();
    empireName = starMap.getPlayerList().getPlayerInfoByIndex(0)
        .getEmpireName();
  }

  /**
   * Get first player's space race.
   * @return Space race
   */
  public SpaceRace getPlayerRace() {
    return playerRace;
  }

  /**
   * Get which star year it was on saved game.
   * @return Star year
   */
  public int getStarYear() {
    return starYear;
  }

  /**
   * Get galaxy size as a textual information.
   * @return Galaxy size as a text
   */
  public String getGalaxySize() {
    return galaxySize;
  }

  /**
   * Get save game file name
   * @return File name as a String
   */
  public String getFilename() {
    return filename;
  }

  /**
   * Get first player's empire name
   * @return Empire name as a String.
   */
  public String getEmpireName() {
    return empireName;
  }

  /**
   * Get save file's creation time
   *
   * @return save file's creation time
   */
  public String getTime() {
    return creationTime;
  }

  /**
   * Get number of realms in game.
   * @return Number of realms, not counting pirates/monsters
   */
  public int getRealms() {
    return realms;
  }

  @Override
  public int compareTo(final SavedGame arg0) {
    if (this.creationTimeMillis < arg0.creationTimeMillis) {
      return -1;
    }
    if (this.creationTimeMillis > arg0.creationTimeMillis) {
      return 1;
    }
    return 0;
  }

  @Override
  public String toString() {
    String text = getFilename() + " - " + getTime() + " Star year: "
        + getStarYear() + " - " + getEmpireName() + " Realms: "
        + getRealms() + " "
        + getGalaxySize();
    return text;
  }

}
