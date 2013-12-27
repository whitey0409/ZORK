package demesnes;

import game.BattleStrategy;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import players.Gruman;
import utility.MathHelper;
import utility.SingleRandom;

/**
 * Final Project
 * @author Steven Olson
 * 
 * A maze containing 5 chamber objects, with doors connecting them
 * Each chamber may or may not hold a gruman depending on gruman odds
 *
 */
public class Maze implements Serializable
{
	
	
	/**
	 * The minimum number of chambers.
	 */
  public static final int MIN_NUMBER_CHAMBERS = 5;
  
	private static final int NONE = Chamber.NONE;
	private static final int GRUMAN_ODDS = 3;
	private static final int DOOR_ODDS = 3;
	
	/**
	 * Initialize instance variables
	 */
	private int chambersLeft;
	private int minChambersLeft;
	private int counter;
	private static Logger log;
	static{
	    setUpLogging();
	}
	private Map<Location, Chamber> map;
	private ArrayList<Gruman> grumans;
	private final int whereShield;
	private final int whereCane;
	private final int whereUmbrella;
	

	/**
	 * Maze constructor, creates a maze with a given amount of chambers
	 * @param int
	 */
	public Maze(int n)
	{
		counter = 0;
		chambersLeft = n-1;
		minChambersLeft = MIN_NUMBER_CHAMBERS;
		int grumanCount = 0;
		int grumanID;
		ArrayList<Location> toDoList = new ArrayList<Location>();
		final Map<Location, Chamber> map = new HashMap<>();
		grumans = new ArrayList<Gruman>();
		Chamber entrance = this.createEntranceChamber(toDoList);
		this.addToMap(map, entrance);
		
		whereShield = n / 4;
		whereCane = n / 2;
		whereUmbrella = 3 * n / 4;
		
		while (!toDoList.isEmpty())
		{
			grumanID = NONE;
			if (SingleRandom.getInstance().nextInt(GRUMAN_ODDS)>0)
			{
				grumans.add(new Gruman());
				grumanID = grumanCount;
				grumanCount++;
			}
			addToMap(map, createRemainingChambers(map, toDoList, grumanID));
		}
		
		showFinishedMaze(map);
		this.map = Collections.unmodifiableMap(map);
	}
		
	/**
	 * Predicate method whether or not a location has a chamber
	 * @param l
	 * @return true/false
	 */
	public boolean hasChamber(Location l)
	{
		return map.containsValue(l);
	}
	
	/**
	 * Returns the keySet of the map
	 * @return KeySet
	 */
	public Set<Location> getKeySet()
	{
		return map.keySet();
	}
	
	/**
	 * Returns the gruman in a chamber
	 * @param l
	 * @return Gruman
	 */
	public Gruman getGruman(Location l)
	{
	  Gruman gruman = null;
	  final Chamber c = getChamber(l);
	  if (c != null)
	  {
  	  final int id = c.getGrumanID();
  	  if (id != NONE)
  	  {
  	    gruman = grumans.get(id);
  	  }
	  }
		return gruman;
	}
	
	/**
	 * Returns the amount of sacks a gruman in a chamber has
	 * @param location
	 * @return number of sacks
	 */
	public int getGrumanSacks(Location l)
	{
	  int sacks = -1;
	  final Gruman g = getGruman(l);
	  if (g != null)
	  {
	    sacks = g.getSacks();
	  }
	  return sacks;
	}
	
	/**
	 * Returns the amount of chambers in the maze
	 * @return total number of chambers
	 */
	public int getTotalChambers()
	{
		return getKeySet().size();
	}
	
	/**
	 * Returns the amount of chambers not yet visited in the maze
	 * @return number of chambers unvisited
	 */
	public int getChambersLeft()
	{
		Set<Location> keySet = getKeySet(); 
		ArrayList<Location> keyList = new ArrayList<Location>(keySet);
		int count = keySet.size();
		  
		for (Location key : keyList)
		{ 
		Chamber chamber = getChamber(key);
		if(chamber.hasVisited())
		{
			count--;
		}
		}
		return count;
	}
	
	/**
	 * Returns the strategy at this location
	 * @param location the location of a chamber
	 * @return the strategy at this chamber
	 */
	public BattleStrategy getStrategy(Location location)
	{
	  return map.get(location).getStrategy();
	}
	
