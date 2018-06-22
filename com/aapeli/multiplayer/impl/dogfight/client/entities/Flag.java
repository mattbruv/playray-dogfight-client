package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.common.resources.ImageLoader;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public class Flag
  extends DrawableEntity
  implements Runnable
{
  private int x;
  private int y;
  private int team;
  private static BufferedImage[] image;
  private int timer;
  private static final int FRAME_TIME = 8;
  
  public static void loadImages()
  {
    image = new BufferedImage[6];
    image[0] = ImageLoader.getImage("pictures/flag_ger_1.gif");
    image[1] = ImageLoader.getImage("pictures/flag_ger_2.gif");
    image[2] = ImageLoader.getImage("pictures/flag_ger_3.gif");
    image[3] = ImageLoader.getImage("pictures/flag_raf_1.gif");
    image[4] = ImageLoader.getImage("pictures/flag_raf_2.gif");
    image[5] = ImageLoader.getImage("pictures/flag_raf_3.gif");
  }
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 2: 
      this.x = paramDataInput.readShort();
      this.y = paramDataInput.readShort();
      this.team = paramDataInput.readByte();
      break;
    }
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    int i = this.timer / 8;
    if (i == 3) {
      i = 1;
    }
    paramGraphics2D.drawImage(image[(this.team * 3 + i)], this.x + paramInt1, this.y + paramInt2, null);
  }
  
  public Integer getDrawLayer()
  {
    return LAYER15;
  }
  
  public void run()
  {
    this.timer = ((this.timer + 1) % 32);
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
    return image[(this.team * 3)].getWidth();
  }
  
  protected int getHeight()
  {
    return image[(this.team * 3)].getHeight();
  }
}
