package com.aapeli.multiplayer.client.session.chat.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

public class BlinkButtonUI
  extends BasicButtonUI
{
  private Color blinkColor = Color.GREEN;
  private static Rectangle viewRect = new Rectangle();
  
  public void paint(Graphics paramGraphics, JComponent paramJComponent)
  {
    AbstractButton localAbstractButton = (AbstractButton)paramJComponent;
    ButtonModel localButtonModel = localAbstractButton.getModel();
    Insets localInsets = paramJComponent.getInsets();
    viewRect.x = localInsets.left;
    viewRect.y = localInsets.top;
    viewRect.width = (localAbstractButton.getWidth() - (localInsets.right + viewRect.x));
    viewRect.height = (localAbstractButton.getHeight() - (localInsets.bottom + viewRect.y));
    paramGraphics.setColor(this.blinkColor);
    paramGraphics.fillRect(viewRect.x, viewRect.y, viewRect.width, viewRect.height);
  }
  
  protected void paintIcon(Graphics paramGraphics, JComponent paramJComponent, Rectangle paramRectangle)
  {
    paramGraphics.setColor(this.blinkColor);
    paramGraphics.fillRect(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
  }
}
