package com.aapeli.multiplayer.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ParameterWrapper
{
  private Map parameters;
  private List listeners;
  private int limit;
  public static final int NO_LIMIT = -1;
  
  public ParameterWrapper(int paramInt)
  {
    this.limit = paramInt;
    this.listeners = Collections.synchronizedList(new ArrayList(10));
    this.parameters = Collections.synchronizedMap(new HashMap(10));
  }
  
  public ParameterWrapper(Map paramMap)
  {
    this.parameters = Collections.synchronizedMap(paramMap);
  }
  
  public boolean containsKey(String paramString)
  {
    return this.parameters.containsKey(paramString);
  }
  
  public String getString(String paramString)
  {
    Object localObject = this.parameters.get(paramString);
    if ((localObject instanceof String)) {
      return (String)localObject;
    }
    return null;
  }
  
  public void silentPut(String paramString1, String paramString2)
  {
    if (isAddAllowed(paramString1)) {
      this.parameters.put(paramString1, paramString2);
    }
  }
  
  public void put(String paramString1, String paramString2)
  {
    if (isAddAllowed(paramString1))
    {
      this.parameters.put(paramString1, paramString2);
      throwChanged();
    }
  }
  
  public Object remove(String paramString)
  {
    Object localObject = this.parameters.remove(paramString);
    throwChanged();
    return localObject;
  }
  
  public boolean isInt(String paramString)
  {
    try
    {
      Integer.parseInt(getString(paramString));
      return true;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return false;
  }
  
  public int getInt(String paramString)
  {
    try
    {
      return Integer.parseInt(getString(paramString));
    }
    catch (NumberFormatException localNumberFormatException)
    {
      System.out.println("ERROR: caught NumberFormatException for " + paramString + "=" + getString(paramString));
      localNumberFormatException.printStackTrace();
    }
    return 0;
  }
  
  /* Error */
  public Map getCopy()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 10	com/aapeli/multiplayer/util/ParameterWrapper:parameters	Ljava/util/Map;
    //   4: dup
    //   5: astore_1
    //   6: monitorenter
    //   7: new 7	java/util/HashMap
    //   10: dup
    //   11: aload_0
    //   12: getfield 10	com/aapeli/multiplayer/util/ParameterWrapper:parameters	Ljava/util/Map;
    //   15: invokespecial 30	java/util/HashMap:<init>	(Ljava/util/Map;)V
    //   18: aload_1
    //   19: monitorexit
    //   20: areturn
    //   21: astore_2
    //   22: aload_1
    //   23: monitorexit
    //   24: aload_2
    //   25: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	26	0	this	ParameterWrapper
    //   5	18	1	Ljava/lang/Object;	Object
    //   21	4	2	localObject1	Object
    // Exception table:
    //   from	to	target	type
    //   7	20	21	finally
    //   21	24	21	finally
  }
  
  public void putList(String paramString, List paramList)
  {
    String str = "";
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      str = str + localIterator.next();
      if (localIterator.hasNext()) {
        str = str + ",";
      }
    }
    put(paramString, str);
    throwChanged();
  }
  
  public List getList(String paramString)
  {
    String str = getString(paramString);
    if (str == null) {
      return null;
    }
    if (str.equals("")) {
      return new ArrayList();
    }
    return new ArrayList(Arrays.asList(str.split(",")));
  }
  
  public void putAll(Map paramMap)
  {
    if (isAddAllowed(paramMap))
    {
      this.parameters.putAll(paramMap);
      throwChanged();
    }
  }
  
  public void addChangeListener(ChangeListener paramChangeListener)
  {
    this.listeners.add(paramChangeListener);
  }
  
  public void removeChangeListener(ChangeListener paramChangeListener)
  {
    this.listeners.remove(paramChangeListener);
  }
  
  private void throwChanged()
  {
    throwEvent(new ChangeEvent(this));
  }
  
  private void throwEvent(ChangeEvent paramChangeEvent)
  {
    synchronized (this.listeners)
    {
      Iterator localIterator = this.listeners.iterator();
      while (localIterator.hasNext()) {
        ((ChangeListener)localIterator.next()).stateChanged(paramChangeEvent);
      }
    }
  }
  
  private boolean isAddAllowed(Map paramMap)
  {
    if ((this.limit != -1) && (this.parameters.size() + paramMap.size() > this.limit))
    {
      int i = 0;
      Iterator localIterator = paramMap.keySet().iterator();
      while (localIterator.hasNext()) {
        if (!this.parameters.containsKey(localIterator.next())) {
          i++;
        }
      }
      if (this.parameters.size() + i > this.limit)
      {
        System.out.println("WARNING: ParameterWrapper: limit overflow, addition failed: " + (this.parameters.size() + i) + " / " + this.limit);
        return false;
      }
      return true;
    }
    return true;
  }
  
  private boolean isAddAllowed(String paramString)
  {
    if ((this.limit != -1) && (this.parameters.size() + 1 > this.limit))
    {
      if (!this.parameters.containsKey(paramString))
      {
        System.out.println("WARNING: ParameterWrapper: limit overflow, addition failed: " + (this.parameters.size() + 1) + " / " + this.limit);
        return false;
      }
      return true;
    }
    return true;
  }
}
