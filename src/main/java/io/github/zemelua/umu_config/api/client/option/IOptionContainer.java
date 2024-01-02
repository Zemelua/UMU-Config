package io.github.zemelua.umu_config.api.client.option;

import io.github.zemelua.umu_config.api.client.option.config.IConfigValue;

import java.util.Collection;

public interface IOptionContainer {
	Collection<IConfigValue<?>> getAllConfigs();
	Collection<IOption<?>> getAllOptions();
}
