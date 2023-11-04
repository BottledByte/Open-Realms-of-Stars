package org.openRealmOfStars.player.leader;

import java.util.ArrayList;
import java.util.Collections;

import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SocialSystem;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.AttitudeScore;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.leader.stats.StatType;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.utilities.DiceGenerator;

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
* Leader Uitlity
*
*/
public final class LeaderUtility {

  /**
   * Special level for marking start ruler for realm.
   */
  public static final int LEVEL_START_RULER = -1;

  /**
   * Good perk type
   */
  public static final int PERK_TYPE_GOOD = 0;
  /**
   * Bad perk type
   */
  public static final int PERK_TYPE_BAD = 1;
  /**
   * Governor type perk
   */
  public static final int PERK_TYPE_GOVERNOR = 2;
  /**
   * Ruler type perk
   */
  public static final int PERK_TYPE_RULER = 3;
  /**
   * Commander type perk
   */
  public static final int PERK_TYPE_COMMANDER = 4;
  /**
   * Mental type perk
   */
  public static final int PERK_TYPE_MENTAL = 5;
  /**
   * Perks which is gained via actions.
   */
  public static final int PERK_TYPE_GAINED = 6;
  /**
   * Hidden constructor.
   */
  private LeaderUtility() {
    // Hiding the constructor.
  }

  /**
   * Get heir name with parent's surname
   * @param heirName Heir name
   * @param parentName Parent name
   * @return heirname with parent's surname
   */
  public static String getParentSurname(final String heirName,
      final String parentName) {
    String[] heirNames = heirName.split(" ");
    String[] parentNames = parentName.split(" ");
    if (parentNames.length >= 2 && heirNames.length >= 2) {
      return heirNames[0] + " "
          + parentNames[parentNames.length - 1];
    }
    return heirName;
  }
  /**
   * Create new leader based on 3 parameters.
   * @param info Realm who is creating new leader.
   * @param planet Home planet for leader. This can be null.
   * @param level Leader leve. Note this can be LEVEL_START_RULER for
   *        realm starting ruler.
   * @return Leader
   */
  public static Leader createLeader(final PlayerInfo info, final Planet planet,
      final int level) {
    Gender gender = Gender.NONE;
    if (info.getRace() != SpaceRace.MECHIONS
        && info.getRace() != SpaceRace.REBORGIANS) {
      if (level == LEVEL_START_RULER) {
        if (info.getGovernment() == GovernmentType.EMPIRE
            || info.getGovernment() == GovernmentType.KINGDOM) {
          if (info.getRace().getSocialSystem() == SocialSystem.PATRIARCHY) {
            gender = Gender.MALE;
          }
          if (info.getRace().getSocialSystem() == SocialSystem.MATRIARCHY) {
            gender = Gender.FEMALE;
          }
          if (info.getRace().getSocialSystem() == SocialSystem.EQUAL) {
            gender = Gender.getRandom();
          }
        } else {
          gender = Gender.getRandom();
        }
      } else {
        gender = Gender.getRandom();
      }
    }
    if (info.getRace() == SpaceRace.SYNTHDROIDS) {
      gender = Gender.FEMALE;
    }
    Leader leader = new Leader(NameGenerator.generateName(info.getRace(),
        gender));
    leader.setGender(gender);
    leader.setRace(info.getRace());
    leader.setExperience(0);
    if (planet != null) {
      leader.setHomeworld(planet.getName());
    } else {
      leader.setHomeworld("Unknown");
    }
    leader.setJob(Job.UNASSIGNED);
    leader.setTitle("");
    if (level == LEVEL_START_RULER) {
      leader.setLevel(1);
      leader.setAge(30 + DiceGenerator.getRandom(20));
      if (leader.getRace().getLifeSpan() < 80) {
        // Low life span starts about 10 years younger as starting ruler
        leader.setAge(25 + DiceGenerator.getRandom(10));
      }
      if (leader.getRace() == SpaceRace.MECHIONS) {
        leader.setAge(4 + DiceGenerator.getRandom(10));
      }
    } else {
      leader.setLevel(level);
      leader.setAge(23 + DiceGenerator.getRandom(15));
      if (leader.getRace() == SpaceRace.MECHIONS) {
        // Mechion leaders are always almost brand new ones.
        leader.setAge(1);
      }
    }
    for (int i = 0; i < leader.getLevel(); i++) {
      Perk[] newPerks = getNewPerks(leader, PERK_TYPE_GOOD);
      int index = DiceGenerator.getRandom(newPerks.length - 1);
      leader.getPerkList().add(newPerks[index]);
      if (DiceGenerator.getRandom(99)  < 10) {
        newPerks = getNewPerks(leader, PERK_TYPE_BAD);
        index = DiceGenerator.getRandom(newPerks.length - 1);
        leader.getPerkList().add(newPerks[index]);
      }
    }
    return leader;
  }

  /**
   * Recruit leader from recruit leader pool. This will find best planet to
   * hire and cost for it. After this realm will have new leader ready to use.
   * This method will also return hired leader or null if hire is not possible.
   * @param map StarMap
   * @param info Realm who is doing the hire
   * @param leaderJob Which job hire the leader. Null for generic.
   * @return Leader or null
   */
  public static Leader recruiteLeader(final StarMap map,
      final PlayerInfo info, final Job leaderJob) {
    RecruitableLeader[] leaders = LeaderUtility.buildRecuitableLeaderPool(map,
        info);
    int leaderCost = LeaderUtility.leaderRecruitCost(info);
    RecruitableLeader bestLeader = null;
    int bestScore = -999;
    for (RecruitableLeader leader : leaders) {
      int score = -999;
      if (leaderJob == null) {
        score = leader.getLeader().calculateLeaderGenericScore();
      } else {
        score = leader.getLeader().calculateLeaderScore(leaderJob);
      }
      if (leader.usePopulation()) {
        Planet planet = map.getPlanetByName(leader.getLeader().getHomeworld());
        if (planet != null) {
          if (planet.getTotalPopulation() - 1
              >= info.getRace().getMinimumPopulationForLeader()) {
            score = score + 1;
          }
          if (planet.getTotalPopulation() - 2
              >= info.getRace().getMinimumPopulationForLeader()) {
            score = score + 1;
          }
        }
      } else {
        score = score + 1;
      }
      if (leader.getCost() <= leaderCost) {
        score = score + 1;
      }
      if (leader.getCost() > leaderCost * 2) {
        score = score - 1;
      }
      if (leader.getCost() > info.getTotalCredits()) {
        // No credits for hiring.
        score = -1;
      }
      if (score > bestScore) {
        bestScore = score;
        bestLeader = leader;
      }
    }
    if (bestLeader != null) {
      leaderCost = bestLeader.getCost();
      if (info.getTotalCredits() >= leaderCost) {
        Leader leader = bestLeader.getLeader();
        leader.assignJob(Job.UNASSIGNED, info);
        info.getLeaderPool().add(leader);
        int realmIndex = bestLeader.getRealmIndex();
        PlayerInfo realm = map.getPlayerByIndex(realmIndex);
        realm.removeRecruitLeader(leader);
        if (realm == info) {
          info.setTotalCredits(info.getTotalCredits() - leaderCost);
          Planet planet = map.getPlanetByName(leader.getHomeworld());
          if (planet != null) {
            planet.takeColonist();
          }
        } else {
          info.setTotalCredits(info.getTotalCredits() - leaderCost);
          realm.setTotalCredits(realm.getTotalCredits() + leaderCost);
          Message msg = new Message(MessageType.LEADER, info.getEmpireName()
              + " hire leader called " + leader.getCallName() + " from "
              + realm.getEmpireName() + " with " + leaderCost + " credits."
              + " This leader is from " + leader.getHomeworld() + ".",
              Icons.getIconByName(Icons.ICON_GOVERNOR));
          realm.getMsgList().addUpcomingMessage(msg);
        }
        return bestLeader.getLeader();
      }
    }
    return null;
  }

