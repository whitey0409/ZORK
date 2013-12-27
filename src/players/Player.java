package players;

import demesnes.Location;

import game.BattleStrategy;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import utility.MathHelper;
import utility.SingleRandom;

/**
 * Team Plasma
 * Boltax, Amanda; Guh, Ten-Young; Hsu, Thomas; Olson, Steve
 * Assignment 12
 * 
 * A being in an underground maze of treasure and zorkmids.
 * 
 * Class invariants: {@code getName() != null && !getName().isEmpty()},
 * {@code getSacks() >= 0},
 * {@code 0 <= getHealth() && getHealth() <= MAX_HEALTH
 * && MAX_HEALTH <= MAX_HEALTH_POSSIBLE},
 * {@code 0 <= getStrength() && getStrength() <= MAX_STRENGTH
 * && MAX_STRENGTH <= MAX_STRENGTH_POSSIBLE},
 * 
 * @author Ten-Young Guh
 *
 */
//@SuppressWarnings("serial") // subclasses will declare the UID
public abstract class Player implements Serializable
{
  /**
   * The Player's maximum ability to annoy,
   * ranging from 1
   * to {@value #MAX_HEALTH_POSSIBLE}.
   */
  public final double MAX_HEALTH;
  
  /**
   * The Player's maximum ability to be annoyed,
   * ranging from 1
   * to {@value #MAX_STRENGTH_POSSIBLE}.
   */
  public final double MAX_STRENGTH;
  
  /**
   * The Player's ID number.
   */
  public final int ID;
  
  private String name;
  private BattleStrategy strategy;
  private int sacks; // number of sacks of treasure
  private double health; // from 0 to MAX_HEALTH
  private double strength; // from 0 to MAX_STRENGTH
  
  /**
   * The scale of a Player's ability to annoy.
   */
  public static final double HEALTH_SCALE = 100.0;
  
  /**
   * The scale of a Player's ability to be annoyed.
   */
  public static final double STRENGTH_SCALE = 10.0;
  
  /**
   * The Players' maximum ability to annoy.
   */
  public static final int MAX_HEALTH_POSSIBLE = (int)HEALTH_SCALE;
  
  /**
   * The Players' maximum ability to be annoyed.
   */
  public static final int MAX_STRENGTH_POSSIBLE = (int)STRENGTH_SCALE;
  
  private static int counter = 1;
  
  /**
   * Instantiates a Player with a valid name,
   * number of sacks of treasure, strength, and health.
   * 
   * @param name
   *  the name of the Player
   * @param sacks
   *  the number of sacks of treasure to start with.
   *  A negative number of sacks binds to zero.
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
   * @param strategy
   *  the possible strategies
   * @throws NullPointerException
   *  if {@code name == null} or {@code strategies == null}
   * @throws IllegalArgumentException
   *  if {@code name.isEmpty()} or {@code strategies.length == 0}
 */
  public Player(final String name, int sacks, double health, double strength, final BattleStrategy strategy)
  {
    final String className = Player.class.getSimpleName();
    
    if (Objects.requireNonNull(name, className + " cannot have a null name").isEmpty() )
    {
      throw new IllegalArgumentException(className + " cannot have empty name");
    }
    
    ID = counter;
    this.name = name;
    this.sacks = Math.max(0, sacks);
    this.health = MAX_HEALTH = MathHelper.setBounds(1, MAX_HEALTH_POSSIBLE, health);
    this.strength = MAX_STRENGTH = MathHelper.setBounds(1, MAX_STRENGTH_POSSIBLE, strength);
    this.strategy = strategy;
    counter++;
    
  }
  
  /**
   * Gets the Player's name.
   * 
   * @return the Player's name
   */
  public String getName()
  {
    return name;
  }
  
  /**
   * Gets the number of sacks of treasure the Player has.
   * 
   * @return the number of sacks
   */
  public int getSacks()
  {
    return sacks;
  }
  
  /**
   * Increment the number of sacks, if possible.
   */
  public void incrementSacks()
  {
   sacks += sacks == Integer.MAX_VALUE? 0 : 1;
  }
  
  /**
   * Decrement the number of sacks, if any.
   */
  public void decrementSacks()
  {
    sacks = Math.max(sacks - 1, 0);
  }
  
  public void setName(String s)
  {
	  this.name = s;
  }
  
  /**
   * Gets the current health
   * as the amount of annoyance the Player can suffer,
   * ranging from 0.0 to the Player's {@link #MAX_HEALTH}.
   * 
   * @return the current health
   */
  public double getHealth()
  {
    return health;
  }
  
