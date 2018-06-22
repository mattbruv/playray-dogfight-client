package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.session.game.implInterface.Entity;
import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.TeamColors;
import com.aapeli.multiplayer.impl.dogfight.common.PlayerInfoConstants;
import java.awt.Font;
import java.awt.Graphics;
import java.io.DataInput;
import java.io.IOException;

public class PlayerInfo
  extends Entity
  implements TeamColors, PlayerInfoConstants
{
  private String name;
  private boolean fragsChanged = false;
  private boolean propertiesChanged = false;
  private static Font nameFont = new Font("arial", 0, 10);
  private static final int DRAW_NAME_MAX_LENGTH = 15;
  private int[] data = new int[dataType.length];
  private String clan = "";
  
  private boolean checkChangedMask(int paramInt1, int paramInt2)
  {
    return (paramInt1 & 1 << paramInt2) != 0;
  }
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 2: 
      this.name = paramDataInput.readUTF();
      if (isOwn())
      {
        DogfightToolkit localDogfightToolkit = (DogfightToolkit)this.toolkit.getAttachment();
        localDogfightToolkit.setOwnPlayerInfo(this);
      }
    case 0: 
    case 1: 
      int i = paramDataInput.readShort() & 0xFFFF;
      for (int j = 0; j < this.data.length; j++) {
        if (checkChangedMask(i, j))
        {
          if (j == 12) {
            setClan(paramDataInput.readUTF());
          }
          switch (dataType[j])
          {
          case 23: 
            this.data[j] = paramDataInput.readByte();
            break;
          case 22: 
            this.data[j] = paramDataInput.readShort();
            break;
          case 21: 
            this.data[j] = (paramDataInput.readByte() & 0xFF);
            break;
          case 20: 
            this.data[j] = (paramDataInput.readShort() & 0xFFFF);
          }
        }
      }
      this.fragsChanged = true;
      this.propertiesChanged = true;
    }
  }
  
  public int getControlId()
  {
    return this.data[7];
  }
  
  public int getFrags()
  {
    return this.data[4];
  }
  
  public int getScore()
  {
    return this.data[5];
  }
  
  public int getDeaths()
  {
    return this.data[6];
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getFullName()
  {
    if (this.clan.length() > 0) {
      return "[" + this.clan + "] " + getName();
    }
    return getName();
  }
  
  public int getAmmo()
  {
    return this.data[2];
  }
  
  public int getBombs()
  {
    return this.data[3];
  }
  
  public int getFuel()
  {
    return this.data[8];
  }
  
  public int getHealth()
  {
    return this.data[0];
  }
  
  public boolean isFragsChanged()
  {
    if (this.fragsChanged)
    {
      this.fragsChanged = false;
      return true;
    }
    return false;
  }
  
  public boolean isPropertiesChanged()
  {
    if (this.propertiesChanged)
    {
      this.propertiesChanged = false;
      return true;
    }
    return false;
  }
  
  public int getTeam()
  {
    return this.data[1];
  }
  
  public int getPing()
  {
    return this.data[11];
  }
  
  public int getRelativeTeam()
  {
    return ((DogfightToolkit)this.toolkit.getAttachment()).getRelativeTeam(getTeam());
  }
  
  public boolean isOwn()
  {
    return this.name.equals(this.toolkit.getName());
  }
  
  public void drawName(Graphics paramGraphics, int paramInt1, int paramInt2)
  {
    paramGraphics.setFont(nameFont);
    switch (getRelativeTeam())
    {
    case 2: 
      paramGraphics.setColor(OWN_COLOR_FG);
      break;
    case 1: 
      paramGraphics.setColor(OPPONENT_COLOR_FG);
      break;
    case 3: 
      paramGraphics.setColor(UNCHOSEN_COLOR_FG);
    }
    String str = getFullName();
    if (str.length() > 15) {
      str = str.substring(0, 15);
    }
    paramGraphics.drawString(str, paramInt1, paramInt2);
  }
  
  public int getAccuracy()
  {
    int i = this.data[9];
    if ((i & 0x80) != 0) {
      return (i & 0x7F) * 10;
    }
    return i;
  }
  
  public boolean isReady()
  {
    return this.data[10] == 1;
  }
  
  public String getClan()
  {
    return this.clan;
  }
  
  public void setClan(String paramString)
  {
    this.clan = paramString;
  }
}
