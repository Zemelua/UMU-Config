package io.github.zemelua.umu_config;

import io.github.zemelua.umu_config.api.config.IConfigProvider;
import io.github.zemelua.umu_config.api.config.container.ConfigContainer;
import io.github.zemelua.umu_config.api.config.container.IConfigContainer;
import io.github.zemelua.umu_config.api.config.value.BooleanConfigValue;
import io.github.zemelua.umu_config.api.config.value.IConfigValue;

import java.util.List;

public final class ModConfigs implements IConfigProvider {
	public static final IConfigValue<Boolean> TEST_BOOLEAN = BooleanConfigValue.builder(UMUConfig.identifier("test_boolean"))
			.build();
	private static final IConfigContainer CONTAINER = ConfigContainer.builder(UMUConfig.identifier("umu_config"))
			.addValue(TEST_BOOLEAN)
			.build();

	@Override
	public List<IConfigContainer> getConfigs() {
		return List.of(CONTAINER);
	}

	@Deprecated public ModConfigs() {}
}
