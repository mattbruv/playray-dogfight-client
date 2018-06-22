package com.aapeli.multiplayer.util.console;

public class ConsoleEvent
{
  private String key;
  private ConsoleValue value;
  private Object source;
  
  public ConsoleEvent(Object paramObject, String paramString1, String paramString2)
  {
    this.source = paramObject;
    this.key = paramString1;
    this.value = new ConsoleValue(paramString2);
  }
  
  public ConsoleEvent(Object paramObject, String paramString)
  {
    this(paramObject, paramString, null);
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public ConsoleValue getValue()
  {
    return this.value;
  }
  
  public Object getSource()
  {
    return this.source;
  }
  
  public String encode()
  {
    return this.key + "\t" + (this.value.exists() ? this.value.getString() : "");
  }
  
  public static ConsoleEvent decode(String paramString)
  {
    return decode(paramString, null);
  }
  
  public static ConsoleEvent decode(String paramString, Object paramObject)
  {
    String[] arrayOfString = paramString.split("\t");
    if (arrayOfString.length == 1) {
      return new ConsoleEvent(paramObject, arrayOfString[0]);
    }
    return new ConsoleEvent(paramObject, arrayOfString[0], arrayOfString[1]);
  }
}
