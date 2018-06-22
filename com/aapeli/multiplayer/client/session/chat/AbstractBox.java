package com.aapeli.multiplayer.client.session.chat;

import com.aapeli.multiplayer.client.resources.Localization;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

public abstract class AbstractBox
  extends JPanel
{
  protected JLabel jLabel2;
  protected JLabel jLabel3;
  protected JButton okButton;
  
  protected void initComponents()
  {
    this.jLabel3 = new JLabel();
    this.jLabel2 = new JLabel();
    this.okButton = new JButton();
    setLayout(null);
    setBackground(new Color(203, 203, 203));
    setBorder(new SoftBevelBorder(0, Color.lightGray, Color.lightGray, Color.lightGray, Color.lightGray));
    this.okButton.setBackground(new Color(153, 255, 153));
    this.okButton.setFont(new Font("Dialog", 1, 14));
    this.okButton.setText(Localization.getInstance().localize("OK"));
    this.okButton.setBorder(new LineBorder(new Color(0, 204, 51), 2, true));
    add(this.okButton);
    this.okButton.setBounds(315, 85, 80, 21);
    this.jLabel3.setBackground(new Color(238, 238, 238));
    this.jLabel3.setBorder(new SoftBevelBorder(0, Color.lightGray, Color.lightGray, Color.lightGray, Color.lightGray));
    this.jLabel3.setOpaque(true);
    add(this.jLabel3);
    this.jLabel3.setBounds(30, 40, 375, 40);
    this.jLabel2.setBackground(new Color(238, 238, 238));
    this.jLabel2.setFont(new Font("Dialog", 1, 14));
    this.jLabel2.setBorder(new SoftBevelBorder(0, Color.lightGray, Color.lightGray, Color.lightGray, Color.lightGray));
    this.jLabel2.setOpaque(true);
    add(this.jLabel2);
    this.jLabel2.setBounds(30, 20, 170, 23);
  }
  
  public void setTopic(String paramString)
  {
    this.jLabel2.setText(paramString);
  }
  
  public void addActionListener(ActionListener paramActionListener)
  {
    this.okButton.addActionListener(paramActionListener);
  }
  
  public void removeActionListener(ActionListener paramActionListener)
  {
    this.okButton.removeActionListener(paramActionListener);
  }
}
