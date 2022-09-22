package io.github.zemelua.umu_config.config;

import io.github.zemelua.umu_config.config.container.IConfigContainer;

import java.util.Collection;

public interface IConfigProvider {
	Collection<IConfigContainer> getConfigs();
}
