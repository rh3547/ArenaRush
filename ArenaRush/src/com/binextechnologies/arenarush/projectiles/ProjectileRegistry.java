package com.binextechnologies.arenarush.projectiles;

import java.awt.Image;
import java.util.TreeMap;

import javax.swing.ImageIcon;

public class ProjectileRegistry
{
	public static TreeMap<Integer, Projectile> projectileRegistry = new TreeMap<Integer, Projectile>();
	public static TreeMap<Integer, ProjectileImages> projectileImages = new TreeMap<Integer, ProjectileImages>();
	
	public static void registerProjectiles()
	{
		registerProjectile(new BasicBullet(0), new ImageIcon("res/projectiles/basicBullet.png").getImage(), null);
		registerProjectile(new RubberBullet(1), new ImageIcon("res/projectiles/rubberBullet.png").getImage(), null);
		registerProjectile(new TriShotBasic(2), new ImageIcon("res/projectiles/basicBullet.png").getImage(), new ImageIcon("res/projectiles/triShotGui.png").getImage());
	}
	
	public static void registerProjectile(Projectile proj, Image image, Image guiImage)
	{
		projectileRegistry.put(proj.projectileId, proj);
		projectileImages.put(proj.projectileId, new ProjectileImages(image, guiImage));
	}
	
	public static Projectile getProjectile(int key)
	{
		return projectileRegistry.get(key);
	}
	
	public static ProjectileImages getProjectileImages(int key)
	{
		return projectileImages.get(key);
	}
}
