package demesnes;

/**
 * Final Project
 * @author Steven Olson
 *
 * Wall enum - a wall is either a door or wall (blank)
 */

/**
 * Enum - a wall is either blank or door
 * @author Steve
 *
 */
public enum Wall {
	
	BLANK("blank")
	{
		public boolean hasDoor() 
		{return false;} 
	},
	DOOR("door")
	{
		public boolean hasDoor() 
		{return true;}
	};
	
	//Initialize instance variable of state
	private String state;
	
	/**
	 * constructs the type of door (blank/door) as string
	 * @param String state
	 */
	private Wall(String state)
	{
		this.state = state;
	}
	
	/**
	 * each value has a hasDoor method, door is true
	 * @return boolean
	 */
	public abstract boolean hasDoor();
	
	/**
	 * toString method
	 */
	public String toString()
	{
		return state;
	}

}
