package com.binextechnologies.arenarush.projectiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import com.binextechnologies.arenarush.gameobject.GameObject;
import com.binextechnologies.arenarush.gameobject.entity.Entity;
import com.binextechnologies.arenarush.main.ArenaRush;
import com.binextechnologies.arenarush.math.Vector2f;
import com.binextechnologies.arenarush.tile.Tile;
import com.binextechnologies.arenarush.world.CollisionHandler;
import com.binextechnologies.arenarush.world.WorldHandler;

public class Projectile extends Entity
{
	private static final long serialVersionUID = 7404769729875511533L;
	
	public int projectileId;
	public int maxLife;
	
	public Projectile(int projectileId)
	{
		super();
		
		this.projectileId = projectileId;
	}
	
	public Projectile(int projectileId, float damage, int maxLife)
	{ 	
		super();
		
		this.projectileId = projectileId;
		this.maxLife = maxLife;
		this.damage = damage;
	}
	
	public Projectile(Projectile proj)
	{
		super();
		
		this.projectileId = proj.projectileId;
		this.posX = proj.posX; 
		this.posY = proj.posY; 
		this.velX = proj.velX;
		this.velY = proj.velY;
		this.direction = proj.direction;
		this.name = proj.name;
		this.width = proj.width;
		this.height = proj.height;
		this.speed = proj.speed;
		this.damage = proj.damage;
		this.maxLife = proj.maxLife;
		this.collides = proj.collides;
		this.boundsStartX = proj.boundsStartX;
		this.boundsEndX = proj.boundsEndX;
		this.boundsStartY = proj.boundsStartY;
		this.boundsEndY = proj.boundsEndY;
	}
	
	@Override
	public void update()
	{	
		super.update();
		
		if (maxLife != -1)
			maxLife--;
		
		if (maxLife != -1 && maxLife <= 0)
			kill();
		
		if (WorldHandler.currentWorld == null)
			return;
		
		if (posX < -16)
			kill();
		if (posX > (WorldHandler.currentWorld.mapWidth * WorldHandler.currentWorld.tileWidth) + 16)
			kill();
		if (posY < -16)
			kill();
		if (posY > (WorldHandler.currentWorld.mapHeight * WorldHandler.currentWorld.tileHeight) + 16)
			kill();
	}
	
	@Override
	public void checkCollisions()
	{
		CollisionHandler.checkProjectileObjectCollisions(this);
		CollisionHandler.checkProjectileTileCollisions(this);
	}
	
	@Override
	public void render(Graphics g, Graphics2D g2d)
	{
		AffineTransform old = g2d.getTransform();
		
		g2d.rotate(Math.toRadians(direction), posX + width / 2, posY + height / 2);
		
		g.setColor(Color.black);
		g.fillOval((int)posX, (int)posY, width, height);
		
		g2d.setTransform(old);
		
		if (ArenaRush.debug)
		{
			renderDebugBoxes(g, g2d);
			renderDebugDirection(g, g2d);
			renderDebugInfo(g, g2d);
		}
	}
	
	public Rectangle getTotalBounds()
	{
		return new Rectangle((int)posX, (int)posY, width, height);
	}
	
	public void fire(float posX, float posY, double direction, float velX, float velY)
	{
		this.setPosX(posX);
		this.setPosY(posY); 
		this.setDirection(direction);
		this.setVelX(velX);
		this.setVelY(velY);
		
		ProjectileHandler.projectiles.add(this);
	}
	
	public void projectileHitEntity(Entity obj)
	{
		obj.damage(damage);
		kill();
	}
	
	public void projectileTileCollide(Tile tile, int sideCollided, Vector2f tilePos, Rectangle rect)
	{
		switch (sideCollided)
		{
			case 0: // Top
				setDirection(180 - direction);
				setPosY(tilePos.y - getHeight() + (tilePos.y - rect.y));
				setVelY(0);
				setCollidingDown(true);
				break;
				
			case 1: // Right
				setDirection(180 - direction);
				setPosX(tilePos.x + WorldHandler.currentWorld.tileWidth - (tilePos.x + WorldHandler.currentWorld.tileWidth - (rect.x + rect.width)));
				setVelX(0);
				setCollidingLeft(true);
				break;
				
			case 2: // Bottom
				setDirection(180 - direction);
				setPosY(tilePos.y + WorldHandler.currentWorld.tileHeight - ((tilePos.y + WorldHandler.currentWorld.tileHeight) - (rect.y + rect.height)));
				setVelY(0);
				setCollidingUp(true);
				break;
				
			case 3: // Left
				setDirection(180 - direction);
				setPosX(tilePos.x - getWidth() + (rect.x - tilePos.x));
				setVelX(0);
				setCollidingRight(true);
				break;
		}
		
		direction = Math.toDegrees(Math.atan2(velY, velX));
		
		kill();
	}
	
	public void projectileObjectCollide(GameObject obj, int sideCollided)
	{
		switch (sideCollided)
		{
			case 0: // Top
				setPosY(obj.getPosY() + obj.getHeight());
				setVelY(0);
				setCollidingUp(true);
				if (obj instanceof Entity)
					projectileHitEntity((Entity)obj);
				break;
				
			case 1: // Right
				setPosX(obj.getPosX() - obj.getWidth());
				setVelX(0);
				setCollidingRight(true);
				if (obj instanceof Entity)
					projectileHitEntity((Entity)obj);
				break;
				
			case 2: // Bottom
				setPosY(obj.getPosY() - getHeight());
				setVelY(0);
				setCollidingDown(true);
				if (obj instanceof Entity)
					projectileHitEntity((Entity)obj);
				break;
				
			case 3: // Left
				setPosX(obj.getPosX() + obj.getWidth());
				setVelX(0);
				setCollidingLeft(true);
				if (obj instanceof Entity)
					projectileHitEntity((Entity)obj);
				break;
		}
	}
	
	@Override
	public void kill()
	{
		dead = true;
	}
}
