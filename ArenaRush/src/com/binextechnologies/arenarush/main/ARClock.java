package com.binextechnologies.arenarush.main;

import com.binextechnologies.arenarush.Testing;
import com.binextechnologies.arenarush.gameobject.ObjectHandler;
import com.binextechnologies.arenarush.gui.ARGui;
import com.binextechnologies.arenarush.gui.ARMenu;
import com.binextechnologies.arenarush.projectiles.ProjectileHandler;
import com.binextechnologies.arenarush.world.WorldHandler;

public class ARClock extends Thread implements Runnable
{ 
	public static boolean running = false; // Is the clock running
	public static boolean update = true; // Should updates occur
	public static boolean render = true; // Should rendering occur
	
	/*
	 * Tick Variables
	 */
	public static int tps = 60;
	private int ticks = 0; // The number of ticks that are actually being updated per second
	public static int topTicks = 0; // The total number of ticks that happened last second
	private long lastTime = System.nanoTime(); // The last time checked in nanoseconds
	private double nsPerTick = 1000000000.0; // The number of nanoseconds per tick
	private long lastTimer = System.currentTimeMillis(); // The last time checked in milliseconds
	private double delta = 0; // A count variable used to track change in time
	private int minuteCount = 0;
	
	/*
	 * Fps Variables
	 */
	public static int fps = 60;
	private int frames = 0; // The number of frames that are actually being updated per second
	public static int topFrames = 0; // The total number of frames that happened last second
	private long lastTime2 = System.nanoTime(); // The last time checked in nanoseconds
	private double nsPerFrame = 1000000000.0; // The number of nanoseconds per tick
	private long lastTimer2 = System.currentTimeMillis(); // The last time checked in milliseconds
	private double delta2 = 0; // A count variable used to track change in time
	
	/**
	 * Start the clock.  This will start updating and rendering.
	 */
	public static void startClock()
	{
		running = true;
	}
	
	/**
	 * Stop the clock.  This will stop
	 * updating and rendering.
	 */
	public static void stopClock()
	{
		running = false;
	}
	
	/**
	 * Set whether or not updates should occur.
	 * @param update
	 */
	public static void setShouldUpdate(boolean update)
	{
		ARClock.update = update;
	}
	
	/**
	 * Set whether or not updates should occur.
	 * @param update
	 */
	public static void setShouldRender(boolean render)
	{
		ARClock.render = render;
	}
	
	@Override
	public void run()
	{
		/*
		 * Setup ticks
		 */
		// Start comparison time in nanoseconds
		lastTime = System.nanoTime();
		// How many nanoseconds per tick the clock should update at
		// Based on tps
		nsPerTick = 1000000000.0 / tps;
		// Start comparison time in milliseconds
		lastTimer = System.currentTimeMillis();
		// Change in time between updates
		delta = 0;
		
		/*
		 * Setup frames
		 */
		// Start comparison time in nanoseconds
		lastTime2 = System.nanoTime();
		// How many nanoseconds per frame the clock should update at
		// Based on fps
		nsPerFrame = 1000000000.0 / fps;
		// Start comparison time in milliseconds
		lastTimer2 = System.currentTimeMillis();
		// Change in time between updates
		delta2 = 0;
		
		while (true)
		{
			while (running)
			{
				nsPerTick = 1000000000.0 / tps;
				nsPerFrame = 1000000000.0 / fps;
				
				/*
				 * Ticks Timer
				 */
				// The current system time in nanoseconds (resets on update)
				long now = System.nanoTime();
				// Update delta every update based on difference in time
				delta += (now - lastTime) / nsPerTick;
				// Set the start comparison time to the last tested time
				lastTime = now;
				
				// Enters loop if an update should occur (tps ticks have occurred in a second)
				while(delta >= 1)
				{
					update();
					
					ticks++; // Increment ticks
					delta -= 1; // Reset delta for next update
				}
				
				// Update half as often as a normal tick
				if ((ticks % 2) == 0)
				{
					halfUpdate();
				}
				
				// If a second has gone by, reset all counts
				if (System.currentTimeMillis() - lastTimer >= 1000)
				{
					lastTimer += 1000;
					
					topTicks = ticks;
					ticks = 0;
					
					minuteCount++;
					
					secUpdate();
				}
				
				if (minuteCount >= 60)
				{
					minuteCount = 0;
					
					minuteUpdate();
				}
				
				/*
				 * Ticks Timer
				 */
				// The current system time in nanoseconds (resets on update)
				long now2 = System.nanoTime();
				// Update delta every update based on difference in time
				delta2 += (now2 - lastTime2) / nsPerFrame;
				// Set the start comparison time to the last tested time
				lastTime2 = now2;
				
				// Enters loop if an update should occur (fps frames have occurred in a second)
				while(delta2 >= 1)
				{
					render();
					
					frames++; // Increment frames
					delta2 -= 1; // Reset delta for next update
				}
				
				// If a second has gone by, reset all counts
				if (System.currentTimeMillis() - lastTimer2 >= 1000)
				{
					lastTimer2 += 1000;
					
					topFrames = frames;
					frames = 0;
				}
			}
		}
	}
	
	/**
	 * Run a game logic update.
	 */
	private void update()
	{
		if (update)
		{
			ArenaRush.core.update();
			ArenaRush.core.canvas.update();
			if (ArenaRush.camera != null)
				ArenaRush.camera.update();
			if (WorldHandler.currentWorld != null)
				WorldHandler.currentWorld.updateSpawnPoints();
			ObjectHandler.updateObjects();
			ObjectHandler.updatePlayers();
			ProjectileHandler.updateProjectiles();
			Testing.testUpdate();
			ARMenu.update();
			ARGui.updateGui();
		}
	}
	
	/**
	 * Updates every second.
	 */
	private void secUpdate()
	{
		
	}
	
	/**
	 * Run a game logic update half as often as update().
	 */
	private void halfUpdate()
	{
		if (update)
		{
			
		}
	}
	
	/**
	 * Run a game logic update every minute.
	 */
	private void minuteUpdate()
	{
		if (update)
		{
			
		}
	}
	
	/**
	 * Run a game render update.
	 */
	private void render()
	{
		if (render)
		{
			ArenaRush.core.canvas.render();
		}
	}
}
