package com.binextechnologies.arenarush.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.binextechnologies.arenarush.gameobject.GameObject;
import com.binextechnologies.arenarush.main.ARToolkit;
import com.binextechnologies.arenarush.main.ArenaRush;
import com.binextechnologies.arenarush.player.Player;
import com.binextechnologies.arenarush.tile.Tile;
import com.binextechnologies.arenarush.tile.TileRegistry;
import com.binextechnologies.arenarush.world.spawning.SpawnPoint;

@SuppressWarnings("serial")
public class World implements Serializable
{
	public int mapWidth = 128; // The map width (in tiles)
	public int mapHeight = 128; // The map height (in tiles)
	public int tileWidth = 16;
	public int tileHeight = 16;
	
	public int[][] tiles; // The integer "map" of this world
	
	public String name; // The name of this world
	public float posX; // The x position of this world
	public float posY; // The y position of this world
	
	/*
	 * In World Data
	 */
	public ArrayList<Player> players = new ArrayList<Player>(); // The players in this world
	public ArrayList<GameObject> objects = new ArrayList<GameObject>(); // The object and entities in this world	
	public ArrayList<SpawnPoint> spawns = new ArrayList<SpawnPoint>();	
	
	public World(String worldName, int posX, int posY, int tileWidth, int tileHeight)
	{
		this.name = worldName;
		this.posX = posX;
		this.posY = posY;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		
		tiles = new int[mapHeight][mapWidth];
	}
	
	public void initWorld()
	{
		Iterator<Integer> iterator = TileRegistry.tileRegistry.keySet().iterator();
		
		while(iterator.hasNext())
		{
			int key = iterator.next();
		    Tile tile = TileRegistry.getTile(key);
		    
		    if (tile.image != null)
		    	tile.image = ARToolkit.resizeImage(ARToolkit.toBufferedImage(tile.image), tileWidth, tileHeight);
		}
	}
	
	public void updateSpawnPoints()
	{
		for (int x = 0; x < spawns.size(); x++)
		{
			spawns.get(x).update();
		}
	}
	
	public void renderSpawnPoints(Graphics g)
	{
		for (int x = 0; x < spawns.size(); x++)
		{
			spawns.get(x).render(g);
		}
	}
	
