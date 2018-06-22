package com.aapeli.multiplayer.client.session;

import com.aapeli.multiplayer.client.network.Connection;
import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.common.network.DataInputPacket;
import java.util.Map;
import javax.swing.JComponent;

public abstract class ClientSession
{
  protected Connection connection;
  protected ClientSessionManager manager;
  
  protected ClientSession(ClientSessionManager paramClientSessionManager)
  {
    this.manager = paramClientSessionManager;
  }
  
  public void login(Connection paramConnection, Map paramMap)
  {
    this.connection = paramConnection;
  }
  
  public void disconnect(Connection paramConnection)
  {
    this.manager.error(paramConnection, Localization.getInstance().localize("Connection lost."));
  }
  
  public abstract void read(DataInputPacket paramDataInputPacket);
  
  public abstract JComponent getScreen();
  
  public void start() {}
  
  public void destroy() {}
}
