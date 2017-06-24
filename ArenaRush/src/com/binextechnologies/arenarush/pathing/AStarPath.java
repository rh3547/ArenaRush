package com.binextechnologies.arenarush.pathing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.binextechnologies.arenarush.math.Vector2f;
import com.binextechnologies.arenarush.tile.Tile;
import com.binextechnologies.arenarush.tile.TileRegistry;
import com.binextechnologies.arenarush.world.WorldHandler;

/**
 * A path generating technique used to find the most efficient
 * way to arrive at a location while avoiding obstacles.
 * 
 * @author Ryan Hochmuth
 *
 */
public class AStarPath
{
	/*
	 * A comparator used to sort lists of nodes based on move cost
	 */
	private static Comparator<AStarNode> nodeSort = new Comparator<AStarNode>()
	{
		@Override
		public int compare(AStarNode node1, AStarNode node2)
		{
			if (node2.fCost < node1.fCost)
				return 1;
			if (node2.fCost > node1.fCost)
				return -1;
			
			return 0;
		}
	};
	
	/**
	 * Find a path from the given start to the given goal.
	 * @param start - Vector2f
	 * @param goal - Vector2f
	 * @return path - ArrayList<AStarNode>
	 */
	public static ArrayList<AStarNode> findPath(Vector2f start, Vector2f goal)
	{
		ArrayList<AStarNode> openList = new ArrayList<AStarNode>(); // The list of nodes that could be in the path
		ArrayList<AStarNode> closedList = new ArrayList<AStarNode>(); // The list of nodes checked and in the path
		
		AStarNode current = new AStarNode(start, null, 0, getDistance(start, goal)); // The current node
		openList.add(current);
		
		while (openList.size() > 0) // While there are still some nodes to check (will fall out if a path cannot be found)
		{
			Collections.sort(openList, nodeSort); // Sort the openList by move cost
			
			current = openList.get(0);
			
			if (current.pos.equals(goal)) // If the current node being checked equals the goal, then the path has been found
			{
				ArrayList<AStarNode> path = new ArrayList<AStarNode>();
				
				while (current.parent != null)
				{
					path.add(current);
					current = current.parent;
				}
				
				openList.clear();
				closedList.clear();
				
				return path;
			}
			
			openList.remove(current);
			closedList.add(current);
			
			ArrayList<CheckedTile> tiles = new ArrayList<CheckedTile>();
			
			/*
			 * Obtain all tiles adjacent to the current.
			 */
			for (int x = 0; x < 9; x++)
			{
				if (x == 4) continue;
				
				float posX = current.pos.x;
				float posY = current.pos.y;
				
				Vector2f tilePos = WorldHandler.getCoordsOfAdjacentTile(posX, posY, x);
				if (tilePos == null) continue;
				
				if (tilePos.x < WorldHandler.currentWorld.posX + 0 || tilePos.x > WorldHandler.currentWorld.posX +
				(WorldHandler.currentWorld.mapWidth * WorldHandler.currentWorld.tileWidth - 0) ||
				tilePos.y < WorldHandler.currentWorld.posY + 0 || tilePos.y > WorldHandler.currentWorld.posY +
				(WorldHandler.currentWorld.mapHeight * WorldHandler.currentWorld.tileHeight - 0)) continue;
				
				int tileId = WorldHandler.getTileIdAtCoords(tilePos.x, tilePos.y);
				
				if (tileId < 0) continue;
				
				Tile tile = TileRegistry.getTile(tileId);
				
				if (tile == null) continue;
				
				boolean collides = false;
				
				if (tile.doesCollide()) collides = true;
				
				tiles.add(new CheckedTile(x, collides, tilePos));
			}
			
			/*
			 * Check all of the adjacent tiles.  If they collide or
			 * don't meet other criteria, they get skipped and
			 * aren't added to the openList as a candidate.
			 */
			for (CheckedTile tile : tiles)
			{
				//int tile0 = getCheckedTileIndex(tiles, 0);
				int tile1 = getCheckedTileIndex(tiles, 1);
				//int tile2 = getCheckedTileIndex(tiles, 2);
				int tile3 = getCheckedTileIndex(tiles, 3);
				//int tile4 = getCheckedTileIndex(tiles, 4);
				int tile5 = getCheckedTileIndex(tiles, 5);
				//int tile6 = getCheckedTileIndex(tiles, 6);
				int tile7 = getCheckedTileIndex(tiles, 7);
				//int tile8 = getCheckedTileIndex(tiles, 8);
				
				if (tile.collides) continue;
				if (tile1 != -1 && tile3 != -1)
					if (tile.adjacentPos == 0 && (tiles.get(tile1).collides || tiles.get(tile3).collides)) continue;
				if (tile1 != -1 && tile5 != -1)
					if (tile.adjacentPos == 2 && (tiles.get(tile1).collides || tiles.get(tile5).collides)) continue;
				if (tile3 != -1 && tile7 != -1)
					if (tile.adjacentPos == 6 && (tiles.get(tile3).collides || tiles.get(tile7).collides)) continue;
				if (tile5 != -1 && tile7 != -1)
					if (tile.adjacentPos == 8 && (tiles.get(tile5).collides || tiles.get(tile7).collides)) continue;
				
				double gCost = current.gCost + getDistance(current.pos, tile.vec); // Move cost since from the start until now
				double hCost = getDistance(tile.vec, goal); // Total move cost left until the goal, ignoring obstacles
				
				/*
				 * Compare move costs, add the lowest one to the open list.
				 */
				AStarNode node = new AStarNode(tile.vec, current, gCost, hCost);
				if (vecInList(closedList, tile.vec) && gCost >= node.gCost) continue;
				if (!vecInList(openList, tile.vec) || gCost < node.gCost)
					openList.add(node);
			}
		}
		
		closedList.clear();
		return null;
	}
	
	/**
	 * Check if the given vecotr is in the given list.
	 * @param list
	 * @param vec
	 * @return
	 */
	private static boolean vecInList(ArrayList<AStarNode> list, Vector2f vec)
	{
		for (AStarNode node : list)
		{
			if (node.pos.equals(vec)) return true;
		}
		
		return false;
	}
	
	/**
	 * Get the move cost distance between the two given vectors.
	 * @param start
	 * @param goal
	 * @return
	 */
	private static double getDistance(Vector2f start, Vector2f goal)
	{
		double dx = start.x - goal.x;
		double dy = start.y - goal.y;
		
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	/**
	 * Get the index in a list of the specified adjacent positioned tile.
	 * @param tiles
	 * @param pos
	 * @return
	 */
	private static int getCheckedTileIndex(ArrayList<CheckedTile> tiles, int pos)
	{
		for (int x = 0; x < tiles.size(); x++)
		{
			if (tiles.get(x).adjacentPos == pos)
				return x;
		}
		
		return -1;
	}
}

/**
 * A class used to keep track of various attributes
 * when checking adjacent tiles for the A* algorithm.
 * 
 * @author Ryan Hochmuth
 *
 */
class CheckedTile
{
	public int adjacentPos;
	public boolean collides;
	public Vector2f vec;
	
	public CheckedTile(int adjacentPos, boolean collides, Vector2f vec)
	{
		this.adjacentPos = adjacentPos;
		this.collides = collides;
		this.vec = vec;
	}
}
