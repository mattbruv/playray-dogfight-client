package com.aapeli.multiplayer.common.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class DataOutputPacket
  extends DataOutputStream
{
  public DataOutputPacket(int paramInt)
  {
    super(new ByteArrayOutputStream(paramInt));
  }
  
  public DataOutputPacket()
  {
    super(new ByteArrayOutputStream());
  }
  
  public byte[] getBytes()
  {
    return ((ByteArrayOutputStream)this.out).toByteArray();
  }
}
