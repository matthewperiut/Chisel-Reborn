package com.periut.chisel;

import com.periut.chisel.block.BlockRegister;
import com.periut.chisel.gui.ChiselScreenHandler;
import com.periut.chisel.item.ChiselItem;
import com.periut.chisel.platform.RegistryHelper;
import com.periut.chisel.registry.ItemGroupRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.function.Supplier;

public class Chisel {
    public static final String MOD_ID = "chisel";
    public static final Logger LOGGER = LogManager.getFormatterLogger("Chisel");

    public static final Identifier CHISEL_SOUND_ID = Identifier.of(MOD_ID, "chisel_sound");
    public static SoundEvent CHISEL_SOUND_EVENT = SoundEvent.of(CHISEL_SOUND_ID);

    public static Supplier<ScreenHandlerType<ChiselScreenHandler>> CHISEL_SCREEN_HANDLER;

    public static ArrayList<String> transparentBlocks = new ArrayList<>();
    public static ArrayList<String> translucentBlocks = new ArrayList<>();

    public static Supplier<Item> chiselSupplier;

    public static void init()
    {
        // Register screen handler first
        CHISEL_SCREEN_HANDLER = RegistryHelper.register(
                Registries.SCREEN_HANDLER,
                Identifier.of(MOD_ID, "chisel_screen_handler"),
                () -> new ScreenHandlerType<>(ChiselScreenHandler::new, FeatureFlags.VANILLA_FEATURES)
        );

        ItemGroupRegistry.Register();
        Identifier chisel_id = Identifier.of("chisel", "chisel");
        RegistryKey<Item> chisel_key = RegistryKey.of(RegistryKeys.ITEM, chisel_id);
        Chisel.chiselSupplier = RegistryHelper.register(Registries.ITEM, chisel_id, () -> new ChiselItem(new Item.Settings().maxCount(1).registryKey(chisel_key)));
        BlockRegister.Register();
    }
}
