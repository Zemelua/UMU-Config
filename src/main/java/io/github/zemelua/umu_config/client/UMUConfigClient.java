package io.github.zemelua.umu_config.client;

import io.github.zemelua.umu_config.ModConfigs;
import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.api.client.config.ConfigManager;
import io.github.zemelua.umu_config.config.ConfigFileManager;
import io.github.zemelua.umu_config.network.NetworkHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

import static net.fabricmc.api.EnvType.CLIENT;

@Environment(CLIENT)
public class UMUConfigClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientConfigManager.initialize();
		NetworkHandler.initializeClient();

		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			ConfigManager.INSTANCE.stream()
					.forEach(ConfigFileManager::loadTo);
		});

		ClientTickEvents.START_CLIENT_TICK.register(s -> {
			UMUConfig.LOGGER.info(ModConfigs.TEST_BOOLEAN.getValue());
		});
	}
}
