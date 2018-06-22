package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightEntity;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.Drawable;
import com.aapeli.multiplayer.impl.dogfight.client.Followable;
import com.aapeli.multiplayer.impl.dogfight.common.PlaneChooserConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public class PlaneChooser
  extends DogfightEntity
  implements Followable, Drawable, PlaneChooserConstants
{
  private int chosen;
  private int x;
  private int y;
  private int team;
  private static BufferedImage selectionScreen;
  private static BufferedImage[] plane;
  private static final int AXIS = 0;
  private static final int ALLIES = 1;
  private static Font font = new Font("arial", 1, 17);
  private static Font bigFont = new Font("arial", 1, 20);
  private static String[][] description;
  private boolean vip;
  private boolean forcePlane;
  
  public static void loadResources()
  {
    selectionScreen = ImageLoader.getImage("pictures/selectionScreen.gif");
    plane = new BufferedImage[6];
    for (int i = 0; i < plane.length; i++) {
      plane[i] = ImageLoader.getImage("pictures/pic_plane" + (4 + i) + ".gif");
    }
    description = new String[6][5];
    for (i = 0; i < description.length; i++) {
      for (int j = 0; j < description[i].length; j++) {
        description[i][j] = Localization.getInstance().localize("plane " + (i + 1) + " description " + (j + 1));
      }
    }
  }
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 2: 
      this.team = paramDataInput.readByte();
      int i = paramDataInput.readByte();
      this.forcePlane = ((i & 0x1) == 1);
      this.vip = (i >> 1 == 1);
    case 0: 
    case 1: 
      this.x = paramDataInput.readShort();
      this.y = paramDataInput.readShort();
      this.chosen = paramDataInput.readByte();
    }
  }
  
  public void drawCentered(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    drawAt(paramGraphics2D, paramInt1 - selectionScreen.getWidth() / 2, paramInt2 - selectionScreen.getHeight() / 2);
  }
  
  public int getCenterX()
  {
    return this.x + selectionScreen.getWidth() / 2;
  }
  
  public int getCenterY()
  {
    return this.y + selectionScreen.getHeight() / 2;
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2) {}
  
  public void drawAt(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    if (!this.forcePlane)
    {
      switch (getDogfightToolkit().getDrawQuality())
      {
      case 2: 
        paramGraphics2D.drawImage(selectionScreen, paramInt1, paramInt2, null);
        if (this.team == 0) {
          paramGraphics2D.drawImage(plane[this.chosen], paramInt1 + 10, paramInt2 + 10, null);
        } else if (this.team == 1) {
          paramGraphics2D.drawImage(plane[(this.chosen + 3)], paramInt1 + 10, paramInt2 + 10, null);
        }
        break;
      case 1: 
        paramGraphics2D.setColor(Color.gray);
        paramGraphics2D.fillRect(paramInt1, paramInt2, selectionScreen.getWidth(), selectionScreen.getHeight());
      }
      drawText(paramGraphics2D, paramInt1, paramInt2);
    }
  }
  
  public void drawText(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    paramGraphics2D.setFont(bigFont);
    if (((!this.vip ? 1 : 0) & (this.chosen >= 3 ? 1 : 0)) != 0) {
      paramGraphics2D.setColor(Color.red);
    } else {
      paramGraphics2D.setColor(Color.white);
    }
    int i = this.chosen;
    if (this.team == 1) {
      i += 3;
    }
    paramGraphics2D.drawString(description[i][0], paramInt1 + 10, paramInt2 + 158);
    paramGraphics2D.setFont(font);
    if (((!this.vip ? 1 : 0) & (this.chosen >= 3 ? 1 : 0)) != 0) {
      paramGraphics2D.setColor(Color.red);
    } else {
      paramGraphics2D.setColor(Color.black);
    }
    paramGraphics2D.drawString(description[i][1], paramInt1 + 15, paramInt2 + 190);
    paramGraphics2D.setColor(Color.black);
    paramGraphics2D.drawString(description[i][2], paramInt1 + 15, paramInt2 + 215);
    paramGraphics2D.drawString(description[i][3], paramInt1 + 15, paramInt2 + 240);
    paramGraphics2D.drawString(description[i][4], paramInt1 + 15, paramInt2 + 265);
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
