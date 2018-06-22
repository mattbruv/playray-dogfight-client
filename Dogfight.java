import com.aapeli.multiplayer.client.launcher.AbstractLauncher;
import com.aapeli.multiplayer.impl.dogfight.client.DogfightGame;
import com.aapeli.multiplayer.impl.dogfight.common.CreditInitializer;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class Dogfight
  extends AbstractLauncher
{
  private static final String GAME_NAME = "Dogfight";
  
  public void init()
  {
    System.out.println("-= Dogfight 1.10 =-");
    System.out.println("");
    HashMap localHashMap = new HashMap(5);
    localHashMap.put("game_name", "Dogfight");
    localHashMap.put("game_version", "1.1");
    localHashMap.put("default_view", new Integer(52));
    localHashMap.put("view_name_join", "Watch");
    localHashMap.put("tabs", new int[] { 50, 52, 51, 53 });
    localHashMap.put("join_view_hide_join", new Boolean(false));
    super.init(localHashMap);
    super.setGame(new DogfightGame());
    super.connect();
  }
  
  protected void initCreditConstants() {}
}
