package com.aapeli.multiplayer.util.console;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

public class ConsoleArea
  extends JEditorPane
{
  public ConsoleArea()
  {
    setEditorKit(new StyledEditorKit());
    addStylesToDocument((StyledDocument)getDocument());
    setBackground(Color.black);
  }
  
  private void addStylesToDocument(StyledDocument paramStyledDocument)
  {
    setFont(new Font("arial", 0, 11));
    Style localStyle1 = StyleContext.getDefaultStyleContext().getStyle("default");
    Style localStyle2 = paramStyledDocument.addStyle("default", localStyle1);
    StyleConstants.setFontFamily(localStyle2, getFont().getFontName());
    StyleConstants.setFontSize(localStyle2, getFont().getSize());
    StyleConstants.setBold(localStyle2, getFont().isBold());
    StyleConstants.setForeground(localStyle2, Color.white);
  }
  
  public void insertLine(String paramString)
  {
    insertLine(paramString, "default");
  }
  
  public void insertLine(String paramString1, String paramString2)
  {
    try
    {
      Document localDocument = getDocument();
      insertString(localDocument.getLength(), paramString1 + "\n", ((StyledDocument)localDocument).getStyle(paramString2));
    }
    catch (BadLocationException localBadLocationException) {}
  }
  
  protected void insertString(int paramInt, String paramString, AttributeSet paramAttributeSet)
    throws BadLocationException
  {
    getDocument().insertString(paramInt, paramString, paramAttributeSet);
  }
  
  public void addStyle(String paramString, Color paramColor)
  {
    StyledDocument localStyledDocument = (StyledDocument)getDocument();
    Style localStyle = localStyledDocument.addStyle(paramString, localStyledDocument.getStyle("default"));
    StyleConstants.setForeground(localStyle, paramColor);
  }
}
