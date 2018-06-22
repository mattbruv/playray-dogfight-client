package com.aapeli.multiplayer.impl.dogfight.client;

import com.aapeli.multiplayer.client.resources.Localization;
import com.aapeli.multiplayer.client.resources.SoundSystem;
import com.aapeli.multiplayer.client.session.chat.create.CustomValuesComboBox;
import com.aapeli.multiplayer.client.session.chat.create.DescriptedComponent;
import com.aapeli.multiplayer.client.session.chat.create.LabelledComboBox;
import com.aapeli.multiplayer.client.session.chat.create.LabelledTextArea;
import com.aapeli.multiplayer.client.session.chat.create.LabelledTextField;
import com.aapeli.multiplayer.client.session.chat.create.ModifiableList;
import com.aapeli.multiplayer.client.session.chat.create.OptionList;
import com.aapeli.multiplayer.client.session.game.implInterface.Game;
import com.aapeli.multiplayer.client.session.game.implInterface.GameToolkit;
import com.aapeli.multiplayer.impl.dogfight.client.entities.AlbatrosPlane;
import com.aapeli.multiplayer.impl.dogfight.client.entities.BackgroundItem;
import com.aapeli.multiplayer.impl.dogfight.client.entities.BigInfo;
import com.aapeli.multiplayer.impl.dogfight.client.entities.BlackSmoke;
import com.aapeli.multiplayer.impl.dogfight.client.entities.Bomb;
import com.aapeli.multiplayer.impl.dogfight.client.entities.BristolPlane;
import com.aapeli.multiplayer.impl.dogfight.client.entities.Coast;
import com.aapeli.multiplayer.impl.dogfight.client.entities.Explosion;
import com.aapeli.multiplayer.impl.dogfight.client.entities.Flag;
import com.aapeli.multiplayer.impl.dogfight.client.entities.FokkerPlane;
import com.aapeli.multiplayer.impl.dogfight.client.entities.Ground;
import com.aapeli.multiplayer.impl.dogfight.client.entities.Hill;
import com.aapeli.multiplayer.impl.dogfight.client.entities.ImportantBuilding;
import com.aapeli.multiplayer.impl.dogfight.client.entities.IntermissionSplash;
import com.aapeli.multiplayer.impl.dogfight.client.entities.JunkersPlane;
import com.aapeli.multiplayer.impl.dogfight.client.entities.Man;
import com.aapeli.multiplayer.impl.dogfight.client.entities.PlaneChooser;
import com.aapeli.multiplayer.impl.dogfight.client.entities.Respawner;
import com.aapeli.multiplayer.impl.dogfight.client.entities.Runway;
import com.aapeli.multiplayer.impl.dogfight.client.entities.SalmsonPlane;
import com.aapeli.multiplayer.impl.dogfight.client.entities.Sky;
import com.aapeli.multiplayer.impl.dogfight.client.entities.Smoke;
import com.aapeli.multiplayer.impl.dogfight.client.entities.SopwithPlane;
import com.aapeli.multiplayer.impl.dogfight.client.entities.TeamChooser;
import com.aapeli.multiplayer.impl.dogfight.client.entities.Water;
import com.aapeli.multiplayer.impl.dogfight.client.gui.KillArea;
import com.aapeli.multiplayer.impl.dogfight.common.TypeTable;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;

