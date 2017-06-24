package com.binextechnologies.arenarush.projectiles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.binextechnologies.arenarush.main.ArenaRush;

public class BasicBullet extends Projectile
{
	private static final long serialVersionUID = -4931817258742138157L;

	public BasicBullet(int projectileId)
	{
		super(projectileId);
		
		this.name = "Basic Bullet";
		this.width = 8;
		this.height = 6;
		this.damage = 8;
		this.maxLife = -1;
		this.collides = true;
		this.speed = 6;
		this.boundsStartX = 0;
		this.boundsEndX = width;
		this.boundsStartY = 0;
		this.boundsEndY = height - boundsStartY;
	}
	
	public BasicBullet(Projectile proj)
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
}
