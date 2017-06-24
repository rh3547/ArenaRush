package com.binextechnologies.arenarush.main;

import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.binextechnologies.arenarush.graphics.ARCanvas;
import com.binextechnologies.arenarush.input.KeyboardHandler;
import com.binextechnologies.arenarush.input.MouseHandler;
import com.binextechnologies.arenarush.profiles.ProfileHandler;

public class Core 
{
	/*
	 * Game Variables
	 */
	public String title;
	public int windowWidth;
	public int windowHeight;
	public int tps;
	public int fps;
	public double mouseX = 0;
	public double mouseY = 0;
	public double worldMouseX = 0;
	public double worldMouseY = 0;
	
	/*
	 * Java Components
	 */
	public JFrame frame = new JFrame();
	
	/*
	 * Game Components
	 */
	public ARCanvas canvas; // The canvas that renders graphics
	public ARClock clock; // The clock that handles updating
	
	public Core(String title, int windowWidth, int windowHeight, int tps, int fps)
	{
		this.title = title;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.tps = tps;
		this.fps = fps;
		
		frame.addWindowListener(new WindowListener()
		{

			@Override
			public void windowActivated(WindowEvent e)
			{
				
			}

			@Override
			public void windowClosed(WindowEvent e)
			{
				
			}

			@Override
			public void windowClosing(WindowEvent e)
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
			}

			@Override
			public void windowDeactivated(WindowEvent e)
			{
				
			}

			@Override
			public void windowDeiconified(WindowEvent e)
			{
				
			}

			@Override
			public void windowIconified(WindowEvent e)
			{
				
			}

			@Override
			public void windowOpened(WindowEvent e)
			{
				
			}
		});
		frame.setIconImage(new ImageIcon("res/icon.png").getImage());
		frame.setTitle(title);
		frame.setSize(windowWidth, windowHeight);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setFocusable(false);
		
		createCanvas();
		
		clock = new ARClock();
	}
	
	public void createCanvas()
	{
		KeyboardHandler keyHandler = new KeyboardHandler();
		MouseHandler mouseHandler = new MouseHandler();
		
		canvas = new ARCanvas();
		canvas.setBounds(0, 0, windowWidth, windowHeight);
		canvas.addMouseListener(mouseHandler);
		canvas.addMouseMotionListener(mouseHandler);
		canvas.addKeyListener(keyHandler);
		
		frame.add(canvas);
	}
	
	/**
	 * Start the game by starting the clock and showing the frame.
	 */
	public void startGame()
	{
		clock.start();
		ARClock.startClock();
		
		frame.setVisible(true);
	}
	
	/**
	 * An update method to perform various tasks.
	 */
	public void update()
	{
		try
		{
			Point ogPoint = new Point(canvas.getMousePosition().x, canvas.getMousePosition().y);
			Point point = new Point((int) (-ArenaRush.camera.getPosX() + ogPoint.getX()), (int) (-ArenaRush.camera.getPosY() + ogPoint.getY()));
		
			setWorldMouseX(point.getX());
			setWorldMouseY(point.getY());
		
			setMouseX(ogPoint.getX());
			setMouseY(ogPoint.getY());
		}
		catch (NullPointerException e)
		{
			
		}
	}

	/**
	 * @return the mouseX
	 */
	public double getMouseX()
	{
		return mouseX;
	}

	/**
	 * @param mouseX the mouseX to set
	 */
	public void setMouseX(double mouseX)
	{
		this.mouseX = mouseX;
	}

	/**
	 * @return the mouseY
	 */
	public double getMouseY()
	{
		return mouseY;
	}

	/**
	 * @param mouseY the mouseY to set
	 */
	public void setMouseY(double mouseY)
	{
		this.mouseY = mouseY;
	}

	/**
	 * @return the worldMouseX
	 */
	public double getWorldMouseX()
	{
		return worldMouseX;
	}

	/**
	 * @param worldMouseX the worldMouseX to set
	 */
	public void setWorldMouseX(double worldMouseX)
	{
		this.worldMouseX = worldMouseX;
	}

	/**
	 * @return the worldMouseY
	 */
	public double getWorldMouseY()
	{
		return worldMouseY;
	}

	/**
	 * @param worldMouseY the worldMouseY to set
	 */
	public void setWorldMouseY(double worldMouseY)
	{
		this.worldMouseY = worldMouseY;
	}
}