package uwu.narumi.example.itemstack;

import uwu.narumi.itemstack.ItemStack;
import uwu.narumi.nbt.impl.CompoundTag;
import uwu.narumi.nbt.impl.StringTag;

public class ItemStackExample {

  public static void main(String... args) throws Exception {
    CompoundTag compoundTag = new CompoundTag();
    compoundTag.with("pages",
        new StringTag("page 1"),
        new StringTag("page 2"),
        new StringTag("page 3")
    );

    ItemStack itemStack = ItemStack.builder()
        .id(386)
        .amount(5)
        .data(0)
        .nbt(compoundTag)
        .build();

    itemStack.name("Magic Book");
    itemStack.lore("lore1", "lore2");
    itemStack.enchant(5, 1);

    System.out.println(itemStack.toString());
  }
}
