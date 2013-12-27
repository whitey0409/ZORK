package demesnes;

import game.BattleStrategy;

import java.util.Map;

/**
 * A Facade of the ChamberFacade class
 * @author Team Plasma
 *
 */

public class ChamberFacade
{
  //Chamber instance variable
  private final Chamber chamber;
  
  /**
   * Facade constructor
   * @param chamber
   */
  public ChamberFacade(final Chamber chamber)
  {
    this.chamber = chamber;
  }

  /**
   * Returns whether or not the facade is visited
   * @return boolean
   */
  public boolean hasVisited()
  {
    return chamber.hasVisited();
  }

  /**
   * Returns whether or not there is a gruman
   * @return boolean
   */
  public boolean hasGruman()
  {
    return chamber.hasGruman();
  }

  /**
   * Returns the location of the facade
   * @return Location
   */
  public Location getLocation()
  {
    return chamber.getLocation();
  }

  /**
   * Returns the wall in the direction parameter given
   * @param direction
   * @return wall
   */
  public Wall getWall(Direction d)
  {
    return chamber.getWall(d);
  }
  
  
}
