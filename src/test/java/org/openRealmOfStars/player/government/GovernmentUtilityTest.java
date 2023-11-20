package org.openRealmOfStars.player.government;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2021 Tuomo Untinen
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
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

/**
* Government Utility Test
*
*/
public class GovernmentUtilityTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHumanGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.HUMAN);
    assertEquals(12, governments.length);
    assertEquals(GovernmentType.UNION, governments[0]);
    assertEquals(GovernmentType.DEMOCRACY, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.REPUBLIC, governments[3]);
    assertEquals(GovernmentType.ENTERPRISE, governments[4]);
    assertEquals(GovernmentType.UTOPIA, governments[5]);
    assertEquals(GovernmentType.EMPIRE, governments[6]);
    assertEquals(GovernmentType.HEGEMONY, governments[7]);
    assertEquals(GovernmentType.FEUDALISM, governments[8]);
    assertEquals(GovernmentType.TECHNOCRACY, governments[9]);
    assertEquals(GovernmentType.HIERARCHY, governments[10]);
    assertEquals(GovernmentType.KINGDOM, governments[11]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCentaursGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.CENTAURS);
    assertEquals(13, governments.length);
    assertEquals(GovernmentType.UNION, governments[0]);
    assertEquals(GovernmentType.DEMOCRACY, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.REPUBLIC, governments[3]);
    assertEquals(GovernmentType.ENTERPRISE, governments[4]);
    assertEquals(GovernmentType.COLLECTIVE, governments[5]);
    assertEquals(GovernmentType.EMPIRE, governments[6]);
    assertEquals(GovernmentType.HEGEMONY, governments[7]);
    assertEquals(GovernmentType.HIERARCHY, governments[8]);
    assertEquals(GovernmentType.KINGDOM, governments[9]);
    assertEquals(GovernmentType.TECHNOCRACY, governments[10]);
    assertEquals(GovernmentType.REGIME, governments[11]);
    assertEquals(GovernmentType.FEUDALISM, governments[12]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGreyansGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.GREYANS);
    assertEquals(11, governments.length);
    assertEquals(GovernmentType.UNION, governments[0]);
    assertEquals(GovernmentType.DEMOCRACY, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.REPUBLIC, governments[3]);
    assertEquals(GovernmentType.ENTERPRISE, governments[4]);
    assertEquals(GovernmentType.COLLECTIVE, governments[5]);
    assertEquals(GovernmentType.EMPIRE, governments[6]);
    assertEquals(GovernmentType.HEGEMONY, governments[7]);
    assertEquals(GovernmentType.TECHNOCRACY, governments[8]);
    assertEquals(GovernmentType.HIERARCHY, governments[9]);
    assertEquals(GovernmentType.KINGDOM, governments[10]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMechionsGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.MECHIONS);
    assertEquals(12, governments.length);
    assertEquals(GovernmentType.UNION, governments[0]);
    assertEquals(GovernmentType.DEMOCRACY, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.REPUBLIC, governments[3]);
    assertEquals(GovernmentType.ENTERPRISE, governments[4]);
    assertEquals(GovernmentType.EMPIRE, governments[5]);
    assertEquals(GovernmentType.COLLECTIVE, governments[6]);
    assertEquals(GovernmentType.REGIME, governments[7]);
    assertEquals(GovernmentType.AI, governments[8]);
    assertEquals(GovernmentType.MECHANICAL_HORDE, governments[9]);
    assertEquals(GovernmentType.HEGEMONY, governments[10]);
    assertEquals(GovernmentType.HIERARCHY, governments[11]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSporksGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.SPORKS);
    assertEquals(11, governments.length);
    assertEquals(GovernmentType.UNION, governments[0]);
    assertEquals(GovernmentType.REGIME, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.REPUBLIC, governments[3]);
    assertEquals(GovernmentType.CLAN, governments[4]);
    assertEquals(GovernmentType.HORDE, governments[5]);
    assertEquals(GovernmentType.FEUDALISM, governments[6]);
    assertEquals(GovernmentType.EMPIRE, governments[7]);
    assertEquals(GovernmentType.HEGEMONY, governments[8]);
    assertEquals(GovernmentType.HIERARCHY, governments[9]);
    assertEquals(GovernmentType.KINGDOM, governments[10]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHomariansGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.HOMARIANS);
    assertEquals(11, governments.length);
    assertEquals(GovernmentType.UNION, governments[0]);
    assertEquals(GovernmentType.UTOPIA, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.REPUBLIC, governments[3]);
    assertEquals(GovernmentType.CLAN, governments[4]);
    assertEquals(GovernmentType.HORDE, governments[5]);
    assertEquals(GovernmentType.FEUDALISM, governments[6]);
    assertEquals(GovernmentType.EMPIRE, governments[7]);
    assertEquals(GovernmentType.HEGEMONY, governments[8]);
    assertEquals(GovernmentType.NEST, governments[9]);
    assertEquals(GovernmentType.KINGDOM, governments[10]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMothoidsGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.MOTHOIDS);
    assertEquals(12, governments.length);
    assertEquals(GovernmentType.UNION, governments[0]);
    assertEquals(GovernmentType.UTOPIA, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.REPUBLIC, governments[3]);
    assertEquals(GovernmentType.CLAN, governments[4]);
    assertEquals(GovernmentType.HORDE, governments[5]);
    assertEquals(GovernmentType.EMPIRE, governments[6]);
    assertEquals(GovernmentType.HEGEMONY, governments[7]);
    assertEquals(GovernmentType.NEST, governments[8]);
    assertEquals(GovernmentType.KINGDOM, governments[9]);
    assertEquals(GovernmentType.FEUDALISM, governments[10]);
    assertEquals(GovernmentType.HIVEMIND, governments[11]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScauriansGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.SCAURIANS);
    assertEquals(11, governments.length);
    assertEquals(GovernmentType.UNION, governments[0]);
    assertEquals(GovernmentType.UTOPIA, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.REPUBLIC, governments[3]);
    assertEquals(GovernmentType.ENTERPRISE, governments[4]);
    assertEquals(GovernmentType.GUILD, governments[5]);
    assertEquals(GovernmentType.EMPIRE, governments[6]);
    assertEquals(GovernmentType.HEGEMONY, governments[7]);
    assertEquals(GovernmentType.HIERARCHY, governments[8]);
    assertEquals(GovernmentType.KINGDOM, governments[9]);
    assertEquals(GovernmentType.DEMOCRACY, governments[10]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTeuthidaesGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.TEUTHIDAES);
    assertEquals(13, governments.length);
    assertEquals(GovernmentType.UNION, governments[0]);
    assertEquals(GovernmentType.REGIME, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.REPUBLIC, governments[3]);
    assertEquals(GovernmentType.ENTERPRISE, governments[4]);
    assertEquals(GovernmentType.HORDE, governments[5]);
    assertEquals(GovernmentType.EMPIRE, governments[6]);
    assertEquals(GovernmentType.HEGEMONY, governments[7]);
    assertEquals(GovernmentType.FEUDALISM, governments[8]);
    assertEquals(GovernmentType.TECHNOCRACY, governments[9]);
    assertEquals(GovernmentType.HIERARCHY, governments[10]);
    assertEquals(GovernmentType.KINGDOM, governments[11]);
    assertEquals(GovernmentType.DEMOCRACY, governments[12]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testChiraloidsGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.CHIRALOIDS);
    assertEquals(12, governments.length);
    assertEquals(GovernmentType.UNION, governments[0]);
    assertEquals(GovernmentType.REGIME, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.REPUBLIC, governments[3]);
    assertEquals(GovernmentType.CLAN, governments[4]);
    assertEquals(GovernmentType.HIVEMIND, governments[5]);
    assertEquals(GovernmentType.COLLECTIVE, governments[6]);
    assertEquals(GovernmentType.FEUDALISM, governments[7]);
    assertEquals(GovernmentType.EMPIRE, governments[8]);
    assertEquals(GovernmentType.HEGEMONY, governments[9]);
    assertEquals(GovernmentType.HIERARCHY, governments[10]);
    assertEquals(GovernmentType.KINGDOM, governments[11]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testReborgianGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.REBORGIANS);
    assertEquals(10, governments.length);
    assertEquals(GovernmentType.REGIME, governments[0]);
    assertEquals(GovernmentType.TECHNOCRACY, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.HIVEMIND, governments[3]);
    assertEquals(GovernmentType.COLLECTIVE, governments[4]);
    assertEquals(GovernmentType.EMPIRE, governments[5]);
    assertEquals(GovernmentType.AI, governments[6]);
    assertEquals(GovernmentType.MECHANICAL_HORDE, governments[7]);
    assertEquals(GovernmentType.HEGEMONY, governments[8]);
    assertEquals(GovernmentType.HIERARCHY, governments[9]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testLithorianGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.LITHORIANS);
    assertEquals(12, governments.length);
    assertEquals(GovernmentType.UNION, governments[0]);
    assertEquals(GovernmentType.HIVEMIND, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.TECHNOCRACY, governments[3]);
    assertEquals(GovernmentType.UTOPIA, governments[4]);
    assertEquals(GovernmentType.COLLECTIVE, governments[5]);
    assertEquals(GovernmentType.EMPIRE, governments[6]);
    assertEquals(GovernmentType.HEGEMONY, governments[7]);
    assertEquals(GovernmentType.HIERARCHY, governments[8]);
    assertEquals(GovernmentType.KINGDOM, governments[9]);
    assertEquals(GovernmentType.REGIME, governments[10]);
    assertEquals(GovernmentType.FEUDALISM, governments[11]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGovernmentIndexes() {
    GovernmentType[] govs = GovernmentType.values();
    for (int i = 0; i < govs.length; i++) {
      assertEquals(govs[i], GovernmentUtility.getGovernmentByIndex(i));
    }
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGovernmentIndexesBig() {
    GovernmentUtility.getGovernmentByIndex(255);
  }

}
