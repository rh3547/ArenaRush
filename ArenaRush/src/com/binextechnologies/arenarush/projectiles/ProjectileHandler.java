package com.binextechnologies.arenarush.projectiles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class ProjectileHandler
{
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	public static void updateProjectiles()
	{
		for (int x = 0; x < projectiles.size(); x++)
		{
			if (projectiles.get(x).isDead())
			{
				projectiles.remove(x);
				continue;
			}
			
			if (projectiles.size() == 0)
				return;
			
			projectiles.get(x).update();
		}
	}
	
	public static void renderProjectiles(Graphics g, Graphics2D g2d)
	{
		for (int x = 0; x < projectiles.size(); x++)
		{
			projectiles.get(x).render(g, g2d);
		}
	}
}
