package game;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Timer;

import players.Gruman;
import players.Human;
import repository.Sounds;
import demesnes.Chamber;
import demesnes.Direction;
import demesnes.Location;
import demesnes.Maze;
import demesnes.MazeFacade;
import demesnes.Wall;

/**
 * @author tguh3
 *
 */
public class GameFacade extends Observable
{

  private Game game;
  private final Storage storage;
  private final ActionListener terrifyHuman;
  private final ActionListener healHuman;
  private final Logger log;
  
  public GameFacade()
  {
    log = Logger.getLogger("GameFacade");
    game = new Game();
    storage = new Storage();
    
    terrifyHuman = new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        // it is possible this event is fired just as the game becomes inactive
        if (game.getState() == Game.State.ACTIVE)
        {
          game.defendHuman(game.attackHuman());
          setChanged();
          notifyObservers(GameEvent.TERRIFY);
          
          if (game.getState() == Game.State.GAMEOVER)
          {
            log.info("Game over");
            setChanged();
            notifyObservers(GameEvent.GAMEOVER);
          }
        }
        else
        {
           final Timer t = (Timer)e.getSource();
           t.stop();
        }
      
      }
      
    };
    
    healHuman = new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        game.healHuman();
        setChanged();
        notifyObservers(GameEvent.HEAL);
      }
      
    };
  }

  // Let's figure out what methods we need for this facade by tracing
  // Game and Storage.
/**
 * resets the game.
 */
  public void resetGame()
  {
    game.resetGame();
    setChanged();
    notifyObservers(GameEvent.RESET);
  }
  
  /**
   * Passes a configuration state to the game.
   * @param humanName
   * @param numChambers
   * @param sacksToWin
   * @param difficulty
   */
  public void configureGame(String humanName, int numChambers, int sacksToWin, Game.Difficulty difficulty)
  {
    game.configureGame(humanName, numChambers, sacksToWin, difficulty);
  }
  

  /**
   * Calls storage for saving the game.
   */
  public void saveGame()
  {
    game.pause();
    storage.setSaveFile();
    game.resume();
    log.info(game.getState().toString());
    storage.write(game);
    setChanged();
    notifyObservers(GameEvent.SAVE);
  }
  
  /**
   * Loads a save into the game.
   */
  public void loadGame()
  {
    game.pause();
    storage.setOpenFile();
    game = storage.read();
    game.resume();
    setChanged();
    notifyObservers(GameEvent.LOAD);
  }
  
  /**
   * Poke the gruman.
   */
  public void pokeGruman()
  {
    game.defendGruman(game.attackGruman());
    setChanged();
    notifyObservers(GameEvent.POKE);
    if (game.getState() == Game.State.VICTORY)
    {
      setChanged();
      notifyObservers(GameEvent.VICTORY);
    }
    else if (game.isGrumanMad())
    {
      setChanged();
      notifyObservers(GameEvent.MAD);
    }
  }
  
  /**
   * Moves the move the Human.
   * @param dir
   */
  public void moveHuman(final Direction dir)
  {
    game.moveHuman(dir, terrifyHuman, healHuman);
    setChanged();
    notifyObservers(GameEvent.MOVE);
  }
  
  /**
   * Returns the location of the Human.
   * @return
   */
  public Location getCurrentLocation()
  {
    return game.getCurrentLocation();
  }
  
  /**
   * Get walls from game.
   * @return
   */
  public Map<Direction, Wall> getCurrentChamberWalls()
  {
    return game.getCurrentChamberWalls();
  }

  /**
   * Checks if the game has a human.
   * @return
   */
  public boolean hasHuman()
  {
    return game.hasHuman();
  }

  public String aString()
  {
    return game.aString();
  }

  /**
   * Has Gruman.
   * @return
   */
  public boolean hasGruman()
  {
    return game.hasGruman();
  }
  
  /**
   * Passes some thing from game.
   * @return
   */
  public boolean hasSacksToWin()
  {
    return game.hasSacksToWin();
  }

  public String mString()
  {
    return game.mString();
  }

  /**
   * Is this the Current chamber?
   * @param location
   * @return
   */
  public boolean isCurrentChamber(Location location)
  {
    return game.isCurrentChamber(location);
  }

  /**
   * Passes the maze.
   * @return
   */
  public MazeFacade getMaze()
  {
    return game.getMaze();
  }
  
  /**
   * Returns the sacks of the human.
   * @return
   */
  public int getHumanSacks()
  {
    return game.getHumanSacks();
  }
  
  /**
   * Passes the Gruman sacks.
   * @return
   */
  public int getGrumanSacks()
  {
      return game.getGrumanSacks();
  }
  
  /**
   * Calls gameover and passes it to the Observer.
   */
  public void winGameGrumans()
  {
    game.winGameGrumans();
    notifyObservers(GameEvent.GAMEOVER);
    setChanged();
  }
  
  /**
   * Calls victory and passes it to the observer.
   */
  public void winGameHuman()
  {
    game.winGameHuman();
    notifyObservers(GameEvent.VICTORY);
    setChanged();
  }
  
  /**
   * Pauses the game.
   */
  public void pause()
  {
    game.pause();
  }
  
  /** Resumes the Game
   * 
   */
  public void resume()
  {
    game.resume();
  }
  
  /**
   * Returns the game's toString.
   */
  public String toString()
  {
    return game.toString();
  }
  
  /**
   * Return game state.
   * @return
   */
  public Game.State getState()
  {
    return game.getState();
  }
  
  /**
   * Mute the game.
   */
  public void mute()
  {
    setChanged();
    notifyObservers(GameEvent.MUTE);
  }
  
  /**
   * Unmute.
   */
  public void unmute()
  {
    setChanged();
    notifyObservers(GameEvent.UNMUTE);
  }

  /**
   * Passes Gruman Anger State.
   * @return
   */
  public boolean isGrumanMad()
  {
    return game.isGrumanMad();
  }
  
  /**
   * Calls give instruction.
   */
  public void giveInstructions()
  {
    game.pause();
    setChanged();
    notifyObservers(GameEvent.INSTRUCTIONS);
  }
  
  /**
   * Calls give Storyline.
   */
  public void tellStoryline()
  {
    game.pause();
    setChanged();
    notifyObservers(GameEvent.STORYLINE);
  }
  
  /**
   * Calls colour Scheme.
   */
  public void setColorScheme()
  {
    setChanged();
    notifyObservers(GameEvent.COLOR);
  }

  /**
   * Change Music.
   * @param option
   */
  public void changeMusic(Sounds.Music option)
  {
    setChanged();
    notifyObservers(GameEvent.valueOf(option.name()));
  }

/**
 * Configures the player name.
 * 
 * @param text
 */
  public void configureName(String text)
  {
    game.configureName(text);
  }
  
  /**
   * Returns the human's strategy.
   * 
   * @return the human's strategy
   */
  public BattleStrategy getHumanStrategy()
  {
    return game.getHumanStrategy();
  }
  
}
