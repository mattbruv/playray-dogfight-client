package com.aapeli.multiplayer.client.session.chat.view;

import com.aapeli.multiplayer.client.common.GUIColors;
import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.common.network.ChatProtocol;
import com.aapeli.shared.ChatTextArea;
import com.aapeli.shared.UserList;
import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalButtonUI;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public abstract class View
  extends JPanel
  implements HierarchyListener, ChatProtocol
{
  protected ActionListener actionListener;
  protected StyledDocument document;
  protected JTextField chatField;
  protected JButton exitButton;
  protected JLabel bgLabel;
  protected UserList userList;
  protected boolean inputEnabled;
  private Applet applet;
  protected JButton[] tabButton;
  protected JLabel[] tabLabel;
  protected boolean chatVisible = true;
  public static final String NORMAL = "black";
  public static final String HIGHLIGHT = "gray";
  public static final String PRIVATE = "blue";
  private static final int TAB_WIDTH = 120;
  private static final int TAB_GAP = 10;
  private int tabType;
  protected ChatTextArea chatArea;
  protected Map parameters;
  private Timer blinkTimer;
  private JButton blinkingButton;
  private boolean blinking = false;
  
  protected View(UserList paramUserList, Applet paramApplet, int paramInt, Map paramMap)
  {
    this.parameters = paramMap;
    this.tabType = paramInt;
    this.applet = paramApplet;
    addHierarchyListener(this);
    this.userList = paramUserList;
    setFocusable(false);
    setLayout(null);
    setBackground(GUIColors.getInstance().get(GUIColors.TOP_BG));
    setFont(new Font("Dialog", 0, 11));
  }
  
  private void initTabs()
  {
    int[] arrayOfInt = (int[])this.parameters.get("tabs");
    this.tabButton = new JButton[arrayOfInt.length];
    this.tabLabel = new JLabel[this.tabButton.length];
    int i = 10;
    for (int j = 0; j < arrayOfInt.length; j++)
    {
      int k = arrayOfInt[j];
      if (k != this.tabType)
      {
        this.tabButton[j] = new JButton();
        this.tabButton[j].setFont(new Font("Dialog", 1, 14));
        this.tabButton[j].setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BORDER), 2, true));
      }
      else
      {
        this.tabLabel[j] = new JLabel();
        this.tabLabel[j].setFont(new Font("Dialog", 1, 14));
        this.tabLabel[j].setHorizontalAlignment(0);
        this.tabLabel[j].setHorizontalTextPosition(10);
        this.tabLabel[j].setOpaque(true);
        this.tabLabel[j].setBackground(GUIColors.getInstance().get(GUIColors.BG));
      }
      String str = null;
      Object localObject = null;
      switch (k)
      {
      case 50: 
        localObject = new ActionListener()
        {
          public void actionPerformed(ActionEvent paramAnonymousActionEvent)
          {
            View.this.actionListener.actionPerformed(new ActionEvent(this, 50, ""));
          }
        };
        str = (String)this.parameters.get("view_name_chat");
        if (str == null) {
          str = "Chat";
        }
        str = Localization.getInstance().localize(str);
        break;
      case 52: 
        localObject = new ActionListener()
        {
          public void actionPerformed(ActionEvent paramAnonymousActionEvent)
          {
            View.this.actionListener.actionPerformed(new ActionEvent(this, 52, ""));
          }
        };
        str = (String)this.parameters.get("view_name_join");
        if (str == null) {
          str = "Join";
        }
        str = Localization.getInstance().localize(str);
        break;
      case 51: 
        localObject = new ActionListener()
        {
          public void actionPerformed(ActionEvent paramAnonymousActionEvent)
          {
            View.this.actionListener.actionPerformed(new ActionEvent(this, 51, ""));
          }
        };
        str = (String)this.parameters.get("view_name_create");
        if (str == null) {
          str = "Create a game";
        }
        str = Localization.getInstance().localize(str);
        break;
      case 53: 
        localObject = new ActionListener()
        {
          public void actionPerformed(ActionEvent paramAnonymousActionEvent)
          {
            View.this.actionListener.actionPerformed(new ActionEvent(this, 53, ""));
          }
        };
        str = (String)this.parameters.get("view_name_manage");
        if (str == null) {
          str = "Manage";
        }
        str = Localization.getInstance().localize(str);
        break;
      case 55: 
        localObject = new ActionListener()
        {
          public void actionPerformed(ActionEvent paramAnonymousActionEvent)
          {
            View.this.actionListener.actionPerformed(new ActionEvent(this, 55, ""));
          }
        };
        str = (String)this.parameters.get("view_name_challenge");
        if (str == null) {
          str = "Challenge";
        }
        str = Localization.getInstance().localize(str);
      }
      Rectangle localRectangle = new Rectangle(i, 10, 120, 30);
      i += 130;
      if (k != this.tabType)
      {
        this.tabButton[j].addActionListener((ActionListener)localObject);
        this.tabButton[j].setText(str);
        add(this.tabButton[j]);
        this.tabButton[j].setBounds(localRectangle);
      }
      else
      {
        this.tabLabel[j].setText(str);
        add(this.tabLabel[j]);
        this.tabLabel[j].setBounds(localRectangle);
      }
    }
    setBlink(55);
  }
  
  protected void initBGComponents()
  {
    this.bgLabel = new JLabel();
    this.bgLabel.setOpaque(true);
    this.bgLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    add(this.bgLabel);
    this.bgLabel.setBounds(0, 40, 740, 510);
  }
  
  protected void initChatField()
  {
    this.chatField = new JTextField();
    this.chatField.setEditable(true);
    this.chatField.setFont(getFont());
    this.chatField.setText("");
    this.chatField.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BORDER), 2, true));
    this.chatField.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        View.this.sendActionPerformed(paramAnonymousActionEvent);
      }
    });
    add(this.chatField);
  }
  
  protected void initFGComponents()
  {
    initTabs();
    initChatField();
    this.exitButton = new JButton();
    this.exitButton.setBackground(new Color(255, 153, 153));
    this.exitButton.setFont(new Font("Dialog", 1, 14));
    this.exitButton.setText(Localization.getInstance().localize("Exit"));
    this.exitButton.setBorder(new LineBorder(new Color(153, 0, 51), 2, true));
    add(this.exitButton, 0);
    this.exitButton.setBounds(600, 10, 70, 27);
    this.exitButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        View.this.exitActionPerformed(paramAnonymousActionEvent);
      }
    });
  }
  
  public void setChatVisible(boolean paramBoolean)
  {
    this.chatVisible = paramBoolean;
    this.userList.setVisible(paramBoolean);
    if (this.chatArea != null) {
      this.chatArea.setVisible(paramBoolean);
    }
  }
  
  private void exitActionPerformed(ActionEvent paramActionEvent)
  {
    try
    {
      URL localURL = new URL(this.applet.getParameter("quitpage"));
      this.applet.getAppletContext().showDocument(localURL);
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
    }
  }
  
  private void addStylesToDocument(StyledDocument paramStyledDocument)
  {
    Style localStyle1 = StyleContext.getDefaultStyleContext().getStyle("default");
    Style localStyle2 = paramStyledDocument.addStyle("regular", localStyle1);
    StyleConstants.setFontFamily(localStyle2, getFont().getFontName());
    StyleConstants.setFontSize(localStyle2, getFont().getSize());
    StyleConstants.setBold(localStyle2, getFont().isBold());
    Style localStyle3 = paramStyledDocument.addStyle("black", localStyle2);
    StyleConstants.setForeground(localStyle3, Color.black);
    localStyle3 = paramStyledDocument.addStyle("gray", localStyle2);
    StyleConstants.setForeground(localStyle3, Color.gray);
    localStyle3 = paramStyledDocument.addStyle("blue", localStyle2);
    StyleConstants.setForeground(localStyle3, Color.blue);
  }
  
  public void addActionListener(ActionListener paramActionListener)
  {
    this.actionListener = paramActionListener;
  }
  
  protected void sendActionPerformed(ActionEvent paramActionEvent)
  {
    String str = this.chatField.getText();
    if (str.length() > 0)
    {
      this.chatField.setText("");
      this.actionListener.actionPerformed(new ActionEvent(this, 12, str));
    }
  }
  
  public void requestFocus()
  {
    this.chatField.setEditable(true);
    this.chatField.requestFocus();
  }
  
  public boolean isInputEnabled()
  {
    return this.inputEnabled;
  }
  
  public void setInputEnabled(boolean paramBoolean)
  {
    this.inputEnabled = paramBoolean;
  }
  
  public void hierarchyChanged(HierarchyEvent paramHierarchyEvent)
  {
    if ((paramHierarchyEvent.getChangeFlags() & 1L) != 0L) {}
  }
  
  public abstract void reinitChat();
  
  public void setChatArea(ChatTextArea paramChatTextArea)
  {
    if (this.chatArea != null) {
      remove(this.chatArea);
    }
    this.chatArea = paramChatTextArea;
    add(paramChatTextArea);
    paramChatTextArea.setVisible(this.chatVisible);
  }
  
  public void setBlink(int paramInt)
  {
    int[] arrayOfInt = (int[])this.parameters.get("tabs");
    int i = 0;
    for (int j = 0; j < arrayOfInt.length; j++) {
      if ((arrayOfInt[j] == paramInt) && (this.tabButton[j] != null))
      {
        this.blinkingButton = this.tabButton[j];
        i = 1;
        break;
      }
    }
    if (i == 0) {
      return;
    }
    this.blinkTimer = new Timer(1000, new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        View.this.blink();
      }
    });
    this.blinkTimer.setInitialDelay(0);
    this.blinkTimer.start();
  }
  
  private void blink()
  {
    if (this.blinkingButton != null)
    {
      if (this.blinking) {
        this.blinkingButton.setUI(new MetalButtonUI());
      } else {
        this.blinkingButton.setUI(new BlinkButtonUI());
      }
      this.blinking = (!this.blinking);
      repaint();
    }
  }
}
