package game;

import java.io.Serializable;

import players.Player;
import utility.SingleRandom;

/**
 * Team Plasma
 * Boltax, Amanda; Guh, Ten-Young; Hsu, Thomas; Olson, Steve
 * Assignment 12
 *  
 * A Player's strategy in attacking and being attacked.
 * 
 * @author Ten-Young Guh
 *
 */
public enum BattleStrategy
{
  /**
   * A strategy that deflects a force sometimes.
   */
  SHIELD
  {
    public static final double DEFLECT_ODDS = 0.3;
    
    /**
     * Leaves the attack force unamplified.
     * 
     * @param force the attack force
     * @return {@code force}
     */
    @Override
    public double attack(final double force)
    {
      return force;
    }

    /**
     * Deflects force some of the time.
     * 
     * @param force the force to deflect
     * @return either 0 or {@code force}
     */
    @Override
    public double suffer(final double force)
    {
      double deflectedForce = force;
      if (SingleRandom.getInstance().nextDouble(1.0) < DEFLECT_ODDS)
      {
        deflectedForce = 0;
      }
      return deflectedForce;
    }
  },
  /**
   * A strategy that amplifies a poke.
   */
  CANE
  {
    /**
     * The force by which this cane amplies a poke.
     */
    public static final double AMPLIFIER = 1.5;
    
    /**
     * Amplifies the force by {@value #AMPLIFIER}.
     * 
     * @param force the force to amplify
     * @return {@code {@value #AMPLIFIER} * force}
     */
    @Override
    public double attack(double force)
    {
      return AMPLIFIER * force;
    }

    /**
     * Does not deflect suffered force.
     * 
     * @param force the suffered force
     * @return {@code force}
     */
    @Override
    public double suffer(double force)
    {
      return force;
    }
  },
  /**
   * A strategy that amplifies a poke.
   */
  UMBRELLA
  {
    /**
     * The force by which this umbrella amplies a poke.
     */
    public static final double AMPLIFIER = 3.0;
    
    /**
     * Amplifies the force by {@value #AMPLIFIER}.
     * 
     * @param force the force to amplify
     * @return {@code {@value #AMPLIFIER} * force}
     */
    @Override
    public double attack(double force)
    {
      return AMPLIFIER * force;
    }

    /**
     * Does not deflect suffered force.
     * 
     * @param force the suffered force
     * @return {@code force}
     */
    @Override
    public double suffer(double force)
    {
      return force;
    }
  },
  /**
   * A strategy that amplifies a roar.
   */
  MEGAPHONE
  {
    /**
     * The force by which this megaphones amplies a roar.
     */
    public static final double AMPLIFIER = 1.5;
    
    /**
     * Amplifies the force by {@value #AMPLIFIER}.
     * 
     * @param force the force to amplify
     * @return {@code {@value #AMPLIFIER} * force}
     */
    @Override
    public double attack(double force)
    {
      return AMPLIFIER * force;
    }

    /**
     * Does not deflect suffered force.
     * 
     * @param force the suffered force
     * @return {@code force}
     */
    @Override
    public double suffer(double force)
    {
      return force;
    }
  },
  /**
   * A strategy to amplify a roar. 
   */
  MASK
  {
    /**
     * The force by which this mask amplies a roar.
     */
    public static final double AMPLIFIER = 2.5;
    
    /**
     * Amplifies the force by {@value #AMPLIFIER}.
     * 
     * @param force the force to amplify
     * @return {@code {@value #AMPLIFIER} * force}
     */
    @Override
    public double attack(double force)
    {
      return AMPLIFIER * force;
    }

    /**
     * Does not deflect suffered force.
     * 
     * @param force the suffered force
     * @return {@code force}
     */
    @Override
    public double suffer(double force)
    {
      return force;
    }
  };
  
	
	/**
	 * Transform an attack force.
	 * 
	 * @param the initial attack force
	 * @return the final attack force
	 */
	public abstract double attack(double force);
	
	/**
	 * Transform a suffered force.
	 * 
	 * @param force the initial force to suffer
	 * @return the final force to suffer
	 */
	public abstract double suffer(double force);
	
	@Override
	public String toString()
	{
	  final String name = super.toString();
	  return name.substring(0, 1) + name.substring(1).toLowerCase();
	}

}
