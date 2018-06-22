package com.aapeli.shared;

import com.aapeli.client.InputTextField;
import com.aapeli.client.StringDraw;
import com.aapeli.client.TextManager;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

class AdminFrame
  extends JFrame
  implements WindowListener, ItemListener, KeyListener, ActionListener
{
  protected static final int MODE_REMOVE = 1;
  protected static final int MODE_MESSAGE = 2;
  protected static final int MODE_MUTE = 3;
  protected static final int MODE_CLEAR = 4;
  protected static final int MODE_BROADCAST = 5;
  private static final Color BG_COLOR = new Color(224, 224, 224);
  private static final Color BG_COLOR_DARK = new Color(208, 208, 208);
  private static final Color FG_COLOR = new Color(0, 0, 0);
  private static final String[] MINUTES_PRESET_TEXTS = { "Presets...", "1 hour", "2 hours", "3 hours", "6 hours", "12 hours", "1 day", "2 days", "4 days", "~1 week" };
  private static final int[] MINUTES_PRESET_MINUTES = { 0, 60, 120, 180, 360, 720, 1440, 2880, 5760, 9999 };
  private static final int MINUTES_PRESETS = MINUTES_PRESET_TEXTS.length;
  private static final int BAN_SHERIFF_DEFAULT = 180;
  private static final int BAN_ADMIN_DEFAULT = 360;
  private static final int MML_BROADCAST = 1000;
  private static final int MML_ADMIN = 1500;
  private static final int MML_SHERIFF = 500;
  private static final int SX = 420;
  private static final int SY = 190;
  private int rsx;
  private int rsy;
  private Insets insets;
  private TextManager tm;
  private UserListHandler ulh;
  private int mode;
  private String nick;
  private InputTextField gui_nick;
  private InputTextField gui_message;
  private InputTextField gui_minutes;
  private ColorCheckbox gui_ban;
  private ColorCheckbox gui_ip;
  private Choice gui_sdm;
  private Choice gui_minpreset;
  private ColorButton gui_ok;
  private ColorButton gui_cancel;
  
  protected AdminFrame(TextManager paramTextManager, UserListHandler paramUserListHandler, int paramInt, String paramString)
  {
    this.tm = paramTextManager;
    this.ulh = paramUserListHandler;
    this.mode = paramInt;
    this.nick = paramString;
    this.rsx = 420;
    this.rsy = 190;
  }
  
  public void addNotify()
  {
    super.addNotify();
    repaint();
  }
  
  public void paint(Graphics paramGraphics)
  {
    update(paramGraphics);
  }
  
  public void update(Graphics paramGraphics)
  {
    paramGraphics.setColor(BG_COLOR);
    paramGraphics.fillRect(0, 0, this.rsx, this.rsy);
    if (this.insets != null)
    {
      paramGraphics.setColor(BG_COLOR_DARK);
      paramGraphics.fillRect(this.insets.left, this.insets.top, 420, 45);
      paramGraphics.fillRect(this.insets.left, this.insets.top + 190 - 10 - 25 - 10, 420, 45);
    }
    paramGraphics.setColor(FG_COLOR);
    if (this.mode == 3) {
      StringDraw.drawStringWithMaxWidth(paramGraphics, "Mute target user so none of his messages are visible to others. Muted user is not notified about this, therefore user may think that other people still see his messages. Mute will stay until user leave this gameserver and returns.", this.insets.left + 10, 100, -1, this.rsx - this.insets.right - 10 - this.insets.left - 10);
    }
    if (this.mode == 4) {
      StringDraw.drawStringWithMaxWidth(paramGraphics, "(Message is displayed to all users after chat is cleared. To avoid any confusion, it's highly recommended that some message is provided.)", this.insets.left + 10, 135, -1, this.rsx - this.insets.right - 10 - this.insets.left - 10);
    }
  }
  
  public void windowOpened(WindowEvent paramWindowEvent) {}
  
  public void windowClosed(WindowEvent paramWindowEvent) {}
  
  public void windowClosing(WindowEvent paramWindowEvent)
  {
    cancelClicked();
  }
  
  public void windowActivated(WindowEvent paramWindowEvent) {}
  
  public void windowDeactivated(WindowEvent paramWindowEvent) {}
  
  public void windowIconified(WindowEvent paramWindowEvent) {}
  
  public void windowDeiconified(WindowEvent paramWindowEvent) {}
  
  public void itemStateChanged(ItemEvent paramItemEvent)
  {
    Object localObject = paramItemEvent.getSource();
    if (localObject == this.gui_ban)
    {
      this.gui_ok.setLabel(this.gui_ban.getState() ? "Ban" : "Kick");
    }
    else
    {
      int i;
      if (localObject == this.gui_sdm)
      {
        i = this.gui_sdm.getSelectedIndex();
        if (i == 0) {
          return;
        }
        String str = getSDM(i);
        if (str != null) {
          this.gui_message.setText(str);
        }
        this.gui_sdm.select(0);
      }
      else if (localObject == this.gui_minpreset)
      {
        i = this.gui_minpreset.getSelectedIndex();
        if (i == 0) {
          return;
        }
        this.gui_minutes.setText("" + MINUTES_PRESET_MINUTES[i]);
        this.gui_ban.setState(true);
      }
    }
  }
  
  public void keyPressed(KeyEvent paramKeyEvent)
  {
    if ((paramKeyEvent.getSource() == this.gui_message) && (paramKeyEvent.getKeyCode() == 10)) {
      okClicked();
    }
  }
  
  public void keyReleased(KeyEvent paramKeyEvent) {}
  
  public void keyTyped(KeyEvent paramKeyEvent) {}
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    Object localObject = paramActionEvent.getSource();
    if (localObject == this.gui_ok) {
      okClicked();
    }
    if (localObject == this.gui_cancel) {
      cancelClicked();
    }
  }
  
  protected void start(Component paramComponent, boolean paramBoolean)
  {
    String str1 = null;
    if (this.mode == 1) {
      str1 = "Sheriff: Remove user";
    }
    if (this.mode == 2) {
      str1 = "Sheriff: Send message to user";
    }
    if (this.mode == 3) {
      str1 = "Sheriff: Mute user";
    }
    if (this.mode == 4) {
      str1 = "Sheriff: Clear chat of every user";
    }
    if (this.mode == 5) {
      str1 = "Admin: Broadcast message to all users";
    }
    setTitle(str1);
    setVisible(true);
    this.insets = getInsets();
    this.rsx = (this.insets.left + 420 + this.insets.right);
    this.rsy = (this.insets.top + 190 + this.insets.bottom);
    setSize(this.rsx, this.rsy);
    setResizable(false);
    Point localPoint = paramComponent.getLocationOnScreen();
    Dimension localDimension = paramComponent.getSize();
    setLocation(localPoint.x + localDimension.width / 2 - this.rsx / 2, localPoint.y + localDimension.height / 2 - this.rsy / 2);
    setComponentColors(this, true);
    setLayout(null);
    String str2 = null;
    Label localLabel1 = new Label("Target user:");
    localLabel1.setBounds(this.insets.left + 10, this.insets.top + 10, 80, 25);
    setComponentColors(localLabel1, false);
    add(localLabel1);
    if ((this.mode == 1) || (this.mode == 2) || (this.mode == 3))
    {
      this.gui_nick = new InputTextField(this.nick, 16);
      this.gui_nick.setBounds(this.insets.left + 10 + 80 + 5, this.insets.top + 10, 150, 25);
      this.gui_nick.noClearOnFirstFocus();
      add(this.gui_nick);
    }
    if ((this.mode == 4) || (this.mode == 5))
    {
      Label localLabel2 = new Label(this.mode == 4 ? "EVERY user in this lobby" : "All");
      localLabel2.setBounds(this.insets.left + 10 + 80 + 5, this.insets.top + 10, 160, 25);
      setComponentColors(localLabel2, false);
      add(localLabel2);
    }
    if (this.mode == 1)
    {
      this.gui_ban = new ColorCheckbox("Ban user, minutes:");
      int i = this.gui_ban.getPreferredSize().width;
      this.gui_ban.setBounds(this.insets.left + 10, this.insets.top + 10 + 25 + 20, i, 20);
      setComponentColors(this.gui_ban, true);
      add(this.gui_ban);
      this.gui_minutes = new InputTextField("" + (paramBoolean ? 360 : 180), 4);
      this.gui_minutes.setBounds(this.insets.left + 10 + i + 5, this.insets.top + 10 + 25 + 20, 50, 20);
      this.gui_minutes.noClearOnFirstFocus();
      add(this.gui_minutes);
      this.gui_minpreset = new Choice();
      for (int j = 0; j < MINUTES_PRESETS; j++) {
        this.gui_minpreset.addItem(MINUTES_PRESET_TEXTS[j]);
      }
      this.gui_minpreset.setBounds(this.rsx - this.insets.right - 15 - 100, this.insets.top + 10 + 25 + 20, 100, 20);
      this.gui_minpreset.setBackground(Color.white);
      this.gui_minpreset.setForeground(Color.black);
      this.gui_minpreset.select(0);
      this.gui_minpreset.addItemListener(this);
      add(this.gui_minpreset);
      str2 = "Kick";
    }
    Label localLabel3;
    if ((this.mode == 4) || (this.mode == 2) || (this.mode == 5))
    {
      localLabel3 = new Label("Message:");
      localLabel3.setBounds(this.insets.left + 10, this.insets.top + 10 + 25 + 20, 80, 25);
      setComponentColors(localLabel3, true);
      add(localLabel3);
      this.gui_message = new InputTextField(paramBoolean ? 1500 : this.mode == 5 ? 1000 : 500);
      this.gui_message.setBounds(this.insets.left + 10 + 80 + 5, this.insets.top + 10 + 25 + 20, 315, 25);
      if (this.mode == 4) {
        this.gui_message.setText(this.tm.getShared("SDM_ChatCleared"));
      }
      add(this.gui_message);
    }
    if (this.mode == 2)
    {
      localLabel3 = new Label("Default messages:");
      localLabel3.setBounds(this.insets.left + 10 + 80 + 5, this.insets.top + 10 + 25 + 20 + 5 + 25, 140, 23);
      setComponentColors(localLabel3, true);
      add(localLabel3);
      this.gui_sdm = new Choice();
      this.gui_sdm.addItem("Choose...");
      this.gui_sdm.addItem("Bad nickname");
      this.gui_sdm.addItem("Sex messages");
      this.gui_sdm.addItem("Cursing/Flooding");
      this.gui_sdm.setBounds(this.insets.left + 10 + 80 + 5 + 140 + 5, this.insets.top + 10 + 25 + 20 + 25 + 5, 170, 23);
      this.gui_sdm.setBackground(Color.white);
      this.gui_sdm.setForeground(Color.black);
      this.gui_sdm.select(0);
      this.gui_sdm.addItemListener(this);
      add(this.gui_sdm);
      this.gui_ip = new ColorCheckbox("Add target user IP to message");
      this.gui_ip.setBounds(this.insets.left + 10, this.insets.top + 10 + 25 + 20 + 25 + 5 + 25 + 5, 400, 20);
      setComponentColors(this.gui_ip, true);
      add(this.gui_ip);
      str2 = "Send";
    }
    if (this.mode == 3) {
      str2 = "Mute";
    }
    if (this.mode == 4) {
      str2 = "Clear";
    }
    if (this.mode == 5) {
      str2 = "Broadcast";
    }
    this.gui_ok = new ColorButton(str2);
    this.gui_ok.setBounds(this.insets.left + 420 - 10 - 90 - 5 - 90, this.insets.top + 190 - 10 - 25, 90, 25);
    this.gui_ok.setBackground(new Color(160, 160, 224));
    add(this.gui_ok);
    this.gui_cancel = new ColorButton("Cancel");
    this.gui_cancel.setBounds(this.insets.left + 420 - 10 - 90, this.insets.top + 190 - 10 - 25, 90, 25);
    add(this.gui_cancel);
    if (this.mode == 1) {
      this.gui_ban.addItemListener(this);
    }
    if ((this.mode == 2) || (this.mode == 5)) {
      this.gui_message.addKeyListener(this);
    }
    this.gui_ok.addActionListener(this);
    this.gui_cancel.addActionListener(this);
    addWindowListener(this);
    requestFocus();
    repaint();
  }
  
  private void setComponentColors(Component paramComponent, boolean paramBoolean)
  {
    paramComponent.setBackground(paramBoolean ? BG_COLOR : BG_COLOR_DARK);
    paramComponent.setForeground(FG_COLOR);
  }
  
  private String getSDM(int paramInt)
  {
    if (paramInt == 1)
    {
      int i = 0;
      String str = this.gui_nick.getInputText(false);
      if ((str.length() > 0) && (str.charAt(0) != '~')) {
        i = 1;
      }
      return this.tm.getShared("SDM_BadNick" + (i != 0 ? "Reg" : "Worm"));
    }
    if (paramInt == 2) {
      return this.tm.getShared("SDM_SexMessages");
    }
    if (paramInt == 3) {
      return this.tm.getShared("SDM_BadMessages");
    }
    return null;
  }
  
  private void okClicked()
  {
    String str1;
    if (this.mode == 1)
    {
      str1 = this.gui_nick.getInputText(false);
      if (str1.length() > 0) {
        if (!this.gui_ban.getState())
        {
          this.ulh.adminCommand("kick", str1);
        }
        else
        {
          int i;
          try
          {
            i = Integer.parseInt(this.gui_minutes.getInputText(false));
          }
          catch (NumberFormatException localNumberFormatException)
          {
            i = 0;
          }
          if (i <= 0) {
            this.ulh.adminCommand("ban", str1);
          } else {
            this.ulh.adminCommand("ban", str1, "" + i);
          }
        }
      }
    }
    else if (this.mode == 2)
    {
      str1 = this.gui_nick.getInputText(false);
      String str2 = this.gui_message.getInputText(false);
      if ((str1.length() > 0) && (str2.length() > 0)) {
        this.ulh.adminCommand("message" + (this.gui_ip.getState() ? "ip" : ""), str1, str2);
      }
    }
    else if (this.mode == 3)
    {
      str1 = this.gui_nick.getInputText(false);
      if (str1.length() > 0) {
        this.ulh.adminCommand("mute", str1);
      }
    }
    else if (this.mode == 4)
    {
      str1 = this.gui_message.getInputText(false);
      if (str1.length() > 0) {
        this.ulh.adminCommand("clear", str1);
      }
    }
    else if (this.mode == 5)
    {
      str1 = this.gui_message.getInputText(false);
      if (str1.length() > 0) {
        this.ulh.adminCommand("broadcast", str1);
      }
    }
    dispose();
  }
  
  private void cancelClicked()
  {
    dispose();
  }
}
