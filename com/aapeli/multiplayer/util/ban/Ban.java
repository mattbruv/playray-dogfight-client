package com.aapeli.multiplayer.util.ban;

public class Ban
{
  private String ip;
  private String nick;
  private long startTime;
  private long duration;
  private String reason;
  
  public Ban(String paramString1, String paramString2, long paramLong, String paramString3)
  {
    this.ip = paramString1;
    this.nick = paramString2;
    this.startTime = System.currentTimeMillis();
    this.duration = paramLong;
    this.reason = paramString3;
  }
  
  public long getDuration()
  {
    return this.duration;
  }
  
  public String getIp()
  {
    return this.ip;
  }
  
  public String getNick()
  {
    return this.nick;
  }
  
  public String getReason()
  {
    return this.reason;
  }
  
  public long getStartTime()
  {
    return this.startTime;
  }
  
  public boolean isValid()
  {
    if (this.duration == -1L) {
      return true;
    }
    return this.startTime + this.duration > System.currentTimeMillis();
  }
}
