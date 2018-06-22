package com.aapeli.multiplayer.common.network;

public abstract interface LoginProtocol
{
  public static final int SESSION_OK = 10;
  public static final int SESSION_FAIL = 11;
  public static final int GUEST_OK = 12;
  public static final int GUEST_FAIL = 13;
  public static final int GUEST_LOGIN = 14;
  public static final int SESSION_LOGIN = 15;
  public static final int ANONYM_LOGIN = 16;
  public static final int VERSION = 86;
}
