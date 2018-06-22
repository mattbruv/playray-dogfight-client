package com.aapeli.multiplayer.client.session.game.gui;

import com.aapeli.multiplayer.util.DuplicatedMap;
import java.awt.Color;
import java.util.Collections;
import java.util.Map;

public class ChatDecorator
{
  private Map lines = Collections.synchronizedMap(new DuplicatedMap(5));
  private Style forcedStyle;
  
  public void removeLine(String paramString)
  {
    this.lines.remove(paramString);
  }
  
  public void addLine(String paramString, Style paramStyle)
  {
    this.lines.put(paramString, paramStyle);
  }
  
  public Style getStyle(String paramString)
  {
    if (this.forcedStyle != null) {
      return this.forcedStyle;
    }
    return (Style)this.lines.get(paramString);
  }
  
  public Style getForcedStyle()
  {
    return this.forcedStyle;
  }
  
  public void setForcedStyle(Style paramStyle)
  {
    this.forcedStyle = paramStyle;
  }
  
  public static class Style
  {
    private Color bg;
    private Color fg;
    
    public Style(Color paramColor1, Color paramColor2)
    {
      this.bg = paramColor1;
      this.fg = paramColor2;
    }
    
    public Color getBackground()
    {
      return this.bg;
    }
    
    public Color getForeground()
    {
      return this.fg;
    }
  }
}
