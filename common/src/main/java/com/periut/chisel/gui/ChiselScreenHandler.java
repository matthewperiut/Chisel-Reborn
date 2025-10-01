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
import net.minecraft.util.Hand;

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
                    ChiselItem.chiselSound(player.getEntityWorld(), player.getBlockPos());
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
        ItemStack hand = player.getStackInHand(Hand.MAIN_HAND);

        // Sanitize inventory before any modifications
        sanitizeInventory(player.getInventory());

        if (!hand.isOf(Chisel.chiselSupplier.get())) {
            ItemStack chiselStack = findChiselInInventory(player);

            // Only modify inventory if we found a valid chisel stack
            if (chiselStack != null && !chiselStack.isEmpty() && chiselStack.getCount() > 0) {
                // Ensure we're not creating an invalid stack
                chiselStack = chiselStack.copyWithCount(Math.max(1, Math.min(chiselStack.getCount(), chiselStack.getMaxCount())));

                // Remove from original slot and set to first slot
                player.getInventory().removeStack(player.getInventory().getSlotWithStack(chiselStack));
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

    // Helper method to find a chisel in the player's inventory
    private ItemStack findChiselInInventory(PlayerEntity player) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isOf(Chisel.chiselSupplier.get()) && stack.getCount() > 0) {
                return stack;
            }
        }
        return null;
    }

    // Sanitization method for inventory
    private void sanitizeInventory(PlayerInventory inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);

            // Sanitize stack
            if (stack == null || stack.isEmpty() || stack.getCount() <= 0) {
                inventory.setStack(i, ItemStack.EMPTY);
            } else {
                // Ensure count is within valid range
                int sanitizedCount = Math.max(1, Math.min(stack.getCount(), stack.getMaxCount()));
                if (sanitizedCount != stack.getCount()) {
                    inventory.setStack(i, stack.copyWithCount(sanitizedCount));
                }
            }
        }
    }

    
    @Override
    public void onSlotClick(int i, int j, SlotActionType actionType, PlayerEntity playerEntity) {
        if (i >= 0 && i < this.slots.size()) {
            Slot slot = this.slots.get(i);

            if (slot.getStack().getItem() instanceof ChiselItem) {
                return;
            }
            
            ItemStack outputStack = slot.getStack();
            ItemStack inputStack = this.slots.get(0).getStack();
            ItemStack cursorStack = playerEntity.currentScreenHandler.getCursorStack();

            if (actionType == SlotActionType.PICKUP && j == 1 && i > 0 && i < inventory.size()) {

                if (!outputStack.isEmpty() && this.slots.get(0).hasStack() && inputStack.getCount() > 1) {
                    int half = inputStack.getCount() / 2;

                    if (!cursorStack.isEmpty()) {
                        if (!cursorStack.isOf(outputStack.getItem()) || cursorStack.getCount() >= cursorStack.getMaxCount()) {
                            return;
                        }
                        if (cursorStack.getCount() + half > cursorStack.getMaxCount()) {
                            inputStack.decrement(cursorStack.getMaxCount() - cursorStack.getCount());
                            cursorStack.increment(cursorStack.getMaxCount() - cursorStack.getCount());
                        }else{
                            inputStack.decrement(half);
                            cursorStack.increment(half);
                        }

                    } else {
                        ItemStack newCursorStack = outputStack.copy();
                        newCursorStack.setCount(half);
                        inputStack.decrement(half);
                        playerEntity.currentScreenHandler.setCursorStack(newCursorStack);
                    }

                    this.markSlotsAndPlaySound(slot, playerEntity);
                    return;
                }
            }
            
            if (actionType == SlotActionType.PICKUP 
                && i > 0 
                && i < inventory.size() 
                && this.slots.get(0).getStack().getCount() == 1
                && !cursorStack.isEmpty()
                && cursorStack.getCount() < cursorStack.getMaxCount()
                && cursorStack.isOf(outputStack.getItem())
                ) {

                this.slots.get(0).setStack(ItemStack.EMPTY);
                cursorStack.increment(1);

                this.markSlotsAndPlaySound(slot, playerEntity);
                return;
            }
 
            if (actionType == SlotActionType.PICKUP 
                && j == 0 
                && i > 0 
                && i < inventory.size()
                && !cursorStack.isEmpty()
                && cursorStack.getCount() < cursorStack.getMaxCount()
                && cursorStack.isOf(outputStack.getItem())
                ) {
                    
                if ( cursorStack.getCount() + inputStack.getCount() > cursorStack.getMaxCount()){
                    inputStack.decrement(cursorStack.getMaxCount() - cursorStack.getCount());
                    cursorStack.increment(cursorStack.getMaxCount() - cursorStack.getCount());
                }else{
                    cursorStack.increment(inputStack.getCount());
                    this.slots.get(0).setStack(ItemStack.EMPTY);
                }
                
                this.markSlotsAndPlaySound(slot, playerEntity);
                return;
            }
        }



        ItemStack before = this.slots.get(0).getStack().copy();
        super.onSlotClick(i, j, actionType, playerEntity);

        ItemStack after = this.slots.get(0).getStack();

        if (i > 0 && i < inventory.size() && (
            !ItemStack.areItemsEqual(before, after) || before.getCount() != after.getCount()
        )) {
            ChiselItem.chiselSound(playerEntity.getEntityWorld(), playerEntity.getBlockPos());
        }

    }

    private void markSlotsAndPlaySound (Slot slot, PlayerEntity playerEntity) {
        this.slots.get(0).markDirty();
        slot.markDirty();
        ChiselItem.chiselSound(playerEntity.getEntityWorld(), playerEntity.getBlockPos());
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