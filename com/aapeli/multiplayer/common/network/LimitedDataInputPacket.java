package com.aapeli.multiplayer.common.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;

public class LimitedDataInputPacket
  extends DataInputStream
{
  private byte[] data;
  private int maxStringLength;
  
  public LimitedDataInputPacket(byte[] paramArrayOfByte, int paramInt)
  {
    super(new ByteArrayInputStream(paramArrayOfByte));
    this.maxStringLength = paramInt;
    this.data = paramArrayOfByte;
  }
  
  public byte[] getData()
  {
    return this.data;
  }
  
  public String readLimitedUTF()
    throws IOException
  {
    String str = super.readUTF();
    if (str.length() > this.maxStringLength)
    {
      System.out.println("WARNING: LimitedDataInputPacket: Cutting text: " + str.length() + " -> " + this.maxStringLength);
      str = str.substring(0, this.maxStringLength);
    }
    return str;
  }
}
