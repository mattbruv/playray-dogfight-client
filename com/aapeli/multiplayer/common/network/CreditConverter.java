package com.aapeli.multiplayer.common.network;

import java.util.HashMap;
import java.util.Map;

public class CreditConverter
  implements CreditConstants
{
  private Map roomTimePrices;
  private Map roomTimes;
  private Map roomSizes;
  private static CreditConverter instance;
  
  public static void init(Map paramMap1, Map paramMap2, Map paramMap3)
  {
    instance = new CreditConverter(paramMap1, paramMap2, paramMap3);
  }
  
  public static void init()
  {
    HashMap localHashMap1 = new HashMap(20);
    HashMap localHashMap2 = new HashMap(20);
    localHashMap2.put("new_room_time", new Long(86400000L));
    HashMap localHashMap3 = new HashMap(4);
    localHashMap3.put("room_medium", new Integer(4));
    instance = new CreditConverter(localHashMap1, localHashMap2, localHashMap3);
  }
  
  private CreditConverter(Map paramMap1, Map paramMap2, Map paramMap3)
  {
    this.roomTimePrices = paramMap1;
    this.roomTimes = paramMap2;
    this.roomSizes = paramMap3;
  }
  
  public static CreditConverter getInstance()
  {
    if (instance == null) {
      throw new RuntimeException("CreditConverter not inited!");
    }
    return instance;
  }
  
  public int getRoomTimePrice(String paramString)
  {
    return ((Integer)this.roomTimePrices.get(paramString)).intValue();
  }
  
  public long getRoomTime(String paramString)
  {
    return ((Long)this.roomTimes.get(paramString)).longValue();
  }
  
  public int getRoomSize(String paramString)
  {
    return ((Integer)this.roomSizes.get(paramString)).intValue();
  }
}
