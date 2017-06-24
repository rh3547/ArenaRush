package com.binextechnologies.arenarush.ai;

import java.util.ArrayList;

import com.binextechnologies.arenarush.gameobject.GameObject;
import com.binextechnologies.arenarush.gameobject.entity.Entity;
import com.binextechnologies.arenarush.math.Vector2f;
import com.binextechnologies.arenarush.pathing.AStarNode;
import com.binextechnologies.arenarush.pathing.AStarPath;
import com.binextechnologies.arenarush.world.WorldHandler;

/**
 * An AI script that uses the A* algorithm to
 * follow the given GameObject.
 * 
 * @author Ryan Hochmuth
 *
 */
public class AIAStarFollow
{
	private Entity thisObj; // The entity that is doing the following
	private GameObject target; // The target object to follow
	
	public ArrayList<AStarNode> path = null; // The A* path to the target
	public int pathTimer = 0; // A timer to determine when a path should be re-calculated
	
	/**
	 * Create a new AIAStarFollow script.
	 * @param thisObj
	 * @param target
	 */
	public AIAStarFollow(Entity thisObj, GameObject target)
	{
		this.thisObj = thisObj;
		this.target = target;
	}
	
	/**
	 * Run this AI script.  Call this every update.  Everything else is handled.
	 * @return boolean - whether or not the path to the target was found.
	 */
	public boolean runScript()
	{
		if (!target.canPathTo) // If the target is not allowed to be found, return false
		{
			thisObj.velX = 0;
			thisObj.velY = 0;
			path = null;
			return false;
		}
		
		pathTimer++;
		
		Vector2f startVec = WorldHandler.getTileOriginFromCoords(thisObj.posX + (thisObj.width / 2), thisObj.posY + (thisObj.height / 2));
		Vector2f goalVec = WorldHandler.getTileOriginFromCoords(target.posX + (target.width / 2), target.posY + (target.height / 2));
		
		if (pathTimer % 3 == 0) // Re-calculate a path 20 times a second
			path = AStarPath.findPath(startVec, goalVec);
		
		if (path != null)
		{
			if (path.size() > 0)
			{
				Vector2f vec = path.get(path.size() - 1).pos; // The closest node in the path
				
				/*
				 * Move to the node
				 */
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
