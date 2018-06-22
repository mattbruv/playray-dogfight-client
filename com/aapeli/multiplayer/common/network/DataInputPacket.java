package com.aapeli.multiplayer.common.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class DataInputPacket
  extends DataInputStream
{
  private byte[] data;
  
  public DataInputPacket(byte[] paramArrayOfByte)
  {
    super(new ByteArrayInputStream(paramArrayOfByte));
    this.data = paramArrayOfByte;
  }
  
  public byte[] getData()
  {
    return this.data;
  }
}
