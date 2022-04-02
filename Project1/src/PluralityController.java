/* PluralityController.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/
package svs;

import java.io.IOException;
import java.util.*;

/** This class is in charge of running the Plurality Algorithm on the ballots
 * and candidates that the user provides. The class consists of a method that
 * actually implements the algorithm, and a method that displays the election
 * results to the winner through the user interface
 *
 */

public class PluralityController extends ElectionController {

  /** The constructor for this class takes in the data structures generated for 
  * the ballots and candidates that are a part of the election. These data 
  * structures are passed to its parent class constructor.
  * @param  ballots
  * @param  candidates
  * @author Amir Jalili (jalil014)   
  */
  public PluralityController(ArrayList<Ballot> ballots, Candidate[] candidates) {
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
  * @author Amir Jalili (jalil014)  
  */
 
  @Override
  public Candidate[] ElectionRoutine(int num_seats) throws IOException {
	ArrayList<Candidate> winner_candidates = new ArrayList<Candidate>();

    // Iterate through the ballots and distribute the votes to the candidates
    distribute_votes();

    // Add the candidates to the ArrayList winner_candidates
    for (int i=0; i <candidates.length; i++ ){
    	winner_candidates.add(candidates[i]);
    }

    // Sort the candidates based on the number of votes.
    Collections.sort(winner_candidates);
    
    // Final results array
    Candidate[]  results = new Candidate[candidates.length];
    

    Iterator<Candidate> it_can = winner_candidates.iterator();
    int j=0;
    while (it_can.hasNext()) {
    	Candidate bb = it_can.next();
    	results[j] = bb; 
    	j++;
    }
    
    // Shuffle the candidates order, if there is a tie in the votes randomly rank those candidates: 
    return randomize_tied_candidates(results);
  }
  
 
  /** This method Iterates through the ballots and distributes the votes 
  * to each candidate.
  * @throws IOException If there as an error file creating a file and writing to it
  * 		while calling the DistributeBallot() method.
  * @author Amir Jalili (jalil014)  
  */    
  private void distribute_votes() throws IOException {
	    Iterator<Ballot> it = ballots.iterator();
	    while (it.hasNext()) {
	      Ballot b = it.next();
	      Candidate SelectedCandidate = b.current_choice();
	      if(SelectedCandidate != null) {
	    	  DistributeBallot(b, SelectedCandidate);
	      }
	    }
  }
 
  /** This method goes through the candidates, if two or more candidates are tied
   *  and have the same of votes, it randomizes their rankings and returns the final
   *  election results.
   * @param candidates[]
   * @return candidates[] 
   * @author Amir Jalili (jalil014)  
   */
  private  Candidate[] randomize_tied_candidates(Candidate[] candidates) {

	    // Shuffle the candidates order, if there is a tie in the votes randomly rank those candidates: 
	    for (int j=0; j< candidates.length - 1; j++) {
	    	int temp = candidates[j].get_num_ballots();
	    	int temp_index = j;
	    	int counter = 1;
	      // If candidates have the same number of ballots, increment counter and temp index
	    	while (candidates[temp_index+1].get_num_ballots() == temp) {
	    		counter++;
	    		temp_index++;
	    		if(temp_index >= candidates.length - 2) {
	    			break;
	    		}
	    	}
	 
	      // If counter is more than one, there is a tie and the candidates are ranked randomly
	    	if (counter > 1) {
	    		Candidate[]  temp_candidates = new Candidate[counter];
	    		temp_index = j;
	    		for (int i=0; i< counter; i++) {
	    			temp_candidates[i] = candidates[temp_index]; 
	    			temp_index++;
	    		}
	    		temp_candidates = shuffle_candidates(temp_candidates);
	    		for (int i=0; i< temp_candidates.length; i++) {
	    			candidates[j] = temp_candidates[i]; 
	    			j++;
	    		}
	    	}
	    }
	  
	  return candidates;
  }
  

  /** This method is to shuffle the array of candidates who have the same votes.
   * @param candidates
   * @return candidates 
   * @author Amir Jalili (jalil014)  
   */
  private  Candidate[] shuffle_candidates(Candidate[] candidates ) {
    Random rgen = new Random(); 			
      
    for (int i=0; i<candidates.length; i++) {
        int randomPosition = rgen.nextInt(candidates.length);
        Candidate temp = candidates[i];
        candidates[i] = candidates[randomPosition];
        candidates[randomPosition] = temp;
      }
    
      return candidates;  
  }
  

  /** This method is in charge of writing election results to the screen. The
   * results include the election type, number of seats, number of ballots,
   * number of candidates, and the lists of the winners and losers.
   * @param results
   * @param num_seats
   * @return res
   * @author Amir Jalili (jalil014)  
   */
  @Override
  public String InterpretResults(Candidate[] results, int num_seats) {
    // Display the election type, total number of ballots, number of candidates and number of seats.
    String res = "Election Type: Plurality\nNumber of ballots: " + ballots.size() + "\n"
            + "Number of seats: " + num_seats + "\n" + "Number of candidates: " + candidates.length + "\n\n";
    res += "Winners:\n";

    // Iterate through the results array and print the winner candidates and their stats
    for(int i=0; i<num_seats; i++) {
    	double percent = results[results.length-(i+1)].get_num_ballots() * 100  / ballots.size() ;
    	String str = i+1 + ".Candidate: " + results[results.length-(i+1)].get_name() + ", Votes: " + results[results.length-(i+1)].get_num_ballots() + ", Percentage of votes: " + percent + "%" +"\n"; 
    	res += str;
    }
    res += "\nLosers:\n";

    // Iterate through the results array and print the loser candidates and their stats
    for(int i=num_seats; i<results.length; i++) {
    	double percent = results[results.length-(i+1)].get_num_ballots() * 100/ ballots.size() ;
        String str = i+1 + ".Candidate: " + results[results.length-(i+1)].get_name() + ", Votes: " + results[results.length-(i+1)].get_num_ballots() + ", Percentage of votes: " + percent + "%" +"\n"; 
        res += str;
    }
    return res;
  }
  

  
  /** This method simply returns the name of the election to other subsystems
   * that way want this information
   * @return the string containing the election name
   * @author Amir Jalili (jalil014)
   */
  public static String ElectionName() { return "Plurality"; }
  
  /** Since invalidating ballots is only done for STV voting system, this
   * method has no affect on any of the ballots. 
   * @return 
   * @author Amir Jalili (jalil014)
   */
  public void invalidate_ballots() {
	  return;
  }
}
