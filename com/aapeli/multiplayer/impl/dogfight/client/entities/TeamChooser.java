package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.client.session.game.implInterface.Entity;
import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.impl.dogfight.client.Drawable;
import com.aapeli.multiplayer.impl.dogfight.client.Followable;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public class TeamChooser
  extends Entity
  implements Followable, Drawable
{
  private int chosen;
  private int gap;
  private int x;
  private int y;
  private static final int DESCRIPTION_BOX_Y = 25;
  private static final int FLAG_Y_OFFSET = 80;
  private static Font font = new Font("arial", 1, 17);
  private static Font bigFont = new Font("arial", 1, 20);
  private static String[] description;
  private static BufferedImage[] flag;
  private static BufferedImage bg;
  
  public static void loadImages()
  {
    flag = new BufferedImage[3];
    flag[0] = ImageLoader.getImage("pictures/germanflag.jpg");
    flag[1] = ImageLoader.getImage("pictures/randomflag.jpg");
    flag[2] = ImageLoader.getImage("pictures/royalairforcesflag.jpg");
    bg = ImageLoader.getImage("pictures/info_box.gif");
    description = new String[7];
    for (int i = 0; i < description.length; i++) {
      description[i] = Localization.getInstance().localize("team chooser description " + (i + 1));
    }
  }
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 2: 
      this.gap = (paramDataInput.readByte() & 0xFF);
    case 0: 
    case 1: 
      this.x = paramDataInput.readShort();
      this.y = paramDataInput.readShort();
      this.chosen = paramDataInput.readByte();
    }
  }
  
  public void drawCentered(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    drawAt(paramGraphics2D, paramInt1, paramInt2);
  }
  
  public int getCenterX()
  {
    return this.x;
  }
  
  public int getCenterY()
  {
    return this.y;
  }
  
  public int getFlagWidth()
  {
    return flag[0].getWidth();
  }
  
  public int getFlagHeight()
  {
    return flag[0].getHeight();
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2) {}
  
  public void drawAt(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    int i = paramInt2 + 80;
    paramGraphics2D.drawImage(flag[0], paramInt1 - this.gap - getFlagWidth() * 3 / 2, i - getFlagHeight() / 2, null);
    paramGraphics2D.drawImage(flag[1], paramInt1 - getFlagWidth() / 2, i - getFlagHeight() / 2, null);
    paramGraphics2D.drawImage(flag[2], paramInt1 + this.gap + getFlagWidth() / 2, i - getFlagHeight() / 2, null);
    paramGraphics2D.setColor(Color.YELLOW);
    paramGraphics2D.drawRect(paramInt1 - this.gap - getFlagWidth() * 3 / 2 + this.chosen * (this.gap + getFlagWidth()), i - getFlagHeight() / 2, getFlagWidth() - 1, getFlagHeight() - 1);
    paramGraphics2D.drawRect(paramInt1 - this.gap - getFlagWidth() * 3 / 2 + this.chosen * (this.gap + getFlagWidth()) - 1, i - getFlagHeight() / 2 - 1, getFlagWidth() + 1, getFlagHeight() + 1);
    paramGraphics2D.drawRect(paramInt1 - this.gap - getFlagWidth() * 3 / 2 + this.chosen * (this.gap + getFlagWidth()) - 2, i - getFlagHeight() / 2 - 2, getFlagWidth() + 3, getFlagHeight() + 3);
    drawText(paramGraphics2D);
  }
  
  public void drawText(Graphics2D paramGraphics2D)
  {
    paramGraphics2D.drawImage(bg, 5, 25, null);
    paramGraphics2D.setFont(bigFont);
    paramGraphics2D.setColor(Color.white);
    paramGraphics2D.drawString(description[0], 22, 57);
    paramGraphics2D.setFont(font);
    paramGraphics2D.setColor(Color.yellow);
    paramGraphics2D.drawString(description[1], 27, 82);
    paramGraphics2D.drawString(description[2], 27, 104);
    paramGraphics2D.drawString(description[3], 27, 126);
    paramGraphics2D.drawString(description[4], 27, 148);
    paramGraphics2D.drawString(description[5], 27, 170);
    paramGraphics2D.drawString(description[6], 27, 192);
  }
  
  public Integer getDrawLayer()
  {
    return LAYER08;
  }
  
  public boolean isInside(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return true;
  }
}
