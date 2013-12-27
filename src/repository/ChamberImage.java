package repository;

import game.GameFacade;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D.Double;
import java.util.Observer;
import java.util.Set;
import utility.SingleRandom;
import demesnes.ChamberFacade;
import demesnes.Direction;
import demesnes.Location;
import demesnes.MazeFacade;

/**
 * Final Project
 * @author Team Plasma
 *
 * Creates the Image of the player moving through the chambers
 */
public class ChamberImage
{	
	
	/**
	 * Initialize instance variables
	 */
	private final double height;
	private final double width;
	private final double scaleWidth;
	private final double scaleHeight;
	private final GameFacade game;
	private final MazeFacade maze;
	private final double centerX;
	private final double centerY;
	private final boolean isPoking;
	private final boolean isTerrifying;
	
	/**
	 * Chamber Image constructor, takes in the game, and the timer initiators
	 * 
	 * Creates an image of a human player moving throughout a maze object made of chambers
	 * 
	 * @param width
	 * @param height
	 * @param game
	 * @param isPoking
	 * @param isTerrifying
	 */
	
	public ChamberImage(int width, int height, GameFacade game, boolean isPoking, boolean isTerrifying)
	{
		this.width = width;
		this.height = height;
		this.game = game;
		this.scaleWidth = width/6;
		this.scaleHeight = height/6;
		this.maze = game.getMaze();
		this.centerX= width/2;
		this.centerY = height/2;
		this.isPoking = isPoking;
		this.isTerrifying = isTerrifying;
	}
	
