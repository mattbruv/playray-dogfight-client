package com.aapeli.multiplayer.common.chat;

public class ChatUser
{
  private String name;
  private boolean reg;
  private boolean vip;
  private boolean sheriff;
  
  public ChatUser(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.name = paramString;
    this.reg = paramBoolean1;
    this.vip = paramBoolean2;
    this.sheriff = paramBoolean3;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public boolean isReg()
  {
    return this.reg;
  }
  
  public boolean isSheriff()
  {
    return this.sheriff;
  }
  
  public boolean isVip()
  {
    return this.vip;
  }
  
  public String getEncodedData()
  {
    return (this.reg ? "1" : "0") + (this.vip ? "1" : "0") + (this.sheriff ? "1" : "0");
  }
}
