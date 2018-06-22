package com.aapeli.multiplayer.util.ban;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BanManager
{
  private Map nickBans = Collections.synchronizedMap(new HashMap(5));
  private Map ipBans = Collections.synchronizedMap(new HashMap(5));
  
  public void add(Ban paramBan)
  {
    if (paramBan.getNick() != null) {
      this.nickBans.put(paramBan.getNick(), paramBan);
    }
    if (paramBan.getIp() != null) {
      this.ipBans.put(paramBan.getIp(), paramBan);
    }
  }
  
  public boolean isIpBanned(String paramString)
  {
    Ban localBan = (Ban)this.ipBans.get(paramString);
    if (localBan != null)
    {
      if (!localBan.isValid())
      {
        remove(localBan);
        return false;
      }
      return true;
    }
    return false;
  }
  
  public boolean isNickBanned(String paramString)
  {
    Ban localBan = (Ban)this.nickBans.get(paramString);
    if (localBan != null)
    {
      if (!localBan.isValid())
      {
        remove(localBan);
        return false;
      }
      return true;
    }
    return false;
  }
  
  public void remove(Ban paramBan)
  {
    removeIpBan(paramBan.getIp());
    removeNickBan(paramBan.getNick());
  }
  
  private void removeIpBan(String paramString)
  {
    if (paramString != null) {
      this.ipBans.remove(paramString);
    }
  }
  
  private void removeNickBan(String paramString)
  {
    if (paramString != null) {
      this.nickBans.remove(paramString);
    }
  }
  
  public void removeBanWithIp(String paramString)
  {
    if (paramString != null)
    {
      Ban localBan = (Ban)this.ipBans.remove(paramString);
      if (localBan != null) {
        removeNickBan(localBan.getNick());
      }
    }
  }
  
  public void removeBanWithNick(String paramString)
  {
    if (paramString != null)
    {
      Ban localBan = (Ban)this.nickBans.remove(paramString);
      if (localBan != null) {
        removeIpBan(localBan.getIp());
      }
    }
  }
  
  public void clearFinishedBans()
  {
    Iterator localIterator;
    String str;
    Ban localBan;
    synchronized (this.nickBans)
    {
      localIterator = this.nickBans.keySet().iterator();
      while (localIterator.hasNext())
      {
        str = (String)localIterator.next();
        localBan = (Ban)this.nickBans.get(str);
        if (!localBan.isValid()) {
          localIterator.remove();
        }
      }
    }
    synchronized (this.ipBans)
    {
      localIterator = this.ipBans.keySet().iterator();
      while (localIterator.hasNext())
      {
        str = (String)localIterator.next();
        localBan = (Ban)this.ipBans.get(str);
        if (!localBan.isValid()) {
          localIterator.remove();
        }
      }
    }
  }
  
  public void clearBans()
  {
    synchronized (this.nickBans)
    {
      this.nickBans.clear();
    }
    synchronized (this.ipBans)
    {
      this.ipBans.clear();
    }
  }
  
  /* Error */
  public java.util.List getNickBans()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 5	com/aapeli/multiplayer/util/ban/BanManager:nickBans	Ljava/util/Map;
    //   4: dup
    //   5: astore_1
    //   6: monitorenter
    //   7: new 24	java/util/ArrayList
    //   10: dup
    //   11: aload_0
    //   12: getfield 5	com/aapeli/multiplayer/util/ban/BanManager:nickBans	Ljava/util/Map;
    //   15: invokeinterface 25 1 0
    //   20: invokespecial 26	java/util/ArrayList:<init>	(Ljava/util/Collection;)V
    //   23: aload_1
    //   24: monitorexit
    //   25: areturn
    //   26: astore_2
    //   27: aload_1
    //   28: monitorexit
    //   29: aload_2
    //   30: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	31	0	this	BanManager
    //   5	23	1	Ljava/lang/Object;	Object
    //   26	4	2	localObject1	Object
    // Exception table:
    //   from	to	target	type
    //   7	25	26	finally
    //   26	29	26	finally
  }
}
