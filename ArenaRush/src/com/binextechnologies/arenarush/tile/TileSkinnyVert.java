package com.binextechnologies.arenarush.tile;

import java.awt.Color;
import java.awt.Image;

public class TileSkinnyVert extends Tile
{
	public TileSkinnyVert(int tileId, Color color, Image image, String tileName)
	{
		super(tileId, color, image, tileName);
		
		this.width = 20;
		this.height = 32;
		this.boundsStartX = 7;
		this.boundsEndX = width;
		this.boundsStartY = 0;
		this.boundsEndY = height;
	}
}
