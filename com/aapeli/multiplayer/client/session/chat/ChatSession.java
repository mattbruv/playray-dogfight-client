package com.aapeli.multiplayer.client.session.chat;

import com.aapeli.client.BadWordFilter;
import com.aapeli.client.Parameters;
import com.aapeli.credit.Product;
import com.aapeli.credit.ProductHandler;
import com.aapeli.credit.PurchaseFrame;
import com.aapeli.credit.PurchaseHandler;
import com.aapeli.multiplayer.client.network.Connection;
import com.aapeli.multiplayer.client.resources.Loadable;
import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.client.session.ClientSession;
import com.aapeli.multiplayer.client.session.ClientSessionManager;
import com.aapeli.multiplayer.common.network.ChatCoder;
import com.aapeli.multiplayer.common.network.ChatEvent;
import com.aapeli.multiplayer.common.network.ChatProtocol;
import com.aapeli.multiplayer.common.network.DataInputPacket;
import com.aapeli.multiplayer.common.network.DataOutputPacket;
import com.aapeli.multiuser.FloodProtection;
import com.aapeli.shared.UserList;
import com.aapeli.shared.UserListHandler;
import com.aapeli.shared.UserListItem;
import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class ChatSession
  extends ClientSession
  implements ChatProtocol, ActionListener, UserListHandler, Runnable, PurchaseHandler
{
  private ChatScreen screen;
  private Map channelMap;
  private static final String DEFAULT_CHANNEL = "#playray";
  private String activeChannel;
  private String game;
  private boolean active = false;
  private Thread thread;
  private long lastGameListRefresh;
  private static final long AUTOMATIC_REFRESH_INTERVAL = 2000L;
  private UserList userList;
  private PurchaseFrame purchaseFrame;
  private Object balanceLock = new Object();
  private Object buyLock = new Object();
  private int credits = 0;
  private int buyResult = 0;
  private String chosenGameRoom;
  private String enteredPassword;
  private BadWordFilter badWordFilter;
  private FloodProtection floodProtection;
  private int buyMode = 0;
  private static final int BUYING_NONE = 0;
  private static final int BUYING_ROOM = 1;
  private static final int BUYING_ACCESS = 2;
  private static final int BUYING_DONATION = 3;
  private String fullProductList;
  
  public ChatSession(ClientSessionManager paramClientSessionManager, String paramString, Map paramMap)
  {
    super(paramClientSessionManager);
    this.game = paramString;
    this.channelMap = Collections.synchronizedMap(new HashMap(5));
    this.userList = new UserList(this, Localization.getInstance().getTextManager(), paramClientSessionManager.getImageManager(), false, true, true, 170, 170);
    this.screen = new ChatScreen(this, this.userList, this.channelMap, paramClientSessionManager.getApplet(), paramMap);
    this.badWordFilter = new BadWordFilter(Localization.getInstance().getTextManager());
    this.floodProtection = new FloodProtection();
  }
  
  public void login(Connection paramConnection, Map paramMap)
  {
    this.active = true;
    super.login(paramConnection, paramMap);
    this.screen.setName(paramConnection.getNick());
    this.screen.setAdmin(paramConnection.isAdmin());
    this.userList.enablePopUpWithOnlyOldCommands(paramConnection.isSheriff(), paramConnection.isAdmin());
    this.activeChannel = "#playray";
    sendJoin("#playray");
    this.screen.setChatVisible(true);
    if (paramMap != null)
    {
      String str1 = (String)paramMap.get("param_quit_reason");
      Integer localInteger = (Integer)paramMap.get("param_quit_type");
      int i = 0;
      if (localInteger != null) {
        i = localInteger.intValue();
      }
      if ((i != 0) && (i != 4))
      {
        String str2 = createQuitMessage(i, str1);
        this.screen.setChatVisible(false);
        this.screen.showNotifyBox("Info", str2);
      }
    }
    new Thread(this).start();
  }
  
  private void logout()
  {
    this.active = false;
    synchronized (this.channelMap)
    {
      this.channelMap.clear();
    }
  }
  
  public void start()
  {
    this.screen.setInputEnabled(true);
    this.screen.setOnScreen(true);
  }
  
  public void stop()
  {
    this.screen.setInputEnabled(false);
    this.screen.setOnScreen(false);
  }
  
  public void read(DataInputPacket paramDataInputPacket)
  {
    try
    {
      int i = paramDataInputPacket.read();
      int j;
      String[] arrayOfString1;
      int k;
      int n;
      String str2;
      Object localObject1;
      Object localObject2;
      Object localObject4;
      Object localObject3;
      switch (i)
      {
      case 12: 
        String str1 = paramDataInputPacket.readUTF();
        parseMessage(str1);
        break;
      case 60: 
        parseChallenge(paramDataInputPacket);
      case 13: 
        j = paramDataInputPacket.readByte() & 0xFF;
        arrayOfString1 = new String[j];
        int[] arrayOfInt = new int[j];
        boolean[] arrayOfBoolean = new boolean[j];
        for (k = 0; k < arrayOfString1.length; k++)
        {
          arrayOfString1[k] = paramDataInputPacket.readUTF();
          arrayOfInt[k] = (paramDataInputPacket.readShort() & 0xFFFF);
          arrayOfBoolean[k] = paramDataInputPacket.readBoolean();
        }
        k = paramDataInputPacket.readShort();
        String[][] arrayOfString = new String[k][arrayOfString1.length];
        String[] arrayOfString2 = new String[k];
        for (int m = 0; m < k; m++)
        {
          arrayOfString2[m] = paramDataInputPacket.readUTF();
          for (n = 0; n < arrayOfString[m].length; n++) {
            arrayOfString[m][n] = paramDataInputPacket.readUTF();
          }
        }
        this.screen.refreshGameSessionList(arrayOfString1, arrayOfInt, arrayOfBoolean, arrayOfString, arrayOfString2);
        break;
      case 10: 
        str2 = paramDataInputPacket.readUTF();
        logout();
        this.manager.transfer(this.connection, str2);
        break;
      case 19: 
        n = paramDataInputPacket.readByte() & 0xFF;
        this.screen.setInputEnabled(true);
        switch (n)
        {
        case 3: 
          this.buyMode = 2;
          String str3 = paramDataInputPacket.readUTF();
          this.credits = paramDataInputPacket.readInt();
          localObject1 = paramDataInputPacket.readUTF();
          localObject2 = new ProductHandler(str3);
          Product localProduct = ((ProductHandler)localObject2).getProduct((String)localObject1);
          if (this.purchaseFrame == null)
          {
            this.purchaseFrame = new PurchaseFrame(this, this.manager.getParameters(), Localization.getInstance().getTextManager(), this.manager.getImageManager(), localProduct, this.credits, Localization.getInstance().localize("buy_frame_title"), Localization.getInstance().localize("buy_frame_continue"), Localization.getInstance().localize("buy_frame_cancel"));
            SwingUtilities.invokeLater(new Runnable()
            {
              public void run()
              {
                ChatSession.this.purchaseFrame.open(ChatSession.this.screen);
              }
            });
          }
          this.screen.setChatVisible(true);
          break;
        case 5: 
          this.screen.setChatVisible(false);
          this.screen.showPasswordBox(new ActionListener()
          {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent)
            {
              ChatSession.this.passwordEntered(paramAnonymousActionEvent);
            }
          });
          break;
        case 2: 
          this.screen.setChatVisible(false);
          this.screen.showNotifyBox("Info", Localization.getInstance().localize("Only registered users may play here."));
          break;
        case 7: 
          this.screen.setChatVisible(false);
          this.screen.showNotifyBox("Info", Localization.getInstance().localize("The room is full"));
          break;
        case 8: 
          this.screen.setChatVisible(false);
          this.screen.showNotifyBox("Info", Localization.getInstance().localize("Banned"));
          break;
        case 1: 
          this.screen.setChatVisible(false);
          this.screen.showNotifyBox("Info", Localization.getInstance().localize("Only royal users may play here."));
          break;
        case 6: 
          this.screen.setChatVisible(false);
          this.screen.showNotifyBox("Info", Localization.getInstance().localize("Wrong password."));
          break;
        }
        this.screen.repaint();
        break;
      case 20: 
        n = paramDataInputPacket.readByte() & 0xFF;
        switch (n)
        {
        case 2: 
          this.screen.setChatVisible(false);
          this.screen.showNotifyBox("Info", Localization.getInstance().localize("Banned"));
          break;
        case 1: 
          this.screen.setChatVisible(false);
          this.screen.showNotifyBox("Info", Localization.getInstance().localize("Not allowed."));
        }
        break;
      case 21: 
        int i1 = paramDataInputPacket.readByte() & 0xFF;
        localObject1 = new HashMap(i1);
        localObject2 = new HashMap(2);
        for (int i3 = 0; i3 < i1; i3++)
        {
          localObject4 = paramDataInputPacket.readUTF();
          localObject5 = paramDataInputPacket.readUTF();
          ((Map)localObject1).put(localObject4, localObject5);
          if (((String)localObject4).startsWith("product_names_")) {
            ((Map)localObject2).put(((String)localObject4).substring("product_names_".length()), localObject5);
          }
        }
        this.credits = Integer.parseInt((String)((Map)localObject1).get("balance"));
        localObject3 = this.manager.getGameCreateOptions((Map)localObject2);
        localObject4 = this.manager.getGameCreateOptions((Map)localObject2);
        Object localObject5 = this.manager.getGameManageOptions((Map)localObject2);
        this.screen.setOptions((Map)localObject3, (Map)localObject4, (Map)localObject5);
        this.fullProductList = ((String)((Map)localObject1).get("full_product_list"));
        break;
      case 15: 
        str2 = paramDataInputPacket.readUTF();
        logout();
        HashMap localHashMap = new HashMap(1);
        localHashMap.put("param_watch", "yes");
        this.manager.transfer(this.connection, str2, localHashMap);
        break;
      case 17: 
        logout();
        this.manager.transfer(this.connection, "Video");
        break;
      case 23: 
        this.credits = paramDataInputPacket.readInt();
        synchronized (this.balanceLock)
        {
          this.balanceLock.notifyAll();
        }
        break;
      case 24: 
        this.buyResult = paramDataInputPacket.readByte();
        this.credits = paramDataInputPacket.readInt();
        synchronized (this.buyLock)
        {
          this.buyLock.notifyAll();
        }
        break;
      case 27: 
        break;
      case 31: 
        ??? = Collections.synchronizedMap(new HashMap(3));
        j = paramDataInputPacket.readByte() & 0xFF;
        arrayOfString1 = new String[j];
        for (int i2 = 0; i2 < arrayOfString1.length; i2++) {
          arrayOfString1[i2] = paramDataInputPacket.readUTF();
        }
        k = paramDataInputPacket.readShort();
        for (i2 = 0; i2 < k; i2++)
        {
          localObject3 = paramDataInputPacket.readUTF();
          localObject4 = Collections.synchronizedMap(new HashMap(3));
          for (int i5 = 0; i5 < arrayOfString1.length; i5++)
          {
            String str5 = paramDataInputPacket.readUTF();
            ((Map)localObject4).put(arrayOfString1[i5], str5);
          }
          ((Map)???).put(localObject3, localObject4);
        }
        this.screen.setManageMap((Map)???);
        break;
      case 34: 
        Map localMap = Collections.synchronizedMap(new HashMap(3));
        k = paramDataInputPacket.readByte() & 0xFF;
        for (int i4 = 0; i4 < k; i4++)
        {
          localObject4 = paramDataInputPacket.readUTF();
          String str4 = paramDataInputPacket.readUTF();
          localMap.put(localObject4, str4);
        }
        this.screen.setCreateMap(localMap);
        break;
      case 11: 
      case 14: 
      case 16: 
      case 18: 
      case 22: 
      case 25: 
      case 26: 
      case 28: 
      case 29: 
      case 30: 
      case 32: 
      case 33: 
      case 35: 
      case 36: 
      case 37: 
      case 38: 
      case 39: 
      case 40: 
      case 41: 
      case 42: 
      case 43: 
      case 44: 
      case 45: 
      case 46: 
      case 47: 
      case 48: 
      case 49: 
      case 50: 
      case 51: 
      case 52: 
      case 53: 
      case 54: 
      case 55: 
      case 56: 
      case 57: 
      case 58: 
      case 59: 
      default: 
        System.out.println("READING GARBAGE: " + i);
      }
    }
    catch (IOException localIOException) {}
  }
  
  public JComponent getScreen()
  {
    return this.screen;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    Object localObject1;
    Object localObject2;
    switch (paramActionEvent.getID())
    {
    case 12: 
      sendMessage(paramActionEvent.getActionCommand());
      break;
    case 13: 
      requestGameSessionList();
      break;
    case 31: 
    case 34: 
      requestInfo(paramActionEvent.getID());
      break;
    case 11: 
    case 14: 
      loadGame(paramActionEvent.getActionCommand(), paramActionEvent.getID());
      break;
    case 16: 
      sendVideo();
      break;
    case 28: 
      this.buyMode = 1;
      localObject1 = new ProductHandler(this.fullProductList);
      localObject2 = ((ProductHandler)localObject1).getProduct(paramActionEvent.getActionCommand());
      System.out.println("Buy_room trying to buy: " + paramActionEvent.getActionCommand());
      if (this.purchaseFrame == null)
      {
        this.purchaseFrame = new PurchaseFrame(this, this.manager.getParameters(), Localization.getInstance().getTextManager(), this.manager.getImageManager(), (Product)localObject2, this.credits, Localization.getInstance().localize("buy_frame_title"), Localization.getInstance().localize("buy_frame_continue"), Localization.getInstance().localize("buy_frame_cancel"));
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            ChatSession.this.purchaseFrame.open(ChatSession.this.screen);
          }
        });
      }
      break;
    case 33: 
      localObject1 = paramActionEvent.getActionCommand().split("\t");
      writeRoomBuyTime(localObject1[1], localObject1[0]);
      break;
    case 32: 
      this.buyMode = 3;
      localObject1 = paramActionEvent.getActionCommand().split("\t");
      localObject2 = new ProductHandler(this.fullProductList);
      Product localProduct = ((ProductHandler)localObject2).getProduct(localObject1[0]);
      System.out.println("Donate trying to buy: " + paramActionEvent.getActionCommand());
      if (this.purchaseFrame == null)
      {
        this.purchaseFrame = new PurchaseFrame(this, this.manager.getParameters(), Localization.getInstance().getTextManager(), this.manager.getImageManager(), localProduct, this.credits, Localization.getInstance().localize("buy_frame_title"), Localization.getInstance().localize("buy_frame_continue"), Localization.getInstance().localize("buy_frame_cancel"), localObject1[1]);
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            ChatSession.this.purchaseFrame.open(ChatSession.this.screen);
          }
        });
      }
      break;
    case 30: 
      localObject1 = this.screen.getManageGameOptions();
      writeManage((Map)localObject1);
      break;
    case 54: 
      localObject1 = this.manager.getParameters().getParameter("pricehelppage");
      try
      {
        localObject2 = new URL((String)localObject1);
        this.manager.getApplet().getAppletContext().showDocument((URL)localObject2, "_blank");
      }
      catch (MalformedURLException localMalformedURLException) {}
    case 61: 
    case 62: 
    case 63: 
    case 64: 
      writeChallenge(paramActionEvent.getID(), paramActionEvent.getActionCommand());
      break;
    case 65: 
      writeProposeChallenge(paramActionEvent.getActionCommand());
    }
  }
  
  private void writeChallenge(int paramInt, String paramString)
  {
    DataOutputPacket localDataOutputPacket = new DataOutputPacket();
    try
    {
      localDataOutputPacket.writeByte(60);
      localDataOutputPacket.writeByte(paramInt);
      localDataOutputPacket.writeUTF(paramString);
    }
    catch (IOException localIOException) {}
    if (this.active) {
      this.connection.write(localDataOutputPacket);
    }
  }
  
  private void writeProposeChallenge(String paramString)
  {
    DataOutputPacket localDataOutputPacket = new DataOutputPacket();
    try
    {
      localDataOutputPacket.writeByte(60);
      localDataOutputPacket.writeByte(65);
      localDataOutputPacket.writeUTF(paramString);
      Map localMap = this.screen.getChallengeOptions();
      localDataOutputPacket.writeByte(localMap.size());
      Iterator localIterator = localMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        String str2 = (String)localMap.get(str1);
        localDataOutputPacket.writeUTF(str1);
        localDataOutputPacket.writeUTF(str2);
      }
    }
    catch (IOException localIOException) {}
    if (this.active) {
      this.connection.write(localDataOutputPacket);
    }
  }
  
  private void requestGameSessionList()
  {
    this.lastGameListRefresh = System.currentTimeMillis();
    DataOutputPacket localDataOutputPacket = new DataOutputPacket();
    try
    {
      localDataOutputPacket.writeByte(13);
      localDataOutputPacket.writeUTF(this.game);
    }
    catch (IOException localIOException) {}
    if (this.active) {
      this.connection.write(localDataOutputPacket);
    }
  }
  
  private void requestInfo(int paramInt)
  {
    DataOutputPacket localDataOutputPacket = new DataOutputPacket();
    try
    {
      localDataOutputPacket.writeByte(paramInt);
      localDataOutputPacket.writeUTF(this.game);
    }
    catch (IOException localIOException) {}
    if (this.active) {
      this.connection.write(localDataOutputPacket);
    }
  }
  
  private void writeRoomBuyTime(String paramString1, String paramString2)
  {
    DataOutputPacket localDataOutputPacket = new DataOutputPacket();
    try
    {
      localDataOutputPacket.writeByte(33);
      localDataOutputPacket.writeUTF(paramString2);
      localDataOutputPacket.writeUTF(paramString1);
    }
    catch (IOException localIOException) {}
    if (this.active) {
      this.connection.write(localDataOutputPacket);
    }
  }
  
  private void sendMessage(String paramString)
  {
    Object localObject;
    if (this.floodProtection.isOkToSay(paramString))
    {
      if (paramString.startsWith("/"))
      {
        localObject = new StringTokenizer(paramString, " ");
        if (((StringTokenizer)localObject).hasMoreTokens())
        {
          String str1 = ((StringTokenizer)localObject).nextToken();
          if ((str1.equals("/msg")) && (((StringTokenizer)localObject).hasMoreTokens()))
          {
            String str2 = ((StringTokenizer)localObject).nextToken();
            int i = paramString.indexOf(str2, str1.length()) + str2.length() + 1;
            if (i < paramString.length())
            {
              String str3 = paramString.substring(i);
              writeString(ChatCoder.encode(new ChatEvent(1, new String[] { str2, this.connection.getNick(), str3 })));
              ownPrivateMessage(str3, str2);
            }
          }
        }
      }
      else
      {
        localObject = this.screen.getUserList().getSelectedUser();
        if ((localObject != null) && (((UserListItem)localObject).isPrivately()))
        {
          writeString(ChatCoder.encode(new ChatEvent(1, new String[] { ((UserListItem)localObject).getNick(), this.connection.getNick(), paramString })));
          ownPrivateMessage(paramString, ((UserListItem)localObject).getNick());
        }
        else
        {
          writeString(ChatCoder.encode(new ChatEvent(1, new String[] { this.activeChannel, this.connection.getNick(), paramString })));
        }
      }
    }
    else if (this.channelMap.containsKey(this.activeChannel))
    {
      localObject = (Channel)this.channelMap.get(this.activeChannel);
      ((Channel)localObject).addFloodMessage();
    }
  }
  
  private void ownPrivateMessage(String paramString1, String paramString2)
  {
    Channel localChannel = (Channel)this.channelMap.get(this.activeChannel);
    if (localChannel != null) {
      localChannel.addOwnSayPrivately(this.connection.getNick(), paramString2, paramString1);
    }
  }
  
  private void sendJoin(String paramString)
  {
    writeString(ChatCoder.encode(new ChatEvent(2, new String[] { paramString, this.connection.getNick() })));
  }
  
  private void writeString(String paramString)
  {
    try
    {
      DataOutputPacket localDataOutputPacket = new DataOutputPacket();
      localDataOutputPacket.writeByte(12);
      localDataOutputPacket.writeUTF(paramString);
      if (this.active) {
        this.connection.write(localDataOutputPacket);
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  private void filterRoomName(Map paramMap)
  {
    String str = (String)paramMap.get("param_name");
    if (str != null)
    {
      str = this.badWordFilter.filter(str);
      paramMap.put("param_name", str);
    }
  }
  
  private void writeBuyRoom(Map paramMap, String paramString)
  {
    filterRoomName(paramMap);
    try
    {
      DataOutputPacket localDataOutputPacket = new DataOutputPacket();
      localDataOutputPacket.writeByte(28);
      localDataOutputPacket.writeUTF(paramString);
      localDataOutputPacket.writeByte(paramMap.size());
      Iterator localIterator = paramMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        String str2 = (String)paramMap.get(str1);
        localDataOutputPacket.writeUTF(str1);
        localDataOutputPacket.writeUTF(str2);
      }
      if (this.active) {
        this.connection.write(localDataOutputPacket);
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  private void writeManage(Map paramMap)
  {
    filterRoomName(paramMap);
    try
    {
      DataOutputPacket localDataOutputPacket = new DataOutputPacket();
      localDataOutputPacket.writeByte(30);
      localDataOutputPacket.writeByte(paramMap.size());
      Iterator localIterator = paramMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        String str2 = (String)paramMap.get(str1);
        localDataOutputPacket.writeUTF(str1);
        localDataOutputPacket.writeUTF(str2);
      }
      if (this.active) {
        this.connection.write(localDataOutputPacket);
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  public void destroy() {}
  
  private void parseMessage(String paramString)
  {
    ChatEvent localChatEvent = ChatCoder.decode(paramString);
    String[] arrayOfString = localChatEvent.getArgs();
    switch (localChatEvent.getType())
    {
    case 1: 
      msg(arrayOfString[0], arrayOfString[1], arrayOfString[2]);
      break;
    case 7: 
      join(arrayOfString[0], arrayOfString[1], arrayOfString[2]);
      break;
    case 3: 
      part(arrayOfString[0], arrayOfString[1]);
      break;
    case 4: 
      channel(arrayOfString);
      break;
    case 6: 
      chatBroadcast(arrayOfString[0]);
      break;
    case 8: 
      chatSheriffMsg(arrayOfString[0]);
    }
  }
  
  private void chatBroadcast(String paramString)
  {
    HashMap localHashMap;
    synchronized (this.channelMap)
    {
      localHashMap = new HashMap(this.channelMap);
    }
    ??? = localHashMap.keySet().iterator();
    while (((Iterator)???).hasNext())
    {
      Channel localChannel = (Channel)localHashMap.get(((Iterator)???).next());
      localChannel.addBroadcastMessage(paramString);
    }
  }
  
  private void chatSheriffMsg(String paramString)
  {
    if (this.channelMap.containsKey(this.activeChannel))
    {
      Channel localChannel = (Channel)this.channelMap.get(this.activeChannel);
      localChannel.addSheriffSay(paramString);
    }
  }
  
  private void sendGoToGame(int paramInt, String paramString)
  {
    this.chosenGameRoom = paramString;
    try
    {
      DataOutputPacket localDataOutputPacket = new DataOutputPacket();
      localDataOutputPacket.writeByte(paramInt);
      localDataOutputPacket.writeUTF(paramString);
      if (paramInt == 29)
      {
        localDataOutputPacket.writeUTF(this.enteredPassword);
        this.enteredPassword = null;
      }
      if (this.active) {
        this.connection.write(localDataOutputPacket);
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  private void sendVideo()
  {
    try
    {
      DataOutputPacket localDataOutputPacket = new DataOutputPacket();
      localDataOutputPacket.writeByte(16);
      if (this.active) {
        this.connection.write(localDataOutputPacket);
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  private String formatNick(String paramString)
  {
    return "<" + paramString + "> ";
  }
  
  public void adminCommand(String paramString1, String paramString2, String paramString3)
  {
    System.out.println("Admin command: " + paramString1 + " param1: " + paramString2 + " param2: " + paramString3);
    writeString(ChatCoder.encode(new ChatEvent(5, new String[] { paramString1, paramString2, paramString3 })));
  }
  
  public void adminCommand(String paramString1, String paramString2)
  {
    System.out.println("Admin command: " + paramString1 + " param1: " + paramString2);
    writeString(ChatCoder.encode(new ChatEvent(5, new String[] { paramString1, paramString2 })));
  }
  
  public void openPlayerCard(String paramString)
  {
    this.manager.getParameters().showPlayerCard(paramString);
  }
  
  private void join(String paramString1, String paramString2, String paramString3)
  {
    Channel localChannel;
    if (paramString2.equals(this.connection.getNick()))
    {
      if (!this.channelMap.containsKey(paramString1))
      {
        localChannel = new Channel(paramString1, this.badWordFilter);
        localChannel.addWelcomeMessage(this.manager.getParameters().getWelcomeMessage());
        this.channelMap.put(paramString1, localChannel);
        this.screen.setChatArea(localChannel.getChatArea());
        localChannel.join(paramString2, isRegistered(paramString3), isVip(paramString3), isSheriff(paramString3));
        if (paramString1.equals(this.activeChannel)) {
          this.screen.getUserList().addUser(new UserListItem(paramString2, true, isRegistered(paramString3), isVip(paramString3), isSheriff(paramString3)));
        }
      }
    }
    else if (this.channelMap.containsKey(paramString1))
    {
      localChannel = (Channel)this.channelMap.get(paramString1);
      localChannel.join(paramString2, isRegistered(paramString3), isVip(paramString3), isSheriff(paramString3));
      if (paramString1.equals(this.activeChannel)) {
        this.screen.getUserList().addUser(new UserListItem(paramString2, false, isRegistered(paramString3), isVip(paramString3), isSheriff(paramString3)));
      }
    }
  }
  
  private void msg(String paramString1, String paramString2, String paramString3)
  {
    UserListItem localUserListItem;
    Channel localChannel;
    if (paramString1.equalsIgnoreCase(this.connection.getNick()))
    {
      if (this.channelMap.containsKey(this.activeChannel))
      {
        localUserListItem = this.screen.getUserList().getUser(paramString2);
        if ((localUserListItem == null) || (!localUserListItem.isIgnore()))
        {
          localChannel = (Channel)this.channelMap.get(this.activeChannel);
          localChannel.addSayPrivately(paramString2, this.connection.getNick(), paramString3);
        }
      }
    }
    else if (this.channelMap.containsKey(paramString1))
    {
      localUserListItem = this.screen.getUserList().getUser(paramString2);
      if ((localUserListItem == null) || (!localUserListItem.isIgnore()))
      {
        localChannel = (Channel)this.channelMap.get(paramString1);
        if (paramString2.equals(this.connection.getNick())) {
          localChannel.addOwnSay(paramString2, paramString3);
        } else {
          localChannel.addSay(paramString2, paramString3);
        }
      }
    }
  }
  
  private boolean isRegistered(String paramString)
  {
    return paramString.charAt(0) == '1';
  }
  
  private boolean isVip(String paramString)
  {
    return paramString.charAt(1) == '1';
  }
  
  private boolean isSheriff(String paramString)
  {
    return paramString.charAt(2) == '1';
  }
  
  private void part(String paramString1, String paramString2)
  {
    if (paramString2.equals(this.connection.getNick()))
    {
      if (this.channelMap.containsKey(paramString1))
      {
        this.channelMap.remove(paramString1);
        if (paramString1.equals(this.activeChannel)) {
          this.screen.getUserList().removeAllUsers();
        }
      }
    }
    else if (this.channelMap.containsKey(paramString1))
    {
      Channel localChannel = (Channel)this.channelMap.get(paramString1);
      localChannel.part(paramString2);
      if (paramString1.equals(this.activeChannel)) {
        this.screen.getUserList().removeUser(paramString2);
      }
    }
  }
  
  private void channel(String[] paramArrayOfString)
  {
    String str1 = paramArrayOfString[0];
    if (this.channelMap.containsKey(str1))
    {
      Channel localChannel = (Channel)this.channelMap.get(str1);
      String str2;
      for (int i = 1; i < paramArrayOfString.length; i += 2)
      {
        str2 = paramArrayOfString[(i + 1)];
        localChannel.alreadyJoined(paramArrayOfString[i], isRegistered(str2), isVip(str2), isSheriff(str2));
      }
      if (str1.equals(this.activeChannel))
      {
        this.screen.getUserList().removeAllUsers();
        for (i = 1; i < paramArrayOfString.length; i += 2)
        {
          str2 = paramArrayOfString[i];
          String str3 = paramArrayOfString[(i + 1)];
          this.screen.getUserList().addUser(new UserListItem(str2, str2.equals(this.connection.getNick()), isRegistered(str3), isVip(str3), isSheriff(str3)));
        }
      }
    }
  }
  
  private boolean loadGame(String paramString, int paramInt)
  {
    ClientSession localClientSession = this.manager.getSession(this.game);
    if ((localClientSession instanceof Loadable))
    {
      this.screen.setInputEnabled(false);
      Loadable localLoadable = (Loadable)localClientSession;
      System.out.println("loadGame: " + paramString + ", " + paramInt + ", " + localLoadable.getLoadStatus());
      if (localLoadable.getLoadStatus() >= 1.0D)
      {
        sendGoToGame(paramInt, paramString);
      }
      else
      {
        localLoadable.startLoading();
        this.screen.loadGame(localLoadable, paramString, paramInt);
      }
    }
    return false;
  }
  
  public void run()
  {
    this.thread = Thread.currentThread();
    while ((this.active) && (this.thread == Thread.currentThread()))
    {
      long l = System.currentTimeMillis();
      if ((this.lastGameListRefresh + 2000L < l) && (!this.screen.isLoading())) {
        requestGameSessionList();
      }
      try
      {
        Thread.sleep(2000L);
      }
      catch (InterruptedException localInterruptedException) {}
    }
  }
  
  private String createQuitMessage(int paramInt, String paramString)
  {
    String str = "";
    switch (paramInt)
    {
    case 5: 
      break;
    case 7: 
      paramString = Localization.getInstance().localize(paramString);
      break;
    case 1: 
      str = "Kicked";
      break;
    case 3: 
      str = "Your connection is too slow";
    }
    if (str.length() > 0)
    {
      str = Localization.getInstance().localize(str);
      if (paramString.length() > 0) {
        str = str + ": ";
      } else {
        str = str + ".";
      }
    }
    return str + paramString;
  }
  
  public int refreshCredits()
  {
    synchronized (this.balanceLock)
    {
      try
      {
        DataOutputPacket localDataOutputPacket = new DataOutputPacket();
        localDataOutputPacket.writeByte(23);
        if (this.active) {
          this.connection.write(localDataOutputPacket);
        }
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
      try
      {
        this.balanceLock.wait();
      }
      catch (InterruptedException localInterruptedException) {}
    }
    return this.credits;
  }
  
  public int userBuyProduct(Product paramProduct, Object paramObject)
  {
    synchronized (this.buyLock)
    {
      if (this.buyMode == 2)
      {
        try
        {
          DataOutputPacket localDataOutputPacket = new DataOutputPacket();
          localDataOutputPacket.writeByte(24);
          localDataOutputPacket.writeUTF(paramProduct.getProductName());
          if (this.active) {
            this.connection.write(localDataOutputPacket);
          }
        }
        catch (IOException localIOException1)
        {
          localIOException1.printStackTrace();
        }
      }
      else
      {
        Object localObject1;
        if (this.buyMode == 1)
        {
          localObject1 = this.screen.getCreateGameOptions();
          writeBuyRoom((Map)localObject1, paramProduct.getProductName());
        }
        else if (this.buyMode == 3)
        {
          try
          {
            localObject1 = new DataOutputPacket();
            ((DataOutputPacket)localObject1).writeByte(32);
            ((DataOutputPacket)localObject1).writeUTF(paramProduct.getProductName());
            ((DataOutputPacket)localObject1).writeUTF((String)paramObject);
            if (this.active) {
              this.connection.write((DataOutputPacket)localObject1);
            }
          }
          catch (IOException localIOException2)
          {
            localIOException2.printStackTrace();
          }
        }
      }
      try
      {
        this.buyLock.wait();
      }
      catch (InterruptedException localInterruptedException) {}
    }
    this.purchaseFrame.close();
    this.purchaseFrame = null;
    if (this.buyMode == 2)
    {
      if ((this.buyResult == 1) && (this.chosenGameRoom != null)) {
        loadGame(this.chosenGameRoom, 11);
      }
    }
    else if (this.buyMode == 1)
    {
      if (this.buyResult == 1) {
        this.screen.buyRoomOk();
      } else {
        this.screen.buyRoomFailed();
      }
    }
    else if (this.buyMode == 3) {
      if (this.buyResult == 1) {
        this.screen.buyDonationOk();
      } else {
        this.screen.buyDonationFailed();
      }
    }
    this.buyMode = 0;
    return this.buyResult;
  }
  
  public void userCanceled()
  {
    this.purchaseFrame.close();
    this.purchaseFrame = null;
    if (this.buyMode == 1) {
      this.screen.buyRoomCanceled();
    }
    this.buyMode = 0;
  }
  
  private void passwordEntered(ActionEvent paramActionEvent)
  {
    this.enteredPassword = this.screen.getPasswordBoxContents();
    this.screen.hidePasswordBox();
    loadGame(this.chosenGameRoom, 29);
  }
  
  private void parseChallenge(DataInputPacket paramDataInputPacket)
    throws IOException
  {
    int i = paramDataInputPacket.readByte() & 0xFF;
    Object localObject;
    switch (i)
    {
    case 65: 
      localObject = Collections.synchronizedMap(new HashMap(10));
      int j = paramDataInputPacket.readInt();
      String str1 = paramDataInputPacket.readUTF();
      int k = paramDataInputPacket.readByte() & 0xFF;
      for (int m = 0; m < k; m++)
      {
        String str2 = paramDataInputPacket.readUTF();
        String str3 = paramDataInputPacket.readUTF();
        ((Map)localObject).put(str2, str3);
      }
      this.screen.challengePropose(str1, j, (Map)localObject);
      break;
    case 61: 
      localObject = paramDataInputPacket.readUTF();
      this.screen.challengeAccept((String)localObject);
      break;
    case 62: 
      localObject = paramDataInputPacket.readUTF();
      this.screen.challengeRefuse((String)localObject);
      break;
    case 63: 
      localObject = paramDataInputPacket.readUTF();
      this.screen.challengeCancel((String)localObject);
      break;
    case 66: 
      System.out.println("Init game...");
    }
  }
}
