package uwu.narumi.nbt.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import uwu.narumi.nbt.NbtTag;
import uwu.narumi.nbt.NbtType;

public class ByteArrayTag implements NbtTag {

  private byte[] value;

  public ByteArrayTag(byte... value) {
    this.value = value;
  }

  @Override
  public void write(DataOutput output) throws IOException {
    output.writeInt(value.length);
    output.write(value);
  }

  @Override
  public void read(DataInput input) throws IOException {
    value = new byte[input.readInt()];
    input.readFully(value);
  }

  public byte[] getValue() {
    return value;
  }

  @Override
  public NbtType type() {
    return NbtType.BYTE_ARRAY;
  }

  @Override
  public String toString() {
    return "ByteArrayTag{" +
        "value=" + Arrays.toString(value) +
        '}';
  }
}
