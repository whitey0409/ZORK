package repository;

import game.GameEvent;

import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sounds implements Observer
{ 
  
  public enum Music
  {
    SCARY, CALM, FUNKY, HAPPY;
    
    @Override
    public String toString()
    {
      final String name = super.toString();
      return name.substring(0, 1) + name.substring(1).toLowerCase() + " Music";
    }
  }
	
  //Instantiate variables
  private Clip bgMusic;
  private boolean enabled;
  private Logger log;
  
  private static final Map<GameEvent, String> sounds;
  static
  {
    sounds = new EnumMap<>(GameEvent.class);
    sounds.put(GameEvent.RESET, "RESET");
    sounds.put(GameEvent.LOAD, "LOAD");
    sounds.put(GameEvent.MOVE, "MOVE");
    sounds.put(GameEvent.POKE, "POKE");
    sounds.put(GameEvent.TERRIFY, "TERRIFY");
    sounds.put(GameEvent.HEAL, "RESET");
    sounds.put(GameEvent.SAVE, "SAVE");
    sounds.put(GameEvent.INSTRUCTIONS, "INSTRUCTIONS");
    sounds.put(GameEvent.STORYLINE, "STORYLINE");
  }
  
  private static final Set<GameEvent> music;
  static
  {
    music = EnumSet.noneOf(GameEvent.class);
    for (Music m : Music.values())
    {
      music.add(GameEvent.valueOf(m.name()));
    }
  }
  
  //Constructor, creates an enumMap of all given sounds
  public Sounds()
  {
    log = Logger.getLogger("Sounds");
    enabled = true;
    bgMusic = repeatClip("SCARY.wav");
  }
  
  //Return if a sound is enabled
  public boolean isEnabled()
  {
    return enabled;
  }

  /**
   * Observer update method
   */
  @Override
  public void update(Observable o, Object arg)
  {
    log.info("Got " + arg);
    if (arg == GameEvent.MUTE)
    {
      bgMusic.stop();
      enabled = false;
    }
    else if (arg == GameEvent.UNMUTE)
    {
      enabled = true;
      bgMusic.start();
    }
    else if (enabled)
    {
      if (music.contains(arg))
      {
        bgMusic.stop();
        bgMusic.close();
        bgMusic = repeatClip(arg + ".wav");
      }
      else
      {
        final String name = sounds.get(arg);
        if (name != null)
        {
          playClip(name + ".wav");
        }
      }
    }
  }
  
  /**
   * @param filename the name of the file that is going to be played
   */
  private void playClip(String filename)
  {
    File soundFile;
    AudioInputStream audioStream;
    
    String a = System.getProperty("user.dir");
    String strFilename = (a.replaceFirst("C:/", "C://"))+"/"+filename;

    try
    {
        soundFile = new File(strFilename);
        audioStream = AudioSystem.getAudioInputStream(soundFile);
        final Clip clip = AudioSystem.getClip();
        clip.addLineListener(new LineListener()
        {

          @Override
          public void update(LineEvent event)
          {
            if (event.getType() == LineEvent.Type.STOP)
            {
              clip.close();
            }
          }
          
        });
        clip.open(audioStream);
        clip.start();
    }
    catch (IOException|LineUnavailableException|UnsupportedAudioFileException e)
    {
      e.printStackTrace();
    }
  }
  
  /**
   * @param filename the name of the file that is going to be played
   * @return the bg clip
   */
  private Clip repeatClip(String filename)
  {
    File soundFile;
    AudioInputStream audioStream;
    
    String a = System.getProperty("user.dir");
    String strFilename = (a.replaceFirst("C:/", "C://"))+"/"+filename;

    Clip clip = null;
    try
    {
        soundFile = new File(strFilename);
        audioStream = AudioSystem.getAudioInputStream(soundFile);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }
    catch (IOException|LineUnavailableException|UnsupportedAudioFileException e)
    {
      e.printStackTrace();
    }
    return clip;
  }

  }