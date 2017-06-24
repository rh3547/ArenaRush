package com.binextechnologies.arenarush.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.binextechnologies.arenarush.main.ArenaRush;
import com.binextechnologies.arenarush.main.DebugInfo;
import com.binextechnologies.arenarush.player.PlayerController;

public class KeyboardHandler implements KeyListener
{
	public static boolean[] keys = new boolean[256];
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		
		keys[keyCode] = true;
		
		PlayerController.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_F3)
		{
			if (!ArenaRush.debug)
				ArenaRush.debug = true;
			else if (ArenaRush.debug)
				ArenaRush.debug = false;
		}
		
		if (ArenaRush.debug)
		{
			if (keyCode == KeyEvent.VK_I)
			{
				if (!DebugInfo.drawInfo)
					DebugInfo.drawInfo = true;
				else if (DebugInfo.drawInfo)
					DebugInfo.drawInfo = false;
			}
		}
		
		keys[keyCode] = false;
		
		PlayerController.keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	public static boolean getKeyState(int keyCode)
	{
		return keys[keyCode];
	}
}