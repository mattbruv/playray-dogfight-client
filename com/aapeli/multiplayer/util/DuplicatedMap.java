package com.aapeli.multiplayer.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DuplicatedMap
  implements Map
{
  private Map map;
  
  public DuplicatedMap(int paramInt)
  {
    this.map = new HashMap(paramInt);
  }
  
  public void clear()
  {
    this.map.clear();
  }
  
  public boolean containsKey(Object paramObject)
  {
    return this.map.containsKey(paramObject);
  }
  
  public boolean containsValue(Object paramObject)
  {
    throw new RuntimeException("Not implemented");
  }
  
  public Set entrySet()
  {
    throw new RuntimeException("Not implemented");
  }
  
  public Object get(Object paramObject)
  {
    MapValue localMapValue = (MapValue)this.map.get(paramObject);
    if (localMapValue != null) {
      return localMapValue.value;
    }
    return null;
  }
  
  public boolean isEmpty()
  {
    return this.map.isEmpty();
  }
  
  public Set keySet()
  {
    return this.map.keySet();
  }
  
  public Object put(Object paramObject1, Object paramObject2)
  {
    MapValue localMapValue = (MapValue)this.map.get(paramObject1);
    if (localMapValue == null) {
      this.map.put(paramObject1, new MapValue(paramObject2));
    } else {
      localMapValue.increaseInstances();
    }
    return null;
  }
  
  public void putAll(Map paramMap)
  {
    throw new RuntimeException("Not implemented");
  }
  
  public Object remove(Object paramObject)
  {
    MapValue localMapValue = (MapValue)this.map.get(paramObject);
    if (localMapValue != null)
    {
      localMapValue.decreaseInstances();
      if (localMapValue.getInstances() <= 0) {
        this.map.remove(paramObject);
      }
      return localMapValue.value;
    }
    return null;
  }
  
  public int size()
  {
    return this.map.size();
  }
  
  public Collection values()
  {
    throw new RuntimeException("Not implemented");
  }
  
  private class MapValue
  {
    Object value;
    int instances;
    
    public MapValue(Object paramObject)
    {
      this.value = paramObject;
      this.instances = 1;
    }
    
    public void increaseInstances()
    {
      this.instances += 1;
    }
    
    public void decreaseInstances()
    {
      this.instances -= 1;
    }
    
    public int getInstances()
    {
      return this.instances;
    }
  }
}
