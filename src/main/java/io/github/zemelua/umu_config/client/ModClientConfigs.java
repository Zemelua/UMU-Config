package io.github.zemelua.umu_config.client;

import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.api.config.IConfigProvider;
import io.github.zemelua.umu_config.api.config.category.ConfigCategory;
import io.github.zemelua.umu_config.api.config.container.ConfigContainer;
import io.github.zemelua.umu_config.api.config.container.IConfigContainer;
import io.github.zemelua.umu_config.api.config.value.BooleanConfigValue;
import io.github.zemelua.umu_config.api.config.value.EnumConfigValue;
import io.github.zemelua.umu_config.api.config.value.IntegerConfigValue;

import java.util.List;

public class ModClientConfigs implements IConfigProvider {
	public static final BooleanConfigValue TEST_BOOLEAN = BooleanConfigValue.builder(UMUConfig.identifier("test_boolean"))
			.defaultValue(false)
			.build();
	public static final IntegerConfigValue TEST_INTEGER = IntegerConfigValue.builder(UMUConfig.identifier("test_integer"))
			.defaultValue(3)
			.range(0, 10)
			.build();
	public static final EnumConfigValue<TestEnum> TEST_ENUM = EnumConfigValue.<TestEnum>builder(UMUConfig.identifier("test_enum"), TestEnum.class)
			.defaultValue(TestEnum.GOLD)
			.build();
	public static final IConfigContainer CONFIG = ConfigContainer.builder(UMUConfig.identifier("umu_config_client"))
			.addValue(TEST_BOOLEAN)
			.addValue(TEST_INTEGER)
			.addCategory(ConfigCategory.builder(UMUConfig.identifier("additional"))
					.addValue(TEST_ENUM)
					.build())
			.build();

	public enum TestEnum {
		RED,
		GOLD,
		RAINBOW
	}

	@Override
	public List<IConfigContainer> getConfigs() {
		return List.of(CONFIG);
	}

	public ModClientConfigs() {}
}
