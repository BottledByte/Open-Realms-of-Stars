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

/**
*
* Json Null value
*
*/
public class NullValue implements JsonValue {

  /**
   * Null literal in JSON.
   */
  public static final String NULL = "null";

  @Override
  public String getValueAsString() {
    return NULL;
  }

  @Override
  public ValueType getType() {
    return ValueType.NULL;
  }

}
