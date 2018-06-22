package com.aapeli.multiplayer.client.session.game.video;

import com.aapeli.multiplayer.client.session.game.GameToolkitImpl;
import com.aapeli.multiplayer.client.session.game.PacketBuffer;
import com.aapeli.multiplayer.client.session.game.implInterface.Game;
import com.aapeli.multiplayer.common.monitor.Monitorable;
import com.aapeli.multiplayer.common.network.DataInputPacket;
import com.aapeli.multiplayer.common.network.GameProtocol;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;

public class VideoBuffer
  extends PacketBuffer
  implements Runnable, GameProtocol, Monitorable
{
  private ByteBuffer buffer;
  private boolean running = true;
  private int position = 0;
  private long checkTime;
  private Thread thread;
  
  public VideoBuffer(GameToolkitImpl paramGameToolkitImpl, Game paramGame, long paramLong)
  {
    super(paramGameToolkitImpl, paramGame, paramLong);
  }
  
  public void setBufferTime(int paramInt) {}
  
  public int getBufferTime()
  {
    return 0;
  }
  
  public void add(int paramInt, DataInputPacket paramDataInputPacket)
  {
    switch (paramInt)
    {
    case 72: 
      try
      {
        this.buffer = ByteBuffer.allocate(paramDataInputPacket.readInt());
        System.out.println("Allocated bytebuffer for " + this.buffer.capacity());
        this.thread = new Thread(this);
        this.thread.start();
      }
      catch (IOException localIOException) {}
    case 71: 
      synchronized (this.buffer)
      {
        this.buffer.put(paramDataInputPacket.getData(), 1, paramDataInputPacket.getData().length - 1);
      }
      unlock();
    }
  }
  
  public void run()
  {
    this.checkTime = System.currentTimeMillis();
    System.out.println("Start");
    while (this.running)
    {
      synchronized (this.buffer)
      {
        do
        {
          int i = 0;
          if (this.buffer.position() - this.position >= 2)
          {
            this.buffer.limit(this.buffer.position());
            this.buffer.position(this.position);
            int j = this.buffer.getShort();
            if (this.buffer.remaining() >= j)
            {
              int k = this.buffer.get() & 0xFF;
              byte[] arrayOfByte = new byte[j - 1];
              this.buffer.get(arrayOfByte);
              DataInputPacket localDataInputPacket = new DataInputPacket(arrayOfByte);
              execute(k, localDataInputPacket);
              this.position = this.buffer.position();
              i = 1;
            }
            this.buffer.position(this.buffer.limit());
            this.buffer.limit(this.buffer.capacity());
          }
          try
          {
            Thread.sleep(this.packetGap);
          }
          catch (InterruptedException localInterruptedException) {}
          this.checkTime = System.currentTimeMillis();
          if (i == 0) {
            break;
          }
        } while (this.running);
      }
      lock();
    }
    this.buffer = null;
    System.out.println("[VideoBuffer Thread finished]");
  }
  
  protected void execute(int paramInt, DataInputPacket paramDataInputPacket)
  {
    if (paramInt == 55) {
      try
      {
        String str = paramDataInputPacket.readUTF();
        throwActionEvent(new ActionEvent(this, 55, str));
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
    } else {
      super.execute(paramInt, paramDataInputPacket);
    }
  }
  
  private synchronized void lock()
  {
    if (this.running)
    {
      System.out.println("Lock");
      try
      {
        wait();
      }
      catch (InterruptedException localInterruptedException) {}
      System.out.println("Over");
    }
  }
  
  private synchronized void unlock()
  {
    notifyAll();
  }
  
  public void stop()
  {
    this.running = false;
    unlock();
  }
  
  public void destroy()
  {
    stop();
  }
  
  protected void streamBroken()
  {
    this.broken = true;
    System.out.println("The videostream is broken!");
  }
  
  public long getCheckTime()
  {
    return this.checkTime;
  }
  
  public long getMaxInterval()
  {
    return 5000L;
  }
  
  public Thread getThread()
  {
    return this.thread;
  }
  
  public void setCheckTime(long paramLong)
  {
    this.checkTime = paramLong;
  }
}
