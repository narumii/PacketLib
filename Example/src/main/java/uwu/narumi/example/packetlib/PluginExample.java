package uwu.narumi.example.packetlib;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import uwu.narumi.itemstack.ItemStack;
import uwu.narumi.itemstack.helper.ItemStackStreamHelper;
import uwu.narumi.nbt.impl.CompoundTag;
import uwu.narumi.packetlib.api.helper.BufHelper;
import uwu.narumi.packetlib.api.packet.PacketHandler;
import uwu.narumi.packetlib.api.packet.PacketInterceptor;
import uwu.narumi.packetlib.api.session.Session;
import uwu.narumi.packetlib.api.state.PacketState;

public class PluginExample extends JavaPlugin {

  private final ConcurrentMap<UUID, Session> sessions = new ConcurrentHashMap<>();

  @Override
  public void onDisable() {
    sessions.forEach(((uuid, session) -> sessions.remove(uuid).unInject()));
  }

  @Override
  public void onEnable() {
    getServer().getPluginManager().registerEvents(new JoinListener(), this);
  }

  private class JoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
      try {
        Session session = Session.create(event.getPlayer());
        session.inject("handler", new PacketHandlerExample(session));
        session.inject("interceptor", new PacketInterceptorExample(session));

        sessions.putIfAbsent(event.getPlayer().getUniqueId(), session);
      } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Error occurred while injecting packet handler: " + e);
      }
    }
  }

  private class PacketHandlerExample extends PacketHandler {

    public PacketHandlerExample(Session session) {
      super(session);
    }

    @Override
    public PacketState read(Object packet) {
      if (packet.getClass().getSimpleName().contains("CustomPayload")) {
        return PacketState.CANCELLED;
      }

      return PacketState.NONE;
    }

    @Override
    public PacketState write(Object packet) {
      return PacketState.NONE;
    }
  }

  private class PacketInterceptorExample extends PacketInterceptor {

    public PacketInterceptorExample(Session session) {
      super(session);
    }

    @Override
    public PacketState receive(int packetId, ByteBuf data, ByteBuf newData) {
      if (packetId == 0x10) {
        try {
          newData.writeShort(data.readShort()); //Slot id

          ItemStack itemStack = ItemStackStreamHelper.readItemStack(data); //original itemstack
          itemStack.setCompoundTag(new CompoundTag()); //setting new item nbt
          ItemStackStreamHelper.writeItemStack(itemStack, newData); //writing new data
          return PacketState.CHANGED;
        }catch (Exception e) {
          return PacketState.NONE;
        }
      }

      return PacketState.NONE;
    }

    @Override
    public void exception(Exception e) {
      System.err.println("Error occurred while decoding: " + e);
    }
  }
}