  /**
   * Build Leader pool for recruit
   * @param map StarMap
   * @param player PlayerInfo which realm to add leader pool.
   * @return Leader pool for recruit
   */
  public static Leader[] buildLeaderPool(final StarMap map,
      final PlayerInfo player) {
    ArrayList<Leader> leaders = new ArrayList<>();
    for (Planet planet : map.getPlanetList()) {
      if (planet.getPlanetPlayerInfo() == player) {
        int level = 1;
        int xp = 0;
        for (Building building : planet.getBuildingList()) {
          if (building.getName().equals("Barracks")) {
            xp = 50;
          }
          if (building.getName().equals("Space academy")) {
            level++;
          }
        }
        Leader leader = LeaderUtility.createLeader(player, planet, level);
        leader.setExperience(xp);
        leader.assignJob(Job.UNASSIGNED, player);
        if (planet.getTotalPopulation() >= player.getRace()
              .getMinimumPopulationForLeader()) {
          leaders.add(leader);
        }
      }
    }
    ArrayList<Leader> currentLeaders = player.getLeaderRecruitPool();
    for (Leader leader : leaders) {
      boolean leaderFromPlanetAlready = false;
      for (Leader comparison : currentLeaders) {
        if (leader.getHomeworld().equals(comparison.getHomeworld())) {
          leaderFromPlanetAlready = true;
        }
      }
      if (!leaderFromPlanetAlready) {
        player.addRecruitLeader(leader);
      }
    }
    ArrayList<Leader> removeLeader = new ArrayList<>();
    for (Leader leader : player.getLeaderRecruitPool()) {
      Planet homePlanet = map.getPlanetByName(leader.getHomeworld());
      if (homePlanet != null
          && homePlanet.getTotalPopulation()
          < player.getRace().getMinimumPopulationForLeader()) {
        removeLeader.add(leader);
      }
    }
    for (Leader leader : removeLeader) {
      player.removeRecruitLeader(leader);
    }
    return player.getLeaderRecruitPool().toArray(
        new Leader[player.getLeaderRecruitPool().size()]);
  }
  /**
   * Build Leader pool for recruit
   * @param map StarMap
   * @param player PlayerInfo which realm to add leader pool.
   * @return Leader pool for recruitable leaders
   */
  public static RecruitableLeader[] buildRecuitableLeaderPool(final StarMap map,
      final PlayerInfo player) {
    ArrayList<RecruitableLeader> leaders = new ArrayList<>();
    Leader[] leaderArray = buildLeaderPool(map, player);
    int leaderCost = LeaderUtility.leaderRecruitCost(player);
    int index = map.getPlayerList().getIndex(player);
    for (Leader leader : leaderArray) {
      RecruitableLeader recruit = new RecruitableLeader(leader, leaderCost,
          true, index);
      leaders.add(recruit);
    }
    if (!player.getGovernment().isXenophopic()) {
      for (int i = 0; i < map.getPlayerList().getCurrentMaxRealms(); i++) {
        PlayerInfo info = map.getPlayerByIndex(i);
        if (info != player) {
          if (player.getDiplomacy().isAlliance(i)
              || player.getDiplomacy().isDefensivePact(i)) {
            leaderArray = buildLeaderPool(map, info);
            for (Leader leader : leaderArray) {
              RecruitableLeader recruit = new RecruitableLeader(leader,
                  leaderCost * 2, false, i);
              leaders.add(recruit);
            }
          } else if (player.getDiplomacy().isTradeAlliance(i)) {
            leaderArray = buildLeaderPool(map, info);
            for (Leader leader : leaderArray) {
              RecruitableLeader recruit = new RecruitableLeader(leader,
                  leaderCost * 3, false, i);
              leaders.add(recruit);
            }
          }
        }
      }
    }
    return leaders.toArray(
        new RecruitableLeader[leaders.size()]);
  }

  /**
   * Assign leader for target job.
   * @param leader Leader to assign
   * @param player Realm aka PlayerInfo
   * @param planets List of all planets in starmap
   * @param target Planet or fleet.
   * @return True if assign was successful.
   */
  public static boolean assignLeader(final Leader leader,
      final PlayerInfo player, final ArrayList<Planet> planets,
      final Object target) {
    boolean result = false;
    Planet activePlanet = null;
    Fleet activeFleet = null;
    if (target instanceof Planet) {
      activePlanet = (Planet) target;
    }
    if (target instanceof Fleet) {
      activeFleet = (Fleet) target;
    }
    if (leader != null && (leader.getJob() == Job.UNASSIGNED
        || leader.getJob() == Job.COMMANDER
        || leader.getJob() == Job.GOVERNOR)) {
      if (leader.getJob() == Job.COMMANDER) {
        for (int i = 0; i < player.getFleets().getNumberOfFleets(); i++) {
          Fleet fleet = player.getFleets().getByIndex(i);
          if (fleet.getCommander() == leader) {
            Mission mission = player.getMissions().getMissionForFleet(
                fleet.getName(), MissionType.ESPIONAGE_MISSION);
            if (mission != null) {
              // Espionage mission underwork, not changing
              return false;
            }
            fleet.setCommander(null);
            break;
          }
        }
      }
      if (leader.getJob() == Job.GOVERNOR) {
        for (Planet planet : planets) {
          if (planet.getGovernor() == leader) {
            planet.setGovernor(null);
          }
        }
      }
      if (activePlanet != null) {
        if (activePlanet.getGovernor() != null) {
          activePlanet.getGovernor().assignJob(Job.UNASSIGNED, player);
        }
        activePlanet.setGovernor(leader);
        result = true;
      }
      if (activeFleet != null) {
        if (activeFleet.getCommander() != null) {
          activeFleet.getCommander().assignJob(Job.UNASSIGNED, player);
        }
        activeFleet.setCommander(leader);
        leader.setTitle(LeaderUtility.createTitleForLeader(leader, player));
        if (!player.isHuman()) {
          Mission mission = player.getMissions().getMissionForFleet(
              activeFleet.getName());
          if (mission != null && mission.getType() == MissionType.SPY_MISSION) {
            mission.setType(MissionType.ESPIONAGE_MISSION);
            mission.setPhase(MissionPhase.TREKKING);
          }
        }
        result = true;
      }
    }
    return result;
  }
  /**
   * Adds random perks.
   * 60% new perk is related to current job.
   * 40% any good perk is added.
   * After adding good perk there is 10% that also bad perk is added.
   * @param leader who will get new perk
   * @return Array of added new perks.
   */
  public static Perk[] addRandomPerks(final Leader leader) {
    boolean jobBasedPerkAdded = false;
    ArrayList<Perk> addedPerks = new ArrayList<>();
    Perk[] goodPerks = getNewPerks(leader, PERK_TYPE_GOOD);
    if (DiceGenerator.getRandom(99) < 60 || goodPerks.length == 0) {
      // Add Perk based on job
      Perk[] newPerks = null;
      if (leader.getJob() == Job.RULER) {
        newPerks = getNewPerks(leader, PERK_TYPE_RULER);
      }
      if (leader.getJob() == Job.GOVERNOR) {
        newPerks = getNewPerks(leader, PERK_TYPE_GOVERNOR);
      }
      if (leader.getJob() == Job.COMMANDER) {
        newPerks = getNewPerks(leader, PERK_TYPE_COMMANDER);
      }
      if (newPerks != null && newPerks.length > 0) {
        int index = DiceGenerator.getRandom(newPerks.length - 1);
        leader.getPerkList().add(newPerks[index]);
        addedPerks.add(newPerks[index]);
        jobBasedPerkAdded = true;
      }
    }
    if (!jobBasedPerkAdded && goodPerks.length > 0) {
      int index = DiceGenerator.getRandom(goodPerks.length - 1);
      leader.getPerkList().add(goodPerks[index]);
      addedPerks.add(goodPerks[index]);
    }
    if (DiceGenerator.getRandom(99)  < 10) {
      Perk[] newPerks = getNewPerks(leader, PERK_TYPE_BAD);
      if (newPerks.length > 0) {
        int index = DiceGenerator.getRandom(newPerks.length - 1);
        leader.getPerkList().add(newPerks[index]);
        addedPerks.add(newPerks[index]);
      }
    }
    return addedPerks.toArray(new Perk[0]);
  }

