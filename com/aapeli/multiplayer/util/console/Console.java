package com.aapeli.multiplayer.util.console;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class Console
  extends JPanel
  implements KeyListener
{
  private JScrollPane areaScrollPane;
  private ConsoleArea area;
  private ConsoleField field;
  private ConsoleConfiguration configuration = new ConsoleConfiguration();
  private static final Object LISTEN_ALL_KEY = new Object();
  private Map consoleListeners = Collections.synchronizedMap(new HashMap(3));
  public static final int COMMAND = 1;
  
  public Console()
  {
    setLayout(new BoxLayout(this, 1));
    setOpaque(true);
    this.field = new ConsoleField();
    setFocusable(false);
    initArea();
    add(this.areaScrollPane);
    add(this.field);
    this.field.addKeyListener(this);
  }
  
  private void initArea()
  {
    this.area = new ConsoleArea();
    this.area.setEditable(false);
    this.areaScrollPane = new JScrollPane(this.area, 20, 31);
    this.areaScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
    {
      private boolean manualAdjust = false;
      
      public void adjustmentValueChanged(AdjustmentEvent paramAnonymousAdjustmentEvent)
      {
        if ((paramAnonymousAdjustmentEvent.getSource() instanceof JScrollBar)) {
          if (paramAnonymousAdjustmentEvent.getValueIsAdjusting())
          {
            this.manualAdjust = true;
          }
          else if (this.manualAdjust)
          {
            this.manualAdjust = false;
          }
          else
          {
            JScrollBar localJScrollBar = (JScrollBar)paramAnonymousAdjustmentEvent.getSource();
            localJScrollBar.setValue(localJScrollBar.getMaximum());
          }
        }
      }
    });
  }
  
  public void keyPressed(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getKeyCode() == 27)
    {
      setVisible(!isVisible());
      if (isVisible()) {
        requestFocus();
      } else {
        getParent().requestFocus();
      }
    }
    else if ((isVisible()) && (paramKeyEvent.getKeyCode() == 10))
    {
      String str1 = this.field.getText().trim();
      if (str1.length() > 0)
      {
        String[] arrayOfString = str1.split(" ", 2);
        String str2 = "";
        if (arrayOfString.length > 1) {
          str2 = arrayOfString[1];
        }
        input(arrayOfString[0], str2);
      }
      SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          Console.this.field.setText("");
          Console.this.field.requestFocus();
        }
      });
    }
  }
  
  public void addKeyListener(KeyListener paramKeyListener)
  {
    this.field.addKeyListener(paramKeyListener);
  }
  
  public void keyReleased(KeyEvent paramKeyEvent) {}
  
  public void keyTyped(KeyEvent paramKeyEvent) {}
  
  public void setVisible(boolean paramBoolean)
  {
    super.setVisible(paramBoolean);
  }
  
  public void requestFocus()
  {
    this.field.requestFocus();
  }
  
  public void addStyle(String paramString, Color paramColor)
  {
    this.area.addStyle(paramString, paramColor);
  }
  
  public void insertLine(String paramString1, String paramString2)
  {
    this.area.insertLine(paramString1, paramString2);
  }
  
  public void setBackground(Color paramColor)
  {
    super.setBackground(paramColor);
    if ((this.area != null) && (this.field != null))
    {
      this.area.setBackground(paramColor);
      this.field.setBackground(paramColor);
    }
  }
  
  public void setForeground(Color paramColor)
  {
    super.setForeground(paramColor);
    if ((this.area != null) && (this.field != null))
    {
      this.area.setForeground(paramColor);
      this.area.addStyle("default", paramColor);
      this.field.setForeground(paramColor);
      this.field.setCaretColor(paramColor);
    }
  }
  
  private void input(String paramString1, String paramString2)
  {
    if (paramString2.equals(""))
    {
      String str = this.configuration.get(paramString1);
      if (str != null) {
        this.area.insertLine("\"" + str + "\"");
      }
      throwEvent(new ConsoleEvent(this, paramString1));
    }
    else
    {
      if (this.configuration.contains(paramString1)) {
        this.configuration.safePut(paramString1, paramString2);
      }
      throwEvent(new ConsoleEvent(this, paramString1, paramString2));
    }
  }
  
  public void addConsoleListener(ConsoleListener paramConsoleListener)
  {
    addConsoleListener(paramConsoleListener, LISTEN_ALL_KEY);
  }
  
  public void addConsoleListener(ConsoleListener paramConsoleListener, Object paramObject)
  {
    List localList = (List)this.consoleListeners.get(paramObject);
    if (localList == null)
    {
      localList = Collections.synchronizedList(new ArrayList(2));
      this.consoleListeners.put(paramObject, localList);
    }
    localList.add(paramConsoleListener);
  }
  
  public void removeConsoleListener(ConsoleListener paramConsoleListener)
  {
    removeConsoleListener(paramConsoleListener, LISTEN_ALL_KEY);
  }
  
  public void removeConsoleListener(ConsoleListener paramConsoleListener, Object paramObject)
  {
    List localList = (List)this.consoleListeners.get(paramObject);
    if (localList != null) {
      localList.remove(paramConsoleListener);
    }
  }
  
  private void throwEvent(ConsoleEvent paramConsoleEvent)
  {
    List localList1 = (List)this.consoleListeners.get(LISTEN_ALL_KEY);
    if (localList1 != null) {
      throwEvent(paramConsoleEvent, localList1);
    }
    List localList2 = (List)this.consoleListeners.get(paramConsoleEvent.getKey());
    if (localList2 != null) {
      throwEvent(paramConsoleEvent, localList2);
    }
  }
  
  private void throwEvent(ConsoleEvent paramConsoleEvent, List paramList)
  {
    ArrayList localArrayList = null;
    synchronized (paramList)
    {
      localArrayList = new ArrayList(paramList);
    }
    ??? = localArrayList.iterator();
    while (((Iterator)???).hasNext())
    {
      ConsoleListener localConsoleListener = (ConsoleListener)((Iterator)???).next();
      localConsoleListener.consoleActionPerformed(paramConsoleEvent);
    }
  }
  
  public ConsoleValue get(String paramString)
  {
    throwEvent(new ConsoleEvent(this, paramString));
    return new ConsoleValue(this.configuration.get(paramString));
  }
  
  public void put(String paramString1, String paramString2)
  {
    this.configuration.put(paramString1, paramString2);
    throwEvent(new ConsoleEvent(this, paramString1, paramString2));
  }
}
