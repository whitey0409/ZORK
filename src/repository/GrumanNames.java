package repository;

import java.io.ObjectStreamClass;

/**
 * Final Project
 * 
 * A repository of names for Grumans.
 * 
 * @author Team Plasma
 *
 */
public class GrumanNames extends Names
{
  
  private static final String[] NAMES_TO_START
  = {"Inky", "Binky", "Blinky", "Dinky",
    "Jinky", "Pinky", "Slinky", "Stinky", 
    "Blimpy", "Gimpy", "Limpy", "Wimpy",
    "Dorky", "Beeky", "Geeky", "Gadget",
    "Didget", "Fidget", "Gidget", "Widget"};
  // please do not change any element!
  
  private static final GrumanNames instance;
  static
  {
    instance = new GrumanNames();
  }
  
  private final static long serialVersionUID;  
  static
  {
    serialVersionUID
    = ObjectStreamClass.lookup(GrumanNames.class).getSerialVersionUID();
  }

  /**
   * Instantiates a repository
   * of Gruman names.
   */
  private GrumanNames()
  {
    super(NAMES_TO_START);
  }
  
  /**
   * Gets the single instance of the Gruman names repository.
   * 
   * @return the single Gruman names repository
   */
  public static GrumanNames getInstance()
  {
    return instance;
  }
}
