package com.aapeli.multiplayer.impl.dogfight.common;

public abstract interface PlayerInfoConstants
{
  public static final int HEALTH = 0;
  public static final int TEAM = 1;
  public static final int AMMO = 2;
  public static final int BOMBS = 3;
  public static final int FRAGS = 4;
  public static final int SCORE = 5;
  public static final int DEATHS = 6;
  public static final int CONTROL_ID = 7;
  public static final int FUEL = 8;
  public static final int ACCURACY = 9;
  public static final int READY = 10;
  public static final int PING = 11;
  public static final int CLAN = 12;
  public static final int UNSIGNED_SHORT = 20;
  public static final int UNSIGNED_BYTE = 21;
  public static final int SHORT = 22;
  public static final int BYTE = 23;
  public static final int CUSTOM = 24;
  public static final int[] dataType = { 21, 23, 21, 21, 22, 22, 22, 20, 21, 21, 23, 21, 24 };
  public static final int FULL_SYNC_CHANGED_MASK = 61439;
}
