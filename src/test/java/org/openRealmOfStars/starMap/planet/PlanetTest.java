package org.openRealmOfStars.starMap.planet;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2023 Tuomo Untinen
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.SpaceRaceUtility;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.Construction;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 * Test for planet
 *
 */
public class PlanetTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPlanetPopulationGrowthAndBuilding() {
    Coordinate planetCoordinate = new Coordinate(10, 15);
    Planet planet = new Planet(planetCoordinate, "Earth", 1, false);
    planet.setRadiationLevel(1);
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN);
    info.setEmpireName("Alliance of Humans");
    planet.setPlanetOwner(0, info);
    planet.setWorkers(Planet.FOOD_FARMERS, 1);
    Building factory = BuildingFactory.createByName("Basic factory");
    planet.setUnderConstruction(factory);
    assertEquals(35, planet.getRushingCost(factory));

    assertEquals("The population of the planet should be one.", 1, planet.getTotalPopulation());
    assertEquals("The production time should be 15 star years.",
        "15 star years", planet.getProductionTimeAsString(factory));
    for (int i=0;i<5;i++) {
      // 5 star years to grow one population
      planet.updateOneTurn(false, null);
    }
    assertEquals("The population of the planet should be two.", 2, planet.getTotalPopulation());
    for (int i=0;i<10;i++) {
      // Total of 15 star years for basic factory
      planet.updateOneTurn(false, null);
    }
    assertEquals("Planet should have only one building.", 1, planet.getBuildingList().length);
    assertEquals("Planet should have a Basic factory.", "Basic factory", planet.getBuildingList()[0].getName());
    assertEquals(false,planet.isFullOfPopulation());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPlanetShipBuilding() {
    Coordinate planetCoordinate = new Coordinate(10, 15);
    Planet planet = new Planet(planetCoordinate, "Earth", 1, false);
    planet.setRadiationLevel(1);
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN);
    info.setEmpireName("Alliance of Humans");
    planet.setPlanetOwner(0, info);
    Building spacePort = BuildingFactory.createByName("Space port");
    planet.addBuilding(spacePort);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
    Construction[] list = planet.getProductionList();
    Construction shipConst = list[list.length - 1];
    planet.setUnderConstruction(shipConst);
    assertEquals(0, info.getFleets().getNumberOfFleets());
    assertEquals("The population of the planet should be one.", 1,
        planet.getTotalPopulation());
    assertEquals("The production time should be 15 star years.",
        "15 star years", planet.getProductionTimeAsString(shipConst));
    for (int i=0;i<15;i++) {
      // 5 star years to grow one population
      planet.updateOneTurn(false, null);
    }
    list = planet.getProductionList();
    shipConst = list[list.length - 1];
    planet.setUnderConstruction(shipConst);
    assertEquals(1, info.getFleets().getNumberOfFleets());
    assertEquals("The production time should be 15 star years.",
        "15 star years", planet.getProductionTimeAsString(shipConst));
    for (int i=0;i<15;i++) {
      // 5 star years to grow one population
      planet.updateOneTurn(true, null);
    }
    assertEquals(1, info.getFleets().getNumberOfFleets());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPlanetTextGenerator() {
      Coordinate planetCoordinate = new Coordinate(10, 15);
      Planet planet = new Planet(planetCoordinate, "Earth", 1, false);
      planet.setGroundSize(10);
      planet.setAmountMetalInGround(6543);
      planet.setRadiationLevel(1);
      planet.setName("Earth");
      planet.setPlanetType(PlanetTypes.WATERWORLD1);

      String tmp = planet.generateInfoText(false, null);
      assertEquals("Earth\nWater world\nRadiation: 1\nSize: small\n",tmp);
      tmp = planet.generateInfoText(true, null);
      assertEquals("Earth\nWater world\nRadiation: 1\nSize: small\nMetal: 6543\n",tmp);
      planet.setHomeWorldIndex(1);
      tmp = planet.generateInfoText(false, null);
      assertEquals("Earth\nWater world\nRadiation: 1\nSize: small\nHome world of\nMechions\n",tmp);
      tmp = planet.generateInfoText(true, null);
      assertEquals("Earth\nWater world\nRadiation: 1\nSize: small\nMetal: 6543\nHome world of\nMechions\n",tmp);
      PlayerInfo info = new PlayerInfo(SpaceRaceUtility.getRaceByName("Mechion"));
      info.setEmpireName("Mechion Great Empire");
      planet.setPlanetOwner(0, info);
      planet.setWorkers(0, 1);
      planet.setCulture(5);
      tmp = planet.generateInfoText(true, null);
      assertEquals("Earth\nWater world\nSuitable: 75%\nRadiation: 1\nSize: small\nMetal: 6543\n"
          + "Home world of\nMechions\nMechion Great Empire\nPopulation"
          + ": 1\nCulture: 5\n",tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPlanetKillWorker() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.GUILD);
    Mockito.when(info.getWarFatigue()).thenReturn(0);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    planet.setPlanetOwner(0, info);
    planet.setWorkers(Planet.FOOD_FARMERS, 2);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 1);
    planet.setWorkers(Planet.METAL_MINERS, 1);
    planet.setWorkers(Planet.CULTURE_ARTIST, 1);
    assertEquals(5, planet.getTotalPopulation());
    assertEquals(0, planet.calculateHappiness());
    assertEquals("<html><li>Population -1<br><li>Artists +1<br></html>",
        planet.getHappinessExplanation());
    planet.killOneWorker();
    assertEquals(4, planet.getTotalPopulation());
    // Kill One worker kills one worker as random
    if (planet.getWorkers(Planet.CULTURE_ARTIST) == 1) {
      assertEquals(1, planet.calculateHappiness());
      assertEquals("<html><li>Artists +1<br></html>",
          planet.getHappinessExplanation());
    } else {
      assertEquals(0, planet.calculateHappiness());
      assertEquals("<html></html>",
          planet.getHappinessExplanation());
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBombBuilding() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    assertEquals(0, planet.calculateHappiness());
    planet.setRadiationLevel(6);
    assertEquals(0, planet.getNumberOfBuildings());
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getName()).thenReturn("Radiation well");
    Mockito.when(building.getProdCost()).thenReturn(10);
    planet.addBuilding(building);
    assertEquals(5, planet.getTotalRadiationLevel());
    assertEquals(1, planet.getNumberOfBuildings());
    planet.removeBuilding(building);
    assertEquals(0, planet.getNumberOfBuildings());
    assertEquals(6, planet.getTotalRadiationLevel());
    planet.setGroundSize(7);
    Mockito.when(building.getName()).thenReturn("Test building");
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    assertEquals(7, planet.getNumberOfBuildings());
    planet.setCulture(40);
    planet.bombOneBuilding();
    assertEquals(6, planet.getNumberOfBuildings());
    assertEquals(30, planet.getCulture());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testOrbitalNuke() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    planet.setGroundSize(8);
    planet.setRadiationLevel(1);
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getName()).thenReturn("Random building");
    Mockito.when(building.getProdCost()).thenReturn(10);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.setWorkers(Planet.FOOD_FARMERS, 8);
    DiceGenerator.initializeGenerators(0, 1, 10);
    PlanetNuked nuked = planet.nukem(20, "Orbital nuke", null, null);
    assertEquals("Orbital nuke killed 2 population and destroyed 2 buildings!"
        + " Radiation level rised on planet surface to 2.", nuked.getText());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testOrbitalNuke2() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    planet.setGroundSize(8);
    planet.setRadiationLevel(1);
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getName()).thenReturn("Random building");
    Mockito.when(building.getProdCost()).thenReturn(10);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.setWorkers(Planet.FOOD_FARMERS, 8);
    DiceGenerator.initializeGenerators(0, 1, 10, 0, 1, 10);
    PlanetNuked nuked = planet.nukem(20, "Orbital nuke", null, null);
    assertEquals("Orbital nuke killed 2 population and destroyed 2 buildings!"
        + " Radiation level rised on planet surface to 2.", nuked.getText());
    nuked = planet.nukem(20, "Orbital nuke", null, nuked);
    assertEquals("Orbital nuke killed 4 population and destroyed 4 buildings!"
        + " Radiation level rised on planet surface to 3.", nuked.getText());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testOrbitalAntimaterBomb() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    planet.setGroundSize(8);
    planet.setRadiationLevel(1);
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getName()).thenReturn("Random building");
    Mockito.when(building.getProdCost()).thenReturn(10);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.setWorkers(Planet.FOOD_FARMERS, 8);
    DiceGenerator.initializeGenerators(0, 0, 0, 0, 0, 10);
    PlanetNuked nuked = planet.nukem(80, "Orbital antimatter bomb", null, null);
    assertEquals("Orbital antimatter bomb killed 8 population and destroyed 5 buildings!"
        + " Radiation level rised on planet surface to 2.", nuked.getText());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testOrbitalNeutronBomb() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    planet.setGroundSize(8);
    planet.setRadiationLevel(1);
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getName()).thenReturn("Random building");
    Mockito.when(building.getProdCost()).thenReturn(10);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.setWorkers(Planet.FOOD_FARMERS, 8);
    DiceGenerator.initializeGenerators(0, 0, 0, 0, 0, 10);
    PlanetNuked nuked = planet.nukem(100, "Orbital neutron bomb", null, null);
    assertEquals("Orbital neutron bomb killed 8 population and destroyed 4 buildings!"
        + " Radiation level rised on planet surface to 2.", nuked.getText());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDestroyBuilding() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    assertEquals(0, planet.calculateHappiness());
    assertEquals("<html>Planet is not colonized!</html>", planet.getHappinessExplanation());
    planet.setRadiationLevel(6);
    assertEquals(0, planet.getNumberOfBuildings());
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getName()).thenReturn("Radiation well");
    Mockito.when(building.getProdCost()).thenReturn(10);
    Mockito.when(building.getMaintenanceCost()).thenReturn(1.0);
    planet.addBuilding(building);
    assertEquals(5, planet.getTotalRadiationLevel());
    assertEquals(1, planet.getNumberOfBuildings());
    planet.removeBuilding(building);
    assertEquals(0, planet.getNumberOfBuildings());
    assertEquals(6, planet.getTotalRadiationLevel());
    planet.setGroundSize(7);
    Mockito.when(building.getName()).thenReturn("Test building");
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    assertEquals(7, planet.getNumberOfBuildings());
    planet.setCulture(40);
    planet.destroyOneBuilding();
    assertEquals(6, planet.getNumberOfBuildings());
    assertEquals(30, planet.getCulture());
    for (Building build : planet.getBuildingList()) {
      assertEquals("Test building", build.getName());
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPlanetValue() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    planet.setGroundSize(10);
    assertEquals(0, planet.getFullLevel());
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getName()).thenReturn("Radiation well");
    Mockito.when(building.getProdCost()).thenReturn(10);
    Mockito.when(building.getMaintenanceCost()).thenReturn(1.0);
    planet.addBuilding(building);
    planet.setWorkers(Planet.FOOD_FARMERS, 2);
    assertEquals(15, planet.getFullLevel());
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.setWorkers(Planet.METAL_MINERS, 1);
    assertEquals(30, planet.getFullLevel());
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 5);
    assertEquals(75, planet.getFullLevel());
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.addBuilding(building);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
    assertEquals(100, planet.getFullLevel());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTroopPower() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    planet.setWorkers(Planet.FOOD_FARMERS, 2);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.GREYANS);
    planet.setPlanetOwner(0, info);
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getBattleBonus()).thenReturn(15);
    planet.addBuilding(building);
    assertEquals(27, planet.getTroopPower());
    assertEquals(9, planet.getTroopPowerBonus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHappinessEffect() {
    Planet planet = new Planet(new Coordinate(5,5), "Happy I", 1, false);
    assertEquals(HappinessBonus.NONE, planet.getHappinessEffect().getType());
    HappinessEffect effect = Mockito.mock(HappinessEffect.class);
    planet.setHappinessEffect(effect);
    assertEquals(effect, planet.getHappinessEffect());
  }


  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreditProduction() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    planet.setPlanetOwner(0, info);
    assertEquals(0, planet.getNumberOfBuildings());
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getName()).thenReturn("Tax center");
    Mockito.when(building.getProdCost()).thenReturn(10);
    Mockito.when(building.getCredBonus()).thenReturn(1);
    planet.addBuilding(building);
    Building building2 = Mockito.mock(Building.class);
    Mockito.when(building2.getName()).thenReturn("Radiation well");
    Mockito.when(building2.getProdCost()).thenReturn(10);
    Mockito.when(building2.getCredBonus()).thenReturn(0);
    planet.addBuilding(building2);
    assertEquals(2, planet.getNumberOfBuildings());
    planet.setRadiationLevel(5);
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    Mockito.when(info.getRace()).thenReturn(SpaceRace.SCAURIANS);
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFoodProduction() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    planet.setPlanetOwner(0, info);
    assertEquals(0, planet.getNumberOfBuildings());
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getName()).thenReturn("Basic farm");
    Mockito.when(building.getProdCost()).thenReturn(10);
    Mockito.when(building.getFarmBonus()).thenReturn(1);
    planet.addBuilding(building);
    assertEquals(3, planet.getFoodProdByPlanetAndBuildings());
    planet.setPlanetaryEvent(PlanetaryEvent.LUSH_VEGETATION);
    assertEquals(4, planet.getFoodProdByPlanetAndBuildings());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPlanetEvent() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    assertEquals(PlanetaryEvent.NONE, planet.getPlanetaryEvent());
    assertEquals(true, planet.isEventActivated());
    planet.setEventActivation(false);
    planet.setPlanetaryEvent(PlanetaryEvent.METAL_RICH_SURFACE);
    assertEquals(PlanetaryEvent.METAL_RICH_SURFACE, planet.getPlanetaryEvent());
    assertEquals(false, planet.isEventActivated());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testClimateChange() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    assertEquals(PlanetaryEvent.NONE, planet.getPlanetaryEvent());
    assertEquals(true, planet.isEventActivated());
    planet.changeClimate(true);
    assertEquals(PlanetaryEvent.LUSH_VEGETATION, planet.getPlanetaryEvent());
    planet.changeClimate(true);
    assertEquals(PlanetaryEvent.PARADISE, planet.getPlanetaryEvent());
    planet.changeClimate(true);
    assertEquals(PlanetaryEvent.PARADISE, planet.getPlanetaryEvent());
    planet.changeClimate(false);
    assertEquals(PlanetaryEvent.LUSH_VEGETATION, planet.getPlanetaryEvent());
    planet.changeClimate(false);
    assertEquals(PlanetaryEvent.NONE, planet.getPlanetaryEvent());
    planet.changeClimate(false);
    assertEquals(PlanetaryEvent.ARID, planet.getPlanetaryEvent());
    planet.changeClimate(false);
    assertEquals(PlanetaryEvent.DESERT, planet.getPlanetaryEvent());
    planet.changeClimate(false);
    assertEquals(PlanetaryEvent.DESERT, planet.getPlanetaryEvent());
    planet.changeClimate(true);
    assertEquals(PlanetaryEvent.ARID, planet.getPlanetaryEvent());
    planet.changeClimate(true);
    assertEquals(PlanetaryEvent.NONE, planet.getPlanetaryEvent());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRoguePlanet() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test", 0, false);
    assertEquals("Test", planet.getName());
    assertEquals(0, planet.getOrderNumber());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testArtificialPlanet() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test", 0, false);
    assertNotEquals(0, planet.getAmountMetalInGround());
    planet.setPlanetType(PlanetTypes.ARTIFICIALWORLD1);
    assertEquals(0, planet.getAmountMetalInGround());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPlanetEventActivationMetalRichSurface() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
    planet.setPlanetOwner(0, info);
    planet.setEventActivation(false);
    planet.setPlanetaryEvent(PlanetaryEvent.METAL_RICH_SURFACE);
    assertEquals(PlanetaryEvent.METAL_RICH_SURFACE, planet.getPlanetaryEvent());
    assertEquals(false, planet.isEventActivated());
    planet.eventActivation(false, null, null);
    assertEquals(true, planet.isEventActivated());
    info.getMsgList().clearMessages();
    assertEquals(Icons.getIconByName(Icons.ICON_METAL_ORE),
        info.getMsgList().getMsg().getIcon());
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_METAL));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPlanetEventActivationPreciousGems() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
    planet.setPlanetOwner(0, info);
    planet.setEventActivation(false);
    planet.setPlanetaryEvent(PlanetaryEvent.PRECIOUS_GEMS);
    assertEquals(PlanetaryEvent.PRECIOUS_GEMS, planet.getPlanetaryEvent());
    assertEquals(false, planet.isEventActivated());
    planet.eventActivation(false, null, null);
    assertEquals(true, planet.isEventActivated());
    info.getMsgList().clearMessages();
    assertEquals(Icons.getIconByName(Icons.ICON_CREDIT),
        info.getMsgList().getMsg().getIcon());
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPlanetEventActivationLushVegetation() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
    planet.setPlanetOwner(0, info);
    planet.setEventActivation(false);
    planet.setPlanetaryEvent(PlanetaryEvent.LUSH_VEGETATION);
    assertEquals(PlanetaryEvent.LUSH_VEGETATION, planet.getPlanetaryEvent());
    assertEquals(false, planet.isEventActivated());
    planet.eventActivation(false, null, null);
    assertEquals(true, planet.isEventActivated());
    info.getMsgList().clearMessages();
    assertEquals(Icons.getIconByName(Icons.ICON_FARM),
        info.getMsgList().getMsg().getIcon());
    assertEquals(3, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPlanetEventActivationParadise() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
    planet.setPlanetOwner(0, info);
    planet.setEventActivation(false);
    planet.setPlanetaryEvent(PlanetaryEvent.PARADISE);
    assertEquals(PlanetaryEvent.PARADISE, planet.getPlanetaryEvent());
    assertEquals(false, planet.isEventActivated());
    planet.eventActivation(false, null, null);
    assertEquals(true, planet.isEventActivated());
    info.getMsgList().clearMessages();
    assertEquals(Icons.getIconByName(Icons.ICON_FARM),
        info.getMsgList().getMsg().getIcon());
    assertEquals(4, planet.getTotalProduction(Planet.PRODUCTION_FOOD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPlanetEventActivationAncientLab() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
    planet.setPlanetOwner(0, info);
    planet.setEventActivation(false);
    planet.setPlanetaryEvent(PlanetaryEvent.ANCIENT_LAB);
    assertEquals(PlanetaryEvent.ANCIENT_LAB, planet.getPlanetaryEvent());
    assertEquals(false, planet.isEventActivated());
    planet.eventActivation(true, null, null);
    assertEquals(true, planet.isEventActivated());
    info.getMsgList().clearMessages();
    assertEquals(Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH),
        info.getMsgList().getMsg().getIcon());
    assertEquals("Ancient lab", planet.getBuildingList()[0].getName());
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPlanetEventActivationAncientFactory() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
    planet.setPlanetOwner(0, info);
    planet.setEventActivation(false);
    planet.setPlanetaryEvent(PlanetaryEvent.ANCIENT_FACTORY);
    assertEquals(PlanetaryEvent.ANCIENT_FACTORY, planet.getPlanetaryEvent());
    assertEquals(false, planet.isEventActivated());
    planet.eventActivation(true, null, null);
    assertEquals(true, planet.isEventActivated());
    info.getMsgList().clearMessages();
    assertEquals(Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH),
        info.getMsgList().getMsg().getIcon());
    assertEquals("Ancient factory", planet.getBuildingList()[0].getName());
    assertEquals(2, planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPlanetEventActivationAncientTemple() {
    Planet planet = new Planet(new Coordinate(5, 5), "Test I", 1, false);
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
    planet.setPlanetOwner(0, info);
    planet.setEventActivation(false);
    planet.setPlanetaryEvent(PlanetaryEvent.ANCIENT_TEMPLE);
    assertEquals(PlanetaryEvent.ANCIENT_TEMPLE, planet.getPlanetaryEvent());
    assertEquals(false, planet.isEventActivated());
    planet.eventActivation(true, null, null);
    assertEquals(true, planet.isEventActivated());
    info.getMsgList().clearMessages();
    assertEquals(Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH),
        info.getMsgList().getMsg().getIcon());
    assertEquals("Ancient temple", planet.getBuildingList()[0].getName());
    assertEquals(1, planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
  }

}
