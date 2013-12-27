package demesnes;

import game.BattleStrategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import players.Gruman;

/**
 * 
 * Maze Facade
 * 
 * Final Project
 * @author Steve Olson
 *
 */
public class MazeFacade
{
  //Instantiate variables	
  private final Maze maze;
  private final Map<Location, ChamberFacade> chambers;

  /**
   * MazeFacade constructor, takes in a maze object and a current 
   * @param current
   * @param maze
   */
  public MazeFacade(final Location current, final Maze maze)
  {
    this.maze = maze;
    final Map<Location, ChamberFacade> chambers = new HashMap<>();
    final Location north = new Location(current, Direction.NORTH);
    final Location south = new Location(current, Direction.SOUTH);
    final Location[] locations = {current, north, south,
        new Location(current, Direction.EAST), new Location(current, Direction.WEST),
        new Location(north, Direction.EAST), new Location(north, Direction.WEST),
        new Location(south, Direction.EAST), new Location(south, Direction.WEST)};
    
    for (final Location l : locations)
    {
      final Chamber c = maze.getChamber(l);
      if (c != null)
      {
        chambers.put(l, new ChamberFacade(c));
      }
    }
    this.chambers = Collections.unmodifiableMap(chambers);
  }

  /**
   * Returns a ChamberFacade object of a given location
   * @param location
   * @return a chamber facade object
   */
  public ChamberFacade getChamber(final Location l)
  {
    return chambers.get(l);
  }
  
  /**
   * Returns the chambers as a keyset of the maze facade
   * @return keyset of chambers
   */
  public Set<Location> getKeySet()
  {
    return chambers.keySet();
  }
  
  /**
   * Returns the amount of gruman sacks from a given location
   * @param location
   * @return amount of gruman sacks
   */
  public int getGrumanSacks(final Location l)
  {
    return maze.getGrumanSacks(l);
  }
  
  /**
   * Returns the strategy of the Gruman at this location
   * @param location the location of a Gruman
   * @return the Gruman's strategy
   */
  public BattleStrategy getGrumanStrategy(Location location)
  {
    return maze.getGrumanStrategy(location);
  }
}
