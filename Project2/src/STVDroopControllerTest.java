/* STVDroopControllerTest.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oneil569)
*/

package svs;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

/**
 * Unit tests for the STVDroopController.
 * 
 * @author Taylor O'Neill (oneil569)
 */
public class STVDroopControllerTest {

  @Test
  /**
   * This unit test is responsible for testing
   * {@link STVDroopController#ElectionRoutine(int)} method without crashing and
   * giving correct results for an election with 5 candidates, 15 ballots, and
   * number of seats ranging from 1 to 5
   *
   * @throws IOException
   */
  public void Test15BallotsandVaryingSeats() throws IOException {
    boolean shuffle = false;
    File test_file = new File("testing/testSTV5C15B.csv");
    ArrayList<File> file_list = new ArrayList<>();
    file_list.add(test_file);
    File[] file = file_list.toArray(new File[file_list.size()]);
    Candidate[] candidates = BallotFactory.InitCandidates(file);

    ArrayList<Ballot> ballots1 = BallotFactory.InitBallots(shuffle, file, candidates);
    ElectionController controller1 = new STVDroopController(ballots1, candidates);
    controller1.invalidate_ballots();
    Candidate[] results1 = controller1.ElectionRoutine(1);
    boolean val1 = true;
    if (results1.length <= 0) {
      val1 = false;
    }
    assertEquals(candidates[3], results1[0]);
    assertEquals(true, val1);
    String message1 = controller1.InterpretResults(results1, 1);
    System.out.println('\n' + message1);

    ArrayList<Ballot> ballots2 = BallotFactory.InitBallots(shuffle, file, candidates);
    ElectionController controller2 = new STVDroopController(ballots2, candidates);
    controller2.invalidate_ballots();
    Candidate[] results2 = controller2.ElectionRoutine(2);
    boolean val2 = true;
    if (results2.length <= 0) {
      val2 = false;
    }
    assertEquals(candidates[3], results2[0]);
    assertEquals(true, val2);
    String message2 = controller2.InterpretResults(results2, 2);
    System.out.println('\n' + message2);

    ArrayList<Ballot> ballots3 = BallotFactory.InitBallots(shuffle, file, candidates);
    ElectionController controller3 = new STVDroopController(ballots3, candidates);
    controller3.invalidate_ballots();
    Candidate[] results3 = controller3.ElectionRoutine(3);
    boolean val3 = true;
    if (results3.length <= 0) {
      val3 = false;
    }
    assertEquals(candidates[3], results3[0]);
    assertEquals(true, val3);
    String message3 = controller3.InterpretResults(results3, 3);
    System.out.println('\n' + message3);

    ArrayList<Ballot> ballots4 = BallotFactory.InitBallots(shuffle, file, candidates);
    ElectionController controller4 = new STVDroopController(ballots4, candidates);
    controller4.invalidate_ballots();
    Candidate[] results4 = controller4.ElectionRoutine(4);
    boolean val4 = true;
    if (results4.length <= 0) {
      val4 = false;
    }
    assertEquals(candidates[3], results4[0]);
    assertEquals(true, val4);
    String message4 = controller4.InterpretResults(results4, 4);
    System.out.println('\n' + message4);

    ArrayList<Ballot> ballots5 = BallotFactory.InitBallots(shuffle, file, candidates);
    ElectionController controller5 = new STVDroopController(ballots5, candidates);
    controller5.invalidate_ballots();
    Candidate[] results5 = controller5.ElectionRoutine(5);
    boolean val5 = true;
    if (results5.length <= 0) {
      val5 = false;
    }
    assertEquals(candidates[4], results5[0]);
    assertEquals(true, val5);
    String message5 = controller5.InterpretResults(results5, 5);
    System.out.println('\n' + message5);
  }

