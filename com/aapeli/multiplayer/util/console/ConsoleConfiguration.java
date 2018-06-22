package com.aapeli.multiplayer.util.console;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConsoleConfiguration
{
  private Map configuration = Collections.synchronizedMap(new HashMap(10));
  
  public String get(String paramString)
  {
    return (String)this.configuration.get(paramString);
  }
  
  public boolean contains(String paramString)
  {
    return this.configuration.containsKey(paramString);
  }
  
  public void safePut(String paramString1, String paramString2)
  {
    if (this.configuration.containsKey(paramString1)) {
      this.configuration.put(paramString1, paramString2);
    }
  }
  
  public void put(String paramString1, String paramString2)
  {
    this.configuration.put(paramString1, paramString2);
  }
}
