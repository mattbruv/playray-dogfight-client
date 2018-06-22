package com.aapeli.multiplayer.client.session.chat;

import com.aapeli.client.BadWordFilter;
import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.common.chat.ChatUser;
import com.aapeli.multiplayer.common.network.ChatProtocol;
import com.aapeli.shared.ChatTextArea;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Channel
  implements ChatProtocol
{
  private List userList;
  private String name;
  private ChatTextArea chatArea;
  
  public Channel(String paramString, BadWordFilter paramBadWordFilter)
  {
    this.name = paramString;
    this.userList = new ArrayList(10);
    this.chatArea = new ChatTextArea(Localization.getInstance().getTextManager(), paramBadWordFilter, 1, 1);
  }
  
  public ChatTextArea getChatArea()
  {
    return this.chatArea;
  }
  
  public List getUserList()
  {
    return this.userList;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void alreadyJoined(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.userList.add(new ChatUser(paramString, paramBoolean1, paramBoolean2, paramBoolean3));
  }
  
  public void join(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.userList.add(new ChatUser(paramString, paramBoolean1, paramBoolean2, paramBoolean3));
  }
  
  public void part(String paramString)
  {
    if (paramString != null) {
      synchronized (this.userList)
      {
        Iterator localIterator = this.userList.iterator();
        while (localIterator.hasNext())
        {
          ChatUser localChatUser = (ChatUser)localIterator.next();
          if (paramString.equals(localChatUser.getName()))
          {
            localIterator.remove();
            break;
          }
        }
      }
    }
  }
  
  public void addBroadcastMessage(String paramString)
  {
    this.chatArea.addBroadcastMessage(paramString);
  }
  
  public void addFloodMessage()
  {
    this.chatArea.addFloodMessage();
  }
  
  public void addHighlightMessage(String paramString)
  {
    this.chatArea.addHighlightMessage(paramString);
  }
  
  public void addMessage(String paramString)
  {
    this.chatArea.addMessage(paramString);
  }
  
  public void addOwnSay(String paramString1, String paramString2)
  {
    this.chatArea.addOwnSay(paramString1, paramString2);
  }
  
  public void addOwnSayPrivately(String paramString1, String paramString2, String paramString3)
  {
    this.chatArea.addOwnSayPrivately(paramString1, paramString2, paramString3);
  }
  
  public void addPlainMessage(String paramString)
  {
    this.chatArea.addPlainMessage(paramString);
  }
  
  public void addSay(String paramString1, String paramString2)
  {
    this.chatArea.addSay(paramString1, paramString2);
  }
  
  public void addSayPrivately(String paramString1, String paramString2, String paramString3)
  {
    this.chatArea.addSayPrivately(paramString1, paramString2, paramString3);
  }
  
  public void addServerSay(String paramString)
  {
    this.chatArea.addServerSay(paramString);
  }
  
  public void addSheriffSay(String paramString)
  {
    this.chatArea.addSheriffSay(paramString);
  }
  
  public void addStartedGameMessage(String paramString)
  {
    this.chatArea.addStartedGameMessage(paramString);
  }
  
  public void addWelcomeMessage(String paramString)
  {
    this.chatArea.addWelcomeMessage(paramString);
  }
}
