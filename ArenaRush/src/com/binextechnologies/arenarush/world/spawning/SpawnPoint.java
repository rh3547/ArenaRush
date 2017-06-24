package com.binextechnologies.arenarush.world.spawning;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.binextechnologies.arenarush.math.Vector2f;

public class SpawnPoint implements Serializable
{
	private static final long serialVersionUID = 1884582697164293486L;
	
	public Vector2f pos;
	public boolean active = false;
	protected ArrayList<SpawnInfo> spawns = new ArrayList<SpawnInfo>();
	public int range;
	public int rate;
	public int tickCount = 0;

	public SpawnPoint(Vector2f pos, int range, int rate)
	{
		this.pos = pos;
		this.range = range;
		this.rate = rate;
	}
	
	public void update()
	{
		
	}
	
	public void render(Graphics g)
	{
		g.drawImage(new ImageIcon("res/objects/spawnPoint.png").getImage(), (int)pos.x, (int)pos.y, null);
	}
	
	public void addSpawnableObject(SpawnInfo spawn)
	{
		for (int x = 0; x < spawn.chanceToSpawn; x++)
		{
			spawns.add(new SpawnInfo(spawn.objectId, spawn.chanceToSpawn));
		}
	}

	/**
	 * @return the pos
	 */
	public Vector2f getPos()
	{
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(Vector2f pos)
	{
		this.pos = pos;
	}

	/**
	 * @return the range
	 */
	public int getRange()
	{
		return range;
	}

	/**
	 * @param range the range to set
	 */
	public void setRange(int range)
	{
		this.range = range;
	}
}
