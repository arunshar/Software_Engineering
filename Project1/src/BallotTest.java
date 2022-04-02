/* BallotTest.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/

package svs;

import static junit.framework.Assert.*;
import org.junit.Test;
import java.util.Random;

/**
 * Unit tests for the methods in {@link Ballot}.
 * 
 * @author Nicholas Lovdahl (lovda015)
 */
public class BallotTest {
  /**
   * Creates Ballots with random IDs and checks if {@link Ballot#get_id()}
   * returns the IDs used.
   */
  @Test
  public void test_get_id() {
    Random rand = new Random();
    Ballot test_ballot;
    int expected_id;
    for (int i = 0; i < 10; i++) {
      expected_id = rand.nextInt();
      test_ballot = new Ballot(null, expected_id);
      assertEquals(expected_id, test_ballot.get_id());
    }
  }

  /**
   * Creates a Ballot and checks that its initial current rank is zero.
   */
  @Test
  public void test_starting_rank() {
    Ballot test_ballot = new Ballot(null, 101);
    assertEquals(0, test_ballot.current_rank());
  }
  
  /**
   * Tests to see that increment rank returns the rank we expect. That is, it
   * should increment by one each time, and -1 when we call increment rank
   * more times than there are rankings.
   */
  @Test
  public void test_increment_rank() {
    Candidate[] candidates = new Candidate[5];
    Ballot test_ballot = new Ballot(candidates, 101);
    
    assertEquals(1, test_ballot.increment_rank());
    assertEquals(2, test_ballot.increment_rank());
    assertEquals(3, test_ballot.increment_rank());
    assertEquals(4, test_ballot.increment_rank());
    assertEquals(-1, test_ballot.increment_rank());
    // calling it again should still return -1
    assertEquals(-1, test_ballot.increment_rank());
    assertEquals(-1, test_ballot.increment_rank());
  }
  
  /**
   * When we increment the rank more times than there are rankings, current rank
   * should return the index of the last element in the array. This should be
   * so even if we keep incrementing the rank.
   */
  @Test
  public void test_ending_rank() {
    Candidate[] candidates = new Candidate[5];
    Ballot test_ballot = new Ballot(candidates, 101);
    
    test_ballot.increment_rank();
    test_ballot.increment_rank();
    test_ballot.increment_rank();
    test_ballot.increment_rank();
    assertEquals(4, test_ballot.current_rank());  // should legitimately be 4
    
    test_ballot.increment_rank();                 // should refuse to advance
    assertEquals(4, test_ballot.current_rank());  // from now on...
    test_ballot.increment_rank();
    test_ballot.increment_rank();
    test_ballot.increment_rank();
    assertEquals(4, test_ballot.current_rank());
  }
  
  /**
   * Tests {@link Ballot#current_rank()} and {@link Ballot#increment_rank()} to
   * see if they work properly together. Getting the current rank should not
   * change the rank, even if called multiple times. Increment rank should only
   * increment the rank by one.
   */
  @Test
  public void test_rank_advancement() {
    Candidate[] candidates = new Candidate[5];
    Ballot test_ballot = new Ballot(candidates, 101);
    
    // call current_rank() multiple times at the start
    assertEquals(0, test_ballot.current_rank());
    assertEquals(0, test_ballot.current_rank());
    assertEquals(0, test_ballot.current_rank());
    // increment the rank and check if the rank advanced one
    test_ballot.increment_rank();
    assertEquals(1, test_ballot.current_rank());
    // calling the current rank should still not changes things
    assertEquals(1, test_ballot.current_rank());
    assertEquals(1, test_ballot.current_rank());
    assertEquals(1, test_ballot.current_rank());
    // we should be able to advance the rank multiple times too
    test_ballot.increment_rank();
    test_ballot.increment_rank();
    test_ballot.increment_rank();
    assertEquals(4, test_ballot.current_rank());
  }

  /**
   * {@link Ballot#reset_current_rank()} should set the rank to zero, whenever
   * it was called.
   */
  @Test
  public void test_reset_current_rank() {
    Candidate[] candidates = new Candidate[5];
    Ballot test_ballot = new Ballot(candidates, 101);
    
    // if we call it right off the bat, it should be 0
    test_ballot.reset_current_rank();
    assertEquals(0, test_ballot.current_rank());
    // if we increment the rank and reset...
    test_ballot.increment_rank();
    test_ballot.increment_rank();
    test_ballot.increment_rank();
    test_ballot.increment_rank();
    test_ballot.reset_current_rank();
    assertEquals(0, test_ballot.current_rank());
    // if we 'advance' it past the number of rankings...
    test_ballot.increment_rank();
    test_ballot.increment_rank();
    test_ballot.increment_rank();
    test_ballot.increment_rank();
    test_ballot.increment_rank();
    test_ballot.increment_rank();
    test_ballot.reset_current_rank();
    assertEquals(0, test_ballot.current_rank());
  }

  /**
   * {@link Ballot#current_choice} should return candidates in the order they
   * were ranked in as we advance the rank. Calling that method multiple
   * times should not change what was returned.
   */
  @Test
  public void test_current_choice() {
    Candidate[] candidates = new Candidate[5];
    Candidate a = new Candidate("A", 0);
    candidates[0] = a;
    Candidate b = new Candidate("B", 1);
    candidates[1] = b;
    Candidate c = new Candidate("C", 2);
    candidates[2] = c;
    Candidate d = new Candidate("D", 3);
    candidates[3] = d;
    Candidate e = new Candidate("E", 4);
    candidates[4] = e;
    Ballot test_ballot = new Ballot(candidates, 101);
    
    // call current_choice() multiple times at the start
    assertSame(a, test_ballot.current_choice());
    assertSame(a, test_ballot.current_choice());
    // call and increment to get different candidates...
    test_ballot.increment_rank();
    assertSame(b, test_ballot.current_choice());
    test_ballot.increment_rank();
    assertSame(c, test_ballot.current_choice());
    test_ballot.increment_rank();
    assertSame(d, test_ballot.current_choice());
    test_ballot.increment_rank();
    // calling current_choice() multiple times at the end...
    assertSame(e, test_ballot.current_choice());
    assertSame(e, test_ballot.current_choice());
  }
}
