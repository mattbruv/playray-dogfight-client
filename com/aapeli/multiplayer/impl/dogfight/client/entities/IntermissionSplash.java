package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.client.session.game.implInterface.Entity;
import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.Drawable;
import com.aapeli.multiplayer.impl.dogfight.client.Followable;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public class IntermissionSplash
  extends Entity
  implements Drawable, Followable
{
  private static BufferedImage image;
  private static final Font font = new Font("arial", 1, 20);
  private static final Color color = Color.BLACK;
  
  public static void loadImages()
  {
    image = ImageLoader.getImage("pictures/mdl_page.jpg");
  }
  
  public static BufferedImage getImage()
  {
    return image;
  }
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 2: 
      break;
    }
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2) {}
  
  public Integer getDrawLayer()
  {
    return LAYER11;
  }
  
  public void drawCentered(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    DogfightToolkit localDogfightToolkit = (DogfightToolkit)this.toolkit.getAttachment();
    paramGraphics2D.drawImage(image, 0, 0, null);
    PlayerInfo localPlayerInfo = localDogfightToolkit.getOwnPlayerInfo();
    if ((localPlayerInfo != null) && (!localPlayerInfo.isReady()))
    {
      paramGraphics2D.setColor(color);
      paramGraphics2D.setFont(font);
      String str = Localization.getInstance().localize("(Press enter when ready)");
      Rectangle2D localRectangle2D = font.getStringBounds(str, paramGraphics2D.getFontRenderContext());
      paramGraphics2D.drawString(str, (int)(localDogfightToolkit.getWidth() - localRectangle2D.getWidth()) / 2, 360);
    }
  }
  
  public int getCenterX()
  {
    return 0;
  }
  
  public int getCenterY()
  {
    return 0;
  }
  
  public boolean isInside(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return true;
  }
}
