package com.binextechnologies.arenarush.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import com.binextechnologies.arenarush.Testing;
import com.binextechnologies.arenarush.gameobject.ObjectHandler;
import com.binextechnologies.arenarush.gui.ARGui;
import com.binextechnologies.arenarush.main.ArenaRush;
import com.binextechnologies.arenarush.main.DebugInfo;
import com.binextechnologies.arenarush.projectiles.ProjectileHandler;
import com.binextechnologies.arenarush.world.WorldHandler;

@SuppressWarnings("serial")
public class ARCanvas extends Canvas
{
	public static boolean canvasActive = false;
	
	public static BufferStrategy bs;
	public static Graphics g;
	public static Graphics2D g2d;
	
	public ARCanvas()
	{
		this.setFocusable(true);
	}
	
	public void update()
	{
		if (!canvasActive) return;
		
		// Place this ARCanvas at the proper location and size
		this.setBounds(0, 0, ArenaRush.core.frame.getWidth(), ArenaRush.core.frame.getHeight());
	}
	
	public void render()
	{
		if (!canvasActive) return;
		
		// Get or create the BufferStrategy to plan rendering
		bs = getBufferStrategy();
		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		/*-----------Start render calls here:--------------------------------------------------------------------------*/
		
		// Create a black rectangle to fill the canvas as a base background
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// Translate the screen based on the Camera
		if (ArenaRush.camera != null)
			g2d.translate(ArenaRush.camera.getPosX(), 
					ArenaRush.camera.getPosY());
		
		if (WorldHandler.currentWorld != null)
		{
			WorldHandler.currentWorld.renderWorld(g, g2d); // Render the world
			WorldHandler.currentWorld.renderSpawnPoints(g);
		}
		
		ObjectHandler.renderObjects(g, g2d); // Render all of the game objects
		ObjectHandler.renderPlayers(g, g2d); // Render all of the players
		
		ProjectileHandler.renderProjectiles(g, g2d); // Render all of the game projectiles
		
		Testing.testRender(g, g2d);
		
		// Translate the screen based on the Camera
		if (ArenaRush.camera != null)
			g2d.translate(-ArenaRush.camera.getPosX(), 
					  	  -ArenaRush.camera.getPosY());
		
		ARGui.renderGui(g, g2d);
		
		if (ArenaRush.debug)
			DebugInfo.drawDebugInfo(g, g2d);
		
		/*-----------Stop render calls here:--------------------------------------------------------------------------*/
		
		g.dispose(); // Dispose of all graphics on the basic Graphics component 
		 // because we have them prepared on the BufferStrategy
		bs.show(); // Show all graphics prepared on the BufferStrategy
		
		ArenaRush.core.frame.repaint();
	}
}