  @Test
  /**
   * This unit test is responsible for testing
   * {@link STVDroopController#ElectionRoutine(int)} method without crashing and
   * giving correct results for an election with only one candidate
   *
   * @throws IOException
   */
  public void TestOneCandidate() throws IOException {
    boolean shuffle = false;
    File test_file = new File("testing/testSTVonecandidate.csv");
    ArrayList<File> file_list = new ArrayList<>();
    file_list.add(test_file);
    File[] file = file_list.toArray(new File[file_list.size()]);
    Candidate[] candidates = BallotFactory.InitCandidates(file);
    ArrayList<Ballot> ballots = BallotFactory.InitBallots(shuffle, file, candidates);
    ElectionController controller = new STVDroopController(ballots, candidates);
    controller.invalidate_ballots();
    Candidate[] results = controller.ElectionRoutine(1);
    boolean val = true;
    if (results.length <= 0) {
      val = false;
    }
    assertEquals(candidates[0], results[0]);

    assertEquals(true, val);
    String message = controller.InterpretResults(results, 1);
    System.out.println('\n' + message);
  }

  @Test
  /**
   * This unit test is responsible for testing
   * {@link STVDroopController#ElectionRoutine(int)} method without crashing and
   * giving correct results for an election with 100,000 ballots
   *
   * @throws IOException
   */
  public void TestOneHundredThousandBallots() throws IOException {
    boolean shuffle = false;
    File test_file = new File("testing/testSTV100000ballots.csv");
    ArrayList<File> file_list = new ArrayList<>();
    file_list.add(test_file);
    File[] file = file_list.toArray(new File[file_list.size()]);
    Candidate[] candidates = BallotFactory.InitCandidates(file);
    ArrayList<Ballot> ballots = BallotFactory.InitBallots(shuffle, file, candidates);
    ElectionController controller = new STVDroopController(ballots, candidates);
    controller.invalidate_ballots();
    Candidate[] results = controller.ElectionRoutine(2);
    boolean val = true;
    if (results.length <= 0) {
      val = false;
    }
    assertEquals(candidates[0], results[0]);
    assertEquals(candidates[1], results[1]);

    assertEquals(true, val);
    String message = controller.InterpretResults(results, 2);
    System.out.println('\n' + message);
  }

  @Test
  /**
   * This unit test is responsible for testing
   * {@link STVDroopController#ElectionRoutine(int)} method without crashing and
   * giving correct results for an election with 0 ballots
   *
   * @throws IOException
   */
  public void TestNoBallot() throws IOException {
    boolean shuffle = false;
    File test_file = new File("testing/testSTVnoballot.csv");
    ArrayList<File> file_list = new ArrayList<>();
    file_list.add(test_file);
    File[] file = file_list.toArray(new File[file_list.size()]);
    Candidate[] candidates = BallotFactory.InitCandidates(file);
    ArrayList<Ballot> ballots = BallotFactory.InitBallots(shuffle, file, candidates);
    ElectionController controller = new STVDroopController(ballots, candidates);
    controller.invalidate_ballots();
    Candidate[] results = controller.ElectionRoutine(2);
    boolean val = true;
    if (results.length <= 0) {
      val = false;
    }
    assertEquals(0, results[0].get_num_ballots());
    assertEquals(0, results[1].get_num_ballots());
    assertEquals(0, results[2].get_num_ballots());
    assertEquals(0, results[3].get_num_ballots());
    assertEquals(0, results[4].get_num_ballots());

    assertEquals(true, val);
    String message = controller.InterpretResults(results, 4);
    System.out.println('\n' + message);
  }

  @Test
  /**
   * This unit test is responsible for testing
   * {@link STVDroopController#ElectionRoutine(int)} method without crashing and
   * giving correct results for an election with 10 candidates
   *
   * @throws IOException
   */
  public void TestTenCandidates() throws IOException {
    boolean shuffle = false;
    File test_file = new File("testing/testSTV10.csv");
    ArrayList<File> file_list = new ArrayList<>();
    file_list.add(test_file);
    File[] file = file_list.toArray(new File[file_list.size()]);
    Candidate[] candidates = BallotFactory.InitCandidates(file);
    ArrayList<Ballot> ballots = BallotFactory.InitBallots(shuffle, file, candidates);
    ElectionController controller = new STVDroopController(ballots, candidates);
    controller.invalidate_ballots();
    Candidate[] results = controller.ElectionRoutine(2);
    boolean val = true;
    if (results.length <= 0) {
      val = false;
    }
    assertEquals(candidates[0], results[0]);
    assertEquals(candidates[1], results[1]);

    assertEquals(true, val);
    String message = controller.InterpretResults(results, 2);
    System.out.println('\n' + message);
  }

