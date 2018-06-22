package com.aapeli.multiplayer.impl.dogfight.client;

import java.awt.Graphics2D;

public abstract interface Drawable
{
  public static final Integer LAYER07 = new Integer(14);
  public static final Integer LAYER08 = new Integer(13);
  public static final Integer LAYER09 = new Integer(12);
  public static final Integer LAYER10 = new Integer(11);
  public static final Integer LAYER11 = new Integer(10);
  public static final Integer LAYER12 = new Integer(9);
  public static final Integer LAYER13 = new Integer(8);
  public static final Integer LAYER14 = new Integer(7);
  public static final Integer LAYER15 = new Integer(6);
  public static final Integer LAYER16 = new Integer(5);
  public static final Integer LAYER17 = new Integer(4);
  public static final Integer LAYER11_LAYER13 = new Integer(55);
  public static final Integer LAYER10_LAYER12 = new Integer(56);
  
  public abstract void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2);
  
  public abstract Integer getDrawLayer();
  
  public abstract boolean isInside(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}
