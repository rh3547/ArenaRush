package com.binextechnologies.arenarush.player;

import java.awt.event.KeyEvent;

import com.binextechnologies.arenarush.gui.ARGui;
import com.binextechnologies.arenarush.input.KeyboardHandler;
import com.binextechnologies.arenarush.main.ARClock;
import com.binextechnologies.arenarush.main.ArenaRush;
import com.binextechnologies.arenarush.main.GameState;
import com.binextechnologies.arenarush.projectiles.BasicBullet;
import com.binextechnologies.arenarush.projectiles.Projectile;
import com.binextechnologies.arenarush.projectiles.ProjectileRegistry;
import com.binextechnologies.arenarush.projectiles.RubberBullet;
import com.binextechnologies.arenarush.projectiles.TriShotBasic;
import com.binextechnologies.arenarush.world.WorldHandler;

public class PlayerController
{
	public static int up = KeyEvent.VK_W;
	public static int down = KeyEvent.VK_S;
	public static int left = KeyEvent.VK_A;
	public static int right = KeyEvent.VK_D;
	public static int boost = KeyEvent.VK_SHIFT;
	public static int one = KeyEvent.VK_1;
	public static int two = KeyEvent.VK_2;
	public static int three = KeyEvent.VK_3;
	public static int four = KeyEvent.VK_4;
	public static int five = KeyEvent.VK_5;
	public static int six = KeyEvent.VK_6;
	public static int seven = KeyEvent.VK_7;
	public static int eight = KeyEvent.VK_8;
	public static int nine = KeyEvent.VK_9;
	
	/**
	 * Called when a key is pressed to handle player movement.
	 * @param keyCode
	 */
	public static void keyPressed(int keyCode)
	{
		if (GameState.state != GameState.PLAY) return;
		
		float speed = ArenaRush.player.getSpeed();
		
		if (KeyboardHandler.getKeyState(up))
		{
			ArenaRush.player.setVelY(-speed);
		}
		if (KeyboardHandler.getKeyState(down))
		{
			ArenaRush.player.setVelY(speed);
		}
		if (KeyboardHandler.getKeyState(left))
		{
			ArenaRush.player.setVelX(-speed);
		}
		if (KeyboardHandler.getKeyState(right))
		{
			ArenaRush.player.setVelX(speed);
		}
	}
	
