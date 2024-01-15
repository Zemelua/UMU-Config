package io.github.zemelua.umu_config.client;

import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.api.config.IConfigProvider;
import io.github.zemelua.umu_config.api.config.category.ConfigCategory;
import io.github.zemelua.umu_config.api.config.container.ConfigContainer;
import io.github.zemelua.umu_config.api.config.container.IConfigContainer;
import io.github.zemelua.umu_config.api.config.value.BooleanConfigValue;
import io.github.zemelua.umu_config.api.config.value.EnumConfigValue;
import io.github.zemelua.umu_config.api.config.value.IntegerConfigValue;
import io.github.zemelua.umu_config.api.util.UMUConfigClothUtils;
import io.github.zemelua.umu_config.config.ConfigFileManager;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public class ModClientConfigs implements IConfigProvider {
	public static final BooleanConfigValue TEST_BOOLEAN = BooleanConfigValue.builder(UMUConfig.identifier("test_boolean"))
			.defaultValue(false)
			.build();
	public static final IntegerConfigValue TEST_INTEGER = IntegerConfigValue.builder(UMUConfig.identifier("test_integer"))
			.defaultValue(3)
			.range(0, 10)
			.build();
	public static final EnumConfigValue<TestEnum> TEST_ENUM = EnumConfigValue.builder(UMUConfig.identifier("test_enum"), TestEnum.class)
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

	@Environment(EnvType.CLIENT)
	public static ConfigBuilder asScreen(Screen parent) {
		ConfigBuilder builder = ConfigBuilder.create();
		ConfigEntryBuilder entryBuilder = builder.entryBuilder();

		SubCategoryBuilder subCategoryBuilder = entryBuilder.startSubCategory(Text.literal("Additional"));
		subCategoryBuilder.add(UMUConfigClothUtils.selector(builder, TEST_ENUM).build());

		builder.getOrCreateCategory(Text.literal("UMU Config Client"))
				.addEntry(UMUConfigClothUtils.toggle(builder, TEST_BOOLEAN).build())
				.addEntry(UMUConfigClothUtils.field(builder, TEST_INTEGER).build())
				.addEntry(subCategoryBuilder.build());


		builder.setParentScreen(parent)
				.setTitle(CONFIG.getName())
				.setSavingRunnable(() -> ConfigFileManager.saveFrom(CONFIG));

		return builder;
	}

	private ModClientConfigs() {}
}
