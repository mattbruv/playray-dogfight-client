package com.aapeli.multiplayer.client.session.chat.create;

import com.aapeli.multiplayer.client.common.GUIColors;
import com.aapeli.multiplayer.client.resources.Localization;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class LabelledTextField
  extends JPanel
  implements Option
{
  private JLabel label;
  private JTextField textField;
  private String textFieldDefaultText;
  private String key;
  private JLabel disabledComponent;
  
  public LabelledTextField(String paramString1, String paramString2)
  {
    this(paramString1, paramString2, 100, 50);
  }
  
  public LabelledTextField(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    this.key = paramString1;
    this.textFieldDefaultText = paramString2;
    setLayout(new BoxLayout(this, 0));
    setOpaque(false);
    this.label = new JLabel();
    this.label.setText(Localization.getInstance().localize(paramString1));
    this.label.setBorder(new EmptyBorder(new Insets(1, 5, 1, 5)));
    add(this.label);
    this.textField = new LimitedTextField(paramInt2);
    this.textField.setMaximumSize(new Dimension(paramInt1, 100));
    this.textField.setMinimumSize(new Dimension(paramInt1, getMinimumSize().height));
    this.textField.setText(paramString2);
    add(this.textField);
    this.disabledComponent = new JLabel();
    this.disabledComponent.setMaximumSize(new Dimension(paramInt1, 100));
    this.disabledComponent.setBackground(GUIColors.getInstance().get(GUIColors.CHALLENGE_SETTING_BG));
    this.disabledComponent.setForeground(GUIColors.getInstance().get(GUIColors.CHALLENGE_SETTING_FG));
    this.disabledComponent.setBorder(new LineBorder(GUIColors.getInstance().get(GUIColors.CHALLENGE_SETTING_BORDER), 1));
    this.disabledComponent.setOpaque(true);
  }
  
  public void setTextFieldMinimumWidth(int paramInt)
  {
    this.textField.setMinimumSize(new Dimension(paramInt, 0));
  }
  
  public String getValue()
  {
    return this.textField.getText();
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public JTextField getTextField()
  {
    return this.textField;
  }
  
  public void reset()
  {
    this.textField.setText(this.textFieldDefaultText);
  }
  
  public void setValue(String paramString)
  {
    this.textField.setText(paramString);
  }
  
  public void setEditable(boolean paramBoolean)
  {
    this.textField.setEditable(paramBoolean);
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      remove(this.disabledComponent);
      add(this.textField);
      validate();
    }
    else
    {
      remove(this.textField);
      this.disabledComponent.setText(getValue());
      add(this.disabledComponent);
      validate();
    }
  }
}
