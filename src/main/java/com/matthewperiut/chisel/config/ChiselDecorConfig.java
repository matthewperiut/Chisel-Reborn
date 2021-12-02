package com.matthewperiut.chisel.config;

import java.io.File;
import java.util.Arrays;

import com.matthewperiut.chisel.Chisel;
import com.oroarmor.config.Config;
import com.oroarmor.config.ConfigItem;
import com.oroarmor.config.ConfigItemGroup;

import net.fabricmc.loader.api.FabricLoader;

public class ChiselDecorConfig extends Config {
    private static final ConfigItemGroup MAIN_GROUP = new ConfigRoot();
    private static final File CONFIG_DIRECTORY = new File(FabricLoader.getInstance().getConfigDir().toFile(), "chisel");
    private static final File CONFIG_FILE = new File(CONFIG_DIRECTORY, "config.json");
    
    public ChiselDecorConfig() {
		super(Arrays.asList(MAIN_GROUP), CONFIG_FILE, "chisel");
        if (!CONFIG_DIRECTORY.exists()) { 
            CONFIG_DIRECTORY.mkdir();
        }
        readConfigFromFile();
	}
    
    public static class ConfigRoot extends ConfigItemGroup {
        public static final ConfigItem<String> BLOCK_PACKS = new ConfigItem<>("block_packs", "malg", "config.chisel.block_packs");

        public ConfigRoot() {
            super(Arrays.asList(BLOCK_PACKS), "chisel_config");
        }
    }

    public String[] getBlockPackDirs() {
        ConfigItem<?> dirStringConfigItem = this.getConfigs().get(0).getConfigs().get(0); // bit ugly but ok
        Object result = dirStringConfigItem.getValue();
        if (!(result instanceof String)) {
            return new String[]{};
        }
        String dirString = (String) result;
        String[] blockPackDirs = dirString.replaceAll("\\s+","").split(",");
        String msg = String.format("Found %d blockpack(s): %s.", blockPackDirs.length, Arrays.toString(blockPackDirs));
        Chisel.LOGGER.info(msg);
        return blockPackDirs;
    }
}
