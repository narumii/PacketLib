package uwu.narumi.nbt.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import uwu.narumi.nbt.NbtTag;
import uwu.narumi.nbt.NbtType;

public class EndTag implements NbtTag {

  @Override
  public void write(DataOutput output) throws IOException {

  }

  @Override
  public void read(DataInput input) throws IOException {

  }

  @Override
  public NbtType type() {
    return NbtType.END;
  }

  @Override
  public String toString() {
    return "EndTag{}";
  }
}

