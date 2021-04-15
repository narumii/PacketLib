package uwu.narumi.itemstack;

import uwu.narumi.nbt.NbtType;
import uwu.narumi.nbt.impl.CompoundTag;
import uwu.narumi.nbt.impl.ListTag;
import uwu.narumi.nbt.impl.StringTag;

public class ItemStack {

  private final int id;
  private final int amount;
  private final short data;
  private final CompoundTag compoundTag;

  public ItemStack(int id, int amount, int data, CompoundTag compoundTag) {
    this.id = id;
    this.amount = amount;
    this.data = (short) data;
    this.compoundTag = compoundTag;
  }

  public void name(String name) {
    if (!compoundTag.has("display", NbtType.COMPOUND)) {
      compoundTag.with("display", new CompoundTag());
    }

    ((CompoundTag) compoundTag.get("display")).with("Name", name);
  }

  public void customName(String name) {
    if (!compoundTag.has("display", NbtType.COMPOUND)) {
      compoundTag.with("display", new CompoundTag());
    }

    ((CompoundTag) compoundTag.get("display")).with("CustomName", name);
  }

  public void lore(String... lines) {
    if (!compoundTag.has("display", NbtType.COMPOUND)) {
      compoundTag.with("display", new CompoundTag());
    }

    ListTag listTag = new ListTag(NbtType.STRING);
    for (String line : lines) {
      listTag.append(new StringTag(line));
    }

    ((CompoundTag) compoundTag.get("display")).with("Lore", listTag);
  }

  public void enchant(int id, int lvl) {
    if (!compoundTag.has("ench", NbtType.LIST)) {
      compoundTag.with("ench", new ListTag(NbtType.COMPOUND));
    }

    ((ListTag) compoundTag.get("ench"))
        .append(new CompoundTag()
            .with("id", id)
            .with("lvl", lvl));
  }

  public boolean hasName() {
    return compoundTag.has("display", NbtType.COMPOUND) && ((CompoundTag) compoundTag
        .get("display")).has("Name", NbtType.STRING);
  }

  public boolean hasCustomName() {
    return compoundTag.has("display", NbtType.COMPOUND) && ((CompoundTag) compoundTag
        .get("display")).has("CustomName", NbtType.STRING);
  }

  public boolean hasLore() {
    return compoundTag.has("display", NbtType.COMPOUND) && ((CompoundTag) compoundTag
        .get("display")).has("Lore", NbtType.LIST);
  }

  public boolean hasEnchantments() {
    return compoundTag.has("ench", NbtType.LIST);
  }

  public int id() {
    return id;
  }

  public int amount() {
    return amount;
  }

  public short data() {
    return data;
  }

  public CompoundTag getCompoundTag() {
    return compoundTag;
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public String toString() {
    return "ItemStack{" +
        "id=" + id +
        ", amount=" + amount +
        ", data=" + data +
        ", compoundTag=" + compoundTag.toString() +
        '}';
  }

  public static class Builder {

    private int id;
    private int amount = 1;
    private int data;
    private CompoundTag compoundTag = new CompoundTag();

    private Builder() {
    }

    public Builder id(int id) {
      this.id = id;
      return this;
    }

    public Builder amount(int amount) {
      if (amount >= 0) {
        this.amount = amount;
      }

      return this;
    }

    public Builder data(int data) {
      this.data = data;
      return this;
    }

    public Builder nbt(CompoundTag compoundTag) {
      if (compoundTag != null) {
        this.compoundTag = compoundTag;
      }
      return this;
    }

    public ItemStack build() {
      return new ItemStack(id, amount, data, compoundTag);
    }
  }
}
