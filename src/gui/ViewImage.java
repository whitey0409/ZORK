package gui;

import game.Game;
import game.GameEvent;
import game.GameFacade;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import java.util.logging.Level;

import repository.ChamberImage;
import repository.GrumanImage;
import repository.HumanImage;
import repository.PlayerImage;
//Additional imports here

// JPanel is a subclass of JComponent and 
//  has more capabilities than JComponent,
//  so we can use it instead of JComponent
public class ViewImage extends JPanel implements Observer
{  
	//-- Class variables --------------------------------------------------------	

	/**
	 * Game model
	 */
	private GameFacade game;
	private final Timer showPokeTimer;
	private final Timer showTerrifyTimer;
	
	public static final int SHOW_ATTACK_MS = 62;
  /**
   * Gets Logger object
   */
	private static Logger log;
  static
  {
    log = Logger.getLogger("ViewerPanel");
  }
  
  
  //-- Instance constants -----------------------------------------------------
  
  // Other declarations here
  
  //-- Constructors -----------------------------------------------------------

  /**
   *  Creates a panel with a given width and height 
   *  @param width
   *  @param height
   */
  public ViewImage(GameFacade game) 
  {
  	setUpLogging();
  	log.info("Viewer Panel");
    this.game = game;
    final ActionListener repaintCallback = new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        repaint();
      }
      
    };
    showPokeTimer = new Timer(SHOW_ATTACK_MS, repaintCallback);
    showPokeTimer.setRepeats(false);
    
    showTerrifyTimer = new Timer(SHOW_ATTACK_MS, repaintCallback);
    showTerrifyTimer.setRepeats(false);
  }

  //-- Class Methods ---------------------------------------------------

  private static void setUpLogging()
  {
    log.setLevel(Level.ALL);     
    //log.setLevel(Level.OFF);
  }  

  //-- Instance Methods -------------------------------------------------------
  // This method is never invoked directly
  // Responsible for "painting" panel
  public void paintComponent(Graphics g)
  {
    log.info("BEGIN paintComponent");
  	// Invoke superclass methods first:  superclass is a JPanel
    super.paintComponent(g);
    
    // Set color and fill rectangle for backdrop
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, getWidth(), getHeight());

    // Cast to Graphics2D
    Graphics2D g2 = (Graphics2D) g; 
    
    //Get width and height
    int width = getWidth();
    int height = getHeight();
    
    ChamberImage mazeView = new ChamberImage(width, height, game, showPokeTimer.isRunning(), showTerrifyTimer.isRunning());
    mazeView.draw(g2);
       
    log.info(String.format("Width is %d, height is %d", width, height));
  } 
  
   
  
  @Override
  /**
   * Update images for poke & terror, and else.
   */
  public void update(Observable o, Object arg)
  {
    if (arg == GameEvent.POKE)
    {
      showPokeTimer.restart();
    }
    else if (arg == GameEvent.TERRIFY)
    {
      showTerrifyTimer.restart();
    }
    else if (arg == GameEvent.RESET || arg == GameEvent.LOAD || arg == GameEvent.MOVE)
    {
      showTerrifyTimer.stop();
      showPokeTimer.stop();
    }
    repaint();
  }


}