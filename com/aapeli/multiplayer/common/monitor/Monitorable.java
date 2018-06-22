package com.aapeli.multiplayer.common.monitor;

public abstract interface Monitorable
{
  public abstract long getCheckTime();
  
  public abstract void setCheckTime(long paramLong);
  
  public abstract long getMaxInterval();
  
  public abstract Thread getThread();
}
