package gui;

import game.Game;
import game.Game.Difficulty;
import game.Game.State;
import game.GameEvent;
import game.GameFacade;
import game.Storage;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import repository.Sounds;

import demesnes.Chamber;
import demesnes.Direction;
import demesnes.Maze;
import demesnes.Wall;

/**
 * This class serves as the CONTROLLER in NewZorkGUI It consists of the controls
 * that can be use to interact with the MODEL
 * 
 * @author Rose Williams
 * 
 */
public class Controller extends JPanel implements Observer
{
  // Instance Variables
  // --------------------------------------------------------

  /**
   * MODEL What this CONTROLLER will interact with
   */
  private final GameFacade game;

  /**
   * Button used to poke the gruman
   */
  private final JButton pokeGruman;

  /**
   * Button used to make a new game
   */
  private final JButton createGame;

  /**
   * Button used to save a game
   */
  private final JButton saveGame;

  /**
   * Button used to load a game
   */
  private final JButton loadGame;

  /**
   * Button used to pause a game
   */
  private final JButton pauseGame;

  /**
   * Button to see instructions
   */
  private final JButton instructions;

  /**
   * Button to mute or unmute
   */
  private final JButton sound;
  
  /**
   * Button to bring up credits image
   */
  private final JButton credits;
  
  /**
   * Button to bring up story line
   */
  private final JButton storyLine;

  /**
   * Button to make a custom game
   * 
   */
  private final JButton customGame;

  /**
   * Button to change settings
   */
  private final JButton settings;

  /**
   * Buttons used to navigate the Human
   */
  private final Map<Direction, JButton> go;

  /**
   * Commands based on GameEvents
   */
  private final ControllerCommands commands;

  private boolean soundEnabled;
  
  private static final String[] DEFAULT_NAMES = {"Steve, the Knight of Space",
    "Mandy, the Witch of Breath",
    "Thomas, the Thief of Light",
    "Ten-Young, the Page of Time"};

  // Constructors
  // --------------------------------------------------------------

