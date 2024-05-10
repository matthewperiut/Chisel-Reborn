package com.matthewperiut.chisel.block;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.*;

public class ChiselGroupLookup {
    private static final Map<String, ChiselGroup> CHISEL_GROUPS = new HashMap<>();

    private ChiselGroupLookup() {
    }

    public static Iterator<String> getGroupNameIterator() {
        return CHISEL_GROUPS.keySet().iterator();
    }

    public static void addGroup(String name) {
        if (!CHISEL_GROUPS.keySet().contains(name)) {
            CHISEL_GROUPS.put(name, new ChiselGroup());
        }
    }

    public static void addItemToGroup(String name, Identifier id) {
        if (!CHISEL_GROUPS.keySet().contains(name)) {
            addGroup(name);
        }
        CHISEL_GROUPS.get(name).addItem(id);
    }

    public static void addItemToGroup(String name, List<Identifier> id) {
        if (!CHISEL_GROUPS.keySet().contains(name)) {
            addGroup(name);
        }
        for (int i = 0; i < id.size(); i++) {
            CHISEL_GROUPS.get(name).addItem(id.get(i));
        }

    }

    public static void addTagToGroup(String name, Identifier id) {
        if (!CHISEL_GROUPS.keySet().contains(name)) {
            addGroup(name);
        }
        CHISEL_GROUPS.get(name).addTag(id);
    }

    public static void addTagToGroup(String name, List<Identifier> id) {
        if (!CHISEL_GROUPS.keySet().contains(name)) {
            addGroup(name);
        }
        for (int i = 0; i < id.size(); i++) {
            CHISEL_GROUPS.get(name).addTag(id.get(i));
        }

    }

    public static ChiselGroup getGroup(Item item) {
        Iterator<ChiselGroup> chiselGroupIterator = CHISEL_GROUPS.values().iterator();
        Identifier itemId = Registries.ITEM.getId(item);
        while (chiselGroupIterator.hasNext()) {
            ChiselGroup group = chiselGroupIterator.next();
            if (group.containsItem(itemId)) {
                return group;
            }
        }
        return null;
    }

    public static List<Item> getBlocksInGroup(String name) {
        ChiselGroup group = CHISEL_GROUPS.get(name);
        if (group != null) {
            return getBlocksInGroup(group);
        }
        return new ArrayList<>();
    }

    public static List<Item> getBlocksInGroup(Item item) {
        ChiselGroup group = getGroup(item);
        if (group != null) {
            return getBlocksInGroup(group);
        }
        return new ArrayList<>();
    }

    public static List<Item> getBlocksInGroup(ChiselGroup group) {
        List<Item> groupItems = new ArrayList<>();
        groupItems.addAll(group.getItems());
        return groupItems;
    }

    public static List<Identifier> getTagsFor(Item item) {
        List<Identifier> tags = new ArrayList<>();
        //redo later
        return tags;
    }

    public static class ChiselGroup {
        private final List<Identifier> items;
        private final List<Identifier> tags;

        public ChiselGroup() {
            this.items = new ArrayList<>();
            this.tags = new ArrayList<>();
        }

        public void addItem(Identifier item) {
            items.add(item);
        }

        public void addTag(Identifier tag) {
            tags.add(tag);
        }

        public boolean containsItem(Identifier item) {
            return inItems(item) || inTags(item);
        }

        public boolean inItems(Identifier item) {
            return items.contains(item);
        }

        public boolean inTags(Identifier id) {
            // redo later when it matters
            return false;
        }

        public List<Item> getItems() {
            List<Item> itemsInGroup = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                Item item = Registries.ITEM.get(items.get(i));
                if (item.equals(Items.AIR)) {
                    continue;
                }
                itemsInGroup.add(item);
            }
            List<Item> itemsInGroupNoDupes = new ArrayList<>();
            for (int i = 0; i < itemsInGroup.size(); i++) {
                Item item = itemsInGroup.get(i);
                if (!itemsInGroupNoDupes.contains(item)) {
                    itemsInGroupNoDupes.add(item);
                }
            }
            return itemsInGroupNoDupes;
        }
    }

}
