package com.aapeli.multiplayer.client.session.chat.create;

import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.common.Utils;
import com.aapeli.multiplayer.common.network.CreditConstants;
import com.aapeli.multiplayer.common.network.CreditConverter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import javax.swing.Box.Filler;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class RoomTimePanel
  extends JPanel
  implements CreditConstants
{
  private LabelledComboBox donateBox;
  private JButton donateButton;
  private LabelledTextField roomAccountField;
  private LabelledTextField roomExpiresField;
  private LabelledComboBox roomTimeBox;
  private JButton roomTimeButton;
  private static final int X_GAP = 10;
  private static final int Y_GAP = 3;
  private ActionListener actionListener;
  
  public RoomTimePanel(ActionListener paramActionListener)
  {
    this.actionListener = paramActionListener;
    Dimension localDimension = new Dimension(10, 3);
    setOpaque(false);
    setLayout(new BoxLayout(this, 1));
    JPanel localJPanel2 = new JPanel();
    localJPanel2.setLayout(new BoxLayout(localJPanel2, 0));
    localJPanel2.setOpaque(false);
    this.donateBox = new LabelledComboBox("Donate money", new String[] { "amount_small", "amount_medium", "amount_large" }, true);
    localJPanel2.add(this.donateBox);
    localJPanel2.add(new Box.Filler(localDimension, localDimension, localDimension));
    this.donateButton = new JButton();
    this.donateButton.setBackground(new Color(153, 255, 153));
    this.donateButton.setFont(new Font("Dialog", 1, 14));
    this.donateButton.setText(Localization.getInstance().localize("Donate"));
    this.donateButton.setBorder(new LineBorder(new Color(0, 204, 51), 2, true));
    this.donateButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        RoomTimePanel.this.donateButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    localJPanel2.add(this.donateButton);
    JPanel localJPanel1 = new JPanel();
    localJPanel1.setOpaque(false);
    localJPanel2.add(localJPanel1);
    JPanel localJPanel3 = new JPanel();
    localJPanel3.setLayout(new BoxLayout(localJPanel3, 0));
    localJPanel3.setOpaque(false);
    this.roomAccountField = new LabelledTextField("Room account", "---", 30, 0);
    this.roomAccountField.setEditable(false);
    DescriptedComponent localDescriptedComponent = new DescriptedComponent(this.roomAccountField, "room account description");
    localJPanel3.add(localDescriptedComponent);
    JPanel localJPanel4 = new JPanel();
    localJPanel4.setLayout(new BoxLayout(localJPanel4, 0));
    localJPanel4.setOpaque(false);
    this.roomExpiresField = new LabelledTextField("Room expires", "-", 120, 0);
    this.roomExpiresField.setEditable(false);
    localDescriptedComponent = new DescriptedComponent(this.roomExpiresField, "room expires description");
    localJPanel4.add(localDescriptedComponent);
    JPanel localJPanel5 = new JPanel();
    localJPanel5.setLayout(new BoxLayout(localJPanel5, 0));
    localJPanel5.setOpaque(false);
    this.roomTimeBox = new LabelledComboBox("Room time to buy", new String[0], true, 200);
    localJPanel5.add(this.roomTimeBox);
    localJPanel5.add(new Box.Filler(localDimension, localDimension, localDimension));
    this.roomTimeButton = new JButton();
    this.roomTimeButton.setBackground(new Color(153, 255, 153));
    this.roomTimeButton.setFont(new Font("Dialog", 1, 14));
    this.roomTimeButton.setText(Localization.getInstance().localize("Get room time"));
    this.roomTimeButton.setBorder(new LineBorder(new Color(0, 204, 51), 2, true));
    this.roomTimeButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        RoomTimePanel.this.roomTimeButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    localJPanel5.add(this.roomTimeButton);
    localJPanel1 = new JPanel();
    localJPanel1.setOpaque(false);
    localJPanel5.add(localJPanel1);
    add(localJPanel2);
    add(new Box.Filler(localDimension, localDimension, localDimension));
    add(localJPanel3);
    add(new Box.Filler(localDimension, localDimension, localDimension));
    add(localJPanel4);
    add(new Box.Filler(localDimension, localDimension, localDimension));
    add(localJPanel5);
  }
  
  private void donateButtonActionPerformed(ActionEvent paramActionEvent)
  {
    throwActionEvent(new ActionEvent(this, 32, this.donateBox.getValue()));
    System.out.println("User donated " + this.donateBox.getValue());
  }
  
  private void roomTimeButtonActionPerformed(ActionEvent paramActionEvent)
  {
    throwActionEvent(new ActionEvent(this, 33, this.roomTimeBox.getValue()));
    System.out.println("Room bought " + this.roomTimeBox.getValue());
  }
  
  private void throwActionEvent(ActionEvent paramActionEvent)
  {
    if (this.actionListener != null) {
      this.actionListener.actionPerformed(paramActionEvent);
    }
  }
  
  public void setRoomParameters(Map paramMap)
  {
    String str1 = (String)paramMap.get("param_room_size");
    String[] arrayOfString = null;
    if ("room_large".equals(str1)) {
      arrayOfString = new String[] { "huge_room_time1", "huge_room_time2", "huge_room_time3" };
    } else if ("room_medium".equals(str1)) {
      arrayOfString = new String[] { "medium_room_time1", "medium_room_time2", "medium_room_time3" };
    } else if ("room_small".equals(str1)) {
      arrayOfString = new String[] { "small_room_time1", "small_room_time2", "small_room_time3" };
    } else if ("room_tiny".equals(str1)) {
      arrayOfString = new String[] { "tiny_room_time1", "tiny_room_time2", "tiny_room_time3" };
    }
    if (arrayOfString != null)
    {
      localObject = new String[3];
      for (int i = 0; i < localObject.length; i++)
      {
        localObject[i] = Localization.getInstance().localize("room_time" + (1 + i) + "_price");
        localObject[i] = localObject[i].replaceAll("\\$1", "" + CreditConverter.getInstance().getRoomTimePrice(arrayOfString[i]));
      }
      this.roomTimeBox.setValues(arrayOfString, (String[])localObject);
      this.roomTimeBox.revalidate();
    }
    this.roomAccountField.setValue((String)paramMap.get("param_credits"));
    Object localObject = (String)paramMap.get("param_expire_date");
    String str2 = (String)paramMap.get("param_server_time");
    long l1 = Long.parseLong(str2);
    try
    {
      Date localDate = Utils.getInstance().getDateFormat().parse((String)localObject);
      long l2 = localDate.getTime();
      localDate.setTime(l2 - l1 + TimeZone.getDefault().getOffset(System.currentTimeMillis()));
      localObject = Utils.getInstance().getDateFormat().format(localDate);
      this.roomExpiresField.setValue((String)localObject);
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
    }
    validate();
  }
}
