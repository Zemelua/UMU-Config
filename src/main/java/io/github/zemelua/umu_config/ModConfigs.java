package io.github.zemelua.umu_config;

import io.github.zemelua.umu_config.old.api.config.IConfigProvider;
import io.github.zemelua.umu_config.old.api.config.container.IConfigContainer;

import java.util.List;

public final class ModConfigs implements IConfigProvider {
	@Override
	public List<IConfigContainer> getConfigs() {
		return List.of();
	}

	@Deprecated public ModConfigs() {}
}
