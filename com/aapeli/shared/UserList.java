package com.aapeli.shared;

import com.aapeli.client.ImageManager;
import com.aapeli.client.TextManager;
import com.aapeli.colorgui.ColorListItem;
import com.aapeli.tools.Tools;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class UserList
  extends JPanel
  implements ComponentListener, ItemListener, ActionListener
{
  public static final int SORT_NICKNAME_ABC = 1;
  public static final int SORT_NICKNAME_CBA = 2;
  public static final int SORT_RANKING_123 = 3;
  public static final int SORT_RANKING_321 = 4;
  private static final int ICON_COUNT = 14;
  private static final int ICON_SX = 11;
  private static final int ICON_SY = 11;
  private static final int CCBSY = 18;
  private static final int DEFAULT_SX = 100;
  private static final int DEFAULT_SY = 200;
  private static final Color BG_COLOR = Color.white;
  private static final Color FG_COLOR = Color.black;
  private static final Font SORT_BY_FONT = new Font("Dialog", 0, 9);
  private static final Color SORT_BY_COLOR = new Color(224, 224, 224);
  private static final Color SORT_BY_COLOR_SELECTED = new Color(208, 208, 255);
  private static final int SORT_BUTTON_SY = 11;
  private UserListHandler ulh;
  private TextManager tm;
  private ImageManager im;
  private int sx;
  private int sy;
  private Image[] image_icons;
  private boolean icons;
  private ColorList gui_nicklist;
  private ColorCheckbox gui_private;
  private ColorCheckbox gui_ignore;
  private ColorButton gui_sortrank;
  private ColorButton gui_sortnick;
  private Image bgimage;
  private Image colorlistbgimage;
  private int bgix;
  private int bgiy;
  private int lxongbset;
  private int lyonbgset;
  private boolean popup_enabled;
  private int popup_sheriff;
  private int popup_admin;
  private JPopupMenu popup_menu;
  private JMenuItem popup_opencard;
  private JMenuItem popup_private;
  private JMenuItem popup_ignore;
  private JMenuItem popup_remove;
  private JMenuItem popup_message;
  private JMenuItem popup_mute;
  private JMenuItem popup_clear;
  private JMenuItem popup_copy;
  private JMenuItem popup_info;
  private JMenuItem popup_broadcast;
  private UserListItem popup_user;
  private AdminFrame popup_frame;
  private Vector memory_privately;
  private Vector memory_ignore;
  private boolean sheriffnote;
  private boolean dimmernicks;
  private ColorTextArea cta;
  
  public UserList(UserListHandler paramUserListHandler, TextManager paramTextManager, ImageManager paramImageManager, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this(paramUserListHandler, paramTextManager, paramImageManager, paramBoolean1, paramBoolean2, paramBoolean3, 100, 200);
  }
  
  public UserList(UserListHandler paramUserListHandler, TextManager paramTextManager, ImageManager paramImageManager, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt1, int paramInt2)
  {
    this.ulh = paramUserListHandler;
    this.tm = paramTextManager;
    this.im = paramImageManager;
    this.sx = paramInt1;
    this.sy = paramInt2;
    setSize(paramInt1, paramInt2);
    this.icons = paramBoolean1;
    setBackground(BG_COLOR);
    setForeground(FG_COLOR);
    setLayout(paramBoolean2, paramBoolean3);
    if (paramBoolean1)
    {
      Image localImage = paramImageManager.getShared("ranking-icons.gif");
      this.image_icons = paramImageManager.separateImages(localImage, 14);
    }
    this.popup_enabled = false;
    this.popup_sheriff = (this.popup_admin = 0);
    this.memory_privately = new Vector();
    this.memory_ignore = new Vector();
    this.sheriffnote = (this.dimmernicks = 1);
    addComponentListener(this);
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
    if (this.bgimage != null)
    {
      paramGraphics.drawImage(this.bgimage, 0, 0, this.sx, this.sy, this.bgix, this.bgiy, this.bgix + this.sx, this.bgiy + this.sy, this);
    }
    else
    {
      paramGraphics.setColor(getBackground());
      paramGraphics.fillRect(0, 0, this.sx, this.sy);
    }
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    update(paramGraphics);
  }
  
  public void componentShown(ComponentEvent paramComponentEvent) {}
  
  public void componentHidden(ComponentEvent paramComponentEvent) {}
  
  public void componentMoved(ComponentEvent paramComponentEvent)
  {
    if (this.bgimage != null)
    {
      Point localPoint = getLocation();
      int i = localPoint.x - this.lxongbset;
      int j = localPoint.y - this.lyonbgset;
      setBackgroundImage(this.bgimage, this.colorlistbgimage, this.bgix + i, this.bgiy + j);
    }
    repaint();
    if (this.gui_private != null) {
      this.gui_private.repaint();
    }
  }
  
  public void componentResized(ComponentEvent paramComponentEvent)
  {
    Dimension localDimension = getSize();
    this.sx = localDimension.width;
    this.sy = localDimension.height;
    int i = this.gui_private != null ? 1 : 0;
    int j = this.gui_ignore != null ? 1 : 0;
    int k = 0;
    if (i != 0) {
      k++;
    }
    if (j != 0) {
      k++;
    }
    if (this.icons)
    {
      this.gui_sortrank.setSize(17, 11);
      this.gui_sortnick.setSize(this.sx - 17, 11);
    }
    int m = this.sx;
    int n = this.sy - (j != 0 ? 18 : 0) - (i != 0 ? 18 : 0) - ((j != 0) || (i != 0) ? 2 : 0) - (this.icons ? 11 : 0);
    this.gui_nicklist.setBounds(0, this.icons ? 11 : 0, m, n);
    if (i != 0) {
      this.gui_private.setBounds(0, this.sy - 18 - (j != 0 ? 18 : 0), this.sx, 18);
    }
    if (j != 0) {
      this.gui_ignore.setBounds(0, this.sy - 18, this.sx, 18);
    }
    componentMoved(paramComponentEvent);
  }
  
  public void itemStateChanged(ItemEvent paramItemEvent)
  {
    Object localObject1 = paramItemEvent.getSource();
    if (localObject1 == this.popup_private)
    {
      this.gui_private.click();
      return;
    }
    if (localObject1 == this.popup_ignore)
    {
      this.gui_ignore.click();
      return;
    }
    ColorListItem localColorListItem = this.gui_nicklist.getSelectedItem();
    int i = 0;
    if (localColorListItem == null)
    {
      setCheckboxesToFalse();
      i = 1;
      localObject2 = paramItemEvent.getItem();
      if (!(localObject2 instanceof ColorListItem)) {
        return;
      }
      localColorListItem = (ColorListItem)paramItemEvent.getItem();
    }
    Object localObject2 = (UserListItem)localColorListItem.getData();
    if (localObject1 == this.gui_nicklist)
    {
      int j = paramItemEvent.getID();
      if (j == 2)
      {
        this.ulh.openPlayerCard(((UserListItem)localObject2).getNick());
      }
      else if ((j == 1) && (this.popup_enabled))
      {
        int[] arrayOfInt = this.gui_nicklist.getLastClickedMouseXY();
        openPopUp((UserListItem)localObject2, arrayOfInt[0], arrayOfInt[1]);
      }
    }
    if (i != 0) {
      return;
    }
    if (localObject1 == this.gui_nicklist)
    {
      if (this.gui_private != null) {
        this.gui_private.setState(((UserListItem)localObject2).isPrivately());
      }
      if (this.gui_ignore != null) {
        this.gui_ignore.setState(((UserListItem)localObject2).isIgnore());
      }
      return;
    }
    if ((localObject1 == this.gui_private) || (localObject1 == this.gui_ignore))
    {
      if (((UserListItem)localObject2).isLocal())
      {
        setCheckboxesToFalse();
      }
      else
      {
        ((UserListItem)localObject2).setPrivately(this.gui_private != null ? this.gui_private.getState() : false);
        ((UserListItem)localObject2).setIgnore(this.gui_ignore != null ? this.gui_ignore.getState() : false);
        localColorListItem.setColor(getUserColor((UserListItem)localObject2));
        this.gui_nicklist.repaint();
      }
      return;
    }
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    Object localObject = paramActionEvent.getSource();
    if (localObject == this.gui_sortrank)
    {
      if (getSorting() == 4) {
        setSorting(3);
      } else {
        setSorting(4);
      }
    }
    else if (localObject == this.gui_sortnick)
    {
      if (getSorting() == 1) {
        setSorting(2);
      } else {
        setSorting(1);
      }
    }
    else if (localObject == this.popup_opencard) {
      this.ulh.openPlayerCard(this.popup_user.getNick());
    } else if (localObject == this.popup_remove) {
      openAdminFrame(1, this.popup_user.getNick());
    } else if (localObject == this.popup_message) {
      openAdminFrame(2, this.popup_user.getNick());
    } else if (localObject == this.popup_mute) {
      openAdminFrame(3, this.popup_user.getNick());
    } else if (localObject == this.popup_clear) {
      openAdminFrame(4, null);
    } else if (localObject != this.popup_copy) {
      if (localObject == this.popup_info) {
        this.ulh.adminCommand("info", this.popup_user.getNick());
      } else if (localObject == this.popup_broadcast) {
        openAdminFrame(5, null);
      }
    }
  }
  
  public static String getNickFromUserInfo(String paramString)
  {
    if (!isNewUserInfoNick(paramString)) {
      return getNickFromUserInfoOld(paramString);
    }
    int i = paramString.indexOf(':');
    int j = paramString.indexOf('^');
    return Tools.changeFromSaveable(paramString.substring(i + 1, j));
  }
  
  public void disableSheriffMark()
  {
    this.sheriffnote = false;
  }
  
  public void disableDimmerNicks()
  {
    this.dimmernicks = false;
  }
  
  public void enablePopUp()
  {
    enablePopUp(false, false);
  }
  
  public void enablePopUp(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.popup_sheriff = (paramBoolean1 ? 2 : 0);
    this.popup_admin = (paramBoolean2 ? 1 : 0);
    this.popup_enabled = true;
  }
  
  public void enablePopUpWithOnlyOldCommands(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.popup_sheriff = (paramBoolean1 ? 1 : 0);
    this.popup_admin = (paramBoolean2 ? 1 : 0);
    this.popup_enabled = true;
  }
  
  public void setCheckBoxesVisible(boolean paramBoolean)
  {
    if (this.gui_private != null) {
      this.gui_private.setVisible(paramBoolean);
    }
    if (this.gui_ignore != null) {
      this.gui_ignore.setVisible(paramBoolean);
    }
  }
  
  public void setBackground(Color paramColor)
  {
    super.setBackground(paramColor);
    if (this.gui_private != null) {
      this.gui_private.setBackground(paramColor);
    }
    if (this.gui_ignore != null) {
      this.gui_ignore.setBackground(paramColor);
    }
    repaint();
  }
  
  public void setForeground(Color paramColor)
  {
    super.setForeground(paramColor);
    if (this.gui_private != null) {
      this.gui_private.setForeground(paramColor);
    }
    if (this.gui_ignore != null) {
      this.gui_ignore.setForeground(paramColor);
    }
  }
  
  public void setBackgroundImage(Image paramImage, int paramInt1, int paramInt2)
  {
    setBackgroundImage(paramImage, null, paramInt1, paramInt2);
  }
  
  /**
   * @deprecated
   */
  public void setBackgroundImage(Image paramImage1, Image paramImage2, int paramInt1, int paramInt2)
  {
    this.bgimage = paramImage1;
    this.colorlistbgimage = paramImage2;
    this.bgix = paramInt1;
    this.bgiy = paramInt2;
    Point localPoint1 = getLocation();
    this.lxongbset = localPoint1.x;
    this.lyonbgset = localPoint1.y;
    Point localPoint2;
    if (paramImage2 != null)
    {
      localPoint2 = this.gui_nicklist.getLocation();
      this.gui_nicklist.setBackgroundImage(paramImage2, paramInt1 + localPoint2.x, paramInt2 + localPoint2.y);
    }
    if (this.gui_private != null)
    {
      localPoint2 = this.gui_private.getLocation();
      this.gui_private.setBackgroundImage(paramImage1, paramInt1 + localPoint2.x, paramInt2 + localPoint2.y);
    }
    if (this.gui_ignore != null)
    {
      localPoint2 = this.gui_ignore.getLocation();
      this.gui_ignore.setBackgroundImage(paramImage1, paramInt1 + localPoint2.x, paramInt2 + localPoint2.y);
    }
    repaint();
  }
  
  public void setListBackgroundImage(Image paramImage, int paramInt1, int paramInt2)
  {
    Point localPoint = this.gui_nicklist.getLocation();
    this.gui_nicklist.setBackgroundImage(paramImage, paramInt1 + localPoint.x, paramInt2 + localPoint.y);
  }
  
  public UserListItem addUser(String paramString, boolean paramBoolean)
  {
    return addUser(paramString, paramBoolean, -1);
  }
  
  public UserListItem addUser(String paramString, boolean paramBoolean, int paramInt)
  {
    if (!isNewUserInfoNick(paramString)) {
      return addUserOld(paramString, paramBoolean, paramInt);
    }
    int i = paramString.indexOf(':');
    paramString = paramString.substring(i + 1);
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "^");
    String str1 = Tools.changeFromSaveable(localStringTokenizer.nextToken());
    String str2 = localStringTokenizer.nextToken();
    int j = Integer.parseInt(localStringTokenizer.nextToken());
    boolean bool1 = str2.indexOf('r') >= 0;
    boolean bool2 = str2.indexOf('v') >= 0;
    boolean bool3 = str2.indexOf('s') >= 0;
    boolean bool4 = str2.indexOf('n') >= 0;
    UserListItem localUserListItem = new UserListItem(str1, paramBoolean, bool1, bool2, bool3, j);
    localUserListItem.setNotAcceptingChallenges(bool4);
    if (paramInt >= 0) {
      localUserListItem.setOverrideColor(paramInt);
    }
    addUser(localUserListItem);
    return localUserListItem;
  }
  
  public void addUser(UserListItem paramUserListItem)
  {
    String str1 = paramUserListItem.getNick();
    if (this.memory_privately.contains(str1)) {
      paramUserListItem.setPrivately(true);
    }
    if (this.memory_ignore.contains(str1)) {
      paramUserListItem.setIgnore(true);
    }
    String str2 = paramUserListItem.getNick();
    if ((paramUserListItem.isSheriff()) && (this.sheriffnote)) {
      str2 = str2 + " " + this.tm.getShared("UserList_Sheriff");
    }
    ColorListItem localColorListItem = new ColorListItem(getRankingIcon(paramUserListItem), getUserColor(paramUserListItem), paramUserListItem.isRegistered(), str2, paramUserListItem, false);
    localColorListItem.setValue(paramUserListItem.getRanking());
    this.gui_nicklist.addItem(localColorListItem);
    paramUserListItem.setColorListItemReference(localColorListItem);
  }
  
  public UserListItem getSelectedUser()
  {
    ColorListItem localColorListItem = this.gui_nicklist.getSelectedItem();
    if (localColorListItem == null) {
      return null;
    }
    return (UserListItem)localColorListItem.getData();
  }
  
  public UserListItem getUser(String paramString)
  {
    ColorListItem[] arrayOfColorListItem = this.gui_nicklist.getAllItems();
    if (arrayOfColorListItem != null)
    {
      int i = arrayOfColorListItem.length;
      if (i > 0) {
        for (int j = 0; j < i; j++)
        {
          UserListItem localUserListItem = (UserListItem)arrayOfColorListItem[j].getData();
          if (localUserListItem.getNick().equals(paramString)) {
            return localUserListItem;
          }
        }
      }
    }
    return null;
  }
  
  public UserListItem getLocalUser()
  {
    ColorListItem[] arrayOfColorListItem = this.gui_nicklist.getAllItems();
    if (arrayOfColorListItem != null)
    {
      int i = arrayOfColorListItem.length;
      if (i > 0) {
        for (int j = 0; j < i; j++)
        {
          UserListItem localUserListItem = (UserListItem)arrayOfColorListItem[j].getData();
          if (localUserListItem.isLocal()) {
            return localUserListItem;
          }
        }
      }
    }
    return null;
  }
  
  public void removeUser(String paramString)
  {
    ColorListItem[] arrayOfColorListItem = this.gui_nicklist.getAllItems();
    if (arrayOfColorListItem != null)
    {
      int i = arrayOfColorListItem.length;
      if (i > 0) {
        for (int j = 0; j < i; j++)
        {
          UserListItem localUserListItem = (UserListItem)arrayOfColorListItem[j].getData();
          if (localUserListItem.getNick().equals(paramString))
          {
            this.gui_nicklist.removeItem(arrayOfColorListItem[j]);
            if (arrayOfColorListItem[j].isSelected()) {
              setCheckboxesToFalse();
            }
            addToMemory(localUserListItem);
            return;
          }
        }
      }
    }
  }
  
  public UserListItem removeUserNew(String paramString)
  {
    ColorListItem[] arrayOfColorListItem = this.gui_nicklist.getAllItems();
    if (arrayOfColorListItem != null)
    {
      int i = arrayOfColorListItem.length;
      if (i > 0) {
        for (int j = 0; j < i; j++)
        {
          UserListItem localUserListItem = (UserListItem)arrayOfColorListItem[j].getData();
          if (localUserListItem.getNick().equals(paramString))
          {
            this.gui_nicklist.removeItem(arrayOfColorListItem[j]);
            if (arrayOfColorListItem[j].isSelected()) {
              setCheckboxesToFalse();
            }
            addToMemory(localUserListItem);
            return localUserListItem;
          }
        }
      }
    }
    return null;
  }
  
  public void removeAllUsers()
  {
    ColorListItem[] arrayOfColorListItem = this.gui_nicklist.getAllItems();
    if (arrayOfColorListItem != null)
    {
      int i = arrayOfColorListItem.length;
      for (int j = 0; j < i; j++) {
        addToMemory((UserListItem)arrayOfColorListItem[j].getData());
      }
    }
    this.gui_nicklist.removeAllItems();
    setCheckboxesToFalse();
  }
  
  public void setNotAcceptingChallenges(UserListItem paramUserListItem, boolean paramBoolean)
  {
    paramUserListItem.setNotAcceptingChallenges(paramBoolean);
    ColorListItem localColorListItem = paramUserListItem.getColorListItemReference();
    localColorListItem.setColor(getUserColor(paramUserListItem));
    this.gui_nicklist.repaint();
  }
  
  public void setSorting(int paramInt)
  {
    this.gui_nicklist.setSorting(paramInt);
    if (this.icons) {
      if ((paramInt == 3) || (paramInt == 4))
      {
        this.gui_sortrank.setBackground(SORT_BY_COLOR_SELECTED);
        this.gui_sortnick.setBackground(SORT_BY_COLOR);
      }
      else
      {
        this.gui_sortrank.setBackground(SORT_BY_COLOR);
        this.gui_sortnick.setBackground(SORT_BY_COLOR_SELECTED);
      }
    }
  }
  
  public int getSorting()
  {
    return this.gui_nicklist.getSorting();
  }
  
  public int getUserCount()
  {
    return this.gui_nicklist.getItemCount();
  }
  
  public void setChatOutputReference(ColorTextArea paramColorTextArea)
  {
    this.cta = paramColorTextArea;
  }
  
  private static boolean isNewUserInfoNick(String paramString)
  {
    return paramString.startsWith("3:");
  }
  
  private void openPopUp(UserListItem paramUserListItem, int paramInt1, int paramInt2)
  {
    this.popup_user = paramUserListItem;
    if (this.popup_menu != null) {
      remove(this.popup_menu);
    }
    this.popup_menu = new JPopupMenu();
    this.popup_opencard = addMenuItem(this.popup_menu, this.tm.getShared("UserList_OpenPlayerCard"));
    this.popup_opencard.setEnabled(paramUserListItem.isRegistered());
    if ((this.gui_private != null) || (this.gui_ignore != null)) {
      this.popup_menu.addSeparator();
    }
    if (this.gui_private != null)
    {
      this.popup_private = addCheckboxMenuItem(this.popup_menu, this.gui_private.getLabel(), paramUserListItem.isPrivately());
      this.popup_private.setEnabled(!paramUserListItem.isLocal());
    }
    if (this.gui_ignore != null)
    {
      this.popup_ignore = addCheckboxMenuItem(this.popup_menu, this.gui_ignore.getLabel(), paramUserListItem.isIgnore());
      this.popup_ignore.setEnabled(!paramUserListItem.isLocal());
    }
    JMenu localJMenu;
    if (this.popup_sheriff > 0)
    {
      this.popup_menu.addSeparator();
      localJMenu = new JMenu("Sheriff");
      this.popup_remove = addMenuItem(localJMenu, "Remove user...");
      this.popup_message = addMenuItem(localJMenu, "Send message...");
      if (this.popup_sheriff > 1)
      {
        this.popup_mute = addMenuItem(localJMenu, "Mute user...");
        if (this.cta != null) {
          this.popup_copy = addMenuItem(localJMenu, "Copy chat");
        }
      }
      this.popup_menu.add(localJMenu);
    }
    if (this.popup_admin > 0)
    {
      localJMenu = new JMenu("Admin");
      this.popup_info = addMenuItem(localJMenu, "Get user info");
      this.popup_broadcast = addMenuItem(localJMenu, "Broadcast message...");
      this.popup_menu.add(localJMenu);
    }
    add(this.popup_menu);
    this.popup_menu.show(this.gui_nicklist, paramInt1, paramInt2);
  }
  
  private JMenuItem addMenuItem(JMenu paramJMenu, String paramString)
  {
    JMenuItem localJMenuItem = new JMenuItem(paramString);
    localJMenuItem.addActionListener(this);
    paramJMenu.add(localJMenuItem);
    return localJMenuItem;
  }
  
  private JMenuItem addMenuItem(JPopupMenu paramJPopupMenu, String paramString)
  {
    JMenuItem localJMenuItem = new JMenuItem(paramString);
    localJMenuItem.addActionListener(this);
    paramJPopupMenu.add(localJMenuItem);
    return localJMenuItem;
  }
  
  private JMenuItem addCheckboxMenuItem(JPopupMenu paramJPopupMenu, String paramString, boolean paramBoolean)
  {
    JCheckBoxMenuItem localJCheckBoxMenuItem = new JCheckBoxMenuItem(paramString, paramBoolean);
    localJCheckBoxMenuItem.addItemListener(this);
    paramJPopupMenu.add(localJCheckBoxMenuItem);
    return localJCheckBoxMenuItem;
  }
  
  private void openAdminFrame(int paramInt, String paramString)
  {
    if (this.popup_frame != null) {
      this.popup_frame.windowClosing(null);
    }
    this.popup_frame = new AdminFrame(this.tm, this.ulh, paramInt, paramString);
    this.popup_frame.start(this.im.getApplet(), this.popup_admin > 0);
  }
  
  private Color getUserColor(UserListItem paramUserListItem)
  {
    int i = paramUserListItem.getColor(this.sheriffnote);
    Color localColor = ColorListItem.getColorById(i);
    if ((this.dimmernicks) && (paramUserListItem.isNotAcceptingChallenges())) {
      localColor = new Color((localColor.getRed() + 896) / 5, (localColor.getGreen() + 896) / 5, (localColor.getBlue() + 896) / 5);
    }
    return localColor;
  }
  
  private void setLayout(boolean paramBoolean1, boolean paramBoolean2)
  {
    setLayout(null);
    if (this.icons)
    {
      this.gui_sortrank = new ColorButton(this.tm.getShared("UserList_SortByRanking"));
      this.gui_sortrank.setBounds(0, 0, 17, 11);
      this.gui_sortrank.setFont(SORT_BY_FONT);
      this.gui_sortrank.setBackground(SORT_BY_COLOR);
      this.gui_sortrank.addActionListener(this);
      add(this.gui_sortrank);
      this.gui_sortnick = new ColorButton(this.tm.getShared("UserList_SortByNick"));
      this.gui_sortnick.setBounds(17, 0, this.sx - 17, 11);
      this.gui_sortnick.setFont(SORT_BY_FONT);
      this.gui_sortnick.setBackground(SORT_BY_COLOR_SELECTED);
      this.gui_sortnick.addActionListener(this);
      add(this.gui_sortnick);
    }
    int i = 0;
    if (paramBoolean1) {
      i++;
    }
    if (paramBoolean2) {
      i++;
    }
    int j = this.sx;
    int k = this.sy - (paramBoolean2 ? 18 : 0) - (paramBoolean1 ? 18 : 0) - ((paramBoolean2) || (paramBoolean1) ? 2 : 0) - (this.icons ? 11 : 0);
    if (this.icons) {
      this.gui_nicklist = new ColorList(j, k, 11, 11);
    } else {
      this.gui_nicklist = new ColorList(j, k);
    }
    this.gui_nicklist.setSelectable(1);
    this.gui_nicklist.setLocation(0, this.icons ? 11 : 0);
    this.gui_nicklist.addItemListener(this);
    add(this.gui_nicklist);
    setSorting(1);
    if (paramBoolean1)
    {
      this.gui_private = new ColorCheckbox(this.tm.getShared("UserList_Privately"));
      this.gui_private.setBounds(0, this.sy - 18 - (paramBoolean2 ? 18 : 0), this.sx, 18);
      this.gui_private.addItemListener(this);
      add(this.gui_private);
    }
    if (paramBoolean2)
    {
      this.gui_ignore = new ColorCheckbox(this.tm.getShared("UserList_Ignore"));
      this.gui_ignore.setBounds(0, this.sy - 18, this.sx, 18);
      this.gui_ignore.addItemListener(this);
      add(this.gui_ignore);
    }
  }
  
  private void setCheckboxesToFalse()
  {
    if (this.gui_private != null) {
      this.gui_private.setState(false);
    }
    if (this.gui_ignore != null) {
      this.gui_ignore.setState(false);
    }
  }
  
  private Image getRankingIcon(UserListItem paramUserListItem)
  {
    if (this.image_icons == null) {
      return null;
    }
    if (!paramUserListItem.isRegistered()) {
      return this.image_icons[0];
    }
    int i = paramUserListItem.getRanking();
    if (i < 0) {
      return null;
    }
    if (i == 0) {
      return this.image_icons[1];
    }
    if (i < 50) {
      return this.image_icons[2];
    }
    for (int j = 100; j <= 1000; j += 100) {
      if (i < j) {
        return this.image_icons[(2 + j / 100)];
      }
    }
    return this.image_icons[13];
  }
  
  private void addToMemory(UserListItem paramUserListItem)
  {
    String str = paramUserListItem.getNick();
    if (paramUserListItem.isPrivately()) {
      this.memory_privately.addElement(str);
    } else {
      this.memory_privately.removeElement(str);
    }
    if (paramUserListItem.isIgnore()) {
      this.memory_ignore.addElement(str);
    } else {
      this.memory_ignore.removeElement(str);
    }
  }
  
  private static String getNickFromUserInfoOld(String paramString)
  {
    if (paramString.startsWith("2:"))
    {
      i = paramString.lastIndexOf('^');
      j = paramString.lastIndexOf('^', i - 1);
      paramString = paramString.substring(2, j);
    }
    int i = paramString.indexOf(',');
    int j = paramString.lastIndexOf(',');
    if (i == j) {
      j = paramString.length();
    }
    return paramString.substring(i + 1, j);
  }
  
  private UserListItem addUserOld(String paramString, boolean paramBoolean, int paramInt)
  {
    if (paramString.startsWith("2:"))
    {
      i = paramString.lastIndexOf('^');
      j = paramString.lastIndexOf('^', i - 1);
      paramString = paramString.substring(2, j);
    }
    int i = paramString.indexOf(',');
    int j = paramString.lastIndexOf(',');
    String str1;
    int k;
    if (i == j)
    {
      str1 = paramString.substring(i + 1);
      k = -2;
    }
    else
    {
      str1 = paramString.substring(i + 1, j);
      k = Integer.parseInt(paramString.substring(j + 1));
    }
    String str2 = paramString.substring(0, i);
    boolean bool1 = str2.indexOf('r') >= 0;
    boolean bool2 = str2.indexOf('v') >= 0;
    boolean bool3 = str2.indexOf('s') >= 0;
    boolean bool4 = str2.indexOf('n') >= 0;
    UserListItem localUserListItem = new UserListItem(str1, paramBoolean, bool1, bool2, bool3, k);
    localUserListItem.setNotAcceptingChallenges(bool4);
    if (paramInt >= 0) {
      localUserListItem.setOverrideColor(paramInt);
    }
    addUser(localUserListItem);
    return localUserListItem;
  }
}
