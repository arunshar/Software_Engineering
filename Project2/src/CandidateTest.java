/* CandidateTest.java
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
 * Unit tests for the methods in {@link Candidate}.
 *
 * @author Arun Sharma (sharm485)
 */

public class CandidateTest {
    /**
     * Test {@link Candidate#get_name()} by creating new candidates with
     * some given name with their respective ballot ids and verifies each
     * candidate name with the given input.
     */
    @Test
    public void test_get_name() {
        Candidate[] candidates = new Candidate[5];
        candidates[0] = new Candidate("A",0);
        candidates[1] = new Candidate("B",1);
        candidates[2] = new Candidate("C",2);
        candidates[3] = new Candidate("D",3);
        candidates[4] = new Candidate("E",4);

        assertEquals("A",candidates[0].get_name());
        assertEquals("B",candidates[1].get_name());
        assertEquals("C",candidates[2].get_name());
        assertEquals("D",candidates[3].get_name());
        assertEquals("E",candidates[4].get_name());
    }

    /**
     * Test {@link Candidate#get_id()} by creating candidates with random IDs
     * and checks the given input with their respective return values.
     */
    @Test
    public void test_get_id() {
        Candidate[] candidates = new Candidate[5];
        Random rand = new Random();
        int expected_id;
        for (int i=0; i < candidates.length; i++) {
            expected_id = rand.nextInt();
            candidates[i] = new Candidate(null,expected_id);
            assertEquals(expected_id,candidates[i].get_id());
        }

    }

    @Test
    /**
     * Test {@link Candidate#get_id()} creating candidates with given iterative
     * values and verifies if the method can differentiate two candidates with
     * same candidate names.
     */
    public void test_get_id_same_name() {
        Candidate[] same_name_candidates = new Candidate[2];
        same_name_candidates[0] = new Candidate("A",0);
        same_name_candidates[1] = new Candidate("A",1);

        assertNotSame(same_name_candidates[0].get_id(),same_name_candidates[1].get_id());

    }

    @Test
    /**
     * Test {@link Candidate#get_num_ballots()} by creating candidates
     * with given candidate names and candidate ids and verifies number
     * of ballots by under different scenarios such as adding, removing
     * etc.
     */
    public void test_get_num_ballots() {
        Candidate[] candidates = new Candidate[5];
        candidates[0] = new Candidate("A",0);
        candidates[1] = new Candidate("B",1);
        candidates[2] = new Candidate("C",2);
        Random rand = new Random();
        Ballot test_ballot;
        int expected_id;

        assertEquals(0,candidates[0].get_num_ballots());

        for (int i = 0; i < 10; i++) {
            expected_id = rand.nextInt();
            test_ballot = new Ballot(null, expected_id);
            candidates[1].add_ballot(test_ballot);
        }
        assertEquals(10,candidates[1].get_num_ballots());

        for (int i = 0; i < 10; i++) {
            expected_id = rand.nextInt();
            test_ballot = new Ballot(null, expected_id);
            candidates[2].add_ballot(test_ballot);
        }
        for (int i = 0; i < 5; i++) {
            candidates[2].pull_ballot();
        }

        assertEquals(5,candidates[2].get_num_ballots());

        candidates[2].reset_ballot_list();

        assertEquals(0,candidates[2].get_num_ballots());
    }

    @Test
    /**
     * Test {@link Candidate#reset_ballot_list()} by creating candidates
     * with given candidate names and candidate ids. It further verifies
     * the given input by adding and resetting the ballots for different
     * candidate list.
     */
    public void test_reset_ballots() {

        Candidate[] candidates = new Candidate[5];
        candidates[0] = new Candidate("A",0);
        candidates[1] = new Candidate("B",1);
        candidates[2] = new Candidate("C",2);
        candidates[3] = new Candidate("D",3);
        Random rand = new Random();
        Ballot test_ballot;
        int expected_id;
        for (int i = 0; i < 10; i++) {
            expected_id = rand.nextInt();
            test_ballot = new Ballot(null, expected_id);
            candidates[0].add_ballot(test_ballot);
        }
        candidates[0].reset_ballot_list();
        assertEquals(0,candidates[0].get_num_ballots());

        for (int i = 0; i < 15; i++) {
            expected_id = rand.nextInt();
            test_ballot = new Ballot(null, expected_id);
            candidates[1].add_ballot(test_ballot);
        }
        candidates[1].reset_ballot_list();
        assertEquals(0,candidates[1].get_num_ballots());

        for (int i = 0; i < 20; i++) {
            expected_id = rand.nextInt();
            test_ballot = new Ballot(null, expected_id);
            candidates[2].add_ballot(test_ballot);
        }
        candidates[2].reset_ballot_list();
        assertEquals(0,candidates[2].get_num_ballots());

        for (int i = 0; i < 25; i++) {
            expected_id = rand.nextInt();
            test_ballot = new Ballot(null, expected_id);
            candidates[3].add_ballot(test_ballot);
        }
        candidates[3].reset_ballot_list();
        assertEquals(0,candidates[3].get_num_ballots());

    }
    @Test
    /**
     * Test {@link Candidate#get_ballot(int)} by adding and fetching ballots
     * and comparing their respective randomly generated candidate ids with
     * the given input values.
     */
    public void test_get_ballot() {

        Candidate[] candidates = new Candidate[5];
        candidates[0] = new Candidate("A",0);
        Ballot test_ballot;
        for (int i = 0; i < 10; i++) {
            test_ballot = new Ballot(null, i);
            candidates[0].add_ballot(test_ballot);
            test_ballot = candidates[0].get_ballot(i);
            assertEquals(i,test_ballot.get_id());
        }
    }


