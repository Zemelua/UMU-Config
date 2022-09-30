package io.github.zemelua.umu_config.client;

import io.github.zemelua.umu_config.ConfigHandler;
import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.config.ConfigManager;
import io.github.zemelua.umu_config.config.IConfigProvider;
import io.github.zemelua.umu_config.config.value.IConfigValue;
import io.github.zemelua.umu_config.config.container.ConfigContainer;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import io.github.zemelua.umu_config.config.value.BooleanConfigValue;
import io.github.zemelua.umu_config.network.NetworkHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

import java.util.List;

import static net.fabricmc.api.EnvType.*;

@Environment(CLIENT)
public class UMUConfigClient implements ClientModInitializer, IConfigProvider {
	public static final IConfigValue<Boolean> EXAMPLE_VALUE_BOOL = new BooleanConfigValue(UMUConfig.identifier("example_client_bool"), true);

	private static final IConfigContainer EXAMPLE_CONFIG_CLIENT = new ConfigContainer(
			UMUConfig.identifier("example_client"),
			EXAMPLE_VALUE_BOOL
	);

	@Override
	public void onInitializeClient() {
		ConfigManager.initializeClient();
		NetworkHandler.initializeClient();

		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> ConfigManager.stream()
				.forEach(ConfigHandler::loadTo));
	}

	@Override
	public List<IConfigContainer> getConfigs() {
		return List.of(EXAMPLE_CONFIG_CLIENT);
	}
}
