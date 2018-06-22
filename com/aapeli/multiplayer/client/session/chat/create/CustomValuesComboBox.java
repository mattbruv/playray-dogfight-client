package com.aapeli.multiplayer.client.session.chat.create;

public class CustomValuesComboBox
  extends LabelledComboBox
{
  public CustomValuesComboBox(String paramString, String[] paramArrayOfString, boolean paramBoolean, int paramInt)
  {
    super(paramString, paramArrayOfString, paramBoolean, paramInt);
  }
  
  public CustomValuesComboBox(String paramString, String[] paramArrayOfString, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    super(paramString, paramArrayOfString, paramBoolean, paramInt1, paramInt2);
  }
  
  public void setValue(String paramString)
  {
    if (paramString.indexOf("\t") == -1)
    {
      super.setValue(paramString);
    }
    else
    {
      String[] arrayOfString1 = paramString.split("\t", 2);
      String[] arrayOfString2 = arrayOfString1[1].split("\t");
      setValues(arrayOfString2);
      super.setValue(arrayOfString1[0]);
    }
  }
}
