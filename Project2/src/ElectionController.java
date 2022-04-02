/* ElectionController.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/
package svs;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The class {@link ElectionController} is responsible for controlling both PluralityController and
 * STVController elections and must be connected with {@link MainWindow} class. It provides every
 * operation which both voting algorithms must perform in order to get correct results.
 *
 * @author Arun Sharma (sharm485)
 */
public abstract class ElectionController {
 /** This class globally stores ballots and candidates for using both the
   * voting algorithms. Also it resetElection after every election run.
   * @param ballots are an ArrayList of ballots from {@link BallotFactory} class.
   * @param candidates are number of candidate list from {@link BallotFactory} class.
   */
  public ElectionController(ArrayList<Ballot> ballots, Candidate[] candidates) {
    this.ballots = ballots;
    this.candidates = candidates;
    this.invalidated_ballots = new ArrayList<>();
    this.validated_ballots = new ArrayList<>();
    file_msg = new ArrayList<>();
    audit_file = null;
    ResetElection();
  }
  
  /**
   *This class is responsible for running the election for both voting algorithms and
   * return final arraylist of winning and losing candidates. It also output the audit file
   * in the form of text recording every intermediate operation including Distributing and
   * Redistributing the ballots along with the final results.
   * @param num_seats are number of seats provided by the user from {@link MainWindow}
   * @return final sets of elected and non-elected candidates from {@link PluralityController}
   * and {@link STVDroopController} class
   * @throws IOException if there is a failure when trying to write the audit
   *         file.
   */
  public final String RunElection(int num_seats) throws IOException {

	//Check for invalidated ballots
	invalidate_ballots();
	  
    // create audit file
    audit_file = new FileWriter("audit_file.txt");
    file_msg = new ArrayList<>();

    // run the election with the given number of seats
    String results = InterpretResults(ElectionRoutine(num_seats), num_seats);
    CustomAuditFileMsg(results);  // write results to the end of the audit file

    // release the audit file
    BufferedWriter writer = new BufferedWriter(audit_file);
    for (int i=0; i < file_msg.size(); i++) {
      writer.write(file_msg.get(i));
      writer.newLine();
    }
    writer.flush();
    writer.close();
    ResetElection();  // return the data used to its initial state
    
    return results;
  }
  
  /**
   * This method is responsible displaying results for both voting algorithm
   * to the user interface.
   * @param results an array of the candidates ordered from best to worst. What
   *                this means ('best' to 'worst') is contextual and depends on
   *                the election.
   * @param num_seats the number of seats used when determining the election.
   * @return String of results
   * @throws IOException If there as an error while creating a file and writing to it
   */
  protected abstract String InterpretResults(Candidate[] results,
                                             int num_seats) throws IOException ;
  
  /**
   * This method runs the election routine of the code for both voting algorithm
   * @param num_seats are number of seats from the {@link MainWindow} class
   * @return null
   * @throws IOException if there is a failure when trying to write the audit
   *         file.
   */
  protected abstract Candidate[] ElectionRoutine(int num_seats) throws IOException;
  
  /** This method is responsible to invalidate the ballots that have 
  * less than half of the candidates ranked. The validated ballots are 
  * added to the validated_ballots array and invalidated ballots are added
  * to the invalidated_ballots array so that a report of invalidated ballots
  * can be created later.
  * @author Amir Jalili (jalil014)
  */
  protected abstract void invalidate_ballots();
  
  
  /** This method creates a file called "Election_report.txt"
   * This file includes the date, type of election , 
   * the candidates, the number of seats, and the winners.
   * @param num_seats Number of seats in the election
   * @param candidates[], All the candidates
   * @param winner_candidates, Winner candidates in order
   * @author Amir Jalili (jalil014)  
   * @throws IOException IOException If there as an error while creating a file and writing to it
   */
  protected abstract void election_report(int num_seats, Candidate[] candidates, Candidate[] winner_candidates) throws IOException;
  
  /**
   * This method can run distribute a given ballot to a designated candidate
   * @param b is the input ballot which is to be distributed
   * @param c is the candidate where the ballot should go.
   * @throws IOException IOException if there is a failure when trying to write the audit
   *         file.
   */
  protected final void DistributeBallot(Ballot b, Candidate c) throws IOException {
    c.add_ballot(b);
    String msg1 = "Ballot " + b.get_id() + " assigned to " + c.get_name() + "\n";
    CustomAuditFileMsg(msg1);
  }
  
  /**
   * This method can redistribute a given ballots from a source candidate to the destination candidate
   * for STV with Droop Quota Algorithm. It also records the messages of the redistribution operation
   * performed inside.
   * @param b is the given ballot
   * @param src is the source Candidate
   * @param dest is the destination candidate
   * @throws IOException if there is a failure when trying to write the audit
   *         file.
   */
  protected final void RedistributeBallot(Ballot b, Candidate src, Candidate dest) throws IOException {
    //Uncomment if STV is doing pull_ballots
//    src.pull_ballot();
    dest.add_ballot(b);
    String msg1 = "The ballot " + b.get_id() + " is removed from " + src.get_name() + "\n";
    String msg2 = "The ballot " + b.get_id() + " is now assigned to " + dest.get_name() + "\n";
    String msg3 = msg1 + msg2;
    CustomAuditFileMsg(msg3);
  }
  
  /**
   * This method will populate the message from both Distribution and Redistribution methods.
   * @param msg is the String msg from any operation
   * @throws IOException if there is a failure when trying to write the audit
   *         file.
   */
  protected final void CustomAuditFileMsg(String msg) throws IOException {
    file_msg.add(msg);
  }
  
  /**
   * Resets the data given to the election controller to an initial state. This
   * means that the same controller could run the election again with the same
   * data.
   */
  private void ResetElection() {
    // reset all of the ballots
    Iterator<Ballot> it = ballots.iterator();
    while (it.hasNext()) {
      Ballot b = it.next();
      b.reset_current_rank();
    }
    
    // create each of the candidates by clearing their ballot lists
    for (Candidate candidate : candidates) {
      candidate.reset_ballot_list();
    }
  }
  protected ArrayList<Ballot> invalidated_ballots;  
  protected ArrayList<Ballot> validated_ballots;
  protected final ArrayList<Ballot> ballots;
  protected final Candidate[] candidates;
  private FileWriter audit_file;
  private ArrayList<String> file_msg;
}