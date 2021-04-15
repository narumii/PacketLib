package uwu.narumi.nbt.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import uwu.narumi.nbt.NbtTag;
import uwu.narumi.nbt.NbtType;

public class DoubleTag implements NbtTag {

  private double value;

  public DoubleTag(double value) {
    this.value = value;
  }

  public DoubleTag() {
  }

  @Override
  public void write(DataOutput output) throws IOException {
    output.writeDouble(value);
  }

  @Override
  public void read(DataInput input) throws IOException {
    value = input.readDouble();
  }

  public double getValue() {
    return value;
  }

  @Override
  public NbtType type() {
    return NbtType.DOUBLE;
  }

  @Override
  public String toString() {
    return "DoubleTag{" +
        "value=" + value +
        '}';
  }
}
