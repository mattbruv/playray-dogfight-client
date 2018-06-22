package com.aapeli.multiplayer.client.session.chat.view;

import com.aapeli.multiplayer.client.common.GUIColors;
import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.shared.ChatTextArea;
import com.aapeli.shared.UserList;
import java.applet.Applet;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

public class ChatView
  extends View
{
  private JButton sendButton;
  private JLabel chatAreaLabel;
  private JLabel userListLabel;
  private JLabel chatFieldLabel;
  
  public ChatView(UserList paramUserList, Applet paramApplet, Map paramMap)
  {
    super(paramUserList, paramApplet, 50, paramMap);
    super.initFGComponents();
    initComponents();
    super.initBGComponents();
  }
  
  protected void initComponents()
  {
    this.sendButton = new JButton();
    this.chatAreaLabel = new JLabel();
    this.userListLabel = new JLabel();
    this.chatFieldLabel = new JLabel();
    this.chatField.setBounds(195, 515, 445, 21);
    this.sendButton.setBackground(GUIColors.getInstance().get(GUIColors.BUTTON));
    this.sendButton.setFont(new Font("Dialog", 1, 14));
    this.sendButton.setText(Localization.getInstance().localize("Send"));
    this.sendButton.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BUTTON_BORDER), 2, true));
    this.sendButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        ChatView.this.sendActionPerformed(paramAnonymousActionEvent);
      }
    });
    add(this.sendButton);
    this.sendButton.setBounds(645, 515, 80, 21);
    this.chatAreaLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.chatAreaLabel.setFont(new Font("Dialog", 1, 14));
    this.chatAreaLabel.setText(Localization.getInstance().localize("Chat"));
    this.chatAreaLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.chatAreaLabel.setOpaque(true);
    add(this.chatAreaLabel);
    this.chatAreaLabel.setBounds(190, 60, 540, 20);
    this.userListLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.userListLabel.setFont(new Font("Dialog", 1, 14));
    this.userListLabel.setText(Localization.getInstance().localize("Users"));
    this.userListLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.userListLabel.setOpaque(true);
    add(this.userListLabel);
    this.userListLabel.setBounds(10, 60, 170, 20);
    this.chatFieldLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.chatFieldLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.chatFieldLabel.setOpaque(true);
    add(this.chatFieldLabel);
    this.chatFieldLabel.setBounds(190, 510, 540, 30);
    add(this.userList);
  }
  
  public void reinitChat()
  {
    add(this.userList);
    this.userList.setBounds(10, 80, 170, 460);
    if (this.chatArea != null)
    {
      add(this.chatArea);
      this.chatArea.setBounds(190, 80, 540, 430);
    }
  }
}
