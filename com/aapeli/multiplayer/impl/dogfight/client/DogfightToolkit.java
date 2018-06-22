package com.aapeli.multiplayer.impl.dogfight.client;

import com.aapeli.multiplayer.impl.dogfight.client.entities.PlayerInfo;
import com.aapeli.multiplayer.impl.dogfight.client.entities.TeamInfo;
import com.aapeli.multiplayer.impl.dogfight.common.WorldInfoConstants;
import java.awt.Component;

public class DogfightToolkit
  implements WorldInfoConstants
{
  private int mode = 1;
  private PlayerInfo ownPlayerInfo;
  private Component component;
  private String roomName;
  private TeamInfo[] teamInfo = new TeamInfo[2];
  public static final int HIGH = 2;
  public static final int LOW = 1;
  private int drawQuality = 2;
  public static final int TEAM_OPPONENT = 1;
  public static final int TEAM_MATE = 2;
  public static final int TEAM_UNDEFINED = 3;
  
  public int getOwnTeam()
  {
    if (this.ownPlayerInfo != null) {
      return this.ownPlayerInfo.getTeam();
    }
    return -1;
  }
  
  public int getRelativeTeam(int paramInt)
  {
    int i = getOwnTeam();
    if ((i == -1) || (paramInt == -1)) {
      return 3;
    }
    if (i == paramInt) {
      return 2;
    }
    return 1;
  }
  
  public PlayerInfo getOwnPlayerInfo()
  {
    return this.ownPlayerInfo;
  }
  
  public void setOwnPlayerInfo(PlayerInfo paramPlayerInfo)
  {
    this.ownPlayerInfo = paramPlayerInfo;
  }
  
  public int getWidth()
  {
    return this.component.getWidth();
  }
  
  public int getHeight()
  {
    return this.component.getHeight();
  }
  
  public void setComponent(Component paramComponent)
  {
    this.component = paramComponent;
  }
  
  public int getMode()
  {
    return this.mode;
  }
  
  public void setMode(int paramInt)
  {
    this.mode = paramInt;
  }
  
  public void setTeamInfo(TeamInfo paramTeamInfo, int paramInt)
  {
    switch (paramInt)
    {
    case 0: 
      this.teamInfo[0] = paramTeamInfo;
      break;
    case 1: 
      this.teamInfo[1] = paramTeamInfo;
    }
  }
  
  public TeamInfo getTeamInfo(int paramInt)
  {
    switch (paramInt)
    {
    case 0: 
      return this.teamInfo[0];
    case 1: 
      return this.teamInfo[1];
    }
    return null;
  }
  
  public String getRoomName()
  {
    return this.roomName;
  }
  
  public void setRoomName(String paramString)
  {
    this.roomName = paramString;
  }
  
  public int getDrawQuality()
  {
    return this.drawQuality;
  }
  
  public void setDrawQuality(int paramInt)
  {
    this.drawQuality = paramInt;
  }
  
  public void reset()
  {
    this.mode = 1;
    this.ownPlayerInfo = null;
    this.roomName = null;
    this.teamInfo[0] = null;
    this.teamInfo[1] = null;
  }
}
