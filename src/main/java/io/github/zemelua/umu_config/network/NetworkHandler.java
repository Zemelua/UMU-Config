package io.github.zemelua.umu_config.network;

import io.github.zemelua.umu_config.ConfigHandler;
import io.github.zemelua.umu_config.UMUConfig;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public final class NetworkHandler {
	public static final Identifier CHANNEL_SAVE_CONFIG = UMUConfig.identifier("save_config");

	public static void initialize() {
		ServerPlayNetworking.registerGlobalReceiver(CHANNEL_SAVE_CONFIG, (server, player, handler, packet, sender) -> {
			final String configName = packet.readString();
			final NbtCompound values = packet.readNbt();

			server.execute(() -> {
				UMUConfig.LOGGER.info(server.isRemote() + ": " + ConfigHandler.getConfigPath().toString());
				UMUConfig.LOGGER.info(server.getClass().getName());
			});
		});
	}

	@Deprecated private NetworkHandler() {}
}
