package com.aapeli.multiplayer.client.session.chat.view;

import com.aapeli.multiplayer.client.common.GUIColors;
import com.aapeli.multiplayer.client.session.chat.create.OptionList;
import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CreatePane
  extends JScrollPane
{
  private OptionList currentOptionList;
  private String currentGame;
  private Map settingsMap = new HashMap(1);
  private JPanel createPanel;
  
  public CreatePane()
  {
    super(22, 30);
    initComponents();
  }
  
  protected void initComponents()
  {
    this.createPanel = new JPanel();
    this.createPanel.setBackground(GUIColors.getInstance().get(GUIColors.AREA_BG));
    this.createPanel.setLayout(new BorderLayout());
    getViewport().add(this.createPanel);
    getViewport().validate();
    setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.SHARP_BORDER)));
  }
  
  public void initOptions()
  {
    if ((this.currentOptionList != null) && (this.settingsMap.size() > 0))
    {
      this.currentOptionList.showCreateName();
      this.currentOptionList.reset();
      this.currentOptionList.setParameters(this.settingsMap);
      this.createPanel.add(this.currentOptionList, "West");
      this.currentOptionList.setBorder(new EmptyBorder(10, 10, 0, 0));
      validate();
      repaint();
    }
  }
  
  public Map getChosenOptions()
  {
    Map localMap = this.currentOptionList.getResults();
    localMap.put("param_type", this.currentGame);
    return localMap;
  }
  
  public void setOptions(Map paramMap)
  {
    if (paramMap.size() > 0)
    {
      Map.Entry localEntry = (Map.Entry)paramMap.entrySet().iterator().next();
      this.currentGame = ((String)localEntry.getKey());
      this.currentOptionList = ((OptionList)localEntry.getValue());
    }
  }
  
  public void setEnabledAll(boolean paramBoolean)
  {
    this.currentOptionList.setEnabledAll(paramBoolean);
    repaint();
  }
  
  public void setSettingsMap(Map paramMap)
  {
    this.settingsMap = paramMap;
    initOptions();
  }
  
  public String getProduct()
  {
    return this.currentOptionList.getProduct();
  }
}
