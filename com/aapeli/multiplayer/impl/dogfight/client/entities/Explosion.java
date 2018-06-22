package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.resources.SoundSystem;
import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightToolkit;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public class Explosion
  extends DrawableEntity
{
  protected volatile int x;
  protected volatile int y;
  protected volatile int phase;
  protected static BufferedImage[] image;
  
  public static void loadImages()
  {
    image = new BufferedImage[8];
    for (int i = 0; i < image.length; i++) {
      image[i] = ImageLoader.getImage("pictures/explosion000" + (i + 1) + ".gif");
    }
  }
  
  public Explosion()
  {
    SoundSystem.getInstance().play("sounds/explosion.au");
  }
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 2: 
      this.x = paramDataInput.readShort();
      this.y = paramDataInput.readShort();
      this.phase = (paramDataInput.readByte() & 0xFF);
      break;
    case 0: 
    case 1: 
      this.phase = (paramDataInput.readByte() & 0xFF);
    }
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    drawAt(paramGraphics2D, this.x + paramInt1, this.y + paramInt2);
  }
  
  public void drawAt(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    switch (getDogfightToolkit().getDrawQuality())
    {
    case 2: 
      paramGraphics2D.drawImage(image[this.phase], paramInt1 - image[0].getWidth() / 2, paramInt2 - image[0].getHeight() / 2, null);
      break;
    case 1: 
      paramGraphics2D.setColor(Color.white);
      paramGraphics2D.fillOval(paramInt1 - image[0].getWidth() / 2, paramInt2 - image[0].getHeight() / 2, image[this.phase].getWidth(), image[this.phase].getHeight());
    }
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
    return image[0].getWidth();
  }
  
  protected int getHeight()
  {
    return image[0].getHeight();
  }
}
