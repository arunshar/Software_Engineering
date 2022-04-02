/* PluralityControllerTest.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/
package svs;

import static junit.framework.Assert.*;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Unit test for {@link PluralityController#ElectionRoutine(int)}
 * for testing the correctness of the election results and
 * handling all possible edge cases via test files.
 * @author Arun Sharma (sharm485)
 */

public class PluralityControllerTest {

    @Test
    /**
     * This unit test is responsible for testing {@link PluralityController#ElectionRoutine(int)} method
     * without crashing and giving correct results for an election with 100000 ballots
     * @throws IOException
     */
    public void testmultipleballots() throws IOException {
        boolean shuffle = false;
        File test_file = new File("testing/testpluralitystress.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        System.out.println(candidates[0].get_name());
        ArrayList<Ballot> ballots = BallotFactory.InitBallots(shuffle, file, candidates);
        System.out.println(ballots.size());
        ElectionController controller = new PluralityController(ballots, candidates);
        Candidate[] results = controller.ElectionRoutine(4);
        for (int i = 0; i < results.length; i++) {
            System.out.println(results[i].get_num_ballots());

        }
        boolean val = true;
        if (results.length <= 0) {
            val = false;
        }
        assertEquals(true,val);

        assertEquals(9897,results[0].get_num_ballots());
        assertEquals(9901,results[1].get_num_ballots());
        assertEquals(9931,results[2].get_num_ballots());
        assertEquals(9958,results[3].get_num_ballots());
        assertEquals(9964,results[4].get_num_ballots());
        assertEquals(9981,results[5].get_num_ballots());
        assertEquals(10013,results[6].get_num_ballots());
        assertEquals(10044,results[7].get_num_ballots());
        assertEquals(10072,results[8].get_num_ballots());
        assertEquals(10239,results[9].get_num_ballots());

        String message = controller.InterpretResults(results, 4);
        System.out.println(message);
    }

    @Test
    /**
     * This unit test is responsible for testing {@link PluralityController#ElectionRoutine(int)} method
     * without crashing and giving correct results for an election with 1 candidate
     * @throws IOException
     */
    public void testoneCandidate() throws IOException {
        boolean shuffle = false;
        File test_file = new File("testing/testpluralityonecandidate.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        System.out.println(candidates[0].get_name());
        ArrayList<Ballot> ballots = BallotFactory.InitBallots(shuffle, file, candidates);
        System.out.println(ballots.size());
        ElectionController controller = new PluralityController(ballots, candidates);
        Candidate[] results = controller.ElectionRoutine(1);
        for (int i = 0; i < results.length; i++) {
            System.out.println(results[i].get_num_ballots());
        }
        boolean val = true;
        if (results.length <= 0) {
            val = false;
        }
        assertEquals(true,val);
        assertEquals(12,results[0].get_num_ballots());

        String message = controller.InterpretResults(results, 1);
        System.out.println(message);
    }

    @Test
    /**
     * This unit test is responsible for testing {@link PluralityController#ElectionRoutine(int)} method
     * without crashing and giving correct results for an election with 10 candidates
     * @throws IOException
     */
    public void testtenCandidates() throws IOException {

        boolean shuffle = false;
        File test_file = new File("testing/testplurality10.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        System.out.println(candidates[0].get_name());
        ArrayList<Ballot> ballots = BallotFactory.InitBallots(shuffle, file, candidates);
        System.out.println(ballots.size());
        ElectionController controller = new PluralityController(ballots, candidates);
        Candidate[] results = controller.ElectionRoutine(5);
        for (int i = 0; i < results.length; i++) {
            System.out.println(results[i].get_num_ballots());
        }
        boolean val = true;
        if (results.length <= 0) {
            val = false;
        }
        assertEquals(true,val);
//
        assertEquals(1,results[0].get_num_ballots());
        assertEquals(1,results[1].get_num_ballots());
        assertEquals(1,results[2].get_num_ballots());
        assertEquals(2,results[3].get_num_ballots());
        assertEquals(3,results[4].get_num_ballots());
        assertEquals(3,results[5].get_num_ballots());
        assertEquals(4,results[6].get_num_ballots());
        assertEquals(4,results[7].get_num_ballots());
        assertEquals(5,results[8].get_num_ballots());
        assertEquals(6,results[9].get_num_ballots());

        String message = controller.InterpretResults(results, 5);
        System.out.println(message);
    }


    @Test
    /**
     * This unit test is responsible for testing {@link PluralityController#ElectionRoutine(int)} method
     * without crashing and giving correct results for an election with no rank candidates
     * @throws IOException
     */
    public void testnorankCandidates() throws IOException {
        boolean shuffle = false;
        File test_file = new File("testing/testpluralitynorank.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        System.out.println(candidates[0].get_name());
        ArrayList<Ballot> ballots = BallotFactory.InitBallots(shuffle, file, candidates);
        System.out.println(ballots.size());
        ElectionController controller = new PluralityController(ballots, candidates);
        Candidate[] results = controller.ElectionRoutine(4);
        for (int i = 0; i < results.length; i++) {
            System.out.println(results[i].get_num_ballots());
        }
        boolean val = true;
        if (results.length <= 0) {
            val = false;
        }

        assertEquals(0,results[0].get_num_ballots());
        assertEquals(0,results[1].get_num_ballots());
        assertEquals(0,results[2].get_num_ballots());
        assertEquals(0,results[3].get_num_ballots());
        assertEquals(0,results[4].get_num_ballots());

        assertEquals(true,val);
        String message = controller.InterpretResults(results, 4);
        System.out.println(message);

    }

    @Test
    /**
     * This unit test is responsible for testing {@link PluralityController#ElectionRoutine(int)} method
     * without crashing and giving correct results for an election with two way tie candidates.
     * @throws IOException
     */
    public void testtwowaytie() throws IOException {
        boolean shuffle = false;
        File test_file = new File("testing/testpluralitytwoway.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        System.out.println(candidates[0].get_name());
        ArrayList<Ballot> ballots = BallotFactory.InitBallots(shuffle, file, candidates);
        System.out.println(ballots.size());
        ElectionController controller = new PluralityController(ballots, candidates);
        Candidate[] results = controller.ElectionRoutine(2);
        for (int i = 0; i < results.length; i++) {
            System.out.println(results[i].get_num_ballots());
        }
        boolean val = true;
        if (results.length <= 0) {
            val = false;
        }
        assertEquals(true,val);

        assertEquals(1,results[0].get_num_ballots());
        assertEquals(3,results[1].get_num_ballots());
        assertEquals(6,results[2].get_num_ballots());
        assertEquals(10,results[3].get_num_ballots());
        assertEquals(10,results[4].get_num_ballots());

        String message = controller.InterpretResults(results, 2);
        System.out.println(message);
    }


    @Test
    /**
     * This unit test is responsible for testing {@link PluralityController#ElectionRoutine(int)} method
     * without crashing and giving correct results for an election with four way tie candidates.
     * @throws IOException
     */
    public void testfourwaytie() throws IOException {
        boolean shuffle = false;
        File test_file = new File("testing/testpluralityfourway.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        System.out.println(candidates[0].get_name());
        ArrayList<Ballot> ballots = BallotFactory.InitBallots(shuffle, file, candidates);
        System.out.println(ballots.size());
        ElectionController controller = new PluralityController(ballots, candidates);
        Candidate[] results = controller.ElectionRoutine(4);
        for (int i = 0; i < results.length; i++) {
            System.out.println(results[i].get_num_ballots());
        }
        boolean val = true;
        if (results.length <= 0) {
            val = false;
        }
        assertEquals(true,val);

        assertEquals(2,results[0].get_num_ballots());
        assertEquals(7,results[1].get_num_ballots());
        assertEquals(7,results[2].get_num_ballots());
        assertEquals(7,results[3].get_num_ballots());
        assertEquals(7,results[4].get_num_ballots());

        String message = controller.InterpretResults(results, 4);
        System.out.println(message);
    }


    @Test
    /**
     * This unit test is responsible for testing {@link PluralityController#ElectionRoutine(int)} method
     * without crashing and giving correct results for an election with 0 ballots
     * @throws IOException
     */
    public void testnoballot () throws IOException {
        boolean shuffle = false;
        File test_file = new File("testing/testpluralitynoballot.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        System.out.println(candidates[0].get_name());
        ArrayList<Ballot> ballots = BallotFactory.InitBallots(shuffle, file, candidates);
        System.out.println(ballots.size());
        ElectionController controller = new PluralityController(ballots, candidates);
        Candidate[] results = controller.ElectionRoutine(4);
        boolean val = true;
        if (results.length <= 0) {
            val = false;
        }
        assertEquals(0,results[0].get_num_ballots());
        assertEquals(0,results[1].get_num_ballots());
        assertEquals(0,results[2].get_num_ballots());
        assertEquals(0,results[3].get_num_ballots());
        assertEquals(0,results[4].get_num_ballots());

        assertEquals(true,val);
            String message = controller.InterpretResults(results, 4);
            System.out.println(message);
        }

    }
