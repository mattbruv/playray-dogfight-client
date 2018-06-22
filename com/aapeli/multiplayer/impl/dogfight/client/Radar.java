package com.aapeli.multiplayer.impl.dogfight.client;

import com.aapeli.multiplayer.client.session.game.implInterface.Entity;
import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.entities.Ground;
import com.aapeli.multiplayer.impl.dogfight.client.entities.Plane;
import com.aapeli.multiplayer.impl.dogfight.client.entities.PlayerInfo;
import com.aapeli.multiplayer.impl.dogfight.client.entities.Runway;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JComponent;

public class Radar
  extends JComponent
  implements TeamColors
{
  private GameToolkit toolkit;
  private static final Color BG_COLOR = new Color(13095903);
  
  public Radar(GameToolkit paramGameToolkit)
  {
    this.toolkit = paramGameToolkit;
    setFocusable(false);
    setOpaque(true);
  }
  
  protected void paintComponent(Graphics paramGraphics)
  {
    paramGraphics.setColor(BG_COLOR);
    paramGraphics.fillRect(0, 0, getWidth(), getHeight());
    Map localMap = this.toolkit.getEntities();
    PlayerInfo localPlayerInfo = getOwnPlayerInfo();
    Followable localFollowable = null;
    if (localPlayerInfo != null)
    {
      Entity localEntity1 = this.toolkit.getEntity(localPlayerInfo.getControlId());
      if ((localEntity1 instanceof Followable)) {
        localFollowable = (Followable)localEntity1;
      }
    }
    if ((localFollowable != null) || (localFollowable != null))
    {
      int i = localFollowable.getCenterX();
      int j = localFollowable.getCenterY();
      Iterator localIterator = localMap.values().iterator();
      Entity localEntity2;
      Object localObject;
      int k;
      int m;
      while (localIterator.hasNext())
      {
        localEntity2 = (Entity)localIterator.next();
        if (localEntity2.getType() == 13)
        {
          paramGraphics.setColor(Color.black);
          localObject = (Ground)localEntity2;
          k = ((Ground)localObject).getX();
          m = ((Ground)localObject).getY();
          paramGraphics.fillRect((k - i + 1000) * getWidth() / 2000, (m - j + 500) * getHeight() / 1000, ((Ground)localObject).getWidth() * getHeight() / 1000, 1);
        }
      }
      localIterator = localMap.values().iterator();
      while (localIterator.hasNext())
      {
        localEntity2 = (Entity)localIterator.next();
        if (((localEntity2 instanceof Plane)) || (localEntity2.getType() == 18))
        {
          localObject = (RadarObject)localEntity2;
          k = ((RadarObject)localObject).getCenterX();
          m = ((RadarObject)localObject).getCenterY();
          if (((RadarObject)localObject).getTeam() == localPlayerInfo.getTeam()) {
            paramGraphics.setColor(OWN_COLOR_FG);
          } else {
            paramGraphics.setColor(OPPONENT_COLOR_FG);
          }
          paramGraphics.fillRect((k - i + 1000) * getWidth() / 2000 - 1, (m - j + 500) * getHeight() / 1000 - 1, 3, 3);
        }
        else if (localEntity2.getType() == 16)
        {
          paramGraphics.setColor(Color.white);
          localObject = (Runway)localEntity2;
          k = ((Runway)localObject).getCenterX();
          m = ((Runway)localObject).getCenterY();
          paramGraphics.fillRect((k - i + 1000) * getWidth() / 2000 - 1, (m - j + 500) * getHeight() / 1000 - 1, 3, 3);
        }
      }
    }
  }
  
  private PlayerInfo getOwnPlayerInfo()
  {
    DogfightToolkit localDogfightToolkit = (DogfightToolkit)this.toolkit.getAttachment();
    return localDogfightToolkit.getOwnPlayerInfo();
  }
}
