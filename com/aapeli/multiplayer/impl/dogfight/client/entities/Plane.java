package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.Followable;
import com.aapeli.multiplayer.impl.dogfight.client.RadarObject;
import com.aapeli.multiplayer.util.ImageUtils;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public abstract class Plane
  extends DrawableEntity
  implements Followable, RadarObject
{
  private static final int DIRECTIONS = 256;
  private static final int IMAGE_DIRECTIONS = 128;
  private volatile int x;
  private volatile int y;
  private BufferedImage[] image;
  private volatile int direction;
  private volatile boolean flipped;
  private boolean firstFlip = true;
  private int frame = 0;
  private long frameTimer;
  private long frameDelay = 80L;
  private int playerInfoId;
  private int team = -1;
  private boolean drawFg;
  
  public Plane(BufferedImage[][] paramArrayOfBufferedImage)
  {
    this.drawFg = false;
  }
  
  public Plane(BufferedImage[] paramArrayOfBufferedImage)
  {
    this.image = paramArrayOfBufferedImage;
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
      this.direction = (paramDataInput.readByte() & 0xFF);
      setFlipped(paramDataInput.readBoolean());
      break;
    case 0: 
      int k = paramDataInput.readByte();
      int m = paramDataInput.readByte();
      this.x += k;
      this.y += m;
      this.direction = (paramDataInput.readByte() & 0xFF);
      setFlipped(paramDataInput.readBoolean());
    }
  }
  
  private void setFlipped(boolean paramBoolean)
  {
    if (this.firstFlip)
    {
      this.firstFlip = false;
      this.flipped = paramBoolean;
    }
    else if (this.flipped != paramBoolean)
    {
      this.flipped = paramBoolean;
      this.frame = 1;
      this.frameTimer = System.currentTimeMillis();
    }
  }
  
  protected int getWidth()
  {
    return this.image[0].getWidth();
  }
  
  protected int getHeight()
  {
    return this.image[0].getHeight();
  }
  
  protected int getX()
  {
    return this.x;
  }
  
  protected int getY()
  {
    return this.y;
  }
  
  public int getCenterX()
  {
    return this.x + getWidth() / 2;
  }
  
  public int getCenterY()
  {
    return this.y + getHeight() / 2;
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    drawAt(paramGraphics2D, this.x + paramInt1, this.y + paramInt2);
  }
  
  public void drawCentered(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    drawAt(paramGraphics2D, paramInt1 - getWidth() / 2, paramInt2 - getHeight() / 2);
  }
  
  public void drawAt(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    Object localObject;
    if (!this.drawFg)
    {
      this.drawFg = true;
      if ((this.frame != 0) && (this.frameTimer + this.frameDelay < System.currentTimeMillis()))
      {
        this.frame += 1;
        if (this.frame >= 4) {
          this.frame = 0;
        } else {
          this.frameTimer = System.currentTimeMillis();
        }
      }
      localObject = AffineTransform.getRotateInstance(getAngle(), getWidth() / 2, getHeight() / 2);
      if (((this.flipped) && ((this.frame == 0) || (this.frame == 3))) || ((!this.flipped) && (this.frame != 0)))
      {
        ((AffineTransform)localObject).scale(1.0D, -1.0D);
        ((AffineTransform)localObject).translate(0.0D, -getHeight());
      }
      int i = this.frame;
      if (i == 3) {
        i = 1;
      }
      if (i >= this.image.length) {
        i = 0;
      }
      int j = -(this.image[i].getWidth() - getWidth()) / 2;
      int k = -(this.image[i].getHeight() - getHeight()) / 2;
      paramGraphics2D.drawImage(this.image[i], new AffineTransformOp((AffineTransform)localObject, 1), paramInt1 + j, paramInt2 + k);
    }
    else
    {
      this.drawFg = false;
      localObject = (PlayerInfo)this.toolkit.getEntity(this.playerInfoId);
      if ((localObject != null) && (((PlayerInfo)localObject).getControlId() == getId())) {
        ((PlayerInfo)localObject).drawName(paramGraphics2D, paramInt1, paramInt2 + 45);
      }
    }
  }
  
  private double getAngle()
  {
    return 6.283185307179586D * this.direction / 256.0D;
  }
  
  protected static BufferedImage createRotatedInstance(BufferedImage paramBufferedImage, int paramInt, double paramDouble)
  {
    AffineTransform localAffineTransform = AffineTransform.getRotateInstance(paramDouble, paramBufferedImage.getWidth() / 2, paramBufferedImage.getHeight() / 2);
    if (paramInt == 1)
    {
      localAffineTransform.scale(1.0D, -1.0D);
      localAffineTransform.translate(0.0D, -paramBufferedImage.getHeight());
    }
    AffineTransformOp localAffineTransformOp = new AffineTransformOp(localAffineTransform, 1);
    BufferedImage localBufferedImage = ImageUtils.createCompatibleImage(paramBufferedImage.getWidth(), paramBufferedImage.getHeight(), 2);
    localAffineTransformOp.filter(paramBufferedImage, localBufferedImage);
    return localBufferedImage;
  }
  
  protected static BufferedImage[][] createRotatedInstances(BufferedImage paramBufferedImage)
  {
    BufferedImage[][] arrayOfBufferedImage = new BufferedImage[2][''];
    for (int i = 0; i < arrayOfBufferedImage.length; i++) {
      for (int j = 0; j < arrayOfBufferedImage[i].length; j++) {
        arrayOfBufferedImage[i][j] = createRotatedInstance(paramBufferedImage, i, j * 3.141592653589793D * 2.0D / arrayOfBufferedImage[i].length);
      }
    }
    return arrayOfBufferedImage;
  }
  
  public Integer getDrawLayer()
  {
    return LAYER10_LAYER12;
  }
  
  public int getTeam()
  {
    return this.team;
  }
}
