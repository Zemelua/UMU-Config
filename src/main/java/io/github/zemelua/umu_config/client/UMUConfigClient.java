package io.github.zemelua.umu_config.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;

import static net.fabricmc.api.EnvType.CLIENT;

@Environment(CLIENT)
public class UMUConfigClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientConfigManager.initialize();
		// NetworkHandler.initializeClient();

//		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> ConfigManager.stre()
//				.forEach(ConfigFileManager::loadTo));
	}
}
