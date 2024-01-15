package io.github.zemelua.umu_config;

import io.github.zemelua.umu_config.api.config.IConfigProvider;
import io.github.zemelua.umu_config.api.config.container.ConfigContainer;
import io.github.zemelua.umu_config.api.config.container.IConfigContainer;
import io.github.zemelua.umu_config.api.config.value.BooleanConfigValue;
import io.github.zemelua.umu_config.api.config.value.IConfigValue;
import io.github.zemelua.umu_config.api.config.value.IRangedConfigValue;
import io.github.zemelua.umu_config.api.config.value.IntegerConfigValue;
import io.github.zemelua.umu_config.api.util.UMUConfigClothUtils;
import io.github.zemelua.umu_config.config.ConfigFileManager;
import io.github.zemelua.umu_config.util.ModUtils;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public final class ModConfigs implements IConfigProvider {
	public static final IConfigValue<Boolean> TEST_BOOLEAN = BooleanConfigValue.builder(UMUConfig.identifier("test_boolean"))
			.build();
	public static final IConfigValue<Integer> TEST_INTEGER = IntegerConfigValue.builder(UMUConfig.identifier("test_integer"))
			.build();
	private static final IConfigContainer CONTAINER = ConfigContainer.builder(UMUConfig.identifier("umu_config/commons"))
			.addValue(TEST_BOOLEAN)
			.build();

	@Override
	public List<IConfigContainer> getConfigs() {
		return List.of(CONTAINER);
	}

	@Environment(EnvType.CLIENT)
	public static ConfigBuilder asScreen(Screen parent) {
		ConfigBuilder builder = ConfigBuilder.create();

		builder.getOrCreateCategory(Text.literal("UMU Config"))
				.addEntry(UMUConfigClothUtils.toggle(builder, TEST_BOOLEAN).build())
				.addEntry(UMUConfigClothUtils.field(builder, (IRangedConfigValue<Integer>) TEST_INTEGER).build());


		builder.setParentScreen(parent)
				.setTitle(CONTAINER.getName())
				.setSavingRunnable(() -> ConfigFileManager.saveFrom(CONTAINER))
				.setEditable(!ModUtils.isInMultiplayServer());

		return builder;
	}

	private ModConfigs() {}
}
