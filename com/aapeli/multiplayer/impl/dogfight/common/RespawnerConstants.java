package com.aapeli.multiplayer.impl.dogfight.common;

public abstract interface RespawnerConstants
{
  public static final int DEFAULT_RESPAWN = 0;
  public static final int SUICIDE_RESPAWN = 1;
  public static final int TEAMKILL_RESPAWN = 2;
  public static final long[] WAIT_TIMES = { 4000L, 8000L, 10000L };
}
