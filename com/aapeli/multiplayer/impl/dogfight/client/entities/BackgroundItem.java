package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.util.ImageUtils;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public class BackgroundItem
  extends DrawableEntity
{
  private int x;
  private int y;
  private int subType;
  private static BufferedImage[] image;
  
  public static void loadImages()
  {
    image = new BufferedImage[6];
    image[0] = ImageLoader.getImage("pictures/controlTower.gif");
    image[1] = ImageUtils.createHorizontallyFlipped(image[0]);
    image[2] = ImageLoader.getImage("pictures/controlTowerDesert.gif");
    image[3] = ImageUtils.createHorizontallyFlipped(image[2]);
    image[4] = ImageLoader.getImage("pictures/palmtree.gif");
    image[5] = ImageUtils.createHorizontallyFlipped(image[4]);
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
  
  protected int getX()
  {
    return this.x - getWidth() / 2;
  }
  
  protected int getY()
  {
    return this.y - getHeight();
  }
  
  protected int getWidth()
  {
    return image[this.subType].getWidth();
  }
  
  protected int getHeight()
  {
    return image[this.subType].getHeight();
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    paramGraphics2D.drawImage(image[this.subType], this.x + paramInt1 - image[this.subType].getWidth() / 2, this.y + paramInt2 - image[this.subType].getHeight(), null);
  }
  
  public Integer getDrawLayer()
  {
    return LAYER15;
  }
}
