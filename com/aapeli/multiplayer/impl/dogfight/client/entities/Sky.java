package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightEntity;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.Drawable;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public class Sky
  extends DogfightEntity
  implements Drawable
{
  private int x;
  private int y;
  private static BufferedImage image;
  
  public static void loadImages()
  {
    image = ImageLoader.getImage("pictures/sky3b.jpg");
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
  
  public int getX()
  {
    return this.x;
  }
  
  public int getY()
  {
    return this.y;
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    int i = paramInt1 / 6 - this.x;
    paramInt2 /= 3;
    if (i < 0) {
      i = image.getWidth() - Math.abs(i) % image.getWidth();
    } else {
      i %= image.getWidth();
    }
    switch (getDogfightToolkit().getDrawQuality())
    {
    case 2: 
      paramGraphics2D.drawImage(image, i, this.y + paramInt2, null);
      paramGraphics2D.drawImage(image, i - image.getWidth(), this.y + paramInt2, null);
      break;
    case 1: 
      paramGraphics2D.setColor(new Color(14540287));
      paramGraphics2D.fillRect(i, this.y + paramInt2, image.getWidth(), image.getHeight());
      paramGraphics2D.fillRect(i - image.getWidth(), this.y + paramInt2, image.getWidth(), image.getHeight());
    }
  }
  
  public Integer getDrawLayer()
  {
    return LAYER17;
  }
  
  public boolean isInside(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return true;
  }
}
