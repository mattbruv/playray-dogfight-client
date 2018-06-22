package com.aapeli.multiplayer.util;

import java.awt.FontMetrics;
import javax.swing.JEditorPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public abstract class AbstractEditorPane
  extends JEditorPane
{
  protected static final int ALIGN_BOTTOM = 55;
  protected static final int ALIGN_TOP = 56;
  protected int lines = 0;
  protected int emptyLines = 0;
  protected int alignment;
  protected static final Style EMPTY_STYLE = StyleContext.getDefaultStyleContext().getStyle("default");
  
  public AbstractEditorPane(int paramInt)
  {
    this.alignment = paramInt;
    setFocusable(false);
    setOpaque(false);
  }
  
  public AbstractEditorPane()
  {
    this(56);
  }
  
  protected void addEmptyLine()
  {
    this.emptyLines += 1;
    try
    {
      Document localDocument = getDocument();
      if ((localDocument instanceof StyledDocument)) {
        insertString(0, "\n", EMPTY_STYLE);
      } else {
        insertString(0, "\n", null);
      }
    }
    catch (BadLocationException localBadLocationException) {}
  }
  
  protected void removeEmptyLine()
  {
    this.emptyLines -= 1;
    try
    {
      String str = getDocument().getText(0, getDocument().getLength());
      getDocument().remove(0, str.indexOf("\n") + 1);
    }
    catch (BadLocationException localBadLocationException) {}
  }
  
  protected int calculateHeight(int paramInt)
  {
    return getFontMetrics(getFont()).getHeight() * paramInt;
  }
  
  protected String insertLine(String paramString1, String paramString2)
  {
    FontMetrics localFontMetrics = getFontMetrics(getFont());
    int i = localFontMetrics.stringWidth(paramString1);
    int j = getWidth() - getWidth() / 50;
    if (i > j)
    {
      int k = paramString1.length() * j / i;
      for (i = localFontMetrics.stringWidth(paramString1.substring(0, k)); i < j; i = localFontMetrics.stringWidth(paramString1.substring(0, k))) {
        k++;
      }
      while (i > j)
      {
        k--;
        i = localFontMetrics.stringWidth(paramString1.substring(0, k));
      }
      paramString1 = paramString1.substring(0, k);
    }
    this.lines += 1;
    try
    {
      Document localDocument = getDocument();
      if ((localDocument instanceof StyledDocument)) {
        insertString(localDocument.getLength(), paramString1 + "\n", ((StyledDocument)localDocument).getStyle(paramString2));
      } else {
        insertString(localDocument.getLength(), paramString1 + "\n", null);
      }
    }
    catch (BadLocationException localBadLocationException) {}
    checkAligment();
    return paramString1;
  }
  
  protected void insertText(String paramString1, String paramString2)
  {
    try
    {
      Document localDocument = getDocument();
      if ((localDocument instanceof StyledDocument)) {
        insertString(localDocument.getLength(), paramString1, ((StyledDocument)localDocument).getStyle(paramString2));
      } else {
        insertString(localDocument.getLength(), paramString1, null);
      }
    }
    catch (BadLocationException localBadLocationException) {}
  }
  
  protected void removeLine()
  {
    try
    {
      if (this.alignment == 55) {
        removeEmptyLines();
      }
      String str1 = getDocument().getText(0, getDocument().getLength());
      if (str1.length() > 0)
      {
        int i = str1.indexOf("\n") + 1;
        if (i == -1) {
          i = str1.length();
        }
        String str2 = getDocument().getText(0, i);
        remove(0, i);
        this.lines -= 1;
        checkAligment();
      }
    }
    catch (BadLocationException localBadLocationException) {}
  }
  
  protected void insertString(int paramInt, String paramString, AttributeSet paramAttributeSet)
    throws BadLocationException
  {
    getDocument().insertString(paramInt, paramString, paramAttributeSet);
  }
  
  protected void remove(int paramInt1, int paramInt2)
    throws BadLocationException
  {
    getDocument().remove(paramInt1, paramInt2);
  }
  
  protected void checkAligment()
  {
    if (this.alignment == 55)
    {
      while (calculateHeight(this.lines + this.emptyLines) < getHeight()) {
        addEmptyLine();
      }
      while (calculateHeight(this.lines + this.emptyLines) > getHeight()) {
        removeEmptyLine();
      }
    }
  }
  
  protected void removeEmptyLines()
  {
    while (this.emptyLines > 0) {
      removeEmptyLine();
    }
  }
  
  public void removeAllLines()
  {
    removeEmptyLines();
    while (this.lines > 0) {
      removeLine();
    }
  }
}
