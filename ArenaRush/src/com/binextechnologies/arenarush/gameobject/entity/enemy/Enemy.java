package com.binextechnologies.arenarush.gameobject.entity.enemy;

import com.binextechnologies.arenarush.gameobject.GameObject;
import com.binextechnologies.arenarush.gameobject.entity.Entity;
import com.binextechnologies.arenarush.world.WorldHandler;

public class Enemy extends Entity
{
	private static final long serialVersionUID = -576383889247115741L;
	
	public GameObject target = null;
	
	public Enemy(int objectId)
	{
		super(objectId);
		
		this.width = 24;
		this.height = 24;
		this.speed = 4;
		this.health = 25;
		this.damage = 5;
		
		this.boundsStartX = 0;
		this.boundsEndX = width;
		this.boundsStartY = 0;
		this.boundsEndY = height;
	}
	
	public Enemy(int objectId, float posX, float posY)
	{
		super(objectId);
		
		this.posX = posX;
		this.posY = posY;
		this.width = 24;
		this.height = 24;
		this.speed = 4;
		this.health = 25;
		this.damage = 5;
		
		this.boundsStartX = 0;
		this.boundsEndX = width;
		this.boundsStartY = 0;
		this.boundsEndY = height;
	}
	
	public Enemy(float posX, float posY)
	{
		super(posX, posY);
		
		this.width = 24;
		this.height = 24;
		this.speed = 4;
		this.health = 25;
		this.damage = 5;
		
		this.boundsStartX = 0;
		this.boundsEndX = width;
		this.boundsStartY = 0;
		this.boundsEndY = height;
	}
	
	@Override
	public void update()
	{
		super.update();
		
		if (target != null)
		{
			double distanceX = target.getPosX() - posX;
			double distanceY = target.getPosY() - posY;
			
			direction = Math.toDegrees(Math.atan2(distanceY, distanceX));	
		}
		
		runAI();
	}
	
	public void runAI()
	{
		if (target != null)
		{
			if (WorldHandler.currentWorld == null)
				return;
		}
	}
	
	public GameObject getTarget()
	{
		return target;
	}
	
	public void setTarget(GameObject target)
	{
		this.target = target;
	}
}
