package com.aapeli.multiplayer.client.session.game;

import com.aapeli.multiplayer.client.session.game.implInterface.Game;
import com.aapeli.multiplayer.common.network.DataInputPacket;
import com.aapeli.multiplayer.common.network.GameProtocol;
import java.awt.event.ActionEvent;
import java.io.DataInput;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SyncBuffer
  extends PacketBuffer
  implements GameProtocol, Runnable
{
  private List buffer = Collections.synchronizedList(new ArrayList(10));
  private long executionTime = -1L;
  private int bufferTime = 0;
  private static final long RE_REQUEST_FIRST_SYNC_TIME = 3000L;
  private long lastTime;
  private int adjust = 0;
  private boolean running;
  private Thread thread;
  private int packets;
  private long packetStartTime;
  private long brokenTime;
  
  public SyncBuffer(GameToolkitImpl paramGameToolkitImpl, Game paramGame)
  {
    super(paramGameToolkitImpl, paramGame, 0L);
  }
  
  public void start()
  {
    this.buffer.clear();
    this.packetStartTime = 0L;
    this.packets = 0;
    this.lastTime = System.currentTimeMillis();
    this.executionTime = -1L;
    this.brokenTime = 0L;
    this.adjust = 0;
    this.lastChangeSyncId = -1;
    this.broken = false;
    this.running = true;
    this.thread = new Thread(this, "SyncBuffer");
    this.thread.start();
  }
  
  public void setBufferTime(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    this.bufferTime = paramInt;
    System.out.println("BUFFER TIME SET TO: " + paramInt);
  }
  
  public int getBufferTime()
  {
    return this.bufferTime;
  }
  
  public void add(int paramInt, DataInputPacket paramDataInputPacket)
  {
    if (this.bufferTime == 0)
    {
      schedule(0L, paramInt, paramDataInputPacket);
    }
    else
    {
      long l1 = System.currentTimeMillis();
      if (this.packetStartTime == 0L)
      {
        this.packetStartTime = l1;
      }
      else
      {
        this.packets += 1;
        this.packetGap = ((l1 - this.packetStartTime) / this.packets);
        if ((this.packets < 5) && (this.packetGap > 100L)) {
          this.packetGap = 100L;
        }
      }
      this.lastTime += this.packetGap;
      long l2 = this.lastTime - l1;
      int i = (int)(l2 * 10L / this.bufferTime);
      switch (i)
      {
      case 0: 
        this.adjust = 4;
        break;
      case 1: 
        this.adjust = 3;
        break;
      case 2: 
        this.adjust = 2;
        break;
      case 3: 
        this.adjust = 1;
        break;
      case 4: 
      case 5: 
        this.adjust = 0;
        break;
      case 6: 
        this.adjust = -1;
        break;
      case 7: 
        this.adjust = -2;
        break;
      case 8: 
        this.adjust = -3;
        break;
      case 9: 
        this.adjust = -4;
        break;
      default: 
        if (i < 0)
        {
          this.adjust = 4;
          if (this.lastTime + this.adjust < l1) {
            this.lastTime = (l1 - this.adjust);
          }
          if (l1 - this.packetStartTime > 2000L) {
            System.out.print("?");
          }
        }
        else
        {
          this.adjust = -4;
          if (l1 - this.packetStartTime > 2000L) {
            System.out.print("!");
          }
        }
        break;
      }
      this.lastTime += this.adjust;
      schedule(this.lastTime, paramInt, paramDataInputPacket);
    }
  }
  
  private void schedule(long paramLong, int paramInt, DataInput paramDataInput)
  {
    BufferPacket localBufferPacket = new BufferPacket(paramInt, paramLong, paramDataInput);
    synchronized (this.buffer)
    {
      this.buffer.add(localBufferPacket);
      if ((this.executionTime < 0L) || (this.executionTime > paramLong)) {
        this.executionTime = paramLong;
      }
      synchronized (this)
      {
        notifyAll();
      }
    }
  }
  
  public void run()
  {
    long l1 = System.currentTimeMillis();
    int i = 0;
    while (this.running)
    {
      long l2;
      if (this.broken)
      {
        l2 = System.currentTimeMillis();
        if (this.brokenTime + 3000L < l2) {
          streamBroken();
        }
      }
      if (this.executionTime < 0L)
      {
        lock(20L);
      }
      else
      {
        l2 = System.currentTimeMillis();
        if (this.executionTime <= l2)
        {
          if (this.buffer.size() == 0)
          {
            System.out.println("buffer.size()==" + this.buffer.size() + " in SyncBuffer.run(). " + this.executionTime + ", " + l2 + " ... skipping");
            this.executionTime = -1L;
          }
          else
          {
            BufferPacket localBufferPacket = (BufferPacket)this.buffer.remove(0);
            execute(localBufferPacket.getType(), (DataInput)localBufferPacket.getData());
            synchronized (this.buffer)
            {
              if (this.buffer.size() > 0) {
                this.executionTime = ((BufferPacket)this.buffer.get(0)).getTime();
              } else {
                this.executionTime = -1L;
              }
            }
            i++;
            if (i % 1000 == 0)
            {
              i = 0;
              l1 = System.currentTimeMillis();
            }
          }
        }
        else {
          lock(this.executionTime - l2);
        }
      }
    }
    System.out.println("[SyncBuffer Thread finished]");
  }
  
  private synchronized void lock(long paramLong)
  {
    if (this.running) {
      try
      {
        wait(paramLong);
      }
      catch (InterruptedException localInterruptedException) {}
    }
  }
  
  protected void streamBroken()
  {
    System.out.println("Stream broken, fixing...");
    this.brokenTime = System.currentTimeMillis();
    this.broken = true;
    throwActionEvent(new ActionEvent(this, 59, null));
  }
  
  public void destroy()
  {
    stop();
  }
  
  public void stop()
  {
    this.running = false;
    synchronized (this)
    {
      notifyAll();
    }
  }
  
  private class BufferPacket
  {
    private Object data;
    private long time;
    private int type;
    
    public BufferPacket(int paramInt, long paramLong, Object paramObject)
    {
      this.type = paramInt;
      this.time = paramLong;
      this.data = paramObject;
    }
    
    public int getType()
    {
      return this.type;
    }
    
    public long getTime()
    {
      return this.time;
    }
    
    public Object getData()
    {
      return this.data;
    }
  }
}
