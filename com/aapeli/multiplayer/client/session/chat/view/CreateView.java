package com.aapeli.multiplayer.client.session.chat.view;

import com.aapeli.multiplayer.client.common.GUIColors;
import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.shared.ChatTextArea;
import com.aapeli.shared.UserList;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

public class CreateView
  extends View
{
  public static final int USERLIST_WIDTH = 170;
  public static final int USERLIST_HEIGHT = 170;
  private JButton sendButton;
  private JButton createGameButton;
  private JButton helpButton;
  private JLabel chatLabel;
  private JLabel userListLabel;
  private JLabel chatFieldLabel;
  private JLabel createPanelLabel;
  private JLabel createButtonLabel;
  private JLabel helpButtonLabel;
  private CreatePane createPane;
  
  public CreateView(UserList paramUserList, Applet paramApplet, Map paramMap)
  {
    super(paramUserList, paramApplet, 51, paramMap);
    super.initFGComponents();
    initComponents();
    super.initBGComponents();
  }
  
  protected void initComponents()
  {
    this.sendButton = new JButton();
    this.chatLabel = new JLabel();
    this.userListLabel = new JLabel();
    this.chatFieldLabel = new JLabel();
    this.createPanelLabel = new JLabel();
    this.chatField.setBounds(195, 515, 445, 21);
    this.sendButton.setBackground(GUIColors.getInstance().get(GUIColors.BUTTON));
    this.sendButton.setFont(new Font("Dialog", 1, 14));
    this.sendButton.setText(Localization.getInstance().localize("Send"));
    this.sendButton.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BUTTON_BORDER), 2, true));
    this.sendButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        CreateView.this.sendActionPerformed(paramAnonymousActionEvent);
      }
    });
    add(this.sendButton);
    this.sendButton.setBounds(645, 515, 80, 21);
    this.chatLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.chatLabel.setFont(new Font("Dialog", 1, 14));
    this.chatLabel.setText(Localization.getInstance().localize("Chat"));
    this.chatLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.chatLabel.setOpaque(true);
    add(this.chatLabel);
    this.chatLabel.setBounds(190, 350, 540, 20);
    this.userListLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.userListLabel.setFont(new Font("Dialog", 1, 14));
    this.userListLabel.setText(Localization.getInstance().localize("Users"));
    this.userListLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.userListLabel.setOpaque(true);
    add(this.userListLabel);
    this.userListLabel.setBounds(10, 350, 170, 20);
    this.chatFieldLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.chatFieldLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.chatFieldLabel.setOpaque(true);
    add(this.chatFieldLabel);
    this.chatFieldLabel.setBounds(190, 510, 540, 30);
    this.createPanelLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.createPanelLabel.setFont(new Font("Dialog", 1, 14));
    this.createPanelLabel.setText(Localization.getInstance().localize("Create a game"));
    this.createPanelLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.createPanelLabel.setOpaque(true);
    add(this.createPanelLabel);
    this.createPanelLabel.setBounds(10, 60, 720, 20);
    add(this.userList);
    this.createGameButton = new JButton();
    this.createGameButton.setBackground(GUIColors.getInstance().get(GUIColors.BUTTON));
    this.createGameButton.setFont(new Font("Dialog", 1, 14));
    this.createGameButton.setText(Localization.getInstance().localize("Create"));
    this.createGameButton.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BUTTON_BORDER), 2, true));
    this.createGameButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        CreateView.this.createGameButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    add(this.createGameButton);
    this.createGameButton.setBounds(615, 320, 110, 21);
    this.createButtonLabel = new JLabel();
    this.createButtonLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.createButtonLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.createButtonLabel.setOpaque(true);
    add(this.createButtonLabel);
    this.createButtonLabel.setBounds(610, 315, 120, 30);
    this.createPane = new CreatePane();
    add(this.createPane);
    this.createPane.setBounds(10, 80, 720, 230);
    this.helpButton = new JButton();
    this.helpButton.setBackground(new Color(255, 153, 153));
    this.helpButton.setFont(new Font("Dialog", 1, 14));
    this.helpButton.setText(Localization.getInstance().localize("Price_help"));
    this.helpButton.setBorder(new LineBorder(new Color(204, 0, 51), 2, true));
    this.helpButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        CreateView.this.helpButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    add(this.helpButton);
    this.helpButton.setBounds(15, 320, 140, 21);
    this.helpButtonLabel = new JLabel();
    this.helpButtonLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.helpButtonLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.helpButtonLabel.setOpaque(true);
    add(this.helpButtonLabel);
    this.helpButtonLabel.setBounds(10, 315, 150, 30);
  }
  
  public void reinitChat()
  {
    add(this.userList);
    this.userList.setBounds(10, 370, 170, 170);
    if (this.chatArea != null)
    {
      add(this.chatArea);
      this.chatArea.setBounds(190, 370, 540, 140);
    }
    refreshSettingsMap();
  }
  
  protected void createGameButtonActionPerformed(ActionEvent paramActionEvent)
  {
    this.createPane.setEnabledAll(false);
    this.actionListener.actionPerformed(new ActionEvent(this, 28, this.createPane.getProduct()));
  }
  
  protected void helpButtonActionPerformed(ActionEvent paramActionEvent)
  {
    this.actionListener.actionPerformed(new ActionEvent(this, 54, ""));
  }
  
  public void hierarchyChanged(HierarchyEvent paramHierarchyEvent)
  {
    if ((paramHierarchyEvent.getChangeFlags() & 1L) != 0L) {
      this.createPane.initOptions();
    }
  }
  
  public void setOptions(Map paramMap)
  {
    this.createPane.setOptions(paramMap);
  }
  
  public void setEnabledAll(boolean paramBoolean)
  {
    this.createPane.setEnabledAll(paramBoolean);
  }
  
  public void setSettingsMap(Map paramMap)
  {
    this.createPane.setSettingsMap(paramMap);
  }
  
  private void refreshSettingsMap()
  {
    this.actionListener.actionPerformed(new ActionEvent(this, 34, ""));
  }
  
  public Map getChosenOptions()
  {
    return this.createPane.getChosenOptions();
  }
}
