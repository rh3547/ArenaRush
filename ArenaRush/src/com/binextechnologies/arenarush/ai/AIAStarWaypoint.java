package com.binextechnologies.arenarush.ai;

import java.util.ArrayList;

import com.binextechnologies.arenarush.gameobject.entity.Entity;
import com.binextechnologies.arenarush.math.Vector2f;
import com.binextechnologies.arenarush.pathing.AStarNode;
import com.binextechnologies.arenarush.pathing.AStarPath;
import com.binextechnologies.arenarush.world.WorldHandler;

/**
 * An AI script that generates a path to the given 
 * vector using the A* algorithm for the given object 
 * to follow.
 * 
 * @author Ryan Hochmuth
 *
 */
public class AIAStarWaypoint
{
	private Entity thisObj; // The object to follow the path
	private Vector2f goal; // The location of the waypoint to travel to
	
	public ArrayList<AStarNode> path = null; // The A* path to follow
	public boolean pathFound = false; // If the path has been found
	public boolean reachedWaypoint = false; // If the object has completed the path to the waypoint
	public int currentIndex;
	
	/**
	 * Create a new AIAStarWaypoint script.
	 * @param thisObj
	 * @param goal
	 */
	public AIAStarWaypoint(Entity thisObj, Vector2f goal)
	{
		this.thisObj = thisObj;
		this.goal = goal;
	}
	
	/**
	 * Run this AI script.  Call this every update.  Everything else is handled.
	 * @return boolean - whether or not a valid path could be found
	 */
	public boolean runScript()
	{
		if (reachedWaypoint) return false; // If the waypoint has been reached, stop searching/moving
		
		Vector2f startVec = WorldHandler.getTileOriginFromCoords(thisObj.posX + (thisObj.width / 2), thisObj.posY + (thisObj.height / 2));
		Vector2f goalVec = WorldHandler.getTileOriginFromCoords(goal.x + (WorldHandler.currentWorld.tileWidth / 2), 
				goal.y + (WorldHandler.currentWorld.tileHeight / 2));
		
		if (!pathFound) // Once the path has been found, don't look for another.
		{
			path = AStarPath.findPath(startVec, goalVec);
			
			if (path == null) return false;
			
			pathFound = true;
			currentIndex = path.size() - 1;
		}
		
		if (path != null)
		{
			if (path.size() > 0)
			{
				Vector2f vec = path.get(currentIndex).pos;
				Vector2f objPos = WorldHandler.getTileOriginFromCoords(thisObj.posX + (thisObj.width / 2), thisObj.posY + (thisObj.height / 2));
				
				/*
				 * If the given object's position is equal to the current node target,
				 * start moving towards the next node on the path.
				 */
				if (objPos.x == vec.x && objPos.y == vec.y) 
				{											
					if (currentIndex == 0) // If the node reached is the waypoint, stop pathing to it
					{
						thisObj.velX = 0;
						thisObj.velY = 0;
						reachedWaypoint = true;
						return false;
					}
					
					currentIndex--;
					vec = path.get(currentIndex).pos;
				}
				
				float diffX = (vec.x + (WorldHandler.currentWorld.tileWidth / 2)) - thisObj.posX;
				float diffY = (vec.y + (WorldHandler.currentWorld.tileHeight / 2)) - thisObj.posY;
			
				float angle = (float)Math.atan2(diffY, diffX);

				if (!thisObj.isCollidingLeft() || !thisObj.isCollidingRight())
					thisObj.velX = (float) (thisObj.speed * Math.cos(angle));
			
				if (!thisObj.isCollidingUp() || !thisObj.isCollidingDown())
					thisObj.velY = (float) (thisObj.speed * Math.sin(angle));
			}
			
			return true;
		}
		else return false;
	}
}
