package com.binextechnologies.arenarush.projectiles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import com.binextechnologies.arenarush.main.ArenaRush;
import com.binextechnologies.arenarush.math.Vector2f;
import com.binextechnologies.arenarush.tile.Tile;
import com.binextechnologies.arenarush.world.WorldHandler;

public class RubberBullet extends Projectile
{
	private static final long serialVersionUID = -3117386172226819540L;
	
	public int numBounces = 0;
	
	public RubberBullet(int projectileId)
	{
		super(projectileId);
		
		this.name = "Rubber Bullet";
		this.width = 8;
		this.height = 8;
		this.damage = 10;
		this.maxLife = -1;
		this.collides = true;
		this.speed = 6;
		this.boundsStartX = 0;
		this.boundsEndX = width;
		this.boundsStartY = 0;
		this.boundsEndY = height - boundsStartY;
	}
	
	public RubberBullet(Projectile proj)
	{
		super(proj);
	}
	
	@Override
	public void render(Graphics g, Graphics2D g2d)
	{
		AffineTransform old = g2d.getTransform();
		
		g2d.rotate(Math.toRadians(direction), posX + width / 2, posY + height / 2);
		
		g.drawImage(ProjectileRegistry.getProjectileImages(projectileId).getProjectileImage(), (int)posX, (int)posY, null);
		
		g2d.setTransform(old);
		
		if (ArenaRush.debug)
		{
			renderDebugBoxes(g, g2d);
			renderDebugDirection(g, g2d);
			renderDebugInfo(g, g2d);
		}
	}
	
	@Override
	public void projectileTileCollide(Tile tile, int sideCollided, Vector2f tilePos, Rectangle rect)
	{
		if (numBounces > 3)
		{
			kill();
			return;
		}
		
		numBounces++;
		
		switch (sideCollided)
		{
			case 0: // Top
				setPosY(tilePos.y + tile.boundsStartY - getHeight());
				setCollidingDown(true);
				velY = -velY;
				break;
				
			case 1: // Right
				setPosX(tilePos.x + WorldHandler.currentWorld.tileWidth - (tilePos.x + WorldHandler.currentWorld.tileWidth - (rect.x + rect.width)));
				setCollidingLeft(true);
				velX = -velX;
				break;
				
			case 2: // Bottom
				setPosY(tilePos.y + tile.boundsStartY + tile.height);
				setCollidingUp(true);
				velY = -velY;
				break;
				
			case 3: // Left
				setPosX(tilePos.x - getWidth() + (rect.x - tilePos.x));
				setCollidingRight(true);
				velX = -velX;
				break;
		}
		
		direction = Math.toDegrees(Math.atan2(velY, velX));
	}
}
