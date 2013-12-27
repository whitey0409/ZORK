package repository;

import game.BattleStrategy;
import game.Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
//Put additional imports here
//i.e., shapes, lines, points, etc.

// Nested packages
// Note that importing java.awt.geom would NOT include classes
// Inner classes
// Note that we do NOT import the inner class
import java.awt.geom.Arc2D; // We will be using Arc2D.Double
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import demesnes.Maze;


/**
 * This class models a shield in the game of NewZork
 * 
 * @author Steven Olson
 *
 */
public class ClipImage
{

  /**
   * Create Player Image object with x,y reference point
   * This reference point starts at the middle of the player
   */
	// Instance Variables --------------------------------------------------------
	  private BufferedImage image;
	  private double xLeft;
	  private double yTop;
	  
	  
	  // Constructors --------------------------------------------------------------
	  
	  public ClipImage(double xLeft, double yTop, BattleStrategy strategy)
	  {
	    String name = strategy.toString();
	    try
	    {
	      image = ImageIO.read(new File(name.substring(0, 1) + name.substring(1).toLowerCase() + ".png"));
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    this.xLeft = xLeft;
	    this.yTop = yTop;
	  }
	    
	  // Instance methods ----------------------------------------------------------
	  
	  // Accessors -----------------------------------------------------------------

	  /**
	   * Gets x coordinate of reference point
	   * @return x coordinate
	   */
	  public double getXLeft()
	  {
	    return xLeft; 
	  }
	  
	  /**
	   * Gets y coordinate of reference point
	   * @return y coordinate
	   */
	  public double getYTop()
	  {
	    return yTop;
	  }
	  
	 
	  
	  /**
	   * Draw player image instructions  
	   * @param g2 Graphics 2D context
	   */
	  public void draw(Graphics2D g2)
	  {
	    g2.drawImage(image, null, (int)xLeft, (int)yTop);
	  }

	 
}