  /**
   * Initializes the game, creates the view and sends it the game, retrieves
   * the subpanels from the view, creates and registers the buttons, and adds
   * the buttons to this JPanel
   * 
   * @param game
   *            - the MODEL for this CONTROLLER
   */
  public Controller(final GameFacade game)
  { 
    // Take in the MODEL and set it
    this.game = game;

    soundEnabled = true;

    // Imma go decouple stuff here

    // Take care of this CONTROLLER:
    this.setFocusable(true);

    // Create the poke gruman button and disable it
    pokeGruman = new JButton("Poke Gruman");
    updatePokeButton();

    //Create the instructions button
    instructions = new JButton("Instructions");
    instructions.setEnabled(true);
    instructions.setBackground(Color.GREEN);

    //Create the sound button
    sound = new JButton("Mute");
    sound.setEnabled(true);
    sound.setBackground(Color.GREEN);

    //Create the settings button
    settings = new JButton("Settings");
    settings.setEnabled(true);
    settings.setBackground(Color.GREEN);

    //Start background music
    //game.playSound("backgroundmusic.wav");
    credits = new JButton("Credits");
    credits.setEnabled(true);
    credits.setBackground(Color.GREEN);

    //Create the storyline button
    storyLine = new JButton("Story Line");
    storyLine.setEnabled(true);
    storyLine.setBackground(Color.GREEN);

    //Create the settings button
    customGame = new JButton("Custom Game");
    customGame.setEnabled(true);
    customGame.setBackground(Color.GREEN);

    // Create the buttons that create game
    createGame = new JButton("Create Game");
    createGame.setBackground(Color.GREEN);
    loadGame = new JButton("Load Game");
    loadGame.setBackground(Color.GREEN);
    saveGame = new JButton("Save Game");
    saveGame.setBackground(Color.GREEN);
    saveGame.setEnabled(false);

    // Create the Pause Button
    pauseGame = new JButton("Pause Game");
    pauseGame.setBackground(Color.GREEN);
    pauseGame.setEnabled(false);

    // Create the direction buttons
    go = new EnumMap<>(Direction.class);

    go.put(Direction.NORTH, new JButton("N"));
    go.put(Direction.SOUTH, new JButton("S"));
    go.put(Direction.EAST, new JButton("E"));
    go.put(Direction.WEST, new JButton("W"));

    // Create the storage class

    // Listener Inner Classes
    // ----------------------------------------------------

    // Objects of Listener classes are "registered" to GUI components and
    // implement listener interfaces by providing implementations for their
    // event handler methods
    // When an event is fired that belongs to the component, the event
    // handler
    // method, or callback, will then be run automatically
    // Note that an event handler is NEVER invoked explicitly
    final JPanel createPanel = new JPanel();
    createPanel.setLayout(new GridLayout(2, 1, 1, 1));
    createPanel.setBorder(BorderFactory
            .createEtchedBorder(EtchedBorder.RAISED));
    createPanel.add(new JLabel("Choose your Hero"));
    final DefaultComboBoxModel<String> nameModel = new DefaultComboBoxModel<>(DEFAULT_NAMES);
    final JComboBox<String> nameCombo = new JComboBox<String>(nameModel);
    createPanel.add(nameCombo);
    createPanel.add(new JLabel("Or Enter your Name"));
    final JTextField nameField = new JTextField(10);
    createPanel.add(nameField);
    
    createGame.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        //Controller.this.game.resetGame();
        Controller.this.game.pause();
          
          final int result = JOptionPane.showConfirmDialog(null, createPanel,
                  "Enter Game Settings", JOptionPane.OK_CANCEL_OPTION);
          if (result == JOptionPane.OK_OPTION)
          {
              String option = (String) nameModel.getSelectedItem();
              if (!nameField.getText().isEmpty())
              {
                  game.configureName(nameField.getText());
                  Controller.this.game.resetGame();
              }
              else
              {
                  game.configureName(option);
                  Controller.this.game.resetGame();
              }
          }
          else
          {
            Controller.this.game.resume();
          }
      }
    });

    saveGame.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        Controller.this.game.saveGame();
      }
    });

    loadGame.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        Controller.this.game.loadGame();
      }
    });

    instructions.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        Controller.this.game.giveInstructions();
        JOptionPane.showMessageDialog(null, "Welcome to the Zork Game!"+"\n"+"\n"+
            "Get ready for the adventure of a lifetime!"+"\n"+
            "To start, simply select \"Create Game\"."+"\n"+
            "To play the game, navigate your player through the maze of chambers using the \"North\", \"South\", \"East\", and \"West\" directional buttons."+"\n"+
            "But, be careful! You may encounter some bad guys along the way!  If you bump into a gruman enemy, select the \"Poke\" button to battle it for its sacks of treasure!"+"\n"+
            "With every poke you get in, the gruman will lose health and strength proportionate to the force of the poke.  " +"\n"+
            "If it loses all it's strength it loses a sack and gains strength, because it's heavy load was lightened."+"\n" +
            "Be warned, just as you Poke the gruman, the gruman can \"Roar\" at you, threatening your health, strength, and sacks!"+"\n"+

        											"The object of the game is to collect 10 sacks AND visit all the chambers without losing all of your health!"+"\n"+"Happy Playing!");
        Controller.this.game.resume();
      }
    });

    sound.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent event)
      {
        if (soundEnabled)
        {
          Controller.this.game.mute();
          sound.setText("Unmute");
        }
        else
        {
          Controller.this.game.unmute();
          sound.setText("Mute");
        }
        soundEnabled = !soundEnabled;
      }

    });
    
    final JLabel teamPicture = new JLabel(new ImageIcon("TeamPlasmaPic.png"));
    credits.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent event)
    	{
             Controller.this.game.pause();
    		 
    		 JOptionPane.showMessageDialog(null, teamPicture, "TEAM PLASMA", 
    		                                 JOptionPane.PLAIN_MESSAGE, null);
    	     Controller.this.game.resume();

    	
    	}
    });

    storyLine.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        //game.playSound("storyline.wav");
        Controller.this.game.tellStoryline();
        JOptionPane.showMessageDialog(null, "Uh oh!"+"\n"+"\n"+
            "It seems you have found yourself lost in the Land of the Grumans.  But beware, though you are lost, you are not alone."+"\n"+
            "Lurking for centuries in this underground maze are the greedy and ornery Gruman tribe!"+"\n"+
            "These tempremental proles will stop at nothing to make sure that that you do not escape their land with any of their treasure, so stay on your toes as you round the spooky corners of this faraway world!");
        Controller.this.game.resume();
      }
    });

    final JPanel setingsPanel = new JPanel();
    setingsPanel.setLayout(new GridLayout(2, 1, 1, 1));
    setingsPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    setingsPanel.add(new JLabel("Colour"));
    final DefaultComboBoxModel<ColorScheme> colorModel = new DefaultComboBoxModel<>(ColorScheme.values());
    final JComboBox<ColorScheme> colorCombo = new JComboBox<>(colorModel);
    setingsPanel.add(colorCombo);
    setingsPanel.add(new JLabel("Music"));
    final DefaultComboBoxModel<Sounds.Music> musicModel = new DefaultComboBoxModel<>(Sounds.Music.values());
    final JComboBox<Sounds.Music> musicCombo = new JComboBox<Sounds.Music>(musicModel);
    setingsPanel.add(musicCombo);
    
    settings.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        Controller.this.game.pause();

        Sounds.Music oldMusicOption = (Sounds.Music)musicCombo.getSelectedItem();
        int okay = JOptionPane.showConfirmDialog(null, setingsPanel, 
            "Change Settings", JOptionPane.OK_CANCEL_OPTION);
        
        if (okay == JOptionPane.OK_OPTION)
        {
          ColorScheme colorOption = (ColorScheme) colorCombo.getSelectedItem();
          Color bgColor = colorOption.bg;
          Color fgColor = colorOption.fg;
        
          Sounds.Music musicOption = (Sounds.Music)musicCombo.getSelectedItem();
          if (oldMusicOption != musicOption)
          {
            Controller.this.game.changeMusic(musicOption);
          }
          
          // pick your poison between this and static variables
          synchronized(getTreeLock())
          {
            for (Component c : getComponents())
            {
              if (c != pokeGruman && !go.containsValue(c))
              {
                c.setBackground(fgColor);
              }
            }
          }
          getParent().setBackground(bgColor);
          synchronized(getParent().getTreeLock())
          {
            for (Component c : getParent().getComponents())
            {
              c.setBackground(bgColor);
            }
          }
          Controller.this.game.setColorScheme();
          
        }                
        Controller.this.game.resume();
      }
    });

    final JTextField customNameField = new JTextField(10);
    final JTextField chamberField = new JTextField(10);
    chamberField.setText(Integer.toString(Game.DEFAULT_NUM_CHAMBERS));
    final JTextField sacksToWinField = new JTextField(10);
    sacksToWinField.setText(Integer.toString(Game.DEFAULT_SACKS_TO_WIN));

    final JPanel customPanel = new JPanel();
    customPanel.setLayout(new GridLayout(4, 1, 1, 1));
    customPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

    customPanel.add(new JLabel("Difficulty"));
    DefaultComboBoxModel<Game.Difficulty> difficultyModel = new DefaultComboBoxModel<>();
    for (Game.Difficulty d : Game.Difficulty.values())
    {
      difficultyModel.addElement(d);
    }
    final JComboBox<Game.Difficulty> difficultyCombo = new JComboBox<>(difficultyModel);
    customPanel.add(difficultyCombo);

    customPanel.add(new JLabel("Name:"));
    customPanel.add(customNameField);
    customPanel.add(new JLabel("Number of Chambers: "));
    customPanel.add(chamberField);
    customPanel.add(new JLabel("Number of Sacks to Win: "));
    customPanel.add(sacksToWinField);
    
    customGame.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        Controller.this.game.pause();

        boolean passed = false;
        do
        {
          final int result = JOptionPane.showConfirmDialog(null, customPanel, 
              "Enter Game Settings", JOptionPane.OK_CANCEL_OPTION);
          if (result == JOptionPane.OK_OPTION)
          {
            int chambers = Maze.MIN_NUMBER_CHAMBERS;
            int sacks = 1;
            try
            {
              chambers = Integer.parseInt(chamberField.getText());
              sacks = Integer.parseInt(sacksToWinField.getText());
              passed = Maze.MIN_NUMBER_CHAMBERS <= chambers & 0 < sacks; 
            }
            catch (NumberFormatException e)
            {
              passed = false;
            }
            
            if (passed)
            {
              game.configureGame(customNameField.getText(), chambers, sacks,
                  (Game.Difficulty) difficultyCombo.getSelectedItem());
            }
            else
            {
              JOptionPane.showMessageDialog(null, "Those inputs didn't work.\n"+
                "Chambers must be between " + Maze.MIN_NUMBER_CHAMBERS + " and " + Integer.MAX_VALUE
                + ", and Sacks to Win must be between 1 and " + Integer.MAX_VALUE + ".");
            }
            
          }
          else
          {
            passed = true;
          }
        }
        while (!passed);
        Controller.this.game.resume();
      }
    });

    pauseGame.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        Controller.this.game.pause();
        JOptionPane.showMessageDialog(null, "Game Paused. Click OK to Resume.");
        Controller.this.game.resume();
      }
    });


    pokeGruman.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        Controller.this.game.pokeGruman();
      }
    });

    for (final Direction d : Direction.values())
    {
      go.get(d).addActionListener(new ActionListener()
      {
        @Override
        public void actionPerformed(ActionEvent event)
        {
          Controller.this.game.moveHuman(d);
        }
      });
      go.get(d).setEnabled(false);
    };

    final Map<Integer, JButton> keysToButtons = new HashMap<>();
    keysToButtons.put(KeyEvent.VK_P, pokeGruman);
    keysToButtons.put(KeyEvent.VK_W, go.get(Direction.NORTH));
    keysToButtons.put(KeyEvent.VK_A, go.get(Direction.WEST));
    keysToButtons.put(KeyEvent.VK_S, go.get(Direction.SOUTH));
    keysToButtons.put(KeyEvent.VK_D, go.get(Direction.EAST));
    KeyboardFocusManager.getCurrentKeyboardFocusManager()
    .addKeyEventDispatcher(new KeyEventDispatcher()
    {
      @Override
      public boolean dispatchKeyEvent(KeyEvent e)
      {
        if (game.getState() == Game.State.ACTIVE && e.getID() == KeyEvent.KEY_PRESSED)
        {
          final JButton button = keysToButtons.get(e.getKeyCode());
          if (button != null)
          {
            button.doClick();
          }
        }
        return false;
      }
    });

    // Set Layout
    this.setLayout(new GridLayout(3, 5, 1, 1));
    this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

    // Row 1
    this.add(createGame);
    this.add(customGame);
    this.add(sound);
    this.add(go.get(Direction.NORTH));
    this.add(pauseGame);

    // Row 2
    this.add(saveGame);
    this.add(storyLine);
    this.add(go.get(Direction.WEST));
    this.add(credits);
    this.add(go.get(Direction.EAST));

    // Row 3
    this.add(loadGame);
    this.add(instructions);
    this.add(settings);
    this.add(go.get(Direction.SOUTH));
    this.add(pokeGruman);

    commands = new ControllerCommands(this);
  }

  @Override
  public void update(Observable o, Object arg)
  {
    commands.execute((GameEvent)arg);
  }

  /**
   * Changes the directional buttons based on which doors exist.
   */
  public void updateDirectionalButtons()
  {
    final Map<Direction, Wall> walls = game.getCurrentChamberWalls();
    for (final Direction d : Direction.values())
    {
      go.get(d).setEnabled(game.getState() == Game.State.ACTIVE && walls.get(d)==Wall.DOOR);
    }
  }

  /**
   * Update Poke Button, turning it on or off. 
   */
  public void updatePokeButton()
  {
    if(game.getState() == Game.State.ACTIVE && game.hasGruman() && !game.isGrumanMad())
    {
      pokeGruman.setBackground(Color.RED);
      pokeGruman.setEnabled(true);
    }
    else
    {
      pokeGruman.setEnabled(false);
      pokeGruman.setBackground(Color.GRAY);
    }
  }

  /**
   * Update pause and save button, turning them on if the game is active. Off otherwise.
   */
  public void updatePauseAndSaveButtons()
  {
    final boolean isActive = game.getState() == Game.State.ACTIVE;
    saveGame.setEnabled(isActive);
    pauseGame.setEnabled(isActive);
  }


}
