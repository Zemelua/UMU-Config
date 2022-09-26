package io.github.zemelua.umu_config.network;

import io.github.zemelua.umu_config.ConfigHandler;
import io.github.zemelua.umu_config.config.ConfigManager;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public final class PacketHandlers {
	public static void syncSingleplayConfig(String name, NbtCompound values) {
		IConfigContainer config = ConfigManager.byName(name).orElseThrow(IllegalStateException::new);

		config.loadFrom(values);
		ConfigHandler.saveFrom(config);
	}

	public static void syncMultiplayConfig(MinecraftServer server, String name, NbtCompound values) {
		IConfigContainer config = ConfigManager.byName(name).orElseThrow(IllegalStateException::new);

		config.loadFrom(values);
		ConfigHandler.saveFrom(config);
		for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
			ConfigManager.sendToClient(player, config);
		}
	}

	public static void syncConfigOnClient(String name, NbtCompound values) {
		IConfigContainer config = ConfigManager.byName(name).orElseThrow(IllegalStateException::new);

		config.loadFrom(values);
	}
}
