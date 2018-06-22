package com.aapeli.multiplayer.common.network;

public class ChatEvent
{
  private int type;
  private String[] args;
  public static final int MSG = 1;
  public static final int JOIN_REQUEST = 2;
  public static final int PART = 3;
  public static final int CHANNEL = 4;
  public static final int ADMIN = 5;
  public static final int BROADCAST = 6;
  public static final int JOIN_ANNOUNCE = 7;
  public static final int SHERIFF_MSG = 8;
  
  public ChatEvent(int paramInt, String[] paramArrayOfString)
  {
    this.type = paramInt;
    this.args = paramArrayOfString;
  }
  
  public String[] getArgs()
  {
    return this.args;
  }
  
  public int getType()
  {
    return this.type;
  }
}
