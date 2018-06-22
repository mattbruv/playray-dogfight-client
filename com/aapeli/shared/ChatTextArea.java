package com.aapeli.shared;

import com.aapeli.client.BadWordFilter;
import com.aapeli.client.TextManager;
import java.awt.Font;
import java.util.Hashtable;

public class ChatTextArea
  extends ColorTextArea
{
  public static final Font DEFAULT_FONT = new Font("Dialog", 0, 12);
  public static final Font SMALL_FONT = new Font("Dialog", 0, 11);
  private TextManager tm;
  private BadWordFilter bwf;
  private Hashtable usercolors;
  
  public ChatTextArea(TextManager paramTextManager, int paramInt1, int paramInt2)
  {
    this(paramTextManager, null, paramInt1, paramInt2, null);
  }
  
  public ChatTextArea(TextManager paramTextManager, int paramInt1, int paramInt2, Font paramFont)
  {
    this(paramTextManager, null, paramInt1, paramInt2, paramFont);
  }
  
  public ChatTextArea(TextManager paramTextManager, BadWordFilter paramBadWordFilter, int paramInt1, int paramInt2)
  {
    this(paramTextManager, paramBadWordFilter, paramInt1, paramInt2, null);
  }
  
  public ChatTextArea(TextManager paramTextManager, BadWordFilter paramBadWordFilter, int paramInt1, int paramInt2, Font paramFont)
  {
    super(paramInt1, paramInt2, paramFont != null ? paramFont : DEFAULT_FONT);
    this.tm = paramTextManager;
    this.bwf = paramBadWordFilter;
    this.usercolors = new Hashtable();
  }
  
  public void addOwnSay(String paramString1, String paramString2)
  {
    realAddSay(3, paramString1, paramString2, true);
  }
  
  public void addOwnSayPrivately(String paramString1, String paramString2, String paramString3)
  {
    realAddSay(3, paramString1, paramString2, paramString3, true);
  }
  
  public void addSay(String paramString1, String paramString2)
  {
    realAddSay(0, paramString1, paramString2, false);
  }
  
  public void addSayPrivately(String paramString1, String paramString2, String paramString3)
  {
    realAddSay(5, paramString1, paramString2, paramString3, false);
  }
  
  public void addJoinMessage(String paramString)
  {
    realAddMessage(2, paramString);
  }
  
  public void addPartMessage(String paramString)
  {
    realAddMessage(1, paramString);
  }
  
  public void addStartedGameMessage(String paramString)
  {
    realAddMessage(7, paramString);
  }
  
  public void addSheriffSay(String paramString)
  {
    addBoldLine(6, this.tm.getShared("Chat_SheriffSay", paramString));
  }
  
  public void addServerSay(String paramString)
  {
    addLine(6, this.tm.getShared("Chat_ServerSay", paramString));
  }
  
  public void addBroadcastMessage(String paramString)
  {
    addBoldLine(6, this.tm.getShared("Chat_ServerBroadcast", paramString));
  }
  
  public void addWelcomeMessage(String paramString)
  {
    if (paramString == null) {
      return;
    }
    addLine(6, paramString);
  }
  
  public void addPlainMessage(String paramString)
  {
    addLine(7, paramString);
  }
  
  public void addMessage(String paramString)
  {
    realAddMessage(7, paramString);
  }
  
  public void addHighlightMessage(String paramString)
  {
    realAddMessage(6, paramString);
  }
  
  public void addErrorMessage(String paramString)
  {
    realAddMessage(1, paramString);
  }
  
  public void addFloodMessage()
  {
    realAddMessage(7, this.tm.getShared("Chat_MessageFlood"));
  }
  
  public void setUserColor(String paramString, int paramInt)
  {
    this.usercolors.put(paramString, new Integer(paramInt));
  }
  
  public void removeUserColor(String paramString)
  {
    this.usercolors.remove(paramString);
  }
  
  public TextManager getTextManager()
  {
    return this.tm;
  }
  
  public BadWordFilter getBadWordFilter()
  {
    return this.bwf;
  }
  
  private void realAddMessage(int paramInt, String paramString)
  {
    addLine(getMessageColor(paramInt), this.tm.getShared("Chat_Message", paramString));
  }
  
  private void realAddSay(int paramInt, String paramString1, String paramString2, boolean paramBoolean)
  {
    paramString2 = filterText(paramString2, paramBoolean);
    if ((paramString2.length() > 4) && (paramString2.toLowerCase().startsWith("/me ")))
    {
      addLine(getSayColor(paramString1, paramInt), this.tm.getShared("Chat_UserAction", paramString1, paramString2.substring(4)), paramBoolean);
      return;
    }
    addLine(getSayColor(paramString1, paramInt), this.tm.getShared("Chat_UserSay", paramString1, paramString2), paramBoolean);
  }
  
  private void realAddSay(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    paramString3 = filterText(paramString3, paramBoolean);
    addLine(getSayColor(paramString1, paramInt), this.tm.getShared("Chat_UserSayPrivate", paramString1, paramString2, paramString3), paramBoolean);
  }
  
  private int getMessageColor(int paramInt)
  {
    if (this.usercolors.size() == 0) {
      return paramInt;
    }
    return 7;
  }
  
  private int getSayColor(String paramString, int paramInt)
  {
    Integer localInteger = (Integer)this.usercolors.get(paramString);
    if (localInteger == null) {
      return paramInt;
    }
    return localInteger.intValue();
  }
  
  private String filterText(String paramString, boolean paramBoolean)
  {
    paramString = removeControlCharacters(paramString);
    paramString = filterBadWords(paramString, paramBoolean);
    return paramString;
  }
  
  private String removeControlCharacters(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    int i = 0;
    int j = arrayOfChar.length;
    for (int k = 0; k < j; k++) {
      if ((arrayOfChar[k] < ' ') || (arrayOfChar[k] == '') || (arrayOfChar[k] == ' ') || (arrayOfChar[k] == ' ') || (arrayOfChar[k] == 65529) || (arrayOfChar[k] == 65530) || (arrayOfChar[k] == 65531) || (arrayOfChar[k] == 61658))
      {
        arrayOfChar[k] = ' ';
        i = 1;
      }
    }
    if (i != 0) {
      paramString = new String(arrayOfChar);
    }
    return paramString;
  }
  
  private String filterBadWords(String paramString, boolean paramBoolean)
  {
    if ((this.bwf != null) && (!paramBoolean)) {
      paramString = this.bwf.filter(paramString);
    }
    return paramString;
  }
}
