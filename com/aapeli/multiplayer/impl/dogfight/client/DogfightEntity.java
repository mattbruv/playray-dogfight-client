package com.aapeli.multiplayer.impl.dogfight.client;

import com.aapeli.multiplayer.client.session.game.implInterface.Entity;
import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;

public abstract class DogfightEntity
  extends Entity
{
  public DogfightToolkit getDogfightToolkit()
  {
    return (DogfightToolkit)this.toolkit.getAttachment();
  }
}
