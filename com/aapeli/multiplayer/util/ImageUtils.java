package com.aapeli.multiplayer.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

public class ImageUtils
{
  private static boolean headless = false;
  
  public static void setHeadless(boolean paramBoolean)
  {
    headless = paramBoolean;
  }
  
  public static BufferedImage createCenteredInMaxRotateArea(BufferedImage paramBufferedImage)
  {
    int i = (int)Math.ceil(Math.sqrt(paramBufferedImage.getWidth() * paramBufferedImage.getWidth() + paramBufferedImage.getHeight() * paramBufferedImage.getHeight()));
    BufferedImage localBufferedImage = createCompatibleImage(i, i, 2);
    Graphics localGraphics = localBufferedImage.getGraphics();
    localGraphics.drawImage(paramBufferedImage, (i - paramBufferedImage.getWidth()) / 2, (i - paramBufferedImage.getHeight()) / 2, null);
    localGraphics.dispose();
    return localBufferedImage;
  }
  
  public static GraphicsConfiguration getGraphicsConfiguration()
  {
    GraphicsEnvironment localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice localGraphicsDevice = localGraphicsEnvironment.getDefaultScreenDevice();
    return localGraphicsDevice.getDefaultConfiguration();
  }
  
  public static boolean isHeadless()
  {
    return headless;
  }
  
  public static BufferedImage createCompatibleImage(int paramInt1, int paramInt2, int paramInt3)
  {
    if (isHeadless()) {
      return new BufferedImage(paramInt1, paramInt2, 2);
    }
    return getGraphicsConfiguration().createCompatibleImage(paramInt1, paramInt2, paramInt3);
  }
  
  public static VolatileImage createVolatileImage(int paramInt1, int paramInt2)
  {
    return getGraphicsConfiguration().createCompatibleVolatileImage(paramInt1, paramInt2);
  }
  
  public static VolatileImage createVolatileCopy(Image paramImage)
  {
    VolatileImage localVolatileImage = createVolatileImage(paramImage.getWidth(null), paramImage.getHeight(null));
    do
    {
      int i = localVolatileImage.validate(getGraphicsConfiguration());
      if (i == 2) {
        localVolatileImage = createVolatileImage(paramImage.getWidth(null), paramImage.getHeight(null));
      }
      Graphics localGraphics = localVolatileImage.getGraphics();
      localGraphics.drawImage(paramImage, 0, 0, null);
      localGraphics.dispose();
    } while (localVolatileImage.contentsLost());
    return localVolatileImage;
  }
  
  public static BufferedImage createCopy(Image paramImage)
  {
    BufferedImage localBufferedImage = createCompatibleImage(paramImage.getWidth(null), paramImage.getHeight(null), 2);
    Graphics2D localGraphics2D = localBufferedImage.createGraphics();
    localGraphics2D.drawImage(paramImage, 0, 0, null);
    localGraphics2D.dispose();
    return localBufferedImage;
  }
  
  public static BufferedImage createHorizontallyFlipped(BufferedImage paramBufferedImage)
  {
    AffineTransform localAffineTransform = new AffineTransform();
    localAffineTransform.scale(-1.0D, 1.0D);
    localAffineTransform.translate(-paramBufferedImage.getWidth(), 0.0D);
    AffineTransformOp localAffineTransformOp = new AffineTransformOp(localAffineTransform, 1);
    return createCopy(localAffineTransformOp.filter(paramBufferedImage, null));
  }
  
  public static BufferedImage createWhiteMask(BufferedImage paramBufferedImage)
  {
    BufferedImage localBufferedImage = createCompatibleImage(paramBufferedImage.getWidth(null), paramBufferedImage.getHeight(null), 2);
    Graphics2D localGraphics2D = localBufferedImage.createGraphics();
    localGraphics2D.drawImage(paramBufferedImage, 0, 0, null);
    Composite localComposite = localGraphics2D.getComposite();
    localGraphics2D.setComposite(AlphaComposite.SrcAtop);
    localGraphics2D.setColor(Color.white);
    localGraphics2D.fillRect(0, 0, paramBufferedImage.getWidth(null), paramBufferedImage.getHeight(null));
    localGraphics2D.setComposite(localComposite);
    localGraphics2D.dispose();
    return localBufferedImage;
  }
}
