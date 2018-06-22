package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.common.resources.ImageLoader;
import java.awt.image.BufferedImage;

public class AlbatrosPlane
  extends Plane
{
  private static BufferedImage[] images;
  
  public static void loadImages()
  {
    images = new BufferedImage[4];
    images[0] = ImageLoader.getRotatedImage("pictures/plane4.gif");
    images[1] = ImageLoader.getRotatedImage("pictures/plane4_flip1.gif");
    images[2] = ImageLoader.getRotatedImage("pictures/plane4_flip2.gif");
  }
  
  public AlbatrosPlane()
  {
    super(images);
  }
}
