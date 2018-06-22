package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.session.game.implInterface.Entity;
import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightToolkit;
import java.io.DataInput;
import java.io.IOException;

public class WorldInfo
  extends Entity
{
  private int mode = -1;
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    DogfightToolkit localDogfightToolkit;
    switch (paramInt)
    {
    case 0: 
    case 2: 
      String str = paramDataInput.readUTF();
      localDogfightToolkit = (DogfightToolkit)this.toolkit.getAttachment();
      localDogfightToolkit.setRoomName(str);
    case 1: 
      int i = this.mode;
      this.mode = paramDataInput.readByte();
      if (this.mode != i)
      {
        localDogfightToolkit = (DogfightToolkit)this.toolkit.getAttachment();
        localDogfightToolkit.setMode(this.mode);
      }
      break;
    }
  }
}
