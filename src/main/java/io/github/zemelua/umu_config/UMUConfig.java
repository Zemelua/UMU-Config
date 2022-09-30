package io.github.zemelua.umu_config;

import io.github.zemelua.umu_config.config.ConfigManager;
import io.github.zemelua.umu_config.config.IConfigProvider;
import io.github.zemelua.umu_config.config.category.ConfigCategory;
import io.github.zemelua.umu_config.config.container.ConfigContainer;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import io.github.zemelua.umu_config.config.value.BooleanConfigValue;
import io.github.zemelua.umu_config.config.value.FloatConfigValue;
import io.github.zemelua.umu_config.config.value.IConfigValue;
import io.github.zemelua.umu_config.config.value.IntegerConfigValue;
import io.github.zemelua.umu_config.network.NetworkHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Locale;

public class UMUConfig implements ModInitializer, IConfigProvider {
	public static final String MOD_ID = "umu_config";
	public static final Logger LOGGER = LogManager.getLogger("UMU-Config");

	public static final IConfigValue<Integer> EXAMPLE_VALUE_INT = new IntegerConfigValue(
			UMUConfig.identifier("example_int"),
			0,
			10,
			0,
			i -> Text.literal(String.valueOf(i)));
	public static final IConfigValue<Float> EXAMPLE_VALUE_FLOAT = new FloatConfigValue(
			UMUConfig.identifier("example_float"),
			5.0F, 25.0F, 2.0F, value -> Text.literal(String.format(Locale.ROOT, "%.1f", value))
	);
	public static final IConfigValue<Boolean> EXAMPLE_CATEGORIZED_VALUE_BOOL = new BooleanConfigValue(
			UMUConfig.identifier("example_categorized_bool"),
			false
	);

	public static final IConfigContainer EXAMPLE_CONFIG = new ConfigContainer(UMUConfig.identifier("example"),
			new BooleanConfigValue(UMUConfig.identifier("example_bool"), false),
			EXAMPLE_VALUE_INT,
			EXAMPLE_VALUE_FLOAT,
			new ConfigCategory(
					UMUConfig.identifier("example"),
					EXAMPLE_CATEGORIZED_VALUE_BOOL
			)
	);

	@Override
	public void onInitialize() {
		NetworkHandler.initialize();
		ConfigManager.initialize();

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> ConfigManager.stream()
				.forEach(config -> ConfigManager.sendToClient(handler.getPlayer(), config)));
	}

	public static Identifier identifier(String path) {
		return new Identifier(MOD_ID, path);
	}

	@Override
	public List<IConfigContainer> getConfigs() {
		return List.of(EXAMPLE_CONFIG);
	}
}
