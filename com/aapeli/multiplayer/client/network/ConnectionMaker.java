package com.aapeli.multiplayer.client.network;

import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.client.session.ClientSessionManager;
import com.aapeli.multiplayer.common.network.GameProtocol;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ConnectionMaker
  implements Runnable, GameProtocol
{
  private Socket socket;
  private String host;
  private int port;
  private Connection connection;
  private ClientSessionManager manager;
  
  public ConnectionMaker(String paramString, int paramInt, ClientSessionManager paramClientSessionManager)
  {
    this.host = paramString;
    this.port = paramInt;
    this.manager = paramClientSessionManager;
  }
  
  public void connect()
  {
    new Thread(this, "ConnectionMaker").start();
  }
  
  public void disconnect()
  {
    if (this.connection != null) {
      this.connection.disconnect();
    }
  }
  
  public boolean isConnected()
  {
    return (this.connection != null) && (this.connection.isConnected());
  }
  
  public void run()
  {
    try
    {
      this.connection = new Connection();
      this.socket = new Socket(this.host, this.port);
      this.connection.init(this.socket);
      this.manager.transfer(this.connection, "Login");
      this.connection.start();
      System.out.println("Connected to " + this.host + ":" + this.port);
    }
    catch (IOException localIOException)
    {
      System.out.println("Could not connect server. ");
      this.manager.error(this.connection, Localization.getInstance().localize("Could not connect to server."));
    }
  }
}
