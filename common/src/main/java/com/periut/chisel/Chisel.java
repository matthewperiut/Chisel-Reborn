package com.periut.chisel;

import com.periut.chisel.block.BlockRegister;
import com.periut.chisel.gui.ChiselScreenHandler;
import com.periut.chisel.item.ChiselItem;
import com.periut.chisel.platform.RegistryHelper;
import com.periut.chisel.registry.ItemGroupRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;

public class Chisel {
    public static final String MOD_ID = "chisel";
    public static final Logger LOGGER = LogManager.getFormatterLogger("Chisel");

    public static final Identifier CHISEL_SOUND_ID = Identifier.fromNamespaceAndPath(MOD_ID, "chisel_sound");
    public static SoundEvent CHISEL_SOUND_EVENT = SoundEvent.createVariableRangeEvent(CHISEL_SOUND_ID);

    public static Supplier<MenuType<ChiselScreenHandler>> CHISEL_SCREEN_HANDLER;

    public static ArrayList<String> transparentBlocks = new ArrayList<>();
    public static ArrayList<String> translucentBlocks = new ArrayList<>();

    public static Supplier<Item> chiselSupplier;

    public static void init()
    {
        // Register screen handler first
        CHISEL_SCREEN_HANDLER = RegistryHelper.register(
                BuiltInRegistries.MENU,
                Identifier.fromNamespaceAndPath(MOD_ID, "chisel_screen_handler"),
                () -> new MenuType<>(ChiselScreenHandler::new, FeatureFlags.VANILLA_SET)
        );

        ItemGroupRegistry.Register();
        Identifier chisel_id = Identifier.fromNamespaceAndPath("chisel", "chisel");
        ResourceKey<Item> chisel_key = ResourceKey.create(Registries.ITEM, chisel_id);
        Chisel.chiselSupplier = RegistryHelper.register(BuiltInRegistries.ITEM, chisel_id, () -> new ChiselItem(new Item.Properties().stacksTo(1).setId(chisel_key)));
        BlockRegister.Register();
    }
}
