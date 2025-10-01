package com.periut.chisel.item;

import com.periut.chisel.Chisel;
import com.periut.chisel.block.ChiselGroupLookup;
import com.periut.chisel.gui.ChiselScreenHandler;
import com.periut.chisel.inventory.InventoryUtil;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static com.periut.chisel.Chisel.chiselSupplier;

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
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        return false;
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference)
    {
        return false;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient())
            if (user.getMainHandStack().isOf(chiselSupplier.get()))
                user.openHandledScreen(this);
        return super.use(world, user, hand);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {

    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
    {
        //ComponentMap nbt = player.getHandItems().iterator().next().getComponents();
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
        BundleContentsComponent bundleContentsComponent = stack.getOrDefault(DataComponentTypes.BUNDLE_CONTENTS, new BundleContentsComponent(new ArrayList<>()));
        assert bundleContentsComponent != null;
        Inventory chiselInv = InventoryUtil.createInventory(bundleContentsComponent);
        return new ChiselScreenHandler(syncId, inv, chiselInv, bundleContentsComponent);
        //return new ChiselDescription(syncId, inv, ScreenHandlerContext.create(player.world, player.getBlockPos()));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.PASS;
    }

    public static void chiselSound(World world, BlockPos pos)
    {
        if (!world.isClient())
        {
            world.playSound(
                    null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                    pos, // The position of where the sound will come from
                    Chisel.CHISEL_SOUND_EVENT, // The sound that will play
                    SoundCategory.BLOCKS, // This determines which of the volume sliders affect this sound
                    1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                    1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
            );
        }
    }

    class TimeSinceUse {
        public PlayerEntity player;
        public Long time;

        TimeSinceUse(PlayerEntity player, Long time) {
            this.player = player;
            this.time = time;
        }
    }

    // this is cursed, blame mojang for removing nbt from items lol
    public static ArrayList<TimeSinceUse> timeSinceUses = new ArrayList<>();

    @Override
    public boolean canMine(ItemStack stack, BlockState state, World world, BlockPos pos, LivingEntity miner) {
        if (!world.isClient())
        {
            if (!(miner instanceof PlayerEntity))
                return false;
            // blame mojang nbt
            boolean found = false;
            for (TimeSinceUse u : timeSinceUses) {
                if (u.player.equals(miner)) {
                    found = true;
                    if (world.getTime() - u.time < 5) {
                        return false;
                    }
                    u.time = world.getTime();
                }
            }
            if (!found) {
                timeSinceUses.add(new TimeSinceUse((PlayerEntity) miner, world.getTime()));
            }
            //

            ItemStack inHand = miner.getStackInHand(Hand.MAIN_HAND);

            BundleContentsComponent bcc = inHand.getOrDefault(DataComponentTypes.BUNDLE_CONTENTS, new BundleContentsComponent(new ArrayList<>(0)));

            ItemStack itemStack;
            if (!bcc.isEmpty()) {
                itemStack = bcc.get(0);
            }
            else {
                List<Item> items = ChiselGroupLookup.getBlocksInGroup(state.getBlock().asItem());
                if(!items.isEmpty())
                {
                    Identifier block = Registries.ITEM.getId(items.get(world.random.nextInt(items.size())));
                    world.setBlockState(pos, Registries.BLOCK.get(block).getDefaultState());
                    chiselSound(world, pos);
                }
                return false;
            }

            Identifier blockId = Registries.BLOCK.getId(state.getBlock());
            Identifier inInventory = Registries.ITEM.getId(itemStack.getItem());

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
                world.setBlockState(pos, Registries.BLOCK.get(inInventory).getDefaultState());
                chiselSound(world, pos);
            }
        }
        return false;
    }

    @Override
    public float getMiningSpeed(ItemStack stack, BlockState state) {
        Item item = state.getBlock().asItem();
        if (!(item instanceof BlockItem)) {
            Chisel.LOGGER.info("How is " + item.getName().getString() +" not a blockItem?");
            return 1.0f;
        }
        return 500.0f;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.chisel.chisel");
    }

}
