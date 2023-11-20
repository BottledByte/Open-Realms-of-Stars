package org.openRealmOfStars.gui.labels;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2017-2020 Tuomo Untinen
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

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

/**
 * Tests for ImageLabel
 */
public class ImageLabelTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreatingImageLabel() {
    BufferedImage image = Mockito.mock(BufferedImage.class);
    ImageLabel label = new ImageLabel(image, true);
    assertEquals(true, label.isBorder());
    assertEquals(null, label.getFillColor());
    Color color = Mockito.mock(Color.class);
    label.setFillColor(color);
    assertEquals(color, label.getFillColor());
    label.setBorder(false);
    assertEquals(false, label.isBorder());
    label = new ImageLabel(image, false);
    assertEquals(false, label.isBorder());
    assertEquals(false, label.isCenter());
    label.setCenter(true);
    assertEquals(true, label.isCenter());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testDrawingImageLabel() {
    BufferedImage image = new BufferedImage(400, 400,
        BufferedImage.TYPE_4BYTE_ABGR);
    BufferedImage back = new BufferedImage(800, 800,
        BufferedImage.TYPE_4BYTE_ABGR);
    ImageLabel label = new ImageLabel(image, true);
    label.paint(back.getGraphics());
    label.setFillColor(Color.BLUE);
    label.paint(back.getGraphics());
    label.setBorder(false);
    label.paint(back.getGraphics());
    label.paintComponent(back.getGraphics());
    label.createToolTip();
  }
  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testDrawingImageLabelCenter() {
    BufferedImage image = new BufferedImage(400, 400,
        BufferedImage.TYPE_4BYTE_ABGR);
    BufferedImage back = new BufferedImage(800, 800,
        BufferedImage.TYPE_4BYTE_ABGR);
    ImageLabel label = new ImageLabel(image, true);
    label.setCenter(true);
    label.setBorder(false);
    label.paintComponent(back.getGraphics());
    label.createToolTip();
    label.setBorder(true);
    label.paintComponent(back.getGraphics());
    label.createToolTip();
  }

}
