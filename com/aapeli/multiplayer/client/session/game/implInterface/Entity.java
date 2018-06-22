package com.aapeli.multiplayer.client.session.game.implInterface;

import com.aapeli.multiplayer.common.network.EntityProtocol;
import java.io.DataInput;
import java.io.IOException;

public abstract class Entity
  implements EntityProtocol
{
  protected GameToolkit toolkit;
  protected int type;
  protected int id;
  
  public void setToolkit(GameToolkit paramGameToolkit)
  {
    this.toolkit = paramGameToolkit;
  }
  
  public void setId(int paramInt)
  {
    this.id = paramInt;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public void init() {}
  
  public final void readSync(DataInput paramDataInput, int paramInt)
  {
    try
    {
      switch (paramInt)
      {
      case 0: 
      case 1: 
      case 2: 
        read(paramDataInput, paramInt);
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  protected abstract void read(DataInput paramDataInput, int paramInt)
    throws IOException;
  
  public int getId()
  {
    return this.id;
  }
  
  public int getType()
  {
    return this.type;
  }
}
