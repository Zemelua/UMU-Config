package io.github.zemelua.umu_config.network;

import io.github.zemelua.umu_config.ConfigHandler;
import io.github.zemelua.umu_config.config.ConfigManager;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import net.minecraft.nbt.NbtCompound;

public final class PacketHandlers {
	public static void saveConfig(String name, NbtCompound values) {
		IConfigContainer config = ConfigManager.byName(name).orElseThrow(IllegalStateException::new);

		config.loadFrom(values);
		ConfigHandler.saveFrom(config);
	}
}
