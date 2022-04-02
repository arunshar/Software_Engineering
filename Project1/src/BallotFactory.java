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
 * The class {@link BallotFactory} is responsible for taking input files as a File array
 * from the MainWindow and return the arraylist of total number of ballots inside
 * a given list of files. It must be connected to appropriate methods and actions
 * involved in the MainWindow. This Class provides an appropriate data structure for
 * which the ballots can be used by different algorithms such as Plurality and
 * STV with Droop Quota for classes {@link PluralityController} and
 * {@link STVDroopController} respectively.
 *
 * @author Arun Sharma (sharm485)
 */

public class BallotFactory {
  /** The method InitBallots provide appropriate data structure for storing total number
   * of ballots from the files selected by the user.
   *
   * @param shuffle is a boolean argument provided by the user from {@link MainWindow}
   * @param files are list of selected files provided by the user from {@link MainWindow}
   * @param candidates are the list of candidates
   * @return an ArrayList of candidates from
   * @throws IOException if no files can be found.
   */
  public static ArrayList<Ballot> InitBallots(boolean shuffle, File[] files,
                                              Candidate[] candidates) throws IOException {
    // Initialize Ballot Factory List
    ArrayList<Ballot> total_ballots = new ArrayList<>();
    // Initialize counter for ballot ID
    int counter = 0;

    // Run through each Ballot file
    for (int i = 0; i < files.length; i++) {

      // initializing buffer reader to read the file
      BufferedReader br = null;
      String line = "";
      String cvsSplitBy = ",";
      br = new BufferedReader(new FileReader(files[i].getPath()));

      // Skip the first line
      br.readLine();
//      if (i == 0) {
//        br.readLine();
//      }

      // Run through each ballot inside the file
      while ((line = br.readLine()) != null) {
        String[] row = line.split(cvsSplitBy);
        HashMap<Double,Candidate> hmap = new HashMap<>();

        // Run through each candidate rankings and populate the hashmap
        for (int j=0; j < row.length; j++) {
          if (row[j].equals("null") || row[j].equals("")) { // check the null or blanks in the rankings,
            continue; // if yes then dont consider it.
          } else {
            hmap.put(Double.valueOf(row[j]),candidates[j]); // saving rankings inside the hashmap where each key is rank and each value is candidate
          }
        }
        Map<Double,Candidate> sortedmap = new TreeMap<>(hmap); // Sorting hashmap based on Keys
        int local_counter = 0;
        Set set2 = sortedmap.entrySet();
        Iterator iterator2 = set2.iterator();
        Candidate[] sortedcandidates = new Candidate[hmap.size()]; // Initialize the sorted candidate list
        while(iterator2.hasNext()) { // Iterating each entry of sorted hashmap and populate the sorted candidates list
          Map.Entry me2 = (Map.Entry)iterator2.next();
          sortedcandidates[local_counter] = (Candidate) me2.getValue();
          local_counter++;
        }
        Ballot bl = new Ballot(sortedcandidates,counter); // Create a ballot based on sorted candidate and id
        total_ballots.add(bl); // save the sorted candidates inside the ballot factory list.
        counter++;
      }
    }

    // if shuffle is true
    if (shuffle) {
      Collections.shuffle(total_ballots);
    }

    return total_ballots; // Return the ballot factory list for PluralityController and STVDroopController
  }
    /**
   *This method provides number of candidates by pruning first row of the file and 
   * saving candidates in an custom array.
   * 
   * @param files are list of selected files provided by the user from {@link MainWindow}
   * @return number of candidates inside the selected files.
   * @throws IOException if no files can be found.
   */
    

  public static Candidate[] InitCandidates(File[] files) throws IOException {

    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ",";
    br = new BufferedReader(new FileReader(files[0].getPath())); // Take the first file
    String top_line = br.readLine(); // Check the top row of the first file
    String[] top_line_list = top_line.split(cvsSplitBy);
    Candidate[] cd_list = new Candidate[top_line_list.length]; // Initialize the candidate list
    for (int i=0; i < top_line_list.length; i++) { // Populate the candidate list
      cd_list[i] = new Candidate(top_line_list[i],i);
    }
    return cd_list; // return the list for InitBallot method

  }

}