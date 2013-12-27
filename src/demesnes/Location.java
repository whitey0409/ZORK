package demesnes;

import java.io.Serializable;

/**
 * Final Project
 * @author Team Plasma
 * 
 * A Location with a column and row position
 *
 */

public class Location implements Serializable
{
  
  /**
   * The origin of the maze - a location at (0,0)
   */
  public static final Location ORIGIN = new Location();
	
	/**
	 * Initialize constants
	 */
	private int column;
	private int row;
	
	/**
	 * Constructor with int column and int row
	 * @param column
	 * @param row
	 */
	public Location(int column, int row)
	{
		this.column = column;
		this.row = row;
	}
	
	/**
	 * Basic location constructor (at the origin)
	 */
	public Location()
	{
		this(0, 0);
	}
	
	/**
	 * Location copy constructor
	 * @param location l
	 */
	public Location(Location l)
	{
		this(l.column, l.row);
	}
	
	/**
	 * Location constructor taking into account a direction
	 * @param location l
	 * @param direction d
	 */
	public Location(Location l, Direction d)
	{
		this(l.column+d.getHorizontalOffset(), l.row+d.getVerticalOffset());
	}
	
	/**
	 * returns the column of the location
	 * @return int column
	 */
	public int getColumn()
	{
		return this.column;
	}
	
	/**
	 * returns the row of the location
	 * @return
	 */
	public int getRow()
	{
		return this.row;
	}
	
	/**
	 * returns true if the location is the origin
	 * @return
	 */
	public boolean isOrigin()
	{
		return (row==0 && column==0);
	}
	
	/**
	 * Returns true if the two locations are the same
	 */
	@Override
	public boolean equals(Object otherObject)
	{
		return (this.column==((Location)otherObject).column
				&& this.row==((Location)otherObject).row);
	}
	
	/**
	 * Creates a hashcode based on the column and row
	 */
	@Override
	public int hashCode()
	{
		return (this.column*7+this.row*3);
	}
	
	/**
	 * String representation of the location class
	 */
	public String toString()
	{
		return ("This location has column: "+column+
				" and row: "+row+"\n");
	}
	
	
}
