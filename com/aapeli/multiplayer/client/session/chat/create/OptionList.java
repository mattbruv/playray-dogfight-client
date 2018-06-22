package com.aapeli.multiplayer.client.session.chat.create;

import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.Box.Filler;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class OptionList
  extends JPanel
{
  private static final int MIN_WIDTH = 250;
  private DescriptedComponent nameTextField;
  private DescriptedComponent nameComboBox;
  private Component nameGap;
  private boolean nameEnabled = false;
  private Map manageMap;
  private Map idMap;
  private boolean manageShown = false;
  private Option productOption;
  
  public OptionList(List paramList, boolean paramBoolean, Option paramOption)
  {
    this.productOption = paramOption;
    Dimension localDimension = new Dimension(250, 3);
    this.nameEnabled = paramBoolean;
    if (paramBoolean)
    {
      this.nameTextField = new DescriptedComponent(new LabelledTextField("param_name", "", 250, 25), "param_name_description", true);
      localObject = new LabelledComboBox("param_name", new String[] { "" }, false, 250);
      this.nameComboBox = new DescriptedComponent((Component)localObject, "param_choose_room_description", true);
      this.nameGap = new Box.Filler(localDimension, localDimension, localDimension);
      add(this.nameTextField, 0);
    }
    setOpaque(false);
    setLayout(new BoxLayout(this, 1));
    add(new Box.Filler(localDimension, localDimension, localDimension));
    Object localObject = paramList.iterator();
    while (((Iterator)localObject).hasNext())
    {
      Component localComponent = (Component)((Iterator)localObject).next();
      add(localComponent);
      add(new Box.Filler(localDimension, localDimension, localDimension));
    }
    setSize(getPreferredSize());
  }
  
  public void addComponent(Component paramComponent, int paramInt)
  {
    add(paramComponent, paramInt);
    setSize(getPreferredSize());
  }
  
  public void showCreateName()
  {
    if (isNameEnabled())
    {
      this.manageShown = false;
      remove(this.nameComboBox);
      remove(this.nameGap);
    }
  }
  
  public void showManageName()
  {
    if (isNameEnabled())
    {
      add(this.nameComboBox, 0);
      add(this.nameGap, 1);
      this.manageShown = true;
    }
  }
  
  public Map getResults()
  {
    HashMap localHashMap = new HashMap(10);
    Component[] arrayOfComponent = getComponents();
    for (int i = 0; i < arrayOfComponent.length; i++) {
      if ((arrayOfComponent[i] instanceof Option))
      {
        Option localOption = (Option)arrayOfComponent[i];
        localHashMap.put(localOption.getKey(), localOption.getValue());
      }
    }
    return localHashMap;
  }
  
  public void setParameters(Map paramMap)
  {
    if (paramMap != null)
    {
      Component[] arrayOfComponent = getComponents();
      for (int i = 0; i < arrayOfComponent.length; i++) {
        if ((arrayOfComponent[i] instanceof Option))
        {
          Option localOption = (Option)arrayOfComponent[i];
          String str = (String)paramMap.get(localOption.getKey());
          if (str != null) {
            localOption.setValue(str);
          }
        }
      }
    }
    validate();
  }
  
  public void reset()
  {
    Component[] arrayOfComponent = getComponents();
    for (int i = 0; i < arrayOfComponent.length; i++) {
      if ((arrayOfComponent[i] instanceof Option)) {
        ((Option)arrayOfComponent[i]).reset();
      }
    }
    setEnabledAll(true);
  }
  
  public LabelledTextField getCreateNameOption()
  {
    return (LabelledTextField)this.nameTextField.getComponent();
  }
  
  public LabelledComboBox getManageNameComponent()
  {
    return (LabelledComboBox)this.nameComboBox.getComponent();
  }
  
  public boolean isNameEnabled()
  {
    return this.nameEnabled;
  }
  
  public void setEnabledAll(boolean paramBoolean)
  {
    Component[] arrayOfComponent = getComponents();
    for (int i = 0; i < arrayOfComponent.length; i++) {
      arrayOfComponent[i].setEnabled(paramBoolean);
    }
  }
  
  public String getProduct()
  {
    return this.productOption.getValue();
  }
}
