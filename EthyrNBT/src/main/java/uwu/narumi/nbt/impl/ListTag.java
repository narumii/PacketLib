package uwu.narumi.nbt.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import uwu.narumi.nbt.NbtTag;
import uwu.narumi.nbt.NbtType;

public class ListTag implements NbtTag {

  private final List<NbtTag> tags = new ArrayList<>();
  private NbtType tagsType;

  public ListTag(NbtType tagsType) {
    this.tagsType = tagsType;
  }

  public ListTag(NbtTag... tags) {
    this.tags.addAll(Arrays.asList(tags));
    tagsType = this.tags.get(0).type();
  }

  public ListTag() {
  }

  @Override
  public void write(DataOutput output) throws IOException {
    if (tags.isEmpty()) {
      return;
    }

    if (tagsType == null) { //ANTIHACKER MOMENT
      tagsType = tags.get(0).type();
    }

    output.writeByte(tags.get(0).type().getId());
    output.writeInt(tags.size());
    for (NbtTag tag : tags) {
      tag.write(output);
    }
  }

  @Override
  public void read(DataInput input) throws IOException {
    byte id = input.readByte();
    int size = input.readInt();

    if (tagsType == null) //ANTIHACKER MOMENT
    {
      tagsType = NbtType.getTypeById(id);
    }

    for (int i = 0; i < size; i++) {
      NbtTag tag = NbtType.getTagById(id);
      if (tag.type() == tagsType) {
        tag.read(input);
        tags.add(tag);
      }
    }
  }

  public ListTag append(NbtTag tag) {
    if (tags.isEmpty() || (tag.type() == tags.get(0).type())) {
      tags.add(tag);
    }
    return this;
  }

  public List<NbtTag> getTags() {
    return tags;
  }

  public NbtType tagsType() {
    return tagsType;
  }

  @Override
  public NbtType type() {
    return NbtType.LIST;
  }

  @Override
  public String toString() {
    return "ListTag{" +
        "tagsType=" + tagsType +
        ", tags=" + tags.stream().map(Object::toString).collect(Collectors.joining(", ")) +
        '}';
  }
}
