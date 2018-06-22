package com.aapeli.multiplayer.impl.dogfight.common;

public class BulletEngine
{
  public static final int WIDTH = 2;
  public static final int HEIGHT = 2;
  public static final int DIRECTIONS = 256;
  public static final int MAX_AGE = 175;
  public static final int COORDINATE_RESOLUTION = 100;
  public static final double SPEED_PER_PIXEL = 25.0D;
  
  public static int[] move(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    double d = getAngle(paramInt3);
    paramInt1 += (int)(100 * paramInt4 / 25.0D * Math.cos(d));
    paramInt2 += (int)(100 * paramInt4 / 25.0D * Math.sin(d));
    return new int[] { paramInt1, paramInt2 };
  }
  
  private static double getAngle(int paramInt)
  {
    return paramInt * 6.283185307179586D / 256.0D;
  }
}
