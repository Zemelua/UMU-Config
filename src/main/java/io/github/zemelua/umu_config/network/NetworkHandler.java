package io.github.zemelua.umu_config.network;

import io.github.zemelua.umu_config.UMUConfig;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import static net.fabricmc.api.EnvType.*;

public final class NetworkHandler {
	public static final Identifier CHANNEL_SYNC_SINGLEPLAY_CONFIG = UMUConfig.identifier("sync_singleplay_config");
	public static final Identifier CHANNEL_SYNC_MULTIPLAY_CONFIG = UMUConfig.identifier("sync_multiplay_config");

	public static void initialize() {
		ServerPlayNetworking.registerGlobalReceiver(CHANNEL_SYNC_SINGLEPLAY_CONFIG, (server, player, handler, packet, sender) -> {
			final String configName = packet.readString();
			final NbtCompound values = packet.readNbt();

			server.execute(() -> PacketHandlers.syncSingleplayConfig(configName, values));
		});
		ServerPlayNetworking.registerGlobalReceiver(CHANNEL_SYNC_MULTIPLAY_CONFIG, (server, player, handler, packet, sender) -> {
			final String configName = packet.readString();
			final NbtCompound values = packet.readNbt();

			server.execute(() -> PacketHandlers.syncMultiplayConfig(server, configName, values));
		});
	}

	public static final Identifier CHANNEL_SYNC_CONFIG_TO_CLIENT = UMUConfig.identifier("sync_config");

	@Environment(CLIENT)
	public static void initializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(CHANNEL_SYNC_CONFIG_TO_CLIENT, (client, handler, packet, sender) -> {
			final String configName = packet.readString();
			final NbtCompound values = packet.readNbt();

			client.execute(() -> PacketHandlers.syncConfigOnClient(configName, values));
		});
	}

	@Deprecated private NetworkHandler() {}
}
