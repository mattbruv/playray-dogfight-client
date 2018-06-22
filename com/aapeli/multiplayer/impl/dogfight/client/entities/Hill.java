package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightEntity;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.Drawable;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public class Hill
  extends DogfightEntity
  implements Drawable
{
  private int x;
  private int y;
  private static BufferedImage[] image;
  private int subType;
  
  public static void loadImages()
  {
    image = new BufferedImage[2];
    image[0] = ImageLoader.getImage("pictures/hill1.gif");
    image[1] = ImageLoader.getImage("pictures/sandhill.gif");
  }
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 2: 
      this.x = paramDataInput.readShort();
      this.y = paramDataInput.readShort();
      this.subType = paramDataInput.readByte();
      break;
    }
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    switch (getDogfightToolkit().getDrawQuality())
    {
    case 2: 
      paramInt1 = paramInt1 * 8 / 10;
      paramInt2 = paramInt2 * 9 / 10;
      paramGraphics2D.drawImage(image[this.subType], this.x + paramInt1 - image[this.subType].getWidth() / 2, this.y + paramInt2, null);
      break;
    }
  }
  
  public Integer getDrawLayer()
  {
    return LAYER16;
  }
  
  public boolean isInside(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return true;
  }
}
