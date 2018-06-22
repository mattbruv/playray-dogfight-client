package com.aapeli.multiplayer.impl.dogfight.client;

import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.client.session.game.implInterface.Entity;
import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.impl.dogfight.client.entities.PlayerInfo;
import com.aapeli.multiplayer.util.TableSorter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class ScoreTable
  extends JPanel
  implements KeyListener, TeamColors, Runnable
{
  private boolean tabPressed;
  private GameToolkit toolkit;
  private DefaultTableModel model;
  private JTable table = new JTable()
  {
    public void paint(Graphics paramAnonymousGraphics)
    {
      paintComponent(paramAnonymousGraphics);
    }
  };
  private JTableHeader header = new JTableHeader(this.table.getTableHeader().getColumnModel())
  {
    public void paint(Graphics paramAnonymousGraphics)
    {
      paintComponent(paramAnonymousGraphics);
    }
  };
  private long lastUpdate;
  private static final long GAME_UPDATE_INTERVAL = 500L;
  private static final long INTERMISSION_UPDATE_INTERVAL = 100L;
  private long updateInterval = 500L;
  private Vector gameColumnIdentifiers;
  private Vector intermissionColumnIdentifiers;
  private List gameColumnSizes;
  private List intermissionColumnSizes;
  private TableSorter sorter;
  private static BufferedImage[] flags;
  private int mode = 1;
  public static final int GAME_STATS = 1;
  public static final int INTERMISSION_STATS = 2;
  private boolean forceDoLayout = true;
  private DogfightToolkit dogfightToolkit;
  private Font headerFont = new Font("arial", 1, 15);
  private Font roomNameFont = new Font("arial", 1, 15);
  private Font cellFont = new Font("arial", 0, 16);
  private JLabel roomName;
  private JPanel roomPanel;
  private int defaultWidth = 700;
  
  public static void loadImages()
  {
    flags = new BufferedImage[3];
    flags[0] = ImageLoader.getImage("pictures/randomflag_small.gif");
    flags[1] = ImageLoader.getImage("pictures/germanflag_small.gif");
    flags[2] = ImageLoader.getImage("pictures/raf_flag_small.gif");
  }
  
  public ScoreTable(GameToolkit paramGameToolkit)
  {
    this.header.setOpaque(true);
    this.header.setReorderingAllowed(false);
    this.header.setResizingAllowed(false);
    this.table.setTableHeader(this.header);
    this.toolkit = paramGameToolkit;
    this.dogfightToolkit = ((DogfightToolkit)paramGameToolkit.getAttachment());
    this.table.setOpaque(true);
    this.table.setDefaultRenderer(Object.class, new CustomCellRenderer());
    this.table.getTableHeader().setDefaultRenderer(new HeaderCellRenderer(Localization.getInstance().localize("Ready")));
    this.model = new DefaultTableModel();
    this.sorter = new TableSorter(this.model);
    this.sorter.setColumnComparator(Object.class, new CustomCellComparator(null));
    this.table.setModel(this.sorter);
    initColumnIdentifiers();
    this.roomName = new JLabel("-");
    this.roomName.setOpaque(true);
    this.roomName.setFont(this.roomNameFont);
    this.roomName.setBackground(Color.WHITE);
    this.roomName.setForeground(Color.BLACK);
    this.roomName.setBorder(new LineBorder(Color.black, 2));
    this.roomPanel = new JPanel();
    this.roomPanel.setOpaque(true);
    this.roomPanel.setBackground(new Color(5592405));
    this.roomPanel.setBorder(null);
    FlowLayout localFlowLayout = new FlowLayout(1);
    localFlowLayout.setHgap(0);
    this.roomPanel.setLayout(localFlowLayout);
    this.roomPanel.add(this.roomName);
    this.roomPanel.doLayout();
    setOpaque(true);
    setVisible(false);
    setLayout(null);
    adjustLayout();
    add(this.roomPanel);
    add(this.header);
    add(this.table);
    updateData();
  }
  
  private void adjustLayout()
  {
    int i = this.roomPanel.getPreferredSize().height;
    this.roomPanel.setSize(this.defaultWidth, i);
    this.roomPanel.setLocation(0, 0);
    int j = this.header.getPreferredSize().height;
    this.header.setSize(this.defaultWidth, j);
    this.header.setLocation(0, i);
    int k = this.table.getPreferredSize().height;
    this.table.setSize(this.defaultWidth, k);
    this.table.setLocation(0, i + j);
    setSize(this.defaultWidth, i + j + k);
  }
  
  private void initColumnIdentifiers()
  {
    this.gameColumnIdentifiers = new Vector(7);
    this.gameColumnIdentifiers.add(Localization.getInstance().localize("T"));
    this.gameColumnIdentifiers.add(Localization.getInstance().localize("Name"));
    this.gameColumnIdentifiers.add(Localization.getInstance().localize("Score"));
    this.gameColumnIdentifiers.add(Localization.getInstance().localize("Frags"));
    this.gameColumnIdentifiers.add(Localization.getInstance().localize("Deaths"));
    this.gameColumnIdentifiers.add(Localization.getInstance().localize("Accuracy"));
    this.gameColumnIdentifiers.add(Localization.getInstance().localize("Ping"));
    this.intermissionColumnIdentifiers = new Vector(7);
    this.intermissionColumnIdentifiers.add(Localization.getInstance().localize("T"));
    this.intermissionColumnIdentifiers.add(Localization.getInstance().localize("Name"));
    this.intermissionColumnIdentifiers.add(Localization.getInstance().localize("Score"));
    this.intermissionColumnIdentifiers.add(Localization.getInstance().localize("Frags"));
    this.intermissionColumnIdentifiers.add(Localization.getInstance().localize("Deaths"));
    this.intermissionColumnIdentifiers.add(Localization.getInstance().localize("Accuracy"));
    this.intermissionColumnIdentifiers.add(Localization.getInstance().localize("Ready"));
    FontMetrics localFontMetrics = getFontMetrics(this.headerFont);
    this.gameColumnSizes = calculateColumnSizes(this.gameColumnIdentifiers, this.defaultWidth, localFontMetrics);
    this.intermissionColumnSizes = calculateColumnSizes(this.intermissionColumnIdentifiers, this.defaultWidth, localFontMetrics);
    this.table.setRowHeight(localFontMetrics.getHeight() + 2);
  }
  
  private List calculateColumnSizes(Vector paramVector, int paramInt, FontMetrics paramFontMetrics)
  {
    int i = 0;
    int j = flags[0].getWidth();
    ArrayList localArrayList1 = new ArrayList(paramVector.size());
    ArrayList localArrayList2 = new ArrayList(paramVector.size());
    localArrayList2.add(new Integer(j));
    localArrayList1.add(new Integer(220));
    i += 220;
    int n;
    for (int k = 2; k < paramVector.size(); k++)
    {
      String str = (String)paramVector.elementAt(k);
      n = paramFontMetrics.stringWidth(str) + 5;
      if ((k == 2) && (n < 70)) {
        n = 70;
      }
      i += n;
      localArrayList1.add(new Integer(n));
    }
    k = j;
    for (int m = 0; m < localArrayList1.size() - 1; m++)
    {
      n = ((Integer)localArrayList1.get(m)).intValue();
      n = n * (paramInt - j) / i;
      k += n;
      localArrayList2.add(new Integer(n));
    }
    localArrayList2.add(new Integer(paramInt - k));
    return localArrayList2;
  }
  
  private void updateData()
  {
    Vector localVector1 = new Vector(10);
    DogfightToolkit localDogfightToolkit = (DogfightToolkit)this.toolkit.getAttachment();
    String str1 = localDogfightToolkit.getRoomName();
    int i = 0;
    if ((str1 != null) && (!str1.equals(this.roomName.getText())))
    {
      this.roomName.setText(localDogfightToolkit.getRoomName());
      i = 1;
    }
    int j = -1;
    if (localDogfightToolkit != null) {
      j = localDogfightToolkit.getOwnTeam();
    }
    int k = this.model.getRowCount();
    Object localObject1;
    Object localObject2;
    Object localObject3;
    Entity localEntity1;
    switch (this.mode)
    {
    case 1: 
      Iterator localIterator1 = this.toolkit.getEntities().values().iterator();
      while (localIterator1.hasNext())
      {
        localEntity2 = (Entity)localIterator1.next();
        if (localEntity2.getType() == 10)
        {
          PlayerInfo localPlayerInfo = (PlayerInfo)localEntity2;
          localObject1 = new Vector();
          if (localPlayerInfo.getTeam() == -1) {
            localObject2 = UNCHOSEN_COLOR_BG;
          } else if (localPlayerInfo.getTeam() == j) {
            localObject2 = OWN_COLOR_BG;
          } else {
            localObject2 = OPPONENT_COLOR_BG;
          }
          ((Vector)localObject1).add(new ImageCell("" + localPlayerInfo.getTeam(), flags[(localPlayerInfo.getTeam() + 1)], (Color)localObject2));
          ((Vector)localObject1).add(new ColoredCell("" + localPlayerInfo.getFullName(), (Color)localObject2, 2));
          ((Vector)localObject1).add(new ColoredCell("" + localPlayerInfo.getScore(), (Color)localObject2));
          ((Vector)localObject1).add(new ColoredCell("" + localPlayerInfo.getFrags(), (Color)localObject2));
          ((Vector)localObject1).add(new ColoredCell("" + localPlayerInfo.getDeaths(), (Color)localObject2));
          int i3 = localPlayerInfo.getAccuracy();
          if (i3 < 100) {
            localObject3 = i3 / 10 + "." + i3 % 10;
          } else {
            localObject3 = "" + i3 / 10;
          }
          ((Vector)localObject1).add(new ColoredCell((String)localObject3, (Color)localObject2));
          ((Vector)localObject1).add(new ColoredCell("" + localPlayerInfo.getPing(), (Color)localObject2));
          localVector1.add(localObject1);
        }
      }
      this.model.setDataVector(localVector1, this.gameColumnIdentifiers);
      this.sorter.setSortingStatus(0, -1);
      this.sorter.setSortingStatus(2, -1);
      localEntity1 = ((Integer)this.gameColumnSizes.get(0)).intValue();
      Entity localEntity2 = localEntity1;
      this.table.getColumnModel().getColumn(0).setMaxWidth(localEntity1);
      this.table.getColumnModel().getColumn(0).setMinWidth(localEntity1);
      for (int i1 = 1; i1 < this.gameColumnSizes.size(); i1++)
      {
        localEntity1 = ((Integer)this.gameColumnSizes.get(i1)).intValue();
        int m;
        localEntity2 += localEntity1;
        this.table.getColumnModel().getColumn(i1).setMinWidth(localEntity1);
        this.table.getColumnModel().getColumn(i1).setMaxWidth(localEntity1);
      }
      break;
    case 2: 
      k = this.model.getRowCount();
      Iterator localIterator2 = this.toolkit.getEntities().values().iterator();
      while (localIterator2.hasNext())
      {
        localObject1 = (Entity)localIterator2.next();
        if (((Entity)localObject1).getType() == 10)
        {
          localObject2 = (PlayerInfo)localObject1;
          Vector localVector2 = new Vector();
          if (((PlayerInfo)localObject2).getTeam() == -1) {
            localObject3 = UNCHOSEN_COLOR_BG;
          } else if (((PlayerInfo)localObject2).getTeam() == j) {
            localObject3 = OWN_COLOR_BG;
          } else {
            localObject3 = OPPONENT_COLOR_BG;
          }
          localVector2.add(new ImageCell("" + ((PlayerInfo)localObject2).getTeam(), flags[(localObject2.getTeam() + 1)], (Color)localObject3));
          localVector2.add(new ColoredCell("" + ((PlayerInfo)localObject2).getFullName(), (Color)localObject3, 2));
          localVector2.add(new ColoredCell("" + ((PlayerInfo)localObject2).getScore(), (Color)localObject3));
          localVector2.add(new ColoredCell("" + ((PlayerInfo)localObject2).getFrags(), (Color)localObject3));
          localVector2.add(new ColoredCell("" + ((PlayerInfo)localObject2).getDeaths(), (Color)localObject3));
          int i4 = ((PlayerInfo)localObject2).getAccuracy();
          String str2;
          if (i4 < 100) {
            str2 = i4 / 10 + "." + i4 % 10;
          } else {
            str2 = "" + i4 / 10;
          }
          localVector2.add(new ColoredCell(str2, (Color)localObject3));
          boolean bool = ((PlayerInfo)localObject2).isReady();
          localVector2.add(new ColoredCell(bool ? Localization.getInstance().localize("yes") : Localization.getInstance().localize("no"), bool ? Color.GREEN : Color.RED, Color.white));
          localVector1.add(localVector2);
        }
      }
      this.model.setDataVector(localVector1, this.intermissionColumnIdentifiers);
      this.sorter.setSortingStatus(0, -1);
      this.sorter.setSortingStatus(2, -1);
      localEntity1 = ((Integer)this.intermissionColumnSizes.get(0)).intValue();
      Entity localEntity3 = localEntity1;
      this.table.getColumnModel().getColumn(0).setMaxWidth(localEntity1);
      this.table.getColumnModel().getColumn(0).setMinWidth(localEntity1);
      for (int i2 = 1; i2 < this.intermissionColumnSizes.size(); i2++)
      {
        localEntity1 = ((Integer)this.intermissionColumnSizes.get(i2)).intValue();
        int n;
        localEntity3 += localEntity1;
        this.table.getColumnModel().getColumn(i2).setMinWidth(localEntity1);
        this.table.getColumnModel().getColumn(i2).setMaxWidth(localEntity1);
      }
    }
    if ((i != 0) || (this.forceDoLayout) || (k != localVector1.size()))
    {
      this.forceDoLayout = false;
      if (i != 0)
      {
        this.roomPanel.doLayout();
        i = 0;
      }
      adjustLayout();
    }
  }
  
  public void run()
  {
    if (this.dogfightToolkit.getMode() == 2)
    {
      if (getMode() == 1) {
        setMode(2);
      }
    }
    else if (getMode() == 2) {
      setMode(1);
    }
    long l = System.currentTimeMillis();
    if (this.lastUpdate + this.updateInterval < l)
    {
      updateData();
      this.lastUpdate = l;
    }
  }
  
  public void keyPressed(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getKeyCode() == 9)
    {
      this.tabPressed = true;
      setVisible(true);
    }
  }
  
  public void keyReleased(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getKeyCode() == 9)
    {
      if (getMode() == 1) {
        setVisible(false);
      }
      this.tabPressed = false;
    }
  }
  
  public void keyTyped(KeyEvent paramKeyEvent) {}
  
  public int getMode()
  {
    return this.mode;
  }
  
  public void setMode(int paramInt)
  {
    switch (paramInt)
    {
    case 1: 
      this.updateInterval = 500L;
      if (!this.tabPressed) {
        setVisible(false);
      }
      break;
    case 2: 
      this.updateInterval = 100L;
      setVisible(true);
    }
    this.forceDoLayout = true;
    this.mode = paramInt;
  }
  
  private class CustomCellComparator
    implements Comparator
  {
    private CustomCellComparator() {}
    
    public int compare(Object paramObject1, Object paramObject2)
    {
      if (((paramObject1 instanceof ScoreTable.TextCell)) && ((paramObject2 instanceof ScoreTable.TextCell)))
      {
        ScoreTable.TextCell localTextCell1 = (ScoreTable.TextCell)paramObject1;
        ScoreTable.TextCell localTextCell2 = (ScoreTable.TextCell)paramObject2;
        try
        {
          Integer localInteger1 = new Integer(localTextCell1.text);
          Integer localInteger2 = new Integer(localTextCell2.text);
          return localInteger1.compareTo(localInteger2);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          return localTextCell1.text.compareTo(localTextCell2.text);
        }
      }
      return 0;
    }
  }
  
  private class ImageCell
    extends ScoreTable.ColoredCell
  {
    Image image;
    
    ImageCell(String paramString, Image paramImage, Color paramColor)
    {
      super(paramString, paramColor);
      this.image = paramImage;
    }
  }
  
  private class ColoredCell
    extends ScoreTable.TextCell
  {
    Color color;
    Color color2;
    int alignment;
    
    ColoredCell(String paramString, Color paramColor)
    {
      this(paramString, paramColor, Color.black);
    }
    
    ColoredCell(String paramString, Color paramColor, int paramInt)
    {
      this(paramString, paramColor, Color.black, paramInt);
    }
    
    ColoredCell(String paramString, Color paramColor1, Color paramColor2)
    {
      this(paramString, paramColor1, paramColor2, 0);
    }
    
    ColoredCell(String paramString, Color paramColor1, Color paramColor2, int paramInt)
    {
      super(paramString);
      this.color = paramColor1;
      this.color2 = paramColor2;
      this.alignment = paramInt;
    }
  }
  
  private class TextCell
  {
    String text;
    
    TextCell(String paramString)
    {
      this.text = paramString;
    }
  }
  
  private class HeaderCellRenderer
    implements TableCellRenderer
  {
    private JLabel label;
    private String exception;
    
    public HeaderCellRenderer(String paramString)
    {
      this.exception = paramString;
      this.label = new JLabel();
      this.label.setHorizontalAlignment(0);
      this.label.setFont(ScoreTable.this.headerFont);
      this.label.setOpaque(true);
    }
    
    public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2)
    {
      if ((paramObject instanceof String))
      {
        String str = (String)paramObject;
        if (this.exception.equals(str))
        {
          this.label.setBackground(Color.white);
          this.label.setForeground(Color.black);
        }
        else
        {
          this.label.setBackground(Color.black);
          this.label.setForeground(Color.white);
        }
        this.label.setText((String)paramObject);
      }
      else
      {
        this.label.setBackground(Color.black);
        this.label.setForeground(Color.white);
        this.label.setText("");
      }
      return this.label;
    }
  }
  
  private class CustomCellRenderer
    implements TableCellRenderer
  {
    private JLabel label = new JLabel();
    
    public CustomCellRenderer()
    {
      this.label.setFont(ScoreTable.this.cellFont);
      this.label.setOpaque(true);
    }
    
    public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2)
    {
      this.label.setText("");
      this.label.setIcon(null);
      Object localObject;
      if ((paramObject instanceof ScoreTable.ImageCell))
      {
        this.label.setHorizontalAlignment(2);
        localObject = (ScoreTable.ImageCell)paramObject;
        this.label.setIcon(new ImageIcon(((ScoreTable.ImageCell)localObject).image));
        this.label.setBackground(((ScoreTable.ImageCell)localObject).color);
      }
      else if ((paramObject instanceof ScoreTable.ColoredCell))
      {
        localObject = (ScoreTable.ColoredCell)paramObject;
        this.label.setHorizontalAlignment(((ScoreTable.ColoredCell)localObject).alignment);
        if (((ScoreTable.ColoredCell)localObject).alignment == 2) {
          this.label.setText(" " + ((ScoreTable.ColoredCell)localObject).text);
        } else {
          this.label.setText(((ScoreTable.ColoredCell)localObject).text);
        }
        this.label.setBackground(((ScoreTable.ColoredCell)localObject).color);
        this.label.setForeground(((ScoreTable.ColoredCell)localObject).color2);
      }
      return this.label;
    }
  }
}
