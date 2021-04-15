package uwu.narumi.nbt.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import uwu.narumi.nbt.NbtTag;
import uwu.narumi.nbt.NbtType;

public class ShortTag implements NbtTag {

  private short value;

  public ShortTag(short value) {
    this.value = value;
  }

  public ShortTag() {
  }

  @Override
  public void write(DataOutput output) throws IOException {
    output.writeShort(value);
  }

  @Override
  public void read(DataInput input) throws IOException {
    value = input.readShort();
  }

  public short getValue() {
    return value;
  }

  @Override
  public NbtType type() {
    return NbtType.SHORT;
  }

  @Override
  public String toString() {
    return "ShortTag{" +
        "value=" + value +
        '}';
  }
}

