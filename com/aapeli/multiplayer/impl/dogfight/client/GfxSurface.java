package com.aapeli.multiplayer.impl.dogfight.client;

import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.client.session.game.implInterface.Entity;
import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.entities.Ghost;
import com.aapeli.multiplayer.impl.dogfight.client.entities.IntermissionSplash;
import com.aapeli.multiplayer.impl.dogfight.client.entities.PlayerInfo;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;

public class GfxSurface
  extends JComponent
{
  private VolatileImage vI;
  private GraphicsConfiguration graphicsConfiguration;
  private int fpsCounter;
  private int fps;
  private long fpsTimer;
  private long fpsDelay = 500L;
  private List drawOrderList;
  private static Font font = new Font("arial", 1, 15);
  private int playerInfoNumberNow;
  private boolean watch = false;
  private GameToolkit toolkit;
  private boolean repaintInQueue = false;
  private boolean showFps = false;
  private boolean drawClipping = true;
  
  public GfxSurface(GameToolkit paramGameToolkit)
  {
    this.toolkit = paramGameToolkit;
    this.drawOrderList = new ArrayList(20);
    setOpaque(true);
  }
  
  public void setWatch(boolean paramBoolean)
  {
    this.watch = paramBoolean;
  }
  
  public void draw()
  {
    if (!this.repaintInQueue)
    {
      this.repaintInQueue = true;
      repaint();
    }
  }
  
  protected void paintComponent(Graphics paramGraphics)
  {
    this.repaintInQueue = false;
    drawHighFPSContent((Graphics2D)paramGraphics);
  }
  
  private void renderOffscreen()
  {
    do
    {
      int i = this.vI.validate(this.graphicsConfiguration);
      if (i == 2) {
        this.vI = createVolatileImage(getWidth(), getHeight());
      }
      Graphics2D localGraphics2D = (Graphics2D)this.vI.getGraphics();
      drawHighFPSContent(localGraphics2D);
      localGraphics2D.dispose();
    } while (this.vI.contentsLost());
  }
  
  public void drawHighFPSContent(Graphics2D paramGraphics2D)
  {
    Map localMap = this.toolkit.getEntities();
    int i = 0;
    int j = 0;
    PlayerInfo localPlayerInfo = getOwnPlayerInfo();
    if (this.watch)
    {
      if (localPlayerInfo != null) {
        localPlayerInfo = (PlayerInfo)localMap.get(new Integer(localPlayerInfo.getId()));
      }
      if (localPlayerInfo == null)
      {
        rotatePlayerInfo(1);
        localPlayerInfo = getOwnPlayerInfo();
      }
    }
    Followable localFollowable = null;
    Object localObject1;
    if (localPlayerInfo != null)
    {
      localObject1 = this.toolkit.getEntity(localPlayerInfo.getControlId());
      if ((localObject1 instanceof Followable)) {
        localFollowable = (Followable)localObject1;
      }
    }
    if (localFollowable == null)
    {
      paramGraphics2D.drawImage(IntermissionSplash.getImage(), 0, 0, null);
    }
    else
    {
      i = -localFollowable.getCenterX() + getWidth() / 2;
      j = -localFollowable.getCenterY() + getHeight() / 2;
      localObject1 = localMap.values().iterator();
      Object localObject3;
      Object localObject4;
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Entity)((Iterator)localObject1).next();
        if ((localObject2 instanceof Drawable))
        {
          localObject3 = (Drawable)localObject2;
          if ((!this.drawClipping) || (((Drawable)localObject3).isInside(-i, -j, getWidth(), getHeight())))
          {
            localObject4 = ((Drawable)localObject3).getDrawLayer();
            if (((Integer)localObject4).equals(Drawable.LAYER11_LAYER13))
            {
              this.drawOrderList.add(new DrawPair(Drawable.LAYER11, (Drawable)localObject3));
              this.drawOrderList.add(new DrawPair(Drawable.LAYER13, (Drawable)localObject3));
            }
            else if (((Integer)localObject4).equals(Drawable.LAYER10_LAYER12))
            {
              this.drawOrderList.add(new DrawPair(Drawable.LAYER07, (Drawable)localObject3));
              this.drawOrderList.add(new DrawPair(Drawable.LAYER12, (Drawable)localObject3));
            }
            else
            {
              this.drawOrderList.add(new DrawPair((Integer)localObject4, (Drawable)localObject3));
            }
          }
        }
      }
      Collections.sort(this.drawOrderList);
      localObject1 = null;
      if ((localFollowable instanceof Ghost)) {
        localObject1 = ((Ghost)localFollowable).getFollowed();
      }
      Object localObject2 = this.drawOrderList.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (DrawPair)((Iterator)localObject2).next();
        localObject4 = ((DrawPair)localObject3).drawable;
        if ((localObject4.equals(localFollowable)) || (localObject4.equals(localObject1))) {
          ((Followable)localObject4).drawCentered(paramGraphics2D, getWidth() / 2, getHeight() / 2);
        } else {
          ((Drawable)localObject4).draw(paramGraphics2D, i, j);
        }
      }
      this.drawOrderList.clear();
      long l = System.currentTimeMillis();
      this.fpsCounter += 1;
      if (this.fpsTimer + this.fpsDelay < l)
      {
        this.fps = (this.fpsCounter * 1000 / (int)(l - this.fpsTimer));
        this.fpsTimer = l;
        this.fpsCounter = 0;
      }
      if (this.showFps)
      {
        paramGraphics2D.setFont(font);
        paramGraphics2D.setColor(Color.WHITE);
        paramGraphics2D.drawString("FPS: " + this.fps, getWidth() - 100, 390);
      }
      if (this.watch)
      {
        paramGraphics2D.setFont(font);
        paramGraphics2D.setColor(Color.YELLOW);
        paramGraphics2D.drawString(Localization.getInstance().localize("SPECTATING"), 540, 370);
        localObject4 = getDogfightToolkit().getOwnPlayerInfo();
        if (localObject4 != null) {
          paramGraphics2D.drawString(Localization.getInstance().localize("Following:") + " " + ((PlayerInfo)localObject4).getFullName(), 540, 390);
        }
      }
    }
  }
  
  private PlayerInfo getOwnPlayerInfo()
  {
    DogfightToolkit localDogfightToolkit = (DogfightToolkit)this.toolkit.getAttachment();
    return localDogfightToolkit.getOwnPlayerInfo();
  }
  
  public void rotatePlayerInfo(int paramInt)
  {
    Map localMap = this.toolkit.getEntities();
    ArrayList localArrayList = new ArrayList(10);
    Object localObject = localMap.values().iterator();
    while (((Iterator)localObject).hasNext())
    {
      Entity localEntity = (Entity)((Iterator)localObject).next();
      if ((localEntity instanceof PlayerInfo)) {
        localArrayList.add(localEntity);
      }
    }
    if (localArrayList.size() == 0)
    {
      getDogfightToolkit().setOwnPlayerInfo(null);
    }
    else
    {
      int i = this.playerInfoNumberNow;
      this.playerInfoNumberNow += paramInt;
      if (this.playerInfoNumberNow >= localArrayList.size()) {
        this.playerInfoNumberNow = 0;
      }
      if (this.playerInfoNumberNow < 0) {
        this.playerInfoNumberNow = (localArrayList.size() - 1);
      }
      localObject = (PlayerInfo)localArrayList.get(this.playerInfoNumberNow);
      if (localArrayList.size() > 0) {
        getDogfightToolkit().setOwnPlayerInfo((PlayerInfo)localObject);
      }
    }
  }
  
  private DogfightToolkit getDogfightToolkit()
  {
    return (DogfightToolkit)this.toolkit.getAttachment();
  }
  
  public void start() {}
  
  public void stop() {}
  
  public void setShowFps(boolean paramBoolean)
  {
    this.showFps = paramBoolean;
  }
  
  public void setDrawClipping(boolean paramBoolean)
  {
    this.drawClipping = paramBoolean;
  }
  
  private class DrawPair
    implements Comparable
  {
    private Drawable drawable;
    private Integer priority;
    
    public DrawPair(Integer paramInteger, Drawable paramDrawable)
    {
      this.drawable = paramDrawable;
      this.priority = paramInteger;
    }
    
    public int compareTo(Object paramObject)
    {
      return this.priority.compareTo(((DrawPair)paramObject).priority);
    }
  }
}
