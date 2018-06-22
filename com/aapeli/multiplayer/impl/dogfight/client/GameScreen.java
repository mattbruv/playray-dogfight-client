package com.aapeli.multiplayer.impl.dogfight.client;

import com.aapeli.multiplayer.client.session.game.gui.ChatArea;
import com.aapeli.multiplayer.client.session.game.gui.ChatField;
import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.common.event.TextListener;
import com.aapeli.multiplayer.common.monitor.Monitorable;
import com.aapeli.multiplayer.impl.dogfight.client.gui.ConsoleAdapter;
import com.aapeli.multiplayer.impl.dogfight.client.gui.KillArea;
import com.aapeli.multiplayer.util.TrafficShaper;
import com.aapeli.multiplayer.util.console.Console;
import com.aapeli.multiplayer.util.console.ConsoleEvent;
import com.aapeli.multiplayer.util.console.ConsoleListener;
import com.aapeli.multiplayer.util.console.ConsoleValue;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintStream;
import java.util.List;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

public class GameScreen
  extends JLayeredPane
  implements Runnable, MouseListener, KeyListener, Monitorable, HierarchyListener, TextListener, ConsoleListener
{
  private GameToolkit toolkit;
  private boolean running = true;
  private ChatField chatField;
  private ChatArea chatArea;
  private KillArea killArea;
  private ScoreTable scoreTable;
  private boolean watch = false;
  private boolean firstWatch = true;
  private Thread thread;
  private GfxSurface gfxSurface;
  private static final long RUN_INTERVAL = 50L;
  private long sleepTime = 2L;
  private boolean sleepYield = true;
  private TrafficShaper trafficShaper;
  private long shaperInterval = 20L;
  private long checkTime;
  
  public GameScreen(GameToolkit paramGameToolkit)
  {
    setIgnoreRepaint(true);
    setLayout(null);
    setOpaque(true);
    setFocusTraversalKeysEnabled(false);
    this.toolkit = paramGameToolkit;
    addHierarchyListener(this);
    this.gfxSurface = new GfxSurface(paramGameToolkit);
    add(this.gfxSurface, new Integer(1));
    this.chatField = new ChatField(this);
    this.chatField.setFont(new Font("arial", 1, 15));
    add(this.chatField, new Integer(4));
    this.chatField.setBounds(2, 5, 500, 20);
    this.chatArea = new ChatArea();
    this.chatArea.setEditable(false);
    this.chatArea.setVisible(true);
    this.chatArea.setFont(new Font("arial", 1, 15));
    add(this.chatArea, new Integer(4));
    this.chatArea.setLocation(2, 280);
    this.chatArea.setSize(730, 120);
    this.killArea = new KillArea(paramGameToolkit);
    this.killArea.setEditable(false);
    this.killArea.setVisible(true);
    this.killArea.setFont(new Font("arial", 1, 15));
    add(this.killArea, new Integer(2));
    this.killArea.setLocation(510, 0);
    this.killArea.setMaximumSize(new Dimension(220, 220));
    this.scoreTable = new ScoreTable(paramGameToolkit);
    add(this.scoreTable, new Integer(3));
    this.scoreTable.setLocation(20, 10);
    Console localConsole = paramGameToolkit.getConsole();
    localConsole.setVisible(false);
    localConsole.addConsoleListener(this);
    addKeyListener(localConsole);
    add(localConsole, new Integer(5));
    localConsole.put("draw_sleep", "" + this.sleepTime);
    localConsole.put("draw_yield", this.sleepYield ? "1" : "0");
    addMouseListener(this);
    paramGameToolkit.addChatListener(this.chatArea);
    paramGameToolkit.addChatListener(this.killArea);
    paramGameToolkit.addChatListener(new ConsoleAdapter(localConsole));
    addKeyListener(this.scoreTable);
    this.trafficShaper = new TrafficShaper();
    add(this.trafficShaper, new Integer(6));
    this.trafficShaper.setBounds(460, 250, 250, 100);
    this.trafficShaper.setVisible(false);
    this.trafficShaper.setBackground(Color.BLACK);
    this.trafficShaper.setForeground(Color.WHITE);
    this.trafficShaper.showLine(0.1D);
    localConsole.put("traffic_shaper", "0");
    localConsole.put("shaper_interval", "" + this.shaperInterval);
    super.addKeyListener(this.chatField);
  }
  
  public void setWatch(boolean paramBoolean)
  {
    if ((this.watch != paramBoolean) || (this.firstWatch))
    {
      this.firstWatch = false;
      if (!paramBoolean) {
        removeKeyListener(this);
      } else if (paramBoolean) {
        addKeyListener(this);
      }
      this.watch = paramBoolean;
      this.gfxSurface.setWatch(paramBoolean);
    }
  }
  
  public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
    this.gfxSurface.setBounds(0, 0, paramInt3 - paramInt1, paramInt4 - paramInt2);
    this.toolkit.getConsole().setBounds(0, 0, paramInt3 - paramInt1, 150);
  }
  
  public void start()
  {
    this.running = true;
    this.gfxSurface.start();
    this.chatArea.removeAllLines();
    this.killArea.removeAllLines();
    new Thread(this, "GameScreen").start();
    requestFocus();
  }
  
  public void stop()
  {
    this.running = false;
    this.gfxSurface.stop();
    try
    {
      if (this.thread != null) {
        this.thread.join(1000L);
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
  }
  
  public void run()
  {
    this.thread = Thread.currentThread();
    long l1 = 0L;
    long l2 = System.currentTimeMillis();
    while ((this.running) && (this.thread == Thread.currentThread()))
    {
      this.checkTime = System.currentTimeMillis();
      this.gfxSurface.draw();
      long l3 = System.currentTimeMillis();
      if (l1 + 50L < l3)
      {
        l1 = l3;
        SwingUtilities.invokeLater(this.chatArea);
        SwingUtilities.invokeLater(this.killArea);
        SwingUtilities.invokeLater(this.scoreTable);
      }
      while ((this.trafficShaper.isVisible()) && (l2 + this.shaperInterval < l3))
      {
        l2 += this.shaperInterval;
        List localList = this.toolkit.retrieveConnectionStatsSince(l2);
        this.trafficShaper.insertColumn(localList.size() * 10.0D / this.shaperInterval * 0.1D);
      }
      sleep();
    }
    System.out.println("[GameScreen Thread finished]");
  }
  
  private void sleep()
  {
    try
    {
      Thread.sleep(this.sleepTime);
      if (this.sleepYield) {
        Thread.yield();
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    requestFocus();
  }
  
  public void requestFocus()
  {
    Console localConsole = this.toolkit.getConsole();
    if (localConsole.isVisible()) {
      localConsole.requestFocus();
    } else if (this.chatField.isEditable()) {
      this.chatField.requestFocus();
    } else {
      super.requestFocus();
    }
  }
  
  public void mouseReleased(MouseEvent paramMouseEvent) {}
  
  public void destroy()
  {
    this.toolkit.getConsole().removeKeyListener(this);
    this.running = false;
  }
  
  public void refresh() {}
  
  public void addKeyListener(KeyListener paramKeyListener)
  {
    super.addKeyListener(paramKeyListener);
    this.chatField.addKeyListener(paramKeyListener);
  }
  
  public void addMouseListener(MouseListener paramMouseListener)
  {
    super.addMouseListener(paramMouseListener);
    this.killArea.addMouseListener(paramMouseListener);
    this.chatArea.addMouseListener(paramMouseListener);
    this.chatField.addMouseListener(paramMouseListener);
  }
  
  public void removeKeyListener(KeyListener paramKeyListener)
  {
    super.removeKeyListener(paramKeyListener);
    this.chatField.removeKeyListener(paramKeyListener);
  }
  
  public void removeMouseListener(MouseListener paramMouseListener)
  {
    super.removeMouseListener(paramMouseListener);
    this.killArea.removeMouseListener(paramMouseListener);
    this.chatArea.removeMouseListener(paramMouseListener);
    this.chatField.removeMouseListener(paramMouseListener);
  }
  
  public void keyPressed(KeyEvent paramKeyEvent)
  {
    if (this.watch) {
      if (paramKeyEvent.getKeyCode() == 37) {
        this.gfxSurface.rotatePlayerInfo(-1);
      } else if (paramKeyEvent.getKeyCode() == 39) {
        this.gfxSurface.rotatePlayerInfo(1);
      }
    }
  }
  
  public void keyReleased(KeyEvent paramKeyEvent) {}
  
  public void keyTyped(KeyEvent paramKeyEvent) {}
  
  public long getCheckTime()
  {
    return this.checkTime;
  }
  
  public long getMaxInterval()
  {
    return 3000L;
  }
  
  public Thread getThread()
  {
    return this.thread;
  }
  
  public void setCheckTime(long paramLong)
  {
    this.checkTime = paramLong;
  }
  
  public void hierarchyChanged(HierarchyEvent paramHierarchyEvent)
  {
    if ((paramHierarchyEvent.getChangeFlags() & 1L) != 0L) {
      validate();
    }
  }
  
  public void textMessage(int paramInt, String paramString1, String paramString2)
  {
    this.toolkit.sendTextMessage(paramInt, paramString2);
  }
  
  public void consoleActionPerformed(ConsoleEvent paramConsoleEvent)
  {
    if (paramConsoleEvent.getKey().equals("show_fps"))
    {
      if ((paramConsoleEvent.getValue().isInt()) && (paramConsoleEvent.getValue().getInt() == 1)) {
        this.gfxSurface.setShowFps(true);
      } else {
        this.gfxSurface.setShowFps(false);
      }
    }
    else if (paramConsoleEvent.getKey().equals("draw_clipping"))
    {
      if ((paramConsoleEvent.getValue().isInt()) && (paramConsoleEvent.getValue().getInt() == 1)) {
        this.gfxSurface.setDrawClipping(true);
      } else {
        this.gfxSurface.setDrawClipping(false);
      }
    }
    else if (paramConsoleEvent.getKey().equals("draw_sleep"))
    {
      if (paramConsoleEvent.getValue().isInt())
      {
        int i = paramConsoleEvent.getValue().getInt();
        if (i < 0) {
          i = 0;
        }
        if (i > 1000) {
          i = 1000;
        }
        this.sleepTime = i;
      }
    }
    else if (paramConsoleEvent.getKey().equals("draw_yield"))
    {
      if ((paramConsoleEvent.getValue().isInt()) && (paramConsoleEvent.getValue().getInt() == 1)) {
        this.sleepYield = true;
      } else {
        this.sleepYield = false;
      }
    }
    else if (paramConsoleEvent.getKey().equals("draw_quality"))
    {
      if (paramConsoleEvent.getValue().isInt())
      {
        DogfightToolkit localDogfightToolkit = (DogfightToolkit)this.toolkit.getAttachment();
        localDogfightToolkit.setDrawQuality(paramConsoleEvent.getValue().getInt());
      }
    }
    else if (paramConsoleEvent.getKey().equals("traffic_shaper"))
    {
      if ((paramConsoleEvent.getValue().isInt()) && (paramConsoleEvent.getValue().getInt() == 1)) {
        this.trafficShaper.setVisible(true);
      } else {
        this.trafficShaper.setVisible(false);
      }
    }
    else if ((paramConsoleEvent.getKey().equals("shaper_interval")) && (paramConsoleEvent.getValue().isInt()))
    {
      int j = paramConsoleEvent.getValue().getInt();
      if (j < 0) {
        j = 0;
      }
      if (j > 1000) {
        j = 1000;
      }
      this.shaperInterval = j;
    }
  }
}
