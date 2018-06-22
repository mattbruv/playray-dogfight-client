package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.common.resources.ImageLoader;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public class Smoke
  extends DrawableEntity
{
  protected volatile int x;
  protected volatile int y;
  protected static BufferedImage image;
  
  public static void loadImages()
  {
    image = ImageLoader.getImage("pictures/smoke1.gif");
  }
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 2: 
      this.x = paramDataInput.readShort();
      this.y = paramDataInput.readShort();
      break;
    }
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    drawAt(paramGraphics2D, this.x + paramInt1, this.y + paramInt2);
  }
  
  public void drawAt(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    paramGraphics2D.drawImage(image, paramInt1 - image.getWidth() / 2, paramInt2 - image.getHeight() / 2, null);
  }
  
  public Integer getDrawLayer()
  {
    return LAYER11;
  }
  
  protected int getX()
  {
    return this.x;
  }
  
  protected int getY()
  {
    return this.y;
  }
  
  protected int getWidth()
  {
    return image.getWidth();
  }
  
  protected int getHeight()
  {
    return image.getHeight();
  }
}
