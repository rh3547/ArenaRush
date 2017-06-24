package com.binextechnologies.arenarush.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.binextechnologies.arenarush.gameobject.GameObject;
import com.binextechnologies.arenarush.gameobject.entity.Entity;
import com.binextechnologies.arenarush.gameobject.entity.enemy.BigginEnemy;
import com.binextechnologies.arenarush.main.ArenaRush;
import com.binextechnologies.arenarush.world.WorldHandler;

public class Player extends Entity
{
	private static final long serialVersionUID = 789181307772441113L;
	
	public int weapon;
	public float boostModifier;
	
	public Player()
	{
		super();
		
		this.width = 32;
		this.height = 32;
		this.speed = 6;
		this.boostModifier = 2.0F;
		this.health = 100;
		this.damage = 10;
		this.collides = true;
		this.weapon = 0;
		
		this.boundsStartX = 0;
		this.boundsEndX = width;
		this.boundsStartY = 0;
		this.boundsEndY = height;
	}
	
	@Override
	public void update()
	{
		super.update();
		
		if (velX > speed)
			velX -= .5;
		else if (velX < -speed)
			velX += .5;
		if (velY > speed)
			velY -= .5;
		else if (velY < -speed)
			velY += .5;
		
		try
		{
			double distanceX = ArenaRush.core.worldMouseX - posX;
			double distanceY = ArenaRush.core.worldMouseY - posY;
			
			direction = Math.toDegrees(Math.atan2(distanceY, distanceX));
		}
		catch (NullPointerException e)
		{
			
		}
		
		try
		{
			/*
			 * Top and bottom bounds
			 */
			if (posY <= 10)
			{
				posY = 10;
				velY = 0;
			}
			if (posY >= (WorldHandler.currentWorld.mapHeight - 3) * WorldHandler.currentWorld.tileHeight)
			{
				posY = (WorldHandler.currentWorld.mapHeight - 3) * WorldHandler.currentWorld.tileHeight;
				velY = 0;
			}
			
			/*
			 * Left and right bounds
			 */
			if (posX <= 0)
			{
				posX = 0;
				velX = 0;
			}
			if (posX >= (WorldHandler.currentWorld.mapWidth - 1) * WorldHandler.currentWorld.tileWidth)
			{
				posX = (WorldHandler.currentWorld.mapWidth - 1) * WorldHandler.currentWorld.tileWidth;
				velX = 0;
			}
		}
		catch (NullPointerException e)
		{
			
		}
	}
	
	@Override
	public void collidedWithObject(GameObject obj, int side)
	{
		super.collidedWithObject(obj, side);
		
		if (obj instanceof BigginEnemy)
		{
			BigginEnemy en = (BigginEnemy)obj;
			
			this.damage(en.damage);
		}
	}

	@Override
	public void render(Graphics g, Graphics2D g2d)
	{    
		AffineTransform old = g2d.getTransform();
		
		g2d.rotate(Math.toRadians(direction), posX + (width / 2), posY + (height / 2));
		
		g.setColor(Color.blue);
		g.fillRect((int)posX, (int)posY, width, height);
		g.setColor(Color.cyan);
		g.fillRect((int)posX + width - (width / 4), (int)posY, (width / 4), height);
		
		//g.drawImage(ArenaRush.playerImage, (int)posX, (int)posY, null);
		
		g2d.setTransform(old);
		
		if (ArenaRush.debug)
		{
			renderDebugBoxes(g, g2d);
			renderDebugDirection(g, g2d);
			renderDebugInfo(g, g2d);
		}
	}
	
	@Override
	public void kill()
	{
		this.dead = true;
	}

	/**
	 * @return the weapon
	 */
	public int getWeapon()
	{
		return weapon;
	}

	/**
	 * @param weapon the weapon to set
	 */
	public void setWeapon(int weapon)
	{
		this.weapon = weapon;
	}

	/**
	 * @return the boostModifier
	 */
	public float getBoostModifier()
	{
		return boostModifier;
	}

	/**
	 * @param boostModifier the boostModifier to set
	 */
	public void setBoostModifier(float boostModifier)
	{
		this.boostModifier = boostModifier;
	}
}
