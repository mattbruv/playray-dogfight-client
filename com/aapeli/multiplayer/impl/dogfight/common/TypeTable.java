package com.aapeli.multiplayer.impl.dogfight.common;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class TypeTable
{
  public static final int UNDEFINED = 0;
  public static final int PLAYER_INFO = 10;
  public static final int PLANE = 11;
  public static final int BULLET = 12;
  public static final int GROUND = 13;
  public static final int SMOKE = 14;
  public static final int SKY = 15;
  public static final int RUNWAY = 16;
  public static final int BOMB = 17;
  public static final int MAN = 18;
  public static final int BLACK_SMOKE = 19;
  public static final int PLANE_CHOOSER = 22;
  public static final int COAST = 23;
  public static final int HILL = 24;
  public static final int BACKGROUND_ITEM = 25;
  public static final int TEAM_INFO = 26;
  public static final int EXPLOSION = 27;
  public static final int WATER = 28;
  public static final int TEAM_CHOOSER = 29;
  public static final int ALBATROS_PLANE = 30;
  public static final int JUNKERS_PLANE = 31;
  public static final int FOKKER_PLANE = 32;
  public static final int BRISTOL_PLANE = 33;
  public static final int SALMSON_PLANE = 34;
  public static final int SOPWITH_PLANE = 35;
  public static final int RESPAWNER = 36;
  public static final int CLOCK = 37;
  public static final int INTERMISSION_SPLASH = 38;
  public static final int WORLD_INFO = 39;
  public static final int FLAG = 40;
  public static final int GHOST = 41;
  public static final int IMPORTANT_BUILDING = 42;
  public static final int BIG_INFO = 43;
  private static Map clientTypeMap;
  private static Map serverTypeMap;
  
  public static void initClientTypes()
  {
    clientTypeMap = new HashMap(60);
    clientTypeMap.put(new Integer(10), com.aapeli.multiplayer.impl.dogfight.client.entities.PlayerInfo.class);
    clientTypeMap.put(new Integer(11), com.aapeli.multiplayer.impl.dogfight.client.entities.Plane.class);
    clientTypeMap.put(new Integer(12), com.aapeli.multiplayer.impl.dogfight.client.entities.Bullet.class);
    clientTypeMap.put(new Integer(13), com.aapeli.multiplayer.impl.dogfight.client.entities.Ground.class);
    clientTypeMap.put(new Integer(14), com.aapeli.multiplayer.impl.dogfight.client.entities.Smoke.class);
    clientTypeMap.put(new Integer(15), com.aapeli.multiplayer.impl.dogfight.client.entities.Sky.class);
    clientTypeMap.put(new Integer(16), com.aapeli.multiplayer.impl.dogfight.client.entities.Runway.class);
    clientTypeMap.put(new Integer(17), com.aapeli.multiplayer.impl.dogfight.client.entities.Bomb.class);
    clientTypeMap.put(new Integer(18), com.aapeli.multiplayer.impl.dogfight.client.entities.Man.class);
    clientTypeMap.put(new Integer(19), com.aapeli.multiplayer.impl.dogfight.client.entities.BlackSmoke.class);
    clientTypeMap.put(new Integer(22), com.aapeli.multiplayer.impl.dogfight.client.entities.PlaneChooser.class);
    clientTypeMap.put(new Integer(23), com.aapeli.multiplayer.impl.dogfight.client.entities.Coast.class);
    clientTypeMap.put(new Integer(24), com.aapeli.multiplayer.impl.dogfight.client.entities.Hill.class);
    clientTypeMap.put(new Integer(25), com.aapeli.multiplayer.impl.dogfight.client.entities.BackgroundItem.class);
    clientTypeMap.put(new Integer(26), com.aapeli.multiplayer.impl.dogfight.client.entities.TeamInfo.class);
    clientTypeMap.put(new Integer(27), com.aapeli.multiplayer.impl.dogfight.client.entities.Explosion.class);
    clientTypeMap.put(new Integer(28), com.aapeli.multiplayer.impl.dogfight.client.entities.Water.class);
    clientTypeMap.put(new Integer(29), com.aapeli.multiplayer.impl.dogfight.client.entities.TeamChooser.class);
    clientTypeMap.put(new Integer(30), com.aapeli.multiplayer.impl.dogfight.client.entities.AlbatrosPlane.class);
    clientTypeMap.put(new Integer(31), com.aapeli.multiplayer.impl.dogfight.client.entities.JunkersPlane.class);
    clientTypeMap.put(new Integer(32), com.aapeli.multiplayer.impl.dogfight.client.entities.FokkerPlane.class);
    clientTypeMap.put(new Integer(33), com.aapeli.multiplayer.impl.dogfight.client.entities.BristolPlane.class);
    clientTypeMap.put(new Integer(34), com.aapeli.multiplayer.impl.dogfight.client.entities.SalmsonPlane.class);
    clientTypeMap.put(new Integer(35), com.aapeli.multiplayer.impl.dogfight.client.entities.SopwithPlane.class);
    clientTypeMap.put(new Integer(36), com.aapeli.multiplayer.impl.dogfight.client.entities.Respawner.class);
    clientTypeMap.put(new Integer(37), com.aapeli.multiplayer.impl.dogfight.client.entities.Clock.class);
    clientTypeMap.put(new Integer(38), com.aapeli.multiplayer.impl.dogfight.client.entities.IntermissionSplash.class);
    clientTypeMap.put(new Integer(39), com.aapeli.multiplayer.impl.dogfight.client.entities.WorldInfo.class);
    clientTypeMap.put(new Integer(40), com.aapeli.multiplayer.impl.dogfight.client.entities.Flag.class);
    clientTypeMap.put(new Integer(41), com.aapeli.multiplayer.impl.dogfight.client.entities.Ghost.class);
    clientTypeMap.put(new Integer(42), com.aapeli.multiplayer.impl.dogfight.client.entities.ImportantBuilding.class);
    clientTypeMap.put(new Integer(43), com.aapeli.multiplayer.impl.dogfight.client.entities.BigInfo.class);
  }
  
  public static void initServerTypes()
  {
    serverTypeMap = new HashMap(60);
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.PlayerInfo.class, new Integer(10));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.Plane.class, new Integer(11));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.Bullet.class, new Integer(12));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.Ground.class, new Integer(13));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.Smoke.class, new Integer(14));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.Sky.class, new Integer(15));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.Runway.class, new Integer(16));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.Bomb.class, new Integer(17));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.Man.class, new Integer(18));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.BlackSmoke.class, new Integer(19));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.PlaneChooser.class, new Integer(22));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.Coast.class, new Integer(23));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.Hill.class, new Integer(24));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.BackgroundItem.class, new Integer(25));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.TeamInfo.class, new Integer(26));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.Explosion.class, new Integer(27));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.Water.class, new Integer(28));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.TeamChooser.class, new Integer(29));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.AlbatrosPlane.class, new Integer(30));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.JunkersPlane.class, new Integer(31));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.FokkerPlane.class, new Integer(32));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.BristolPlane.class, new Integer(33));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.SalmsonPlane.class, new Integer(34));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.SopwithPlane.class, new Integer(35));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.Respawner.class, new Integer(36));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.Clock.class, new Integer(37));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.IntermissionSplash.class, new Integer(38));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.WorldInfo.class, new Integer(39));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.Flag.class, new Integer(40));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.Ghost.class, new Integer(41));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.ImportantBuilding.class, new Integer(42));
    serverTypeMap.put(com.aapeli.multiplayer.impl.dogfight.server.entities.BigInfo.class, new Integer(43));
  }
  
  public static Map getClientTypes()
  {
    return clientTypeMap;
  }
  
  public static int getServerType(Class paramClass)
  {
    Integer localInteger = (Integer)serverTypeMap.get(paramClass);
    if (localInteger != null) {
      return localInteger.intValue();
    }
    System.out.println("ERROR: class not found from type map, class: " + paramClass + " map: " + serverTypeMap);
    new Exception("DEBUG").printStackTrace();
    return 0;
  }
}
