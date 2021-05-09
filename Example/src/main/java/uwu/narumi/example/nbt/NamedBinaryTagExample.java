package uwu.narumi.example.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import uwu.narumi.nbt.helper.NbtStreamHelper;
import uwu.narumi.nbt.impl.CompoundTag;
import uwu.narumi.nbt.impl.IntTag;
import uwu.narumi.nbt.impl.ListTag;
import uwu.narumi.nbt.impl.StringTag;

public class NamedBinaryTagExample {

  public static void main(String... args) throws Exception {
    CompoundTag compoundTag = new CompoundTag();
    ListTag listTag = new ListTag();

    for (int i = 0; i < 5; i++) {
      listTag.append(new IntTag(i));
    }

    compoundTag
        .with("numbers", listTag)
        .with("name", "1234")
        .with("double", 1D)
        .with("int", 1)
        .with("float", 1F)
        .with("long", 1L)
        .with("byte", (byte) 1)
        .with("short", (short) 1)
        .with("barray", new byte[5])
        .with("iarray", new int[5])
        .with("larray", new long[5])
        .with("pages", new StringTag("1"), new StringTag("2"), new StringTag("3"))
        .with("dupa", new StringTag("whiodhqwiodhq2hd"));

    try (DataOutputStream out = new DataOutputStream(new FileOutputStream("test.nbt"))) {
      NbtStreamHelper.writeTag(compoundTag, out);
    }

    try (DataInputStream in = new DataInputStream(new FileInputStream("test.nbt"))) {
      CompoundTag tag = (CompoundTag) NbtStreamHelper.readTag(in);
      System.out.println(tag);
    }
  }
}
