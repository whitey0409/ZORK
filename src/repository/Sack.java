package repository;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

import utility.SingleRandom;

public class Sack
{
  // Instance Variables --------------------------------------------------------
  
  // Offsets from midpoint of player
  private int xOffset;
  private int yOffset;
     
  // Constructors --------------------------------------------------------------
  
  /**
   * Creates sack image relative to player position
   * @param unitWidth the unit width of a player
   * @param xLeft - x midpoint position 
   * @param yTop  - y midpoint position
   */
  public Sack(double unitWidth, double centerX, double centerY)
  {
    xOffset = SingleRandom.getInstance().nextInt(
      (int)(centerX), (int)(centerX + unitWidth));
    
    yOffset = SingleRandom.getInstance().nextInt(
        (int)(centerY + 2*unitWidth/3), 
        (int)(centerY + unitWidth));
  }
  
  // Drawing Methods -----------------------------------------------------------
   
  /**
   * Draw sacks instructions  
   * @param g2 Graphics 2D context
   */  
  public void drawSack(Graphics2D g2)
  {
    int x[] = 
      {10,24,22,23,24,36,40,42,40,36,24,8,0,0,4,12};
    int y[] = 
      {0,0,8,10,10,12,20,28, 36,42,44,42,36,20,16,12};
    
    GeneralPath sack = new GeneralPath(GeneralPath.WIND_NON_ZERO, x.length);
    sack.moveTo(x[0] + xOffset, (y[0] + yOffset));
    for (int i = 0; i < x.length-1; i++)
    {
      sack.quadTo(x[i] + xOffset, y[i]+ yOffset, 
                       x[i+1] + xOffset, y[i+1]+ yOffset);
    }
    sack.quadTo(x[x.length-1] + xOffset, y[y.length-1] + yOffset, 
                     x[0] + xOffset, y[0] + yOffset);
    g2.setStroke(new BasicStroke());
    g2.setColor(new Color(156, 93, 82));  //Brown
    g2.fill(sack);
    g2.setColor(Color.YELLOW);  
    g2.draw(sack);
  }
}
