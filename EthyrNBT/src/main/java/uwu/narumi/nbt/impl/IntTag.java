package uwu.narumi.nbt.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import uwu.narumi.nbt.NbtTag;
import uwu.narumi.nbt.NbtType;

public class IntTag implements NbtTag {

  private int value;

  public IntTag(int value) {
    this.value = value;
  }

  public IntTag() {
  }

  @Override
  public void write(DataOutput output) throws IOException {
    output.writeInt(value);
  }

  @Override
  public void read(DataInput input) throws IOException {
    value = input.readInt();
  }

  public int getValue() {
    return value;
  }

  @Override
  public NbtType type() {
    return NbtType.INT;
  }

  @Override
  public String toString() {
    return "IntTag{" +
        "value=" + value +
        '}';
  }
}
