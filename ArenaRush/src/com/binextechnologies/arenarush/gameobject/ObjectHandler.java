package com.binextechnologies.arenarush.gameobject;

import java.awt.Graphics;
import java.awt.Graphics2D;

import com.binextechnologies.arenarush.gameobject.entity.Entity;
import com.binextechnologies.arenarush.player.Player;
import com.binextechnologies.arenarush.world.WorldHandler;

public class ObjectHandler
{
	/**
	 * Update all of the game objects in the list.
	 */
	public static void updateObjects()
	{
		if (WorldHandler.currentWorld == null) return;
		
		for (int x = 0; x < WorldHandler.currentWorld.objects.size(); x++)
		{
			if (WorldHandler.currentWorld.objects.get(x) instanceof Entity)
				if (((Entity)(WorldHandler.currentWorld.objects.get(x))).isDead())
				{
					WorldHandler.currentWorld.objects.remove(x);
					continue;
				}
			
			if (WorldHandler.currentWorld.objects.size() == 0)
				return;
			
			WorldHandler.currentWorld.objects.get(x).update();
		}
	}
	
	/**
	 * Render all of the game objects in the list.
	 * @param g
	 */
	public static void renderObjects(Graphics g, Graphics2D g2d)
	{
		if (WorldHandler.currentWorld == null) return;
		
		for (int x = 0; x < WorldHandler.currentWorld.objects.size(); x++)
		{
			WorldHandler.currentWorld.objects.get(x).render(g, g2d);
		}
	}
	
	/**
	 * Add an object to the object list.
	 * @param obj
	 */
	public static void addObject(GameObject obj)
	{
		if (WorldHandler.currentWorld == null) return;
		
		WorldHandler.currentWorld.objects.add(obj);
	}
	
	public static void updatePlayers()
	{
		if (WorldHandler.currentWorld == null) return;
		
		for (int x = 0; x < WorldHandler.currentWorld.players.size(); x++)
		{
			if (WorldHandler.currentWorld.players.get(x).isDead())
			{
				WorldHandler.currentWorld.players.remove(x);
				continue;
			}
			
			if (WorldHandler.currentWorld.players.size() == 0)
				return;
			
			WorldHandler.currentWorld.players.get(x).update();
		}
	}
	

	public static void renderPlayers(Graphics g, Graphics2D g2d)
	{
		if (WorldHandler.currentWorld == null) return;
		
		for (int x = 0; x < WorldHandler.currentWorld.players.size(); x++)
		{
			WorldHandler.currentWorld.players.get(x).render(g, g2d);
		}
	}
	

	public static void addPlayer(Player player)
	{
		if (WorldHandler.currentWorld == null) return;
		
		WorldHandler.currentWorld.players.add(player);
	}
}
