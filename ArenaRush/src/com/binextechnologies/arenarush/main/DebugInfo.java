package com.binextechnologies.arenarush.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.binextechnologies.arenarush.projectiles.ProjectileHandler;
import com.binextechnologies.arenarush.world.WorldHandler;

public class DebugInfo
{
	public static int posX = 10;
	public static int posY = 20;
	
	public static boolean drawInfo = true;
	
	public static void drawDebugInfo(Graphics g, Graphics2D g2d)
	{
		if (drawInfo)
		{
			g.setColor(new Color(255, 255, 255, 180));
			g.fillRect(5, 5, 160, 145);
			
			g.setFont(new Font("Verdana", Font.BOLD, 16));
			
			g.setColor(Color.black);
			g.drawString("TPS: ", posX, posY);
			g.setColor(Color.blue);
			g.drawString(String.valueOf(ARClock.topTicks), posX + 40, posY);
			g.setColor(Color.black);
			g.drawString("FPS: ", posX, posY + 20);
			g.setColor(Color.blue);
			g.drawString(String.valueOf(ARClock.topFrames), posX + 40, posY + 20);
			
			g.setColor(Color.black);
			g.drawString("Camera X: ", posX, posY + 50);
			g.setColor(Color.blue);
			g.drawString(String.valueOf(ArenaRush.camera.getPosX()), posX + 100, posY + 50);
			g.setColor(Color.black);
			g.drawString("Camera Y: ", posX, posY + 70);
			g.setColor(Color.blue);
			g.drawString(String.valueOf(ArenaRush.camera.getPosY()), posX + 100, posY + 70);
			
			if (WorldHandler.currentWorld != null)
			{
				g.setColor(Color.black);
				g.drawString("Game Objects: ", posX, posY + 100);
				g.setColor(Color.blue);
				g.drawString(String.valueOf(WorldHandler.currentWorld.objects.size()), posX + 140, posY + 100);
			}
			
			g.setColor(Color.black);
			g.drawString("Projectiles: ", posX, posY + 120);
			g.setColor(Color.blue);
			g.drawString(String.valueOf(ProjectileHandler.projectiles.size()), posX + 140, posY + 120);
		}
	}
}
