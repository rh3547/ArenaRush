package com.binextechnologies.arenarush.world;

import java.awt.Rectangle;

import com.binextechnologies.arenarush.gameobject.GameObject;
import com.binextechnologies.arenarush.math.Vector2f;
import com.binextechnologies.arenarush.projectiles.Projectile;
import com.binextechnologies.arenarush.tile.Tile;
import com.binextechnologies.arenarush.tile.TileRegistry;

public class CollisionHandler
{
	public static void checkTileCollisions(GameObject obj)
	{
		if (WorldHandler.currentWorld == null) return;
		
		int mapWidth = 0;
		int mapHeight = 0;
		float posX = 0;
		float posY = 0;
		
		mapWidth = WorldHandler.currentWorld.mapWidth;
		mapHeight = WorldHandler.currentWorld.mapHeight;
		
		posX = WorldHandler.currentWorld.posX;
		posY = WorldHandler.currentWorld.posY;
		
		for (int y = 0; y < mapHeight; y++)
		{
			for (int x = 0; x < mapWidth; x++)
			{
				float tilePosX = posX + (x * WorldHandler.currentWorld.tileWidth);
				float tilePosY = posY + (((mapHeight - 1) - y) * WorldHandler.currentWorld.tileHeight);
				
				if (tilePosX > (obj.getPosX() - (WorldHandler.currentWorld.tileWidth * 2)) && 
					tilePosX < (obj.getPosX() + (WorldHandler.currentWorld.tileWidth * 2)) &&
					tilePosY > (obj.getPosY() - (WorldHandler.currentWorld.tileHeight * 2)) && 
					tilePosY < (obj.getPosY() + (WorldHandler.currentWorld.tileHeight * 2)))
				{
					Tile tile = TileRegistry.getTile(WorldHandler.currentWorld.tiles[y][x]);
					
					if (tile.doesCollide())
					{
						Rectangle upRect = tile.getCollisionBox(0, tilePosX, tilePosY);
						Rectangle rightRect = tile.getCollisionBox(1, tilePosX, tilePosY);	
						Rectangle downRect = tile.getCollisionBox(2, tilePosX, tilePosY);	
						Rectangle leftRect = tile.getCollisionBox(3, tilePosX, tilePosY);
						
						if (obj.getCollisionBox(2).intersects(upRect)) // Object's bottom collides with tile top
						{
							obj.collidedWithTile(tile, 2, new Vector2f(tilePosX, tilePosY), upRect);
						}	
						else if (obj.getCollisionBox(1).intersects(leftRect)) // Object's right collides with tile left
						{
							obj.collidedWithTile(tile, 1, new Vector2f(tilePosX, tilePosY), leftRect);
						}
						else if (obj.getCollisionBox(3).intersects(rightRect)) // Object's left collides with tile right
						{
							obj.collidedWithTile(tile, 3, new Vector2f(tilePosX, tilePosY), rightRect);
						}
						else if (obj.getCollisionBox(0).intersects(downRect)) // Object's top collides with tile bottom
						{
							obj.collidedWithTile(tile, 0, new Vector2f(tilePosX, tilePosY), downRect);
						}
						
						if (!obj.getCollisionBox(2).intersects(upRect)) // Object's bottom collides with tile top
							obj.setCollidingDown(false);
						if (!obj.getCollisionBox(1).intersects(leftRect)) // Object's right collides with tile left
							obj.setCollidingRight(false);
						if (!obj.getCollisionBox(3).intersects(rightRect)) // Object's left collides with tile right
							obj.setCollidingLeft(false);
						if (!obj.getCollisionBox(0).intersects(downRect)) // Object's top collides with tile bottom
							obj.setCollidingUp(false);
					}
				}
			}
		}
	}
	
