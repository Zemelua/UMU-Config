package io.github.zemelua.umu_config.network;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ConfigPayload(Identifier id, NbtCompound nbt) implements CustomPayload {
	public static final CustomPayload.Id<ConfigPayload> ID = new CustomPayload.Id<>(NetworkHandler.CHANNEL_SYNC_CONFIG_TO_CLIENT);
	public static final PacketCodec<RegistryByteBuf, ConfigPayload> CODEC = PacketCodec.tuple(
			Identifier.PACKET_CODEC, ConfigPayload::id,
			PacketCodecs.NBT_COMPOUND, ConfigPayload::nbt,
			ConfigPayload::new
	);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
