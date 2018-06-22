package com.aapeli.shared;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JPanel;

public class ColorCheckbox
  extends JPanel
  implements ItemSelectable, MouseListener
{
  public static final int ALIGN_LEFT = -1;
  public static final int ALIGN_CENTER = 0;
  public static final int ALIGN_RIGHT = 1;
  private static final Color BOX_BG_COLOR = new Color(248, 248, 248);
  private static final Color BOX_FG_COLOR = Color.black;
  private Font font;
  private Color fgcolor;
  private Color boxbgcolor;
  private Color boxfgcolor;
  private Color boxlbcolor;
  private Color boxdbcolor;
  private Image bgimage;
  private int bgix;
  private int bgiy;
  private String text;
  private int align;
  private boolean state;
  private Vector listeners;
  private Image bi;
  private Graphics bg;
  private int bsx;
  private int bsy;
  
  public ColorCheckbox()
  {
    this(null, false);
  }
  
  public ColorCheckbox(boolean paramBoolean)
  {
    this(null, paramBoolean);
  }
  
  public ColorCheckbox(String paramString)
  {
    this(paramString, false);
  }
  
  public ColorCheckbox(String paramString, boolean paramBoolean)
  {
    this.text = paramString;
    this.state = paramBoolean;
    this.listeners = new Vector();
    this.align = -1;
    setFont(Common.DEFAULT_FONT);
    setForeground(Common.DEFAULT_FG_COLOR);
    setBoxBackground(BOX_BG_COLOR);
    setBoxForeground(BOX_FG_COLOR);
    addMouseListener(this);
  }
  
  public void addNotify()
  {
    super.addNotify();
    repaint();
  }
  
  public void paint(Graphics paramGraphics)
  {
    update(paramGraphics);
  }
  
  public void update(Graphics paramGraphics)
  {
    Dimension localDimension = getSize();
    int i = localDimension.width;
    int j = localDimension.height;
    if ((this.bi == null) || (i != this.bsx) || (j != this.bsy))
    {
      this.bi = createBuffer(i, j);
      this.bg = this.bi.getGraphics();
      this.bsx = i;
      this.bsy = j;
    }
    if (this.bgimage != null)
    {
      this.bg.drawImage(this.bgimage, 0, 0, i, j, this.bgix, this.bgiy, this.bgix + i, this.bgiy + j, this);
    }
    else
    {
      this.bg.setColor(getBackground());
      this.bg.fillRect(0, 0, i, j);
    }
    Font localFont = this.text != null ? getFontForTextAndSize(this.font, this.text, i - (j + 4)) : null;
    int k = 0;
    if ((this.align == 0) || (this.align == 1))
    {
      m = j + 4 + (localFont != null ? getFontMetrics(localFont).stringWidth(this.text) : 0);
      if (this.align == 0) {
        k = i / 2 - m / 2;
      } else {
        k = i - 2 - m;
      }
    }
    int m = j - 4;
    this.bg.setColor(this.boxbgcolor);
    this.bg.fillRect(k + 2, 2, m, m);
    this.bg.setColor(this.boxlbcolor);
    this.bg.drawRect(k + 2, 2, m - 1, m - 1);
    this.bg.setColor(this.boxdbcolor);
    this.bg.drawLine(k + 2, 2, k + m, 2);
    this.bg.drawLine(k + 2, 2, k + 2, m + 1);
    if (this.state)
    {
      this.bg.setColor(this.boxfgcolor);
      m -= 4;
      int n = m / 3;
      int i1 = n - 1;
      int i2 = m - n - 2;
      this.bg.drawLine(k + 4 + n, 4 + m - 2, k + 4 + n - i1, 4 + m - 2 - i1);
      this.bg.drawLine(k + 4 + n, 4 + m - 2 - 1, k + 4 + n - i1, 4 + m - 2 - i1 - 1);
      this.bg.drawLine(k + 4 + n, 4 + m - 2 - 2, k + 4 + n - i1, 4 + m - 2 - i1 - 2);
      this.bg.drawLine(k + 4 + n, 4 + m - 2, k + 4 + n + i2, 4 + m - 2 - i2);
      this.bg.drawLine(k + 4 + n, 4 + m - 2 - 1, k + 4 + n + i2, 4 + m - 2 - i2 - 1);
      this.bg.drawLine(k + 4 + n, 4 + m - 2 - 2, k + 4 + n + i2, 4 + m - 2 - i2 - 2);
    }
    if (localFont != null)
    {
      this.bg.setFont(localFont);
      this.bg.setColor(this.fgcolor);
      drawText(this.bg, this.text, k + j + 4, j / 2 + localFont.getSize() * 3 / 8 + 1);
    }
    paramGraphics.drawImage(this.bi, 0, 0, this);
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    update(paramGraphics);
  }
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mousePressed(MouseEvent paramMouseEvent) {}
  
  public void mouseClicked(MouseEvent paramMouseEvent) {}
  
  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    boolean bool = !this.state;
    realSetState(bool);
    processItemEvent();
  }
  
  public synchronized void addItemListener(ItemListener paramItemListener)
  {
    this.listeners.addElement(paramItemListener);
  }
  
  public synchronized void removeItemListener(ItemListener paramItemListener)
  {
    this.listeners.removeElement(paramItemListener);
  }
  
  public Object[] getSelectedObjects()
  {
    if (!this.state) {
      return null;
    }
    Object[] arrayOfObject = { this };
    return arrayOfObject;
  }
  
  public void setLabel(String paramString)
  {
    this.text = paramString;
    repaint();
  }
  
  public String getLabel()
  {
    return this.text;
  }
  
  public void setFont(Font paramFont)
  {
    this.font = paramFont;
    repaint();
  }
  
  public void setAlign(int paramInt)
  {
    this.align = paramInt;
    repaint();
  }
  
  /**
   * @deprecated
   */
  public void setBackgroundImage(Image paramImage, int paramInt1, int paramInt2)
  {
    this.bgimage = paramImage;
    this.bgix = paramInt1;
    this.bgiy = paramInt2;
    repaint();
  }
  
  public void setForeground(Color paramColor)
  {
    this.fgcolor = paramColor;
    repaint();
  }
  
  public void setBoxBackground(Color paramColor)
  {
    this.boxbgcolor = paramColor;
    this.boxlbcolor = getColor(paramColor, 32);
    this.boxdbcolor = getColor(paramColor, -48);
    repaint();
  }
  
  public void setBoxForeground(Color paramColor)
  {
    this.boxfgcolor = paramColor;
    repaint();
  }
  
  public void setState(boolean paramBoolean)
  {
    if (this.state == paramBoolean) {
      return;
    }
    realSetState(paramBoolean);
  }
  
  public boolean getState()
  {
    return this.state;
  }
  
  public void click()
  {
    mouseReleased(null);
  }
  
  public Dimension getPreferredSize()
  {
    int i = 3 + this.font.getSize() + 3;
    return new Dimension(i + 4 + getFontMetrics(this.font).stringWidth(this.text) + 4, i);
  }
  
  public Image createBuffer(int paramInt1, int paramInt2)
  {
    return createImage(paramInt1, paramInt2);
  }
  
  public void drawText(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2)
  {
    paramGraphics.drawString(paramString, paramInt1, paramInt2);
  }
  
  public void realSetState(boolean paramBoolean)
  {
    this.state = paramBoolean;
    repaint();
  }
  
  private Color getColor(Color paramColor, int paramInt)
  {
    return new Color(changeColorComponent(paramColor.getRed(), paramInt), changeColorComponent(paramColor.getGreen(), paramInt), changeColorComponent(paramColor.getBlue(), paramInt));
  }
  
  private int changeColorComponent(int paramInt1, int paramInt2)
  {
    paramInt1 += paramInt2;
    if (paramInt1 < 0) {
      paramInt1 = 0;
    }
    if (paramInt1 > 255) {
      paramInt1 = 255;
    }
    return paramInt1;
  }
  
  private Font getFontForTextAndSize(Font paramFont, String paramString, int paramInt)
  {
    int i = getFontMetrics(paramFont).stringWidth(paramString);
    if (i <= paramInt) {
      return paramFont;
    }
    int k = paramFont.getSize();
    do
    {
      Font localFont = paramFont;
      int j = i;
      k--;
      paramFont = new Font(localFont.getName(), localFont.getStyle(), k);
      i = getFontMetrics(paramFont).stringWidth(paramString);
      if (i >= j) {
        return localFont;
      }
    } while ((i > paramInt) && (k > 9));
    return paramFont;
  }
  
  private synchronized void processItemEvent()
  {
    if (this.listeners.size() == 0) {
      return;
    }
    ItemEvent localItemEvent = new ItemEvent(this, 0, this, 701);
    Enumeration localEnumeration = this.listeners.elements();
    while (localEnumeration.hasMoreElements()) {
      ((ItemListener)localEnumeration.nextElement()).itemStateChanged(localItemEvent);
    }
  }
}
