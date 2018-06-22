package com.aapeli.shared;

import com.aapeli.colorgui.ColorListItem;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.ItemSelectable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

public final class ColorList
  extends JPanel
  implements ComponentListener, AdjustmentListener, MouseListener, ItemSelectable
{
  public static final int SELECTABLE_NONE = 0;
  public static final int SELECTABLE_ONE = 1;
  public static final int SELECTABLE_MULTI = 2;
  public static final int ID_CLICKED = 0;
  public static final int ID_RIGHTCLICKED = 1;
  public static final int ID_DOUBLECLICKED = 2;
  public static final int SORT_NONE = 0;
  public static final int SORT_TEXT_ABC = 1;
  public static final int SORT_TEXT_CBA = 2;
  public static final int SORT_VALUE_123 = 3;
  public static final int SORT_VALUE_321 = 4;
  protected static final Color BG_COLOR = new Color(255, 255, 255);
  private static final Color LIGHT_BORDER_COLOR = new Color(192, 192, 192);
  private static final Color DARK_BORDER_COLOR = new Color(64, 64, 64);
  private static final int SB_WIDTH = 16;
  private static final char LAST_CHAR = 'ÿ';
  private JScrollBar scrollbar;
  private boolean sbadded;
  private Image bgimage;
  private int bgix;
  private int bgiy;
  private Font font;
  private Font bfont;
  private int iconsx;
  private int sx;
  private int sy;
  private int spacing;
  private int lvis;
  private int selectable;
  private int sorting;
  private String title;
  private Color tcolor;
  private Vector items;
  private Vector ditems;
  private int lcmx;
  private int lcmy;
  private int msd_index;
  private int msd_event;
  private Image bi;
  private Graphics bg;
  private int bsx;
  private int bsy;
  private Vector listeners;
  
  public ColorList(int paramInt1, int paramInt2)
  {
    this(paramInt1, paramInt2, Common.DEFAULT_FONT, 0, 0);
  }
  
  public ColorList(int paramInt1, int paramInt2, Font paramFont)
  {
    this(paramInt1, paramInt2, paramFont, 0, 0);
  }
  
  public ColorList(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this(paramInt1, paramInt2, Common.DEFAULT_FONT, paramInt3, paramInt4);
  }
  
  public ColorList(int paramInt1, int paramInt2, Font paramFont, int paramInt3, int paramInt4)
  {
    this.sx = paramInt1;
    this.sy = paramInt2;
    setSize(paramInt1, paramInt2);
    this.font = paramFont;
    int i = paramFont.getSize();
    this.bfont = new Font(paramFont.getName(), 1, i);
    this.iconsx = paramInt3;
    this.items = new Vector();
    this.selectable = 0;
    this.sorting = 0;
    this.spacing = ((i > paramInt4 ? i : paramInt4) + 4);
    this.lvis = (paramInt2 / this.spacing);
    this.lcmx = (this.lcmy = this.bsx = this.bsy = -1);
    this.msd_index = -1;
    setLayout(null);
    this.scrollbar = new JScrollBar(1);
    this.scrollbar.setBounds(paramInt1 - 16 - 1, 1, 16, paramInt2 - 2);
    this.scrollbar.setBlockIncrement(this.lvis - 1);
    this.scrollbar.setUnitIncrement(1);
    this.sbadded = false;
    addComponentListener(this);
    addMouseListener(this);
    this.listeners = new Vector();
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
      this.ditems = new Vector();
      int i = 0;
      ColorListDItem localColorListDItem;
      if (this.title != null)
      {
        localColorListDItem = new ColorListDItem(1, i, this.sx - 2, this.spacing, this.iconsx, this.bgimage != null, this.bfont, this.tcolor, this.title, null);
        this.ditems.addElement(localColorListDItem);
        localColorListDItem.draw(this.bg, this);
        i += this.spacing;
      }
      int j = this.items.size();
      if (j > 0)
      {
        int k = this.title != null ? this.lvis - 1 : this.lvis;
        int m = k;
        int n = this.sbadded ? this.scrollbar.getValue() : 0;
        for (int i1 = 0; (i1 < m + 1) && (n < j); i1++)
        {
          ColorListItem localColorListItem = getItem(n);
          localColorListDItem = new ColorListDItem(1, i, this.sx - 2, this.spacing, this.iconsx, this.bgimage != null, this.font, this.bfont, localColorListItem);
          this.ditems.addElement(localColorListDItem);
          localColorListDItem.draw(this.bg, this);
          i += this.spacing;
          n++;
        }
      }
    }
    this.bg.setColor(LIGHT_BORDER_COLOR);
    this.bg.drawRect(0, 0, this.sx - 1, this.sy - 1);
    this.bg.setColor(DARK_BORDER_COLOR);
    this.bg.drawLine(0, 0, this.sx - 1, 0);
    this.bg.drawLine(0, 0, 0, this.sy - 1);
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
    this.lvis = (this.sy / this.spacing);
    this.scrollbar.setBounds(this.sx - 16 - 1, 1, 16, this.sy - 2);
    this.scrollbar.setBlockIncrement(this.lvis - 1);
    setScrollBar(false, 0);
    repaint();
  }
  
  public void adjustmentValueChanged(AdjustmentEvent paramAdjustmentEvent)
  {
    repaint();
  }
  
  public synchronized void mousePressed(MouseEvent paramMouseEvent)
  {
    this.lcmx = paramMouseEvent.getX();
    this.lcmy = paramMouseEvent.getY();
    ColorListItem localColorListItem = getItemByMouseY(this.lcmy);
    if (localColorListItem == null) {
      return;
    }
    boolean bool = paramMouseEvent.isMetaDown();
    int i = paramMouseEvent.getClickCount() == 2 ? 1 : 0;
    int j = i != 0 ? 2 : bool ? 1 : 0;
    int k = 701;
    if (!localColorListItem.isSelected())
    {
      if (this.selectable == 0) {
        return;
      }
      if (this.selectable == 1) {
        removeAllSelections();
      }
      localColorListItem.setSelected(true);
      k = 1;
    }
    else if (!bool)
    {
      localColorListItem.setSelected(false);
      k = 2;
    }
    if (this.selectable == 2)
    {
      int m = this.items.indexOf(localColorListItem);
      if ((j == 0) && ((k == 1) || (k == 2)))
      {
        if ((this.msd_index >= 0) && (paramMouseEvent.isShiftDown()))
        {
          int n = Math.min(this.msd_index, m);
          int i1 = Math.max(this.msd_index, m);
          for (int i2 = n; i2 <= i1; i2++) {
            getItem(i2).setSelected(this.msd_event == 1);
          }
        }
        this.msd_index = m;
        this.msd_event = k;
      }
      else
      {
        this.msd_index = -1;
      }
    }
    if (bool) {
      update(getGraphics());
    }
    processItemEvent(localColorListItem, j, k);
    repaint();
  }
  
  public void mouseReleased(MouseEvent paramMouseEvent) {}
  
  public void mouseClicked(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
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
    return getSelectedItems();
  }
  
  public void setSelectable(int paramInt)
  {
    this.selectable = paramInt;
    if (paramInt == 0) {
      removeAllSelections();
    }
    if ((paramInt == 1) && (getSelectedItemCount() > 1)) {
      removeAllSelections();
    }
  }
  
  /**
   * @deprecated
   */
  public void setSorted(boolean paramBoolean)
  {
    setSorting(paramBoolean ? 1 : 0);
  }
  
  public void setSorting(int paramInt)
  {
    if (paramInt == this.sorting) {
      return;
    }
    this.sorting = paramInt;
    if (paramInt == 0) {
      return;
    }
    reSort();
  }
  
  public synchronized void reSort()
  {
    int i = this.items.size();
    if (i == 0) {
      return;
    }
    ColorListItem[] arrayOfColorListItem = new ColorListItem[i];
    for (int j = 0; j < i; j++) {
      arrayOfColorListItem[j] = getItem(j);
    }
    this.items.removeAllElements();
    for (j = 0; j < i; j++) {
      this.items.insertElementAt(arrayOfColorListItem[j], getSortIndex(arrayOfColorListItem[j]));
    }
    repaint();
  }
  
  public int getSorting()
  {
    return this.sorting;
  }
  
  public void setBackgroundImage(Image paramImage, int paramInt1, int paramInt2)
  {
    this.bgimage = paramImage;
    this.bgix = paramInt1;
    this.bgiy = paramInt2;
    repaint();
  }
  
  public void setTitle(String paramString, Color paramColor)
  {
    this.title = paramString;
    this.tcolor = paramColor;
    repaint();
  }
  
  public int getItemCount()
  {
    return this.items.size();
  }
  
  public synchronized int getSelectedItemCount()
  {
    int i = this.items.size();
    int j = 0;
    for (int k = 0; k < i; k++) {
      if (getItem(k).isSelected()) {
        j++;
      }
    }
    return j;
  }
  
  public void addItem(String paramString)
  {
    addItem(new ColorListItem(paramString));
  }
  
  public synchronized void addItem(ColorListItem paramColorListItem)
  {
    int i = getSortIndex(paramColorListItem);
    this.items.insertElementAt(paramColorListItem, i);
    int j = this.scrollbar.getValue();
    int k = i < j ? 1 : 0;
    if ((k == 0) && (j > 0) && (j + this.scrollbar.getVisibleAmount() == this.scrollbar.getMaximum())) {
      k = 1;
    }
    setScrollBar(this.sorting == 0, k);
    repaint();
  }
  
  public synchronized ColorListItem getItem(int paramInt)
  {
    return (ColorListItem)this.items.elementAt(paramInt);
  }
  
  public synchronized ColorListItem getItem(String paramString)
  {
    int i = this.items.size();
    if (i == 0) {
      return null;
    }
    for (int j = 0; j < i; j++)
    {
      ColorListItem localColorListItem = getItem(j);
      if (paramString.equals(localColorListItem.getString())) {
        return localColorListItem;
      }
    }
    return null;
  }
  
  public synchronized ColorListItem getSelectedItem()
  {
    ColorListItem[] arrayOfColorListItem = getSelectedItems();
    if (arrayOfColorListItem == null) {
      return null;
    }
    if (arrayOfColorListItem.length != 1) {
      return null;
    }
    return arrayOfColorListItem[0];
  }
  
  public synchronized ColorListItem[] getSelectedItems()
  {
    return getItems(true);
  }
  
  public synchronized ColorListItem[] getAllItems()
  {
    return getItems(false);
  }
  
  public synchronized ColorListItem removeItem(String paramString)
  {
    ColorListItem localColorListItem = getItem(paramString);
    if (localColorListItem == null) {
      return null;
    }
    return removeItem(localColorListItem);
  }
  
  public synchronized ColorListItem removeItem(ColorListItem paramColorListItem)
  {
    int i = this.items.indexOf(paramColorListItem);
    if (i >= 0)
    {
      this.items.removeElementAt(i);
      int j = i < this.scrollbar.getValue() ? -1 : 0;
      setScrollBar(false, j);
      repaint();
    }
    return paramColorListItem;
  }
  
  public synchronized void removeAllItems()
  {
    if (this.items.size() == 0) {
      return;
    }
    this.items.removeAllElements();
    setScrollBar(false, 0);
    repaint();
  }
  
  public synchronized void removeAllSelections()
  {
    int i = this.items.size();
    for (int j = 0; j < i; j++) {
      getItem(j).setSelected(false);
    }
    repaint();
  }
  
  public int[] getLastClickedMouseXY()
  {
    int[] arrayOfInt = { this.lcmx, this.lcmy };
    return arrayOfInt;
  }
  
  private synchronized void setScrollBar(boolean paramBoolean, int paramInt)
  {
    int i = this.items.size();
    int j = this.lvis;
    if (this.title != null) {
      j--;
    }
    if (i <= j)
    {
      if (this.sbadded)
      {
        this.scrollbar.removeAdjustmentListener(this);
        remove(this.scrollbar);
        this.sbadded = false;
      }
      return;
    }
    int k;
    if (!this.sbadded)
    {
      add(this.scrollbar);
      this.scrollbar.addAdjustmentListener(this);
      this.sbadded = true;
      k = 0;
    }
    else
    {
      k = this.scrollbar.getValue();
      if ((k > i) || (paramBoolean)) {
        k = i;
      }
    }
    k += paramInt;
    this.scrollbar.setValues(k, j, 0, i);
  }
  
  private ColorListItem getItemByMouseY(int paramInt)
  {
    int i = this.ditems.size();
    if (i == 0) {
      return null;
    }
    for (int j = 0; j < i; j++)
    {
      ColorListDItem localColorListDItem = (ColorListDItem)this.ditems.elementAt(j);
      if (localColorListDItem.isMouseOverThisItem(paramInt)) {
        return localColorListDItem.getItem();
      }
    }
    return null;
  }
  
  private synchronized ColorListItem[] getItems(boolean paramBoolean)
  {
    int i = paramBoolean ? getSelectedItemCount() : getItemCount();
    if (i == 0) {
      return null;
    }
    ColorListItem[] arrayOfColorListItem = new ColorListItem[i];
    int j = this.items.size();
    int k = 0;
    for (int m = 0; m < j; m++)
    {
      ColorListItem localColorListItem = getItem(m);
      if ((!paramBoolean) || (localColorListItem.isSelected()))
      {
        arrayOfColorListItem[k] = localColorListItem;
        k++;
      }
    }
    return arrayOfColorListItem;
  }
  
  private synchronized int getSortIndex(ColorListItem paramColorListItem)
  {
    int i = this.items.size();
    if (i == 0) {
      return 0;
    }
    return getSortIndex(paramColorListItem, i);
  }
  
  private int getSortIndex(ColorListItem paramColorListItem, int paramInt)
  {
    if ((this.sorting == 1) || (this.sorting == 2))
    {
      String str1 = getAlphaCompareString(paramColorListItem.getString());
      for (k = 0; k < paramInt; k++)
      {
        String str2 = getAlphaCompareString(getItem(k).getString());
        if (this.sorting == 1)
        {
          if (str1.compareTo(str2) < 0) {
            return k;
          }
        }
        else if (str1.compareTo(str2) > 0) {
          return k;
        }
      }
      return paramInt;
    }
    int i = paramColorListItem.getValue();
    for (int k = 0; k < paramInt; k++)
    {
      int j = getItem(k).getValue();
      if (this.sorting == 3)
      {
        if (i < j) {
          return k;
        }
      }
      else if (i > j) {
        return k;
      }
    }
    return paramInt;
  }
  
  private String getAlphaCompareString(String paramString)
  {
    paramString = paramString.toLowerCase().trim();
    int i = paramString.length();
    StringBuffer localStringBuffer = new StringBuffer(i);
    for (int j = 0; j < i; j++)
    {
      char c = paramString.charAt(j);
      if (((c >= 'a') && (c <= 'z')) || ((c >= '0') && (c <= '9')) || (c == 'ä') || (c == 'ö') || (c == 'å')) {
        localStringBuffer.append(c);
      }
      if (c == '~') {
        localStringBuffer.append('ÿ');
      }
    }
    return localStringBuffer.toString().trim();
  }
  
  private synchronized void processItemEvent(ColorListItem paramColorListItem, int paramInt1, int paramInt2)
  {
    if (this.listeners.size() == 0) {
      return;
    }
    ItemEvent localItemEvent = new ItemEvent(this, paramInt1, paramColorListItem, paramInt2);
    Enumeration localEnumeration = this.listeners.elements();
    while (localEnumeration.hasMoreElements()) {
      ((ItemListener)localEnumeration.nextElement()).itemStateChanged(localItemEvent);
    }
  }
}
