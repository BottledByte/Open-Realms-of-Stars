package org.openRealmOfStars.player.artifact;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2022 Tuomo Untinen
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

import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.icons.Icons;

/**
*
* Artifact
*
*/
public class Artifact {

  /**
   * Artifact index
   */
  private int index;
  /**
   * Artifact name.
   */
  private String name;
  /**
   * Artifact type.
   */
  private ArtifactType artifactType;
  /**
   * One time tech bonus;
   */
  private int oneTimeTechBonus;
  /**
   * Artifact icon
   */
  private Icon16x16 icon;
  /**
   * Artifact description.
   */
  private String description;
  /**
   * Is artifact unique so it cannot find from random location.
   */
  private boolean unique;
  /**
   * Get Artifact index.
   * @return Artifact index.
   */
  public int getIndex() {
    return index;
  }
  /**
   * Constructor for Artifact
   * @param index Artifact index
   * @param name Artifact name
   * @param type Artifact type
   */
  public Artifact(final int index, final String name,
      final ArtifactType type) {
    this.index = index;
    this.name = name;
    this.artifactType = type;
    this.unique = false;
    this.setIcon(Icons.getIconByName(Icons.ICON_ANCIENT_FRAGMENT));
  }
  /**
   * Get Artifact name.
   * @return String
   */
  public String getName() {
    return name;
  }
  /**
   * Get Artifact type
   * @return Artifact type
   */
  public ArtifactType getArtifactType() {
    return artifactType;
  }
  /**
   * Get Artifact one time bonus for research
   * @return Research bonus
   */
  public int getOneTimeTechBonus() {
    return oneTimeTechBonus;
  }
  /**
   * Set one time research bonus.
   * @param oneTimeTechBonus Research bonus
   */
  public void setOneTimeTechBonus(final int oneTimeTechBonus) {
    this.oneTimeTechBonus = oneTimeTechBonus;
  }
  /**
   * Get Icon16x16 icon.
   * @return Icon16x16
   */
  public Icon16x16 getIcon() {
    return icon;
  }
  /**
   * Set Icon16x16.
   * @param icon Icon to set.
   */
  public void setIcon(final Icon16x16 icon) {
    this.icon = icon;
  }
  /**
   * Get Artifact description
   * @return Artifact description
   */
  public String getDescription() {
    return description;
  }
  /**
   * Set Artifact description.
   * @param description Artifact description
   */
  public void setDescription(final String description) {
    this.description = description;
  }

  /**
   * Get full description of artifact.
   * @return String
   */
  public String getFullDescription() {
    StringBuilder sb = new StringBuilder();
    sb.append(getName());
    sb.append(" - ");
    sb.append(getArtifactType().toString());
    sb.append("\n");
    sb.append("Tech bonus: ");
    sb.append(getOneTimeTechBonus());
    sb.append("\n\n");
    sb.append(getDescription());
    return sb.toString();
  }
  /**
   * Is artifact unique or common
   * @return True if unique
   */
  public boolean isUnique() {
    return unique;
  }
  /**
   * Set artifact unique.
   * @param unique True for unique.
   */
  public void setUnique(final boolean unique) {
    this.unique = unique;
  }
}
