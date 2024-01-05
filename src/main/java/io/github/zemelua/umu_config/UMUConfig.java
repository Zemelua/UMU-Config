package io.github.zemelua.umu_config;

import io.github.zemelua.umu_config.network.NetworkHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UMUConfig implements ModInitializer {
	public static final String MOD_ID = "umu_config";
	public static final Logger LOGGER = LogManager.getLogger("UMU-Config");

	@Override
	public void onInitialize() {
		NetworkHandler.initialize();
		// ConfigManager.initialize();

//		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> ConfigManager.streamCommon()
//				.forEach(config -> ConfigManager.sendToClient(handler.getPlayer(), config)));
	}

	public static Identifier identifier(String path) {
		return new Identifier(MOD_ID, path);
	}
}
