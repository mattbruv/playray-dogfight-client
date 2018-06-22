package com.aapeli.multiplayer.client.session.error;

import com.aapeli.multiplayer.client.network.Connection;
import com.aapeli.multiplayer.client.session.ClientSession;
import com.aapeli.multiplayer.client.session.ClientSessionManager;
import com.aapeli.multiplayer.common.network.DataInputPacket;
import java.io.PrintStream;
import java.util.Map;
import javax.swing.JComponent;

public class ErrorSession
  extends ClientSession
{
  private ErrorScreen screen;
  
  public ErrorSession(ClientSessionManager paramClientSessionManager)
  {
    super(paramClientSessionManager);
    this.screen = new ErrorScreen(paramClientSessionManager.getApplet());
  }
  
  public void login(Connection paramConnection, Map paramMap)
  {
    super.login(paramConnection, paramMap);
    this.screen.setMessage((String)paramMap.get("error"));
    System.out.println("Error: " + (String)paramMap.get("error"));
  }
  
  public void logout() {}
  
  public void read(DataInputPacket paramDataInputPacket) {}
  
  public JComponent getScreen()
  {
    return this.screen;
  }
  
  public void destroy() {}
}
