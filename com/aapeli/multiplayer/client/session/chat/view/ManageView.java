package com.aapeli.multiplayer.client.session.chat.view;

import com.aapeli.multiplayer.client.common.GUIColors;
import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.client.session.chat.create.LabelledComboBox;
import com.aapeli.multiplayer.client.session.chat.create.OptionList;
import com.aapeli.multiplayer.client.session.chat.create.RoomTimePanel;
import com.aapeli.shared.UserList;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

public class ManageView
  extends View
  implements ItemListener
{
  public static final int USERLIST_WIDTH = 170;
  public static final int USERLIST_HEIGHT = 103;
  public static final int CHAT_AREA_HEIGHT = 80;
  private JButton sendButton;
  private JButton createGameButton;
  private JButton helpButton;
  private JLabel chatLabel;
  private JLabel userListLabel;
  private JLabel chatFieldLabel;
  private JLabel createPanelLabel;
  private JLabel createButtonLabel;
  private JLabel helpButtonLabel;
  private JPanel createPanel;
  private JPanel roomTimeBackgroundPanel;
  private JScrollPane scrollPane;
  private Map optionMap;
  private Map settingsMap = new HashMap(1);
  private Map idMap = Collections.synchronizedMap(new HashMap(5));
  private JLabel noRoomsLabel;
  private RoomTimePanel roomTimePanel;
  private OptionList currentOptionList;
  private String lastChosenName;
  
  public ManageView(UserList paramUserList, Applet paramApplet, Map paramMap)
  {
    super(paramUserList, paramApplet, 53, paramMap);
    super.initFGComponents();
    initComponents();
    super.initBGComponents();
  }
  
  protected void initChatField() {}
  
  protected void initComponents()
  {
    this.sendButton = new JButton();
    this.chatLabel = new JLabel();
    this.userListLabel = new JLabel();
    this.chatFieldLabel = new JLabel();
    this.createPanelLabel = new JLabel();
    this.createButtonLabel = new JLabel();
    this.createPanelLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.createPanelLabel.setFont(new Font("Dialog", 1, 14));
    this.createPanelLabel.setText(Localization.getInstance().localize("Manage"));
    this.createPanelLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.createPanelLabel.setOpaque(true);
    add(this.createPanelLabel);
    this.createPanelLabel.setBounds(10, 60, 720, 20);
    add(this.userList);
    this.createGameButton = new JButton();
    this.createGameButton.setBackground(GUIColors.getInstance().get(GUIColors.BUTTON));
    this.createGameButton.setFont(new Font("Dialog", 1, 14));
    this.createGameButton.setText(Localization.getInstance().localize("Commit"));
    this.createGameButton.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BUTTON_BORDER), 2, true));
    this.createGameButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        ManageView.this.manageGameButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    add(this.createGameButton);
    this.createGameButton.setBounds(615, 390, 110, 21);
    this.createButtonLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.createButtonLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.createButtonLabel.setOpaque(true);
    add(this.createButtonLabel);
    this.createButtonLabel.setBounds(610, 385, 120, 30);
    this.helpButton = new JButton();
    this.helpButton.setBackground(new Color(255, 153, 153));
    this.helpButton.setFont(new Font("Dialog", 1, 14));
    this.helpButton.setText(Localization.getInstance().localize("Price_help"));
    this.helpButton.setBorder(new LineBorder(new Color(204, 0, 51), 2, true));
    this.helpButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        ManageView.this.helpButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    add(this.helpButton);
    this.helpButton.setBounds(15, 390, 140, 21);
    this.helpButtonLabel = new JLabel();
    this.helpButtonLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.helpButtonLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.helpButtonLabel.setOpaque(true);
    add(this.helpButtonLabel);
    this.helpButtonLabel.setBounds(10, 385, 150, 30);
    this.createPanel = new JPanel();
    this.createPanel.setBackground(GUIColors.getInstance().get(GUIColors.AREA_BG));
    this.createPanel.setLayout(null);
    this.scrollPane = new JScrollPane(22, 30);
    this.scrollPane.getViewport().add(this.createPanel);
    this.scrollPane.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.SHARP_BORDER)));
    add(this.scrollPane);
    this.scrollPane.setBounds(10, 83, 720, 297);
    this.noRoomsLabel = new JLabel(Localization.getInstance().localize("You aren't an administrator of any room."));
    this.noRoomsLabel.setFont(new Font("Dialog", 1, 14));
    this.noRoomsLabel.setLocation(5, 0);
    this.noRoomsLabel.setSize(this.noRoomsLabel.getPreferredSize());
    this.createPanel.add(this.noRoomsLabel);
    this.createPanel.setPreferredSize(this.noRoomsLabel.getPreferredSize());
    this.roomTimeBackgroundPanel = new JPanel();
    this.roomTimeBackgroundPanel.setOpaque(true);
    this.roomTimeBackgroundPanel.setBackground(GUIColors.getInstance().get(GUIColors.AREA_BG));
    this.roomTimeBackgroundPanel.setLayout(null);
    this.roomTimeBackgroundPanel.setVisible(false);
    this.roomTimeBackgroundPanel.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.SHARP_BORDER)));
    add(this.roomTimeBackgroundPanel);
    this.roomTimeBackgroundPanel.setBounds(10, 420, 720, 120);
    this.roomTimePanel = new RoomTimePanel(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        ManageView.this.roomTimePanelActionPerformed(paramAnonymousActionEvent);
      }
    });
    this.roomTimeBackgroundPanel.add(this.roomTimePanel);
    this.roomTimePanel.setBounds(5, 5, 710, 110);
  }
  
  private void roomTimePanelActionPerformed(ActionEvent paramActionEvent)
  {
    this.actionListener.actionPerformed(new ActionEvent(this, paramActionEvent.getID(), paramActionEvent.getActionCommand() + "\t" + getChosenID()));
  }
  
  private void initOptions(Map paramMap)
  {
    if ((this.optionMap != null) && (this.optionMap.size() > 0) && (paramMap.size() > 0))
    {
      this.roomTimeBackgroundPanel.setVisible(true);
      this.createPanel.remove(this.noRoomsLabel);
      String[] arrayOfString1 = new String[this.optionMap.size()];
      int i = 0;
      Object localObject1 = this.optionMap.keySet().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        arrayOfString1[i] = ((String)((Iterator)localObject1).next());
        i++;
      }
      localObject1 = (OptionList)this.optionMap.get(arrayOfString1[0]);
      String[] arrayOfString2 = new String[paramMap.size()];
      this.idMap.clear();
      i = 0;
      Object localObject2 = paramMap.entrySet().iterator();
      String str2;
      Map localMap;
      while (((Iterator)localObject2).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject2).next();
        str2 = (String)localEntry.getKey();
        localMap = (Map)localEntry.getValue();
        arrayOfString2[i] = ((String)localMap.get("param_name"));
        this.idMap.put(arrayOfString2[i], str2);
        i++;
      }
      localObject2 = null;
      if ((this.currentOptionList != null) && (this.currentOptionList.isNameEnabled()))
      {
        localObject2 = this.currentOptionList.getManageNameComponent().getValue();
        this.currentOptionList.getManageNameComponent().removeItemListener(this);
      }
      if (this.currentOptionList != null) {
        this.createPanel.remove(this.currentOptionList);
      }
      this.currentOptionList = ((OptionList)localObject1);
      if (this.currentOptionList.isNameEnabled())
      {
        this.currentOptionList.getManageNameComponent().setValues(arrayOfString2);
        if (localObject2 != null) {
          for (int j = 0; j < arrayOfString2.length; j++) {
            if (((String)localObject2).equals(arrayOfString2[j]))
            {
              this.currentOptionList.getManageNameComponent().setValue((String)localObject2);
              break;
            }
          }
        }
        this.currentOptionList.getManageNameComponent().addItemListener(this);
        String str1 = this.currentOptionList.getManageNameComponent().getValue();
        str2 = (String)this.idMap.get(str1);
        localMap = (Map)paramMap.get(str2);
        this.currentOptionList.setParameters(localMap);
        this.currentOptionList.showManageName();
        this.roomTimePanel.setRoomParameters(localMap);
      }
      this.createPanel.add(this.currentOptionList);
      this.currentOptionList.setSize(this.currentOptionList.getPreferredSize());
      this.currentOptionList.setLocation(10, 10);
      this.createPanel.setPreferredSize(new Dimension(this.currentOptionList.getPreferredSize().width + 10, this.currentOptionList.getPreferredSize().height + 10));
      validate();
      repaint();
    }
    else
    {
      if (this.currentOptionList != null)
      {
        remove(this.currentOptionList);
        this.currentOptionList = null;
      }
      this.roomTimeBackgroundPanel.setVisible(false);
      this.createPanel.add(this.noRoomsLabel);
      this.createPanel.setPreferredSize(this.noRoomsLabel.getPreferredSize());
    }
  }
  
  private String getGameName()
  {
    return (String)this.optionMap.keySet().iterator().next();
  }
  
  public Map getChosenOptions()
  {
    String str = getGameName();
    Map localMap = this.currentOptionList.getResults();
    localMap.put("param_type", str);
    localMap.put("param_id", getChosenID());
    return localMap;
  }
  
  private String getChosenID()
  {
    String str = this.currentOptionList.getManageNameComponent().getValue();
    return (String)this.idMap.get(str);
  }
  
  public void reinitChat()
  {
    refreshSettingsMap();
  }
  
  private void refreshSettingsMap()
  {
    this.actionListener.actionPerformed(new ActionEvent(this, 31, ""));
  }
  
  protected void manageGameButtonActionPerformed(ActionEvent paramActionEvent)
  {
    this.actionListener.actionPerformed(new ActionEvent(this, 30, ""));
    this.actionListener.actionPerformed(new ActionEvent(this, 52, ""));
  }
  
  public void hierarchyChanged(HierarchyEvent paramHierarchyEvent)
  {
    if ((paramHierarchyEvent.getChangeFlags() & 1L) != 0L) {
      initOptions(this.settingsMap);
    }
  }
  
  public void setSettingsMap(Map paramMap)
  {
    this.settingsMap = paramMap;
    initOptions(paramMap);
  }
  
  public void setOptions(Map paramMap)
  {
    this.optionMap = paramMap;
    initOptions(this.settingsMap);
  }
  
  public void itemStateChanged(ItemEvent paramItemEvent)
  {
    if ((paramItemEvent.getID() == 701) && (paramItemEvent.getStateChange() == 1) && (this.settingsMap != null))
    {
      String str = (String)this.idMap.get(paramItemEvent.getItem());
      if (str != null)
      {
        Map localMap = (Map)this.settingsMap.get(str);
        this.currentOptionList.setParameters(localMap);
        this.roomTimePanel.setRoomParameters(localMap);
      }
    }
  }
  
  protected void helpButtonActionPerformed(ActionEvent paramActionEvent)
  {
    this.actionListener.actionPerformed(new ActionEvent(this, 54, ""));
  }
}
