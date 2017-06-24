package com.binextechnologies.arenarush.main;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.binextechnologies.arenarush.Testing;
import com.binextechnologies.arenarush.gameobject.ObjectRegistry;
import com.binextechnologies.arenarush.graphics.ARCamera;
import com.binextechnologies.arenarush.gui.ARMenu;
import com.binextechnologies.arenarush.player.Player;
import com.binextechnologies.arenarush.profiles.ARProfile;
import com.binextechnologies.arenarush.profiles.ProfileHandler;
import com.binextechnologies.arenarush.projectiles.ProjectileRegistry;
import com.binextechnologies.arenarush.tile.TileRegistry;

public class ArenaRush 
{
	public static String OS = "";
	public static String gamePath = "";
	public static int renderDistance;
	
	public static Core core; // The core that controls most of the game
	
	public static Player player; // The player
	public static Image playerImage = new ImageIcon("res/objects/player3.png").getImage();
	public static ARCamera camera;
	
	public static boolean debug = false;
	
	public ArenaRush()
	{
		// Detect the user's operating system
		OS = ARToolkit.getOS();
		// Set the game's res and file paths based on OS
		if (OS.equals("windows"))
		{
			gamePath = System.getProperty("user.home") + "\\Documents\\Games\\" + "Arena Rush";
		}
		else if (OS.equals("mac"))
		{
			gamePath = System.getProperty("user.home") + "\\Games\\" + "Arena Rush";
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Operating system not supported");
			System.exit(0);
		}
		
		// Create the game's base directory
		File dir = new File(gamePath);
		if (!dir.exists())
			try
			{
		        dir.mkdir();
		    } 
			catch(SecurityException se)
			{
		        
		    }
		dir = new File(gamePath + "\\Profiles");
		if (!dir.exists())
			try
			{
		        dir.mkdir();
		    } 
			catch(SecurityException se)
			{
		        
		    }
		dir = new File(gamePath + "\\Levels");
		if (!dir.exists())
			try
			{
		        dir.mkdir();
		    } 
			catch(SecurityException se)
			{
		        
		    }
		
		dir = new File(gamePath + "\\Profiles");
		if (dir.isDirectory())
			if (dir.list().length == 0)
			{
				ProfileHandler.createProfile();
				try
				{
					File f = new File(ArenaRush.gamePath + "\\" + "info" + ".txt");
					PrintWriter pw = new PrintWriter(f);
					pw.print(ProfileHandler.currentProfile.name + ".profile");
					pw.close();
				}
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				try
				{
					File f = new File(ArenaRush.gamePath + "\\" + "info" + ".txt");
					Scanner scan = new Scanner(f);
					
					File file = new File(gamePath + "\\Profiles\\" + scan.next());
			    	
			    	try
				    {
				       FileInputStream fileIn = new FileInputStream(file);
				       ObjectInputStream in = new ObjectInputStream(fileIn);
				       
				       ProfileHandler.currentProfile = (ARProfile) in.readObject();
				       
				       in.close();
				       fileIn.close();
				       scan.close();
				    }
					catch(IOException i)
				    {
				       i.printStackTrace();
				    }
					catch(ClassNotFoundException c)
				    {
				       System.out.println("ARProfile class not found");
				       c.printStackTrace();
				    }
				}
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		
		core = new Core("Arena Rush", 1280, 720, 60, 60);
		renderDistance = (ArenaRush.core.windowWidth / 2) + 632;
		
		TileRegistry.registerTiles();
		ProjectileRegistry.registerProjectiles();
		ObjectRegistry.registerObjects();
		
		ARMenu.setupPanel();
		GameState.switchToMainMenu();
		
		Testing.test();
		
		core.startGame();
	}
	
	public static void main(String[] args)
	{
		new ArenaRush();
	}
}