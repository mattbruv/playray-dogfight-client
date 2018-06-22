package com.aapeli.multiplayer.common.event;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;

public class MouseEventTranslator
  implements MouseListener
{
  private Component destination;
  private MouseListener listener;
  
  public MouseEventTranslator(MouseListener paramMouseListener, Component paramComponent)
  {
    this.listener = paramMouseListener;
    this.destination = paramComponent;
  }
  
  private MouseEvent translateEvent(MouseEvent paramMouseEvent)
  {
    if ((paramMouseEvent.getSource() instanceof Component)) {
      return SwingUtilities.convertMouseEvent((Component)paramMouseEvent.getSource(), paramMouseEvent, this.destination);
    }
    return null;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent)
  {
    this.listener.mouseClicked(translateEvent(paramMouseEvent));
  }
  
  public void mouseEntered(MouseEvent paramMouseEvent)
  {
    this.listener.mouseEntered(translateEvent(paramMouseEvent));
  }
  
  public void mouseExited(MouseEvent paramMouseEvent)
  {
    this.listener.mouseExited(translateEvent(paramMouseEvent));
  }
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    this.listener.mousePressed(translateEvent(paramMouseEvent));
  }
  
  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    this.listener.mouseReleased(translateEvent(paramMouseEvent));
  }
}
