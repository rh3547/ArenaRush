package com.binextechnologies.arenarush.projectiles;

import java.awt.Image;

public class ProjectileImages
{
	private Image projectileImage;
	private Image guiImage;
	
	public ProjectileImages(Image projectileImage, Image guiImage)
	{
		this.projectileImage = projectileImage;
		this.guiImage = guiImage;
	}

	/**
	 * @return the projectileImage
	 */
	public Image getProjectileImage()
	{
		return projectileImage;
	}

	/**
	 * @param projectileImage the projectileImage to set
	 */
	public void setProjectileImage(Image projectileImage)
	{
		this.projectileImage = projectileImage;
	}

	/**
	 * @return the guiImage
	 */
	public Image getGuiImage()
	{
		if (guiImage == null)
			return projectileImage;
		
		return guiImage;
	}

	/**
	 * @param guiImage the guiImage to set
	 */
	public void setGuiImage(Image guiImage)
	{
		this.guiImage = guiImage;
	}
}
