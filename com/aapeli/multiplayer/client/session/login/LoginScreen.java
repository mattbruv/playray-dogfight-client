package com.aapeli.multiplayer.client.session.login;

import com.aapeli.multiplayer.client.common.GUIColors;
import com.aapeli.multiplayer.client.resources.Localization;
import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

public class LoginScreen
  extends JPanel
{
  private JButton jButton1;
  private JButton jButton5;
  private JLabel statusLabel;
  private JLabel middleBgLabel;
  private JLabel bigMiddleBgLabel;
  private JLabel jLabel5;
  private JTextField jTextField1;
  private ActionListener actionListener;
  private Applet applet;
  
  public LoginScreen(Applet paramApplet, ActionListener paramActionListener)
  {
    this.applet = paramApplet;
    this.actionListener = paramActionListener;
    initComponents();
    setSessionLogin();
  }
  
  public String getName()
  {
    this.jTextField1.setEditable(false);
    this.jButton1.setEnabled(false);
    return this.jTextField1.getText();
  }
  
  public void setGuestLogin()
  {
    this.statusLabel.setText(Localization.getInstance().localize("Nickname"));
    this.jTextField1.setVisible(true);
    this.jTextField1.setText("");
    this.jTextField1.setEditable(true);
    this.jButton1.setEnabled(true);
    this.jButton1.setVisible(true);
    this.middleBgLabel.setVisible(true);
    this.bigMiddleBgLabel.setVisible(true);
  }
  
  public void setSessionLogin()
  {
    this.jTextField1.setVisible(false);
    this.jTextField1.setEditable(false);
    this.jButton1.setEnabled(false);
    this.jButton1.setVisible(false);
  }
  
  private void initComponents()
  {
    this.jTextField1 = new JTextField();
    this.jButton1 = new JButton();
    this.jButton5 = new JButton();
    this.middleBgLabel = new JLabel();
    this.statusLabel = new JLabel();
    this.bigMiddleBgLabel = new JLabel();
    this.jLabel5 = new JLabel();
    setLayout(null);
    setFocusable(false);
    setBackground(GUIColors.getInstance().get(GUIColors.TOP_BG));
    this.jTextField1.setFont(new Font("Dialog", 0, 14));
    this.jTextField1.setText("");
    this.jTextField1.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.BORDER), 2, true));
    this.jTextField1.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        LoginScreen.this.okButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    add(this.jTextField1);
    this.jTextField1.setBounds(195, 335, 280, 21);
    this.jButton1.setBackground(new Color(153, 255, 153));
    this.jButton1.setFont(new Font("Dialog", 1, 14));
    this.jButton1.setText(Localization.getInstance().localize("OK"));
    this.jButton1.setBorder(new LineBorder(new Color(0, 204, 51), 2, true));
    this.jButton1.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        LoginScreen.this.okButtonActionPerformed(paramAnonymousActionEvent);
      }
    });
    add(this.jButton1);
    this.jButton1.setBounds(480, 335, 80, 21);
    this.jButton5.setBackground(new Color(255, 153, 153));
    this.jButton5.setFont(new Font("Dialog", 1, 14));
    this.jButton5.setText(Localization.getInstance().localize("Exit"));
    this.jButton5.setBorder(new LineBorder(new Color(153, 0, 51), 2, true));
    this.jButton5.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        LoginScreen.this.exitActionPerformed(paramAnonymousActionEvent);
      }
    });
    add(this.jButton5);
    this.jButton5.setBounds(660, 10, 70, 27);
    this.middleBgLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.middleBgLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.middleBgLabel.setOpaque(true);
    this.middleBgLabel.setVisible(false);
    add(this.middleBgLabel);
    this.middleBgLabel.setBounds(190, 330, 375, 30);
    this.statusLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.statusLabel.setFont(new Font("Dialog", 1, 14));
    this.statusLabel.setText(Localization.getInstance().localize("Logging in..."));
    this.statusLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.statusLabel.setOpaque(true);
    add(this.statusLabel);
    this.statusLabel.setBounds(190, 310, 170, 23);
    this.bigMiddleBgLabel.setOpaque(true);
    this.bigMiddleBgLabel.setVisible(false);
    this.bigMiddleBgLabel.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.bigMiddleBgLabel.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    add(this.bigMiddleBgLabel);
    this.bigMiddleBgLabel.setBounds(70, 240, 580, 200);
    this.jLabel5.setFont(new Font("Dialog", 1, 14));
    this.jLabel5.setHorizontalAlignment(0);
    this.jLabel5.setText(Localization.getInstance().localize("Login"));
    this.jLabel5.setHorizontalTextPosition(10);
    this.jLabel5.setOpaque(true);
    this.jLabel5.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.jLabel5.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    add(this.jLabel5);
    this.jLabel5.setBounds(10, 10, 70, 30);
  }
  
  private void okButtonActionPerformed(ActionEvent paramActionEvent)
  {
    this.actionListener.actionPerformed(new ActionEvent(this, 0, ""));
  }
  
  public void requestFocus()
  {
    this.jTextField1.requestFocus();
  }
  
  private void exitActionPerformed(ActionEvent paramActionEvent)
  {
    try
    {
      URL localURL = new URL(this.applet.getParameter("quitpage"));
      this.applet.getAppletContext().showDocument(localURL);
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
    }
  }
}
