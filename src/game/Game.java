package game;

import players.*;
import repository.Sounds;
import demesnes.Chamber;
import demesnes.Direction;
import demesnes.Location;
import demesnes.Maze;
import demesnes.MazeFacade;
import demesnes.Wall;

import java.util.Map;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 * This class serves as the MODEL in the NewZorkGUI It pits a single human
 * against a single gruman Note that each method delegates its job to other
 * objects (i.e., this game class is a wrapper that uses a portion of the Zork
 * classes)
 * 
 * 
 * @author Rose Williams
 * 
 */
public class Game implements Serializable
{
  // Class Variables -----------------------------------------------------------
    
  public enum State
  {
    /**
     * A game has yet to begin.
     * Inactive: Game isn't doing anything
     * Active: Stuff is happening.
     * Game Over: Loss.
     * Victory: Triumph
     */
    INACTIVE, ACTIVE, GAMEOVER, VICTORY;
  }
  
  /**
   * These should be fairly self explanatory.
   *
   */
  public enum Difficulty
  {
    EASY(1.0), MEDIUM(0.75), HARD(0.5);
    
    public final double FACTOR;
    
    Difficulty(final double f)
    {
      FACTOR = f;
    }
    
    @Override
    public String toString()
    {
      final String name = super.toString();
      return name.substring(0, 1) + name.substring(1).toLowerCase();
    }
  }
  private String gameStatus;
  private Timer attackHumanTimer;
  private Timer healGrumanTimer;
  private Timer healHumanTimer;
  private String humanName;
  private int numChambers;
  private int sacksToWin;
  private boolean shouldHealGruman;
  private boolean shouldHealHuman;
  /**
   * Provides logging for our game
   */
  private static Logger log;
  static
  {
    log = Logger.getLogger("Game");
  }

  /**
   * Delay for the heal human timer.
   */
  public static final int HEAL_HUMAN_DELAY_MS = 5000;
  /**
   * Number of Chambers
   */
  public static final int DEFAULT_NUM_CHAMBERS = 10;
  /**
   * Amount of Sacks that you need to win.
   */
  public static final int DEFAULT_SACKS_TO_WIN = 10;
  /**
   * The default name.
   */
  public static final String DEFAULT_NAME = "Steve";

  // Instance Variables --------------------------------------------------------

  /**
   * Player's human player. Must steal treasure from gruman to win
   */
  private Human invader;

  /**
   * Game player that has treasure
   */
  private Gruman citizen;

  /**
   * Map of maze for game
   */
  private Maze maze;

  /**
   * Location of human player
   */
  private Location location;

  private State previousState;
  
  /**
   * The current game state.
   */
  private State state;
  
  /**
   * The current game difficulty.
   */
  private Difficulty difficulty;

  /**
   * Constructs a new game having one human and one gruman
   */
  public Game()
  {
    setUpLogging();
    humanName = DEFAULT_NAME;
    numChambers = DEFAULT_NUM_CHAMBERS;
    sacksToWin = DEFAULT_SACKS_TO_WIN;
    difficulty = Difficulty.MEDIUM;
    previousState = state = State.INACTIVE;
  }

  // Class Methods -------------------------------------------------------------

  /**
   * Enables or disables logging
   */
  private static void setUpLogging()
  {
    log.setLevel(Level.ALL);
    // log.setLevel(Level.OFF);
  }

  // Predicate Methods ---------------------------------------------------------

  /**
   * Determines whether or not an Human has been created
   * 
   * @return Human object status
   */
  public boolean hasHuman()
  {
    return invader != null;
  }

  /**
   * Determines whether or not a Gruman is attacking the Human
   * 
   * @return Gruman object status
   */
  public boolean hasGruman()
  {
    return citizen != null;
  }

  /**
   * Determines whether or not this gruman has sacks
   * 
   * @return true when has sacks, false when no sacks
   */
  public boolean grumanHasSacks()
  {
    return citizen.hasSacks();
  }

  // Accessor Methods ----------------------------------------------------------

  public State getState()
  {
    return state;
  }

  /**
   * Returns formatted String representation of this human
   * 
   * @return formatted String that represents the state of this human
   */
  public String aString()
  {
    return invader.toString();
  }

  /**
   * Returns formatted String representation of this gruman
   * 
   * @return formatted String that represents the state of this gruman
   */
  public String mString()
  {
    return citizen.toString();
  }

  /**
   * Causes gruman to roar at human and get force of roar
   * 
   * @return force of roar
   */
  public double attackHuman()
  {
    return citizen.terrifyHuman();
  }

  /**
   * Causes human to poke gruman and gets force of poke
   * 
   * @return force of poke
   */
  public double attackGruman()
  {
    return invader.pokeGruman();
  }

