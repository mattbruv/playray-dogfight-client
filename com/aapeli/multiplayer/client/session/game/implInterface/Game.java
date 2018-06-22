package com.aapeli.multiplayer.client.session.game.implInterface;

import com.aapeli.multiplayer.client.session.chat.create.OptionList;
import java.util.Map;
import javax.swing.JComponent;

public abstract interface Game
{
  public abstract void init(GameToolkit paramGameToolkit);
  
  public abstract void login(Map paramMap);
  
  public abstract void connectionLost();
  
  public abstract JComponent getScreen();
  
  public abstract void destroy();
  
  public abstract void start();
  
  public abstract void stop();
  
  public abstract void refresh();
  
  public abstract void loadResources();
  
  public abstract double getLoadStatus();
  
  public abstract OptionList createOptionList(String[] paramArrayOfString);
  
  public abstract OptionList manageOptionList(String[] paramArrayOfString);
}
