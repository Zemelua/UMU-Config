package io.github.zemelua.umu_config.network;

import io.github.zemelua.umu_config.UMUConfig;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import static net.fabricmc.api.EnvType.CLIENT;

public final class NetworkHandler {
	public static final Identifier CHANNEL_SYNC_CONFIG_TO_CLIENT = UMUConfig.identifier("sync_config");

	@Environment(CLIENT)
	public static void initializeClient() {
		PayloadTypeRegistry.playS2C().register(ConfigPayload.ID, ConfigPayload.CODEC);

		ClientPlayNetworking.registerGlobalReceiver(ConfigPayload.ID, (packet, context) -> {
			final Identifier configID = packet.id();
			final NbtCompound values = packet.nbt();

			context.client().execute(() -> PacketHandlers.syncConfigOnClient(configID, values));
		});
	}

	private NetworkHandler() {}
}
