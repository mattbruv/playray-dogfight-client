package com.aapeli.multiplayer.impl.dogfight.common;

public abstract interface ChatMessageProtocol
{
  public static final int CHAT_PLANE_KILL_SELF = 1;
  public static final int CHAT_PLANE_KILL_TEAM = 2;
  public static final int CHAT_PLANE_KILL_OPPONENT = 3;
  public static final int CHAT_MAN_KILL_SELF = 4;
  public static final int CHAT_MAN_KILL_TEAM = 5;
  public static final int CHAT_MAN_KILL_OPPONENT = 6;
  public static final int CHAT_CONTROL_END = 1;
  public static final int CHAT_CONTROL_START = 2;
  public static final int CHAT_KILL_TYPE_PLANE = 1;
  public static final int CHAT_KILL_TYPE_MAN = 2;
}
