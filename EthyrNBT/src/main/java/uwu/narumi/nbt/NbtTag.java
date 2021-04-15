package uwu.narumi.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface NbtTag {

  void write(DataOutput output) throws IOException;

  void read(DataInput input) throws IOException;

  NbtType type();

  String toString();
}
