package uwu.narumi.nbt.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import uwu.narumi.nbt.NbtTag;
import uwu.narumi.nbt.NbtType;

public class StringTag implements NbtTag {

  private String value;

  public StringTag(String value) {
    this.value = value;
  }

  public StringTag() {
  }

  @Override
  public void write(DataOutput output) throws IOException {
    output.writeUTF(value);
  }

  @Override
  public void read(DataInput input) throws IOException {
    value = input.readUTF();
  }

  public String getValue() {
    return value;
  }

  @Override
  public NbtType type() {
    return NbtType.STRING;
  }

  @Override
  public String toString() {
    return "StringTag{" +
        "value='" + value + '\'' +
        '}';
  }
}

