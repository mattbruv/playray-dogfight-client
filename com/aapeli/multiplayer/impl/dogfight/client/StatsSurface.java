package com.aapeli.multiplayer.impl.dogfight.client;

import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.impl.dogfight.client.entities.PlayerInfo;
import com.aapeli.multiplayer.impl.dogfight.client.entities.TeamInfo;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class StatsSurface
  extends JComponent
  implements TeamColors
{
  private GameToolkit toolkit;
  private static final BufferedImage[] panelImage = new BufferedImage[2];
  private static final BufferedImage[] bombImage = new BufferedImage[2];
  private static final int STATS_X = 290;
  private static final int STATS_LENGTH = 74;
  private static Font font = new Font("arial", 0, 15);
  private DogfightToolkit dogfightToolkit;
  private boolean forceDraw = true;
  
  public static void loadImages()
  {
    if (panelImage[0] == null)
    {
      panelImage[0] = ImageLoader.getImage("pictures/metalpanel.jpg");
      panelImage[1] = ImageLoader.getImage("pictures/woodpanel.jpg");
      bombImage[0] = ImageLoader.getImage("pictures/carrybomb.gif");
      bombImage[1] = ImageLoader.getImage("pictures/droppedbomb.gif");
    }
  }
  
  public StatsSurface(GameToolkit paramGameToolkit)
  {
    setBackground(Color.BLACK);
    setFocusable(false);
    setOpaque(true);
    this.toolkit = paramGameToolkit;
    this.dogfightToolkit = ((DogfightToolkit)paramGameToolkit.getAttachment());
  }
  
  protected void paintComponent(Graphics paramGraphics)
  {
    PlayerInfo localPlayerInfo = getOwnPlayerInfo();
    if ((localPlayerInfo != null) && (localPlayerInfo.getTeam() != -1)) {
      paramGraphics.drawImage(panelImage[localPlayerInfo.getTeam()], 0, 0, null);
    } else {
      paramGraphics.drawImage(panelImage[0], 0, 0, null);
    }
    paramGraphics.setFont(font);
    TeamInfo[] arrayOfTeamInfo = new TeamInfo[2];
    arrayOfTeamInfo[0] = this.dogfightToolkit.getTeamInfo(0);
    arrayOfTeamInfo[1] = this.dogfightToolkit.getTeamInfo(1);
    int i;
    int j;
    if ((arrayOfTeamInfo[0] != null) && (arrayOfTeamInfo[1] != null)) {
      for (i = 0; i < arrayOfTeamInfo.length; i++)
      {
        if (localPlayerInfo == null) {
          paramGraphics.setColor(OPPONENT_COLOR_FG);
        } else if (localPlayerInfo.getTeam() == i) {
          paramGraphics.setColor(OWN_COLOR_FG);
        } else {
          paramGraphics.setColor(OPPONENT_COLOR_FG);
        }
        j = arrayOfTeamInfo[i].getScore();
        if (j < 0) {
          j = 0;
        }
        int k = arrayOfTeamInfo[(1 - i)].getScore();
        if (k < 0) {
          k = 0;
        }
        int m = 0;
        if (j + k != 0) {
          m = 129 * j / (j + k);
        }
        if (i == 0)
        {
          paramGraphics.fillRect(64, 29, m, 14);
          paramGraphics.setColor(Color.white);
          paramGraphics.drawString("" + arrayOfTeamInfo[i].getScore(), 65, 69);
        }
        else
        {
          paramGraphics.fillRect(64, 86, m, 14);
          paramGraphics.setColor(Color.white);
          paramGraphics.drawString("" + arrayOfTeamInfo[i].getScore(), 65, 126);
        }
      }
    }
    if (localPlayerInfo != null)
    {
      paramGraphics.setColor(Color.WHITE);
      paramGraphics.fillRect(290, 44, 74 * localPlayerInfo.getHealth() / 255, 12);
      paramGraphics.fillRect(290, 65, 74 * localPlayerInfo.getFuel() / 255, 12);
      paramGraphics.fillRect(290, 86, 74 * localPlayerInfo.getAmmo() / 255, 12);
      i = localPlayerInfo.getBombs();
      for (j = 0; j < 5; j++) {
        if (i > j) {
          paramGraphics.drawImage(bombImage[0], 296 + j * 14, 108, null);
        } else {
          paramGraphics.drawImage(bombImage[1], 296 + j * 14, 108, null);
        }
      }
    }
    else
    {
      for (i = 0; i < 5; i++) {
        paramGraphics.drawImage(bombImage[1], 296 + i * 14, 108, null);
      }
    }
  }
  
  private PlayerInfo getOwnPlayerInfo()
  {
    DogfightToolkit localDogfightToolkit = (DogfightToolkit)this.toolkit.getAttachment();
    return localDogfightToolkit.getOwnPlayerInfo();
  }
  
  public boolean checkUpdates()
  {
    if (this.forceDraw)
    {
      this.forceDraw = false;
      return true;
    }
    PlayerInfo localPlayerInfo = getOwnPlayerInfo();
    if ((localPlayerInfo != null) && (localPlayerInfo.isPropertiesChanged())) {
      return true;
    }
    TeamInfo[] arrayOfTeamInfo = new TeamInfo[2];
    arrayOfTeamInfo[0] = this.dogfightToolkit.getTeamInfo(0);
    arrayOfTeamInfo[1] = this.dogfightToolkit.getTeamInfo(1);
    return (arrayOfTeamInfo[0] != null) && (arrayOfTeamInfo[1] != null) && ((arrayOfTeamInfo[0].isChanged()) || (arrayOfTeamInfo[1].isChanged()));
  }
  
  public void setForceDraw(boolean paramBoolean)
  {
    this.forceDraw = paramBoolean;
  }
}
