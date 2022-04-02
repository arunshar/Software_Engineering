/* HelpWindow.java
   This file is a part of the Straightforward Voting System
   CSCI 5801 - Team 2
   Amir Jalili (jalil014), Arun Sharma (sharm485), Nicholas Lovdahl(lovda015),
   and Taylor O'Neill (oniel569)
*/

package svs;

import javax.swing.*;

/**
 * A dialog window which presents the user with instructions to help them use
 * SVS.
 * 
 * @author Nicholas Lovdahl (lovda015)
 */
public class HelpWindow extends JDialog {
  /**
   * Creates the help window and sets its parent to the given component. This
   * also involves setting the text that is set by the user.
   * 
   * @param parent the component that will own this help window.
   */
  public HelpWindow(JFrame parent) {
    // create a dialog window that blocks input and has the specified title
    super(parent, "Straightforward Voting System - Help", true,
          parent.getGraphicsConfiguration());  // use parent's look and feel
    
    // set the help text
    help_text_field = new JTextArea(20, 60);  // size in rows / columns
    help_text_field.setEditable(false);
    help_text_field.setLineWrap(true);
    help_text_field.setWrapStyleWord(true);
    
    // this will be what the user sees when they go for help
    help_text_field.setText("Helpful text will go here...");
    
    // set the content of this dialog to be the scroll pane with the help text
    help_text_pane = new JScrollPane(help_text_field);
    this.setContentPane(help_text_pane);
    // set the minimum size of the help window using its text area
    this.setMinimumSize(help_text_field.getPreferredScrollableViewportSize());
    this.setLocationByPlatform(true);
  }
  
  private final JTextArea help_text_field;
  private final JScrollPane help_text_pane; 
}
