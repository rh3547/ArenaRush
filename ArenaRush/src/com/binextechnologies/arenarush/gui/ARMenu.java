package com.binextechnologies.arenarush.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.binextechnologies.arenarush.main.ArenaRush;
import com.binextechnologies.arenarush.main.GameState;
import com.binextechnologies.arenarush.profiles.ProfileHandler;

public class ARMenu
{
	/*
	 * The menu that is active.
	 * 0 = Main menu
	 * 1 = Options menu
	 */
	public static int menuActive = 0;
	
	public static ARMMActionListener al = new ARMMActionListener();
	
	public static JPanel panel;
	public static Image bgImage;
	
	public static JButton backBtn = new JButton();
	
	// Main Menu
	public static JButton exitBtn = new JButton();
	public static JButton playBtn = new JButton();
	public static JButton optionsBtn = new JButton();
	public static JButton profileBtn = new JButton();
	
	// Profile Menu
	public static JButton statsBtn = new JButton();
	public static JButton storeBtn = new JButton();
	public static JButton achievementsBtn = new JButton();
	public static JButton signOutBtn = new JButton();
	public static JButton signInBtn = new JButton();
	public static JButton newBtn = new JButton();
	
	@SuppressWarnings("serial")
	public static void setupPanel()
	{
		bgImage = new ImageIcon("res/gui/mainMenu.png").getImage();
		
		panel = new JPanel()
		{
			@Override
			public void paintComponent(Graphics g)
			{
				g.drawImage(bgImage, 0, 0, null);
				
				if (GameState.state == GameState.MAIN_MENU)
					if (ProfileHandler.currentProfile != null)
					{
						g.setFont(new Font("Verdana", Font.PLAIN, 24));
						g.setColor(Color.WHITE);
						g.drawString(ProfileHandler.currentProfile.name, 225, ArenaRush.core.windowHeight - 50);
					}
			}
		};
		panel.setBounds(0, 0, ArenaRush.core.windowWidth, ArenaRush.core.windowHeight);
		panel.setLayout(null);
		panel.setVisible(true);
		ArenaRush.core.frame.add(panel);
	}
	
	public static void createMainMenu()
	{
		panel.removeAll();
		
		bgImage = new ImageIcon("res/gui/mainMenu.png").getImage();
		
		panel.setVisible(true);
		
		exitBtn.setContentAreaFilled(false);
		exitBtn.setBorderPainted(false);
		exitBtn.setFocusPainted(false);
		exitBtn.setIcon(new ImageIcon("res/gui/exitBtn.png"));
		exitBtn.setRolloverIcon(new ImageIcon("res/gui/exitBtnHover.png"));
		exitBtn.addActionListener(al);
		exitBtn.setBounds((ArenaRush.core.windowWidth / 2) + 150, ArenaRush.core.windowHeight - 377, 400, 170);
		exitBtn.setVisible(true);
		panel.add(exitBtn);
		
		playBtn.setContentAreaFilled(false);
		playBtn.setBorderPainted(false);
		playBtn.setFocusPainted(false);
		playBtn.setIcon(new ImageIcon("res/gui/playBtn.png"));
		playBtn.setRolloverIcon(new ImageIcon("res/gui/playBtnHover.png"));
		playBtn.addActionListener(al);
		playBtn.setBounds((ArenaRush.core.windowWidth / 2) - 205, ArenaRush.core.windowHeight - 400, 400, 170);
		playBtn.setVisible(true);
		panel.add(playBtn);
		
		optionsBtn.setContentAreaFilled(false);
		optionsBtn.setBorderPainted(false);
		optionsBtn.setFocusPainted(false);
		optionsBtn.setIcon(new ImageIcon("res/gui/optionsBtn.png"));
		optionsBtn.setRolloverIcon(new ImageIcon("res/gui/optionsBtnHover.png"));
		optionsBtn.addActionListener(al);
		optionsBtn.setBounds((ArenaRush.core.windowWidth / 2) - 560, ArenaRush.core.windowHeight - 423, 400, 170);
		optionsBtn.setVisible(true);
		panel.add(optionsBtn);
		
		profileBtn.setContentAreaFilled(false);
		profileBtn.setBorderPainted(false);
		profileBtn.setFocusPainted(false);
		profileBtn.setIcon(new ImageIcon("res/gui/profileBtn.png"));
		profileBtn.setRolloverIcon(new ImageIcon("res/gui/profileBtnHover.png"));
		profileBtn.addActionListener(al);
		profileBtn.setBounds(25, ArenaRush.core.windowHeight - 100, 185, 75);
		profileBtn.setVisible(true);
		panel.add(profileBtn);
	}
	
