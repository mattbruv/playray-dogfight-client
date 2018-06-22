package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.bigtext.BigText;
import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightEntity;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.Drawable;
import com.aapeli.multiplayer.impl.dogfight.common.BigInfoConstants;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.DataInput;
import java.io.IOException;

public class BigInfo
  extends DogfightEntity
  implements Drawable, BigInfoConstants
{
  private static Color fgColor = new Color(175, 175, 90);
  private static Color bgColor = new Color(50, 50, 90);
  private static final int Y_OFFSET = -70;
  private static BigText[] bigText;
  private int infoNumber;
  
  public static void loadImages()
  {
    bigText = new BigText[3];
    bigText[0] = new BigText(Localization.getInstance().localize("Centrals have won!"), fgColor, 255);
    bigText[1] = new BigText(Localization.getInstance().localize("Allies have won!"), fgColor, 255);
    bigText[2] = new BigText(Localization.getInstance().localize("Tie!"), fgColor, 255);
  }
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 0: 
    case 1: 
    case 2: 
      this.infoNumber = paramDataInput.readByte();
    }
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    drawAt(paramGraphics2D, paramInt1, paramInt2);
  }
  
  public void drawAt(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    if (this.infoNumber != -1)
    {
      int i = getDogfightToolkit().getWidth();
      int j = getDogfightToolkit().getHeight();
      paramGraphics2D.drawImage(bigText[this.infoNumber].getImage(), i / 2 - bigText[this.infoNumber].getImage().getWidth(null) / 2, j / 2 - bigText[this.infoNumber].getImage().getHeight(null) / 2 + -70, null);
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
