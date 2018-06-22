package com.aapeli.multiplayer.client.session.chat.create;

import com.aapeli.multiplayer.client.common.GUIColors;
import com.aapeli.multiplayer.client.resources.Localization;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LabelledTextArea
  extends JPanel
  implements Option
{
  private JLabel label;
  private JTextArea textArea;
  private String textFieldDefaultText;
  private String key;
  
  public LabelledTextArea(String paramString1, String paramString2)
  {
    this(paramString1, paramString2, 100, 100, 30, 5);
  }
  
  public LabelledTextArea(String paramString1, String paramString2, int paramInt1, int paramInt2, final int paramInt3, final int paramInt4)
  {
    this.key = paramString1;
    this.textFieldDefaultText = paramString2;
    setLayout(new BoxLayout(this, 0));
    setOpaque(false);
    this.label = new JLabel();
    this.label.setText(Localization.getInstance().localize(paramString1));
    this.label.setBorder(new EmptyBorder(new Insets(1, 5, 1, 5)));
    add(this.label);
    this.textArea = new JTextArea();
    this.textArea.setText(paramString2);
    this.textArea.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.SHARP_BORDER), 1));
    this.textArea.setMaximumSize(new Dimension(paramInt1, paramInt2));
    this.textArea.setMinimumSize(new Dimension(paramInt1, paramInt2));
    this.textArea.setPreferredSize(new Dimension(paramInt1, paramInt2));
    this.textArea.setLineWrap(false);
    this.textArea.setEditable(true);
    this.textArea.setDocument(new PlainDocument()
    {
      public void insertString(int paramAnonymousInt, String paramAnonymousString, AttributeSet paramAnonymousAttributeSet)
        throws BadLocationException
      {
        for (int i = 0; i < paramAnonymousString.length(); i++) {
          insertChar(paramAnonymousInt + i, paramAnonymousString.charAt(i), paramAnonymousAttributeSet);
        }
      }
      
      public void insertChar(int paramAnonymousInt, char paramAnonymousChar, AttributeSet paramAnonymousAttributeSet)
        throws BadLocationException
      {
        if (paramAnonymousChar == '\f') {
          paramAnonymousChar = '\n';
        } else if (paramAnonymousChar == '\r') {
          throw new BadLocationException("" + paramAnonymousChar, paramAnonymousInt);
        }
        String str = getText(0, getLength());
        if (paramAnonymousChar == '\n')
        {
          if (str.split("\n", -1).length >= paramInt4) {
            throw new BadLocationException("" + paramAnonymousChar, paramAnonymousInt);
          }
        }
        else
        {
          int i = paramAnonymousInt - 1;
          if (i < 0) {
            i = 0;
          }
          int j = str.lastIndexOf('\n', i);
          if (j == -1) {
            j = 0;
          } else {
            j++;
          }
          int k = str.indexOf('\n', paramAnonymousInt);
          if (k == -1) {
            k = str.length();
          }
          int m = k - j;
          if (m >= paramInt3) {
            throw new BadLocationException("" + paramAnonymousChar, paramAnonymousInt);
          }
        }
        super.insertString(paramAnonymousInt, "" + paramAnonymousChar, paramAnonymousAttributeSet);
      }
    });
    add(this.textArea);
  }
  
  public void setTextAreaMinimumWidth(int paramInt)
  {
    this.textArea.setMinimumSize(new Dimension(paramInt, 0));
  }
  
  public String getValue()
  {
    return this.textArea.getText();
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public void reset()
  {
    this.textArea.setText(this.textFieldDefaultText);
  }
  
  public void setValue(String paramString)
  {
    this.textArea.setText(paramString);
  }
  
  public void setEditable(boolean paramBoolean)
  {
    this.textArea.setEditable(paramBoolean);
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    this.textArea.setEditable(paramBoolean);
  }
}
