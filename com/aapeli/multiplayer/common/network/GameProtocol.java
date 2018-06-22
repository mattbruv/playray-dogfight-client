package com.aapeli.multiplayer.common.network;

public abstract interface GameProtocol
{
  public static final int FULL_SYNC = 51;
  public static final int CHANGE_SYNC = 52;
  public static final int INPUT = 53;
  public static final int FIRST_SYNC = 54;
  public static final int TEXT_MESSAGE = 55;
  public static final int PING = 56;
  public static final int FORCE_QUIT = 57;
  public static final int QUIT = 58;
  public static final int REQUEST_FIRST_SYNC = 59;
  public static final int ERROR = 60;
  public static final int QUITTING = 61;
  public static final int FORCE_QUITTING = 62;
  public static final int VIDEO_GET = 70;
  public static final int VIDEO_PUSH_CONTINUE = 71;
  public static final int VIDEO_PUSH_START = 72;
  public static final int BUFFER_TIME_CHANGE = 1020;
  public static final int UNDEFINED_ID = 1;
  public static final int ADDED_ID = 4;
  public static final int REMOVED_ID = 5;
  public static final int FIRST_FREE_ID = 6;
  public static final int CHAT_ALL = 1;
  public static final int CHAT_TEAM = 2;
  public static final int TEXT_INFO = 3;
  public static final int TEXT_CUSTOM = 4;
  public static final int TEXT_CONSOLE_COMMAND = 5;
  public static final int CHAT_SPECTATOR = 6;
  public static final int CHAT_ROOM_BROADCAST = 7;
  public static final int CHAT_SERVER_BROADCAST = 8;
  public static final int QUIT_UNDEFINED = 0;
  public static final int QUIT_KICK = 1;
  public static final int QUIT_BANNED = 2;
  public static final int QUIT_LOW_CONNECTION = 3;
  public static final int QUIT_USER_REQUEST = 4;
  public static final int QUIT_CUSTOM = 5;
  public static final int QUIT_FULL = 6;
  public static final int QUIT_CUSTOM_TRANSLATED = 7;
}
