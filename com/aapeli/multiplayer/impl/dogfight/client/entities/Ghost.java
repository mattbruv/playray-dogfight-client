package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.client.session.game.implInterface.Entity;
import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightEntity;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.Drawable;
import com.aapeli.multiplayer.impl.dogfight.client.Followable;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.DataInput;
import java.io.IOException;

public class Ghost
  extends DogfightEntity
  implements Followable, Drawable
{
  private int team;
  private int followId;
  private int x;
  private int y;
  private static final Font font = new Font("arial", 1, 20);
  private static final Color color = Color.WHITE;
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 2: 
      this.team = paramDataInput.readByte();
      this.x = paramDataInput.readShort();
      this.y = paramDataInput.readShort();
    case 0: 
    case 1: 
      this.followId = (paramDataInput.readShort() & 0xFFFF);
    }
  }
  
  public Followable getFollowed()
  {
    Entity localEntity = this.toolkit.getEntity(this.followId);
    if ((localEntity instanceof PlayerInfo))
    {
      PlayerInfo localPlayerInfo = (PlayerInfo)localEntity;
      localEntity = this.toolkit.getEntity(localPlayerInfo.getControlId());
      if (((localEntity instanceof Followable)) && (!(localEntity instanceof Ghost))) {
        return (Followable)localEntity;
      }
    }
    return null;
  }
  
  public void drawCentered(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    paramGraphics2D.setColor(color);
    paramGraphics2D.setFont(font);
    String str = Localization.getInstance().localize("(Wait for round to finish)");
    Rectangle2D localRectangle2D = font.getStringBounds(str, paramGraphics2D.getFontRenderContext());
    paramGraphics2D.drawString(str, (int)(getDogfightToolkit().getWidth() - localRectangle2D.getWidth()) / 2, 375);
  }
  
  public int getCenterX()
  {
    Followable localFollowable = getFollowed();
    if (localFollowable != null) {
      return localFollowable.getCenterX();
    }
    return this.x;
  }
  
  public int getCenterY()
  {
    Followable localFollowable = getFollowed();
    if (localFollowable != null) {
      return localFollowable.getCenterY();
    }
    return this.y;
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2) {}
  
  public Integer getDrawLayer()
  {
    return LAYER07;
  }
  
  public boolean isInside(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return true;
  }
}