  /**
   * This unit test is responsible for testing
   * {@link STVDroopController#ElectionRoutine(int)} method without crashing and
   * giving correct results for an election where there are 2 winners, and a
   * two-way tie between loser candidates. In this case, candidate "C" should be
   * put on the losers first This unit test is also responsible for testing
   * {@link STVDroopController#InterpretResults(int)} method.
   *
   * @throws IOException
   */
  @Test
  public void testElectionRoutine_TwoWinnersC() throws Exception {
    Candidate c1 = new Candidate("A", 1);
    Candidate c2 = new Candidate("B", 2);
    Candidate c3 = new Candidate("C", 3);
    Candidate c4 = new Candidate("D", 4);
    Candidate c5 = new Candidate("E", 5);

    Candidate[] b1 = new Candidate[5];
    b1[0] = c5;
    b1[1] = c4;
    b1[2] = c1;
    b1[3] = c3;
    b1[4] = c2;

    Candidate[] b2 = new Candidate[5];
    b2[0] = c1;
    b2[1] = c3;
    b2[2] = c5;
    b2[3] = c4;
    b2[4] = c2;

    Candidate[] b3 = new Candidate[5];
    b3[0] = c2;
    b3[1] = c5;
    b3[2] = c4;
    b3[3] = c3;
    b3[4] = c1;

    Candidate[] b4 = new Candidate[5];
    b4[0] = c5;
    b4[1] = c2;
    b4[2] = c3;
    b4[3] = c1;
    b4[4] = c4;

    Candidate[] b5 = new Candidate[5];
    b5[0] = c5;
    b5[1] = c4;
    b5[2] = c2;
    b5[3] = c3;
    b5[4] = c1;

    Candidate[] b6 = new Candidate[5];
    b6[0] = c1;
    b6[1] = c5;
    b6[2] = c3;
    b6[3] = c4;
    b6[4] = c2;

    Candidate[] b7 = new Candidate[5];
    b7[0] = c4;
    b7[1] = c3;
    b7[2] = c5;
    b7[3] = c2;
    b7[4] = c1;

    Candidate[] b8 = new Candidate[5];
    b8[0] = c4;
    b8[1] = c5;
    b8[2] = c2;
    b8[3] = c3;
    b8[4] = c1;

    Candidate[] b9 = new Candidate[5];
    b9[0] = c4;
    b9[1] = c1;
    b9[2] = c3;
    b9[3] = c2;
    b9[4] = c5;

    Candidate[] b10 = new Candidate[5];
    b10[0] = c4;
    b10[1] = c2;
    b10[2] = c5;
    b10[3] = c3;
    b10[4] = c1;

    Candidate[] b11 = new Candidate[5];
    b11[0] = c4;
    b11[1] = c2;
    b11[2] = c3;
    b11[3] = c5;
    b11[4] = c1;

    Candidate[] b12 = new Candidate[5];
    b12[0] = c3;
    b12[1] = c2;
    b12[2] = c5;
    b12[3] = c1;
    b12[4] = c4;

    Candidate[] b13 = new Candidate[5];
    b13[0] = c4;
    b13[1] = c2;
    b13[2] = c3;
    b13[3] = c5;
    b13[4] = c1;

    Candidate[] b14 = new Candidate[5];
    b14[0] = c1;
    b14[1] = c3;
    b14[2] = c4;
    b14[3] = c2;
    b14[4] = c5;

    Candidate[] b15 = new Candidate[5];
    b15[0] = c1;
    b15[1] = c2;
    b15[2] = c4;
    b15[3] = c5;
    b15[4] = c3;

    Ballot ballot1 = new Ballot(b1, 1);
    Ballot ballot2 = new Ballot(b2, 2);
    Ballot ballot3 = new Ballot(b3, 3);
    Ballot ballot4 = new Ballot(b4, 4);
    Ballot ballot5 = new Ballot(b5, 5);
    Ballot ballot6 = new Ballot(b6, 6);
    Ballot ballot7 = new Ballot(b7, 7);
    Ballot ballot8 = new Ballot(b8, 8);
    Ballot ballot9 = new Ballot(b9, 9);
    Ballot ballot10 = new Ballot(b10, 10);
    Ballot ballot11 = new Ballot(b11, 11);
    Ballot ballot12 = new Ballot(b12, 12);
    Ballot ballot13 = new Ballot(b13, 13);
    Ballot ballot14 = new Ballot(b14, 14);
    Ballot ballot15 = new Ballot(b15, 15);

    ArrayList<Ballot> ballotTest = new ArrayList<>();
    ballotTest.add(ballot1);
    ballotTest.add(ballot2);
    ballotTest.add(ballot3);
    ballotTest.add(ballot4);
    ballotTest.add(ballot5);
    ballotTest.add(ballot6);
    ballotTest.add(ballot7);
    ballotTest.add(ballot8);
    ballotTest.add(ballot9);
    ballotTest.add(ballot10);
    ballotTest.add(ballot11);
    ballotTest.add(ballot12);
    ballotTest.add(ballot13);
    ballotTest.add(ballot14);
    ballotTest.add(ballot15);

    Candidate[] candidateTest = new Candidate[5];
    candidateTest[0] = c1;
    candidateTest[1] = c2;
    candidateTest[2] = c3;
    candidateTest[3] = c4;
    candidateTest[4] = c5;

    int num_seats = 2;
    STVDroopController instance = new STVDroopController(ballotTest, candidateTest);
    instance.invalidate_ballots();
    Candidate[] result = instance.ElectionRoutine(num_seats);

    Candidate[] expResult = new Candidate[5];
    expResult[0] = c4;
    expResult[1] = c5;
    expResult[2] = c1;
    expResult[3] = c2;
    expResult[4] = c3;

    assertArrayEquals(expResult, result);
    String resultString = instance.InterpretResults(result, num_seats);
    String expString = "Election Type: Droop Quota\nNumber of ballots: 15\nNumber of seats: "
            + "2\nNumber of candidates: 5\nWinners (in order):\nD\nE\n"
            + "Losers (from most recent to first):\nA\nB\nC\n";
    assertEquals(expString, resultString);
  }

