package com.aapeli.shared;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JPanel;

public class ColorButton
  extends JPanel
  implements MouseMotionListener, MouseListener
{
  public static final int BORDER_NONE = 0;
  public static final int BORDER_NORMAL = 1;
  public static final int BORDER_THICK = 2;
  private static final Color BG_COLOR = new Color(192, 192, 192);
  private static final int MIN_FONT_SIZE = 9;
  private Color bgcolor;
  private Color fgcolor;
  private Color hlbgcolor;
  private Color lbcolor;
  private Color dbcolor;
  private Color bcolor;
  private Image img_bg_normal;
  private Image img_bg_highlight;
  private Image img_fg_normal;
  private Image img_fg_selected;
  private Image img_icon;
  private int imgbglx;
  private int imgbgly;
  private int imgfgsx;
  private int imgfgsy;
  private int imgisx;
  private int imgisy;
  private boolean imgbgfit;
  private String text;
  private Font font;
  private boolean bgfade;
  private boolean highlight;
  private boolean pressed;
  private int border;
  private Vector listeners;
  private Image bi;
  private Graphics bg;
  private int bsx;
  private int bsy;
  
  public ColorButton()
  {
    this(null);
  }
  
  public ColorButton(String paramString)
  {
    setBackground(BG_COLOR);
    setForeground(Common.DEFAULT_FG_COLOR);
    setFont(Common.DEFAULT_FONT);
    setLabel(paramString);
    this.bgfade = true;
    this.highlight = (this.pressed = 0);
    this.border = 1;
    this.listeners = new Vector();
    addMouseMotionListener(this);
    addMouseListener(this);
  }
  
  public void addNotify()
  {
    super.addNotify();
    this.highlight = (this.pressed = 0);
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
      this.bi = createImage(i, j);
      this.bg = this.bi.getGraphics();
      this.bsx = i;
      this.bsy = j;
    }
    this.bg.setColor(getBackground());
    this.bg.fillRect(0, 0, i, j);
    boolean bool = isHighlighted();
    Object localObject;
    if ((this.img_bg_normal != null) && (this.img_bg_highlight != null))
    {
      localObject = bool ? this.img_bg_highlight : this.img_bg_normal;
      if (!this.imgbgfit) {
        this.bg.drawImage((Image)localObject, 0, 0, i, j, this.imgbglx, this.imgbgly, this.imgbglx + i, this.imgbgly + j, this);
      } else {
        this.bg.drawImage((Image)localObject, 0, 0, i, j, this);
      }
    }
    else
    {
      localObject = bool ? this.hlbgcolor : this.bgcolor;
      if (this.bgfade)
      {
        drawBackground(this.bg, (Color)localObject, i, j);
      }
      else
      {
        this.bg.setColor((Color)localObject);
        clearBackground(this.bg, i, j);
      }
    }
    if (this.bcolor != null) {
      this.bg.setColor(this.bcolor);
    }
    drawBorder(this.bg, i, j);
    if (this.img_fg_normal != null) {
      this.bg.drawImage(isNormalState() ? this.img_fg_normal : this.img_fg_selected, this.imgfgsx > 0 ? i / 2 - this.imgfgsx / 2 : 0, this.imgfgsy > 0 ? j / 2 - this.imgfgsy / 2 : 0, this);
    }
    int k = i / 2;
    int m = 0;
    int i1;
    if (this.img_icon != null)
    {
      int n = (j - this.imgisy) / 2;
      i1 = drawIcon(this.bg, this.img_icon, n);
      k = i / 2 + n + this.imgisx / 2 - 1;
      m = i1 + this.imgisx + 1;
    }
    if (this.text != null)
    {
      this.bg.setColor(this.fgcolor);
      Font localFont = getFontForTextAndSize(getFontWithPossibleBold(this.font), this.text, i - 2);
      this.bg.setFont(localFont);
      i1 = k - getFontMetrics(localFont).stringWidth(this.text) / 2;
      if (i1 < m) {
        i1 = m;
      }
      this.bg.drawString(this.text, i1, j / 2 + localFont.getSize() * 3 / 8 + 1);
    }
    paramGraphics.drawImage(this.bi, 0, 0, this);
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    update(paramGraphics);
  }
  
  public void mouseEntered(MouseEvent paramMouseEvent)
  {
    this.highlight = true;
    repaint();
  }
  
  public void mouseExited(MouseEvent paramMouseEvent)
  {
    this.highlight = (this.pressed = 0);
    repaint();
  }
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    this.pressed = true;
    repaint();
  }
  
  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    boolean bool = this.pressed;
    this.pressed = false;
    repaint();
    if (bool) {
      processActionEvent();
    }
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {}
  
  public void mouseMoved(MouseEvent paramMouseEvent) {}
  
  public void mouseDragged(MouseEvent paramMouseEvent) {}
  
  public void setBackground(Color paramColor)
  {
    if (paramColor == null) {
      paramColor = BG_COLOR;
    }
    super.setBackground(paramColor);
    this.bgcolor = paramColor;
    this.hlbgcolor = getColor(paramColor, 32);
    this.lbcolor = getColor(paramColor, 48);
    this.dbcolor = getColor(paramColor, -48);
    repaint();
  }
  
  public void setBackgroundFade(boolean paramBoolean)
  {
    this.bgfade = paramBoolean;
  }
  
  public void setForeground(Color paramColor)
  {
    if (paramColor == null) {
      paramColor = Common.DEFAULT_FG_COLOR;
    }
    this.fgcolor = paramColor;
    repaint();
  }
  
  public void setBackgroundImage(Image paramImage)
  {
    setBackgroundImage(paramImage, paramImage, 0, 0);
  }
  
  public void setBackgroundImage(Image paramImage1, Image paramImage2)
  {
    setBackgroundImage(paramImage1, paramImage2, 0, 0);
  }
  
  public void setBackgroundImage(Image paramImage, int paramInt1, int paramInt2)
  {
    setBackgroundImage(paramImage, paramImage, paramInt1, paramInt2);
  }
  
  public void setBackgroundImage(Image paramImage1, Image paramImage2, int paramInt1, int paramInt2)
  {
    this.img_bg_normal = paramImage1;
    this.img_bg_highlight = paramImage2;
    this.imgbglx = paramInt1;
    this.imgbgly = paramInt2;
    this.imgbgfit = false;
    repaint();
  }
  
  public void setFittedBackgroundImage(Image paramImage1, Image paramImage2)
  {
    this.img_bg_normal = paramImage1;
    this.img_bg_highlight = paramImage2;
    this.imgbgfit = true;
    repaint();
  }
  
  public void setForegroundImage(Image paramImage)
  {
    setForegroundImage(paramImage, paramImage, 0, 0);
  }
  
  public void setForegroundImage(Image paramImage1, Image paramImage2)
  {
    setForegroundImage(paramImage1, paramImage2, 0, 0);
  }
  
  public void setForegroundImage(Image paramImage, int paramInt1, int paramInt2)
  {
    setForegroundImage(paramImage, paramImage, paramInt1, paramInt2);
  }
  
  public void setForegroundImage(Image paramImage1, Image paramImage2, int paramInt1, int paramInt2)
  {
    this.img_fg_normal = paramImage1;
    this.img_fg_selected = paramImage2;
    this.imgfgsx = paramInt1;
    this.imgfgsy = paramInt2;
    repaint();
  }
  
  public void setIconImage(Image paramImage)
  {
    if (paramImage != null) {
      setIconImage(paramImage, paramImage.getWidth(null), paramImage.getHeight(null));
    } else {
      setIconImage(null, -1, -1);
    }
  }
  
  public void setIconImage(Image paramImage, int paramInt1, int paramInt2)
  {
    this.img_icon = paramImage;
    this.imgisx = paramInt1;
    this.imgisy = paramInt2;
    repaint();
  }
  
  public void setFont(Font paramFont)
  {
    this.font = paramFont;
    repaint();
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
  
  public void setBorder(int paramInt)
  {
    this.border = paramInt;
  }
  
  public void setBorderColor(Color paramColor)
  {
    this.bcolor = paramColor;
    repaint();
  }
  
  public void click()
  {
    this.pressed = true;
    mouseReleased(null);
  }
  
  public Dimension getPreferredSize()
  {
    return new Dimension(13 + getFontMetrics(this.font).stringWidth(this.text) + 13, 5 + this.font.getSize() + 5);
  }
  
  public synchronized void addActionListener(ActionListener paramActionListener)
  {
    this.listeners.addElement(paramActionListener);
  }
  
  public synchronized void removeActionListener(ActionListener paramActionListener)
  {
    this.listeners.removeElement(paramActionListener);
  }
  
  public boolean isNormalState()
  {
    return !this.pressed;
  }
  
  public synchronized void processActionEvent()
  {
    if (this.listeners.size() == 0) {
      return;
    }
    ActionEvent localActionEvent = new ActionEvent(this, 1001, this.text);
    Enumeration localEnumeration = this.listeners.elements();
    while (localEnumeration.hasMoreElements()) {
      ((ActionListener)localEnumeration.nextElement()).actionPerformed(localActionEvent);
    }
  }
  
  public boolean isHighlighted()
  {
    boolean bool = this.highlight;
    return bool;
  }
  
  public boolean isBolded()
  {
    return false;
  }
  
  public void clearBackground(Graphics paramGraphics, int paramInt1, int paramInt2)
  {
    paramGraphics.fillRect(0, 0, paramInt1, paramInt2);
  }
  
  public void drawBorder(Graphics paramGraphics, int paramInt1, int paramInt2)
  {
    if (this.border == 0) {
      return;
    }
    boolean bool = isNormalState();
    int i = this.border == 1 ? 1 : 0;
    if (this.bcolor == null) {
      paramGraphics.setColor(bool ? this.dbcolor : this.lbcolor);
    }
    if (i != 0)
    {
      paramGraphics.drawRect(0, 0, paramInt1 - 1, paramInt2 - 1);
    }
    else
    {
      paramGraphics.drawRect(0, 0, paramInt1 - 1, paramInt2 - 1);
      paramGraphics.drawRect(1, 1, paramInt1 - 3, paramInt2 - 3);
    }
    if (this.bcolor == null) {
      paramGraphics.setColor(bool ? this.lbcolor : this.dbcolor);
    }
    if (i != 0)
    {
      paramGraphics.drawLine(0, 0, paramInt1 - 1, 0);
      paramGraphics.drawLine(0, 0, 0, paramInt2 - 1);
    }
    else
    {
      paramGraphics.drawLine(0, 0, paramInt1 - 2, 0);
      paramGraphics.drawLine(0, 1, paramInt1 - 3, 1);
      paramGraphics.drawLine(0, 0, 0, paramInt2 - 1);
      paramGraphics.drawLine(1, 0, 1, paramInt2 - 2);
    }
  }
  
  public int drawIcon(Graphics paramGraphics, Image paramImage, int paramInt)
  {
    paramGraphics.drawImage(paramImage, paramInt, paramInt, this);
    return paramInt;
  }
  
  private Color getColor(Color paramColor, int paramInt)
  {
    int i = paramColor.getRed() + paramInt;
    int j = paramColor.getGreen() + paramInt;
    int k = paramColor.getBlue() + paramInt;
    if (i < 0) {
      i = 0;
    }
    if (i > 255) {
      i = 255;
    }
    if (j < 0) {
      j = 0;
    }
    if (j > 255) {
      j = 255;
    }
    if (k < 0) {
      k = 0;
    }
    if (k > 255) {
      k = 255;
    }
    return new Color(i, j, k);
  }
  
  private void drawBackground(Graphics paramGraphics, Color paramColor, int paramInt1, int paramInt2)
  {
    int i = paramColor.getRed();
    int j = paramColor.getGreen();
    int k = paramColor.getBlue();
    int m = i;
    int n = j;
    int i1 = k;
    for (int i2 = paramInt2 / 2; i2 >= 0; i2--)
    {
      paramGraphics.setColor(new Color(m, n, i1));
      paramGraphics.drawLine(0, i2, paramInt1 - 1, i2);
      m = changeColorComponent(m, 3);
      n = changeColorComponent(n, 3);
      i1 = changeColorComponent(i1, 3);
    }
    m = i;
    n = j;
    i1 = k;
    for (i2 = paramInt2 / 2 + 1; i2 < paramInt2; i2++)
    {
      m = changeColorComponent(m, -3);
      n = changeColorComponent(n, -3);
      i1 = changeColorComponent(i1, -3);
      paramGraphics.setColor(new Color(m, n, i1));
      paramGraphics.drawLine(0, i2, paramInt1 - 1, i2);
    }
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
  
  private Font getFontWithPossibleBold(Font paramFont)
  {
    if (isBolded()) {
      return new Font(paramFont.getName(), 1, paramFont.getSize());
    }
    return paramFont;
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
}
