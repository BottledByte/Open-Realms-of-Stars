package org.openRealmOfStars.gui.labels;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.starMap.planet.Planet;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2023 Tuomo Untinen
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
*
*
* Planet info panel for uncolozized
*
*/
public class UncolonizedPlanetInfoLabel extends EmptyInfoPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Planet attribute
   */
  private Planet planet;

  /**
   * Constructor for planet info label.
   * @param target Planet which information is shown
   * @param listener ActionListener
   */
  public UncolonizedPlanetInfoLabel(final Planet target,
      final ActionListener listener) {
    planet = target;
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    if (planet == null) {
      this.add(Box.createRigidArea(new Dimension(400, 25)));
      return;
    }
    int tileIndex = planet.getPlanetType().getTileIndex();
    Tile planetTile = Tiles.getTileByIndex(tileIndex);
    BufferedImage image = new BufferedImage(Tile.MAX_WIDTH, Tile.MAX_HEIGHT,
        BufferedImage.TYPE_4BYTE_ABGR);
    planetTile.draw(image.createGraphics(), 0, 0);
    ImageLabel planetImage = new ImageLabel(image, false);
    this.add(planetImage);
    SpaceLabel planetLabel = new SpaceLabel(planet.getName());
    this.add(planetLabel);
    if (planet.getHomeWorldIndex() != -1) {
      this.add(new IconLabel(null, Icons.getIconByName(Icons.ICON_CULTURE),
          ""));
    } else {
      this.add(Box.createRigidArea(new Dimension(26, 25)));
    }
    int textWidth = 150 - GuiStatics.getTextWidth(GuiStatics.getFontCubellan(),
        planet.getName());
    if (textWidth > 0) {
      this.add(Box.createRigidArea(new Dimension(textWidth, 25)));
    }
    IconLabel icon = new IconLabel(null, Icons.getIconByName(
        Icons.ICON_RADIATION), String.valueOf(planet.getRadiationLevel()));
    icon.setToolTipText("Planet's current radiation level");
    addIcon(icon);
    icon = new IconLabel(null, Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH),
        planet.getSizeAsString());
    icon.setToolTipText("Planet's total ground size");
    addIcon(icon);
    icon = new IconLabel(null, Icons.getIconByName(Icons.ICON_PLANET),
        planet.getPlanetType().getTypeAsString());
    icon.setToolTipText("Planet's type");
    addIcon(icon);
    SpaceButton btn = new SpaceButton("View", planet.getName() + "|"
        + GameCommands.COMMAND_VIEW_PLANET);
    btn.addActionListener(listener);
    this.add(btn);
  }

  /**
   * Add IconLabel with possible padding.
   * @param icon Icon Label to add
   */
  private void addIcon(final IconLabel icon) {
    this.add(icon);
    int textWidth = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(), "88")
        - GuiStatics.getTextWidth(GuiStatics.getFontCubellan(), icon.getText());
    if (textWidth > 0) {
      this.add(Box.createRigidArea(new Dimension(textWidth, 25)));
    }
  }

}
