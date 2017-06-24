package com.binextechnologies.arenarush.gameobject.entity.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.concurrent.TimeUnit;

import com.binextechnologies.arenarush.gameobject.entity.Entity;
import com.binextechnologies.arenarush.main.ArenaRush;
import com.binextechnologies.arenarush.world.WorldHandler;

public class BigginEnemy extends Enemy
{
	private static final long serialVersionUID = 6567343841347152511L;
	
	public boolean jumping = false;
	public boolean slamming = false;
	public int jumpRange = 200;
	public float hangTime = 0.8F;
	public int jumpRate = 2;
	public int slamRate = 3;
	public int maxScale = 2;
	public float origPosY;
	public int origWidth;
	public int origHeight;
	public int maxWidth;
	public int maxHeight;
	public float lastTargetPosX;
	public float lastTargetPosY;
	public int jumpWait = 5;
	public long startTime;
	public boolean justSlammed = false;
	
	public BigginEnemy(int objectId)
	{
		super(objectId);
		
		this.width = 64;
		this.height = 64;
		this.target = ArenaRush.player;
		this.speed = 2;
		this.health = 100;
		this.damage = 25;
		
		this.boundsStartX = 0;
		this.boundsEndX = width;
		this.boundsStartY = 0;
		this.boundsEndY = height;
		
		this.origWidth = width;
		this.origHeight = height;
		this.maxWidth = width * maxScale;
		this.maxHeight = height * maxScale;
	}
	
	public BigginEnemy(int objectId, float posX, float posY)
	{
		super(objectId);
		
		this.posX = posX;
		this.posY = posY;
		
		this.width = 64;
		this.height = 64;
		this.target = ArenaRush.player;
		this.speed = 2;
		this.health = 100;
		this.damage = 25;
		
		this.boundsStartX = 0;
		this.boundsEndX = width;
		this.boundsStartY = 0;
		this.boundsEndY = height;
		
		this.origWidth = width;
		this.origHeight = height;
		this.maxWidth = width * maxScale;
		this.maxHeight = height * maxScale;
	}
	
	public BigginEnemy(float posX, float posY)
	{
		super(posX, posY);
		
		this.width = 64;
		this.height = 64;
		this.target = ArenaRush.player;
		this.speed = 2;
		this.health = 100;
		this.damage = 25;
		
		this.boundsStartX = 0;
		this.boundsEndX = width;
		this.boundsStartY = 0;
		this.boundsEndY = height;
		
		this.origWidth = width;
		this.origHeight = height;
		this.maxWidth = width * maxScale;
		this.maxHeight = height * maxScale;
	}
	
	@Override
	public void runAI()
	{
		if (target != null && !((Entity)(target)).isDead())
		{
			if (WorldHandler.currentWorld == null)
				return;
			
			long duration = System.nanoTime() - startTime;
			
			if (justSlammed && (TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS) >= (.5)))
			{
				damage = damage / 4;
				justSlammed = false;
			}
			
			if (posX > target.posX - jumpRange && posX < target.posX + jumpRange &&
				posY > target.posY - jumpRange && posY < target.posY + jumpRange &&
				!jumping &&
				(TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS) >= (jumpWait)))
			{
				startTime = 0;
				origPosY = posY;
				lastTargetPosX = target.posX;
				lastTargetPosY = target.posY;
				
				jumping = true;
				speed = speed * 4;
			}
			
			if (jumping)
			{
				jump();
			}
			
			if (!jumping)
			{
				
			}
			else if (jumping)
			{
				float diffX = target.posX - this.posX;
				float diffY = target.posY - this.posY;
			
				float angle = (float)Math.atan2(diffY, diffX);

				if (!isCollidingLeft() || !isCollidingRight())
					velX = (float) (speed * Math.cos(angle));
			
				if (!isCollidingUp() || !isCollidingDown())
					velY = (float) (speed * Math.sin(angle));
			}
		}
	}
	
	public void jump()
	{
		collides = false;
		
		if (!slamming)
		{
			if (width < maxWidth)
				width += jumpRate;
			if (height < maxHeight)
			{
				height += jumpRate;
				posY -= jumpRate;
			}
			
			this.boundsEndX = width;
			this.boundsEndY = height;
			
			if (width >= maxWidth && height >= maxHeight)
			{
				slamming = true;
			}
		}
		else if (slamming)
		{
			if (width > origWidth)
				width -= slamRate;
			if (height > origHeight)
			{
				height -= slamRate;
				posY += slamRate;
			}
			
			if (width <= origWidth && height <= origHeight)
			{
				width = origWidth;
				height = origHeight;
				
				startTime = System.nanoTime();
				
				justSlammed = true;
				damage = damage * 4;
				
				slamming = false;
				jumping = false;
				collides = true;
				speed = speed / 4;
			}
			
			this.boundsEndX = width;
			this.boundsEndY = height;
		}
	}

	@Override
	public void render(Graphics g, Graphics2D g2d)
	{
		AffineTransform old = g2d.getTransform();
		
		g.setColor(new Color(0, 0, 0, 60));
		
		if (jumping)
			g.fillOval((int)posX + width / 2, (int)posY + (height) - (origHeight / (height / origHeight)) / 2, origWidth / (width / origWidth), origHeight / (height / origHeight));
		
		g2d.rotate(Math.toRadians(direction), posX + width / 2, posY + height / 2);
		
		g.setColor(Color.red);
		g.fillOval((int)posX, (int)posY, width, height);
		g.setColor(Color.black);
		g.fillOval((int)posX + width - (width / 4), (int)posY + (height / 2) - ((height / 4) / 2), width / 4, height / 4);
		
		g2d.setTransform(old);
		
		if (ArenaRush.debug)
		{
			renderDebugBoxes(g, g2d);
			renderDebugDirection(g, g2d);
			renderDebugInfo(g, g2d);
		}
	}
	
	/**
	 * Spawn this object in the world.
	 * @param posX
	 * @param posY
	 */
	@Override
	public void spawn(float posX, float posY)
	{
		if (WorldHandler.currentWorld == null) return;
		
		WorldHandler.currentWorld.objects.add(new BigginEnemy(posX, posY));
	}
}
