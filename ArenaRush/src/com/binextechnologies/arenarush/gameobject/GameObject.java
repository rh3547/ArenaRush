package com.binextechnologies.arenarush.gameobject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;

import com.binextechnologies.arenarush.main.ArenaRush;
import com.binextechnologies.arenarush.math.Vector2f;
import com.binextechnologies.arenarush.tile.Tile;
import com.binextechnologies.arenarush.world.CollisionHandler;
import com.binextechnologies.arenarush.world.WorldHandler;

public class GameObject implements Serializable
{
	private static final long serialVersionUID = -8860644627903514622L;
	
	/*
	 * Characteristics
	 */
	public int objectId = -1;
	public String name = null;
	public int width = 16; // The width of this object
	public int height = 16; // The height of this object
	
	/*
	 * Positioning
	 */
	public float posX = 0; // The x position of this object
	public float posY = 0; // The y position of this object
	public float velX = 0; // The x velocity of this object
	public float velY = 0; // The y velocity of this object
	
	/*
	 * Collision Bounds
	 */
	public int boundsStartX = 0;
	public int boundsEndX = width;
	public int boundsStartY = 0;
	public int boundsEndY = height;
	
	/*
	 * Flags
	 */
	public boolean collides = true; // Does this object collide with things
	public boolean collidingUp = false;
	public boolean collidingRight = false;
	public boolean collidingDown = false;
	public boolean collidingLeft = false;
	public boolean canPathTo = true;
	
	public GameObject()
	{
		
	}
	
	public GameObject(int objectId)
	{
		this.objectId = objectId;
	}
	
	public GameObject(float posX, float posY)
	{
		this.posX = posX;
		this.posY = posY;
	}
	
	/**
	 * Update the logic of this game object.
	 */
	public void update()
	{
		posX += velX;
		posY += velY;
		
		if (collides)
			checkCollisions();
	}
	
	/**
	 * Called when this object collides with another.
	 * @param obj
	 * @param side
	 */
	public void collidedWithObject(GameObject obj, int side)
	{
		switch (side)
		{
			case 0: // Top
				setPosY(obj.getPosY() + obj.getHeight());
				setVelY(0);
				setCollidingUp(true);
				break;
				
			case 1: // Right
				setPosX(obj.getPosX() - getWidth());
				setVelX(0);
				setCollidingRight(true);
				break;
				
			case 2: // Bottom
				setPosY(obj.getPosY() - getHeight());
				setVelY(0);
				setCollidingDown(true);
				break;
				
			case 3: // Left
				setPosX(obj.getPosX() + obj.getWidth());
				setVelX(0);
				setCollidingLeft(true);
				break;
		}
	}
	
	/**
	 * Called when this object collides with another.
	 * @param tile
	 * @param side
	 */
	public void collidedWithTile(Tile tile, int side, Vector2f tilePos, Rectangle rect)
	{
		switch (side)
		{
			case 0: // Top
				setPosY(tilePos.y + tile.boundsStartY + tile.height);
				setVelY(0);
				setCollidingUp(true);
				break;
				
			case 1: // Right
				setPosX(tilePos.x - getWidth() + (rect.x - tilePos.x));
				setVelX(0);
				setCollidingRight(true);
				break;
				
			case 2: // Bottom
				setPosY(tilePos.y + tile.boundsStartY - getHeight());
				setVelY(0);
				setCollidingDown(true);
				break;
				
			case 3: // Left
				setPosX(tilePos.x + tile.boundsStartX + tile.width);
				setVelX(0);
				setCollidingLeft(true);
				break;
		}
	}
	
	/**
	 * Render this game object.
	 * @param g
	 */
	public void render(Graphics g, Graphics2D g2d)
	{
		g.setColor(Color.pink);
		g.drawRect((int)posX, (int)posY, width, height);
		
		if (ArenaRush.debug)
		{
			renderDebugBoxes(g, g2d);
		}
	}
	
	/**
	 * Render the collision boxes over this object.
	 * @param g
	 */
	public void renderDebugBoxes(Graphics g, Graphics2D g2d)
	{
		Rectangle up = getCollisionBox(0);
		Rectangle right = getCollisionBox(1);
		Rectangle down = getCollisionBox(2);
		Rectangle left = getCollisionBox(3);
		
		g.setColor(Color.green);
		g.fillRect(up.x, up.y, up.width, up.height);
		g.setColor(Color.ORANGE);
		g.fillRect(down.x, down.y, down.width, down.height);
		g.setColor(Color.magenta);
		g.fillRect(left.x, left.y, left.width, left.height);
		g.setColor(Color.red.brighter());
		g.fillRect(right.x, right.y, right.width, right.height);
		
		g.setColor(Color.white);
		g.fillOval((int)posX, (int)posY, width / 6, height / 6);
	}
	
