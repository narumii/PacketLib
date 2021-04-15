package uwu.narumi.nbt.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import uwu.narumi.nbt.NbtTag;
import uwu.narumi.nbt.NbtType;

public class IntArrayTag implements NbtTag {

  private int[] value;

  public IntArrayTag(int... value) {
    this.value = value;
  }

  @Override
  public void write(DataOutput output) throws IOException {
    output.writeInt(value.length);
    for (int i : value) {
      output.writeInt(i);
    }
  }

  @Override
  public void read(DataInput input) throws IOException {
    value = new int[input.readInt()];
    for (int i = 0; i < value.length; i++) {
      value[i] = input.readInt();
    }
  }

  @Override
  public NbtType type() {
    return NbtType.INT_ARRAY;
  }

  @Override
  public String toString() {
    return "IntArrayTag{" +
        "value=" + Arrays.toString(value) +
        '}';
  }
}