	public void renderWorld(Graphics g, Graphics2D g2d)
	{
		for (int y = 0; y < mapHeight; y++)
		{
			for (int x = 0; x < mapWidth; x++)
			{
				float tilePosX = posX + (x * tileWidth);
				float tilePosY = posY + (((mapHeight - 1) - y) * tileHeight);
				
				if (tilePosX > (ArenaRush.player.getPosX() - ArenaRush.renderDistance) && tilePosX < (ArenaRush.player.getPosX() + ArenaRush.renderDistance) &&
					tilePosY > (ArenaRush.player.getPosY() - ArenaRush.renderDistance) && tilePosY < (ArenaRush.player.getPosY() + ArenaRush.renderDistance))
				{
					Tile tile = TileRegistry.getTile(tiles[y][x]);
					
					if (tiles[y][x] == 0)
					{
						
					}
					else
					{
						if (tile.getImage() != null)
						{
							g.drawImage(tile.getImage(), 
									(int)posX + (x * tileWidth), 
									(int)posY + (((mapHeight - 1) - y) * tileHeight), null);
						}
						else if (tile.getColor() != null)
						{
							g.fillRect((int)posX + (x * tileWidth), 
									(int)posY + (((mapHeight - 1) - y) * tileHeight), tileWidth, tileHeight);
						}
						
						if (ArenaRush.debug)
						{
							if (tile.doesCollide())
							{
								Rectangle rect = tile.getTotalBounds(tilePosX, tilePosY);
								g.setColor(Color.red);
								g.drawRect((int)rect.x, (int)rect.y, rect.width, rect.height);
								
								g.setColor(Color.green);
								
								Rectangle upRect = tile.getCollisionBox(0, tilePosX, tilePosY);
								Rectangle rightRect = tile.getCollisionBox(1, tilePosX, tilePosY);
								Rectangle downRect = tile.getCollisionBox(2, tilePosX, tilePosY);
								Rectangle leftRect = tile.getCollisionBox(3, tilePosX, tilePosY);
								
								g.fillRect(upRect.x, upRect.y, upRect.width, upRect.height);
								g.fillRect(rightRect.x, rightRect.y, rightRect.width, rightRect.height);
								g.fillRect(downRect.x, downRect.y, downRect.width, downRect.height);
								g.fillRect(leftRect.x, leftRect.y, leftRect.width, leftRect.height);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * This method is used to create world files to be used
	 * with the game.  Create a text file in this format: 
	 * (You can look at the base levels for example)
	 * 
	 * 4 (This is the world width, don't include this comment)
	 * 4 (This is the world height, don't include this comment)
	 * (After this line "build" the map using tile id numbers, don't include this comment)
	 * 0 0 0 0
	 * 0 0 1 0
	 * 0 0 1 0
	 * 0 0 0 0
	 * 
	 * Once you create that file, create a world and run this method.
	 * The world will be serialized and saved in the same directory.
	 * 
	 * @param filePath
	 */
	public void generateWorldFile(String filePath, String fileName)
	{
		File file = new File(filePath + fileName);
		
		try
		{
			Scanner fileScan = new Scanner(file);
			
			mapWidth = Integer.parseInt(fileScan.nextLine());
			mapHeight = Integer.parseInt(fileScan.nextLine());
			
			tiles = new int[mapHeight][mapWidth];
			
			int y = mapHeight - 1;
			int x = 0;
			
			while (fileScan.hasNextLine()) // Scan the file line by line
			{
				String line = fileScan.nextLine();
				
				Scanner lineScan = new Scanner(line);
				
				while (lineScan.hasNext()) // Scan the current line token by token
				{
					String token = lineScan.next();
					
					tiles[y][x++] = Integer.parseInt(token);
				}
				
				lineScan.close();
				
				y--;
				x = 0;
			}
			
			fileScan.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		
		try
	    {
	       FileOutputStream fileOut = new FileOutputStream(filePath + name + ".world");
	       ObjectOutputStream out = new ObjectOutputStream(fileOut);
	       out.writeObject(this);
	       out.close();
	       fileOut.close();
	    }
		catch(IOException i)
	    {
	        i.printStackTrace();
	    }
	}
	
	/**
	 * Load a world from file.
	 * @param filePath
	 * @return World
	 */
	public static World loadWorld(String filePath)
	{
		try
	    {
	       FileInputStream fileIn = new FileInputStream(filePath);
	       ObjectInputStream in = new ObjectInputStream(fileIn);
	       
	       World world = (World) in.readObject();
	       
	       in.close();
	       fileIn.close();
	       
	       return world;
	    }
		catch(IOException i)
	    {
	       i.printStackTrace();
	       return null;
	    }
		catch(ClassNotFoundException c)
	    {
	       System.out.println("World class not found");
	       c.printStackTrace();
	       return null;
	    }
	}

	/**
	 * @return the mapWidth
	 */
	public int getMapWidth()
	{
		return mapWidth;
	}

	/**
	 * @param mapWidth the mapWidth to set
	 */
	public void setMapWidth(int mapWidth)
	{
		this.mapWidth = mapWidth;
	}

	/**
	 * @return the mapHeight
	 */
	public int getMapHeight()
	{
		return mapHeight;
	}

	/**
	 * @param mapHeight the mapHeight to set
	 */
	public void setMapHeight(int mapHeight)
	{
		this.mapHeight = mapHeight;
	}

	/**
	 * @return the tiles
	 */
	public int[][] getTiles()
	{
		return tiles;
	}

	/**
	 * @param tiles the tiles to set
	 */
	public void setTiles(int[][] tiles)
	{
		this.tiles = tiles;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the posX
	 */
	public float getPosX()
	{
		return posX;
	}

	/**
	 * @param posX the posX to set
	 */
	public void setPosX(float posX)
	{
		this.posX = posX;
	}

	/**
	 * @return the posY
	 */
	public float getPosY()
	{
		return posY;
	}

	/**
	 * @param posY the posY to set
	 */
	public void setPosY(float posY)
	{
		this.posY = posY;
	}

	/**
	 * @return the tileWidth
	 */
	public int getTileWidth()
	{
		return tileWidth;
	}

	/**
	 * @param tileWidth the tileWidth to set
	 */
	public void setTileWidth(int tileWidth)
	{
		this.tileWidth = tileWidth;
	}

	/**
	 * @return the tileHeight
	 */
	public int getTileHeight()
	{
		return tileHeight;
	}

	/**
	 * @param tileHeight the tileHeight to set
	 */
	public void setTileHeight(int tileHeight)
	{
		this.tileHeight = tileHeight;
	}
}
