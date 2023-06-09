package io.github.zemelua.umu_config.config;

import io.github.zemelua.umu_config.config.container.IConfigContainer;

import java.util.List;

public interface IConfigProvider {
	List<IConfigContainer> getConfigs();
}
