package com.aapeli.multiplayer.impl.dogfight.common;

import com.aapeli.multiplayer.common.network.CreditConstants;
import com.aapeli.multiplayer.common.network.CreditConverter;
import java.util.HashMap;
import java.util.Map;

public class CreditInitializer
  implements CreditConstants
{
  private static final int HUGE_ROOM_TIME1_PRICE = 120;
  private static final int HUGE_ROOM_TIME2_PRICE = 210;
  private static final int HUGE_ROOM_TIME3_PRICE = 350;
  private static final int MEDIUM_ROOM_TIME1_PRICE = 100;
  private static final int MEDIUM_ROOM_TIME2_PRICE = 170;
  private static final int MEDIUM_ROOM_TIME3_PRICE = 300;
  private static final int SMALL_ROOM_TIME1_PRICE = 70;
  private static final int SMALL_ROOM_TIME2_PRICE = 120;
  private static final int SMALL_ROOM_TIME3_PRICE = 250;
  private static final int TINY_ROOM_TIME1_PRICE = 35;
  private static final int TINY_ROOM_TIME2_PRICE = 70;
  private static final int TINY_ROOM_TIME3_PRICE = 150;
  private static final long ROOM_TIME0 = 86400000L;
  private static final long ROOM_TIME1 = 604800000L;
  private static final long ROOM_TIME2 = 2592000000L;
  private static final long ROOM_TIME3 = 7776000000L;
  private static final int HUGE_ROOM_SIZE = 16;
  private static final int MEDIUM_ROOM_SIZE = 12;
  private static final int SMALL_ROOM_SIZE = 8;
  private static final int TINY_ROOM_SIZE = 4;
  
  public static void initConstants()
  {
    HashMap localHashMap1 = new HashMap(20);
    localHashMap1.put("huge_room_time1", new Integer(120));
    localHashMap1.put("huge_room_time2", new Integer(210));
    localHashMap1.put("huge_room_time3", new Integer(350));
    localHashMap1.put("medium_room_time1", new Integer(100));
    localHashMap1.put("medium_room_time2", new Integer(170));
    localHashMap1.put("medium_room_time3", new Integer(300));
    localHashMap1.put("small_room_time1", new Integer(70));
    localHashMap1.put("small_room_time2", new Integer(120));
    localHashMap1.put("small_room_time3", new Integer(250));
    localHashMap1.put("tiny_room_time1", new Integer(35));
    localHashMap1.put("tiny_room_time2", new Integer(70));
    localHashMap1.put("tiny_room_time3", new Integer(150));
    HashMap localHashMap2 = new HashMap(20);
    localHashMap2.put("new_room_time", new Long(86400000L));
    localHashMap2.put("huge_room_time1", new Long(604800000L));
    localHashMap2.put("huge_room_time2", new Long(2592000000L));
    localHashMap2.put("huge_room_time3", new Long(7776000000L));
    localHashMap2.put("medium_room_time1", new Long(604800000L));
    localHashMap2.put("medium_room_time2", new Long(2592000000L));
    localHashMap2.put("medium_room_time3", new Long(7776000000L));
    localHashMap2.put("small_room_time1", new Long(604800000L));
    localHashMap2.put("small_room_time2", new Long(2592000000L));
    localHashMap2.put("small_room_time3", new Long(7776000000L));
    localHashMap2.put("tiny_room_time1", new Long(604800000L));
    localHashMap2.put("tiny_room_time2", new Long(2592000000L));
    localHashMap2.put("tiny_room_time3", new Long(7776000000L));
    HashMap localHashMap3 = new HashMap(4);
    localHashMap3.put("room_tiny", new Integer(4));
    localHashMap3.put("room_small", new Integer(8));
    localHashMap3.put("room_medium", new Integer(12));
    localHashMap3.put("room_large", new Integer(16));
    CreditConverter.init(localHashMap1, localHashMap2, localHashMap3);
  }
}
