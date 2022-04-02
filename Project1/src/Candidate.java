/* Candidate.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/

package svs;

import java.util.ArrayList;

/**
 * Contains the information used to describe a particular candidate in an
 * election and provides methods to facilitate the distribution and
 * redistribution of ballots during the course of an election. That is, this
 * serves as a 'bucket' which can be used to keep track of which ballots belong
 * to a candidate throughout the course of an election.
 * 
 * @author Nicholas Lovdahl (lovda015), Amir Jalili (jalil014)
 */
public class Candidate implements Comparable<Candidate> {
  /**
   * Creates the Candidate for the provided name and ID. Upon creation, the
   * candidate has an empty (but initialized) ballot list.
   * 
   * @param name the name of the candidate (for example, "John Q. Public").
   * @param id a number used to denote the candidate being created.
   */
  public Candidate(String name, int id) {
    this.name = name;
    this.id = id;
    ballot_list = new ArrayList<>();
  }
  
  /**
   * Returns the name of the candidate.
   * 
   * @return the name of the candidate (for example, "John Q. Public")
   */
  public String get_name() { return name; }
  
  /**
   * Returns the ID, the number used to denote a particular candidate.
   * 
   * @return the ID of the candidate.
   */
  public int get_id() { return id; }
  
  /**
   * Returns the number of ballots currently distributed to the candidate in
   * their ballot list.
   * 
   * @return the number of ballots currently in the candidate's ballot list.
   */
  public int get_num_ballots() { return ballot_list.size(); }
  
  /**
   * Clears the candidate's ballot list so that no ballots are distributed to
   * them.
   */
  public void reset_ballot_list() { ballot_list.clear(); }
  
  /**
   * Returns the 'nth' choice in candidates for the ballot. The index, n, used
   * to access this choice starts at zero. For example, to get the second
   * choice candidate, 1 would be used. If the index used is not in range with
   * respect to the rankings, then null is returned instead of a candidate.
   * 
   * @param n the index representing the choice in candidates to access.
   * @return the candidate corresponding to the choice for the provided index.
   */
  public Ballot get_ballot(int n) {
    if (n < ballot_list.size()) {
      return ballot_list.get(n);
    } else {
      return null;
    }
  }
  
  /**
   * Adds, or distributes, a ballot to the candidate's ballot list.
   * 
   * @param ballot the ballot to be added to the candidate's ballot list.
   */
  public void add_ballot(Ballot ballot) { ballot_list.add(ballot); }
  
  /**
   * Pulls the last ballot on the candidate's ballot list. If the candidate's
   * ballot list is empty, then null is returned instead.
   * 
   * @return the ballot that was pulled from the candidate's ballot list.
   */
  public Ballot pull_ballot() {
    if (ballot_list.isEmpty()) {
      return null;
    } else {
      return ballot_list.remove(ballot_list.size() - 1);
    }
  }
  
  /**
   * Compares the number of ballots between candidates. If a candidate has more
   * votes than the candidate they are compared to, the number returned will be
   * positive. If the candidate they are compared to has more votes, the number
   * returned will be negative.
   * 
   * @param compare_candidate the candidate to be compared to.
   * @return the difference in the number of ballots between candidates.
   */
  @Override
  public int compareTo(Candidate compare_candidate) {
    int compare_ballots = ((Candidate) compare_candidate).get_num_ballots();

    return this.get_num_ballots() - compare_ballots;
  }
  
  private final String name;
  private final int id;
  private final ArrayList<Ballot> ballot_list;
}
