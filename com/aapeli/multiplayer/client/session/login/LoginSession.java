package com.aapeli.multiplayer.client.session.login;

import com.aapeli.multiplayer.client.network.Connection;
import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.client.session.ClientSession;
import com.aapeli.multiplayer.client.session.ClientSessionManager;
import com.aapeli.multiplayer.common.network.DataInputPacket;
import com.aapeli.multiplayer.common.network.DataOutputPacket;
import com.aapeli.multiplayer.common.network.LoginProtocol;
import java.applet.Applet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import javax.swing.JComponent;

public class LoginSession
  extends ClientSession
  implements LoginProtocol, ActionListener
{
  private LoginScreen screen;
  private boolean active = false;
  private boolean loggingIn = false;
  private String gameVersion;
  
  public LoginSession(ClientSessionManager paramClientSessionManager, String paramString)
  {
    super(paramClientSessionManager);
    this.gameVersion = paramString;
    this.screen = new LoginScreen(paramClientSessionManager.getApplet(), this);
  }
  
  public void login(Connection paramConnection, Map paramMap)
  {
    this.active = true;
    super.login(paramConnection, paramMap);
  }
  
  public void logout()
  {
    this.active = false;
  }
  
  private void sendSession()
  {
    String str = this.manager.getApplet().getParameter("session");
    if (str == null)
    {
      System.out.println("No session found, logging as guest.");
      doAnonymousLogin();
    }
    else
    {
      try
      {
        DataOutputPacket localDataOutputPacket = new DataOutputPacket();
        localDataOutputPacket.writeByte(15);
        localDataOutputPacket.writeUTF(str);
        this.connection.write(localDataOutputPacket);
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
    }
  }
  
  public void read(DataInputPacket paramDataInputPacket)
  {
    try
    {
      int i = paramDataInputPacket.read();
      switch (i)
      {
      case 86: 
        String str1 = paramDataInputPacket.readUTF();
        String[] arrayOfString1 = str1.split("\t", 2);
        if (arrayOfString1[1].equals("1.58"))
        {
          System.out.println("Multiplayer version ok: 1.58");
          String str2 = paramDataInputPacket.readUTF();
          String[] arrayOfString2 = str2.split("\t", 2);
          if (arrayOfString2[1].equals(this.gameVersion))
          {
            System.out.println("Game version ok: " + this.gameVersion);
            sendSession();
          }
          else
          {
            this.manager.error(this.connection, Localization.getInstance().localize("Your game is outdated, please close all browsers and try again.") + " " + this.gameVersion + " <-> " + arrayOfString2[1]);
          }
        }
        else
        {
          this.manager.error(this.connection, Localization.getInstance().localize("Your game is outdated, please close all browsers and try again.") + " " + "1.58" + " <-> " + arrayOfString1[1]);
        }
        break;
      case 10: 
        this.connection.setNick(paramDataInputPacket.readUTF());
        this.connection.setAdmin(paramDataInputPacket.readBoolean());
        this.connection.setSheriff(paramDataInputPacket.readBoolean());
        System.out.println("Logged in as " + this.connection.getNick() + " " + (this.connection.isSheriff() ? "Sheriff" : this.connection.isAdmin() ? "Admin" : "Normal"));
        logout();
        this.manager.transfer(this.connection, "Chat");
        break;
      case 12: 
        this.connection.setNick(paramDataInputPacket.readUTF());
        logout();
        this.manager.transfer(this.connection, "Chat");
        break;
      case 11: 
        doAnonymousLogin();
        System.out.println("Session login failed: " + paramDataInputPacket.readByte());
        break;
      case 13: 
        this.loggingIn = false;
        doAnonymousLogin();
        System.out.println("Guest login failed: " + paramDataInputPacket.readByte());
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
    if (!this.loggingIn)
    {
      this.loggingIn = true;
      String str = this.screen.getName();
      if ((str == null) || (str.length() == 0)) {
        this.screen.setGuestLogin();
      } else {
        try
        {
          System.out.println("Submitting guest login.");
          DataOutputPacket localDataOutputPacket = new DataOutputPacket();
          localDataOutputPacket.writeByte(14);
          localDataOutputPacket.writeUTF(str);
          this.connection.write(localDataOutputPacket);
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
        }
      }
    }
  }
  
  public void destroy() {}
  
  private void doAnonymousLogin()
  {
    if (!this.loggingIn)
    {
      this.loggingIn = true;
      try
      {
        DataOutputPacket localDataOutputPacket = new DataOutputPacket();
        localDataOutputPacket.writeByte(16);
        this.connection.write(localDataOutputPacket);
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
    }
  }
}
