package uwu.narumi.nbt.helper;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import uwu.narumi.nbt.NbtTag;
import uwu.narumi.nbt.NbtType;

public class NbtStreamHelper {

  public static void writeTag(NbtTag tag, DataOutput output) throws IOException {
    output.writeByte(tag.type().getId());
    if (tag.type() != NbtType.END) {
      output.writeUTF(""); //MOJANG?????
      tag.write(output);
    }
  }

  public static NbtTag readTag(DataInput input) throws IOException {
    NbtTag tag = NbtType.getTagById(input.readByte());
    if (tag.type() != NbtType.END) {
      input.readUTF(); //MOJANG?????
      tag.read(input);
    }

    return tag;
  }
}
