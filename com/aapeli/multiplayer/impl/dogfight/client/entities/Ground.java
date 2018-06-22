package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightToolkit;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public class Ground
  extends DrawableEntity
{
  private int x;
  private int y;
  private int width;
  private int subType;
  private static BufferedImage[] image;
  
  public static void loadImages()
  {
    image = new BufferedImage[2];
    image[0] = ImageLoader.getImage("pictures/ground1.gif");
    image[1] = ImageLoader.getImage("pictures/groundDesert.gif");
  }
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 2: 
      this.x = paramDataInput.readShort();
      this.y = paramDataInput.readShort();
      this.width = paramDataInput.readShort();
      this.subType = paramDataInput.readByte();
      break;
    }
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    DogfightToolkit localDogfightToolkit = (DogfightToolkit)this.toolkit.getAttachment();
    int i = localDogfightToolkit.getWidth();
    int j = localDogfightToolkit.getHeight();
    Shape localShape = paramGraphics2D.getClip();
    int k = 0;
    if (this.x + paramInt1 > 0) {
      k = this.x + paramInt1;
    }
    int m = this.x + paramInt1 + this.width - k;
    if (k + m > i) {
      m = i - k;
    }
    if (m > 0)
    {
      paramGraphics2D.setClip(new Rectangle(k, this.y + paramInt2, m, j - this.y - paramInt2));
      if (this.subType == 0) {
        paramGraphics2D.setColor(Water.COLOR);
      } else {
        paramGraphics2D.setColor(Water.DESERT_COLOR);
      }
      paramGraphics2D.fillRect(k, this.y + paramInt2 + image[this.subType].getHeight(), m, j - this.y - paramInt2);
      int n = image[this.subType].getWidth();
      for (int i1 = (k - (this.x + paramInt1)) / n; this.x + paramInt1 + i1 * n + 1 < k + m; i1++) {
        paramGraphics2D.drawImage(image[this.subType], i1 * n + this.x + paramInt1, this.y + paramInt2, null);
      }
      paramGraphics2D.setClip(localShape);
    }
  }
  
  public Integer getDrawLayer()
  {
    return LAYER14;
  }
  
  public int getX()
  {
    return this.x;
  }
  
  public int getY()
  {
    return this.y;
  }
  
  public int getWidth()
  {
    return this.width;
  }
  
  protected int getHeight()
  {
    return image[this.subType].getHeight();
  }
}
