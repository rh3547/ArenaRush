package com.binextechnologies.arenarush.world;

import com.binextechnologies.arenarush.math.Vector2f;

public class WorldHandler
{
	public static World currentWorld;
	
	/**
	 * Gets the origin coordinates of the tile located within the
	 * specified coordinates.
	 * @param posX
	 * @param posY
	 * @return
	 */
	public static Vector2f getTileOriginFromCoords(float posX, float posY)
	{
		if (currentWorld == null)
			return null;
		
		int coordX = (int) (currentWorld.tileWidth * (Math.floor(posX / currentWorld.tileWidth)));
		int coordY = (int) (currentWorld.tileHeight * (Math.floor(posY / currentWorld.tileHeight)));
		
		//int coordX = (int) (posX - (posX % currentWorld.tileWidth));
		//int coordY = (int) (posY - (posY % currentWorld.tileHeight));
		
		return new Vector2f(coordX, coordY);
	}
	
	/**
	 * Get the tile id of the tile located at the
	 * specified coordinates.
	 * @param posX
	 * @param posY
	 * @return
	 */
	public static int getTileIdAtCoords(float posX, float posY)
	{
		if (currentWorld == null)
			return -1;
		
		float newPosX = posX;
		float newPosY = posY;
		
		if (posY >= currentWorld.mapHeight * currentWorld.tileHeight)
			newPosY = currentWorld.mapHeight * currentWorld.tileHeight - 2;
		if (posX >= currentWorld.mapWidth * currentWorld.tileWidth)
			newPosX = currentWorld.mapWidth * currentWorld.tileWidth - 2;
		
		Vector2f vec = getTileOriginFromCoords(newPosX, newPosY);
		
		int arrayIndexX = (int) (vec.x / currentWorld.tileWidth);
		int arrayIndexY = (currentWorld.mapHeight - 1) - ((int) (vec.y / currentWorld.tileHeight));
		
		if (arrayIndexX < 0)
			arrayIndexX = 0;
		if (arrayIndexY < 0)
			arrayIndexY = 0;
		
		return currentWorld.tiles[arrayIndexY][arrayIndexX];
	}
	
	/**
	 * Get the coordinates of a tile adjacent
	 * to the one at the given coordinates.
	 * The tile that will be returned is determined
	 * by the argument "side".
	 * Sides:
	 * T = the tile at the given coordinates.
	 * 
	 * 012
	 * 3T4
	 * 567
	 * 
	 * @param posX
	 * @param posY
	 * @param side
	 * @return
	 */
	public static Vector2f getCoordsOfAdjacentTile(float posX, float posY, int side)
	{
		if (currentWorld == null)
			return null;
		
		Vector2f vec = getTileOriginFromCoords(posX, posY);
		
		switch (side)
		{
			case 0:
				return new Vector2f(vec.x - currentWorld.tileWidth, vec.y - currentWorld.tileHeight);
				
			case 1:
				return new Vector2f(vec.x, vec.y - currentWorld.tileHeight);
				
			case 2:
				return new Vector2f(vec.x + currentWorld.tileWidth, vec.y - currentWorld.tileHeight);
				
			case 3:
				return new Vector2f(vec.x - currentWorld.tileWidth, vec.y);
			
			case 4:
				return new Vector2f(vec.x, vec.y);
				
			case 5:
				return new Vector2f(vec.x + currentWorld.tileWidth, vec.y);
				
			case 6:
				return new Vector2f(vec.x - currentWorld.tileWidth, vec.y + currentWorld.tileHeight);
				
			case 7:
				return new Vector2f(vec.x, vec.y + currentWorld.tileHeight);
				
			case 8:
				return new Vector2f(vec.x + currentWorld.tileWidth, vec.y + currentWorld.tileHeight);
				
			default:
				return null;
		}
	}
}