	/**
	 * Called when a key is released to handle player movement.
	 * @param keyCode
	 */
	public static void keyReleased(int keyCode)
	{
		if (GameState.state != GameState.PLAY) return;
		
		float speed = ArenaRush.player.getSpeed();
		float boostModifier = ArenaRush.player.getBoostModifier();
		
		if (!KeyboardHandler.getKeyState(up) && !KeyboardHandler.getKeyState(down))
		{
			ArenaRush.player.setVelY(0);
		}
		else if (KeyboardHandler.getKeyState(up) && !KeyboardHandler.getKeyState(down))
		{
			ArenaRush.player.setVelY(-speed);
		}
		else if (!KeyboardHandler.getKeyState(up) && KeyboardHandler.getKeyState(down))
		{
			ArenaRush.player.setVelY(speed);
		}
		
		if (!KeyboardHandler.getKeyState(left) && !KeyboardHandler.getKeyState(right))
		{
			ArenaRush.player.setVelX(0);
		}
		else if (KeyboardHandler.getKeyState(left) && !KeyboardHandler.getKeyState(right))
		{
			ArenaRush.player.setVelX(-speed);
		}
		else if (!KeyboardHandler.getKeyState(left) && KeyboardHandler.getKeyState(right))
		{
			ArenaRush.player.setVelX(speed);
		}
		
		if (keyCode == boost && KeyboardHandler.getKeyState(left) && !KeyboardHandler.getKeyState(right))
		{
			if (!ArenaRush.player.isCollidingLeft())
				ArenaRush.player.setVelX((-speed * boostModifier));
		}
		if (keyCode == boost && !KeyboardHandler.getKeyState(left) && KeyboardHandler.getKeyState(right))
		{
			if (!ArenaRush.player.isCollidingRight())
				ArenaRush.player.setVelX((speed * boostModifier));
		}
		if (keyCode == boost && KeyboardHandler.getKeyState(up) && !KeyboardHandler.getKeyState(down))
		{
			if (!ArenaRush.player.isCollidingUp())
				ArenaRush.player.setVelY((-speed * boostModifier));
		}
		if (keyCode == boost && !KeyboardHandler.getKeyState(up) && KeyboardHandler.getKeyState(down))
		{
			if (!ArenaRush.player.isCollidingDown())
				ArenaRush.player.setVelY((speed * boostModifier));
		}
		
		if (keyCode == KeyEvent.VK_ESCAPE)
		{
			if (GameState.state == GameState.PLAY)
				GameState.switchToMainMenu();
		}
		
		if (keyCode == KeyEvent.VK_F1) // Show / hide GUI
		{
			if (ARGui.drawGui)
				ARGui.drawGui = false;
			else
				ARGui.drawGui = true;
		}
		
		if (keyCode == KeyEvent.VK_F6) // Save world
		{
			WorldHandler.currentWorld.generateWorldFile("res/worlds/", "testWorldRaw.world");
		}
		
		if (keyCode == KeyEvent.VK_F7) // Toggle player canPathTo
		{
			if (ArenaRush.player.canPathTo)
				ArenaRush.player.canPathTo = false;
			else
				ArenaRush.player.canPathTo = true;
		}
		
		if (keyCode == KeyEvent.VK_F8) // Start / stop rendering
		{
			if (ARClock.render)
				ARClock.render = false;
			else if (!ARClock.render)
				ARClock.render = true;
		}
		
		if (keyCode == KeyEvent.VK_F9) // Start / stop updating
		{
			if (ARClock.update)
				ARClock.update = false;
			else if (!ARClock.update)
				ARClock.update = true;
		}
		
		if (keyCode == one)
		{
			ArenaRush.player.weapon = 0;
			ARGui.updateWeaponImage();
		}
		
		if (keyCode == two)
		{
			ArenaRush.player.weapon = 1;
			ARGui.updateWeaponImage();
		}
		
		if (keyCode == three)
		{
			ArenaRush.player.weapon = 2;
			ARGui.updateWeaponImage();
		}
	}
	
	/**
	 * Called when a button is clicked.
	 * @param button
	 */
	public static void mouseClicked(int button)
	{
		if (GameState.state != GameState.PLAY) return;
		
		if (button == 1) // LMB
		{
			
		}
	}
	
	/**
	 * Called when a button is released.
	 * @param button
	 */
	public static void mouseReleased(int button)
	{
		if (GameState.state != GameState.PLAY) return;
			
		if (button == 1) // LMB
		{
			Projectile proj = null;
			
			if (ProjectileRegistry.getProjectile(ArenaRush.player.weapon) instanceof BasicBullet)
				proj = new BasicBullet(ProjectileRegistry.getProjectile(ArenaRush.player.weapon));
			if (ProjectileRegistry.getProjectile(ArenaRush.player.weapon) instanceof RubberBullet)
				proj = new RubberBullet(ProjectileRegistry.getProjectile(ArenaRush.player.weapon));
			if (ProjectileRegistry.getProjectile(ArenaRush.player.weapon) instanceof TriShotBasic)
				proj = new TriShotBasic(ProjectileRegistry.getProjectile(ArenaRush.player.weapon));
			
			if (proj == null)
				return;
			
			proj.fire((float) ((ArenaRush.player.getPosX() + (ArenaRush.player.width / 2) - proj.width / 2) + 
					(20 * Math.cos(Math.toRadians(ArenaRush.player.direction)))), 
					(float) ((ArenaRush.player.getPosY() + (ArenaRush.player.height / 2) - proj.width / 2) + 
					(20 * Math.sin(Math.toRadians(ArenaRush.player.direction)))), 
					ArenaRush.player.getDirection(), 
					(float) (proj.getSpeed() * Math.cos(Math.toRadians(ArenaRush.player.getDirection()))), 
					(float) (proj.getSpeed() * Math.sin(Math.toRadians(ArenaRush.player.getDirection()))));
		}
	}
}
