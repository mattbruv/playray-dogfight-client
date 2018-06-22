package com.aapeli.multiplayer.client.session.chat;

import com.aapeli.multiplayer.client.resources.Loadable;
import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.client.session.chat.view.ChallengeView;
import com.aapeli.multiplayer.client.session.chat.view.ChatView;
import com.aapeli.multiplayer.client.session.chat.view.CreateView;
import com.aapeli.multiplayer.client.session.chat.view.JoinView;
import com.aapeli.multiplayer.client.session.chat.view.ManageView;
import com.aapeli.multiplayer.client.session.chat.view.View;
import com.aapeli.multiplayer.common.network.ChatProtocol;
import com.aapeli.shared.ChatTextArea;
import com.aapeli.shared.UserList;
import java.applet.Applet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ChatScreen
  extends JLayeredPane
  implements ActionListener, ChatProtocol, ChangeListener
{
  private View activeView;
  private Map viewMap;
  private ActionListener actionListener;
  private String name;
  private LoadingBox loadingBox;
  private NotifyBox notifyBox;
  private PasswordBox passwordBox;
  private Shader shader;
  private UserList userList;
  private boolean chatVisible = true;
  private boolean productDataReady = false;
  private int defaultView;
  
  public ChatScreen(ActionListener paramActionListener, UserList paramUserList, Map paramMap1, Applet paramApplet, Map paramMap2)
  {
    this.defaultView = ((Integer)paramMap2.get("default_view")).intValue();
    this.actionListener = paramActionListener;
    setLayout(null);
    setFocusable(false);
    this.userList = paramUserList;
    this.viewMap = Collections.synchronizedMap(new HashMap(5));
    int[] arrayOfInt = (int[])paramMap2.get("tabs");
    for (int i = 0; i < arrayOfInt.length; i++)
    {
      Object localObject = null;
      switch (arrayOfInt[i])
      {
      case 50: 
        localObject = new ChatView(paramUserList, paramApplet, paramMap2);
        break;
      case 52: 
        localObject = new JoinView(paramUserList, paramApplet, paramMap2);
        break;
      case 51: 
        localObject = new CreateView(paramUserList, paramApplet, paramMap2);
        break;
      case 53: 
        localObject = new ManageView(paramUserList, paramApplet, paramMap2);
        break;
      case 55: 
        localObject = new ChallengeView(paramUserList, paramApplet, paramMap2);
      }
      if (localObject != null)
      {
        ((View)localObject).addActionListener(this);
        ((View)localObject).setBounds(0, 0, getWidth(), getHeight());
        this.viewMap.put(new Integer(arrayOfInt[i]), localObject);
      }
    }
    this.notifyBox = new NotifyBox();
    this.notifyBox.setVisible(false);
    add(this.notifyBox, new Integer(3));
    this.notifyBox.setBounds(160, 290, 440, 110);
    this.notifyBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        ChatScreen.this.notifyBoxOkPressed();
      }
    });
    this.shader = new Shader();
    this.shader.setVisible(false);
    add(this.shader, new Integer(2));
  }
  
  public void setChatArea(ChatTextArea paramChatTextArea)
  {
    synchronized (this.viewMap)
    {
      Iterator localIterator = this.viewMap.values().iterator();
      while (localIterator.hasNext())
      {
        View localView = (View)localIterator.next();
        localView.setChatArea(paramChatTextArea);
      }
    }
    if (this.activeView != null) {
      this.activeView.reinitChat();
    }
  }
  
  public Map getCreateGameOptions()
  {
    CreateView localCreateView = (CreateView)this.viewMap.get(new Integer(51));
    if (localCreateView == null) {
      return null;
    }
    return localCreateView.getChosenOptions();
  }
  
  public Map getChallengeOptions()
  {
    ChallengeView localChallengeView = (ChallengeView)this.viewMap.get(new Integer(55));
    if (localChallengeView == null) {
      return null;
    }
    return localChallengeView.getChosenOptions();
  }
  
  public Map getManageGameOptions()
  {
    ManageView localManageView = (ManageView)this.viewMap.get(new Integer(53));
    if (localManageView == null) {
      return null;
    }
    return localManageView.getChosenOptions();
  }
  
  private void setMode(int paramInt)
  {
    if (this.activeView != null) {
      remove(this.activeView);
    }
    View localView = (View)this.viewMap.get(new Integer(paramInt));
    if (localView == null) {
      return;
    }
    this.activeView = localView;
    this.activeView.reinitChat();
    this.activeView.setChatVisible(this.chatVisible);
    add(this.activeView, new Integer(1));
  }
  
  public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
    synchronized (this.viewMap)
    {
      Iterator localIterator = this.viewMap.values().iterator();
      while (localIterator.hasNext())
      {
        View localView = (View)localIterator.next();
        localView.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
      }
    }
    this.shader.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    switch (paramActionEvent.getID())
    {
    case 51: 
    case 53: 
    case 55: 
      if (!this.productDataReady) {
        break;
      }
    case 50: 
    case 52: 
      setMode(paramActionEvent.getID());
      break;
    case 42: 
    case 43: 
      break;
    case 44: 
    case 45: 
    case 46: 
    case 47: 
    case 48: 
    case 49: 
    case 54: 
    default: 
      this.actionListener.actionPerformed(paramActionEvent);
    }
  }
  
  public void setInputEnabled(boolean paramBoolean)
  {
    synchronized (this.viewMap)
    {
      Iterator localIterator = this.viewMap.values().iterator();
      while (localIterator.hasNext())
      {
        View localView = (View)localIterator.next();
        localView.setInputEnabled(paramBoolean);
      }
    }
  }
  
  public void setOnScreen(boolean paramBoolean)
  {
    if (paramBoolean) {
      setMode(this.defaultView);
    }
  }
  
  public void requestFocus()
  {
    if (this.activeView != null) {
      this.activeView.requestFocus();
    }
  }
  
  public void setManageMap(Map paramMap)
  {
    ManageView localManageView = (ManageView)this.viewMap.get(new Integer(53));
    if (localManageView == null) {
      return;
    }
    localManageView.setSettingsMap(paramMap);
  }
  
  public void setCreateMap(Map paramMap)
  {
    CreateView localCreateView = (CreateView)this.viewMap.get(new Integer(51));
    if (localCreateView != null) {
      localCreateView.setSettingsMap(paramMap);
    }
    ChallengeView localChallengeView = (ChallengeView)this.viewMap.get(new Integer(55));
    if (localChallengeView != null) {
      localChallengeView.setSettingsMap(paramMap);
    }
  }
  
  public void refreshGameSessionList(String[] paramArrayOfString1, int[] paramArrayOfInt, boolean[] paramArrayOfBoolean, String[][] paramArrayOfString, String[] paramArrayOfString2)
  {
    final JoinView localJoinView = (JoinView)this.viewMap.get(new Integer(52));
    if (localJoinView == null) {
      return;
    }
    localJoinView.setGameSessionList(paramArrayOfString1, paramArrayOfInt, paramArrayOfBoolean, paramArrayOfString, paramArrayOfString2);
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        localJoinView.refreshGameSessionList();
      }
    });
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void loadGame(Loadable paramLoadable, String paramString, int paramInt)
  {
    setChatVisible(false);
    this.shader.setVisible(true);
    this.shader.requestFocus();
    LoadingBar localLoadingBar = new LoadingBar(paramLoadable, paramString, paramInt);
    localLoadingBar.addChangeListener(this);
    this.loadingBox = new LoadingBox(localLoadingBar);
    add(this.loadingBox, new Integer(3));
    this.loadingBox.setBounds(160, 290, 440, 110);
    validate();
    repaint();
  }
  
  public void showPasswordBox(ActionListener paramActionListener)
  {
    if (this.passwordBox == null)
    {
      setInputEnabled(false);
      this.shader.setVisible(true);
      this.shader.requestFocus();
      this.passwordBox = new PasswordBox();
      this.passwordBox.setVisible(true);
      this.passwordBox.setTopic(Localization.getInstance().localize("Enter password"));
      add(this.passwordBox, new Integer(3));
      this.passwordBox.setBounds(160, 290, 440, 110);
      this.passwordBox.addActionListener(paramActionListener);
      this.passwordBox.requestFocus();
    }
  }
  
  public void showNotifyBox(String paramString1, String paramString2)
  {
    setInputEnabled(false);
    this.shader.setVisible(true);
    this.shader.requestFocus();
    this.notifyBox.setTopic(paramString1);
    this.notifyBox.setDescription(paramString2);
    this.notifyBox.setVisible(true);
  }
  
  public void stateChanged(ChangeEvent paramChangeEvent)
  {
    if ((paramChangeEvent.getSource() instanceof LoadingBar))
    {
      LoadingBar localLoadingBar = (LoadingBar)paramChangeEvent.getSource();
      if (localLoadingBar.getValue() == localLoadingBar.getMaximum())
      {
        localLoadingBar.removeChangeListener(this);
        this.actionListener.actionPerformed(new ActionEvent(this, localLoadingBar.getType(), localLoadingBar.getGameName()));
        remove(this.loadingBox);
        this.shader.setVisible(false);
        this.loadingBox = null;
      }
    }
  }
  
  public void setChatVisible(boolean paramBoolean)
  {
    this.chatVisible = paramBoolean;
    if (this.activeView != null) {
      this.activeView.setChatVisible(paramBoolean);
    }
  }
  
  private void hideNotifyBox()
  {
    setInputEnabled(true);
    this.shader.setVisible(false);
    this.notifyBox.setVisible(false);
  }
  
  public void hidePasswordBox()
  {
    setInputEnabled(true);
    this.shader.setVisible(false);
    remove(this.passwordBox);
    this.passwordBox = null;
  }
  
  public UserList getUserList()
  {
    return this.userList;
  }
  
  public boolean isLoading()
  {
    return this.loadingBox != null;
  }
  
  private void notifyBoxOkPressed()
  {
    if (this.notifyBox.isVisible())
    {
      setChatVisible(true);
      hideNotifyBox();
    }
  }
  
  public void setAdmin(boolean paramBoolean) {}
  
  public String getPasswordBoxContents()
  {
    if (this.passwordBox != null) {
      return this.passwordBox.getPassword();
    }
    return "";
  }
  
  public void buyRoomOk()
  {
    CreateView localCreateView = (CreateView)this.viewMap.get(new Integer(51));
    if (localCreateView != null) {
      localCreateView.setEnabledAll(true);
    }
    setMode(53);
  }
  
  public void buyRoomFailed()
  {
    setMode(this.defaultView);
    setChatVisible(false);
    showNotifyBox(Localization.getInstance().localize("Error"), Localization.getInstance().localize("Failed to buy the room."));
  }
  
  public void buyDonationOk() {}
  
  public void buyDonationFailed()
  {
    setChatVisible(false);
    showNotifyBox(Localization.getInstance().localize("Error"), Localization.getInstance().localize("Donation failed."));
  }
  
  public void buyRoomCanceled()
  {
    CreateView localCreateView = (CreateView)this.viewMap.get(new Integer(51));
    if (localCreateView == null) {
      return;
    }
    localCreateView.setEnabledAll(true);
  }
  
  public void setOptions(Map paramMap1, Map paramMap2, Map paramMap3)
  {
    CreateView localCreateView = (CreateView)this.viewMap.get(new Integer(51));
    if (localCreateView != null) {
      localCreateView.setOptions(paramMap1);
    }
    ManageView localManageView = (ManageView)this.viewMap.get(new Integer(53));
    if (localManageView != null) {
      localManageView.setOptions(paramMap3);
    }
    ChallengeView localChallengeView = (ChallengeView)this.viewMap.get(new Integer(55));
    if (localChallengeView != null) {
      localChallengeView.setOptions(paramMap2);
    }
    this.productDataReady = true;
  }
  
  public void challengeCancel(String paramString)
  {
    ChallengeView localChallengeView = (ChallengeView)this.viewMap.get(new Integer(55));
    if (localChallengeView != null) {
      localChallengeView.challengeCancel(paramString);
    }
  }
  
  public void challengePropose(String paramString, int paramInt, Map paramMap)
  {
    ChallengeView localChallengeView = (ChallengeView)this.viewMap.get(new Integer(55));
    if (localChallengeView != null) {
      localChallengeView.challengePropose(paramString, paramInt, paramMap);
    }
  }
  
  public void challengeAccept(String paramString)
  {
    ChallengeView localChallengeView = (ChallengeView)this.viewMap.get(new Integer(55));
    if (localChallengeView != null) {
      localChallengeView.challengeAccept(paramString);
    }
  }
  
  public void challengeRefuse(String paramString)
  {
    ChallengeView localChallengeView = (ChallengeView)this.viewMap.get(new Integer(55));
    if (localChallengeView != null) {
      localChallengeView.challengeRefuse(paramString);
    }
  }
}
