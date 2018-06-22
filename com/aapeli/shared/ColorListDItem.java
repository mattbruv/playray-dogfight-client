package com.aapeli.shared;

import com.aapeli.colorgui.ColorListItem;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

class ColorListDItem
{
  private static final int HMARGIN = 4;
  private int x;
  private int y;
  private int w;
  private int h;
  private int iconsx;
  private boolean bgimage;
  private Font font;
  private Font bfont;
  private ColorListItem item;
  private Color color;
  private String text;
  private Image icon;
  private boolean subtitle;
  
  protected ColorListDItem(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, Font paramFont1, Font paramFont2, ColorListItem paramColorListItem)
  {
    this.x = paramInt1;
    this.y = paramInt2;
    this.w = paramInt3;
    this.h = paramInt4;
    this.iconsx = paramInt5;
    this.bgimage = paramBoolean;
    this.font = paramFont1;
    this.bfont = paramFont2;
    this.item = paramColorListItem;
    this.subtitle = false;
  }
  
  protected ColorListDItem(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, Font paramFont, Color paramColor, String paramString, Image paramImage)
  {
    this.x = paramInt1;
    this.y = paramInt2;
    this.w = paramInt3;
    this.h = paramInt4;
    this.iconsx = paramInt5;
    this.bgimage = paramBoolean;
    this.font = paramFont;
    this.color = paramColor;
    this.text = paramString;
    this.icon = paramImage;
    this.subtitle = (paramImage != null);
  }
  
  protected void draw(Graphics paramGraphics, ColorList paramColorList)
  {
    if (this.item != null) {
      drawItem(paramGraphics, paramColorList);
    } else {
      drawText(paramGraphics, paramColorList);
    }
  }
  
  protected boolean isMouseOverThisItem(int paramInt)
  {
    return (paramInt >= this.y) && (paramInt < this.y + this.h);
  }
  
  protected ColorListItem getItem()
  {
    return this.item;
  }
  
  private void drawItem(Graphics paramGraphics, ColorList paramColorList)
  {
    Color localColor = this.item.getColor();
    if (this.item.isSelected())
    {
      paramGraphics.setColor(localColor);
      paramGraphics.fillRect(this.x, this.y, this.w, this.h);
      localColor = getOppositeColor(localColor);
    }
    drawIconAndString(paramGraphics, paramColorList, this.item.getIcon(), localColor, this.item.isBold() ? this.bfont : this.font, this.item.getString());
  }
  
  private void drawText(Graphics paramGraphics, ColorList paramColorList)
  {
    if (this.subtitle)
    {
      paramGraphics.setColor(new Color(232, 232, 232));
      paramGraphics.fillRect(this.x, this.y, this.w, this.h);
    }
    drawIconAndString(paramGraphics, paramColorList, this.icon, this.color, this.font, this.text);
  }
  
  private void drawIconAndString(Graphics paramGraphics, ColorList paramColorList, Image paramImage, Color paramColor, Font paramFont, String paramString)
  {
    int i = 4;
    if (paramImage != null)
    {
      paramGraphics.drawImage(paramImage, i, this.y + this.h / 2 - paramImage.getHeight(paramColorList) / 2, paramColorList);
      int j = this.iconsx > 0 ? this.iconsx : paramImage.getWidth(null);
      i += j + 3;
    }
    paramGraphics.setColor(paramColor);
    paramGraphics.setFont(paramFont);
    paramGraphics.drawString(paramString, i, this.y + this.h * 3 / 4 + 1);
  }
  
  private Color getOppositeColor(Color paramColor)
  {
    if (!this.bgimage) {
      return ColorList.BG_COLOR;
    }
    int i = paramColor.getRed();
    int j = paramColor.getGreen();
    int k = paramColor.getBlue();
    return new Color(255 - i, 255 - j, 255 - k);
  }
}
