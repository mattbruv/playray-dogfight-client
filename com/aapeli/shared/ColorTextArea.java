package com.aapeli.shared;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.JPanel;

public class ColorTextArea
  extends JPanel
  implements ComponentListener, AdjustmentListener
{
  public static final int COLOR_BLACK = 0;
  public static final int COLOR_RED = 1;
  public static final int COLOR_GREEN = 2;
  public static final int COLOR_BLUE = 3;
  public static final int COLOR_YELLOW = 4;
  public static final int COLOR_MAGENTA = 5;
  public static final int COLOR_CYAN = 6;
  public static final int COLOR_GRAY = 7;
  public static final int COLOR_WHITE = 8;
  private static final Color[] COLOR = { new Color(0, 0, 0), new Color(224, 0, 0), new Color(0, 160, 0), new Color(0, 0, 240), new Color(160, 128, 0), new Color(160, 0, 160), new Color(0, 144, 160), new Color(112, 112, 112), new Color(255, 255, 255) };
  private static final Color BG_COLOR = new Color(255, 255, 255);
  private static final Color LIGHT_BORDER_COLOR = new Color(192, 192, 192);
  private static final Color DARK_BORDER_COLOR = new Color(64, 64, 64);
  private static final int HMARGIN = 3;
  private static final int SB_WIDTH = 16;
  private Scrollbar scrollbar;
  private boolean sbadded;
  private Image bgimage;
  private int bgix;
  private int bgiy;
  private Font font;
  private Font bfont;
  private FontMetrics fm;
  private int fh;
  private int sx;
  private int sy;
  private int maxlen;
  private int spacing;
  private int lvis;
  private Vector lines;
  private Vector cache;
  private Image bi;
  private Graphics bg;
  private int bsx;
  private int bsy;
  private boolean borders;
  
  public ColorTextArea(int paramInt1, int paramInt2)
  {
    this(paramInt1, paramInt2, null);
  }
  
  public ColorTextArea(int paramInt1, int paramInt2, Font paramFont)
  {
    this.sx = paramInt1;
    this.sy = paramInt2;
    setSize(paramInt1, paramInt2);
    if (paramFont == null) {
      paramFont = Common.DEFAULT_FONT;
    }
    this.font = paramFont;
    this.fm = getFontMetrics(paramFont);
    this.fh = paramFont.getSize();
    this.bfont = new Font(paramFont.getName(), 1, paramFont.getSize());
    this.lines = new Vector();
    this.cache = new Vector();
    this.bgimage = null;
    this.spacing = (this.fh + 3);
    setVariablesBySize();
    this.borders = true;
    addComponentListener(this);
    setLayout(null);
    this.scrollbar = new Scrollbar(1);
    setScrollBarVariablesBySize();
    this.scrollbar.setUnitIncrement(1);
    this.sbadded = false;
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
    if ((this.bi == null) || (this.sx != this.bsx) || (this.sy != this.bsy))
    {
      this.bi = createImage(this.sx, this.sy);
      if (this.bi == null) {
        return;
      }
      this.bg = this.bi.getGraphics();
      this.bsx = this.sx;
      this.bsy = this.sy;
    }
    if (this.bgimage == null)
    {
      this.bg.setColor(BG_COLOR);
      this.bg.fillRect(0, 0, this.sx, this.sy);
    }
    else
    {
      this.bg.drawImage(this.bgimage, 0, 0, this.sx, this.sy, this.bgix, this.bgiy, this.bgix + this.sx, this.bgiy + this.sy, this);
    }
    synchronized (this)
    {
      int i = this.lines.size();
      if (i > 0)
      {
        int j = this.fh;
        int k = this.sbadded ? this.scrollbar.getValue() : 0;
        for (int m = 0; (m <= this.lvis) && (k < i); m++)
        {
          Line localLine = (Line)this.lines.elementAt(k);
          if (!localLine.isEmpty())
          {
            this.bg.setFont(localLine.isBold() ? this.bfont : this.font);
            this.bg.setColor(localLine.getColor());
            this.bg.drawString(localLine.getString(), 3, j);
          }
          j += this.spacing;
          k++;
        }
      }
    }
    if (this.borders)
    {
      this.bg.setColor(LIGHT_BORDER_COLOR);
      this.bg.drawRect(0, 0, this.sx - 1, this.sy - 1);
      this.bg.setColor(DARK_BORDER_COLOR);
      this.bg.drawLine(0, 0, this.sx - 1, 0);
      this.bg.drawLine(0, 0, 0, this.sy - 1);
    }
    paramGraphics.drawImage(this.bi, 0, 0, this);
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    update(paramGraphics);
  }
  
  public void componentShown(ComponentEvent paramComponentEvent) {}
  
  public void componentHidden(ComponentEvent paramComponentEvent) {}
  
  public void componentMoved(ComponentEvent paramComponentEvent) {}
  
  public void componentResized(ComponentEvent paramComponentEvent)
  {
    Dimension localDimension = getSize();
    this.sx = localDimension.width;
    this.sy = localDimension.height;
    resetAllLines();
  }
  
  public void adjustmentValueChanged(AdjustmentEvent paramAdjustmentEvent)
  {
    repaint();
  }
  
  public void setBackgroundImage(Image paramImage)
  {
    setBackgroundImage(paramImage, 0, 0);
  }
  
  public void setBackgroundImage(Image paramImage, int paramInt1, int paramInt2)
  {
    this.bgimage = paramImage;
    this.bgix = paramInt1;
    this.bgiy = paramInt2;
    repaint();
  }
  
  public void clear()
  {
    clear(true);
  }
  
  public void addLine()
  {
    addLine(null, null, false);
  }
  
  public void addLine(int paramInt, String paramString)
  {
    addLine(COLOR[paramInt], paramString, false);
  }
  
  public void addLine(Color paramColor, String paramString)
  {
    addLine(paramColor, paramString, false);
  }
  
  public void addLine(int paramInt, String paramString, boolean paramBoolean)
  {
    addLine(COLOR[paramInt], paramString, paramBoolean);
  }
  
  public void addLine(Color paramColor, String paramString, boolean paramBoolean)
  {
    addLineCache(paramColor, paramString, false, paramBoolean);
  }
  
  public void addBoldLine(int paramInt, String paramString)
  {
    addLineCache(COLOR[paramInt], paramString, true, true);
  }
  
  public synchronized String[] getTimeStampedCache()
  {
    int i = this.cache.size();
    String[] arrayOfString = new String[i];
    if (i > 0)
    {
      String str = "";
      for (int m = 0; m < i; m++)
      {
        Line localLine = (Line)this.cache.elementAt(m);
        if (localLine.isEmpty())
        {
          arrayOfString[m] = str;
        }
        else
        {
          Calendar localCalendar = Calendar.getInstance();
          localCalendar.setTime(new Date(localLine.getTime()));
          int j = localCalendar.get(11);
          int k = localCalendar.get(12);
          arrayOfString[m] = ("[" + (j < 10 ? "0" : "") + j + ":" + (k < 10 ? "0" : "") + k + "] " + localLine.getString());
        }
      }
    }
    return arrayOfString;
  }
  
  public void drawBorders(boolean paramBoolean)
  {
    this.borders = paramBoolean;
    repaint();
  }
  
  public Font getFont()
  {
    return this.font;
  }
  
  private synchronized void clear(boolean paramBoolean)
  {
    this.lines.removeAllElements();
    if (paramBoolean) {
      this.cache.removeAllElements();
    }
    remove(this.scrollbar);
    this.sbadded = false;
    repaint();
  }
  
  private synchronized void resetAllLines()
  {
    setVariablesBySize();
    setScrollBarVariablesBySize();
    clear(false);
    int i = this.cache.size();
    if (i == 0) {
      return;
    }
    for (int j = 0; j < i; j++)
    {
      Line localLine = (Line)this.cache.elementAt(j);
      realAddLine(localLine.getColor(), localLine.getString(), localLine.isBold());
    }
    setScrollBar(0, true);
    repaint();
  }
  
  private void setVariablesBySize()
  {
    this.maxlen = (this.sx - 6 - 16);
    this.lvis = (this.sy / this.spacing);
  }
  
  private void setScrollBarVariablesBySize()
  {
    this.scrollbar.setBounds(this.sx - 16 - 1, 1, 16, this.sy - 2);
    this.scrollbar.setBlockIncrement(this.lvis - 1);
  }
  
  private synchronized void addLineCache(Color paramColor, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.cache.addElement(new Line(paramColor, paramString, paramBoolean1));
    int i = this.lines.size();
    realAddLine(paramColor, paramString, paramBoolean1);
    setScrollBar(i, paramBoolean2);
    repaint();
  }
  
  private synchronized void realAddLine(Color paramColor, String paramString, boolean paramBoolean)
  {
    int i = paramString != null ? this.fm.stringWidth(paramString) : 0;
    if (i <= this.maxlen)
    {
      actualAddLine(paramColor, paramString, paramBoolean);
      return;
    }
    int j = paramString.length();
    int k = j - 1;
    while (this.fm.stringWidth(paramString.substring(0, k)) > this.maxlen)
    {
      k--;
      if (k <= 5)
      {
        actualAddLine(paramColor, paramString, paramBoolean);
        return;
      }
    }
    for (int m = k; (m > 3) && (paramString.charAt(m) != ' '); m--) {}
    if (m == 3) {
      m = k;
    }
    actualAddLine(paramColor, paramString.substring(0, m), paramBoolean);
    realAddLine(paramColor, (paramString.charAt(m) == ' ' ? " " : "  ") + paramString.substring(m), paramBoolean);
  }
  
  private synchronized void actualAddLine(Color paramColor, String paramString, boolean paramBoolean)
  {
    this.lines.addElement(new Line(paramColor, paramString, paramBoolean));
  }
  
  private synchronized void setScrollBar(int paramInt, boolean paramBoolean)
  {
    int i = this.lines.size();
    if (i <= this.lvis) {
      return;
    }
    int j = i - this.lvis;
    if (!this.sbadded)
    {
      add(this.scrollbar);
      this.scrollbar.addAdjustmentListener(this);
      this.sbadded = true;
    }
    else
    {
      int k = this.scrollbar.getValue();
      if ((!paramBoolean) && (k + this.lvis < paramInt)) {
        j = k;
      }
    }
    this.scrollbar.setValues(j, this.lvis, 0, i);
  }
  
  private class Line
  {
    private long time = System.currentTimeMillis();
    private Color color;
    private String string;
    private boolean bold;
    
    public Line(Color paramColor, String paramString, boolean paramBoolean)
    {
      this.color = paramColor;
      this.string = paramString;
      this.bold = paramBoolean;
    }
    
    protected long getTime()
    {
      return this.time;
    }
    
    protected Color getColor()
    {
      return this.color;
    }
    
    protected String getString()
    {
      return this.string;
    }
    
    protected boolean isBold()
    {
      return this.bold;
    }
    
    protected boolean isEmpty()
    {
      return this.string == null;
    }
  }
}
