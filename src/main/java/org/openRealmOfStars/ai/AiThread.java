package org.openRealmOfStars.ai;
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

import org.openRealmOfStars.game.state.AITurnView;

/**
*
* Thread for handling AI
*
*/
public class AiThread extends Thread {

  /**
   * AI Turn view
   */
  private AITurnView view;

  /**
   * Has thread started?
   */
  private boolean started;

  /**
   * Is thread running
   */
  private boolean running;

  /**
   * Constructor for AI thread
   * @param turnView AI Turn view for calling AI thread
   */
  public AiThread(final AITurnView turnView) {
    view = turnView;
    started = false;
    running = false;
  }

  /**
   * Has thread started yet
   * @return True if started
   */
  public synchronized boolean isStarted() {
    return started;
  }

  /**
   * Is thread still running
   * @return True if running
   */
  public synchronized boolean isRunning() {
    return running;
  }

  @Override
  public synchronized void start() {
    started = true;
    super.start();
  }

  @Override
  public void run() {
    synchronized (this) {
      running = true;
    }
    boolean turnEnd = false;
    do {
      do {
        turnEnd = view.handleAiTurn();
      } while (!turnEnd);
    } while (!view.exitThread());
    synchronized (this) {
      running = false;
    }
  }

}
