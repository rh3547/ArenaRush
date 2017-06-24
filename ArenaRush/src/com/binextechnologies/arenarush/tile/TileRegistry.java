package com.binextechnologies.arenarush.tile;

import java.awt.Color;
import java.util.TreeMap;

import javax.swing.ImageIcon;

public class TileRegistry
{
	public static TreeMap<Integer, Tile> tileRegistry = new TreeMap<Integer, Tile>();
	
	public static void registerTiles()
	{
		registerTile(0, new Tile(0, null, null, "Void"));
		registerTile(1, new Tile(1, new Color(0xe1bb85), new ImageIcon("res/tiles/tileSand.png").getImage(), "Sand"));
		registerTile(2, new Tile(2, new Color(0x1e7220), new ImageIcon("res/tiles/tileGrass.png").getImage(), "Grass"));
		registerTile(3, new Tile(3, new Color(0x1e7220), new ImageIcon("res/tiles/tileTechFloor.png").getImage(), "Barricade 1").setCollides(true));
		registerTile(4, new TileSkinnyVert(4, new Color(0x1e7220), new ImageIcon("res/tiles/tileBarricade2.png").getImage(), "Barricade 2").setCollides(true));
		registerTile(5, new TileSmall(5, new Color(0x1e7220), new ImageIcon("res/tiles/tileBarricade3.png").getImage(), "Barricade 3").setCollides(true));
	}
	
	/**
	 * Register a new tile with the tile registry.
	 * @param tile
	 */
	public static void registerTile(int key, Tile tile)
	{
		tileRegistry.put(key, tile);
	}
	
	/**
	 * Get a tile from the tile registry.
	 * @param key
	 * @return
	 */
	public static Tile getTile(int key)
	{
		return tileRegistry.get(key);
	}
	
	/**
	 * Remove the tile with the specified key and
	 * check that it matches the given tile before
	 * removing.  Returns false if the tiles don't match.
	 * @param key
	 * @param tile
	 * @return boolean
	 */
	public static boolean removeTileWithCheck(int key, Tile tile)
	{
		return tileRegistry.remove(key, tile);
	}
	
	/**
	 * Remove the tile with the specified key.
	 * @param key
	 */
	public static void removeTileWithKey(int key)
	{
		tileRegistry.remove(key);
	}
}