public class DogfightGame
  implements Game
{
  private GameToolkit toolkit;
  private MainScreen screen;
  private boolean firstTime = true;
  private double loadStatus = -1.0D;
  private static int instances = 0;
  private static boolean imagesLoaded = false;
  private Object contentMutex = new Object();
  
  public void init(GameToolkit paramGameToolkit)
  {
    instances += 1;
    this.toolkit = paramGameToolkit;
    if (this.firstTime)
    {
      TypeTable.initClientTypes();
      this.firstTime = false;
    }
    paramGameToolkit.addTypes(TypeTable.getClientTypes());
    DogfightToolkit localDogfightToolkit = new DogfightToolkit();
    paramGameToolkit.setAttachment(localDogfightToolkit);
    this.screen = new MainScreen(paramGameToolkit);
    localDogfightToolkit.setComponent(this.screen.getGameAreaComponent());
  }
  
  public void login(Map paramMap)
  {
    boolean bool = false;
    if (paramMap != null)
    {
      String str = (String)paramMap.get("param_watch");
      if ((str != null) && (str.equals("yes"))) {
        bool = true;
      }
    }
    this.screen.setWatch(bool);
  }
  
  public void start()
  {
    this.screen.start();
    SoundSystem.getInstance().loop("sounds/motor.au", -1);
  }
  
  public void stop()
  {
    SoundSystem.getInstance().loop("sounds/motor.au", 0);
    this.screen.stop();
    DogfightToolkit localDogfightToolkit = (DogfightToolkit)this.toolkit.getAttachment();
    localDogfightToolkit.reset();
  }
  
  public void connectionLost() {}
  
  public JComponent getScreen()
  {
    return this.screen;
  }
  
  public void destroy()
  {
    instances -= 1;
    if (instances == 0) {
      SoundSystem.getInstance().loop("sounds/motor.au", 0);
    }
    synchronized (this.contentMutex)
    {
      if (this.screen != null)
      {
        this.screen.stop();
        this.screen.destroy();
      }
    }
  }
  
  public void refresh()
  {
    synchronized (this.contentMutex)
    {
      if (this.toolkit != null)
      {
        Iterator localIterator = this.toolkit.getEntities().values().iterator();
        while (localIterator.hasNext())
        {
          Object localObject1 = localIterator.next();
          if ((localObject1 instanceof Runnable))
          {
            Runnable localRunnable = (Runnable)localObject1;
            localRunnable.run();
          }
        }
      }
      if (this.screen != null) {
        this.screen.refresh();
      }
    }
  }
  
  public void loadResources()
  {
    if (!imagesLoaded)
    {
      this.loadStatus = 0.0D;
      SopwithPlane.loadImages();
      this.loadStatus = 0.05D;
      TeamChooser.loadImages();
      this.loadStatus = 0.1D;
      BigInfo.loadImages();
      this.loadStatus = 0.12D;
      Sky.loadImages();
      this.loadStatus = 0.15D;
      ScoreTable.loadImages();
      this.loadStatus = 0.17D;
      Runway.loadImages();
      this.loadStatus = 0.2D;
      IntermissionSplash.loadImages();
      this.loadStatus = 0.22D;
      Smoke.loadImages();
      this.loadStatus = 0.25D;
      Flag.loadImages();
      this.loadStatus = 0.27D;
      BlackSmoke.loadImages();
      this.loadStatus = 0.3D;
      KillArea.loadImages();
      this.loadStatus = 0.32D;
      this.loadStatus = 0.35D;
      Bomb.loadImages();
      this.loadStatus = 0.37D;
      ImportantBuilding.loadImages();
      this.loadStatus = 0.4D;
      Man.loadImages();
      this.loadStatus = 0.42D;
      PlaneChooser.loadResources();
      this.loadStatus = 0.44D;
      Coast.loadImages();
      Respawner.loadImages();
      this.loadStatus = 0.45D;
      Hill.loadImages();
      this.loadStatus = 0.47D;
      BackgroundItem.loadImages();
      this.loadStatus = 0.49D;
      Explosion.loadImages();
      this.loadStatus = 0.5D;
      AlbatrosPlane.loadImages();
      this.loadStatus = 0.52D;
      JunkersPlane.loadImages();
      this.loadStatus = 0.55D;
      FokkerPlane.loadImages();
      this.loadStatus = 0.57D;
      BristolPlane.loadImages();
      Water.loadImages();
      this.loadStatus = 0.59D;
      SalmsonPlane.loadImages();
      this.loadStatus = 0.61D;
      Ground.loadImages();
      this.loadStatus = 0.65D;
      StatsSurface.loadImages();
      this.loadStatus = 0.69D;
      imagesLoaded = true;
    }
    SoundSystem.getInstance().loadClip("sounds/m16.au", 3);
    this.loadStatus = 0.75D;
    SoundSystem.getInstance().loadClip("sounds/bombdrop.au", 3);
    this.loadStatus = 0.83D;
    SoundSystem.getInstance().loadClip("sounds/explosion.au", 3);
    this.loadStatus = 0.91D;
    SoundSystem.getInstance().loadClip("sounds/motor.au");
    this.loadStatus = 0.96D;
    SoundSystem.getInstance().loadClip("sounds/hit.au");
    this.loadStatus = 10.0D;
    System.out.println("Loaded, status now " + this.loadStatus);
  }
  
  public double getLoadStatus()
  {
    return this.loadStatus;
  }
  
  public OptionList createOptionList(String[] paramArrayOfString)
  {
    ArrayList localArrayList = new ArrayList(10);
    localArrayList.add(new DescriptedComponent(new LabelledComboBox("param_watch", new String[] { "yes", "no" }, true, 80), "param_watch_description"));
    localArrayList.add(new DescriptedComponent(new LabelledTextField("param_game time", "10", 40, 3), "param_game time_description"));
    localArrayList.add(new DescriptedComponent(new LabelledTextField("param_intermission_time", "20", 40, 3), "param_intermission_time_description"));
    localArrayList.add(new DescriptedComponent(new LabelledTextField("param_password", "", 70, 10), "param_password_description"));
    localArrayList.add(new DescriptedComponent(new CustomValuesComboBox("param_levels", new String[] { "" }, false, 150), "param_level_description"));
    localArrayList.add(new DescriptedComponent(new LabelledComboBox("param_game mode", new String[] { "normal", "rounds" }, true, 150), "param_game mode_description"));
    DescriptedComponent localDescriptedComponent = new DescriptedComponent(new LabelledComboBox("param_room_size", paramArrayOfString, true, 220), "param_room_size_description");
    localArrayList.add(localDescriptedComponent);
    OptionList localOptionList = new OptionList(localArrayList, true, localDescriptedComponent);
    return localOptionList;
  }
  
  public OptionList manageOptionList(String[] paramArrayOfString)
  {
    ArrayList localArrayList = new ArrayList(10);
    localArrayList.add(new DescriptedComponent(new LabelledComboBox("param_watch", new String[] { "yes", "no" }, true, 80), "param_watch_description"));
    localArrayList.add(new DescriptedComponent(new LabelledTextField("param_game time", "10", 40, 3), "param_game time_description"));
    localArrayList.add(new DescriptedComponent(new LabelledTextField("param_intermission_time", "20", 40, 3), "param_intermission_time_description"));
    localArrayList.add(new DescriptedComponent(new LabelledComboBox("param_allow", new String[] { "all", "registered", "royal" }, true, 150), "param_allow_description"));
    localArrayList.add(new DescriptedComponent(new LabelledTextField("param_password", "", 70, 10), "param_password_description"));
    localArrayList.add(new DescriptedComponent(new CustomValuesComboBox("param_levels", new String[] { "" }, false, 150), "param_level_description"));
    localArrayList.add(new DescriptedComponent(new LabelledComboBox("param_game mode", new String[] { "normal", "rounds" }, true, 150), "param_game mode_description"));
    localArrayList.add(new DescriptedComponent(new LabelledTextField("param_max players", "10", 40, 2), "param_max players_description"));
    localArrayList.add(new DescriptedComponent(new ModifiableList("param_room_sheriffs", Localization.getInstance().localize("Remove"), Localization.getInstance().localize("Add"), 250, 100), "admin_list_description"));
    localArrayList.add(new DescriptedComponent(new LabelledComboBox("param_team_balance", new String[] { "yes", "no" }, true, 80), "param_team_balance_description"));
    localArrayList.add(new DescriptedComponent(new LabelledTextArea("param_welcome_message", "", 370, 80, 45, 5), "param_welcome_message_description"));
    OptionList localOptionList = new OptionList(localArrayList, true, null);
    return localOptionList;
  }
}
