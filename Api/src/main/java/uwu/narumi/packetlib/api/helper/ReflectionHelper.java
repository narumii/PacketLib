package uwu.narumi.packetlib.api.helper;

import io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReflectionHelper {

  private static String BUKKIT;
  private static String NMS;

  private static Class<?> CRAFT_PLAYER;

  static {
    try {
      BUKKIT = Bukkit.getServer().getClass().getName().replace(".CraftServer", "");
      NMS = BUKKIT.replace("org.bukkit.craftbukkit", "net.minecraft.server");

      CRAFT_PLAYER = Class.forName(BUKKIT + ".entity.CraftPlayer");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Channel getChannel(Player player) throws Exception {
    Object entityPlayer = CRAFT_PLAYER.getMethod("getHandle").invoke(player);
    Object playerConnection = entityPlayer.getClass().getField("playerConnection")
        .get(entityPlayer);
    Object networkManager = playerConnection.getClass().getField("networkManager")
        .get(playerConnection);
    return (Channel) networkManager.getClass().getField("channel").get(networkManager);
  }
}
