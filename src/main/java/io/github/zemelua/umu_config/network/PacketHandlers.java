package io.github.zemelua.umu_config.network;

import io.github.zemelua.umu_config.config.ConfigFileManager;
import io.github.zemelua.umu_config.old.api.config.ConfigManager;
import io.github.zemelua.umu_config.old.api.config.container.IConfigContainer;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import static net.fabricmc.api.EnvType.*;

public final class PacketHandlers {
	private static final Text FAILED_RECEIVE_CONFIG = Text.translatable("config.error.failed_receive_config").formatted(Formatting.YELLOW);

	public static void syncSingleplayConfig(Identifier ID, NbtCompound values) {
		IConfigContainer config = ConfigManager.byNameCommon(ID).orElseThrow(IllegalStateException::new);

		config.loadFrom(values);
		ConfigFileManager.saveFrom(config);
	}

	public static void syncMultiplayConfig(MinecraftServer server, Identifier ID, NbtCompound values, PlayerEntity sender) {
		IConfigContainer config = ConfigManager.byNameCommon(ID).orElseThrow(IllegalStateException::new);

		if (config.canEdit(sender)) {
			config.loadFrom(values);
			ConfigFileManager.saveFrom(config);
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				ConfigManager.sendToClient(player, config);
			}
		} else {
			sender.sendMessage(FAILED_RECEIVE_CONFIG);
		}
	}

	@Environment(CLIENT)
	public static void syncConfigOnClient(Identifier ID, NbtCompound values) {
		IConfigContainer config = ConfigManager.byNameCommon(ID).orElseThrow(IllegalStateException::new);

		config.loadFrom(values);
	}

	@Environment(CLIENT)
	public static void reloadConfigsOnClient() {
		ConfigManager.streamCommon().forEach(ConfigFileManager::loadTo);
		ConfigManager.streamClient().forEach(ConfigFileManager::loadTo);
		ConfigManager.streamCommon().forEach(ConfigFileManager::saveFrom);
		ConfigManager.streamClient().forEach(ConfigFileManager::saveFrom);
	}
}
