/* PluralityController.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/
package svs;

import static junit.framework.Assert.*;
import org.junit.Test;
import java.io.*;
import java.util.ArrayList;

/**
 * Unit test for {@link STVDroopController#invalidate_ballots()} and
 * creating reports which are saved to disk
 * @author Arun Sharma (sharm485)
 */

public class InvalidationTest{
    /**
     * Checks the contents of {@link STVDroopController#invalidated_ballots} populated after
     * executing method {@link STVDroopController#invalidate_ballots()} with random
     * invalidated ballots from the input file.
    * @throws IOException If there as an error file creating a file and writing to it
     */

    @Test
    public void invalidate_random_ballots() throws IOException {
        boolean shuffle = false;
        File test_file = new File("testing/testinvalid.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        ArrayList<Ballot> ballots = BallotFactory.InitBallots(shuffle, file, candidates);
        int first_ballot = ballots.get(0).get_id();
        int seventh_ballot = ballots.get(6).get_id();
        int eleventh_ballot = ballots.get(10).get_id();
        int fifteenth_ballot = ballots.get(14).get_id();
        int twentythrid_ballot = ballots.get(22).get_id();

        ElectionController controller = new STVDroopController(ballots,candidates);
        controller.invalidate_ballots();
        ArrayList<Ballot> invalidated_ballots = controller.invalidated_ballots;
        ArrayList<Ballot> validated_ballots = controller.validated_ballots;

        assertEquals(first_ballot,invalidated_ballots.get(0).get_id());
        assertEquals(seventh_ballot,invalidated_ballots.get(1).get_id());
        assertEquals(eleventh_ballot,invalidated_ballots.get(2).get_id());
        assertEquals(fifteenth_ballot,invalidated_ballots.get(3).get_id());
        assertEquals(twentythrid_ballot,invalidated_ballots.get(4).get_id());

    }
    /**
     * Checks the size of {@link STVDroopController#invalidated_ballots} populated after
     * executing method {@link STVDroopController#invalidate_ballots()} from
     * given input files containing invalidated ballots ranged from
     * 0, 5, 10, 15 and 20.
     * @throws IOException If there as an error file creating a file and writing to it
     */
    @Test
    public void invalidate_ballots() throws IOException {
        boolean shuffle = false;
        File test_file = new File("testing/test.csv");
        File test_file1 = new File("testing/testinvalid5.csv");
        File test_file2 = new File("testing/testinvalid10.csv");
        File test_file3 = new File("testing/testinvalid15.csv");
        File test_file4 = new File("testing/testinvalid20.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        file_list.add(test_file1);
        file_list.add(test_file2);
        file_list.add(test_file3);
        file_list.add(test_file4);

        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        ArrayList<Ballot> ballots = BallotFactory.InitBallots(shuffle, file, candidates);
        ArrayList<ArrayList<Ballot>> total_invalid_ballots = new ArrayList<>();

        int seg = 30;
        for (int i=0; i < 5; i++) {
            ArrayList<Ballot> seg_ballot = new ArrayList<>();

            for (int j=(i)*seg; j < (i+1)*seg; j++) {
                seg_ballot.add(ballots.get(j));
            }
            ElectionController controller = new STVDroopController(seg_ballot,candidates);
            controller.invalidate_ballots();

            ArrayList<Ballot> invalidated_ballots = controller.invalidated_ballots;
            total_invalid_ballots.add(invalidated_ballots);
        }

        assertEquals(0,total_invalid_ballots.get(0).size());
        assertEquals(5,total_invalid_ballots.get(1).size());
        assertEquals(10,total_invalid_ballots.get(2).size());
        assertEquals(15,total_invalid_ballots.get(3).size());
        assertEquals(20,total_invalid_ballots.get(4).size());
    }

    /**
     * Checks if the size of {@link STVDroopController#invalidated_ballots} and
     * {@link STVDroopController#validated_ballots} is equal to total number of ballots
     * form the input file range from 0, 5, 10, 15 and 20.
     * @throws IOException If there as an error file creating a file and writing to it
     */

    @Test
    public void invalidate_validate_total() throws IOException {
        boolean shuffle = false;
        File test_file = new File("testing/test.csv");
        File test_file1 = new File("testing/testinvalid5.csv");
        File test_file2 = new File("testing/testinvalid10.csv");
        File test_file3 = new File("testing/testinvalid15.csv");
        File test_file4 = new File("testing/testinvalid20.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        file_list.add(test_file1);
        file_list.add(test_file2);
        file_list.add(test_file3);
        file_list.add(test_file4);

        System.out.println(file_list.size());
        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        System.out.println(candidates.length);
        ArrayList<Ballot> ballots = BallotFactory.InitBallots(shuffle, file, candidates);
        System.out.println(ballots.size());

        int seg = 30;
        for (int i=0; i < 5; i++) {
            ArrayList<Ballot> seg_ballot = new ArrayList<>();

            for (int j=(i)*seg; j < (i+1)*seg; j++) {
                seg_ballot.add(ballots.get(j));
            }
            ElectionController controller = new STVDroopController(seg_ballot,candidates);
            controller.invalidate_ballots();

            ArrayList<Ballot> invalidated_ballots = controller.invalidated_ballots;

            ArrayList<Ballot> validated_ballots = controller.validated_ballots;

            int total_ballots_inside = invalidated_ballots.size() + validated_ballots.size();
            assertEquals(30,total_ballots_inside);

        }

    }

    /**
     * This method checks if the report file is successfully created after
     * executing method {@link STVDroopController#invalidate_ballots()} and
     * saved to disk.
     * @throws IOException If there as an error file creating a file and writing to it
     */
    @Test
    public void invalidated_report_create() throws IOException {
        boolean shuffle = false;
        File test_file = new File("testing/test.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        ArrayList<Ballot> ballots = BallotFactory.InitBallots(shuffle, file, candidates);
        double validation_limit = Math.ceil(candidates.length/2.0);
        ElectionController controller = new STVDroopController(ballots,candidates);
        controller.invalidate_ballots();
        File output = new File("invalidated_ballots.txt");
        System.out.println("Is the file exists ? " + output.exists());
        assertEquals(true,output.exists());
    }

    /**
     * Checks i the contents of the report are
     * consistent with the actual contents from
     * invalidation ballots array after executing
     * {@link STVDroopController#invalidate_ballots()}
     * @throws IOException If there as an error file creating a file and writing to it
     */
    @Test
    public void invalidated_report_contents() throws IOException {
        boolean shuffle = false;
        File test_file = new File("testing/testinvalid.csv");
        ArrayList<File> file_list = new ArrayList<>();
        file_list.add(test_file);
        File[] file = file_list.toArray(new File[file_list.size()]);
        Candidate[] candidates = BallotFactory.InitCandidates(file);
        ArrayList<Ballot> ballots = BallotFactory.InitBallots(shuffle, file, candidates);
        ElectionController controller = new STVDroopController(ballots,candidates);
        controller.invalidate_ballots();

        BufferedReader br = new BufferedReader(new FileReader("invalidated_ballots.txt"));
        String line = "";

        br.readLine();
        br.readLine();
        ArrayList<String> file_content = new ArrayList<>();

        while ((line = br.readLine()) != null) {
//            assertEquals("0.B,D,",line);
            file_content.add(line);
        }
        assertEquals(file_content.get(0),"1.B,D,");
        assertEquals(file_content.get(1),"2.D,B,");
        assertEquals(file_content.get(2),"3.A,B,");
        assertEquals(file_content.get(3),"4.A,D,");
        assertEquals(file_content.get(4),"5.E,B,");

    }



}
