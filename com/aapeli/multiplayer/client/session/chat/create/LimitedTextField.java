package com.aapeli.multiplayer.client.session.chat.create;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimitedTextField
  extends JTextField
{
  public LimitedTextField(int paramInt)
  {
    setDocument(new LimitedDocument(paramInt));
  }
  
  static class LimitedDocument
    extends PlainDocument
  {
    private int textLimit;
    
    public LimitedDocument(int paramInt)
    {
      this.textLimit = paramInt;
    }
    
    public void insertString(int paramInt, String paramString, AttributeSet paramAttributeSet)
      throws BadLocationException
    {
      for (int i = 0; i < paramString.length(); i++) {
        insertChar(paramInt + i, paramString.charAt(i), paramAttributeSet);
      }
    }
    
    public void insertChar(int paramInt, char paramChar, AttributeSet paramAttributeSet)
      throws BadLocationException
    {
      if ((getLength() >= this.textLimit) && (this.textLimit != 0)) {
        throw new BadLocationException("" + paramChar, paramInt);
      }
      super.insertString(paramInt, "" + paramChar, paramAttributeSet);
    }
  }
}
