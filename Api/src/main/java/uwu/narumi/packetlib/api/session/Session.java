package uwu.narumi.packetlib.api.session;

import io.netty.channel.Channel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import uwu.narumi.packetlib.api.helper.ReflectionHelper;
import uwu.narumi.packetlib.api.packet.PacketHandler;
import uwu.narumi.packetlib.api.packet.PacketInterceptor;

public class Session {

  private final List<String> adapters = new ArrayList<>();
  private final Player player;
  private final Channel channel;

  private Session(Player player, Channel channel) {
    this.player = player;
    this.channel = channel;
  }

  public static Session create(Player player) {
    try {
      Channel channel = ReflectionHelper.getChannel(player);
      return new Session(player, channel);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void inject(String name, PacketInterceptor adapter) {
    try {
      if (channel.pipeline().get("decompress") != null) {
        channel.pipeline().addAfter("decompress", name, adapter);
      } else if (channel.pipeline().get("decoder") != null) {
        channel.pipeline().addBefore("decoder", name, adapter);
      } else {
        throw new IOException(
            String.format("Can't inject PacketInterceptor (%s)", player.getName()));
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    adapters.add(name);
  }

  public void inject(String name, PacketHandler adapter) {
    try {
      if (channel.pipeline().get("packet_handler") != null) {
        channel.pipeline().addBefore("packet_handler", name, adapter);
      } else if (channel.pipeline().get("decoder") != null) {
        channel.pipeline().addAfter("decoder", name, adapter);
      } else {
        throw new IOException(String.format("Can't inject PacketHandler (%s)", player.getName()));
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    adapters.add(name);
  }

  public void unInject() {
    adapters.forEach(name -> {
      if (channel.pipeline().get(name) != null) {
        channel.pipeline().remove(name);
      }
    });
    adapters.clear();
  }

  public void unInject(String name) {
    if (channel.pipeline().get(name) != null) {
      channel.pipeline().remove(name);
      adapters.remove(name);
    }
  }

  public Player getPlayer() {
    return player;
  }

  public Channel getChannel() {
    return channel;
  }
}
