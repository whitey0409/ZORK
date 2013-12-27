package utility;
import java.util.Random;

/**
 * Final Project
 * @author Team Plasma
 *
 * Creates a single instance of a pseudo random class
 */

public class SingleRandom
{
	// Static Variables
	  
	  //This is the single instance accessed by members of
	  //  other classes via the getInstance() factory method
	  private static SingleRandom  instance;  
	  static //static initializer:  This instantiates it once
	  {
	    instance = new SingleRandom();
	  }

	  // Instance Variables
	  private Random generator;  //This field remains hidden

	  
	  // Default and other Constructors
	 
	  /**
	   * Default constructor
	   * Creates a private SingleRandom object 
	   * Members of other classes cannot declare and instantiate it
	   */
	  private SingleRandom()
	  {
	    generator = new Random();
	  }
	    
	  // Instance methods 
	  /**
	   * Sets seed for pseudo-random calculation to incoming value
	   * Invoke for testing to get same random series for multiple runs
	   * Just a WRAPPER
	   * @param seed - starting seed for pseudo-random number generator
	   */
	  public void setSeed(int seed)  
	  {
	    generator.setSeed(seed);
	  }

	  
	  /**
	   * Returns pseudo-random integer number in range 0 to range-1
	   * @param range upper limit of random number generated, exclusive
	   * Just a WRAPPER
	   * @return  integer in range 0 to range-1 inclusive
	   */
	  public int nextInt(int range)
	  {
	    return generator.nextInt(range);
	  }
	  
	  /**
	   * Returns an intenger from lower range to upper range
	   * @param lower
	   * @param upper
	   * @return an integer
	   */
	  public int nextInt(int lower, int upper)
	  {
		  return generator.nextInt(upper-lower+1)+lower;
	  }
	  
	  /**
	   * Returns a double from 0 to a given range
	   * @param range
	   * @return a double from 0 to given range
	   */
	  public double nextDouble(double range)
	  {
		  return generator.nextDouble();
	  }
	  
	  /**
	   * Returns a random float as a double
	   * @return double
	   */
	  public double nextFloat()
	  {
		  return generator.nextFloat();
	  }

	  // Static methods  
	  
	  /**
	   * Factory method - gives out reference to single instance of class
	   * Cannot access otherwise
	   * @return reference to random number generator
	   */
	  public static SingleRandom getInstance() 
	  {
	    return instance;
	  }
}
