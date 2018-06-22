package com.aapeli.multiplayer.client.session;

import com.aapeli.client.ImageManager;
import com.aapeli.client.Parameters;
import com.aapeli.multiplayer.client.network.Connection;
import com.aapeli.multiplayer.client.session.error.ErrorSession;
import com.aapeli.multiplayer.client.session.game.GameSession;
import com.aapeli.multiplayer.client.session.game.implInterface.Game;
import java.applet.Applet;
import java.awt.Container;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

public class ClientSessionManager
{
  private Applet applet;
  private Parameters parameters;
  private JRootPane rootPane;
  protected Map sessionMap;
  private ImageManager imageManager;
  private Map classes;
  
  public ClientSessionManager(JApplet paramJApplet, Parameters paramParameters, ImageManager paramImageManager)
  {
    this(paramJApplet, paramParameters, paramJApplet.getRootPane(), paramImageManager);
  }
  
  public ClientSessionManager(JApplet paramJApplet, Parameters paramParameters, JRootPane paramJRootPane, ImageManager paramImageManager)
  {
    this.applet = paramJApplet;
    this.parameters = paramParameters;
    this.imageManager = paramImageManager;
    this.rootPane = paramJRootPane;
    paramJRootPane.getContentPane().setLayout(null);
    this.sessionMap = Collections.synchronizedMap(new HashMap(10));
    this.sessionMap.put("Error", new ErrorSession(this));
  }
  
  public boolean transfer(Connection paramConnection, String paramString)
  {
    return transfer(paramConnection, paramString, null);
  }
  
  public boolean transfer(Connection paramConnection, String paramString, Map paramMap)
  {
    System.out.println("Transfer request to " + paramString);
    if (this.sessionMap.containsKey(paramString))
    {
      final ClientSession localClientSession = getSession(paramString);
      paramConnection.setSession(localClientSession);
      localClientSession.login(paramConnection, paramMap);
      final Container localContainer = this.rootPane.getContentPane();
      try
      {
        SwingUtilities.invokeAndWait(new Runnable()
        {
          public void run()
          {
            localContainer.removeAll();
            JComponent localJComponent = localClientSession.getScreen();
            localJComponent.setBounds(0, 0, localContainer.getWidth(), localContainer.getHeight());
            localContainer.add(localClientSession.getScreen());
            localClientSession.start();
          }
        });
      }
      catch (InterruptedException localInterruptedException) {}catch (InvocationTargetException localInvocationTargetException)
      {
        localInvocationTargetException.printStackTrace();
      }
      localClientSession.getScreen().requestFocus();
      localContainer.repaint();
      return true;
    }
    return false;
  }
  
  public void error(Connection paramConnection, String paramString)
  {
    HashMap localHashMap = new HashMap(1);
    localHashMap.put("error", paramString);
    transfer(paramConnection, "Error", localHashMap);
  }
  
  public void destroy()
  {
    Iterator localIterator = this.sessionMap.values().iterator();
    while (localIterator.hasNext())
    {
      ClientSession localClientSession = (ClientSession)localIterator.next();
      localClientSession.destroy();
    }
  }
  
  public ClientSession getSession(String paramString)
  {
    return (ClientSession)this.sessionMap.get(paramString);
  }
  
  public void putSession(String paramString, ClientSession paramClientSession)
  {
    this.sessionMap.put(paramString, paramClientSession);
  }
  
  public Applet getApplet()
  {
    return this.applet;
  }
  
  public ImageManager getImageManager()
  {
    return this.imageManager;
  }
  
  public Parameters getParameters()
  {
    return this.parameters;
  }
  
  public Map getGameCreateOptions(Map paramMap)
  {
    HashMap localHashMap = new HashMap(3);
    Iterator localIterator = paramMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      String str2 = (String)paramMap.get(str1);
      String[] arrayOfString = str2.split("\t");
      GameSession localGameSession = (GameSession)getSession(str1);
      localHashMap.put(str1, localGameSession.getGame().createOptionList(arrayOfString));
    }
    return localHashMap;
  }
  
  public Map getGameManageOptions(Map paramMap)
  {
    HashMap localHashMap = new HashMap(3);
    Iterator localIterator = paramMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      String str2 = (String)paramMap.get(str1);
      String[] arrayOfString = str2.split("\t");
      GameSession localGameSession = (GameSession)getSession(str1);
      localHashMap.put(str1, localGameSession.getGame().manageOptionList(arrayOfString));
    }
    return localHashMap;
  }
}
