package com.aapeli.multiplayer.client.session.game.video;

import com.aapeli.multiplayer.client.network.Connection;
import com.aapeli.multiplayer.client.session.ClientSession;
import com.aapeli.multiplayer.client.session.ClientSessionManager;
import com.aapeli.multiplayer.client.session.game.GameToolkitImpl;
import com.aapeli.multiplayer.client.session.game.PacketBuffer;
import com.aapeli.multiplayer.client.session.game.implInterface.Game;
import com.aapeli.multiplayer.common.network.DataInputPacket;
import com.aapeli.multiplayer.common.network.DataOutputPacket;
import com.aapeli.multiplayer.common.network.GameProtocol;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class VideoSession
  extends ClientSession
  implements GameProtocol, ActionListener
{
  private GameToolkitImpl toolkit;
  private Game game;
  private boolean running = true;
  private PacketBuffer buffer;
  private boolean active = false;
  private Map gameMap;
  private JPanel panel;
  
  public VideoSession(ClientSessionManager paramClientSessionManager, Map paramMap)
  {
    super(paramClientSessionManager);
    this.gameMap = paramMap;
    this.panel = new JPanel();
    this.panel.setBackground(Color.red);
    this.panel.setLayout(null);
    this.panel.setFocusable(false);
  }
  
  public void start() {}
  
  public void stop()
  {
    if (this.game != null) {
      this.game.stop();
    }
  }
  
  public void logout()
  {
    this.active = false;
    this.buffer.destroy();
    this.panel.removeAll();
  }
  
  public void login(Connection paramConnection, Map paramMap)
  {
    super.login(paramConnection, paramMap);
    this.active = true;
    this.toolkit.setName(paramConnection.getNick());
    requestVideo("test.video");
  }
  
  private void requestVideo(String paramString)
  {
    System.out.println("Requesting video: " + paramString);
    try
    {
      DataOutputPacket localDataOutputPacket = new DataOutputPacket();
      localDataOutputPacket.writeByte(70);
      localDataOutputPacket.writeUTF(paramString);
      if (this.active) {
        this.connection.write(localDataOutputPacket);
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  public JComponent getScreen()
  {
    return this.panel;
  }
  
  public void read(DataInputPacket paramDataInputPacket)
  {
    try
    {
      int i = paramDataInputPacket.read();
      switch (i)
      {
      case 72: 
        try
        {
          String str = paramDataInputPacket.readUTF();
          System.out.println("Starting to play video for game: " + str + " " + this.gameMap);
          Class localClass = (Class)this.gameMap.get(str);
          this.game = ((Game)localClass.newInstance());
          HashMap localHashMap = new HashMap(1);
          localHashMap.put("type", "watch");
          this.panel.add(this.game.getScreen());
          Rectangle localRectangle = this.panel.getBounds();
          this.game.getScreen().setBounds(localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
          this.game.start();
          this.buffer = new VideoBuffer(this.toolkit, this.game, 10L);
          this.buffer.addActionListener(this);
          this.buffer.add(i, paramDataInputPacket);
        }
        catch (InstantiationException localInstantiationException)
        {
          localInstantiationException.printStackTrace();
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          localIllegalAccessException.printStackTrace();
        }
      case 71: 
        this.buffer.add(i, paramDataInputPacket);
        break;
      default: 
        System.out.println("Lost stream position! Read type " + i + ", datain len: " + paramDataInputPacket.getData().length);
        for (int j = 0; (j < paramDataInputPacket.getData().length) && (j < 8); j++) {
          System.out.print(paramDataInputPacket.getData()[j] + " ");
        }
        System.out.println();
      }
    }
    catch (IOException localIOException) {}
  }
  
  public void destroy()
  {
    this.running = false;
    if (this.game != null) {
      this.game.destroy();
    }
    if (this.buffer != null) {
      this.buffer.destroy();
    }
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getID() == 58)
    {
      DataOutputPacket localDataOutputPacket = new DataOutputPacket();
      try
      {
        localDataOutputPacket.writeByte(58);
      }
      catch (IOException localIOException) {}
      if (this.active) {
        this.connection.write(localDataOutputPacket);
      }
    }
    else if (paramActionEvent.getID() != 55) {}
  }
  
  private class DataElement
  {
    private DataInput in;
    private int time;
    
    private DataElement(int paramInt, DataInput paramDataInput)
    {
      this.time = paramInt;
      this.in = paramDataInput;
    }
    
    private int getType()
    {
      return this.time;
    }
    
    private DataInput getIn()
    {
      return this.in;
    }
  }
}
