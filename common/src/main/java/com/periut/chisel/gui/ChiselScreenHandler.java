package com.periut.chisel.gui;

import com.periut.chisel.Chisel;
import com.periut.chisel.inventory.ChiselInventory;
import com.periut.chisel.inventory.InventoryUtil;
import com.periut.chisel.item.ChiselItem;
import com.periut.cryonicconfig.CryonicConfig;
import java.time.LocalTime;
import java.util.ArrayList;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;

public class ChiselScreenHandler extends AbstractContainerMenu {
    boolean compactTexture;
    private final ChiselInventory inventory;
    private final BundleContents componentInventory;
    LocalTime currentTime = LocalTime.now();

    // MC 26.1: ItemStack.getMaxStackSize() was removed; the value lives in the MAX_STACK_SIZE component.
    private static int maxStack(ItemStack s) {
        return s.getOrDefault(DataComponents.MAX_STACK_SIZE, 64);
    }

    public ChiselScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new ChiselInventory());
    }

    public ChiselScreenHandler(int syncId, Inventory playerInventory, Container inventory) {
        this(syncId, playerInventory, inventory, new BundleContents(new ArrayList<>()));
    }

    public ChiselScreenHandler(int syncId, Inventory playerInventory, Container inventory, BundleContents component) {
        super(Chisel.CHISEL_SCREEN_HANDLER.get(), syncId);
        compactTexture = CryonicConfig.getConfig("chisel").getBoolean("compact_chisel_gui", false);
        checkContainerSize(inventory, 61);
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
    public boolean stillValid(Player player) {
        return true;
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack quickMoveStack(Player player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem(); // stack in slot clicked
            newStack = originalStack.copy();
            if (originalStack.getItem().equals(Chisel.chiselSupplier.get())) {
                return ItemStack.EMPTY;
            }
            if (invSlot < this.inventory.getContainerSize()) {
                // slot clicked was in chisel inv
                if (invSlot != 0) {
                    if (this.slots.get(0).hasItem()) {
                        int count = this.slots.get(0).getItem().getCount();
                        if (count > 0 && count <= 99) {
                            originalStack.setCount(count);
                        }
                    }
                    ChiselItem.chiselSound(player.level(), player.blockPosition());
                }
                if (!this.moveItemStackTo(originalStack, this.inventory.getContainerSize(), this.slots.size(), true)) {
                    this.inventory.refresh(this.slots.get(0).getItem().getItem());
                    return ItemStack.EMPTY;
                } else {
                    this.inventory.clearContent();
                }

            } else if (!this.moveItemStackTo(originalStack, 0, 1, false)) {
                // slot clicked was not in chisel inv
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return newStack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        ItemStack hand = player.getItemInHand(InteractionHand.MAIN_HAND);

        // Sanitize inventory before any modifications
        sanitizeInventory(player.getInventory());

        if (!hand.is(Chisel.chiselSupplier.get())) {
            ItemStack chiselStack = findChiselInInventory(player);

            // Only modify inventory if we found a valid chisel stack
            if (chiselStack != null && !chiselStack.isEmpty() && chiselStack.getCount() > 0) {
                // Ensure we're not creating an invalid stack
                chiselStack = chiselStack.copyWithCount(Math.max(1, Math.min(chiselStack.getCount(), maxStack(chiselStack))));

                // Remove from original slot and set to first slot
                player.getInventory().removeItemNoUpdate(player.getInventory().findSlotMatchingItem(chiselStack));
                player.getInventory().setItem(0, chiselStack);
            }
        }

        if (inventory.isEmpty()) {
            BundleContents c = InventoryUtil.createBundleComponent(inventory);
            var changes = DataComponentPatch.builder().remove(DataComponents.BUNDLE_CONTENTS).build();
            hand.applyComponentsAndValidate(changes);
        } else {
            BundleContents c = InventoryUtil.createBundleComponent(inventory);
            var changes = DataComponentPatch.builder().remove(DataComponents.BUNDLE_CONTENTS).set(DataComponents.BUNDLE_CONTENTS, c).build();
            hand.applyComponentsAndValidate(changes);
        }
    }

    // Helper method to find a chisel in the player's inventory
    private ItemStack findChiselInInventory(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(Chisel.chiselSupplier.get()) && stack.getCount() > 0) {
                return stack;
            }
        }
        return null;
    }

    // Sanitization method for inventory
    private void sanitizeInventory(Inventory inventory) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);

            // Sanitize stack
            if (stack == null || stack.isEmpty() || stack.getCount() <= 0) {
                inventory.setItem(i, ItemStack.EMPTY);
            } else {
                // Ensure count is within valid range
                int sanitizedCount = Math.max(1, Math.min(stack.getCount(), maxStack(stack)));
                if (sanitizedCount != stack.getCount()) {
                    inventory.setItem(i, stack.copyWithCount(sanitizedCount));
                }
            }
        }
    }


    @Override
    public void clicked(int i, int j, ContainerInput actionType, Player playerEntity) {
        if (i >= 0 && i < this.slots.size()) {
            Slot slot = this.slots.get(i);

            if (slot.getItem().getItem() instanceof ChiselItem) {
                return;
            }

            ItemStack outputStack = slot.getItem();
            ItemStack inputStack = this.slots.get(0).getItem();
            ItemStack cursorStack = playerEntity.containerMenu.getCarried();

            if (actionType == ContainerInput.PICKUP && j == 1 && i > 0 && i < inventory.getContainerSize()) {

                if (!outputStack.isEmpty() && this.slots.get(0).hasItem() && inputStack.getCount() > 1) {
                    int half = inputStack.getCount() / 2;

                    if (!cursorStack.isEmpty()) {
                        if (!cursorStack.is(outputStack.getItem()) || cursorStack.getCount() >= maxStack(cursorStack)) {
                            return;
                        }
                        if (cursorStack.getCount() + half > maxStack(cursorStack)) {
                            inputStack.shrink(maxStack(cursorStack) - cursorStack.getCount());
                            cursorStack.grow(maxStack(cursorStack) - cursorStack.getCount());
                        }else{
                            inputStack.shrink(half);
                            cursorStack.grow(half);
                        }

                    } else {
                        ItemStack newCursorStack = outputStack.copy();
                        newCursorStack.setCount(half);
                        inputStack.shrink(half);
                        playerEntity.containerMenu.setCarried(newCursorStack);
                    }

                    this.markSlotsAndPlaySound(slot, playerEntity);
                    return;
                }
            }

            if (actionType == ContainerInput.PICKUP
                && i > 0
                && i < inventory.getContainerSize()
                && this.slots.get(0).getItem().getCount() == 1
                && !cursorStack.isEmpty()
                && cursorStack.getCount() < maxStack(cursorStack)
                && cursorStack.is(outputStack.getItem())
                ) {

                this.slots.get(0).setByPlayer(ItemStack.EMPTY);
                cursorStack.grow(1);

                this.markSlotsAndPlaySound(slot, playerEntity);
                return;
            }

            if (actionType == ContainerInput.PICKUP
                && j == 0
                && i > 0
                && i < inventory.getContainerSize()
                && !cursorStack.isEmpty()
                && cursorStack.getCount() < maxStack(cursorStack)
                && cursorStack.is(outputStack.getItem())
                ) {

                if ( cursorStack.getCount() + inputStack.getCount() > maxStack(cursorStack)){
                    inputStack.shrink(maxStack(cursorStack) - cursorStack.getCount());
                    cursorStack.grow(maxStack(cursorStack) - cursorStack.getCount());
                }else{
                    cursorStack.grow(inputStack.getCount());
                    this.slots.get(0).setByPlayer(ItemStack.EMPTY);
                }

                this.markSlotsAndPlaySound(slot, playerEntity);
                return;
            }
        }



        ItemStack before = this.slots.get(0).getItem().copy();
        super.clicked(i, j, actionType, playerEntity);

        ItemStack after = this.slots.get(0).getItem();

        if (i > 0 && i < inventory.getContainerSize() && (
            !ItemStack.isSameItem(before, after) || before.getCount() != after.getCount()
        )) {
            ChiselItem.chiselSound(playerEntity.level(), playerEntity.blockPosition());
        }

    }

    private void markSlotsAndPlaySound (Slot slot, Player playerEntity) {
        this.slots.get(0).setChanged();
        slot.setChanged();
        ChiselItem.chiselSound(playerEntity.level(), playerEntity.blockPosition());
    }


    private static class SlotChiselOutput extends Slot {
        public SlotChiselOutput(Container inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public void onTake(Player player, ItemStack stack) {
            this.container.clearContent();
            super.onTake(player, stack);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }

        @Override
        public int getMaxStackSize() {
            return 64;
        }



    }

}
