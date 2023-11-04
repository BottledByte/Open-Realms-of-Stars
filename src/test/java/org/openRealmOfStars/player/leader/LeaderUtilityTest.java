package org.openRealmOfStars.player.leader;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.AiDifficulty;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonus;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.leader.stats.StatType;
import org.openRealmOfStars.starMap.GalaxyConfig;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2020-2023 Tuomo Untinen
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
 * Test for Leader utility class
 */
public class LeaderUtilityTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerHumanKingdom() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setGender(Gender.FEMALE);
    leader.setJob(Job.RULER);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Queen", tmp);
    leader.setGender(Gender.MALE);
    tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("King", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerHumanDemogracy() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.DEMOCRACY);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setJob(Job.RULER);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("President", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerHumanEmpire() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.EMPIRE);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setJob(Job.RULER);
    leader.setGender(Gender.FEMALE);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Empiress", tmp);
    leader.setGender(Gender.MALE);
    tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Emperor", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerCentaurHiearchy() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.CENTAURS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.HIERARCHY);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setJob(Job.RULER);
    leader.setGender(Gender.FEMALE);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Lady", tmp);
    leader.setGender(Gender.MALE);
    tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Lord", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerScaurianEnterprise() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.SCAURIANS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.ENTERPRISE);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setJob(Job.RULER);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("CEO", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerSporkClan() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.SPORKS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.CLAN);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setJob(Job.RULER);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Chief", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerGreyanHegemony() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.GREYANS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.HEGEMONY);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setJob(Job.RULER);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Leader", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerMothoidHivemind() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.MOTHOIDS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.HIVEMIND);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setJob(Job.RULER);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Master", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSLevel1HumanKingdom() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet, 1);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    assertNotEquals(0, leader.getPerkList().size());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSLevel2HumanKingdom() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet, 2);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(2, leader.getLevel());
    assertEquals(0, leader.getExperience());
    assertNotEquals(0, leader.getPerkList().size());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerMechionAi() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.MECHIONS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setJob(Job.RULER);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Main Process", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerMothoidEmpire() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.MOTHOIDS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.EMPIRE);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerSporkKingdom() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.SPORKS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGoodPerks() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    Perk[] perks = LeaderUtility.getNewPerks(leader,
        LeaderUtility.PERK_TYPE_GOOD);
    assertEquals(28, perks.length);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBadPerks() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    Perk[] perks = LeaderUtility.getNewPerks(leader,
        LeaderUtility.PERK_TYPE_BAD);
    assertEquals(true, perks.length == 13 || perks.length == 14);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNextLeaderKingdom() {
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    Mockito.when(realm.getRace()).thenReturn(SpaceRace.HUMAN);
    ArrayList<Leader> pool = new ArrayList<>();
    Mockito.when(realm.getLeaderPool()).thenReturn(pool);
    Leader parent = new Leader("Test Parent");
    parent.setAge(75);
    parent.setJob(Job.DEAD);
    pool.add(parent);
    Leader leader = new Leader("Test Leader");
    leader.setAge(50);
    leader.setParent(parent);
    leader.getPerkList().add(Perk.COMBAT_MASTER);
    leader.getPerkList().add(Perk.COMBAT_TACTICIAN);
    leader.getPerkList().add(Perk.DISCIPLINE);
    leader.getPerkList().add(Perk.CHARISMATIC);
    leader.getPerkList().add(Perk.COUNTER_AGENT);
    leader.getPerkList().add(Perk.CORRUPTED);
    leader.getPerkList().add(Perk.MICRO_MANAGER);
    leader.getPerkList().add(Perk.MILITARISTIC);
    leader.getPerkList().add(Perk.POWER_HUNGRY);
    leader.getPerkList().add(Perk.WARLORD);
    pool.add(leader);
    Leader ruler = LeaderUtility.getNextRuler(realm);
    assertEquals(leader, ruler);
    Leader leader2 = new Leader("Test Leader2");
    leader2.setAge(50);
    leader2.setParent(null);
    leader2.getPerkList().add(Perk.COMBAT_MASTER);
    leader2.getPerkList().add(Perk.COMBAT_TACTICIAN);
    leader2.getPerkList().add(Perk.DISCIPLINE);
    leader2.getPerkList().add(Perk.CHARISMATIC);
    leader2.getPerkList().add(Perk.COUNTER_AGENT);
    leader2.getPerkList().add(Perk.CORRUPTED);
    leader2.getPerkList().add(Perk.MICRO_MANAGER);
    leader2.getPerkList().add(Perk.MILITARISTIC);
    leader2.getPerkList().add(Perk.POWER_HUNGRY);
    leader2.getPerkList().add(Perk.WARLORD);
    pool.add(leader2);
    leader.setJob(Job.DEAD);
    ruler = LeaderUtility.getNextRuler(realm);
    assertEquals(leader2, ruler);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNextLeaderHorde() {
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getGovernment()).thenReturn(GovernmentType.HORDE);
    Mockito.when(realm.getRace()).thenReturn(SpaceRace.HUMAN);
    ArrayList<Leader> pool = new ArrayList<>();
    Mockito.when(realm.getLeaderPool()).thenReturn(pool);
    Leader parent = new Leader("Test Parent");
    parent.setAge(75);
    parent.setJob(Job.DEAD);
    pool.add(parent);
    Leader leader = new Leader("Test Leader");
    leader.setAge(30);
    leader.setParent(parent);
    leader.getPerkList().add(Perk.COMBAT_MASTER);
    leader.getPerkList().add(Perk.COMBAT_TACTICIAN);
    leader.getPerkList().add(Perk.DISCIPLINE);
    leader.getPerkList().add(Perk.CHARISMATIC);
    leader.getPerkList().add(Perk.COUNTER_AGENT);
    leader.getPerkList().add(Perk.CORRUPTED);
    leader.getPerkList().add(Perk.MICRO_MANAGER);
    leader.getPerkList().add(Perk.MILITARISTIC);
    leader.getPerkList().add(Perk.POWER_HUNGRY);
    leader.getPerkList().add(Perk.WARLORD);
    pool.add(leader);
    Leader leader2 = new Leader("Test Leader2");
    leader2.setAge(50);
    leader2.setParent(null);
    leader2.getPerkList().add(Perk.COMBAT_MASTER);
    leader2.getPerkList().add(Perk.COMBAT_TACTICIAN);
    leader2.getPerkList().add(Perk.DISCIPLINE);
    leader2.getPerkList().add(Perk.CHARISMATIC);
    leader2.getPerkList().add(Perk.COUNTER_AGENT);
    leader2.getPerkList().add(Perk.CORRUPTED);
    leader2.getPerkList().add(Perk.MICRO_MANAGER);
    leader2.getPerkList().add(Perk.MILITARISTIC);
    leader2.getPerkList().add(Perk.POWER_HUNGRY);
    leader2.getPerkList().add(Perk.WARLORD);
    pool.add(leader2);
    Leader ruler = LeaderUtility.getNextRuler(realm);
    assertEquals(leader, ruler);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNextLeaderCeo() {
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getGovernment()).thenReturn(GovernmentType.ENTERPRISE);
    ArrayList<Leader> pool = new ArrayList<>();
    Mockito.when(realm.getLeaderPool()).thenReturn(pool);
    Leader leader = new Leader("Test Leader");
    leader.setAge(30);
    leader.getPerkList().add(Perk.COMBAT_MASTER);
    leader.getPerkList().add(Perk.COMBAT_TACTICIAN);
    leader.getPerkList().add(Perk.DISCIPLINE);
    leader.getPerkList().add(Perk.CHARISMATIC);
    leader.getPerkList().add(Perk.COUNTER_AGENT);
    leader.getPerkList().add(Perk.CORRUPTED);
    leader.getPerkList().add(Perk.MICRO_MANAGER);
    leader.getPerkList().add(Perk.MILITARISTIC);
    leader.getPerkList().add(Perk.POWER_HUNGRY);
    leader.getPerkList().add(Perk.WARLORD);
    pool.add(leader);
    Leader leader2 = new Leader("Test Leader2");
    leader2.setAge(50);
    leader2.getPerkList().add(Perk.COMBAT_MASTER);
    leader2.getPerkList().add(Perk.COMBAT_TACTICIAN);
    leader2.getPerkList().add(Perk.MERCHANT);
    leader2.getPerkList().add(Perk.CHARISMATIC);
    leader2.getPerkList().add(Perk.COUNTER_AGENT);
    leader2.getPerkList().add(Perk.CORRUPTED);
    leader2.getPerkList().add(Perk.MICRO_MANAGER);
    leader2.getPerkList().add(Perk.TRADER);
    leader2.getPerkList().add(Perk.POWER_HUNGRY);
    leader2.getPerkList().add(Perk.ACADEMIC);
    pool.add(leader2);
    Leader ruler = LeaderUtility.getNextRuler(realm);
    assertEquals(leader2, ruler);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNextLeaderDemocracy() {
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getGovernment()).thenReturn(GovernmentType.UNION);
    ArrayList<Leader> pool = new ArrayList<>();
    Mockito.when(realm.getLeaderPool()).thenReturn(pool);
    Leader leader = new Leader("Test Leader");
    leader.setAge(80);
    leader.getPerkList().add(Perk.INDUSTRIAL);
    leader.getPerkList().add(Perk.MINER);
    leader.getPerkList().add(Perk.DISCIPLINE);
    leader.getPerkList().add(Perk.CHARISMATIC);
    leader.getPerkList().add(Perk.COUNTER_AGENT);
    leader.getPerkList().add(Perk.CORRUPTED);
    leader.getPerkList().add(Perk.PACIFIST);
    leader.getPerkList().add(Perk.GOOD_LEADER);
    leader.getPerkList().add(Perk.POWER_HUNGRY);
    leader.getPerkList().add(Perk.ACADEMIC);
    pool.add(leader);
    Leader leader2 = new Leader("Test Leader2");
    leader2.setAge(80);
    leader2.getPerkList().add(Perk.COMBAT_MASTER);
    leader2.getPerkList().add(Perk.COMBAT_TACTICIAN);
    leader2.getPerkList().add(Perk.MERCHANT);
    leader2.getPerkList().add(Perk.CHARISMATIC);
    leader2.getPerkList().add(Perk.COUNTER_AGENT);
    leader2.getPerkList().add(Perk.CORRUPTED);
    leader2.getPerkList().add(Perk.MICRO_MANAGER);
    leader2.getPerkList().add(Perk.TRADER);
    leader2.getPerkList().add(Perk.ACADEMIC);
    pool.add(leader2);
    Leader ruler = LeaderUtility.getNextRuler(realm);
    assertEquals(leader, ruler);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNextLeaderFederation() {
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getGovernment()).thenReturn(GovernmentType.FEDERATION);
    ArrayList<Leader> pool = new ArrayList<>();
    Mockito.when(realm.getLeaderPool()).thenReturn(pool);
    Leader leader = new Leader("Test Leader");
    leader.setAge(80);
    leader.getPerkList().add(Perk.INDUSTRIAL);
    leader.getPerkList().add(Perk.MINER);
    leader.getPerkList().add(Perk.DISCIPLINE);
    leader.getPerkList().add(Perk.CHARISMATIC);
    leader.getPerkList().add(Perk.COUNTER_AGENT);
    leader.getPerkList().add(Perk.CORRUPTED);
    leader.getPerkList().add(Perk.PACIFIST);
    leader.getPerkList().add(Perk.GOOD_LEADER);
    leader.getPerkList().add(Perk.SLOW_LEARNER);
    leader.getPerkList().add(Perk.STUPID);
    pool.add(leader);
    Leader leader2 = new Leader("Test Leader2");
    leader2.setAge(80);
    leader2.getPerkList().add(Perk.COMBAT_MASTER);
    leader2.getPerkList().add(Perk.COMBAT_TACTICIAN);
    leader2.getPerkList().add(Perk.MERCHANT);
    leader2.getPerkList().add(Perk.CHARISMATIC);
    leader2.getPerkList().add(Perk.COUNTER_AGENT);
    leader2.getPerkList().add(Perk.CORRUPTED);
    leader2.getPerkList().add(Perk.MICRO_MANAGER);
    leader2.getPerkList().add(Perk.TRADER);
    leader2.getPerkList().add(Perk.ACADEMIC);
    leader2.getPerkList().add(Perk.MILITARISTIC);
    leader2.getPerkList().add(Perk.WARLORD);
    pool.add(leader2);
    Leader ruler = LeaderUtility.getNextRuler(realm);
    assertEquals(leader2, ruler);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNextLeaderHegemony() {
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getGovernment()).thenReturn(GovernmentType.HEGEMONY);
    Mockito.when(realm.getRace()).thenReturn(SpaceRace.HUMAN);
    ArrayList<Leader> pool = new ArrayList<>();
    Mockito.when(realm.getLeaderPool()).thenReturn(pool);
    Leader leader = new Leader("Test Leader");
    leader.setAge(80);
    leader.getPerkList().add(Perk.SLOW_LEARNER);
    leader.getPerkList().add(Perk.STUPID);
    pool.add(leader);
    Leader leader2 = new Leader("Test Leader2");
    leader2.setAge(85);
    leader2.getPerkList().add(Perk.SCIENTIST);
    leader2.getPerkList().add(Perk.ARCHAEOLOGIST);
    leader2.getPerkList().add(Perk.FTL_ENGINEER);
    leader2.getPerkList().add(Perk.EXPLORER);
    leader2.getPerkList().add(Perk.CHARISMATIC);
    leader2.getPerkList().add(Perk.SCANNER_EXPERT);
    leader2.getPerkList().add(Perk.CORRUPTED);
    leader2.getPerkList().add(Perk.MICRO_MANAGER);
    leader2.getPerkList().add(Perk.TRADER);
    leader2.getPerkList().add(Perk.ACADEMIC);
    leader2.getPerkList().add(Perk.MILITARISTIC);
    leader2.getPerkList().add(Perk.WARLORD);
    pool.add(leader2);
    Leader ruler = LeaderUtility.getNextRuler(realm);
    assertEquals(leader2, ruler);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNextLeaderAi() {
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getGovernment()).thenReturn(GovernmentType.AI);
    ArrayList<Leader> pool = new ArrayList<>();
    Mockito.when(realm.getLeaderPool()).thenReturn(pool);
    Leader leader = new Leader("Test Leader");
    leader.setAge(18);
    leader.getPerkList().add(Perk.SLOW_LEARNER);
    leader.getPerkList().add(Perk.STUPID);
    pool.add(leader);
    Leader leader2 = new Leader("Test Leader2");
    leader2.setAge(18);
    leader2.getPerkList().add(Perk.SCIENTIST);
    leader2.getPerkList().add(Perk.ARCHAEOLOGIST);
    leader2.getPerkList().add(Perk.FTL_ENGINEER);
    leader2.getPerkList().add(Perk.EXPLORER);
    leader2.getPerkList().add(Perk.CHARISMATIC);
    leader2.getPerkList().add(Perk.SCANNER_EXPERT);
    leader2.getPerkList().add(Perk.CORRUPTED);
    leader2.getPerkList().add(Perk.MICRO_MANAGER);
    leader2.getPerkList().add(Perk.TRADER);
    leader2.getPerkList().add(Perk.ACADEMIC);
    leader2.getPerkList().add(Perk.MILITARISTIC);
    leader2.getPerkList().add(Perk.WARLORD);
    pool.add(leader2);
    Leader ruler = LeaderUtility.getNextRuler(realm);
    assertEquals(leader2, ruler);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testLeaderBio() {
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getGovernment()).thenReturn(GovernmentType.AI);
    Leader leader = new Leader("Robot X");
    leader.setJob(Job.GOVERNOR);
    leader.setTitle("Governor");
    leader.setAge(18);
    leader.getPerkList().add(Perk.SLOW_LEARNER);
    leader.getPerkList().add(Perk.STUPID);
    String str = LeaderUtility.createBioForLeader(leader, realm);
    assertEquals("Robot X is jobless. Currently Robot X is governor."
        + " Governor is still young and is able to achieve many things. ",
        str);
    leader = new Leader("Robo Test");
    leader.setJob(Job.RULER);
    leader.setTitle("King");
    leader.setAge(18);
    leader.getStats().addOne(StatType.NUMBER_OF_RULER);
    leader.getStats().addOne(StatType.RULER_REIGN_LENGTH);
    leader.getStats().addOne(StatType.WAR_DECLARATIONS);
    leader.getStats().addOne(StatType.WAR_DECLARATIONS);
    leader.getStats().addOne(StatType.NUMBER_OF_TRADES);
    leader.getPerkList().add(Perk.AGGRESSIVE);
    str = LeaderUtility.createBioForLeader(leader, realm);
    assertEquals("Robo Test is the Main Process. Currently Robo Test is Main"
        + " Process. King is known for war declarations and trades."
        + " King Robo Test is known to be aggressive. ", str);

  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHiringLeaderFromAnotherRealm() {
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(4);
    config.setScoringVictoryTurns(200);
    config.setAiOnly(true);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    config.setPlayerDifficult(0, AiDifficulty.NORMAL);
    config.setPlayerDifficult(1, AiDifficulty.NORMAL);
    config.setPlayerDifficult(2, AiDifficulty.NORMAL);
    config.setPlayerDifficult(3, AiDifficulty.NORMAL);
    config.setPlayerName(0, "Realm of Pop");
    config.setPlayerGovernment(0, GovernmentType.DEMOCRACY);
    config.setRace(0, SpaceRace.HUMAN);
    config.setPlayerName(1, "Realm of Credit");
    config.setPlayerGovernment(1, GovernmentType.DEMOCRACY);
    config.setRace(1, SpaceRace.HUMAN);
    PlayerList playerList = PlayerList.createPlayerList(config);
    StarMap map = new StarMap(config, playerList);
    PlayerInfo info = map.getPlayerByIndex(0);
    info.getDiplomacy().getDiplomacyList(1).addBonus(
        new DiplomacyBonus(DiplomacyBonusType.IN_DEFENSIVE_PACT,
            info.getRace()));
    info.getDiplomacy().getDiplomacyList(1).addBonus(
        new DiplomacyBonus(DiplomacyBonusType.LONG_PEACE,
            info.getRace()));
    info.getDiplomacy().getDiplomacyList(1).addBonus(
        new DiplomacyBonus(DiplomacyBonusType.IN_TRADE_ALLIANCE,
            info.getRace()));
    info = map.getPlayerByIndex(1);
    info.getDiplomacy().getDiplomacyList(0).addBonus(
        new DiplomacyBonus(DiplomacyBonusType.IN_DEFENSIVE_PACT,
            info.getRace()));
    info.getDiplomacy().getDiplomacyList(0).addBonus(
        new DiplomacyBonus(DiplomacyBonusType.IN_TRADE_ALLIANCE,
            info.getRace()));
    info.getDiplomacy().getDiplomacyList(0).addBonus(
        new DiplomacyBonus(DiplomacyBonusType.LONG_PEACE,
            info.getRace()));
    info.setTotalCredits(120);
    for (Planet planet : map.getPlanetList()) {
      if (planet.getPlanetOwnerIndex() == 0) {
        planet.setWorkers(Planet.FOOD_FARMERS, 6);
      }
    }
    info = map.getPlayerByIndex(0);
    Leader[] leaders = LeaderUtility.buildLeaderPool(map, info);
    info = map.getPlayerByIndex(1);
    Leader leader = LeaderUtility.recruiteLeader(map, info, null);
    assertNotNull(leader);
    assertEquals(SpaceRace.HUMAN, leader.getRace());
    assertEquals(leaders[0], leader);
    info = map.getPlayerByIndex(0);
    LeaderUtility.buildLeaderPool(map, info);
    ArrayList<Leader> leaderList = info.getLeaderRecruitPool();
    assertNotEquals(leaderList.get(0), leader);
  }

}
