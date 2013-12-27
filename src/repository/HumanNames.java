package repository;

import java.io.ObjectStreamClass;

/**
 * Final Project
 * 
 * A repository of names for Humans.
 * 
 * @author Team Plasma
 *
 */
public class HumanNames extends Names
{
  
  private static final String[] NAMES_TO_START
  = {"Thutmose", "Darius", "Alexander", "Ch'in", "Pompey", "Julius",
    "Atilla", "Charlemagne", "Hrorekr", "Genghis", "Napoleon", "Naughty"};
  // please do not change any element!
  
  private static final HumanNames instance;
  static
  {
    instance = new HumanNames();
  }
  
  private final static long serialVersionUID;  
  static
  {
    serialVersionUID
    = ObjectStreamClass.lookup(HumanNames.class).getSerialVersionUID();
  }

  /**
   * Instantiates a repository
   * of Human names.
   */
  private HumanNames()
  {
    super(NAMES_TO_START);
  }
  
  /**
   * Gets the single instance of the Human names repository.
   * 
   * @return the single Human names repository
   */
  public static HumanNames getInstance()
  {
    return instance;
  }
}
