package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import repository.Sounds;
import game.Game;
import game.GameFacade;

/**
 * This class creates and delivers the topPanel and viewImage panels for the 
 *   NewZorkGUI, and creates and delivers the model to the topPanel, which
 *   passes it on to the viewImage
 * @author Rose Williams
 *
 */
public class PanelCreator
{
  // Instance Variables --------------------------------------------------------
  
  // removed unnecessary variables
  
  /**
   * This topPanel is the TOP PANEL that the user will use to interact with
   *   the MODEL
   */
  private JPanel topPanel; 
  
  /**
   * This viewImage is the VIEW that the user will use to view aspects about the 
   *   MODEL
   */
  private JPanel viewImagePanel;
 


  // Constructors --------------------------------------------------------------
  
  /**
   * Create a new game, create a new topPanel and send the game to it
   * Get the view back from the topPanel
   */
  public PanelCreator()
  {
    topPanel = new JPanel();
    topPanel.setLayout(new BorderLayout());
   
    final GameFacade game = new GameFacade();
    Controller controller = new Controller(game);
    topPanel.add(controller, BorderLayout.LINE_START);
    game.addObserver(controller);
    
    // Make the View Info
    ViewInfo viewInfo = new ViewInfo(game);
    topPanel.add(viewInfo, BorderLayout.LINE_END);
    game.addObserver(viewInfo);
    
    viewInfo.setBackground(Color.YELLOW);
    topPanel.setBackground(Color.YELLOW);
    controller.setBackground(Color.YELLOW);
    
    viewImagePanel = new JPanel();
    viewImagePanel.setLayout(new BorderLayout());
    // Make the View Image
    final ViewImage viewImage = new ViewImage(game);
    viewImage.setSize(viewImagePanel.getSize());
    viewImagePanel.add(viewImage);
    game.addObserver(viewImage);
    
    game.addObserver(new Sounds());
  }
  
  /**
   * Returns the topPanel panel
   * @return the topPanel
   */
  public JPanel getTopPanel()
  {    
    return topPanel;
  }
  
  /**
   * Returns the viewImage panel
   * 
   * @return the viewImage
   */
  public JPanel getViewImagePanel()
  {
    return viewImagePanel;
  }
  

}