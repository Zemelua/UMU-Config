package io.github.zemelua.umu_config.client;

import io.github.zemelua.umu_config.config.ConfigFileManager;
import io.github.zemelua.umu_config.config.ConfigManager;
import io.github.zemelua.umu_config.network.NetworkHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

import static net.fabricmc.api.EnvType.*;

@Environment(CLIENT)
public class UMUConfigClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ConfigManager.initializeClient();
		NetworkHandler.initializeClient();

		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> ConfigManager.streamCommon()
				.forEach(ConfigFileManager::loadTo));
	}
}
