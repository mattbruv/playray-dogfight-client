package com.aapeli.multiplayer.util;

import java.util.ArrayList;
import java.util.List;

public class SimpleTimeQueue
{
  private List elements;
  
  public SimpleTimeQueue(int paramInt)
  {
    this.elements = new ArrayList(paramInt);
  }
  
  public List getAll()
  {
    ArrayList localArrayList;
    synchronized (this.elements)
    {
      localArrayList = new ArrayList(this.elements);
      this.elements.clear();
    }
    return localArrayList;
  }
  
  public void add(Object paramObject)
  {
    synchronized (this.elements)
    {
      this.elements.add(paramObject);
    }
  }
  
  public void clear()
  {
    synchronized (this.elements)
    {
      this.elements.clear();
    }
  }
}
