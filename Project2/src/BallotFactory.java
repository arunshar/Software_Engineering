/* BallotFactory.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/

package svs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The class {@link BallotFactory} is responsible for taking input files as a
 * File array from the Main Window and return the arraylist of total number of
 * ballots inside a given list of files. It must be connected to appropriate
 * methods and actions involved in the Main Window. This Class provides an
 * appropriate data structure for which the ballots can be used by different
 * algorithms such as Plurality and STV with Droop Quota for classes
 * {@link PluralityController} and {@link STVDroopController}, respectively.
 *
 * @author Arun Sharma (sharm485)
 */
public class BallotFactory {
  /**
   * Provides the appropriate data structure for loading ballots from the given
   * files. This structure will be shuffled if the shuffle flag is true.
   *
   * @param shuffle indicates whether or not ballots should be shuffled after
   *                loading and initializing them.
   * @param files an array of ballot files to load and initialize ballots from.
   * @param candidates an array of candidates that the ballots being initialized
   *                   will reference.
   * @return an array list of ballots loaded from the given files.
   * @throws IOException if no files can be found.
   */
  public static ArrayList<Ballot> InitBallots(boolean shuffle, File[] files,
                                              Candidate[] candidates)
                                              throws IOException {
    // Initialize Ballot Factory List
    ArrayList<Ballot> total_ballots = new ArrayList<>();
    // Initialize counter for ballot ID
    int counter = 0;

    // Run through each Ballot file
    for (File file : files) {
      // initializing buffer reader to read the file
      BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
      // skip the first line (this should be the header row)
      br.readLine();
      
      String line;
      // run through each ballot inside the file
      while ((line = br.readLine()) != null) {
        String[] row = line.split(",");
        HashMap<Double,Candidate> hmap = new HashMap<>();
  
        // run through each candidate rankings and populate the hashmap
        for (int j = 0; j < row.length; j++) {
          // load information if there is something to load and save in hashmap
          if (!row[j].equals("null") && !row[j].equals("")) {
            // each key is rank and each value is candidate
            hmap.put(Double.valueOf(row[j]),candidates[j]);
          }
        }
        
        // sorting hashmap based on keys
        Map<Double, Candidate> sortedmap = new TreeMap<>(hmap);
        int local_counter = 0;
        Set set = sortedmap.entrySet();
        Iterator iterator = set.iterator();
        
        // iterating through the sorted hashmap and populate sorted candidates
        Candidate[] sortedcandidates = new Candidate[hmap.size()];
        while(iterator.hasNext()) {
          Map.Entry me = (Map.Entry) iterator.next();
          sortedcandidates[local_counter] = (Candidate) me.getValue();
          local_counter++;
        }
        
        // create a ballot using the sorted candidates / id and add it
        Ballot b = new Ballot(sortedcandidates, counter);
        total_ballots.add(b);
        counter++;
      }
    }
    
    // shuffle the loaded ballots if the shuffle flag is set
    if (shuffle) { Collections.shuffle(total_ballots); }
    
    return total_ballots;  // return the list of all of the ballots
  }
  
  /**
   * This method produces an array of candidates using the array of files given
   * to it. That is, it provides the candidates described in the files. Only the
   * first file is checked in this process.
   * 
   * @param files are list of selected files provided by the user from {@link MainWindow}
   * @return the array of candidates in the files.
   * @throws IOException if no files can be found.
   */
  public static Candidate[] InitCandidates(File[] files) throws IOException {
    // read the first row of the first file given to us
    BufferedReader br = new BufferedReader(new FileReader(files[0].getPath()));
    String top_line = br.readLine();
    
    // get the candidates from the top line and split it to get the candidates
    String[] top_line_values = top_line.split(",");
    Candidate[] candidates = new Candidate[top_line_values.length];
    // populate the list of candidates into a structure
    for (int i = 0; i < top_line_values.length; i++) {
      candidates[i] = new Candidate(top_line_values[i], i);
    }
    
    return candidates;
  }
}