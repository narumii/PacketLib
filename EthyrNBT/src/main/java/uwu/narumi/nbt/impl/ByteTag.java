package uwu.narumi.nbt.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import uwu.narumi.nbt.NbtTag;
import uwu.narumi.nbt.NbtType;

public class ByteTag implements NbtTag {

  private byte value;

  public ByteTag(byte value) {
    this.value = value;
  }

  public ByteTag() {
  }

  @Override
  public void write(DataOutput output) throws IOException {
    output.writeByte(value);
  }

  @Override
  public void read(DataInput input) throws IOException {
    value = input.readByte();
  }

  public byte getValue() {
    return value;
  }

  @Override
  public NbtType type() {
    return NbtType.BYTE;
  }

  @Override
  public String toString() {
    return "ByteTag{" +
        "value=" + value +
        '}';
  }
}