    @Test
    /**
     * Test {@link Candidate#add_ballot(Ballot)} by creating candidates with
     * given candidate names and candidate ids. It further verifies by adding
     * ballots for each candidate and verifies with the given input values.
     */
    public void test_add_ballot() {
        Candidate[] candidates = new Candidate[5];
        candidates[0] = new Candidate("A",0);
        candidates[1] = new Candidate("B",1);
        candidates[2] = new Candidate("C",2);
        candidates[3] = new Candidate("D",3);
        Random rand = new Random();
        Ballot test_ballot;
        int expected_id;
        for (int i = 0; i < 10; i++) {
            expected_id = rand.nextInt();
            test_ballot = new Ballot(null, expected_id);
            candidates[0].add_ballot(test_ballot);
        }
        assertEquals(10,candidates[0].get_num_ballots());
        for (int i = 0; i < 15; i++) {
            expected_id = rand.nextInt();
            test_ballot = new Ballot(null, expected_id);
            candidates[1].add_ballot(test_ballot);
        }
        assertEquals(15,candidates[1].get_num_ballots());
        for (int i = 0; i < 20; i++) {
            expected_id = rand.nextInt();
            test_ballot = new Ballot(null, expected_id);
            candidates[2].add_ballot(test_ballot);
        }
        assertEquals(20,candidates[2].get_num_ballots());
        for (int i = 0; i < 25; i++) {
            expected_id = rand.nextInt();
            test_ballot = new Ballot(null, expected_id);
            candidates[3].add_ballot(test_ballot);
        }
        assertEquals(25,candidates[3].get_num_ballots());
    }

    @Test
    /**
     * Test {@link Candidate#pull_ballot()} by creating candidates with
     * given candidate names and candidate ids. It further checks each
     * candidates by adding and removing ballots under different case
     * scenarios with the given input values.
     */
    public void test_pull_ballot() {
        Candidate[] candidates = new Candidate[5];
        candidates[0] = new Candidate("A",0);
        candidates[1] = new Candidate("B",1);

        candidates[0].pull_ballot();
        assertEquals(0,candidates[0].get_num_ballots());

        Random rand = new Random();
        Ballot test_ballot;
        int expected_id;
        for (int i = 0; i < 10; i++) {
            expected_id = rand.nextInt();
            test_ballot = new Ballot(null, expected_id);
            candidates[0].add_ballot(test_ballot);
        }

        for (int i = 0; i < 10; i++) {
            candidates[0].pull_ballot();
        }

        assertEquals(0,candidates[0].get_num_ballots());
        for (int i = 0; i < 10; i++) {
            expected_id = rand.nextInt();
            test_ballot = new Ballot(null, expected_id);
            candidates[1].add_ballot(test_ballot);
            candidates[1].pull_ballot();
        }
        assertEquals(0,candidates[1].get_num_ballots());
    }

    @Test
    /**
     * Test {@link Candidate#compareTo(Candidate)} by creating candidates with
     * given candidate names with their respective ids. It further compares two
     * candidates by subtracting their respective ballot list sizes with the
     * given input value.
     */
    public void test_compareTo() {
        Candidate[] candidates = new Candidate[5];
        candidates[0] = new Candidate("A",0);
        candidates[1] = new Candidate("B",1);

        Random rand = new Random();
        Ballot test_ballot;
        int expected_id;
        for (int i = 0; i < 10; i++) {
            expected_id = rand.nextInt();
            test_ballot = new Ballot(null, expected_id);
            candidates[0].add_ballot(test_ballot);
        }

        for (int i = 0; i < 15; i++) {
            expected_id = rand.nextInt();
            test_ballot = new Ballot(null, expected_id);
            candidates[1].add_ballot(test_ballot);
        }
        assertEquals(5,candidates[1].compareTo(candidates[0]));
        assertEquals(-5,candidates[0].compareTo(candidates[1]));

    }


}
