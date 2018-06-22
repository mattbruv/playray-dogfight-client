package com.aapeli.multiplayer.common.event;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class EventCodex
{
  public static final int KEY = 21;
  public static final int MOUSE = 22;
  private static Component source = new Panel();
  
  public static byte[] encodeKeyEvent(KeyEvent paramKeyEvent)
  {
    return new byte[] { 21, (byte)(paramKeyEvent.getID() >>> 8), (byte)(paramKeyEvent.getID() & 0xFF), (byte)(paramKeyEvent.getKeyCode() >>> 8), (byte)(paramKeyEvent.getKeyCode() & 0xFF) };
  }
  
  public static byte[] encodeMouseEvent(MouseEvent paramMouseEvent)
  {
    return new byte[] { 22, (byte)(paramMouseEvent.getID() >>> 8), (byte)(paramMouseEvent.getID() & 0xFF), (byte)(paramMouseEvent.getX() >>> 8), (byte)(paramMouseEvent.getX() & 0xFF), (byte)(paramMouseEvent.getY() >>> 8), (byte)(paramMouseEvent.getY() & 0xFF), (byte)paramMouseEvent.getButton() };
  }
  
  public static KeyEvent decodeKeyEvent(byte[] paramArrayOfByte)
  {
    int i = (paramArrayOfByte[1] << 8) + (paramArrayOfByte[2] & 0xFF);
    int j = (paramArrayOfByte[3] << 8) + (paramArrayOfByte[4] & 0xFF);
    return new KeyEvent(source, i, 0L, 0, j, (char)j);
  }
  
  public static MouseEvent decodeMouseEvent(byte[] paramArrayOfByte)
  {
    int i = (paramArrayOfByte[1] << 8) + (paramArrayOfByte[2] & 0xFF);
    int j = (paramArrayOfByte[3] << 8) + (paramArrayOfByte[4] & 0xFF);
    int k = (paramArrayOfByte[5] << 8) + (paramArrayOfByte[6] & 0xFF);
    int m = paramArrayOfByte[7] & 0xFF;
    return new MouseEvent(source, i, 0L, 0, j, k, 0, false, m);
  }
  
  public static AWTEvent decodeEvent(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte[0] & 0xFF;
    if (i == 21) {
      return decodeKeyEvent(paramArrayOfByte);
    }
    if (i == 22) {
      return decodeMouseEvent(paramArrayOfByte);
    }
    return null;
  }
}
