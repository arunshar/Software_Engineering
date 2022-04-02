/* SVS.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/
package svs;

/**
 * The class that contains the entry point for SVS. It handles any command line
 * arguments and then launches the user interface for SVS.
 * 
 * @author Nicholas Lovdahl (lovda015)
 */
public class SVS {
  /**
   * The entry point for SVS. The arguments provided to SVS are checked to see
   * if the shuffling should be disabled - this is passed along to
   * {link MainWindow} when it is created.
   * 
   * @param args arguments provided to the program through the command line.
   */
  public static void main(String[] args) {
    boolean shuffle_ballots = true;
    // check for the "-ds" flag...
    for (String arg : args) {
      if (arg.compareToIgnoreCase("-ds") == 0) { shuffle_ballots = false; }
    }
    
    // run the Main Window, passing along whether or not to shuffle ballots
    MainWindow.Run(shuffle_ballots);
  }
}
