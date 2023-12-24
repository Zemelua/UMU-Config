package io.github.zemelua.umu_config.api.config;

import io.github.zemelua.umu_config.api.config.container.IConfigContainer;

import java.util.List;

public interface IConfigProvider {
	List<IConfigContainer> getConfigs();
}
