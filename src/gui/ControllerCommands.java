package gui;

import game.GameEvent;

import java.util.EnumMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class ControllerCommands
{
  private final Map<GameEvent, Command> commands;
  
  public ControllerCommands(final Controller controller)
  {
    commands = new EnumMap<>(GameEvent.class);
    
    final Command updateAllButtons = new Command()
    {
      @Override
      public void execute()
      {
        controller.updatePauseAndSaveButtons();
        controller.updatePokeButton();
        controller.updateDirectionalButtons();
      }
    };
    
    final Command gameOver = new Command()
    {
    	@Override
    	public void execute()
    	{
    	  controller.updatePauseAndSaveButtons();
    	  controller.updatePokeButton();
    	  controller.updateDirectionalButtons();
    	  JOptionPane.showMessageDialog(null,"You lose, you should have absconded while you had the chance.", "Game Over", JOptionPane.PLAIN_MESSAGE);
    	}
    };
    final Command victory = new Command()
    {
      @Override
      public void execute()
      {
        controller.updatePauseAndSaveButtons();
        controller.updatePokeButton();
        controller.updateDirectionalButtons();
        JOptionPane.showMessageDialog(null,"You win, ascending the echeladder every day!", "Victory", JOptionPane.PLAIN_MESSAGE);
      }
    };
    
    commands.put(GameEvent.RESET, updateAllButtons);
    commands.put(GameEvent.LOAD, updateAllButtons);
    commands.put(GameEvent.GAMEOVER, gameOver);
    commands.put(GameEvent.VICTORY, victory);
    commands.put(GameEvent.MOVE, new Command()
    {
      @Override
      public void execute()
      {
        controller.updatePokeButton();        
        controller.updateDirectionalButtons();

      }
    });
    
    final Command updatePokeButton = new Command()
    {

      @Override
      public void execute()
      {
        controller.updatePokeButton();
      }
      
    };
    commands.put(GameEvent.POKE, updatePokeButton);
    commands.put(GameEvent.TERRIFY, updatePokeButton);
    //commands.put(GameEvent.HEAL, Command.DO_NOTHING);
  }
  
  /**
   * Executes code based on game events.
   * @param evt
   */
  public void execute(final GameEvent evt)
  {
    final Command command = commands.get(evt);
    if (command != null)
    {
      command.execute();
    }
  }
  
}
