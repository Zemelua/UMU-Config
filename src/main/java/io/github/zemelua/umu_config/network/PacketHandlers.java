package io.github.zemelua.umu_config.network;

import io.github.zemelua.umu_config.config.ConfigFileManager;
import io.github.zemelua.umu_config.config.ConfigManager;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public final class PacketHandlers {
	public static void syncSingleplayConfig(Identifier ID, NbtCompound values) {
		IConfigContainer config = ConfigManager.byName(ID).orElseThrow(IllegalStateException::new);

		config.loadFrom(values);
		ConfigFileManager.saveFrom(config);
	}

	public static void syncMultiplayConfig(MinecraftServer server, Identifier ID, NbtCompound values) {
		IConfigContainer config = ConfigManager.byName(ID).orElseThrow(IllegalStateException::new);

		config.loadFrom(values);
		ConfigFileManager.saveFrom(config);
		for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
			ConfigManager.sendToClient(player, config);
		}
	}

	public static void syncConfigOnClient(Identifier ID, NbtCompound values) {
		IConfigContainer config = ConfigManager.byName(ID).orElseThrow(IllegalStateException::new);

		config.loadFrom(values);
	}
}
