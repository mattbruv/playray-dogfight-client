package com.aapeli.multiplayer.util;

import java.awt.Color;
import java.awt.Graphics;
import java.io.PrintStream;
import javax.swing.JComponent;

public class TrafficShaper
  extends JComponent
{
  private LinkedDoubleElement last;
  private LinkedDoubleElement first;
  private int listLength = 0;
  private Object mutex = new Object();
  private double lineY = -1.0D;
  
  public TrafficShaper()
  {
    setOpaque(true);
    setFocusable(false);
  }
  
  public void showLine(double paramDouble)
  {
    this.lineY = paramDouble;
  }
  
  public void hideLine()
  {
    this.lineY = -1.0D;
  }
  
  public void insertColumn(double paramDouble)
  {
    synchronized (this.mutex)
    {
      if (this.first != null)
      {
        this.last.next = new LinkedDoubleElement(paramDouble);
        this.last = this.last.next;
      }
      else
      {
        this.first = (this.last = new LinkedDoubleElement(paramDouble));
      }
      for (this.listLength += 1; this.listLength > getWidth(); this.listLength -= 1) {
        this.first = this.first.next;
      }
    }
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    paramGraphics.setColor(getBackground());
    paramGraphics.fillRect(0, 0, getWidth(), getHeight());
    paramGraphics.setColor(getForeground());
    synchronized (this.mutex)
    {
      int i = getWidth();
      int j = getHeight();
      LinkedDoubleElement localLinkedDoubleElement = this.first;
      for (int k = 0; k < i; k++) {
        if (this.listLength > k)
        {
          if (localLinkedDoubleElement == null) {
            System.out.println("list length: " + this.listLength + " i: " + k + " w: " + i);
          }
          int m = (int)(localLinkedDoubleElement.value * j);
          localLinkedDoubleElement = localLinkedDoubleElement.next;
          paramGraphics.fillRect(k, j - m, 1, m);
        }
      }
      if (this.lineY >= 0.0D)
      {
        paramGraphics.setColor(Color.green);
        paramGraphics.drawLine(0, (int)(j - j * this.lineY), i, (int)(j - j * this.lineY));
      }
    }
  }
  
  private class LinkedDoubleElement
  {
    LinkedDoubleElement next;
    double value;
    
    LinkedDoubleElement(double paramDouble)
    {
      this.value = paramDouble;
    }
  }
}
