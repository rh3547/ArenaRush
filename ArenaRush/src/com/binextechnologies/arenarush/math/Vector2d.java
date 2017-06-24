package com.binextechnologies.arenarush.math;

public class Vector2d
{
	public double x;
	public double y;
	
	public Vector2d(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2d(Vector2d vec)
	{
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public double length()
	{
		return Math.sqrt(x*x + y*y);
	}
	
	public double dot(Vector2d vec)
	{
		return (x * vec.getX()) + (y * vec.getY());
	}
	
	public Vector2d normalize()
	{
		double length = length();
		
		x /= length;
		y /= length;
		
		return this;
	}
	
	public Vector2d rotate(double angle)
	{
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		
		return new Vector2d((x * cos - y * sin), (x * sin + y * cos));
	}
	
	public boolean equals(Vector2d vec)
	{
		if (this.x == vec.x && this.y == vec.y)
			return true;
		
		return false;
	}
	
	public Vector2d add(Vector2d vec)
	{
		return new Vector2d(x + vec.getX(), y + vec.getY());
	}
	
	public Vector2d add(Vector2d vec1, Vector2d vec2)
	{
		return new Vector2d(x + vec1.getX() + vec2.getX(), y + vec1.getY() + vec2.getY());
	}
	
	public Vector2d add(Vector2d vec1, Vector2d vec2, Vector2d vec3)
	{
		return new Vector2d(x + vec1.getX() + vec2.getX() + vec3.getX(), y + vec1.getY() + vec2.getY() + vec3.getY());
	}
	
	public Vector2d add(double val)
	{
		return new Vector2d(x + val, y + val);
	}
	
	public Vector2d add(double val1, double val2)
	{
		return new Vector2d(x + val1 + val2, y + val1 + val2);
	}
	
	public Vector2d add(double val1, double val2, double val3)
	{
		return new Vector2d(x + val1 + val2 + val3, y + val1 + val2 + val3);
	}
	
	public Vector2d subtract(Vector2d vec)
	{
		return new Vector2d(x - vec.getX(), y - vec.getY());
	}
	
	public Vector2d subtract(double val)
	{
		return new Vector2d(x - val, y - val);
	}
	
	public Vector2d multiply(Vector2f vec)
	{
		return new Vector2d(x * vec.getX(), y * vec.getY());
	}
	
	public Vector2d multiply(double val)
	{
		return new Vector2d(x * val, y * val);
	}
	
	public Vector2d divide(Vector2f vec)
	{
		return new Vector2d(x / vec.getX(), y / vec.getY());
	}
	
	public Vector2d divide(double val)
	{
		return new Vector2d(x / val, y / val);
	}
	
	public String toString()
	{
		return "[ X: " + x + " Y: " + y + " ]";
	}

	/**
	 * @return the x
	 */
	public double getX()
	{
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x)
	{
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY()
	{
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y)
	{
		this.y = y;
	}
}