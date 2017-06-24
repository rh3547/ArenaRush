package com.binextechnologies.arenarush.gameobject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.binextechnologies.arenarush.math.Vector2f;
import com.binextechnologies.arenarush.world.WorldHandler;

public class BoidSwarm extends GameObject
{
	private static final long serialVersionUID = -3144893194563831348L;

	public ArrayList<Boid> boids = new ArrayList<Boid>();
	
	public int numBoids;
    public int velocityLimit;
    public int radiusLimit;
    public int flockRadius;
    
	public BoidSwarm(int objectId, int numBoids, int velocityLimit, int radiusLimit, int flockRadius)
	{
		super(objectId);
		
		this.numBoids = numBoids;
		this.velocityLimit = velocityLimit;
		this.radiusLimit = radiusLimit;
		this.flockRadius = flockRadius;
		
		this.width = 8;
		this.height = 8;
		
		initBoids();
	}
	
	public BoidSwarm(int objectId, int numBoids, int velocityLimit, int radiusLimit, int flockRadius, float posX, float posY)
	{
		super(objectId);
		
		this.numBoids = numBoids;
		this.velocityLimit = velocityLimit;
		this.radiusLimit = radiusLimit;
		this.flockRadius = flockRadius;
		this.posX = posX;
		this.posY = posY;
		
		this.width = 8;
		this.height = 8;
		
		initBoids();
	}
	
    public void initBoids() 
    {
        for (int x = 0; x < numBoids; x++) 
        {
        	Vector2f vec = randomizeBoidPos();
            boids.add((Boid) new Boid(vec.x, vec.y).setCollides(true));
        }
    }
	
	@Override
	public void update()
	{
		super.update();
		
		for (int x = 0; x < boids.size(); x ++)
		{
			updateBoidWithBounds(boids.get(x));
			boids.get(x).update();
		}
	}
	
	@Override
	public void render(Graphics g, Graphics2D g2d)
	{
		for (int x = 0; x < boids.size(); x ++)
		{
			boids.get(x).render(g, g2d);
		}
	}
	
	@Override
	public void checkCollisions()
	{
		
	}
	
	public Vector2f randomizeBoidPos()
	{
		Random rand = new Random();
		
		return new Vector2f(rand.nextInt(WorldHandler.currentWorld.mapWidth), rand.nextInt(WorldHandler.currentWorld.mapHeight));
	}
	
    private void updateBoidWithBounds(Boid thisBoid) 
    {
    	Vector2f velVec = new Vector2f(thisBoid.velX, thisBoid.velY);
    	
    	velVec.add(rule1(thisBoid), rule2(thisBoid), rule3(thisBoid));
    	
    	thisBoid.velX = velVec.x;
    	thisBoid.velY = velVec.y;
    	
        limitVelocity(thisBoid);
    }
	
	  /**
     * boids try to fly towards the centre of mass of neighbouring boids
     * 
     * @return resulting vector
     */
    private Vector2f rule1(Boid thisBoid)
    {
        Vector2f center = new Vector2f(0, 0);
        List<Boid> flock = flock(thisBoid);
        
        for (Boid boid : flock) 
        {
            if (!boid.equals(this)) 
            {
                center.add(new Vector2f(boid.posX, boid.posY));
            }
        }
        
        center.divide(flock.size() - 1);
        Vector2f result = new Vector2f(center);
        result.subtract(new Vector2f(thisBoid.posX, thisBoid.posY));
        result.divide(100);
        
        return result;
    }

    /**
     * try to keep a small distance away from other objects
     * 
     * @return
     */
    private Vector2f rule2(Boid thisBoid) 
    {
        Vector2f result = new Vector2f(0, 0);
        
        for (Boid boid : flock(thisBoid)) 
        {
            if (!boid.equals(this)) 
            {
                if (thisBoid.isClose(new Vector2f(boid.posX, boid.posY), radiusLimit)) 
                {
                    Vector2f sub = new Vector2f(new Vector2f(boid.posX, boid.posY));
                    sub.subtract(new Vector2f(thisBoid.posX, thisBoid.posY));
                    result.subtract(sub);
                }
            }
        }
        
        return result;
    }

    /**
     * boids try to match velocity with near boids
     * 
     * @return
     */
    private Vector2f rule3(Boid thisBoid) 
    {
        Vector2f pvj = new Vector2f(0, 0);
        List<Boid> flock = flock(thisBoid);
        
        for (Boid boid : flock) 
        {
            if (!boid.equals(this)) 
            {
                pvj.add(new Vector2f(boid.velX, boid.velY));
            }
        }
        
        pvj.divide(flock.size() - 1);
        pvj.subtract(new Vector2f(thisBoid.velX, thisBoid.velY));
        
        return pvj;
    }
    
    public void limitVelocity(Boid thisBoid) 
    {
        if (new Vector2f(thisBoid.velX, thisBoid.velY).length() > velocityLimit) 
        {
        	Vector2f velVec = new Vector2f(thisBoid.velX, thisBoid.velY);
        	
        	velVec.divide(new Vector2f(thisBoid.velX, thisBoid.velY).length());
        	velVec.multiply(velocityLimit);
        	
        	thisBoid.velX = velVec.x;
        	thisBoid.velY = velVec.y;
        }
    }

    public List<Boid> flock(Boid thisBoid) 
    {
        List<Boid> flock = new LinkedList<Boid>();
        
        for (Boid boid : boids) 
        {
            if (thisBoid.isInFlock(new Vector2f(boid.posX, boid.posY), flockRadius)) 
            {
                flock.add(boid);
            }
        }
        
        return flock;
    }

	/**
	 * @return the boids
	 */
	public ArrayList<Boid> getBoids()
	{
		return boids;
	}

	/**
	 * @param boids the boids to set
	 */
	public void setBoids(ArrayList<Boid> boids)
	{
		this.boids = boids;
	}

	/**
	 * @return the numBoids
	 */
	public int getNumBoids()
	{
		return numBoids;
	}

	/**
	 * @param numBoids the numBoids to set
	 */
	public void setNumBoids(int numBoids)
	{
		this.numBoids = numBoids;
	}

	/**
	 * @return the velocityLimit
	 */
	public int getVelocityLimit()
	{
		return velocityLimit;
	}

	/**
	 * @param velocityLimit the velocityLimit to set
	 */
	public void setVelocityLimit(int velocityLimit)
	{
		this.velocityLimit = velocityLimit;
	}

	/**
	 * @return the radiusLimit
	 */
	public int getRadiusLimit()
	{
		return radiusLimit;
	}

	/**
	 * @param radiusLimit the radiusLimit to set
	 */
	public void setRadiusLimit(int radiusLimit)
	{
		this.radiusLimit = radiusLimit;
	}

	/**
	 * @return the flockRadius
	 */
	public int getFlockRadius()
	{
		return flockRadius;
	}

	/**
	 * @param flockRadius the flockRadius to set
	 */
	public void setFlockRadius(int flockRadius)
	{
		this.flockRadius = flockRadius;
	}
}