	public static void checkObjectCollisions(GameObject obj)
	{
		if (WorldHandler.currentWorld == null) return;
		
		for (int x = 0; x < WorldHandler.currentWorld.objects.size(); x++)
		{
			GameObject checkObj = WorldHandler.currentWorld.objects.get(x);
			
			if (checkObj == obj)
				continue;
			
			if (checkObj.getPosX() > (obj.getPosX() - (WorldHandler.currentWorld.tileWidth * 2)) && 
				checkObj.getPosX() < (obj.getPosX() + (WorldHandler.currentWorld.tileWidth * 2)) &&
				checkObj.getPosY() > (obj.getPosY() - (WorldHandler.currentWorld.tileHeight * 2)) && 
				checkObj.getPosY() < (obj.getPosY() + (WorldHandler.currentWorld.tileHeight * 2)))
			{
				if (checkObj.doesCollide())
				{
					if (obj.getCollisionBox(2).intersects(checkObj.getCollisionBox(0))) // Object's bottom collides with other's top
					{
						obj.collidedWithObject(checkObj, 2);
					}	
					else if (obj.getCollisionBox(1).intersects(checkObj.getCollisionBox(3))) // Object's right collides with other's left
					{
						obj.collidedWithObject(checkObj, 1);
					}
					else if (obj.getCollisionBox(3).intersects(checkObj.getCollisionBox(1))) // Object's left collides with other's right
					{
						obj.collidedWithObject(checkObj, 3);
					}
					else if (obj.getCollisionBox(0).intersects(checkObj.getCollisionBox(2))) // Object's top collides with other's bottom
					{
						obj.collidedWithObject(checkObj, 0);
					}
					
					if (!obj.getCollisionBox(2).intersects(checkObj.getCollisionBox(0))) // Object's bottom collides with other's top
						obj.setCollidingDown(false);
					if (!obj.getCollisionBox(1).intersects(checkObj.getCollisionBox(3))) // Object's right collides with other's left
						obj.setCollidingRight(false);
					if (!obj.getCollisionBox(3).intersects(checkObj.getCollisionBox(1))) // Object's left collides with other's right
						obj.setCollidingLeft(false);
					if (!obj.getCollisionBox(0).intersects(checkObj.getCollisionBox(2))) // Object's top collides with other's bottom
						obj.setCollidingUp(false);
				}
			}
		}
	}
	
	public static void checkProjectileObjectCollisions(Projectile obj)
	{
		if (WorldHandler.currentWorld == null) return;
		
		for (int x = 0; x < WorldHandler.currentWorld.objects.size(); x++)
		{
			GameObject checkObj = WorldHandler.currentWorld.objects.get(x);
			
			if (checkObj.getPosX() > (obj.getPosX() - (WorldHandler.currentWorld.tileWidth * 2)) && 
				checkObj.getPosX() < (obj.getPosX() + (WorldHandler.currentWorld.tileWidth * 2)) &&
				checkObj.getPosY() > (obj.getPosY() - (WorldHandler.currentWorld.tileHeight * 2)) && 
				checkObj.getPosY() < (obj.getPosY() + (WorldHandler.currentWorld.tileHeight * 2)))
			{
				if (checkObj.doesCollide())
				{
					if (obj.getCollisionBox(2).intersects(checkObj.getCollisionBox(0))) // Object's bottom collides with other's top
					{
						obj.projectileObjectCollide(checkObj, 2);
					}	
					else if (obj.getCollisionBox(1).intersects(checkObj.getCollisionBox(3))) // Object's right collides with other's left
					{
						obj.projectileObjectCollide(checkObj, 1);
					}
					else if (obj.getCollisionBox(3).intersects(checkObj.getCollisionBox(1))) // Object's left collides with other's right
					{
						obj.projectileObjectCollide(checkObj, 3);
					}
					else if (obj.getCollisionBox(0).intersects(checkObj.getCollisionBox(2))) // Object's top collides with other's bottom
					{
						obj.projectileObjectCollide(checkObj, 0);
					}
					
					if (!obj.getCollisionBox(2).intersects(checkObj.getCollisionBox(0))) // Object's bottom collides with other's top
						obj.setCollidingDown(false);
					if (!obj.getCollisionBox(1).intersects(checkObj.getCollisionBox(3))) // Object's right collides with other's left
						obj.setCollidingRight(false);
					if (!obj.getCollisionBox(3).intersects(checkObj.getCollisionBox(1))) // Object's left collides with other's right
						obj.setCollidingLeft(false);
					if (!obj.getCollisionBox(0).intersects(checkObj.getCollisionBox(2))) // Object's top collides with other's bottom
						obj.setCollidingUp(false);
				}
			}
		}
	}
	
