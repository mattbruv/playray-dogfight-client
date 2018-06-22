package com.aapeli.multiplayer.common.monitor;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ThreadMonitor
  implements Runnable
{
  private static ThreadMonitor threadMonitor = new ThreadMonitor();
  private boolean running = false;
  private List monitorableList = Collections.synchronizedList(new ArrayList(5));
  
  public void run()
  {
    while (this.running)
    {
      long l1 = System.currentTimeMillis();
      ArrayList localArrayList;
      synchronized (this.monitorableList)
      {
        localArrayList = new ArrayList(this.monitorableList);
      }
      ??? = localArrayList.iterator();
      while (((Iterator)???).hasNext())
      {
        Monitorable localMonitorable = (Monitorable)((Iterator)???).next();
        long l2 = l1 - localMonitorable.getCheckTime();
        if (l2 > localMonitorable.getMaxInterval())
        {
          System.out.println("DEBUG: ThreadMonitor: " + localMonitorable + " has exceeded it's max interval! " + l2 + " > " + localMonitorable.getMaxInterval());
          Thread localThread = localMonitorable.getThread();
          String str = "Stacktrace: \n";
          if (localThread != null)
          {
            StackTraceElement[] arrayOfStackTraceElement = localThread.getStackTrace();
            for (int i = 0; i < arrayOfStackTraceElement.length; i++) {
              str = str + arrayOfStackTraceElement[i] + "\n";
            }
          }
          else
          {
            str = str + "thread == " + localThread;
          }
          System.out.println(str);
          localMonitorable.setCheckTime(System.currentTimeMillis() + 240000L);
        }
      }
      try
      {
        Thread.sleep(1000L);
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
    }
  }
  
  public static void addMonitorable(Monitorable paramMonitorable)
  {
    if (threadMonitor == null) {
      threadMonitor = new ThreadMonitor();
    }
    threadMonitor.add(paramMonitorable);
  }
  
  private void add(Monitorable paramMonitorable)
  {
    paramMonitorable.setCheckTime(System.currentTimeMillis());
    this.monitorableList.add(paramMonitorable);
    start();
  }
  
  private synchronized void start()
  {
    if (!this.running)
    {
      this.running = true;
      new Thread(this, "ThreadMonitor").start();
    }
  }
  
  public static void removeMonitorable(Monitorable paramMonitorable)
  {
    if (threadMonitor != null) {
      threadMonitor.remove(paramMonitorable);
    }
  }
  
  private void remove(Monitorable paramMonitorable)
  {
    this.monitorableList.remove(paramMonitorable);
    stop();
  }
  
  private synchronized void stop()
  {
    if (this.monitorableList.size() == 0) {
      this.running = false;
    }
  }
  
  public static void destroy()
  {
    if (threadMonitor != null) {
      threadMonitor.finalize();
    }
  }
  
  public void finalize()
  {
    this.monitorableList.clear();
    this.running = false;
  }
  
  public void threadWakeup() {}
  
  private String getAllStackTraces()
  {
    Map localMap = Thread.getAllStackTraces();
    String str = "All stack traces:\n";
    Iterator localIterator = localMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Thread localThread = (Thread)localIterator.next();
      str = str + localThread + "\n";
      StackTraceElement[] arrayOfStackTraceElement = (StackTraceElement[])localMap.get(localThread);
      for (int i = 0; i < arrayOfStackTraceElement.length; i++) {
        str = str + " " + i + ": " + arrayOfStackTraceElement[i] + "\n";
      }
    }
    str = str + "---------------------------\n";
    return str;
  }
}
