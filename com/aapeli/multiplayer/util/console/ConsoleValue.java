package com.aapeli.multiplayer.util.console;

import java.util.ArrayList;
import java.util.List;

public class ConsoleValue
{
  private String value;
  
  public ConsoleValue(String paramString)
  {
    this.value = paramString;
  }
  
  public String getString()
  {
    return this.value;
  }
  
  public boolean isInt()
  {
    if (!exists()) {
      return false;
    }
    try
    {
      Integer.parseInt(this.value);
      return true;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return false;
  }
  
  public boolean exists()
  {
    return this.value != null;
  }
  
  public int getInt()
  {
    try
    {
      return Integer.parseInt(this.value);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw localNumberFormatException;
    }
  }
  
  public List getList()
  {
    ArrayList localArrayList = new ArrayList(4);
    int i = 0;
    int j = 0;
    if (this.value == null) {
      return localArrayList;
    }
    for (int k = 0; k < this.value.length(); k++)
    {
      if (this.value.charAt(k) == '"') {
        i = i == 0 ? 1 : 0;
      }
      if ((i == 0) && (this.value.charAt(k) == ' '))
      {
        String str2 = this.value.substring(j, k);
        str2 = str2.replaceAll("\"", "");
        localArrayList.add(str2);
        do
        {
          k++;
        } while ((k < this.value.length()) && (this.value.charAt(k) == ' '));
        j = k;
        k--;
      }
    }
    if (j < this.value.length())
    {
      String str1 = this.value.substring(j, this.value.length());
      str1 = str1.replaceAll("\"", "");
      localArrayList.add(str1);
    }
    return localArrayList;
  }
}
