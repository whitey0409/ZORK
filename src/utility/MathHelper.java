package utility;

/**
 * Final Project
 * @author Team Plasma
 * 
 * A helper class for common calculations.
 *
 */
public final class MathHelper
{

  /**
   * Private constructor to ensure
   * MathHelpler cannot be instantiated.
   */
  private MathHelper()
  {
  }
  
  /**
   * Binds a value to upper and lower bounds.
   * 
   * @param min the lower bound
   * @param max the upper bound
   * @param unbounded the value to bind
   * @return
   *  {@code unbounded} if {@code min <= unbounded <= max},
   *  {@code min} if {@code unbounded < min},
   *  {@code max} if {@code unbounded > max}
   * @throws IllegalArgumentException
   *  if {@code min > max}
   */
  public static double setBounds(final double min, final double max, final double unbounded)
  {
    if (min > max)
    {
      throw new IllegalArgumentException("Lower bound " + min + " should be less than or equal to upper bound " + max);
    }
    return Math.max(Math.min(unbounded, max), min);
  }
  
  /**
   * Binds a value to upper and lower bounds.
   * 
   * @param min the lower bound
   * @param max the upper bound
   * @param unbounded the value to bind
   * @return
   *  {@code unbounded} if {@code min <= unbounded <= max},
   *  {@code min} if {@code unbounded < min},
   *  {@code max} if {@code unbounded > max}
   * @throws IllegalArgumentException
   *  if {@code min > max}
   */
  public static int setBounds(final int min, final int max, final int unbounded)
  {
    if (min > max)
    {
      throw new IllegalArgumentException("Lower bound " + min + " should be less than or equal to upper bound " + max);
    }
    return Math.max(Math.min(unbounded, max), min);
  }
  
  /**
   * Determines if an integer is a power of 2.
   * 
   * @param n the integer
   * @return {@code true} if {@code n} is a power of 2
   */
  public static boolean isPowerOfTwo(int n)
  {
    boolean val= false;
    
    while (n>1 && Math.abs(n) > 0 && n%2==0)
    {
      n = n/2;
      
      if(n==1)
      {
        val = true;
      }
    }
    
    return val;
  }
  
}
