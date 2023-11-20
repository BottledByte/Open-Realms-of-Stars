package org.openRealmOfStars.audio.soundeffect;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2020 Tuomo Untinen
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

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test for SoundPlayer class
 */

public class SoundPlayerTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSoundVolume() {
    SoundPlayer.setSoundVolume(100);
    assertEquals(100, SoundPlayer.getSoundVolume());
    SoundPlayer.setSoundVolume(50);
    assertEquals(50, SoundPlayer.getSoundVolume());
    SoundPlayer.setSoundVolume(0);
    SoundPlayer.setSoundEnabled(true);
    assertEquals(true, SoundPlayer.isSoundEnabled());
    SoundPlayer.setSoundEnabled(false);
    assertEquals(false, SoundPlayer.isSoundEnabled());
    SoundPlayer.setSoundEnabled(true);
    assertEquals(true, SoundPlayer.isSoundEnabled());
    SoundPlayer.setSoundEnabled(false);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSoundPlayer() {
    for (int i = 0; i < 100; i++) {
      SoundPlayer.playEngineSound();
      SoundPlayer.playMenuDisabled();;
      SoundPlayer.playShieldSound();;
      SoundPlayer.playEngineSound();
      SoundPlayer.playEngineSound();
    }
  }

}
