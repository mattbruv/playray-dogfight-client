package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.impl.dogfight.client.Followable;
import com.aapeli.multiplayer.impl.dogfight.client.RadarObject;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public class Man
  extends DrawableEntity
  implements Followable, RadarObject
{
  private int x;
  private int y;
  private static BufferedImage[] image;
  private int mode;
  private int frame;
  private long frameTimer;
  private static final long frameDelay = 100L;
  private int team = -1;
  private int playerInfoId;
  private boolean drawFg = false;
  private static final int FALLING = 0;
  private static final int PARACHUTE = 1;
  private static final int STANDING = 2;
  private static final int WALKING_LEFT = 3;
  private static final int WALKING_RIGHT = 4;
  
  public static void loadImages()
  {
    image = new BufferedImage[4];
    image[0] = ImageLoader.getImage("pictures/parachuter0.gif");
    image[1] = ImageLoader.getImage("pictures/parachuter1.gif");
    image[2] = ImageLoader.getImage("pictures/parachuter2.gif");
    image[3] = ImageLoader.getImage("pictures/parachuter3.gif");
  }
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 2: 
      this.team = paramDataInput.readByte();
      this.playerInfoId = (paramDataInput.readShort() & 0xFFFF);
    case 1: 
      int i = this.x;
      int j = this.y;
      this.x = paramDataInput.readShort();
      this.y = paramDataInput.readShort();
      this.mode = (paramDataInput.readByte() & 0xFF);
      break;
    case 0: 
      int k = paramDataInput.readByte();
      this.x += k;
      this.y += paramDataInput.readByte();
      this.mode = (paramDataInput.readByte() & 0xFF);
    }
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    drawAt(paramGraphics2D, this.x + paramInt1, this.y + paramInt2);
  }
  
  public Integer getDrawLayer()
  {
    return LAYER10_LAYER12;
  }
  
  public void drawAt(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    if (!this.drawFg)
    {
      this.drawFg = true;
      switch (this.mode)
      {
      case 0: 
        paramGraphics2D.drawImage(image[0], paramInt1, paramInt2, null);
        break;
      case 1: 
        paramGraphics2D.drawImage(image[1], paramInt1, paramInt2, null);
        break;
      case 2: 
        paramGraphics2D.drawImage(image[0], paramInt1, paramInt2, null);
        break;
      case 3: 
        if (this.frameTimer + 100L < System.currentTimeMillis())
        {
          this.frame = (1 - this.frame);
          this.frameTimer = System.currentTimeMillis();
        }
        paramGraphics2D.drawImage(image[(2 + this.frame)], paramInt1, paramInt2, null);
        break;
      case 4: 
        if (this.frameTimer + 100L < System.currentTimeMillis())
        {
          this.frame = (1 - this.frame);
          this.frameTimer = System.currentTimeMillis();
        }
        int i = image[(2 + this.frame)].getWidth();
        int j = image[(2 + this.frame)].getHeight();
        paramGraphics2D.drawImage(image[(2 + this.frame)], paramInt1, paramInt2, paramInt1 + i, paramInt2 + j, i, 0, 0, j, null);
      }
    }
    else
    {
      this.drawFg = false;
      PlayerInfo localPlayerInfo = (PlayerInfo)this.toolkit.getEntity(this.playerInfoId);
      if ((localPlayerInfo != null) && (localPlayerInfo.getControlId() == getId())) {
        localPlayerInfo.drawName(paramGraphics2D, paramInt1, paramInt2);
      }
    }
  }
  
  public void drawCentered(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    drawAt(paramGraphics2D, paramInt1 - image[0].getWidth() / 2, paramInt2 - image[0].getHeight() / 2);
  }
  
  public int getCenterX()
  {
    return this.x + image[0].getWidth() / 2;
  }
  
  public int getCenterY()
  {
    return this.y + image[0].getHeight() / 2;
  }
  
  public int getTeam()
  {
    return this.team;
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
