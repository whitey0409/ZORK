package gui;

import game.Game;
import game.GameFacade;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

/**
 * This class serves as the VIEW in the NewZorkGUI
 * It creates an area where aspects of the MODEL can be displayed
 * @author Rose Williams
 *
 */
public class ViewInfo extends JPanel implements Observer
{
  // Instance Variables --------------------------------------------------------

  /**
   * MODEL
   * What this VIEW will show
   */
  private GameFacade game;
  
  /**
   * SubPanel of this VIEW
   * Will show info about Human portion of MODEL
   */
  private ViewSubPanel viewHuman;
  
  /**
   * SubPanel of this VIEW
   * Will show info about Gruman portion of MODEL
   */
  private ViewSubPanel viewGruman;
  
  /**
   * SubPanel of the game status
   * Will show info about Game
   */
  private ViewSubPanel viewGame;
 
  // Constructors --------------------------------------------------------------
  
  /**
   * Creates this JPanel as well as the subpanels that reside within it
   * @param game
   */
  public ViewInfo(GameFacade game)
  {
    // Initialize MODEL
    this.game = game; 
        
    // Inner Classes -------------------------------------------------------------
    
    /**
     * Inner class that implements PlayerStatus interface
     * Will generate and return Human status
     */
    class HumanStatus implements PlayerStatus
    {
      /**
       * Returns String showing info about human portion of MODEL
       * Note that all information requests go through MODEL
       */
      public String getStatus()
      {
        String status = "";
        if (ViewInfo.this.game.hasHuman())
        {
          // Get human status from MODEL
          status = ViewInfo.this.game.aString();
        }
        return status;
      }    
    }
    

    /**
     * Inner class that implements PlayerStatus interface
     * Will generate and return Gruman status
     */
    class GrumanStatus implements PlayerStatus
    {
      /**
       * Returns String showing info about gruman portion of MODEL
       * Note that all information requests go through MODEL
       */
      public String getStatus()
      {
      	String status = "";
          if (ViewInfo.this.game.hasGruman())
          {
            // Get gruman status from MODEL
            status = ViewInfo.this.game.mString();
          }
          return status;
      }
      
    }  
    
    class GameStatus implements PlayerStatus
    {
      /**
       * Returns String showing info about MODEL
       * Note that all information requests go through MODEL
       */
      public String getStatus()
      {
        String status = "Start or load a game";
        if (ViewInfo.this.game.toString()!=null)
        {
          status = ViewInfo.this.game.toString();
      	}
        //System.out.println(status);
        return status;
      }    
    } 
    
	// Create subpanels
    // Note that each takes in an appropriate PlayerStatus object
    viewGame = new ViewSubPanel(new GameStatus());
    viewHuman = new ViewSubPanel(new HumanStatus());
    viewGruman = new ViewSubPanel(new GrumanStatus());
    
    // Set the layout manager of this JPanel to BorderLayout, and add the
    //   human subpanel to the top, and the gruman subpanel to the bottom
    this.setLayout(new GridLayout(3, 1, 0, 0));
    this.add(viewGame);
    this.add(viewHuman);
    this.add(viewGruman);
    
  }

  @Override
  /**
   * Update views.
   */
  public void update(Observable o, Object arg)
  {
    viewGame.updateView();
    viewHuman.updateView();
    viewGruman.updateView();
  }
}