	public static void createOptionsMenu()
	{
		panel.removeAll();
		
		bgImage = new ImageIcon("res/gui/optionsMenu.png").getImage();
		
		panel.setVisible(true);
		
		backBtn.setContentAreaFilled(false);
		backBtn.setBorderPainted(false);
		backBtn.setFocusPainted(false);
		backBtn.setIcon(new ImageIcon("res/gui/backBtn.png"));
		backBtn.setRolloverIcon(new ImageIcon("res/gui/backBtnHover.png"));
		backBtn.addActionListener(al);
		backBtn.setBounds(25, ArenaRush.core.windowHeight - 100, 185, 75);
		backBtn.setVisible(true);
		panel.add(backBtn);
	}
	
	public static void createProfileMenu()
	{
		panel.removeAll();
		
		bgImage = new ImageIcon("res/gui/profileMenu.png").getImage();
		
		panel.setVisible(true);
		
		achievementsBtn.setContentAreaFilled(false);
		achievementsBtn.setBorderPainted(false);
		achievementsBtn.setFocusPainted(false);
		achievementsBtn.setIcon(new ImageIcon("res/gui/achievementsBtn.png"));
		achievementsBtn.setRolloverIcon(new ImageIcon("res/gui/achievementsBtnHover.png"));
		achievementsBtn.addActionListener(al);
		achievementsBtn.setBounds((ArenaRush.core.windowWidth / 2) + 150, ArenaRush.core.windowHeight - 377, 400, 170);
		achievementsBtn.setVisible(true);
		panel.add(achievementsBtn);
		
		storeBtn.setContentAreaFilled(false);
		storeBtn.setBorderPainted(false);
		storeBtn.setFocusPainted(false);
		storeBtn.setIcon(new ImageIcon("res/gui/storeBtn.png"));
		storeBtn.setRolloverIcon(new ImageIcon("res/gui/storeBtnHover.png"));
		storeBtn.addActionListener(al);
		storeBtn.setBounds((ArenaRush.core.windowWidth / 2) - 205, ArenaRush.core.windowHeight - 400, 400, 170);
		storeBtn.setVisible(true);
		panel.add(storeBtn);
		
		statsBtn.setContentAreaFilled(false);
		statsBtn.setBorderPainted(false);
		statsBtn.setFocusPainted(false);
		statsBtn.setIcon(new ImageIcon("res/gui/statsBtn.png"));
		statsBtn.setRolloverIcon(new ImageIcon("res/gui/statsBtnHover.png"));
		statsBtn.addActionListener(al);
		statsBtn.setBounds((ArenaRush.core.windowWidth / 2) - 560, ArenaRush.core.windowHeight - 423, 400, 170);
		statsBtn.setVisible(true);
		panel.add(statsBtn);
		
		backBtn.setContentAreaFilled(false);
		backBtn.setBorderPainted(false);
		backBtn.setFocusPainted(false);
		backBtn.setIcon(new ImageIcon("res/gui/backBtn.png"));
		backBtn.setRolloverIcon(new ImageIcon("res/gui/backBtnHover.png"));
		backBtn.addActionListener(al);
		backBtn.setBounds(25, ArenaRush.core.windowHeight - 100, 185, 75);
		backBtn.setVisible(true);
		panel.add(backBtn);
		
		signOutBtn.setContentAreaFilled(false);
		signOutBtn.setBorderPainted(false);
		signOutBtn.setFocusPainted(false);
		signOutBtn.setIcon(new ImageIcon("res/gui/signOutBtn.png"));
		signOutBtn.setRolloverIcon(new ImageIcon("res/gui/signOutBtnHover.png"));
		signOutBtn.addActionListener(al);
		signOutBtn.setBounds(215, ArenaRush.core.windowHeight - 90, 185, 75);
		signOutBtn.setVisible(true);
		panel.add(signOutBtn);
	}
	