	/**
	 * Check if this game object is colliding with anything.
	 */
	public void checkCollisions()
	{
		CollisionHandler.checkTileCollisions(this);
		CollisionHandler.checkObjectCollisions(this);
	}
	
	/**
	 * Get the collision box for the specified
	 * side of this object.
	 * 0 = up
	 * 1 = right
	 * 2 = down
	 * 3 = left
	 * @param int - side
	 * @return Rectangle
	 */
	public Rectangle getCollisionBox(int side)
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
				return new Rectangle((int)posX + boundsStartX + offsetX, (int)posY + boundsEndY - horizBoxThickness, horizLength - (offsetX * 2), horizBoxThickness); // Down
				
			case 3:
				return new Rectangle((int)posX + boundsStartX, (int)posY + boundsStartY + offsetY, vertBoxThickness, vertLength - (offsetY * 2)); // Left
				
			default:
				return null;
		}
		
	}
	
	/**
	 * Spawn this object in the world.
	 * @param posX
	 * @param posY
	 */
	public void spawn(float posX, float posY)
	{
		if (WorldHandler.currentWorld == null) return;
		
		WorldHandler.currentWorld.objects.add(new GameObject(posX, posY));
	}

	/**
	 * @return the id
	 */
	public int getObjectId()
	{
		return objectId;
	}

	/**
	 * @param id the id to set
	 */
	public void setObjectId(int objectId)
	{
		this.objectId = objectId;
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
	 * @return the posX
	 */
	public float getPosX()
	{
		return posX;
	}

	/**
	 * @param posX the posX to set
	 */
	public GameObject setPosX(float posX)
	{
		this.posX = posX;
		
		return this;
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
	public GameObject setPosY(float posY)
	{
		this.posY = posY;
		
		return this;
	}
	
	/**
	 * @return the velX
	 */
	public float getVelX()
	{
		return velX;
	}

	/**
	 * @param velX the velX to set
	 */
	public GameObject setVelX(float velX)
	{
		this.velX = velX;
		
		return this;
	}

	/**
	 * @return the velY
	 */
	public float getVelY()
	{
		return velY;
	}

	/**
	 * @param velY the velY to set
	 */
	public GameObject setVelY(float velY)
	{
		this.velY = velY;
		
		return this;
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
	public GameObject setCollides(boolean collides)
	{
		this.collides = collides;
		
		return this;
	}
	
	public boolean isColliding()
	{
		return (collidingUp || collidingRight || collidingDown || collidingLeft);	
	}

	/**
	 * @return the collidingUp
	 */
	public boolean isCollidingUp()
	{
		return collidingUp;
	}

	/**
	 * @param collidingUp the collidingUp to set
	 */
	public void setCollidingUp(boolean collidingUp)
	{
		this.collidingUp = collidingUp;
	}

	/**
	 * @return the collidingRight
	 */
	public boolean isCollidingRight()
	{
		return collidingRight;
	}

	/**
	 * @param collidingRight the collidingRight to set
	 */
	public void setCollidingRight(boolean collidingRight)
	{
		this.collidingRight = collidingRight;
	}

	/**
	 * @return the collidingDown
	 */
	public boolean isCollidingDown()
	{
		return collidingDown;
	}

	/**
	 * @param collidingDown the collidingDown to set
	 */
	public void setCollidingDown(boolean collidingDown)
	{
		this.collidingDown = collidingDown;
	}

	/**
	 * @return the collidingLeft
	 */
	public boolean isCollidingLeft()
	{
		return collidingLeft;
	}

	/**
	 * @param collidingLeft the collidingLeft to set
	 */
	public void setCollidingLeft(boolean collidingLeft)
	{
		this.collidingLeft = collidingLeft;
	}

	/**
	 * @return the canPathTo
	 */
	public boolean isCanPathTo()
	{
		return canPathTo;
	}

	/**
	 * @param canPathTo the canPathTo to set
	 */
	public void setCanPathTo(boolean canPathTo)
	{
		this.canPathTo = canPathTo;
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
