package com.aapeli.multiplayer.common;

import java.text.DateFormat;
import java.util.Locale;

public class Utils
{
  private DateFormat dateFormat = DateFormat.getDateTimeInstance(3, 3, Locale.FRANCE);
  private static Utils instance;
  
  public static Utils getInstance()
  {
    if (instance != null) {
      return instance;
    }
    instance = new Utils();
    return instance;
  }
  
  public DateFormat getDateFormat()
  {
    return this.dateFormat;
  }
}
