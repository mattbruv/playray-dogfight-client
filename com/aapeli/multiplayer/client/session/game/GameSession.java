package com.aapeli.multiplayer.client.session.game;

import com.aapeli.client.BadWordFilter;
import com.aapeli.multiplayer.client.network.Connection;
import com.aapeli.multiplayer.client.resources.Loadable;
import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.client.session.ClientSession;
import com.aapeli.multiplayer.client.session.ClientSessionManager;
import com.aapeli.multiplayer.client.session.game.implInterface.Game;
import com.aapeli.multiplayer.common.network.DataInputPacket;
import com.aapeli.multiplayer.common.network.DataOutputPacket;
import com.aapeli.multiplayer.common.network.GameProtocol;
import com.aapeli.multiplayer.util.console.Console;
import com.aapeli.multiplayer.util.console.ConsoleEvent;
import com.aapeli.multiplayer.util.console.ConsoleListener;
import com.aapeli.multiplayer.util.console.ConsoleValue;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;

public class GameSession
  extends ClientSession
  implements GameProtocol, EncodedEventListener, ActionListener, Loadable, ConsoleListener, GameToolkitListener
{
  private GameToolkitImpl toolkit;
  private Game game;
  private InputEncoder inputEncoder;
  private PacketBuffer buffer;
  private boolean active = false;
  private Console console;
  private int started = 0;
  private Object broadcastLock = new Object();
  private boolean watch = false;
  private boolean initialized = false;
  private BadWordFilter badWordFilter;
  
  public GameSession(ClientSessionManager paramClientSessionManager, Game paramGame)
  {
    super(paramClientSessionManager);
    this.game = paramGame;
    this.badWordFilter = new BadWordFilter(Localization.getInstance().getTextManager());
  }
  
  public void init()
  {
    this.inputEncoder = new InputEncoder(this);
    this.console = new Console();
    this.console.put("buffertime", "50");
    this.console.addStyle("info", new Color(0, 0, 0));
    this.console.addConsoleListener(this);
    this.toolkit = new GameToolkitImpl(this, this.console);
    this.game.init(this.toolkit);
    this.buffer = new SyncBuffer(this.toolkit, this.game);
    this.buffer.addActionListener(this);
    ConsoleValue localConsoleValue = this.console.get("buffertime");
    if (localConsoleValue.isInt()) {
      this.buffer.setBufferTime(localConsoleValue.getInt());
    }
    this.initialized = true;
  }
  
  public synchronized void start()
  {
    if (++this.started > 0) {
      this.game.start();
    }
  }
  
  public synchronized void stop()
  {
    if (--this.started == 0)
    {
      this.game.stop();
      this.toolkit.reset();
    }
  }
  
  public void logout()
  {
    JComponent localJComponent = this.game.getScreen();
    if (!this.watch)
    {
      localJComponent.removeKeyListener(this.inputEncoder);
      localJComponent.removeMouseListener(this.inputEncoder);
    }
    this.active = false;
    this.buffer.stop();
  }
  
  public void login(Connection paramConnection, Map paramMap)
  {
    super.login(paramConnection, paramMap);
    this.watch = false;
    if (paramMap != null)
    {
      localObject = (String)paramMap.get("param_watch");
      if ((localObject != null) && (((String)localObject).equals("yes"))) {
        this.watch = true;
      }
    }
    this.active = true;
    this.game.login(paramMap);
    Object localObject = this.game.getScreen();
    if (!this.watch)
    {
      ((JComponent)localObject).addKeyListener(this.inputEncoder);
      ((JComponent)localObject).addMouseListener(this.inputEncoder);
    }
    this.toolkit.setName(paramConnection.getNick());
    ((SyncBuffer)this.buffer).start();
  }
  
  public JComponent getScreen()
  {
    return this.game.getScreen();
  }
  
  public void read(DataInputPacket paramDataInputPacket)
  {
    try
    {
      int i = paramDataInputPacket.readByte();
      String str3;
      switch (i)
      {
      case 55: 
        int j = paramDataInputPacket.readByte();
        String str1 = paramDataInputPacket.readUTF();
        if (j == 5)
        {
          this.console.insertLine(str1, "info");
        }
        else
        {
          String str2 = str1.substring(0, str1.indexOf("\t"));
          str1 = str1.substring(str1.indexOf("\t") + 1);
          this.toolkit.readTextMessage(j, str2, str1);
        }
        break;
      case 56: 
        long l = paramDataInputPacket.readLong();
        DataOutputPacket localDataOutputPacket1 = new DataOutputPacket();
        localDataOutputPacket1.writeByte(56);
        localDataOutputPacket1.writeLong(l);
        broadcast(localDataOutputPacket1);
        break;
      case 57: 
        str3 = paramDataInputPacket.readUTF();
        int k = paramDataInputPacket.readByte();
        String str4 = paramDataInputPacket.readUTF();
        DataOutputPacket localDataOutputPacket2 = new DataOutputPacket();
        try
        {
          localDataOutputPacket2.writeByte(62);
        }
        catch (IOException localIOException2) {}
        int m = 0;
        synchronized (this.broadcastLock)
        {
          if (this.active)
          {
            this.active = false;
            this.connection.write(localDataOutputPacket2);
            m = 1;
          }
        }
        if (m != 0)
        {
          ??? = new HashMap(1);
          ((Map)???).put("param_quit_reason", str4);
          ((Map)???).put("param_quit_type", new Integer(k));
          stop();
          logout();
          this.manager.transfer(this.connection, str3, (Map)???);
        }
        break;
      case 61: 
        str3 = paramDataInputPacket.readUTF();
        stop();
        logout();
        this.manager.transfer(this.connection, str3);
        break;
      case 51: 
      case 52: 
      case 54: 
        this.buffer.add(i, paramDataInputPacket);
        this.toolkit.packetRead(i);
        break;
      case 53: 
      case 58: 
      case 59: 
      case 60: 
      default: 
        System.out.println("Lost stream position! Read type " + i);
        this.buffer.streamBroken();
      }
    }
    catch (IOException localIOException1) {}
  }
  
  private void broadcast(DataOutputPacket paramDataOutputPacket)
  {
    synchronized (this.broadcastLock)
    {
      if (this.active) {
        this.connection.write(paramDataOutputPacket);
      }
    }
  }
  
  public void eventPerformed(byte[] paramArrayOfByte)
  {
    if (this.connection != null)
    {
      DataOutputPacket localDataOutputPacket = new DataOutputPacket();
      try
      {
        localDataOutputPacket.writeByte(53);
        for (int i = 0; i < paramArrayOfByte.length; i++) {
          localDataOutputPacket.writeByte(paramArrayOfByte[i]);
        }
      }
      catch (IOException localIOException) {}
      broadcast(localDataOutputPacket);
    }
  }
  
  public void writeError(String paramString)
  {
    if (this.connection != null)
    {
      DataOutputPacket localDataOutputPacket = new DataOutputPacket();
      try
      {
        localDataOutputPacket.writeByte(60);
        localDataOutputPacket.writeUTF(paramString);
      }
      catch (IOException localIOException) {}
      if (this.active)
      {
        broadcast(localDataOutputPacket);
        System.out.println("Wrote report to server.");
      }
    }
  }
  
  public void destroy()
  {
    if (this.game != null) {
      this.game.destroy();
    }
    if (this.buffer != null) {
      this.buffer.destroy();
    }
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getID() == 59)
    {
      DataOutputPacket localDataOutputPacket = new DataOutputPacket();
      try
      {
        localDataOutputPacket.writeByte(59);
      }
      catch (IOException localIOException) {}
      if (this.active)
      {
        broadcast(localDataOutputPacket);
        System.out.println("Sent request first sync");
      }
    }
  }
  
  public double getLoadStatus()
  {
    double d = this.game.getLoadStatus();
    if ((!this.initialized) && (d > 0.95D)) {
      d = 0.95D;
    }
    return d;
  }
  
  public void startLoading()
  {
    new Thread(new ResourceLoader(null), "ResourceLoader").start();
  }
  
  public void consoleActionPerformed(ConsoleEvent paramConsoleEvent)
  {
    if ((paramConsoleEvent.getKey().equals("buffertime")) && (this.buffer != null) && (paramConsoleEvent.getValue().isInt())) {
      this.buffer.setBufferTime(paramConsoleEvent.getValue().getInt());
    }
    if (this.connection != null)
    {
      DataOutputPacket localDataOutputPacket = new DataOutputPacket();
      try
      {
        localDataOutputPacket.writeByte(55);
        localDataOutputPacket.writeByte(5);
        localDataOutputPacket.writeUTF(paramConsoleEvent.encode());
      }
      catch (IOException localIOException) {}
      broadcast(localDataOutputPacket);
    }
  }
  
  public void quit()
  {
    DataOutputPacket localDataOutputPacket = new DataOutputPacket();
    try
    {
      localDataOutputPacket.writeByte(58);
    }
    catch (IOException localIOException) {}
    synchronized (this.broadcastLock)
    {
      if (this.active)
      {
        this.active = false;
        this.connection.write(localDataOutputPacket);
      }
    }
  }
  
  public void sendTextMessage(int paramInt, String paramString)
  {
    if (this.connection != null)
    {
      paramString = this.badWordFilter.filter(paramString);
      DataOutputPacket localDataOutputPacket = new DataOutputPacket();
      try
      {
        localDataOutputPacket.writeByte(55);
        localDataOutputPacket.writeByte(paramInt);
        localDataOutputPacket.writeUTF(paramString);
      }
      catch (IOException localIOException) {}
      broadcast(localDataOutputPacket);
    }
  }
  
  public Game getGame()
  {
    return this.game;
  }
  
  private class ResourceLoader
    implements Runnable
  {
    private ResourceLoader() {}
    
    public void run()
    {
      GameSession.this.game.loadResources();
      GameSession.this.init();
    }
  }
}
