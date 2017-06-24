package com.binextechnologies.arenarush.pathing;

import com.binextechnologies.arenarush.math.Vector2f;

public class AStarNode
{
	public Vector2f pos;
	public AStarNode parent;
	public double fCost;
	public double gCost;
	public double hCost;
	
	public AStarNode(Vector2f pos, AStarNode parent, double gCost, double hCost)
	{
		this.pos = pos;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = hCost + gCost;
	}
}
