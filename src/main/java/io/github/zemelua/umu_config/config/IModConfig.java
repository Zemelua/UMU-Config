package io.github.zemelua.umu_config.config;

import io.github.zemelua.umu_config.config.container.IConfigContainer;

import java.util.Collection;

public interface IModConfig {
	Collection<IConfigContainer> getConfigs();
}
