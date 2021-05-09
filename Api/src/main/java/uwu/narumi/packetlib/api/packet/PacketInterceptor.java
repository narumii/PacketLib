package uwu.narumi.packetlib.api.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import uwu.narumi.packetlib.api.helper.BufHelper;
import uwu.narumi.packetlib.api.session.Session;
import uwu.narumi.packetlib.api.state.PacketState;

public abstract class PacketInterceptor extends ByteToMessageDecoder {

  protected Session session;

  public PacketInterceptor(Session session) {
    this.session = session;
  }

  @Override
  protected final void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) {
    if (!buf.isReadable()) { //We don't want reading empty buffer
      return;
    }

    try {
      ByteBuf copy = buf.copy();
      ByteBuf newData = Unpooled.buffer();

      int packetId = BufHelper.readVarInt(copy);
      BufHelper.writeVarInt(packetId, newData);

      PacketState packetState = receive(packetId, copy, newData);
      switch (packetState) {
        case CHANGED:
          buf.skipBytes(buf.readableBytes()); //If we don't do that netty give us a exception
          out.add(newData);
          break;
        case NONE:
          out.add(buf.retain()
              .readBytes(buf.readableBytes())); //Yonniks now you can fix your refCnt exceptions
          break;
        case CANCELLED: //Clearing data
          if (!out.isEmpty()) {
            out.clear();
          }

          copy.clear();
          newData.clear();
          buf.clear();
          break;
      }
    } catch (Exception e) {
      exception(e);
    }
  }

  public abstract PacketState receive(int packetId, ByteBuf data, ByteBuf newData) throws Exception;

  public abstract void exception(Exception e);
}
