package com.aapeli.multiplayer.client.resources;

import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine.Info;

public class SoundSystem
{
  private boolean sounds = true;
  private Map soundMap = Collections.synchronizedMap(new HashMap(15));
  private static URL rootURL;
  private static SoundSystem instance;
  private List loops = Collections.synchronizedList(new ArrayList(2));
  
  public void setSounds(boolean paramBoolean)
  {
    this.sounds = paramBoolean;
    Iterator localIterator;
    MultiClip localMultiClip;
    if (!paramBoolean) {
      synchronized (this.loops)
      {
        localIterator = this.loops.iterator();
        while (localIterator.hasNext())
        {
          localMultiClip = (MultiClip)localIterator.next();
          localMultiClip.loop(0);
        }
      }
    } else {
      synchronized (this.loops)
      {
        localIterator = this.loops.iterator();
        while (localIterator.hasNext())
        {
          localMultiClip = (MultiClip)localIterator.next();
          localMultiClip.loop(-1);
        }
      }
    }
  }
  
  public boolean isSounds()
  {
    return this.sounds;
  }
  
  public static void setRootURL(URL paramURL)
  {
    rootURL = paramURL;
  }
  
  public static SoundSystem getInstance()
  {
    if (instance == null) {
      instance = new SoundSystem();
    }
    return instance;
  }
  
  public long play(String paramString)
  {
    if (isSounds())
    {
      MultiClip localMultiClip = (MultiClip)this.soundMap.get(paramString);
      if (localMultiClip != null) {
        return localMultiClip.play();
      }
    }
    return 0L;
  }
  
  public void loop(String paramString, int paramInt)
  {
    MultiClip localMultiClip = (MultiClip)this.soundMap.get(paramString);
    if (localMultiClip != null)
    {
      if (paramInt == -1)
      {
        paramInt = -1;
        this.loops.add(localMultiClip);
      }
      else if (paramInt == 0)
      {
        this.loops.remove(localMultiClip);
      }
      if (isSounds()) {
        localMultiClip.loop(paramInt);
      }
    }
  }
  
  private Clip loadClipFromFile(String paramString)
  {
    if (this.sounds) {
      try
      {
        AudioInputStream localAudioInputStream = AudioSystem.getAudioInputStream(new URL(rootURL, paramString));
        AudioFormat localAudioFormat = localAudioInputStream.getFormat();
        if (localAudioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED)
        {
          System.out.println("Converting... " + paramString);
          localAudioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, localAudioFormat.getSampleRate(), localAudioFormat.getSampleSizeInBits() * 2, localAudioFormat.getChannels(), localAudioFormat.getFrameSize() * 2, localAudioFormat.getFrameRate(), true);
          localAudioInputStream = AudioSystem.getAudioInputStream(localAudioFormat, localAudioInputStream);
        }
        DataLine.Info localInfo = new DataLine.Info(Clip.class, localAudioInputStream.getFormat(), (int)localAudioInputStream.getFrameLength() * localAudioFormat.getFrameSize());
        Clip localClip = (Clip)AudioSystem.getLine(localInfo);
        localClip.open(localAudioInputStream);
        return localClip;
      }
      catch (Exception localException)
      {
        System.out.println("Could not load sound (" + paramString + ") " + localException);
        System.out.println("Sounds disabled.");
        this.sounds = false;
      }
    }
    return null;
  }
  
  public void loadClip(String paramString)
  {
    loadClip(paramString, 1);
  }
  
  public void loadClip(String paramString, int paramInt)
  {
    System.out.println("Load clip: " + paramString + " " + paramInt + " " + this.soundMap.containsKey(paramString));
    if (!this.soundMap.containsKey(paramString)) {
      this.soundMap.put(paramString, new MultiClip(paramString, paramInt));
    }
  }
  
  public static void destroy()
  {
    if (instance != null) {
      instance._destroy();
    }
    instance = null;
  }
  
  private void _destroy()
  {
    HashMap localHashMap;
    synchronized (this.soundMap)
    {
      localHashMap = new HashMap(this.soundMap);
    }
    this.soundMap.clear();
    ??? = localHashMap.values().iterator();
    while (((Iterator)???).hasNext())
    {
      MultiClip localMultiClip = (MultiClip)((Iterator)???).next();
      localMultiClip.destroy();
    }
  }
  
  private class MultiClip
  {
    List clipList;
    private String file;
    
    MultiClip(String paramString, int paramInt)
    {
      this.file = paramString;
      this.clipList = new ArrayList(paramInt);
      for (int i = 0; i < paramInt; i++)
      {
        Clip localClip = SoundSystem.this.loadClipFromFile(paramString);
        if (localClip != null) {
          this.clipList.add(localClip);
        }
      }
    }
    
    void destroy()
    {
      Iterator localIterator = this.clipList.iterator();
      if (localIterator.hasNext())
      {
        Clip localClip = (Clip)localIterator.next();
        localClip.close();
      }
      System.out.println("Closed line: " + this.file);
    }
    
    long play()
    {
      Object localObject = null;
      int i = 0;
      Iterator localIterator = this.clipList.iterator();
      while (localIterator.hasNext())
      {
        Clip localClip = (Clip)localIterator.next();
        if (!localClip.isActive())
        {
          localClip.setFramePosition(0);
          localClip.start();
          return localClip.getMicrosecondLength();
        }
        if (localClip.getFramePosition() > i)
        {
          i = localClip.getFramePosition();
          localObject = localClip;
        }
      }
      if (localObject != null)
      {
        ((Clip)localObject).stop();
        ((Clip)localObject).setFramePosition(0);
        ((Clip)localObject).start();
      }
      return 0L;
    }
    
    void loop(int paramInt)
    {
      Iterator localIterator = this.clipList.iterator();
      if (localIterator.hasNext())
      {
        Clip localClip = (Clip)localIterator.next();
        localClip.setFramePosition(0);
        localClip.loop(paramInt);
      }
    }
  }
}
