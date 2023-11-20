package org.openRealmOfStars.game.States;
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

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.labels.StarFieldTextArea;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.utilities.IOUtilities;

/**
 *
 * Credits view for Open Realm of Stars
 *
 */

public class CreditsView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Text area to show credits
   */
  private StarFieldTextArea textArea;

  /**
   * Number of lines to show in text area
   */
  private static final int NUMBER_OF_LINES = 60;

  /**
   * Main Credits string
   */
  public static final String MAIN_CREDITS =
      "# Programming and Design by\n\n"
      + "Tuomo Untinen\n\n"
      + "# Additional programming\n\n"
      + "Dávid Szigecsán \"sigee\"\n\n"
      + "Diocto\n\n"
      + "dlahtl1004\n\n"
      + "Krlucete\n\n"
      + "wksdn18\n\n"
      + "All graphics under CC-BY-SA 3.0 license. Unless otherwise noted.\n"
      + "See http://creativecommons.org/licenses/by-sa/3.0/\n\n"
      + "# Graphics by\n\n"
      + "Moons and planet made by Unnamed (Viktor.Hahn@web.de)\n"
      + "(http://opengameart.org/content/16-planet-sprites)\n"
      + "(https://opengameart.org/content/17-planet-sprites)\n\n"
      + "SunRed by Priest865 "
      + "(http://opengameart.org/content/space-assets)\n\n"
      + "O-Class Star by cogitollc (CC0) "
      + "(https://opengameart.org/content/o-class-star)\n\n"
      + "Red dwarf star (first try) by cogitollc (CC0) "
      + "(https://opengameart.org/content/red-dwarf-star-first-try)\n\n"
      + "141 Military Icons Set by AngryMeteor.com - "
      + "http://opengameart.org/content/140-military-icons-set-fixed\n\n"
      + "Nebulae, star field and space panel by Tuomo Untinen\n\n"
      + "Route dot by Tuomo Untinen\n\n"
      + "Relations icons by Tuomo Untinen\n\n"
      + "Photon torpedo and plasma bullet by Tuomo Untinen\n\n"
      + "Space ships are done with Surt's modular ships\n\n"
      + "Scaurian ships are done with "
      + "BizmasterStudios's SpaceShip Building Kit\n"
      + "Licensed under CC BY 4.0\n"
      + "https://creativecommons.org/licenses/by/4.0/\n\n"
      + "Smaugirian ships are done with Wuhu's spaceships by Wuhu.\n"
      + "https://opengameart.org/content/spaceships-1\n"
      + "Licensed under CC BY 3.0\n"
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Explosions by Csaba Felvegi aka Chabull\n\n"
      + "Alonian ships by Tuomo Untinen\n\n"
      + "Alien pictures by Surt - "
      + "http://opengameart.org/forumtopic/cc0-scraps\n\n"
      + "Scaurian trader picture by Tuomo Untinen, original art by Surt - "
      + "http://opengameart.org/forumtopic/cc0-scraps\n\n"
      + "Chiraloid race picture by Tuomo Untinen, original art by Surt - "
      + "http://opengameart.org/forumtopic/cc0-scraps\n\n"
      + "Reborgian race picture by Tuomo Untinen, original art by Surt - "
      + "http://opengameart.org/forumtopic/cc0-scraps\n\n"
      + "Lithorian race picture by Tuomo Untinen, original art by Surt - "
      + "http://opengameart.org/forumtopic/cc0-scraps\n\n"
      + "Alteirian race picture by Tuomo Untinen, original art by Surt - "
      + "http://opengameart.org/forumtopic/cc0-scraps\n\n"
      + "Smaugirian race picture by Tuomo Untinen, original art by Surt - "
      + "http://opengameart.org/forumtopic/cc0-scraps\n\n"
      + "Alonian race picture by Tuomo Untinen, original art by Surt - "
      + "http://opengameart.org/forumtopic/cc0-scraps\n\n"
      + "Space Captain by Justin Nichol \n"
      + "Tuomo Untinen added legs for the captain. -"
      + "https://opengameart.org/content/space-captain-with-legs\n\n"
      + "The Husk- Human Analog Android by Justin Nichol - "
      + "https://opengameart.org/content/the-husk-human-analog-android\n"
      + "Tuomo Untinen modified to Synthdroid(Husk with legs)\n\n"
      + "Earth by Justin Nichol - "
      + "https://opengameart.org/content/earth\n"
      + "Licensed under CC BY 3.0\n"
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Mars at Opposition (the Acidalia Region)\n"
      + "by Steve Lee (University of Colorado), Jim Bell (Cornell University)"
      + ",\nMike Wolff (Space Science Institute), and NASA/ESA\n"
      + "This includes full planet image and map tile scaled from original\n"
      + "Licensed under CC BY 4.0\n"
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Hubble's View of Jupiter and Europa in August 2020\n"
      + "by NASA, ESA, A. Simon (Goddard Space Flight Center),"
      + " and M. H. Wong (University of California, Berkeley)"
      + " and the OPAL team.\n"
      + "This includes full planet image and map tile scaled from original\n"
      + "Licensed under CC BY 4.0\n"
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Hubble's Latest Saturn Picture Precedes Cassini's Arrival\n"
      + "by NASA, ESA and E. Karkoschka (University of Arizona)\n"
      + "This includes full planet image and map tile scaled from original\n"
      + "Licensed under CC BY 4.0\n"
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "18 Planet Variations by Hansjörg Malthaner\n"
      + "http://opengameart.org/users/varkalandar\n"
      + "Licensed under CC BY 3.0\n"
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "GBNC logo by Tuomo Untinen\n\n"
      + "Worm hole by Tuomo Untinen\n\n"
      + "Privateering effect in combat by Tuomo Untinen\n\n"
      + "Privateer race picture by Tuomo Untinen\n\n"
      + "Homarian and Scaurian ships are done with "
      + "BizmasterStudios's SpaceShip Building Kit\n"
      + "Licensed under CC BY 4.0\n"
      + "https://creativecommons.org/licenses/by/4.0/\n\n"
      + "Repair symbol from 141 Military Icons Set by AngryMeteor.com\n"
      + "http://opengameart.org/content/140-military-icons-set-fixed\n\n"
      + "Defense symbol modified to look like shield by Tuomo Untinen\n"
      + "Original from 141 Military Icons Set by AngryMeteor.com \n"
      + "http://opengameart.org/content/140-military-icons-set-fixed\n\n"
      + "Music note and Speaker icon by Tuomo Untinen\n\n"
      + "Antenna icon by Tuomo Untinen\n\n"
      + "Prison icon by Tuomo Untinen\n\n"
      + "Happy and Sad faces by Tuomo Untinen\n\n"
      + "Space anomaly cloud tile by Vyntershtoff\n\n"
      + "Wormhole tiles modifications by Tuomo Untinen\n"
      + "Original art by Vyntershtoff\n\n"
      + "Asteroids image\n"
      + "Asteroids by phaelax\n"
      + "Background stars by Tuomo Untinen\n\n"
      + "Pirate pilot image by Tuomo Untinen\n\n"
      + "Blackhole image by Tuomo Untinen\n\n"
      + "Old Probe image contains 3D model of satellite by Grefuntor\n"
      + " http://atmostatic.blogspot.com\n"
      + "Background stars by Tuomo Untinen\n\n"
      + "Old ship image contains "
      + "3D model of Scifi plane model and texture by Ulf\n"
      + "Background stars by Tuomo Untinen\n\n"
      + "Black star ship by canisferus (CC0)\n\n"
      + "Electron nebula by kitart360\n\n"
      + "Space ship with turrets by gfx0 (CC0)\n\n"
      + "Space ship trader vessels by gfx0\n\n"
      + "Space ship bridge by Jani Mäkinen aka gfx0\n\n"
      + "Centaur Space Ship Bridge by Tuomo Untinen\n\n"
      + "Artificial Planet by Tuomo Untinen\n\n"
      + "Shield animation from Sci-fi effects by Skorpio\n"
      + "https://opengameart.org/content/sci-fi-effects\n"
      + "Licensed under CC BY 4.0\n"
      + "https://creativecommons.org/licenses/by/4.0/\n\n"
      + "ORoS logos by Tuomo Untinen\n\n"
      + "Scaurian Space Ship Bridge by Tuomo Untinen\n\n"
      + "Galactic Olympics logo by Tuomo Untinen\n\n"
      + "Fat Man by Renderwahn\n\n"
      + "Galaxy image by Tuomo Untinen\n\n"
      + "Big Ban and peace logos by Tuomo Untinen\n\n"
      + "Pirate ship (Night Raider) by dravenx\n\n"
      + "United Galaxy Tower by Tuomo Untinen\n\n"
      + "Big missile icon by qubodup (CC0)\n\n"
      + "Big money icon by Tuomo Untinen\n\n"
      + "Original money images done by photoshopwizard (CC0)\n\n"
      + "Mechion Space Ship Bridge by Tuomo Untinen\n\n"
      + "Sun with and without solar flares by Tuomo Untinen\n\n"
      + "Desert by Cethiel (CC0)\n\n"
      + "Prehistoric nature landscape by Pepper Racoon - "
      + "https://opengameart.org/content/landscape\n\n"
      + "Viruses (Cold, Flu, Chicken Pox) by bart (CC0)\n\n"
      + "Human Space Ship Bridge by Tuomo Untinen\n\n"
      + "Space ship images by Viktor Hahn (Viktor.Hahn@web.de)\n"
      + "using space ship models by greyoxide, Michael Davies,"
      + " and Viktor Hahn,\n"
      + "is licensed under the Creative Commons Attribution 3.0 Unported"
      + " License.\n"
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Factory image by Viktor Hahn (Viktor.Hahn@web.de)\n"
      + "using space ship models by greyoxide, Michael Davies,"
      + " and Viktor Hahn, background is made by Tuomo Untinen\n"
      + "is licensed under the Creative Commons Attribution 3.0 Unported"
      + " License.\n"
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Big explosion by Tuomo Untinen\n\n"
      + "Tutorial icon by Tuomo Untinen\n\n"
      + "Meteor by Tuomo Untinen\n\n"
      + "Mothoid Space Ship Bridge by Tuomo Untinen\n\n"
      + "Mysterious Signal by Tuomo Untinen\n\n"
      + "Technical Breakthrough by Tuomo Untinen\n\n"
      + "Greyan Space Ship Bridge by Tuomo Untinen\n\n"
      + "Time warp by Tuomo Untinen\n\n"
      + "Homarian Space Ship Bridge by Tuomo Untinen\n\n"
      + "Spinosaurus by Tuomo Untinen\n\n"
      + "Spinosaurus icon by Tuomo Untinen\n\n"
      + "Lightning animation from Sci-fi effects by Skorpio\n"
      + "https://opengameart.org/content/sci-fi-effects\n"
      + "Licensed under CC BY 4.0\n\n"
      + "Tractor beam, organic armor and solar armor"
      + " icons modified by Tuomo Untinen\n"
      + "Originals from 141 Military Icons Set by AngryMeteor.com \n\n"
      + "Plasma, Ion cannon, distortion shield, gravity ripper\n"
      + "and multidimension shield icons modified by Tuomo Untinen\n"
      + "Originals from 141 Military Icons Set by AngryMeteor.com \n\n"
      + "Teuthidae Space Ship Bridge by Tuomo Untinen\n\n"
      + "Spork Space Ship Bridge by Tuomo Untinen\n\n"
      + "Chiraloid Space Ship Bridge by Tuomo Untinen\n\n"
      + "Sci-Fi containers by Rubberduck (CC0)\n\n"
      + "Space Pirate Space Ship Bridge by Tuomo Untinen\n\n"
      + "Rare tech capsule by Tuomo Untinen\n\n"
      + "Floating artifacts by Tuomo Untinen\n\n"
      + "Lithorian ships are done by using"
      + " Space ship building bits, volume 1 ships\n"
      + "by Stephen Challener (Redshrike), hosted by OpenGameArt.org\n"
      + "https://opengameart.org/content/space-ship-building-bits-volume-1\n\n"
      + "Cloaked ship and shuttle 2 by Tuomo Untinen\n\n"
      + "Stasis chamber in shuttle by Tuomo Untinen\n\n"
      + "Lithorian Space Ship Bridge by Tuomo Untinen\n\n"
      + "Reborgian Space Ship Bridge by Tuomo Untinen\n\n"
      + "Smaugirian Space Ship Bridge by Tuomo Untinen\n\n"
      + "Alteirian Space Ship Bridge by Tuomo Untinen\n\n"
      + "Scanner animation by Tuomo Untinen\n\n"
      + "Warzone Concept by Justin Nichol\n"
      + "https://opengameart.org/content/warzone-concept\n\n"
      + "News reader robot by Tuomo Untinen\n\n"
      + "UFO Spritesheet by mieki256 (CC0)\n\n"
      + "Space worm and Kraken ships by Tuomo Untinen\n\n"
      + "Heart and mouth icons modified by Tuomo Untinen\n"
      + "Originals from 141 Military Icons Set by AngryMeteor.com\n\n"
      + "Space fin and tentacle icons by Tuomo Untinen\n\n"
      + "Expanding circle animation from Sci-fi effects by Skorpio\n"
      + "https://opengameart.org/content/sci-fi-effects\n"
      + "Modified to gravity ripper animation by Tuomo Untinen\n"
      + "Licensed under CC BY 4.0\n"
      + "https://creativecommons.org/licenses/by/4.0/\n\n"
      + "Victorian Era desk with court hammer, book and lamp\n"
      + "A courthammer and an open book by mvdwoude. "
      + "Victorian desk by philbgarner.\n"
      + "Lamp base by mvdwoude from courthammer base. "
      + "Rest of the lamp by Tuomo Untinen.\n"
      + "Licensed under CC BY SA 4.0\n"
      + "https://creativecommons.org/licenses/by-sa/4.0/\n\n"
      + "Alien ship by Tuomo Untinen with Stable Diffusion\n\n"
      + "Metal rich surface by Tuomo Untinen with Stable Diffusion\n\n"
      + "Ancient Factory by Tuomo Untinen with Stable Diffusion\n\n"
      + "Ancient Laboratory by Tuomo Untinen with Stable Diffusion\n\n"
      + "Ancient Temple by Tuomo Untinen with Stable Diffusion\n\n"
      + "Ancient Palace by Tuomo Untinen with Stable Diffusion\n\n"
      + "Ancient Research by Tuomo Untinen with Stable Diffusion\n\n"
      + "Gems by Tuomo Untinen with Stable Diffusion\n\n"
      + "Destroyed Planet by Tuomo Untinen with Stable Diffusion\n\n"
      + "Black Monolith by Tuomo Untinen with Stable Diffusion\n\n"
      + "Molten Lava by Tuomo Untinen with Stable Diffusion\n\n"
      + "Arid by Tuomo Untinen with Stable Diffusion\n\n"
      + "Lush Vegetation by Tuomo Untinen with Stable Diffusion\n\n"
      + "Artifact on Planet by Tuomo Untinen with Stable Diffusion\n\n"
      + "Destroyed planet map tile by Tuomo Untinen with Stable Diffusion\n\n"
      + "Gravity veins map tiles by Tuomo Untinen\n\n"
      + "Animated Portal or Wormhole, several Variants by Hansjörg Malthaner\n"
      + "http://opengameart.org/users/varkalandar\n"
      + "Licensed under CC BY SA 4.0\n"
      + "https://creativecommons.org/licenses/by-sa/4.0/\n\n"
      + "\n\nFonts are under SIL Open Font License, Version 1.1.\n"
      + "# Fonts by\n\n"
      + "Cubellan font by Jyrki Ihalainen (yardan74@gmail.com)\n\n"
      + "Squarrion font by Cristiano Sobral (cssobral2013@gmail.com)\n"
      + "from Reserved Font Name EXO by Natanael Gama (exo@ndiscovered.com)\n\n"
      + "# Sounds by\n\n"
      + "Space combat weapons sounds by\n"
      + "Michel Baradari apollo-music.de\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Space combat explosions sounds by\n"
      + "Michel Baradari apollo-music.de\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Menu click sounds by\n"
      + "Tim Mortimer\n"
      + "http://www.archive.org/details/TimMortimer\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Radio call sound by\n"
      + "Tuomo Untinen\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Engine sounds by\n"
      + "Tuomo Untinen\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Starbase deploy sound\n"
      + "Adaptation by Tuomo Untinen, original sounds by\n"
      + "Lee Barkovich and Michel Baradari\n"
      + "http://www.archive.org/details/Berklee44Barkovich"
      + " http://www.lbarkovich.com/\n"
      + "https://opengameart.org/content/robotic-mechanic-step-sounds\n"
      + "https://opengameart.org/content/9-sci-fi-computer-sounds-and-beeps\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Ship repair sound\n"
      + "Adaptation by Tuomo Untinen, original sounds by\n"
      + "Lee Barkovich\n"
      + "http://www.archive.org/details/Berklee44Barkovich"
      + " http://www.lbarkovich.com/\n"
      + "https://opengameart.org/content/robotic-mechanic-step-sounds\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Laser_07 as EMP explosion by\n"
      + "Little Robot Sound Factory\n"
      + "www.littlerobotsoundfactory.com\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Teleport sound by\n"
      + "Michel Baradari\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Wormhole sound\n"
      + "Adaptation by Tuomo Untinen, original sounds by\n"
      + "Écrivain\n"
      + "https://opengameart.org/content/4-space-portal-sounds\n"
      + "Licensed under CC0\n\n"
      + "Muffled Distant Explosion by NenadSimic (CC0)\n\n"
      + "Nuke Sound\n"
      + "Adaptation by Tuomo Untinen, from\n"
      + "Chunky Explosion by Joth(CC0)\n\n"
      + "Disabled menu sound by\n"
      + "Lokif(CC0)\n\n"
      + "Shield sounds by Bart(CC0)\n\n"
      + "Alarm loop by\n"
      + "Little Robot Sound Factory\n"
      + "www.littlerobotsoundfactory.com\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Warp engine engage by Bart(CC0)\n\n"
      + "Destroy building sound from 100 CC0 SFX #2 by Rubberduck (CC0)\n\n"
      + "Whipping sound by\n"
      + "Tuomo Untinen\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Coins sound by\n"
      + "Tuomo Untinen\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Robotic Transformations (Dissamble) sound by\n"
      + "Mekaal - https://opengameart.org/content/robotic-transformations\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "SpaceEngine_Start_00 as colonized by\n"
      + "Little Robot Sound Factory\n"
      + "www.littlerobotsoundfactory.com\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Glitch sound by Tuomo Untinen(CC0)\n\n"
      + "Electricity Sound Effects by Erich Izdepski\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Overloaded engine, jammer, targeting computer and cloaking device\n"
      + "sounds from 50 CC0 SFX #2 by Rubberduck (CC0)\n\n"
      + "Tractor beam sound by BLACK LODGE GAMES, LLC (CC0)\n"
      + "https://creativecommons.org/publicdomain/zero/1.0/\n\n"
      + "Ion Cannon Sound by \n"
      + "colmmullally\n"
      + "https://freesound.org/people/colmmullally/sounds/462220/\n"
      + "Licensed under CC BY 3.0 "
      + "https://creativecommons.org/licenses/by/3.0/\n\n"
      + "Plasma Cannon Sound Effects by Erokia\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Transport inbound Sci-Fi Trooper voice by Angus Macnaughton\n"
      + "https://opengameart.org/content/sci-fi-trooper-voice-pack-54-lines\n"
      + "Licensed under CC BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Scanner overload sound by\n"
      + "Tuomo Untinen\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Bite and tentacle sounds by\n"
      + "Tuomo Untinen\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Futuristic Weapons / Assault Rifle by\n"
      + "Michael Klier\n"
      + "Licensed under  CC-BY-SA 3.0 "
      + "http://creativecommons.org/licenses/by-sa/3.0/\n\n"
      + "Gravity Ripper sound by\n"
      + "Tuomo Untinen\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "# Musics By\n\n"
      + "Observing The Star by\n"
      + "YD (CC0)\n\n"
      + "A million light years between us by\n"
      + "Alexandr Zhelanov https://soundcloud.com/alexandr-zhelanov\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Neon Transit by\n"
      + "Alexandr Zhelanov https://soundcloud.com/alexandr-zhelanov\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Thrust Sequence by\n"
      + "Matthew Pablo http://www.matthewpablo.com\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Nautilus by\n"
      + "poinl (CC0)\n\n"
      + "Brave Space Explorers by\n"
      + "Alexandr Zhelanov https://soundcloud.com/alexandr-zhelanov\n"
      + "Licensed under CC BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Unstoppable Driver by\n"
      + "Alexandr Zhelanov https://www.youtube.com/c/AlexandrZhelanovsMusic\n"
      + "Licensed under CC BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Lost Signal by\n"
      + "PetterTheSturgeon\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Walking with Poseidon by\n"
      + "mvrasseli\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Conquerors by\n"
      + "Alexandr Zhelanov https://soundcloud.com/alexandr-zhelanov\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Diplomacy by\n"
      + "Ove Melaa\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Pressure by\n"
      + "YD (CC0)\n\n"
      + "Fantasy choir 2 by\n"
      + "www.punchytap.com (CC0)\n\n"
      + "Space Theme by\n"
      + "Joshua Stephen Kartes\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Fight Music Theme01 by\n"
      + "GTDStudio - Pavel Panferov\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Trogl by\n"
      + "oglsdl https://soundcloud.com/oglsdl\n"
      + "Licensed under CC BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Abandoned Steel Mill by\n"
      + "Spring (CC0)\n\n"
      + "Interplanetary Odyssey by\n"
      + "Patrick de Arteaga https://patrickdearteaga.com/\n"
      + "Licensed under CC BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Malloga Balling by\n"
      + "Joe Reynolds - Professorlamp  jrtheories.webs.com\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Menace by\n"
      + "YD (CC0)\n\n"
      + "Law In The City by\n"
      + "Alexandr Zhelanov https://soundcloud.com/alexandr-zhelanov\n"
      + "Licensed under CC BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "I Do Know You by\n"
      + "Memoraphile @ You're Perfect Studio (CC0)\n\n"
      + "Out of Time by\n"
      + "Hitctrl https://soundcloud.com/hitctrl\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Drifting Beyond the Stars by\n"
      + "Hitctrl https://soundcloud.com/hitctrl\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Set Fire to Reality - dark/electronic by \n"
      + "Justin Dalessandro(ColdOneK)\n"
      + "Licensed under CC BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Death Is Just Another Path by \n"
      + "Otto Halmén\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Braindead by \n"
      + "Kim Lightyear\n"
      + "Licensed under  CC-BY-SA 3.0 "
      + "http://creativecommons.org/licenses/by-sa/3.0/\n\n"
      + "Techno DRIVE!!! by \n"
      + "Centurion_of_war\n"
      + "Licensed under CC-BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Mysterious Anomaly by \n"
      + "Eric Matyas Soundimage.org\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Sky Portal by\n"
      + "Alexandr Zhelanov https://soundcloud.com/alexandr-zhelanov\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Guitar Song by Nicole Marie T\n"
      + "https://opengameart.org/content/guitar-song-loops-indie-game-seamless-"
      + "looping-rpg-level-stage-etc\n"
      + "Licensed under CC BY 3.0 "
      + "http://creativecommons.org/licenses/by/3.0/\n\n"
      + "Cyborg by\n"
      + "Umplix (CC0)\n\n"
      + "Dark Intro by\n"
      + "Nikke (CC0)\n\n"
      + "Cosmic Creature by Vitalezzz\n"
      + "Licensed under CC BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Planetary Assault by Vitalezzz\n"
      + "Licensed under CC BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n"
      + "Solar Sail by Vitalezzz\n"
      + "Licensed under CC BY 4.0 "
      + "http://creativecommons.org/licenses/by/4.0/\n\n";

  /**
   * Selection for credits and license text.
   */
  public static final int CREDITS_AND_LICENSE = 0;

  /**
   * Selection for change log.
   */
  public static final int CHANGE_LOG = 1;

  /**
   * Constructor for Credits view
   * @param listener Action Listener for command
   * @param title Program Title
   * @param version Program Version
   * @param textType See CREDITS_AND_LICENSE and CHANGE_LOG.
   * @throws IOException If error happens on reading license files.
   */
  public CreditsView(final ActionListener listener, final String title,
      final String version, final int textType) throws IOException {
    String creditsText = "";
    if (textType == CREDITS_AND_LICENSE) {
      creditsText = generateCreditsAndLicense(title, version);
    }
    if (textType == CHANGE_LOG) {
      creditsText = generateChangeLog(title, version);
    }
    this.setLayout(new BorderLayout());
    textArea = new StarFieldTextArea();
    textArea.setScrollText(creditsText, NUMBER_OF_LINES);
    textArea.setText(creditsText);
    textArea.setSmoothScroll(true);
    textArea.setEditable(false);
    this.add(textArea, BorderLayout.CENTER);

    SpaceButton btn = new SpaceButton("OK", GameCommands.COMMAND_OK);
    btn.addActionListener(listener);
    this.add(btn, BorderLayout.SOUTH);

  }

  /**
  * Generate credits and license text.
  * @param title Program Title
  * @param version Program Version
  * @return Credit and license text
  * @throws IOException If error happens on reading license files.
  */
  public String generateCreditsAndLicense(final String title,
      final String version) throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
        + "\n\n\n\n\n\n\n\n\n\n\n# ");
    sb.append(title);
    sb.append(" ");
    sb.append(version);
    sb.append("\n\n# Credits\n\n");
    sb.append(MAIN_CREDITS);

    InputStream is = CreditsView.class
        .getResourceAsStream("/resources/GPL2.txt");
    BufferedInputStream bis = new BufferedInputStream(is);
    String gpl2License = "";
    try (DataInputStream dis = new DataInputStream(bis)) {
      gpl2License = IOUtilities.readTextFile(dis);
    }
    is = CreditsView.class.getResourceAsStream(
        "/resources/fonts/Cubellan_v_0_7/Cubellan_License_SIL_OFL.txt");
    bis = new BufferedInputStream(is);
    String cubellanLicense = "";
    try (DataInputStream dis = new DataInputStream(bis)) {
      cubellanLicense = IOUtilities.readTextFile(dis);
    }
    is = CreditsView.class.getResourceAsStream(
        "/resources/fonts/Squarion/License.txt");
    bis = new BufferedInputStream(is);
    String squarionLicense = "";
    try (DataInputStream dis = new DataInputStream(bis)) {
      squarionLicense = IOUtilities.readTextFile(dis);
    }
    sb.append("\n\n"
        + "# GNU GENERAL PUBLIC LICENSE Version 2, June 1991\n");
    sb.append(gpl2License);
    sb.append("\n\n# Cubellan Font license\n\n");
    sb.append(cubellanLicense);
    sb.append("\n\n# Squarion Font License\n\n");
    sb.append(squarionLicense);
    return sb.toString();
  }
  /**
  * Generate Change lgo.
  * @param title Program Title
  * @param version Program Version
  * @return Change log text
  * @throws IOException If error happens on reading license files.
  */
  public String generateChangeLog(final String title,
      final String version) throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
        + "\n\n\n\n\n\n\n\n\n\n\n# ");
    sb.append(title);
    sb.append(" ");
    sb.append(version);
    sb.append("\n\n# Change log\n\n");

    InputStream is = CreditsView.class
        .getResourceAsStream("/resources/changelog.txt");
    BufferedInputStream bis = new BufferedInputStream(is);
    String changeLog = "";
    try (DataInputStream dis = new DataInputStream(bis)) {
      changeLog = IOUtilities.readTextFile(dis);
    }
    sb.append(changeLog);
    return sb.toString();
  }
  /**
   * Get full credits as a String
   * @return Full credits
   */
  public String getCreditsText() {
    return textArea.getText();
  }
  /**
   * Update Text area
   */
  public void updateTextArea() {
    if (textArea.getSmoothScrollNextRow()) {
      textArea.getNextLine();
    }
    repaint();
  }

}
