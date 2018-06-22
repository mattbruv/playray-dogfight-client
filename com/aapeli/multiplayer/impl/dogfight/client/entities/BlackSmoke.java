package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.impl.dogfight.client.Drawable;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BlackSmoke
  extends Smoke
  implements Drawable
{
  protected volatile int x;
  protected volatile int y;
  protected static BufferedImage image;
  
  public static void loadImages()
  {
    image = ImageLoader.getRotatedImage("pictures/smoke2.gif");
  }
  
  public void drawAt(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    paramGraphics2D.drawImage(image, paramInt1 - image.getWidth() / 2, paramInt2 - image.getHeight() / 2, null);
  }
}
