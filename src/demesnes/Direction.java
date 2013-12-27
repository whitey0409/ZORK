package demesnes;

/**
 * Final Project
 * @author Team Plasma
 *
 * An enum of Direction, including North, South, East, West
 * Each direction is one unit in an x-y coordinate system
 * 
 */

/**
 * Each in the enum will have coordinate values (x,y), next() and toString() methods
 *
 * next() - the next direction in the enum
 * toString() - the string form of the enum
 */
public enum Direction
{
  NORTH(0,1)
  {
    public Direction next()
    {
      return EAST;
    }
    public String toString()
    {
      return "North";
    }
  },
  EAST(1,0)
  {
    public Direction next()
    {
      return SOUTH;
    }
    public String toString()
    {
      return "East";
    }
  },
  SOUTH(0,-1)
  {
    public Direction next()
    {
      return WEST;
    }
    public String toString()
    {
      return "South";
    }
  },
  
  WEST(-1,0)
  {
    public Direction next()
    {
      return NORTH;
    }
    public String toString()
    {
      return "West";
    }
  };

  /**
   * initialize variables horizontalOffset (x) and vertical Offset (y)
   */
  private int horizontalOffset;
  private int verticalOffset;
  
  /**
   * Direction constructor with values (x,y)
   * @param int horizontal offset h
   * @param int vertical offset h
   */
  private Direction(int h, int v)
  {
    this.horizontalOffset=h;
    this.verticalOffset=v;
  }
  
  /**
   * abstract method invoked in the enum
   * returns the next direction
   */
  public abstract Direction next();
  
  /**
   * returns the horizontal offset
   * @return horizontal offset
   */
  public int getHorizontalOffset()
  {
    return horizontalOffset;
  }
  
  /**
   * returns the vertical offset
   * @return vertical offset
   */
  public int getVerticalOffset()
  {
    return verticalOffset;
  }
  
  /**
   * returns the opposite direction (ex. north -> south)
   * @return direction
   */
  public Direction opposite()
  {
    return this.next().next();
  }
  

}
