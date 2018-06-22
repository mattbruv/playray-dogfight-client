package com.aapeli.multiplayer.client.session.chat.view;

import com.aapeli.multiplayer.client.common.GUIColors;
import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.util.TableSorter;
import com.aapeli.shared.ChatTextArea;
import com.aapeli.shared.UserList;
import java.applet.Applet;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class JoinView
  extends View
{
  public static final int USERLIST_WIDTH = 170;
  public static final int USERLIST_HEIGHT = 170;
  private DefaultTableModel gameListTableModel;
  private TableSorter gameListSorter;
  private JButton sendButton;
  private JButton watchGameButton;
  private JButton joinGameButton;
  private JLabel chatLabel;
  private JLabel userListLabel;
  private JLabel jLabel3;
  private JLabel joinAreaLabel;
  private JLabel buttonBGLabel;
  private JTable gameList;
  private JScrollPane gameListPane;
  private String[] fields;
  private int[] fieldWidths;
  private Object[][] data;
  private String[] roomIDs;
  private boolean hideJoin = false;
  
  public JoinView(UserList paramUserList, Applet paramApplet, Map paramMap)
  {
    super(paramUserList, paramApplet, 52, paramMap);
    Boolean localBoolean = (Boolean)paramMap.get("join_view_hide_join");
    if (localBoolean != null) {
      this.hideJoin = localBoolean.booleanValue();
    }
    super.initFGComponents();
    initComponents();
    super.initBGComponents();
  }
  
  protected void initComponents()
  {
    this.sendButton = new JButton();
    this.chatLabel = new JLabel();
    this.userListLabel = new JLabel();
    this.jLabel3 = new JLabel();
    this.joinAreaLabel = new JLabel();
    this.joinGameButton = new JButton();
    this.watchGameButton = new JButton();
    this.buttonBGLabel = new JLabel();
    this.chatField.setBounds(195, 515, 445, 21);
    this.sendButton.setBackground(GUIColors.getInstance().get(GUIColors.BUTTON));
    this.sendButton.setFont(new Font("Dialog", 1, 14));
    this.sendButton.setText(Localization.getInstance().localize("Send"));
    this.sendButton.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BUTTON_BORDER), 2, true));
    this.sendButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        JoinView.this.sendActionPerformed(paramAnonymousActionEvent);
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
    this.jLabel3.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.jLabel3.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.jLabel3.setOpaque(true);
    add(this.jLabel3);
    this.jLabel3.setBounds(190, 510, 540, 30);
    this.joinAreaLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.joinAreaLabel.setFont(new Font("Dialog", 1, 14));
    this.joinAreaLabel.setText(Localization.getInstance().localize("Games"));
    this.joinAreaLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.joinAreaLabel.setOpaque(true);
    add(this.joinAreaLabel);
    this.joinAreaLabel.setBounds(10, 60, 720, 20);
    initTable();
    add(this.userList);
    this.joinGameButton.setBackground(GUIColors.getInstance().get(GUIColors.BUTTON));
    this.joinGameButton.setFont(new Font("Dialog", 1, 14));
    this.joinGameButton.setText(Localization.getInstance().localize("Join"));
    this.joinGameButton.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BUTTON_BORDER), 2, true));
    this.joinGameButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        JoinView.this.joinGameButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    if (!this.hideJoin) {
      add(this.joinGameButton);
    }
    this.joinGameButton.setBounds(520, 320, 100, 21);
    this.watchGameButton.setBackground(GUIColors.getInstance().get(GUIColors.BUTTON));
    this.watchGameButton.setFont(new Font("Dialog", 1, 14));
    this.watchGameButton.setText(Localization.getInstance().localize("Watch"));
    this.watchGameButton.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BUTTON_BORDER), 2, true));
    this.watchGameButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        JoinView.this.watchGameButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    add(this.watchGameButton);
    this.watchGameButton.setBounds(625, 320, 100, 21);
    this.buttonBGLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.buttonBGLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.buttonBGLabel.setOpaque(true);
    add(this.buttonBGLabel);
    if (this.hideJoin) {
      this.buttonBGLabel.setBounds(620, 315, 110, 30);
    } else {
      this.buttonBGLabel.setBounds(515, 315, 215, 30);
    }
  }
  
  protected void joinGameButtonActionPerformed(ActionEvent paramActionEvent)
  {
    if (this.inputEnabled)
    {
      int i = this.gameList.getSelectedRow();
      if (i != -1)
      {
        i = this.gameListSorter.modelIndex(i);
        this.actionListener.actionPerformed(new ActionEvent(this, 11, this.roomIDs[i]));
      }
    }
  }
  
  protected void watchGameButtonActionPerformed(ActionEvent paramActionEvent)
  {
    if (this.inputEnabled)
    {
      int i = this.gameList.getSelectedRow();
      if (i != -1)
      {
        i = this.gameListSorter.modelIndex(i);
        this.actionListener.actionPerformed(new ActionEvent(this, 14, this.roomIDs[i]));
      }
    }
  }
  
  protected void videoButtonActionPerformed(ActionEvent paramActionEvent)
  {
    if (this.inputEnabled) {
      this.actionListener.actionPerformed(new ActionEvent(this, 16, null));
    }
  }
  
  public void setGameSessionList(String[] paramArrayOfString1, int[] paramArrayOfInt, boolean[] paramArrayOfBoolean, String[][] paramArrayOfString, String[] paramArrayOfString2)
  {
    for (int i = 0; i < paramArrayOfString1.length; i++) {
      paramArrayOfString1[i] = Localization.getInstance().localize(paramArrayOfString1[i]);
    }
    this.fields = paramArrayOfString1;
    this.fieldWidths = paramArrayOfInt;
    this.roomIDs = paramArrayOfString2;
    for (i = 0; i < paramArrayOfString.length; i++) {
      for (int j = 0; j < paramArrayOfBoolean.length; j++) {
        if (paramArrayOfBoolean[j] != 0) {
          paramArrayOfString[i][j] = Localization.getInstance().localize(paramArrayOfString[i][j]);
        }
      }
    }
    this.data = paramArrayOfString;
  }
  
  public void refreshGameSessionList()
  {
    int i = this.gameListSorter.getColumnCount() == this.fields.length ? 1 : 0;
    int[] arrayOfInt = null;
    int j = 0;
    int k;
    if (i != 0)
    {
      arrayOfInt = new int[this.gameListSorter.getColumnCount()];
      for (k = 0; k < arrayOfInt.length; k++) {
        arrayOfInt[k] = this.gameListSorter.getSortingStatus(k);
      }
      j = this.gameList.getSelectedRow();
    }
    if (i == 0)
    {
      this.gameListTableModel.setDataVector(this.data, this.fields);
      this.gameListSorter.setSortingStatus(1, -1);
      for (k = 0; k < this.gameList.getColumnCount(); k++)
      {
        TableColumn localTableColumn = this.gameList.getColumnModel().getColumn(k);
        if (this.fieldWidths[k] > 0) {
          localTableColumn.setPreferredWidth(this.fieldWidths[k]);
        }
      }
    }
    else
    {
      while (this.gameListTableModel.getRowCount() > 0) {
        this.gameListTableModel.removeRow(0);
      }
      for (k = 0; k < this.data.length; k++) {
        this.gameListTableModel.addRow(this.data[k]);
      }
      for (k = 0; k < arrayOfInt.length; k++) {
        this.gameListSorter.setSortingStatus(k, arrayOfInt[k]);
      }
      if ((j != -1) && (j < this.gameList.getRowCount())) {
        this.gameList.setRowSelectionInterval(j, j);
      }
    }
  }
  
  public void setInputEnabled(boolean paramBoolean)
  {
    super.setInputEnabled(paramBoolean);
    this.joinGameButton.setEnabled(paramBoolean);
    this.watchGameButton.setEnabled(paramBoolean);
    this.sendButton.setEnabled(paramBoolean);
    this.gameListPane.setEnabled(paramBoolean);
    this.gameList.setEnabled(paramBoolean);
    this.gameListPane.getVerticalScrollBar().setEnabled(paramBoolean);
  }
  
  public void reinitChat()
  {
    add(this.userList);
    this.userList.setBounds(10, 370, 170, 170);
    if (this.chatArea != null)
    {
      add(this.chatArea);
      this.chatArea.setBounds(190, 370, 540, 140);
      this.chatArea.setVisible(this.chatVisible);
    }
    this.actionListener.actionPerformed(new ActionEvent(this, 13, ""));
  }
  
  private void initTable()
  {
    this.gameList = new JTable();
    this.gameList.setFont(new Font("Dialog", 1, 14));
    this.gameList.setBackground(GUIColors.getInstance().get(GUIColors.TABLE_BG));
    this.gameList.setForeground(GUIColors.getInstance().get(GUIColors.TABLE_FG));
    this.gameList.setSelectionMode(0);
    this.gameListTableModel = new DefaultTableModel();
    this.gameListSorter = new TableSorter(this.gameListTableModel);
    this.gameList.setModel(this.gameListSorter);
    this.gameList.getTableHeader().setBackground(GUIColors.getInstance().get(GUIColors.TABLE_HEADER_BG));
    this.gameList.getTableHeader().setForeground(GUIColors.getInstance().get(GUIColors.TABLE_HEADER_FG));
    this.gameListSorter.setTableHeader(this.gameList.getTableHeader());
    this.gameListSorter.setColumnComparator(Object.class, new CustomCellComparator(null));
    this.gameList.setAutoResizeMode(4);
    this.gameListPane = new JScrollPane();
    this.gameListPane.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.SHARP_BORDER)));
    this.gameListPane.setVerticalScrollBarPolicy(22);
    this.gameListPane.setOpaque(false);
    this.gameListPane.getViewport().add(this.gameList);
    this.gameListPane.getViewport().setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.gameListPane.setBounds(10, 80, 720, 230);
    add(this.gameListPane);
  }
  
  private class CustomCellComparator
    implements Comparator
  {
    private CustomCellComparator() {}
    
    public int compare(Object paramObject1, Object paramObject2)
    {
      if (((paramObject1 instanceof String)) && ((paramObject2 instanceof String)))
      {
        String str1 = (String)paramObject1;
        String str2 = (String)paramObject2;
        try
        {
          Integer localInteger1 = new Integer(str1);
          Integer localInteger2 = new Integer(str2);
          return localInteger1.compareTo(localInteger2);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          return str1.compareTo(str2);
        }
      }
      return 0;
    }
  }
}
