/* PluralityController.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/
package svs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/** This class is in charge of running the Plurality Algorithm on the ballots
 * and candidates provided by the user. The class consists of methods that
 * are responsible for implementation of the algorithm and displaying the
 * election results via user interface.
 *
 */

public class PluralityController extends ElectionController {

  /** Constructor for {@link PluralityController} which takes in the data structures
   * generated for the ballots and candidates as a part of the election process.
   * These data structures are passed to its parent class constructor.
   * @param ballots Array of all the ballots.
   * @param candidates Array of all the candidates.
   * @author Amir Jalili (jalil014)
   */
  public PluralityController(ArrayList<Ballot> ballots, Candidate[] candidates) {
    super(ballots, candidates);
  }

  /** The Election Routine method is responsible for running the Plurality Algorithm
    * on the ballots and candidates provided. The method takes in the number of seats
    * that must be assigned as the input argument and finally returns a list of winning
    * and loosing candidates to the Election Controller.
    * @param num_seats Number of seats in the election
    * @throws IOException if no file is selected
    * @return array of all the candidates in winning and losing order.
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
    return  randomize_tied_candidates(results);

  }
  
 
  /** This method iterates over a given number of ballots and distributes the votes
   * based on their current choice of candidate.
   * @throws IOException If there as an error in creating a file and writing to it
   *    while calling the DistributeBallot() method.
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
 
  /** This method iterates through a list candidates and checks
   * if two or more candidates have the same of number of votes.
   * If true, it randomizes their rankings and returns the final
   * election results.
   * @param candidates Array of all the candidates.
   * @return An array of candidates in the final results order
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
  

  /** This method is responsible for shuffling the array of candidates having
   * the same number of votes.
   * @param candidates array of candidates who are tied.
   * @return shuffled array of tied candidates 
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
   * @param results The array of candidates in the order of results
   * @param num_seats Number of seats in the election
   * @return A string containing all the information about the results, Winners and losers lists and the election details.
   * @author Amir Jalili (jalil014)
   * @throws IOException If there as an error while creating a file and writing to it
   */
  @Override
  public String InterpretResults(Candidate[] results, int num_seats) throws IOException {
	  Candidate[]  winner_candidates = new Candidate[num_seats];
    // Display the election type, total number of ballots, number of candidates and number of seats.
    String res = "Election Type: Plurality\nNumber of ballots: " + ballots.size() + "\n"
            + "Number of seats: " + num_seats + "\n" + "Number of candidates: " + candidates.length + "\n\n";
    res += "Winners:\n";

    // Iterate through the results array and print the winner candidates and their stats
    for(int i=0; i<num_seats; i++) {
        if (!ballots.isEmpty()) {
            double percent = results[results.length-(i+1)].get_num_ballots() * 100  / ballots.size() ;
            String str = i+1 + ".Candidate: " + results[results.length-(i+1)].get_name() + ", Votes: " + results[results.length-(i+1)].get_num_ballots() + ", Percentage of votes: " + percent + "%" +"\n";
            res += str;
            winner_candidates[i] = results[results.length-(i+1)];
        }
        else {
            String str = i+1 + ".Candidate: " + results[results.length-(i+1)].get_name() + ", Votes: " + results[results.length-(i+1)].get_num_ballots() + ", Percentage of votes: " + "0" + "%" +"\n";
            res += str;
            winner_candidates[i] = results[results.length-(i+1)];
        }

    }
    res += "\nLosers:\n";

    // Iterate through the results array and print the loser candidates and their stats
    for(int i=num_seats; i<results.length; i++) {
        if (!ballots.isEmpty()) {
            double percent = results[results.length-(i+1)].get_num_ballots() * 100/ ballots.size() ;
            String str = i+1 + ".Candidate: " + results[results.length-(i+1)].get_name() + ", Votes: " + results[results.length-(i+1)].get_num_ballots() + ", Percentage of votes: " + percent + "%" +"\n";
            res += str;
        }
        else {
            String str = i+1 + ".Candidate: " + results[results.length-(i+1)].get_name() + ", Votes: " + results[results.length-(i+1)].get_num_ballots() + ", Percentage of votes: " + "0" + "%" +"\n";
            res += str;
        }

    }
    election_report(num_seats, results, winner_candidates);
    return res;
  }
 
  /** This method creates a file called "Election_report.txt"
   * This file includes the date, type of election , 
   * the candidates, the number of seats, and the winners.
   * @param num_seats Number of seats in the election
   * @param candidates, All the candidates
   * @param winner_candidates, Winner candidates in order
   * @author Amir Jalili (jalil014)  
   * @throws IOException If there as an error while creating a file and writing to it
   */
  @Override
  public void election_report(int num_seats, Candidate[] candidates, Candidate[] winner_candidates) throws IOException {
		// create the election report file
      FileWriter report_file = new FileWriter("Election_report.txt");
      BufferedWriter writer = new BufferedWriter(report_file);

      writer.write("ELECTION REPORT\n");
      writer.write("Date: " +  java.time.LocalDate.now() + "\n");
      writer.write("Election type: Plurality\n");
      writer.write("Number of seats: " + num_seats + "\n");
      writer.write("All canidates:\n");
      for(int i =0; i< candidates.length; i++) {
    	  writer.write(candidates[i].get_name()+", ");
      }
      writer.newLine();
      writer.newLine();
      writer.write("Winners:\n");
      for(int i =0; i< winner_candidates.length; i++) {
    	  writer.write( i+1 + "." + winner_candidates[i].get_name() + " with " + winner_candidates[i].get_num_ballots() + " votes.\n");
      }      
      
          
      writer.flush();
      writer.close();
  }
  

  
  /** This method simply returns the name of the election to other subsystems
   * @return The string containing the election name
   * @author Amir Jalili (jalil014)
   */
  public static String ElectionName() { return "Plurality"; }
  
  /** Since invalidating ballots is only done for STV voting system, this
   * method has no affect on any of the ballots. 
   * @author Amir Jalili (jalil014)
   */
  public void invalidate_ballots() {
	  return;
  }
}
