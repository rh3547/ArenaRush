package com.binextechnologies.arenarush.math;

import java.io.Serializable;

public class Vector2f implements Serializable
{
	private static final long serialVersionUID = -8097674808033541352L;
	
	public float x;
	public float y;
	
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(Vector2f vec)
	{
		this.x = vec.x;
		this.y = vec.y;
	}
	

	
	public float length()
	{
		return (float)Math.sqrt(x*x + y*y);
	}
	
	public float dot(Vector2f vec)
	{
		return (x * vec.getX()) + (y * vec.getY());
	}
	
	public Vector2f normalize()
	{
		float length = length();
		
		x /= length;
		y /= length;
		
		return this;
	}
	
	public Vector2f rotate(float angle)
	{
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		
		return new Vector2f((float)(x * cos - y * sin), (float)(x * sin + y * cos));
	}
	
	public boolean equals(Vector2f vec)
	{
		if (this.x == vec.x && this.y == vec.y)
			return true;
		
		return false;
	}
	
	public Vector2f add(Vector2f vec)
	{
		return new Vector2f(x + vec.getX(), y + vec.getY());
	}
	
	public Vector2f add(Vector2f vec1, Vector2f vec2)
	{
		return new Vector2f(x + vec1.getX() + vec2.getX(), y + vec1.getY() + vec2.getY());
	}
	
	public Vector2f add(Vector2f vec1, Vector2f vec2, Vector2f vec3)
	{
		return new Vector2f(x + vec1.getX() + vec2.getX() + vec3.getX(), y + vec1.getY() + vec2.getY() + vec3.getY());
	}
	
	public Vector2f add(float val)
	{
		return new Vector2f(x + val, y + val);
	}
	
	public Vector2f add(float val1, float val2)
	{
		return new Vector2f(x + val1 + val2, y + val1 + val2);
	}
	
	public Vector2f add(float val1, float val2, float val3)
	{
		return new Vector2f(x + val1 + val2 + val3, y + val1 + val2 + val3);
	}
	
	public Vector2f subtract(Vector2f vec)
	{
		return new Vector2f(x - vec.getX(), y - vec.getY());
	}
	
	public Vector2f subtract(float val)
	{
		return new Vector2f(x - val, y - val);
	}
	
	public Vector2f multiply(Vector2f vec)
	{
		return new Vector2f(x * vec.getX(), y * vec.getY());
	}
	
	public Vector2f multiply(float val)
	{
		return new Vector2f(x * val, y * val);
	}
	
	public Vector2f divide(Vector2f vec)
	{
		return new Vector2f(x / vec.getX(), y / vec.getY());
	}
	
	public Vector2f divide(float val)
	{
		return new Vector2f(x / val, y / val);
	}
	
	@Override
	public String toString()
	{
		return "[ X: " + x + " Y: " + y + " ]";
	}

	/**
	 * @return the x
	 */
	public float getX()
	{
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(float x)
	{
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY()
	{
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(float y)
	{
		this.y = y;
	}
}

