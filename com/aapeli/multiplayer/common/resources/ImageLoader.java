package com.aapeli.multiplayer.common.resources;

import com.aapeli.multiplayer.util.ImageUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImageLoader
{
  private static URL rootURL;
  
  public static void setRootURL(URL paramURL)
  {
    rootURL = paramURL;
  }
  
  public static BufferedImage getImage(String paramString)
  {
    BufferedImage localBufferedImage = loadImage(paramString);
    if (localBufferedImage == null) {
      return null;
    }
    return ImageUtils.createCopy(localBufferedImage);
  }
  
  private static BufferedImage loadImage(String paramString)
  {
    try
    {
      BufferedImage localBufferedImage = ImageIO.read(new URL(rootURL, paramString));
      return localBufferedImage;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }
  
  private static BufferedImage loadImageFromFile(String paramString)
  {
    try
    {
      BufferedImage localBufferedImage = ImageIO.read(new File(paramString));
      return localBufferedImage;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }
  
  public static BufferedImage getRotatedImage(String paramString)
  {
    BufferedImage localBufferedImage = loadImage(paramString);
    if (localBufferedImage == null) {
      return null;
    }
    return ImageUtils.createCenteredInMaxRotateArea(localBufferedImage);
  }
  
  public static BufferedImage getRotatedImageFromFile(String paramString)
  {
    BufferedImage localBufferedImage = loadImageFromFile(paramString);
    if (localBufferedImage == null) {
      return null;
    }
    return ImageUtils.createCenteredInMaxRotateArea(localBufferedImage);
  }
  
  public static BufferedImage getImageFromFile(String paramString)
  {
    BufferedImage localBufferedImage = loadImageFromFile(paramString);
    if (localBufferedImage == null) {
      return null;
    }
    return ImageUtils.createCopy(localBufferedImage);
  }
}
