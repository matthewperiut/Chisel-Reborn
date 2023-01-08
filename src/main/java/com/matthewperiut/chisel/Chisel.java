package com.matthewperiut.chisel;

import com.matthewperiut.chisel.block.BlockRegister;
import com.matthewperiut.chisel.gui.ChiselScreenHandler;
import com.matthewperiut.chisel.item.ChiselItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Chisel implements ModInitializer
{
	public static final String MOD_ID = "chisel";
	public static final Logger LOGGER = LogManager.getFormatterLogger("Chisel");

	public static final Item ITEM_CHISEL = new ChiselItem(new FabricItemSettings().maxCount(1));
	public static final ItemGroup CHISEL_GROUP = FabricItemGroup.builder(new Identifier(MOD_ID, "chisel")).icon(() -> new ItemStack(ITEM_CHISEL)).build();

	public static final Identifier CHISEL_SOUND_ID = new Identifier(MOD_ID, "chisel_sound");
	public static SoundEvent CHISEL_SOUND_EVENT = SoundEvent.of(CHISEL_SOUND_ID);

	public static final Identifier CHISEL_INVENTORY = new Identifier(MOD_ID, "chisel_inventory");
	public static final ScreenHandlerType<ChiselScreenHandler> CHISEL_SCREEN_HANDLER;

	static {
		CHISEL_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(CHISEL_INVENTORY, ChiselScreenHandler::new);
	}

	@Override
	public void onInitialize()
	{
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "chisel"), ITEM_CHISEL);
		Registry.register(Registries.SOUND_EVENT, CHISEL_SOUND_ID, CHISEL_SOUND_EVENT);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ITEM_CHISEL));
		BlockRegister.Register();
	}
}
