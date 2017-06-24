package com.binextechnologies.arenarush.world.spawning;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

import com.binextechnologies.arenarush.gameobject.GameObject;
import com.binextechnologies.arenarush.gameobject.ObjectRegistry;
import com.binextechnologies.arenarush.math.Vector2f;
import com.binextechnologies.arenarush.world.WorldHandler;

public class SpawnPointFinite extends SpawnPoint
{
	private static final long serialVersionUID = 2795829495035155056L;
	
	public int maxSpawns;
	public int spawnCount = 0;

	public SpawnPointFinite(Vector2f pos, int range, int rate, int maxSpawns)
	{
		super(pos, range, rate);
		
		this.maxSpawns = maxSpawns;
	}
	
	@Override
	public void update()
	{
		tickCount++;
		
		if (WorldHandler.currentWorld == null) return;
		
		if (spawnCount >= maxSpawns)
		{
			active = false;
			return;
		}
		
		active = false;
		
		for (int x = 0; x < WorldHandler.currentWorld.players.size(); x++)
		{
			if ((WorldHandler.currentWorld.players.get(x).posX <= pos.x + range && 
				WorldHandler.currentWorld.players.get(x).posX >= pos.x - range) ||
				(WorldHandler.currentWorld.players.get(x).posY <= pos.y + range && 
				WorldHandler.currentWorld.players.get(x).posY >= pos.y - range))
			{
				active = true;
				break;
			}
		}
		
		if (active)
		{
			if (tickCount % rate == 0)
			{
				GameObject obj = ObjectRegistry.getObject(getObjectToSpawn());
				obj.spawn(pos.x, pos.y);
				spawnCount++;
			}
		}
	}
	
	public int getObjectToSpawn()
	{
		Random rand = new Random();
		
		return rand.nextInt(spawns.size());
	}
	
	@Override
	public void render(Graphics g)
	{
		g.drawImage(new ImageIcon("res/objects/spawnPoint.png").getImage(), (int)pos.x, (int)pos.y, null);
	}

	/**
	 * @return the active
	 */
	public boolean isActive()
	{
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active)
	{
		this.active = active;
	}
}
