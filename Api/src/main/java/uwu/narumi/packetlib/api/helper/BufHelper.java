package uwu.narumi.packetlib.api.helper;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import java.util.UUID;

public final class BufHelper {

  public static int readVarInt(ByteBuf buf) {
    int numRead = 0;
    int result = 0;
    byte read;
    do {
      read = buf.readByte();
      int value = (read & 0b01111111);
      result |= (value << (7 * numRead));

      numRead++;
      if (numRead > 5) {
        throw new RuntimeException("VarInt is too big");
      }
    } while ((read & 0b10000000) != 0);

    return result;
  }

  public static long readVarLong(ByteBuf buf) {
    int numRead = 0;
    long result = 0;
    byte read;
    do {
      read = buf.readByte();
      long value = (read & 0b01111111);
      result |= (value << (7 * numRead));

      numRead++;
      if (numRead > 10) {
        throw new RuntimeException("VarLong is too big");
      }
    } while ((read & 0b10000000) != 0);

    return result;
  }

  public static byte[] readByteArray(ByteBuf buf) {
    int size = readVarInt(buf);
    if (size > Short.MAX_VALUE) {
      throw new RuntimeException("Received too big byte array");
    }

    byte[] bytes = new byte[size];
    buf.readBytes(bytes);
    return bytes;
  }

  public static String readString(ByteBuf buf) {
    return new String(readByteArray(buf));
  }

  public static UUID readUUID(ByteBuf buf) {
    return new UUID(buf.readLong(), buf.readLong());
  }

  public static void writeVarInt(int value, ByteBuf buf) {
    do {
      byte temp = (byte) (value & 0b01111111);
      value >>>= 7;
      if (value != 0) {
        temp |= 0b10000000;
      }
      buf.writeByte(temp);
    } while (value != 0);
  }

  public static void writeVarLong(long value, ByteBuf buf) {
    do {
      byte temp = (byte) (value & 0b01111111);
      value >>>= 7;
      if (value != 0) {
        temp |= 0b10000000;
      }
      buf.writeByte(temp);
    } while (value != 0);
  }

  public static void writeByteArray(byte[] bytes, ByteBuf buf) {
    if (bytes.length > Short.MAX_VALUE) {
      return;
    }

    writeVarInt(bytes.length, buf);
    buf.writeBytes(bytes);
  }

  public static void writeString(String string, ByteBuf buf) {
    writeByteArray(string.getBytes(), buf);
  }

  public static void writeUUID(UUID uuid, ByteBuf buf) {
    buf.writeLong(uuid.getMostSignificantBits());
    buf.writeLong(uuid.getLeastSignificantBits());
  }

  public static byte[] asBytes(ByteBuf buf) {
    byte[] bytes = new byte[buf.readableBytes()];
    buf.readBytes(bytes);
    return bytes;
  }

  public static ByteBufInputStream asInputStream(ByteBuf buf) {
    return new ByteBufInputStream(buf);
  }

  public static ByteBufOutputStream asOutputStream(ByteBuf buf) {
    return new ByteBufOutputStream(buf);
  }
}
