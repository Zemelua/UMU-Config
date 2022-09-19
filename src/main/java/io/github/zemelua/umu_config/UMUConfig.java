package io.github.zemelua.umu_config;

import io.github.zemelua.umu_config.config.IModConfig;
import io.github.zemelua.umu_config.config.container.BasicConfigContainer;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import io.github.zemelua.umu_config.config.container.TestConfigContainer;
import io.github.zemelua.umu_config.config.value.BooleanConfigValue;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UMUConfig implements ModInitializer, IModConfig {
	public static final Logger LOGGER = LogManager.getLogger("UMU-Config");

	public static final IConfigContainer TEST_CONFIG = new TestConfigContainer();
	public static final IConfigContainer TEST_BASIC_CONFIG = new BasicConfigContainer("test_basic", new BooleanConfigValue("test_basicBool", false));

	private static final List<IConfigContainer> CONFIGS = new ArrayList<>();

	@Override
	public void onInitialize() {
		List<EntrypointContainer<IModConfig>> modConfigs = FabricLoader.getInstance().getEntrypointContainers("umu-config", IModConfig.class);
		modConfigs.forEach(entrypoint -> {
			IModConfig modConfig = entrypoint.getEntrypoint();
			CONFIGS.addAll(modConfig.getConfigs());
		});

		CONFIGS.forEach(ConfigHandler::loadTo);
		CONFIGS.forEach(ConfigHandler::saveFrom);
	}

	@Override
	public Collection<IConfigContainer> getConfigs() {
		return List.of(TEST_CONFIG, TEST_BASIC_CONFIG);
	}
}
