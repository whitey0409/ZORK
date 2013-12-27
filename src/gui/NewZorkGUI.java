package gui;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * creates a frame to view and control a game
 */
public class NewZorkGUI
{
  // Class variables -----------------------------------------------------------
  
  // Gets Logger object for debugging
  public static Logger log;
  static
  {
    log = Logger.getLogger("NewZorkGUI");
  }
  
  // Instance variables --------------------------------------------------------
    
  /**
   * Top-level container for a GUI
   */
  private JFrame frame;
  
  /**
   * Creates panels that will be added to frame
   */
  private PanelCreator creator;
  
  // Constructors --------------------------------------------------------------
  
  /**
   * Creates, fills and displays frame
   */
  private NewZorkGUI()
  {
    // Start logging
    setUpLogging();
    
    // Create frame with title
    this.frame = new JFrame("NewZork");
    
    // Prevent program from running after window closed
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // Set initial frame size
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension dimension = toolkit.getScreenSize();
    frame.setSize(dimension);
    
    // Creates and delivers the controller and view panels for our GUI
    // Model is introduced inside Panel Creator to reduce coupling here
    this.creator = new PanelCreator();
    
    // Declare and get the controller here
    log.info("Create Controller Panel");    
    JPanel top = creator.getTopPanel(); // CONTROLLER
    log.info("Create Controller Panel Done");
    
    // Declare and get the view here
    log.info("Create View Panel");
    JPanel bottom = creator.getViewImagePanel(); // VIEW
    log.info("Create Viewer Panel Done");
    
    //Add the panels to the frame
    //The BorderLayout manager is the default for frames
    
    // Add the control panel to the left side of the frame
    frame.add(top, BorderLayout.NORTH);
    
    // Add the view panel to the center of the frame
    frame.add(bottom, BorderLayout.CENTER);
    
    //Make the GUI visible
    log.info("Make window and panels visible");
    frame.setVisible(true);
    log.info("Make window and panels visible Done");
    log.info("End of NewZorkGUI code");
  }
  
  // -- Class Methods ----------------------------------------------------------

  /**
   * Turns Logger object on and off
   */
  private static void setUpLogging()
  {
    log.setLevel(Level.ALL);
    //log.setLevel(Level.OFF);
  }
  
  
  // -- Main method ------------------------------------------------------------
  
  /**
   * "Boilerplate" code to use for GUI main methods:
   * @param args
   */
  public static void main(String[] args)
  {
    // Schedule a job for the event-dispatching thread:
    // creating and showing this application's Frame
    class EventDispatchingThread implements Runnable
    {
      public void run()
      {
        // Create GUI here
        new NewZorkGUI();
      }
    }
    javax.swing.SwingUtilities.invokeLater(new EventDispatchingThread());
  }
}
