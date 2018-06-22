package com.aapeli.multiplayer.client.launcher;

import com.aapeli.bigtext.BigText;
import com.aapeli.client.ImageManager;
import com.aapeli.client.Parameters;
import com.aapeli.multiplayer.client.network.ConnectionMaker;
import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.client.resources.SoundSystem;
import com.aapeli.multiplayer.client.session.ClientSessionManager;
import com.aapeli.multiplayer.client.session.chat.ChatSession;
import com.aapeli.multiplayer.client.session.game.GameSession;
import com.aapeli.multiplayer.client.session.game.implInterface.Game;
import com.aapeli.multiplayer.client.session.login.LoginSession;
import com.aapeli.multiplayer.common.network.CreditConverter;
import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.util.ImageUtils;
import java.io.PrintStream;
import java.util.Map;
import javax.swing.JApplet;

public class AbstractLauncher
  extends JApplet
{
  protected ConnectionMaker connectionMaker;
  protected ClientSessionManager manager;
  protected static int instances = 0;
  protected String gameName;
  
  public void init(Map paramMap)
  {
    this.gameName = ((String)paramMap.get("game_name"));
    if (this.gameName == null)
    {
      System.out.println("Game name has to be defined!");
      return;
    }
    System.out.println("-= Multiplayerplatform 1.581 =-");
    System.out.println("Copyright (c) 2006 Apaja (www.apaja.com)");
    System.out.println("Programming: Pyry Lehdonvirta");
    System.out.println("Graphics: Janne Matilainen & Tom Liimatta");
    System.out.println("");
    instances += 1;
    System.out.println("Java version: " + System.getProperty("java.version"));
    ImageUtils.setHeadless(false);
    ImageLoader.setRootURL(getCodeBase());
    SoundSystem.setRootURL(getCodeBase());
    Parameters localParameters = new Parameters(this);
    Localization.getInstance().init(localParameters);
    ImageManager localImageManager = new ImageManager(this);
    BigText.initialize(localImageManager);
    String str = (String)paramMap.get("game_version");
    if (str == null) {
      str = "1.0";
    }
    initCreditConstants();
    this.manager = new ClientSessionManager(this, localParameters, localImageManager);
    this.manager.putSession("Login", new LoginSession(this.manager, str));
    this.manager.putSession("Chat", new ChatSession(this.manager, this.gameName, paramMap));
    this.connectionMaker = new ConnectionMaker(localParameters.getServerIp(), localParameters.getServerPort(), this.manager);
  }
  
  public void setGame(Game paramGame)
  {
    this.manager.putSession(this.gameName, new GameSession(this.manager, paramGame));
  }
  
  public void connect()
  {
    this.connectionMaker.connect();
  }
  
  public void destroy()
  {
    instances -= 1;
    this.manager.destroy();
    this.connectionMaker.disconnect();
    this.manager = null;
    this.connectionMaker = null;
    if (instances == 0) {
      SoundSystem.destroy();
    }
    System.gc();
    try
    {
      Thread.sleep(100L);
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
  }
  
  protected void initCreditConstants() {}
}
