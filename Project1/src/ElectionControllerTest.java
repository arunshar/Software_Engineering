import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class {@link ElectionControllerTest} check certain methods which can be individually tested
 * and gets verified with given inputs by the asserts.
 *
 * @author Arun Sharma (sharm485)
 */

class ElectionControllerTest extends SVS {
    /**
     * This unit test checks the method distribute ballots by comparing the number of ballots for a
     * candidate with the given input
     * @throws IOException
     */
    @Test
    void distributeBallot() throws IOException {
        boolean shuffle = false;
        File test_file = new File("testing/test.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        ArrayList<Ballot> total_ballots = BallotFactory.InitBallots(shuffle, file, candidates);
        Ballot b = total_ballots.get(0);
        Ballot b1 = total_ballots.get(1);
        Ballot b2 = total_ballots.get(2);
        Ballot b3 = total_ballots.get(3);
        Candidate c = total_ballots.get(0).current_choice();
        assertEquals(0,c.get_num_ballots());
        c.add_ballot(b);
        assertEquals(1,c.get_num_ballots());
        c.add_ballot(b1);
        assertEquals(2,c.get_num_ballots());
        c.add_ballot(b2);
        assertEquals(3,c.get_num_ballots());
        c.add_ballot(b3);
        assertEquals(4,c.get_num_ballots());
    }

    /**
     * This test checks if the the ballots are redistributed by checking the number of ballots for
     * each candidate with respect to the given input
     * @throws IOException
     */
    @Test
    void redistributeBallot() throws IOException {
        boolean shuffle = false;
        File test_file = new File("testing/test.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        ArrayList<Ballot> total_ballots = BallotFactory.InitBallots(shuffle, file, candidates);
        Ballot b = total_ballots.get(0);
        Ballot b1 = total_ballots.get(1);
        Ballot b2 = total_ballots.get(2);
        Ballot b3 = total_ballots.get(3);
        Candidate c = total_ballots.get(0).current_choice();
        assertEquals(0,c.get_num_ballots());
        c.add_ballot(b);
        assertEquals(1,c.get_num_ballots());
        c.add_ballot(b1);
        c.pull_ballot();
        assertEquals(1,c.get_num_ballots());
        c.add_ballot(b2);
        assertEquals(2,c.get_num_ballots());
        c.add_ballot(b3);
        c.pull_ballot();
        assertEquals(2,c.get_num_ballots());

    }

    /**
     * This UNIT test checks if the file msgs are accumulated by comparing the list
     * size with the given input.
     * @throws IOException
     */
    @Test
    void customAuditFileMsg() throws IOException {
        boolean shuffle = false;
        File test_file = new File("testing/test.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        ArrayList<Ballot> total_ballots = BallotFactory.InitBallots(shuffle, file, candidates);
        ArrayList<String> file_msg = new ArrayList<>();
        Ballot b = total_ballots.get(0);
        Ballot b1 = total_ballots.get(1);
        Candidate c = total_ballots.get(0).current_choice();
        Candidate c2 = total_ballots.get(1).current_choice();

        assertEquals(0,file_msg.size());

        c.add_ballot(b);
        String msg1 = "Ballot " + b.get_id() + " assigned to " + c.get_name() + "\n";
        file_msg.add(msg1);
        assertEquals(1,file_msg.size());

        c.add_ballot(b1);
        String msg2 = "Ballot " + b1.get_id() + " assigned to " + c.get_name() + "\n";
        file_msg.add(msg2);
        assertEquals(2,file_msg.size());

        Ballot b2 = c.pull_ballot();
        c2.add_ballot(b2);
        String msg3 = "The ballot " + b1.get_id() + " is removed from " + c.get_name() + "\n";
        file_msg.add(msg3);
        assertEquals(3,file_msg.size());


        String msg4 = "The ballot " + b1.get_id() + " is now assigned to " + c2.get_name() + "\n";
        file_msg.add(msg4);
        assertEquals(4,file_msg.size());

    }
}