package com.aapeli.multiplayer.client.network;

import com.aapeli.multiplayer.client.session.ClientSession;
import com.aapeli.multiplayer.common.network.DataInputPacket;
import com.aapeli.multiplayer.common.network.DataOutputPacket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Connection
  implements Runnable
{
  private Socket socket;
  private boolean connected;
  private BufferedOutputStream out;
  private BufferedInputStream in;
  private String name;
  private ClientSession session;
  private boolean admin;
  private boolean sheriff;
  
  public void init(Socket paramSocket)
    throws IOException
  {
    this.socket = paramSocket;
    paramSocket.setTcpNoDelay(true);
    this.in = new BufferedInputStream(paramSocket.getInputStream());
    this.out = new BufferedOutputStream(paramSocket.getOutputStream());
    this.connected = true;
    System.out.println("New connection: " + paramSocket.getInetAddress());
  }
  
  public void start()
  {
    if (this.connected)
    {
      Thread localThread = new Thread(this, "Connection");
      localThread.start();
    }
  }
  
  public void disconnect()
  {
    System.out.println("[Disconnecting]");
    this.connected = false;
    try
    {
      if (this.socket != null) {
        this.socket.close();
      }
    }
    catch (IOException localIOException) {}
  }
  
  public void setSession(ClientSession paramClientSession)
  {
    this.session = paramClientSession;
  }
  
  public ClientSession getSession()
  {
    return this.session;
  }
  
  public void run()
  {
    try
    {
      while (this.connected)
      {
        int i = this.in.read();
        if (i == -1) {
          throw new EOFException("Manual EOF. Read -1.");
        }
        int j = (i << 8) + this.in.read();
        if (j <= 0) {
          System.out.println("Size of an incoming packet is " + j + " in connection!");
        }
        byte[] arrayOfByte = new byte[j];
        int k = 0;
        do
        {
          k += this.in.read(arrayOfByte, k, arrayOfByte.length - k);
        } while (k < arrayOfByte.length);
        this.session.read(new DataInputPacket(arrayOfByte));
      }
    }
    catch (IOException localIOException)
    {
      connectionLost();
    }
    System.out.println("[Connection Thread finished]");
  }
  
  public void write(DataOutputPacket paramDataOutputPacket)
  {
    write(paramDataOutputPacket.getBytes());
  }
  
  public void write(byte[] paramArrayOfByte)
  {
    try
    {
      synchronized (this.out)
      {
        this.out.write(paramArrayOfByte.length >> 8);
        this.out.write(paramArrayOfByte.length);
        this.out.write(paramArrayOfByte);
        this.out.flush();
      }
    }
    catch (IOException localIOException)
    {
      connectionLost();
    }
  }
  
  private void connectionLost()
  {
    if (this.connected)
    {
      System.out.println("Lost connection: " + this.socket.getInetAddress());
      this.connected = false;
      if (this.session != null) {
        this.session.disconnect(this);
      }
    }
  }
  
  public InputStream getInputStream()
  {
    return this.in;
  }
  
  public boolean isConnected()
  {
    return this.connected;
  }
  
  public String toString()
  {
    if (this.socket == null) {
      return "Connection with null socket";
    }
    return this.socket.getInetAddress() + ": " + this.connected;
  }
  
  public void setNick(String paramString)
  {
    this.name = paramString;
  }
  
  public String getNick()
  {
    return this.name;
  }
  
  public boolean isAdmin()
  {
    return this.admin;
  }
  
  public void setAdmin(boolean paramBoolean)
  {
    this.admin = paramBoolean;
  }
  
  public boolean isSheriff()
  {
    return this.sheriff;
  }
  
  public void setSheriff(boolean paramBoolean)
  {
    this.sheriff = paramBoolean;
  }
}
