package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.session.game.implInterface.Entity;
import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightToolkit;
import java.io.DataInput;
import java.io.IOException;

public class TeamInfo
  extends Entity
{
  private int score;
  private int teamNumber;
  private boolean changed = false;
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 2: 
      this.teamNumber = (paramDataInput.readByte() & 0xFF);
      DogfightToolkit localDogfightToolkit = (DogfightToolkit)this.toolkit.getAttachment();
      localDogfightToolkit.setTeamInfo(this, this.teamNumber);
    case 0: 
    case 1: 
      this.score = paramDataInput.readInt();
      this.changed = true;
    }
  }
  
  public boolean isChanged()
  {
    if (this.changed)
    {
      this.changed = false;
      return true;
    }
    return false;
  }
  
  public int getScore()
  {
    return this.score;
  }
  
  public int getTeamNumber()
  {
    return this.teamNumber;
  }
  
  public void setTeamNumber(int paramInt)
  {
    this.teamNumber = paramInt;
  }
}
