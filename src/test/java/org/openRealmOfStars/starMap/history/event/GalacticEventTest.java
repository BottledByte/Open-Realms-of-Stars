package org.openRealmOfStars.starMap.history.event;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018 Tuomo Untinen
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

import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
*
* Galactic event Test
*
*/
public class GalacticEventTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void test() {
    GalacticEvent event = new GalacticEvent("Test text");
    assertEquals("Test text", event.getText());
    assertEquals(EventType.GALACTIC_NEWS, event.getType());
    event.setText("Historical");
    assertEquals("Historical", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEncodingAndParsing() throws IOException {
    GalacticEvent event = new GalacticEvent("Historical");
    byte[] buf = event.createByteArray();
    GalacticEvent event2 = GalacticEvent.createGalacticEvent(buf);
    assertEquals(EventType.GALACTIC_NEWS, event2.getType());
    assertEquals(event.getText(), event2.getText());
  }
}
