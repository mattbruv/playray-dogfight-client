package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.resources.SoundSystem;
import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.Drawable;
import com.aapeli.multiplayer.impl.dogfight.client.TeamColors;
import com.aapeli.multiplayer.util.ImageUtils;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public class ImportantBuilding
  extends DrawableEntity
  implements Drawable
{
  private static final int HEALTH_BAR_HEIGHT = 3;
  private int x;
  private int y;
  private int buildingType;
  private int health;
  private int lastHealth;
  private static BufferedImage[] image;
  private long blinkTime;
  private byte team = 0;
  private static final long blinkDelay = 100L;
  
  public static void loadImages()
  {
    image = new BufferedImage[6];
    image[0] = ImageLoader.getImage("pictures/headquarter_germans.gif");
    image[1] = ImageUtils.createWhiteMask(image[0]);
    image[2] = ImageLoader.getImage("pictures/headquarter_broke.gif");
    image[3] = ImageLoader.getImage("pictures/headquarter_raf.gif");
    image[4] = ImageUtils.createWhiteMask(image[3]);
    image[5] = image[2];
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
      this.buildingType = paramDataInput.readByte();
      this.lastHealth = (this.health = paramDataInput.readByte() & 0xFF);
      break;
    case 0: 
    case 1: 
      this.health = (paramDataInput.readByte() & 0xFF);
    }
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    drawAt(paramGraphics2D, this.x + paramInt1, this.y + paramInt2);
  }
  
  private void playBlinkSound()
  {
    SoundSystem.getInstance().play("sounds/hit.au");
  }
  
  private void drawAt(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    if (this.health <= 0)
    {
      paramGraphics2D.drawImage(image[(this.buildingType * 3)], paramInt1, paramInt2, null);
    }
    else
    {
      if (this.health < this.lastHealth) {
        this.blinkTime = System.currentTimeMillis();
      }
      this.lastHealth = this.health;
      if (this.blinkTime + 100L > System.currentTimeMillis())
      {
        playBlinkSound();
        paramGraphics2D.drawImage(image[(this.buildingType * 3 + 1)], paramInt1, paramInt2, null);
      }
      else
      {
        paramGraphics2D.drawImage(image[(this.buildingType * 3)], paramInt1, paramInt2, null);
      }
      int i = ((DogfightToolkit)this.toolkit.getAttachment()).getRelativeTeam(this.team);
      switch (i)
      {
      case 2: 
        paramGraphics2D.setColor(TeamColors.OWN_COLOR_BG);
        break;
      case 1: 
        paramGraphics2D.setColor(TeamColors.OPPONENT_COLOR_BG);
        break;
      case 3: 
        paramGraphics2D.setColor(TeamColors.UNCHOSEN_COLOR_BG);
      }
      paramGraphics2D.fillRect(paramInt1 + 10, paramInt2 + image[(this.buildingType * 3)].getHeight() - 3 - 1, (image[(this.buildingType * 3)].getWidth() - 20) * this.health / 255, 3);
    }
  }
  
  public Integer getDrawLayer()
  {
    if (this.health <= 0) {
      return LAYER13;
    }
    return LAYER09;
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
    return image[(this.buildingType * 3)].getWidth();
  }
  
  protected int getHeight()
  {
    return image[(this.buildingType * 3)].getHeight();
  }
}
