package com.aapeli.multiplayer.client.session.chat;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JLabel;

public class Shader
  extends JLabel
{
  private Color color = new Color(80, 80, 80);
  
  public Shader()
  {
    setOpaque(false);
  }
  
  protected void paintComponent(Graphics paramGraphics)
  {
    Rectangle localRectangle = getBounds();
    paramGraphics.setColor(this.color);
    for (int i = 0; i < localRectangle.height; i += 2) {
      paramGraphics.drawLine(localRectangle.x, localRectangle.y + i, localRectangle.x + localRectangle.width, localRectangle.y + i);
    }
  }
}
