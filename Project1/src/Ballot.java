/* Ballot.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/


package svs;
/**
 * Contains the information related to an individual ballot and provides methods
 * to retrieve information about the choices made on that ballot during the
 * course of an election.
 * 
 * @author Nicholas Lovdahl (lovda015)
 */

public class Ballot {
  /**
   * Creates a ballot with the provided rankings and ID. The new ballot's
   * current rank is initially zero. The rankings provided for the creation of
   * the ballot should be ordered from the first choice candidate as the first
   * element to the last choice candidate as the last element. If the rankings
   * provided have no choices (if the voter did not rank all of the candidates),
   * then this should either be reflected through a shorter array of only the
   * ranked candidates or as null values in the corresponding last elements of
   * the array. Any null values in the rankings should all be at the end, never
   * in the middle.
   * 
   * @param rankings an array of candidates representing the choices for the
   *                 ballot.
   * @param id a number used to denote the ballot being created.
   */
  public Ballot(Candidate[] rankings, int id) {
    this.rankings = rankings;
    this.id = id;
    current_rank = 0;
  }
  
  /**
   * Returns the ID, the number used to denote a particular ballot.
   * 
   * @return the ID of the ballot.
   */
  public int get_id() { return id; }
  
  /**
   * Returns the order of the rank currently being used for this ballot. That
   * is, the 'n' corresponding the 'nth' choice that is given for this ballot at
   * the moment.
   * 
   * @return the order of the rank current used for the ballot.
   */
  public int current_rank() { return current_rank; }
  
  /**
   * Resets the order of the rank being used by this ballot to zero. This rank
   * would correspond to the first choice for the ballot's rankings.
   */
  public void reset_current_rank() { current_rank = 0; }
  
  /**
   * Increments the order of the current rank used by the ballot by one and
   * returns the new rank. If incrementing the rank would cause the current
   * choice for the ballot to run off of the array of rankings (if the ballot
   * is on the last choice), -1 is returned instead.
   * 
   * @return the new order of the rank being used by the ballot, or -1 if the
   *         rank cannot be incremented.
   */
  public int increment_rank() {
    if (current_rank < rankings.length - 1) {
      current_rank++;
      return current_rank;
    } else {
      return -1;
    }
  }
  
  /**
   * Returns the candidate that is the current choice of for the ballot. That
   * is, the candidate that is the 'nth' choice where 'n' is the current order
   * of the rank used by the ballot. If the current choice does not correspond
   * to any of the rankings for the ballot (if the order of the rank is greater
   * than the number of rankings), then null is returned instead.
   * 
   * @return the candidate that is the current choice for the ballot, or null if
   *         their is no candidate in the ballot's rankings for the current
   *         choice.
   */
  public Candidate current_choice() {
    // we check in case the array of rankings is of size zero
    if (current_rank < rankings.length) {
      return rankings[current_rank];
    } else {
      return null;
    }
  }
  
  private final Candidate[] rankings;
  private final int id;
  private int current_rank;
}
