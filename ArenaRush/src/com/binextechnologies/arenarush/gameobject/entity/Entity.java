package com.binextechnologies.arenarush.gameobject.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import com.binextechnologies.arenarush.gameobject.GameObject;
import com.binextechnologies.arenarush.main.ArenaRush;
import com.binextechnologies.arenarush.pathing.AStarNode;
import com.binextechnologies.arenarush.world.WorldHandler;

public class Entity extends GameObject
{
	private static final long serialVersionUID = -9014903354023079826L;

	public double direction = 0; // The direction this object is facing (as an angle in degrees)
	
	public float speed = 4; // The movement speed of this object
	public double health = 100; // The health of this object
	public double damage = 0; // The attack damage of this object
	
	public boolean dead = false; // Is this object dead
	
	public Entity()
	{
		super();
	}
	
	public Entity(int objectId)
	{
		super(objectId);
	}
	
	public Entity(float posX, float posY)
	{
		super(posX, posY);
	}
	
	/**
	 * Update the logic of this game object.
	 */
	public void update()
	{
		super.update();
		
		if (health <= 0)
			kill();
	}
	
	/**
	 * Render this game object.
	 * @param g
	 */
	public void render(Graphics g, Graphics2D g2d)
	{
		AffineTransform old = g2d.getTransform();
		
		g2d.rotate(Math.toRadians(direction), posX + width / 2, posY + height / 2);
		
		g.setColor(Color.pink);
		g.drawRect((int)posX, (int)posY, width, height);
		
		g2d.setTransform(old);
		
		if (ArenaRush.debug)
		{
			renderDebugBoxes(g, g2d);
			renderDebugDirection(g, g2d);
			renderDebugInfo(g, g2d);
		}
	}
	
	public void renderDebugDirection(Graphics g, Graphics2D g2d)
	{
		AffineTransform old = g2d.getTransform();
		
		old = g2d.getTransform();
		g2d.rotate(Math.toRadians(direction + 90), posX + width / 2, posY + height / 2);
		
		g.setColor(Color.red);
		g.drawLine((int)posX + (width / 2), (int)posY, (int)posX + (width / 2), (int)posY - (height));
		
		g2d.setTransform(old);
	}
	
	public void renderDebugInfo(Graphics g, Graphics2D g2d)
	{
		g.setColor(Color.black);
		g.drawString(String.valueOf(health), (int)posX + ((width / 2) - (g.getFontMetrics().stringWidth(String.valueOf(health))) / 2), 
				(int)posY + (height + (g.getFontMetrics().getHeight() / 2)) + 10);
	}
	
	public void renderPath(Graphics g, ArrayList<AStarNode> path)
	{
		if (path != null)
		{
			if (path.size() > 1)
			{
			
				for (int x = path.size() - 1; x >= 0; x--)
				{
					g.setColor(Color.red);
					g.drawRect((int)path.get(x).pos.x, (int)path.get(x).pos.y, 
							WorldHandler.currentWorld.tileWidth, WorldHandler.currentWorld.tileHeight);
					
					if (x > 0)
					{
						int lineStartX = (int) path.get(x).pos.x + (WorldHandler.currentWorld.tileWidth / 2);
						int lineStartY = (int) path.get(x).pos.y + (WorldHandler.currentWorld.tileWidth / 2);
						int lineEndX = (int) path.get(x - 1).pos.x + (WorldHandler.currentWorld.tileWidth / 2);
						int lineEndY = (int) path.get(x - 1).pos.y + (WorldHandler.currentWorld.tileWidth / 2);
					
						g.setColor(Color.black);
						g.drawLine(lineStartX, lineStartY, lineEndX, lineEndY);
					}
				}
				
				g.setColor(Color.red);
				g.fillRect((int)path.get(path.size() - 1).pos.x, (int)path.get(path.size() - 1).pos.y, 
						WorldHandler.currentWorld.tileWidth, WorldHandler.currentWorld.tileHeight);
				
				g.setColor(Color.green);
				g.drawRect((int)path.get(0).pos.x, (int)path.get(0).pos.y, 
						WorldHandler.currentWorld.tileWidth, WorldHandler.currentWorld.tileHeight);
			}
		}
	}
	
	/**
	 * Damage this objects health by the specified amount.
	 * @param damage
	 */
	public void damage(double damage)
	{
		this.health -= damage;
	}
	
	/**
	 * Kill this object and remove it from the game.
	 */
	public void kill()
	{
		dead = true;
	}

	/**
	 * @return the direction
	 */
	public double getDirection()
	{
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public Entity setDirection(double direction)
	{
		this.direction = direction;
		
		return this;
	}
	
	/**
	 * @return the speed
	 */
	public float getSpeed()
	{
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public Entity setSpeed(float speed)
	{
		this.speed = speed;
		
		return this;
	}

	/**
	 * @return the dead
	 */
	public boolean isDead()
	{
		return dead;
	}

	/**
	 * @param dead the dead to set
	 */
	public void setDead(boolean dead)
	{
		this.dead = dead;
	}

	/**
	 * @return the health
	 */
	public double getHealth()
	{
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public Entity setHealth(double health)
	{
		this.health = health;
		
		return this;
	}

	/**
	 * @return the damage
	 */
	public double getDamage()
	{
		return damage;
	}

	/**
	 * @param damage the damage to set
	 */
	public Entity setDamage(double damage)
	{
		this.damage = damage;
		
		return this;
	}
}
