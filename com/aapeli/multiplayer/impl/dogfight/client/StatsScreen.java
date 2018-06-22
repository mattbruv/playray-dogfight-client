package com.aapeli.multiplayer.impl.dogfight.client;

import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.client.resources.SoundSystem;
import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.util.console.Console;
import com.aapeli.multiplayer.util.console.ConsoleValue;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;

public class StatsScreen
  extends JLayeredPane
  implements ActionListener, TeamColors
{
  private GameToolkit toolkit;
  private Radar radar;
  private StatsSurface statsSurface;
  private JButton exitButton;
  private JCheckBox soundCheckBox;
  private JCheckBox gfxCheckBox;
  
  public StatsScreen(GameToolkit paramGameToolkit)
  {
    setLayout(null);
    setBackground(Color.BLACK);
    setFocusable(false);
    this.toolkit = paramGameToolkit;
    this.statsSurface = new StatsSurface(paramGameToolkit);
    this.statsSurface.setBounds(0, 0, 740, 200);
    add(this.statsSurface, new Integer(1));
    this.radar = new Radar(paramGameToolkit);
    this.radar.setBounds(425, 26, 208, 104);
    add(this.radar, new Integer(2));
    this.exitButton = new JButton();
    this.exitButton.setBackground(new Color(255, 153, 153));
    this.exitButton.setFont(new Font("Dialog", 1, 14));
    this.exitButton.setText(Localization.getInstance().localize("Exit"));
    this.exitButton.setBorder(new LineBorder(new Color(153, 0, 51), 2, true));
    add(this.exitButton, new Integer(2));
    this.exitButton.setBounds(653, 120, 70, 27);
    this.exitButton.addActionListener(this);
    this.exitButton.setFocusable(false);
    this.soundCheckBox = new JCheckBox();
    this.soundCheckBox.setOpaque(false);
    this.soundCheckBox.setFont(new Font("Dialog", 1, 14));
    this.soundCheckBox.setText(Localization.getInstance().localize("Sounds"));
    this.soundCheckBox.setForeground(Color.white);
    this.soundCheckBox.setSelected(SoundSystem.getInstance().isSounds());
    add(this.soundCheckBox, new Integer(2));
    this.soundCheckBox.setLocation(650, 80);
    this.soundCheckBox.setSize(this.soundCheckBox.getPreferredSize());
    this.soundCheckBox.addActionListener(this);
    this.soundCheckBox.setFocusable(false);
    this.gfxCheckBox = new JCheckBox();
    this.gfxCheckBox.setOpaque(false);
    this.gfxCheckBox.setFont(new Font("Dialog", 1, 14));
    this.gfxCheckBox.setText(Localization.getInstance().localize("Gfx"));
    this.gfxCheckBox.setForeground(Color.white);
    ConsoleValue localConsoleValue = paramGameToolkit.getConsole().get("draw_quality");
    if ((localConsoleValue.isInt()) && (localConsoleValue.getInt() == 1)) {
      this.gfxCheckBox.setSelected(false);
    } else {
      this.gfxCheckBox.setSelected(true);
    }
    add(this.gfxCheckBox, new Integer(2));
    this.gfxCheckBox.setLocation(650, 40);
    this.gfxCheckBox.setSize(this.gfxCheckBox.getPreferredSize());
    this.gfxCheckBox.addActionListener(this);
    this.gfxCheckBox.setFocusable(false);
  }
  
  public void refresh()
  {
    if (this.statsSurface.checkUpdates()) {
      this.statsSurface.repaint();
    } else {
      this.radar.repaint();
    }
  }
  
  public void start()
  {
    this.statsSurface.setForceDraw(true);
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource().equals(this.exitButton)) {
      this.toolkit.quit();
    } else if (paramActionEvent.getSource().equals(this.soundCheckBox))
    {
      if (this.soundCheckBox.isSelected()) {
        SoundSystem.getInstance().setSounds(true);
      } else {
        SoundSystem.getInstance().setSounds(false);
      }
    }
    else if (paramActionEvent.getSource().equals(this.gfxCheckBox)) {
      if (this.gfxCheckBox.isSelected())
      {
        this.toolkit.getConsole().put("draw_quality", "2");
        this.toolkit.getConsole().put("draw_sleep", "2");
      }
      else
      {
        this.toolkit.getConsole().put("draw_quality", "1");
        this.toolkit.getConsole().put("draw_sleep", "5");
      }
    }
  }
}
