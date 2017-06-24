package com.binextechnologies.arenarush.world.spawning;

import java.io.Serializable;

public class SpawnInfo implements Serializable
{
	private static final long serialVersionUID = 8938303471165205284L;
	
	public int objectId;
	public int chanceToSpawn;
	
	public SpawnInfo(int objectId, int chanceToSpawn)
	{
		this.objectId = objectId;
		this.chanceToSpawn = chanceToSpawn;
	}

	/**
	 * @return the objectId
	 */
	public int getObjectId()
	{
		return objectId;
	}

	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(int objectId)
	{
		this.objectId = objectId;
	}

	/**
	 * @return the chanceToSpawn
	 */
	public int getChanceToSpawn()
	{
		return chanceToSpawn;
	}

	/**
	 * @param chanceToSpawn the chanceToSpawn to set
	 */
	public void setChanceToSpawn(int chanceToSpawn)
	{
		this.chanceToSpawn = chanceToSpawn;
	}
}