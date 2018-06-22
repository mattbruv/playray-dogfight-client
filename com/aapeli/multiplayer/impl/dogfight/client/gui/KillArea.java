package com.aapeli.multiplayer.impl.dogfight.client.gui;

import com.aapeli.multiplayer.client.session.game.gui.AbstractChatEditorPane;
import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.common.network.GameProtocol;
import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.TeamColors;
import com.aapeli.multiplayer.impl.dogfight.common.ChatMessageProtocol;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

public class KillArea
  extends AbstractChatEditorPane
  implements GameProtocol, ChatMessageProtocol, TeamColors
{
  private static BufferedImage plane;
  private static BufferedImage man;
  private GameToolkit toolkit;
  
  public static void loadImages()
  {
    plane = ImageLoader.getImage("pictures/plane_icon.gif");
    man = ImageLoader.getImage("pictures/man_icon.gif");
  }
  
  public KillArea(GameToolkit paramGameToolkit)
  {
    this.toolkit = paramGameToolkit;
    setFont(new Font("arial", 1, 13));
    setEditorKit(new StyledEditorKit());
    addStylesToDocument((StyledDocument)getDocument());
    this.removeDelay = 8000L;
  }
  
  private void addStylesToDocument(StyledDocument paramStyledDocument)
  {
    Style localStyle1 = StyleContext.getDefaultStyleContext().getStyle("default");
    Style localStyle2 = paramStyledDocument.addStyle("regular", localStyle1);
    StyleConstants.setFontFamily(localStyle2, getFont().getFontName());
    StyleConstants.setFontSize(localStyle2, getFont().getSize());
    StyleConstants.setBold(localStyle2, getFont().isBold());
    StyleConstants.setAlignment(localStyle2, 2);
    Style localStyle3 = paramStyledDocument.addStyle("opponent", localStyle2);
    StyleConstants.setForeground(localStyle3, OPPONENT_COLOR_FG);
    localStyle3 = paramStyledDocument.addStyle("unchosen", localStyle2);
    StyleConstants.setForeground(localStyle3, UNCHOSEN_COLOR_FG);
    localStyle3 = paramStyledDocument.addStyle("own", localStyle2);
    StyleConstants.setForeground(localStyle3, OWN_COLOR_FG);
    localStyle3 = paramStyledDocument.addStyle("plane", localStyle2);
    StyleConstants.setIcon(localStyle3, new ImageIcon(plane));
    localStyle3 = paramStyledDocument.addStyle("man", localStyle2);
    StyleConstants.setIcon(localStyle3, new ImageIcon(man));
  }
  
  public void run()
  {
    super.run();
    if (getPreferredSize().height != getSize().height)
    {
      setSize(getMaximumSize().width, getPreferredSize().height);
      revalidate();
    }
  }
  
  public void parseChatMessage(int paramInt, String paramString1, String paramString2)
  {
    if (paramInt == 4)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString2, "\t");
      int i = Integer.parseInt(localStringTokenizer.nextToken());
      int j = ((DogfightToolkit)this.toolkit.getAttachment()).getOwnTeam();
      String str1;
      String str2;
      if (j == -1)
      {
        str1 = "unchosen";
        str2 = "unchosen";
      }
      else if (j == i)
      {
        str1 = "own";
        str2 = "opponent";
      }
      else
      {
        str1 = "opponent";
        str2 = "own";
      }
      int k = Integer.parseInt(localStringTokenizer.nextToken());
      String str3;
      String str4;
      switch (k)
      {
      case 1: 
        insertText(localStringTokenizer.nextToken() + " ", str1);
        insertLine(" ", "plane");
        break;
      case 2: 
        str3 = localStringTokenizer.nextToken();
        str4 = localStringTokenizer.nextToken();
        insertText(str3 + " ", str1);
        insertText(" ", "plane");
        insertLine(" " + str4 + " ", str1);
        break;
      case 3: 
        str3 = localStringTokenizer.nextToken();
        str4 = localStringTokenizer.nextToken();
        insertText(str3 + " ", str1);
        insertText(" ", "plane");
        insertLine(" " + str4 + " ", str2);
        break;
      case 4: 
        insertText(localStringTokenizer.nextToken() + " ", str1);
        insertLine(" ", "man");
        break;
      case 5: 
        str3 = localStringTokenizer.nextToken();
        str4 = localStringTokenizer.nextToken();
        insertText(str3 + " ", str1);
        insertText(" ", "man");
        insertLine(" " + str4 + " ", str1);
        break;
      case 6: 
        str3 = localStringTokenizer.nextToken();
        str4 = localStringTokenizer.nextToken();
        insertText(str3 + " ", str1);
        insertText(" ", "man");
        insertLine(" " + str4 + " ", str2);
      }
    }
  }
}
