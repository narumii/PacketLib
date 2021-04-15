package uwu.narumi.nbt;

import uwu.narumi.nbt.exception.NBTException;
import uwu.narumi.nbt.impl.ByteArrayTag;
import uwu.narumi.nbt.impl.ByteTag;
import uwu.narumi.nbt.impl.CompoundTag;
import uwu.narumi.nbt.impl.DoubleTag;
import uwu.narumi.nbt.impl.EndTag;
import uwu.narumi.nbt.impl.FloatTag;
import uwu.narumi.nbt.impl.IntArrayTag;
import uwu.narumi.nbt.impl.IntTag;
import uwu.narumi.nbt.impl.ListTag;
import uwu.narumi.nbt.impl.LongArrayTag;
import uwu.narumi.nbt.impl.LongTag;
import uwu.narumi.nbt.impl.ShortTag;
import uwu.narumi.nbt.impl.StringTag;

public enum NbtType {

  END("END", 0),
  BYTE("BYTE", 1),
  SHORT("SHORT", 2),
  INT("INT", 3),
  LONG("LONG", 4),
  FLOAT("FLOAT", 5),
  DOUBLE("DOUBLE", 6),
  BYTE_ARRAY("BYTE[]", 7),
  STRING("STRING", 8),
  LIST("LIST", 9),
  COMPOUND("COMPOUND", 10),
  INT_ARRAY("INT[]", 11),
  LONG_ARRAY("LONG[]", 12);

  private final String name;
  private final int id;

  NbtType(String name, int id) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static NbtType getTypeById(byte id) {
    for (NbtType value : values()) {
      if (value.id == id) {
        return value;
      }
    }

    throw new NBTException("Invalid tag id");
  }

  public static NbtTag getTagById(byte id) {
    switch (id) {
      case 0:
        return new EndTag();
      case 1:
        return new ByteTag();
      case 2:
        return new ShortTag();
      case 3:
        return new IntTag();
      case 4:
        return new LongTag();
      case 5:
        return new FloatTag();
      case 6:
        return new DoubleTag();
      case 7:
        return new ByteArrayTag();
      case 8:
        return new StringTag();
      case 9:
        return new ListTag();
      case 10:
        return new CompoundTag();
      case 11:
        return new IntArrayTag();
      case 12:
        return new LongArrayTag();
      default:
        throw new NBTException("Invalid nbt type id");
    }
  }
}
