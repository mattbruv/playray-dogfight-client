package com.aapeli.multiplayer.client.session.error;

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
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

public class ErrorScreen
  extends JPanel
{
  private JButton jButton1;
  private JButton jButton5;
  private JLabel jLabel2;
  private JTextArea errorArea;
  private JLabel jLabel5;
  private static final int MIN_TEXT_WIDTH = 250;
  private Applet applet;
  
  public ErrorScreen(Applet paramApplet)
  {
    this.applet = paramApplet;
    initComponents();
  }
  
  public void reset()
  {
    this.jButton1.setEnabled(true);
  }
  
  private void initComponents()
  {
    this.jButton1 = new JButton();
    this.jButton5 = new JButton();
    this.errorArea = new JTextArea();
    this.jLabel2 = new JLabel();
    this.jLabel5 = new JLabel();
    setLayout(null);
    setFocusable(false);
    setBackground(GUIColors.getInstance().get(GUIColors.TOP_BG));
    this.jButton5.setBackground(new Color(255, 153, 153));
    this.jButton5.setFont(new Font("Dialog", 1, 14));
    this.jButton5.setText(Localization.getInstance().localize("Exit"));
    this.jButton5.setBorder(new LineBorder(new Color(153, 0, 51), 2, true));
    add(this.jButton5);
    this.jButton5.setBounds(660, 10, 70, 27);
    this.jButton5.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        ErrorScreen.this.exitActionPerformed(paramAnonymousActionEvent);
      }
    });
    this.errorArea.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.errorArea.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.errorArea.setOpaque(true);
    this.errorArea.setFont(new Font("Dialog", 1, 14));
    this.errorArea.setLineWrap(true);
    this.errorArea.setWrapStyleWord(true);
    this.errorArea.setEditable(false);
    add(this.errorArea);
    this.errorArea.setBounds(190, 330, 350, 60);
    this.jLabel2.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.jLabel2.setFont(new Font("Dialog", 1, 14));
    this.jLabel2.setText(Localization.getInstance().localize("Error:"));
    this.jLabel2.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.jLabel2.setOpaque(true);
    add(this.jLabel2);
    this.jLabel2.setBounds(190, 310, 170, 23);
    this.jLabel5.setFont(new Font("Dialog", 1, 14));
    this.jLabel5.setHorizontalAlignment(0);
    this.jLabel5.setText(Localization.getInstance().localize("Error"));
    this.jLabel5.setHorizontalTextPosition(10);
    this.jLabel5.setBackground(GUIColors.getInstance().get(GUIColors.BG));
    this.jLabel5.setBorder(new SoftBevelBorder(0, GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER), GUIColors.getInstance().get(GUIColors.BORDER)));
    this.jLabel5.setOpaque(true);
    add(this.jLabel5);
    this.jLabel5.setBounds(10, 10, 70, 30);
  }
  
  public void setMessage(String paramString)
  {
    this.errorArea.setText(paramString);
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
