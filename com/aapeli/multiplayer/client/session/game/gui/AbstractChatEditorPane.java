package com.aapeli.multiplayer.client.session.game.gui;

import com.aapeli.multiplayer.common.event.TextListener;
import com.aapeli.multiplayer.common.network.GameProtocol;
import com.aapeli.multiplayer.util.AbstractEditorPane;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractChatEditorPane
  extends AbstractEditorPane
  implements TextListener, GameProtocol, Runnable
{
  protected long removeDelay = 8000L;
  protected List lineAges = Collections.synchronizedList(new ArrayList(5));
  protected List messageBuffer = Collections.synchronizedList(new ArrayList(2));
  
  public AbstractChatEditorPane(int paramInt)
  {
    super(paramInt);
  }
  
  public AbstractChatEditorPane()
  {
    this(56);
  }
  
  protected abstract void parseChatMessage(int paramInt, String paramString1, String paramString2);
  
  public void run()
  {
    while (this.messageBuffer.size() > 0)
    {
      ChatMessage localChatMessage = (ChatMessage)this.messageBuffer.remove(0);
      parseChatMessage(localChatMessage.getType(), localChatMessage.getNick(), localChatMessage.getMessage());
    }
    if (this.lineAges.size() > 0)
    {
      long l1 = ((Long)this.lineAges.get(0)).longValue();
      long l2 = System.currentTimeMillis();
      if (l1 + this.removeDelay < l2) {
        removeLine();
      }
    }
  }
  
  protected String insertLine(String paramString1, String paramString2)
  {
    this.lineAges.add(new Long(System.currentTimeMillis()));
    paramString1 = super.insertLine(paramString1, paramString2);
    if (this.alignment == 55)
    {
      if (calculateHeight(this.lines + this.emptyLines) > getHeight()) {
        if (this.emptyLines > 0) {
          removeEmptyLine();
        } else {
          removeLine();
        }
      }
    }
    else if (calculateHeight(this.lines) > getMaximumSize().height) {
      removeLine();
    }
    return paramString1;
  }
  
  protected void removeLine()
  {
    this.lineAges.remove(0);
    super.removeLine();
  }
  
  public void textMessage(int paramInt, String paramString1, String paramString2)
  {
    this.messageBuffer.add(new ChatMessage(paramInt, paramString1, paramString2));
  }
  
  private class ChatMessage
  {
    private int type;
    private String nick;
    private String message;
    
    public ChatMessage(int paramInt, String paramString1, String paramString2)
    {
      this.type = paramInt;
      this.nick = paramString1;
      this.message = paramString2;
    }
    
    public String getMessage()
    {
      return this.message;
    }
    
    public String getNick()
    {
      return this.nick;
    }
    
    public int getType()
    {
      return this.type;
    }
  }
}
