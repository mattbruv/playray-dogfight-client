package com.aapeli.multiplayer.impl.dogfight.client;

import java.awt.Graphics2D;

public abstract interface Followable
{
  public abstract int getCenterX();
  
  public abstract int getCenterY();
  
  public abstract void drawCentered(Graphics2D paramGraphics2D, int paramInt1, int paramInt2);
}
