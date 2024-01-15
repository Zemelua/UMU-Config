package io.github.zemelua.umu_config.util;

import net.minecraft.client.MinecraftClient;

public final class ModUtils {
	public static boolean isInMultiplayServer() {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.world == null) return false;

		return !client.isIntegratedServerRunning();
	}

	private ModUtils() {}
}
