package com.aapeli.multiplayer.client.session.chat.create;

public abstract interface Option
{
  public abstract String getKey();
  
  public abstract String getValue();
  
  public abstract void setValue(String paramString);
  
  public abstract void reset();
}
