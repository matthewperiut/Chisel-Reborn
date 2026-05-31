package com.periut.chisel.item;

import com.periut.chisel.Chisel;
import com.periut.chisel.block.ChiselGroupLookup;
import com.periut.chisel.gui.ChiselScreenHandler;
import com.periut.chisel.inventory.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import java.util.ArrayList;
import java.util.List;

import static com.periut.chisel.Chisel.chiselSupplier;

public class ChiselItem extends BundleItem implements MenuProvider
{
    public ChiselItem(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isBarVisible(ItemStack stack)
    {
        return false;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction clickType, Player player) {
        return false;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference)
    {
        return false;
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        if (!world.isClientSide())
            if (user.getMainHandItem().is(chiselSupplier.get()))
                user.openMenu(this);
        return super.use(world, user, hand);
    }

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {

    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player)
    {
        //ComponentMap nbt = player.getHandItems().iterator().next().getComponents();
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        BundleContents bundleContentsComponent = stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, new BundleContents(new ArrayList<>()));
        assert bundleContentsComponent != null;
        Container chiselInv = InventoryUtil.createInventory(bundleContentsComponent);
        return new ChiselScreenHandler(syncId, inv, chiselInv, bundleContentsComponent);
        //return new ChiselDescription(syncId, inv, ScreenHandlerContext.create(player.world, player.getBlockPos()));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return InteractionResult.PASS;
    }

    public static void chiselSound(Level world, BlockPos pos)
    {
        if (!world.isClientSide())
        {
            world.playSound(
                    null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                    pos, // The position of where the sound will come from
                    Chisel.CHISEL_SOUND_EVENT, // The sound that will play
                    SoundSource.BLOCKS, // This determines which of the volume sliders affect this sound
                    1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                    1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
            );
        }
    }

    class TimeSinceUse {
        public Player player;
        public Long time;

        TimeSinceUse(Player player, Long time) {
            this.player = player;
            this.time = time;
        }
    }

    // this is cursed, blame mojang for removing nbt from items lol
    public static ArrayList<TimeSinceUse> timeSinceUses = new ArrayList<>();

    @Override
    public boolean canDestroyBlock(ItemStack stack, BlockState state, Level world, BlockPos pos, LivingEntity miner) {
        if (!world.isClientSide())
        {
            if (!(miner instanceof Player))
                return false;
            // blame mojang nbt
            boolean found = false;
            for (TimeSinceUse u : timeSinceUses) {
                if (u.player.equals(miner)) {
                    found = true;
                    if (world.getGameTime() - u.time < 5) {
                        return false;
                    }
                    u.time = world.getGameTime();
                }
            }
            if (!found) {
                timeSinceUses.add(new TimeSinceUse((Player) miner, world.getGameTime()));
            }
            //

            ItemStack inHand = miner.getItemInHand(InteractionHand.MAIN_HAND);

            BundleContents bcc = inHand.getOrDefault(DataComponents.BUNDLE_CONTENTS, new BundleContents(new ArrayList<>(0)));

            ItemStack itemStack;
            if (!bcc.isEmpty()) {
                itemStack = bcc.itemCopyStream().findFirst().orElse(ItemStack.EMPTY);
            }
            else {
                List<Item> items = ChiselGroupLookup.getBlocksInGroup(state.getBlock().asItem());
                if(!items.isEmpty())
                {
                    Identifier block = BuiltInRegistries.ITEM.getKey(items.get(world.getRandom().nextInt(items.size())));
                    world.setBlockAndUpdate(pos, BuiltInRegistries.BLOCK.getValue(block).defaultBlockState());
                    chiselSound(world, pos);
                }
                return false;
            }

            Identifier blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
            Identifier inInventory = BuiltInRegistries.ITEM.getKey(itemStack.getItem());

            String[] compare = new String[2];

            String[] temp;
            temp = inInventory.getPath().split("/");
            compare[0] = temp[temp.length-1];
            temp = blockId.getPath().split("/");
            compare[1] = temp[temp.length-1];

            if (compare[1].contains("stairs")) {
                return false;
            }

            if(compare[0].contains(compare[1]) || compare[1].contains(compare[0]))
            {
                world.setBlockAndUpdate(pos, BuiltInRegistries.BLOCK.getValue(inInventory).defaultBlockState());
                chiselSound(world, pos);
            }
        }
        return false;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        Item item = state.getBlock().asItem();
        if (!(item instanceof BlockItem)) {
            Chisel.LOGGER.info("How is " + item.getName(item.getDefaultInstance()).getString() +" not a blockItem?");
            return 1.0f;
        }
        return 500.0f;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("gui.chisel.chisel");
    }

}
