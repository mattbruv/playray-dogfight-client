package com.aapeli.multiplayer.client.session.chat;

import com.aapeli.multiplayer.client.resources.Loadable;
import javax.swing.JProgressBar;

public class LoadingBar
  extends JProgressBar
  implements Runnable
{
  private Loadable loadable;
  private String gameName;
  private int type;
  
  public LoadingBar(Loadable paramLoadable, String paramString, int paramInt)
  {
    this.loadable = paramLoadable;
    this.gameName = paramString;
    this.type = paramInt;
    setMinimum(0);
    setValue(0);
    setMaximum(100);
    new Thread(this).start();
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void run()
  {
    for (double d = this.loadable.getLoadStatus(); d < 1.0D; d = this.loadable.getLoadStatus())
    {
      setValue((int)(getMaximum() * this.loadable.getLoadStatus()));
      try
      {
        Thread.sleep(30L);
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
    }
    setValue(getMaximum());
  }
  
  public String getGameName()
  {
    return this.gameName;
  }
}
