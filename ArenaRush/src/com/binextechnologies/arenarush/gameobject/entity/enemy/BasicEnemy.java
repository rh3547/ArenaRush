package com.binextechnologies.arenarush.gameobject.entity.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import com.binextechnologies.arenarush.ai.AIAStarFollow;
import com.binextechnologies.arenarush.ai.AIWander;
import com.binextechnologies.arenarush.main.ArenaRush;
import com.binextechnologies.arenarush.pathing.AStarNode;
import com.binextechnologies.arenarush.world.WorldHandler;

public class BasicEnemy extends Enemy
{
	private static final long serialVersionUID = -3840613924346313609L;
	
	public transient AIAStarFollow aiAStarFollow;
	public transient AIWander aiWander = new AIWander(this, 400, 5);
	
	public transient ArrayList<AStarNode> pathToDraw = null;
	
	public BasicEnemy(int objectId)
	{
		super(objectId);
		
		this.width = 24;
		this.height = 24;
		this.target = ArenaRush.player;
		this.speed = 4;
		this.health = 25;
		this.damage = 5;
		
		this.boundsStartX = 0;
		this.boundsEndX = width;
		this.boundsStartY = 0;
		this.boundsEndY = height;
		
		this.aiAStarFollow = new AIAStarFollow(this, target);
	}
	
	public BasicEnemy(float posX, float posY)
	{
		super(posX, posY);
		
		this.width = 24;
		this.height = 24;
		this.target = ArenaRush.player;
		this.speed = 4;
		this.health = 25;
		this.damage = 5;
		
		this.boundsStartX = 0;
		this.boundsEndX = width;
		this.boundsStartY = 0;
		this.boundsEndY = height;
		
		this.aiAStarFollow = new AIAStarFollow(this, target);
	}
	
	@Override
	public void runAI()
	{
		if (target != null)
		{
			if (WorldHandler.currentWorld == null)
				return;
				
			if (aiAStarFollow == null)
				this.aiAStarFollow = new AIAStarFollow(this, target);
			if (aiWander == null)
				this.aiWander = new AIWander(this, 400, 5);
			
			if (aiAStarFollow.runScript())
				pathToDraw = aiAStarFollow.path;
			else
				if(aiWander.runScript())
					pathToDraw = aiWander.aStarWaypoint.path;
		}
	}

	@Override
	public void render(Graphics g, Graphics2D g2d)
	{
		AffineTransform old = g2d.getTransform();
		
		g2d.rotate(Math.toRadians(direction), posX + width / 2, posY + height / 2);
		
		g.setColor(Color.red);
		g.fillOval((int)posX, (int)posY, width, height);
		g.setColor(Color.black);
		g.fillOval((int)posX + width - (width / 4), (int)posY + (height / 2) - ((height / 4) / 2), width / 4, height / 4);
		
		g2d.setTransform(old);
		
		if (ArenaRush.debug)
		{
			renderPath(g, pathToDraw);
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
		
		WorldHandler.currentWorld.objects.add(new BasicEnemy(posX, posY));
	}
}
