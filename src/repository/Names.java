package repository;

import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import utility.SingleRandom;

/**
 * Final Project
 * A repository of possible names for a Player subclass.
 * 
 * @author Team Plasma
 *
 */
public abstract class Names implements Serializable
{
  private final String[] NAMES; // do not modify any entry!
  private final int MAX_NAMES_TO_START;
  private final ArrayList<String> names;
  
  private static final String NO_NULL = "Names repository does not store null names";
  private final static long serialVersionUID;  
  static
  {
    serialVersionUID
    = ObjectStreamClass.lookup(Names.class).getSerialVersionUID();
  }
  
  /**
   * Construct a new Names repository
   * starting with the initial possible names.
   */
  public Names(final String[] initialNames)
  {
    NAMES = initialNames.clone();
    MAX_NAMES_TO_START = NAMES.length;
    
    names = new ArrayList<String>();
    for (final String name : NAMES)
    {
      names.add(name);
    }
  }
  
  /**
   * Gets the maximum number of names to start with.
   * 
   * @return the number of names to start with
   */
  public int getMaxNamesToStart()
  {
    return MAX_NAMES_TO_START;
  }
  
  /**
   * Gets the current number of names.
   * 
   * @return the current number of names
   */
  public int getCurrentNumberOfNames()
  {
    return names.size();
  }
  
  /**
   * Gets the name at a given index.
   * 
   * @param index
   *  the position of the name to look up
   * @return
   *  the name at that index
   * @throws IndexOutOfBoundsException
   *  if the index is out of range
   *  <code>(index < 0 || index >= getCurrentNumberOfNames())</code>
   */
  public String getName(final int index)
  {
    return names.get(index);
  }
  
  /**
   * If the name exists, returns its index.
   * Otherwise, returns -1.
   * 
   * @param name
   *  the name whose index is to be determined
   * @return
   *  the index of the name if it exists, or -1 otherwise
   * @throws NullPointerException
   *  if the name is null
   */
  public int findName(final String name)
  {
    if (name == null)
    {
      throw new NullPointerException(NO_NULL);
    }
    return names.indexOf(name);
  }
  
  /**
   * Returns whether a given name exists.
   * 
   * @param name
   *  the name to look up
   * @return
   *  {@code true} if the name exists,
   *  {@code false} if it does not
   * @throws NullPointerException
   *  if the name is null
   */
  public boolean hasName(final String name)
  {
    if (name == null)
    {
      throw new NullPointerException(NO_NULL);
    }
    return names.contains(name);
  }
  
  /**
   * Returns whether this repository has any names.
   * 
   * @return
   *  {@code true} if the repository contains any names,
   *  {@code false} if it does not.
   */
  public boolean hasNames()
  {
    return !names.isEmpty();
  }
  
  /**
   * Removes and returns a random name from the repository
   * if it has any names.
   * If it does not have any names,
   * the repository resets, and then a random name
   * is removed and returned from the reset repository.
   * 
   * @return
   *  a randomly removed name from the repository
   */
  public String takeName()
  {
    if (names.isEmpty())
    {
      resetNames();
    }
    return names.remove(SingleRandom.getInstance().nextInt(names.size()));
  }
  
  /**
   * Adds a name to the repository.
   * 
   * @param name
   *  the name to add
   * @return
   *  {@code true} if the name was added and did not already exist
   * @throws NullPointerException
   *  if the name is null
   */
  public boolean addName(final String name)
  {
    final boolean isNull = name == null;
    if (isNull)
    {
      throw new NullPointerException(NO_NULL);
    }
    return !isNull && !names.contains(name) && names.add(name);
  }
  
  /**
   * Replaces a name at a given index with a new name.
   * 
   * @param index
   *  the index of the name to replace
   * @param name
   *  the new name
   * @return
   *  the old name
   *  
   * @throws NullPointerException
   *  if the name is null
   * @throws IndexOutOfBoundsException
   *  if the index is out of range
   *  <code>(index < 0 || index >= getCurrentNumberOfNames())</code>
   */
  public String replaceName(final int index, final String name)
  {
    if (name == null)
    {
      throw new NullPointerException(NO_NULL);
    }
    return names.set(index, name);
  }
  
  /**
   * Replaces an old name with a new name.
   * 
   * @param oldName
   *  the name to replace
   * @param newName
   *  the new name
   *  
   * @return the index at which the name to replace exists
   *  
   * @throws NullPointerException
   *  if either {@code oldName} or {@code newName} are null
   * @throws NoSuchElementException
   *  if {@code oldName} does not exist
   */
  public int replaceName(String oldName, String newName)
  {
    if (oldName == null || newName == null)
    {
      throw new NullPointerException(NO_NULL);
    }
    final int index = names.indexOf(oldName);
    if (index == -1)
    {
      throw new NoSuchElementException(
          "Name " + oldName + " does not exist in this repository");
    }
    names.set(index, newName);
    return index;
  }
  
  /**
   * Removes all the names in this repository.
   */
  public void eraseNames()
  {
    names.clear();
  }
  
  /**
   * Resets the repository to an initial set of names.
   */
  public void resetNames()
  {
    names.clear();
    for (final String name : NAMES)
    {
      names.add(name);
    }
  }
  
  /**
   * Returns a String representation of this repository.
   * 
   * @return the String representation of this repository
   */
  public String toString()
  {
    return "\n" + getClass().getSimpleName() + " are:\n" + names;
  }
 
}