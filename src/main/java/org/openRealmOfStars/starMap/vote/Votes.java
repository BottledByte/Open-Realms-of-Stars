package org.openRealmOfStars.starMap.vote;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2019-2023 Tuomo Untinen
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.openRealmOfStars.starMap.vote.sports.VotingChoice;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Votes for starmap
*
*/
public class Votes {

  /**
   * List of votes for starmap.
   */
  private ArrayList<Vote> listOfVotes;

  /**
   * Constructor for Votes.
   */
  public Votes() {
    listOfVotes = new ArrayList<>();
  }

  /**
   * Read Votes from DataInputStream
   * @param dis DataInputStream
   * @param numberOfRealms Number of Realms in starmap.
   * @throws IOException if there is any problem with DataInputStream
   */
  public Votes(final DataInputStream dis, final int numberOfRealms)
      throws IOException {
    listOfVotes = new ArrayList<>();
    int count = dis.readInt();
    for (int i = 0; i < count; i++) {
      Vote vote = new Vote(dis, numberOfRealms);
      listOfVotes.add(vote);
    }
  }
  /**
   * Get Votes as a list
   * @return ArrayList of votes.
   */
  public ArrayList<Vote> getVotes() {
    return listOfVotes;
  }

  /**
   * Add new vote for list.
   * @param vote Vote to add.
   */
  public void addNewVote(final Vote vote) {
    listOfVotes.add(vote);
  }
  /**
   * Remove Ruler of Galaxy vote.
   */
  public void removeRulerVote() {
    Vote removeVote = null;
    for (Vote vote : listOfVotes) {
      if (vote.getType() == VotingType.RULER_OF_GALAXY) {
        removeVote = vote;
      }
    }
    listOfVotes.remove(removeVote);
  }
  /**
   * Get votes that are voteable.
   * @return ArrayList of votes
   */
  public ArrayList<Vote> getVotableVotes() {
    ArrayList<Vote> list = new ArrayList<>();
    for (Vote vote : listOfVotes) {
      if (vote.getType() != VotingType.FIRST_CANDIDATE
          && vote.getType() != VotingType.SECOND_CANDIDATE) {
        list.add(vote);
      }
    }
    return list;
  }

  /**
   * Get Next important vote, which isn't galactic olympic participation.
   * @return Vote or null
   */
  public Vote getNextImportantVote() {
    for (Vote vote : listOfVotes) {
      if (vote.getType() != VotingType.GALACTIC_OLYMPIC_PARTICIPATE
          && vote.getTurnsToVote() > 0) {
        return vote;
      }
    }
    return null;
  }

  /**
   * Generate next important vote. This will add new vote
   * to list of votes automatically.
   * @param maxNumberOfVotes Maximum number of votes
   * @param numberOfRealms NUmber of realms in starmap
   * @param turns When voting needs to be done.
   * @return Vote if able to add new one, otherwise null
   */
  public Vote generateNextVote(final int maxNumberOfVotes,
      final int numberOfRealms, final int turns) {
    int count = 0;
    for (Vote vote : listOfVotes) {
      if (vote.getType() != VotingType.GALACTIC_OLYMPIC_PARTICIPATE
          && vote.getType() != VotingType.FIRST_CANDIDATE
          && vote.getType() != VotingType.SECOND_CANDIDATE) {
        count++;
      }
    }
    if (maxNumberOfVotes - count <= 1) {
      Vote vote = new Vote(VotingType.RULER_OF_GALAXY, numberOfRealms, turns);
      vote.setOrganizerIndex(getFirstCandidate());
      vote.setSecondCandidateIndex(getSecondCandidate());
      listOfVotes.add(vote);
      return vote;
    }
    if (maxNumberOfVotes - count == 2) {
      Vote vote = new Vote(VotingType.SECOND_CANDIDATE_MILITARY,
          numberOfRealms, turns);
      listOfVotes.add(vote);
      return vote;
    }
    if (maxNumberOfVotes - count > 2) {
      ArrayList<VotingType> types = new ArrayList<>();
      types.add(VotingType.BAN_NUCLEAR_WEAPONS);
      types.add(VotingType.BAN_PRIVATEER_SHIPS);
      types.add(VotingType.GALACTIC_PEACE);
      types.add(VotingType.TAXATION_OF_RICHEST_REALM);
      for (Vote vote : listOfVotes) {
        types.remove(vote.getType());
      }
      int index = DiceGenerator.getRandom(types.size() - 1);
      Vote vote = new Vote(types.get(index), numberOfRealms, turns);
      listOfVotes.add(vote);
      return vote;
    }
    return null;
  }

