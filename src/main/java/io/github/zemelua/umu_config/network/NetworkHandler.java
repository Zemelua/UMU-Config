package io.github.zemelua.umu_config.network;

import io.github.zemelua.umu_config.UMUConfig;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import static net.fabricmc.api.EnvType.CLIENT;

public final class NetworkHandler {
	public static final Identifier CHANNEL_SYNC_CONFIG_TO_CLIENT = UMUConfig.identifier("sync_config");

	@Environment(CLIENT)
	public static void initializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(CHANNEL_SYNC_CONFIG_TO_CLIENT, (client, handler, packet, sender) -> {
			final Identifier configID = packet.readIdentifier();
			final NbtCompound values = packet.readNbt();

			client.execute(() -> PacketHandlers.syncConfigOnClient(configID, values));
		});
	}

	@Deprecated private NetworkHandler() {}
}
