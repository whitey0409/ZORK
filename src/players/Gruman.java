package players;

import game.BattleStrategy;

import java.util.Random;
import java.io.IOException;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import repository.GrumanNames;
import repository.Names;

import utility.MathHelper;
import utility.SingleRandom;

/**
 * Team Plasma
 * Boltax, Amanda; Guh, Ten-Young; Hsu, Thomas; Olson, Steve
 * Assignment 12
 * 
 * A monster who lurks in an underground maze of treasure and zorkmids,
 * annoying Humans at the risk of being annoyed itself.
 * 
 * @author Ten-Young Guh
 */
public class Gruman extends Player
{   
  private final int attackDelayMS;
  private final int attackIntervalMS;
  private final int healDelayMS;
  
  /**
   * The maximum number of sacks a Gruman may start with.
   */
  public static final int MAX_SACKS_TO_START = 10;
  
  public static final int MIN_ATTACK_DELAY_MS = 250;
  public static final int MAX_ATTACK_DELAY_MS = 2000;
  
  public static final int MIN_ATTACK_INTERVAL_MS = 125;
  public static final int MAX_ATTACK_INTERVAL_MS = 1000;
  
  public static final int MIN_HEAL_DELAY_MS = 250;
  public static final int MAX_HEAL_DELAY_MS = 2000;
  
  private static final BattleStrategy[] STRATEGIES;
  static
  {
    STRATEGIES = new BattleStrategy[]{null, null, null, null, null, BattleStrategy.SHIELD, BattleStrategy.MEGAPHONE, BattleStrategy.MASK};
  }
  
  
  private final static long serialVersionUID;  
  static
  {
    serialVersionUID = ObjectStreamClass.lookup(Gruman.class).getSerialVersionUID();
  }
  private static final Logger log;
  static
  {
    log = Logger.getLogger(Gruman.class.getName());
  }
  //null means simply use default console handler until we get our own
  private static Handler handler;
  
  /**
   * Instantiates a Gruman with a random name,
   * starting with a random number sacks of treasure,
   * a random health, and a random strength.
   */
  public Gruman()
  {
    this(GrumanNames.getInstance().takeName());
  }
  
  /**
   * Instantiates a Gruman with a valid name,
   * starting with a random number of sacks of treasure,
   * a random health and a random strength.
   * 
   * @param name
   *  the name of the Gruman
   *  
   * @throws NullPointerException
   *  if the name is null
   * @throws IllegalArgumentException
   *  if the name is empty
   */
  public Gruman(final String name)
  {
    this(name,
        SingleRandom.getInstance().nextInt(MAX_SACKS_TO_START + 1),
        1 + SingleRandom.getInstance().nextInt(MAX_HEALTH_POSSIBLE),
        1 + SingleRandom.getInstance().nextInt(MAX_STRENGTH_POSSIBLE));
  }
  
  /**
   * Instantiates a Gruman with a valid name,
   * number of sacks of treasure, strength, and health.
   * 
   * @param name
   *  the name of the Gruman
   * @param sacks
   *  the number of sacks of treasure to start with.
   *  A negative number of sacks binds to zero,
   *  and a number of sacks greater than {@value #MAX_SACKS_TO_START}
   *  binds to {@value #MAX_SACKS_TO_START}.
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
  public Gruman(final String name, int sacks, double health, double strength)
  {
    super(name, Math.min(sacks, MAX_SACKS_TO_START), health, strength,
        STRATEGIES[SingleRandom.getInstance().nextInt(STRATEGIES.length)]);
    
    final SingleRandom rand = SingleRandom.getInstance();
    attackDelayMS = rand.nextInt(MIN_ATTACK_DELAY_MS, MAX_ATTACK_DELAY_MS);
    attackIntervalMS = rand.nextInt(MIN_ATTACK_INTERVAL_MS, MAX_ATTACK_INTERVAL_MS);
    healDelayMS = rand.nextInt(MIN_HEAL_DELAY_MS, MAX_HEAL_DELAY_MS);
 
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
   * Calculates the magnitude of the Gruman's force of terror.
   * 
   * @return the force of terror
   */
  public double terrifyHuman()
  {
    double force = getStrength() * getHealth() / HEALTH_SCALE;
    final BattleStrategy strategy = getStrategy();
    if (strategy != null)
    {
      force = strategy.attack(force);
    }
    //Check value of computation BEFORE return statement:
    log.info("ID = " + ID + ", Roar Force = " + force);
    return force;
  }
  
  /**
   * Suffers from a Human's poke of a given magnitude.
   * 
   * @param force
   *  the magnitude of the Human's poking force
   * @throws IllegalArgumentException
   *  if the force is negative
   */
  public void sufferPoke(double force)
  {    
    if (force < 0.0)
    {
      throw new IllegalArgumentException(
          "Cannot suffer negative force "+ force);
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
   * Return Attack Delay Interval
   * @return
   */
  public int getAttackDelay()
  {
    return attackDelayMS;
  }
  
  /**
   * Return Attack Interval.
   * @return
   */
  public int getAttackInterval()
  {
    return attackIntervalMS;
  }
  
  /**
   * Get Health Delay.
   * @return
   */
  public int getHealDelay()
  {
    return healDelayMS;
  }
  
  @Override
  /**
   * Increment Sacks.
   */
  public void incrementSacks()
  {
    super.setSacks(Math.min(getSacks() + 1, MAX_SACKS_TO_START));
  }
  
  @Override
  /**
   * Set sacks manually.
   */
  public void setSacks(final int sacks)
  {
    super.setSacks(Math.min(sacks, MAX_SACKS_TO_START));
  }
  
}