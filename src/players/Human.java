package players;

import game.BattleStrategy;

import java.io.IOException;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import repository.HumanNames;

import utility.MathHelper;
import utility.SingleRandom;

/**
 * Team Plasma
 * Boltax, Amanda; Guh, Ten-Young; Hsu, Thomas; Olson, Steve
 * Assignment 12
 * 
 * A human who searches an underground maze for treasure and zorkmids,
 * may be annoyed by a Gruman, and may annoy a Gruman back.
 * 
 * @author Ten-Young Guh
 */
public class Human extends Player
{  
  
  private static final BattleStrategy[] STRATEGIES;
  static
  {
    STRATEGIES = new BattleStrategy[]{BattleStrategy.SHIELD, BattleStrategy.CANE, BattleStrategy.UMBRELLA};
  }
  
  private final static long serialVersionUID;  
  static
  {
    serialVersionUID = ObjectStreamClass.lookup(Human.class).getSerialVersionUID();
  }
  private static final Logger log;
  static
  {
    log = Logger.getLogger(Human.class.getName());
  }
  private static Handler handler; // null means simply use default console handler until we get our own
  
  /**
   * Instantiates a Human named {@value #DEFAULT_NAME},
   * starting with no sacks of treasure,
   * a random health, and a random strength.
   * 
   */
  public Human()
  {
    this(HumanNames.getInstance().takeName());
  }
  
  /**
   * Instantiates a Human with a valid name,
   * starting with no sacks of treasure,
   * a random health, and a random strength.
   * 
   * @param name
   *  the name of the Human
   *  
   * @throws NullPointerException
   *  if the name is null
   * @throws IllegalArgumentException
   *  if the name is empty
   */
  public Human(final String name)
  {
    this(name,
        1 + SingleRandom.getInstance().nextInt(MAX_HEALTH_POSSIBLE),
        1 + SingleRandom.getInstance().nextInt(MAX_STRENGTH_POSSIBLE) );
  }
  
  /**
   * Instantiates a Human with a valid name,
   * strength, and health, starting with no sacks of treasure.
   * 
   * @param name
   *  the name of the Human
   * @param health
   *  the amount of health to start with.
   *  A strength less than one binds to one,
   *  and a health greater than {@value #MAX_HEALTH_POSSIBLE}
   *  binds to {@value #MAX_HEALTH_POSSIBLE}.
   * @param strength
   *  the amount of strength to start with,
   *  A strength less than one binds to one,
   *  and a strength greater than {@value #MAX_STRENGTH_POSSIBLE}
   *  binds to {@value #MAX_STRENGTH_POSSIBLE}.
   *  
   * @throws NullPointerException
   *  if the name is null
   * @throws IllegalArgumentException
   *  if the name is empty
 */
  public Human(final String name, double health, double strength)
  {
    super(name, 0, health, strength, null);
    final String className = getClass().getName();
    if (handler == null)
    {
      try
      {
        //creates this handler
        //output will go to file named in argument
        Handler tmp = new FileHandler(className + ".txt");
        //SimpleFormatter produces plain text(as opposed to XML)
        tmp.setFormatter(new SimpleFormatter());
        //attach handler for file output
        log.addHandler(tmp);
        handler = tmp;
      }
      catch (IOException e)
      {
        //if exception is thrown, print trace
        e.printStackTrace();
        // just use the console handler instead
      }
    }
    //make sure it's turned on
    log.setLevel(Level.ALL);
    //When finished, can turn off as follows:
    log.setLevel(Level.OFF);
    
    //log information for trace
    log.info("Create " + className + " #" + ID + ": " + this);
  }
  
  /**
   * Calculates the magnitude of the Human's poking force.
   * 
   * @return the force of the poke
   */
  public double pokeGruman()
  {
    double force = getStrength() * getHealth() / HEALTH_SCALE;
    final BattleStrategy strategy = getStrategy();
    if (strategy != null)
    {
      force = strategy.attack(force);
    }
    //Check value of computation BEFORE return statement:
    log.info("ID = " + ID + ", Poke Force = " + force);
    return force;
  }

  
  /**
   * Suffers from a Gruman's terror of a given magnitude
   * 
   * @param force
   *  the magnitude of the Gruman's force of terror
   * @throws IllegalArgumentException
   *  if the force is negative
   */
  public void sufferTerror(double force)
  {    
    if (force < 0.0)
    {
      throw new IllegalArgumentException("Cannot suffer negative force " + force);
    }
    else
    {
      final BattleStrategy strategy = getStrategy();
      if (strategy != null)
      {
        force = getStrategy().suffer(force);
      }
      final double health = getHealth();
      log.info("ID = " + ID + ", Health = " + health);
      setHealth(health - force);
      setStrength(getStrength() - force / STRENGTH_SCALE);
    }
    //Check values AFTER change:
    log.info("ID = " + ID + ", Strength = " + getStrength());
  }
  
  /**
   * Returns whether an object is a Human
   * that has the same state:
   * the same name, number of sacks,
   * health, strength, maximum health,
   * and maximum strength.
   * 
   * @param o the object
   * @return {@code true} if {@code o} is a Human with the same state  
   */
  @Override
  public boolean equals(final Object o)
  {
    boolean isEqual = false;
    if (o != null && getClass() == o.getClass())
    {
      final Human h = (Human)o;
      isEqual = getName().equals(h.getName())
          && getSacks() == h.getSacks()
          && getHealth() == h.getHealth()
          && getStrength() == h.getStrength();
    }
    return isEqual;
  }
  
  @Override
  public int hashCode()
  {
    final int PRIME = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(MAX_HEALTH);
    result = PRIME * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(MAX_STRENGTH);
    result = PRIME * result + (int) (temp ^ (temp >>> 32));
    final String name = getName();
    result = PRIME * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }
  
  
}
