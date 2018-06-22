package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.resources.SoundSystem;
import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightToolkit;
import com.aapeli.multiplayer.util.ImageUtils;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public class Bomb
  extends DrawableEntity
{
  private static final int DIRECTIONS = 256;
  private static final int IMAGE_DIRECTIONS = 64;
  private volatile int x;
  private volatile int y;
  private static BufferedImage[] image;
  private volatile int direction;
  
  public static void loadImages()
  {
    BufferedImage localBufferedImage = ImageLoader.getRotatedImage("pictures/bomb.gif");
    image = createRotatedInstances(localBufferedImage);
  }
  
  public Bomb()
  {
    SoundSystem.getInstance().play("sounds/bombdrop.au");
  }
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 1: 
    case 2: 
      int i = this.x;
      int j = this.y;
      this.x = paramDataInput.readShort();
      this.y = paramDataInput.readShort();
      this.direction = (paramDataInput.readByte() & 0xFF);
      break;
    case 0: 
      int k = paramDataInput.readByte();
      this.x += k;
      this.y += paramDataInput.readByte();
      this.direction = (paramDataInput.readByte() & 0xFF);
    }
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    drawAt(paramGraphics2D, this.x + paramInt1, this.y + paramInt2);
  }
  
  public Integer getDrawLayer()
  {
    return LAYER11;
  }
  
  public void drawAt(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    switch (getDogfightToolkit().getDrawQuality())
    {
    case 1: 
    case 2: 
      paramGraphics2D.drawImage(image[(this.direction * image.length / 256)], paramInt1, paramInt2, null);
    }
  }
  
  private double getAngle()
  {
    return 6.283185307179586D * this.direction / 256.0D;
  }
  
  protected static BufferedImage createRotatedInstance(BufferedImage paramBufferedImage, double paramDouble)
  {
    AffineTransform localAffineTransform = AffineTransform.getRotateInstance(paramDouble, paramBufferedImage.getWidth() / 2, paramBufferedImage.getHeight() / 2);
    AffineTransformOp localAffineTransformOp = new AffineTransformOp(localAffineTransform, 1);
    BufferedImage localBufferedImage = ImageUtils.createCompatibleImage(paramBufferedImage.getWidth(), paramBufferedImage.getHeight(), 2);
    localAffineTransformOp.filter(paramBufferedImage, localBufferedImage);
    return localBufferedImage;
  }
  
  protected static BufferedImage[] createRotatedInstances(BufferedImage paramBufferedImage)
  {
    BufferedImage[] arrayOfBufferedImage = new BufferedImage[64];
    for (int i = 0; i < arrayOfBufferedImage.length; i++) {
      arrayOfBufferedImage[i] = createRotatedInstance(paramBufferedImage, i * 3.141592653589793D * 2.0D / arrayOfBufferedImage.length);
    }
    return arrayOfBufferedImage;
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
