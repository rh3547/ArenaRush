package com.binextechnologies.arenarush.profiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.binextechnologies.arenarush.main.ArenaRush;

public class ProfileHandler
{
	public static ARProfile currentProfile;
	
	public static void chooseProfile()
	{
		JFileChooser chooser = new JFileChooser(ArenaRush.gamePath + "\\Profiles");
		
		int returnVal = chooser.showOpenDialog(null);

	    if (returnVal == JFileChooser.APPROVE_OPTION) 
	    {
	    	File file = chooser.getSelectedFile();
	    	
	    	try
		    {
		       FileInputStream fileIn = new FileInputStream(file);
		       ObjectInputStream in = new ObjectInputStream(fileIn);
		       
		       currentProfile = (ARProfile) in.readObject();
		       
		       in.close();
		       fileIn.close();
		       
		       try
				{
					File f = new File(ArenaRush.gamePath + "\\" + "info" + ".txt");
					f.delete();
					
					File f2 = new File(ArenaRush.gamePath + "\\" + "info" + ".txt");
					PrintWriter pw = new PrintWriter(f2);
					pw.print(ProfileHandler.currentProfile.name + ".profile");
					pw.close();
				}
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
		       
		       setPlayerStats();
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
	    else
	    {
	    	return;
	    }
	}
	
	public static void createProfile()
	{
		String name = JOptionPane.showInputDialog(null, "Enter the profile name.");
		
		ARProfile prof = new ARProfile(name);
		
		try
	    {
			File dir = new File(ArenaRush.gamePath + "\\Profiles\\" + name);
			if (!dir.exists())
				try
				{
			        dir.mkdir();
			    } 
				catch(SecurityException se)
				{
			        
			    }
			
	       FileOutputStream fileOut = new FileOutputStream(ArenaRush.gamePath + "\\Profiles\\" + name + "\\" + name + ".profile");
	       ObjectOutputStream out = new ObjectOutputStream(fileOut);
	       out.writeObject(prof);
	       out.close();
	       fileOut.close();
	       
	       currentProfile = prof;
	       
	       try
			{
				File f = new File(ArenaRush.gamePath + "\\" + "info" + ".txt");
				f.delete();
				
				File f2 = new File(ArenaRush.gamePath + "\\" + "info" + ".txt");
				PrintWriter pw = new PrintWriter(f2);
				pw.print(ProfileHandler.currentProfile.name + ".profile");
				pw.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
	       
	       setPlayerStats();
	    }
		catch(IOException i)
	    {
	        i.printStackTrace();
	    }
	}
	
	public static void setPlayerStats()
	{
		if (ArenaRush.player == null) return;
		
		ArenaRush.player.health = currentProfile.health;
		ArenaRush.player.damage = currentProfile.damage;
		ArenaRush.player.speed = currentProfile.speed;
		ArenaRush.player.boostModifier = currentProfile.boostModifier;
	}
}
