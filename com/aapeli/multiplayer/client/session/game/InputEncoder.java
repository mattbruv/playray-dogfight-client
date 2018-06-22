package com.aapeli.multiplayer.client.session.game;

import com.aapeli.multiplayer.common.event.EventCodex;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InputEncoder
  implements MouseListener, KeyListener
{
  private EncodedEventListener listener;
  private List pressedKeys;
  private static final boolean TYPED_ENABLED = false;
  
  public InputEncoder(EncodedEventListener paramEncodedEventListener)
  {
    this.listener = paramEncodedEventListener;
    this.pressedKeys = Collections.synchronizedList(new ArrayList(10));
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent)
  {
    this.listener.eventPerformed(EventCodex.encodeMouseEvent(paramMouseEvent));
  }
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    this.listener.eventPerformed(EventCodex.encodeMouseEvent(paramMouseEvent));
  }
  
  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    this.listener.eventPerformed(EventCodex.encodeMouseEvent(paramMouseEvent));
  }
  
  public void mouseEntered(MouseEvent paramMouseEvent)
  {
    this.listener.eventPerformed(EventCodex.encodeMouseEvent(paramMouseEvent));
  }
  
  public void mouseExited(MouseEvent paramMouseEvent)
  {
    this.listener.eventPerformed(EventCodex.encodeMouseEvent(paramMouseEvent));
  }
  
  public void keyTyped(KeyEvent paramKeyEvent) {}
  
  public void keyPressed(KeyEvent paramKeyEvent)
  {
    int i = paramKeyEvent.getKeyCode();
    if (!this.pressedKeys.contains(new Integer(i)))
    {
      this.pressedKeys.add(new Integer(i));
      this.listener.eventPerformed(EventCodex.encodeKeyEvent(paramKeyEvent));
    }
  }
  
  public void keyReleased(KeyEvent paramKeyEvent)
  {
    int i = paramKeyEvent.getKeyCode();
    if (this.pressedKeys.contains(new Integer(i)))
    {
      this.pressedKeys.remove(new Integer(i));
      this.listener.eventPerformed(EventCodex.encodeKeyEvent(paramKeyEvent));
    }
  }
}
