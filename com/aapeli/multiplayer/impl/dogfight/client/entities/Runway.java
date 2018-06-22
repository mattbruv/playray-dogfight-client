package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.resources.SoundSystem;
import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.Followable;
import com.aapeli.multiplayer.impl.dogfight.client.TeamColors;
import com.aapeli.multiplayer.util.ImageUtils;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public class Runway
  extends DrawableEntity
  implements Followable
{
  private static final int HEALTH_BAR_HEIGHT = 3;
  private int x;
  private int y;
  private int direction;
  private int health;
  private int lastHealth;
  private static BufferedImage[] image;
  private boolean drawFG = false;
  private long blinkTime;
  private byte team = 0;
  private static final long blinkDelay = 100L;
  
  public static void loadImages()
  {
    image = new BufferedImage[8];
    image[0] = ImageLoader.getImage("pictures/runway.gif");
    image[1] = ImageLoader.getImage("pictures/runway2.gif");
    image[2] = ImageLoader.getImage("pictures/runway2b.gif");
    image[3] = ImageUtils.createWhiteMask(image[0]);
    image[4] = ImageUtils.createWhiteMask(image[1]);
    image[5] = ImageUtils.createWhiteMask(image[2]);
    image[6] = ImageLoader.getImage("pictures/runway_broke.gif");
    image[7] = ImageLoader.getImage("pictures/runway2_broke.gif");
  }
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 2: 
      this.x = paramDataInput.readShort();
      this.y = paramDataInput.readShort();
      this.direction = paramDataInput.readByte();
      this.team = paramDataInput.readByte();
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
  
  public void drawAt(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    if (this.health <= 0)
    {
      drawFG(paramGraphics2D, paramInt1, paramInt2);
      drawBG(paramGraphics2D, paramInt1, paramInt2);
    }
    else if (this.drawFG)
    {
      drawFG(paramGraphics2D, paramInt1, paramInt2);
    }
    else
    {
      drawBG(paramGraphics2D, paramInt1, paramInt2);
    }
  }
  
  private void drawBG(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    if ((this.direction == 0) && (this.health > 0)) {
      if (this.blinkTime + 100L > System.currentTimeMillis()) {
        paramGraphics2D.drawImage(image[5], paramInt1 + 217, paramInt2, null);
      } else {
        paramGraphics2D.drawImage(image[2], paramInt1 + 217, paramInt2, null);
      }
    }
    this.drawFG = true;
  }
  
  private void playBlinkSound()
  {
    SoundSystem.getInstance().play("sounds/hit.au");
  }
  
  private void drawFG(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    if (this.health <= 0)
    {
      if (this.direction == 1) {
        paramGraphics2D.drawImage(image[6], paramInt1, paramInt2, null);
      } else {
        paramGraphics2D.drawImage(image[7], paramInt1, paramInt2, null);
      }
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
        if (this.direction == 1) {
          paramGraphics2D.drawImage(image[3], paramInt1, paramInt2, null);
        } else {
          paramGraphics2D.drawImage(image[4], paramInt1, paramInt2, null);
        }
      }
      else if (this.direction == 1)
      {
        paramGraphics2D.drawImage(image[0], paramInt1, paramInt2, null);
      }
      else
      {
        paramGraphics2D.drawImage(image[1], paramInt1, paramInt2, null);
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
      paramGraphics2D.fillRect(paramInt1 + 10, paramInt2 + image[0].getHeight() - 3 - 1, (image[0].getWidth() - 20) * this.health / 255, 3);
    }
    this.drawFG = false;
  }
  
  public void drawCentered(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    drawAt(paramGraphics2D, paramInt1 - image[(1 - this.direction)].getWidth() / 2, paramInt2 - image[(1 - this.direction)].getHeight() / 2);
  }
  
  public int getCenterX()
  {
    return this.x + image[(1 - this.direction)].getWidth() / 2;
  }
  
  public int getCenterY()
  {
    return this.y + image[(1 - this.direction)].getHeight() / 2;
  }
  
  public Integer getDrawLayer()
  {
    if (this.health <= 0) {
      return LAYER13;
    }
    return LAYER11_LAYER13;
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
    return image[(1 - this.direction)].getWidth();
  }
  
  protected int getHeight()
  {
    return image[(1 - this.direction)].getHeight();
  }
}
