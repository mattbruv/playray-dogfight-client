package com.aapeli.multiplayer.client.session.game;

public abstract interface GameToolkitListener
{
  public abstract void sendTextMessage(int paramInt, String paramString);
  
  public abstract void quit();
}
