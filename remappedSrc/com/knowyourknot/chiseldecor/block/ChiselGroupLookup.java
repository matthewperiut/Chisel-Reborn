package com.knowyourknot.chiseldecor.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ChiselGroupLookup {
    private static final Map<String, ChiselGroup> CHISEL_GROUPS = new HashMap<>();

    private ChiselGroupLookup() {}

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

    public static ChiselGroup getGroup(Item item, World world) {
        Iterator<ChiselGroup> chiselGroupIterator = CHISEL_GROUPS.values().iterator();
        Identifier itemId = Registry.ITEM.getId(item);
        while (chiselGroupIterator.hasNext()) {
            ChiselGroup group = chiselGroupIterator.next();
            if (group.containsItem(itemId, world)) {
                return group;
            }
        }
        return null;
    }

    public static List<Item> getBlocksInGroup(Item item, World world) {
        ChiselGroup group = getGroup(item, world);
        if (group != null) {
            return getBlocksInGroup(group, world);
        }
        return new ArrayList<>();
    }

    public static List<Item> getBlocksInGroup(ChiselGroup group, World world) {
        List<Item> groupItems = new ArrayList<>();
        groupItems.addAll(group.getItems(world));
        return groupItems;
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

        public boolean containsItem(Identifier item, World world) {
            return inItems(item) || inTags(item, world);
        }

        public boolean inItems(Identifier item) {
            return items.contains(item);
        }

        public boolean inTags(Identifier id, World world) {
            Item item = Registry.ITEM.get(id);
            TagGroup<Item> itemTags = world.getTagManager().getItems();
            Iterator<Identifier> tagsForItem = itemTags.getTagsFor(item).iterator();
            while (tagsForItem.hasNext()) {
                if (tags.contains(tagsForItem.next())) {
                    return true;
                }
            }
            return false;
        }
        
        public List<Item> getItems(World world) {
            List<Item> itemsInGroup = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                Item item = Registry.ITEM.get(items.get(i));
                if (item.equals(Items.AIR)) {
                    continue;
                }
                itemsInGroup.add(item);
            }
            TagGroup<Item> itemTags = world.getTagManager().getItems();
            for (int i = 0; i < tags.size(); i++) {
                Tag<Item> itemTag = itemTags.getTag(tags.get(i));
                if (itemTag == null) {
                    continue;
                }
                List<Item> tagItems = itemTag.values();
                itemsInGroup.addAll(tagItems);
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
