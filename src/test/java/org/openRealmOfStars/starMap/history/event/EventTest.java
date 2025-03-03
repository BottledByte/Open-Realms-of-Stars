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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.starMap.Coordinate;

/**
*
* Generic event test
*
*/
public class EventTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void test() {
    int maxEvent = EventType.values().length;
    for (int i = 0; i < maxEvent; i++) {
      EventType type = EventType.getTypeByIndex(i);
      assertEquals(i, type.getIndex());
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEncodingAndParsing() throws IOException {
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(coord.getX()).thenReturn(22);
    Mockito.when(coord.getY()).thenReturn(11);
    CombatEvent event = new CombatEvent(coord);
    event.setText("Historical");
    event.setPlanetName("Test I");
    byte[] buf = event.createByteArray();
    try (ByteArrayInputStream is = new ByteArrayInputStream(buf)) {
      Event result = Event.parseEvent(is);
      assertEquals(EventType.SPACE_COMBAT, result.getType());
    }
  }

}
