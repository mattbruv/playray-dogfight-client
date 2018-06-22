package com.aapeli.multiplayer.client.session.game;

import com.aapeli.multiplayer.client.session.game.implInterface.Entity;
import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.common.event.TextListener;
import com.aapeli.multiplayer.util.TimeQueue;
import com.aapeli.multiplayer.util.console.Console;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GameToolkitImpl
  implements GameToolkit
{
  private Map entityList;
  private Map typeList;
  private String name;
  private GameToolkitListener gameToolkitListener;
  private List chatListenerList;
  private Object attachment;
  private Console console;
  private TimeQueue syncStats;
  
  public GameToolkitImpl(GameToolkitListener paramGameToolkitListener, Console paramConsole)
  {
    this.console = paramConsole;
    this.gameToolkitListener = paramGameToolkitListener;
    this.syncStats = new TimeQueue(50);
    this.chatListenerList = Collections.synchronizedList(new ArrayList(5));
    this.entityList = Collections.synchronizedMap(new HashMap(30));
    this.typeList = new HashMap(10);
  }
  
  public void removeAllEntities()
  {
    this.entityList.clear();
  }
  
  public void removeEntity(int paramInt)
  {
    this.entityList.remove(new Integer(paramInt));
  }
  
  public void addEntity(Entity paramEntity)
  {
    this.entityList.put(new Integer(paramEntity.getId()), paramEntity);
  }
  
  public void setEntities(Map paramMap)
  {
    synchronized (this.entityList)
    {
      this.entityList.clear();
      this.entityList.putAll(paramMap);
    }
  }
  
  public Entity getEntity(int paramInt)
  {
    return (Entity)this.entityList.get(new Integer(paramInt));
  }
  
  public Map getEntities()
  {
    synchronized (this.entityList)
    {
      HashMap localHashMap = new HashMap(this.entityList);
      return localHashMap;
    }
  }
  
  public Entity entityForType(int paramInt)
  {
    try
    {
      if (!this.typeList.containsKey(new Integer(paramInt)))
      {
        System.out.println("ERROR: Illegal type in create: " + paramInt);
        return null;
      }
      Entity localEntity = (Entity)((Class)this.typeList.get(new Integer(paramInt))).newInstance();
      localEntity.setToolkit(this);
      localEntity.setType(paramInt);
      localEntity.init();
      return localEntity;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      System.err.println("Type: " + paramInt + " Class: " + (Class)this.typeList.get(new Integer(paramInt)));
    }
    return null;
  }
  
  public void addType(Class paramClass, int paramInt)
  {
    this.typeList.put(new Integer(paramInt), paramClass);
  }
  
  public void addTypes(Map paramMap)
  {
    this.typeList.putAll(paramMap);
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void sendTextMessage(int paramInt, String paramString)
  {
    this.gameToolkitListener.sendTextMessage(paramInt, paramString);
  }
  
  public void readTextMessage(int paramInt, String paramString1, String paramString2)
  {
    ArrayList localArrayList;
    synchronized (this.chatListenerList)
    {
      localArrayList = new ArrayList(this.chatListenerList);
    }
    ??? = localArrayList.iterator();
    while (((Iterator)???).hasNext())
    {
      TextListener localTextListener = (TextListener)((Iterator)???).next();
      localTextListener.textMessage(paramInt, paramString1, paramString2);
    }
  }
  
  public void addChatListener(TextListener paramTextListener)
  {
    this.chatListenerList.add(paramTextListener);
  }
  
  public void removeChatListener(TextListener paramTextListener)
  {
    this.chatListenerList.remove(paramTextListener);
  }
  
  public void quit()
  {
    this.gameToolkitListener.quit();
  }
  
  public Object getAttachment()
  {
    return this.attachment;
  }
  
  public void setAttachment(Object paramObject)
  {
    this.attachment = paramObject;
  }
  
  public Console getConsole()
  {
    return this.console;
  }
  
  public List retrieveConnectionStatsSince(long paramLong)
  {
    return this.syncStats.retrieveBefore(paramLong);
  }
  
  void packetRead(int paramInt)
  {
    if (this.syncStats.size() < 300)
    {
      long l = System.currentTimeMillis();
      this.syncStats.add(l, new Integer(paramInt));
    }
  }
  
  void reset()
  {
    removeAllEntities();
    this.syncStats.clear();
  }
}