  /**
   * Gets the current strength
   * as the amount of annoyance the Player can cause,
   * ranging from 0.0 to the Player's {@link #MAX_STRENGTH}.
   * 
   * @return the current strength
   */
  public double getStrength()
  {
    return strength;
  }
  
  /**
   * Gets the Player's battle strategy.
   * 
   * @return the battle strategy
   */
  public BattleStrategy getStrategy()
  {
    return strategy;
  }
  
  /**
   * Returns whether the Player has any sacks.
   * 
   * @return
   *  {@code true} if the Player has one or more sacks,
   *  {@code false} if the Player has zero sacks
   */
  public boolean hasSacks()
  {
    return sacks > 0;
  }
 
  /**
   * Returns whether the Player has any strength.
   * 
   * @return
   *  {@code true} if the Player has a strength greater than 0.0,
   *  {@code false} if the Player has a strength of 0.0
   */
  public boolean hasStrength()
  {
    return strength > 0.0;
  }
  
  /**
   * Returns whether the Player has any health.
   * 
   * @return
   *  {@code true} if the Player has a health greater than 0.0,
   *  {@code false} if the Player has a health of 0.0
   */
  public boolean hasHealth()
  {
    return health > 0.0;
  }
  
  /**
   * Sets the Player's number of sacks.
   * 
   * @param sacks the new number of sacks.
   *  Binds to 0 if less than 0,
   *  to {@value #MAX_SACKS_TO_START}
   *  if greater than {@value #MAX_SACKS_TO_START}.
   */
  public void setSacks(int sacks)
  {
    this.sacks = Math.max(sacks, 0);
  }
  
  /**
   * Sets the Player's health.
   * 
   * @param health the new health.
   *  Binds to 0 if less than 0,
   *  to {@link #MAX_HEALTH} if greater than {@link #MAX_HEALTH}.
   */
  public void setHealth(double health)
  {
    this.health = MathHelper.setBounds(0, MAX_HEALTH, health);
  }
  
  /**
   * Sets the Player's strength.
   * 
   * @param strength the new strength.
   *  Binds to 0 if less than 0,
   *  to {@link #MAX_STRENGTH} if greater than {@link #MAX_STRENGTH}.
   */
  public void setStrength(double strength)
  {
    this.strength = MathHelper.setBounds(0, MAX_STRENGTH, strength);
  }
  
  /**
   * Sets the Player's strategy.
   * 
   * @param strategy the new strategy
   */
  public void pickupStrategy(BattleStrategy strategy)
  {
    if (strategy != null)
    {
      this.strategy = strategy;
    }
  }
  
  /**
   * Sets the Player's health to its {@link #MAX_HEALTH}.
   */
  public void resetHealth()
  {
    health = MAX_HEALTH;
  }
  
  /**
   * Sets the Player's strength to its {@link #MAX_STRENGTH}.
   */
  public void resetStrength()
  {
    strength = MAX_STRENGTH;
  }
  
  /**
   * Restores the Player's health by
   * either the quotient of {@link #MAX_HEALTH} by {@value #STRENGTH_SCALE}
   * or the difference between {@link #MAX_HEALTH} and the Player's current health,
   * whichever is less. 
   * 
   */
  public void restoreHealth()
  {
    health = Math.min(health + MAX_HEALTH / STRENGTH_SCALE, MAX_HEALTH);
  }
  
  /**
   * Restores the Player's strength by
   * either the quotient of half of {@link #MAX_STRENGTH} by {@value #STRENGTH_SCALE}
   * or the difference between {@link #MAX_STRENGTH} and the Player's current strength,
   * whichever is less.
   */
  public void restoreStrength()
  {
    strength = Math.min(strength + MAX_STRENGTH / (2 * STRENGTH_SCALE), MAX_STRENGTH);
  }
  
  /**
   * Reduces the Player's strength by
   * either the quotient of half of {@link #MAX_STRENGTH} by {@value #STRENGTH_SCALE}
   * or the difference between {@link #MAX_STRENGTH} and the Player's current strength,
   * whichever is less.
   */
  public void reduceStrength()
  {
    strength = Math.max(strength - MAX_STRENGTH / (2 * STRENGTH_SCALE), 0);
  }
  
  /**
   * Provides a String representation of the Player's state.
   * 
   * @return the String representation of the Player's state
   */
  @Override
  public String toString()
  {
    return String.format(
        "%s: %s "
    //+ "ID:\t%d "
    + "Sacks:\t%d "
    + "Health:\t%.1f "
    + "Strength:\t%.1f "
    + "Strategy:\t%s",
    getClass().getSimpleName(), name, /*ID,*/ sacks, health, strength, strategy == null? "None" : strategy);
  }

}
