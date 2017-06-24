package com.binextechnologies.arenarush.tile;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

public class Tile
{
	public int tileId;
	public int width = 32;
	public int height = 32;
	public Color color;
	public Image image;
	public String tileName;
	
	public boolean collides = false;
	
	public int boundsStartX = 0;
	public int boundsEndX = width;
	public int boundsStartY = 0;
	public int boundsEndY = height;
	
	public Tile(int tileId, Color color, Image image, String tileName)
	{
		this.tileId = tileId;
		this.color = color;
		this.image = image;
		this.tileName = tileName;
		
		this.boundsStartX = 0;
		this.boundsEndX = width;
		this.boundsStartY = 0;
		this.boundsEndY = height - boundsStartY;
	}
	
	public Rectangle getCollisionBox(int side, float posX, float posY)
	{
		int horizLength = boundsEndX;
		int vertLength = boundsEndY;
		int horizBoxThickness = height / 3;
		int vertBoxThickness = width / 3;
		int offsetX = vertBoxThickness / 2;
		int offsetY = horizBoxThickness / 2;
		
		switch (side)
		{
			case 0:
				return new Rectangle((int)posX + boundsStartX + offsetX, (int)posY + boundsStartY, horizLength - (offsetX * 2), horizBoxThickness); // Up
				
			case 1:
				return new Rectangle((int)posX + boundsStartX + boundsEndX - vertBoxThickness, (int)posY + boundsStartY + offsetY, vertBoxThickness, vertLength - (offsetY * 2)); // Right
				
			case 2:
				return new Rectangle((int)posX + boundsStartX + offsetX, (int)posY + boundsStartY + boundsEndY - horizBoxThickness, horizLength - (offsetX * 2), horizBoxThickness); // Down
				
			case 3:
				return new Rectangle((int)posX + boundsStartX, (int)posY + boundsStartY + offsetY, vertBoxThickness, vertLength - (offsetY * 2)); // Left
				
			default:
				return null;
		}
	}
	
	public Rectangle getTotalBounds(float posX, float posY)
	{
		return new Rectangle((int)posX + boundsStartX, (int)posY + boundsStartY, width, height);
	}
	
	/**
	 * @return the tileId
	 */
	public int getTileId()
	{
		return tileId;
	}
	/**
	 * @param tileId the tileId to set
	 */
	public void setTileId(int tileId)
	{
		this.tileId = tileId;
	}
	/**
	 * @return the color
	 */
	public Color getColor()
	{
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}
	/**
	 * @return the image
	 */
	public Image getImage()
	{
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(Image image)
	{
		this.image = image;
	}
	/**
	 * @return the tileName
	 */
	public String getTileName()
	{
		return tileName;
	}
	/**
	 * @param tileName the tileName to set
	 */
	public void setTileName(String tileName)
	{
		this.tileName = tileName;
	}

	/**
	 * @return the collides
	 */
	public boolean doesCollide()
	{
		return collides;
	}

	/**
	 * @param collides the collides to set
	 */
	public Tile setCollides(boolean collides)
	{
		this.collides = collides;
		
		return this;
	}

	/**
	 * @return the width
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}

	/**
	 * @return the boundsStartX
	 */
	public int getBoundsStartX()
	{
		return boundsStartX;
	}

	/**
	 * @param boundsStartX the boundsStartX to set
	 */
	public void setBoundsStartX(int boundsStartX)
	{
		this.boundsStartX = boundsStartX;
	}

	/**
	 * @return the boundsEndX
	 */
	public int getBoundsEndX()
	{
		return boundsEndX;
	}

	/**
	 * @param boundsEndX the boundsEndX to set
	 */
	public void setBoundsEndX(int boundsEndX)
	{
		this.boundsEndX = boundsEndX;
	}

	/**
	 * @return the boundsStartY
	 */
	public int getBoundsStartY()
	{
		return boundsStartY;
	}

	/**
	 * @param boundsStartY the boundsStartY to set
	 */
	public void setBoundsStartY(int boundsStartY)
	{
		this.boundsStartY = boundsStartY;
	}

	/**
	 * @return the boundsEndY
	 */
	public int getBoundsEndY()
	{
		return boundsEndY;
	}

	/**
	 * @param boundsEndY the boundsEndY to set
	 */
	public void setBoundsEndY(int boundsEndY)
	{
		this.boundsEndY = boundsEndY;
	}
}
