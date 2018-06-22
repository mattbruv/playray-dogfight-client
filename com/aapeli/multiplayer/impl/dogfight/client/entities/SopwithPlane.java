package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.common.resources.ImageLoader;
import java.awt.image.BufferedImage;

public class SopwithPlane
  extends Plane
{
  private static BufferedImage[] images;
  
  public static void loadImages()
  {
    images = new BufferedImage[3];
    images[0] = ImageLoader.getRotatedImage("pictures/plane9.gif");
    images[1] = ImageLoader.getRotatedImage("pictures/plane9_flip1.gif");
    images[2] = ImageLoader.getRotatedImage("pictures/plane9_flip2.gif");
  }
  
  public SopwithPlane()
  {
    super(images);
  }
}
