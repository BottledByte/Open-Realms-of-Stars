package org.openRealmOfStars.utilities.json.values;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2020 Tuomo Untinen
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

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
*
* JUnit for ObjectValue Test
*
*/
public class ObjectValueTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBasic() {
    Member member = new Member("Empty");
    ObjectValue value = new ObjectValue();
    assertEquals("{}", value.getValueAsString());
    value.getMembers().add(member);
    assertEquals("{\"Empty\": null}", value.getValueAsString());
    StringValue str = new StringValue("Test");
    value.getMembers().remove(0);
    member.setValue(str);
    value.getMembers().add(member);
    assertEquals("{\"Empty\": \"Test\"}", value.getValueAsString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAddMember() {
    ObjectValue root = new ObjectValue();
    root.addStringMember("TestString", "StringValue");
    root.addIntegerMember("IntegerTest", 47);
    root.addNumberMember("NumberTest", "47.74");
    root.addBooleanMember("TestTrue", true);
    root.addBooleanMember("TestFalse", false);
    assertEquals("{\"TestString\": \"StringValue\","
        + "\"IntegerTest\": 47,\"NumberTest\": 47.74,"
        + "\"TestTrue\": true,\"TestFalse\": false}", root.getValueAsString());
  }

}
