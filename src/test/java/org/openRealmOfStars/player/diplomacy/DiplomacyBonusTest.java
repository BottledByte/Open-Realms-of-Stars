package org.openRealmOfStars.player.diplomacy;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2017-2022 Tuomo Untinen
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
import org.openRealmOfStars.player.race.SpaceRace;

/**
 * Tests for Diplomacy Bonus
 */
public class DiplomacyBonusTest {


  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    DiplomacyBonus bonus = new DiplomacyBonus(
        DiplomacyBonusType.getTypeByIndex(0), SpaceRace.HUMAN);
    bonus.setBonusLasting(9);
    bonus.setBonusValue(5);
    assertEquals(9, bonus.getBonusLasting());
    assertEquals(5, bonus.getBonusValue());
    bonus.setBonusLasting(256);
    assertEquals(255, bonus.getBonusLasting());
    bonus.setBonusLasting(-1);
    assertEquals(0, bonus.getBonusLasting());
    assertEquals(0, bonus.getBonusValue());
    bonus.setBonusLasting(10);
    bonus.setBonusValue(12);
    assertEquals(10, bonus.getBonusLasting());
    assertEquals(12, bonus.getBonusValue());
    bonus.setBonusLasting(0);
    assertEquals(0, bonus.getBonusLasting());
    assertEquals(0, bonus.getBonusValue());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHuman() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE
    int[] bonusValues =  {-30,  -8,  12,  25,  -5,  -3,   3,  -5,  5,   5,  5, -3, -6, 0, 30, -4,
        10, 2, 4, 0, -5, 4, -4, 0, 10, -7, 8, 3, 3, 10, -10, -12, -4, -2, 3,
        2, 3, -2, 1};
    int[] bonusLasting = {255, 150, 255, 255, 200,  20,  50, 80, 110, 255, 1, 70, 120, 10, 255, 30,
        20, 255, 255, 255, 20, 60, 60, 255, 110, 100, 110, 40, 40, 100, 100, 255, 60, 60, 5,
        255, 255, 255 ,255};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.HUMAN);
      assertEquals(bonusValues[i], bonus.getBonusValue());

      assertEquals(bonusLasting[i], bonus.getBonusLasting());
      if (i == 0 || i == 2 || i == 3) {
        assertEquals(true, bonus.isOnlyOne());
      }
      if (i == 5) {
        assertEquals(false, bonus.isOnlyOne());
      }
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCentaurs() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE
    int[] bonusValues =  {-30,  -8,  12,  25,  -8,  -3,   2,  -5,  4,   5,  5, -3, -8, 0, 25, -4,
        10, -1, 3, 0, -5, 4, -4, 0, 7, -7, 6, 3, 3, 8, -8, -12, -4, -2, 3,
        2, 3, -2, 1};
    int[] bonusLasting = {255, 150, 255, 255, 200,  20,  50, 80, 100, 255, 1, 70, 120, 10, 255, 30,
        20, 255, 255, 255, 20, 60, 60, 255, 100, 100, 100, 40, 40, 100, 100, 255, 60, 60, 5,
        255, 255, 255 ,255};
    int[] bonusLasting2 = {255, 149, 255, 255, 199,  19,  49, 79, 99, 255, 2, 69, 119, 9, 255, 29,
        19, 255, 255, 255, 19, 59, 59, 255, 99, 99, 99, 39, 39, 99, 99, 255, 59, 59, 4,
        255, 255, 255 ,255};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.CENTAURS);
      assertEquals(bonusValues[i], bonus.getBonusValue());
      assertEquals(bonusLasting[i], bonus.getBonusLasting());
      bonus.handleBonusForTurn();
      assertEquals(bonusLasting2[i], bonus.getBonusLasting());
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGreyans() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE
    int[] bonusValues =  {-40,  -8,  18,  30,  -5,  -3,   2,  -5,  4,   5,  5, -3, -5, 0, 25, -4,
        10, 0, 3, 0, -5, 4, -4, 0, 7, -7, 6, 3, 3, 8, -8, -12, -4, -2, 3,
        2, 3, -2, 1};
    int[] bonusLasting = {255, 150, 255, 255, 200,  20,  50, 80, 100, 255, 1, 70, 100, 10, 255, 30,
        20, 255, 255, 255, 20, 60, 60, 255, 100, 100, 100, 40, 40, 100, 100, 255, 60, 60, 5,
        255, 255, 255 ,255};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.GREYANS);
      assertEquals(bonusValues[i], bonus.getBonusValue());
      assertEquals(bonusLasting[i], bonus.getBonusLasting());
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSporks() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE
    int[] bonusValues =  {-30,  -2,  12,  25,  -3,  -2,   2,  -10,  4,   2,  1, -2, -4, 0, 20, -2,
        13, -3, 3, 0, -8, 2, -2, 0, 10, -10, 6, 3, 3, 8, -4, -12, -4, -2, 3,
        2, 3, -2, 1};
    int[] bonusLasting = {255, 100, 255, 255, 200,  15,  50, 150, 100, 255, 1, 60, 100, 10, 255, 20,
        20, 255, 255, 255, 20, 60, 60, 255, 110, 110, 100, 40, 40, 100, 100, 255, 60, 60, 5,
        255, 255, 255 ,255};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.SPORKS);
      assertEquals(bonusValues[i], bonus.getBonusValue());
      assertEquals(bonusLasting[i], bonus.getBonusLasting());
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMechions() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE
    int[] bonusValues =  {-30,  -8,  12,  25,  -5,  -3,   2,  -5,  4,  -3,  5, -1, -3, 0, 25, -6,
        8, -2, 3, 0, -5, 2, -2, 0, 5, -5, 4, 3, 3, 6, -6, -12, -4, -2, 3,
        2, 3, -2, 1};
    int[] bonusLasting = {255, 150, 255, 255, 200,  15,  50, 80, 100, 255, 1, 20, 80, 10, 255, 20,
        20, 255, 255, 255, 20, 60, 60, 255, 90, 90, 90, 40, 40, 100, 100, 255, 60, 60, 5,
        255, 255, 255 ,255};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.MECHIONS);
      assertEquals(bonusValues[i], bonus.getBonusValue());
      assertEquals(bonusLasting[i], bonus.getBonusLasting());
      System.out.print(bonus.getType().toString()+", ");
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMothoids() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE
    int[] bonusValues =  {-30,  -8,  12,  25,  -5,  -3,   2,  -5,  4,   5,  8, -3, -5, 0, 25, -4,
        10, 0, 3, 0, -5, 2, -2, 0, 7, -7, 6, 3, 3, 8, -8, -12, -4, -2, 3,
        2, 3, -2, 1};
    int[] bonusLasting = {255, 150, 255, 255, 200,  20,  50, 80, 100, 255, 1, 70, 100, 10, 255, 30,
        20, 255, 255, 255, 20, 60, 60, 255, 100, 100, 100, 40, 40, 100, 100, 255, 60, 60, 5,
        255, 255, 255 ,255};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.MOTHOIDS);
      assertEquals(bonusValues[i], bonus.getBonusValue());
      assertEquals(bonusLasting[i], bonus.getBonusLasting());
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTeuthidaes() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE
    int[] bonusValues =  {-30,  -8,  12,  25,  -5,  -3,   2,  -5,  4,   -3,  5, -3, -6, 0, 20, -2,
        15, -2, 3, 0, -5, 2, -2, 0, 7, -7, 6, 3, 3, 8, -8, -12, -4, -2, 3,
        2, 3, -2, 1};
    int[] bonusLasting = {255, 150, 255, 255, 200,  20,  50, 80, 100, 255, 1, 70, 120, 10, 255, 20,
        20, 255, 255, 255, 20, 60, 60, 255, 100, 100, 100, 40, 40, 100, 100, 255, 60, 60, 5,
        255, 255, 255 ,255};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.TEUTHIDAES);
      assertEquals(bonusValues[i], bonus.getBonusValue());
      assertEquals(bonusLasting[i], bonus.getBonusLasting());
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHomarians() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE
    int[] bonusValues =  {-30,  -8,  12,  25,  -5,  -3,   2,  -5,  4,   5,  5, -3, -8, 0, 30, -4,
        8, 1, 3, 0, -5, 2, -2, 0, 7, -7, 6, 3, 3, 8, -8, -12, -4, -2, 3,
        2, 3, -2, 1};
    int[] bonusLasting = {255, 150, 255, 255, 200,  20,  50, 80, 100, 255, 1, 70, 120, 10, 255, 30,
        20, 255, 255, 255, 20, 60, 60, 255, 100, 100, 100, 40, 40, 100, 100, 255, 60, 60, 5,
        255, 255, 255 ,255};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.HOMARIANS);
      assertEquals(bonusValues[i], bonus.getBonusValue());

      assertEquals(bonusLasting[i], bonus.getBonusLasting());
      if (i == 0 || i == 2 || i == 3) {
        assertEquals(true, bonus.isOnlyOne());
      }
      if (i == 5) {
        assertEquals(false, bonus.isOnlyOne());
      }
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScaurians() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE
    int[] bonusValues =  {-30,  -8,  18,  25,  -5,  -2,   3,  -5,  5,   5,  6, -3, -4, 0, 25, -4,
        10, 1, 6, 0, -8, 4, -4, 0, 7, -7, 6, 3, 3, 8, -8, -12, -4, -2, 3,
        2, 3, -2, 1};
    int[] bonusLasting = {255, 150, 255, 255, 200,  15,  50, 80, 110, 255, 1, 70, 100, 10, 255, 30,
        20, 255, 255, 255, 20, 60, 60, 255, 100, 100, 100, 40, 40, 100, 100, 255, 60, 60, 5,
        255, 255, 255 ,255};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.SCAURIANS);
      assertEquals(bonusValues[i], bonus.getBonusValue());
      assertEquals(bonusLasting[i], bonus.getBonusLasting());
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testChiraloids() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE
    int[] bonusValues =  {-30,  -8,  12,  25,  -5,  -3,   2,  -5,  4,   5,  5, -2, 2, 0, 25, -2,
        12, -4, 3, 0, -8, 2, -2, 0, 7, -7, 6, 3, 3, 8, -8, -12, -4, -2, 3,
        2, 3, -2, 1};
    int[] bonusLasting = {255, 150, 255, 255, 200,  20,  50, 80, 100, 255, 1, 60, 40, 10, 255, 20,
        20, 255, 255, 255, 20, 60, 60, 255, 100, 100, 100, 40, 40, 100, 100, 255, 60, 60, 5,
        255, 255, 255 ,255};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.CHIRALOIDS);
      assertEquals(bonusValues[i], bonus.getBonusValue());
      assertEquals(bonusLasting[i], bonus.getBonusLasting());
    }
  }

}
