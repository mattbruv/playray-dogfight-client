package com.aapeli.multiplayer.client.session.game.gui;

import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.common.network.GameProtocol;
import java.util.StringTokenizer;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class ChatArea
  extends AbstractChatEditorPane
  implements GameProtocol, ChatColors
{
  private ChatDecorator decorator = new ChatDecorator();
  private static final ChatDecorator.Style INFO = new ChatDecorator.Style(INFO_BG, INFO_FG);
  private static final ChatDecorator.Style ALL = new ChatDecorator.Style(ALL_BG, ALL_FG);
  private static final ChatDecorator.Style TEAM = new ChatDecorator.Style(TEAM_BG, TEAM_FG);
  private static final ChatDecorator.Style SERVER_BROADCAST = new ChatDecorator.Style(SERVER_BROADCAST_BG, SERVER_BROADCAST_FG);
  private static final ChatDecorator.Style ROOM_BROADCAST = new ChatDecorator.Style(ROOM_BROADCAST_BG, ROOM_BROADCAST_FG);
  
  public ChatArea()
  {
    super(55);
    this.removeDelay = 13000L;
    setEditorKit(new ChatEditorKit(this.decorator));
  }
  
  public void parseChatMessage(int paramInt, String paramString1, String paramString2)
  {
    Object localObject;
    if (paramInt == 2)
    {
      localObject = Localization.getInstance().localize("(team)") + " " + paramString1 + ": " + paramString2;
      localObject = insertLine((String)localObject, "");
      this.decorator.addLine((String)localObject + "\n", TEAM);
    }
    else if (paramInt == 3)
    {
      localObject = new StringTokenizer(paramString2, "\t");
      String str = "";
      if (paramString1.length() > 0) {
        str = paramString1;
      } else if (!((StringTokenizer)localObject).hasMoreTokens()) {}
      for (str = Localization.getInstance().localize(((StringTokenizer)localObject).nextToken()); ((StringTokenizer)localObject).hasMoreTokens(); str = str + " " + Localization.getInstance().localize(((StringTokenizer)localObject).nextToken())) {}
      str = insertLine(str, "");
      this.decorator.addLine(str + "\n", INFO);
    }
    else if (paramInt == 1)
    {
      localObject = paramString1 + ": " + paramString2;
      localObject = insertLine((String)localObject, "");
      this.decorator.addLine((String)localObject + "\n", ALL);
    }
    else if (paramInt == 6)
    {
      localObject = Localization.getInstance().localize("(spectator)") + " " + paramString1 + ": " + paramString2;
      localObject = insertLine((String)localObject, "");
      this.decorator.addLine((String)localObject + "\n", TEAM);
    }
    else if (paramInt == 7)
    {
      localObject = Localization.getInstance().localize("admin_broadcast") + " " + paramString2;
      localObject = insertLine((String)localObject, "");
      this.decorator.addLine((String)localObject + "\n", ROOM_BROADCAST);
    }
    else if (paramInt == 8)
    {
      localObject = Localization.getInstance().localize("server_broadcast") + " " + paramString2;
      localObject = insertLine((String)localObject, "");
      this.decorator.addLine((String)localObject + "\n", SERVER_BROADCAST);
    }
  }
  
  protected void remove(int paramInt1, int paramInt2)
    throws BadLocationException
  {
    if (paramInt2 > paramInt1)
    {
      String str = getDocument().getText(paramInt1, paramInt2);
      this.decorator.removeLine(str);
    }
    getDocument().remove(paramInt1, paramInt2);
  }
}
