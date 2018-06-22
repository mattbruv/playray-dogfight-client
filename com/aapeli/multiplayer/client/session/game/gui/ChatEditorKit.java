package com.aapeli.multiplayer.client.session.game.gui;

import com.aapeli.client.StringDraw;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.plaf.basic.BasicTextAreaUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainView;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

public class ChatEditorKit
  extends DefaultEditorKit
  implements ChatColors
{
  ChatDecorator decorator;
  private final AdvancedViewFactory defaultFactory = new AdvancedViewFactory();
  
  public ChatEditorKit(ChatDecorator paramChatDecorator)
  {
    this.decorator = paramChatDecorator;
  }
  
  public ViewFactory getViewFactory()
  {
    return this.defaultFactory;
  }
  
  class OutlinedView
    extends PlainView
  {
    OutlinedView(Element paramElement)
    {
      super();
    }
    
    protected int drawUnselectedText(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
      throws BadLocationException
    {
      Document localDocument = getDocument();
      String str = localDocument.getText(paramInt3, paramInt4 - paramInt3);
      Color localColor1 = Color.black;
      Color localColor2 = Color.WHITE;
      ChatDecorator.Style localStyle = ChatEditorKit.this.decorator.getStyle(str);
      if (localStyle != null)
      {
        localColor1 = localStyle.getBackground();
        localColor2 = localStyle.getForeground();
      }
      paramGraphics.setColor(localColor2);
      StringDraw.drawOutlinedString(paramGraphics, localColor1, str, paramInt1, paramInt2, -1);
      Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
      return paramInt1 + (int)paramGraphics.getFont().getStringBounds(str, localGraphics2D.getFontRenderContext()).getWidth();
    }
    
    protected int drawSelectedText(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
      throws BadLocationException
    {
      return drawUnselectedText(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  class AdvancedViewFactory
    extends BasicTextAreaUI
  {
    AdvancedViewFactory() {}
    
    public View create(Element paramElement)
    {
      return new ChatEditorKit.OutlinedView(ChatEditorKit.this, paramElement);
    }
  }
}
