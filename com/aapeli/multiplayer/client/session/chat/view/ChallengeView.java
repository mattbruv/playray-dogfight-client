package com.aapeli.multiplayer.client.session.chat.view;

import com.aapeli.multiplayer.client.common.GUIColors;
import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.shared.ChatTextArea;
import com.aapeli.shared.UserList;
import com.aapeli.shared.UserListItem;
import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.util.Map;
import javax.swing.Box.Filler;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

public class ChallengeView
  extends View
{
  private CreatePane createPane;
  private JButton sendButton;
  private JLabel chatAreaLabel;
  private JLabel userListLabel;
  private JLabel chatFieldLabel;
  private JPanel challengeArea;
  private JLabel challengeText;
  private JLabel buttonBGLabel;
  private JButton quickplayButton;
  private JButton challengeButton;
  private JButton acceptButton;
  private JButton refuseButton;
  private JCheckBox challengesCheckBox;
  private JCheckBox announcementsCheckBox;
  private JLabel createPanelLabel;
  private static final int CREATE_Y = 60;
  private static final int CREATE_HEIGHT = 200;
  private static final int BUTTON_AREA_Y = 265;
  private static final int BUTTON_AREA_HEIGHT = 110;
  private static final int CHAT_Y = 380;
  private static final int CHAT_HEIGHT = 165;
  private int quickplayButtonMode = 1;
  private int challengeButtonMode = 1;
  private static final int YOU_CHALLENGED_SOMEONE = 1;
  private static final int SOMEONE_CHALLENGED_YOU = 2;
  private static final int NOT_CHALLENGED = 3;
  private static final int WAITING = 4;
  private int mode = 3;
  private String opponent = "";
  private static final int NOT_CHOSEN = 1;
  private static final int CHOSEN = 2;
  private Map defaultSettings;
  private Map currentValues;
  
  public ChallengeView(UserList paramUserList, Applet paramApplet, Map paramMap)
  {
    super(paramUserList, paramApplet, 55, paramMap);
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
    this.chatField.setBounds(195, 520, 445, 21);
    this.sendButton.setBackground(GUIColors.getInstance().get(GUIColors.BUTTON));
    this.sendButton.setFont(new Font("Dialog", 1, 14));
    this.sendButton.setText(Localization.getInstance().localize("Send"));
    this.sendButton.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BUTTON_BORDER), 2, true));
    this.sendButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        ChallengeView.this.sendActionPerformed(paramAnonymousActionEvent);
      }
    });
    add(this.sendButton);
    this.sendButton.setBounds(645, 520, 80, 21);
    this.chatAreaLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.chatAreaLabel.setFont(new Font("Dialog", 1, 14));
    this.chatAreaLabel.setText(Localization.getInstance().localize("Chat"));
    this.chatAreaLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.chatAreaLabel.setOpaque(true);
    add(this.chatAreaLabel);
    this.chatAreaLabel.setBounds(190, 380, 540, 20);
    this.userListLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.userListLabel.setFont(new Font("Dialog", 1, 14));
    this.userListLabel.setText(Localization.getInstance().localize("Users"));
    this.userListLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.userListLabel.setOpaque(true);
    add(this.userListLabel);
    this.userListLabel.setBounds(10, 380, 170, 20);
    this.chatFieldLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.chatFieldLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.chatFieldLabel.setOpaque(true);
    add(this.chatFieldLabel);
    this.chatFieldLabel.setBounds(190, 515, 540, 30);
    add(this.userList);
    this.challengeArea = new JPanel();
    this.challengeArea.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.challengeArea.setOpaque(false);
    this.challengeArea.setLayout(new BoxLayout(this.challengeArea, 0));
    add(this.challengeArea);
    this.challengeArea.setLocation(10, 270);
    this.challengeText = new JLabel("");
    this.challengeText.setForeground(GUIColors.getInstance().get(GUIColors.TEXT_FG));
    this.challengeText.setOpaque(false);
    this.challengeButton = new JButton();
    this.challengeButton.setBackground(GUIColors.getInstance().get(GUIColors.BUTTON));
    this.challengeButton.setFont(new Font("Dialog", 1, 14));
    this.challengeButton.setText(Localization.getInstance().localize("Challenge"));
    this.challengeButton.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BUTTON_BORDER), 2, true));
    this.challengeButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        ChallengeView.this.challengeButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    this.acceptButton = new JButton();
    this.acceptButton.setBackground(GUIColors.getInstance().get(GUIColors.BUTTON));
    this.acceptButton.setFont(new Font("Dialog", 1, 14));
    this.acceptButton.setText(Localization.getInstance().localize("Accept"));
    this.acceptButton.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BUTTON_BORDER), 2, true));
    this.acceptButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        ChallengeView.this.acceptButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    this.refuseButton = new JButton();
    this.refuseButton.setBackground(GUIColors.getInstance().get(GUIColors.BUTTON));
    this.refuseButton.setFont(new Font("Dialog", 1, 14));
    this.refuseButton.setText(Localization.getInstance().localize("Refuse"));
    this.refuseButton.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BUTTON_BORDER), 2, true));
    this.refuseButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        ChallengeView.this.refuseButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    this.quickplayButton = new JButton();
    this.quickplayButton.setBackground(GUIColors.getInstance().get(GUIColors.BUTTON));
    this.quickplayButton.setFont(new Font("Dialog", 1, 14));
    this.quickplayButton.setText(Localization.getInstance().localize("Quickplay"));
    this.quickplayButton.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BUTTON_BORDER), 2, true));
    this.quickplayButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        ChallengeView.this.quickplayButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    add(this.quickplayButton);
    this.quickplayButton.setBounds(10, 300, 150, 21);
    this.challengesCheckBox = new JCheckBox();
    this.challengesCheckBox.setOpaque(false);
    this.challengesCheckBox.setFont(new Font("Dialog", 1, 14));
    this.challengesCheckBox.setText(Localization.getInstance().localize("No challenges"));
    this.challengesCheckBox.setForeground(GUIColors.getInstance().get(GUIColors.TEXT_FG));
    this.challengesCheckBox.setSelected(false);
    add(this.challengesCheckBox, new Integer(2));
    this.challengesCheckBox.setLocation(190, 305);
    this.challengesCheckBox.setSize(this.challengesCheckBox.getPreferredSize());
    this.announcementsCheckBox = new JCheckBox();
    this.announcementsCheckBox.setOpaque(false);
    this.announcementsCheckBox.setFont(new Font("Dialog", 1, 14));
    this.announcementsCheckBox.setText(Localization.getInstance().localize("No announcements"));
    this.announcementsCheckBox.setForeground(GUIColors.getInstance().get(GUIColors.TEXT_FG));
    this.announcementsCheckBox.setSelected(false);
    add(this.announcementsCheckBox, new Integer(2));
    this.announcementsCheckBox.setLocation(190, 325);
    this.announcementsCheckBox.setSize(this.announcementsCheckBox.getPreferredSize());
    this.createPane = new CreatePane();
    add(this.createPane);
    this.createPane.setBounds(10, 80, 720, 180);
    this.createPanelLabel = new JLabel();
    this.createPanelLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.createPanelLabel.setFont(new Font("Dialog", 1, 14));
    this.createPanelLabel.setText(Localization.getInstance().localize("Challenge options"));
    this.createPanelLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.createPanelLabel.setOpaque(true);
    add(this.createPanelLabel);
    this.createPanelLabel.setBounds(10, 60, 720, 20);
    showNotChallenged();
  }
  
  protected void acceptButtonActionPerformed(ActionEvent paramActionEvent)
  {
    if (this.mode == 2)
    {
      showWaiting();
      this.actionListener.actionPerformed(new ActionEvent(this, 61, this.opponent));
    }
  }
  
  protected void refuseButtonActionPerformed(ActionEvent paramActionEvent)
  {
    if (this.mode == 2)
    {
      showNotChallenged();
      this.actionListener.actionPerformed(new ActionEvent(this, 62, this.opponent));
    }
  }
  
  protected void challengeButtonActionPerformed(ActionEvent paramActionEvent)
  {
    if (this.challengeButtonMode == 1)
    {
      if ((this.mode == 3) && (this.defaultSettings != null))
      {
        UserListItem localUserListItem = this.userList.getSelectedUser();
        if (localUserListItem != null)
        {
          showYouChallengedSomeone();
          this.opponent = localUserListItem.getNick();
          this.currentValues = this.createPane.getChosenOptions();
          this.createPane.setEnabledAll(false);
          this.actionListener.actionPerformed(new ActionEvent(this, 65, this.opponent));
        }
      }
    }
    else if (this.mode == 1)
    {
      showNotChallenged();
      this.actionListener.actionPerformed(new ActionEvent(this, 63, this.opponent));
    }
  }
  
  protected void quickplayButtonActionPerformed(ActionEvent paramActionEvent)
  {
    if (this.quickplayButtonMode == 1)
    {
      this.quickplayButtonMode = 2;
      this.quickplayButton.setBackground(GUIColors.getInstance().get(GUIColors.BUTTON_CHOSEN));
      this.quickplayButton.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BUTTON_CHOSEN_BORDER), 2, true));
      this.quickplayButton.setText(Localization.getInstance().localize("Undo quickplay"));
    }
    else
    {
      this.quickplayButtonMode = 1;
      this.quickplayButton.setBackground(GUIColors.getInstance().get(GUIColors.BUTTON));
      this.quickplayButton.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BUTTON_BORDER), 2, true));
      this.quickplayButton.setText(Localization.getInstance().localize("Quickplay"));
    }
  }
  
  public void reinitChat()
  {
    add(this.userList);
    this.userList.setBounds(10, 400, 170, 145);
    if (this.chatArea != null)
    {
      add(this.chatArea);
      this.chatArea.setBounds(190, 400, 540, 115);
    }
  }
  
  private void showNotChallenged()
  {
    resetSettings();
    this.mode = 3;
    this.challengeArea.removeAll();
    this.challengeText.setText("");
    this.challengeButtonMode = 1;
    this.challengeButton.setBackground(GUIColors.getInstance().get(GUIColors.BUTTON));
    this.challengeButton.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BUTTON_BORDER), 2, true));
    this.challengeButton.setText(Localization.getInstance().localize("Challenge"));
    this.challengeArea.add(this.challengeButton);
    this.challengeArea.setSize(this.challengeArea.getPreferredSize());
    this.challengeArea.validate();
  }
  
  private void showSomeoneChallengedYou(String paramString, int paramInt)
  {
    this.mode = 2;
    this.challengeArea.removeAll();
    this.challengeText.setText(paramString + " (" + paramInt + ") " + Localization.getInstance().localize("has challenged you!"));
    this.challengeArea.add(this.challengeText);
    Dimension localDimension = new Dimension(3, 1);
    this.challengeArea.add(new Box.Filler(localDimension, localDimension, localDimension));
    this.challengeArea.add(this.acceptButton);
    this.challengeArea.add(new Box.Filler(localDimension, localDimension, localDimension));
    this.challengeArea.add(this.refuseButton);
    this.challengeArea.setSize(this.challengeArea.getPreferredSize());
    this.challengeArea.validate();
  }
  
  private void showYouChallengedSomeone()
  {
    this.mode = 1;
    this.createPane.setEnabledAll(false);
    this.challengeArea.removeAll();
    this.challengeText.setText(Localization.getInstance().localize("Waiting for a response..."));
    this.challengeButtonMode = 2;
    this.challengeButton.setBackground(GUIColors.getInstance().get(GUIColors.BUTTON_CHOSEN));
    this.challengeButton.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BUTTON_CHOSEN_BORDER), 2, true));
    this.challengeButton.setText(Localization.getInstance().localize("Undo challenge"));
    this.challengeArea.add(this.challengeText);
    Dimension localDimension = new Dimension(3, 1);
    this.challengeArea.add(new Box.Filler(localDimension, localDimension, localDimension));
    this.challengeArea.add(this.challengeButton);
    this.challengeArea.setSize(this.challengeArea.getPreferredSize());
    this.challengeArea.validate();
  }
  
  private void showWaiting()
  {
    this.mode = 4;
    this.challengeArea.removeAll();
    this.challengeText.setText(Localization.getInstance().localize("Waiting..."));
    this.challengeArea.add(this.challengeText);
    this.challengeArea.setSize(this.challengeArea.getPreferredSize());
    this.challengeArea.validate();
  }
  
  public void challengeCancel(String paramString)
  {
    if (((this.mode == 2) || (this.mode == 4)) && (paramString.equals(this.opponent))) {
      showNotChallenged();
    }
  }
  
  public void challengePropose(String paramString, int paramInt, Map paramMap)
  {
    if ((this.mode == 3) && (this.defaultSettings != null))
    {
      this.currentValues = this.createPane.getChosenOptions();
      this.createPane.setSettingsMap(paramMap);
      this.createPane.setEnabledAll(false);
      showSomeoneChallengedYou(paramString, paramInt);
      this.opponent = paramString;
    }
    else
    {
      this.actionListener.actionPerformed(new ActionEvent(this, 63, paramString));
    }
  }
  
  public void challengeAccept(String paramString)
  {
    if ((this.mode == 1) && (paramString.equals(this.opponent)))
    {
      showWaiting();
      this.actionListener.actionPerformed(new ActionEvent(this, 64, paramString));
    }
  }
  
  public void challengeRefuse(String paramString)
  {
    if ((this.mode == 1) && (paramString.equals(this.opponent))) {
      showNotChallenged();
    }
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
    this.actionListener.actionPerformed(new ActionEvent(this, 34, ""));
  }
  
  public void setEnabledAll(boolean paramBoolean)
  {
    this.createPane.setEnabledAll(paramBoolean);
  }
  
  public void setSettingsMap(Map paramMap)
  {
    if (this.defaultSettings == null)
    {
      this.defaultSettings = paramMap;
      this.currentValues = this.defaultSettings;
      this.createPane.setSettingsMap(this.defaultSettings);
      this.createPane.setEnabledAll(true);
    }
  }
  
  private void resetSettings()
  {
    if (this.currentValues != null)
    {
      this.createPane.setSettingsMap(this.currentValues);
      this.createPane.setEnabledAll(true);
    }
  }
  
  public Map getChosenOptions()
  {
    return this.createPane.getChosenOptions();
  }
}
