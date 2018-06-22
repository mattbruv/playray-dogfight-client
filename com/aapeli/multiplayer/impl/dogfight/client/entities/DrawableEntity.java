package com.aapeli.multiplayer.impl.dogfight.client.entities;

import com.aapeli.multiplayer.impl.dogfight.client.DogfightEntity;
import com.aapeli.multiplayer.impl.dogfight.client.Drawable;

public abstract class DrawableEntity
  extends DogfightEntity
  implements Drawable
{
  public boolean isInside(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return (paramInt1 + paramInt3 > getX()) && (paramInt1 < getX() + getWidth()) && (paramInt2 + paramInt4 > getY()) && (paramInt2 < getY() + getHeight());
  }
  
  protected abstract int getWidth();
  
  protected abstract int getHeight();
  
  protected abstract int getX();
  
  protected abstract int getY();
}