	/**
	 * Draw function, draws the entirety of the chamber and mazes - fills ViewImage panel
	 * @param g2
	 */
	public void draw(Graphics2D g2)
	{
		g2.setColor(Color.GRAY);
		
		//If there is a maze object
		if(maze != null)
		{
		  //Get the keySet of the chambers of the maze
		  Set<Location> keySet = maze.getKeySet();
		  
		  //Set the offset point to the origin
		  int offsetColumn = 0;
		  int offsetRow = 0;
			
		  //Set the current location to the player's location
		  final Location currentLocation = game.getCurrentLocation();
		  
		  //For each location in the maze
		  for (Location key : keySet)
		  {
			  
			//Set the offset point to the player's location
  			offsetColumn = currentLocation.getColumn();
  			offsetRow = currentLocation.getRow();
  			
  			//Set the center of the compass to the bottom right corner
  			double compassCenterX = (width-scaleWidth);
  			double compassCenterY = (height-scaleHeight);
  					
  			//Draw the Vertical line of the compass
  			Line2D.Double compassNS = 
  					new Line2D.Double(compassCenterX, compassCenterY+scaleHeight/3, compassCenterX, compassCenterY-scaleWidth/3);
  					g2.setColor(Color.BLUE);
  					g2.draw(compassNS);
  					
  			//Draw the Horiz line of the compass
  			Line2D.Double compassWE = 
  					new Line2D.Double(compassCenterX-scaleWidth/3, compassCenterY-scaleHeight/6, compassCenterX+scaleWidth/3, compassCenterY-scaleHeight/6);
  					g2.setColor(Color.BLUE);
  					g2.draw(compassWE);
  					
  			//Draw the Needle of the compass
  			Line2D.Double compassNeedle = 
  		  			new Line2D.Double(compassCenterX, compassCenterY-scaleHeight/6, compassCenterX+(scaleWidth*-offsetColumn/12), compassCenterY-scaleHeight/6-(scaleHeight)*-offsetRow/12);
  					BasicStroke penWidth = new BasicStroke(4);  //This is > default
  		  			g2.setStroke(penWidth);
  		  			g2.setColor(Color.CYAN);
  		  			g2.draw(compassNeedle);
			
  		  	//Set the current chamber to the chamber in loop
			ChamberFacade chamber = maze.getChamber(key);
			
			//WARNING - Ugly Math ahead!
			
			//Set the chamber center to the chambers location, relative to the players location (multiplied by the scale relative to the center)
			double chamberX = (key.getColumn()-offsetColumn)*scaleWidth*2+centerX;
	    	double chamberY = centerY-(key.getRow()-offsetRow)*scaleHeight*2;  	

	    	//If the chamber is visited
    		if(chamber.hasVisited())
    		{
    			//Make the chamber dark grey (visible)
    			Rectangle2D.Double drawWall = 
    					new Rectangle2D.Double(chamberX-scaleWidth, 
			      				chamberY-scaleHeight,
			      				scaleWidth*2, 
			       				scaleHeight*2);

    			g2.setColor(Color.DARK_GRAY);
    			g2.fill(drawWall);
    			
    			//Draw a gruman inside
    			if(chamber.hasGruman())
	    		{
    				int sacks = maze.getGrumanSacks(key);
	    			GrumanImage gruman = new GrumanImage(width, chamberX-scaleWidth, chamberY, sacks, maze.getGrumanStrategy(key));
		    		gruman.draw(g2);
		    				    		
		    		//If the player is attack the Gruman
		    		if(isPoking)
		    		{
		
		    		  //Draw the attacking X through the Gruman
					  Line2D.Double cross = new Line2D.Double(centerX-scaleWidth/3-10, centerY-25, centerX-scaleWidth+10, centerY+25);
					  Line2D.Double crossTwo = new Line2D.Double(centerX-scaleWidth/3-10, centerY+25, centerX-scaleWidth+10, centerY-25);
					  g2.setColor(Color.PINK);
		  		      g2.setStroke(new BasicStroke(4));
					  g2.draw(cross);
					  g2.draw(crossTwo);		    					 
		    		}
		    		
	    		}
    		}
    		
    		//If the chamber is the human's current
    		if(game.isCurrentChamber(key))
	    	{
	    		//Draw the human
			    HumanImage human = new HumanImage(width, centerX, centerY, game.getHumanSacks(), game.getHumanStrategy());
	    		human.draw(g2);
	    		
	    	}
    		
    		//If the player is being attacked by the Gruman
    		if(isTerrifying)
    		{

    		  //Draw an X through the player
			  Line2D.Double crossA = new Line2D.Double(centerX+scaleWidth/3-10, centerY-25, centerX+scaleWidth+10, centerY+25);
			  Line2D.Double crossB = new Line2D.Double(centerX+scaleWidth/3-10, centerY+25, centerX+scaleWidth+10, centerY-25);
			  g2.setColor(Color.RED);
  		      g2.setStroke(new BasicStroke(4));
			  g2.draw(crossA);
			  g2.draw(crossB);    					 
    		}
	    	
    		//For each direction of the chamber
	    	for (Direction d : Direction.values()) //for each direction in chamber
		    {
	    		
	    		//Set the middle of the chamber to (0,0)
	    		//Top left (-1, -1), top right (1, -1), bottom right (-1, 1)
	    		
		    	double xOffset;
		    	double yOffset;
		    	double rectWidth;
		    	double rectHeight;

		    	if(d == Direction.NORTH || d == Direction.WEST)
	    		{
		    		xOffset = -1;
		    		yOffset = -1;
		    	}
		    	else if(d == Direction.SOUTH)
		    	{
		    		xOffset = -1;
		    		yOffset = 1;
		    	}
		    	else
		    	{
		    		xOffset = 1;
		    		yOffset = -1;
		    	}
		    	
		    	//Set the wall sizes depending if the wall is North/South(horizontal) or East/West(vertical)
		    	if(d.getHorizontalOffset()==0)
		    	{
		    		rectWidth = scaleWidth*2;
		    		rectHeight = scaleHeight/25;
		    	}
		    	else
		    	{
		    		rectWidth = scaleWidth/25;
		    		rectHeight = scaleHeight*2;
		    	}	    			    		
		    	
		    	//If the direction is a wall
		    	if(!chamber.getWall(d).hasDoor())
		    	{
		    		//Draw a wall
		    		Rectangle2D.Double drawWall = 
		    		new Rectangle2D.Double(chamberX+(xOffset*scaleWidth), 
			      				chamberY+(yOffset*scaleHeight),
			      				rectWidth, 
			       				rectHeight);
		    		
		    		penWidth = new BasicStroke(2);  //This is > default
		    	    g2.setStroke(penWidth);
		    		g2.setColor(Color.GREEN);
		    		g2.fill(drawWall);
		    		g2.setColor(Color.GREEN);
		    		g2.draw(drawWall);
		    	}
		    	
		    	//Otherwise, hate yourself with this math
		    	else
		    	{
		    		penWidth = new BasicStroke(2);
		    		
		    		//Change the height depending on North/South or East/West
		    		if(d.getHorizontalOffset()==0)
			    	{
			    		rectWidth = rectWidth/3;
			    	}
			    	else
			    	{
			    		rectHeight = rectHeight/3;
			    	}
		    		
		    		//Draw the first half of the wall
		    		Rectangle2D.Double halfWall1 = 
				    		new Rectangle2D.Double(chamberX+(xOffset*scaleWidth), 
					      				chamberY+(yOffset*scaleHeight),
					      				rectWidth, 
					       				rectHeight);
				    		
				    	g2.setStroke(penWidth);
				    	g2.setColor(Color.GREEN);
				    	g2.fill(halfWall1);
				    	g2.setColor(Color.GREEN);
					   	g2.draw(halfWall1);
				
				//Now for the other part of the wall! Change the orientation of the wall
				if(d.getHorizontalOffset()==0)
				{
					xOffset = xOffset*-1/3;
				}
				else
				{
					yOffset = yOffset*-1/3;
				}
				
					//Draw the other half of the wall   	
					Rectangle2D.Double halfWall2 = 
					new Rectangle2D.Double(chamberX+(xOffset*scaleWidth), 
					   				chamberY+(yOffset*scaleHeight),
					  				rectWidth, 
					   				rectHeight);
					    		
					g2.setStroke(penWidth);
					g2.setColor(Color.GREEN);
					g2.fill(halfWall2);
					g2.setColor(Color.GREEN);
					g2.draw(halfWall2);
		    	}
		    }
		  }
		}
	}
}
