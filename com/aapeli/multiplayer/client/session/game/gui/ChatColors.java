package com.aapeli.multiplayer.client.session.game.gui;

import java.awt.Color;

public abstract interface ChatColors
{
  public static final Color INFO_FG = Color.white;
  public static final Color ALL_FG = Color.yellow.brighter();
  public static final Color TEAM_FG = new Color(180, 180, 255);
  public static final Color INFO_BG = Color.black;
  public static final Color ALL_BG = Color.black;
  public static final Color TEAM_BG = Color.black;
  public static final Color SERVER_BROADCAST_BG = Color.white;
  public static final Color SERVER_BROADCAST_FG = Color.black;
  public static final Color ROOM_BROADCAST_BG = Color.black;
  public static final Color ROOM_BROADCAST_FG = new Color(6946645);
}
