package com.matthewperiut.chisel;

import com.matthewperiut.chisel.block.BlockRegister;
import com.matthewperiut.chisel.gui.ChiselScreenHandler;
import com.matthewperiut.chisel.item.ChiselItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Chisel implements ModInitializer
{
	public static final String MOD_ID = "chisel";
	public static final Logger LOGGER = LogManager.getFormatterLogger("Chisel");

	public static final Item ITEM_CHISEL = new ChiselItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1));
	public static final ItemGroup CHISEL_GROUP = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "chisel")).icon(() -> new ItemStack(ITEM_CHISEL)).build();


	public static final Identifier CHISEL_INVENTORY = new Identifier(MOD_ID, "chisel_inventory");
	public static final ScreenHandlerType<ChiselScreenHandler> CHISEL_SCREEN_HANDLER;

	static {
		CHISEL_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(CHISEL_INVENTORY, ChiselScreenHandler::new);
	}

	@Override
	public void onInitialize()
	{
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "chisel"), ITEM_CHISEL);

		BlockRegister.Register();
		com.matthewperiut.chisel.legacy.Chisel.onInitialize();
	}
}