  /**
   * Get best leader training planet for realm
   * @param planets Array of planet
   * @param realm Realm who is about to train leader
   * @return Best planet or null if nothing is available.
   */
  public static Planet getBestLeaderTrainingPlanet(
      final ArrayList<Planet> planets, final PlayerInfo realm) {
    Planet result = null;
    int bestPlanetValue = 0;
    for (Planet planet : planets) {
      if (planet.getPlanetPlayerInfo() == realm) {
        int value = planet.getTotalPopulation();
        if (value >= realm.getRace().getMinimumPopulationForLeader()) {
          for (Building building : planet.getBuildingList()) {
            if (building.getName().equals("Barracks")) {
              value = value + 20;
            }
            if (building.getName().equals("Space academy")) {
              value = value + 40;
            }
          }
          if (value > bestPlanetValue) {
            bestPlanetValue = value;
            result = planet;
          }
        }
      }
    }
    return result;
  }
  /**
   * Calculate leader recruit cost.
   * @param realm PlayerInfo
   * @return Leader recruit cost.
   */
  public static int leaderRecruitCost(final PlayerInfo realm) {
    int result = 0;
    int leaders = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getParent() == null && (leader.getJob() == Job.COMMANDER
          || leader.getJob() == Job.GOVERNOR
          || leader.getJob() == Job.RULER
          || leader.getJob() == Job.UNASSIGNED)) {
        leaders++;
      }
    }
    int cost = realm.getGovernment().leaderRecruitCost();
    if (leaders < realm.getGovernment().leaderPoolLimit()) {
      return cost;
    }
    if (leaders < 11) {
      return cost * leaders;
    }
    result = cost * leaders;
    if (result < 100) {
      result = result + 100;
    }
    return result;
  }

  /**
   * Get Ruler title for leader and government type.
   * @param gender Ruler gender
   * @param government Government type
   * @return Ruler title
   */
  public static String getRulerTitle(final Gender gender,
      final GovernmentType government) {
    switch (government) {
    default:
    case DEMOCRACY:
    case FEDERATION:
    case REPUBLIC:
    case UNION: {
      return "President";
    }
    case EMPIRE: {
      if (gender == Gender.FEMALE) {
        return "Empiress";
      }
      return "Emperor";
    }
    case FEUDALISM:
    case NEST:
    case KINGDOM: {
      if (gender == Gender.FEMALE) {
        return "Queen";
      }
      return "King";
    }
    case HORDE:
    case MECHANICAL_HORDE:
    case CLAN: {
      return "Chief";
    }
    case UTOPIA: {
      return "Wise";
    }
    case ENTERPRISE: {
      return "CEO";
    }
    case SYNDICATE: {
      return "Boss";
    }
    case GUILD:
    case HEGEMONY:
    case REGIME:
    case COLLECTIVE: {
      return "Leader";
    }
    case TECHNOCRACY: {
      return "Master engineer";
    }
    case AI: {
      return "Main Process";
    }
    case SPACE_PIRATES: {
      return "Leader";
    }
    case HIVEMIND: {
      return "Master";
    }
    case HIERARCHY: {
      if (gender == Gender.FEMALE) {
        return "Lady";
      }
      return "Lord";
    }
  }
  }
  /**
   * Create Title for leader
   * @param leader Leader to whom to create title
   * @param realm Realm where leader belongs to.
   * @return Title name as string.
   */
  public static String createTitleForLeader(final Leader leader,
      final PlayerInfo realm) {
    StringBuilder sb = new StringBuilder();
    if (leader.getJob() == Job.RULER) {
      sb.append(getRulerTitle(leader.getGender(), realm.getGovernment()));
    }
    if (leader.getJob() == Job.COMMANDER) {
      if (leader.getMilitaryRank() == MilitaryRank.CIVILIAN) {
        leader.setMilitaryRank(MilitaryRank.ENSIGN);
      }
      sb.append(leader.getMilitaryRank().toString());
    }
    if (leader.getJob() == Job.GOVERNOR) {
      if (leader.getParent() != null) {
        if (leader.getGender() == Gender.FEMALE) {
          sb.append("Princess");
        } else {
          sb.append("Prince");
        }
      } else {
        sb.append("Governor");
      }
    }
    if (leader.getJob() == Job.TOO_YOUNG) {
      if (leader.getGender() == Gender.FEMALE) {
        sb.append("Princess");
      } else {
        sb.append("Prince");
      }
    }
    if (leader.getJob() == Job.UNASSIGNED || leader.getJob() == Job.PRISON) {
      if (leader.getParent() != null) {
        if (leader.getGender() == Gender.FEMALE) {
          sb.append("Princess");
        } else {
          sb.append("Prince");
        }
      } else if (leader.getMilitaryRank() != MilitaryRank.CIVILIAN) {
        sb.append(leader.getMilitaryRank().toString());
      } else {
       sb.append("");
      }
    }
    // Dead leader keeps it previous title, no need to change it.
    return sb.toString();
  }
  /**
   * Get list of new perks that leader is missing.
   * @param leader Leader whose perks to check.
   * @param perkType Perk type good, bad, ruler etc.
   * @return Array of new perks.
   */
  public static Perk[] getNewPerks(final Leader leader, final int perkType) {
    ArrayList<Perk> list = new ArrayList<>();
    for (Perk perk : Perk.values()) {
      if (perkType == PERK_TYPE_GOOD && perk.isBadPerk()) {
        continue;
      }
      if (perkType == PERK_TYPE_BAD && !perk.isBadPerk()) {
        continue;
      }
      if (perkType == PERK_TYPE_RULER && !perk.isRulerPerk()) {
        continue;
      }
      if (perkType == PERK_TYPE_GOVERNOR && !perk.isGovernorPerk()) {
        continue;
      }
      if (perkType == PERK_TYPE_COMMANDER && !perk.isFleetCommanderPerk()) {
        continue;
      }
      if (perkType == PERK_TYPE_MENTAL && !perk.isMentalPerk()) {
        continue;
      }
      if (perk.isGainedPerk()) {
        continue;
      }
      boolean alreadyHas = false;
      for (Perk leaderPerk : leader.getPerkList()) {
        if (perk == leaderPerk) {
          alreadyHas = true;
          break;
        }
      }
      if (!perk.isPerkAllowedForRace(leader.getRace())) {
        alreadyHas = true;
      }
      if (!alreadyHas) {
        list.add(perk);
      }
    }
    return list.toArray(new Perk[list.size()]);
  }

  /**
   * Get How strong leader is.
   * This is used in horde and clan goverments to determine how
   * good candidate leader is for ruler.
   * @param leader Leader to evaluate
   * @return Strong score
   */
  private static int getStrongPoints(final Leader leader) {
    int result = 0;
    if (leader.getAge() > 17 && leader.getAge() < 25) {
      result = 15 + leader.getAge();
    }
    if (leader.getAge() > 24 && leader.getAge() < 31) {
      result = 70 - leader.getAge();
    }
    if (leader.getAge() > 30 && leader.getAge() < 40) {
      result = 68 - leader.getAge();
    }
    if (leader.getAge() > 39 && leader.getAge() < 50) {
      result = 65 - leader.getAge();
    }
    if (leader.getAge() > 49 && leader.getAge() < 60) {
      result = 60 - leader.getAge();
    }
    if (leader.getAge() > 59) {
      result = 0;
    }
    if (leader.getParent() != null) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.COMBAT_MASTER)) {
      result = result + 32;
    }
    if (leader.hasPerk(Perk.COMBAT_TACTICIAN)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.DISCIPLINE)) {
      result = result + 22;
    }
    if (leader.hasPerk(Perk.CHARISMATIC)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.COUNTER_AGENT)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.CORRUPTED)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.CONVICT)) {
      result = result + 2;
    }
    if (leader.hasPerk(Perk.MILITARISTIC)) {
      result = result + 25;
    }
    if (leader.hasPerk(Perk.PACIFIST)) {
      result = result - 40;
    }
    if (leader.hasPerk(Perk.POWER_HUNGRY)) {
      result = result + 50;
    }
    if (leader.hasPerk(Perk.WEALTHY)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.WARLORD)) {
      result = result + 35;
    }
    if (leader.hasPerk(Perk.WEAK_LEADER)) {
      result = result - 30;
    }
    if (leader.hasPerk(Perk.EXPLORER)) {
      result = result + 7;
    }
    if (leader.hasPerk(Perk.SKILLFUL)) {
      result = result + 3;
    }
    if (leader.hasPerk(Perk.INCOMPETENT)) {
      result = result - 10;
    }
    return result;
  }

  /**
   * Get Strongest leader for ruler.
   * @param realm PlayerInfo
   * @param  xenophobe If true allows only original space race rulers.
   * @return Strongest leader in realm.
   */
  public static Leader getStrongestLeader(final PlayerInfo realm,
      final boolean xenophobe) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getStrongPoints(leader);
      if (xenophobe && leader.getRace() != realm.getRace()) {
        score = 0;
      }
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Realm has heir only too young heirs.
   * @param realm Realm to check
   * @return True if all heirs are too young to rule.
   */
  public static boolean hasTooYoungHeirs(final PlayerInfo realm) {
    boolean hasHeirs = false;
    boolean onlyTooYoungHeirs = true;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getParent() != null) {
        if (leader.getJob() == Job.DEAD || leader.getJob() == Job.PRISON) {
          continue;
        }
        hasHeirs = true;
        if (leader.getJob() != Job.TOO_YOUNG) {
          onlyTooYoungHeirs = false;
        }
      }
    }
    if (hasHeirs && onlyTooYoungHeirs) {
      return true;
    }
    return false;
  }
  /**
   * Get heir leader for ruler.
   * @param realm PlayerInfo
   * @return heir leader in realm.
   */
  public static Leader getNextHeir(final PlayerInfo realm) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getStrongHeirPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }
  /**
   * Get next possible heir leader for ruler.
   * @param realm PlayerInfo
   * @return heir leader in realm.
   */
  public static Leader getNextPossbileHeir(final PlayerInfo realm) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getStrongHeirPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Get How strong heir is.
   * This is used in kingdom and empire goverments to determine how
   * good candidate leader is for ruler.
   * @param leader Leader to evaluate
   * @return Strong score
   */
  private static int getStrongHeirPoints(final Leader leader) {
    int result = 0;
    if (leader.getParent() != null) {
      result = leader.getAge() * 4;
      if (leader.hasPerk(Perk.COMBAT_MASTER)) {
        result = result + 2;
      }
      if (leader.hasPerk(Perk.COMBAT_TACTICIAN)) {
        result = result + 2;
      }
      if (leader.hasPerk(Perk.DISCIPLINE)) {
        result = result + 2;
      }
      if (leader.hasPerk(Perk.CHARISMATIC)) {
        result = result + 2;
      }
      if (leader.hasPerk(Perk.COUNTER_AGENT)) {
        result = result + 2;
      }
      if (leader.hasPerk(Perk.CORRUPTED)) {
        result = result + 3;
      }
      if (leader.hasPerk(Perk.MILITARISTIC)) {
        result = result + 2;
      }
      if (leader.hasPerk(Perk.POWER_HUNGRY)) {
        result = result + 10;
      }
      if (leader.hasPerk(Perk.WEALTHY)) {
        result = result + 10;
      }
      if (leader.hasPerk(Perk.WARLORD)) {
        result = result + 3;
      }
      if (leader.hasPerk(Perk.WEAK_LEADER)) {
        result = result - 10;
      }
      if (leader.hasPerk(Perk.EXPLORER)) {
        result = result + 1;
      }
      if (leader.hasPerk(Perk.SKILLFUL)) {
        result = result + 3;
      }
      if (leader.hasPerk(Perk.INCOMPETENT)) {
        result = result - 10;
      }
      if (leader.hasPerk(Perk.CONVICT)) {
        result = result - 10;
      }
    }
    return result;
  }

  /**
   * Get best business man as leader. Used for enterprise and guild ruler.
   * @param leader Leader to evaluate
   * @return Strong score
   */
  private static int getBusinessPoints(final Leader leader) {
    int result = 0;
    if (leader.getAge() > 30) {
      result = result + 2;
    }
    if (leader.getAge() > 40) {
      result = result + 2;
    }
    if (leader.getAge() > 50) {
      result = result + 2;
    }
    if (leader.hasPerk(Perk.CHARISMATIC)) {
      result = result + 15;
    }
    if (leader.hasPerk(Perk.DIPLOMATIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.CRUEL)) {
      result = result - 30;
    }
    if (leader.hasPerk(Perk.GOOD_LEADER)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.INDUSTRIAL)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.MINER)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.MERCHANT)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.ACADEMIC)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.ADDICTED)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.POWER_HUNGRY)) {
      result = result + 40;
    }
    if (leader.hasPerk(Perk.WEALTHY)) {
      result = result + 30;
    }
    if (leader.hasPerk(Perk.CORRUPTED)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.TRADER)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.REPULSIVE)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.MICRO_MANAGER)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.SLOW_LEARNER)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.STUPID)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.MAD)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.LOGICAL)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.SKILLFUL)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.INCOMPETENT)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.CONVICT)) {
      result = result - 20;
    }
    return result;
  }

  /**
   * Get CEO leader for ruler.
   * @param realm PlayerInfo
   * @return ceo leader in realm.
   */
  public static Leader getNextCeo(final PlayerInfo realm) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getBusinessPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Get democratic scores for ruler.
   * @param leader Leader to evaluate
   * @return Strong score
   */
  private static int getDemocraticPoints(final Leader leader) {
    int result = 0;
    if (leader.getAge() > 30) {
      result = result + 2;
    }
    if (leader.getAge() > 40) {
      result = result + 2;
    }
    if (leader.getAge() > 50) {
      result = result + 2;
    }
    if (leader.getAge() > 60) {
      result = result + 2;
    }
    if (leader.getAge() > 70) {
      result = result - 2;
    }
    if (leader.getAge() > 80) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.CHARISMATIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.CRUEL)) {
      result = result - 30;
    }
    if (leader.hasPerk(Perk.DIPLOMATIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.GOOD_LEADER)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.INDUSTRIAL)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.MINER)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.MERCHANT)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.ACADEMIC)) {
      result = result + 15;
    }
    if (leader.hasPerk(Perk.ADDICTED)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.POWER_HUNGRY)) {
      result = result + 40;
    }
    if (leader.hasPerk(Perk.WEALTHY)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.CORRUPTED)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.ARTISTIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.REPULSIVE)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.MICRO_MANAGER)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.SLOW_LEARNER)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.STUPID)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.PACIFIST)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.PEACEFUL)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.LOGICAL)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.MAD)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.SKILLFUL)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.INCOMPETENT)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.CONVICT)) {
      result = result - 10;
    }
    // Simulates voting for leader
    result = result + DiceGenerator.getRandom(20);
    return result;
  }

  /**
   * Get best democratic ruler.
   * @param realm Realm who is evaluating new ruler
   * @return Democratic ruler
   */
  public static Leader getNextDemocraticRuler(final PlayerInfo realm) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getDemocraticPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Get scientist scores for leader.
   * @param leader Leader to evaluate
   * @return Scientist score
   */
  private static int getScientistPoints(final Leader leader) {
    int result = 0;
    if (leader.getAge() > 30) {
      result = result + 1;
    }
    if (leader.getAge() > 40) {
      result = result + 1;
    }
    if (leader.getAge() > 50) {
      result = result + 1;
    }
    if (leader.getAge() > 60) {
      result = result + 1;
    }
    if (leader.getAge() > 70) {
      result = result + 1;
    }
    if (leader.getAge() > 80) {
      result = result + 1;
    }
    if (leader.hasPerk(Perk.CRUEL)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.DIPLOMATIC)) {
      result = result + 1;
    }
    if (leader.hasPerk(Perk.GOOD_LEADER)) {
      result = result + 1;
    }
    if (leader.hasPerk(Perk.INDUSTRIAL)) {
      result = result + 2;
    }
    if (leader.hasPerk(Perk.MINER)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.MERCHANT)) {
      result = result + 2;
    }
    if (leader.hasPerk(Perk.ACADEMIC)) {
      result = result + 25;
    }
    if (leader.hasPerk(Perk.ADDICTED)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.POWER_HUNGRY)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.WEALTHY)) {
      result = result + 3;
    }
    if (leader.hasPerk(Perk.CORRUPTED)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.ARTISTIC)) {
      result = result + 2;
    }
    if (leader.hasPerk(Perk.REPULSIVE)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.MICRO_MANAGER)) {
      result = result + 1;
    }
    if (leader.hasPerk(Perk.SLOW_LEARNER)) {
      result = result - 15;
    }
    if (leader.hasPerk(Perk.STUPID)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.SCIENTIST)) {
      result = result + 30;
    }
    if (leader.hasPerk(Perk.ARCHAEOLOGIST)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.EXPLORER)) {
      result = result + 3;
    }
    if (leader.hasPerk(Perk.SCANNER_EXPERT)) {
      result = result + 3;
    }
    if (leader.hasPerk(Perk.FTL_ENGINEER)) {
      result = result + 3;
    }
    if (leader.hasPerk(Perk.MASTER_ENGINEER)) {
      result = result + 3;
    }
    if (leader.hasPerk(Perk.LOGICAL)) {
      result = result + 1;
    }
    if (leader.hasPerk(Perk.MAD)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.SKILLFUL)) {
      result = result + 1;
    }
    if (leader.hasPerk(Perk.INCOMPETENT)) {
      result = result - 10;
    }
    return result;
  }

  /**
   * Get best scientist leader.
   * @param realm Realm who is evaluating best scientist
   * @return Best scientist
   */
  public static Leader getBestScientist(final PlayerInfo realm) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getScientistPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Get federation scores for ruler.
   * @param leader Leader to evaluate
   * @return Strong score
   */
  private static int getFederationPoints(final Leader leader) {
    int result = 0;
    if (leader.getAge() > 30) {
      result = result + 1;
    }
    if (leader.getAge() > 40) {
      result = result + 2;
    }
    if (leader.getAge() > 50) {
      result = result + 2;
    }
    if (leader.getAge() > 60) {
      result = result + 2;
    }
    if (leader.getAge() > 70) {
      result = result + 2;
    }
    if (leader.getAge() > 80) {
      result = result - 2;
    }
    if (leader.hasPerk(Perk.CHARISMATIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.DIPLOMATIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.CRUEL)) {
      result = result - 30;
    }
    if (leader.hasPerk(Perk.GOOD_LEADER)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.INDUSTRIAL)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.MINER)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.MERCHANT)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.ACADEMIC)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.ADDICTED)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.POWER_HUNGRY)) {
      result = result + 40;
    }
    if (leader.hasPerk(Perk.WEALTHY)) {
      result = result + 15;
    }
    if (leader.hasPerk(Perk.CORRUPTED)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.MILITARISTIC)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.REPULSIVE)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.MICRO_MANAGER)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.SLOW_LEARNER)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.STUPID)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.PACIFIST)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.PEACEFUL)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.AGGRESSIVE)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.MAD)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.WARLORD)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.SKILLFUL)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.INCOMPETENT)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.CONVICT)) {
      result = result - 10;
    }
    // Simulates voting for leader
    result = result + DiceGenerator.getRandom(20);
    return result;
  }

  /**
   * Get best federation ruler.
   * @param realm Realm who is evaluating new ruler
   * @return Federation ruler
   */
  public static Leader getNextFederationRuler(final PlayerInfo realm) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getFederationPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Get hegemony scores for ruler.
   * @param leader Leader to evaluate
   * @return Strong score
   */
  private static int getHegemonyPoints(final Leader leader) {
    int result = 0;
    if (leader.getAge() > 30) {
      result = result + 2;
    }
    if (leader.getAge() > 40) {
      result = result + 2;
    }
    if (leader.getAge() > 50) {
      result = result + 1;
    }
    if (leader.getAge() > 60) {
      result = result - 1;
    }
    if (leader.getAge() > 70) {
      result = result - 1;
    }
    if (leader.getAge() > 80) {
      result = result - 2;
    }
    if (leader.hasPerk(Perk.CHARISMATIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.DIPLOMATIC)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.GOOD_LEADER)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.SCIENTIST)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.ARCHAEOLOGIST)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.ACADEMIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.ADDICTED)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.POWER_HUNGRY)) {
      result = result + 40;
    }
    if (leader.hasPerk(Perk.WEALTHY)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.CORRUPTED)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.EXPLORER)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.FTL_ENGINEER)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.MASTER_ENGINEER)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.MICRO_MANAGER)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.SLOW_LEARNER)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.STUPID)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.SCANNER_EXPERT)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.WEAK_LEADER)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.PEACEFUL)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.MAD)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.SKILLFUL)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.INCOMPETENT)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.CONVICT)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.CRUEL)) {
      result = result - 10;
    }
    return result;
  }

  /**
   * Get best hegemony ruler.
   * @param realm Realm who is evaluating new ruler
   * @param xenophobe If true only original space race is allowed to be ruler.
   * @return Hegemony ruler
   */
  public static Leader getNextHegemonyRuler(final PlayerInfo realm,
      final boolean xenophobe) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getHegemonyPoints(leader);
      if (xenophobe && leader.getRace() != realm.getRace()) {
        score = 0;
      }
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Get air scores for ruler.
   * @param leader Leader to evaluate
   * @return Strong score
   */
  private static int getAiPoints(final Leader leader) {
    int result = 0;
    if (leader.hasPerk(Perk.CHARISMATIC)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.GOOD_LEADER)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.SCIENTIST)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.ARCHAEOLOGIST)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.ACADEMIC)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.ADDICTED)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.POWER_HUNGRY)) {
      result = result + 40;
    }
    if (leader.hasPerk(Perk.WEALTHY)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.CORRUPTED)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.CRUEL)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.MERCHANT)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.MILITARISTIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.PACIFIST)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.PEACEFUL)) {
      result = result - 15;
    }
    if (leader.hasPerk(Perk.AGGRESSIVE)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.DIPLOMATIC)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.SLOW_LEARNER)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.STUPID)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.REPULSIVE)) {
      result = result - 15;
    }
    if (leader.hasPerk(Perk.WEAK_LEADER)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.WARLORD)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.LOGICAL)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.SKILLFUL)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.INCOMPETENT)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.CONVICT)) {
      result = result - 10;
    }
    return result;
  }

  /**
   * Get best ai ruler.
   * @param realm Realm who is evaluating new ruler
   * @return AI ruler
   */
  public static Leader getNextAiRuler(final PlayerInfo realm) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getAiPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Get Next ruler from the leader pool.
   * @param realm PlayerInfo
   * @return Next leader or null if not available.
   */
  public static Leader getNextRuler(final PlayerInfo realm) {
    Leader bestLeader = null;
    switch (realm.getGovernment()) {
      default:
      case CLAN:
      case HORDE:
      case MECHANICAL_HORDE:
      case HIVEMIND:
      case NEST: {
        bestLeader = getStrongestLeader(realm, true);
        break;
      }
      case HIERARCHY:
      case REGIME: {
        bestLeader = getStrongestLeader(realm, false);
        break;
      }
      case EMPIRE:
      case FEUDALISM:
      case KINGDOM: {
        bestLeader = getNextHeir(realm);
        if (bestLeader == null && hasTooYoungHeirs(realm)) {
          Message msg = new Message(MessageType.LEADER,
              realm.getEmpireName() + " has heirs but all are too young to be"
                  + " ruler. This is difficult time...",
                  Icons.getIconByName(Icons.ICON_RULER));
          realm.getMsgList().addUpcomingMessage(msg);
          break;
        }
        if (bestLeader == null) {
          bestLeader = getStrongestLeader(realm, true);
        }
        break;
      }
      case GUILD:
      case ENTERPRISE: {
        bestLeader = getNextCeo(realm);
        break;
      }
      case DEMOCRACY:
      case TECHNOCRACY:
      case UNION: {
        bestLeader = getNextDemocraticRuler(realm);
        break;
      }
      case FEDERATION:
      case REPUBLIC: {
        bestLeader = getNextFederationRuler(realm);
        break;
      }
      case HEGEMONY: {
        bestLeader = getNextHegemonyRuler(realm, true);
        break;
      }
      case UTOPIA: {
        bestLeader = getNextHegemonyRuler(realm, false);
        break;
      }
      case AI:
      case COLLECTIVE: {
        bestLeader = getNextAiRuler(realm);
        break;
      }
    }
    return bestLeader;
  }

  /**
   * Get random living leader from realm.
   * @param realm Realm whose leaders are being looked for.
   * @return Leader or null
   */
  public static Leader getRandomLivingLeader(final PlayerInfo realm) {
    ArrayList<Leader> tmpList = new ArrayList<>();
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() != Job.DEAD) {
        tmpList.add(leader);
      }
    }
    if (tmpList.size() > 0) {
      int index = DiceGenerator.getRandom(tmpList.size() - 1);
      return tmpList.get(index);
    }
    return null;
  }
  /**
   * Assign Leader as a realm ruler.
   * @param ruler Leader to assign as ruler
   * @param realm Realm which is about to get new ruler.
   * @param map StarMap for going through planets.
   */
  public static void assignLeaderAsRuler(final Leader ruler,
      final PlayerInfo realm, final StarMap map) {
    if (ruler != null && (ruler.getJob() == Job.UNASSIGNED
        || ruler.getJob() == Job.COMMANDER
        || ruler.getJob() == Job.GOVERNOR
        || ruler.getJob() == Job.RULER)) {
      if (ruler.getJob() == Job.COMMANDER) {
        for (int i = 0; i < realm.getFleets().getNumberOfFleets(); i++) {
          Fleet fleet = realm.getFleets().getByIndex(i);
          if (fleet.getCommander() == ruler) {
            fleet.setCommander(null);
            break;
          }
        }
      }
      if (ruler.getJob() == Job.GOVERNOR) {
        for (Planet planet : map.getPlanetList()) {
          if (planet.getGovernor() == ruler) {
            planet.setGovernor(null);
          }
        }
      }
      realm.setRuler(ruler);
      ruler.setTimeInJob(0);
    }
  }

  /**
   * Get Icon for leader based on leader current job.
   * @param leader Leader to get icon
   * @return Icon16x16
   */
  public static Icon16x16 getIconBasedOnLeaderJob(final Leader leader) {
    switch (leader.getJob()) {
      case RULER: {
        return Icons.getIconByName(Icons.ICON_RULER);
      }
      case COMMANDER: {
        return Icons.getIconByName(Icons.ICON_COMMANDER);
     }
      case GOVERNOR: {
        return Icons.getIconByName(Icons.ICON_GOVERNOR);
      }
      case PRISON: {
        return Icons.getIconByName(Icons.ICON_PRISON);
      }
      case DEAD: {
        return Icons.getIconByName(Icons.ICON_DEATH);
      }
      default:
      case UNASSIGNED: {
        return Icons.getIconByName(Icons.ICON_AIRLOCK_OPEN);
      }
      case TOO_YOUNG: {
        return Icons.getIconByName(Icons.ICON_TOO_YOUNG);
      }
    }
  }

  /**
   * Get ruler attitude
   * @param leader Leader for getting the attitude
   * @return Attitude
   */
  public static Attitude getRulerAttitude(final Leader leader) {
    AttitudeScore aggressive = new AttitudeScore(Attitude.AGGRESSIVE);
    AttitudeScore backstabbing = new AttitudeScore(Attitude.BACKSTABBING);
    AttitudeScore diplomatic = new AttitudeScore(Attitude.DIPLOMATIC);
    AttitudeScore expansionist = new AttitudeScore(Attitude.EXPANSIONIST);
    AttitudeScore logical = new AttitudeScore(Attitude.LOGICAL);
    AttitudeScore merchantical = new AttitudeScore(Attitude.MERCHANTICAL);
    AttitudeScore militaristic = new AttitudeScore(Attitude.MILITARISTIC);
    AttitudeScore peaceful = new AttitudeScore(Attitude.PEACEFUL);
    AttitudeScore scientific = new AttitudeScore(Attitude.SCIENTIFIC);
    for (Perk perk : leader.getPerkList()) {
      if (perk == Perk.ACADEMIC || perk == Perk.SCIENTIST
          || perk == Perk.ARCHAEOLOGIST) {
        scientific.setValue(scientific.getValue() + 5);
      }
      if (perk == Perk.ARTISTIC) {
        peaceful.setValue(peaceful.getValue() + 1);
      }
      if (perk == Perk.CHARISMATIC) {
        peaceful.setValue(peaceful.getValue() + 1);
        diplomatic.setValue(peaceful.getValue() + 5);
      }
      if (perk == Perk.CHATTERBOX) {
        diplomatic.setValue(diplomatic.getValue() + 1);
      }
      if (perk == Perk.COMBAT_MASTER) {
        aggressive.setValue(aggressive.getValue() + 3);
        militaristic.setValue(militaristic.getValue() + 1);
        peaceful.setValue(peaceful.getValue() - 1);
      }
      if (perk == Perk.COMBAT_TACTICIAN) {
        aggressive.setValue(aggressive.getValue() + 3);
        militaristic.setValue(militaristic.getValue() + 1);
        peaceful.setValue(peaceful.getValue() - 1);
      }
      if (perk == Perk.CORRUPTED) {
        backstabbing.setValue(backstabbing.getValue() + 1);
      }
      if (perk == Perk.CONVICT) {
        backstabbing.setValue(backstabbing.getValue() + 1);
      }
      if (perk == Perk.CRUEL) {
        backstabbing.setValue(backstabbing.getValue() + 5);
        aggressive.setValue(aggressive.getValue() + 1);
      }
      if (perk == Perk.COUNTER_AGENT || perk == Perk.DISCIPLINE) {
        militaristic.setValue(militaristic.getValue() + 1);
      }
      if (perk == Perk.EXPLORER) {
        expansionist.setValue(expansionist.getValue() + 5);
        scientific.setValue(scientific.getValue() + 1);
      }
      if (perk == Perk.FTL_ENGINEER || perk == Perk.SCANNER_EXPERT) {
        expansionist.setValue(expansionist.getValue() + 3);
        scientific.setValue(scientific.getValue() + 1);
      }
      if (perk == Perk.MASTER_ENGINEER) {
        expansionist.setValue(expansionist.getValue() + 1);
        scientific.setValue(scientific.getValue() + 2);
      }
      if (perk == Perk.GOOD_LEADER) {
        logical.setValue(logical.getValue() + 3);
        peaceful.setValue(peaceful.getValue() + 1);
        diplomatic.setValue(peaceful.getValue() + 1);
      }
      if (perk == Perk.INDUSTRIAL) {
        logical.setValue(logical.getValue() + 1);
        merchantical.setValue(merchantical.getValue() + 1);
      }
      if (perk == Perk.AGRICULTURAL || perk == Perk.MINER) {
        merchantical.setValue(merchantical.getValue() + 1);
      }
      if (perk == Perk.MERCHANT || perk == Perk.TRADER) {
        merchantical.setValue(merchantical.getValue() + 5);
      }
      if (perk == Perk.MILITARISTIC) {
        militaristic.setValue(militaristic.getValue() + 5);
        peaceful.setValue(peaceful.getValue() - 5);
      }
      if (perk == Perk.PACIFIST) {
        peaceful.setValue(peaceful.getValue() + 5);
        diplomatic.setValue(diplomatic.getValue() + 1);
        militaristic.setValue(militaristic.getValue() - 5);
        aggressive.setValue(aggressive.getValue() - 5);
      }
      if (perk == Perk.POWER_HUNGRY) {
        aggressive.setValue(aggressive.getValue() + 3);
        backstabbing.setValue(backstabbing.getValue() + 1);
      }
      if (perk == Perk.REPULSIVE) {
        backstabbing.setValue(backstabbing.getValue() + 5);
        diplomatic.setValue(diplomatic.getValue() - 5);
      }
      if (perk == Perk.ADDICTED) {
        backstabbing.setValue(backstabbing.getValue() + 3);
      }
      if (perk == Perk.WEAK_LEADER) {
        aggressive.setValue(aggressive.getValue() - 5);
        militaristic.setValue(militaristic.getValue() - 5);
        peaceful.setValue(peaceful.getValue() + 5);
      }
      if (perk == Perk.SECRET_AGENT || perk == Perk.SPY_MASTER) {
        logical.setValue(logical.getValue() + 3);
      }
      if (perk == Perk.SLOW_LEARNER || perk == Perk.STUPID) {
        scientific.setValue(scientific.getValue() - 1);
      }
      if (perk == Perk.WARLORD) {
        backstabbing.setValue(backstabbing.getValue() + 1);
        aggressive.setValue(aggressive.getValue() + 5);
      }
      if (perk == Perk.WEALTHY) {
        merchantical.setValue(merchantical.getValue() + 1);
        diplomatic.setValue(diplomatic.getValue() + 1);
      }
      if (perk == Perk.PEACEFUL) {
        peaceful.setValue(peaceful.getValue() + 5);
        aggressive.setValue(aggressive.getValue() - 5);
        militaristic.setValue(militaristic.getValue() - 5);
      }
      if (perk == Perk.LOGICAL) {
        logical.setValue(logical.getValue() + 5);
      }
      if (perk == Perk.AGGRESSIVE) {
        aggressive.setValue(aggressive.getValue() + 5);
      }
      if (perk == Perk.MAD) {
        backstabbing.setValue(backstabbing.getValue() + 5);
        peaceful.setValue(peaceful.getValue() - 5);
        diplomatic.setValue(diplomatic.getValue() - 5);
      }
      if (perk == Perk.SKILLFUL) {
        logical.setValue(logical.getValue() + 1);
      }
      if (perk == Perk.INCOMPETENT) {
        backstabbing.setValue(backstabbing.getValue() + 1);
        logical.setValue(logical.getValue() - 1);
      }
    }
    ArrayList<AttitudeScore> scores = new ArrayList<>();
    scores.add(scientific);
    scores.add(peaceful);
    scores.add(merchantical);
    scores.add(expansionist);
    scores.add(backstabbing);
    scores.add(aggressive);
    scores.add(militaristic);
    scores.add(logical);
    scores.add(diplomatic);
    Collections.sort(scores, Collections.reverseOrder());
    if (scores.get(0).getValue() > 0) {
      return scores.get(0).getAttitude();
    }
    return null;
  }

  /**
   * Get ruler attitude
   * @param leader Leader for getting the attitude
   * @param secondary Backup for secondary realm attitude
   * @return Attitude
   */
  public static Attitude getRulerAttitude(final Leader leader,
      final Attitude secondary) {
    Attitude attitude = getRulerAttitude(leader);
    if (attitude != null) {
      return attitude;
    }
    return secondary;
  }

  /**
   * Handle leader release from espionage missage.
   * Leader was caught but realm decided to release leader.
   * @param info Realm who was trying espionage
   * @param planet Planet where espionage was tried
   * @param fleet Fleet which leader is commanding
   * @param message Message for two realms.
   * @param starMap StarMap
   */
  public static void handleLeaderReleased(final PlayerInfo info,
      final Planet planet, final Fleet fleet, final String message,
      final StarMap starMap) {
    Message msg = new Message(MessageType.LEADER, message,
        Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
    msg.setCoordinate(planet.getCoordinate());
    msg.setMatchByString(fleet.getCommander().getName());
    info.getMsgList().addUpcomingMessage(msg);
    msg = msg.copy();
    msg.setMatchByString(planet.getName());
    planet.getPlanetPlayerInfo().getMsgList().addUpcomingMessage(msg);
    starMap.getHistory().addEvent(NewsFactory.makeLeaderEvent(
        fleet.getCommander(), info, starMap, msg.getMessage()));
  }

  /**
   * Handle leader killed because of espionage mission.
   * Leader may escaped due wealthy perk.
   * @param info Realm who was trying espionage
   * @param planet Planet where espionage was tried
   * @param fleet Fleet which leader is commanding
   * @param escapedMsg Message visible if leader escapes
   * @param killedMsg Message visible if leader is killed
   * @param game Games for adding news about killed leader.
   */
  public static void handleLeaderKilled(final PlayerInfo info,
      final Planet planet, final Fleet fleet, final String escapedMsg,
      final String killedMsg, final Game game) {
    if (fleet.getCommander().hasPerk(Perk.WEALTHY)) {
      fleet.getCommander().useWealth();
      Message msg = new Message(MessageType.LEADER, escapedMsg,
          Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(fleet.getCommander().getName());
      info.getMsgList().addUpcomingMessage(msg);
      msg.setMatchByString(planet.getName());
      planet.getPlanetPlayerInfo().getMsgList().addUpcomingMessage(msg);
      game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
          fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
    } else {
      Message msg = new Message(MessageType.LEADER, killedMsg,
          Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(fleet.getCommander().getName());
      info.getMsgList().addUpcomingMessage(msg);
      msg = msg.copy();
      msg.setMatchByString(planet.getName());
      planet.getPlanetPlayerInfo().getMsgList().addUpcomingMessage(msg);
      fleet.getCommander().setJob(Job.DEAD);
      NewsData news = NewsFactory.makeLeaderDies(fleet.getCommander(),
          info, "execution by "
          + planet.getPlanetPlayerInfo().getEmpireName());
      if (game.getStarMap().hasHumanMet(info)
          || game.getStarMap().hasHumanMet(planet.getPlanetPlayerInfo())) {
        game.getStarMap().getNewsCorpData().addNews(news);
      }
      game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
          fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
      fleet.setCommander(null);
    }
  }

  /**
   * Handle leader prisoned because of espionage mission.
   * Leader may escaped due wealthy perk.
   * @param info Realm who was trying espionage
   * @param planet Planet where espionage was tried
   * @param fleet Fleet which leader is commanding
   * @param escapedMsg Message visible if leader escapes
   * @param prisonMsg Message visible if leader is prisoned
   * @param shortReason Short reason for prisoning
   * @param time sentence time in star years.
   * @param game Games for adding news about killed leader.
   */
  public static void handleLeaderPrison(final PlayerInfo info,
      final Planet planet, final Fleet fleet, final String escapedMsg,
      final String prisonMsg, final String shortReason, final int time,
      final Game game) {
    if (fleet.getCommander().hasPerk(Perk.WEALTHY)) {
      fleet.getCommander().useWealth();
      Message msg = new Message(MessageType.LEADER, escapedMsg,
          Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(fleet.getCommander().getName());
      info.getMsgList().addUpcomingMessage(msg);
      planet.getPlanetPlayerInfo().getMsgList().addUpcomingMessage(msg);
      game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
          fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
    } else {
      Message msg = new Message(MessageType.LEADER, prisonMsg,
          Icons.getIconByName(Icons.ICON_PRISON));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(fleet.getCommander().getName());
      info.getMsgList().addUpcomingMessage(msg);
      msg = msg.copy();
      msg.setMatchByString(planet.getName());
      planet.getPlanetPlayerInfo().getMsgList().addUpcomingMessage(msg);
      NewsData news = NewsFactory.makeLeaderPrisoned(fleet.getCommander(),
          info, shortReason, prisonMsg, time);
      if (game.getStarMap().hasHumanMet(info)) {
        game.getStarMap().getNewsCorpData().addNews(news);
      }
      game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
          fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
      fleet.getCommander().setJob(Job.PRISON);
      fleet.getCommander().setTimeInJob(time);
      fleet.getCommander().addPerk(Perk.CONVICT);
      fleet.getCommander().getStats().addOne(StatType.NUMBER_OF_JAIL_TIME);
      fleet.setCommander(null);
    }
  }

  /**
   * Is leader with power hungry perk ready to kill ruler?
   * @param government Government type
   * @return True if ready to kill
   */
  public static boolean isPowerHungryReadyForKill(
      final GovernmentType government) {
    if (government == GovernmentType.CLAN
        || government == GovernmentType.EMPIRE
        || government == GovernmentType.HEGEMONY
        || government == GovernmentType.HIERARCHY
        || government == GovernmentType.HIVEMIND
        || government == GovernmentType.HORDE
        || government == GovernmentType.KINGDOM
        || government == GovernmentType.MECHANICAL_HORDE
        || government == GovernmentType.NEST
        || government == GovernmentType.FEUDALISM
        || government == GovernmentType.REGIME) {
      return true;
    }
    return false;
  }

  /**
   * Get Main job description for leader.
   * @param leader Leader for getting the main job.
   * @param government Government type for getting proper title for ruler.
   * @return Job title
   */
  private static String getMainJob(final Leader leader,
      final GovernmentType government) {
    int ruler = leader.getStats().getStat(StatType.RULER_REIGN_LENGTH);
    int governor = leader.getStats().getStat(StatType.GOVERNOR_LENGTH);
    int commander = leader.getStats().getStat(StatType.COMMANDER_LENGTH);
    int avg = (ruler + governor + commander) / 3;
    if (ruler > avg) {
      return getRulerTitle(leader.getGender(), government);
    }
    if (avg == 0) {
      return "jobless";
    }
    if (governor > avg) {
      return "Governor";
    }
    if (commander > avg) {
      return "Commander";
    }
    return "between jobs";
  }

  /**
   * Get textual description where leader is known about.
   * @param leader Leader.
   * @return String
   */
  private static String getBestKnown(final Leader leader) {
    StringBuilder sb = new StringBuilder();
    boolean and = false;
    boolean noMore = false;
    int numberOfBattle = leader.getStats().getStat(StatType.NUMBER_OF_BATTLES);
    int numberOfAnomalies = leader.getStats().getStat(
        StatType.NUMBER_OF_ANOMALY);
    int trades = leader.getStats().getStat(
        StatType.NUMBER_OF_TRADES);
    int privateering = leader.getStats().getStat(
        StatType.NUMBER_OF_PRIVATEERING);
    int numberOfPlanets = leader.getStats().getStat(
        StatType.NUMBER_OF_PLANETS_EXPLORED);
    int commanderAvg = (numberOfAnomalies + numberOfBattle + trades
        + privateering + numberOfPlanets) / 5;
    int numberOfBuildings = leader.getStats().getStat(
        StatType.NUMBER_OF_BUILDINGS_BUILT);
    int numberOfShips = leader.getStats().getStat(
        StatType.NUMBER_OF_SHIPS_BUILT);
    int populationGrowth = leader.getStats().getStat(
        StatType.POPULATION_GROWTH);
    int governorAvg = (numberOfBuildings + numberOfShips
        + populationGrowth) / 3;
    int warDeclarations = leader.getStats().getStat(StatType.WAR_DECLARATIONS);
    int diplomaticTrades = leader.getStats().getStat(StatType.DIPLOMATIC_TRADE);
    int rulerAvg = (warDeclarations + diplomaticTrades) / 2;
    if (rulerAvg >= commanderAvg && rulerAvg > 0) {
      if (warDeclarations > diplomaticTrades) {
        sb.append("war declarations");
        and = true;
      } else {
        sb.append("diplomatic trades");
        and = true;
      }
    }
    if (governorAvg >= commanderAvg) {
      if (numberOfShips > numberOfBuildings
          && numberOfShips > populationGrowth) {
        if (and) {
          noMore = true;
          sb.append(" and ");
        }
        sb.append("ship building");
        and = true;
      }
      if (!noMore && numberOfBuildings > numberOfShips
          && numberOfBuildings > populationGrowth) {
        if (and) {
          noMore = true;
          sb.append(" and ");
        }
        sb.append("building projects");
        and = true;
      }
      if (!noMore && populationGrowth > numberOfShips
          && populationGrowth > numberOfBuildings) {
        if (and) {
          noMore = true;
          sb.append(" and ");
        }
        sb.append("population growth");
        and = true;
      }
    }
    if (commanderAvg >= governorAvg) {
      if (!noMore && numberOfBattle > numberOfAnomalies
          && numberOfBattle > privateering
          && numberOfBattle > trades
          && numberOfBattle > numberOfPlanets) {
        if (and) {
          noMore = true;
          sb.append(" and ");
        }
        sb.append("space battles");
        if (leader.getStats().getStat(StatType.NUMBER_OF_PIRATE_BATTLES)
            >= numberOfBattle / 2) {
          sb.append(" against space pirates");
        }
        and = true;
      }
      if (!noMore && numberOfAnomalies > numberOfBattle
          && numberOfAnomalies > privateering
          && numberOfAnomalies > trades
          && numberOfAnomalies > numberOfPlanets) {
        if (and) {
          noMore = true;
          sb.append(" and ");
        }
        sb.append("exploring space anomalies");
        and = true;
      }
      if (!noMore && trades > numberOfBattle
          && trades > privateering
          && trades > numberOfAnomalies
          && trades > numberOfPlanets) {
        if (and) {
          noMore = true;
          sb.append(" and ");
        }
        sb.append("trades");
        and = true;
      }
      if (!noMore && numberOfPlanets > numberOfBattle
          && numberOfPlanets > trades
          && numberOfPlanets > numberOfAnomalies
          && numberOfPlanets > privateering) {
        if (and) {
          noMore = true;
          sb.append(" and ");
        }
        sb.append("boldly exploring planets");
        and = true;
      }
      if (!noMore && privateering > numberOfBattle
          && privateering > trades
          && privateering > numberOfAnomalies
          && privateering > numberOfPlanets) {
        if (and) {
          noMore = true;
          sb.append(" and ");
        }
        sb.append("space pirating");
        and = true;
      }
    }
    return sb.toString();
  }
  /**
   * Create Bio for leader
   * @param leader Leader whom to create bio.
   * @param info PlayerInfo
   * @return Bio as a String.
   */
  public static String createBioForLeader(final Leader leader,
      final PlayerInfo info) {
    boolean living = true;
    boolean young = false;
    if (leader.getAge() < 35) {
      young = true;
    }
    if (leader.getJob() == Job.DEAD) {
      living = false;
      young = false;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(leader.getName());
    if (leader.getJob() == Job.TOO_YOUNG) {
      sb.append(" is still growing up and will achieve many things later.");
      return sb.toString();
    }
    if (living) {
      sb.append(" is ");
    } else {
      sb.append(" was ");
    }
    int ruler = leader.getStats().getStat(StatType.RULER_REIGN_LENGTH);
    int governor = leader.getStats().getStat(StatType.GOVERNOR_LENGTH);
    int commander = leader.getStats().getStat(StatType.COMMANDER_LENGTH);
    if (ruler > 0 && governor == 0 && commander == 0) {
      sb.append("the ");
    } else if (ruler == 0 && governor > 0 && commander == 0
        || ruler == 0 && governor == 0 && commander > 0) {
      sb.append("working as ");
    } else if (ruler != 0 || governor != 0 || commander != 0) {
      sb.append("mostly working as ");
    }
    sb.append(getMainJob(leader, info.getGovernment()));
    if (living) {
      sb.append(". Currently ");
      sb.append(leader.getName());
      sb.append(" is ");
      if (leader.getJob() == Job.RULER) {
        sb.append(getRulerTitle(leader.getGender(), info.getGovernment()));
      } else if (leader.getJob() == Job.COMMANDER) {
        sb.append(leader.getMilitaryRank().toString());
      } else {
        sb.append(leader.getJob().toString().toLowerCase());
      }
      sb.append(". ");
    } else {
      sb.append(". ");
    }
    sb.append(leader.getTitle());
    String known = getBestKnown(leader);
    if (known.isEmpty()) {
      if (young && living) {
        sb.append(" is still young and is able to achieve many things. ");
      }
      if (!young && living) {
        sb.append(" is still live and ");
        if (leader.getRace().isRoboticRace()) {
          sb.append("functional and");
        } else if (leader.hasPerk(Perk.HEALTHY)) {
          sb.append("has healthy lifestyle and");
        } else if (!leader.hasPerk(Perk.ADDICTED)) {
          sb.append("healthy and");
        }
        sb.append(" is able to achieve things. ");
      }
      if (!living) {
        sb.append(" has passed away with respect. ");
      }
    } else {
      if (living) {
        sb.append(" is known for ");
      } else {
        sb.append(" will be remembered for ");
      }
      sb.append(known);
      sb.append(". ");
    }
    if (leader.getStats().getStat(StatType.NUMBER_OF_ESPIONAGE) > 0) {
      sb.append(leader.getName());
      if (living) {
        sb.append(" is suspected to be spy. ");
      } else {
        sb.append(" was suspected to be spy. ");
      }
    }
    if (leader.getStats().getStat(StatType.NUMBER_OF_JAIL_TIME) > 0) {
      sb.append(leader.getName());
      if (living) {
        sb.append(" has been in jail. ");
      } else {
        sb.append(" has been sentenced to jail ");
        sb.append(leader.getStats().getStat(StatType.NUMBER_OF_JAIL_TIME));
        sb.append(" times. ");
      }
    }
    Attitude attitude = getRulerAttitude(leader);
    if (attitude != null) {
      sb.append(leader.getCallName());
      if (living) {
        sb.append(" is known to be ");
      } else {
        sb.append(" was known to be ");
      }
      switch (attitude) {
      case AGGRESSIVE: {
        sb.append("aggressive");
        break;
      }
      case BACKSTABBING: {
        sb.append("untrustworthy");
        break;
      }
      case DIPLOMATIC: {
        sb.append("diplomatic");
        break;
      }
      case EXPANSIONIST: {
        sb.append("adventurous");
        break;
      }
      case LOGICAL: {
        sb.append("very logical");
        break;
      }
      case MERCHANTICAL: {
        sb.append("merchantical");
        break;
      }
      case MILITARISTIC: {
        sb.append("militaristic");
        break;
      }
      case PEACEFUL: {
        sb.append("calm and peaceful");
        break;
      }
      default:
      case SCIENTIFIC: {
        sb.append("scientific");
        break;
      }
      }
      sb.append(". ");
    }
    if (leader.getStats().getStat(StatType.RESEARCH_ARTIFACTS) > 0) {
      sb.append(leader.getName());
      boolean famous = false;
      if (leader.getStats().getStat(StatType.RESEARCH_ARTIFACTS) > 2) {
        famous = true;
      }
      if (living) {
        sb.append(" is ");
      } else {
        sb.append(" was ");
      }
      if (famous) {
        sb.append("famous ancient artifact researcher. ");
      } else {
        sb.append("interested in ancient artifact research. ");
      }
    }
    return sb.toString();
  }

  /**
   * Get Reason for gaining perk for leader.
   * @param leader Leader who is gain perk
   * @param perk Perk which was gained.
   * @return Explanation string
   */
  public static String getReasonForPerk(final Leader leader, final Perk perk) {
    StringBuilder sb = new StringBuilder();
    switch (perk) {
      default: {
        sb.append("Unkown perk gained...");
        break;
      }
      case ACADEMIC: {
        sb.append(leader.getName());
        sb.append(" has higher level of studies and learn how to be academic.");
        break;
      }
      case ADDICTED: {
        sb.append(leader.getName());
        sb.append(" has hooked on substance and ");
        sb.append(leader.getGender().getHeShe());
        sb.append(" has to use it.");
        break;
      }
      case AGGRESSIVE: {
        sb.append(leader.getName());
        sb.append(" has become aggressive due ");
        sb.append(leader.getGender().getHisHer());
        sb.append(" stress levels.");
        break;
      }
      case AGRICULTURAL: {
        sb.append(leader.getName());
        sb.append(" has started to grown ");
        sb.append(leader.getGender().getHisHer());
        sb.append(" own farm and thus become more interested in agriculture.");
        break;
      }
      case ARCHAEOLOGIST: {
        sb.append(leader.getName());
        sb.append(" has researched ancient artifacts and ");
        sb.append(leader.getGender().getHeShe());
        sb.append(" has done couple of new findings.");
        break;
      }
      case ARTISTIC: {
        sb.append(leader.getName());
        sb.append(" has started to ");
        switch (DiceGenerator.getRandom(3)) {
          default:
          case 0: {
            sb.append("paint ");
            break;
          }
          case 1: {
            sb.append("sculture ");
            break;
          }
          case 2: {
            sb.append("composing music ");
            break;
          }
          case 3: {
            sb.append("writing novels ");
            break;
          }
        }
        sb.append("and ");
        sb.append(leader.getGender().getHisHer());
        sb.append(" interest towards art has been increased.");
        break;
      }
      case CHARISMATIC: {
        sb.append(leader.getName());
        sb.append(" has learn how to behave charismatically.");
        break;
      }
      case CHATTERBOX: {
        sb.append(leader.getName());
        sb.append(" feels compulsary need to brag ");
        sb.append(leader.getGender().getHisHer());
        sb.append(" achievements and always speaks a too much.");
        break;
      }
      case COMBAT_MASTER: {
        sb.append(leader.getName());
        sb.append(" has improved how to shoot accurately. ");
        break;
      }
      case COMBAT_TACTICIAN: {
        sb.append(leader.getName());
        sb.append(" has learned how to react with ");
        sb.append(leader.getGender().getHisHer());
        sb.append(" fleet in space combat faster than before.");
        break;
      }
      case CONVICT: {
        sb.append(leader.getName());
        sb.append(" has been in prison. ");
        break;
      }
      case CORRUPTED: {
        sb.append(leader.getName());
        sb.append(" takes realm's credits into ");
        sb.append(leader.getGender().getHisHer());
        sb.append(" pockets and ");
        sb.append(leader.getGender().getHeShe());
        sb.append(" thinks that is the right thing to do.");
        break;
      }
      case COUNTER_AGENT: {
        sb.append(leader.getName());
        sb.append(" has learn how to spot cloaked fleets by noticing little"
            + " things in scanner views.");
        break;
      }
      case CRUEL: {
        sb.append(leader.getName());
        sb.append(" has killed too young heir. What cruel thing to do!");
        break;
      }
      case DIPLOMATIC: {
        sb.append(leader.getName());
        sb.append(" has developped skill to be a diplomatic.");
        break;
      }
      case DISCIPLINE: {
        sb.append(leader.getName());
        sb.append(" has learned to be strict commander during wartime.");
        break;
      }
      case EXPLORER: {
        sb.append(leader.getName());
        sb.append(" is very keen to explore and"
            + " pushes the engines a bit over the limit.");
        break;
      }
      case FTL_ENGINEER: {
        sb.append(leader.getName());
        sb.append(" wants to get moving fast and"
            + " pushes the engines a bit over the limit.");
        break;
      }
      case GOOD_LEADER: {
        sb.append(leader.getName());
        sb.append(" has learned new people skill:"
            + " how to treat every one equally and fairly.");
        break;
      }
      case HEALTHY: {
        sb.append(leader.getName());
        sb.append(" has started to live more healthier life than before.");
        break;
      }
      case INCOMPETENT: {
        sb.append(leader.getName());
        sb.append(" has fallen down and hit ");
        sb.append(leader.getGender().getHisHer());
        sb.append(" head very badly. Things aren't the same anymore...");
        break;
      }
      case INDUSTRIAL: {
        sb.append(leader.getName());
        sb.append(" has shown interest in factory automation ");
        sb.append(" and seems like ");
        sb.append(leader.getGender().getHeShe());
        sb.append(" can get the factories running much more efficiently.");
        break;
      }
      case LOGICAL: {
        sb.append(leader.getName());
        sb.append(" has starting to behave logically. This is ");
        sb.append(leader.getGender().getHisHer());
        sb.append(" new way to get things done.");
        break;
      }
      case MAD: {
        sb.append(leader.getName());
        sb.append(" had very bad disease and ");
        sb.append(leader.getGender().getHeShe());
        sb.append(" became mad afterwards.");
        break;
      }
      case MASTER_ENGINEER: {
        sb.append(leader.getName());
        sb.append(" has deeper knowledge how to overload ship's"
            + " components with less danger.");
        break;
      }
      case MERCHANT: {
        sb.append(leader.getName());
        sb.append(" has interest in trading and thus makes better deals.");
        break;
      }
      case MICRO_MANAGER: {
        sb.append(leader.getCallName());
        sb.append(" has habit that everything needs to be done by ");
        sb.append(leader.getGender().getHisHer());
        sb.append(" way and this kind of management does not end well.");
        break;
      }
      case MILITARISTIC: {
        sb.append(leader.getName());
        sb.append(" has been interesting in military tactics and can handle"
            + " larger number of fleets.");
        break;
      }
      case MINER: {
        sb.append(leader.getName());
        sb.append(" has been interested in minerals and stone and therefore ");
        sb.append(leader.getGender().getHeShe());
        sb.append(" is good spotting great mining areas.");
        break;
      }
      case PACIFIST: {
        sb.append(leader.getCallName());
        sb.append(" thinks that war is never a solution and there must be"
            + " a peaceful way to solve things.");
        break;
      }
      case PEACEFUL: {
        sb.append(leader.getCallName());
        sb.append(" has learned how to calm ");
        sb.append(leader.getGender().getHisHer());
        sb.append(" nerves. This gives a cool impression.");
        break;
      }
      case POWER_HUNGRY: {
        sb.append(leader.getCallName());
        sb.append(" is graving to be a ruler and ");
        sb.append(leader.getGender().getHeShe());
        sb.append(" is willing to do lot for it, some of those can be"
            + " questionable.");
        break;
      }
      case REPULSIVE: {
        sb.append(leader.getName());
        sb.append(" has turned into selfish and arrogant that ");
        sb.append(" doesn't seem to care about others.");
        break;
      }
      case SCANNER_EXPERT: {
        sb.append(leader.getName());
        sb.append(" has learned how scanners work and ");
        sb.append(leader.getGender().getHeShe());
        sb.append(" knows how to manually tweak in order get them"
            + " perform better.");
        break;
      }
      case SCIENTIST: {
        sb.append(leader.getName());
        sb.append(" has published scientific paper during ");
        sb.append(leader.getGender().getHisHer());
        sb.append(" spare time. Impressive.");
        break;
      }
      case SECRET_AGENT: {
        sb.append(leader.getName());
        sb.append(" has learned how to tune cloaking ");
        sb.append(" device to perform slightly better.");
        break;
      }
      case SKILLFUL: {
        sb.append(leader.getName());
        sb.append(" has become skillfull, ");
        sb.append(leader.getGender().getHeShe());
        sb.append(" is able to pick any task and perform it well.");
        break;
      }
      case SLOW_LEARNER: {
        sb.append(leader.getName());
        sb.append(" is feeling that learning new things is a burden and ");
        sb.append(leader.getGender().getHeShe());
        sb.append(" hasn't any motivation to improve situation.");
        break;
      }
      case SPY_MASTER: {
        sb.append(leader.getName());
        sb.append(" has watched couple of old spy movies and ");
        sb.append(" learned new tricks for espionage.");
        break;
      }
      case STUPID: {
        sb.append(leader.getName());
        sb.append(" has got too many hits to ");
        sb.append(leader.getGender().getHisHer());
        sb.append(" head and become simply stupid.");
        break;
      }
      case TRADER: {
        sb.append(leader.getName());
        sb.append(" has got interested in trading ");
        sb.append(" and gains a bit of extra while doing it.");
        break;
      }
      case WARLORD: {
        sb.append(leader.getName());
        sb.append(" has learned motivate population during war ");
        sb.append(" so that they work more like a team.");
        break;
      }
      case WEAK_LEADER: {
        sb.append(leader.getName());
        sb.append(" has started to be affraid of war and this is bad ");
        sb.append(" since fear spreads specially during war times.");
        break;
      }
      case WEALTHY: {
        sb.append(leader.getName());
        sb.append(" has got some personal extra credits. ");
        break;
      }
    }
    return sb.toString();
  }
}
