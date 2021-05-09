package uwu.narumi.packetlib.api.helper;

import io.netty.channel.Channel;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class ReflectionHelper {

  private static String VERSION;
  private static String BUKKIT;
  private static String NMS;

  private static Method getHandleMethod;
  private static Field playerConnectionField;
  private static Field networkManagerField;
  private static Field channelField;

  static {
    try {
      BUKKIT = Bukkit.getServer().getClass().getName().replace(".CraftServer", "");
      NMS = BUKKIT.replace("org.bukkit.craftbukkit", "net.minecraft.server");
      VERSION = (BUKKIT.split("\\.")[BUKKIT.split("\\.").length - 1]).substring(1).replace("_", "."); //Yes i know boiler plate

      Class<?> craftPlayerClass = Class.forName(BUKKIT + ".entity.CraftPlayer");
      Class<?> entityPlayerClass = Class.forName(NMS + ".EntityPlayer");
      Class<?> playerConnectionClass = Class.forName(NMS + ".PlayerConnection");
      Class<?> networkManagerClass = Class.forName(NMS + ".NetworkManager");

      getHandleMethod = craftPlayerClass.getMethod("getHandle");
      playerConnectionField = entityPlayerClass.getField("playerConnection");
      networkManagerField = playerConnectionClass.getField("networkManager");
      channelField = networkManagerClass.getField("channel");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Channel getChannel(Player player) throws Exception {
    Object entityPlayer = getHandleMethod.invoke(player);
    Object playerConnection = playerConnectionField.get(entityPlayer);
    Object networkManager = networkManagerField.get(playerConnection);
    return (Channel) channelField.get(networkManager);
  }

  public static String getBukkitPackage() {
    return BUKKIT;
  }

  public static String getServerPackage() {
    return NMS;
  }

  public static String getVersion() {
    return VERSION;
  }
}
