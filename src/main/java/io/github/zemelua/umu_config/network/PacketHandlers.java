package io.github.zemelua.umu_config.network;

import io.github.zemelua.umu_config.api.client.config.ConfigManager;
import io.github.zemelua.umu_config.api.config.container.IConfigContainer;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import static net.fabricmc.api.EnvType.*;

public final class PacketHandlers {
	// private static final Text FAILED_RECEIVE_CONFIG = Text.translatable("config.error.failed_receive_config").formatted(Formatting.YELLOW);

	@Environment(CLIENT)
	public static void syncConfigOnClient(Identifier ID, NbtCompound values) {
		IConfigContainer config = ConfigManager.INSTANCE.fromID(ID).orElseThrow();

		config.loadFrom(values);
	}
}
