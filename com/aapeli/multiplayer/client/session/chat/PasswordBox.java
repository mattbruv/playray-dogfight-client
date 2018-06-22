package com.aapeli.multiplayer.client.session.chat;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class PasswordBox
  extends AbstractBox
{
  private JPasswordField passwordField = new JPasswordField();
  
  public PasswordBox()
  {
    add(this.passwordField);
    this.passwordField.setBounds(40, 50, 360, 20);
    initComponents();
  }
  
  public void addActionListener(ActionListener paramActionListener)
  {
    this.passwordField.addActionListener(paramActionListener);
    this.okButton.addActionListener(paramActionListener);
  }
  
  public String getPassword()
  {
    Document localDocument = this.passwordField.getDocument();
    try
    {
      return localDocument.getText(0, localDocument.getLength());
    }
    catch (BadLocationException localBadLocationException) {}
    return "";
  }
  
  public void requestFocus()
  {
    this.passwordField.requestFocus();
  }
}