	public static void checkProjectileTileCollisions(Projectile obj)
	{
		if (WorldHandler.currentWorld == null) return;
		
		int mapWidth = 0;
		int mapHeight = 0;
		float posX = 0;
		float posY = 0;
		
		mapWidth = WorldHandler.currentWorld.mapWidth;
		mapHeight = WorldHandler.currentWorld.mapHeight;
		
		posX = WorldHandler.currentWorld.posX;
		posY = WorldHandler.currentWorld.posY;
		
		for (int y = 0; y < mapHeight; y++)
		{
			for (int x = 0; x < mapWidth; x++)
			{
				float tilePosX = posX + (x * WorldHandler.currentWorld.tileWidth);
				float tilePosY = posY + (((mapHeight - 1) - y) * WorldHandler.currentWorld.tileHeight);
				
				if (tilePosX > (obj.getPosX() - (WorldHandler.currentWorld.mapWidth * 2)) && 
					tilePosX < (obj.getPosX() + (WorldHandler.currentWorld.mapWidth * 2)) &&
					tilePosY > (obj.getPosY() - (WorldHandler.currentWorld.mapHeight * 2)) && 
					tilePosY < (obj.getPosY() + (WorldHandler.currentWorld.mapHeight * 2)))
				{
					Tile tile = TileRegistry.getTile(WorldHandler.currentWorld.tiles[y][x]);
					
					if (tile.doesCollide())
					{
						Rectangle upRect = tile.getCollisionBox(0, tilePosX, tilePosY);
						Rectangle rightRect = tile.getCollisionBox(1, tilePosX, tilePosY);	
						Rectangle downRect = tile.getCollisionBox(2, tilePosX, tilePosY);	
						Rectangle leftRect = tile.getCollisionBox(3, tilePosX, tilePosY);
						
						if (obj.getCollisionBox(2).intersects(upRect)) // Object's bottom collides with tile top
						{
							obj.projectileTileCollide(tile, 0, new Vector2f(tilePosX, tilePosY), upRect);
						}	
						else if (obj.getTotalBounds().intersects(leftRect)) // Object's right collides with tile left
						{
							obj.projectileTileCollide(tile, 3, new Vector2f(tilePosX, tilePosY), leftRect);
						}
						else if (obj.getTotalBounds().intersects(rightRect)) // Object's left collides with tile right
						{
							obj.projectileTileCollide(tile, 1, new Vector2f(tilePosX, tilePosY), rightRect);
						}
						else if (obj.getTotalBounds().intersects(downRect)) // Object's top collides with tile bottom
						{
							obj.projectileTileCollide(tile, 2, new Vector2f(tilePosX, tilePosY), downRect);
						}
						
						if (!obj.getCollisionBox(2).intersects(upRect)) // Object's bottom collides with tile top
							obj.setCollidingDown(false);
						if (!obj.getCollisionBox(1).intersects(leftRect)) // Object's right collides with tile left
							obj.setCollidingRight(false);
						if (!obj.getCollisionBox(3).intersects(rightRect)) // Object's left collides with tile right
							obj.setCollidingLeft(false);
						if (!obj.getCollisionBox(0).intersects(downRect)) // Object's top collides with tile bottom
							obj.setCollidingUp(false);
					}
				}
			}
		}
	}
}
