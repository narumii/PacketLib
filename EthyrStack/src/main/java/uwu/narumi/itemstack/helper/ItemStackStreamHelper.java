package uwu.narumi.itemstack.helper;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import java.io.IOException;
import uwu.narumi.itemstack.ItemStack;
import uwu.narumi.nbt.helper.NbtStreamHelper;
import uwu.narumi.nbt.impl.CompoundTag;

public class ItemStackStreamHelper {

  public static void writeItemStack(ItemStack itemStack, ByteBuf buf) throws IOException {
    if (itemStack == null) {
      buf.writeShort(-1);
    } else {
      buf.writeShort(itemStack.id());
      buf.writeByte(itemStack.amount());
      buf.writeShort(itemStack.data());

      NbtStreamHelper.writeTag(itemStack.getCompoundTag(), new ByteBufOutputStream(buf));
    }
  }

  public static ItemStack readItemStack(ByteBuf buf) throws IOException {
    ItemStack itemStack = null;
    short id = buf.readShort();
    if (id >= 0) {
      byte amount = buf.readByte();
      short data = buf.readShort();
      CompoundTag compoundTag = (CompoundTag) NbtStreamHelper.readTag(new ByteBufInputStream(buf));
      itemStack = new ItemStack(id, amount, data, compoundTag);
    }

    return itemStack;
  }
}
