/* STVDroopController.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/
package svs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/** This class is in charge of running the Droop Quota Algorithm on the ballots
 * and candidates that the user provides. The class consists of a method that
 * actually implements the algorithm, and a method that displays the election
 * results to the winner through the user interface
 *
 * @author Taylor O'Neill (oneil569)
 */
public class STVDroopController extends ElectionController {
  
  /** The constructor for this class takes in the data structures generated for 
  * the ballots and candidates that are a part of the election. These data 
  * structures are passed to its parent class constructor.
  * @param  ballots
  * @param  candidates
  * @author Taylor O'Neill (oneil569)
  */
  public STVDroopController(ArrayList<Ballot> ballots, Candidate[] candidates) {
    super(ballots, candidates);
  }
  
  /** The Election Routine method is the method that is in charge of running
  * the Droop Quota Algorithm on the ballots and candidates provided. The method
  * takes in the number of seats that must be assigned as the argument. The method
  * generates a list of winners and losers and returns it to the Election
  * Controller.
  * @param num_seats
  * @throws IOException if no file is selected
  * @return array of winning and losing candidates.
  * @author tlone
  */
  @Override
  public Candidate[] ElectionRoutine(int num_seats) throws IOException {
    // List that declared winners will be appended to
    ArrayList<Candidate> winner_candidates = new ArrayList<>();
    // List that declared losers will be appended to
    ArrayList<Candidate> loser_candidates = new ArrayList<>();
    // an array that keeps track of when a candidate receives their first ballot
    int[] firstBallotIndicator = new int[candidates.length];
    for (int index = 0; index < firstBallotIndicator.length; index++) {
        firstBallotIndicator[index] = -1;
    }

    int firstBallot = 1;
    int droopQuota = (ballots.size() / (num_seats+1)) + 1;

    // Iterate through the ballots and distribute the votes
    Iterator<Ballot> it = ballots.iterator();
    // This first loop goes through all of the ballots and assigns the top ranked
    // candidate on each ballot to that candidate (unless the candidate has already
    // won.
    while (it.hasNext()) {
        Ballot b = it.next();
        boolean next = false;
        while (!next) {
            Candidate SelectedCandidate = b.current_choice();
            // Case where no candidates on the ballot are valid choices
            if (SelectedCandidate == null) {
                next = true;
            }
            // case where the candidate on a ballot has already been declared
            // a winner. In this case, the ballot will increment its rank to 
            // point to the next ranked candidate
            else if (winner_candidates.contains(SelectedCandidate)) {
                b.increment_rank();
            }
            else {
                // Distributing the ballot to the specified candidate
                DistributeBallot(b, SelectedCandidate);
                // this for loop find the candidate that was just selected and 
                // marks down that they have finally received their first ballot
                // if required
                for (int findCandidate = 0; findCandidate < candidates.length; findCandidate++) {
                    if (candidates[findCandidate].equals(SelectedCandidate)) {
                        if (firstBallotIndicator[findCandidate] == -1) {
                            firstBallotIndicator[findCandidate] = firstBallot++;                            
                        }
                    }
                }
                //check if candidate has reached droop quota
                if (SelectedCandidate.get_num_ballots() == droopQuota) {
                    winner_candidates.add(SelectedCandidate);
                }
                next = true;
            }
        }
    }
    // Next, we kind the first loser of the election and reassign their ballots
    // to the next ranked candidate on the ballot. This process continues until
    // all of the seats in the election have been filled.
    while (winner_candidates.size() < num_seats) {
        int minimumBallots = 0;
        int minBallotIndex = 0;
        // This for loop finds a valid candidate that could potentially be the
        // loser of this round. This candidate is simply used as a starting reference
        // for the next for loop.
        for (int z = 0; z < candidates.length; z++) {
            if (!winner_candidates.contains(candidates[z]) && !loser_candidates.contains(candidates[z])) {
                minimumBallots = candidates[z].get_num_ballots();
                minBallotIndex = z;
                break;
            }
        }
        // The candidate with the least amount of ballot (who has not already been
        // claimed a winner or loser) is determined. This candidate is not deemed
        // the new loser
        for (int i = 0; i < candidates.length; i++) {
            if ((candidates[i].get_num_ballots() <= minimumBallots) && !winner_candidates.contains(candidates[i]) && !loser_candidates.contains(candidates[i])) {
                if (candidates[i].get_num_ballots() == minimumBallots) {
                    if (firstBallotIndicator[i] > firstBallotIndicator[minBallotIndex]) {
                        minimumBallots = candidates[i].get_num_ballots();
                        minBallotIndex = i;
                    }
                }
                else {
                    minimumBallots = candidates[i].get_num_ballots();
                    minBallotIndex = i;
                }
                
            }
        }
        loser_candidates.add(candidates[minBallotIndex]);
        // The ballots from the losing candidate are now redistributed.
        for (int j = 0; j < candidates[minBallotIndex].get_num_ballots(); j++) {
            Ballot redistBallot = candidates[minBallotIndex].get_ballot(j);
            redistBallot.increment_rank();
            boolean next = false;
            while (!next) {
                Candidate SelectedCandidate = redistBallot.current_choice();
                if (SelectedCandidate == null) {
                    next = true;
                }
                else if (winner_candidates.contains(SelectedCandidate) || loser_candidates.contains(SelectedCandidate)) {
                    redistBallot.increment_rank();
                }
                else {
                    RedistributeBallot(redistBallot, candidates[minBallotIndex], SelectedCandidate);
                    //check if candidate has reached droop quota
                    if (SelectedCandidate.get_num_ballots() == droopQuota) {
                        winner_candidates.add(SelectedCandidate);
                    }      
                    next = true;
                }
            }
            if (winner_candidates.size() == num_seats) {
                break;
            }
        }
    }
    // This array will hold the final results of the election and be returned.
    // The first num_seats indices will contain the winning candidates, in order,
    // while the remaining indices will show the losers.
    Candidate[] finalResults = new Candidate[candidates.length];
    int ptr = 0;
    for (int winnerIndex = 0; winnerIndex < winner_candidates.size(); winnerIndex++) {
        finalResults[ptr++] = winner_candidates.get(winnerIndex);
    }
    for (int rest = 0; rest < candidates.length; rest++) {
        if (!winner_candidates.contains(candidates[rest]) && !loser_candidates.contains(candidates[rest])) {
            finalResults[ptr++] = candidates[rest];
        }
    }
    for (int loserIndex = loser_candidates.size()-1; loserIndex >= 0; loserIndex--) {
        finalResults[ptr++] = loser_candidates.get(loserIndex);
    }
    return finalResults;
  }
  /** This method is in charge of writing election results to the screen. The
   * results include the election type, number of seats, number of ballots,
   * number of candidates, and the lists of the winners and losers.
   * @param results
   * @param num_seats
   * @return 
   * @author Taylor O'Neill (oneil569)
   */
  @Override
  public String InterpretResults(Candidate[] results, int num_seats) {
      String msg1 = "Election Type: Droop Quota\nNumber of ballots: " + ballots.size() + "\n"
              + "Number of seats: " + num_seats + "\n" + "Number of candidates: " + candidates.length + "\n";
      msg1 = msg1 + "Winners (in order):\n";
      int i = 0;
      for (i = 0; i < num_seats; i++) {
          msg1 = msg1 + results[i].get_name() + "\n";
      }
      msg1 = msg1 + "Losers (from most recent to first):\n";
      while (i < results.length) {
          msg1 = msg1 + results[i++].get_name() + "\n";
      }
      return msg1;
  }
  
  /** This method simply returns the name of the election to other subsystems
   * that way want this information
   * @return the string containing the election name
   * @author Taylor O'Neill (oneil569)
   */
  public static String ElectionName() { return "STV with Droop Quota"; }
}
