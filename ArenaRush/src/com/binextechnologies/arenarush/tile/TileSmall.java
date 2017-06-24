package com.binextechnologies.arenarush.tile;

import java.awt.Color;
import java.awt.Image;

public class TileSmall extends Tile
{
	public TileSmall(int tileId, Color color, Image image, String tileName)
	{
		super(tileId, color, image, tileName);
		
		this.width = 20;
		this.height = 20;
		this.boundsStartX = 7;
		this.boundsEndX = width;
		this.boundsStartY = 7;
		this.boundsEndY = height;
	}
}
