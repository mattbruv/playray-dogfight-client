package com.aapeli.shared;

import com.aapeli.colorgui.ColorListItem;

public final class UserListItem
{
  private String nick;
  private boolean local;
  private boolean reg;
  private boolean vip;
  private boolean sheriff;
  private int ranking;
  private int orcolor;
  private boolean privately;
  private boolean ignore;
  private boolean nochall;
  private ColorListItem cli;
  
  public UserListItem(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    this(paramString, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, -1);
  }
  
  public UserListItem(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt)
  {
    if (!paramBoolean2)
    {
      paramBoolean3 = paramBoolean4 = 0;
      paramInt = -1;
    }
    this.nick = paramString;
    this.local = paramBoolean1;
    this.reg = paramBoolean2;
    this.vip = paramBoolean3;
    this.sheriff = paramBoolean4;
    this.ranking = paramInt;
    this.privately = (this.ignore = this.nochall = 0);
    this.orcolor = -1;
  }
  
  public String getNick()
  {
    return this.nick;
  }
  
  public boolean isLocal()
  {
    return this.local;
  }
  
  public boolean isRegistered()
  {
    return this.reg;
  }
  
  public boolean isVip()
  {
    return this.vip;
  }
  
  public boolean isSheriff()
  {
    return this.sheriff;
  }
  
  public int getRanking()
  {
    return this.ranking;
  }
  
  public boolean isPrivately()
  {
    return this.privately;
  }
  
  public boolean isIgnore()
  {
    return this.ignore;
  }
  
  public void setOverrideColor(int paramInt)
  {
    this.orcolor = paramInt;
  }
  
  public boolean isNotAcceptingChallenges()
  {
    return this.nochall;
  }
  
  public void setAfterNickText(String paramString)
  {
    String str = this.nick;
    if (paramString != null) {
      str = str + " " + paramString;
    }
    this.cli.setString(str);
  }
  
  protected void setNotAcceptingChallenges(boolean paramBoolean)
  {
    this.nochall = paramBoolean;
  }
  
  protected void setPrivately(boolean paramBoolean)
  {
    this.privately = paramBoolean;
  }
  
  protected void setIgnore(boolean paramBoolean)
  {
    this.ignore = paramBoolean;
  }
  
  protected int getColor(boolean paramBoolean)
  {
    if (this.orcolor >= 0) {
      return this.orcolor;
    }
    int i;
    if (this.local)
    {
      i = !this.vip ? 3 : 6;
    }
    else
    {
      int j = (this.sheriff) && (paramBoolean) ? 1 : 0;
      if ((j != 0) || (this.vip)) {
        i = j != 0 ? 4 : 2;
      } else {
        i = 0;
      }
    }
    if (this.privately) {
      i = 5;
    }
    if (this.ignore) {
      i = 1;
    }
    return i;
  }
  
  protected void setColorListItemReference(ColorListItem paramColorListItem)
  {
    this.cli = paramColorListItem;
  }
  
  protected ColorListItem getColorListItemReference()
  {
    return this.cli;
  }
}
