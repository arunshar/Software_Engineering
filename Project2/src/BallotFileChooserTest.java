/* BallotFileChooserTest.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/

package svs;

import java.io.File;
import static junit.framework.Assert.*;
import org.junit.Test;

/**
 * Unit tests for the methods in {@link BallotFileChooser}.
 * 
 * @author Nicholas Lovdahl (lovda015)
 */
public class BallotFileChooserTest {
  /**
   * Checks that null is returned if the user aborts the selection of multiple
   * files.
   */
  @Test
  public void test_choose_ballot_files_abort() {
    BallotFileChooser bfc = new BallotFileChooser(null);
    System.out.println("Abort the selection process by closing the file " +
                       "chooser or by pressing cancel.");
    File[] files = bfc.ChooseBallotFiles();
    assertNull(files);
  }
  
  /**
   * Checks that null is returned if the user aborts the selection of a single
   * directory.
   */
  @Test
  public void test_choose_directory_abort() {
    BallotFileChooser bfc = new BallotFileChooser(null);
    System.out.println("Abort the selection process by closing the file " +
                       "chooser or by pressing cancel.");
    File[] files = bfc.ChooseDirectory();
    assertNull(files);
  }
  
  /**
   * Checks that an empty array is returned when the user selects a directory
   * that has no files with an accepted extension on it.
   */
  @Test
  public void test_select_empty_dir() {
    BallotFileChooser bfc = new BallotFileChooser(null);
    System.out.println("Select test_empty_sub_dir.");
    File[] files = bfc.ChooseDirectory();
    assertNotNull(files);
    assertEquals(0, files.length);
  }
  
  /**
   * Checks that all ballot files are returned when a user selects a directory,
   * but no files from any subdirectories are returned. No files other than
   * ballot files should be returned.
   */
  @Test
  public void test_select_dir() {
    BallotFileChooser bfc = new BallotFileChooser(null);
    System.out.println("Select ballot_file_chooser_test_dir.");
    File[] files = bfc.ChooseDirectory();
    assertNotNull(files);
    assertEquals(5, files.length);  // there should be 5 files total
    assertTrue(containsFilename(files, "test_1.csv"));
    assertTrue(containsFilename(files, "test_2.csv"));
    assertTrue(containsFilename(files, "test_3.csv"));
    assertTrue(containsFilename(files, "test_4.csv"));
    assertTrue(containsFilename(files, "test_5.csv"));
  }
  
  /**
   * Checks that the user can select a single particular ballot file.
   */
  @Test
  public void test_select_single_file() {
    BallotFileChooser bfc = new BallotFileChooser(null);
    System.out.println("Select test_1.csv.");
    File[] files = bfc.ChooseBallotFiles();
    assertNotNull(files);
    assertEquals(1, files.length);
    assertTrue(containsFilename(files, "test_1.csv"));
  }
  
  /**
   * Checks that the user can select a few particular ballot files.
   */
  @Test
  public void test_select_some_files() {
    BallotFileChooser bfc = new BallotFileChooser(null);
    System.out.println("Select test_1.csv, test_3.csv, and test_4.csv.");
    File[] files = bfc.ChooseBallotFiles();
    assertNotNull(files);
    assertEquals(3, files.length);
    assertTrue(containsFilename(files, "test_1.csv"));
    assertTrue(containsFilename(files, "test_3.csv"));
    assertTrue(containsFilename(files, "test_4.csv"));
  }
  
  /**
   * Checks that the user can select every ballot file.
   */
  @Test
  public void test_select_all_files() {
    BallotFileChooser bfc = new BallotFileChooser(null);
    System.out.println("Select test_1.csv, test_2.csv, test_3.csv, " +
                       "test_4.csv, and test_5.csv.");
    File[] files = bfc.ChooseBallotFiles();
    assertNotNull(files);
    assertEquals(5, files.length);
    assertTrue(containsFilename(files, "test_1.csv"));
    assertTrue(containsFilename(files, "test_2.csv"));
    assertTrue(containsFilename(files, "test_3.csv"));
    assertTrue(containsFilename(files, "test_4.csv"));
    assertTrue(containsFilename(files, "test_5.csv"));
  }
  
  /**
   * Returns true if and only if a specified filename is contained within the
   * given array of files.
   */
  private boolean containsFilename(File[] files, String filename) {
    if (files == null) { return false; }
    
    // check each file - return true for a match
    for (File file : files) {
      if (filename.compareTo(file.getName()) == 0) { return true; }
    }
    return false;  // return false if no match was found in the entire array
  }
}
