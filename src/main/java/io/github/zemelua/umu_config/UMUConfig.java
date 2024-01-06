package io.github.zemelua.umu_config;

import io.github.zemelua.umu_config.api.client.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UMUConfig implements ModInitializer {
	public static final String MOD_ID = "umu_config";
	public static final Logger LOGGER = LogManager.getLogger("UMU-Config");

	@Override
	public void onInitialize() {
		MinecraftClient.getInstance();

		UMUConfig.LOGGER.info(FabricLoader.getInstance().getEnvironmentType().toString() + FabricLoader.getInstance().getConfigDir());

		ConfigManager.INSTANCE.initialize("umu-config");

		// ConfigManager.initialize();

		ServerPlayConnectionEvents.INIT.register((handler, sender) -> ConfigManager.INSTANCE.stream()
				.forEach(config -> ConfigManager.sendToClient(handler.getPlayer(), config)));
	}

	public static Identifier identifier(String path) {
		return new Identifier(MOD_ID, path);
	}
}
