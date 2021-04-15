package uwu.narumi.nbt.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import uwu.narumi.nbt.NbtTag;
import uwu.narumi.nbt.NbtType;

public class FloatTag implements NbtTag {

  private float value;

  public FloatTag(float value) {
    this.value = value;
  }

  public FloatTag() {
  }

  @Override
  public void write(DataOutput output) throws IOException {
    output.writeFloat(value);
  }

  @Override
  public void read(DataInput input) throws IOException {
    value = input.readFloat();
  }

  public float getValue() {
    return value;
  }

  @Override
  public NbtType type() {
    return NbtType.FLOAT;
  }

  @Override
  public String toString() {
    return "FloatTag{" +
        "value=" + value +
        '}';
  }
}

