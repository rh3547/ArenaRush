package com.binextechnologies.arenarush.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class ARToolkit
{
	public static String getOS()
	{
		String OS;
		
		OS = System.getProperty("os.name", "generic").toLowerCase();
		
		if (OS.startsWith("windows")) 
		{
			OS = "windows";
		} 
		else if (OS.startsWith("linux")) 
		{
			OS = "linux";
		} 
		else if (OS.startsWith("sunos")) 
		{
			OS = "solaris";
		} 
		else if (OS.startsWith("mac") || OS.startsWith("darwin")) 
		{
			OS = "mac";
		} 
		else if (OS.startsWith("aix")) 
		{
			OS = "aix";
		} 
		else 
			OS = "generic";
		
		return OS;
	}
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D g2d = bimage.createGraphics();
	    g2d.drawImage(img, 0, 0, null);
	    g2d.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
	/**
	 * <h1>Simple Method</h1>
	 * Scales an image to the given size.
	 * Does not use the ImgSclr library.
	 * @param originalImage
	 * @param width
	 * @param height
	 * @return the scaled image as a BufferedImage
	 */
	public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height)
	{
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
	 
		return resizedImage;
	}
	
	/**
	 * <h1>Good Method</h1>
	 * Scales an image to the given size and sets quality rendering hints.
	 * Does not use the ImgSclr library.
	 * @param originalImage
	 * @param width
	 * @param height
	 * @return the scaled image as a BufferedImage
	 */
    public static BufferedImage resizeImageWithHint(BufferedImage originalImage, int width, int height)
    {
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();	
		g.setComposite(AlphaComposite.Src);
	 
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
		RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
	 
		return resizedImage;
   }
    
    /**
     * Get the graphics configuration for the screen.
     * @return GraphicsConfiguration
     */
    public static GraphicsConfiguration getGraphicsConfiguration() 
    {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }

    /**
     * Create a compatible image.
     * @param width
     * @param height
     * @param transparency
     * @return BufferedImage
     */
    public static BufferedImage createCompatibleImage(int width, int height, int transparency) 
    {
        BufferedImage image = getGraphicsConfiguration().createCompatibleImage(width, height, transparency);
        image.coerceData(true);
        return image;
    }

    /**
     * Applies various quality rendering hints to the given Graphics2D.
     * @param g2d
     */
    public static void applyQualityRenderingHints(Graphics2D g2d) 
    {
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    /**
     * Generate a mask image based on the given
     * image with the given color and transparency.
     * @param imgSource - the original image
     * @param color - the color of the new mask
     * @param alpha - the transparency of the new mask
     * @return BufferedImage - the new image mask
     */
    public static BufferedImage generateMask(BufferedImage imgSource, Color color, float alpha) 
    {
        int imgWidth = imgSource.getWidth();
        int imgHeight = imgSource.getHeight();

        BufferedImage imgMask = createCompatibleImage(imgWidth, imgHeight, Transparency.TRANSLUCENT);
        Graphics2D g2 = imgMask.createGraphics();
        applyQualityRenderingHints(g2);

        g2.drawImage(imgSource, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
        g2.setColor(color);

        g2.fillRect(0, 0, imgSource.getWidth(), imgSource.getHeight());
        g2.dispose();

        return imgMask;
    }

    /**
     * Create a new image that is a combination
     * of the given master image and the given
     * tint image.
     * @param master - the master image
     * @param tint - the tint image to be drawn over the master image
     * @return BufferedImage - the tinted image
     */
    public static BufferedImage tint(BufferedImage master, BufferedImage tint) 
    {
        int imgWidth = master.getWidth();
        int imgHeight = master.getHeight();

        BufferedImage tinted = createCompatibleImage(imgWidth, imgHeight, Transparency.TRANSLUCENT);
        Graphics2D g2 = tinted.createGraphics();
        applyQualityRenderingHints(g2);
        g2.drawImage(master, 0, 0, null);
        g2.drawImage(tint, 0, 0, null);
        g2.dispose();

        return tinted;
    }
}
