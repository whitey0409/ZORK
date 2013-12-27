package repository;

import game.BattleStrategy;
import game.Game;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * This class models the image for a Human in the game of NewZork
 * @author Rose Williams
 */
public class HumanImage extends PlayerImage
{  
  //Constructors ---------------------------------------------------------------
  
  /**
   * Create this Gruman image with midpoint at given reference point and
   *    with given color scheme
   * @param strategy TODO
   */
	public HumanImage(double width, double xCenter, double yCenter, int sacks, BattleStrategy strategy)
	{
	  super(width, xCenter, yCenter, sacks, strategy, Color.YELLOW, Color.BLUE, Color.RED, Color.BLACK, Color.BLACK);
	}

  // Drawing Methods -----------------------------------------------------------  
  
  /**
   * Here are the instructions for drawing this Gruman image
   * @param g2 - Graphics 2D context
   */
	public void draw(Graphics2D g2)
	{
	  drawHair(g2);
	  super.draw(g2);
	  drawSmile(g2);
	} 

  /**
   * Draws the hair on a human
   * @param g2
   */
  private void drawHair(Graphics2D g2)
  {
    final double unitWidth = getUnitWidth();
    Rectangle2D.Double hair = new Rectangle2D.Double(
        getXLeft() + unitWidth, getYTop() - unitWidth / 4, 3 * unitWidth, unitWidth);
    g2.setColor(Color.BLACK);
    g2.fill(hair);
    g2.setColor(Color.DARK_GRAY); // Change pen color  
    g2.draw(hair);
  }
  
  /**
   * Draws the smile on a human
   * @param g2
   */
  private void drawSmile(Graphics2D g2)
  {
	  final double unitWidth = getUnitWidth();
	  final double MOUTH_LEVEL = 7 * unitWidth / 2;
	  Line2D.Double mouth1 = new Line2D.Double(
	      getXLeft() + 2 * unitWidth, getYTop() + MOUTH_LEVEL, getXLeft() + 2.5 * unitWidth, getYTop() + MOUTH_LEVEL+10);
	  Line2D.Double mouth2 = new Line2D.Double(
		      getXLeft() + 2.5 * unitWidth, getYTop() + MOUTH_LEVEL+10, getXLeft() + 3 * unitWidth, getYTop() + MOUTH_LEVEL);
	  // Make it the color you like
      g2.setStroke(new BasicStroke(2F));
	  g2.setColor(Color.BLACK);  
	  g2.draw(mouth1); 	
	  g2.draw(mouth2);
  }
  
}
