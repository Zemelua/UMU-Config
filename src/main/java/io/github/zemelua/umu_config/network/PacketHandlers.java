package io.github.zemelua.umu_config.network;

import io.github.zemelua.umu_config.config.ConfigManager;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import net.minecraft.nbt.NbtCompound;

import java.util.Optional;

public final class PacketHandlers {
	public static void saveConfig(String name, NbtCompound values) {
		Optional<IConfigContainer> config = ConfigManager.byName(name);
	}
}
