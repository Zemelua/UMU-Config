package io.github.zemelua.umu_config;

import io.github.zemelua.umu_config.config.IConfigProvider;
import io.github.zemelua.umu_config.config.container.ConfigContainer;
import io.github.zemelua.umu_config.config.container.IConfigContainer;

import java.util.List;

public final class ModConfigs implements IConfigProvider {
	private static final IConfigContainer COMMON_CONFIG;

	@Override
	public List<IConfigContainer> getConfigs() {
		return List.of(COMMON_CONFIG);
	}

	static {
		COMMON_CONFIG = new ConfigContainer.Builder(UMUConfig.identifier("umu_config"))
				.build();
	}

	@Deprecated public ModConfigs() {}
}