	public static void createSignInMenu()
	{
		panel.removeAll();
		
		bgImage = new ImageIcon("res/gui/profileMenu.png").getImage();
		
		panel.setVisible(true);
		
		newBtn.setContentAreaFilled(false);
		newBtn.setBorderPainted(false);
		newBtn.setFocusPainted(false);
		newBtn.setIcon(new ImageIcon("res/gui/newBtn.png"));
		newBtn.setRolloverIcon(new ImageIcon("res/gui/newBtnHover.png"));
		newBtn.addActionListener(al);
		newBtn.setBounds((ArenaRush.core.windowWidth / 2) + 150, ArenaRush.core.windowHeight - 377, 400, 170);
		newBtn.setVisible(true);
		panel.add(newBtn);
		
		signInBtn.setContentAreaFilled(false);
		signInBtn.setBorderPainted(false);
		signInBtn.setFocusPainted(false);
		signInBtn.setIcon(new ImageIcon("res/gui/signInBtn.png"));
		signInBtn.setRolloverIcon(new ImageIcon("res/gui/signInBtnHover.png"));
		signInBtn.addActionListener(al);
		signInBtn.setBounds((ArenaRush.core.windowWidth / 2) - 560, ArenaRush.core.windowHeight - 423, 400, 170);
		signInBtn.setVisible(true);
		panel.add(signInBtn);
		
		ArenaRush.core.frame.repaint();
	}
	
	public static void update()
	{
		
	}
	
	public static void render(Graphics g)
	{
		
	}
}

class ARMMActionListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (GameState.state == GameState.MAIN_MENU)
		{
			if (e.getSource() == ARMenu.exitBtn)
			{
				try
			    {
			       FileOutputStream fileOut = new FileOutputStream(ArenaRush.gamePath + "\\Profiles\\" + ProfileHandler.currentProfile.name + ".profile");
			       ObjectOutputStream out = new ObjectOutputStream(fileOut);
			       out.writeObject(ProfileHandler.currentProfile);
			       out.close();
			       fileOut.close();
			       
			       try
					{
						File f = new File(ArenaRush.gamePath + "\\" + "info" + ".txt");
						f.delete();
						
						File f2 = new File(ArenaRush.gamePath + "\\" + "info" + ".txt");
						PrintWriter pw = new PrintWriter(f2);
						pw.print(ProfileHandler.currentProfile.name + ".profile");
						pw.close();
					}
					catch (FileNotFoundException e2)
					{
						e2.printStackTrace();
					}
			    }
				catch(IOException i)
			    {
			        i.printStackTrace();
			    }
				
				System.exit(0);
			}
			
			if (e.getSource() == ARMenu.playBtn)
			{
				GameState.switchToPlay();
			}
			
			if (e.getSource() == ARMenu.optionsBtn)
			{
				GameState.switchToOptionsMenu();
			}
			
			if (e.getSource() == ARMenu.profileBtn)
			{
				GameState.switchToProfileMenu();
			}
		}
		
		if (GameState.state == GameState.OPTIONS_MENU)
		{
			if (e.getSource() == ARMenu.backBtn)
			{
				GameState.switchToMainMenu();
			}
		}
		
		if (GameState.state == GameState.PROFILE_MENU)
		{
			if (e.getSource() == ARMenu.backBtn)
			{
				GameState.switchToMainMenu();
			}
			
			if (e.getSource() == ARMenu.statsBtn)
			{
				
			}
			
			if (e.getSource() == ARMenu.storeBtn)
			{
				
			}
			
			if (e.getSource() == ARMenu.achievementsBtn)
			{
				
			}
			
			if (e.getSource() == ARMenu.signOutBtn)
			{
				ARMenu.createSignInMenu();
			}
			
			if (e.getSource() == ARMenu.signInBtn)
			{
				ProfileHandler.chooseProfile();
				
				GameState.switchToMainMenu();
			}
			
			if (e.getSource() == ARMenu.newBtn)
			{
				ProfileHandler.createProfile();
				
				GameState.switchToMainMenu();
			}
		}
	}
}