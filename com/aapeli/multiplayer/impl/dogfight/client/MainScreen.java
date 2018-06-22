package com.aapeli.multiplayer.impl.dogfight.client;

import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.common.event.MouseEventTranslator;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

public class MainScreen
  extends JPanel
{
  private GameScreen gameScreen;
  private StatsScreen statsScreen;
  private Map mouseListeners = Collections.synchronizedMap(new HashMap(2));
  
  public MainScreen(GameToolkit paramGameToolkit)
  {
    setBackground(Color.red);
    setLayout(null);
    this.gameScreen = new GameScreen(paramGameToolkit);
    this.statsScreen = new StatsScreen(paramGameToolkit);
    setFocusable(false);
    add(this.gameScreen);
    add(this.statsScreen);
  }
  
  public void setWatch(boolean paramBoolean)
  {
    this.gameScreen.setWatch(paramBoolean);
  }
  
  public Component getGameAreaComponent()
  {
    return this.gameScreen;
  }
  
  public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
    this.gameScreen.setBounds(paramInt1, paramInt2, paramInt3, paramInt4 - 150);
    this.statsScreen.setBounds(paramInt1, paramInt2 + paramInt4 - 150, paramInt3, 150);
  }
  
  public void start()
  {
    this.gameScreen.start();
    this.statsScreen.start();
    refresh();
  }
  
  public void stop()
  {
    this.gameScreen.stop();
  }
  
  public void destroy()
  {
    this.gameScreen.destroy();
  }
  
  public void refresh()
  {
    if (this.gameScreen != null) {
      this.gameScreen.refresh();
    }
    if (this.statsScreen != null) {
      this.statsScreen.refresh();
    }
  }
  
  protected void paintComponent(Graphics paramGraphics) {}
  
  public void addKeyListener(KeyListener paramKeyListener)
  {
    this.gameScreen.addKeyListener(paramKeyListener);
  }
  
  public void addMouseListener(MouseListener paramMouseListener)
  {
    MouseEventTranslator localMouseEventTranslator = new MouseEventTranslator(paramMouseListener, this);
    this.mouseListeners.put(paramMouseListener, localMouseEventTranslator);
    this.gameScreen.addMouseListener(localMouseEventTranslator);
    this.statsScreen.addMouseListener(localMouseEventTranslator);
  }
  
  public void removeKeyListener(KeyListener paramKeyListener)
  {
    this.gameScreen.removeKeyListener(paramKeyListener);
  }
  
  public void removeMouseListener(MouseListener paramMouseListener)
  {
    MouseListener localMouseListener = (MouseListener)this.mouseListeners.remove(paramMouseListener);
    this.gameScreen.removeMouseListener(localMouseListener);
    this.statsScreen.removeMouseListener(localMouseListener);
  }
}
