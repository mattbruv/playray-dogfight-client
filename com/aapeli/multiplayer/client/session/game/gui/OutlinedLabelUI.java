package com.aapeli.multiplayer.client.session.game.gui;

import com.aapeli.client.StringDraw;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicLabelUI;

public class OutlinedLabelUI
  extends BasicLabelUI
{
  protected void paintEnabledText(JLabel paramJLabel, Graphics paramGraphics, String paramString, int paramInt1, int paramInt2)
  {
    paramGraphics.setColor(paramJLabel.getForeground());
    StringDraw.drawOutlinedString(paramGraphics, paramJLabel.getBackground(), paramString, paramInt1, paramInt2, -1);
  }
}
