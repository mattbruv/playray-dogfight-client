package com.aapeli.multiplayer.client.session.chat.create;

import com.aapeli.multiplayer.client.resources.Localization;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box.Filler;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DescriptedComponent
  extends JPanel
  implements Option
{
  private static final int X_GAP = 10;
  private Component component;
  private JLabel descriptionLabel;
  
  public DescriptedComponent(Component paramComponent, String paramString)
  {
    this(paramComponent, paramString, true);
  }
  
  public DescriptedComponent(Component paramComponent, String paramString, boolean paramBoolean)
  {
    this.component = paramComponent;
    setLayout(new BoxLayout(this, 0));
    setBorder(null);
    setOpaque(false);
    setMaximumSize(new Dimension(2000, 100));
    add(paramComponent);
    Dimension localDimension = new Dimension(10, 3);
    add(new Box.Filler(localDimension, localDimension, localDimension));
    this.descriptionLabel = new JLabel();
    if (paramBoolean) {
      paramString = Localization.getInstance().localize(paramString);
    }
    this.descriptionLabel.setText(paramString);
    this.descriptionLabel.setBorder(null);
    add(this.descriptionLabel);
  }
  
  public Component getComponent()
  {
    return this.component;
  }
  
  public JLabel getDescriptionLabel()
  {
    return this.descriptionLabel;
  }
  
  public String getKey()
  {
    if ((this.component instanceof Option))
    {
      Option localOption = (Option)this.component;
      return localOption.getKey();
    }
    return null;
  }
  
  public String getValue()
  {
    if ((this.component instanceof Option))
    {
      Option localOption = (Option)this.component;
      return localOption.getValue();
    }
    return null;
  }
  
  public void reset()
  {
    if ((this.component instanceof Option))
    {
      Option localOption = (Option)this.component;
      localOption.reset();
    }
  }
  
  public void setValue(String paramString)
  {
    if ((this.component instanceof Option))
    {
      Option localOption = (Option)this.component;
      localOption.setValue(paramString);
    }
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    this.component.setEnabled(paramBoolean);
  }
}
