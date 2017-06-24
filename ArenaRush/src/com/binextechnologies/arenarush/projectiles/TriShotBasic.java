package com.binextechnologies.arenarush.projectiles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.binextechnologies.arenarush.main.ArenaRush;

public class TriShotBasic extends Projectile
{
	private static final long serialVersionUID = 967103321256108425L;

	public TriShotBasic(int projectileId)
	{
		super(projectileId);
		
		this.name = "Tri-Shot";
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
	
	public TriShotBasic(Projectile proj)
	{
		super(proj);
	}
	
	@Override
	public void fire(float posX, float posY, double direction, float velX, float velY)
	{
		this.setPosX(posX);
		this.setPosY(posY); 
		this.setDirection(direction);
		this.setVelX(velX);
		this.setVelY(velY);
		
		BasicBullet shotStraight = new BasicBullet(this);
		shotStraight.fire(posX, posY, direction, 
		velX, 
		velY);
		
		BasicBullet shotRight = new BasicBullet(this);
		shotRight.fire(posX, posY, direction + 20, 
		(float) (shotRight.getSpeed() * Math.cos(Math.toRadians(direction + 20))), 
		(float) (shotRight.getSpeed() * Math.sin(Math.toRadians(direction + 20))));
		
		BasicBullet shotLeft = new BasicBullet(this);
		shotLeft.fire(posX, posY, direction - 20, 
		(float) (shotLeft.getSpeed() * Math.cos(Math.toRadians(direction - 20))), 
		(float) (shotLeft.getSpeed() * Math.sin(Math.toRadians(direction - 20))));
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
