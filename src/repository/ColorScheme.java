package repository;

/**
 * Defines color palette for a player
 *      
 * @author Rose Williams
 */

import java.awt.Color;

public class ColorScheme
{
  //Instance Constants
  public final Color legOutlineColor;
  public final Color legFillColor;
  public final Color faceOutlineColor;
  public final Color faceFillColor;
  public final Color featureOutlineColor;
  public final Color featureFillColor;

  
  // Constructor
  /**
   *  Explicit value constructor
   *  Defines all of the colors needed to define a player
   */
  public ColorScheme(Color legOutlineColor, Color legFillColor, 
                     Color faceOutlineColor, Color faceFillColor, 
                     Color featureOutlineColor, Color featureFillColor)
  {
    this.legOutlineColor = legOutlineColor;
    this.legFillColor = legFillColor;
    this.faceOutlineColor = faceOutlineColor;
    this.faceFillColor = faceFillColor;
    this.featureOutlineColor = featureOutlineColor;
    this.featureFillColor = featureFillColor;
  }
  
  /* 
   *  Copy Constructor
   */
  public ColorScheme(ColorScheme other)
  {
    this.legOutlineColor = other.legOutlineColor;
    this.legFillColor = other.legFillColor;
    this.faceOutlineColor = other.faceOutlineColor;
    this.faceFillColor = other.faceFillColor;
    this.featureOutlineColor = other.featureOutlineColor;
    this.featureFillColor = other.featureFillColor;    
  }
}