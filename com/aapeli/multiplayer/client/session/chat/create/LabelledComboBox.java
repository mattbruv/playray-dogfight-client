package com.aapeli.multiplayer.client.session.chat.create;

import com.aapeli.multiplayer.client.common.GUIColors;
import com.aapeli.multiplayer.client.resources.Localization;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ItemListener;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class LabelledComboBox
  extends JPanel
  implements Option
{
  private JLabel label;
  private JLabel disabledComponent;
  private JComboBox comboBox;
  private int defaultSelection = 0;
  private String key;
  private String[] values;
  private boolean translated;
  private int maxWidth;
  private int maxHeight;
  
  public LabelledComboBox(String paramString, String[] paramArrayOfString)
  {
    this(paramString, paramArrayOfString, true);
  }
  
  public LabelledComboBox(String paramString, String[] paramArrayOfString, boolean paramBoolean)
  {
    this(paramString, paramArrayOfString, paramBoolean, 100);
  }
  
  public LabelledComboBox(String paramString, String[] paramArrayOfString, boolean paramBoolean, int paramInt)
  {
    this(paramString, paramArrayOfString, paramBoolean, paramInt, 100);
  }
  
  public LabelledComboBox(String paramString, String[] paramArrayOfString, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    this.maxHeight = paramInt2;
    this.maxWidth = paramInt1;
    this.translated = paramBoolean;
    this.key = paramString;
    setLayout(new BoxLayout(this, 0));
    setOpaque(false);
    this.label = new JLabel();
    this.label.setText(Localization.getInstance().localize(paramString));
    this.label.setBorder(new EmptyBorder(new Insets(1, 5, 1, 5)));
    add(this.label);
    this.comboBox = new JComboBox();
    this.comboBox.setModel(new DefaultComboBoxModel());
    this.comboBox.setMaximumSize(new Dimension(paramInt1, paramInt2));
    this.disabledComponent = new JLabel();
    this.disabledComponent.setMaximumSize(new Dimension(paramInt1, paramInt2));
    this.disabledComponent.setBackground(GUIColors.getInstance().get(GUIColors.CHALLENGE_SETTING_BG));
    this.disabledComponent.setForeground(GUIColors.getInstance().get(GUIColors.CHALLENGE_SETTING_FG));
    this.disabledComponent.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.CHALLENGE_SETTING_BORDER), 1));
    this.disabledComponent.setOpaque(true);
    add(this.comboBox);
    setValues(paramArrayOfString);
  }
  
  public void setComboBoxMaxHeight(int paramInt)
  {
    this.maxHeight = paramInt;
    this.comboBox.setMaximumSize(new Dimension(this.maxWidth, paramInt));
    this.disabledComponent.setMaximumSize(new Dimension(this.maxWidth, paramInt));
  }
  
  public void addItemListener(ItemListener paramItemListener)
  {
    this.comboBox.addItemListener(paramItemListener);
  }
  
  public void removeItemListener(ItemListener paramItemListener)
  {
    this.comboBox.removeItemListener(paramItemListener);
  }
  
  public void setValues(String[] paramArrayOfString)
  {
    String[] arrayOfString = (String[])paramArrayOfString.clone();
    if (this.translated) {
      for (int i = 0; i < arrayOfString.length; i++) {
        arrayOfString[i] = Localization.getInstance().localize(arrayOfString[i]);
      }
    }
    setValues(paramArrayOfString, arrayOfString);
  }
  
  public void setValues(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    this.values = paramArrayOfString1;
    this.comboBox.setModel(new DefaultComboBoxModel(paramArrayOfString2));
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public String getValue()
  {
    int i = this.comboBox.getSelectedIndex();
    if (i != -1) {
      return this.values[this.comboBox.getSelectedIndex()];
    }
    return "";
  }
  
  private String getUserValue()
  {
    Object localObject = this.comboBox.getSelectedItem();
    if ((localObject instanceof String)) {
      return (String)localObject;
    }
    return null;
  }
  
  public JComboBox getComboBox()
  {
    return this.comboBox;
  }
  
  public void reset()
  {
    this.comboBox.getModel().setSelectedItem(this.comboBox.getModel().getElementAt(this.defaultSelection));
  }
  
  public void setValue(String paramString)
  {
    if (this.translated) {
      paramString = Localization.getInstance().localize(paramString);
    }
    this.comboBox.getModel().setSelectedItem(paramString);
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      remove(this.disabledComponent);
      add(this.comboBox);
      validate();
    }
    else
    {
      remove(this.comboBox);
      this.disabledComponent.setText(getUserValue());
      add(this.disabledComponent);
      validate();
    }
  }
}
