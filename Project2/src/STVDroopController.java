/* STVDroopController.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/

package svs;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
  * 
  * @param ballots Array of all the ballots.
  * @param candidates Array of all the candidates.
  * @author Taylor O'Neill (oneil569)
  */
  public STVDroopController(ArrayList<Ballot> ballots, Candidate[] candidates) {
    super(ballots, candidates);
  }

  /** The Election Routine method is the method that is in charge of running
  * the Droop Quota Algorithm on the ballots and candidates provided. The method
  * takes in the number of seats that must be assigned as the argument. The
  * method generates a list of winners and losers and returns it to the Election
  * Controller.
  * 
  * @param num_seats The number of seats in the election.
  * @throws IOException if no file is selected.
  * @return array of winning and losing candidates.
  * @author Taylor O'Neill (oneil569)
  */
  @Override
  public Candidate[] ElectionRoutine(int num_seats) throws IOException {
    ArrayList<Candidate> winner_candidates = new ArrayList<>();
    ArrayList<Candidate> loser_candidates = new ArrayList<>();
    // an array that keeps track of when a candidate receives their first ballot
    int[] firstBallotIndicator = new int[candidates.length];
    for (int index = 0; index < firstBallotIndicator.length; index++) {
        firstBallotIndicator[index] = -1;
    }

    int firstBallot = 1;
    int droopQuota = (validated_ballots.size() / (num_seats+1)) + 1;

    // Iterate through the ballots and distribute the votes
    Iterator<Ballot> it = validated_ballots.iterator();
    /* This first loop goes through all of the ballots and assigns the top
     ranked candidate on each ballot to that candidate (unless the candidate
     has already won. */
    while (it.hasNext()) {
      Ballot b = it.next();
      boolean next = false;
      while (!next) {
          Candidate SelectedCandidate = b.current_choice();
          // Case where no candidates on the ballot are valid choices
          if (SelectedCandidate == null) { next = true; }
          /* case where the candidate on a ballot has already been declared a
           winner. In this case, the ballot will increment its rank to point to
           the next ranked candidate */
          else if (winner_candidates.contains(SelectedCandidate)) {
            int initial = b.current_rank();
            b.increment_rank();
            int after = b.current_rank();
            if (initial == after) { next = true; }
        } else {  // else the current rank on the ballot is not a winner yet
          DistributeBallot(b, SelectedCandidate);
          /* find the candidate that was just selected and mark that they have
           finally received their first ballot if required */
          for (int findCandidate = 0; findCandidate < candidates.length;
               findCandidate++) {
            if (candidates[findCandidate].equals(SelectedCandidate)) {
              if (firstBallotIndicator[findCandidate] == -1) {
                firstBallotIndicator[findCandidate] = firstBallot++;
              }
            }
          }
          
          // if candidate has reached droop quota, then make them a winner
          if (SelectedCandidate.get_num_ballots() == droopQuota) {
            winner_candidates.add(SelectedCandidate);
          }
          next = true;
        }
      }
    }
    
    /* Next, we kind the first loser of the election and reassign their ballots
     to the next ranked candidate on the ballot. This process continues until
     all of the seats in the election have been filled. */
    while (winner_candidates.size() + loser_candidates.size()
           < candidates.length) {
      int minimumBallots = 0;
      int minBallotIndex = 0;
      
      /* find a valid candidate that could potentially be the loser of this
       round. This candidate is simply used as a starting reference for the next
       for loop. */
      for (int z = 0; z < candidates.length; z++) {
        if (!winner_candidates.contains(candidates[z]) && !loser_candidates.contains(candidates[z])) {
          minimumBallots = candidates[z].get_num_ballots();
          minBallotIndex = z;
          break;
        }
      }
      
      /* The candidate with the fewest ballots (who has not already been claimed
       a winner or loser) is determined. This candidate is not deemed the new
       loser */
      for (int i = 0; i < candidates.length; i++) {
        // if candidate is on neither the winners or losers list
        if ((candidates[i].get_num_ballots() <= minimumBallots) &&
            !winner_candidates.contains(candidates[i]) &&
            !loser_candidates.contains(candidates[i])) {
          if (candidates[i].get_num_ballots() == minimumBallots) {
            if (firstBallotIndicator[i] > firstBallotIndicator[minBallotIndex]) {
              minimumBallots = candidates[i].get_num_ballots();
              minBallotIndex = i;
            }
          } else {
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
          
          // if the ballot is pointing to a null candidate (we skip it then)
          if (SelectedCandidate == null) {
            next = true;
          // if its given to someone who already won/lost, look at the next rank
          } else if (winner_candidates.contains(SelectedCandidate) ||
                     loser_candidates.contains(SelectedCandidate)) {
            int initial = redistBallot.current_rank();
            redistBallot.increment_rank();
            int after = redistBallot.current_rank();
            if (initial == after) { next = true; }
          } else {  // else we found a valid person to give the ballot to
            RedistributeBallot(redistBallot, candidates[minBallotIndex],
                               SelectedCandidate);
            // check if candidate has reached droop quota
            if (SelectedCandidate.get_num_ballots() == droopQuota) {
              winner_candidates.add(SelectedCandidate);
            }
            next = true;
          }
        }
        
        // if all the seats are filled then we can quit early
        if (winner_candidates.size() == num_seats) { break; }
      }
    }
    
    /* This array will hold the final results of the election and be returned.
     The first num_seats indices will contain the winning candidates, in order,
     while the remaining indices will show the losers. */
    Candidate[] finalResults = new Candidate[candidates.length];
    int ptr = 0;
    
    // organize the winners into the final results array
    for (int winnerIndex = 0; winnerIndex < winner_candidates.size();
         winnerIndex++) {
      finalResults[ptr++] = winner_candidates.get(winnerIndex);
    }
    // organize dangling candidates (better losers)
    for (Candidate candidate : candidates) {
      if (!winner_candidates.contains(candidate) &&
          !loser_candidates.contains(candidate)) {
        finalResults[ptr++] = candidate;
      }
    }
    // organize the losers into the final results array
    for (int loserIndex = loser_candidates.size()-1; loserIndex >= 0; loserIndex--) {
      finalResults[ptr++] = loser_candidates.get(loserIndex);
    }
    
    return finalResults;
  }
  
  /** This method gives the results of the election as they would appear on
   * screen to the user. The results include the election type, number of seats,
   * number of ballots, number of candidates, and the lists of the winners and
   * losers.
   * 
   * @param results The array of candidates in the order of results.
   * @param num_seats The number of seats in the election.
   * @return A string containing all the information about the results.
   * @throws IOException if there is a failure when trying to write the report.
   * @author Taylor O'Neill (oneil569)
   */
  @Override
  public String InterpretResults(Candidate[] results, int num_seats)
             throws IOException  {
	  Candidate[]  winner_candidates = new Candidate[num_seats];
    String msg1 = "Election Type: Droop Quota\nNumber of ballots: " +
                  validated_ballots.size() + "\n" +
                  "Number of seats: " + num_seats + "\n" +
                  "Number of candidates: " + candidates.length + "\n" +
                  "Winners (in order):\n";
    int i;
    // list the winners in order
    for (i = 0; i < num_seats; i++) {
        msg1 = msg1 + results[i].get_name() + "\n";
        winner_candidates[i] = results[i];
    }
    
    msg1 = msg1 + "Losers (from most recent to first):\n";
    // list the losers in order
    while (i < results.length) {
      msg1 = msg1 + results[i++].get_name() + "\n";
    }
    
    // write the report to disk
    election_report(num_seats, results, winner_candidates);
    
    return msg1;
  }
  
  /** This method creates a file called "Election_report.txt". This file
   * includes the date, type of election, the candidates, the number of seats,
   * and the winners.
   * 
   * @param num_seats Number of seats in the election.
   * @param candidates All the candidates.
   * @param winner_candidates Winner candidates in order that Droop was reached.
   * @author Amir Jalili (jalil014)  
   * @throws IOException If there as an error while creating a file and writing
   *                     to it.
   */
  @Override
  public void election_report(int num_seats, Candidate[] candidates,
                              Candidate[] winner_candidates) throws IOException{
	  // create the election report file
    FileWriter report_file = new FileWriter("Election_report.txt");
    BufferedWriter writer = new BufferedWriter(report_file);

    writer.write("ELECTION REPORT\n");
    writer.write("Date: " +  java.time.LocalDate.now() + "\n");
    writer.write("Election type: STV with Droop Qouta\n");
    writer.write("Number of seats: " + num_seats + "\n");
    writer.write("All canidates:\n");
    // write out all of the candidates by name
    for (Candidate candidate : candidates) {
      writer.write(candidate.get_name() + ", ");
    }
    
    writer.newLine();
    writer.newLine();
    writer.write("Winners:\n");
    // write out all of the winners by name
    for(int i =0; i < winner_candidates.length; i++) {
      writer.write(i + 1 + "." + winner_candidates[i].get_name() + "\n");
    }
    
    writer.flush();
    writer.close();
  }

  /** This method simply returns the name of the election to other subsystems
   * that way want this information
   * @return the string containing the election name
   * @author Taylor O'Neill (oneil569)
   */
  public static String ElectionName() { return "STV with Droop Quota"; }


  /** This method is responsible to invalidate the ballots that have
   * less than half of the candidates ranked. The validated ballots are
   * added to the validated_ballots array and invalidated ballots are added
   * to the invalidated_ballots array so that a report of invalidated ballots
   * can be created later.
   * @author Amir Jalili (jalil014)
   */
  @Override
  public void invalidate_ballots() {
    // Minimum number of candidates that need to be ranked for each ballot
    double validation_limit = Math.ceil(candidates.length/2.0);

    /* Iterate through all the ballots, if any ballot doesn't meet the
     validation criteria, add it to the invalidated_ballots array, otherwise add
     the ballot to the validated_ballots array */
    for (int i = 0; i < ballots.size(); i++) {
      if (ballots.get(i).ranking_count() < validation_limit) {
        invalidated_ballots.add(ballots.get(i));
      } else {
        validated_ballots.add(ballots.get(i));
      }
    }

    // After the invalidated ballots have been identified, call invalidate_report() to
    // produce a report of those ballots.
    try {
      invalidate_report((int) validation_limit);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** This method is in charge of creating the "invalidated_ballots.txt" file
   * and output the ballots got invalidated for the election. Each line contains
   * the information about each specific ballot, with the candidate names that
   * were ranked for the ballot.
   * @param invalidation_limit Minimum number of candidates required to be
   *                           ranked for each ballot.
   * @throws IOException If there as an error file creating a file and writing
   *                     to it.
   * @author Amir Jalili (jalil014)
   */
  private void invalidate_report(int validation_limit) throws IOException {
    // create the invalidated report file
    FileWriter invalidated_file = new FileWriter("invalidated_ballots.txt");
    BufferedWriter writer = new BufferedWriter(invalidated_file);

    writer.write("Validated ballots should atleast have " + validation_limit +
                 " candidates ranked." );
    writer.newLine();
    writer.write("Ballots that got invalidated for this election:");
    writer.newLine();
    
    // list each of the invalidated ballots
    for (int i = 0; i < invalidated_ballots.size(); i++) {
      writer.write(i + 1 + ".");
      for (int j = 0; j < invalidated_ballots.get(i).ranking_count(); j++) {
        writer.write(invalidated_ballots.get(i).current_choice().get_name() +
                     ",");
        invalidated_ballots.get(i).increment_rank();
      }
      writer.newLine();
    }

    writer.flush();
    writer.close();
  }
}
