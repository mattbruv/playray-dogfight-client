package com.aapeli.multiplayer.util.console;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JEditorPane;
import javax.swing.border.LineBorder;
import javax.swing.text.PlainDocument;

public class ConsoleField
  extends JEditorPane
{
  public ConsoleField()
  {
    setEditable(true);
    setBackground(Color.black);
    setFont(new Font("arial", 0, 11));
    setMaximumSize(new Dimension(740, getFontMetrics(getFont()).getHeight() + 2));
    setCaretColor(Color.white);
    setForeground(Color.white);
    setDocument(new PlainDocument());
  }
  
  public void setBackground(Color paramColor)
  {
    super.setBackground(paramColor);
    setBorder(new LineBorder(paramColor.darker().darker(), 1));
  }
}
