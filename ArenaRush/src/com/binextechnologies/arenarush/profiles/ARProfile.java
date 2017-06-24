package com.binextechnologies.arenarush.profiles;

import java.io.Serializable;

public class ARProfile implements Serializable
{
	private static final long serialVersionUID = 2320068977145795865L;
	
	public String name;
	
	public int currency = 0;
	
	public float health = 25;
	public float damage = 5;
	public float speed = 5;
	public float boostModifier = 2.0F;

	public ARProfile(String name)
	{
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
