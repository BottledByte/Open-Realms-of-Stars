package org.openRealmOfStars.gui.panels;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2017 Tuomo Untinen
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

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 *
 * Handling invisible panel. Forces redrawing parent component when
 * panel is dirty.
 *
 */
public class InvisiblePanel extends JPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Parent component
   */
  private Component parent;

  /**
   * Components are dirty need to repaint background
   */
  private boolean dirty;

  /**
   * Create a new invisible panel
   * @param parent Parent component
   */
  public InvisiblePanel(final Component parent) {
    this.parent = parent;
  }

  @Override
  public void paintComponent(final Graphics g) {
    if (dirty) {
      parent.repaint();
      dirty = false;
    }
    // Invisible panel does not paint anything
  }

  /**
   * Components are dirty need repainting.
   */
  public void setDirty() {
    dirty = true;
  }

}
