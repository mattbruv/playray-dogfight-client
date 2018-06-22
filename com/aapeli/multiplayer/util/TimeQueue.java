package com.aapeli.multiplayer.util;

import java.util.ArrayList;
import java.util.List;

public class TimeQueue
{
  private List elements;
  private volatile long oldest;
  private volatile long newest;
  private volatile long lastFetch;
  private long lifetime = 0L;
  private Object clearLock = new Object();
  
  public TimeQueue(int paramInt)
  {
    this(paramInt, 0);
  }
  
  public TimeQueue(int paramInt1, int paramInt2)
  {
    this.elements = new ArrayList(paramInt1);
    this.lifetime = paramInt2;
  }
  
  public List retrieveBefore(long paramLong)
  {
    synchronized (this.elements)
    {
      Object localObject1 = new ArrayList(this.elements);
      int i = this.elements.size();
      this.lastFetch = System.currentTimeMillis();
      long l1 = this.newest;
      long l2 = this.oldest;
      if (((List)localObject1).size() == 0) {
        return (List)localObject1;
      }
      if (paramLong <= l2)
      {
        ((List)localObject1).clear();
        return (List)localObject1;
      }
      if (paramLong > l1)
      {
        this.elements.clear();
        return (List)localObject1;
      }
      int j = 0;
      if ((paramLong > l2) && (l1 > l2)) {}
      for (j = (int)(paramLong - l2) * (((List)localObject1).size() - 1) / (int)(l1 - l2); (j > 0) && (!((Element)((List)localObject1).get(j)).isBefore(paramLong)); j--) {}
      while ((j < ((List)localObject1).size()) && (((Element)((List)localObject1).get(j)).isBefore(paramLong))) {
        j++;
      }
      if (j == 0)
      {
        ((List)localObject1).clear();
        return (List)localObject1;
      }
      for (int k = 0; k < j; k++) {
        this.elements.remove(0);
      }
      if (this.elements.size() > 0) {
        this.oldest = ((Element)this.elements.get(0)).getTime();
      }
      localObject1 = ((List)localObject1).subList(0, j);
      return (List)localObject1;
    }
  }
  
  public void add(Object paramObject)
  {
    add(System.currentTimeMillis(), paramObject);
  }
  
  public void add(long paramLong, Object paramObject)
  {
    synchronized (this.elements)
    {
      if (this.lastFetch == paramLong) {
        paramLong += 1L;
      }
      if (this.lifetime != 0L) {
        while ((this.oldest + this.lifetime < paramLong) && (this.elements.size() > 0) && (((Element)this.elements.get(0)).getTime() + this.lifetime < paramLong))
        {
          this.elements.remove(0);
          if (this.elements.size() > 0) {
            this.oldest = ((Element)this.elements.get(0)).getTime();
          }
        }
      }
      if (this.elements.size() == 0) {
        this.oldest = paramLong;
      }
      this.newest = paramLong;
      this.elements.add(new Element(paramLong, paramObject));
    }
  }
  
  public void clear()
  {
    synchronized (this.elements)
    {
      this.elements.clear();
    }
  }
  
  /* Error */
  public int size()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 8	com/aapeli/multiplayer/util/TimeQueue:elements	Ljava/util/List;
    //   4: dup
    //   5: astore_1
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 8	com/aapeli/multiplayer/util/TimeQueue:elements	Ljava/util/List;
    //   11: invokeinterface 10 1 0
    //   16: aload_1
    //   17: monitorexit
    //   18: ireturn
    //   19: astore_2
    //   20: aload_1
    //   21: monitorexit
    //   22: aload_2
    //   23: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	24	0	this	TimeQueue
    //   5	16	1	Ljava/lang/Object;	Object
    //   19	4	2	localObject1	Object
    // Exception table:
    //   from	to	target	type
    //   7	18	19	finally
    //   19	22	19	finally
  }
  
  public class Element
  {
    private Object object;
    private long time;
    
    public Element(long paramLong, Object paramObject)
    {
      this.time = paramLong;
      this.object = paramObject;
    }
    
    public boolean isAfter(long paramLong)
    {
      return this.time > paramLong;
    }
    
    public boolean isBefore(long paramLong)
    {
      return this.time < paramLong;
    }
    
    public long getTime()
    {
      return this.time;
    }
    
    public Object getObject()
    {
      return this.object;
    }
  }
}
