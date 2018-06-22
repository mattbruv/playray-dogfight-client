package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.util.ImageUtils;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public class Coast
  extends DrawableEntity
{
  private int x;
  private int y;
  private int subType;
  private static BufferedImage[] image;
  
  public static void loadImages()
  {
    image = new BufferedImage[4];
    image[0] = ImageLoader.getImage("pictures/beach-l.gif");
    image[1] = ImageUtils.createHorizontallyFlipped(image[0]);
    image[2] = ImageLoader.getImage("pictures/beach-l_desert.gif");
    image[3] = ImageUtils.createHorizontallyFlipped(image[2]);
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
    paramGraphics2D.drawImage(image[this.subType], this.x + paramInt1, this.y + paramInt2, null);
    if (this.subType < 2) {
      paramGraphics2D.setColor(Water.COLOR);
    } else {
      paramGraphics2D.setColor(Water.DESERT_COLOR);
    }
    paramGraphics2D.fillRect(this.x + paramInt1, this.y + paramInt2 + image[this.subType].getHeight(), image[this.subType].getWidth(), 500);
  }
  
  public Integer getDrawLayer()
  {
    return LAYER10;
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
    return image[this.subType].getWidth();
  }
  
  protected int getHeight()
  {
    return image[this.subType].getHeight();
  }
}
