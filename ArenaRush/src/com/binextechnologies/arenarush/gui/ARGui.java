package com.binextechnologies.arenarush.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import com.binextechnologies.arenarush.main.ARToolkit;
import com.binextechnologies.arenarush.main.ArenaRush;
import com.binextechnologies.arenarush.projectiles.ProjectileRegistry;

public class ARGui
{
	public static boolean drawGui = false;
	public static Image weaponImage = null;
	
	public static void updateGui()
	{
		
	}
	
	public static void updateWeaponImage()
	{
		weaponImage = ARToolkit.resizeImage(ARToolkit.toBufferedImage(ProjectileRegistry.getProjectileImages(ArenaRush.player.weapon).getGuiImage()), 48, 48);
	}
	
	public static void renderGui(Graphics g, Graphics2D g2d)
	{
		if (drawGui)
		{
			g.drawImage(new ImageIcon("res/gui/weaponSlot.png").getImage(), 20, ArenaRush.core.frame.getHeight() - 150, null);
			
			if (weaponImage != null)
			{
				g.drawImage(weaponImage, 20 + 26, ArenaRush.core.frame.getHeight() - 150 + 26, null);
				g.setColor(Color.white);
				g.setFont(new Font("Verdana", Font.BOLD, 18));
				g.drawString(ProjectileRegistry.getProjectile(ArenaRush.player.weapon).name, 20 + 14, ArenaRush.core.frame.getHeight() - 40);
			}
		}
	}
}
