package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.common.resources.ImageLoader;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.Drawable;
import com.aapeli.multiplayer.util.ImageUtils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.IOException;

public class Water
  extends DrawableEntity
  implements Drawable
{
  private int x;
  private int y;
  private int subType;
  private int width;
  public static final Color COLOR = new Color(3051728);
  public static final Color DESERT_COLOR = new Color(2344139);
  private static BufferedImage[][] image;
  private static final long WAVE_PHASE_TIME = 200L;
  
  public static void loadImages()
  {
    image = new BufferedImage[2][7];
    for (int i = 0; i < image[0].length; i++)
    {
      image[0][i] = ImageLoader.getImage("pictures/wave-l_" + (i + 1) + ".gif");
      image[1][i] = ImageUtils.createHorizontallyFlipped(image[0][i]);
    }
  }
  
  protected void read(DataInput paramDataInput, int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 2: 
      this.x = paramDataInput.readShort();
      this.y = paramDataInput.readShort();
      this.width = (paramDataInput.readShort() & 0xFFFF);
      this.subType = paramDataInput.readByte();
      break;
    }
  }
  
  public void draw(Graphics2D paramGraphics2D, int paramInt1, int paramInt2)
  {
    DogfightToolkit localDogfightToolkit = (DogfightToolkit)this.toolkit.getAttachment();
    int i = localDogfightToolkit.getWidth();
    int j = localDogfightToolkit.getHeight();
    Shape localShape = paramGraphics2D.getClip();
    int k = 0;
    if (this.x + paramInt1 > 0) {
      k = this.x + paramInt1;
    }
    int m = this.x + paramInt1 + this.width - k;
    if (k + m > i) {
      m = i - k;
    }
    if (m > 0)
    {
      paramGraphics2D.setClip(new Rectangle(k, this.y + paramInt2, m, j - this.y - paramInt2));
      if (this.subType < 2) {
        paramGraphics2D.setColor(COLOR);
      } else {
        paramGraphics2D.setColor(DESERT_COLOR);
      }
      paramGraphics2D.fillRect(k, this.y + paramInt2, m, j - this.y - paramInt2);
      int n = (int)(System.currentTimeMillis() / 200L % image[0].length);
      int i1 = image[0][0].getWidth();
      int i2 = (this.subType == 0) || (this.subType == 2) ? 0 : 1;
      for (int i3 = (k - (this.x + paramInt1)) / i1; this.x + paramInt1 + i3 * i1 + 1 < k + m; i3++) {
        paramGraphics2D.drawImage(image[i2][n], i3 * i1 + this.x + paramInt1, this.y + paramInt2, null);
      }
      paramGraphics2D.setClip(localShape);
    }
  }
  
  public Integer getDrawLayer()
  {
    return LAYER11;
  }
  
  protected int getX()
  {
    return this.x;
  }
  
  protected int getY()
  {
    return this.y;
  }
  
  protected int getWidth()
  {
    return this.width;
  }
  
  protected int getHeight()
  {
    return 500;
  }
}
