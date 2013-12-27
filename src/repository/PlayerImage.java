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

import demesnes.Maze;


/**
 * This class models a generic player in the game of NewZork
 * 
 * @author Steven Olson
 *
 */
public class PlayerImage
{

  /**
   * Create Player Image object with x,y reference point
   * This reference point starts at the middle of the player
   */
	// Instance Variables --------------------------------------------------------
	  private double scale;
	  private double unitWidth;  
	  private Color faceColor;
	  private Color legColor;
	  private Color armColor;
	  private Color eyeColor;
	  private Color mouthColor; 
	  private int sacks;
	  private double xLeft;
	  private double yTop;
	  private double xCenter;
	  private double yCenter;
	  private BattleStrategy strategy;
	  
	  
	  // Constructors --------------------------------------------------------------
	  
	  public PlayerImage(double width, double xCenter, double yCenter, int sacks,
	      BattleStrategy strategy, Color faceColor, Color legColor, Color armColor, Color eyeColor, Color mouthColor)
	  {
		this.scale = width/14;
		this.sacks = sacks;
		this.unitWidth = scale/5;
		this.xCenter = xCenter;
		this.yCenter = yCenter;
	    this.xLeft = xCenter + scale / 2;
	    this.yTop = yCenter - scale / 2;
	    this.faceColor = faceColor;
	    this.legColor = legColor;
	    this.armColor = armColor;
	    this.eyeColor = eyeColor;
	    this.mouthColor = mouthColor;
	    this.strategy = strategy;
	  }
	  
