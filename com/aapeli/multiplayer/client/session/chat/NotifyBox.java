package com.aapeli.multiplayer.client.session.chat;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.border.SoftBevelBorder;

public class NotifyBox
  extends AbstractBox
{
  private JLabel descriptionLabel = new JLabel();
  
  public NotifyBox()
  {
    this.descriptionLabel.setBackground(new Color(238, 238, 238));
    this.descriptionLabel.setFont(new Font("Dialog", 1, 14));
    this.descriptionLabel.setBorder(new SoftBevelBorder(0, Color.lightGray, Color.lightGray, Color.lightGray, Color.lightGray));
    this.descriptionLabel.setOpaque(true);
    add(this.descriptionLabel);
    this.descriptionLabel.setBounds(40, 50, 360, 20);
    initComponents();
  }
  
  public void setTopic(String paramString)
  {
    this.jLabel2.setText(paramString);
  }
  
  public void setDescription(String paramString)
  {
    this.descriptionLabel.setText(paramString);
  }
}
