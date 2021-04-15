package uwu.narumi.nbt.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import uwu.narumi.nbt.NbtTag;
import uwu.narumi.nbt.NbtType;

public class CompoundTag implements NbtTag {

  private final Map<String, NbtTag> tags = new HashMap<>();

  @Override
  public void write(DataOutput output) throws IOException {
    for (Entry<String, NbtTag> entry : tags.entrySet()) {
      NbtTag tag = entry.getValue();
      output.writeByte(tag.type().getId());
      if (tag.type() != NbtType.END) {
        output.writeUTF(entry.getKey());
        tag.write(output);
      }
    }

    output.write(0);
  }

  @Override
  public void read(DataInput input) throws IOException {
    byte id;

    while ((id = input.readByte()) != 0) {
      NbtTag tag = NbtType.getTagById(id);
      String name = input.readUTF();
      tag.read(input);

      tags.put(name, tag);
    }
  }

  public boolean has(String name) {
    return tags.containsKey(name);
  }

  public boolean has(String name, NbtType type) {
    return tags.containsKey(name) && tags.get(name).type() == type;
  }

  public boolean hasList(String name, NbtType type) {
    return tags.containsKey(name) && tags.get(name).type() == NbtType.LIST
        && ((ListTag) tags.get(name)).tagsType() == type;
  }

  public NbtTag get(String name) {
    return tags.get(name);
  }

  public CompoundTag remove(String name) {
    tags.remove(name);
    return this;
  }

  public CompoundTag with(String name, NbtTag tag) {
    tags.put(name, tag);
    return this;
  }

  public CompoundTag with(String name, NbtTag... nbtTags) {
    tags.put(name, new ListTag(nbtTags));
    return this;
  }

  public CompoundTag with(String name, String string) {
    tags.put(name, new StringTag(string));
    return this;
  }

  public CompoundTag with(String name, byte... bytes) {
    tags.put(name, new ByteArrayTag(bytes));
    return this;
  }

  public CompoundTag with(String name, int... ints) {
    tags.put(name, new IntArrayTag(ints));
    return this;
  }

  public CompoundTag with(String name, long... longs) {
    tags.put(name, new LongArrayTag(longs));
    return this;
  }

  public CompoundTag with(String name, Number number) { //haker typer lol
    if (number instanceof Integer) {
      tags.put(name, new IntTag(number.intValue()));
    } else if (number instanceof Long) {
      tags.put(name, new LongTag(number.longValue()));
    } else if (number instanceof Float) {
      tags.put(name, new FloatTag(number.floatValue()));
    } else if (number instanceof Double) {
      tags.put(name, new DoubleTag(number.doubleValue()));
    } else if (number instanceof Short) {
      tags.put(name, new ShortTag(number.shortValue()));
    } else if (number instanceof Byte) {
      tags.put(name, new ByteTag(number.byteValue()));
    }

    return this;
  }

  public Map<String, NbtTag> getTags() {
    return tags;
  }

  @Override
  public NbtType type() {
    return NbtType.COMPOUND;
  }

  @Override
  public String toString() {
    return "CompoundTag{" +
        "tags=" + getTagsString() +
        '}';
  }

  private String getTagsString() {
    StringBuilder stringBuilder = new StringBuilder();
    AtomicInteger atomicInteger = new AtomicInteger();

    tags.forEach((name, tag) -> {
      atomicInteger.incrementAndGet();
      stringBuilder
          .append(tag.getClass().getSimpleName())
          .append("{")
          .append("name=")
          .append(name)
          .append(", ")
          .append(tag.toString())
          .append((atomicInteger.get() >= tags.size() ? "" : ", "));
    });

    return stringBuilder.toString();
  }
}
