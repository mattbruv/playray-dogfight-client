package com.aapeli.multiplayer.common.network;

import java.util.StringTokenizer;

public class ChatCoder
{
  public static String encode(ChatEvent paramChatEvent)
  {
    String[] arrayOfString = paramChatEvent.getArgs();
    switch (paramChatEvent.getType())
    {
    case 1: 
      return "msg\t" + arrayOfString[0] + "\t" + arrayOfString[1] + "\t" + arrayOfString[2];
    case 7: 
      return "join_announce\t" + arrayOfString[0] + "\t" + arrayOfString[1] + "\t" + arrayOfString[2];
    case 2: 
      return "join_request\t" + arrayOfString[0] + "\t" + arrayOfString[1];
    case 3: 
      return "part\t" + arrayOfString[0] + "\t" + arrayOfString[1];
    case 4: 
      String str = arrayOfString[0];
      for (int i = 1; i < arrayOfString.length; i++) {
        str = str + "\t" + arrayOfString[i];
      }
      return "channel\t" + str;
    case 5: 
      return "admin\t" + arrayOfString[0] + "\t" + arrayOfString[1] + (arrayOfString.length > 2 ? "\t" + arrayOfString[2] : "");
    case 6: 
      return "broadcast\t" + arrayOfString[0];
    case 8: 
      return "sheriff_msg\t" + arrayOfString[0];
    }
    return null;
  }
  
  public static ChatEvent decode(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "\t");
    String str = localStringTokenizer.nextToken();
    int i = -1;
    if (str.equals("msg")) {
      i = 1;
    } else if (str.equals("join_announce")) {
      i = 7;
    } else if (str.equals("join_request")) {
      i = 2;
    } else if (str.equals("part")) {
      i = 3;
    } else if (str.equals("channel")) {
      i = 4;
    } else if (str.equals("admin")) {
      i = 5;
    } else if (str.equals("broadcast")) {
      i = 6;
    } else if (str.equals("sheriff_msg")) {
      i = 8;
    }
    String[] arrayOfString;
    switch (i)
    {
    case 1: 
      arrayOfString = new String[] { localStringTokenizer.nextToken(), localStringTokenizer.nextToken(), localStringTokenizer.nextToken() };
      return new ChatEvent(i, arrayOfString);
    case 7: 
      arrayOfString = new String[] { localStringTokenizer.nextToken(), localStringTokenizer.nextToken(), localStringTokenizer.nextToken() };
      return new ChatEvent(i, arrayOfString);
    case 2: 
      arrayOfString = new String[] { localStringTokenizer.nextToken(), localStringTokenizer.nextToken() };
      return new ChatEvent(i, arrayOfString);
    case 3: 
      arrayOfString = new String[] { localStringTokenizer.nextToken(), localStringTokenizer.nextToken() };
      return new ChatEvent(i, arrayOfString);
    case 4: 
      arrayOfString = new String[localStringTokenizer.countTokens()];
      for (int j = 0; j < arrayOfString.length; j++) {
        arrayOfString[j] = localStringTokenizer.nextToken();
      }
      return new ChatEvent(i, arrayOfString);
    case 5: 
      arrayOfString = new String[] { localStringTokenizer.nextToken(), localStringTokenizer.nextToken(), "" };
      if (localStringTokenizer.hasMoreTokens()) {
        arrayOfString[2] = localStringTokenizer.nextToken();
      }
      return new ChatEvent(i, arrayOfString);
    case 6: 
      arrayOfString = new String[] { localStringTokenizer.nextToken() };
      return new ChatEvent(i, arrayOfString);
    case 8: 
      arrayOfString = new String[] { localStringTokenizer.nextToken() };
      return new ChatEvent(i, arrayOfString);
    }
    return null;
  }
}