  /**
   * Amount of Sacks that the Human has.
   * @return
   */
  public int getHumanSacks()
  {
    return invader.getSacks();
  }

  /**
   * Get the amount of sacks that the Gruman has.
   * @return
   */
  public int getGrumanSacks()
  {
    return citizen.getSacks();
  }

  // Mutator Methods -----------------------------------------------------------

  /**
   * Makes the Human win.
   */
  public void winGameHuman()
  {
    /*
    gameStatus = invader.getName() + " Wins! with " + invader.getSacks()
        + " sacks and " + (maze.getTotalChambers() - maze.getChambersLeft())
        + " chambers discovered";
        */
    stopTimers();
    previousState = state;
    state = State.VICTORY;
    updateStatus();
  }

  /**
   * The game is over.
   */
  public void winGameGrumans()
  {
    //gameStatus = invader.getName() + " has no health left. Start a new game?";
    log.info("You lost");
    stopTimers();
    previousState = state;
    state = State.GAMEOVER;
    updateStatus();
  }

  /**
   * Causes human to suffer force of gruman's roar
   * 
   * @param force
   *          of gruman's roar
   */
  public void defendHuman(double force)
  {
    invader.sufferTerror(force);
    shouldHealHuman = true;
    if (!(invader.hasStrength() && invader.hasHealth()) && invader.hasSacks())
    {
      invader.decrementSacks();
      invader.restoreStrength();
      winRoundGruman();
    }
    if (!invader.hasHealth())
    {
      winGameGrumans();
    }
    updateStatus();
  }

  /**
   * Causes gruman to suffer force of humans poke
   * 
   * @param force
   *          of humans poke
   */
  public void defendGruman(double force)
  {
    citizen.sufferPoke(force);
    shouldHealGruman = true;
    if (!(citizen.hasStrength() && citizen.hasHealth()) && citizen.hasSacks())
    {
      citizen.decrementSacks();
      citizen.restoreStrength();
      winRoundHuman();
    }
    if (hasSacksToWin())
    {
      winGameHuman();
    }
    updateStatus();
  }


  /**
   * Restart the game.
   */
  public void resetGame()
  {
    stopTimers();
    attackHumanTimer = healHumanTimer = healGrumanTimer = null;
    
    invader = new Human(humanName);
    invader.setHealth(invader.getHealth() * difficulty.FACTOR);
  
    citizen = null;
    location = Location.ORIGIN;
    this.maze = new Maze(numChambers);
    previousState = state;
    state = State.ACTIVE;
    shouldHealHuman = shouldHealGruman = false;
    
    updateStatus();
  }

  /**
   * Sets the parameters for the new game.
   * @param humanName
   * @param numChambers
   * @param sacksToWin
   * @param difficulty
   */
  public void configureGame(String humanName, int numChambers, int sacksToWin, Difficulty difficulty)
  {
    this.humanName = humanName;
    this.numChambers = numChambers;
    this.sacksToWin = sacksToWin;
    this.difficulty = difficulty;
  }
  
  /**
   * Sets the human's name.
   * 
   * @param humanName
   */
  public void configureName(String humanName)
  {
    this.humanName = humanName;
  }

  /**
   * Returns the maze.
   * @return
   */
  public MazeFacade getMaze()
  {
    MazeFacade view = null;
    if (maze != null)
    {
      view = new MazeFacade(location, maze);
    }
    return view;
  }

  /**
   * Moves the human and responds to some listeners
   * @param dir
   * @param terrifyHumanCallback
   * @param healHumanCallback
   */
  public void moveHuman(final Direction dir,
      final ActionListener terrifyHumanCallback,
      final ActionListener healHumanCallback)
  {
    if (maze.hasDoorAt(dir, location))
    {
      location = new Location(location, dir);
      invader.pickupStrategy(maze.getStrategy(location));
      maze.visit(location);
      
      stopAttackingHuman();
      if (citizen != null && shouldHealGruman)
      {
        startHealingGruman();
      }
      final Gruman oldCitizen = citizen;
      citizen = maze.getGruman(location);
      if (citizen != null)
      {
        stopHealingHuman();
        startAttackingHuman(terrifyHumanCallback);
      }
      else if (oldCitizen != null && shouldHealHuman) // citizen went from
                                                        // non-null to null
      {
        startHealingHuman(healHumanCallback);
      }
    }
    updateStatus();
  }

  private void stopHealingHuman()
  {
    if (healHumanTimer != null)
    {
      healHumanTimer.stop();
    }
  }

  private void stopAttackingHuman()
  {
    if (attackHumanTimer != null)
    {
      attackHumanTimer.stop();
      attackHumanTimer = null;
    }
  }

