package com.aapeli.multiplayer.util;

import java.io.PrintStream;

public class TryableMutex
{
  private final Object mutex = new Object();
  private Thread lockOwner = null;
  private int lockAcquired = 0;
  
  public boolean tryAcquire()
  {
    synchronized (this.mutex)
    {
      if ((this.lockOwner == null) || (this.lockOwner == Thread.currentThread()))
      {
        this.lockOwner = Thread.currentThread();
        this.lockAcquired += 1;
        return true;
      }
      return false;
    }
  }
  
  public void acquire()
  {
    acquire(1);
  }
  
  public void acquire(int paramInt)
  {
    synchronized (this.mutex)
    {
      while ((this.lockOwner != null) && (this.lockOwner != Thread.currentThread())) {
        try
        {
          this.mutex.wait();
        }
        catch (InterruptedException localInterruptedException) {}
      }
      this.lockOwner = Thread.currentThread();
      this.lockAcquired += paramInt;
    }
  }
  
  public void release()
  {
    synchronized (this.mutex)
    {
      if (this.lockOwner != Thread.currentThread())
      {
        System.out.println("WARNING: not owner trying to release tryablemutex! " + this.lockAcquired + " " + this.lockOwner + " " + Thread.currentThread() + " " + this);
        new Exception("DEBUG").printStackTrace();
        return;
      }
      this.lockAcquired -= 1;
      if (this.lockAcquired == 0)
      {
        this.lockOwner = null;
        this.mutex.notifyAll();
      }
    }
  }
  
  public int releaseAll()
  {
    synchronized (this.mutex)
    {
      if (this.lockOwner != Thread.currentThread())
      {
        System.out.println("WARNING: not owner trying to release tryablemutex! " + this.lockAcquired + " " + this.lockOwner + " " + Thread.currentThread() + " " + this);
        new Exception("DEBUG").printStackTrace();
        return 0;
      }
      int i = this.lockAcquired;
      this.lockAcquired = 0;
      this.lockOwner = null;
      this.mutex.notifyAll();
      return i;
    }
  }
  
  public boolean holdedBy(Thread paramThread)
  {
    return this.lockOwner == paramThread;
  }
  
  public boolean currentHolds()
  {
    return this.lockOwner == Thread.currentThread();
  }
}
