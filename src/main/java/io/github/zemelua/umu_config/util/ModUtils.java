package io.github.zemelua.umu_config.util;

import net.fabricmc.loader.api.FabricLoader;

public final class ModUtils {
	public static String getModName(String modID) {
		return FabricLoader.getInstance().getModContainer(modID).orElseThrow().getMetadata().getName();
	}
}
