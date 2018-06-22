package com.aapeli.multiplayer.client.session.chat;

import com.aapeli.multiplayer.client.resources.Localization;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;

public class LoadingBox
  extends JPanel
{
  private JLabel jLabel2;
  private JLabel jLabel3;
  private LoadingBar loadingBar;
  
  public LoadingBox(LoadingBar paramLoadingBar)
  {
    this.loadingBar = paramLoadingBar;
    initComponents();
  }
  
  private void initComponents()
  {
    this.jLabel3 = new JLabel();
    this.jLabel2 = new JLabel();
    setLayout(null);
    setBackground(new Color(203, 203, 203));
    setBorder(new SoftBevelBorder(0, Color.lightGray, Color.lightGray, Color.lightGray, Color.lightGray));
    this.loadingBar.setValue(50);
    add(this.loadingBar);
    this.loadingBar.setBounds(40, 50, 360, 20);
    this.jLabel3.setBackground(new Color(238, 238, 238));
    this.jLabel3.setBorder(new SoftBevelBorder(0, Color.lightGray, Color.lightGray, Color.lightGray, Color.lightGray));
    this.jLabel3.setOpaque(true);
    add(this.jLabel3);
    this.jLabel3.setBounds(30, 40, 375, 40);
    this.jLabel2.setBackground(new Color(238, 238, 238));
    this.jLabel2.setFont(new Font("Dialog", 1, 14));
    this.jLabel2.setText(Localization.getInstance().localize("Loading game..."));
    this.jLabel2.setBorder(new SoftBevelBorder(0, Color.lightGray, Color.lightGray, Color.lightGray, Color.lightGray));
    this.jLabel2.setOpaque(true);
    add(this.jLabel2);
    this.jLabel2.setBounds(30, 20, 170, 23);
  }
}
