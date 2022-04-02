/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import svs.Candidate;
import svs.STVDroopController;
import svs.Ballot;
import java.util.ArrayList;

/**
 *
 * @author tlone
 */
public class STVDroopControllerTest {
    
    public STVDroopControllerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of ElectionRoutine method, of class STVDroopController.
     * Case of 2 winners and C loses first
     * Test of InterpretResults method, of class STVDroopController
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
        
        System.out.println("ElectionRoutine");
        int num_seats = 2;
        STVDroopController instance = new STVDroopController(ballotTest, candidateTest);
        Candidate[] expResult = new Candidate[5];
        expResult[0] = c4;
        expResult[1] = c5;
        expResult[2] = c1;
        expResult[3] = c2;
        expResult[4] = c3;
        
        Candidate[] result = instance.ElectionRoutine(num_seats);
        assertArrayEquals(expResult, result);
        String resultString = instance.InterpretResults(result, num_seats);
        String expString = "Election Type: Droop Quota\nNumber of ballots: 15\nNumber of seats: "
                + "2\nNumber of candidates: 5\nWinners (in order):\nD\nE\n"
                + "Losers (from most recent to first):\nA\nB\nC\n";
        assertEquals(expString, resultString);
        
        STVDroopController instance1 = new STVDroopController(ballotTest, candidateTest);
        num_seats = 1;
        Candidate[] result1 = instance1.ElectionRoutine(num_seats);
        assertArrayEquals(expResult, result1);
        String resultString1 = instance1.InterpretResults(result, num_seats);
        String expString1 = "Election Type: Droop Quota\nNumber of ballots: 15\nNumber of seats: "
                + "1\nNumber of candidates: 5\nWinners (in order):\nD\n"
                + "Losers (from most recent to first):\nE\nA\nB\nC\n";
        assertEquals(expString1, resultString1);
    }
    
    
    /**
     * Test of ElectionRoutine method, of class STVDroopController.
     * Case of 2 winners and B loses first
     * Test of InterpretResults method, of class STVDroopController
     */
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
        
        System.out.println("ElectionRoutine");
        int num_seats = 2;
        STVDroopController instance = new STVDroopController(ballotTest, candidateTest);
        Candidate[] expResult = new Candidate[5];
        expResult[0] = c4;
        expResult[1] = c5;
        expResult[2] = c1;
        expResult[3] = c3;
        expResult[4] = c2;
        
        Candidate[] result = instance.ElectionRoutine(num_seats);
        assertArrayEquals(expResult, result);
        String resultString = instance.InterpretResults(result, num_seats);
        String expString = "Election Type: Droop Quota\nNumber of ballots: 15\nNumber of seats: "
                + "2\nNumber of candidates: 5\nWinners (in order):\nD\nE\n"
                + "Losers (from most recent to first):\nA\nC\nB\n";
        assertEquals(expString, resultString);
    }

    /**
     * Test of ElectionName method, of class STVDroopController.
     */
    @Test
    public void testElectionName() {
        System.out.println("ElectionName");
        String expResult = "STV with Droop Quota";
        String result = STVDroopController.ElectionName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
