package com.aapeli.multiplayer.client.session.game.gui;

import com.aapeli.client.StringDraw;
import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.common.event.TextListener;
import com.aapeli.multiplayer.common.network.GameProtocol;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class ChatField
  extends JPanel
  implements KeyListener, GameProtocol, ChatColors
{
  private TextListener chatListener;
  private boolean teamMode = false;
  private JEditorPane textField;
  private JLabel say;
  private ChatDecorator decorator;
  private static final ChatDecorator.Style ALL = new ChatDecorator.Style(ALL_BG, ALL_FG);
  private static final ChatDecorator.Style TEAM = new ChatDecorator.Style(TEAM_BG, TEAM_FG);
  
  public ChatField(TextListener paramTextListener)
  {
    this.chatListener = paramTextListener;
    setVisible(false);
    setOpaque(false);
    setFocusable(false);
    setLayout(new BoxLayout(this, 0));
    this.say = new JLabel(Localization.getInstance().localize("Say:") + " ");
    this.say.setUI(new OutlinedLabelUI());
    this.say.setOpaque(false);
    add(this.say);
    this.decorator = new ChatDecorator();
    this.textField = new JEditorPane();
    this.textField.setEditorKit(new ChatEditorKit(this.decorator));
    this.textField.setOpaque(false);
    this.textField.setBorder(null);
    this.textField.setEditable(false);
    this.textField.addKeyListener(this);
    this.textField.setCaretColor(Color.black);
    add(this.textField);
    this.textField.setDocument(new PlainDocument()
    {
      public void insertString(int paramAnonymousInt, String paramAnonymousString, AttributeSet paramAnonymousAttributeSet)
        throws BadLocationException
      {
        String str = getText(0, getLength());
        if (StringDraw.getStringWidth(ChatField.this, ChatField.this.getFont(), str + paramAnonymousString) < ChatField.this.textField.getWidth()) {
          super.insertString(paramAnonymousInt, paramAnonymousString, paramAnonymousAttributeSet);
        }
      }
    });
    doLayout();
  }
  
  public void keyPressed(KeyEvent paramKeyEvent)
  {
    if (this.textField.isEditable())
    {
      if (paramKeyEvent.getKeyCode() == 10)
      {
        if (this.textField.getText().trim().length() > 0) {
          if (this.teamMode) {
            this.chatListener.textMessage(2, "", this.textField.getText());
          } else {
            this.chatListener.textMessage(1, "", this.textField.getText());
          }
        }
        this.textField.setEditable(false);
        setVisible(false);
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            ChatField.this.textField.setText("");
            ChatField.this.getParent().requestFocus();
          }
        });
      }
    }
    else if (paramKeyEvent.getKeyCode() == 89)
    {
      this.decorator.setForcedStyle(ALL);
      this.say.setForeground(ALL_FG);
      this.say.setBackground(ALL_BG);
      this.teamMode = false;
      SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          ChatField.this.textField.setEditable(true);
          ChatField.this.setVisible(true);
          ChatField.this.textField.requestFocus();
        }
      });
    }
    else if (paramKeyEvent.getKeyCode() == 85)
    {
      this.decorator.setForcedStyle(TEAM);
      this.say.setForeground(TEAM_FG);
      this.say.setBackground(TEAM_BG);
      this.teamMode = true;
      SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          ChatField.this.textField.setEditable(true);
          ChatField.this.setVisible(true);
          ChatField.this.textField.requestFocus();
        }
      });
    }
  }
  
  public void keyReleased(KeyEvent paramKeyEvent) {}
  
  public void keyTyped(KeyEvent paramKeyEvent) {}
  
  public void setBounds(Rectangle paramRectangle)
  {
    super.setBounds(paramRectangle);
    doLayout();
    revalidate();
  }
  
  public void setFont(Font paramFont)
  {
    super.setFont(paramFont);
    if (this.say != null) {
      this.say.setFont(paramFont);
    }
    if (this.textField != null) {
      this.textField.setFont(paramFont);
    }
  }
  
  public void setEditable(boolean paramBoolean)
  {
    this.textField.setEditable(paramBoolean);
  }
  
  public void setText(String paramString)
  {
    this.textField.setText(paramString);
  }
  
  public boolean isEditable()
  {
    return this.textField.isEditable();
  }
  
  public void requestFocus()
  {
    if (this.textField != null) {
      this.textField.requestFocus();
    }
  }
  
  public void addKeyListener(KeyListener paramKeyListener)
  {
    this.textField.addKeyListener(paramKeyListener);
  }
  
  public void removeKeyListener(KeyListener paramKeyListener)
  {
    this.textField.removeKeyListener(paramKeyListener);
  }
}
