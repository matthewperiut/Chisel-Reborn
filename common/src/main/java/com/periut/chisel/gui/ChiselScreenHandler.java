package com.periut.chisel.gui;

import com.periut.chisel.Chisel;
import com.periut.chisel.inventory.ChiselInventory;
import com.periut.chisel.inventory.InventoryUtil;
import com.periut.chisel.item.ChiselItem;
import com.periut.cryonicconfig.CryonicConfig;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

import java.time.LocalTime;
import java.util.ArrayList;

public class ChiselScreenHandler extends ScreenHandler {
    boolean compactTexture;
    private final ChiselInventory inventory;
    private final BundleContentsComponent componentInventory;
    LocalTime currentTime = LocalTime.now();

    public ChiselScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new ChiselInventory());
    }

    public ChiselScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        this(syncId, playerInventory, inventory, new BundleContentsComponent(new ArrayList<>()));
    }

    public ChiselScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, BundleContentsComponent component) {
        super(Chisel.CHISEL_SCREEN_HANDLER.get(), syncId);
        compactTexture = CryonicConfig.getConfig("chisel").getBoolean("compact_chisel_gui", false);
        checkSize(inventory, 61);
        this.inventory = (ChiselInventory) inventory;
        componentInventory = component;

        if (compactTexture) {
            // input slot
            this.addSlot(new Slot(inventory, 0, 8, 145));

            // output slots
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 6; y++) {
                    this.addSlot(new SlotChiselOutput(inventory, 1 + x + 10 * y, 62 + 18 * x - 54, 8 + 18 * y + 7));
                }
            }

            // inv
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 3; y++) {
                    this.addSlot(new Slot(playerInventory, 9 + x + 9 * y, 71 + 18 * x - 45, 120 + 18 * y + 7));
                }
            }

            // hotbar
            for (int x = 0; x < 9; x++) {
                this.addSlot(new Slot(playerInventory, x, 71 + 18 * x - 45, 178 + 7));
            }
        } else {
            // input slot
            this.addSlot(((BigSlot)new Slot(inventory, 0, 24, 24)).setBigSlot(true));

            // output slots
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 6; y++) {
                    this.addSlot(new SlotChiselOutput(inventory, 1 + x + 10 * y, 62 + 18 * x, 8 + 18 * y));
                }
            }

            // inv
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 3; y++) {
                    this.addSlot(new Slot(playerInventory, 9 + x + 9 * y, 71 + 18 * x, 120 + 18 * y));
                }
            }

            // hotbar
            for (int x = 0; x < 9; x++) {
                this.addSlot(new Slot(playerInventory, x, 71 + 18 * x, 178));
            }
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack(); // stack in slot clicked
            newStack = originalStack.copy();
            if (originalStack.getItem().equals(Chisel.chiselSupplier.get())) {
                return ItemStack.EMPTY;
            }
            if (invSlot < this.inventory.size()) {
                // slot clicked was in chisel inv
                if (invSlot != 0) {
                    if (this.slots.get(0).hasStack()) {
                        int count = this.slots.get(0).getStack().getCount();
                        if (count > 0 && count <= 99) {
                            originalStack.setCount(count);
                        }
                    }
                    ChiselItem.chiselSound(player.getWorld(), player.getBlockPos());
                }
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    this.inventory.refresh(this.slots.get(0).getStack().getItem());
                    return ItemStack.EMPTY;
                } else {
                    this.inventory.clear();
                }

            } else if (!this.insertItem(originalStack, 0, 1, false)) {
                // slot clicked was not in chisel inv
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        ItemStack hand = player.getHandItems().iterator().next();
        if (!hand.isOf(Chisel.chiselSupplier.get()))
        {
            ItemStack chiselStack = ItemStack.EMPTY;
            int chiselSlot = -1;

            // Find the chisel in inventory
            for (int i = 0; i < player.getInventory().size(); i++)
            {
                if(player.getInventory().getStack(i).isOf(Chisel.chiselSupplier.get()))
                {
                    chiselStack = player.getInventory().getStack(i);
                    chiselSlot = i;
                    break; // Stop at the first chisel found
                }
            }

            // Only modify inventory if we found a chisel
            if (chiselSlot != -1 && !chiselStack.isEmpty() && chiselStack.getCount() > 0) {
                player.getInventory().removeStack(chiselSlot);
                player.getInventory().setStack(0, chiselStack);
            }
        }

        if (inventory.isEmpty()) {
            BundleContentsComponent c = InventoryUtil.createBundleComponent(inventory);
            var changes = ComponentChanges.builder().remove(DataComponentTypes.BUNDLE_CONTENTS).build();
            hand.applyChanges(changes);
        } else {
            BundleContentsComponent c = InventoryUtil.createBundleComponent(inventory);
            var changes = ComponentChanges.builder().remove(DataComponentTypes.BUNDLE_CONTENTS).add(DataComponentTypes.BUNDLE_CONTENTS, c).build();
            hand.applyChanges(changes);
        }

    }

    @Override
    public void onSlotClick(int i, int j, SlotActionType actionType, PlayerEntity playerEntity) {
        if (i >= 0 && i < this.slots.size()) {
            Slot slot = this.slots.get(i);
            if (slot.getStack().getItem() instanceof ChiselItem) {
                return;
            }
        }
        super.onSlotClick(i, j, actionType, playerEntity);
    }

    private static class SlotChiselOutput extends Slot {
        public SlotChiselOutput(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public void onTakeItem(PlayerEntity player, ItemStack stack) {
            this.inventory.clear();
            super.onTakeItem(player, stack);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false;
        }

        @Override
        public int getMaxItemCount() {
            return 64;
        }



    }

}