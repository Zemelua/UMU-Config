package io.github.zemelua.umu_config.old.api.config;

import io.github.zemelua.umu_config.old.api.config.container.IConfigContainer;

import java.util.List;

public interface IConfigProvider {
	List<IConfigContainer> getConfigs();
}
