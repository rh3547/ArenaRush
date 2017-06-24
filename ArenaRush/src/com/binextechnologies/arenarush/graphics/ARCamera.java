package com.binextechnologies.arenarush.graphics;

import com.binextechnologies.arenarush.gameobject.GameObject;
import com.binextechnologies.arenarush.main.ArenaRush;

public class ARCamera
{
	private float posX; // The camera's x position
	private float posY; // The camera's y position
	private GameObject followObject; // The object that the camera should follow
	
	/**
	 * Create a new Camera.
	 * @param xPos
	 * @param yPos
	 * @param followObject
	 */
	public ARCamera(float xPos, float yPos, GameObject followObject)
	{
		this.posX = xPos;
		this.posY = yPos;
		this.followObject = followObject;
	}
	
	/**
	 * Update the position of the camera based on the
	 * position of the followObject.  A tweaning
	 * effect is applied to the camera's movement.
	 * Tweaning is what makes the camera seem a bit
	 * "lagged".  It makes the camera move a bit slower
	 * than the followObject to create a cool effect.
	 */
	public void update()
	{
		if (followObject.getPosX() > 650 && followObject.getPosX() <= 3500) 
		{
			posX += ((-followObject.getPosX() + ArenaRush.core.frame.getWidth() / 2) - posX) * 0.0275F;
		}
		else
		{
			if (posX < -5 && posX >= -2810)
				posX += ((-followObject.getPosX() + ArenaRush.core.frame.getWidth() / 2) - posX) * 0.0275F;
			else
			{
				if (posX >= -5)
				{
					posX = 0;
				}
			}
		}
		
		/*
		posY += ((-followObject.getPosY() + ArenaRush.core.frame.getHeight() / 2) - posY) * 0.075F;
		*/
	}

	/**
	 * Get the camera's x position.
	 * @return the xPos
	 */
	public float getPosX()
	{
		return posX;
	}

	/**
	 * get the camera's y position.
	 * @return the yPos
	 */
	public float getPosY()
	{
		return posY;
	}
	
	/**
	 * @param xPos the xPos to set
	 */
	public void setPosX(float xPos)
	{
		this.posX = xPos;
	}

	/**
	 * @param yPos the yPos to set
	 */
	public void setPosY(float yPos)
	{
		this.posY = yPos;
	}

	/**
	 * Get the GameObject this Camera is following.
	 * @return followObject
	 */
	public GameObject getFollowObject()
	{
		return followObject;
	}
}

