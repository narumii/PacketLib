package uwu.narumi.packetlib.api.packet;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import uwu.narumi.packetlib.api.session.Session;
import uwu.narumi.packetlib.api.state.PacketState;

public abstract class PacketHandler extends ChannelDuplexHandler {

  protected Session session;

  public PacketHandler(Session session) {
    this.session = session;
  }

  public abstract PacketState read(Object packet);

  public abstract PacketState write(Object packet);

  @Override
  public final void write(ChannelHandlerContext channelHandlerContext, Object o,
      ChannelPromise channelPromise) throws Exception {
    if (write(o) == PacketState.NONE) {
      super.write(channelHandlerContext, o, channelPromise);
    }
  }

  @Override
  public final void channelRead(ChannelHandlerContext channelHandlerContext, Object o)
      throws Exception {
    if (read(o) == PacketState.NONE) {
      super.channelRead(channelHandlerContext, o);
    }
  }
}
