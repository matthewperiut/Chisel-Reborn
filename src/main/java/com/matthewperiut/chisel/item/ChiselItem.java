package com.matthewperiut.chisel.item;
import com.matthewperiut.chisel.Chisel;
import com.matthewperiut.chisel.block.ChiselGroupLookup;
import com.matthewperiut.chisel.gui.ChiselScreenHandler;
import com.matthewperiut.chisel.inventory.InventoryNbtUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.TagGroup;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

import static com.matthewperiut.chisel.Chisel.ITEM_CHISEL;

public class ChiselItem extends BundleItem implements NamedScreenHandlerFactory
{
    public ChiselItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack)
    {
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context)
    {

    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        return false;
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference)
    {
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        if (!world.isClient)
        {
            if (user.getItemsHand().iterator().next().isOf(ITEM_CHISEL))
                user.openHandledScreen(this);
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
    {
        NbtCompound nbt = player.getItemsHand().iterator().next().getOrCreateNbt();
        Inventory chiselInv = InventoryNbtUtil.createInventory(nbt);
        return new ChiselScreenHandler(syncId, inv, chiselInv, nbt);
        //return new ChiselDescription(syncId, inv, ScreenHandlerContext.create(player.world, player.getBlockPos()));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        return ActionResult.PASS;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        if (!world.isClient)
        {
            ItemStack inHand = miner.getItemsHand().iterator().next();
            NbtCompound nbtCompound = inHand.getOrCreateNbt();
            if (!nbtCompound.contains("Items")) {
                return false;
            }
            NbtList nbtList = nbtCompound.getList("Items", 10);

            NbtCompound nbtCompound2 = nbtList.getCompound(0);
            ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);

            String inInventoryModID;
            Identifier inHandType = Registry.BLOCK.getId(state.getBlock());
            Identifier inInventory = Registry.ITEM.getId(itemStack.getItem());

            String[] compare = new String[2];

            String[] temp;
            temp = inInventory.getPath().split("/");
            compare[0] = temp[temp.length-1];
            temp = inHandType.getPath().split("/");
            compare[1] = temp[temp.length-1];

            if(compare[0].contains(compare[1]) || compare[1].contains(compare[0]))
            {
                world.setBlockState(pos, Registry.BLOCK.get(inInventory).getDefaultState());
            }
        }
        return false;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        Item item = state.getBlock().asItem();
        if (!(item instanceof BlockItem)) {
            Chisel.LOGGER.info("How is " + item.getName().getString() +" not an blockItem?");
            return 1.0f;
        }
        TagGroup<Item> itemTags = ServerTagManagerHolder.getTagManager().getOrCreateTagGroup(Registry.ITEM_KEY);
        List<Item> chiselBlocks = ChiselGroupLookup.getBlocksInGroup(item, itemTags);
        if (chiselBlocks.isEmpty()) {
            return 1.0f;
        }
        return state.getBlock().getHardness() * 200.0f;
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("gui.chisel.chisel");
    }

}
