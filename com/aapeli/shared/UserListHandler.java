package com.aapeli.shared;

public abstract interface UserListHandler
{
  public abstract void openPlayerCard(String paramString);
  
  public abstract void adminCommand(String paramString1, String paramString2);
  
  public abstract void adminCommand(String paramString1, String paramString2, String paramString3);
}
