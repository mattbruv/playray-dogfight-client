package com.aapeli.multiplayer.impl.dogfight.client.gui;

import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.common.event.TextListener;
import com.aapeli.multiplayer.common.network.GameProtocol;
import com.aapeli.multiplayer.impl.dogfight.common.ChatMessageProtocol;
import com.aapeli.multiplayer.util.console.Console;
import java.awt.Color;
import java.util.StringTokenizer;

public class ConsoleAdapter
  implements GameProtocol, ChatMessageProtocol, TextListener
{
  private Console console;
  
  public ConsoleAdapter(Console paramConsole)
  {
    this.console = paramConsole;
    paramConsole.addStyle("chat", new Color(100, 100, 0));
    paramConsole.addStyle("kill", new Color(0, 0, 150));
    paramConsole.setForeground(Color.black);
    paramConsole.setBackground(new Color(13885183));
    paramConsole.put("show_fps", "0");
    paramConsole.put("draw_clipping", "1");
    paramConsole.put("clan", "");
  }
  
  public void textMessage(int paramInt, String paramString1, String paramString2)
  {
    StringTokenizer localStringTokenizer;
    String str1;
    switch (paramInt)
    {
    case 2: 
      this.console.insertLine(Localization.getInstance().localize("(team)") + " <" + paramString1 + "> " + paramString2, "chat");
      break;
    case 6: 
      this.console.insertLine(Localization.getInstance().localize("(spectator)") + " <" + paramString1 + "> " + paramString2, "chat");
      break;
    case 1: 
      this.console.insertLine("<" + paramString1 + "> " + paramString2, "chat");
      break;
    case 8: 
      this.console.insertLine(Localization.getInstance().localize("server_broadcast") + " " + paramString2, "chat");
      break;
    case 7: 
      this.console.insertLine(Localization.getInstance().localize("admin_broadcast") + " " + paramString2, "chat");
      break;
    case 3: 
      localStringTokenizer = new StringTokenizer(paramString2, "\t");
      str1 = "";
      if (paramString1.length() > 0) {
        str1 = paramString1;
      } else if (!localStringTokenizer.hasMoreTokens()) {}
      for (str1 = Localization.getInstance().localize(localStringTokenizer.nextToken()); localStringTokenizer.hasMoreTokens(); str1 = str1 + " " + Localization.getInstance().localize(localStringTokenizer.nextToken())) {}
      this.console.insertLine(str1, "chat");
      break;
    case 4: 
      localStringTokenizer = new StringTokenizer(paramString2, "\t");
      int i = Integer.parseInt(localStringTokenizer.nextToken());
      int j = Integer.parseInt(localStringTokenizer.nextToken());
      String str2;
      String str3;
      switch (j)
      {
      case 1: 
        str2 = localStringTokenizer.nextToken();
        str2 = quoteDollarAndSlash(str2);
        str1 = Localization.getInstance().localize("plane kill self");
        str1 = str1.replaceAll("\\$1", str2);
        this.console.insertLine(str1, "kill");
        break;
      case 2: 
      case 3: 
        str3 = localStringTokenizer.nextToken();
        str2 = localStringTokenizer.nextToken();
        str3 = quoteDollarAndSlash(str3);
        str2 = quoteDollarAndSlash(str2);
        str1 = Localization.getInstance().localize("plane kill");
        str1 = str1.replaceAll("\\$1", str3);
        str1 = str1.replaceAll("\\$2", str2);
        this.console.insertLine(str1, "kill");
        break;
      case 4: 
        str2 = localStringTokenizer.nextToken();
        str2 = quoteDollarAndSlash(str2);
        str1 = Localization.getInstance().localize("man kill self");
        str1 = str1.replaceAll("\\$1", str2);
        this.console.insertLine(str1, "kill");
        break;
      case 5: 
      case 6: 
        str3 = localStringTokenizer.nextToken();
        str2 = localStringTokenizer.nextToken();
        str3 = quoteDollarAndSlash(str3);
        str2 = quoteDollarAndSlash(str2);
        str1 = Localization.getInstance().localize("man kill");
        str1 = str1.replaceAll("\\$1", str3);
        str1 = str1.replaceAll("\\$2", str2);
        this.console.insertLine(str1, "kill");
      }
      break;
    }
  }
  
  private String quoteDollarAndSlash(String paramString)
  {
    paramString = paramString.replaceAll("\\\\", "\\\\\\\\");
    paramString = paramString.replaceAll("\\$", "\\\\\\$");
    return paramString;
  }
}
