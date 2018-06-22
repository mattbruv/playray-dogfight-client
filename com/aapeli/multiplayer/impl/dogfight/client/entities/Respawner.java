package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.bigtext.BigText;
import com.aapeli.client.StringDraw;
import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.client.session.game.implInterface.Entity;
import com.aapeli.multiplayer.impl.dogfight.client.Drawable;
import com.aapeli.multiplayer.impl.dogfight.client.Followable;
import com.aapeli.multiplayer.impl.dogfight.common.RespawnerConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.DataInput;
import java.io.IOException;

public class Respawner
  extends Entity
  implements Followable, Drawable, RespawnerConstants
{
  private int x;
  private int y;
  private long startTime;
  private static Color fgColor = new Color(175, 175, 90);
  private static Color bgColor = new Color(50, 50, 90);
  private static BigText bigText;
  private static BigText[] bigNumber = new BigText[10];
  private static final Font penaltyFont = new Font("arial", 0, 20);
  private int respawnType;
  private static int textXOffset;
  private static int yOffset;
  private static int numberXOffset;
  
  public static void loadImages()
  {
    bigText = new BigText(Localization.getInstance().localize("Respawning in") + "  ", fgColor, 255);
    for (int i = 0; i < bigNumber.length; i++) {
      bigNumber[i] = new BigText(i + 1 + "", fgColor, 255);
    }
    i = bigText.getImage().getWidth(null);
    int j = bigNumber[0].getImage().getWidth(null);
    textXOffset = -(i + j) / 2;
    yOffset = -(bigText.getImage().getHeight(null) / 2);
    numberXOffset = textXOffset + i;
  }
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 2: 
      this.startTime = System.currentTimeMillis();
      this.x = paramDataInput.readShort();
      this.y = paramDataInput.readShort();
      this.respawnType = paramDataInput.readByte();
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
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2) {}
  
  public void drawAt(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    int i = 1 + (int)(WAIT_TIMES[this.respawnType] - (System.currentTimeMillis() - this.startTime)) / 1000;
    if ((i > 0) && (i <= bigNumber.length))
    {
      paramGraphics2D.drawImage(bigText.getImage(), paramInt1 + textXOffset, paramInt2 + yOffset, null);
      paramGraphics2D.drawImage(bigNumber[(i - 1)].getImage(), paramInt1 + numberXOffset, paramInt2 + yOffset, null);
      paramGraphics2D.setFont(penaltyFont);
      paramGraphics2D.setColor(fgColor);
      String str;
      switch (this.respawnType)
      {
      case 1: 
        str = Localization.getInstance().localize("(Suicide penalty)");
        StringDraw.drawOutlinedString(paramGraphics2D, bgColor, str, paramInt1, paramInt2 + yOffset + 70, 0);
        break;
      case 2: 
        str = Localization.getInstance().localize("(Teamkill penalty)");
        StringDraw.drawOutlinedString(paramGraphics2D, bgColor, str, paramInt1, paramInt2 + yOffset + 70, 0);
      }
    }
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
