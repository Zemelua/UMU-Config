package io.github.zemelua.umu_config.client;

import io.github.zemelua.umu_config.api.client.config.ConfigManager;

public final class ClientConfigManager {
	public static final ConfigManager INSTANCE = new ConfigManager();

	public static void initialize() {
		INSTANCE.initialize("umu-config-client");
	}


}
