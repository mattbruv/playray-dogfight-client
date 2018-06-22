package com.aapeli.multiplayer.client.common;

import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GUIColors
{
  private static GUIColors guiColors;
  private Map colorMap = Collections.synchronizedMap(new HashMap(10));
  public static Integer BG = new Integer(1);
  public static Integer TOP_BG = new Integer(2);
  public static Integer AREA_BG = new Integer(3);
  public static Integer BORDER = new Integer(4);
  public static Integer SHARP_BORDER = new Integer(5);
  public static Integer TABLE_FG = new Integer(6);
  public static Integer TABLE_BG = new Integer(7);
  public static Integer TABLE_HEADER_FG = new Integer(8);
  public static Integer TABLE_HEADER_BG = new Integer(9);
  public static Integer BUTTON = new Integer(10);
  public static Integer BUTTON_CHOSEN = new Integer(11);
  public static Integer BUTTON_BORDER = new Integer(12);
  public static Integer BUTTON_CHOSEN_BORDER = new Integer(13);
  public static Integer TEXT_FG = new Integer(14);
  public static Integer CHALLENGE_SETTING_BORDER = new Integer(15);
  public static Integer CHALLENGE_SETTING_BG = new Integer(16);
  public static Integer CHALLENGE_SETTING_FG = new Integer(17);
  
  public static GUIColors getInstance()
  {
    if (guiColors == null) {
      guiColors = new GUIColors();
    }
    return guiColors;
  }
  
  private GUIColors()
  {
    put(BG, new Color(9346469));
    put(TOP_BG, new Color(9808575));
    put(AREA_BG, new Color(255, 255, 255));
    put(BORDER, new Color(5335150));
    put(SHARP_BORDER, new Color(0, 0, 0));
    put(TABLE_FG, new Color(0, 0, 0));
    put(TABLE_BG, new Color(255, 255, 255));
    put(TABLE_HEADER_FG, new Color(0, 0, 0));
    put(TABLE_HEADER_BG, new Color(240, 240, 240));
    put(BUTTON, new Color(153, 255, 153));
    put(BUTTON_CHOSEN, new Color(255, 153, 153));
    put(BUTTON_BORDER, new Color(0, 204, 51));
    put(BUTTON_CHOSEN_BORDER, new Color(204, 0, 51));
    put(TEXT_FG, new Color(255, 255, 255));
    put(CHALLENGE_SETTING_BORDER, new Color(100, 255, 100));
    put(CHALLENGE_SETTING_BG, new Color(200, 255, 200));
    put(CHALLENGE_SETTING_FG, new Color(0, 0, 0));
  }
  
  public Color get(Integer paramInteger)
  {
    return (Color)this.colorMap.get(paramInteger);
  }
  
  public void put(Integer paramInteger, Color paramColor)
  {
    if (paramColor != null) {
      this.colorMap.put(paramInteger, paramColor);
    }
  }
}
