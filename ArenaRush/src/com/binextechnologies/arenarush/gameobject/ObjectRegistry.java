package com.binextechnologies.arenarush.gameobject;

import java.awt.Image;
import java.util.TreeMap;

import javax.swing.ImageIcon;

import com.binextechnologies.arenarush.gameobject.entity.enemy.BasicEnemy;
import com.binextechnologies.arenarush.gameobject.entity.enemy.BigginEnemy;

public class ObjectRegistry
{
	public static TreeMap<Integer, GameObject> objectRegistry = new TreeMap<Integer, GameObject>();
	public static TreeMap<Integer, Image> objectImages = new TreeMap<Integer, Image>();
	
	public static void registerObjects()
	{
		registerObject(new BasicEnemy(0), new ImageIcon("res/objects/basicEnemy.png").getImage());
		registerObject(new BigginEnemy(1), new ImageIcon("res/objects/bigginEnemy.png").getImage());
	}
	
	public static void registerObject(GameObject obj, Image image)
	{
		objectRegistry.put(obj.objectId, obj);
		objectImages.put(obj.objectId, image);
	}
	
	public static GameObject getObject(int key)
	{
		return objectRegistry.get(key);
	}
	
	public static Image getObjectImage(int key)
	{
		return objectImages.get(key);
	}
}
