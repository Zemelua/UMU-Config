package io.github.zemelua.umu_config.config;

import com.google.common.collect.ImmutableList;
import io.github.zemelua.umu_config.ConfigHandler;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

import java.util.List;
import java.util.Optional;

public final class ConfigManager {
	private static ImmutableList<IConfigContainer> CONFIGS;

	public static void initialize() {
		List<EntrypointContainer<IConfigProvider>> modConfigs = FabricLoader.getInstance().getEntrypointContainers("umu-config", IConfigProvider.class);
		CONFIGS = modConfigs.stream().flatMap(entrypoint -> {
			IConfigProvider modConfig = entrypoint.getEntrypoint();
			return modConfig.getConfigs().stream();
		}).collect(ImmutableList.toImmutableList());

		CONFIGS.forEach(ConfigHandler::loadTo);
		CONFIGS.forEach(ConfigHandler::saveFrom);
	}

	public static Optional<IConfigContainer> byName(String name) {
		return CONFIGS.stream().filter(config -> config.getName().equals(name))
				.findFirst();
	}
}
