/* BallotFactoryTest.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/

package svs;

import org.junit.Test;
import static junit.framework.Assert.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The {@link BallotFactory} BallotFactoryTest is the unit test for each function
 * inside the {@link BallotFactory} class.
 *
 * @author Arun Sharma (sharm485)
 */

public class BallotFactoryTest {
  /**
   * Test {@link BallotFactory#InitBallots(boolean, File[], Candidate[])} by
   * first verifying if the  ballot size matches the given input test files.
   * Furthermore, it checks the contents of the first ballot by comparing their
   * respective IDs with the given ballots and their respective current choice
   * candidates. Note: if shuffle is true then this test will fail.
   * 
   * @throws IOException if the test file cannot be found.
   */
  @Test
  public void initBallots() throws IOException {
    boolean shuffle = false;
    File test_file = new File("testing/test.csv");
    ArrayList<File> file_list = new ArrayList<>();
    file_list.add(test_file);
    File[] file = file_list.toArray(new File[file_list.size()]);
    Candidate[] candidates = BallotFactory.InitCandidates(file);
    ArrayList<Ballot> total_ballots = BallotFactory.InitBallots(shuffle, file,
                                                                candidates);
    assertEquals(30,total_ballots.size());
    assertEquals(0,total_ballots.get(0).get_id());
    assertEquals("A",total_ballots.get(0).current_choice().get_name());
  }

 /**
  * Test {@link BallotFactory#InitBallots(boolean, File[], Candidate[])} by first initializing
  * candidate list with ballots and checks whether the shuffle operations is successfully
  * performed in order to have a fair elections.
  * 
  * @throws IOException if the test file cannot be found.
  */
  @Test
  public void shuffleBallots() throws IOException {
      boolean shuffle = false;
      File test_file = new File("testing/test.csv");
      ArrayList<File> file_list = new ArrayList<>();
      file_list.add(test_file);
      File[] file = file_list.toArray(new File[file_list.size()]);
      Candidate[] candidates = BallotFactory.InitCandidates(file);
      ArrayList<Ballot> total_ballots = BallotFactory.InitBallots(shuffle, file, candidates);
      boolean shuffle_1 = true;
      ArrayList<Ballot> total_ballots_1 = BallotFactory.InitBallots(shuffle_1, file, candidates);
      assertNotSame(total_ballots.get(0),total_ballots_1.get(0));
    }
  
  /**
   * Test {@link BallotFactory#InitCandidates(File[])} by first verifying the
   * total number of candidates populated with a given name and then further
   * compares each candidate name with it's corresponding ID with a given set of
   * inputs to the candidate list.
   * 
   * @throws IOException if the test file cannot be found.
   */
  @Test
  public void initCandidates() throws IOException {
    File test_file = new File("testing/test.csv");
    ArrayList<File> file_list = new ArrayList<>();
    file_list.add(test_file);
    File[] file = file_list.toArray(new File[file_list.size()]);
    Candidate[] candidates = BallotFactory.InitCandidates(file);
    assertEquals(5,candidates.length);
    assertEquals("A",candidates[0].get_name());
    assertEquals(0,candidates[0].get_id());
    assertEquals("B",candidates[1].get_name());
    assertEquals(1,candidates[1].get_id());
    assertEquals("C",candidates[2].get_name());
    assertEquals(2,candidates[2].get_id());
    assertEquals("D",candidates[3].get_name());
    assertEquals(3,candidates[3].get_id());
    assertEquals("E",candidates[4].get_name());
    assertEquals(4,candidates[4].get_id());
  }
}