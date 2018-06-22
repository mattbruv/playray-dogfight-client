package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.session.game.implInterface.Entity;
import com.aapeli.multiplayer.impl.dogfight.client.Drawable;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.DataInput;
import java.io.IOException;

public class Clock
  extends Entity
  implements Drawable
{
  private int x;
  private int y;
  private int time;
  private static Font font = new Font("arial", 1, 17);
  private static Color color = new Color(175, 175, 90);
  private static Color color2 = new Color(255, 130, 70);
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 0: 
    case 1: 
    case 2: 
      this.time = (paramDataInput.readShort() & 0xFFFF);
    }
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    paramGraphics2D.setFont(font);
    int i = this.time / 60;
    String str1 = "" + i;
    int j = this.time % 60;
    String str2 = "" + j;
    if (str1.length() < 2) {
      str1 = " " + str1;
    }
    if (str2.length() < 2) {
      str2 = "0" + str2;
    }
    if ((i == 0) && (j < 5)) {
      paramGraphics2D.setColor(color2);
    } else {
      paramGraphics2D.setColor(color);
    }
    paramGraphics2D.drawString(str1 + ":" + str2, 350, 397);
  }
  
  public Integer getDrawLayer()
  {
    return LAYER07;
  }
  
  public boolean isInside(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return true;
  }
}
