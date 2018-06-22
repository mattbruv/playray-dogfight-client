package com.aapeli.multiplayer.client.session.game.implInterface;

import com.aapeli.multiplayer.common.event.TextListener;
import com.aapeli.multiplayer.util.console.Console;
import java.util.List;
import java.util.Map;

public abstract interface GameToolkit
{
  public abstract void removeAllEntities();
  
  public abstract void removeEntity(int paramInt);
  
  public abstract void addEntity(Entity paramEntity);
  
  public abstract Entity getEntity(int paramInt);
  
  public abstract Map getEntities();
  
  public abstract Entity entityForType(int paramInt);
  
  public abstract void addType(Class paramClass, int paramInt);
  
  public abstract void addTypes(Map paramMap);
  
  public abstract String getName();
  
  public abstract void sendTextMessage(int paramInt, String paramString);
  
  public abstract void addChatListener(TextListener paramTextListener);
  
  public abstract void removeChatListener(TextListener paramTextListener);
  
  public abstract void quit();
  
  public abstract Object getAttachment();
  
  public abstract void setAttachment(Object paramObject);
  
  public abstract List retrieveConnectionStatsSince(long paramLong);
  
  public abstract Console getConsole();
}
