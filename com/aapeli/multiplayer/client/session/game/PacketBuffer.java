package com.aapeli.multiplayer.client.session.game;

import com.aapeli.multiplayer.client.session.game.implInterface.Entity;
import com.aapeli.multiplayer.client.session.game.implInterface.Game;
import com.aapeli.multiplayer.common.network.DataInputPacket;
import com.aapeli.multiplayer.common.network.GameProtocol;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public abstract class PacketBuffer
  implements GameProtocol
{
  protected Game game;
  protected GameToolkitImpl toolkit;
  protected int lastChangeSyncId = -1;
  protected boolean broken = false;
  protected long packetGap;
  protected ActionListener actionListener;
  
  protected PacketBuffer(GameToolkitImpl paramGameToolkitImpl, Game paramGame, long paramLong)
  {
    this.packetGap = paramLong;
    this.toolkit = paramGameToolkitImpl;
    this.game = paramGame;
  }
  
  public abstract void setBufferTime(int paramInt);
  
  public abstract int getBufferTime();
  
  public abstract void add(int paramInt, DataInputPacket paramDataInputPacket);
  
  public abstract void destroy();
  
  public abstract void stop();
  
  public void addActionListener(ActionListener paramActionListener)
  {
    this.actionListener = paramActionListener;
  }
  
  public void removeActionListener(ActionListener paramActionListener)
  {
    this.actionListener = null;
  }
  
  protected void print(byte[] paramArrayOfByte)
  {
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      System.out.print(paramArrayOfByte[i] + ", ");
      if (i % 20 == 0) {
        System.out.println();
      }
    }
    System.out.println();
  }
  
  protected void execute(int paramInt, DataInput paramDataInput)
  {
    int n;
    int i1;
    Entity localEntity4;
    HashMap localHashMap;
    int i3;
    switch (paramInt)
    {
    case 52: 
      if (!this.broken) {
        try
        {
          int i = paramDataInput.readByte() & 0xFF;
          if ((this.lastChangeSyncId == -1) || (i == (this.lastChangeSyncId + 1) % 256))
          {
            this.lastChangeSyncId = i;
          }
          else
          {
            System.out.println("A missing change sync packet detected! Last was " + this.lastChangeSyncId + " and read " + i);
            this.lastChangeSyncId = i;
          }
          int m = paramDataInput.readShort() & 0xFFFF;
          for (n = 0; n < m; n++)
          {
            i1 = paramDataInput.readShort() & 0xFFFF;
            int i2;
            if (i1 == 4)
            {
              i2 = paramDataInput.readShort() & 0xFFFF;
              int i4 = paramDataInput.readByte() & 0xFF;
              localEntity4 = this.toolkit.entityForType(i4);
              localEntity4.setId(i2);
              localEntity4.readSync(paramDataInput, 2);
              this.toolkit.addEntity(localEntity4);
            }
            else if (i1 == 5)
            {
              i2 = paramDataInput.readShort() & 0xFFFF;
              this.toolkit.removeEntity(i2);
            }
            else
            {
              Entity localEntity1 = this.toolkit.getEntity(i1);
              if (localEntity1 == null)
              {
                System.out.println("ERR entity null in changesync: " + i1 + " " + this.toolkit.getEntities().size());
                print(((DataInputPacket)paramDataInput).getData());
                streamBroken();
                break;
              }
              localEntity1.readSync(paramDataInput, 0);
            }
          }
        }
        catch (IOException localIOException1)
        {
          localIOException1.printStackTrace();
        }
      }
      break;
    case 54: 
      try
      {
        if (this.broken)
        {
          System.out.println("Recovered from a broken stream. Either there was lag or your connection is too slow!");
          this.broken = false;
        }
        int j = paramDataInput.readShort() & 0xFFFF;
        System.out.println("First " + j);
        localHashMap = new HashMap(j + 10);
        for (n = 0; n < j; n++)
        {
          i1 = paramDataInput.readShort() & 0xFFFF;
          i3 = paramDataInput.readByte() & 0xFF;
          Entity localEntity3 = this.toolkit.entityForType(i3);
          localEntity3.setToolkit(this.toolkit);
          localEntity3.setId(i1);
          localEntity3.readSync(paramDataInput, 2);
          localHashMap.put(new Integer(localEntity3.getId()), localEntity3);
        }
        this.toolkit.setEntities(localHashMap);
      }
      catch (IOException localIOException2)
      {
        localIOException2.printStackTrace();
      }
    case 51: 
      if (!this.broken) {
        try
        {
          int k = paramDataInput.readShort() & 0xFFFF;
          localHashMap = new HashMap(k + 10);
          for (n = 0; n < k; n++)
          {
            i1 = paramDataInput.readShort() & 0xFFFF;
            if (i1 == 4)
            {
              i3 = paramDataInput.readShort() & 0xFFFF;
              int i5 = paramDataInput.readByte() & 0xFF;
              localEntity4 = this.toolkit.entityForType(i5);
              localEntity4.setId(i3);
              localEntity4.readSync(paramDataInput, 2);
              localHashMap.put(new Integer(localEntity4.getId()), localEntity4);
            }
            else if (i1 == 5)
            {
              paramDataInput.readShort();
            }
            else
            {
              Entity localEntity2 = this.toolkit.getEntity(i1);
              if (localEntity2 == null)
              {
                System.out.println("ERR unknown entity in full sync " + i1);
                print(((DataInputPacket)paramDataInput).getData());
                streamBroken();
                break;
              }
              localEntity2.readSync(paramDataInput, 1);
              localHashMap.put(new Integer(localEntity2.getId()), localEntity2);
            }
          }
          this.toolkit.setEntities(localHashMap);
        }
        catch (IOException localIOException3)
        {
          localIOException3.printStackTrace();
        }
      }
      break;
    case 53: 
    default: 
      System.out.println("Unidentified transfertype at execute(): " + paramInt);
    }
    this.game.refresh();
  }
  
  protected void throwActionEvent(ActionEvent paramActionEvent)
  {
    if (this.actionListener != null) {
      this.actionListener.actionPerformed(paramActionEvent);
    }
  }
  
  protected abstract void streamBroken();
}
