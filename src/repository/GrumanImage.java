package repository;

import game.BattleStrategy;
import game.Game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * This class models the image for a Gruman in the game of NewZork
 * 
 * Final Project
 * @author Team Plasma
 * 
 */
public class GrumanImage extends PlayerImage
{  
  //Constructors ---------------------------------------------------------------
  
  /**
   * Create this Gruman image with midpoint at given reference point and
   *    with given color scheme
   * @param strategy TODO
   */
	public GrumanImage(double width, double xCenter, double yCenter, int sacks, BattleStrategy strategy)
	{
		super(width, xCenter, yCenter, sacks, strategy, Color.BLUE, Color.GREEN, Color.CYAN, Color.RED, Color.PINK);
	}
	
	public void draw(Graphics2D g2)
	{
		super.draw(g2);
		if (getStrategy() != BattleStrategy.MASK)
		{
		  drawFrown(g2);
		}
	}

	/**
	 * Draws the frown on a gruman
	 * @param g2
	 */
	private void drawFrown(Graphics2D g2)
	  {
		  final double unitWidth = getUnitWidth();
		  final double MOUTH_LEVEL = 7 * unitWidth / 2;
		  Line2D.Double mouth1 = new Line2D.Double(
		      getXLeft() + 2 * unitWidth, getYTop() + MOUTH_LEVEL, getXLeft() + 2.5 * unitWidth, getYTop() + MOUTH_LEVEL-10);
		  Line2D.Double mouth2 = new Line2D.Double(
			      getXLeft() + 2.5 * unitWidth, getYTop() + MOUTH_LEVEL-10, getXLeft() + 3 * unitWidth, getYTop() + MOUTH_LEVEL);
		  // Make it the color you like
		  g2.setStroke(new BasicStroke(2F));
		  g2.setColor(Color.BLACK);  
		  g2.draw(mouth1); 	
		  g2.draw(mouth2);
	  }
 
}
