package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.resources.SoundSystem;
import com.aapeli.multiplayer.impl.dogfight.common.BulletEngine;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.DataInput;
import java.io.IOException;

public class Bullet
  extends DrawableEntity
  implements Runnable
{
  private static final int DIRECTIONS = 256;
  private int x;
  private int y;
  private int drawX;
  private int drawY;
  private int direction;
  private int speed;
  private int age = 0;
  private static final Color[] colors = new Color[5];
  
  public Bullet()
  {
    SoundSystem.getInstance().play("sounds/m16.au");
  }
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 2: 
      this.drawX = paramDataInput.readShort();
      this.drawY = paramDataInput.readShort();
      this.x = (100 * this.drawX);
      this.y = (100 * this.drawY);
      this.speed = (paramDataInput.readByte() & 0xFF);
      this.direction = (paramDataInput.readByte() & 0xFF);
      break;
    case 1: 
      this.drawX = paramDataInput.readShort();
      this.drawY = paramDataInput.readShort();
      this.x = (100 * this.drawX);
      this.y = (100 * this.drawY);
      break;
    }
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    drawAt(paramGraphics2D, this.drawX + paramInt1, this.drawY + paramInt2);
  }
  
  public Integer getDrawLayer()
  {
    return LAYER11;
  }
  
  public void drawAt(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    int i = colors.length * this.age / 175;
    if (i >= colors.length) {
      i = colors.length - 1;
    }
    paramGraphics2D.setColor(colors[i]);
    paramGraphics2D.fillRect(paramInt1, paramInt2, 2, 2);
  }
  
  public void run()
  {
    int[] arrayOfInt = BulletEngine.move(this.x, this.y, this.direction, this.speed);
    this.x = arrayOfInt[0];
    this.y = arrayOfInt[1];
    this.drawX = (this.x / 100);
    this.drawY = (this.y / 100);
    this.age += 1;
  }
  
  protected int getX()
  {
    return this.x / 100;
  }
  
  protected int getY()
  {
    return this.y / 100;
  }
  
  protected int getWidth()
  {
    return 2;
  }
  
  protected int getHeight()
  {
    return 2;
  }
  
  static
  {
    colors[0] = new Color(0);
    colors[1] = new Color(1118481);
    colors[2] = new Color(3355443);
    colors[3] = new Color(6316128);
    colors[4] = new Color(10066329);
  }
}
