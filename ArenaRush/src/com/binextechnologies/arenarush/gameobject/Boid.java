package com.binextechnologies.arenarush.gameobject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.binextechnologies.arenarush.gameobject.entity.Entity;
import com.binextechnologies.arenarush.math.Vector2f;
import com.binextechnologies.arenarush.world.WorldHandler;

public class Boid extends Entity
{
	private static final long serialVersionUID = -2973516729254841030L;

	public Boid(float posX, float posY)
	{
		this.posX = posX;
		this.posY = posY;
		
		this.width = 16;
		this.height = 16;
		this.speed = 4;
		this.health = 25;
		this.damage = 5;
	}
	
	@Override
	public void update()
	{
		super.update();
		
        if (posX > WorldHandler.currentWorld.mapWidth) 
        {
        	posX = WorldHandler.currentWorld.mapWidth - width;
            velX = -velX;
        }
        if (posX < 0) 
        {
            posX = 0;
            velX = -velX;
        }
        if (posY > WorldHandler.currentWorld.mapHeight) 
        {
            posY = WorldHandler.currentWorld.mapHeight - height;
            velY = -velY;
        }
        if (posY < 0) 
        {
            posY = 0;
            velY = -velY;
        }
	}
	
	@Override
	public void render(Graphics g, Graphics2D g2d)
	{
		g.setColor(Color.darkGray);
		g.fillRect((int)posX, (int)posY, width, height);
	}
	
    public boolean isInFlock(final Vector2f pos, int radius) 
    {
        Vector2f range = new Vector2f(new Vector2f(posX, posY));
        range.subtract(pos);
        return range.length() < radius;
    }

    public boolean isClose(final Vector2f pos, int radiusLimit) 
    {
        Vector2f range = new Vector2f(new Vector2f(posX, posY));
        range.subtract(pos);
        return range.length() < radiusLimit;
    }
}
