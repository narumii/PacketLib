package uwu.narumi.nbt.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import uwu.narumi.nbt.NbtTag;
import uwu.narumi.nbt.NbtType;

public class LongArrayTag implements NbtTag {

  private long[] value;

  public LongArrayTag(long... value) {
    this.value = value;
  }

  @Override
  public void write(DataOutput output) throws IOException {
    output.writeInt(value.length);
    for (long l : value) {
      output.writeLong(l);
    }
  }

  @Override
  public void read(DataInput input) throws IOException {
    value = new long[input.readInt()];
    for (int i = 0; i < value.length; i++) {
      value[i] = input.readLong();
    }
  }

  public long[] getValue() {
    return value;
  }

  @Override
  public NbtType type() {
    return NbtType.LONG_ARRAY;
  }

  @Override
  public String toString() {
    return "LongArrayTag{" +
        "value=" + Arrays.toString(value) +
        '}';
  }
}