  /**
   * Checks if the Human's sacks are enough.
   * @return
   */
  public boolean hasSacksToWin()
  {
    return (getHumanSacks() >= this.sacksToWin);
  }

  /**
   * Self-explanatory.
   * @param l
   * @return
   */
  public boolean isCurrentChamber(Location l)
  {
    boolean val = false;

    if (this.location.equals(l))
    {
      val = true;
    }

    return val;
  }

  public Location getCurrentLocation()
  {
    return location;
  }

  /**
   * After an initial delay randomly determined at its instatiation, the Gruman
   * will start attacking a Human at some interval.
   */
  private void startAttackingHuman(final ActionListener callback)
  {
    attackHumanTimer = new Timer(citizen.getAttackDelay(), callback);
    attackHumanTimer.setRepeats(true);
    attackHumanTimer.setDelay(citizen.getAttackInterval());
    attackHumanTimer.start();
  }


  /**
   * Resumes from pause.
   */
  public void resume()
  {
    // You cannot resume a gameover or victory,
    // and you don't need to resume an already active game.
    if (state == State.INACTIVE)
    {
      previousState = state;
      state = State.ACTIVE;
      if (attackHumanTimer != null)
      {
        attackHumanTimer.restart();
      }
      if (healHumanTimer != null)
      {
        healHumanTimer.restart();
      }
      if (healGrumanTimer != null)
      {
        healGrumanTimer.restart();
      }
    }
  }

  // Overridden Object Methods -------------------------------------------------
  /**
   * Returns formatted String version indicating status of game elements
   * 
   * @return current state of human and gruman objects
   */
  @Override
  public String toString()
  {
    return gameStatus;
  }

  /**
   * Returns walls.
   * @return
   */
  public Map<Direction, Wall> getCurrentChamberWalls()
  {
    return maze.getWalls(location);
  }

  /**
   * Heals the Human and restores some strength.
   */
  public void healHuman()
  {
    invader.restoreHealth();
    invader.restoreStrength();
    shouldHealHuman = false;
    healHumanTimer = null;
    updateStatus();
  }
  /**
   * Checks mad state.
   * @return
   */
  public boolean isGrumanMad()
  {
    return citizen != null && !citizen.hasSacks();
  }
  
  /**
   * Update the status text.
   */
  public void updateStatus()
  {
    if (state == State.GAMEOVER)
    {
      gameStatus = invader.getName()+" has no health left. Start a new game?";
    }
    else if (state == State.VICTORY)
    {
      gameStatus = invader.getName()+" wins! With "+
          invader.getSacks()+" sacks and "+
          (maze.getTotalChambers()-maze.getChambersLeft())+" chambers discovered";
    }
    else
    {
      gameStatus= "Chambers Left: "+
          maze.getChambersLeft()+"/"+maze.getTotalChambers();
    }
  }

  /**
   * Pauses the game.
   */
  public void pause()
  {
    if (state == State.ACTIVE)
    {
      stopTimers();
      previousState = state;
      state = State.INACTIVE;
    }
  }
  
  /**
   * Returns the human's strategy.
   * 
   * @return the human's strategy
   */
  public BattleStrategy getHumanStrategy()
  {
    return invader.getStrategy();
  }
  
  /**
   * Causes human to gain a sack of treasure after poking gruman
   */
  private void winRoundHuman()
  {
    invader.incrementSacks();
    invader.restoreHealth();
    invader.reduceStrength();
  }

  /**
   * Causes gruman to gain a sack of treasure after terrifying human
   */
  private void winRoundGruman()
  {
    citizen.incrementSacks();
    citizen.restoreHealth();
    citizen.reduceStrength();
  }
  
  /**
   * After a delay, all of the Gruman's health and strength will be restored.
   */
  private void startHealingGruman()
  {
    final Gruman oldCitizen = citizen;
    healGrumanTimer = new Timer(oldCitizen.getHealDelay(), new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        oldCitizen.resetHealth();
        oldCitizen.resetStrength();
        shouldHealGruman = false;
        healGrumanTimer = null;
      }

    });
    healGrumanTimer.setRepeats(false);
    healGrumanTimer.start();
  }

  private void startHealingHuman(ActionListener callback)
  {
    healHumanTimer = new Timer(HEAL_HUMAN_DELAY_MS, callback);
    healHumanTimer.setRepeats(false);
    healHumanTimer.start();
  }
  
  /**
   * Stops the times.
   */
  private void stopTimers()
  {
    if (healHumanTimer != null)
    {
      healHumanTimer.stop();
    }
    if (healGrumanTimer != null)
    {
      healGrumanTimer.stop();
    }
    if (attackHumanTimer != null)
    {
      attackHumanTimer.stop();
    }
  }

}
