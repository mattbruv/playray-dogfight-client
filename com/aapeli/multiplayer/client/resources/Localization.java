package com.aapeli.multiplayer.client.resources;

import com.aapeli.client.Parameters;
import com.aapeli.client.TextManager;

public class Localization
{
  private TextManager textManager;
  private static Localization instance;
  
  public static Localization getInstance()
  {
    if (instance != null) {
      return instance;
    }
    instance = new Localization();
    return instance;
  }
  
  public String localize(String paramString)
  {
    return this.textManager.get(paramString);
  }
  
  public void init(Parameters paramParameters)
  {
    this.textManager = new TextManager(paramParameters);
  }
  
  public TextManager getTextManager()
  {
    return this.textManager;
  }
}
