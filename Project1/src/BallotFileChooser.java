/* BallotFileChooser.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/
package svs;

import java.awt.Component;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.ArrayList;

/**
 * A file chooser interface which allows a user to navigate the file system and
 * select either ballot files or a directory. This is provided through the
 * invocation of methods to selected either ballot files or a single directory,
 * respectively.
 * 
 * @author Nicholas Lovdahl (lovda015)
 */
public class BallotFileChooser extends JFileChooser {
  /**
   * Creates the file chooser and sets its parent to the given component. The
   * ability for the user to select just any file is disabled in the constructor
   * as well, so that users must abide by the options presented by the file
   * chooser. Other options are set dynamically (not in the constructor) for
   * invocations to select files or directories.
   * 
   * @param parent the component that will own this file chooser.
   */
  public BallotFileChooser(Component parent) {
    this.parent = parent;
    
    setAcceptAllFileFilterUsed(false);  // user cannot accept just any files
  }
  
  /**
   * Allows the user to select multiple ballot files through a file chooser. The
   * user can see directories and files with valid extensions while navigating.
   * Users are only allowed to select files whose extensions are those of valid
   * ballot files. The files selected by the user are filtered so that only the
   * files that pass this filter are returned. Null is returned if the user
   * aborts the selection process. An empty array is returned if no valid ballot
   * files are found in the user's selection.
   * 
   * @return an array of files selected by the user, if any.
   * 
   * @see #ChooseDirectory()
   * @see #FilterForBallotFiles(File[] selected_files)
   */
  public File[] ChooseBallotFiles() {
    setMultiSelectionEnabled(true);  // can select many files
    setFileSelectionMode(JFileChooser.FILES_ONLY);  // can select only files
    setFileFilter(NAVIGATION_FILE_FILTER);
    
    File[] chosen_files = null;
    
    // ask the user to make a selection
    if (showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
      // we chose every selected file that passes the filter
      chosen_files = FilterForBallotFiles(getSelectedFiles());
    }  // else the user aborted / canceled the selection
    
    return chosen_files;
  }
  
  /**
   * Allows the user to select a single directory through a file chooser. The
   * user can only see directories while navigating (not files). The files in
   * the directory (but not in any subdirectories) selected by the user are
   * filtered so that only the files whose extensions are those of valid ballot
   * files are returned. Null is returned if the user aborts the selection
   * process. An empty array is returned if no valid ballot files are found in
   * the selected directory.
   * 
   * @return an array of files from the directory selected by the user, if any.
   * 
   * @see #ChooseBallotFiles()
   * @see #FilterForBallotFiles(File[] selected_files)
   */
  public File[] ChooseDirectory() {
    setMultiSelectionEnabled(false);  // can select a single directory
    setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  // can select dir only
    setFileFilter(DIR_FILE_FILTER);
    
    File[] chosen_files = null;
    
    // ask the user to make a selection
    if (showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
      // we chose every file in the directory that passes the filter
      chosen_files = FilterForBallotFiles(getSelectedFile().listFiles());
    }  // else the user aborted / canceled the selection
    
    return chosen_files;
  }
  
  /**
   * Takes an array of files and filters them into an array of only the files
   * whose extensions are those of valid ballot files. If no files pass this
   * filter, then an empty array is returned.
   * 
   * @param selected_files an array of files
   * @return an array of only the given files which pass through the filter.
   */
  private File[] FilterForBallotFiles(File[] selected_files) {
    // proceed only if we were not given null
    if (selected_files == null) { return null; }
    
    // create a new array of files the pass the filter
    ArrayList<File> files = new ArrayList<>();
    for (File selected_file : selected_files) {
      if (BALLOT_FILE_FILTER.accept(selected_file)) {
        files.add(selected_file);
      }
    }
    
    // transform the list to an array whoose size is the size of the ArrayList
    return files.toArray(new File[files.size()]);
  }
  
  private final Component parent;
  
  // filter allows only files that end with the .csv extension
  private static final FileFilter BALLOT_FILE_FILTER = new FileFilter() {
    public boolean accept(File f) {
      return f.getName().toLowerCase().endsWith(".csv");
    }
    public String getDescription() { return "CSV Files"; }
  };
  // filter allows only directories
  private static final FileFilter DIR_FILE_FILTER = new FileFilter() {
    public boolean accept(File f) {
      return f.isDirectory();
    }
    public String getDescription() { return "Directory"; }
  };
  // we need both files and dirs to allow the user to navigate the filesystem
  private static final FileFilter NAVIGATION_FILE_FILTER = new FileFilter() {
    public boolean accept(File f) {
      return BALLOT_FILE_FILTER.accept(f) || DIR_FILE_FILTER.accept(f);
    }
    public String getDescription() {
      return BALLOT_FILE_FILTER.getDescription();
    }
  };
}
