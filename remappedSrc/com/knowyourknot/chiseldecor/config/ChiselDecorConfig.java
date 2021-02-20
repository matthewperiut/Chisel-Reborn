package com.knowyourknot.chiseldecor.config;

import java.io.File;
import java.util.Arrays;

import com.oroarmor.config.Config;
import com.oroarmor.config.ConfigItem;
import com.oroarmor.config.ConfigItemGroup;

import net.fabricmc.loader.api.FabricLoader;

public class ChiselDecorConfig extends Config {
    private static final ConfigItemGroup MAIN_GROUP = new ConfigRoot();
    private static final File CONFIG_DIRECTORY = new File(FabricLoader.getInstance().getConfigDir().toFile(), "chiseldecor");
    private static final File CONFIG_FILE = new File(CONFIG_DIRECTORY, "config.json");

    public ChiselDecorConfig() {
		super(Arrays.asList(MAIN_GROUP), CONFIG_FILE, "chiseldecor");
        if (!CONFIG_DIRECTORY.exists()) { 
            CONFIG_DIRECTORY.mkdir();
            DefaultBlockPack.createDefaultBlockPack();
        }
        readConfigFromFile();
	}
    
    public static class ConfigRoot extends ConfigItemGroup {
        public static final ConfigItem<String> BLOCK_PACKS = new ConfigItem<>("block_packs", "default", "config.chiseldecor.block_packs");
        public static final ConfigItem<Boolean> REGEN_DEFAULT = new ConfigItem<>("regen_default", false, "config.chiseldecor.regen_default");

        public ConfigRoot() {
            super(Arrays.asList(BLOCK_PACKS, REGEN_DEFAULT), "chiseldecor_config");
        }
    }

    public String[] getBlockPackDirs() {
        ConfigItem<?> dirStringConfigItem = this.getConfigs().get(0).getConfigs().get(0); // bit ugly but ok
        Object result = dirStringConfigItem.getValue();
        if (!(result instanceof String)) {
            return new String[]{};
        }
        String dirString = (String) result;
        return dirString.replaceAll("\\s+","").split(",");
    }

    public void refreshDefaultBlockPack() {
        System.out.println("Deleting and re-creating default blockpack.");
        DefaultBlockPack.deleteDefaultBlockPack();
        DefaultBlockPack.createDefaultBlockPack();
    }
}
