package com.aapeli.multiplayer.client.resources;

public abstract interface Loadable
{
  public abstract void startLoading();
  
  public abstract double getLoadStatus();
}