	  /**
	   * Create Player Image object with center of the player's face
	   * @param strategy TODO
	   */
	  public PlayerImage(double width, double xCenter, double yCenter, int sacks, BattleStrategy strategy)
	  {
	    this(width, xCenter, yCenter, sacks, null, Color.WHITE, Color.GRAY, Color.WHITE, Color.DARK_GRAY, Color.DARK_GRAY);
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
	   * Gets xCenter
	   * @return x center
	   */
	  public double getXCenter()
	  {
		  return xCenter;
	  }
	  
	  /**
	   * Gets yCenter
	   * @return y center
	   */
	  public double getYCenter()
	  {
		  return yCenter;
	  }
	  
	  /**
	   * Returns the unit Width of the map
	   * @return unit width
	   */
	  public double getUnitWidth()
	  {
		  return unitWidth;
	  }
	  
	  /**
	   * Returns the strategy of this image
	   * @return strategy
	   */
	  public BattleStrategy getStrategy()
	  {
	    return strategy;
	  }
	  
	  /**
	   * Draw player image instructions  
	   * @param g2 Graphics 2D context
	   */
	  public void draw(Graphics2D g2)
	  {
		BasicStroke penWidth = new BasicStroke(2);
	    g2.setStroke(penWidth);

	    drawFace(g2);
	    drawLegs(g2);
	    drawArms(g2);
	    drawEyes(g2);  
	    //drawMouth(g2);
	    drawAccessories(g2);

	  }

	  // Instance Helper Methods ---------------------------------------------------

	  /**
	   * Draw legs instructions  
	   * @param g2 Graphics 2D context
	   */  
	  private void drawLegs(Graphics2D g2)
	  {
	    final double DIAMETER = unitWidth;
	    final double LEG_LEVEL = 4 * unitWidth;
	    
	    Ellipse2D.Double leg1 =
	        new Ellipse2D.Double(xLeft, yTop + LEG_LEVEL, DIAMETER, DIAMETER);
	    Ellipse2D.Double leg2 =
	        new Ellipse2D.Double(xLeft+ 4 * DIAMETER, yTop + LEG_LEVEL, DIAMETER, DIAMETER);
	    
	    g2.setStroke(new BasicStroke(1.5F));
	    g2.setColor(legColor);
	    
	    g2.fill(leg1);
	    g2.fill(leg2);
	    g2.setColor(Color.DARK_GRAY);
	    g2.draw(leg1);
	    g2.draw(leg2);
	  }
	  
	  /**
	   * Draw face instructions  
	   * @param g2 Graphics 2D context
	   */  
	  private void drawFace(Graphics2D g2)
	  {	
	  	Ellipse2D.Double face = 
	  	new Ellipse2D.Double(xLeft, yTop, scale, scale); 
	  	
	    g2.setColor(faceColor);
	    
	    g2.fill(face); 
	    g2.setColor(Color.DARK_GRAY);
	    g2.draw(face); 
	  }

	  /**
	   * Draw eyes instructions  
	   * Can leave out for generic player
	   * @param g2 Graphics 2D context
	   */
	  private void drawEyes(Graphics2D g2)
	  {
	  	final double EYE_WIDTH = unitWidth / 2;
	  	final double EYE_LEVEL = 2 * unitWidth - EYE_WIDTH;
	    
	    // Use ellipse to model
	    Ellipse2D.Double leftEye = new Ellipse2D.Double(
	      xLeft + unitWidth + (unitWidth - EYE_WIDTH) / 2, yTop + EYE_LEVEL, EYE_WIDTH, EYE_WIDTH);
	    Ellipse2D.Double rightEye = new Ellipse2D.Double(
	      xLeft + 3 * unitWidth + (unitWidth - EYE_WIDTH) / 2, yTop + EYE_LEVEL, EYE_WIDTH, EYE_WIDTH);
	    
	    // Set pen color
	    g2.setColor(eyeColor); 
	    
	    g2.fill(leftEye);
	    g2.fill(rightEye);
	    g2.setColor(Color.DARK_GRAY);
	    g2.draw(leftEye);
	    g2.draw(rightEye);
	  }
	  
	  /**
	   * Draw mouth instructions  
	   * Can leave this out for generic player
	   * @param g2 Graphics 2D context
	   */
	  private void drawMouth(Graphics2D g2)
	  {
	    final double MOUTH_LEVEL = 7 * unitWidth / 2;
	    Line2D.Double mouth = new Line2D.Double(
	        xLeft + 2 * unitWidth, yTop + MOUTH_LEVEL, xLeft + 3 * unitWidth, yTop + MOUTH_LEVEL);
	    g2.setColor(mouthColor);  
	    g2.draw(mouth); 	
	  }
	  
	  /**
	   * Draw arm instructions
	   * @param g2 Graphics 2D context
	   */
	  private void drawArms(Graphics2D g2)
	  {
	    final double DIAMETER = unitWidth;
	    final double ARM_LEVEL = 2 * unitWidth;
	    
	    Ellipse2D.Double arm1 =
	        new Ellipse2D.Double(xLeft - unitWidth, yTop + ARM_LEVEL, DIAMETER, DIAMETER);
	    Ellipse2D.Double arm2 =
	        new Ellipse2D.Double(xLeft + scale, yTop + ARM_LEVEL, DIAMETER, DIAMETER);
	    
	    g2.setStroke(new BasicStroke(1.5F));
	    g2.setColor(armColor);
	    
	    g2.fill(arm1);
	    g2.fill(arm2);
	    g2.setColor(Color.DARK_GRAY);
	    g2.draw(arm1);
	    g2.draw(arm2);
	  }
	  

	  
	  /**
	   * Draw accessories instructions  
	   * @param g2 Graphics 2D context
	   */  
	  private void drawAccessories(Graphics2D g2)
	  {
	    for (int i = 0; i < sacks; i++)
	    {
	      Sack sack = new Sack(unitWidth, getXCenter(), getYCenter());
	      sack.drawSack(g2);
	    }
	    if (strategy != null)
	    {
	      drawStrategy(g2);
	    }
	  }
	  
	  /**
	   * Draw strategy instructions
	   * @param g2 Graphics2D context
	   */
	  private void drawStrategy(Graphics2D g2)
	  {
	    double x;
	    if (strategy == BattleStrategy.MASK)
	    {
	      x = xLeft - unitWidth * 1.375;
	    }
	    else if (strategy == BattleStrategy.MEGAPHONE || strategy == BattleStrategy.SHIELD)
	    {
	      x = xLeft + unitWidth * 2;
	    }
	    else
	    {
	      x = xLeft + unitWidth;
	    }
	    new ClipImage(x, yTop - unitWidth * 2, strategy).draw(g2);
	  }
	 
}