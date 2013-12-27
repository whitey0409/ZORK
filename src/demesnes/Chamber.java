package demesnes;

import game.BattleStrategy;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import utility.SingleRandom;
import demesnes.Location;

/**
 * Final Project
 * @author Team Plasma
 * 
 * A chamber object that has 3 walls and a door, and possibly a gruman
 *
 */

public class Chamber implements Serializable
{
	
  public static final int NONE = -1;
  
	/**
	 * Initialize instance variables
	 */
	private boolean visited;
	private Location location;
	private int grumanID;
	private BattleStrategy strategy;
	private final Map<Direction, Wall> walls;
	
	/**
	 * Constructor
	 */
	public Chamber()
	{
		final Map<Direction, Wall> walls = new EnumMap<>(Direction.class);
		for (Direction d : Direction.values())
		{
			walls.put(d, Wall.BLANK);
		}
		
		int pick = SingleRandom.getInstance().nextInt(walls.size());
		walls.put(Direction.values()[pick], Wall.DOOR);
		
		this.walls = Collections.unmodifiableMap(walls);
		this.location=new Location();
		this.visited=false;
		this.grumanID=NONE;
		strategy = null;
	}
	
	/**
	 * Explicit constructor
	 * @param enumMap walls
	 * @param location
	 * @param int id
	 */
	public Chamber(EnumMap<Direction, Wall> walls, Location l, int i, BattleStrategy strategy)
	{
		this.walls=Collections.unmodifiableMap(walls.clone());
		this.location = l;
		this.visited=false;
		this.grumanID=i;
		this.strategy = strategy;
	}
	
	/**
	 * returns the location of the chamber
	 * @return location
	 */
	public Location getLocation()
	{
		return this.location;
	}
	
	/**
	 * returns the id of the gruman in the chamber
	 * @return grumanID
	 */
	public int getGrumanID()
	{
		return this.grumanID;
	}
	
	/**
	 * returns whether or not the chamber has been visited
	 * @return boolean visited
	 */
	public boolean hasVisited()
	{
		return this.visited;
	}
	
	/**
	 * returns whether or not the chamber has a gruman
	 * @return boolean
	 */
	public boolean hasGruman()
	{
		return this.grumanID!=NONE;
	}
	
	/**
	 * returns whether or not the wall in direction d is a door
	 * @param direction
	 * @return boolean
	 */
	public boolean hasDoor(Direction d)
	{
		return walls.get(d)==Wall.DOOR;
	}
	
	/**
	 * returns the type of wall in direction d
	 * @param direction
	 * @return Wall.BLANK or Wall.DOOR
	 */
	public Wall getWall(Direction d)
	{
		return walls.get(d);
	}
	
	public Map<Direction, Wall> getWalls()
	{
	  return walls;
	}
	
	/**
	 * Returns the power-up strategy at this chamber.
	 * @return the power-up strategy
	 */
	public BattleStrategy getStrategy()
	{
	  //System.out.println(strategy);
	  return strategy;
	}
	
	/**
	 * returns whether or not the chamber is location at the origin
	 * @return boolean
	 */
	public boolean isOrigin()
	{
		return location.isOrigin();
	}
	
	/**
	 * sets the chamber to visited by the player
	 */
	public void setVisited()
	{
		this.visited = true;
		strategy = null;
	}
	
	/**
	 * toString method
	 * @return String
	 */
	public String toString()
	{
		String s = "";
		
		for (Direction d : Direction.values())
		{
			s += "Wall in direction: "+d+" is "+walls.get(d).name()+"\n";
		}
		
		return s;
	}
	
	
}
