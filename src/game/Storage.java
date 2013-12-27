package game;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * This class provides object file I/O services for the Game class
 * @author Steven Olson
 * Section B55
 * Assignment 4
 *
 */
public class Storage
{  
	//-- Class variables for debugging ------------------------------------------

  /**
   * Logger object for debugging
   */
  public static Logger log;
  static
  {
    log = Logger.getLogger("Storage");
  }  
  
  //-- Instance Variables -----------------------------------------------------
 
  /**
   * Virtual file object
   */
  //Declare a virtual file object (you can call it file)
  
  File file;
  
  //-- Methods ----------------------------------------------------------------  

  //-- Constructors -----------------------------------------------------------

  /**
   * Responsibility: Creates a Storage object 
   */
  public Storage()
  {
    setUpLogging();
    //Initialize file to null
    file = null;
  }
  
  //-- Class (Helper) methods -------------------------------------------------
 
  /**
   * Responsibility: Turns logging on when desired 
   */
  private static void setUpLogging()
  {
    log.setLevel(Level.ALL);     
    //log.setLevel(Level.OFF);
  }  

  //------ Instance methods ---------------------------------------------------
  //-- Predicates -------------------------------------------------------------

  /**
   * Responsibility: Determines if file has been selected
   * @return true if present, false otherwise
   */  
  public boolean hasFile()
  {
    //Place your code here
	  return file.exists();
  }

  //-- Accessors --------------------------------------------------------------
      
  /**
   * Responsibility: reads in game from file
   * @return game to be loaded
   */
  public Game read()
  {
    //Note structure of exception-handling!
    
    //Set game to null
	  Game game = null;
    //Will read in entire game object at a time
    //Declare an object of the ObjectInputStream class and set it to null
	  ObjectInputStream input = null;
    try 
    {
    	//Note decorator pattern: 
      //  low-level stream wraps file, high-level stream wraps low-level stream
      FileInputStream fileInput = new FileInputStream(file);
      //Declare and instantiate an object of the FileInputStream class, 
      //  sending it the file instance variable as an argument
      //Instantiate hi-level Object stream, sending low-level stream as argument
      input = new ObjectInputStream(fileInput);
      //Set game to result of reading in object from Object stream --
      //  don't forget to cast from Object to Game
      game = (Game)input.readObject();
      
      try
      {   
      	//leave blank
      }
      finally //Close stream if created
      {
        //close Object stream if it isn't null    
    	  if (input != null){
    		  input.close();
    	  }
      }      
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      System.out.println(e.getCause());
      JOptionPane.showMessageDialog
                  (null, "Read failed:  not a valid Game file");
    } 
    catch (IOException e)
    {
        e.printStackTrace();
        System.out.println(e.getCause());
        JOptionPane.showMessageDialog(null, "Open failed:  IOException");
    }
    //return game object
	  return game;
  }

  /**
   * Responsibility: Returns state of storage object
   * @return formatted String description of file
   */ 
  public String toString()
  {
    return file!=null ? 
    			 "\nFile is:  " + file.toString() : "\nFile is undefined";
  }
   
  //-- Mutators ---------------------------------------------------------------

  /**
   * Responsibility: Allows user to select file to open
   * using JFileChooser
   */
  public void setOpenFile() 
  {  
    //Set file to null
	  file = null;
    //Declare and instantiate object of JFileChooser class
	  JFileChooser myChooser = new JFileChooser();
    //Set the mode of the chooser dialog to FILES_ONLY
	  myChooser.setDialogType(JFileChooser.FILES_ONLY);
    //Set the title of the dialog to "Open"
	  myChooser.setDialogTitle("Open");
    //Display the dialog box (showOpenDialog())and store the option selected
	  int option = myChooser.showOpenDialog(null);
	  
	  
    //If the user has chosen APPROVE_OPTION
	  
	  if (option == JFileChooser.APPROVE_OPTION){
		  //  Set the instance variable file to the selected file
		  file = myChooser.getSelectedFile(); 
	  }
   
	  //If we don't have the file, bring up a message box that says so
	  if (file == null)
	  {
		  System.out.println("There's no file here...");
		  JOptionPane.showMessageDialog(null, "There's no file here...");
	  }

  }
  
  /**
   * Responsibility: Allows user to select file to write to
   * using JFileChooser
   */  
  public void setSaveFile()
  {
    //Set the file instance variable to null
	  file = null;
    //Declare and instantiate object of JFileChooser class
	  JFileChooser myChooser = new JFileChooser();
    //Set the mode of the chooser dialog to FILES_ONLY
	  myChooser.setDialogType(JFileChooser.FILES_ONLY);
    //Set the title of the dialog to "Save"
	  myChooser.setDialogTitle("Save");
    //Display the dialog box (showOpenDialog())and store the option selected
	  int option = myChooser.showSaveDialog(null);
	  
	  
    //If the user has chosen APPROVE_OPTION
	  if (option == JFileChooser.APPROVE_OPTION){
		    //  Set the instance variable file to the selected file
		  file = myChooser.getSelectedFile();
	  }
    //If we don't have the file, bring up a message box that says so
	  if (file == null)
	  {
		  System.out.println("We don't have the file...");
		  JOptionPane.showMessageDialog(null, "We don't have the file...");
	  }
  }
    
  /**
   * Responsibility:  Writes game to file
   * @param game - game to be saved in file
   */ 
  public void write(Game game)
  {
    //Will write entire game object at a time
    //Declare an object of the ObjectOutputStream class and set it to null
	ObjectOutputStream output = null;
    try
    {
    	//Note decorator pattern:
      //  low-level stream wraps file, high-level stream wraps low-level stream
      
      //Declare and instantiate an object of the FileOutputStream class,
    	FileOutputStream fileOutput = new FileOutputStream(file);
      //  sending it the file instance variable as an argument
    	output = new ObjectOutputStream(fileOutput);
      //Instantiate hi-level Object stream, sending low-level stream as argument
      //Write game object to file using writeObject() method   
    	output.writeObject(game);
      
      try
      {
        //Leave blank
      }
      finally  
      {
        //Close Output stream if it isn't null
    	  if (output != null){
    		  output.close();
    	  }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
      System.out.println(e.getCause());
      JOptionPane.showMessageDialog(null, "Save failed:  IOException");
    }
  }
}