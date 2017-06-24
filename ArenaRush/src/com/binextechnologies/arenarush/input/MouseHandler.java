package com.binextechnologies.arenarush.input;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import com.binextechnologies.arenarush.player.PlayerController;

public class MouseHandler implements MouseInputListener
{
	@Override
	public void mouseClicked(MouseEvent e)
	{
		PlayerController.mouseClicked(e.getButton());
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		PlayerController.mouseReleased(e.getButton());
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		
	}
}