  /**
   * This unit test is responsible for testing
   * {@link STVDroopController#ElectionRoutine(int)} method without crashing and
   * giving correct results for an election where there are 2 winner, and a
   * two-way tie between loser candidates. In this case, candidate "B" should be
   * put on the losers first This unit test is also responsible for testing
   * {@link STVDroopController#InterpretResults(int)} method.
   *
   * @throws IOException
   */
  @Test
  public void testElectionRoutine_TwoWinnersB() throws Exception {
    Candidate c1 = new Candidate("A", 1);
    Candidate c2 = new Candidate("B", 2);
    Candidate c3 = new Candidate("C", 3);
    Candidate c4 = new Candidate("D", 4);
    Candidate c5 = new Candidate("E", 5);

    Candidate[] b1 = new Candidate[5];
    b1[0] = c5;
    b1[1] = c4;
    b1[2] = c1;
    b1[3] = c3;
    b1[4] = c2;

    Candidate[] b2 = new Candidate[5];
    b2[0] = c1;
    b2[1] = c3;
    b2[2] = c5;
    b2[3] = c4;
    b2[4] = c2;

    Candidate[] b3 = new Candidate[5];
    b3[0] = c2;
    b3[1] = c5;
    b3[2] = c4;
    b3[3] = c3;
    b3[4] = c1;

    Candidate[] b4 = new Candidate[5];
    b4[0] = c5;
    b4[1] = c2;
    b4[2] = c3;
    b4[3] = c1;
    b4[4] = c4;

    Candidate[] b5 = new Candidate[5];
    b5[0] = c5;
    b5[1] = c4;
    b5[2] = c2;
    b5[3] = c3;
    b5[4] = c1;

    Candidate[] b6 = new Candidate[5];
    b6[0] = c1;
    b6[1] = c5;
    b6[2] = c3;
    b6[3] = c4;
    b6[4] = c2;

    Candidate[] b7 = new Candidate[5];
    b7[0] = c4;
    b7[1] = c3;
    b7[2] = c5;
    b7[3] = c2;
    b7[4] = c1;

    Candidate[] b8 = new Candidate[5];
    b8[0] = c4;
    b8[1] = c5;
    b8[2] = c2;
    b8[3] = c3;
    b8[4] = c1;

    Candidate[] b9 = new Candidate[5];
    b9[0] = c4;
    b9[1] = c1;
    b9[2] = c3;
    b9[3] = c2;
    b9[4] = c5;

    Candidate[] b10 = new Candidate[5];
    b10[0] = c4;
    b10[1] = c2;
    b10[2] = c5;
    b10[3] = c3;
    b10[4] = c1;

    Candidate[] b11 = new Candidate[5];
    b11[0] = c4;
    b11[1] = c2;
    b11[2] = c3;
    b11[3] = c5;
    b11[4] = c1;

    Candidate[] b12 = new Candidate[5];
    b12[0] = c3;
    b12[1] = c2;
    b12[2] = c5;
    b12[3] = c1;
    b12[4] = c4;

    Candidate[] b13 = new Candidate[5];
    b13[0] = c4;
    b13[1] = c2;
    b13[2] = c3;
    b13[3] = c5;
    b13[4] = c1;

    Candidate[] b14 = new Candidate[5];
    b14[0] = c1;
    b14[1] = c3;
    b14[2] = c4;
    b14[3] = c2;
    b14[4] = c5;

    Candidate[] b15 = new Candidate[5];
    b15[0] = c1;
    b15[1] = c2;
    b15[2] = c4;
    b15[3] = c5;
    b15[4] = c3;

    Ballot ballot1 = new Ballot(b1, 1);
    Ballot ballot2 = new Ballot(b2, 2);
    Ballot ballot3 = new Ballot(b3, 3);
    Ballot ballot4 = new Ballot(b4, 4);
    Ballot ballot5 = new Ballot(b5, 5);
    Ballot ballot6 = new Ballot(b6, 6);
    Ballot ballot7 = new Ballot(b7, 7);
    Ballot ballot8 = new Ballot(b8, 8);
    Ballot ballot9 = new Ballot(b9, 9);
    Ballot ballot10 = new Ballot(b10, 10);
    Ballot ballot11 = new Ballot(b11, 11);
    Ballot ballot12 = new Ballot(b12, 12);
    Ballot ballot13 = new Ballot(b13, 13);
    Ballot ballot14 = new Ballot(b14, 14);
    Ballot ballot15 = new Ballot(b15, 15);

    ArrayList<Ballot> ballotTest = new ArrayList<>();
    ballotTest.add(ballot1);
    ballotTest.add(ballot2);
    ballotTest.add(ballot4);
    ballotTest.add(ballot5);
    ballotTest.add(ballot6);
    ballotTest.add(ballot7);
    ballotTest.add(ballot8);
    ballotTest.add(ballot9);
    ballotTest.add(ballot10);
    ballotTest.add(ballot11);
    ballotTest.add(ballot12);
    ballotTest.add(ballot13);
    ballotTest.add(ballot14);
    ballotTest.add(ballot15);
    ballotTest.add(ballot3);

    Candidate[] candidateTest = new Candidate[5];
    candidateTest[0] = c1;
    candidateTest[1] = c2;
    candidateTest[2] = c3;
    candidateTest[3] = c4;
    candidateTest[4] = c5;

    int num_seats = 2;
    STVDroopController instance = new STVDroopController(ballotTest, candidateTest);
    instance.invalidate_ballots();
    Candidate[] result = instance.ElectionRoutine(num_seats);

    Candidate[] expResult = new Candidate[5];
    expResult[0] = c4;
    expResult[1] = c5;
    expResult[2] = c1;
    expResult[3] = c3;
    expResult[4] = c2;

    assertArrayEquals(expResult, result);
    String resultString = instance.InterpretResults(result, num_seats);
    String expString = "Election Type: Droop Quota\nNumber of ballots: 15\nNumber of seats: "
            + "2\nNumber of candidates: 5\nWinners (in order):\nD\nE\n"
            + "Losers (from most recent to first):\nA\nC\nB\n";
    assertEquals(expString, resultString);
  }

  /**
   * This unit test is responsible for testing
   * {@link STVDroopController#ElectionName()} method without crashing.
   */
  @Test
  public void testElectionName() {
    System.out.println("\nElectionName Test");
    String expResult = "STV with Droop Quota";
    String result = STVDroopController.ElectionName();
    assertEquals(expResult, result);
  }

}
