package com.aapeli.multiplayer.client.network;

import java.io.DataInput;
import java.io.IOException;

public abstract class ClientEntity
{
  protected int type;
  protected int id;
  
  public final void readFullSync(DataInput paramDataInput)
  {
    try
    {
      this.type = (paramDataInput.readByte() & 0xFF);
      this.id = (paramDataInput.readByte() & 0xFF);
      read(paramDataInput, true);
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  public final void readSync(DataInput paramDataInput)
  {
    try
    {
      read(paramDataInput, false);
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  protected abstract void read(DataInput paramDataInput, boolean paramBoolean)
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
