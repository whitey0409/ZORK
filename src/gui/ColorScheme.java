package gui;

import java.awt.Color;

/**
 * A color scheme for the top panel.
 * 
 * @author Team Plasma
 *
 */
public enum ColorScheme
{
  YELLOW_GREEN(Color.YELLOW, Color.GREEN),
  RED_PINK(Color.RED, Color.PINK),
  ORANGE_YELLOW(Color.ORANGE, Color.YELLOW),
  CYAN_WHITE(Color.CYAN, Color.WHITE);
  
  /**
   * The background color.
   */
  public final Color bg;
  
  /**
   * The foreground (button) color.
   */
  public final Color fg;
  
  ColorScheme(Color bg, Color fg)
  {
    this.bg = bg;
    this.fg = fg;
  }
  
  @Override
  public String toString()
  {
    final String fullname = super.toString();
    final int underscore = fullname.indexOf('_');
    return fullname.substring(0, 1) + fullname.substring(1, underscore).toLowerCase() + "/"
        + fullname.substring(underscore + 1, underscore + 2) + fullname.substring(underscore + 2).toLowerCase();
  }
  
  
}