  /**
   * Get List of voteable.
   * @param maxNumberOfVotes Max number of votes
   * @param numberOfRealms Number of realms in starmap
   * @param turns When voting needs to be done.
   * @return Array of votetables.
   */
  public Vote[] getListOfVoteables(final int maxNumberOfVotes,
      final int numberOfRealms, final int turns) {
    int count = 0;
    ArrayList<Vote> votesAvailable = new ArrayList<>();
    boolean secondCandidateVoted = false;
    for (Vote vote : listOfVotes) {
      if (vote.getType() != VotingType.GALACTIC_OLYMPIC_PARTICIPATE
          && vote.getType() != VotingType.FIRST_CANDIDATE
          && vote.getType() != VotingType.SECOND_CANDIDATE) {
        count++;
      }
      if (vote.getType() == VotingType.SECOND_CANDIDATE) {
        secondCandidateVoted = true;
      }
    }
    if (maxNumberOfVotes - count <= 1 && secondCandidateVoted) {
      Vote vote = new Vote(VotingType.RULER_OF_GALAXY, numberOfRealms, turns);
      vote.setOrganizerIndex(getFirstCandidate());
      vote.setSecondCandidateIndex(getSecondCandidate());
      votesAvailable.add(vote);
    } else if (maxNumberOfVotes - count <= 2 && !secondCandidateVoted) {
      Vote vote = new Vote(VotingType.SECOND_CANDIDATE_MILITARY,
          numberOfRealms, turns);
      votesAvailable.add(vote);
    }
    ArrayList<VotingType> types = new ArrayList<>();
    types.add(VotingType.BAN_NUCLEAR_WEAPONS);
    types.add(VotingType.BAN_PRIVATEER_SHIPS);
    types.add(VotingType.GALACTIC_PEACE);
    types.add(VotingType.TAXATION_OF_RICHEST_REALM);
    for (Vote vote : listOfVotes) {
      types.remove(vote.getType());
    }
    for (VotingType type : types) {
      Vote vote = new Vote(type, numberOfRealms, turns);
      votesAvailable.add(vote);
    }
    return votesAvailable.toArray(new Vote[votesAvailable.size()]);
  }
  /**
   * Has first candidate selected or not.
   * @return True if selected.
   */
  public boolean firstCandidateSelected() {
    for (Vote vote : listOfVotes) {
      if (vote.getType() == VotingType.FIRST_CANDIDATE) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get First candidate realm index
   * @return First candidate realm index or -1 if not set yet
   */
  public int getFirstCandidate() {
    for (Vote vote : listOfVotes) {
      if (vote.getType() == VotingType.FIRST_CANDIDATE) {
        return vote.getOrganizerIndex();
      }
    }
    return -1;
  }
  /**
   * Get Second candidate realm index
   * @return Second candidate realm index or -1 if not set yet
   */
  public int getSecondCandidate() {
    for (Vote vote : listOfVotes) {
      if (vote.getType() == VotingType.SECOND_CANDIDATE) {
        return vote.getOrganizerIndex();
      }
    }
    return -1;
  }
  /**
   * Save votes to Data output stream
   * @param dos DataOutputStream where to write
   * @throws IOException If writing fails.
   */
  public void saveVotes(final DataOutputStream dos) throws IOException {
    dos.writeInt(listOfVotes.size());
    for (int i = 0; i < listOfVotes.size(); i++) {
      listOfVotes.get(i).saveVote(dos);
    }
  }

  /**
   * Is certain voting type passed or not.
   * @param type Voting type
   * @return True if passed
   */
  private boolean isVotingTypePassed(final VotingType type) {
    int index = getFirstCandidate();
    for (Vote vote : listOfVotes) {
      if (vote.getType() == type) {
        if (vote.getTurnsToVote() == 0
            && vote.getResult(index) == VotingChoice.VOTED_YES) {
          return true;
        }
        return false;
      }
    }
    return false;
  }
  /**
   * Are privateer ships banned or not?
   * @return True if they are banned
   */
  public boolean arePrivateersBanned() {
    return isVotingTypePassed(VotingType.BAN_PRIVATEER_SHIPS);
  }

  /**
   * Are nukes banned or not?
   * @return True if they are banned
   */
  public boolean areNukesBanned() {
    return isVotingTypePassed(VotingType.BAN_NUCLEAR_WEAPONS);
  }

  /**
   * Is taxation of richest realm enabled?
   * @return True if it is.
   */
  public boolean isTaxationOfRichestEnabled() {
    return isVotingTypePassed(VotingType.TAXATION_OF_RICHEST_REALM);
  }

}
