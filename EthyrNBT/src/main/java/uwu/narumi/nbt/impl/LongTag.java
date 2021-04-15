package uwu.narumi.nbt.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import uwu.narumi.nbt.NbtTag;
import uwu.narumi.nbt.NbtType;

public class LongTag implements NbtTag {

  private long value;

  public LongTag(long value) {
    this.value = value;
  }

  public LongTag() {
  }

  @Override
  public void write(DataOutput output) throws IOException {
    output.writeLong(value);
  }

  @Override
  public void read(DataInput input) throws IOException {
    value = input.readLong();
  }

  public long getValue() {
    return value;
  }

  @Override
  public NbtType type() {
    return NbtType.LONG;
  }

  @Override
  public String toString() {
    return "LongTag{" +
        "value=" + value +
        '}';
  }
}
