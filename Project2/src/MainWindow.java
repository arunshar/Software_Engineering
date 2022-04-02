/* MainWindow.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/

package svs;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

/**
 * The primary interface through which a user interacts with SVS. It provides
 * controls for the user to load ballot files, input the number of seats up for
 * an election, select the type of election, and run that election using the
 * given information. MainWindow provides output to the user is provided through
 * a text field. It also provides access to a the {@link HelpWindow} which
 * provides the user with instructions to successfully operate SVS. All of this
 * is done using the look and feel of the system which should provide the user
 * with a simple and familiar environment.
 * <p>
 * Moving beyond the user's perspective, MainWindow is also responsible for
 * connecting the actions of the user to calls to the appropriate components of
 * SVS - loading files into memory, running elections, and so on. In this sense,
 * MainWindow can be thought of as a bridge between the user and the underlying
 * logic of SVS.
 * 
 * @author Nicholas Lovdahl (lovda015)
 */
public class MainWindow extends JFrame {
  /**
   * The constructor for MainWindow is responsible for initializing the
   * components it uses (buttons, spinners, drop-down menus, and so on). It must
   * connect the actions involved with these components to appropriate methods.
   * This also involves preparing the {@link BallotFileChooser} and
   * {@link HelpWindow} used by MainWindow to allow the user to select ballot
   * files and to provide instructions to help the user, respectively.
   * 
   * @param shuffle_ballots used to pass on whether or not ballots loaded from
   *                        files should be shuffled to {@link BallotFactory}.
   */
  public MainWindow(boolean shuffle_ballots) {
    this.shuffle_ballots = shuffle_ballots; // keep track of whether we shuffle
    election_running = false;
    // set the ballots and candidates to null (nothing loaded yet)
    candidates = null;
    ballots = null;
    
    // set the properties for the frame itself
    setTitle("Straightforward Voting System");
    setLocationByPlatform(true);
    // intercept events to close the window and send them to our action event
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent event) {
        ActionEvent e = new ActionEvent(event.getSource(), event.getID(), null);
        ExitAction(e);
      }
    });
    
    // initialize the menu bar for the frame
    menu_bar = new JMenuBar();
    
    file_menu = new JMenu("File");
    load_file_menu_item = new JMenuItem("Load Ballots from File(s)...");
    load_file_menu_item.addActionListener(
      (ActionEvent event) -> { LoadBallotFilesAction(event); }
    );
    load_dir_menu_item = new JMenuItem("Load Ballots from Directory...");
    load_dir_menu_item.addActionListener(
      (ActionEvent event) -> { LoadBallotDirAction(event); }
    );
    exit_menu_item = new JMenuItem("Exit");
    exit_menu_item.addActionListener(
      (ActionEvent event) -> { ExitAction(event); }
    );
    
    help_menu = new JMenu("Help");
    about_menu_item = new JMenuItem("About");
    about_menu_item.addActionListener(
      (ActionEvent event) -> { OpenHelpWindowAction(event); }
    );
    
    // setup the menu bar within the frame
    file_menu.add(load_file_menu_item);
    file_menu.add(load_dir_menu_item);
    file_menu.add(new JSeparator());
    file_menu.add(exit_menu_item);
    menu_bar.add(file_menu);
    help_menu.add(about_menu_item);
    menu_bar.add(help_menu);
    setJMenuBar(menu_bar);
    
    // initialize the rest of the frame's components (e.g. buttons)
    load_file_button = new JButton("Load Ballots from File(s)...");
    load_file_button.addActionListener(
      (ActionEvent event) -> { LoadBallotFilesAction(event); }
    );
    load_dir_button = new JButton("Load Ballots from Directory...");
    load_dir_button.addActionListener(
      (ActionEvent event) -> { LoadBallotDirAction(event); }
    );
    
    seats_label = new JLabel("Number of Seats for Election:");
    SpinnerNumberModel seats_spinner_model =
      new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);  // range of 0 to MAX
    seats_spinner = new JSpinner(seats_spinner_model);
    
    DefaultComboBoxModel<String> election_type_model =
      new DefaultComboBoxModel<>(new String[] {
        PluralityController.ElectionName(),
        STVDroopController.ElectionName()
      });
    election_type_dropdown = new JComboBox<>(election_type_model);
    
    run_election_button = new JButton("Run Election");
    run_election_button.addActionListener(
      (ActionEvent event) -> { RunElectionAction(event); }
    );
    
    output_text_field = new JTextArea(15, 80);  // size in rows / columns
    output_text_field.setEditable(false);
    output_text_field.setLineWrap(true);
    output_text_field.setWrapStyleWord(true);
    output_text_pane = new JScrollPane(output_text_field);
    // set the minimum size of the frame using the size for the output
    this.setMinimumSize(output_text_field.getPreferredScrollableViewportSize());
    
    progress_bar = new JProgressBar();
    progress_bar.setString("Running...");
    update_progress_bar();  // progress bar does not start out 'running'
    
    // initialize the file chooser with this frame as the parent component
    file_chooser = new BallotFileChooser(this);
    
    // initialize the help window and its contents
    help_window = new HelpWindow(this);
    
    // arrange the layout of the frame's components within the frame
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addComponent(progress_bar, GroupLayout.DEFAULT_SIZE,
                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(
                    GroupLayout.Alignment.LEADING, false)
          .addComponent(load_file_button, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(load_dir_button, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(layout.createParallelGroup(
                    GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(seats_label)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(seats_spinner, GroupLayout.DEFAULT_SIZE,
                          140, Short.MAX_VALUE))
          .addComponent(election_type_dropdown, 0,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
      .addComponent(output_text_pane,
                    GroupLayout.Alignment.TRAILING)
      .addComponent(run_election_button, GroupLayout.DEFAULT_SIZE,
                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(GroupLayout.Alignment.TRAILING,
                layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(
                    GroupLayout.Alignment.BASELINE)
          .addComponent(load_file_button)
          .addComponent(seats_spinner, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
          .addComponent(seats_label))
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(
                    GroupLayout.Alignment.BASELINE)
          .addComponent(load_dir_button)
          .addComponent(election_type_dropdown,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(run_election_button)
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(output_text_pane, GroupLayout.DEFAULT_SIZE,
                      300, Short.MAX_VALUE)
        .addComponent(progress_bar, GroupLayout.PREFERRED_SIZE, 20,
                      GroupLayout.PREFERRED_SIZE))
    );
    
    pack();
  }
  
  /**
   * Creates an instance of MainWindow and begins running threads to handle
   * event dispatches. An example of an event would be a user clicking on a
   * button, triggering some ActionEvent method. Doing this creates a
   * convenient abstraction for handling threads and asynchronous events.
   * <p>
   * Prior to the creation of MainWindow, this method will also attempt to set
   * the look and feel of the content to be displayed so that it matches with
   * the system running SVS. If this cannot be done, then the user is notified.
   * 
   * @param shuffle_ballots used to pass on whether or not ballots loaded from
   *                        files should be shuffled to the constructor for
   *                        MainWindow.
   * 
   * @see SwingUtilities#invokeLater(Runnable)
   */
  public static void Run(boolean shuffle_ballots) {
    // try to set the UI's look and feel
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null,
        "Failed to set system look and feel. The default will be used.");
    }
    
    new MainWindow(shuffle_ballots).setVisible(true);
  }
  
  /**
   * Handles the event for when the user wants to load ballots from files. If an
   * election is being run when this happens, the user will be notified that
   * this cannot be done and nothing more happens. Otherwise, the
   * {@link BallotFileChooser} will be invoked to allow the user to select CSV
   * files; the selected files are loaded via
   * {@link #LoadSelectedFiles(File[])}.
   * 
   * @param event contains information related to the invocation of this event.
   * 
   * @see #LoadBallotDirAction(ActionEvent)
   * @see #LoadSelectedFiles(File[])
   */
  private void LoadBallotFilesAction(ActionEvent event) {
    // if there is an election running, then tell the user we cannot load
    if (election_running) {
      JOptionPane.showMessageDialog(this,
        "You cannot load ballots while an election is running.");
    } else {  // else we can proceed to load ballots
      File[] selected_files = file_chooser.ChooseBallotFiles();
      LoadSelectedFiles(selected_files);
    }
  }
  
  /**
   * Handles the event for when the user wants to load ballots from a directory.
   * If an election is being run when this happens, the user will be notified
   * that this cannot be done and nothing more happens. Otherwise, the
   * {@link BallotFileChooser} will be invoked to allow the user to select a
   * single directory; the files within this directory are loaded via
   * {@link #LoadSelectedFiles(File[])}.
   * 
   * @param event contains information related to the invocation of this event.
   * 
   * @see #LoadBallotFilesAction(ActionEvent)
   * @see #LoadSelectedFiles(File[])
   */
  private void LoadBallotDirAction(ActionEvent event) {
    // if there is an election running, then tell the user we cannot load
    if (election_running) {
      JOptionPane.showMessageDialog(this,
        "You cannot load ballots while an election is running.");
    } else {  // else we can proceed to load ballots
      File[] selected_files = file_chooser.ChooseDirectory();
      LoadSelectedFiles(selected_files);
    }
  }
  
  /**
   * Handles the event for when the user wants to run an election. If an
   * election is already being run when this happens, the user will be notified
   * that another election cannot be run. If no ballots or candidates were
   * loaded from files, the user will be notified that they cannot run an
   * election. Otherwise, an election will be run using the information entered
   * by the user.
   * <p>
   * The first step in this process is to set a flag to denote that an election
   * is currently being run. Then the number of seats is gotten from the seats
   * spinner. Next, an election controller is created using the loaded ballots
   * and candidates; the type of controller created is determined from the value
   * of the drop-down for the type of election to be run. Once this information
   * has been set, {@link ElectionWorker} is started up to run the
   * election in the background.
   * 
   * @param event contains information related to the invocation of this event.
   * 
   * @see ElectionController
   * @see PluralityController
   * @see STVDroopController
   */
  private void RunElectionAction(ActionEvent event) {
    // if there is an election running, then tell the user we cannot run another
    if (election_running) {
      JOptionPane.showMessageDialog(this,
        "You cannot run another election while an election is running.");
    } else {
      // check if nothing has been loaded yet (candidates or ballots is null)
      if (candidates == null || ballots == null) {
        JOptionPane.showMessageDialog(this,
          "An election cannot be run before loading files.");
      } else {  // we can proceed to run an election now
        // reflect that we are running an election
        election_running = true;
        update_progress_bar();
        
        // set the number of seats based on the value from the seats spinner
        num_seats = (Integer) seats_spinner.getValue();
        
        // set election controller based on which election type was chosen
        String election_type = election_type_dropdown.getSelectedItem().toString();
        
        if (PluralityController.ElectionName().compareTo(election_type) == 0) {
          controller = new PluralityController(ballots, candidates);
        } else if (STVDroopController.ElectionName().compareTo(
                     election_type) == 0) {
          controller = new STVDroopController(ballots, candidates);
        }  // else no election type matches, something terrible has happened...
        
        // proceed if we have a controller (the selected election type matched)
        if (controller != null) {
          // run the election in a worker thread in the background
          new ElectionWorker().execute();
        } else {  // else we let the user know something went wrong
          JOptionPane.showMessageDialog(this,
            "Your selected election type could not be matched.");
        }
      }
    }
  }
  
  /**
   * Handles the event for when the user wants to see the Help Window. This will
   * simply reveal the {@link HelpWindow} on the screen for the user.
   * 
   * @param event contains information related to the invocation of this event.
   */
  private void OpenHelpWindowAction(ActionEvent event) {
    help_window.setVisible(true);
  }
  
  /**
   * Handles the event for when the user wants to exit the program. If an
   * election is running, the user will be notified that they cannot exit until
   * the election finishes running and nothing more will happen. Otherwise, the
   * program will exit.
   * 
   * @param event contains information related to the invocation of this event.
   */
  private void ExitAction(ActionEvent event) {
    // if there is an election running, then tell the user we cannot exit yet
    if (election_running) {
      JOptionPane.showMessageDialog(this,
        "You cannot exit while an election is running.");
    } else {  // otherwise we can just terminate
      System.exit(0);
    }
  }
  
  /**
   * Handle the loading of candidates and ballots from an array of selected
   * files and informs the user of the outcome of doing so. If the array of
   * selected files is null (this should be the case if and only if the user
   * aborts the selection process) nothing is done. If the array of files is
   * empty (if the user's selection contained no valid ballot files), then we
   * inform the user of this via a message and do nothing more.
   * <p>
   * If there is at least one selected file, however, we attempt to initialize
   * candidates and ballots from the files. If we fail to do so, then the user
   * is informed of this and no data is changed (we do not overwrite the
   * ballots / candidates in memory). If there are no problems doing this, then
   * the results from loading the files it presented to the user via the
   * output text field; the user is told how many files, candidates, and ballots
   * were loaded.
   * 
   * @param selected_files an array of files to be loaded.
   */
  private void LoadSelectedFiles(File[] selected_files) {
    // only do something if the user actually made a selection (not null)
    if (selected_files != null) {
      // proceed to load files if the user selected at least one ballot file
      if (selected_files.length > 0) {      
        // try to init candidates from the first file and then load ballots
        Candidate[] new_candidates = null;
        ArrayList<Ballot> new_ballots = null;
        try {
          new_candidates = BallotFactory.InitCandidates(selected_files);
          new_ballots = BallotFactory.InitBallots(shuffle_ballots,
                                                  selected_files,
                                                  new_candidates);
        } catch (Exception e) {
          JOptionPane.showMessageDialog(this,
            "An exception was raised while loading date from the files.\n" +
            "Error Message: " + e.getMessage());
        }
        
        // only change if we did not have trouble loading candidates and ballots
        if (new_candidates != null && new_ballots != null) {
          candidates = new_candidates;
          ballots = new_ballots;
          // give the user info on what was just loaded through the output text
          output_text_field.setText(
            selected_files.length + " file(s) loaded.\n" +
            candidates.length + " candidate(s) found.\n" +
            ballots.size() + " ballot(s) found.\n");
        }
      } else {  // if there are no valid files to load, let the user know
        JOptionPane.showMessageDialog(this,
          "No valid ballot files could be found.");
      }
    }
  }
  
  /**
   * Sets the progress bar to reflect whether or not an election is currently
   * running. This provides users with visual feedback that something is
   * happening.
   */
  private void update_progress_bar() {
    progress_bar.setStringPainted(election_running);
    progress_bar.setIndeterminate(election_running);
  }
  
  /**
   * This is a worker thread that runs an election in the background. Doing this
   * allows the GUI to continue responding to the user instead of freezing up
   * when an election is running. This thread, when executed, first runs its
   * background method (running the election) and then runs its done method
   * (writing the results of the election to the output text field).
   * <p>
   * This class is nested inside of [@link MainWindow} to allow it access to its
   * attributes.
   */
  class ElectionWorker extends SwingWorker<String, Object> {
    /**
     * The 'main body' of the worker thread that runs the election. If an
     * exception is raised in the course of running the election, the user is
     * notified with a message.
     * 
     * @return the results of the election as the user would see them in the
     *         output text field.
     */
    @Override
    protected String doInBackground() {
      // get the current text so we return / preserve it in case of an error
      String results = output_text_field.getText();
      
      try {
        results = controller.RunElection(num_seats);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
          "An exception was raised while running the election.\n" +
          "Error Message: " + e.getMessage());
      }
      
      return results;
    }
    
    /**
     * Called once at the end of the thread's work (once the election is done)
     * and writes the results of the election to the output text field. The flag
     * denoting that an election is running is reset (no election running) and
     * the progress bar is reset so it is no longer 'running'.
     */
    @Override
    protected void done() {
      try {
        output_text_field.setText(get());
      } catch (Exception e) {
        // this would be triggered if the thread was interrupted...
      }
      
      // reflect that the election has finished running
      election_running = false;
      update_progress_bar();
    }
  }
  
  // attributes used for running elections
  private final boolean shuffle_ballots;
  private ArrayList<Ballot> ballots;
  private Candidate[] candidates;
  private int num_seats;
  private ElectionController controller;
  private boolean election_running;
  
  // attributes used for the ui
  private final JMenuBar menu_bar;
  private final JMenu file_menu;
  private final JMenuItem load_file_menu_item;
  private final JMenuItem load_dir_menu_item;
  private final JMenuItem exit_menu_item;
  private final JMenu help_menu;
  private final JMenuItem about_menu_item;
  
  private final JButton load_file_button;
  private final JButton load_dir_button;
  private final JLabel seats_label;
  private final JSpinner seats_spinner;
  private final JComboBox<String> election_type_dropdown;
  private final JButton run_election_button;
  
  private final JTextArea output_text_field;
  private final JScrollPane output_text_pane;
  
  private final JProgressBar progress_bar;
  
  private final BallotFileChooser file_chooser;
  private final HelpWindow help_window;
}
