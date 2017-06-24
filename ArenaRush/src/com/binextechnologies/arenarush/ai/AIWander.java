package com.binextechnologies.arenarush.ai;

import java.util.Random;

import com.binextechnologies.arenarush.gameobject.entity.Entity;
import com.binextechnologies.arenarush.main.ARClock;
import com.binextechnologies.arenarush.math.Vector2f;
import com.binextechnologies.arenarush.tile.Tile;
import com.binextechnologies.arenarush.tile.TileRegistry;
import com.binextechnologies.arenarush.world.WorldHandler;

/**
 * An AI script that causes the given object to "wander"
 * around to random locations in the world within the
 * given radius at the given rate.  Uses the A* algorithm
 * via the AIAStarWaypoint script.
 * 
 * @author Ryan Hochmuth
 *
 */
public class AIWander
{
	private Entity thisObj; // The object that should wander
	private int radius; // The radius in which the object can go to
	private int rate; // The rate at which the object should wander to another place
	public int wanderTimer = 0;
	public AIAStarWaypoint aStarWaypoint = null; // The waypoint A* path to follow
	
	/**
	 * Create a new AIWander script.
	 * @param thisObj
	 * @param radius
	 * @param rate
	 */
	public AIWander(Entity thisObj, int radius, int rate)
	{
		this.thisObj = thisObj;
		this.radius = radius;
		this.rate = rate * ARClock.tps;
	}
	
	/**
	 * Run this AI script.  Call this every update.  Everything else is handled.
	 * @return boolean - whether or not a place could be found or a path could be found
	 */
	public boolean runScript()
	{
		wanderTimer++;
		
		if (wanderTimer % rate == 0) // Only update this as often as rate
		{
			Random rand = new Random();
			boolean searching = true; // True if a valid wander location hasn't yet been found
			
			int randPosX = 0;
			int randPosY = 0;
			
			Vector2f goal = null;
			
			while (searching)
			{
				// Generate a random position within the world boundaries
				randPosX = (int) (WorldHandler.currentWorld.posX + (rand.nextInt(WorldHandler.currentWorld.mapWidth * WorldHandler.currentWorld.tileWidth)));
				randPosY = (int) (WorldHandler.currentWorld.posY + (rand.nextInt(WorldHandler.currentWorld.mapHeight * WorldHandler.currentWorld.tileHeight)));
				
				// If the generated location is outside of the radius, set it the the radius
				if (randPosX < ((thisObj.posX + (thisObj.width / 2)) - radius))
					randPosX = (int) ((thisObj.posX + (thisObj.width / 2)) - radius);
				if (randPosX > ((thisObj.posX + (thisObj.width / 2)) + radius))
					randPosX = (int) ((thisObj.posX + (thisObj.width / 2)) + radius);
				if (randPosY < ((thisObj.posY + (thisObj.height / 2)) - radius))
					randPosY = (int) ((thisObj.posY + (thisObj.height / 2)) - radius);
				if (randPosY > ((thisObj.posY + (thisObj.height / 2)) + radius))
					randPosY = (int) ((thisObj.posY + (thisObj.height / 2)) + radius);
				
				// Make sure location chosen is within world boundaries
				if (randPosX < WorldHandler.currentWorld.posX + 0 || randPosX > WorldHandler.currentWorld.posX +
				(WorldHandler.currentWorld.mapWidth * WorldHandler.currentWorld.tileWidth - 0) ||
				randPosY < WorldHandler.currentWorld.posY + 0 || randPosY > WorldHandler.currentWorld.posY +
				(WorldHandler.currentWorld.mapHeight * WorldHandler.currentWorld.tileHeight - 0)) continue;
				
				int tileId = WorldHandler.getTileIdAtCoords(randPosX, randPosY);
				if (tileId < 0) continue; // Make sure the tile found is valid
				
				Tile tile = TileRegistry.getTile(tileId);
				if (tile.collides) continue; // Make sure the tile found doesn't collide
				
				goal = new Vector2f(randPosX, randPosY);
				
				if (goal != null)
					aStarWaypoint = new AIAStarWaypoint(thisObj, goal); // Generate an AStar path to the goal
				
				searching = false; // If the search gets to this point then a valid location has been found!
			}
		}

		if (aStarWaypoint == null) return false;
		if (!aStarWaypoint.runScript()) return false;
		
		return true;
	}
}