	/**
	 * Returns the strategy of the Gruman at this location
	 * @param location the location of a Gruman
	 * @return the Gruman's strategy
	 */
	public BattleStrategy getGrumanStrategy(Location location)
	{
	  final Gruman g = getGruman(location);
	  BattleStrategy strategy = null;
	  if (g != null)
	  {
	    strategy = g.getStrategy();
	  }
	  return strategy;
	}

  /**
   * Sets a chamber on the maze to visited
   * @param location
   */
  public void visit(final Location location)
  {
	 map.get(location).setVisited();
  }

  /**
   * Returns the walls of a given chambers
   * @param location
   * @return Walls of a chamber
   */
  public Map<Direction, Wall> getWalls(Location location)
  {
    return getChamber(location).getWalls();
  }
  
  /**
   * Returns whether or not there is a door in the direction at a given chamber
   * @param dir
   * @param location
   * @return boolean
   */
  public boolean hasDoorAt(Direction dir, Location location)
  {
    return getChamber(location).hasDoor(dir);
  }
	
  /**
   * Returns the chamber from a certain location
   * @param l
   * @return chamber
   */
  public Chamber getChamber(Location l)
  {
    return map.get(l);
  }
  
	
  /**
   * Sets up the logger for the maze, to Maze.txt
   */
  private static void setUpLogging()
  {
    log = Logger.getLogger("Maze");
    try
    {
      Handler handler = new FileHandler("Maze.txt");
      log.addHandler(handler);
    }
    catch(IOException exception)
    {
      exception.printStackTrace();
    }
    
    log.setLevel(Level.ALL);
  }
  
  /**
   * Creates the entrance chamber for the maze at the origin
   * @param toDoList
   * @return Chamber
   */
  private Chamber createEntranceChamber(ArrayList<Location> toDoList)
  {
    Chamber c = new Chamber();
    chambersLeft--;
    for (Direction d : Direction.values())
    {
      if (c.hasDoor(d))
      {
        toDoList.add(new Location(c.getLocation(), d));
      }
    }
    c.setVisited();
    return c;
  }
  
  
  /**
   * Creates the remaining chambers for the maze, these may or may not have grumans
   * @param map TODO
   * @param toDoList
   * @param grumanID
   * @return Chamber with walls, location, and grumanIDs
   */
  private Chamber createRemainingChambers(Map<Location, Chamber> map, ArrayList<Location> toDoList, int grumanID)
  {
    final int where = counter;
    Location l = toDoList.remove(toDoList.size() - 1);
    EnumMap<Direction, Wall>walls = new EnumMap<Direction,Wall>(Direction.class);
    
    for (Direction d : Direction.values())
    {
      Location neighbor = new Location(l, d);
      if (map.containsKey(neighbor))
      {
        if (map.get(neighbor).getWall(d.opposite())==Wall.DOOR)
        {
          walls.put(d, Wall.DOOR);
        }
        else
        {
          walls.put(d, Wall.BLANK);
        }
      }
      else if(chambersLeft>0 && (SingleRandom.getInstance().nextInt(DOOR_ODDS)>0 || minChambersLeft > 0))
      {
        walls.put(d, Wall.DOOR);
        if (!toDoList.contains(neighbor))
        {
          toDoList.add(neighbor);
          chambersLeft--;
          minChambersLeft--;
        }
      }
      else
      {
        walls.put(d, Wall.BLANK);
      }
    }
    
    BattleStrategy strategy = null;
    if (where == whereShield)
    {
      //log.info("Here's the shield");
      strategy = BattleStrategy.SHIELD;
    }
    else if (where == whereCane)
    {
      //log.info("Here's the cane");
      strategy = BattleStrategy.CANE;
    }
    else if (where == whereUmbrella)
    {
      //log.info("Here's the umbrella");
      strategy = BattleStrategy.UMBRELLA;
    }
    return new Chamber(walls, l, grumanID, strategy);
  }
  
  /**
   * Adds the chamber to the HashMap map
   * @param map
   * @param c
   */
  private void addToMap(Map<Location, Chamber> map, Chamber c)
  {
    map.put(c.getLocation(), c);
    counter++;
  }
  
  /**
   * Sends the finished maze to the logger as a string
   * @param map
   */
  private void showFinishedMaze(Map<Location, Chamber> map)
  {
    log.info(map.toString());
  }
  
	
/**
 * toString method
 * @return String representation of Maze
 */
  public String toString()
  {
	return "Chambers Left: "+chambersLeft+"\n"+
			"Count: "+counter+"\n"+
			"Map: "+map.toString()+"\n"+
			"Grumans: "+grumans.toString();
  }
  
}
