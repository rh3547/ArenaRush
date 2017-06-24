package com.binextechnologies.arenarush.main;

import com.binextechnologies.arenarush.graphics.ARCamera;
import com.binextechnologies.arenarush.graphics.ARCanvas;
import com.binextechnologies.arenarush.gui.ARGui;
import com.binextechnologies.arenarush.gui.ARMenu;
import com.binextechnologies.arenarush.profiles.ProfileHandler;
import com.binextechnologies.arenarush.world.World;
import com.binextechnologies.arenarush.world.WorldHandler;

public enum GameState
{
	MAIN_MENU, OPTIONS_MENU, PROFILE_MENU, ESCAPE_MENU, PLAY;
	
	public static GameState state = MAIN_MENU;
	
	public static void switchToMainMenu()
	{
		GameState.state = MAIN_MENU;
		
		ARCanvas.canvasActive = false;	
		ARCanvas.bs = null;
		ARGui.drawGui = false;
		WorldHandler.currentWorld = null;
		
		ARMenu.createMainMenu();
		
		ArenaRush.core.frame.setComponentZOrder(ARMenu.panel, 0);
		ArenaRush.core.frame.setComponentZOrder(ArenaRush.core.canvas, 1);
		
		ArenaRush.core.frame.repaint();
	}
	
	public static void switchToOptionsMenu()
	{
		GameState.state = OPTIONS_MENU;
		
		ARCanvas.canvasActive = false;	
		ARCanvas.bs = null;
		ARGui.drawGui = false;
		WorldHandler.currentWorld = null;
		
		ARMenu.createOptionsMenu();
		
		ArenaRush.core.frame.setComponentZOrder(ARMenu.panel, 0);
		ArenaRush.core.frame.setComponentZOrder(ArenaRush.core.canvas, 1);
		
		ArenaRush.core.frame.repaint();
	}
	
	public static void switchToProfileMenu()
	{
		GameState.state = PROFILE_MENU;
		
		ARCanvas.canvasActive = false;	
		ARCanvas.bs = null;
		ARGui.drawGui = false;
		WorldHandler.currentWorld = null;
		
		ARMenu.createProfileMenu();
		
		ArenaRush.core.frame.setComponentZOrder(ARMenu.panel, 0);
		ArenaRush.core.frame.setComponentZOrder(ArenaRush.core.canvas, 1);
		
		ArenaRush.core.frame.repaint();
	}
	
	public static void switchToPlay()
	{
		GameState.state = GameState.PLAY;
		
		ArenaRush.core.frame.setComponentZOrder(ArenaRush.core.canvas, 0);
		ArenaRush.core.frame.setComponentZOrder(ARMenu.panel, 1);
		ARMenu.panel.setVisible(false);
		
		WorldHandler.currentWorld = World.loadWorld("res/worlds/TestWorld.world");
		WorldHandler.currentWorld.initWorld();
		ArenaRush.player = WorldHandler.currentWorld.players.get(0);
		ArenaRush.camera = new ARCamera(0, 0, ArenaRush.player);
		
		ARGui.drawGui = true;
		ARGui.updateWeaponImage();
		
		ProfileHandler.setPlayerStats();
		
		ArenaRush.core.canvas.requestFocus();
		ARCanvas.canvasActive = true;
	}
}
