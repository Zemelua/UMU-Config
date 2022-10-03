package io.github.zemelua.umu_config.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;

public final class ModUtils {
	public static String getModName(String modID) {
		return FabricLoader.getInstance().getModContainer(modID).orElseThrow().getMetadata().getName();
	}

	public static boolean isInMultiplayServer() {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.world == null) return false;

		return !client.isIntegratedServerRunning();
	}
}
