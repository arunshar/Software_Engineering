import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The {@link BallotFactory} BallotFactoryTest is the unit test for each function
 * inside the {@link BallotFactory} class
 *
 * @author Arun Sharma (sharm485)
 */

class BallotFactoryTest extends SVS {
    /**
     * This Unit test will check the ballot size, the first ballot id and the
     * first ballot current choice candidate. If shuffle is true then this test
     * will fail
     * @throws IOException
     */
    @Test
    void initBallots() throws IOException {
        boolean shuffle = false;
        File test_file = new File("testing/test.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        ArrayList<Ballot> total_ballots = BallotFactory.InitBallots(shuffle, file, candidates);
        assertEquals(30,total_ballots.size());
        assertEquals(0,total_ballots.get(0).get_id());
        assertEquals("A",total_ballots.get(0).current_choice().get_name());

    }

    @Test
    /**
     * This unit test will compare each candidate name with the candidate list
     * generated from the given output. It will also check the candidate length
     */
    void initCandidates() throws IOException {
        File test_file = new File("testing/test.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        assertEquals("A",candidates[0].get_name());
        assertEquals("B",candidates[1].get_name());
        assertEquals("C",candidates[2].get_name());
        assertEquals("D",candidates[3].get_name());
        assertEquals("E",candidates[4].get_name());
        assertEquals(5,candidates.length);
    }
}